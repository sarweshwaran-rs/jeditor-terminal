package com.tecs.application.terminal;

import org.jline.utils.NonBlockingReader;

public class KeyReader {

    private final NonBlockingReader reader;

    public KeyReader(NonBlockingReader reader) {
        this.reader = reader;
    }

    public Key readKey() {
        try {
            int ch = reader.read(50);

            if(ch == NonBlockingReader.READ_EXPIRED) {
                return null;
            }

            switch (ch) {
                
                case 12:
                    return new Key(KeyType.CTRL_L, '\0');
                
                case 15:
                    return new Key(KeyType.CTRL_O, '\0');
                    
                case 17:
                    return new Key(KeyType.CTRL_Q, '\0');

                case 19:
                    return new Key(KeyType.CTRL_S, '\0');

                case 6:
                    return new Key(KeyType.CTRL_F, '\0');

                case 13:
                    return new Key(KeyType.ENTER, '\0');

                case 9:
                    return new Key(KeyType.TAB, '\0');

                case 8:
                case 127:
                    return new Key(KeyType.BACKSPACE, '\0');

                case 27:
                    return parseEscape();

                default:
                    if (ch >= 32) {
                        return new Key(
                                KeyType.CHARACTER,
                                (char) ch);
                    }

                    return new Key(KeyType.UNKNOWN, '\0');
            }

        } catch (Exception e) {
            return new Key(KeyType.UNKNOWN, '\0');
        }
    }

    private Key parseEscape() {
        try {

            int c1 = reader.read();

            if (c1 == 'O') {

                int c2 = reader.read();

                return switch (c2) {
                    case 'A' -> new Key(KeyType.ARROW_UP, '\0');
                    case 'B' -> new Key(KeyType.ARROW_DOWN, '\0');
                    case 'C' -> new Key(KeyType.ARROW_RIGHT, '\0');
                    case 'D' -> new Key(KeyType.ARROW_LEFT, '\0');
                    default -> new Key(KeyType.UNKNOWN, '\0');
                };
            }

            if (c1 == '[') {

                int c2 = reader.read();

                switch (c2) {

                    case 'H':
                        return new Key(KeyType.HOME, '\0');

                    case 'F':
                        return new Key(KeyType.END, '\0');

                    case '1':
                        reader.read(); // consume '~'
                        return new Key(KeyType.HOME, '\0');
                    case '3':
                        reader.read(); // consume '~'
                        return new Key(KeyType.DELETE, '\0');

                    case '4':
                        reader.read(); // consume '~'
                        return new Key(KeyType.END, '\0');

                    case '5':
                        reader.read(); // consume '~'
                        return new Key(KeyType.PAGE_UP, '\0');

                    case '6':
                        reader.read(); // consume '~'
                        return new Key(KeyType.PAGE_DOWN, '\0');
                }
            }

            return new Key(KeyType.ESCAPE, '\0');

        } catch (Exception e) {
            return new Key(KeyType.UNKNOWN, '\0');
        }
    }
}
