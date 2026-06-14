package com.tecs.application.editor;

import java.nio.file.Path;

import com.tecs.application.cli.EditorOptions;
import com.tecs.application.document.Document;
import com.tecs.application.editor.search.SearchController;
import com.tecs.application.editor.search.SearchEngine;
import com.tecs.application.editor.search.SearchResult;
import com.tecs.application.editor.search.SearchState;
import com.tecs.application.render.ScreenRenderer;
import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.KeyReader;
import com.tecs.application.terminal.KeyType;
import com.tecs.application.terminal.Terminal;
import com.tecs.application.ui.ViewMenu;
import com.tecs.application.ui.dialog.AboutDialog;
import com.tecs.application.ui.dialog.ConfirmationDialog;
import com.tecs.application.ui.dialog.Dialog;
import com.tecs.application.ui.dialog.DialogManager;
import com.tecs.application.ui.dialog.DialogResult;
import com.tecs.application.ui.dialog.GotoLineDialog;
import com.tecs.application.ui.dialog.MessageDialog;
import com.tecs.application.ui.dialog.OpenFileDialog;
import com.tecs.application.ui.dialog.SaveAsDialog;
import com.tecs.application.ui.menu.MenuBar;
import com.tecs.application.ui.menu.MenuCommand;
import com.tecs.application.file.FileManager;

public class EditorApplication {

    private final EditorOptions options;
    private final Terminal terminal;
    private final FileManager fileManager;
    private final StatusMessage statusMessage;
    private final ViewMenu viewMenu;
    private final DialogManager dialogManager;
    private final MenuBar menuBar;
    private final SearchState searchState;
    private final SearchController searchController;
    private final SearchEngine searchEngine;

    private boolean running = true;
    private boolean pendingQuit;
    private boolean pendingOpen;
    private boolean pendingNew;
    private Editor editor;
    private Document document;
    private ScreenRenderer renderer;
    private KeyReader keyReader;

    public EditorApplication(EditorOptions options, Terminal terminal, FileManager fileManager,
            StatusMessage statusMessage, ViewMenu viewMenu, DialogManager dialogManager, MenuBar menuBar,
            SearchState searchState) {
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
    }

    public void run() {

        document = loadDocument();
        updateWindowTitle();

        editor = new Editor(document);

        renderer = new ScreenRenderer(terminal, statusMessage, viewMenu, dialogManager, menuBar, searchState);
        keyReader = new KeyReader(terminal.getReader());

        try {
            initializeTerminal();

            eventLoop();
        } finally {
            shutdownTerminal();
        }
    }

    private Document loadDocument() {
        return options.getFileName() == null
                ? fileManager.createNew()
                : fileManager.open(Path.of(options.getFileName()));
    }

    private void initializeTerminal() {
        terminal.enterRawMode();
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
    }

    private void updateWindowTitle() {
        String fileName = document.getFilePath() == null
                ? "Untitled"
                : document.getFilePath().getFileName()
                        .toString();
        terminal.setTitle(fileName + " - JEditor");
    }

    private void eventLoop() {
        while (running) {
            renderer.refreshScreen(editor);

            Key key = keyReader.readKey();

            if (key == null) {
                continue;
            }

            if (dialogManager.hasDialog()) {
                Dialog dialog = dialogManager.getActiveDialog();

                dialog.handleKey(key);
                processDialogResult();
                continue;
            }

            if (searchState.isActive()) {
                if (key.type() == KeyType.ENTER) {
                    searchState.nextMatch();
                    moveToSelectedMatch();
                    continue;
                }

                if (key.type() == KeyType.ARROW_DOWN) {
                    searchState.nextMatch();
                    moveToSelectedMatch();
                    continue;
                }

                if(key.type() == KeyType.ARROW_UP) {
                    searchState.previousMatch();
                    moveToSelectedMatch();
                    continue;
                }

                boolean handled = searchController.handleKey(key);
                if (handled) {
                    updateSearchMatches();
                    moveToSelectedMatch();
                }
                continue;
            }

            if (menuBar.isActive()) {
                handleMenuNavigation(key);
                continue;
            }

            if (handleGlobalKey(key)) {
                continue;
            }

            editor.processKey(key);
        }
    }

    private boolean handleGlobalKey(Key key) {
        if (key == null) {
            return false;
        }

        switch (key.type()) {

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
                    updateWindowTitle();

                    statusMessage.update("Saved as " + result.value());

                    executePendingAction();
                }

                case OPEN -> {
                    document = fileManager.open(Path.of(result.value()));

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
        var matches = searchEngine.findAll(
                document, 
                searchState.getQuery(), 
                searchState.options()
            );
        
        searchState.setMatches(matches);
        searchState.setSelectedIndex(0);
    }

    private void moveToSelectedMatch() {
        SearchResult result = searchState.selectedMatch();

        if(result == null) {
            return;
        }
        editor.getCursor().setPosition(result.row(), result.column());
    }

    private void gotoPosition(String value) {
        searchState.deactivate();

        if(value == null || value.isBlank()) {
            return;
        }

        try {
            String[] parts = value.split(":");

            int row = Integer.parseInt(parts[0]);

            int column = parts.length > 1 ? Integer.parseInt(parts[1]) - 1 : 0;
            
            row--;

            if(row < 0 || row >= document.lineCount()) {
                dialogManager.show(new MessageDialog("Line not found"));
                return;
            }

            String line = document.getLine(row);

            column = Math.max(0, Math.min(column, line.length()));

            editor.getCursor().setPosition(row, column);
            statusMessage.update("Moved to ine " + (row+1) + ", column " + (column+1));
        } catch(NumberFormatException ex) {
            dialogManager.show(new MessageDialog("Invalid position"));
        }
    }
}
