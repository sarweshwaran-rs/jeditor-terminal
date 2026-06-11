package com.tecs.application.ui.dialog;

import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.TerminalSize;

public final class ConfirmationDialog extends BaseDialog {
    public ConfirmationDialog() {
        super("Confirmation Dialog", 80, 10);
    }

    private int selected;

    private static final String[] OPTIONS = {
        "Save",
        "Don't Save",
        "Cancel"
    };

    @Override
    public void handleKey(Key key) {
        switch(key.type()) {
            case ARROW_LEFT -> selected = Math.max(0, selected-1);

            case ARROW_RIGHT -> selected = Math.min(OPTIONS.length -1, selected + 1);

            case ENTER -> closeSelected();

            case ESCAPE -> cancel();

            default -> { }
        } 
    }

    @Override
    public void render(StringBuilder buffer, TerminalSize size) {
        StringBuilder buttons = new StringBuilder();

        for(int i=0;i<OPTIONS.length;i++) {
            if(i==selected) {
                buttons.append("[");
                buttons.append(OPTIONS[i]);
                buttons.append("]");
            } else {
                buttons.append(" ");
                buttons.append(OPTIONS[i]);
                buttons.append(" ");
            }
            buttons.append("  ");
        }

        String[] lines = {
            "",
            "File has unsaved changes.",
            " Do you want to save changes ?",
            buttons.toString(),
            ""
        };
        DialogRenderer.drawWindow(buffer, size, title, width, height, lines);
    }    

    private void closeSelected() {
        switch(selected) {
            case 0 -> {
                close(DialogAction.SAVE, "");
            }

            case 1 -> {
                close(DialogAction.NO, "");
            }

            case 2 -> {
                close(DialogAction.CANCEL, "");
            }
        }
    }
}
