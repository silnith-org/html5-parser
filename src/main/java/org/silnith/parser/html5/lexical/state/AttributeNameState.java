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
 * @see <a
 *      href="http://www.w3.org/TR/html5/syntax.html#attribute-name-state">8.2.4.35
 *      Attribute name state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class AttributeNameState extends TokenizerState {
    
    public AttributeNameState(final Tokenizer tokenizer) {
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
            setTokenizerState(Tokenizer.State.AFTER_ATTRIBUTE_NAME);
            return NOTHING;
        } // break;
        case SOLIDUS: {
            setTokenizerState(Tokenizer.State.SELF_CLOSING_START_TAG);
            return NOTHING;
        } // break;
        case EQUALS_SIGN: {
            setTokenizerState(Tokenizer.State.BEFORE_ATTRIBUTE_VALUE);
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
            appendToAttributeName(toLower((char) ch));
            return NOTHING;
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                appendToAttributeName(REPLACEMENT_CHARACTER);
                return NOTHING;
            } else {
                throw new ParseErrorException(
                        "Null character in attribute name.");
            }
        } // break;
        case QUOTATION_MARK: // fall through
        case APOSTROPHE: // fall through
        case LESS_THAN_SIGN: {
            if (isAllowParseErrors()) {
                return defaultCase(ch);
            } else {
                throw new ParseErrorException(
                        "Illegal character in attribute name: " + (char) ch);
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                return NOTHING;
            } else {
                throw new ParseErrorException(
                        "Unexpected end-of-file in attribute name.");
            }
        } // break;
        default: {
            return defaultCase(ch);
        } // break;
        }
    }
    
    private List<Token> defaultCase(final int ch) {
        appendToAttributeName((char) ch);
        return NOTHING;
    }
    
}
