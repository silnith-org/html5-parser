package org.silnith.parser.html5.lexical.state;

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
 * Applies the script data state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"&lt;" (U+003C)
 *   <dd>Switch to the script data less-than sign state.
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Emit a U+FFFD REPLACEMENT CHARACTER character token.
 *   <dt>EOF
 *   <dd>Emit an end-of-file token.
 *   <dt>Anything else
 *   <dd>Emit the current input character as a character token.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#SCRIPT_DATA
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#script-data-state">8.2.4.6 Script data state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class ScriptDataState extends TokenizerState {
    
    public ScriptDataState(final Tokenizer tokenizer) {
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
        case LESS_THAN_SIGN: {
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_LESS_THAN_SIGN);
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
