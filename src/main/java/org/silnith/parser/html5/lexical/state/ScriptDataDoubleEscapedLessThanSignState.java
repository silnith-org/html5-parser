package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.SOLIDUS;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the script data double escaped less than sign state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"/" (U+002F)
 *   <dd>Set the temporary buffer to the empty string. Switch to the script data double escape end state. Emit a U+002F SOLIDUS character token.
 *   <dt>Anything else
 *   <dd>Switch to the script data double escaped state. Reconsume the current input character.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#SCRIPT_DATA_DOUBLE_ESCAPED_LESS_THAN_SIGN
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#script-data-double-escaped-less-than-sign-state">8.2.4.32 Script data double escaped less-than sign state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class ScriptDataDoubleEscapedLessThanSignState extends TokenizerState {
    
    public ScriptDataDoubleEscapedLessThanSignState(final Tokenizer tokenizer) {
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
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_DOUBLE_ESCAPE_END);
            return one(new CharacterToken(SOLIDUS));
        } // break;
        default: {
            unconsume(ch);
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_DOUBLE_ESCAPED);
            return NOTHING;
        } // break;
        }
    }
    
}
