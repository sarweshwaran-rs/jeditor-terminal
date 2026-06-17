package com.tecs.application.highlight;

public final class TokenColorMapper {
    private TokenColorMapper() {}

    public static String color(TokenType type) {
        return switch(type) {
            case KEYWORD -> AnsiColor.BLUE;

            case STRING -> AnsiColor.GREEN;

            case NUMBER -> AnsiColor.CYAN;

            case TYPE -> AnsiColor.CYAN;

            case COMMENT -> AnsiColor.GRAY;

            case SEARCH_MATCH -> AnsiColor.SEARCH;

            case CURRENT_SEARCH_MATCH -> AnsiColor.CURRENT_SEARCH;

            case MARKDOWN_HEADER -> AnsiColor.YELLOW;

            case MARKDOWN_BOLD -> AnsiColor.CYAN;

            case MARKDOWN_ITALIC -> AnsiColor.GREEN;

            case MARKDOWN_CODE -> AnsiColor.GRAY;

            case PREPROCESSOR -> AnsiColor.YELLOW;
            
            default -> "";
        };
    }
}
