package com.tecs.application.ui.dialog;

import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.TerminalSize;

public interface Dialog {
    void handleKey(Key key);
    void render(StringBuilder buffer, TerminalSize size);
    boolean isClosed();
    DialogResult result();
}
