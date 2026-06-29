package com.tecs.application.editor.layout;

import com.tecs.application.editor.navigation.ViewPort;

public final class EditorLayout {

    private final int editorTop;
    private final int editorLeft;

    private final int editorWidth;
    private final int editorHeight;
    private final int gutterWidth;

    private final int menuHeight;
    private final int searchHeight;
    private final int statusHeight;

    private final int messageHeight;
    private final int scrollbarWidth;

    public EditorLayout(int editorTop, int editorLeft, int editorWidth, int editorHeight, int gutterWidth,
            int menuHeight, int searchHeight, int statusHeight, int messageHeight, int scrollbarWidth) {
        this.editorTop = editorTop;
        this.editorLeft = editorLeft;
        this.editorWidth = editorWidth;
        this.editorHeight = editorHeight;
        this.gutterWidth = gutterWidth;
        this.menuHeight = menuHeight;
        this.searchHeight = searchHeight;
        this.statusHeight = statusHeight;
        this.messageHeight = messageHeight;
        this.scrollbarWidth = scrollbarWidth;
    }

    public int getEditorTop() {
        return editorTop;
    }

    public int getEditorLeft() {
        return editorLeft;
    }

    public int getEditorWidth() {
        return editorWidth;
    }

    public int getEditorHeight() {
        return editorHeight;
    }

    public int getGutterWidth() {
        return gutterWidth;
    }

    public int getMenuHeight() {
        return menuHeight;
    }

    public int getSearchHeight() {
        return searchHeight;
    }

    public int getStatusHeight() {
        return statusHeight;
    }

    public int getMessageHeight() {
        return messageHeight;
    }

    public int getScrollbarWidth() {
        return scrollbarWidth;
    }

    public boolean isInsideEditor(int row, int column) {
        return row >= editorTop
                && row < editorTop + editorHeight
                && column >= editorLeft + gutterWidth
                && column < editorLeft + editorWidth;
    }

    public boolean isInsideGutter(int row, int column) {
        return row >= editorTop
                && row < editorTop + editorHeight
                && column >= editorLeft
                && column < editorLeft + gutterWidth;
    }

    public boolean isInsideSearch(int row, int column) {
        return row >= searchTop() && row <= searchBottom();
    }

    public boolean isInsideMenubar(int row) {
        return row >= menuTop() && row < menuTop() + menuHeight;
    }

    public int menuTop() {
        return 1;
    }

    public boolean isInsideStatusBar(int row, int column) {
        int top = editorTop + editorHeight;

        return row >= top
                && row < top + statusHeight;
    }

    public boolean isInsideVerticalScrollbar(int row, int column) {
        return row >= editorTop
                && row < editorTop + editorHeight
                && column >= editorLeft + editorWidth && column < editorLeft + editorWidth + scrollbarWidth;
    }

    public int editorBottom() {
        return editorTop + editorHeight - 1;
    }

    public int editorRight() {
        return editorLeft + editorWidth - 1;
    }

    public int textLeft() {
        return editorLeft + gutterWidth;
    }

    public int scrollbarLeft() {
        return textLeft() + editorWidth;
    }

    public int textRight() {
        return textLeft() + editorWidth - 1;
    }

    public int statusbarTop() {
        return editorBottom() + 1;
    }

    public int messagebarTop() {
        return statusbarTop() + statusHeight;
    }

    public int searchTop() {
        return menuHeight;
    }

    public int searchBottom() {
        return searchTop() + searchHeight - 1;
    }

    public int textWidth() {
        return editorWidth;
    }

    public int textHeight() {
        return editorHeight;
    }

    public DocumentPosition screenToDocument(int screenRow, int screenColumn, ViewPort viewPort) {
        int row = viewPort.rowOffset() + screenRow - editorTop;

        int column = viewPort.columnOffset() + screenColumn - textLeft();

        return new DocumentPosition(row, column);
    }

    public ScreenPosition documentToScreen(int row, int column, ViewPort viewPort) {
        int r = editorTop + row - viewPort.rowOffset();
        int c = editorLeft + gutterWidth + column - viewPort.columnOffset();

        return new ScreenPosition(r, c);
    }
}
