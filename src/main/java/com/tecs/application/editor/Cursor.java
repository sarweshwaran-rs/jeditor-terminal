package com.tecs.application.editor;

public class Cursor {
    private int row;
    private int column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void moveLeft() {
        if(column > 0) {
            column--;
        }
    }

    public void moveRight() {
        column++;
    }

    public void moveUp() {
        if(row > 0) {
            row--;
        }
    }

    public void moveDown() {
        row++;
    }
}
