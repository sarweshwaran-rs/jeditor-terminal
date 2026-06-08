package com.tecs.application.document;

import java.util.ArrayList;
import java.util.List;

public class Document {

    private final List<String> lines;

    public Document() {
        this.lines = new ArrayList<>();
        this.lines.add("");
    }

    public List<String > getLines() {
        return lines;
    }

    public int lineCount() {
        return lines.size();
    }

    public String getLine(int row) {
        return lines.get(row);
    }

    public void setLine(int row, String value) {
        lines.set(row, value);
    }

    public void insertLine(int row, String value) {
        lines.add(row, value);
    }

    public void removeLine(int row) {
        lines.remove(row);
    }
}
