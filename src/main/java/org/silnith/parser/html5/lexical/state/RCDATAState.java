package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.AMPERSAND;
import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.EndOfFileToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the rcdata state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>U+0026 AMPERSAND (&amp;)
 *   <dd>Switch to the character reference in RCDATA state.
 *   <dt>"&lt;" (U+003C)
 *   <dd>Switch to the RCDATA less-than sign state.
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Emit a U+FFFD REPLACEMENT CHARACTER character token.
 *   <dt>EOF
 *   <dd>Emit an end-of-file token.
 *   <dt>Anything else
 *   <dd>Emit the current input character as a character token.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#RCDATA
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#rcdata-state">8.2.4.3 RCDATA state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class RCDATAState extends TokenizerState {
    
    public RCDATAState(final Tokenizer tokenizer) {
        super(tokenizer);
    }
    
    @Override
    public int getMaxPushback() {
        return 0;
    }
    
    @Override
    public List<Token> getNextTokens() throws IOException {
        final int ch = consume();
        switch (ch) {
        case AMPERSAND: {
            setTokenizerState(Tokenizer.State.CHARACTER_REFERENCE_IN_RCDATA);
            return NOTHING;
        } // break;
        case LESS_THAN_SIGN: {
            setTokenizerState(Tokenizer.State.RCDATA_LESS_THAN_SIGN);
            return NOTHING;
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                return one(new CharacterToken(REPLACEMENT_CHARACTER));
            } else {
                throw new ParseErrorException("Null character.");
            }
        } // break;
        case EOF: {
            return one(new EndOfFileToken());
        } // break;
        default: {
            return one(new CharacterToken((char) ch));
        } // break;
        }
    }
    
}
