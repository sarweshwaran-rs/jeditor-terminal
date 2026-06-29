
package com.tecs.application.editor.layout;

import com.tecs.application.terminal.TerminalSize;

public final class EditorLayoutCalculator {
    public EditorLayout calculate(TerminalSize size, int gutterWidth, boolean searchVisible, boolean horizontalScrollbarVisible) {
        int menuHeight = LayoutConstants.MENU_HEIGHT;
        int searchHeight = searchVisible ? LayoutConstants.SEARCH_HEIGHT : 0;
        int statusHeight = LayoutConstants.STATUS_HEIGHT;
        int messageHeight = LayoutConstants.MESSAGE_HEIGHT;
        int scrollbarWidth = LayoutConstants.SCROLLBAR_WIDTH;
        int horizontalScrollbarHeight = horizontalScrollbarVisible ? 1 : 0;

        int editorTop = menuHeight + searchHeight;
        int editorLeft = 1;

        int editorWidth = size.columns() - gutterWidth - scrollbarWidth;

        int editorHeight = size.rows() - menuHeight - searchHeight - statusHeight - messageHeight - horizontalScrollbarHeight;

        return new EditorLayout(editorTop, editorLeft, editorWidth, editorHeight, gutterWidth, menuHeight, searchHeight,
                statusHeight, messageHeight, scrollbarWidth);
    }
}
