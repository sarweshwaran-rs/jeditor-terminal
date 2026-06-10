package com.tecs.application;

import com.tecs.application.exceptions.CommandLineException;
import com.tecs.application.exceptions.FileOperationException;

public class App {
    public static void main(String[] args) {
        try {
            new EditorLauncher().launch(args);
        } catch(FileOperationException ex) {
            System.err.println("File Error: " + ex.getMessage());
            System.exit(ExitCode.FILE_ERROR.code());
        } catch (CommandLineException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("System Exited with status code: " + ExitCode.INVALID_ARGUMENTS.code());
            System.exit(ExitCode.INVALID_ARGUMENTS.code());
        } catch(Exception ex) {
            System.err.println("Internal Error: " + ex.getMessage());
            ex.printStackTrace();

            System.exit(ExitCode.INTERNAL_ERROR.code());
        }
    }
}
