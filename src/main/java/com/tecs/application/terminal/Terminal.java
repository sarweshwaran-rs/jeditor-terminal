package com.tecs.application.terminal;

import java.io.Reader;

public interface Terminal {
    void enterRawMode();
    void exitRawMode();
    void clearScreen();
    void hideCursor();
    void showCursor();
    void moveCursor(int row, int column);
    void write(String text);
    void flush();
    int getWidth();
    int getHeight();
    Reader getReader();
    void enterAlternateScreen();
    void exitAlternateScreen();
}
