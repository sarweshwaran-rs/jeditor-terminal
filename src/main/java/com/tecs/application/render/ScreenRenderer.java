package com.tecs.application.render;

import java.util.ArrayList;
import java.util.List;

import com.tecs.application.editor.Editor;
import com.tecs.application.editor.StatusMessage;
import com.tecs.application.editor.layout.EditorLayout;
import com.tecs.application.editor.layout.ScreenPosition;
import com.tecs.application.editor.navigation.ViewPort;
import com.tecs.application.editor.search.SearchFocus;
import com.tecs.application.editor.search.SearchState;
import com.tecs.application.highlight.AnsiColor;
import com.tecs.application.highlight.LanguageDefinition;
import com.tecs.application.highlight.SearchHighlighter;
import com.tecs.application.highlight.SyntaxHighlighter;
import com.tecs.application.highlight.Token;
import com.tecs.application.highlight.TokenColorMapper;
import com.tecs.application.highlight.TokenType;
import com.tecs.application.selection.Selection;
import com.tecs.application.terminal.Terminal;
import com.tecs.application.terminal.TerminalSize;
import com.tecs.application.ui.ScrollBar;
import com.tecs.application.ui.ScrollBarCalculator;
import com.tecs.application.ui.StatusBar;
import com.tecs.application.ui.ViewMenu;
import com.tecs.application.ui.dialog.Dialog;
import com.tecs.application.ui.dialog.DialogManager;
import com.tecs.application.ui.menu.*;

public class ScreenRenderer {
    private static final String GUTTER_SEPARATOR = "| ";
    private static final int MAX_HORIZONTAL_THUMB_PERCENT = 20;

    private final Terminal terminal;
    private final StringBuilder screenBuffer;
    private final StatusMessage statusMessage;
    private final ViewMenu viewMenu;
    private final DialogManager dialogManager;
    private final MenuBar menuBar;
    private final SearchState searchState;
    private final SyntaxHighlighter syntaxHighlighter;
    private final SearchHighlighter searchHighlighter;
    private final ScrollBarCalculator scrollBarCalculator;
    private final MenuLayout menuLayout;
    private final Selection selection;

    public ScreenRenderer(Terminal terminal, StatusMessage statusMessage,
            ViewMenu viewMenu, DialogManager dialogManager, MenuBar menuBar, SearchState searchState, Selection selection) {
        this.terminal = terminal;
        this.screenBuffer = new StringBuilder();
        this.statusMessage = statusMessage;
        this.viewMenu = viewMenu;
        this.dialogManager = dialogManager;
        this.menuBar = menuBar;
        this.searchState = searchState;
        this.syntaxHighlighter = new SyntaxHighlighter();
        this.searchHighlighter = new SearchHighlighter();
        this.scrollBarCalculator = new ScrollBarCalculator();
        this.menuLayout = new MenuLayout();
        this.selection = selection;
    }

