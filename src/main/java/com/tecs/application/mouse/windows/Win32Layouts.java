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
    @SuppressWarnings("preview")
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
    @SuppressWarnings("preview")
    static final MemoryLayout MOUSE_EVENT_RECORD =  MemoryLayout.structLayout(
        COORD.withName("dwMousePosition"),
        ValueLayout.JAVA_INT.withName("dwButtonState"),
        ValueLayout.JAVA_INT.withName("dwControlKeyState"),
        ValueLayout.JAVA_INT.withName("dwEventFlags")
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
    @SuppressWarnings("preview")
    static final MemoryLayout INPUT_RECORD = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT.withName("EventType"),
        ValueLayout.JAVA_SHORT.withName("Padding"),
        MOUSE_EVENT_RECORD.withName("MouseEvent")
    );
}
