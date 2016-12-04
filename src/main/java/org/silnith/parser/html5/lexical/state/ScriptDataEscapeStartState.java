package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.HYPHEN_MINUS;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the script data escape start state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"-" (U+002D)
 *   <dd>Switch to the script data escape start dash state. Emit a U+002D HYPHEN-MINUS character token.
 *   <dt>Anything else
 *   <dd>Switch to the script data state. Reconsume the current input character.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#SCRIPT_DATA_ESCAPE_START
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#script-data-escape-start-state">8.2.4.20 Script data escape start state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class ScriptDataEscapeStartState extends TokenizerState {
    
    public ScriptDataEscapeStartState(final Tokenizer tokenizer) {
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
        case HYPHEN_MINUS: {
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_ESCAPE_START_DASH);
            return one(new CharacterToken(HYPHEN_MINUS));
        } // break;
        default: {
            unconsume(ch);
            setTokenizerState(Tokenizer.State.SCRIPT_DATA);
            return NOTHING;
        } // break;
        }
    }
    
}
