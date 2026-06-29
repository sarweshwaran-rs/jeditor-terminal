package com.tecs.application.ui.dialog;

import com.tecs.application.terminal.TerminalSize;

public final class DialogLayoutCalculator {

    public DialogLayout calculate(TerminalSize size, BaseDialog dialog) {
        int left = (size.columns() - dialog.getWidth()) / 2 + 1;
        int top = (size.rows() - dialog.getHeight()) / 2 + 1;

        return new DialogLayout(left, top, dialog.width, dialog.height);
    }
}
