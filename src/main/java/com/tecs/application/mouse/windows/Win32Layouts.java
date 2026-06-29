package com.tecs.application.mouse.windows;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout;

final class Win32Layouts {
    private Win32Layouts() {

    }

    // COORD, MOUSE_EVENT_RECORD, INPUT_RECORD

    /**
     * typedef struct _COORD {
     *  SHORT X;
     *  SHORT Y;
     * } COORD;
     */
    static final MemoryLayout COORD = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT.withName("X"),
        ValueLayout.JAVA_SHORT.withName("Y")
    );

    /**
     * typedef struct MOUSE_EVENT_RECORD {
     *  COORD dwMousePosition;
     *  DWORD dwButtonState;
     *  DWORD dwControlKeyState;
     *  DWORD dwEventFlags;
     * } MOUSE_EVENT_ECORD;
     */
    static final MemoryLayout MOUSE_EVENT_RECORD =  MemoryLayout.structLayout(
        COORD.withName("dwMousePosition"),
        ValueLayout.JAVA_INT.withName("dwButtonState"),
        ValueLayout.JAVA_INT.withName("dwControlKeyState"),
        ValueLayout.JAVA_INT.withName("dwEventFlags")
    );

    static final MemoryLayout KEY_EVENT_RECORD = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("bKeyDown"),
            ValueLayout.JAVA_SHORT.withName("wRepeatCount"),
            ValueLayout.JAVA_SHORT.withName("wVirtualKeyCode"),
            ValueLayout.JAVA_SHORT.withName("wVirtualScanCode"),
            MemoryLayout.unionLayout(
                ValueLayout.JAVA_SHORT.withName("UnicodeChar"),
                ValueLayout.JAVA_BYTE.withName("AsciiChar")
            ).withName("Char"),
            ValueLayout.JAVA_INT.withName("dwControlKeyState")
    );

    /**
     * typedef struct {
     *  WORD EventType;
     *  WORD Padding;
     *  union {
     *  MOUSE_EVENT_RECORD MouseEvent;
     * };
     * } INPUT_RECORD;
     */
    static final MemoryLayout INPUT_RECORD = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT.withName("EventType"),
        ValueLayout.JAVA_SHORT.withName("Padding"),
        MemoryLayout.unionLayout(
            KEY_EVENT_RECORD.withName("KeyEvent"),
            MOUSE_EVENT_RECORD.withName("MouseEvent")
        ).withName("Event")
    );
}
