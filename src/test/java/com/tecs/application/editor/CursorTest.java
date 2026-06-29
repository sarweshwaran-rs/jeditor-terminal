package com.tecs.application.editor;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CursorTest {

    @Test
    void shoudMoveRight() {
        Cursor cursor = new Cursor();
        cursor.moveRight();
        assertEquals(1, cursor.getColumn());
    }

    @Test
    void shouldMoveLeft() {
        Cursor cursor = new Cursor();
        cursor.moveLeft();
        assertEquals(0, cursor.getColumn());
    }

    @Test
    void shouldMoveDown() {
        Cursor cursor = new Cursor();
        cursor.moveDown();
        assertEquals(1, cursor.getRow());
    }

    @Test
    void shouldMoveUp() {
        Cursor cursor = new Cursor();
        cursor.moveDown();
        cursor.moveUp();
        assertEquals(0, cursor.getRow());
    }

    @Test
    void shouldSetPosition() {
        Cursor cursor = new Cursor();

        cursor.setPosition(5, 10);

        assertEquals(5, cursor.getRow());
        assertEquals(10, cursor.getColumn());
    }
}
