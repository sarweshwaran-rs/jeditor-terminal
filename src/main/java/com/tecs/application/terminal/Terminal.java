package com.tecs.application.terminal;

import org.jline.utils.NonBlockingReader;

public interface Terminal {
    void enterRawMode();
    void exitRawMode();
    void clearScreen();
    void hideCursor();
    void showCursor();
    void setTitle(String title);
    void moveCursor(int row, int column);
    void write(String text);
    void flush();
    NonBlockingReader getReader();
    void enterAlternateScreen();
    void exitAlternateScreen();
    TerminalSize getSize();
    void close();
}
