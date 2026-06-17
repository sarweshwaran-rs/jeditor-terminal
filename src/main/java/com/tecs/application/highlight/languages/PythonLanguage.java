package com.tecs.application.highlight.languages;

import java.util.Set;
import java.util.regex.Pattern;

public class PythonLanguage extends RegexLanguage {

    @Override
    public String name() {
        return "Python";
    }

    @Override
    protected Pattern commentPattern() {
        return Pattern.compile("#.*");
    }


    @Override
    protected Pattern stringPattern() {
        return Pattern.compile("\"(\\\\.|[^\"\\\\])*\"|'(\\\\.|[^'\\\\])*'");
    }

    private static final Set<String> KEYWORDS = Set.of(
            "False",
            "None",
            "True",
            "and",
            "as",
            "assert",
            "async",
            "await",
            "break",
            "class",
            "continue",
            "def",
            "del",
            "elif",
            "else",
            "except",
            "finally",
            "for",
            "from",
            "global",
            "if",
            "import",
            "in",
            "is",
            "lambda",
            "nonlocal",
            "not",
            "or",
            "pass",
            "raise",
            "return",
            "try",
            "while",
            "with",
            "yield");

    private static final Set<String> TYPES = Set.of(
            "int",
            "float",
            "str",
            "bool",
            "list",
            "tuple",
            "dict",
            "set",
            "object"
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
