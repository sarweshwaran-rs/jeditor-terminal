package com.tecs.application.ui;

public class ViewMenu {
    private boolean lineNumbersEnabled = true;

    public boolean lineNumbersEnabled() {
        return lineNumbersEnabled;
    }

    public void toggleLineNumbers() {
        lineNumbersEnabled = !lineNumbersEnabled;
    }
}
