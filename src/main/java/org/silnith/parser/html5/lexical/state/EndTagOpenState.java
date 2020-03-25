package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.SOLIDUS;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the end tag open state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>Uppercase ASCII letter
 *   <dd>Create a new end tag token, set its tag name to the lowercase version of the current input character (add 0x0020 to the character's code point), then switch to the tag name state. (Don't emit the token yet; further details will be filled in before it is emitted.)
 *   <dt>Lowercase ASCII letter
 *   <dd>Create a new end tag token, set its tag name to the current input character, then switch to the tag name state. (Don't emit the token yet; further details will be filled in before it is emitted.)
 *   <dt>"&gt;" (U+003E)
 *   <dd>Parse error. Switch to the data state.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Emit a U+003C LESS-THAN SIGN character token and a U+002F SOLIDUS character token. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Parse error. Switch to the bogus comment state.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#END_TAG_OPEN
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#end-tag-open-state">8.2.4.9 End tag open state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class EndTagOpenState extends TokenizerState {
    
    public EndTagOpenState(final Tokenizer tokenizer) {
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
            setPendingTag(new EndTagToken());
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
            setPendingTag(new EndTagToken());
            appendToTagName((char) ch);
            setTokenizerState(Tokenizer.State.TAG_NAME);
            return NOTHING;
        } // break;
        case GREATER_THAN_SIGN: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected '>' while parsing end tag.");
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                return CharacterToken.toTokens(LESS_THAN_SIGN, SOLIDUS);
            } else {
                throw new ParseErrorException("End-of-file reached while parsing end tag.");
            }
        } // break;
        default: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.BOGUS_COMMENT);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected character while parsing end tag: '" + (char) ch + "'.");
            }
        } // break;
        }
    }
    
}
