package net.skiby.elva.tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final StringBuilder buffer = new StringBuilder();
    private final List<Token> tokens = new ArrayList<>();
    private TokenType suspectedTokenType;
    private int bufferStartPosition;
    private int currentPosition;
    private String input;

    private void init(String input) {
        buffer.setLength(0);
        tokens.clear();
        suspectedTokenType = null;
        bufferStartPosition = 0;
        currentPosition = 0;
        this.input = input;
    }

    public List<Token> tokenize(String input) {
        init(input);

        while (currentPosition < input.length()) {
            char currentCharacter = input.charAt(currentPosition);
            bufferizeOrTokenize(currentCharacter);
            currentPosition++;
        }

        tokens.add(new Token("", TokenType.EOF, currentPosition, currentPosition));

        return tokens;
    }

    private void bufferizeOrTokenize(char currentCharacter) {
        if (suspectedTokenType == null) {
            suspectedTokenType = TokenizerUtils.identifyTokenType(currentCharacter);
        }

        buffer.append(currentCharacter);

        if (hasNext()) {
            var nextCharacter = peek(1);
            var nextType = TokenizerUtils.identifyTokenType(nextCharacter);

            if (suspectedTokenType != nextType) {
                if (wouldBeValidFloat(currentCharacter, nextCharacter)) {
                    return;
                } else if (wouldBeValidIdentifier(currentCharacter, nextCharacter)) {
                    return;
                }

                tokenizeBufferContents();
                resetBuffer(nextType);
            } else {
                if (wouldBeValidSymbol(currentCharacter)) {
                    tokenizeBufferContents();
                    resetBuffer(nextType);
                }
            }
        } else {
            tokenizeBufferContents();
        }
    }

    private void tokenizeBufferContents() {
        final var token = new Token(buffer.toString(), suspectedTokenType, bufferStartPosition + 1, currentPosition + 1);
        tokens.add(token);
        buffer.setLength(0);
    }

    private void resetBuffer(TokenType suspectedType) {
        suspectedTokenType = suspectedType;
        bufferStartPosition = currentPosition + 1;
    }

    private boolean wouldBeValidFloat(char current, char next) {
        if (buffer.indexOf(",") != -1) {
            return false;
        }

        if (currentPosition + 2 >= input.length()) {
            return false;
        }

        if (suspectedTokenType != TokenType.NUMBER) {
            return false;
        }

        return Character.isDigit(current) && next == ',' && Character.isDigit(peek(2));
    }

    private boolean wouldBeValidIdentifier(char current, char next) {
        if (TokenizerUtils.isInvalidIdentifierCharacter(current) || TokenizerUtils.isInvalidIdentifierCharacter(next)) {
            return false;
        }

        return suspectedTokenType == TokenType.IDENTIFIER;
    }

    private boolean wouldBeValidSymbol(char current) {
        if (buffer.length() != 1) {
            return false;
        }

        return TokenizerUtils.isSymbol(buffer.charAt(0)) && TokenizerUtils.isSymbol(current);
    }

    public static String reconstruct(List<Token> tokens) {
        var output = new StringBuilder();

        for (Token token : tokens) {
            output.append(token.value());
        }

        return output.toString();
    }

    private char peek(int offset) {
        return input.charAt(currentPosition + offset);
    }

    private boolean hasNext() {
        return currentPosition + 1 < input.length();
    }
}
