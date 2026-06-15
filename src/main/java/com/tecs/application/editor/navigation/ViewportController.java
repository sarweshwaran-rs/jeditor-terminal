package com.tecs.application.editor.navigation;

import com.tecs.application.editor.Editor;

public final class ViewportController {

    private final ViewPort viewPort;

    public ViewportController(ViewPort viewPort) {
        this.viewPort = viewPort;
    }

    public void update(Editor editor, int visibleWidth, int visibleHeight) {
        updateHorizontal(editor, visibleWidth);
        updateVertical(editor, visibleHeight);
    }

    private void updateHorizontal(Editor editor, int visibleWidth) {
        int cursorColumn = editor.getCursor().getColumn();
        int offset = viewPort.columnOffset();

        int margin = Math.min(10, visibleWidth / 4);

        if(cursorColumn < offset + margin) {
            viewPort.setColumnOffset(Math.max(0, cursorColumn - margin));
            return;
        }

        if(cursorColumn >= offset + visibleWidth - margin) {
            viewPort.setColumnOffset(cursorColumn - visibleWidth + margin + 1);
        }
    }

    private void updateVertical(Editor editor, int visibleHeight) {
        int cursorRow = editor.getCursor().getRow();
        int offset = viewPort.rowOffset();

        int margin = Math.min(5, visibleHeight / 4);

        if(cursorRow < offset + margin) {
            viewPort.setRowOffset(Math.max(0, cursorRow - margin));
            return;
        }

        if(cursorRow >= offset + visibleHeight - margin) {
            viewPort.setRowOffset(cursorRow - visibleHeight + margin + 1);
        }
    }
}
