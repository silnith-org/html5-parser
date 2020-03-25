package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the self closing start tag state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"&gt;" (U+003E)
 *   <dd>Set the self-closing flag of the current tag token. Switch to the data state. Emit the current tag token.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Parse error. Switch to the before attribute name state. Reconsume the character.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#SELF_CLOSING_START_TAG
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#self-closing-start-tag-state">8.2.4.43 Self-closing start tag state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class SelfClosingStartTagState extends TokenizerState {
    
    public SelfClosingStartTagState(final Tokenizer tokenizer) {
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
            final TagToken pendingToken = clearPendingTag();
            pendingToken.setSelfClosing();
            return one(pendingToken);
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                return NOTHING;
            } else {
                throw new ParseErrorException();
            }
        } // break;
        default: {
            if (isAllowParseErrors()) {
                unconsume(ch);
                setTokenizerState(Tokenizer.State.BEFORE_ATTRIBUTE_NAME);
                return NOTHING;
            } else {
                throw new ParseErrorException();
            }
        } // break;
        }
    }
    
}
