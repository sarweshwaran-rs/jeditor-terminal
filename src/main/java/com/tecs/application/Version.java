package com.tecs.application;

public final class Version {
    private Version() {}

    public static final String APP_NAME = "JEditor";
    public static final String VERSION = "0.4.0";

    public static String fullVersion() {
        return APP_NAME + " " + VERSION;
    }
}
