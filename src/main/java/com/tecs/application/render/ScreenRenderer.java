package com.tecs.application.render;

import java.util.List;

import com.tecs.application.editor.Editor;
import com.tecs.application.editor.StatusMessage;
import com.tecs.application.editor.navigation.ViewPort;
import com.tecs.application.editor.search.SearchFocus;
import com.tecs.application.editor.search.SearchState;
import com.tecs.application.terminal.Terminal;
import com.tecs.application.terminal.TerminalSize;
import com.tecs.application.ui.StatusBar;
import com.tecs.application.ui.ViewMenu;
import com.tecs.application.ui.dialog.Dialog;
import com.tecs.application.ui.dialog.DialogManager;
import com.tecs.application.ui.menu.Menu;
import com.tecs.application.ui.menu.MenuBar;
import com.tecs.application.ui.menu.MenuItem;

public class ScreenRenderer {
    private static final String GUTTER_SEPARATOR = "| ";
    private static final int SEARCH_PANEL_HEIGHT = 4;

    private final Terminal terminal;
    private final StringBuilder screenBuffer;
    private final StatusMessage statusMessage;
    private final ViewMenu viewMenu;
    private final DialogManager dialogManager;
    private final MenuBar menuBar;
    private final SearchState searchState;

    public ScreenRenderer(Terminal terminal, StatusMessage statusMessage,
            ViewMenu viewMenu, DialogManager dialogManager, MenuBar menuBar, SearchState searchState) {
        this.terminal = terminal;
        this.screenBuffer = new StringBuilder();
        this.statusMessage = statusMessage;
        this.viewMenu = viewMenu;
        this.dialogManager = dialogManager;
        this.menuBar = menuBar;
        this.searchState = searchState;
    }

    public void refreshScreen(Editor editor, ViewPort viewport) {
        TerminalSize size = terminal.getSize();
        int cursorRowOffset = searchState.isActive() ? (2 + SEARCH_PANEL_HEIGHT) : 2;
        screenBuffer.setLength(0);
        screenBuffer.append("\u001B[H");
        screenBuffer.append("\u001B[J");

        drawMenuBar(size);

        if (searchState.isActive()) {
            drawSearchPanel(editor);
        }

        drawRows(editor, size, viewport);
        drawStatusBar(editor, size);
        drawMessageBar();

        if (menuBar.isActive()) {
            drawMenuDropdown(size);
        }

        if (dialogManager.hasDialog()) {
            Dialog dialog = dialogManager.getActiveDialog();
            dialog.render(screenBuffer, size);
        }

        terminal.hideCursor();
        terminal.write(screenBuffer.toString());
        if (menuBar.isActive()) {
            terminal.hideCursor();
        } else if (searchState.isActive()) {
            if (searchState.focus() == SearchFocus.QUERY) {
                terminal.moveCursor(
                        2,
                        gutterWidth(editor) + "Search: ".length() + searchState.queryCursor() + 1);
                terminal.showCursor();
            } else {
                terminal.hideCursor();
            }
        } else if (!dialogManager.hasDialog()) {
            terminal.moveCursor(
                    editor.getCursor().getRow()  - viewport.rowOffset() + cursorRowOffset,
                    gutterWidth(editor) + editor.getCursor().getColumn() - viewport.columnOffset() + 1);
            terminal.showCursor();
        }
        terminal.flush();
    }

    private void drawMenuBar(TerminalSize size) {
        screenBuffer.append("\u001B[44m");
        screenBuffer.append("\u001B[37m");

        for (int i = 0; i < menuBar.menuCount(); i++) {
            Menu menu = menuBar.menus().get(i);
            if (menuBar.isActive()
                    && menuBar.selectedMenu() == i) {

                screenBuffer.append("\u001B[7m");
                screenBuffer.append(" ");
                screenBuffer.append(menu.title());
                screenBuffer.append(" ");
                screenBuffer.append("\u001B[0m");

                screenBuffer.append("\u001B[44m");
                screenBuffer.append("\u001B[37m");
            } else {
                screenBuffer.append(" ");
                screenBuffer.append(menu.title());
                screenBuffer.append(" ");
            }
        }

        int remaining = size.columns() - visibleMenuBarWidth();
        if (remaining > 0) {
            screenBuffer.append(
                    " ".repeat(remaining));
        }
        screenBuffer.append("\u001B[0m");
        screenBuffer.append("\r\n");
    }

    private void drawRows(Editor editor, TerminalSize size, ViewPort viewPort) {
        int searchOffset = searchState.isActive()
                ? SEARCH_PANEL_HEIGHT
                : 0;

        int visibleRows = size.rows() - 3 - searchOffset;

        for (int scrennRow = 0; scrennRow < visibleRows; scrennRow++) {
            
            int documentRow = viewPort.rowOffset() + scrennRow;
            drawGutter(editor, documentRow);

            if (documentRow < editor.getLineCount()) {
                String line = editor.getLine(documentRow);

                int columnOffset = viewPort.columnOffset();
                
                line = columnOffset < line.length() 
                    ? line.substring(columnOffset) 
                    : "";
                
                int availableWidth = size.columns() - gutterWidth(editor);
                if (line.length() > availableWidth) {
                    line = line.substring(0, availableWidth);
                }
                screenBuffer.append(line);
            }
            screenBuffer.append("\r\n");
        }
    }

