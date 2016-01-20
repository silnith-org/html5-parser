package org.silnith.parser.html5.grammar.mode;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;

/**
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#parsing-main-inselectintable">8.2.5.4.17 The "in select in table" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InSelectInTableInsertionMode extends InsertionMode {

	public InSelectInTableInsertionMode(Parser parser) {
		super(parser);
	}

	@Override
	public boolean insert(final Token token) {
		switch (token.getType()) {
		case START_TAG: {
			final StartTagToken startTagToken = (StartTagToken) token;
			final String tagName = startTagToken.getTagName();
			switch (tagName) {
			case "caption": // fall through
			case "table": // fall through
			case "tbody": // fall through
			case "tfoot": // fall through
			case "thead": // fall through
			case "tr": // fall through
			case "td": // fall through
			case "th": {
				if (isAllowParseErrors()) {
					Element popped;
					do {
						popped = popCurrentNode();
					} while ( !isElementA(popped, "select"));
					resetInsertionModeAppropriately();
					return REPROCESS_TOKEN;
				} else {
					throw new ParseErrorException("Unexpected start tag token in select in table: " + startTagToken);
				}
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
			case "caption": // fall through
			case "table": // fall through
			case "tbody": // fall through
			case "tfoot": // fall through
			case "thead": // fall through
			case "tr": // fall through
			case "td": // fall through
			case "th": {
				if (isAllowParseErrors()) {
					if ( !hasParticularElementInTableScope(tagName)) {
						return IGNORE_TOKEN;
					}
					Element popped;
					do {
						popped = popCurrentNode();
					} while ( !isElementA(popped, "select"));
					resetInsertionModeAppropriately();
					return REPROCESS_TOKEN;
				} else {
					throw new ParseErrorException("Unexpected end tag token in select in table: " + endTagToken);
				}
			} // break;
			default: {
				return anythingElse(endTagToken);
			} // break;
			}
		} // break;
		default: {
			return anythingElse(token);
		} // break;
		}
	}

	private boolean anythingElse(final Token token) {
		return processUsingRulesFor(Parser.Mode.IN_SELECT, token);
	}

}
