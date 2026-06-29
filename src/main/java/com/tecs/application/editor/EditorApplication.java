package com.tecs.application.editor;

import java.nio.file.Path;

import com.tecs.application.cli.EditorOptions;
import com.tecs.application.clipboard.ClipboardService;
import com.tecs.application.document.Document;
import com.tecs.application.editor.layout.EditorLayout;
import com.tecs.application.editor.layout.EditorLayoutCalculator;
import com.tecs.application.editor.navigation.ViewPort;
import com.tecs.application.editor.navigation.ViewportController;
import com.tecs.application.editor.search.*;
import com.tecs.application.input.*;
import com.tecs.application.mouse.MouseEvent;
import com.tecs.application.render.ScreenRenderer;
import com.tecs.application.selection.Selection;
import com.tecs.application.selection.SelectionController;
import com.tecs.application.terminal.Key;

import com.tecs.application.terminal.Terminal;
import com.tecs.application.ui.ViewMenu;
import com.tecs.application.ui.dialog.*;
import com.tecs.application.ui.menu.MenuBar;
import com.tecs.application.ui.menu.MenuCommand;
import com.tecs.application.file.FileManager;
import com.tecs.application.highlight.LanguageRegistry;
import com.tecs.application.mouse.MouseController;

public class EditorApplication {

    private final EditorOptions options;
    private final SelectionController selectionController;
    private final EditorLayoutCalculator layoutCalculator;
    private final Terminal terminal;
    private final FileManager fileManager;
    private final StatusMessage statusMessage;
    private final ViewMenu viewMenu;
    private final DialogManager dialogManager;
    private final MenuBar menuBar;
    private final ViewPort viewport;
    private final ViewportController viewportController;
    private final SearchState searchState;
    private final SearchController searchController;
    private final SearchEngine searchEngine;
    private final LanguageRegistry languageRegistry;
    private final ClipboardService clipboard;
    private MouseController mouseController;
    private DialogMouseController dialogMouseController;
    private final Selection selection;
    private final SearchMouseController searchMouseController;
    private InputReader inputReader;
    private EditorLayout layout;
    private boolean running = true;
    private boolean pendingQuit;
    private boolean pendingOpen;
    private boolean pendingNew;
    private Editor editor;
    private Document document;
    private ScreenRenderer renderer;


    public EditorApplication(EditorOptions options, Terminal terminal, FileManager fileManager,
            StatusMessage statusMessage, ViewMenu viewMenu, DialogManager dialogManager, MenuBar menuBar,
            SearchState searchState) {
        this.layoutCalculator = new EditorLayoutCalculator();
        this.options = options;
        this.terminal = terminal;
        this.fileManager = fileManager;
        this.statusMessage = statusMessage;
        this.viewMenu = viewMenu;
        this.dialogManager = dialogManager;
        this.menuBar = menuBar;
        this.searchState = searchState;
        this.searchController = new SearchController(searchState);
        this.searchEngine = new SearchEngine();
        this.searchMouseController = new SearchMouseController(searchState);
        this.viewport = new ViewPort();
        this.viewportController = new ViewportController(viewport);
        this.languageRegistry = new LanguageRegistry();
        this.selection = new Selection();
        this.selectionController = new SelectionController(selection);
        this.clipboard = new ClipboardService();
    }

    public void run() {

        document = loadDocument();
        document.setLanguage(languageRegistry.detect(document.getFilePath()));
        updateWindowTitle();
        
        editor = new Editor(document);
        renderer = new ScreenRenderer(terminal, statusMessage, viewMenu, dialogManager, menuBar, searchState,
                selection);
        inputReader = InputReaderFactory.create(terminal);
        
        try {
            initializeTerminal();
        
            eventLoop();
        } finally {
            shutdownTerminal();
        }
    }

    private Document loadDocument() {
        return options.getFileName() == null ? fileManager.createNew()
                : fileManager.open(Path.of(options.getFileName()));
    }

    private void initializeTerminal() {
        terminal.enterRawMode();
        terminal.enableMouse();
        mouseController = new MouseController(selectionController);
        dialogMouseController = new DialogMouseController();
        terminal.enterAlternateScreen();
        terminal.hideCursor();
        terminal.clearScreen();
    }

