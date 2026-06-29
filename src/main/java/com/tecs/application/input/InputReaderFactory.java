package com.tecs.application.input;

import com.tecs.application.input.ansi.AnsiInputReader;
import com.tecs.application.input.windows.Win32InputReader;
import com.tecs.application.platform.OperatingSystem;
import com.tecs.application.terminal.Terminal;

public final class InputReaderFactory {

    private InputReaderFactory() {
    }

    public static InputReader create(Terminal terminal) {
        if(OperatingSystem.isWindows()) {
            return new Win32InputReader();
        }

        return new AnsiInputReader(terminal);
    }
}
