package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.EXCLAMATION_MARK;
import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.QUESTION_MARK;
import static org.silnith.parser.util.UnicodeCodePoints.SOLIDUS;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the tag open state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"!" (U+0021)
 *   <dd>Switch to the markup declaration open state.
 *   <dt>"/" (U+002F)
 *   <dd>Switch to the end tag open state.
 *   <dt>Uppercase ASCII letter
 *   <dd>Create a new start tag token, set its tag name to the lowercase version of the current input character (add 0x0020 to the character's code point), then switch to the tag name state. (Don't emit the token yet; further details will be filled in before it is emitted.)
 *   <dt>Lowercase ASCII letter
 *   <dd>Create a new start tag token, set its tag name to the current input character, then switch to the tag name state. (Don't emit the token yet; further details will be filled in before it is emitted.)
 *   <dt>"?" (U+003F)
 *   <dd>Parse error. Switch to the bogus comment state.
 *   <dt>Anything else
 *   <dd>Parse error. Switch to the data state. Emit a U+003C LESS-THAN SIGN character token. Reconsume the current input character.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#TAG_OPEN
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#tag-open-state">8.2.4.8 Tag open state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class TagOpenState extends TokenizerState {
    
    public TagOpenState(final Tokenizer tokenizer) {
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
        case EXCLAMATION_MARK: {
            setTokenizerState(Tokenizer.State.MARKUP_DECLARATION_OPEN);
            return NOTHING;
        } // break;
        case SOLIDUS: {
            setTokenizerState(Tokenizer.State.END_TAG_OPEN);
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
            setPendingTag(new StartTagToken());
            appendToTagName(toLower((char) ch));
            setTokenizerState(Tokenizer.State.TAG_NAME);
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
            setPendingTag(new StartTagToken());
            appendToTagName((char) ch);
            setTokenizerState(Tokenizer.State.TAG_NAME);
            return NOTHING;
        } // break;
        case QUESTION_MARK: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.BOGUS_COMMENT);
                return NOTHING;
            } else {
                throw new ParseErrorException("Illegal question mark character in tag open.");
            }
        } // break;
        default: {
            if (isAllowParseErrors()) {
                unconsume(ch);
                setTokenizerState(Tokenizer.State.DATA);
                return one(new CharacterToken(LESS_THAN_SIGN));
            } else {
                throw new ParseErrorException("Illegal character in tag open: '" + (char) ch + "'.");
            }
        } // break;
        }
    }
    
}