    private void shutdownTerminal() {
        try {
            terminal.showCursor();
        } catch (Exception ignored) {
        }
        try {
            terminal.exitAlternateScreen();
        } catch (Exception ignored) {
        }
        try {
            terminal.exitRawMode();
        } catch (Exception ignored) {
        }
        try {
            terminal.close();
        } catch (Exception ignored) {
        }
        terminal.disableMouse();
    }

    private void updateWindowTitle() {
        String fileName = document.getFilePath() == null ? "Untitled" : document.getFilePath().getFileName().toString();
        terminal.setTitle(fileName + " - JEditor");
    }

    private void eventLoop() {
        while (running) {
            layout = layoutCalculator.calculate(terminal.getSize(), renderer.gutterWidth(editor),
                    searchState.isActive(), false);
            boolean horizonalScrollbar = renderer.longestLine(editor) > layout.getEditorWidth();
            layout = layoutCalculator.calculate(terminal.getSize(), renderer.gutterWidth(editor),
                    searchState.isActive(), horizonalScrollbar);
            InputEvent event = inputReader.read();
            if (event == null) {
                renderer.refreshScreen(editor, viewport, layout);
                continue;
            }
            if (event instanceof MouseInputEvent(MouseEvent mouseEvent)) {
                if (dialogManager.hasDialog()) {
                    Dialog dialog = dialogManager.getActiveDialog();
                    if (dialogMouseController.handle(mouseEvent, dialog, terminal.getSize())) {
                        processDialogResult();
                        renderer.refreshScreen(editor, viewport, layout);
                        continue;
                    }
                } else if (searchState.isActive()) {
                    if (searchMouseController.handle(mouseEvent, layout)) {
                        updateSearchMatches();
                        moveToSelectedMatch();
                        renderer.refreshScreen(editor, viewport, layout);
                        continue;
                    }
                }
                if (mouseController.handle(mouseEvent, editor, layout, viewport, viewportController, menuBar)) {
                    MenuCommand command = mouseController.consumeSelectedCommand();
                    if (command != null) {
                        menuBar.deactivate();
                        executeMenuCommand(command);
                    }
                    renderer.refreshScreen(editor, viewport, layout);
                    continue;
                }
            }
            if (event instanceof KeyboardInputEvent(Key key)) {
                if (dialogManager.hasDialog()) {
                    Dialog dialog = dialogManager.getActiveDialog();
                    dialog.handleKey(key);
                    processDialogResult();
                } else if (searchState.isActive()) {
                    switch (key.type()) {
                        case ENTER, ARROW_DOWN -> {
                            searchState.nextMatch();
                            moveToSelectedMatch();
                        }
                        case ARROW_UP -> {
                            searchState.previousMatch();
                            moveToSelectedMatch();
                        }
                        default -> {
                            boolean handled = searchController.handleKey(key);
                            if (handled) {
                                updateSearchMatches();
                                moveToSelectedMatch();
                            }
                        }
                    }
                } else if (menuBar.isActive()) {
                    handleMenuNavigation(key);
                } else if (!handleGlobalKey(key)) {
                    if (key.shift()) {
                        handleShiftSelection(key);
                    } else {
                        handleEditorKey(key);
                    }
                }
            }
            ensureCursorVisible();
            renderer.refreshScreen(editor, viewport, layout);
        }
    }

    private boolean handleGlobalKey(Key key) {
        if (key == null) {
            return false;
        }
        switch (key.type()) {
            case CTRL_A -> {
                selectionController.selectAll(editor);
                return true;
            }
            
            case CTRL_C -> {
                copySelection();
                return true;
            }
            
            case CTRL_L -> {
                viewMenu.toggleLineNumbers();
                return true;
            }

            case CTRL_N -> {
                newDocument();
                return true;
            }
            
            case CTRL_O -> {
                openDocument();
                document.setLanguage(languageRegistry.detect(document.getFilePath()));
                return true;
            }
            
            case CTRL_Q -> {
                quitApplication();
                return true;
            }
            
            case CTRL_S -> {
                saveCurrentDocument();
                return true;
            }
            
            case CTRL_X -> {
                cutSelection();
                return true;
            }
            
            case CTRL_V -> {
                statusMessage.update("CTRL+V");
                pasteClipboard();
                return true;
            }
            
            case CTRL_F -> {
                searchState.activate();
                return true;
            }
            
            case CTRL_G -> {
                searchState.deactivate();
                dialogManager.show(new GotoLineDialog());
                return true;
            }
            
            case TAB -> {
                editor.insertTab(EditorConstants.TAB_SIZE);
                return true;
            }
            
            case CTRL_T -> {
                menuBar.toggle();
                return true;
            }
            
            default -> {
                return false;
            }
        }
    }

