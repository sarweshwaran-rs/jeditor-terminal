package com.tecs.application.highlight.languages;

import java.util.Set;
import java.util.regex.Pattern;

public class CLanguage extends RegexLanguage {

    @Override
    public String name() {
        return "C";
    }

    @Override
    protected Pattern commentPattern() {
        return Pattern.compile("//.*");
    }

    private static final Set<String> KEYWORDS = Set.of(
            "auto",
            "break",
            "case",
            "const",
            "continue",
            "default",
            "do",
            "else",
            "enum",
            "extern",
            "for",
            "goto",
            "if",
            "register",
            "return",
            "sizeof",
            "static",
            "struct",
            "switch",
            "typedef",
            "union",
            "volatile",
            "while"
        );

    private static final Set<String> TYPES = Set.of(
            "char",
            "short",
            "int",
            "long",
            "float",
            "double",
            "void",
            "signed",
            "unsigned"
        );

    @Override
    protected Set<String> keywords() {
        return KEYWORDS;
    }

    @Override
    protected Set<String> types() {
        return TYPES;
    }
}
