package com.tecs.application;

public final class HelpPrinter {
    private HelpPrinter() {}

    public static void printHelp() {
        System.out.println(Version.APP_NAME + " " + Version.VERSION);
        System.out.println();
        System.out.println("A terminal-based text editor written in Java.");
        System.out.println("Usage");
        System.out.println("  jeditor [file]");
        System.out.println("  jeditor --open <filename>");
        System.out.println("Options:");
        System.out.println("  -h, --help        Show help");
        System.out.println("  -v, --version     Show version");
        System.out.println("  -o, --open FILE   Open file");
        System.out.println();

        System.out.println("EXAMPLES:");
        System.out.println("  jeditor");
        System.out.println("  jeditor notes.txt");
        System.out.println("  jeditor -o Main.java");
    }
}
