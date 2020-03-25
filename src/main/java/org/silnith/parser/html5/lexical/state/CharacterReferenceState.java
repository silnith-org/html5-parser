package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.AMPERSAND;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.NUMBER_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;
import static org.silnith.parser.util.UnicodeCodePoints.SEMICOLON;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Tokenizes a character reference.
 * <p>
 * This section defines how to consume a character reference, optionally with an additional allowed character, which, if specified where the algorithm is invoked, adds a character to the list of characters that cause there to not be a character reference.
 * <p>
 * This definition is used when parsing character references in text and in attributes.
 * <p>
 * The behavior depends on the identity of the next character (the one immediately after the U+0026 AMPERSAND character), as follows:
 * <dl>
 *   <dt>"tab" (U+0009)
 *   <dt>"LF" (U+000A)
 *   <dt>"FF" (U+000C)
 *   <dt>U+0020 SPACE
 *   <dt>U+003C LESS-THAN SIGN
 *   <dt>U+0026 AMPERSAND
 *   <dt>EOF
 *   <dt>The additional allowed character, if there is one
 *   <dd>
 *     <p>Not a character reference. No characters are consumed, and nothing is returned. (This is not an error, either.)
 *   </dd>
 *   <dt>"#" (U+0023)
 *   <dd>
 *     <p>Consume the U+0023 NUMBER SIGN.
 *     <p>The behavior further depends on the character after the U+0023 NUMBER SIGN:
 *     <dl>
 *       <dt>U+0078 LATIN SMALL LETTER X
 *       <dt>U+0058 LATIN CAPITAL LETTER X
 *       <dd>
 *         <p>Consume the X.
 *         <p>Follow the steps below, but using ASCII hex digits.
 *         <p>When it comes to interpreting the number, interpret it as a hexadecimal number.
 *       </dd>
 *       <dt>Anything else
 *       <dd>
 *         <p>Follow the steps below, but using ASCII digits.
 *         <p>When it comes to interpreting the number, interpret it as a decimal number.
 *       </dd>
 *     </dl>
 *     <p>
 *     Consume as many characters as match the range of characters given above (ASCII hex digits or ASCII digits).
 *     <p>
 *     If no characters match the range, then don't consume any characters (and unconsume the U+0023 NUMBER SIGN character and, if appropriate, the X character). This is a parse error; nothing is returned.
 *     <p>
 *     Otherwise, if the next character is a U+003B SEMICOLON, consume that too. If it isn't, there is a parse error.
 *     <p>
 *     If one or more characters match the range, then take them all and interpret the string of characters as a number (either hexadecimal or decimal as appropriate).
 *     <p>If that number is one of the numbers in the first column of the following table, then this is a parse error. Find the row with that number in the first column, and return a character token for the Unicode character given in the second column of that row.
 *     <table>
 *       <caption>Character replacement table.</caption>
 *       <thead>
 *         <tr>
 *           <th>Number
 *           <th colspan="2">Unicode character
 *       </thead>
 *       <tbody>
 *         <tr>
 *           <td>0x00
 *           <td>U+FFFD
 *           <td>REPLACEMENT CHARACTER
 *         <tr>
 *           <td>0x80
 *           <td>U+20AC
 *           <td>EURO SIGN (€)
 *         <tr>
 *           <td>0x82
 *           <td>U+201A
 *           <td>SINGLE LOW-9 QUOTATION MARK (‚)
 *         <tr>
 *           <td>0x83
 *           <td>U+0192
 *           <td>LATIN SMALL LETTER F WITH HOOK (ƒ)
 *         <tr>
 *           <td>0x84
 *           <td>U+201E
 *           <td>DOUBLE LOW-9 QUOTATION MARK („)
 *         <tr>
 *           <td>0x85
 *           <td>U+2026
 *           <td>HORIZONTAL ELLIPSIS (…)
 *         <tr>
 *           <td>0x86
 *           <td>U+2020
 *           <td>DAGGER (†)
 *         <tr>
 *           <td>0x87
 *           <td>U+2021
 *           <td>DOUBLE DAGGER (‡)
 *         <tr>
 *           <td>0x88
 *           <td>U+02C6
 *           <td>MODIFIER LETTER CIRCUMFLEX ACCENT (ˆ)
 *         <tr>
 *           <td>0x89
 *           <td>U+2030
 *           <td>PER MILLE SIGN (‰)
 *         <tr>
 *           <td>0x8A
 *           <td>U+0160
 *           <td>LATIN CAPITAL LETTER S WITH CARON (Š)
 *         <tr>
 *           <td>0x8B
 *           <td>U+2039
 *           <td>SINGLE LEFT-POINTING ANGLE QUOTATION MARK (‹)
 *         <tr>
 *           <td>0x8C
 *           <td>U+0152
 *           <td>LATIN CAPITAL LIGATURE OE (Œ)
 *         <tr>
 *           <td>0x8E
 *           <td>U+017D
 *           <td>LATIN CAPITAL LETTER Z WITH CARON (Ž)
 *         <tr>
 *           <td>0x91
 *           <td>U+2018
 *           <td>LEFT SINGLE QUOTATION MARK (‘)
 *         <tr>
 *           <td>0x92
 *           <td>U+2019
 *           <td>RIGHT SINGLE QUOTATION MARK (’)
 *         <tr>
 *           <td>0x93
 *           <td>U+201C
 *           <td>LEFT DOUBLE QUOTATION MARK (“)
 *         <tr>
 *           <td>0x94
 *           <td>U+201D
 *           <td>RIGHT DOUBLE QUOTATION MARK (”)
 *         <tr>
 *           <td>0x95
 *           <td>U+2022
 *           <td>BULLET (•)
 *         <tr>
 *           <td>0x96
 *           <td>U+2013
 *           <td>EN DASH (–)
 *         <tr>
 *           <td>0x97
 *           <td>U+2014
 *           <td>EM DASH (—)
 *         <tr>
 *           <td>0x98
 *           <td>U+02DC
 *           <td>SMALL TILDE (˜)
 *         <tr>
 *           <td>0x99
 *           <td>U+2122
 *           <td>TRADE MARK SIGN (™)
 *         <tr>
 *           <td>0x9A
 *           <td>U+0161
 *           <td>LATIN SMALL LETTER S WITH CARON (š)
 *         <tr>
 *           <td>0x9B
 *           <td>U+203A
 *           <td>SINGLE RIGHT-POINTING ANGLE QUOTATION MARK (›)
 *         <tr>
 *           <td>0x9C
 *           <td>U+0153
 *           <td>LATIN SMALL LIGATURE OE (œ)
 *         <tr>
 *           <td>0x9E
 *           <td>U+017E
 *           <td>LATIN SMALL LETTER Z WITH CARON (ž)
 *         <tr>
 *           <td>0x9F
 *           <td>U+0178
 *           <td>LATIN CAPITAL LETTER Y WITH DIAERESIS (Ÿ)
 *       </tbody>
 *     </table>
 *     <p>
 *     Otherwise, if the number is in the range 0xD800 to 0xDFFF or is greater than 0x10FFFF, then this is a parse error. Return a U+FFFD REPLACEMENT CHARACTER character token.
 *     <p>
 *     Otherwise, return a character token for the Unicode character whose code point is that number. Additionally, if the number is in the range 0x0001 to 0x0008, 0x000D to 0x001F, 0x007F to 0x009F, 0xFDD0 to 0xFDEF, or is one of 0x000B, 0xFFFE, 0xFFFF, 0x1FFFE, 0x1FFFF, 0x2FFFE, 0x2FFFF, 0x3FFFE, 0x3FFFF, 0x4FFFE, 0x4FFFF, 0x5FFFE, 0x5FFFF, 0x6FFFE, 0x6FFFF, 0x7FFFE, 0x7FFFF, 0x8FFFE, 0x8FFFF, 0x9FFFE, 0x9FFFF, 0xAFFFE, 0xAFFFF, 0xBFFFE, 0xBFFFF, 0xCFFFE, 0xCFFFF, 0xDFFFE, 0xDFFFF, 0xEFFFE, 0xEFFFF, 0xFFFFE, 0xFFFFF, 0x10FFFE, or 0x10FFFF, then this is a parse error.
 *   <dt>Anything else
 *   <dd>
 *     <p>Consume the maximum number of characters possible, with the consumed characters matching one of the identifiers in the first column of the named character references table (in a case-sensitive manner).
 *     <p>If no match can be made, then no characters are consumed, and nothing is returned. In this case, if the characters after the U+0026 AMPERSAND character (&amp;) consist of a sequence of one or more alphanumeric ASCII characters followed by a U+003B SEMICOLON character (;), then this is a parse error.
 *     <p>If the character reference is being consumed as part of an attribute, and the last character matched is not a ";" (U+003B) character, and the next character is either a "=" (U+003D) character or an alphanumeric ASCII character, then, for historical reasons, all the characters that were matched after the U+0026 AMPERSAND character (&amp;) must be unconsumed, and nothing is returned. However, if this next character is in fact a "=" (U+003D) character, then this is a parse error, because some legacy user agents will misinterpret the markup in those cases.
 *     <p>Otherwise, a character reference is parsed. If the last character matched is not a ";" (U+003B) character, there is a parse error.
 *     <p>Return one or two character tokens for the character(s) corresponding to the character reference name (as given by the second column of the named character references table).
 *   </dd>
 * </dl>
 * 
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#tokenizing-character-references">8.2.4.69 Tokenizing character references</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class CharacterReferenceState extends TokenizerState {
    
    private final int additionalAllowedCharacter;
    
    public CharacterReferenceState(final Tokenizer tokenizer, final char additionalAllowedCharacter) {
        super(tokenizer);
        this.additionalAllowedCharacter = additionalAllowedCharacter;
    }
    
    public CharacterReferenceState(final Tokenizer tokenizer) {
        super(tokenizer);
        this.additionalAllowedCharacter = -1;
    }
    
    @Override
    public int getMaxPushback() {
        return CharacterReferenceData.longestEntityName + 2;
    }
    
    private void unreadCharArray(final char... characters) throws IOException {
        for (int i = characters.length - 1; i >= 0; i-- ) {
            unconsume(characters[i]);
        }
    }
    
    private List<Token> handlePrefix(final int ch, final String name) throws IOException {
        final String prefix = findPrefixMatch(name);
        if (prefix == null) {
            unreadCharArray(name.toCharArray());
            return null;
        } else {
            // return the prefix, unconsume the unused
            final String unused = name.substring(prefix.length(), name.length());
            unreadCharArray(unused.toCharArray());
            final StringBuilder characters = new StringBuilder();
            for (final int codePoint : CharacterReferenceData.entityMap.get(prefix)) {
                characters.append(Character.toChars(codePoint));
            }
            return toTokens(characters.toString().toCharArray());
        }
    }
    
    private String findPrefixMatch(final String name) {
        for (int i = name.length() - 2; i > 0; i-- ) {
            final String substring = name.substring(0, i);
            if (CharacterReferenceData.entityMap.containsKey(substring)) {
                return substring;
            }
        }
        return null;
    }
    
    private char[] replaceDisallowedCharacters(final int codePoint) {
        if (CharacterReferenceData.replacementMap.containsKey(codePoint)) {
            if (isAllowParseErrors()) {
                return Character.toChars(CharacterReferenceData.replacementMap.get(codePoint));
            } else {
                throw new ParseErrorException("Disallowed character: (code point " + codePoint + ")");
            }
        }
        
        if (codePoint >= 0x10FFFF) {
            if (isAllowParseErrors()) {
                return Character.toChars(REPLACEMENT_CHARACTER);
            } else {
                throw new ParseErrorException("Disallowed character: (code point " + codePoint + ")");
            }
        }
        
        if (CharacterReferenceData.disallowedCharacters.contains(codePoint)) {
//            if (isAllowParseErrors()) {
//                return Character.toChars(REPLACEMENT_CHARACTER);
//            } else {
            throw new ParseErrorException("Disallowed character: (code point " + codePoint + ")");
//        }
        }
        
        return Character.toChars(codePoint);
    }
    
    private List<Token> toTokens(final char[] characters) {
        final List<Token> tokens = new ArrayList<>();
        for (final char ch : characters) {
            tokens.add(new CharacterToken(ch));
        }
        return tokens;
    }
    
    /**
     * Returning {@code null} means no character reference was found. This is a
     * distinct result from a parse error.
     */
    @Override
    public List<Token> getNextTokens() throws IOException {
        final StringBuilder content = new StringBuilder();
        
        int ch = consume();
        switch (ch) {
        case NUMBER_SIGN: {
            ch = consume();
            char x = 'X';
            switch (ch) {
            case 'x':
                x = 'x'; // fall through
            case 'X': {
                ch = consume();
                while (ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'f' || ch >= 'A' && ch <= 'F') {
                    content.append((char) ch);
                    ch = consume();
                }
                if (content.length() == 0) {
                    // parse error
                    if (isAllowParseErrors()) {
                        unreadCharArray(NUMBER_SIGN, x, (char) ch);
                        return null;
                    } else {
                        throw new ParseErrorException("Expected number after \"&#\"" + x + ", found: " + (char) ch);
                    }
                }
                if (ch != SEMICOLON) {
                    if (isAllowParseErrors()) {
                        unconsume(ch);
                    } else {
                        throw new ParseErrorException(
                                "Missing semicolon after numeric character reference: &#" + x + content);
                    }
                }
                // parse hex number
                final int num = Integer.parseInt(content.toString(), 16);
                return toTokens(replaceDisallowedCharacters(num));
            }
            default: {
                while (ch >= '0' && ch <= '9') {
                    content.append((char) ch);
                    ch = consume();
                }
                if (content.length() == 0) {
                    if (isAllowParseErrors()) {
                        unreadCharArray(NUMBER_SIGN, (char) ch);
                        return null;
                    } else {
                        throw new ParseErrorException("Expected number after \"&#\"" + x + ", found: " + (char) ch);
                    }
                }
                if (ch != SEMICOLON) {
                    if (isAllowParseErrors()) {
                        unconsume(ch);
                    } else {
                        throw new ParseErrorException(
                                "Missing semicolon after numeric character reference: &#" + content);
                    }
                }
                // parse decimal number
                final int num = Integer.parseInt(content.toString());
                return toTokens(replaceDisallowedCharacters(num));
            }
            }
        } // break;
        case CHARACTER_TABULATION: // fall through
        case LINE_FEED: // fall through
        case FORM_FEED: // fall through
        case SPACE: // fall through
        case LESS_THAN_SIGN: // fall through
        case AMPERSAND: // fall through
        case EOF: {
            unconsume(ch);
            return null;
        } // break;
        default: {
            // check for additional allowed character
            // can't use a variable as a case in a switch statement
            if (ch == additionalAllowedCharacter) {
                unconsume(ch);
                return null;
            }
            
            while (ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') {
                content.append((char) ch);
                ch = consume();
                if (content.length() >= CharacterReferenceData.longestEntityName) {
                    break;
                }
            }
            
            final String name = content.toString();
            if (name.isEmpty()) {
                unconsume(ch);
                return null;
            }
            if (ch == SEMICOLON) {
                final int[] codePoints = CharacterReferenceData.entityMap.get(name);
                if (codePoints == null) {
                    if (isAllowParseErrors()) {
                        unconsume(ch);
                        return handlePrefix(ch, name);
                    } else {
                        throw new ParseErrorException("Unrecognized entity reference: &" + name + ";");
                    }
                } else {
                    final StringBuilder characters = new StringBuilder();
                    for (final int codePoint : codePoints) {
                        characters.append(Character.toChars(codePoint));
                    }
                    return toTokens(characters.toString().toCharArray());
                }
            } else {
                if (isAllowParseErrors()) {
                    unconsume(ch);
                    return handlePrefix(ch, name);
                } else {
                    throw new ParseErrorException("Entity reference is not closed by a semicolon: &" + name);
                }
            }
        } // break;
        }
    }
    
}
