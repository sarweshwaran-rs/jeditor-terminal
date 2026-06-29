package com.tecs.application.mouse.windows;

import java.lang.foreign.Arena;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.foreign.FunctionDescriptor;

import java.lang.invoke.MethodHandle;

public final class Win32Support {

    private MemorySegment inputHandle;
    private int originalConsoleMode;
    private final Arena arena;
    private final MethodHandle getStdHandle;
    private final MethodHandle getConsoleMode;
    private final MethodHandle setConsoleMode;
    private final MethodHandle readConsoleInputW;
    private final MemorySegment record;
    private final MemorySegment numberRead;

    public Win32Support() {
        Linker linker = Linker.nativeLinker();
        arena = Arena.ofShared();

        SymbolLookup kernel32 = SymbolLookup.libraryLookup("kernel32", arena);
        getStdHandle = linker.downcallHandle(
            kernel32.find("GetStdHandle").orElseThrow(),
            FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)
        );

        getConsoleMode = linker.downcallHandle(
            kernel32.find("GetConsoleMode").orElseThrow(),
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)
        );

        setConsoleMode = linker.downcallHandle(
            kernel32.find("SetConsoleMode").orElseThrow(),
            FunctionDescriptor.of(
                ValueLayout.JAVA_INT,
                ValueLayout.ADDRESS,
                ValueLayout.JAVA_INT
            )
        );

        readConsoleInputW = linker.downcallHandle(
            kernel32.find("ReadConsoleInputW").orElseThrow(),
            FunctionDescriptor.of(
                ValueLayout.JAVA_INT,
                ValueLayout.ADDRESS,
                ValueLayout.ADDRESS,
                ValueLayout.JAVA_INT,
                ValueLayout.ADDRESS
            )
        );

        record = arena.allocate(Win32Layouts.INPUT_RECORD);
        numberRead = arena.allocate(ValueLayout.JAVA_INT);
    }

    private MemorySegment getInputHandle()  {
        try {
            if(inputHandle == null) {
                inputHandle = (MemorySegment) getStdHandle.invoke(Win32Constants.STD_INPUT_HANDLE);
            }
            return inputHandle;
        } catch (Throwable ex) {
            throw new RuntimeException("Unable to obtain console input handle. ", ex);
        }
    }

    private int getConsoleMode() {
        try {
            MemorySegment mode =arena.allocate(ValueLayout.JAVA_INT);

            int result = (int) getConsoleMode.invoke(getInputHandle(), mode);

            if(result == 0) {
                throw new IllegalStateException("GetConsoleMode failed");
            }

            return mode.get(ValueLayout.JAVA_INT, 0);
        } catch(Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void setConsoleMode(int mode) {
        try {
            int result = (int) setConsoleMode.invoke(getInputHandle(), mode);

            if(result == 0) {
                throw new IllegalStateException("setConsoleMode failed");
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void enableMouse() {
        originalConsoleMode = getConsoleMode();

        int mode = originalConsoleMode;

        mode &= ~Win32Constants.ENABLE_QUICK_EDIT_MODE;

        mode |= Win32Constants.ENABLE_EXTENDED_FLAGS;

        mode |= Win32Constants.ENABLE_MOUSE_INPUT;

        setConsoleMode(mode);
    }

    public MemorySegment readInputRecord() {
        try {
            int result = (int) readConsoleInputW.invoke(
                    getInputHandle(),
                    record,
                    1,
                    numberRead);

            if(result == 0) {
                throw new IllegalStateException("ReadConsoleInputW failed.");
            }

            if(numberRead.get(ValueLayout.JAVA_INT, 0) == 0) {
                return null;
            }

            return record;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void disableMouse() {
        setConsoleMode(originalConsoleMode);
    }
}
