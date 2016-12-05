package org.silnith.parser.html5.grammar.mode;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the in row insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in row" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A start tag whose tag name is one of: "th", "td"
 *   <dd>
 *     <p>Clear the stack back to a table row context. (See below.)
 *     <p>Insert an HTML element for the token, then switch the insertion mode to "in cell".
 *     <p>Insert a marker at the end of the list of active formatting elements.
 *   </dd>
 *   <dt>An end tag whose tag name is "tr"
 *   <dd>
 *     <p>If the stack of open elements does not have a tr element in table scope, this is a parse error; ignore the token.
 *     <p>Otherwise:
 *     <p>Clear the stack back to a table row context. (See below.)
 *     <p>Pop the current node (which will be a tr element) from the stack of open elements. Switch the insertion mode to "in table body".
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr"
 *   <dt>An end tag whose tag name is "table"
 *   <dd>
 *     <p>If the stack of open elements does not have a tr element in table scope, this is a parse error; ignore the token.
 *     <p>Otherwise:
 *     <p>Clear the stack back to a table row context. (See below.)
 *     <p>Pop the current node (which will be a tr element) from the stack of open elements. Switch the insertion mode to "in table body".
 *     <p>Reprocess the token.
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "tbody", "tfoot", "thead"
 *   <dd>
 *     <p>If the stack of open elements does not have an element in table scope that is an HTML element and with the same tag name as the token, this is a parse error; ignore the token.
 *     <p>If the stack of open elements does not have a tr element in table scope, ignore the token.
 *     <p>Otherwise:
 *     <p>Clear the stack back to a table row context. (See below.)
 *     <p>Pop the current node (which will be a tr element) from the stack of open elements. Switch the insertion mode to "in table body".
 *     <p>Reprocess the token.
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "body", "caption", "col", "colgroup", "html", "td", "th"
 *   <dd>
 *     <p>Parse error. Ignore the token.
 *   </dd>
 *   <dt>Anything else
 *   <dd>
 *     <p>Process the token using the rules for the "in table" insertion mode.
 *   </dd>
 * </dl>
 * <p>When the steps above require the UA to clear the stack back to a table row context, it means that the UA must, while the current node is not a tr, template, or html element, pop elements from the stack of open elements.
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_ROW
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intr">8.2.5.4.14 The "in row" insertion mode</a>
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
