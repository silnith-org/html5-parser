package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.SOLIDUS;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the rcdata less than sign state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"/" (U+002F)
 *   <dd>Set the temporary buffer to the empty string. Switch to the RCDATA end tag open state.
 *   <dt>Anything else
 *   <dd>Switch to the RCDATA state. Emit a U+003C LESS-THAN SIGN character token. Reconsume the current input character.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#RCDATA_LESS_THAN_SIGN
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#rcdata-less-than-sign-state">8.2.4.11 RCDATA less-than sign state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class RCDATALessThanSignState extends TokenizerState {
    
    public RCDATALessThanSignState(final Tokenizer tokenizer) {
        super(tokenizer);
    }
    
    @Override
    public int getMaxPushback() {
        return 1;
    }
    
    @Override
    public List<Token> getNextTokens() throws IOException {
        final int ch = consume();
        switch (ch) {
        case SOLIDUS: {
            createTemporaryBuffer();
            setTokenizerState(Tokenizer.State.RCDATA_END_TAG_OPEN);
            return NOTHING;
        } // break;
        default: {
            unconsume(ch);
            setTokenizerState(Tokenizer.State.RCDATA);
            return one(new CharacterToken(LESS_THAN_SIGN));
        } // break;
        }
    }
    
}
