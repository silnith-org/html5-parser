package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.SOLIDUS;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;

/**
 * @see <a
 *      href="http://www.w3.org/TR/html5/syntax.html#rawtext-less-than-sign-state">8.2.4.14
 *      RAWTEXT less-than sign state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class RAWTEXTLessThanSignState extends TokenizerState {
    
    public RAWTEXTLessThanSignState(final Tokenizer tokenizer) {
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
            setTokenizerState(Tokenizer.State.RAWTEXT_END_TAG_OPEN);
            return NOTHING;
        } // break;
        default: {
            unconsume(ch);
            setTokenizerState(Tokenizer.State.RAWTEXT);
            return one(new CharacterToken(LESS_THAN_SIGN));
        } // break;
        }
    }
    
}
