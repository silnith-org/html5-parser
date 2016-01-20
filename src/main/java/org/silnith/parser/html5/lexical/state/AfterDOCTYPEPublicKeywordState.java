package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.APOSTROPHE;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.QUOTATION_MARK;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.DOCTYPEToken;
import org.silnith.parser.html5.lexical.token.Token;

/**
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#after-doctype-public-keyword-state">8.2.4.56 After DOCTYPE public keyword state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class AfterDOCTYPEPublicKeywordState extends TokenizerState {

	public AfterDOCTYPEPublicKeywordState(final Tokenizer tokenizer) {
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
		case CHARACTER_TABULATION: // fall through
		case LINE_FEED: // fall through
		case FORM_FEED: // fall through
		case SPACE: {
			setTokenizerState(Tokenizer.State.BEFORE_DOCTYPE_PUBLIC_IDENTIFIER);
			return NOTHING;
		} // break;
		case QUOTATION_MARK: {
			if (isAllowParseErrors()) {
				createPublicIdentifier();
				setTokenizerState(Tokenizer.State.DOCTYPE_PUBLIC_IDENTIFIER_DOUBLE_QUOTED);
				return NOTHING;
			} else {
				throw new ParseErrorException("Unexpected quotation mark after DOCTYPE public keyword.");
			}
		} // break;
		case APOSTROPHE: {
			if (isAllowParseErrors()) {
				createPublicIdentifier();
				setTokenizerState(Tokenizer.State.DOCTYPE_PUBLIC_IDENTIFIER_SINGLE_QUOTED);
				return NOTHING;
			} else {
				throw new ParseErrorException("Unexpected apostrophe after DOCTYPE public keyword.");
			}
		} // break;
		case GREATER_THAN_SIGN: {
			if (isAllowParseErrors()) {
				setTokenizerState(Tokenizer.State.DATA);
				setForceQuirks();
				final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
				return one(doctypeToken);
			} else {
				throw new ParseErrorException("Unexpected '>' after DOCTYPE public keyword.");
			}
		} // break;
		case EOF: {
			if (isAllowParseErrors()) {
				setTokenizerState(Tokenizer.State.DATA);
				setForceQuirks();
				final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
				return one(doctypeToken);
			} else {
				throw new ParseErrorException("Unexpected end-of-file after DOCTYPE public keyword.");
			}
		} // break;
		default: {
			if (isAllowParseErrors()) {
				setTokenizerState(Tokenizer.State.BOGUS_DOCTYPE);
				setForceQuirks();
				return NOTHING;
			} else {
				throw new ParseErrorException("Unexpected character after DOCTYPE public keyword: " + (char) ch);
			}
		} // break;
		}
	}

}
