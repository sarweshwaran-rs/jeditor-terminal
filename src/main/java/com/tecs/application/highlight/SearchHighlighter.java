package com.tecs.application.highlight;

import java.util.ArrayList;
import java.util.List;

import com.tecs.application.editor.search.SearchResult;
import com.tecs.application.editor.search.SearchState;

public class SearchHighlighter {

    public List<Token> highlight(int row, SearchState state) {
        List<Token> tokens = new ArrayList<>();

        if(!state.isActive()) {
            return List.of();
        }

        for(int i=0; i < state.matches().size(); i++) {
            
            SearchResult match = state.matches().get(i);
            
            if(match.row() != row) {
                continue;
            }

            TokenType type = i == state.selectedIndex()
                ? TokenType.CURRENT_SEARCH_MATCH
                : TokenType.SEARCH_MATCH;

            tokens.add(
                new Token(
                    type,
                    match.column(),
                    match.column() + match.length()
                )
            );
        }

        return tokens;
    }
}
