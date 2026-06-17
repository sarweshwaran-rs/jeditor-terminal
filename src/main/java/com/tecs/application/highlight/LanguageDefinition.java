package com.tecs.application.highlight;

import java.util.List;

public  interface LanguageDefinition {
    String name();

    List<Token> tokenize(String line);
}
