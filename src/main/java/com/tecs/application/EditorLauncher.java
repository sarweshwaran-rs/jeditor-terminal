package com.tecs.application;

import com.tecs.application.cli.CommandLineParser;
import com.tecs.application.cli.EditorOptions;
import com.tecs.application.editor.EditorApplication;
import com.tecs.application.editor.StatusMessage;
import com.tecs.application.exceptions.CommandLineException;
import com.tecs.application.file.FileManager;
import com.tecs.application.file.LocalFileManager;
import com.tecs.application.terminal.JLineTerminal;
import com.tecs.application.terminal.Terminal;
import com.tecs.application.ui.ViewMenu;
import com.tecs.application.ui.dialog.DialogManager;

public final class EditorLauncher {
    public void launch(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        EditorOptions options = parser.parse(args);
        
        validate(options);

        Terminal terminal = new JLineTerminal();
        FileManager fileManager = new LocalFileManager();
        
        StatusMessage statusMessage = new StatusMessage();
        ViewMenu viewMenu = new ViewMenu();
        DialogManager dialogManager = new DialogManager();
        
        EditorApplication app = new EditorApplication(
            options, 
            terminal, 
            fileManager, 
            statusMessage, 
            viewMenu, 
            dialogManager
        );

        if(options.isShowHelp()) {
            HelpPrinter.printHelp();
            return;
        }

        if(options.isShowVersion()) {
            System.out.println(Version.APP_NAME + " " + Version.VERSION);
            return;
        }

        app.run();
        
    }

    private void validate(EditorOptions options) {

        if(options.isShowHelp() && options.isShowVersion()) {
            throw new CommandLineException("Cannot use --help and --version together.");
        }

        if(options.isShowHelp() && options.getFileName() != null) {
            throw new CommandLineException("--help cannot be combined with a file.");
        }

        if(options.isShowVersion() && options.getFileName() != null) {
            throw new CommandLineException("--version cannot be combined with a file.");
        }
    }
}
