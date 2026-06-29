package com.tecs.application.editor.search;

public record SearchBounds(
        int queryLeft,
        int queryRight,

        int exactLeft,
        int exactRight,

        int caseLeft,
        int caseRight,

        int previousLeft,
        int previousRight,

        int nextLeft,
        int nextRight
) { }
