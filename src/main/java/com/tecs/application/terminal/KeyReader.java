package com.tecs.application.terminal;

import com.tecs.application.input.keyboard.KeyboardParser;
import org.jline.utils.NonBlockingReader;

import com.tecs.application.editor.EditorConstants;

public class KeyReader {

    private final NonBlockingReader reader;
    private final KeyboardParser parser;

    public KeyReader(NonBlockingReader reader) {
        this.reader = reader;
        this.parser = new KeyboardParser();
    }

    public Key readKey() {
        try {
            int first = reader.read(EditorConstants.KEY_READ_TIMEOUT_MS);

            if (first == NonBlockingReader.READ_EXPIRED) {
                return null;
            }

            Key key = parser.parseControl(first);

            if (key != null) {
                return key;
            }

            key = parser.parseCharacter(first);

            if (key != null) {
                return key;
            }

            if (first != 27) {
                return new Key(KeyType.UNKNOWN, '\0', false);
            }

            int second = reader.read(EditorConstants.KEY_READ_TIMEOUT_MS);

            if  (second == NonBlockingReader.READ_EXPIRED) {
                return new Key(KeyType.ESCAPE, '\0', false);
            }

            int third = reader.read(EditorConstants.KEY_READ_TIMEOUT_MS);

            if (third == NonBlockingReader.READ_EXPIRED) {
                third = -1;
            }

            return parser.parseEscape(second, third, reader::read);
        } catch (Exception e) {
            return new Key(KeyType.UNKNOWN, '\0', false);
        }
    }
}
