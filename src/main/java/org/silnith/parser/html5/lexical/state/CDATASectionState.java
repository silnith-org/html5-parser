package org.silnith.parser.html5.lexical.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the cdata section state logic.
 * <p>
 * Switch to the data state.
 * <p>Consume every character up to the next occurrence of the three character sequence U+005D RIGHT SQUARE BRACKET U+005D RIGHT SQUARE BRACKET U+003E GREATER-THAN SIGN (]]>), or the end of the file (EOF), whichever comes first. Emit a series of character tokens consisting of all the characters consumed except the matching three character sequence at the end (if one was found before the end of the file).
 * <p>If the end of the file was reached, reconsume the EOF character.
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#CDATA_SECTION
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#cdata-section-state">8.2.4.68 CDATA section state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class CDATASectionState extends TokenizerState {
    
    public CDATASectionState(final Tokenizer tokenizer) {
        super(tokenizer);
    }
    
    @Override
    public int getMaxPushback() {
        return 0;
    }
    
    @Override
    public List<Token> getNextTokens() throws IOException {
        setTokenizerState(Tokenizer.State.DATA);
        final StringBuilder stringBuilder = new StringBuilder();
        int ch = consume();
        while (ch != -1) {
            stringBuilder.append((char) ch);
            final int length = stringBuilder.length();
            if (length >= 3) {
                final int realLength = length - 3;
                final String suffix = stringBuilder.substring(realLength);
                if (suffix.equals("]]>")) {
                    stringBuilder.setLength(realLength);
                    break;
                }
            }
            ch = consume();
        }
        final List<Token> tokens = new ArrayList<>();
        for (final char character : stringBuilder.toString().toCharArray()) {
            tokens.add(new CharacterToken(character));
        }
        return tokens;
    }
    
}
