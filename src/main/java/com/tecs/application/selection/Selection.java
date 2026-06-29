package com.tecs.application.selection;

public final class Selection {

    private SelectionRange anchor;
    private SelectionRange caret;
    private boolean keyboardSelecting;
    private boolean dragging;

    public boolean isActive() {
        return anchor != null && caret != null;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void clear() {
        anchor = null;
        caret = null;
        dragging = false;
    }

    public void begin(int row, int column) {
        anchor = new SelectionRange(row, column);
        caret = anchor;
        dragging = true;
    }

    public void update(int row, int column) {
        if (anchor == null) {
            return;
        }

        if (!dragging) {
            return;
        }
        caret = new SelectionRange(row, column);
    }

    public void end() {
        dragging = false;
    }

    public SelectionRange getAnchor() {
        return anchor;
    }

    public SelectionRange getCaret() {
        return caret;
    }

    public boolean contains(int row, int column) {
        if (!isActive()) {
            return false;
        }

        SelectionRange start = anchor;
        SelectionRange end = caret;

        if (compare(start, end) > 0) {
            SelectionRange temp = start;
            start = end;
            end = temp;
        }

        if (row < start.row()) {
            return false;
        }

        if (row > end.row()) {
            return false;
        }

        if (start.row() == end.row()) {
            return column >= start.column() && column < end.column();
        }

        if (row == start.row()) {
            return column >= start.column();
        }

        if (row == end.row()) {
            return column < end.column();
        }

        return true;
    }

    private int compare(SelectionRange a, SelectionRange b) {
        if (a.row() != b.row()) {
            return Integer.compare(a.row(), b.row());
        }

        return Integer.compare(a.column(), b.column());
    }

    public boolean isKeyboardSelecting() {
        return keyboardSelecting;
    }

    public void beginKeyboard(int row, int column) {
        if (!isActive()) {
            begin(row, column);
        }
        keyboardSelecting = true;
    }

    public void endKeyboard() {
        keyboardSelecting = false;
    }
}
