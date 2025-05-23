package net.skiby.elva.tokenizer;

import java.util.Objects;

public class Token {
    public final String value;
    public final TokenType type;
    public final int startPosition;
    public final int endPosition;

    public Token(String value, TokenType type, int startPosition, int endPosition) {
        this.value = value;
        this.type = type;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public String toString() {
        final var tag = switch (type) {
            case NUMBER -> "NUMBER";
            case IDENTIFIER -> "IDENT";
            case SYMBOL -> "SYMBOL";
            case WHITESPACE -> "WHITESPACE";
            case END -> "END";
            case UNKNOWN -> "UNKNOWN";
        };

        if (type == TokenType.END) {
            return "<END />";
        } else if (type == TokenType.WHITESPACE) {
            return String.format("<%s start=\"%s\" end=\"%s\"/>", tag, startPosition, endPosition);
        }else {
            return String.format("<%s start=\"%s\" end=\"%s\">%s<%s/>", tag, startPosition, endPosition, value, tag);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return startPosition == token.startPosition && endPosition == token.endPosition && Objects.equals(value, token.value) && type == token.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type, startPosition, endPosition);
    }
}