    private void handleMenuNavigation(Key key) {
        switch (key.type()) {
            case ARROW_LEFT -> menuBar.previousMenu();
            
            case ARROW_RIGHT -> menuBar.nextMenu();
            
            case ARROW_UP -> menuBar.previousItem();
            
            case ARROW_DOWN -> menuBar.nextItem();
            
            case ENTER -> {
                MenuCommand command = menuBar.currentItem().command();
                
                menuBar.deactivate();
                executeMenuCommand(command);
            }
            
            case ESCAPE -> menuBar.deactivate();
            
            default -> {
            }
        }
    }

    private void executeMenuCommand(MenuCommand command) {
        switch (command) {
            case NEW -> newDocument();
            
            case OPEN -> openDocument();
            
            case SAVE -> saveCurrentDocument();
            
            case SAVE_AS -> dialogManager.show(new SaveAsDialog());
            
            case QUIT -> quitApplication();
            
            case FIND -> searchState.activate();
            
            case GO_TO_LINE -> {
                searchState.deactivate();
                dialogManager.show(new GotoLineDialog());
            }
            
            case TOGGLE_LINE_NUMBERS -> viewMenu.toggleLineNumbers();
            
            case ABOUT -> dialogManager.show(new AboutDialog());
            
            default -> {
            }
        }
    }

    private void newDocument() {
        if (document.isModified()) {
            pendingNew = true;
            dialogManager.show(new ConfirmationDialog());
        } else {
            createNewDocument();
        }
    }

    private void openDocument() {
        if (document.isModified()) {
            pendingOpen = true;
            dialogManager.show(new ConfirmationDialog());
        } else {
            dialogManager.show(new OpenFileDialog());
            document.setLanguage(languageRegistry.detect(document.getFilePath()));
        }
    }

    private void saveCurrentDocument() {
        if (document.getFilePath() == null) {
            dialogManager.show(new SaveAsDialog());
            return;
        }
        saveDocument();
    }

    private void quitApplication() {
        if (document.isModified()) {
            pendingQuit = true;
            dialogManager.show(new ConfirmationDialog());
        } else {
            running = false;
        }
    }

    private void processDialogResult() {
        Dialog dialog = dialogManager.getActiveDialog();

        if (dialog == null) {
            return;
        }
        
        if (!dialog.isClosed()) {
            return;
        }

        DialogResult result = dialog.result();
        dialogManager.close();
        
        try {
            switch (result.action()) {
                case SAVE_AS -> {
                    fileManager.saveAs(document, Path.of(result.value()));
                    document.setLanguage(languageRegistry.detect(document.getFilePath()));
                    updateWindowTitle();

                    statusMessage.update("Saved as " + result.value());
        
                    executePendingAction();
                }

                case OPEN -> {
                    document = fileManager.open(Path.of(result.value()));
                    document.setLanguage(languageRegistry.detect(document.getFilePath()));
                    editor = new Editor(document);
                    updateWindowTitle();
                    statusMessage.update("Opened: " + result.value());
                }
        
                case SAVE -> {
                    if (document.getFilePath() == null) {
                        dialogManager.close();
                        dialogManager.show(new SaveAsDialog());
                        return;
                    }

                    saveDocument();
                    executePendingAction();
                }
        
                case NO -> executePendingAction();

                case CANCEL -> clearPendingActions();

        
                case GO_TO_POSITION -> gotoPosition(result.value());
        
                default -> {
                }
            }
        } catch (Exception ex) {
            clearPendingActions();
            statusMessage.update(ex.getMessage());
        }
    }

    private void executePendingAction() {
        if (pendingQuit) {
            pendingQuit = false;
            running = false;
            return;
        }
        
        if (pendingOpen) {
            pendingOpen = false;
            dialogManager.show(new OpenFileDialog());
            return;
        }
        
        if (pendingNew) {
            pendingNew = false;
            createNewDocument();
        }
    }

    private void clearPendingActions() {
        pendingQuit = false;
        pendingOpen = false;
        pendingNew = false;
    }

