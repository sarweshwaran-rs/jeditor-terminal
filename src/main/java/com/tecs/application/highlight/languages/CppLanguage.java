package com.tecs.application.highlight.languages;

import java.util.Set;
import java.util.regex.Pattern;

public class CppLanguage extends RegexLanguage {
    @Override
    public String name() {
        return "CPP";
    }

    @Override
    protected Pattern commentPattern() {
        return Pattern.compile("//.*");
    }

    private static final Set<String> KEYWORDS = Set.of(
            "class",
            "namespace",
            "public",
            "private",
            "protected",
            "virtual",
            "override",
            "template",
            "typename",
            "new",
            "delete",
            "this",
            "friend",
            "operator",
            "constexpr",
            "if",
            "else",
            "switch",
            "case",
            "while",
            "for",
            "return");

    private static final Set<String> TYPES = Set.of(
            "int",
            "char",
            "bool",
            "float",
            "double",
            "void",

            "string",
            "vector",
            "map",
            "set",
            "unordered_map",

            "list",
            "deque",
            "queue",
            "stack",
            "pair",
            "tuple",
            "array",

            "istream",
            "ostream",
            "ifstream",
            "ofstream"
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
