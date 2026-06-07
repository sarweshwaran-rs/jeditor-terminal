package com.tecs.application;

import com.tecs.application.exceptions.CommandLineException;

public class App {
    public static void main(String[] args) {
        try {
            new EditorLauncher().launch(args);
        } catch (CommandLineException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Systed Exited with status code: " + ExitCode.INVALID_ARGUMENTS.code());
            System.exit(ExitCode.INVALID_ARGUMENTS.code());
        }
    }
}
