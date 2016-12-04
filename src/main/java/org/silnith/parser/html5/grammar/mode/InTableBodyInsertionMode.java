package org.silnith.parser.html5.grammar.mode;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the in table body insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in table body" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A start tag whose tag name is "tr"
 *   <dd>
 *     Clear the stack back to a table body context. (See below.)
 *     <p>Insert an HTML element for the token, then switch the insertion mode to "in row".
 *   <dt>A start tag whose tag name is one of: "th", "td"
 *   <dd>
 *     Parse error.
 *     <p>Clear the stack back to a table body context. (See below.)
 *     <p>Insert an HTML element for a "tr" start tag token with no attributes, then switch the insertion mode to "in row".
 *     <p>Reprocess the current token.
 *   <dt>An end tag whose tag name is one of: "tbody", "tfoot", "thead"
 *   <dd>
 *     If the stack of open elements does not have an element in table scope that is an HTML element and with the same tag name as the token, this is a parse error; ignore the token.
 *     <p>Otherwise:
 *     <p>Clear the stack back to a table body context. (See below.)
 *     <p>Pop the current node from the stack of open elements. Switch the insertion mode to "in table".
 *   <dt>A start tag whose tag name is one of: "caption", "col", "colgroup", "tbody", "tfoot", "thead"
 *   <dt>An end tag whose tag name is "table"
 *   <dd>
 *     If the stack of open elements does not have a tbody, thead, or tfoot element in table scope, this is a parse error; ignore the token.
 *     <p>Otherwise:
 *     <p>Clear the stack back to a table body context. (See below.)
 *     <p>Pop the current node from the stack of open elements. Switch the insertion mode to "in table".
 *     <p>Reprocess the token.
 *   <dt>An end tag whose tag name is one of: "body", "caption", "col", "colgroup", "html", "td", "th", "tr"
 *   <dd>Parse error. Ignore the token.
 *   <dt>Anything else
 *   <dd>Process the token using the rules for the "in table" insertion mode.
 * </dl>
 * <p>When the steps above require the UA to clear the stack back to a table body context, it means that the UA must, while the current node is not a tbody, tfoot, thead, template, or html element, pop elements from the stack of open elements.
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_TABLE_BODY
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intbody">8.2.5.4.13 The "in table body" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InTableBodyInsertionMode extends InsertionMode {
    
    public InTableBodyInsertionMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        switch (token.getType()) {
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "tr": {
                clearStackBackToTableBodyContext();
                insertHTMLElement(startTagToken);
                setInsertionMode(Parser.Mode.IN_ROW);
                return TOKEN_HANDLED;
            } // break;
            case "th": // fall through
            case "td": {
                if (isAllowParseErrors()) {
                    clearStackBackToTableBodyContext();
                    insertHTMLElement("tr");
                    setInsertionMode(Parser.Mode.IN_ROW);
                    return REPROCESS_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in table body: " + startTagToken);
                }
            } // break;
            case "caption": // fall through
            case "col": // fall through
            case "colgroup": // fall through
            case "tbody": // fall through
            case "tfoot": // fall through
            case "thead": {
                if ( !hasParticularElementInTableScope("tbody") && !hasParticularElementInTableScope("tfoot")
                        && !hasParticularElementInTableScope("thead")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException();
                    }
                }
                clearStackBackToTableBodyContext();
                popCurrentNode();
                setInsertionMode(Parser.Mode.IN_TABLE);
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
            case "tbody": // fall through
            case "tfoot": // fall through
            case "thead": {
                // verify stack of open elements has matching start tag in table
// scope
                if ( !hasParticularElementInTableScope(tagName)) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException(
                                "Unexpected end tag token with no matching element in table scope: " + endTagToken);
                    }
                }
                clearStackBackToTableBodyContext();
                popCurrentNode();
                setInsertionMode(Parser.Mode.IN_TABLE);
                return TOKEN_HANDLED;
            } // break;
            case "table": {
                if ( !hasParticularElementInTableScope("tbody") && !hasParticularElementInTableScope("tfoot")
                        && !hasParticularElementInTableScope("thead")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException();
                    }
                }
                clearStackBackToTableBodyContext();
                popCurrentNode();
                setInsertionMode(Parser.Mode.IN_TABLE);
                return REPROCESS_TOKEN;
            } // break;
            case "body": // fall through
            case "caption": // fall through
            case "col": // fall through
            case "colgroup": // fall through
            case "html": // fall through
            case "td": // fall through
            case "th": // fall through
            case "tr": {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected end tag token in table body: " + endTagToken);
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
