package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.HYPHEN_MINUS;
import static org.silnith.parser.util.UnicodeCodePoints.LESS_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;

/**
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#script-data-double-escaped-dash-state">8.2.4.30 Script data double escaped dash state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class ScriptDataDoubleEscapedDashState extends TokenizerState {

	public ScriptDataDoubleEscapedDashState(final Tokenizer tokenizer) {
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
		case HYPHEN_MINUS: {
			setTokenizerState(Tokenizer.State.SCRIPT_DATA_DOUBLE_ESCAPED_DASH_DASH);
			return one(new CharacterToken(HYPHEN_MINUS));
		} // break;
		case LESS_THAN_SIGN: {
			setTokenizerState(Tokenizer.State.SCRIPT_DATA_DOUBLE_ESCAPED_LESS_THAN_SIGN);
			return one(new CharacterToken(LESS_THAN_SIGN));
		} // break;
		case NULL: {
			if (isAllowParseErrors()) {
				setTokenizerState(Tokenizer.State.SCRIPT_DATA_DOUBLE_ESCAPED);
				return one(new CharacterToken(REPLACEMENT_CHARACTER));
			} else {
				throw new ParseErrorException("Null token in script data double escaped dash state.");
			}
		} // break;
		case EOF: {
			if (isAllowParseErrors()) {
				setTokenizerState(Tokenizer.State.DATA);
				return NOTHING;
			} else {
				throw new ParseErrorException("Unexpected end-of-file in script data double escaped dash state.");
			}
		} // break;
		default: {
			setTokenizerState(Tokenizer.State.SCRIPT_DATA_DOUBLE_ESCAPED);
			return one(new CharacterToken((char) ch));
		} // break;
		}
	}

}
