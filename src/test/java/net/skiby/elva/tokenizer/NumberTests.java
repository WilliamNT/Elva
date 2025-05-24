package net.skiby.elva.tokenizer;

import net.skiby.elva.tokenizer.Token;
import net.skiby.elva.tokenizer.TokenType;
import net.skiby.elva.tokenizer.Tokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class NumberTests {
    private List<Token> tokenize(String s) {
        return new Tokenizer().tokenize(s);
    }

    @Test
    void singleDigitNumberIsRecognized() {
        var actual = tokenize("1");

        Assertions.assertEquals(
                "1",
                actual.get(0).value()
        );

        Assertions.assertEquals(
                TokenType.NUMBER,
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
    void multiDigitNumberIsRecognized() {
        var actual = tokenize("123456789");

        Assertions.assertEquals(
                "123456789",
                actual.get(0).value()
        );

        Assertions.assertEquals(
                TokenType.NUMBER,
                actual.get(0).type()
        );

        Assertions.assertEquals(
                1,
                actual.get(0).startPosition()
        );

        Assertions.assertEquals(
                9,
                actual.get(0).endPosition()
        );
    }

    @Test
    void floatIsRecognized() {
        var actual = tokenize("1,2");

        Assertions.assertEquals(
                "1,2",
                actual.get(0).value()
        );

        Assertions.assertEquals(
                TokenType.NUMBER,
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
    void floatCannotStartWithComma() {
        var actual = tokenize(",1");

        var expected = new ArrayList<Token>();
        expected.add(new Token(",", TokenType.COMMA, 1,1));
        expected.add(new Token("1", TokenType.NUMBER, 2, 2));
        expected.add(new Token("", TokenType.EOF, 2, 2));


        Assertions.assertEquals(expected, actual);
    }

    @Test
    void floatCannotEndWithComma() {
        var actual = tokenize("1,");

        var expected = new ArrayList<Token>();
        expected.add(new Token("1", TokenType.NUMBER, 1, 1));
        expected.add(new Token(",", TokenType.COMMA, 2,2));
        expected.add(new Token("", TokenType.EOF, 2, 2));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void multipleCommasResultInMultipleTokens() {
        var actual = tokenize("1,1,1");

        var expected = new ArrayList<Token>();
        expected.add(new Token("1,1", TokenType.NUMBER, 1, 3));
        expected.add(new Token(",", TokenType.COMMA, 4,4));
        expected.add(new Token("1", TokenType.NUMBER, 5, 5));
        expected.add(new Token("", TokenType.EOF, 5, 5));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void multipleFloatsCanBeNextToEachOther() {
        var actual = tokenize("1,1,1,1");

        var expected = new ArrayList<Token>();
        expected.add(new Token("1,1", TokenType.NUMBER, 1, 3));
        expected.add(new Token(",", TokenType.COMMA, 4,4));
        expected.add(new Token("1,1", TokenType.NUMBER, 5, 7));
        expected.add(new Token("", TokenType.EOF, 7, 7));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void nonNumericCharsBreakTheFloatProperly() {
        var actual = tokenize("1,x1");

        var expected = new ArrayList<Token>();
        expected.add(new Token("1", TokenType.NUMBER, 1, 1));
        expected.add(new Token(",", TokenType.COMMA, 2,2));
        expected.add(new Token("x1", TokenType.IDENTIFIER, 3, 4));
        expected.add(new Token("", TokenType.EOF, 4, 4));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void commaSeparatedNonDigitsAreNotTreatedAsFloats() {
        var actual = tokenize("x,yz");

        var expected = new ArrayList<Token>();
        expected.add(new Token("x", TokenType.IDENTIFIER, 1, 1));
        expected.add(new Token(",", TokenType.COMMA, 2,2));
        expected.add(new Token("yz", TokenType.IDENTIFIER, 3, 4));
        expected.add(new Token("", TokenType.EOF, 4, 4));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void negativeNumbersAreRecognizedAsNumberToken() {
        var testValues = new String[]{"-5", "-1,2", "-1,234", "-1234"};

        for (String testValue : testValues) {
            var tokens = tokenize(testValue);

            var expected = new ArrayList<Token>();
            expected.add(new Token(testValue, TokenType.NUMBER, 1, testValue.length()));
            expected.add(new Token("", TokenType.EOF, testValue.length(), testValue.length()));

            Assertions.assertEquals(expected, tokens);
        }
    }

    @Test
    void subtractionDoesntGetConfusedAsNegativeNumbers() {
        var tokens = tokenize("5--5");

        var expected = new ArrayList<Token>();
        expected.add(new Token("5", TokenType.NUMBER, 1, 1));
        expected.add(new Token("-", TokenType.MINUS, 2, 2));
        expected.add(new Token("-5", TokenType.NUMBER, 3, 4));
        expected.add(new Token("", TokenType.EOF, 4, 4));

        Assertions.assertEquals(expected, tokens);
    }
}
