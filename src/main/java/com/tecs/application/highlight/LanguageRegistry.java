package com.tecs.application.highlight;

import java.nio.file.Path;

import com.tecs.application.highlight.languages.CLanguage;
import com.tecs.application.highlight.languages.CppLanguage;
import com.tecs.application.highlight.languages.JavaLanguage;
import com.tecs.application.highlight.languages.MarkdownLanguage;
import com.tecs.application.highlight.languages.PlainTextLanguage;
import com.tecs.application.highlight.languages.PythonLanguage;

public class LanguageRegistry {

    private static final LanguageDefinition JAVA = new JavaLanguage();
    private static final LanguageDefinition PYTHON = new PythonLanguage();
    private static final LanguageDefinition C = new CLanguage();
    private static final LanguageDefinition CPP = new CppLanguage();
    private static final LanguageDefinition MARKDOWN = new MarkdownLanguage();
    private static final LanguageDefinition PLAIN_TEXT = new PlainTextLanguage();

    public LanguageDefinition detect(Path file) {

        if (file == null) {
            return PLAIN_TEXT;
        }

        String fileName = file.getFileName().toString().toLowerCase();

        if (fileName.endsWith(".java")) {
            return JAVA;
        }

        if (fileName.endsWith(".py")) {
            return PYTHON;
        }

        if (fileName.endsWith(".cpp")
                || fileName.endsWith(".cc")
                || fileName.endsWith(".cxx")
                || fileName.endsWith(".hpp")
                || fileName.endsWith(".hh")) {
            return CPP;
        }

        if (fileName.endsWith(".c") || fileName.endsWith(".h")) {
            return C;
        }

        if(fileName.endsWith(".md") || fileName.endsWith(".markdown")) {
            return MARKDOWN;
        }

        return PLAIN_TEXT;
    }
}
