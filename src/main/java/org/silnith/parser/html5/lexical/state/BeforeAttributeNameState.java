package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.APOSTROPHE;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.EQUALS_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.QUOTATION_MARK;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;
import static org.silnith.parser.util.UnicodeCodePoints.SOLIDUS;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the before attribute name state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"tab" (U+0009)
 *   <dt>"LF" (U+000A)
 *   <dt>"FF" (U+000C)
 *   <dt>U+0020 SPACE
 *   <dd>Ignore the character.
 *   <dt>"/" (U+002F)
 *   <dd>Switch to the self-closing start tag state.
 *   <dt>">" (U+003E)
 *   <dd>Switch to the data state. Emit the current tag token.
 *   <dt>Uppercase ASCII letter
 *   <dd>Start a new attribute in the current tag token. Set that attribute's name to the lowercase version of the current input character (add 0x0020 to the character's code point), and its value to the empty string. Switch to the attribute name state.
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Start a new attribute in the current tag token. Set that attribute's name to a U+FFFD REPLACEMENT CHARACTER character, and its value to the empty string. Switch to the attribute name state.
 *   <dt>U+0022 QUOTATION MARK (")
 *   <dt>"'" (U+0027)
 *   <dt>"<" (U+003C)
 *   <dt>"=" (U+003D)
 *   <dd>Parse error. Treat it as per the "anything else" entry below.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Start a new attribute in the current tag token. Set that attribute's name to the current input character, and its value to the empty string. Switch to the attribute name state.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#BEFORE_ATTRIBUTE_NAME
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#before-attribute-name-state">8.2.4.34 Before attribute name state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class BeforeAttributeNameState extends TokenizerState {
    
    public BeforeAttributeNameState(final Tokenizer tokenizer) {
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
        case SOLIDUS: {
            setTokenizerState(Tokenizer.State.SELF_CLOSING_START_TAG);
            return NOTHING;
        } // break;
        case GREATER_THAN_SIGN: {
            setTokenizerState(Tokenizer.State.DATA);
            final TagToken pendingToken = clearPendingTag();
            return one(pendingToken);
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
            // start a new attribute in the current tag token
            createAttribute(toLower((char) ch));
            setTokenizerState(Tokenizer.State.ATTRIBUTE_NAME);
            return NOTHING;
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                createAttribute(REPLACEMENT_CHARACTER);
                setTokenizerState(Tokenizer.State.ATTRIBUTE_NAME);
                return NOTHING;
            } else {
                throw new ParseErrorException("Null character in before attribute name state.");
            }
        } // break;
        case QUOTATION_MARK: // fall through
        case APOSTROPHE: // fall through
        case LESS_THAN_SIGN: // fall through
        case EQUALS_SIGN: {
            if (isAllowParseErrors()) {
                return defaultCase(ch);
            } else {
                throw new ParseErrorException("Unexpected token before attribute name: " + (char) ch);
            }
        } // break;
        case EOF: {
            // parse error
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected end-of-file before attribute name.");
            }
        } // break;
        default: {
            return defaultCase(ch);
        } // break;
        }
    }
    
    private List<Token> defaultCase(final int ch) throws IOException {
        // start a new attribute in the current tag token
        createAttribute((char) ch);
        setTokenizerState(Tokenizer.State.ATTRIBUTE_NAME);
        return NOTHING;
    }
    
}
