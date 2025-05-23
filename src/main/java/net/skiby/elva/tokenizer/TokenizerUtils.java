package net.skiby.elva.tokenizer;

import java.util.Set;

public class TokenizerUtils {
    public static TokenType identifyTokenType(char c) {
        if (Character.isWhitespace(c)) {
            return TokenType.WHITESPACE;
        } else if (Character.isDigit(c)) {
            return TokenType.NUMBER;
        } else if (Character.isLetter(c)) {
            return TokenType.IDENTIFIER;
        } else if (isSymbol(c)) {
            return TokenType.SYMBOL;
        } else if (c == '(') {
            return TokenType.LPAREN;
        } else if (c == ')') {
            return TokenType.RPAREN;
        }

        return TokenType.UNKNOWN;
    }

    public static boolean isSymbol(char c) {
        var symbols = Set.of('+', '-', '*', '/', ',', '=');
        return symbols.contains(c);
    }

    public static boolean isInvalidIdentifierCharacter(char c) {
        return !Character.isDigit(c) && !Character.isLetter(c);
    }
}
