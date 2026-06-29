package com.tecs.application.mouse.windows;

import com.tecs.application.input.windows.Keys;
import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.KeyType;

public final class WindowsKeyAdapter {
    public Key convert(Win32NativeKeyEvent event) {
        if(!event.keyDown()) {
            return null;
        }

        boolean ctrl = (event.controlKeyState()
                            & (Win32Constants.LEFT_CTRL_PRESSED
                                | Win32Constants.RIGHT_CTRL_PRESSED))
                        != 0;

        boolean shift = (event.controlKeyState() & Win32Constants.SHIFT_PRESSED) != 0;

        if(ctrl) {
            System.err.println("CTRL pressed: " + event.virtualKey());
            switch(event.virtualKey()){
                case Win32Constants.VK_F:
                    return Keys.CTRL_F;

                case Win32Constants.VK_G:
                    return Keys.CTRL_G;

                case Win32Constants.VK_L:
                    return Keys.CTRL_L;

                case Win32Constants.VK_N:
                    return Keys.CTRL_N;

                case Win32Constants.VK_O:
                    return Keys.CTRL_O;

                case Win32Constants.VK_Q:
                    return Keys.CTRL_Q;

                case Win32Constants.VK_S:
                    return Keys.CTRL_S;

                case Win32Constants.VK_T:
                    return Keys.CTRL_T;

                case Win32Constants.VK_A:
                    return Keys.CTRL_A;

                case Win32Constants.VK_C:
                    return Keys.CTRL_C;

                case Win32Constants.VK_V:
                    return Keys.CTRL_V;

                case Win32Constants.VK_X:
                    return Keys.CTRL_X;

                case Win32Constants.VK_Y:
                    return Keys.CTRL_Y;

                case Win32Constants.VK_Z:
                    return Keys.CTRL_Z;
            }
        }

        switch (event.virtualKey()) {
            case Win32Constants.VK_LEFT:
                return new Key(KeyType.ARROW_LEFT, '\0', shift);

            case Win32Constants.VK_RIGHT:
                return new Key(KeyType.ARROW_RIGHT, '\0', shift);

            case Win32Constants.VK_UP:
                return new Key(KeyType.ARROW_UP, '\0', shift);

            case Win32Constants.VK_DOWN:
                return new Key(KeyType.ARROW_DOWN,'\0', shift);

            case Win32Constants.VK_HOME:
                return Keys.HOME;

            case Win32Constants.VK_END:
                return Keys.END;

            case Win32Constants.VK_DELETE:
                return Keys.DELETE;

            case Win32Constants.VK_PRIOR:
                return Keys.PAGE_UP;

            case Win32Constants.VK_NEXT:
                return Keys.PAGE_DOWN;

            case Win32Constants.VK_BACK:
                 return Keys.BACKSPACE;

            case Win32Constants.VK_TAB:
                return Keys.TAB;

            case Win32Constants.VK_RETURN:
                return Keys.ENTER;

            case Win32Constants.VK_ESCAPE:
                return Keys.ESCAPE;
        }

        if (event.character() == 32) {
            return Keys.SPACE;
        }

        if (event.character() > 32) {
            return new Key(KeyType.CHARACTER, event.character(), shift);
        }

        return Keys.UNKNOWN;
    }
}
