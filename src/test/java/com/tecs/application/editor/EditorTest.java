package com.tecs.application.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.tecs.application.document.Document;
import com.tecs.application.file.FileManager;
import com.tecs.application.file.LocalFileManager;
import com.tecs.application.selection.SelectionRange;

public class EditorTest {

    @Test
    void shouldInsertCharacter() {
        Editor editor = new Editor(new Document());
        editor.insertCharacter('A');
        assertEquals("A", editor.getLine(0));
        assertEquals(1, editor.getCursor().getColumn());
    }

    @Test
    void shouldDeleteCharacter() {
        Editor editor = new Editor(new Document());
        editor.insertCharacter('A');
        editor.insertCharacter('B');
        editor.insertCharacter('C');

        editor.deleteChar();
        assertEquals("AB", editor.getLine(0));
    }

    @Test
    void shouldMoveHome() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('B');

        editor.moveCursorHome();
        assertEquals(0, editor.getCursor().getColumn());
    }

    @Test
    void shouldMoveEnd() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('B');

        editor.moveCursorHome();
        editor.moveCursorEnd();

        assertEquals(2, editor.getCursor().getColumn());
    }

    @Test
    void shouldMergeLinesWhenBackspacingAtColumnZero() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('B');
        editor.insertCharacter('C');

        editor.insertNewLine();

        editor.insertCharacter('D');
        editor.insertCharacter('E');
        editor.insertCharacter('F');

        editor.moveCursorHome();
        editor.deleteChar();

        assertEquals(1, editor.getLineCount());
        assertEquals("ABCDEF", editor.getLine(0));
    }

    @Test
    void shouldMoveToPreviousLineWhenPressingLeftAtColumnZero() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('B');
        editor.insertCharacter('C');

        editor.insertNewLine();

        editor.moveCursorLeft();
        assertEquals(0, editor.getCursor().getRow());
        assertEquals(3, editor.getCursor().getColumn());
    }

    @Test
    void shouldMoveToNextLineWhenPressingRightAtLineEnd() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('B');
        editor.insertCharacter('C');
        editor.insertNewLine();
        editor.moveCursorLeft();
        editor.moveCursorRight();

        assertEquals(1, editor.getCursor().getRow());
        assertEquals(0, editor.getCursor().getColumn());
    }

    @Test
    void shouldInsertCharacterInMiddleOfLine() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('C');

        editor.moveCursorLeft();
        editor.insertCharacter('B');

        assertEquals("ABC", editor.getLine(0));
    }

    @Test
    void shouldDeleteCharacterInMiddleOfLine() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('B');
        editor.insertCharacter('C');

        editor.moveCursorLeft();
        editor.deleteChar();

        assertEquals("AC", editor.getLine(0));
    }

    @Test
    void shouldCreateMultipleLines() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertNewLine();
        editor.insertCharacter('B');
        editor.insertNewLine();
        editor.insertCharacter('C');

        assertEquals(3, editor.getLineCount());

        assertEquals("A", editor.getLine(0));
        assertEquals("B", editor.getLine(1));
        assertEquals("C", editor.getLine(2));
    }

    @Test
    void shouldMaintainDocumentIntegrityAfterRepeatedEdits() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('H');
        editor.insertCharacter('e');
        editor.insertCharacter('l');
        editor.insertCharacter('l');
        editor.insertCharacter('o');

        editor.insertNewLine();

        editor.insertCharacter('J');
        editor.insertCharacter('a');
        editor.insertCharacter('v');
        editor.insertCharacter('a');

        editor.moveCursorUp();
        editor.moveCursorEnd();

        editor.insertCharacter('!');

        editor.moveCursorDown();
        editor.deleteChar();

        assertEquals("Hello!", editor.getLine(0));
        assertEquals("Jav", editor.getLine(1));
    }

    @Test
    void shouldMoveCursorLeft() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('B');
        editor.insertCharacter('C');
        editor.insertCharacter('D');

        editor.moveCursorLeft();
        assertEquals(3, editor.getCursor().getColumn());
    }

    @Test
    void shouldMoveCursorRight() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('B');
        editor.insertCharacter('C');
        editor.insertCharacter('D');

        editor.moveCursorHome();
        editor.moveCursorRight();
        assertEquals(1, editor.getCursor().getColumn());
    }

    @Test
    void shouldMoveCursorUp() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertNewLine();
        editor.insertCharacter('B');

        editor.moveCursorUp();

        assertEquals(0, editor.getCursor().getRow());
    }

    @Test
    void shouldMoveCursorDown() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertNewLine();

        editor.moveCursorUp();
        editor.moveCursorDown();

        assertEquals(1, editor.getCursor().getRow());
    }

    @Test
    void shouldDeleteForwardCharacter() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertCharacter('B');
        editor.insertCharacter('C');

        editor.moveCursorHome();
        editor.deleteForward();

        assertEquals("BC", editor.getLine(0));
    }

    @Test
    void shouldNotDeleteForwardAtEndOfLine() {
        Editor editor = new Editor(new Document());
        editor.insertCharacter('A');
        editor.deleteForward();
        assertEquals("A", editor.getLine(0));
    }

    @Test
    void shouldPageUp() {
        Editor editor = new Editor(new Document());

        for (int i = 0; i < 30; i++) {
            editor.insertNewLine();
        }

        editor.pageDown();
        editor.pageUp();
        assertEquals(10, editor.getCursor().getRow());
    }

    @Test
    void shouldNotPageUpPastBeginning() {
        Editor editor = new Editor(new Document());

        editor.pageUp();
        assertEquals(0, editor.getCursor().getRow());
    }

    @Test
    void shouldPageDown() {
        Editor editor = new Editor(new Document());

        for (int i = 0; i < 30; i++) {
            editor.insertNewLine();
        }

        editor.pageDown();
        assertEquals(30, editor.getCursor().getRow());
    }

    @Test
    void shouldNotPageDownPastLastLine() {

        Editor editor = new Editor(new Document());

        editor.insertNewLine();

        editor.pageDown();

        assertEquals(1, editor.getCursor().getRow());
    }

    @Test
    void shouldMarkDocumentModifiedAfterInsert() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        assertTrue(editor.getDocument().isModified());
    }

    @Test
    void souldClearModifiedFlagAfterSave() {
        Document document = new Document();
        document.markSaved(Path.of("test.txt"));
        assertFalse(document.isModified());
    }

    @Test
    void shouldOpenNonExistingFile() {
        FileManager manager = new LocalFileManager();

        Document document = manager.open(Path.of("does-not-exist.txt"));

        assertEquals("does-not-exist.txt", document.getFilePath().toString());

        assertFalse(document.isModified());
    }

    @Test
    void shouldMergeLinesWhenDeletePressedAtLineEnd() {
        Editor editor = new Editor(new Document());

        editor.insertCharacter('A');
        editor.insertNewLine();
        editor.insertCharacter('B');

        editor.moveCursorUp();
        editor.moveCursorEnd();

        editor.deleteForward();

        assertEquals(1, editor.getLineCount());
        assertEquals("AB", editor.getLine(0));
    }

    @Test
    void shouldInsertText() {
        Editor editor = new Editor(new Document());

        editor.insertText("Hello");

        assertEquals("Hello", editor.getLine(0));
    }

    @Test
    void shouldInsertMultiLineText() {
        Editor editor = new Editor(new Document());

        editor.insertText("Hello\nWorld");
        assertEquals(2, editor.getLineCount());
        assertEquals("Hello", editor.getLine(0));
        assertEquals("World", editor.getLine(1));
    }

    @Test
    void shouldIgnoreEmptyInsertText() {
        Editor editor = new Editor(new Document());

        editor.insertText("");

        assertEquals("", editor.getLine(0));
    }

    @Test
    void shouldDeleteSingleLineSelection() {
        Editor editor = new Editor(new Document());

        editor.insertText("Hello World");

        editor.delete(
                new SelectionRange(0, 5),
                new SelectionRange(0, 11));

        assertEquals("Hello", editor.getLine(0));
    }

    @Test
    void shouldDeleteMultiLineSelection() {
        Editor editor = new Editor(new Document());

        editor.insertText("Hello\nWorld");

        editor.delete(
                new SelectionRange(0, 2),
                new SelectionRange(1, 3));

        assertEquals(1, editor.getLineCount());
        assertEquals("Held", editor.getLine(0));
    }
}
