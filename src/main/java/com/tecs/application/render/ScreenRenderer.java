package com.tecs.application.render;

import com.tecs.application.editor.Editor;
import com.tecs.application.terminal.Terminal;

public class ScreenRenderer {
    private final Terminal terminal;
    private final Editor editor;
    private final StringBuilder screenBuffer;

    public ScreenRenderer(Terminal terminal, Editor editor) {
        this.terminal = terminal;
        this.editor = editor;
        this.screenBuffer = new StringBuilder();
    }

    public void refreshScreen() {

        screenBuffer.setLength(0);

        screenBuffer.append("\u001B[H");
        screenBuffer.append("\u001B[J");

        drawRows();
        drawStatusBar();
        drawMessageBar();

        terminal.hideCursor();
        terminal.write(screenBuffer.toString());
        terminal.moveCursor(
            editor.getCursor().getRow() + 1, 
            editor.getCursor().getColumn() + 1
        );
        terminal.showCursor();
        terminal.flush();
    }

    private void drawRows() {
        for (int row = 0; row < terminal.getHeight() - 2; row++) {
            if(row < editor.getDocument().lineCount()) {
                screenBuffer.append("\u001B[K");
                String line = editor.getLine(row);
                int width = terminal.getWidth();

                if(line.length() > width) {
                    line = line.substring(0, width);
                }
                screenBuffer.append(line);
            } else {
                screenBuffer.append("~");
            }
            screenBuffer.append("\r\n");
        }
    }

    private void drawStatusBar() {

        screenBuffer.append("\u001B[7m");

        String status = buildStatusText();

        if(status.length() < terminal.getWidth()) {
            status += " ".repeat(terminal.getWidth() - status.length());
        }
        
        screenBuffer.append(status);

        screenBuffer.append("\u001B[m");

        screenBuffer.append("\r\n");
    }

    private void drawMessageBar() {

        screenBuffer.append("HELP: Ctrl-S = save | Ctrl-Q = quit");
    }

    private String buildStatusText() {
        return "Ln " + (editor.getCursor().getRow() + 1) + " Col " + (editor.getCursor().getColumn() + 1);
    }
}
