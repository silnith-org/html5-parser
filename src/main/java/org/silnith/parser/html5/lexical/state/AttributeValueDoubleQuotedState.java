package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.AMPERSAND;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.QUOTATION_MARK;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the attribute value double quoted state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>U+0022 QUOTATION MARK (")
 *   <dd>Switch to the after attribute value (quoted) state.
 *   <dt>U+0026 AMPERSAND (&amp;)
 *   <dd>Switch to the character reference in attribute value state, with the additional allowed character being U+0022 QUOTATION MARK (").
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Append a U+FFFD REPLACEMENT CHARACTER character to the current attribute's value.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Append the current input character to the current attribute's value.
 * </dl>
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#ATTRIBUTE_VALUE_DOUBLE_QUOTED
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#attribute-value-(double-quoted)-state">8.2.4.38 Attribute value (double-quoted) state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class AttributeValueDoubleQuotedState extends TokenizerState {
    
    private final CharacterReferenceState characterReferenceState;
    
    public AttributeValueDoubleQuotedState(final Tokenizer tokenizer) {
        super(tokenizer);
        this.characterReferenceState = new CharacterReferenceState(tokenizer, QUOTATION_MARK);
    }
    
    @Override
    public int getMaxPushback() {
        return characterReferenceState.getMaxPushback();
    }
    
    @Override
    public List<Token> getNextTokens() throws IOException {
        final int ch = consume();
        switch (ch) {
        case QUOTATION_MARK: {
            setTokenizerState(Tokenizer.State.AFTER_ATTRIBUTE_VALUE_QUOTED);
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
        case NULL: {
            if (isAllowParseErrors()) {
                appendToAttributeValue(REPLACEMENT_CHARACTER);
                return NOTHING;
            } else {
                throw new ParseErrorException("Null character in double-quoted attribute value.");
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected end-of-file in double-quoted attribute value.");
            }
        } // break;
        default: {
            appendToAttributeValue((char) ch);
            return NOTHING;
        } // break;
        }
    }
    
}
