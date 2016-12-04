package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.DOCTYPEToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the before doctype name state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"tab" (U+0009)
 *   <dt>"LF" (U+000A)
 *   <dt>"FF" (U+000C)
 *   <dt>U+0020 SPACE
 *   <dd>Ignore the character.
 *   <dt>Uppercase ASCII letter
 *   <dd>Create a new DOCTYPE token. Set the token's name to the lowercase version of the current input character (add 0x0020 to the character's code point). Switch to the DOCTYPE name state.
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Create a new DOCTYPE token. Set the token's name to a U+FFFD REPLACEMENT CHARACTER character. Switch to the DOCTYPE name state.
 *   <dt>">" (U+003E)
 *   <dd>Parse error. Create a new DOCTYPE token. Set its force-quirks flag to on. Switch to the data state. Emit the token.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Create a new DOCTYPE token. Set its force-quirks flag to on. Emit the token. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Create a new DOCTYPE token. Set the token's name to the current input character. Switch to the DOCTYPE name state.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#BEFORE_DOCTYPE_NAME
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#before-doctype-name-state">8.2.4.53 Before DOCTYPE name state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class BeforeDOCTYPENameState extends TokenizerState {
    
    public BeforeDOCTYPENameState(final Tokenizer tokenizer) {
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
        case CHARACTER_TABULATION: // fall through
        case LINE_FEED: // fall through
        case FORM_FEED: // fall through
        case SPACE: {
            // ignore the character
            return NOTHING;
        } // break;
        case 'A': // fall through
        case 'B': // fall through
        case 'C': // fall through
        case 'D': // fall through
        case 'E': // fall through
        case 'F': // fall through
        case 'G': // fall through
        case 'H': // fall through
        case 'I': // fall through
        case 'J': // fall through
        case 'K': // fall through
        case 'L': // fall through
        case 'M': // fall through
        case 'N': // fall through
        case 'O': // fall through
        case 'P': // fall through
        case 'Q': // fall through
        case 'R': // fall through
        case 'S': // fall through
        case 'T': // fall through
        case 'U': // fall through
        case 'V': // fall through
        case 'W': // fall through
        case 'X': // fall through
        case 'Y': // fall through
        case 'Z': {
            createDOCTYPE(toLower((char) ch));
            setTokenizerState(Tokenizer.State.DOCTYPE_NAME);
            return NOTHING;
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                createDOCTYPE(REPLACEMENT_CHARACTER);
                setTokenizerState(Tokenizer.State.DOCTYPE_NAME);
                return NOTHING;
            } else {
                throw new ParseErrorException("Null character in DOCTYPE name.");
            }
        } // break;
        case GREATER_THAN_SIGN: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                setForceQuirks();
                final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
                return one(doctypeToken);
            } else {
                throw new ParseErrorException();
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                setForceQuirks();
                final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
                return one(doctypeToken);
            } else {
                throw new ParseErrorException();
            }
        } // break;
        default: {
            createDOCTYPE((char) ch);
            setTokenizerState(Tokenizer.State.DOCTYPE_NAME);
            return NOTHING;
        } // break;
        }
    }
    
}
