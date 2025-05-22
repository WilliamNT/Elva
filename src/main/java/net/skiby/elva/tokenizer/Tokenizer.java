package net.skiby.elva.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Tokenizer {
    private StringBuilder buffer = new StringBuilder();
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

        buffer.append(c);

        var nextPosition = currentPosition + 1;
        if (nextPosition < input.length()) {
            var nextCharacter = input.charAt(nextPosition);

            if (suspectedTokenType != identifyTokenType(nextCharacter)) {
                if (wouldBeValidFloat(c, nextCharacter)) {
                    return;
                }

                tokenizeBuffer();

                suspectedTokenType = identifyTokenType(nextCharacter);
                bufferStartPosition = nextPosition;
            }
        } else if (currentPosition + 1 == input.length()) {
            tokenizeBuffer();
        }
    }

    private void tokenizeBuffer() {
        final var token = new Token(buffer.toString(), suspectedTokenType, bufferStartPosition + 1, currentPosition + 1);
        tokens.add(token);
        buffer.setLength(0);
    }

    private TokenType identifyTokenType(char c) {
        if (Character.isWhitespace(c)) {
            return TokenType.WHITESPACE;
        } else if (Character.isDigit(c)) {
            return TokenType.NUMBER;
        } else if (Character.isLetter(c)) {
            return TokenType.IDENTIFIER;
        } else if (isSymbol(c)) {
            return TokenType.SYMBOL;
        }

        return TokenType.UNKNOWN;
    }

    private boolean isSymbol(char c) {
        var symbols = Set.of('+', '-', '*', '/', ',');
        return symbols.contains(c);
    }

    private boolean wouldBeValidFloat(char current, char next) {
        if (buffer.indexOf(",") != -1) {
            return false;
        }

        if (currentPosition + 2 >= input.length()) {
            return false;
        }

        return Character.isDigit(current) && next == ',' && Character.isDigit(input.charAt(currentPosition+2));
    }

    public static String reconstruct(List<Token> tokens) {
        var output = new StringBuilder();

        for (Token token : tokens) {
            output.append(token.value);
        }

        return output.toString();
    }
}
