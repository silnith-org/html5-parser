package org.silnith.parser.html5.grammar.mode;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;

/**
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#parsing-main-intr">8.2.5.4.14 The "in row" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InRowInsertionMode extends InsertionMode {

	public InRowInsertionMode(final Parser parser) {
		super(parser);
	}

	@Override
	public boolean insert(final Token token) {
		switch (token.getType()) {
		case START_TAG: {
			final StartTagToken startTagToken = (StartTagToken) token;
			final String tagName = startTagToken.getTagName();
			switch (tagName) {
			case "th": // fall through
			case "td": {
				clearStackBackToTableRowContext();
				insertHTMLElement(startTagToken);
				setInsertionMode(Parser.Mode.IN_CELL);
				insertMarkerAtEndOfListOfActiveFormattingElements();
				return TOKEN_HANDLED;
			} // break;
			case "caption": // fall through
			case "col": // fall through
			case "colgroup": // fall through
			case "tbody": // fall through
			case "tfoot": // fall through
			case "thead": // fall through
			case "tr": {
				if ( !hasParticularElementInTableScope("tr")) {
					if (isAllowParseErrors()) {
						return IGNORE_TOKEN;
					} else {
						throw new ParseErrorException("Expected to find tr element in table scope.");
					}
				}
				clearStackBackToTableRowContext();
				popCurrentNode();
				// verify popped was "tr"
				setInsertionMode(Parser.Mode.IN_TABLE_BODY);
				return REPROCESS_TOKEN;
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
			case "tr": {
				if ( !hasParticularElementInTableScope("tr")) {
					if (isAllowParseErrors()) {
						return IGNORE_TOKEN;
					} else {
						throw new ParseErrorException("Expected to find tr element in table scope.");
					}
				}
				clearStackBackToTableRowContext();
				popCurrentNode();
				// verify popped was "tr"
				setInsertionMode(Parser.Mode.IN_TABLE_BODY);
				return TOKEN_HANDLED;
			} // break;
			case "table": {
				if ( !hasParticularElementInTableScope("tr")) {
					if (isAllowParseErrors()) {
						return IGNORE_TOKEN;
					} else {
						throw new ParseErrorException("Expected to find tr element in table scope.");
					}
				}
				clearStackBackToTableRowContext();
				popCurrentNode();
				// verify popped was "tr"
				setInsertionMode(Parser.Mode.IN_TABLE_BODY);
				return REPROCESS_TOKEN;
			} // break;
			case "tbody": // fall through
			case "tfoot": // fall through
			case "thead": {
				if ( !hasParticularElementInTableScope(tagName)) {
					if (isAllowParseErrors()) {
						return IGNORE_TOKEN;
					} else {
						throw new ParseErrorException("Expected to find " + tagName + " element in table scope.");
					}
				}
				if ( !hasParticularElementInTableScope("tr")) {
					return IGNORE_TOKEN;
				}
				clearStackBackToTableRowContext();
				popCurrentNode();
				// verify popped "tr"
				setInsertionMode(Parser.Mode.IN_TABLE_BODY);
				return IGNORE_TOKEN;
			} // break;
			case "body": // fall through
			case "caption": // fall through
			case "col": // fall through
			case "colgroup": // fall through
			case "html": // fall through
			case "td": // fall through
			case "th": {
				if (isAllowParseErrors()) {
					return IGNORE_TOKEN;
				} else {
					throw new ParseErrorException("Unexpected end tag token in row: " + endTagToken);
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
		return processUsingRulesFor(Parser.Mode.IN_TABLE, token);
	}

}
