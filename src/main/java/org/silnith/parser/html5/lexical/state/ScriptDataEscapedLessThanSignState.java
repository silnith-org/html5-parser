package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.SOLIDUS;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the script data escaped less than sign state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"/" (U+002F)
 *   <dd>Set the temporary buffer to the empty string. Switch to the script data escaped end tag open state.
 *   <dt>Uppercase ASCII letter
 *   <dd>Set the temporary buffer to the empty string. Append the lowercase version of the current input character (add 0x0020 to the character's code point) to the temporary buffer. Switch to the script data double escape start state. Emit a U+003C LESS-THAN SIGN character token and the current input character as a character token.
 *   <dt>Lowercase ASCII letter
 *   <dd>Set the temporary buffer to the empty string. Append the current input character to the temporary buffer. Switch to the script data double escape start state. Emit a U+003C LESS-THAN SIGN character token and the current input character as a character token.
 *   <dt>Anything else
 *   <dd>Switch to the script data escaped state. Emit a U+003C LESS-THAN SIGN character token. Reconsume the current input character.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#SCRIPT_DATA_ESCAPED_LESS_THAN_SIGN
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#script-data-escaped-less-than-sign-state">8.2.4.25 Script data escaped less-than sign state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class ScriptDataEscapedLessThanSignState extends TokenizerState {
    
    public ScriptDataEscapedLessThanSignState(final Tokenizer tokenizer) {
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
        case SOLIDUS: {
            createTemporaryBuffer();
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_ESCAPED_END_TAG_OPEN);
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
            createTemporaryBuffer();
            appendToTemporaryBuffer(toLower((char) ch));
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_DOUBLE_ESCAPE_START);
            return CharacterToken.toTokens(LESS_THAN_SIGN, (char) ch);
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
            createTemporaryBuffer();
            appendToTemporaryBuffer((char) ch);
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_DOUBLE_ESCAPE_START);
            return CharacterToken.toTokens(LESS_THAN_SIGN, (char) ch);
        } // break;
        default: {
            unconsume(ch);
            setTokenizerState(Tokenizer.State.SCRIPT_DATA_ESCAPED);
            return one(new CharacterToken(LESS_THAN_SIGN));
        } // break;
        }
    }
    
}
