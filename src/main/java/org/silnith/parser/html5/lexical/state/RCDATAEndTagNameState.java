package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SOLIDUS;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the rcdata end tag name state logic.
 * <p>
 * This state assumes that there is a pending token in the tokenizer, and that
 * it is a {@link org.silnith.parser.html5.lexical.token.StartTagToken}.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"tab" (U+0009)
 *   <dt>"LF" (U+000A)
 *   <dt>"FF" (U+000C)
 *   <dt>U+0020 SPACE
 *   <dd>If the current end tag token is an appropriate end tag token, then switch to the before attribute name state. Otherwise, treat it as per the "anything else" entry below.
 *   <dt>"/" (U+002F)
 *   <dd>If the current end tag token is an appropriate end tag token, then switch to the self-closing start tag state. Otherwise, treat it as per the "anything else" entry below.
 *   <dt>">" (U+003E)
 *   <dd>If the current end tag token is an appropriate end tag token, then switch to the data state and emit the current tag token. Otherwise, treat it as per the "anything else" entry below.
 *   <dt>Uppercase ASCII letter
 *   <dd>Append the lowercase version of the current input character (add 0x0020 to the character's code point) to the current tag token's tag name. Append the current input character to the temporary buffer.
 *   <dt>Lowercase ASCII letter
 *   <dd>Append the current input character to the current tag token's tag name. Append the current input character to the temporary buffer.
 *   <dt>Anything else
 *   <dd>Switch to the RCDATA state. Emit a U+003C LESS-THAN SIGN character token, a U+002F SOLIDUS character token, and a character token for each of the characters in the temporary buffer (in the order they were added to the buffer). Reconsume the current input character.
 * </dl>
 *
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#RCDATA_END_TAG_NAME
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#rcdata-end-tag-name-state">8.2.4.13 RCDATA end tag name state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class RCDATAEndTagNameState extends TokenizerState {
    
    public RCDATAEndTagNameState(final Tokenizer tokenizer) {
        super(tokenizer);
    }
    
    @Override
    public int getMaxPushback() {
        return 1;
    }
    
    @Override
    public List<Token> getNextTokens() throws IOException {
        assert hasPendingTag();
        assert isPendingEndTag();
        
        final int ch = consume();
        switch (ch) {
        case CHARACTER_TABULATION: // fall through
        case LINE_FEED: // fall through
        case FORM_FEED: // fall through
        case SPACE: {
            if (isAppropriateEndTag()) {
                setTokenizerState(Tokenizer.State.BEFORE_ATTRIBUTE_NAME);
                return NOTHING;
            } else {
                return defaultCase(ch);
            }
        } // break;
        case SOLIDUS: {
            if (isAppropriateEndTag()) {
                setTokenizerState(Tokenizer.State.SELF_CLOSING_START_TAG);
                return NOTHING;
            } else {
                return defaultCase(ch);
            }
        } // break;
        case GREATER_THAN_SIGN: {
            if (isAppropriateEndTag()) {
                setTokenizerState(Tokenizer.State.DATA);
                final TagToken pendingToken = clearPendingTag();
                return one(pendingToken);
            } else {
                return defaultCase(ch);
            }
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
            appendToTagName(toLower((char) ch));
            appendToTemporaryBuffer((char) ch);
            return NOTHING;
        } // break;
        case 'a': // fall through
        case 'b': // fall through
        case 'c': // fall through
        case 'd': // fall through
        case 'e': // fall through
        case 'f': // fall through
        case 'g': // fall through
        case 'h': // fall through
        case 'i': // fall through
        case 'j': // fall through
        case 'k': // fall through
        case 'l': // fall through
        case 'm': // fall through
        case 'n': // fall through
        case 'o': // fall through
        case 'p': // fall through
        case 'q': // fall through
        case 'r': // fall through
        case 's': // fall through
        case 't': // fall through
        case 'u': // fall through
        case 'v': // fall through
        case 'w': // fall through
        case 'x': // fall through
        case 'y': // fall through
        case 'z': {
            appendToTagName((char) ch);
            appendToTemporaryBuffer((char) ch);
            return NOTHING;
        } // break;
        default: {
            return defaultCase(ch);
        } // break;
        }
    }
    
    private List<Token> defaultCase(final int ch) throws IOException {
        unconsume(ch);
        setTokenizerState(Tokenizer.State.RCDATA);
        final String content = "</" + clearTemporaryBuffer();
        return CharacterToken.toTokens(content);
    }
    
}
