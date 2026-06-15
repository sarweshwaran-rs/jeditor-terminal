package com.tecs.application.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.tecs.application.document.Document;
import com.tecs.application.editor.navigation.ViewPort;
import com.tecs.application.editor.navigation.ViewportController;
import com.tecs.application.editor.search.SearchEngine;
import com.tecs.application.editor.search.SearchOptions;
import com.tecs.application.editor.search.SearchResult;
import com.tecs.application.editor.search.SearchState;

public class SearchEngineTest {

    @Test
    void shouldFindPartialMatches() {
        Document document = new Document();

        document.replaceLine(0, "editor");
        document.insertLine(1, "texteditor");

        SearchEngine engine = new SearchEngine();

        var results = engine.findAll(document, "editor", new SearchOptions(false, false));

        assertEquals(2, results.size());
    }

    @Test
    void shouldFindExactMatchesOnly() {
        Document document = new Document();

        document.replaceLine(0, "editor");
        document.insertLine(1, "texteditor");

        SearchEngine engine = new SearchEngine();

        var results = engine.findAll(
                document,
                "editor",
                new SearchOptions(false, true));

        assertEquals(1, results.size());
    }

    @Test
    void shouldRespectCaseSensitiveSearch() {
        Document document = new Document();

        document.replaceLine(0, "Editor");
        document.insertLine(1, "editor");

        SearchEngine engine = new SearchEngine();

        var results = engine.findAll(
                document,
                "Editor",
                new SearchOptions(true, false));

        assertEquals(1, results.size());
    }

    @Test
    void shouldReturnNoMatchesForEmptyQuery() {
        SearchEngine engine = new SearchEngine();

        var results = engine.findAll(
                new Document(),
                "",
                new SearchOptions(false, false));

        assertTrue(results.isEmpty());
    }

    @Test
    void shouldFindMultipleMatchesInSameLine() {
        Document document = new Document();

        document.replaceLine(0, "java java java");

        SearchEngine engine = new SearchEngine();

        var results = engine.findAll(
                document,
                "java",
                new SearchOptions(false, false));

        assertEquals(3, results.size());
    }

    @Test
    void shouldActivateSearch() {
        SearchState state = new SearchState();

        state.activate();

        assertTrue(state.isActive());
    }

    @Test
    void shouldDeactivateSearch() {
        SearchState state = new SearchState();

        state.activate();
        state.deactivate();

        assertFalse(state.isActive());
    }

    @Test
    void shouldAppendCharacter() {
        SearchState state = new SearchState();

        state.append('a');
        state.append('b');

        assertEquals("ab", state.getQuery());
    }

    @Test
    void shouldDeletePreviousCharacter() {
        SearchState state = new SearchState();

        state.append('a');
        state.append('b');

        state.deletePrevious();

        assertEquals("a", state.getQuery());
    }

    @Test
    void shouldWrapToFirstMatch() {
        SearchState state = new SearchState();

        state.setMatches(List.of(
                new SearchResult(0, 0, 1),
                new SearchResult(1, 0, 1)));

        state.setSelectedIndex(1);

        state.nextMatch();

        assertEquals(0, state.selectedIndex());
    }

    @Test
    void shouldWrapToLastMatch() {
        SearchState state = new SearchState();

        state.setMatches(List.of(
                new SearchResult(0, 0, 1),
                new SearchResult(1, 0, 1)));

        state.previousMatch();

        assertEquals(1, state.selectedIndex());
    }

    @Test
    void shouldClampSelectedIndexWhenMatchesShrink() {
        SearchState state = new SearchState();

        state.setMatches(List.of(
                new SearchResult(0, 0, 1),
                new SearchResult(1, 0, 1),
                new SearchResult(2, 0, 1)));

        state.setSelectedIndex(2);

        state.setMatches(List.of(
                new SearchResult(0, 0, 1)));

        assertEquals(0, state.selectedIndex());
    }

    @Test
    void shouldNotAllowNegativeRowOffset() {
        ViewPort viewport = new ViewPort();

        viewport.setRowOffset(-10);

        assertEquals(0, viewport.rowOffset());
    }

    @Test
    void shouldNotAllowNegativeColumnOffset() {
        ViewPort viewport = new ViewPort();

        viewport.setColumnOffset(-20);

        assertEquals(0, viewport.columnOffset());
    }

    @Test
    void shouldScrollHorizontally() {
        Editor editor = new Editor(new Document());

        for (int i = 0; i < 100; i++) {
            editor.insertCharacter('A');
        }

        ViewPort viewport = new ViewPort();

        ViewportController controller = new ViewportController(viewport);

        controller.update(editor, 20, 20);

        assertTrue(viewport.columnOffset() > 0);
    }

    @Test
    void shouldScrollVertically() {
        Editor editor = new Editor(new Document());

        for (int i = 0; i < 50; i++) {
            editor.insertNewLine();
        }

        ViewPort viewport = new ViewPort();

        ViewportController controller = new ViewportController(viewport);

        controller.update(editor, 80, 10);

        assertTrue(viewport.rowOffset() > 0);
    }
}
