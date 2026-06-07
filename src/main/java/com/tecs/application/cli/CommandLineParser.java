package com.tecs.application.cli;

import com.tecs.application.exceptions.CommandLineException;

public class CommandLineParser {

    public EditorOptions parse(String[] args) {
        EditorOptions options = new EditorOptions();

        for(int i=0; i<args.length; i++) {
            String arg = args[i];

            switch(arg) {
                case "--help":
                case "-h":
                    options.setShowHelp(true);
                    break;

                case "--version":
                case "-v":
                    options.setShowVersion(true);
                    break;
                case "--open":
                case "-o":
                    if(i + 1 >= args.length) {
                        throw new CommandLineException("Missing file name after " + arg);
                    }
                    setFileName(options, args[++i]);
                    break;
                default:
                    if(arg.startsWith("-")) {
                        throw new CommandLineException("Unknown option: " + arg);
                    }

                    setFileName(options,arg);
            }
        }
        return options;
    }

    private void setFileName(EditorOptions options, String fileName) {
        if(options.getFileName() != null) {
            throw new CommandLineException("Only one file may be opened.");
        }
        options.setFileName(fileName);
    }
}
