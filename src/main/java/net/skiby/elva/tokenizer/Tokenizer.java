package net.skiby.elva.tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private String buffer = "";
    private final List<Token> tokens = new ArrayList<>();
    private TokenType suspectedTokenType;
    private int bufferStartPosition;
    private int currentPosition;
    private String input;

    public List<Token> tokenize(String input) {
        this.input = input;

        for (currentPosition = 0; currentPosition < input.length(); currentPosition++) {
            char c = input.charAt(currentPosition);
            bufferizeOrTokenize(c);
        }

        return tokens;
    }

    private void bufferizeOrTokenize(char c) {
        if (suspectedTokenType == null) {
            suspectedTokenType = identifyTokenType(c);
        }

        buffer += c;

        var nextPosition = currentPosition + 1;
        if (nextPosition < input.length()) {
            var nextCharacter = input.charAt(nextPosition);

            if (suspectedTokenType != identifyTokenType(nextCharacter)) {
                tokenizeBuffer();

                suspectedTokenType = identifyTokenType(nextCharacter);
                bufferStartPosition = nextPosition;
            }
        } else if (currentPosition + 1 == input.length()) {
            tokenizeBuffer();
        }
    }

    private void tokenizeBuffer() {
        final var token = new Token(buffer, suspectedTokenType, bufferStartPosition + 1, currentPosition + 1);
        tokens.add(token);
        buffer = "";
    }

    private TokenType identifyTokenType(char c) {
        if (Character.isWhitespace(c)) {
            return TokenType.WHITESPACE;
        } else if (Character.isDigit(c)) {
            return TokenType.NUMBER;
        } else if (Character.isLetter(c)) {
            return TokenType.IDENTIFIER;
        }

        return TokenType.UNKNOWN;
    }
}
