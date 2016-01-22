package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.DOCTYPEToken;
import org.silnith.parser.html5.lexical.token.Token;

/**
 * @see <a
 *      href="http://www.w3.org/TR/html5/syntax.html#bogus-doctype-state">8.2.4.67
 *      Bogus DOCTYPE state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class BogusDOCTYPEState extends TokenizerState {
    
    public BogusDOCTYPEState(final Tokenizer tokenizer) {
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
        case GREATER_THAN_SIGN: {
            setTokenizerState(Tokenizer.State.DATA);
            final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
            return one(doctypeToken);
        } // break;
        case EOF: {
            setTokenizerState(Tokenizer.State.DATA);
            final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
            return one(doctypeToken);
        } // break;
        default: {
            // ignore character
            return NOTHING;
        } // break;
        }
    }
    
}
