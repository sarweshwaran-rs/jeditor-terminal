package com.tecs.application.editor.search;

import java.util.List;

public final class SearchState {
    private boolean active;
    private String query = "";
    private boolean exactMatch;
    private boolean caseSensitive;
    private SearchFocus focus = SearchFocus.QUERY;
    private int queryCursor = 0;
    private List<SearchResult> matches = List.of();
    private int selectedIndex;

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
        focus = SearchFocus.QUERY;
        queryCursor = query.length();
    }

    public void deactivate() {
        active = false;
        matches = List.of();
        selectedIndex = 0;
    }

    public String getQuery() {
        return query;
    }

    public void append(char ch) {
        query = query.substring(0, queryCursor)
                + ch
                + query.substring(queryCursor);

        queryCursor++;
    }

    public void deletePrevious() {
        if (queryCursor == 0) {
            return;
        }

        query = query.substring(0, queryCursor - 1)
                + query.substring(queryCursor);

        queryCursor--;
    }

    public void moveCursorLeft() {
        if (queryCursor > 0) {
            queryCursor--;
        }
    }

    public void moveCursorRight() {
        if (queryCursor < query.length()) {
            queryCursor++;
        }
    }

    public int queryCursor() {
        return queryCursor;
    }

    public boolean isExactMatch() {
        return exactMatch;
    }

    public void toggleExactMatch() {
        exactMatch = !exactMatch;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void toggleCaseSensitive() {
        caseSensitive = !caseSensitive;
    }

    public SearchFocus focus() {
        return focus;
    }

    public void nextFocus() {
        switch (focus) {
            case QUERY -> focus = SearchFocus.EXACT_MATCH;
            case EXACT_MATCH -> focus = SearchFocus.CASE_SENSITIVE;
            case CASE_SENSITIVE -> focus = SearchFocus.QUERY;
        }
    }

    public List<SearchResult> matches() {
        return matches;
    }

    public void setMatches(List<SearchResult> matches) {
        this.matches = matches;

        if(matches.isEmpty()) {
            selectedIndex = 0;
            return;
        }

        if(selectedIndex >= matches.size()) {
            selectedIndex = matches.size() - 1;
        }
    }

    public int selectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int totalMatches() {
        return matches.size();
    }

    public int currentMatch() {
        if (matches.isEmpty()) {
            return 0;
        }
        return selectedIndex + 1;
    }

    public SearchOptions options() {
        return new SearchOptions(caseSensitive, exactMatch);
    }

    public void nextMatch() {

        if (matches.isEmpty()) {
            return;
        }

        selectedIndex = (selectedIndex + 1) % matches().size();
    }

    public void previousMatch() {
        if (matches.isEmpty()) {
            return;
        }

        selectedIndex = (selectedIndex - 1 + matches.size()) % matches.size();
    }

    public SearchResult selectedMatch() {
        if(matches.isEmpty()) {
            return null;
        }
        return matches.get(selectedIndex);
    }
}
