package com.tecs.application.editor.search;

import com.tecs.application.editor.layout.EditorLayout;
import com.tecs.application.mouse.MouseButton;
import com.tecs.application.mouse.MouseEvent;
import com.tecs.application.mouse.MouseEventType;

public final class SearchMouseController {

    public SearchMouseController(SearchState searchState) {
        this.searchState = searchState;
    }

    private final SearchLayout searchLayout = new SearchLayout();
    private final SearchState searchState;

    public boolean handle(MouseEvent event, EditorLayout layout) {
        SearchBounds bounds = searchLayout.calculate(layout);

        if (event.type() != MouseEventType.PRESS) {
            return false;
        }

        if (event.button() != MouseButton.LEFT) {
            return false;
        }

        if (searchLayout.insideQuery(layout, bounds, event.row(), event.column())) {
            searchState.setFocus(SearchFocus.QUERY);
            return true;
        }

        if (searchLayout.insideExact(layout, bounds, event.row(), event.column())) {
            searchState.setFocus(SearchFocus.EXACT_MATCH);
            searchState.toggleExactMatch();
            return true;
        }

        if (searchLayout.insideCase(layout, bounds, event.row(), event.column())) {
            searchState.setFocus(SearchFocus.CASE_SENSITIVE);
            searchState.toggleCaseSensitive();
            return true;
        }
        return false;
    }
}
