package com.tecs.application.document;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentTest {

    @Test
    void shouldStartsWithSingleEmptyLine() {
        Document document = new Document();

        assertEquals(1, document.lineCount());
        assertEquals("", document.getLine(0));
    }

    @Test
    void shouldInsertLine() {
        Document document = new Document();

        document.insertLine(1, "Hello");
        assertEquals(2, document.lineCount());
        assertEquals("Hello", document.getLine(1));
    }

    @Test
    void shouldRemoveLine() {
        Document document = new Document();

        document.insertLine(1, "ABC");
        document.removeLine(1);
        assertEquals(1, document.lineCount());
    }

    @Test
    void shouldSetLine() {
        Document document = new Document();
        document.setLine(0, "Java");
        assertEquals("Java", document.getLine(0));
    }
}
