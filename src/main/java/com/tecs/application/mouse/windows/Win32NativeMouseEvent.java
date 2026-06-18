package com.tecs.application.mouse.windows;

public record Win32NativeMouseEvent(
    short x, 
    short y,
    int buttonState,
    int controlKeyState,
    int eventFlags
) { }
