package com.tecs.application;

import java.util.List;

import com.tecs.application.cli.CommandLineParser;
import com.tecs.application.cli.EditorOptions;
import com.tecs.application.editor.EditorApplication;
import com.tecs.application.editor.StatusMessage;
import com.tecs.application.editor.search.SearchState;
import com.tecs.application.exceptions.CommandLineException;
import com.tecs.application.file.FileManager;
import com.tecs.application.file.LocalFileManager;
import com.tecs.application.terminal.JLineTerminal;
import com.tecs.application.terminal.Terminal;
import com.tecs.application.ui.ViewMenu;
import com.tecs.application.ui.dialog.DialogManager;
import com.tecs.application.ui.menu.EditMenu;
import com.tecs.application.ui.menu.FileMenu;
import com.tecs.application.ui.menu.HelpMenu;
import com.tecs.application.ui.menu.MenuBar;

public final class EditorLauncher {
    public void launch(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        EditorOptions options = parser.parse(args);

        validate(options);

        if (options.isShowHelp()) {
            HelpPrinter.printHelp();
            return;
        }

        if (options.isShowVersion()) {
            System.out.println(Version.fullVersion());
            return;
        }

        MenuBar menuBar = createMenuBar();
        EditorApplication app = createApplication(options, menuBar);
        app.run();
    }

    private void validate(EditorOptions options) {

        if (options.isShowHelp() && options.isShowVersion()) {
            throw new CommandLineException("Cannot use --help and --version together.");
        }

        if (options.isShowHelp() && options.getFileName() != null) {
            throw new CommandLineException("--help cannot be combined with a file.");
        }

        if (options.isShowVersion() && options.getFileName() != null) {
            throw new CommandLineException("--version cannot be combined with a file.");
        }
    }

    private MenuBar createMenuBar() {
        return new MenuBar(
            List.of(
                new FileMenu(), 
                new EditMenu(), 
                new HelpMenu()));
    }

    private EditorApplication createApplication(EditorOptions options, MenuBar menuBar) {
        Terminal terminal = new JLineTerminal();
        FileManager fileManager = new LocalFileManager();
        StatusMessage statusMessage = new StatusMessage();
        ViewMenu viewMenu = new ViewMenu();
        DialogManager dialogManager = new DialogManager();
        SearchState searchState = new SearchState();
        
        return new EditorApplication(
                options,
                terminal,
                fileManager,
                statusMessage,
                viewMenu,
                dialogManager,
                menuBar,
                searchState);
    }
}
