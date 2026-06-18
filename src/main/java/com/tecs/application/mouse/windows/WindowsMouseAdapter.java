package com.tecs.application.mouse.windows;

import com.tecs.application.mouse.MouseButton;
import com.tecs.application.mouse.MouseEvent;
import com.tecs.application.mouse.MouseEventType;

public final class WindowsMouseAdapter {

    private MouseButton previousButton = MouseButton.NONE;

    public MouseEvent convert(Win32NativeMouseEvent event) {
        MouseButton button = switch (event.buttonState()) {
            case Win32Constants.FROM_LEFT_1ST_BUTTON_PRESSED -> button = MouseButton.LEFT;

            case Win32Constants.RIGHTMOST_BUTTON_PRESSED -> button = MouseButton.RIGHT;

            case Win32Constants.FROM_LEFT_2ND_BUTTON_PRESSED -> button = MouseButton.MIDDLE;

            default -> button = MouseButton.NONE;
        };

        MouseEventType type;

        switch (event.eventFlags()) {
            case 0 -> {
                if(button == MouseButton.NONE) {
                    MouseButton released = previousButton;
                    previousButton = MouseButton.NONE;

                    return new MouseEvent(MouseEventType.RELEASE, released, event.y() + 1, event.x() + 1);
                }
                previousButton = button;
                type = MouseEventType.PRESS;
            }

            case Win32Constants.MOUSE_MOVED -> {
                type = button == MouseButton.NONE 
                    ? MouseEventType.MOVE
                    : MouseEventType.DRAG;
            }

            case Win32Constants.DOUBLE_CLICK -> {
                previousButton = button;
                type = MouseEventType.DOUBLE_CLICK;
            }

            case Win32Constants.MOUSE_WHEELED -> {
                return wheel(event);
            }

            case Win32Constants.MOUSE_HWHEELED -> {
                return horizontalWheel(event);
            }
            default -> {
                previousButton = button;
                type = MouseEventType.PRESS;
            }
        }

        return new MouseEvent(type, button, event.y() + 1, event.x() + 1);
    }

    private MouseEvent wheel(Win32NativeMouseEvent event) {
        int delta = (short) (event.buttonState() >> 16);

        MouseEventType type = delta > 0 ? MouseEventType.SCROLL_UP : MouseEventType.SCROLL_DOWN;

        return new MouseEvent(type, MouseButton.NONE, event.y() + 1, event.x() + 1);
    }

    private MouseEvent horizontalWheel(Win32NativeMouseEvent event) {
        int delta = (short) (event.buttonState() >> 16);

        MouseEventType type = delta > 0
                ? MouseEventType.SCROLL_RIGHT
                : MouseEventType.SCROLL_LEFT;

        return new MouseEvent(type, MouseButton.NONE, event.y() + 1, event.x() + 1);
    }
}
