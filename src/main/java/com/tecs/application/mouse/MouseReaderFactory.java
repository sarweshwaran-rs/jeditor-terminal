package com.tecs.application.mouse;

import com.tecs.application.mouse.ansi.AnsiMouseReader;
import com.tecs.application.mouse.windows.Win32MouseReader;
import com.tecs.application.platform.OperatingSystem;
import com.tecs.application.terminal.Terminal;

public class MouseReaderFactory {

    private MouseReaderFactory() {

    }

    public static MouseReader create(Terminal terminal) {
        if(OperatingSystem.isWindows()) {
            return new Win32MouseReader();
        }

        return new AnsiMouseReader(terminal);
    }
}
