package com.tecs.application.terminal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

public class JLineTerminal implements Terminal{
    private final org.jline.terminal.Terminal terminal;
    private final PrintWriter writer;

    public JLineTerminal(){
        try {
            terminal = TerminalBuilder.builder()
                        .system(true)
                        .jni(true)
                        .ffm(false)
                        .build();

            writer = terminal.writer(); 
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enterRawMode() {
        terminal.enterRawMode();
    }

    @Override
    public void exitRawMode() {
        terminal.echo(true);
    }

    @Override
    public void enterAlternateScreen() {
        terminal.puts(InfoCmp.Capability.enter_ca_mode);
        flush();
    }

    @Override
    public void exitAlternateScreen() {
        terminal.puts(InfoCmp.Capability.exit_ca_mode);
        flush();
    }

    @Override
    public void clearScreen() {
        terminal.puts(InfoCmp.Capability.clear_screen);
        flush();
    }

    @Override
    public void hideCursor() {
        terminal.puts(InfoCmp.Capability.cursor_invisible);
    }

    @Override
    public void showCursor() {
        terminal.puts(InfoCmp.Capability.cursor_normal);
    }

    @Override
    public void moveCursor(int row, int column) {
        write(String.format("\u001B[%d;%dH", row, column));
    }

    @Override
    public void write(String text) {
        writer.print(text);
    }

    @Override
    public void flush() {
        writer.flush();
    }

    @Override
    public int getWidth() {
        return terminal.getColumns();
    }

    @Override
    public int getHeight() {
        return terminal.getRows();
    }

    @Override
    public Reader getReader() {
        return terminal.reader();
    }
}
