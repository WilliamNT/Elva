package tokenizer;

import net.skiby.elva.tokenizer.Token;
import net.skiby.elva.tokenizer.TokenType;
import net.skiby.elva.tokenizer.Tokenizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class BasicTests {
    @Test
    void emptyInputProducesEmptyOutputArray() {
        var t = new Tokenizer();
        var tokens = t.tokenize("");

        var expected = new ArrayList<Token>();
        expected.add(new Token("", TokenType.EOF, 0, 0));

        Assertions.assertEquals(expected, tokens);
    }

    @Test
    void singleCharacterInputIsRecognizedCorrectly() {
        var t = new Tokenizer();

        var values = new char[]{'a', '1', '#', '+', ' ', '\t', '\n'};
        for (char c : values) {
            var tokens = t.tokenize(Character.toString(c));

            Assertions.assertEquals(2, tokens.size()); // because of EOF token
            Assertions.assertEquals(Character.toString(c), tokens.get(0).value());
            Assertions.assertEquals(1, tokens.get(0).startPosition());
            Assertions.assertEquals(1, tokens.get(0).endPosition());
        }
    }

    @Test
    void cleanUpWorks() {
        var t = new Tokenizer();

        t.tokenize("asd asd");

        var actual = t.tokenize("123");
        var expected = new ArrayList<>();
        expected.add(new Token(
                "123",
                TokenType.NUMBER,
                1,
                3
        ));

        expected.add(new Token("", TokenType.EOF, 3, 3));

        Assertions.assertEquals(expected, actual);
    }
}
