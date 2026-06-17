package com.tecs.application.highlight.languages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tecs.application.highlight.LanguageDefinition;
import com.tecs.application.highlight.Token;
import com.tecs.application.highlight.TokenType;

public abstract class RegexLanguage implements LanguageDefinition {

    protected abstract Pattern commentPattern();

    protected abstract Set<String> keywords();

    protected abstract Set<String> types();

    protected Pattern stringPattern() {
        return Pattern.compile("\"(\\\\.|[^\"\\\\])*\"|'(\\\\.|[^'\\\\])*'");
    }

    protected Pattern preprocessorPattern() {
        return Pattern.compile("^\\s*#\\s*\\w+");
    }

    protected Pattern numberPattern() {
        return Pattern.compile("\\b\\d+\\b");
    }

    protected static final Pattern WORD = Pattern.compile("\\b[A-Za-z_][A-Za-z0-9_]*\\b");

    @Override
    public List<Token> tokenize(String line) {
        List<Token> tokens = new ArrayList<>();

        findMatches(tokens, commentPattern().matcher(line), TokenType.COMMENT);

        findMatches(tokens, stringPattern().matcher(line), TokenType.STRING);

        findMatches(tokens, numberPattern().matcher(line), TokenType.NUMBER);

        findMatches(tokens, preprocessorPattern().matcher(line), TokenType.PREPROCESSOR);

        Matcher words = WORD.matcher(line);

        while(words.find()) {
            String word = words.group();

            if(keywords().contains(word)) {
                tokens.add(
                    new Token(TokenType.KEYWORD, words.start(), words.end()) 
                );
            }

            if(types().contains(word)) {
                tokens.add(
                    new Token(TokenType.TYPE, words.start(), words.end())
                );
            }
        }
        removePreprocessorOverlaps(tokens);
        
        tokens.sort((a, b) -> Integer.compare(a.start(), b.start()));
        return tokens;
    }

    private void findMatches(List<Token> tokens, Matcher matcher, TokenType type) {
        while(matcher.find()) {
            tokens.add(
                new Token(type, matcher.start(), matcher.end())
            );
        }
    }

    private void removePreprocessorOverlaps(List<Token> tokens) {
        List<Token> remove = new ArrayList<>();

        for(Token preprocessor : tokens) {
            if(preprocessor.type() != TokenType.PREPROCESSOR) {
                continue;
            }

            for(Token token : tokens) {
                if(token == preprocessor) {
                    continue;
                }

                if(overlaps(preprocessor, token)) {
                    remove.add(token);
                }
            }
        }
        tokens.removeAll(remove);
    }

    private boolean overlaps(Token a, Token b) {
        return a.start() < b.end() && b.start() < a.end();
    }
}
