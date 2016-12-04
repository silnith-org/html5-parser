package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.AMPERSAND;
import static org.silnith.parser.util.UnicodeCodePoints.APOSTROPHE;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.EQUALS_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.GRAVE_ACCENT;
import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.QUOTATION_MARK;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the attribute value unquoted state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"tab" (U+0009)
 *   <dt>"LF" (U+000A)
 *   <dt>"FF" (U+000C)
 *   <dt>U+0020 SPACE
 *   <dd>Switch to the before attribute name state.
 *   <dt>U+0026 AMPERSAND (&)
 *   <dd>Switch to the character reference in attribute value state, with the additional allowed character being ">" (U+003E).
 *   <dt>">" (U+003E)
 *   <dd>Switch to the data state. Emit the current tag token.
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Append a U+FFFD REPLACEMENT CHARACTER character to the current attribute's value.
 *   <dt>U+0022 QUOTATION MARK (")
 *   <dt>"'" (U+0027)
 *   <dt>"<" (U+003C)
 *   <dt>"=" (U+003D)
 *   <dt>"`" (U+0060)
 *   <dd>Parse error. Treat it as per the "anything else" entry below.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Append the current input character to the current attribute's value.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#ATTRIBUTE_VALUE_UNQUOTED
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#attribute-value-(unquoted)-state">8.2.4.40 Attribute value (unquoted) state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class AttributeValueUnquotedState extends TokenizerState {
    
    private final CharacterReferenceState characterReferenceState;
    
    public AttributeValueUnquotedState(final Tokenizer tokenizer) {
        super(tokenizer);
        this.characterReferenceState = new CharacterReferenceState(tokenizer, GREATER_THAN_SIGN);
    }
    
    @Override
    public int getMaxPushback() {
        return characterReferenceState.getMaxPushback();
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
        case AMPERSAND: {
            List<Token> characterReferences = characterReferenceState.getNextTokens();
            if (characterReferences == null || characterReferences.isEmpty()) {
                characterReferences = one(new CharacterToken(AMPERSAND));
            }
            
            for (final Token token : characterReferences) {
                final CharacterToken characterReference = (CharacterToken) token;
                appendToAttributeValue(characterReference.getCharacter());
            }
            
            return NOTHING;
        } // break;
        case GREATER_THAN_SIGN: {
            setTokenizerState(Tokenizer.State.DATA);
            final TagToken pendingToken = clearPendingTag();
            return one(pendingToken);
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                appendToAttributeValue(REPLACEMENT_CHARACTER);
                return NOTHING;
            } else {
                throw new ParseErrorException("Null character in unquoted attribute value.");
            }
        } // break;
        case QUOTATION_MARK: // fall through
        case APOSTROPHE: // fall through
        case LESS_THAN_SIGN: // fall through
        case EQUALS_SIGN: // fall through
        case GRAVE_ACCENT: {
            // grave accent
            if (isAllowParseErrors()) {
                return defaultCase(ch);
            } else {
                throw new ParseErrorException("Illegal character in unquoted attribute value: " + (char) ch);
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected end-of-file in unquoted attribute value.");
            }
        } // break;
        default: {
            return defaultCase(ch);
        } // break;
        }
    }
    
    private List<Token> defaultCase(final int ch) {
        appendToAttributeValue((char) ch);
        return NOTHING;
    }
    
}
