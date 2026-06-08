package com.tecs.application.render;

import com.tecs.application.editor.Editor;
import com.tecs.application.terminal.Terminal;
import com.tecs.application.terminal.TerminalSize;

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
        TerminalSize size = terminal.getSize();
        
        screenBuffer.setLength(0);
        screenBuffer.append("\u001B[H");
        screenBuffer.append("\u001B[J");

        drawRows(size);
        drawStatusBar(size);
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
    
    private void drawRows(TerminalSize size) {
        for (int row = 0; row < size.rows() - 2; row++) {
            if(row < editor.getDocument().lineCount()) {
                screenBuffer.append("\u001B[K");
                String line = editor.getLine(row);
                int width = size.columns();

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

    private void drawStatusBar(TerminalSize size) {

        screenBuffer.append("\u001B[7m");

        String status = buildStatusText();

        if(status.length() < size.columns()) {
            status += " ".repeat(size.columns() - status.length());
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
