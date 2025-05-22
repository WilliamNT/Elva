package net.skiby.elva;

import net.skiby.elva.tokenizer.Token;
import net.skiby.elva.tokenizer.Tokenizer;

public class Main {
    public static void main(String[] args) {
        var t = new Tokenizer();

        var input = "var x = 555,5555 * 5,5;";
        var tokens = t.tokenize(input);

        for (Token token : tokens) {
            System.out.println(token.toString());
        }

        System.out.println(input);
        System.out.println(Tokenizer.reconstruct(tokens));
    }
}