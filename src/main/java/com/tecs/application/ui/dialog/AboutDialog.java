package com.tecs.application.ui.dialog;

import com.tecs.application.Version;
import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.KeyType;
import com.tecs.application.terminal.TerminalSize;

public final class AboutDialog extends BaseDialog {
    public AboutDialog() {
        super("About JEditor", 80, 10);
    }
    
    String[] lines = {
        "",
        "JEditor",
        "",
        "Version " + Version.VERSION,
        "",
        "Written in Java",
        "",
        "[ ENTER ]"
    };

    @Override
    public void handleKey(Key key) {
        if(key.type() == KeyType.ENTER || key.type() == KeyType.ESCAPE) {
            close(DialogAction.OK, "");
        }
    }

    @Override
    public void render(StringBuilder buffer, TerminalSize size) {
        DialogRenderer.drawWindow(buffer, size, title, width, height, lines);
    }
}
