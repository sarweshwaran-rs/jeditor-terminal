package com.tecs.application.highlight.languages;

import java.util.Set;
import java.util.regex.Pattern;

public class JavaLanguage extends RegexLanguage {

    @Override
    public String name() {
        return "Java";
    }

    private static final Set<String> KEYWORDS = Set.of(
            "abstract",
            "assert",
            "boolean",
            "break",
            "byte",
            "case",
            "catch",
            "char",
            "class",
            "continue",
            "default",
            "do",
            "double",
            "else",
            "enum",
            "extends",
            "final",
            "finally",
            "float",
            "for",
            "if",
            "implements",
            "import",
            "instanceof",
            "int",
            "interface",
            "long",
            "native",
            "new",
            "package",
            "private",
            "protected",
            "public",
            "return",
            "short",
            "static",
            "strictfp",
            "super",
            "switch",
            "synchronized",
            "this",
            "throw",
            "throws",
            "transient",
            "try",
            "void",
            "volatile",
            "while");

    private static final Set<String> TYPES = Set.of(
            "String",
            "Integer",
            "Long",
            "Double",
            "Float",
            "Boolean",
            "Character",
            "Object",
            "List",
            "Map",
            "Set"
        );

    private static final Pattern COMMENT = Pattern.compile("//.*");

    @Override
    protected Pattern commentPattern() {
        return COMMENT;
    }

    @Override
    protected Set<String> keywords() {
        return KEYWORDS;
    }

    @Override
    protected Set<String> types() {
        return TYPES;
    }
}
