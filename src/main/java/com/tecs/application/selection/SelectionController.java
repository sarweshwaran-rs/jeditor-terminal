package com.tecs.application.selection;

import com.tecs.application.editor.Editor;

public final class SelectionController {

    private final Selection selection;

    public SelectionController(Selection selection) {
        this.selection = selection;
    }

    public void begin(Editor editor) {
        selection.clear();
        selection.begin(editor.getCursor().getRow(), editor.getCursor().getColumn());
    }

    public void beginIfNeeded(Editor editor) {
        if (!selection.isActive()) {
            selection.begin(editor.getCursor().getRow(), editor.getCursor().getColumn());
        }
    }

    public void update(Editor editor) {
        selection.update(editor.getCursor().getRow(), editor.getCursor().getColumn());
    }

    public void finish() {
        selection.end();
    }

    public void clear() {
        selection.clear();
    }

    public boolean hasSelection() {
        return selection.isActive();
    }

    public void deleteSelection(Editor editor) {
        if (!selection.isActive()) {
            return;
        }

        SelectionRange start = normalizedStart();
        SelectionRange end = normalizedEnd();

        editor.delete(start, end);

        editor.getCursor().setPosition(start.row(), start.column());
        selection.clear();
    }

    public void selectAll(Editor editor) {
        selection.clear();

        selection.begin(0, 0);

        int lastRow = editor.getLineCount() - 1;

        int lastColumn = editor.getLine(lastRow).length();

        selection.update(lastRow, lastColumn);
        finish();
        editor.getCursor().setPosition(lastRow, lastColumn);
    }

    public String getSelectedText(Editor editor) {
        if (!selection.isActive()) {
            return "";
        }

        SelectionRange start = normalizedStart();
        SelectionRange end = normalizedEnd();

        if (start.equals(end)) {
            return "";
        }

        return extract(editor, start, end);
    }

    private SelectionRange normalizedStart() {
        SelectionRange a = selection.getAnchor();
        SelectionRange b = selection.getCaret();

        return compare(a, b) <= 0 ? a : b;
    }

    private SelectionRange normalizedEnd() {
        SelectionRange a = selection.getAnchor();
        SelectionRange b = selection.getCaret();

        return compare(a, b) <= 0 ? b : a;
    }

    private int compare(SelectionRange a, SelectionRange b) {
        if (a.row() != b.row()) {
            return Integer.compare(a.row(), b.row());
        }
        return Integer.compare(a.column(), b.column());
    }

    private String extract(Editor editor, SelectionRange start, SelectionRange end) {
        if (start.row() ==end.row()) {
            String line = editor.getLine(start.row());
            return line.substring(start.column(), end.column());
        }

        StringBuilder builder = new StringBuilder();

        String first = editor.getLine(start.row());
        builder.append(first.substring(start.column()));
        builder.append('\n');

        for (int row = start.row() + 1; row < end.row(); row++) {
            builder.append(editor.getLine(row));
            builder.append('\n');
        }

        String last = editor.getLine(end.row());

        builder.append(last, 0, end.column());

        return builder.toString();
    }
}
