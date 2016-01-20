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
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#attribute-value-%28double-quoted%29-state">8.2.4.38 Attribute value (double-quoted) state</a>
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
