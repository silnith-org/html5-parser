package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.HYPHEN_MINUS;
import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the script data escaped dash dash state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"-" (U+002D)
 *   <dd>Emit a U+002D HYPHEN-MINUS character token.
 *   <dt>"<" (U+003C)
 *   <dd>Switch to the script data escaped less-than sign state.
 *   <dt>">" (U+003E)
 *   <dd>Switch to the script data state. Emit a U+003E GREATER-THAN SIGN character token.
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Switch to the script data escaped state. Emit a U+FFFD REPLACEMENT CHARACTER character token.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Switch to the script data escaped state. Emit the current input character as a character token.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#SCRIPT_DATA_ESCAPED_DASH_DASH
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#script-data-escaped-dash-dash-state">8.2.4.24 Script data escaped dash dash state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class ScriptDataEscapedDashDashState extends TokenizerState {
    
    public ScriptDataEscapedDashDashState(final Tokenizer tokenizer) {
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
        case HYPHEN_MINUS: {
            return one(new CharacterToken(HYPHEN_MINUS));
        } // break;
        case LESS_THAN_SIGN: {
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_ESCAPED_LESS_THAN_SIGN);
            return NOTHING;
        } // break;
        case GREATER_THAN_SIGN: {
            setTokenizerState(Tokenizer.State.SCRIPT_DATA);
            return one(new CharacterToken(GREATER_THAN_SIGN));
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.SCRIPT_DATA_ESCAPED);
                return one(new CharacterToken(REPLACEMENT_CHARACTER));
            } else {
                throw new ParseErrorException("Null character in script data escaped dash dash state.");
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected end-of-file in script data escaped dash dash state.");
            }
        } // break;
        default: {
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_ESCAPED);
            return one(new CharacterToken((char) ch));
        } // break;
        }
    }
    
}
