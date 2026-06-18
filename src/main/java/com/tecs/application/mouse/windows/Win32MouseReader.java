package com.tecs.application.mouse.windows;

import java.lang.foreign.MemorySegment;

import com.tecs.application.mouse.MouseEvent;
import com.tecs.application.mouse.MouseReader;

public final class Win32MouseReader implements MouseReader, AutoCloseable {

    private final Win32Support support; 
    private final WindowsMouseAdapter adapter;

    public Win32MouseReader() {
        this.support = new Win32Support();
        this.adapter = new WindowsMouseAdapter();

        support.enableMouse();
    }

    @SuppressWarnings("preview")
    @Override
    public MouseEvent read() {
        while(true) {
            MemorySegment record = support.readInputRecord();

            if(record == null) {
                return null;
            }

            if(Win32Structures.eventType(record) != Win32Constants.MOUSE_EVENT) {
                continue;
            }

            Win32NativeMouseEvent event = new Win32NativeMouseEvent(
                Win32Structures.x(record),
                Win32Structures.y(record),
                Win32Structures.buttonState(record),
                Win32Structures.controlKeyState(record),
                Win32Structures.eventFlags(record)
            );

            return adapter.convert(event);
        }
    }

    @Override
    public void close() {
        support.disableMouse();
    }
}