    private void drawSearchPanel(Editor editor) {
        int gutterWidth = gutterWidth(editor);
        String exactMarker = searchState.focus() == SearchFocus.EXACT_MATCH ? "> " : " ";
        String caseMarker = searchState.focus() == SearchFocus.CASE_SENSITIVE ? "> " : " ";

        screenBuffer.append(" ".repeat(gutterWidth - GUTTER_SEPARATOR.length()));
        screenBuffer.append(GUTTER_SEPARATOR);
        screenBuffer.append("Search: ");
        screenBuffer.append(searchState.getQuery());

        if (!searchState.getQuery().isBlank()) {
            if(searchState.totalMatches() > 0) {
                screenBuffer.append(" [");
                screenBuffer.append(searchState.currentMatch());
                screenBuffer.append("/");
                screenBuffer.append(searchState.totalMatches());
                screenBuffer.append("] ");
            } else {
                screenBuffer.append(" No Matches ");    
            }
        }

        screenBuffer.append("\r\n");

        screenBuffer.append(" ".repeat(gutterWidth - GUTTER_SEPARATOR.length()));
        screenBuffer.append(GUTTER_SEPARATOR);
        screenBuffer.append(exactMarker);
        screenBuffer.append(searchState.isExactMatch()
                ? "[x] Exact Match"
                : "[ ] Exact Match");
        screenBuffer.append("  ");
        screenBuffer.append(caseMarker);
        screenBuffer.append(searchState.isCaseSensitive()
                ? "[x] Case Sensitive"
                : "[ ] Case Sensitive");
        screenBuffer.append("\r\n");
        screenBuffer.append(" ".repeat(gutterWidth - GUTTER_SEPARATOR.length()));
        screenBuffer.append(GUTTER_SEPARATOR);
        screenBuffer.append("↑ Previous  ↓ Next  Enter Select  Tab Options  Esc Close");

        screenBuffer.append("\r\n\n");
    }

    private void drawStatusBar(Editor editor, TerminalSize size) {
        screenBuffer.append("\u001B[44m");
        screenBuffer.append("\u001B[37m");

        String status = StatusBar.build(editor, size.columns());
        screenBuffer.append(status);
        screenBuffer.append("\u001B[0m");
        screenBuffer.append("\r\n");
    }

    private void drawMessageBar() {
        String text = statusMessage.currentMessage();
        if (text.isBlank()) {
            text = "HELP: Ctrl - S = save | Ctrl - Q = quit";
        }

        int width = terminal.getSize().columns();

        if(text.length() > width) {
            text = text.substring(0, width);
        }
        screenBuffer.append(String.format("%-" + width + "s", text));
    }

    private void drawMenuDropdown(TerminalSize size) {
        Menu menu = menuBar.currentMenu();
        if (menu.items().isEmpty()) {
            return;
        }

        int left = calculateMenuStartPosition();
        int width = calculateMenuWidth(menu);
        List<MenuItem> items = menu.items();

        moveTo(2, left);

        screenBuffer.append("┌");
        screenBuffer.append("─".repeat(width - 2));
        screenBuffer.append("┐");

        for (int i = 0; i < items.size(); i++) {
            moveTo(3 + i, left);
            screenBuffer.append("│");

            MenuItem item = items.get(i);
            String text;

            if (item.shortcut().isBlank()) {
                text = item.title();
            } else {
                text = item.title() + " " + item.shortcut();
            }

            if (i == menuBar.selectedItem()) {
                screenBuffer.append("\u001B[7m");
                screenBuffer.append(pad(text, width - 2));
                screenBuffer.append("\u001B[0m");
                screenBuffer.append("\u001B[40m");
                screenBuffer.append("\u001B[37m");
            } else {
                screenBuffer.append(pad(text, width - 2));
            }
            screenBuffer.append("│");
        }

        moveTo(items.size() + 3, left);
        screenBuffer.append("└");
        screenBuffer.append("─".repeat(width - 2));
        screenBuffer.append("┘");
    }

    private void drawGutter(Editor editor, int row) {
        boolean shorCursorMarker = !searchState.isActive() && row == editor.getCursor().getRow();

        screenBuffer.append(shorCursorMarker ? ">" : " ");

        if (viewMenu.lineNumbersEnabled()) {
            int width = lineNumberWidth(editor);
            if (row < editor.getLineCount()) {
                screenBuffer.append(
                        String.format("%" + width + "d", row + 1));
            } else {
                screenBuffer.append(" ".repeat(width));
            }
        }
        screenBuffer.append(GUTTER_SEPARATOR);
    }

    public int gutterWidth(Editor editor) {
        if (!viewMenu.lineNumbersEnabled()) {
            return GUTTER_SEPARATOR.length() + 1;
        }
        return 1
                + lineNumberWidth(editor)
                + GUTTER_SEPARATOR.length();
    }

    private int lineNumberWidth(Editor editor) {
        int digits = String.valueOf(Math.max(1, editor.getLineCount())).length();
        return Math.max(2, digits);
    }

    private int calculateMenuStartPosition() {
        int position = 1;
        for (int i = 0; i < menuBar.selectedMenu(); i++) {
            Menu menu = menuBar.menus().get(i);

            position += menu.title().length() + 3;
        }
        return position;
    }

    private int calculateMenuWidth(Menu menu) {
        int width = 0;
        for (MenuItem item : menu.items()) {
            String line = item.title() + "  " + item.shortcut();
            width = Math.max(width, line.length());
        }
        return width + 4;
    }

    private int visibleMenuBarWidth() {
        int width = 0;
        for (int i = 0; i < menuBar.menuCount(); i++) {
            Menu menu = menuBar.menus().get(i);
            width += menu.title().length() + 2;
        }
        return width;
    }

    private void moveTo(int row, int col) {
        screenBuffer.append(String.format("\u001B[%d;%dH", row, col));
    }

    private static String pad(String value, int width) {
        if (value.length() >= width) {
            return value.substring(0, width);
        }
        return String.format("%-" + width + "s", value);
    }
}
