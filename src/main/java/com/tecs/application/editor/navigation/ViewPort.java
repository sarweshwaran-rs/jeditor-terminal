package com.tecs.application.editor.navigation;

public final class ViewPort {
    private int rowOffset;
    private int columnOffset;

    public int rowOffset() {
        return rowOffset;
    }

    public int columnOffset() {
        return columnOffset;
    }

    public void setRowOffset(int rowOffset) {
        this.rowOffset = Math.max(0, rowOffset);
    }

    public void setColumnOffset(int columnOffset) {
        this.columnOffset = Math.max(0, columnOffset);
    }
}
