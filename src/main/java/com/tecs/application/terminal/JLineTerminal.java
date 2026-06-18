package com.tecs.application.terminal;

import java.io.IOException;
import java.io.PrintWriter;

import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;
import org.jline.utils.NonBlockingReader;

import com.tecs.application.mouse.MouseProtocol;
import com.tecs.application.mouse.MouseReader;
import com.tecs.application.mouse.MouseReaderFactory;

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
    public TerminalSize getSize() {
        return new TerminalSize(
            terminal.getRows(),
            terminal.getColumns()
        );
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
    public void setTitle(String title) {
        write("\033]0;" + title + "\007");
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
    public NonBlockingReader getReader() {
        return terminal.reader();
    }

    @Override
    public void close() {
        try {
            terminal.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enableMouse() {
        write(MouseProtocol.ENABLE);
        flush();
    }

    @Override
    public void disableMouse() {
        write(MouseProtocol.DISABLE);
        flush();
    }

    @Override
    public MouseReader getMouseReader() {
        return MouseReaderFactory.create(this);
    }
}
