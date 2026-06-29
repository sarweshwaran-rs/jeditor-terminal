package com.tecs.application.input.ansi;

import com.tecs.application.editor.EditorConstants;
import com.tecs.application.input.InputEvent;
import com.tecs.application.input.InputReader;
import com.tecs.application.input.KeyboardInputEvent;
import com.tecs.application.input.MouseInputEvent;
import com.tecs.application.input.keyboard.KeyboardParser;
import com.tecs.application.input.mouse.MouseParser;
import com.tecs.application.mouse.MouseEvent;
import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.KeyType;
import com.tecs.application.terminal.Terminal;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

public class AnsiInputReader implements InputReader {
    private final NonBlockingReader reader;
    private final KeyboardParser keyboardParser;
    private final MouseParser mouseParser;

    public AnsiInputReader(Terminal terminal) {
        reader = terminal.getReader();
        keyboardParser = new KeyboardParser();
        mouseParser = new MouseParser();
    }

    @Override
    public InputEvent read() {
        try {
            int first = reader.read(EditorConstants.KEY_READ_TIMEOUT_MS);

            if (first == NonBlockingReader.READ_EXPIRED) {
                return null;
            }

            if (first != 27) {
                return readKeyboard(first);
            }

            int second = reader.read(EditorConstants.KEY_READ_TIMEOUT_MS);

            if (second == NonBlockingReader.READ_EXPIRED) {
                return new KeyboardInputEvent(new Key(KeyType.ESCAPE, '\0', false));
            }

            int third = reader.read(EditorConstants.KEY_READ_TIMEOUT_MS);

            if (third == NonBlockingReader.READ_EXPIRED) {
                third = -1;
            }

            if (second == '[' && third == '<') {
                return readMouse(first, second, third);
            }

            Key key = keyboardParser.parseEscape(second, third, reader::read);

            return new KeyboardInputEvent(key);
        } catch (Exception ex) {
            return null;
        }
    }

    private MouseInputEvent readMouse(int first, int second, int third) throws IOException {
        StringBuilder builder = new StringBuilder();

        builder.append((char) first);
        builder.append((char) second);
        builder.append((char) third);

        while (true) {
            int next = reader.read(EditorConstants.KEY_READ_TIMEOUT_MS);

            if (next == NonBlockingReader.READ_EXPIRED) {
                return null;
            }

            builder.append((char) next);

            if (next == 'M' || next == 'm') {
                break;
            }
        }


        MouseEvent event = mouseParser.parse(builder.toString());

        if (event == null) {
            return null;
        }

        return new MouseInputEvent(event);
    }

    private KeyboardInputEvent readKeyboard(int firstByte) {
        Key key = keyboardParser.parseControl(firstByte);

        if (key == null) {
            key = keyboardParser.parseCharacter(firstByte);
        }

        if (key == null) {
            key = new Key(KeyType.UNKNOWN, '\0', false);
        }

        return new KeyboardInputEvent(key);
    }
}
