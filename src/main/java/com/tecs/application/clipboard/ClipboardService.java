package com.tecs.application.selection;

public final class ClipboardService {
    private String text = "";

    public void copy(String value) {
        text = value;
    }

    public String paste() {
        return text;
    }

    public boolean hasText() {
        return !text.isEmpty();
    }
}
