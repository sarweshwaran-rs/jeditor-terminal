package com.tecs.application.mouse;

public final class MouseProtocol {

    public static final String ENABLE = "\u001B[?1000h" + // click
            "\u001B[?1002h" + // drag
            "\u001B[?1003h" + // all motion
            "\u001B[?1006h"; // sgr

    public static final String DISABLE = "\u001B[?1000l" +
            "\u001B[?1002l" +
            "\u001B[?1003l" +
            "\u001B[?1006l";
}
