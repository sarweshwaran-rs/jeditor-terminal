package com.tecs.application.ui.dialog;

public final class DialogManager {

    private Dialog activeDialog;

    public boolean hasDialog() {
        return activeDialog != null;
    }

    public Dialog getActiveDialog() {
        return activeDialog;
    }

    public void show(Dialog dialog) {
        this.activeDialog = dialog;
    }

    public void close() {
        this.activeDialog = null;
    }
}
