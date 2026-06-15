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
    void shouldReplaceLine() {
        Document document = new Document();
        document.replaceLine(0, "Java");
        assertEquals("Java", document.getLine(0));
    }

    @Test
    void shouldCountCharacters() {
        Document document = new Document();
        document.replaceLine(0, "Hello");

        assertEquals(5, document.characterCount());
    }

    @Test
    void shouldCountCharactersAcrossLines() {
        Document document = new Document();

        document.replaceLine(0, "Hello");
        document.insertLine(1, "Java");

        assertEquals(10, document.characterCount());
    }

    @Test
    void shouldCountWords() {
        Document document = new Document();

        document.replaceLine(0, "Hello Java World");

        assertEquals(3, document.wordCount());
    }

    @Test
    void shouldIgnoreMultipleSpaces() {
        Document document = new Document();

        document.replaceLine(0, "Hello     Java");

        assertEquals(2, document.wordCount());
    }

    @Test
    void shouldIgnoreEmptyLines() {
        Document document = new Document();

        document.replaceLine(0, "");
        document.insertLine(1, "");

        assertEquals(0, document.wordCount());
    }

    @Test
    void shouldMarkModifiedAfterReplace() {
        Document document = new Document();

        document.replaceLine(0, "Java");

        assertTrue(document.isModified());
    }

    @Test
    void shouldMarkModifiedAfterInsert() {
        Document document = new Document();

        document.insertLine(1, "Hello");

        assertTrue(document.isModified());
    }

    @Test
    void shouldMarkModifiedAfterRemove() {
        Document document = new Document();

        document.insertLine(1, "Hello");

        document.removeLine(1);

        assertTrue(document.isModified());
    }
}
