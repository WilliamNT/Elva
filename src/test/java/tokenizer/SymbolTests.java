package tokenizer;

import net.skiby.elva.tokenizer.Token;
import net.skiby.elva.tokenizer.TokenType;
import net.skiby.elva.tokenizer.Tokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SymbolTests {
    private List<Token> tokenize(String s) {
        return new Tokenizer().tokenize(s);
    }

    @Test
    void singleSymbolIsRecognized() {
        var actual = tokenize("+").get(0);

        Assertions.assertEquals("+", actual.value);
        Assertions.assertEquals(TokenType.PLUS, actual.type);
        Assertions.assertEquals(1, actual.startPosition);
        Assertions.assertEquals(1, actual.endPosition);
    }

    @Test
    void multipleSymbolsNextToEachOtherAreTreatedAsIndividualTokens() {
        var actual = tokenize("+=*/");

        var expected = new ArrayList<Token>();
        expected.add(new Token("+", TokenType.PLUS, 1, 1));
        expected.add(new Token("=", TokenType.EQUALS, 2, 2));
        expected.add(new Token("*", TokenType.MULTIPLY, 3, 3));
        expected.add(new Token("/", TokenType.DIVIDE, 4, 4));

        Assertions.assertEquals(expected, actual);
    }
}
