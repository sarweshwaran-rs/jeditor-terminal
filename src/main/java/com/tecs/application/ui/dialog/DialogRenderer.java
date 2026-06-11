package com.tecs.application.ui.dialog;

import com.tecs.application.terminal.TerminalSize;

public final class DialogRenderer {

    private DialogRenderer() { }

    public static void drawWindow(StringBuilder buffer, TerminalSize size, String title, int width, int height, String[] content) {

        int left = (size.columns() - width) / 2;
        int top = (size.rows() - height) / 2;

        //
        // Top Border
        //
        moveTo(buffer, top + 1, left + 1);

        drawTopBorder(buffer, width, title);

        //
        // Content
        //
        for (int row = 0; row < content.length; row++) {

            moveTo(buffer, top + row + 2, left + 1);

            buffer.append("│");
            buffer.append(pad( content[row], width - 2));
            buffer.append("│");
        }

        //
        // Empty rows if content < height
        //
        int remainingRows = Math.max(0, height - content.length - 2);

        for (int row = 0; row < remainingRows; row++) {

            moveTo(buffer, top + content.length + row + 2,left + 1);

            buffer.append("│");
            buffer.append(" ".repeat(width - 2));
            buffer.append("│");
        }

        //
        // Bottom Border
        //
        moveTo(buffer, top + height, left + 1);

        buffer.append("└");
        buffer.append("─".repeat(width - 2));
        buffer.append("┘");
    }

    private static void drawTopBorder(StringBuilder buffer, int width, String title) {

        buffer.append("┌");

        String header = " " + title + " ";

        int remaining = width - header.length() - 2;

        buffer.append(header);
        buffer.append("─".repeat(Math.max(0, remaining)));

        buffer.append("┐");
    }

    private static String pad(String value, int width) {

        if (value.length() >= width) {
            return value.substring(0, width);
        }

        return String.format("%-" + width + "s", value);
    }

    private static void moveTo(StringBuilder buffer, int row, int col) {
        buffer.append(String.format("\u001B[%d;%dH", row, col));
    }
}
