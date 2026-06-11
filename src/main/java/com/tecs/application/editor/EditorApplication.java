package com.tecs.application.editor;

import java.nio.file.Path;

import com.tecs.application.cli.EditorOptions;
import com.tecs.application.document.Document;
import com.tecs.application.render.ScreenRenderer;
import com.tecs.application.terminal.Key;
import com.tecs.application.terminal.KeyReader;
import com.tecs.application.terminal.Terminal;
import com.tecs.application.ui.ViewMenu;
import com.tecs.application.ui.dialog.AboutDialog;
import com.tecs.application.ui.dialog.ConfirmationDialog;
import com.tecs.application.ui.dialog.Dialog;
import com.tecs.application.ui.dialog.DialogManager;
import com.tecs.application.ui.dialog.DialogResult;
import com.tecs.application.ui.dialog.OpenFileDialog;
import com.tecs.application.ui.dialog.SaveAsDialog;
import com.tecs.application.file.FileManager;

public class EditorApplication {

    private final EditorOptions options;
    private final Terminal terminal;
    private final FileManager fileManager;
    private final StatusMessage statusMessage;
    private final ViewMenu viewMenu;
    private final DialogManager dialogManager;

    private boolean running = true;
    private boolean quitAfterSaving;
    private boolean openAfterConfirmation;
    private Editor editor;
    private Document document;
    private ScreenRenderer renderer;
    private KeyReader keyReader;

    public EditorApplication(EditorOptions options, Terminal terminal, FileManager fileManager,
            StatusMessage statusMessage, ViewMenu viewMenu, DialogManager dialogManager) {
        this.options = options;
        this.terminal = terminal;
        this.fileManager = fileManager;
        this.statusMessage = statusMessage;
        this.viewMenu = viewMenu;
        this.dialogManager = dialogManager;
    }

    public void run() {

        document = loadDocument();
        updateWindowTitle();

        editor = new Editor(document);

        renderer = new ScreenRenderer(terminal, statusMessage, viewMenu, dialogManager);
        keyReader = new KeyReader(terminal.getReader());

        try {
            initializeTerminal();

            eventLoop();
        } finally {
            shutdownTerminal();
        }
    }

    private void updateWindowTitle() {
        String fileName = document.getFilePath() == null
                ? "Untitled"
                : document.getFilePath().getFileName()
                        .toString();
        terminal.setTitle(fileName + " - JEditor");
    }

    private Document loadDocument() {
        if (options.getFileName() != null) {
            return fileManager.open(Path.of(options.getFileName()));
        }
        return fileManager.createNew();
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

            if (handleGlobalKey(key)) {
                continue;
            }

            editor.processKey(key);
        }
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

    private boolean handleGlobalKey(Key key) {
        if (key == null) {
            return false;
        }

        switch (key.type()) {

            case CTRL_L:
                viewMenu.toggleLineNumbers();
                return true;

            case CTRL_O: {
                if (document.isModified()) {
                    openAfterConfirmation = true;
                    dialogManager.show(new ConfirmationDialog());
                } else {
                    dialogManager.show(new OpenFileDialog());
                }
                return true;
            }    

            case CTRL_Q: {
                if (document.isModified()) {
                    quitAfterSaving = true;
                    dialogManager.show(new ConfirmationDialog());
                } else {
                    running = false;
                }
                return true;
            }

            case CTRL_S: {
                if (document.getFilePath() == null) {
                    dialogManager.show(new SaveAsDialog());
                } else {
                    saveDocument();
                }
                return true;
            }
            
            case CTRL_F:
                statusMessage.update("Search not implemented yet");
                return true;

            case TAB:
                dialogManager.show(new AboutDialog());
                return true;

            default:
                return false;
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

        try {
            switch (result.action()) {
                case SAVE_AS -> {
                    fileManager.saveAs(document, Path.of(result.value()));
                    updateWindowTitle();

                    statusMessage.update("Saved as " + result.value());

                    if (quitAfterSaving) {
                        quitAfterSaving = false;
                        running = false;
                    }

                    if(openAfterConfirmation) {
                        openAfterConfirmation = false;
                        dialogManager.show(new OpenFileDialog());
                        return;
                    }
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
                    if (quitAfterSaving) {
                        quitAfterSaving = false;
                        running = false;
                    }

                    if(openAfterConfirmation) {
                        openAfterConfirmation = false;
                        dialogManager.show(new OpenFileDialog());
                        return;
                    }
                }

                case NO -> {
                    if(quitAfterSaving) {
                        quitAfterSaving = false;
                        running = false;
                    }

                    if(openAfterConfirmation) {
                        openAfterConfirmation = false;
                        dialogManager.show(new OpenFileDialog());
                        return;
                    }
                }

                case CANCEL -> {
                    quitAfterSaving = false;
                    openAfterConfirmation = false;
                }

                default -> {
                }
            }
        } catch (Exception ex) {
            quitAfterSaving = false;
            openAfterConfirmation = false;
            statusMessage.update(ex.getMessage());
        }
        dialogManager.close();
    }
}
