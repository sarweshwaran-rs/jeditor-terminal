package com.tecs.application.ui.dialog;

import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.KeyType;
import com.tecs.application.terminal.TerminalSize;

public final class MessageDialog extends BaseDialog {

    private final String message;

    public MessageDialog(String message) {
        super("Message", 40, 7);
        this.message = message;
    }

    @Override
    public void handleKey(Key key) {
        if(key.type() == KeyType.ENTER || key.type() == KeyType.ESCAPE) {
            close(DialogAction.OK, "");
        }
    }

    @Override
    public void render(StringBuilder buffer, TerminalSize size) {
        String[] lines = {
            "",
            message,
            "",
            "[ Enter ]"
        };

        DialogRenderer.drawWindow(buffer, size, title, width, height, lines);
    }
}
