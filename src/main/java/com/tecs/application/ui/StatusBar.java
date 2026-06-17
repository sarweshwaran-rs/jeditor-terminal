package com.tecs.application.ui;

import com.tecs.application.editor.Editor;

public final class StatusBar {

    public static String build(Editor editor, int width) {

        String left = " Ln "
                + (editor.getCursor().getRow() + 1)
                + ", Col "
                + (editor.getCursor().getColumn() + 1)
                + " | "
                + editor.getDocument().wordCount()
                + " words | "
                + editor.getDocument().characterCount()
                + " characters ";

        String fileName = editor.getDocument().getFilePath() == null
                ? "Untitled.txt"
                : editor.getDocument()
                        .getFilePath()
                        .getFileName()
                        .toString();

        if (editor.getDocument().isModified()) {
            fileName += " *";
        }

        String language = editor.getDocument().getLanguage().name();

        String right = "[" + language + "] [" + fileName + "]";

        if (left.length() + right.length() > width) {

            left = " Ln "
                    + (editor.getCursor().getRow() + 1)
                    + ", Col "
                    + (editor.getCursor().getColumn() + 1);

            if (left.length() + right.length() > width) {
                return left.substring(0, Math.min(left.length(), width));
            }
        }

        int spaces = Math.max(1, width - left.length() - right.length());

        return left + " ".repeat(spaces) + right;
    }
}
