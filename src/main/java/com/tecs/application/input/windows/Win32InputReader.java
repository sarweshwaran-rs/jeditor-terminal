package com.tecs.application.input.windows;

import com.tecs.application.input.InputEvent;
import com.tecs.application.input.InputReader;
import com.tecs.application.input.KeyboardInputEvent;
import com.tecs.application.input.MouseInputEvent;
import com.tecs.application.mouse.windows.*;
import com.tecs.application.terminal.Key;

import java.lang.foreign.MemorySegment;

public final class Win32InputReader implements InputReader, AutoCloseable {
    private  final Win32Support support;
    private final WindowsMouseAdapter mouseAdapter;
    private final WindowsKeyAdapter keyAdapter;

    public Win32InputReader() {
        support = new Win32Support();
        mouseAdapter = new WindowsMouseAdapter();
        keyAdapter = new WindowsKeyAdapter();
        support.enableMouse();
    }

    @Override
    public InputEvent read() {
        while(true) {
            MemorySegment record = support.readInputRecord();

            if (record == null) {
                return null;
            }

            switch (Win32Structures.eventType(record)) {
                case Win32Constants.KEY_EVENT -> {
                    KeyboardInputEvent event = readKeyboard(record);

                    if (event != null) {
                        return event;
                    }
                    continue;
                }

                case Win32Constants.MOUSE_EVENT -> {
                    MouseInputEvent event = readMouse(record);

                    if (event != null) {
                        return event;
                    }
                    continue;
                }

                default -> {
                    continue;
                }
            }
        }
    }

    private KeyboardInputEvent readKeyboard(MemorySegment record) {
        Win32NativeKeyEvent event = new Win32NativeKeyEvent(
                Win32Structures.keyDown(record),
                Win32Structures.virtualKey(record),
                Win32Structures.unicodeChar(record),
                Win32Structures.controlKeyState(record)
        );

        Key key = keyAdapter.convert(event);

        if (key == null) {
            return null;
        }

        return new KeyboardInputEvent(key);
    }

    private MouseInputEvent readMouse(MemorySegment record) {
        Win32NativeMouseEvent event = new Win32NativeMouseEvent(
                Win32Structures.x(record),
                Win32Structures.y(record),
                Win32Structures.buttonState(record),
                Win32Structures.controlKeyState(record),
                Win32Structures.eventFlags(record)
        );

        var mouseEvent = mouseAdapter.convert(event);

        if (mouseEvent == null) {
            return null;
        }

        return new MouseInputEvent(mouseEvent);
    }

    @Override
    public void close() {
        support.disableMouse();
    }
}
