package com.tecs.application.editor;

import com.tecs.application.document.Document;
import com.tecs.application.terminal.Key;

public class Editor {
    private final Document document;
    private final Cursor cursor;

    public Editor(Document document) {
        this.document = document;
        this.cursor = new Cursor();
    }

    public Document getDocument() {
        return document;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void moveCursorLeft() {
        if (cursor.getColumn() > 0) {
            cursor.moveLeft();
            return;
        }

        if (cursor.getRow() == 0) {
            return;
        }
        int previousLength = document.getLine(cursor.getRow() - 1).length();

        cursor.setPosition(cursor.getRow() - 1, previousLength);
    }

    public void moveCursorRight() {
        String line = document.getLine(cursor.getRow());
        if (cursor.getColumn() < line.length()) {
            cursor.moveRight();
            return;
        }

        if (cursor.getRow() >= document.lineCount() - 1) {
            return;
        }

        cursor.setPosition(cursor.getRow() + 1, 0);
    }

    public void moveCursorUp() {
        if (cursor.getRow() == 0) {
            return;
        }
        cursor.moveUp();
        adjustColumn();
    }

    public void moveCursorDown() {
        if (cursor.getRow() >= document.lineCount() - 1) {
            return;
        }
        cursor.moveDown();
        adjustColumn();
    }

    public void insertTab(int tabSize) {
        int spaces = tabSize - (cursor.getColumn() % tabSize);

        for(int i=0; i < spaces; i++) {
            insertCharacter(' ');
        }
    }

    public void insertCharacter(char c) {
        String line = document.getLine(cursor.getRow());

        int col = cursor.getColumn();

        String updated = line.substring(0, col) + c + line.substring(col);

        document.replaceLine(cursor.getRow(), updated);
        cursor.moveRight();
    }

    public void deleteChar() {
        int row = cursor.getRow();
        int col = cursor.getColumn();

        if (row == 0 && col == 0) {
            return;
        }

        if (col > 0) {
            String line = document.getLine(row);

            String updated = line.substring(0, col - 1) + line.substring(col);

            document.replaceLine(row, updated);
            cursor.moveLeft();
            return;
        }

        String currentLine = document.getLine(row);
        String previousLine = document.getLine(row - 1);

        int previousLength = previousLine.length();

        document.replaceLine(row - 1, previousLine + currentLine);

        document.removeLine(row);
        cursor.setPosition(row - 1, previousLength);
    }

    public void insertNewLine() {
        int row = cursor.getRow();
        int col = cursor.getColumn();

        String current = document.getLine(row);

        String left = current.substring(0, col);
        String right = current.substring(col);

        document.replaceLine(row, left);
        document.insertLine(row + 1, right);
        cursor.setPosition(row + 1, 0);
    }

    public void deleteForward() {
        int row = cursor.getRow();
        int col = cursor.getColumn();

        String line = document.getLine(row);

        if (col == line.length()) {
            if(row >= document.lineCount() - 1) {
                return;
            }

            String nextLine = document.getLine(row + 1);

            document.replaceLine(row, line+nextLine);

            document.removeLine(row+1);
            return;
        }

        String updated = line.substring(0, col) + line.substring(col + 1);
        document.replaceLine(row, updated);
    }

    public void moveCursorHome() {
        cursor.setPosition(cursor.getRow(), 0);
    }

    public void moveCursorEnd() {
        cursor.setPosition(cursor.getRow(), document.getLine(cursor.getRow()).length());
    }

    public void pageUp() {
        cursor.setPosition(Math.max(0, cursor.getRow() - 20), cursor.getColumn());
        adjustColumn();
    }

    public void pageDown() {
        cursor.setPosition(Math.min(document.lineCount() - 1, cursor.getRow() + 20), cursor.getColumn());
        adjustColumn();

    }

    public void processKey(Key key) {
        switch (key.type()) {
            case ARROW_LEFT -> moveCursorLeft();
            case ARROW_RIGHT -> moveCursorRight();
            case ARROW_UP -> moveCursorUp();
            case ARROW_DOWN -> moveCursorDown();
            case CHARACTER -> insertCharacter(key.character());
            case BACKSPACE -> deleteChar();
            case ENTER -> insertNewLine();
            case HOME -> moveCursorHome();
            case END -> moveCursorEnd();
            case PAGE_UP -> pageUp();
            case PAGE_DOWN -> pageDown();
            case DELETE -> deleteForward();
            default -> {
            }
        }
    }

    private void adjustColumn() {
        int maxColumn = document.getLine(cursor.getRow()).length();

        if (cursor.getColumn() > maxColumn) {
            cursor.setPosition(cursor.getRow(), maxColumn);
        }
    }

    public int getLineCount() {
        return document.lineCount();
    }

    public String getLine(int row) {
        return document.getLine(row);
    }
}
