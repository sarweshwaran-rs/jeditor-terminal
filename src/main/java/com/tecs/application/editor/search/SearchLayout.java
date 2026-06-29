package com.tecs.application.editor.search;

import com.tecs.application.editor.layout.EditorLayout;

public final class SearchLayout {

    private static final String SEARCH_LABEL = "Search: ";
    private static final String EXACT_LABEL = "[x] Exact Match";
    private static final String CASE = "[x] Case Sensitive";
    private static final String PREVIOUS = "↑ Previous";
    private static final String NEXT = "↓ Next";

    public SearchBounds calculate(EditorLayout layout) {
        int left = layout.textLeft();

        int queryLeft = left + SEARCH_LABEL.length();
        int queryRight = layout.textRight();

        int exactRight = left + EXACT_LABEL.length();

        int caseLeft = exactRight + 4;
        int caseRight = caseLeft + CASE.length() + 2;

        int previousRight = left + PREVIOUS.length() - 1;

        int nextLeft = previousRight + 3;
        int nextRight = nextLeft + NEXT.length();

        return new SearchBounds(queryLeft, queryRight, left, exactRight, caseLeft, caseRight, left, previousRight, nextLeft, nextRight);
    }

    public boolean insideQuery(EditorLayout layout, SearchBounds bounds, int row, int column) {
        return row == layout.searchTop()
                && column >= bounds.queryLeft();
    }

    public boolean insideExact(EditorLayout layout, SearchBounds bounds, int row, int column) {
        return row == layout.searchTop() + 1
                && column >= bounds.exactLeft()
                && column <= bounds.exactRight();
    }

    public boolean insideCase(EditorLayout layout, SearchBounds bounds, int row, int column) {
        return row == layout.searchTop() + 1
                && column >= bounds.caseLeft()
                && column <= bounds.caseRight();
    }

    public boolean insidePrevious(EditorLayout layout, SearchBounds bounds, int row, int column) {
        return row == layout.searchTop() + 2
                && column >= bounds.previousLeft()
                && column <= bounds.previousRight();
    }

    public boolean insideNext(EditorLayout layout, SearchBounds bounds, int row, int column) {
        return row == layout.searchTop() + 2
                && column >= bounds.nextLeft()
                && column <= bounds.nextRight();
    }
}
