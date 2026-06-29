package com.tecs.application.mouse.windows;

public record Win32NativeKeyEvent(
        boolean keyDown,
        short virtualKey,
        char character,
        int controlKeyState
) { }
