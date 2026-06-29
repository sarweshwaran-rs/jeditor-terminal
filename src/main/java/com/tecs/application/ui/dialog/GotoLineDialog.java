package com.tecs.application.ui.dialog;

import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.TerminalSize;

public final class GotoLineDialog extends BaseDialog{

    public GotoLineDialog() {
        super("Go To Position", 50, 8);
    }

    private String value = "";
    private int cursor;
    
    @Override
    public void handleKey(Key key) {
        switch(key.type()) {
            case CHARACTER -> {
                value = value.substring(0, cursor) + key.character() + value.substring(cursor);
                cursor++;
            }

            case BACKSPACE -> {
                if(cursor > 0) {
                    value = value.substring(0, cursor - 1) + value.substring(cursor);
                    cursor--;
                }
            }

            case ARROW_LEFT -> {
                if(cursor>0) {
                    cursor--;
                }
            }

            case ARROW_RIGHT -> {
                if(cursor < value.length()) {
                    cursor++;
                }
            }

            case ENTER -> close(DialogAction.GO_TO_POSITION, value);
            
            case ESCAPE -> close(DialogAction.CANCEL, "");

            default -> { }
        }
    }

    public int cursor() {
        return cursor;
    }

    public String value() {
        return value;
    }

    @Override
    public void render(StringBuilder buffer, TerminalSize size) {
        String[] lines = {
            "",
            "Position: " + value,
            "",
            "Enter = Go Esc = Cancel"
        };
        
        DialogRenderer.drawWindow(buffer, size, title, width, height, lines);
    }
}
