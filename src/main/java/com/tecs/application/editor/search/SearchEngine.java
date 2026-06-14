package com.tecs.application.editor.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.tecs.application.document.Document;

public class SearchEngine {
    
    public SearchResult findNext(Document document, String query, SearchOptions options, int startRow,
            int startColumn) {
        if (query == null || query.isBlank()) {
            return null;
        }
        for (int row = startRow; row < document.lineCount(); row++) {
            String line = document.getLine(row);

            String source = normalize(line, options.caseSensitive());
            String target = normalize(query, options.caseSensitive());

            int searchFrom = (row == startRow) ? startColumn : 0;
            int index = line.indexOf(target, searchFrom);
            while(index >= 0) {
                if(isMatch(line, query, index, options)) {
                    return new SearchResult(row, index, query.length());
                }
                index = source.indexOf(target, index+target.length());
            }
        }
        return null;
    }

    public SearchResult findPrevious(Document document, String query, SearchOptions options, int startRow,
            int startColumn) {
        if (query == null || query.isBlank()) {
            return null;
        }
        for (int row = startRow; row >= 0; row--) {
            String line = document.getLine(row);

            String source = normalize(line, options.caseSensitive());
            String target = normalize(query, options.caseSensitive());

            int searchTo = (row == startRow) ? Math.min(startColumn, line.length()) : line.length();
            int index = source.lastIndexOf(target, searchTo-1);
            while (index >= 0) {
                if(isMatch(line, query, index, options)) {
                    return new SearchResult(row, index, query.length());
                }
                index = source.lastIndexOf(target, index - 1);
            }
        }
        return null;
    }

    public List<SearchResult> findAll(Document document, String query, SearchOptions options) {
        List<SearchResult> matches = new ArrayList<>();

        if (query == null || query.isBlank()) {
            return matches;
        }

        for (int row = 0; row < document.lineCount(); row++) {
            String line = document.getLine(row);

            String source = normalize(line, options.caseSensitive());

            String target = normalize(query, options.caseSensitive());

            int index = 0;

            while (true) {
                index = source.indexOf(target, index);

                if (index < 0) {
                    break;
                }

                if (isMatch(line, query, index, options)) {
                    matches.add(new SearchResult(row, index, query.length()));
                }
                index += target.length();
            }
        }
        return matches;
    }

    private String normalize(String value, boolean caseSensitive) {
        return caseSensitive ? value : value.toLowerCase(Locale.ROOT);
    }

    private boolean isWordCharacter(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    private boolean isMatch(String line, String query, int index, SearchOptions options) {
        if (!options.exactMatch()) {
            return true;
        }

        boolean leftBoundary = index == 0
                || !isWordCharacter(line.charAt(index - 1));

        int end = index + query.length();

        boolean rightBoundary = end >= line.length()
                || !isWordCharacter(line.charAt(end));

        return leftBoundary && rightBoundary;
    }
}
