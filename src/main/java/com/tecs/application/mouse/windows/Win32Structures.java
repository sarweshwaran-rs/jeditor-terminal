package com.tecs.application.mouse.windows;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;

final class Win32Structures {
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

    @SuppressWarnings("preview")
    private static final VarHandle EVENT_TYPE = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("EventType")
    );

    /**
     * Mouse Position
     */
    @SuppressWarnings("preview")
    private static final VarHandle MOUSE_X = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwMousePosition"),
        MemoryLayout.PathElement.groupElement("X")
    );

    @SuppressWarnings("preview")
    private static final VarHandle MOUSE_Y = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwMousePosition"),
        MemoryLayout.PathElement.groupElement("Y")
    );

    /**
     * Button State
     */
    @SuppressWarnings("preview")
    private static final VarHandle BUTTON_STATE = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwButtonState")
    );

    /**
     * Control Key State
     */
    @SuppressWarnings("preview")
    private static final VarHandle CONTROL_KEY_STATE = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwControlKeyState")
    );

    /**
     * Event Flags
     */
    @SuppressWarnings("preview")
    private static final VarHandle EVENT_FLAGS = Win32Layouts.INPUT_RECORD.varHandle(
        MemoryLayout.PathElement.groupElement("MouseEvent"),
        MemoryLayout.PathElement.groupElement("dwEventFlags")
    );

    static short eventType(@SuppressWarnings("preview") MemorySegment record) {
        return (short) EVENT_TYPE.get(record);
    }

    static short x(@SuppressWarnings("preview") MemorySegment record) {
        return (short) MOUSE_X.get(record);
    }

    static short y(@SuppressWarnings("preview") MemorySegment record) {
        return (short) MOUSE_Y.get(record);
    }

    static int buttonState(@SuppressWarnings("preview") MemorySegment record) {
        return (int) BUTTON_STATE.get(record);
    }

    static int controlKeyState(@SuppressWarnings("preview") MemorySegment record) {
        return (int) CONTROL_KEY_STATE.get(record);
    }

    static int eventFlags(@SuppressWarnings("preview") MemorySegment record) {
        return (int) EVENT_FLAGS.get(record);
    }
}
