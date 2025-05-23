package net.skiby.elva.tokenizer;

import java.util.Map;

public class TokenizerUtils {
    private static final Map<Character, TokenType> RESERVED_SYMBOLS = Map.ofEntries(
            Map.entry('(', TokenType.LPAREN),
            Map.entry(')', TokenType.RPAREN),
            Map.entry('+', TokenType.PLUS),
            Map.entry('-', TokenType.MINUS),
            Map.entry('*', TokenType.MULTIPLY),
            Map.entry('/', TokenType.DIVIDE),
            Map.entry('=', TokenType.EQUALS),
            Map.entry(',', TokenType.COMMA)
    );

    public static TokenType identifyTokenType(char c) {
        if (Character.isWhitespace(c)) {
            return TokenType.WHITESPACE;
        } else if (Character.isDigit(c)) {
            return TokenType.NUMBER;
        } else if (Character.isLetter(c)) {
            return TokenType.IDENTIFIER;
        } else if (isSymbol(c)) {
            return RESERVED_SYMBOLS.get(c);
        }

        return TokenType.UNKNOWN;
    }

    public static boolean isSymbol(char c) {
        return RESERVED_SYMBOLS.containsKey(c);
    }

    public static boolean isInvalidIdentifierCharacter(char c) {
        return !Character.isDigit(c) && !Character.isLetter(c);
    }
}
