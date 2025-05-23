package net.skiby.elva.tokenizer;

public enum TokenType {
    // math
    NUMBER,
    LPAREN,
    RPAREN,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    EQUALS,

    // generic
    IDENTIFIER,
    COMMA,

    // ignored
    WHITESPACE,
    UNKNOWN,
    EOF
}
