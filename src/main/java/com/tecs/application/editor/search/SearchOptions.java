package com.tecs.application.editor.search;

public record SearchOptions(
    boolean caseSensitive,
    boolean exactMatch
) { }
