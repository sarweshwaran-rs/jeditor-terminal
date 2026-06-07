package com.tecs.application;

import com.tecs.application.cli.CommandLineParser;
import com.tecs.application.cli.EditorOptions;
import com.tecs.application.editor.EditorApplication;
import com.tecs.application.exceptions.CommandLineException;

public final class EditorLauncher {
    public void launch(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        EditorOptions options = parser.parse(args);
        EditorApplication app = new EditorApplication(options);
        validate(options);

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
