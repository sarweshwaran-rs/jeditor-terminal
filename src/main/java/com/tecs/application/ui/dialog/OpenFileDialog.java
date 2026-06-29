package com.tecs.application.ui.dialog;

import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.TerminalSize;

public final class OpenFileDialog extends BaseDialog {
    public OpenFileDialog() {
        super("Open File", 80, 10);
    }

    private final StringBuilder fileName = new StringBuilder();

    @Override
    public void handleKey(Key key) {
        switch (key.type()) {
            case CHARACTER -> fileName.append(key.character());

            case BACKSPACE -> {
                if (!fileName.isEmpty()) {
                    fileName.deleteCharAt(fileName.length() - 1);
                }
            }

            case ENTER -> {
                if (fileName.isEmpty()) {
                    return;
                }

                close(DialogAction.OPEN, fileName.toString());
            }

            case ESCAPE -> cancel();

            default -> { } 
        }
    }

    @Override
    public void render(StringBuilder buffer, TerminalSize size) {
        String[] lines = {
                "",
                "File Name:",
                fileName.toString(),
                "",
                "[ Enter = Open ]",
                "[ Esc = Cancel ]",
                ""
        };

        DialogRenderer.drawWindow(buffer, size, title, width, height, lines);
    }
}
