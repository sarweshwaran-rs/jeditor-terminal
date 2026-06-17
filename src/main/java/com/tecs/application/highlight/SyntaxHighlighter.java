package com.tecs.application.highlight;

import java.util.List;

public final class SyntaxHighlighter {

    public List<Token> highlight(LanguageDefinition language, String line) {
        if(language == null || line == null) {
            return List.of();
        }
        
        return language.tokenize(line);
    }
}