    public void refreshScreen(Editor editor, ViewPort viewport, EditorLayout layout) {
        TerminalSize size = terminal.getSize();
        screenBuffer.setLength(0);
        screenBuffer.append("\u001B[H");
        screenBuffer.append("\u001B[J");

        drawMenuBar(size);

        if (searchState.isActive()) {
            drawSearchPanel(editor);
        }

        drawRows(editor, size, viewport, layout);
        drawHorizontalScrollBar(editor, size, viewport, layout);
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
            ScreenPosition position = layout.documentToScreen(editor.getCursor().getRow(), editor.getCursor().getColumn(), viewport);

            terminal.moveCursor(position.row(), position.column());
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

    private void drawRows(Editor editor, TerminalSize size, ViewPort viewPort, EditorLayout layout) {
        int visibleRows = layout.getEditorHeight();
        for (int screenRow = 0; screenRow < visibleRows; screenRow++) {

            int documentRow = viewPort.rowOffset() + screenRow;
            
            drawGutter(editor, documentRow);
            
            if (documentRow < editor.getLineCount()) {
                String line = editor.getLine(documentRow);

                LanguageDefinition language = editor.getDocument().getLanguage();

                int columnOffset = viewPort.columnOffset();

                line = columnOffset < line.length()
                        ? line.substring(columnOffset)
                        : "";

                int availableWidth = layout.getEditorWidth();
                if (line.length() > availableWidth) {
                    line = line.substring(0, availableWidth);
                }

                drawHighlightedLine(documentRow, line, language);
            }
            screenBuffer.append("\r\n");
        }
        drawVerticalScrollBar(editor, visibleRows, viewPort, layout);
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
            if (searchState.totalMatches() > 0) {
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
        moveTo(size.rows() - 1, 1);

        screenBuffer.append("\u001B[44m");
        screenBuffer.append("\u001B[37m");

        String status = StatusBar.build(editor, size.columns());
        screenBuffer.append(status);
        screenBuffer.append("\u001B[0m");
    }

    private void drawMessageBar() {
        moveTo(terminal.getSize().rows(), 1);
        String text = statusMessage.currentMessage();
        if (text.isBlank()) {
            text = "HELP: Ctrl - S = save | Ctrl - Q = quit";
        }

        int width = terminal.getSize().columns();

        if (text.length() > width) {
            text = text.substring(0, width);
        }
        screenBuffer.append(String.format("%-" + width + "s", text));
    }

    private void drawMenuDropdown(TerminalSize size) {
        Menu menu = menuBar.currentMenu();
        if (menu.items().isEmpty()) {
            return;
        }

        MenuBounds bounds = menuLayout.dropdownBounds(menuBar);

        int left = bounds.left();
        int width = bounds.width();

        List<MenuItem> items = menu.items();

        moveTo(bounds.top(), left);

        screenBuffer.append("┌");
        screenBuffer.append("─".repeat(width - 2));
        screenBuffer.append("┐");

        for (int i = 0; i < items.size(); i++) {
            moveTo(bounds.top() + 1 + i, left);
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

        moveTo(bounds.top() + bounds.height() - 1, left);
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

    private void drawHighlightedLine(int documentRow, String line, LanguageDefinition language) {
        List<Token> tokens = new ArrayList<>();

        tokens.addAll(
                syntaxHighlighter.highlight(language, line));

        tokens.addAll(
                searchHighlighter.highlight(documentRow, searchState));

        removeOverlaps(tokens);

        for (int column = 0; column < line.length(); column++) {
            TokenType type = tokenTypeAt(tokens, column);

            if (selection.contains(documentRow, column)) {
                screenBuffer.append("\u001B[47m");
                screenBuffer.append("\u001B[30m");
            } else if (type != null) {
                screenBuffer.append(TokenColorMapper.color(type));
            }

            screenBuffer.append(line.charAt(column));
            screenBuffer.append(AnsiColor.RESET);
        }
    }

    private boolean overlaps(Token a, Token b) {
        return a.start() < b.end() && b.start() < a.end();
    }

    private boolean isSearchToken(Token token) {
        return token.type() == TokenType.SEARCH_MATCH
                || token.type() == TokenType.CURRENT_SEARCH_MATCH;
    }

    private void removeOverlaps(List<Token> tokens) {
        List<Token> remove = new ArrayList<>();

        for (Token search : tokens) {

            if (!isSearchToken(search)) {
                continue;
            }

            for (Token token : tokens) {
                if (token == search) {
                    continue;
                }

                if (overlaps(search, token)) {
                    remove.add(token);
                }
            }
        }
        tokens.removeAll(remove);
    }

    private void drawVerticalScrollBar(Editor editor, int visibleRows, ViewPort viewPort, EditorLayout layout) {
        if(editor.getLineCount() <= visibleRows) {
            return;
        }

        ScrollBar scrollBar = scrollBarCalculator.calculate(editor.getLineCount(), visibleRows, viewPort.rowOffset());

        int editorStartRow = layout.getEditorTop();

        for(int row = 0; row < visibleRows; row++) {
            boolean insideThumb = row >= scrollBar.thumbTop()
                && row < scrollBar.thumbTop() + scrollBar.thumbHeight();

            moveTo(editorStartRow + row, layout.scrollbarLeft());

            screenBuffer.append(insideThumb ? "█" : "|");
        }
    }

    public int longestLine(Editor editor) {
        int longest = 0;

        for(int i=0;i<editor.getLineCount(); i++) {
            longest = Math.max(longest, editor.getLine(i).length());
        }
        return longest;
    }

    private void drawHorizontalScrollBar(Editor editor, TerminalSize size, ViewPort viewPort, EditorLayout layout)  {
        int contentWidth = layout.getEditorWidth();

        int longestLine = longestLine(editor);

        if(longestLine <= contentWidth) {
            return;
        }
        int scrollbarRow = size.rows() - 2;
        int thumbWidth = Math.max(8, (contentWidth * contentWidth) / longestLine);
        int maxThumbWidth = (contentWidth * MAX_HORIZONTAL_THUMB_PERCENT) / 100;
        thumbWidth = Math.min(thumbWidth, maxThumbWidth);
        
        int maxOffset = longestLine - contentWidth;

        int maxThumbLeft = contentWidth - thumbWidth;
        int thumbLeft = maxOffset == 0 
            ? 0 
            : (viewPort.columnOffset() * maxThumbLeft) / maxOffset;

        moveTo(scrollbarRow,  layout.textLeft());

        for(int i=0; i<contentWidth ; i++) {
            boolean insideThumb = i >= thumbLeft
                && i < thumbLeft + thumbWidth;

            screenBuffer.append(insideThumb ? '▄'  : '─');
        }
    }

    private TokenType tokenTypeAt(List<Token> tokens, int column) {
        for (Token token : tokens) {
            if (column >= token.start() && column < token.end()) {
                return token.type();
            }
        }
        return null;
    }
}
