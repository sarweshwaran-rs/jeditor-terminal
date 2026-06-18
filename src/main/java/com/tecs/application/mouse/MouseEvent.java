package com.tecs.application.mouse;

public record MouseEvent(
    MouseEventType type,
    MouseButton button,
    int row,
    int column
) { }
