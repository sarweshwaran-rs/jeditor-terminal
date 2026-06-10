package com.tecs.application;

public final class HelpPrinter {
    private HelpPrinter() {}

    public static void printHelp() {
        System.out.println(Version.APP_NAME + " " + Version.VERSION);
        System.out.println();
        System.out.println("A terminal-based text editor written in Java.");
        System.out.println("UsAGE");
        System.out.println("  jeditor [file]");
        System.out.println("  jeditor --open <filename>");
        System.out.println("OPTIONS");
        System.out.println("  -h, --help        Show this help message and exit");
        System.out.println("  -v, --version     Show version information and exit");
        System.out.println("  -o, --open FILE   Open a file");
        System.out.println();

        System.out.println("EXAMPLES:");
        System.out.println("  jeditor");
        System.out.println("  jeditor notes.txt");
        System.out.println("  jeditor -o Main.java");

        System.out.println("EXIT CODES");
        System.out.println("  0 Success");
        System.out.println("  1 Invalid arguments");
        System.out.println("  2 File error");
        System.out.println("  3 Internal error");

        System.out.println("KEYBOARD SHORTCUTS");
        System.out.println("  CTRL+S Save");
        System.out.println("  CTRL+O Open File");
        System.out.println("  CTRL+Q Quit");
        System.out.println("  CTRL+L Toggle Line Numbers");
        System.out.println("  Tab About Dialog");
    }
}
