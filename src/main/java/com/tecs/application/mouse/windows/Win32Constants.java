package com.tecs.application.mouse.windows;

public final class Win32Constants {
    private Win32Constants() {

    }

    public static final int STD_INPUT_HANDLE = -10;
    public static final int ENABLE_MOUSE_INPUT = 0x0010;
    public static final int ENABLE_EXTENDED_FLAGS = 0x0080;
    public static final int ENABLE_QUICK_EDIT_MODE = 0x0040;

    public static final short KEY_EVENT = 0x0001;
    public static final short MOUSE_EVENT = 0x0002;

    public static final int FROM_LEFT_1ST_BUTTON_PRESSED = 0x0001;
    public static final int FROM_LEFT_2ND_BUTTON_PRESSED = 0x0004;
    public static final int RIGHTMOST_BUTTON_PRESSED = 0x0002;

    public static final int MOUSE_MOVED = 0x0001;
    public static final int DOUBLE_CLICK = 0x0002;
    public static final int MOUSE_WHEELED = 0x0004;
    public static final int MOUSE_HWHEELED = 0x0008;

    public static final int VK_BACK = 0x08;
    public static final int VK_TAB = 0x09;
    public static final int VK_RETURN = 0x0D;
    public static final int VK_ESCAPE = 0x1B;
    public static final int VK_SPACE = 0x20;
    public static final int VK_PRIOR = 0x21;
    public static final int VK_NEXT = 0x22;
    public static final int VK_END = 0x23;
    public static final int VK_HOME = 0x24;
    public static final int VK_LEFT = 0x25;
    public static final int VK_UP = 0x26;
    public static final int VK_RIGHT = 0x27;
    public static final int VK_DOWN = 0x28;
    public static final int VK_DELETE = 0x2E;

    public static final int RIGHT_ALT_PRESSED = 0x0001;
    public static final int LEFT_ALT_PRESSED = 0x0002;
    public static final int RIGHT_CTRL_PRESSED = 0x0004;
    public static final int LEFT_CTRL_PRESSED = 0x0008;
    public static final int SHIFT_PRESSED = 0x0010;
    public static final int VK_F = 0x46;
    public static final int VK_G = 0x47;
    public static final int VK_L = 0x4C;
    public static final int VK_N = 0x4E;
    public static final int VK_O = 0x4F;
    public static final int VK_Q = 0x51;
    public static final int VK_S = 0x53;
    public static final int VK_T = 0x54;

    public static final int VK_A = 0x41;
    public static final int VK_C = 0x43;
    public static final int VK_V = 0x56;
    public static final int VK_X = 0x58;
    public static final int VK_Y = 0x59;
    public static final int VK_Z = 0x5A;
}
