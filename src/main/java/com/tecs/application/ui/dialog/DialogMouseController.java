package com.tecs.application.ui.dialog;

import com.tecs.application.mouse.MouseButton;
import com.tecs.application.mouse.MouseEvent;
import com.tecs.application.terminal.TerminalSize;

public final class DialogMouseController {

    private static final int BUTTON_ROW = 5;

    public boolean handle(MouseEvent event, Dialog dialog, TerminalSize size) {
        if (!(dialog instanceof BaseDialog base)) {
            return false;
        }

        DialogLayout layout = new DialogLayoutCalculator().calculate(size, base);

        return switch (dialog) {
            case AboutDialog about -> handleAbout(event, about, layout);
            case ConfirmationDialog confirmation -> handleConfirmation(event, confirmation, layout);
            case MessageDialog message -> handleMessage(event, message, layout);
            case SaveAsDialog saveAs -> handleSaveAs(event, saveAs, layout);
            case OpenFileDialog open -> handleOpen(event, open, layout);
            case GotoLineDialog goTo -> handleGoTo(event, goTo, layout);
            default -> false;
        };
    }

    private boolean handleAbout(MouseEvent event, AboutDialog about, DialogLayout layout) {
        if (event.button() != MouseButton.LEFT) {
            return false;
        }

        if (!inside(layout, event)) {
            return false;
        }

        about.close(DialogAction.OK, "");

        return true;
    }

    private boolean handleMessage(MouseEvent event, MessageDialog dialog, DialogLayout layout) {
        if (event.button() != MouseButton.LEFT) {
            return false;
        }

        if (!inside(layout, event)) {
            return false;
        }
        dialog.close(DialogAction.OK, "");
        return true;
    }

    private boolean handleConfirmation(MouseEvent event, ConfirmationDialog dialog, DialogLayout layout) {
        if (event.button() != MouseButton.LEFT) {
            return false;
        }

        int button = buttonAt(layout, event);

        switch (button) {
            case 0 -> dialog.close(DialogAction.OK, "");

            case 1 -> dialog.close(DialogAction.NO, "");

            case 2 -> dialog.close(DialogAction.CANCEL, "");

            default -> {
                return false;
            }
        }

        return true;
    }

    private int buttonAt( DialogLayout layout, MouseEvent event) {
        int row = layout.top() + BUTTON_ROW;

        if (event.row() != row) {
            return -1;
        }

        int col = event.column();

        int saveStart = layout.left() + 20;

        int saveEnd = saveStart + "[Save]".length() - 1;

        int noStart = saveEnd + 4;
        int noEnd = noStart + "[Don't Save]".length() - 1;

        int cancelStart = noEnd + 4;
        int cancelEnd = cancelStart + "[Cancel]".length() - 1;

        if (col >= noStart && col <= saveEnd) {
            return 0;
        }

        if (col >= noStart && col <= noEnd) {
            return 1;
        }

        if (col >= cancelStart && col <= cancelEnd) {
            return 2;
        }

        return -1;
    }

    private boolean handleSaveAs(MouseEvent event, SaveAsDialog dialog, DialogLayout layout) {
        return inside(layout, event);
    }

    private boolean handleOpen(MouseEvent event, OpenFileDialog dialog, DialogLayout layout) {
        return inside(layout, event);
    }

    private boolean handleGoTo(MouseEvent event, GotoLineDialog dialog, DialogLayout layout) {
        return inside(layout, event);
    }

    private boolean inside(DialogLayout layout, MouseEvent event) {
        return event.row() >= layout.top()
                && event.row() < layout.top() + layout.heigh()
                && event.column() >= layout.left()
                && event.column() < layout.left() + layout.width();
    }
}
