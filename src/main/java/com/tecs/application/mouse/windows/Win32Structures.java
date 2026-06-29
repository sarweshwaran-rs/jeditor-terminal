package com.tecs.application.mouse.windows;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;

public final class Win32Structures {
    // Helpers
    // contains
    /**
     * eventType(segment)
     * mousePosition(segment)
     * buttonState(segment)
     * controlKeyState(segment)
     * eventFlags(segment)
    */

    private Win32Structures() {

    }

    /**
     * INPUT_RECORD.EventType
     */

    private static final VarHandle EVENT_TYPE = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("EventType")
    );

    /**
     * Mouse Position
     */
    private static final VarHandle MOUSE_X = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("Event"),
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwMousePosition"),
        MemoryLayout.PathElement.groupElement("X")
    );

    private static final VarHandle MOUSE_Y = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("Event"),
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwMousePosition"),
        MemoryLayout.PathElement.groupElement("Y")
    );

    /**
     * Button State
     */
    private static final VarHandle BUTTON_STATE = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("Event"),
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwButtonState")
    );

    /**
     * Control Key State
     */
    private static final VarHandle CONTROL_KEY_STATE = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("Event"),
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwControlKeyState")
    );

    /**
     * Event Flags
     */
    private static final VarHandle EVENT_FLAGS = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("Event"),
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwEventFlags")
    );

    private static final VarHandle KEY_DOWN = Win32Layouts.INPUT_RECORD.varHandle(
      MemoryLayout.PathElement.groupElement("Event"),
      MemoryLayout.PathElement.groupElement("KeyEvent"),
      MemoryLayout.PathElement.groupElement("bKeyDown")
    );

    private static final VarHandle VIRTUAL_KEY = Win32Layouts.INPUT_RECORD.varHandle(
            MemoryLayout.PathElement.groupElement("Event"),
            MemoryLayout.PathElement.groupElement("KeyEvent"),
            MemoryLayout.PathElement.groupElement("wVirtualKeyCode")
    );

    private static final VarHandle UNICODE = Win32Layouts.INPUT_RECORD.varHandle(
            MemoryLayout.PathElement.groupElement("Event"),
            MemoryLayout.PathElement.groupElement("KeyEvent"),
            MemoryLayout.PathElement.groupElement("Char"),
            MemoryLayout.PathElement.groupElement("UnicodeChar")
    );

    private static final VarHandle KEY_CONTROL = Win32Layouts.INPUT_RECORD.varHandle(
            MemoryLayout.PathElement.groupElement("Event"),
            MemoryLayout.PathElement.groupElement("KeyEvent"),
            MemoryLayout.PathElement.groupElement("dwControlKeyState")
    );

    public static short eventType(MemorySegment record) {
        return (short) EVENT_TYPE.get(record, 0L);
    }

    public static short x(MemorySegment record) {
        return (short) MOUSE_X.get(record, 0L);
    }

    public static short y(MemorySegment record) {
        return (short) MOUSE_Y.get(record, 0L);
    }

    public static int buttonState(MemorySegment record) {
        return (int) BUTTON_STATE.get(record, 0L);
    }

    public static int controlKeyState(MemorySegment record) {
        return switch (eventType(record)){
            case Win32Constants.KEY_EVENT -> (int) KEY_CONTROL.get(record, 0L);

            case Win32Constants.MOUSE_EVENT -> (int) CONTROL_KEY_STATE.get(record, 0L);

            default -> 0;
        };
    }

    public static int eventFlags(MemorySegment record) {
        return (int) EVENT_FLAGS.get(record, 0L);
    }

    public static boolean keyDown(MemorySegment segment) {
        return ((int) KEY_DOWN.get(segment, 0L)) != 0;
    }

    public static short virtualKey(MemorySegment record) {
        return (short) VIRTUAL_KEY.get(record, 0L);
    }

    public static char unicodeChar(MemorySegment record) {
        return (char)((short)UNICODE.get(record, 0L));
    }
}