    private void createNewDocument() {
        document = fileManager.createNew();
        document.setLanguage(languageRegistry.detect(document.getFilePath()));
        editor = new Editor(document);
        updateWindowTitle();
        statusMessage.update("Created new file");
    
    }

    private void saveDocument() {
        if (document.getFilePath() == null) {
            statusMessage.update("Cannot save: no file name");
            return;
        }
        try {
            fileManager.save(document);

            statusMessage.update("Saved " + document.getFilePath().getFileName());
        } catch (Exception ex) {
            statusMessage.update("Save failed: " + ex.getMessage());
        }
    }

    private void updateSearchMatches() {
        var matches = searchEngine.findAll(document, searchState.getQuery(), searchState.options());
        searchState.setMatches(matches);
        searchState.setSelectedIndex(0);
    }

    private void moveToSelectedMatch() {
        SearchResult result = searchState.selectedMatch();
        if (result == null) {
            return;
        }
        editor.getCursor().setPosition(result.row(), result.column());
    }

    private void gotoPosition(String value) {
        searchState.deactivate();
        if (value == null || value.isBlank()) {
            return;
        }

        try {
            String[] parts = value.split(":");

            int row = Integer.parseInt(parts[0]);

            int column = parts.length > 1 ? Integer.parseInt(parts[1]) - 1 : 0;

            row--;
            if (row < 0 || row >= document.lineCount()) {
                dialogManager.show(new MessageDialog("Line not found"));
                return;
            }

            String line = document.getLine(row);
            column = Math.clamp(column, 0, line.length());
            editor.getCursor().setPosition(row, column);
            statusMessage.update("Moved to line " + (row + 1) + ", column " + (column + 1));
        } catch (NumberFormatException ex) {
            dialogManager.show(new MessageDialog("Invalid position"));
        }
    }

    private void handleEditorKey(Key key) {
        switch (key.type()) {
            case BACKSPACE, DELETE -> {
                if (selectionController.hasSelection()) {
                    selectionController.deleteSelection(editor);
                } else {
                    editor.processKey(key);
                }
            }
            case CHARACTER, SPACE, TAB, ENTER -> {
                if (selectionController.hasSelection()) {
                    selectionController.deleteSelection(editor);
                }
                editor.processKey(key);
            }
            default -> {
                selectionController.clear();
                editor.processKey(key);
            }
        }
    }

    private void handleShiftSelection(Key key) {
        switch (key.type()) {
            case ARROW_LEFT -> extendLeft();
            case ARROW_RIGHT -> extendRight();
            case ARROW_UP -> extendUp();
            case ARROW_DOWN -> extendDown();
            default -> editor.processKey(key);
        }
    }

    private void extendRight() {
        selectionController.beginIfNeeded(editor);
        editor.moveCursorRight();
        selectionController.update(editor);
    }

    private void extendLeft() {
        selectionController.beginIfNeeded(editor);
        editor.moveCursorLeft();
        selectionController.update(editor);
    }

    private void extendUp() {
        selectionController.beginIfNeeded(editor);
        editor.moveCursorUp();
        selectionController.update(editor);
    }

    private void extendDown() {
        selectionController.beginIfNeeded(editor);
        editor.moveCursorDown();
        selectionController.update(editor);
    }

    private void ensureCursorVisible() {
        viewportController.ensureCursorVisible(editor, layout.getEditorWidth(), layout.getEditorHeight());
    }

    private void copySelection() {
        if (!selectionController.hasSelection()) {
            return;
        }
        String text = selectionController.getSelectedText(editor);
        statusMessage.update("Copied = [" + text + "]");
        clipboard.copy(text);
    }

    private void cutSelection() {
        if (!selectionController.hasSelection()) {
            return;
        }
        String text = selectionController.getSelectedText(editor);
        statusMessage.update("Cut = [" + text + "]" + " From Clipboard: " + clipboard.paste());
        clipboard.copy(text);
        selectionController.deleteSelection(editor);
    }

    private void pasteClipboard() {
        if (!clipboard.hasText()) {
            return;
        }
        if (selectionController.hasSelection()) {
            selectionController.deleteSelection(editor);
        }
        String text = clipboard.paste();
        statusMessage.update("Paste [" + text + "]" + " From Clipboard: " + clipboard.paste());
        editor.insertText(text);
    }
}
