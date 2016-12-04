package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.DOCTYPEToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the doctype state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"tab" (U+0009)
 *   <dt>"LF" (U+000A)
 *   <dt>"FF" (U+000C)
 *   <dt>U+0020 SPACE
 *   <dd>Switch to the before DOCTYPE name state.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Create a new DOCTYPE token. Set its force-quirks flag to on. Emit the token. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Parse error. Switch to the before DOCTYPE name state. Reconsume the character.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#DOCTYPE
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#doctype-state">8.2.4.52 DOCTYPE state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class DOCTYPEState extends TokenizerState {
    
    public DOCTYPEState(final Tokenizer tokenizer) {
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
            setTokenizerState(Tokenizer.State.BEFORE_DOCTYPE_NAME);
            return NOTHING;
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                final DOCTYPEToken doctypeToken = new DOCTYPEToken();
                doctypeToken.setForceQuirks();
                return one(doctypeToken);
            } else {
                throw new ParseErrorException("Unexpected end-of-file in DOCTYPE.");
            }
        } // break;
        default: {
            if (isAllowParseErrors()) {
                unconsume(ch);
                setTokenizerState(Tokenizer.State.BEFORE_DOCTYPE_NAME);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected character in DOCTYPE: " + (char) ch);
            }
        } // break;
        }
    }
    
}
