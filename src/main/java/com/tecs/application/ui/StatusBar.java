package com.tecs.application.ui;

import com.tecs.application.editor.Editor;

public final class StatusBar {

    public static String build(Editor editor, int width) {

        String left = "  Ln " 
                        + (editor.getCursor().getRow() + 1)
                        + ", Col "
                        + (editor.getCursor().getColumn() + 1);

        String fileName = editor.getDocument()
            .getFilePath() == null 
            ? "Untitled.txt"
            : editor.getDocument()
            .getFilePath()
            .getFileName()
            .toString();
        

        if(editor.getDocument().isModified()) {
            fileName += " *";
        }

        String right = "  [" + fileName + "]  ";

        int spaces = Math.max(1, width - left.length() - right.length());

        return left + " ".repeat(spaces) + right;
    }
}
