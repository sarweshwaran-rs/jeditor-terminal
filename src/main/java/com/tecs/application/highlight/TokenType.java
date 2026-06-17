package com.tecs.application.highlight;

public enum TokenType {
    KEYWORD,
    STRING,
    COMMENT,
    NUMBER,
    TYPE,
    OPERATOR,
    PREPROCESSOR,
    
    MARKDOWN_HEADER,
    MARKDOWN_BOLD,
    MARKDOWN_ITALIC,
    MARKDOWN_CODE,

    SEARCH_MATCH,
    CURRENT_SEARCH_MATCH,

    NORMAL
}
