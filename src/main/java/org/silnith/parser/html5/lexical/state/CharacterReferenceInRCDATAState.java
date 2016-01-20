package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.AMPERSAND;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;

/**
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#character-reference-in-rcdata-state">8.2.4.4 Character reference in RCDATA state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class CharacterReferenceInRCDATAState extends TokenizerState {

	private final CharacterReferenceState characterReferenceState;

	public CharacterReferenceInRCDATAState(final Tokenizer tokenizer) {
		super(tokenizer);
		this.characterReferenceState = new CharacterReferenceState(tokenizer);
	}

	@Override
	public int getMaxPushback() {
		return characterReferenceState.getMaxPushback();
	}

	@Override
	public List<Token> getNextTokens() throws IOException {
		setTokenizerState(Tokenizer.State.RCDATA);
		final List<Token> characterReference = characterReferenceState.getNextTokens();
		if (characterReference == null || characterReference.isEmpty()) {
			return one(new CharacterToken(AMPERSAND));
		} else {
			return characterReference;
		}
	}

}
