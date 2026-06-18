package com.tecs.application.mouse.windows;

final class Win32Constants {
    private Win32Constants() {

    }

    static final int STD_INPUT_HANDLE = -10;
    static final int ENABLE_MOUSE_INPUT = 0x0010;
    static final int ENABLE_EXTENDED_FLAGS  = 0x0080;
    static final int ENABLE_QUICK_EDIT_MODE = 0x0040;

    static final short MOUSE_EVENT = 0x0002;

    static final int FROM_LEFT_1ST_BUTTON_PRESSED = 0x0001;
    static final int FROM_LEFT_2ND_BUTTON_PRESSED = 0x0004;
    static final int RIGHTMOST_BUTTON_PRESSED = 0x0002;

    static final int MOUSE_MOVED = 0x0001;
    static final int DOUBLE_CLICK = 0x0002;
    static final int MOUSE_WHEELED = 0x0004;
    static final int MOUSE_HWHEELED = 0x0008;
}
