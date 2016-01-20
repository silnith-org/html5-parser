package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.AMPERSAND;
import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.EndOfFileToken;
import org.silnith.parser.html5.lexical.token.Token;

/**
 * Implements <a href="http://www.w3.org/TR/html5/syntax.html#data-state">8.2.4.1 Data state</a>.
 * 
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#data-state">8.2.4.1 Data state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class DataState extends TokenizerState {

	public DataState(final Tokenizer tokenizer) {
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
		case AMPERSAND: {
			setTokenizerState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
			return NOTHING;
		} // break;
		case LESS_THAN_SIGN: {
			setTokenizerState(Tokenizer.State.TAG_OPEN);
			return NOTHING;
		} // break;
		case NULL: {
			if (isAllowParseErrors()) {
				return one(new CharacterToken(NULL));
			} else {
				throw new ParseErrorException("Null character.");
			}
		} // break;
		case EOF: {
			return one(new EndOfFileToken());
		} // break;
		default: {
			assert Character.toChars(ch).length == 1;
			
			return one(new CharacterToken((char) ch));
		} // break;
		}
	}

}
