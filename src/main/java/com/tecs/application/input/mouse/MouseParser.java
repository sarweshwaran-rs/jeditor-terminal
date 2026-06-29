package com.tecs.application.input.mouse;

import com.tecs.application.mouse.MouseButton;
import com.tecs.application.mouse.MouseEvent;
import com.tecs.application.mouse.MouseEventType;

public final class MouseParser {

    public MouseEvent parse(String sequence) {
        try {
            if (!sequence.startsWith("\u001B[<")) {
                return null;
            }

            char action = sequence.charAt(sequence.length() - 1);

            String body = sequence.substring(3, sequence.length() - 1);
            String[] parts = body.split(";");

            if (parts.length != 3) {
                return null;
            }

            int code = Integer.parseInt(parts[0]);
            int column = Integer.parseInt(parts[1]);
            int row = Integer.parseInt(parts[2]);

            if (code == 64) {
                return new MouseEvent(MouseEventType.SCROLL_UP, MouseButton.NONE, row, column);
            }

            if (code == 65) {
                return new MouseEvent(MouseEventType.SCROLL_DOWN, MouseButton.NONE, row, column);
            }

            MouseButton button = switch (code) {
                case 0 -> MouseButton.LEFT;
                case 1 -> MouseButton.MIDDLE;
                case 2 -> MouseButton.RIGHT;
                default -> MouseButton.NONE;
            };

            MouseEventType type = action == 'm'
                    ? MouseEventType.RELEASE
                    : MouseEventType.PRESS;

            return new MouseEvent(type, button, row, column);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
