package com.tecs.application.highlight.languages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tecs.application.highlight.LanguageDefinition;
import com.tecs.application.highlight.Token;
import com.tecs.application.highlight.TokenType;

public class MarkdownLanguage implements LanguageDefinition {

    private static final Pattern HEADER = Pattern.compile("^#{1,6}\\s.*");

    private static final Pattern CODE = Pattern.compile("`[^`]+`");

    private static final Pattern BOLD = Pattern.compile("\\*\\*.*?\\*\\*");

    private static final Pattern ITALIC = Pattern.compile("(?<!\\*)\\*[^*]+\\*(?!\\*)");

    @Override
    public String name() {
        return "markdown";
    }

    @Override
    public List<Token> tokenize(String line) {
        List<Token> tokens = new ArrayList<>();

        Matcher header = HEADER.matcher(line);

        if (header.find()) {
            tokens.add(
                    new Token(TokenType.MARKDOWN_HEADER, header.start(), header.end()));
        }

        Matcher code = CODE.matcher(line);

        while (code.find()) {
            tokens.add(
                    new Token(
                            TokenType.MARKDOWN_CODE,
                            code.start(),
                            code.end()));
        }

        List<Token> boldTokens = new ArrayList<>();

        Matcher bold = BOLD.matcher(line);

        while (bold.find()) {
            Token token = new Token(
                    TokenType.MARKDOWN_BOLD,
                    bold.start(),
                    bold.end());

            boldTokens.add(token);
            tokens.add(token);
        }

        Matcher italic = ITALIC.matcher(line);

        while (italic.find()) {

            Token italicToken = new Token(
                    TokenType.MARKDOWN_ITALIC,
                    italic.start(),
                    italic.end());

            boolean insideBold = false;

            for (Token boldToken : boldTokens) {
                if (overlaps(italicToken, boldToken)) {
                    insideBold = true;
                    break;
                }
            }

            if (!insideBold) {
                tokens.add(italicToken);
            }
        }

        tokens.sort((a, b) -> Integer.compare(a.start(), b.start()));
        return tokens;
    }

    private boolean overlaps(Token a, Token b) {
        return a.start() < b.end()
                && b.start() < a.end();
    }
}
