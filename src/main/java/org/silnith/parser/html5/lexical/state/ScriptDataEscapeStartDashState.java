package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.HYPHEN_MINUS;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;

/**
 * @see <a
 *      href="http://www.w3.org/TR/html5/syntax.html#script-data-escape-start-dash-state">8.2.4.21
 *      Script data escape start dash state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class ScriptDataEscapeStartDashState extends TokenizerState {
    
    public ScriptDataEscapeStartDashState(final Tokenizer tokenizer) {
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
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_ESCAPED_DASH_DASH);
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
