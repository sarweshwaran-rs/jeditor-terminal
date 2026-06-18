package com.tecs.application.platform;

public final class OperatingSystem {

    private static final String NAME = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return NAME.contains("win");
    }

    public static boolean isLinux() {
        return NAME.contains("linux");
    }

    public static boolean isMac() {
        return NAME.contains("mac");
    }
}
