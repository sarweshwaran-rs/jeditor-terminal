package com.tecs.application.highlight;

public record Token(
    TokenType type,
    int start,
    int end
) {}
