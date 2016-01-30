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
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#rawtext-state">8.2.4.5
 *      RAWTEXT state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class RAWTEXTState extends TokenizerState {
    
    public RAWTEXTState(final Tokenizer tokenizer) {
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
            setTokenizerState(Tokenizer.State.RAWTEXT_LESS_THAN_SIGN);
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
