package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;

/**
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#parsing-main-inframeset">8.2.5.4.20 The "in frameset" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InFramesetInsertionMode extends InsertionMode {

	public InFramesetInsertionMode(final Parser parser) {
		super(parser);
	}

	@Override
	public boolean insert(final Token token) {
		switch (token.getType()) {
		case CHARACTER: {
			final CharacterToken characterToken = (CharacterToken) token;
			final char character = characterToken.getCharacter();
			switch (character) {
			case CHARACTER_TABULATION: // fall through
			case LINE_FEED: // fall through
			case FORM_FEED: // fall through
			case CARRIAGE_RETURN: // fall through
			case SPACE: {
				insertCharacter(character);
				return TOKEN_HANDLED;
			} // break;
			default: {
				return anythingElse(characterToken);
			} // break;
			}
		} // break;
		case COMMENT: {
			final CommentToken commentToken = (CommentToken) token;
			insertComment(commentToken);
			return TOKEN_HANDLED;
		} // break;
		case DOCTYPE: {
			if (isAllowParseErrors()) {
				return IGNORE_TOKEN;
			} else {
				throw new ParseErrorException("Unexpected DOCTYPE token in frameset: " + token);
			}
		} // break;
		case START_TAG: {
			final StartTagToken startTagToken = (StartTagToken) token;
			final String tagName = startTagToken.getTagName();
			switch (tagName) {
			case "html": {
				return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
			} // break;
			case "frameset": {
				insertHTMLElement(startTagToken);
				return TOKEN_HANDLED;
			} // break;
			case "frame": {
				insertHTMLElement(startTagToken);
				popCurrentNode();
				// acknowledge self-closing flag
				acknowledgeTokenSelfClosingFlag(startTagToken);
				return TOKEN_HANDLED;
			} // break;
			case "noframes": {
				return processUsingRulesFor(Parser.Mode.IN_HEAD, startTagToken);
			} // break;
			default: {
				return anythingElse(startTagToken);
			} // break;
			}
		} // break;
		case END_TAG: {
			final EndTagToken endTagToken = (EndTagToken) token;
			final String tagName = endTagToken.getTagName();
			switch (tagName) {
			case "frameset": {
				if (isElementA(getCurrentNode(), "html")) {
					if (isAllowParseErrors()) {
						return IGNORE_TOKEN;
					} else {
						throw new ParseErrorException("Unexpected end tag token in document fragment: " + endTagToken);
					}
				}
				popCurrentNode();
				if ( !isHTMLFragmentParsingAlgorithm() && !isElementA(getCurrentNode(), "frameset")) {
					setInsertionMode(Parser.Mode.AFTER_FRAMESET);
				}
				return TOKEN_HANDLED;
			} // break;
			default: {
				return anythingElse(endTagToken);
			} // break;
			}
		} // break;
		case EOF: {
			if ( !isElementA(getCurrentNode(), "html")) {
				if (isAllowParseErrors()) {
					// do nothing?
				} else {
					throw new ParseErrorException("Unexpected end-of-file with unclosed elements.");
				}
			}
			stopParsing();
			return TOKEN_HANDLED;
		} // break;
		default: {
			return anythingElse(token);
		} // break;
		}
	}

	private boolean anythingElse(final Token token) {
		if (isAllowParseErrors()) {
			return IGNORE_TOKEN;
		} else {
			throw new ParseErrorException("Unexpected token in frameset: " + token);
		}
	}

}
