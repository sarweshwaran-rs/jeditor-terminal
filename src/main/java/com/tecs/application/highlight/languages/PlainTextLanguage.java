package com.tecs.application.highlight.languages;

import java.util.List;

import com.tecs.application.highlight.LanguageDefinition;
import com.tecs.application.highlight.Token;

public class PlainTextLanguage implements LanguageDefinition{
    
    @Override
    public String name() {
        return "Plain Text";
    }

    @Override
    public List<Token> tokenize(String line) {
        return List.of();
    }
}
