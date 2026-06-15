package com.tecs.application.editor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.tecs.application.document.Document;
import com.tecs.application.ui.StatusBar;

public class StatusBarTest {
    @Test
    void shouldShowModifiedIndicator() {
        Document document = new Document();

        document.markSaved(Path.of("Main.java"));

        Editor editor = new Editor(document);

        editor.insertCharacter('A');

        String status = StatusBar.build(editor, 120);

        assertTrue(status.contains("*"));
    }

    @Test
    void shouldShowFileName() {
        Document document = new Document();

        document.markSaved(Path.of("Main.java"));

        Editor editor = new Editor(document);

        String status = StatusBar.build(editor, 120);

        assertTrue(status.contains("Main.java"));
    }

    @Test
    void shouldHandleSmallTerminalWidth() {
        Document document = new Document();

        Editor editor = new Editor(document);

        String status = StatusBar.build(editor, 20);

        assertTrue(status.length() <= 20);
    }
}
