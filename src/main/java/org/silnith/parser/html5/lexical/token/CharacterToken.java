package org.silnith.parser.html5.lexical.token;

import java.util.ArrayList;
import java.util.List;

/**
 * A token representing a single character of text in a document.
 *
 * @see Token.Type#CHARACTER
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class CharacterToken extends Token {
    
    /**
     * Converts an array of characters into a list of character tokens. This is
     * a literal conversion, no additional processing is done on the array.
     * 
     * @param characters
     *            the array of characters to convert
     * @return a list of tokens representing the characters in the array, in
     *         order
     */
    public static List<Token> toTokens(final char... characters) {
        final List<Token> tokens = new ArrayList<>(characters.length);
        for (final char character : characters) {
            tokens.add(new CharacterToken(character));
        }
        return tokens;
    }
    
    /**
     * Converts a string into a list of character tokens. This is a literal
     * conversion, no additional processing is done on the string.
     * 
     * @param string
     *            the string to convert
     * @return a list of tokens representing the characters in the string, in
     *         order
     */
    public static List<Token> toTokens(final String string) {
        return toTokens(string.toCharArray());
    }
    
    /**
     * Converts a sequence of characters into a list of character tokens. This
     * is a literal conversion, no additional processing is done on the
     * sequence.
     * 
     * @param charSequence
     *            the sequence of characters to convert
     * @return a list of tokens representing the characters in the sequence, in
     *         order
     */
    public static List<Token> toTokens(final CharSequence charSequence) {
        return toTokens(charSequence.toString());
    }
    
    private final char character;
    
    public CharacterToken(final char character) {
        super();
        this.character = character;
    }
    
    public char getCharacter() {
        return character;
    }
    
    @Override
    public Type getType() {
        return Type.CHARACTER;
    }
    
    @Override
    public String toString() {
        return "char '" + character + "'";
    }
    
}
