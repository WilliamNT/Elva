package net.skiby.elva.tokenizer;

import net.skiby.elva.tokenizer.Token;
import net.skiby.elva.tokenizer.TokenType;
import net.skiby.elva.tokenizer.Tokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class IdentifierTests {
    private List<Token> tokenize(String s) {
        return new Tokenizer().tokenize(s);
    }

    @Test
    void singleCharacterIdentifierIsRecognized() {
        var actual = tokenize("x");

        Assertions.assertEquals(
                "x",
                actual.get(0).value()
        );

        Assertions.assertEquals(
                TokenType.IDENTIFIER,
                actual.get(0).type()
        );

        Assertions.assertEquals(
                1,
                actual.get(0).startPosition()
        );

        Assertions.assertEquals(
                1,
                actual.get(0).endPosition()
        );
    }

    @Test
    void multiCharacterIdentifierIsRecognized() {
        var actual = tokenize("xyz");

        Assertions.assertEquals(
                "xyz",
                actual.get(0).value()
        );

        Assertions.assertEquals(
                TokenType.IDENTIFIER,
                actual.get(0).type()
        );

        Assertions.assertEquals(
                1,
                actual.get(0).startPosition()
        );

        Assertions.assertEquals(
                3,
                actual.get(0).endPosition()
        );
    }

    @Test
    void identifierCanEndWithDigit() {
        var actual = tokenize("foo1 foo123");

        var expected = new ArrayList<Token>();
        expected.add(new Token("foo1", TokenType.IDENTIFIER, 1, 4));
        expected.add(new Token(" ", TokenType.WHITESPACE, 5, 5));
        expected.add(new Token("foo123", TokenType.IDENTIFIER, 6, 11));
        expected.add(new Token("", TokenType.EOF, 11, 11));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void floatsCantBeInIdentifiers() {
        var actual = tokenize("foo1,5");

        var expected = new ArrayList<Token>();
        expected.add(new Token("foo1", TokenType.IDENTIFIER, 1, 4));
        expected.add(new Token(",", TokenType.COMMA, 5, 5));
        expected.add(new Token("5", TokenType.NUMBER, 6, 6));
        expected.add(new Token("", TokenType.EOF, 6, 6));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void identifiersCantStartWithDigit() {
        var actual = tokenize("1foo");

        var expected = new ArrayList<Token>();
        expected.add(new Token("1", TokenType.NUMBER, 1, 1));
        expected.add(new Token("foo", TokenType.IDENTIFIER, 2, 4));
        expected.add(new Token("", TokenType.EOF, 4, 4));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void floatsBeforeIdentifiersAreTreatedSeparately() {
        var actual = tokenize("1,5foo");

        var expected = new ArrayList<Token>();
        expected.add(new Token("1,5", TokenType.NUMBER, 1, 3));
        expected.add(new Token("foo", TokenType.IDENTIFIER, 4, 6));
        expected.add(new Token("", TokenType.EOF, 6, 6));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void floatInMiddleOfIdentifierCausesSplitting() {
        var actual = tokenize("xyz1,5foo");

        var expected = new ArrayList<Token>();
        expected.add(new Token("xyz1", TokenType.IDENTIFIER, 1, 4));
        expected.add(new Token(",", TokenType.COMMA, 5, 5));
        expected.add(new Token("5", TokenType.NUMBER, 6, 6));
        expected.add(new Token("foo", TokenType.IDENTIFIER, 7, 9));
        expected.add(new Token("", TokenType.EOF, 9, 9));


        Assertions.assertEquals(expected, actual);
    }

}
