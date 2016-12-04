package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SOLIDUS;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the after attribute value quoted state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"tab" (U+0009)
 *   <dt>"LF" (U+000A)
 *   <dt>"FF" (U+000C)
 *   <dt>U+0020 SPACE
 *   <dd>Switch to the before attribute name state.
 *   <dt>"/" (U+002F)
 *   <dd>Switch to the self-closing start tag state.
 *   <dt>">" (U+003E)
 *   <dd>Switch to the data state. Emit the current tag token.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Parse error. Switch to the before attribute name state. Reconsume the character.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#AFTER_ATTRIBUTE_VALUE_QUOTED
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#after-attribute-value-(quoted)-state">8.2.4.42 After attribute value (quoted) state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class AfterAttributeValueQuotedState extends TokenizerState {
    
    public AfterAttributeValueQuotedState(final Tokenizer tokenizer) {
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
        case CHARACTER_TABULATION: // fall through
        case LINE_FEED: // fall through
        case FORM_FEED: // fall through
        case SPACE: {
            setTokenizerState(Tokenizer.State.BEFORE_ATTRIBUTE_NAME);
            return NOTHING;
        } // break;
        case SOLIDUS: {
            setTokenizerState(Tokenizer.State.SELF_CLOSING_START_TAG);
            return NOTHING;
        } // break;
        case GREATER_THAN_SIGN: {
            setTokenizerState(Tokenizer.State.DATA);
            final TagToken pendingToken = clearPendingTag();
            return one(pendingToken);
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected end-of-file after quoted attribute value.");
            }
        } // break;
        default: {
            if (isAllowParseErrors()) {
                unconsume(ch);
                setTokenizerState(Tokenizer.State.BEFORE_ATTRIBUTE_NAME);
                return NOTHING;
            } else {
                throw new ParseErrorException("Illegal character after quoted attribute value: " + (char) ch);
            }
        } // break;
        }
    }
    
}
