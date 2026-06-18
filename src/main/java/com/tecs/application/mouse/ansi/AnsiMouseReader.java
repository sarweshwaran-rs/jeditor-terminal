package com.tecs.application.mouse.ansi;

import org.jline.utils.NonBlockingReader;

import com.tecs.application.mouse.MouseEvent;
import com.tecs.application.mouse.MouseParser;
import com.tecs.application.mouse.MouseReader;
import com.tecs.application.terminal.Terminal;

public class AnsiMouseReader implements MouseReader {

    @SuppressWarnings("unused")
    private final Terminal terminal;
    private final NonBlockingReader reader;
    private final MouseParser parser;

    public AnsiMouseReader(Terminal terminal) {
        this.terminal = terminal;
        this.reader = terminal.getReader();
        terminal.enableMouse();
        this.parser = new MouseParser();
    }

    @Override
    public MouseEvent read() {

        try {

            int ch = reader.read(50);

            if (ch == NonBlockingReader.READ_EXPIRED) {
                return null;
            }

            if (ch != 27) {
                return null;
            }

            int second = reader.read(50);

            if (second != '[') {
                return null;
            }

            int third = reader.read(50);

            if (third != '<') {
                return null;
            }

            StringBuilder sequence = new StringBuilder();

            sequence.append((char) ch);
            sequence.append((char) second);
            sequence.append((char) third);

            while (true) {

                int next = reader.read(50);

                if (next == NonBlockingReader.READ_EXPIRED) {
                    return null;
                }

                sequence.append((char) next);

                if (next == 'M' || next == 'm') {
                    break;
                }
            }

            String raw = sequence.toString();

            return parser.parse(raw);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
