package net.skiby.elva;

import net.skiby.elva.tokenizer.Token;
import net.skiby.elva.tokenizer.Tokenizer;

public class Main {
    public static void main(String[] args) {
        var t = new Tokenizer();

        var tokens = t.tokenize("This is a test text. 12345 something");

        for (Token token : tokens) {
            System.out.println(token.toString());
        }
    }
}