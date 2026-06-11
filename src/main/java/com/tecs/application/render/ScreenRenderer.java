package com.tecs.application.render;

import com.tecs.application.editor.Editor;
import com.tecs.application.editor.StatusMessage;
import com.tecs.application.terminal.Terminal;
import com.tecs.application.terminal.TerminalSize;
import com.tecs.application.ui.StatusBar;
import com.tecs.application.ui.ViewMenu;
import com.tecs.application.ui.dialog.Dialog;
import com.tecs.application.ui.dialog.DialogManager;

public class ScreenRenderer {
    private static final String GUTTER_SEPARATOR = " | ";

    private final Terminal terminal;
    private final StringBuilder screenBuffer;
    private final StatusMessage statusMessage;
    private final ViewMenu viewMenu;
    private final DialogManager dialogManager;

    public ScreenRenderer(Terminal terminal, StatusMessage statusMessage,
        ViewMenu viewMenu, DialogManager dialogManager) {
        this.terminal = terminal;
        this.screenBuffer = new StringBuilder();
        this.statusMessage = statusMessage;
        this.viewMenu = viewMenu;
        this.dialogManager = dialogManager;
    }

    public void refreshScreen(Editor editor) {
        TerminalSize size = terminal.getSize();

        screenBuffer.setLength(0);
        screenBuffer.append("\u001B[H");
        screenBuffer.append("\u001B[J");

        drawMenuBar(size);
        drawRows(editor, size);
        drawStatusBar(editor, size);
        drawMessageBar();

        if(dialogManager.hasDialog()) {
            Dialog dialog = dialogManager.getActiveDialog();
            dialog.render(screenBuffer, size);
        }

        terminal.hideCursor();
        terminal.write(screenBuffer.toString());
        if(!dialogManager.hasDialog()) {
            terminal.moveCursor(
                editor.getCursor().getRow() + 2,
                gutterWidth(editor) + editor.getCursor().getColumn() + 1);
                terminal.showCursor();
        }
        terminal.flush();
    }

    private void drawMenuBar(TerminalSize size) {
        screenBuffer.append("\u001B[44m");
        screenBuffer.append("\u001B[37m");

        String bar = " File  Edit  View  Help";

        screenBuffer.append(bar);

        if (bar.length() < size.columns()) {
            screenBuffer.append(
                    " ".repeat(size.columns() - bar.length()));
        }

        screenBuffer.append("\u001B[0m");
        screenBuffer.append("\r\n");
    }

    private void drawRows(Editor editor, TerminalSize size) {
        for (int row = 0; row < size.rows() - 3; row++) {
            drawGutter(editor, row);

            if (row < editor.getLineCount()) {
                String line = editor.getLine(row);

                int availableWidth = size.columns() - gutterWidth(editor);

                if (line.length() > availableWidth) {
                    line = line.substring(0, availableWidth);
                }
                screenBuffer.append(line);
            }
            screenBuffer.append("\r\n");
        }
    }

    private void drawStatusBar(Editor editor, TerminalSize size) {

        screenBuffer.append("\u001B[44m");
        screenBuffer.append("\u001B[37m");

        String status = StatusBar.build(editor, size.columns());

        screenBuffer.append(status);

        screenBuffer.append("\u001B[0m");
        screenBuffer.append("\r\n");
    }

    private void drawMessageBar() {
        String text = statusMessage.currentMessage();
        if(text.isBlank()) {
           text = "HELP: Ctrl - S = save | Ctrl - Q = quit";
        }

        screenBuffer.append(text);
    }

    private int gutterWidth(Editor editor) {
        if (!viewMenu.lineNumbersEnabled()) {
            return GUTTER_SEPARATOR.length() + 1;
        }

        return 1
               + lineNumberWidth(editor)
               + GUTTER_SEPARATOR.length();
    }

    private void drawGutter(Editor editor, int row) {

        screenBuffer.append(
                row == editor.getCursor().getRow()
                        ? ">"
                        : " ");

        if (viewMenu.lineNumbersEnabled()) {
            int width = lineNumberWidth(editor);
            if (row < editor.getLineCount()) {
                screenBuffer.append(
                        String.format("%" + width + "d", row + 1));
            } else {
                screenBuffer.append(" ".repeat(width));
            }
        }
        screenBuffer.append(GUTTER_SEPARATOR);
    }

    private int lineNumberWidth(Editor editor) {
        int digits = String.valueOf(
                Math.max(1, editor.getLineCount())).length();
            
        return Math.max(2, digits);
    }
}
