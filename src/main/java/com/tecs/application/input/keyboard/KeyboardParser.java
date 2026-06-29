package com.tecs.application.input.keyboard;

import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.KeyType;

public final class KeyboardParser {

    public Key parseControl(int ch) {
        return switch(ch) {

            case 1 -> new Key(KeyType.CTRL_A, '\0', false);

            case 3 -> new Key(KeyType.CTRL_C, '\0', false);

            case 6 -> new Key(KeyType.CTRL_F, '\0', false);

            case 7 -> new Key(KeyType.CTRL_G, '\0', false);

            case 8, 127 -> new Key(KeyType.BACKSPACE, '\0', false);

            case 9 -> new Key(KeyType.TAB, '\0', false);

            case 12 -> new Key(KeyType.CTRL_L, '\0', false);

            case 13 -> new Key(KeyType.ENTER, '\0', false);

            case 14 -> new Key(KeyType.CTRL_N, '\0', false);

            case 15 -> new Key(KeyType.CTRL_O, '\0', false);

            case 17 -> new Key(KeyType.CTRL_Q, '\0', false);

            case 19 -> new Key(KeyType.CTRL_S, '\0', false);

            case 20 -> new Key(KeyType.CTRL_T, '\0', false);

            case 22 -> new Key(KeyType.CTRL_V, '\0', false);

            case 24  -> new Key(KeyType.CTRL_X, '\0', false);

            case 25 -> new Key(KeyType.CTRL_Y, '\0', false);

            case 26 -> new Key(KeyType.CTRL_Z, '\0', false);

            default -> null;
        };
    }

    public Key parseCharacter(int ch) {
        if(ch == 32) {
            return new Key(KeyType.SPACE, '\0', false);
        }

        if(ch > 32) {
            return new Key(KeyType.CHARACTER, (char) ch, false);
        }
        return null;
    }

    public Key parseEscape(int secondByte, int thirdByte, ByteSupplier supplier) {
        try {
            if(secondByte == 'O') {
                return parseSimpleEscape(thirdByte);
            }

            if(secondByte == '[') {
                return parseCsiEscape(thirdByte, supplier);
            }
            return new Key(KeyType.ESCAPE, '\0', false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Key parseSimpleEscape(int ch) {
        return switch(ch) {
            case 'A' -> new Key(KeyType.ARROW_UP, '\0', false);

            case 'B' -> new Key(KeyType.ARROW_DOWN, '\0', false);

            case 'C' -> new Key(KeyType.ARROW_RIGHT, '\0', false);

            case 'D' -> new Key(KeyType.ARROW_LEFT, '\0', false);

            case 'H' -> new Key(KeyType.HOME, '\0', false);

            case 'F' -> new Key(KeyType.END, '\0', false);

            default -> new Key(KeyType.UNKNOWN, '\0', false);
        };
    }

    private Key parseCsiEscape(int ch, ByteSupplier supplier) throws Exception {
        return switch (ch) {
            case 'A' -> new Key(KeyType.ARROW_UP, '\0', false);

            case 'B' -> new Key(KeyType.ARROW_DOWN, '\0', false);

            case 'C' -> new Key(KeyType.ARROW_RIGHT, '\0', false);

            case 'D' -> new Key(KeyType.ARROW_LEFT, '\0', false);

            case 'H' -> new Key(KeyType.HOME, '\0', false);

            case 'F' -> new Key(KeyType.END, '\0', false);

            case '1' -> {
                supplier.read();
                yield  new Key(KeyType.HOME, '\0', false);
            }

            case '3' -> {
                supplier.read();
                yield  new Key(KeyType.DELETE, '\0', false);
            }

            case '4' -> {
                supplier.read();
                yield  new Key(KeyType.END, '\0', false);
            }

            case '5' -> {
                supplier.read();
                yield  new Key(KeyType.PAGE_UP, '\0', false);
            }

            case '6' -> {
                supplier.read();
                yield new Key(KeyType.PAGE_DOWN, '\0', false);
            }

            default -> new Key(KeyType.UNKNOWN, '\0', false);
        };
    }
}
