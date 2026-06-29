package com.tecs.application.ui;

import com.tecs.application.document.Document;
import com.tecs.application.editor.Editor;

import java.nio.file.Path;

public final class StatusBar {

    public static String build(Editor editor, int width) {

        Document document = editor.getDocument();

        String language = " [" + document.getLanguage().name() + "]";

        String location = (editor.getCursor().getRow() + 1) + ":" + (editor.getCursor().getColumn() + 1);

        String words = document.wordCount() + " words";

        String characters = document.characterCount() + " chars";

        String file = "[" + fileName(document) + "] ";

        String right = file;

        String left = language + " | " + location + " | " + words + " | " + characters;

        if (fits(left, right, width)) {
            return pad(left, right, width);
        }

        left = language + " | " + location;

        if (fits(left, right,width)) {
            return pad(left, right, width);
        }

        left = language;

        if (fits(left, right, width)) {
            return pad(left, right, width);
        }

        if (file.length() <= width) {
            return file;
        }

        return location.substring(0, Math.min(language.length(), width));
    }

    private static String fileName(Document document) {
        Path path = document.getFilePath();

        String name = path == null
                ? "Untitled.txt"
                : path.getFileName().toString();

        if (document.isModified()) {
            name += " *";
        }
        return name;
    }

    private static boolean fits(String left, String right, int width) {
        return left.length() + right.length() + 1 <= width;
    }

    private static String pad(String left, String right, int width) {
        int spaces = Math.max(1, width - left.length() - right.length());
        return left + " ".repeat(spaces) + right;
    }
}
