package com.tecs.application.editor.search;

import com.tecs.application.terminal.Key;

public final class SearchController {

    private final SearchState searchState;

    public SearchController(SearchState searchState) {
        this.searchState = searchState;
    }

    public boolean handleKey(Key key) {
        switch(key.type()) {
            case ARROW_RIGHT -> {
                if(searchState.focus() == SearchFocus.QUERY) {
                    searchState.moveCursorRight();
                }
                return true;
            }
            
            case ARROW_LEFT -> {
                if(searchState.focus() == SearchFocus.QUERY) {
                    searchState.moveCursorLeft();
                }
                return true;
            }

            case ESCAPE ->{ 
                searchState.deactivate();
                return true;
            }
            
            case TAB -> {
                searchState.nextFocus();
                return true;
            }

            case BACKSPACE -> {
                if(searchState.focus() == SearchFocus.QUERY) {
                    searchState.deletePrevious();
                }
                return true;
            }

            case CHARACTER -> {
                if(searchState.focus() == SearchFocus.QUERY) {
                    searchState.append(key.character());
                }
                return true;
            }

            case SPACE -> {
                switch(searchState.focus()) {
                    case EXACT_MATCH -> searchState.toggleExactMatch();

                    case CASE_SENSITIVE -> searchState.toggleCaseSensitive();
                    
                    case QUERY -> searchState.append(' ');
                    default -> { }
                }
                return true;
            }

            default -> {
                return false;
            }
        }
    }
}
