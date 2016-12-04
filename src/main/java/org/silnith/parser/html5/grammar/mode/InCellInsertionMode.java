package org.silnith.parser.html5.grammar.mode;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the in cell insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in cell" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>An end tag whose tag name is one of: "td", "th"
 *   <dd>
 *     If the stack of open elements does not have an element in table scope that is an HTML element and with the same tag name as that of the token, then this is a parse error; ignore the token.
 *     <p>Otherwise:
 *     <p>Generate implied end tags.
 *     <p>Now, if the current node is not an HTML element with the same tag name as the token, then this is a parse error.
 *     <p>Pop elements from the stack of open elements stack until an HTML element with the same tag name as the token has been popped from the stack.
 *     <p>Clear the list of active formatting elements up to the last marker.
 *     <p>Switch the insertion mode to "in row".
 *   <dt>A start tag whose tag name is one of: "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr"
 *   <dd>
 *     If the stack of open elements does not have a td or th element in table scope, then this is a parse error; ignore the token. (fragment case)
 *     <p>Otherwise, close the cell (see below) and reprocess the token.
 *   <dt>An end tag whose tag name is one of: "body", "caption", "col", "colgroup", "html"
 *   <dd>Parse error. Ignore the token.
 *   <dt>An end tag whose tag name is one of: "table", "tbody", "tfoot", "thead", "tr"
 *   <dd>
 *     If the stack of open elements does not have an element in table scope that is an HTML element and with the same tag name as that of the token, then this is a parse error; ignore the token.
 *     <p>Otherwise, close the cell (see below) and reprocess the token.
 *   <dt>Anything else
 *   <dd>Process the token using the rules for the "in body" insertion mode.
 * </dl>
 * <p>Where the steps above say to close the cell, they mean to run the following algorithm:
 * <ol>
 *   <li>Generate implied end tags.
 *   <li>If the current node is not now a td element or a th element, then this is a parse error.
 *   <li>Pop elements from the stack of open elements stack until a td element or a th element has been popped from the stack.
 *   <li>Clear the list of active formatting elements up to the last marker.
 *   <li>Switch the insertion mode to "in row".
 * </ol>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_CELL
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intd">8.2.5.4.15 The "in cell" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InCellInsertionMode extends InsertionMode {
    
    public InCellInsertionMode(final Parser parser) {
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
            case "col": // fall through
            case "colgroup": // fall through
            case "tbody": // fall through
            case "td": // fall through
            case "tfoot": // fall through
            case "th": // fall through
            case "thead": // fall through
            case "tr": {
                if ( !hasParticularElementInTableScope("td") && !hasParticularElementInTableScope("th")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Expected to find table cell in table scope.");
                    }
                }
                closeCell();
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
            case "td": // fall through
            case "th": {
                if ( !hasParticularElementInTableScope(tagName)) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Expected to find " + tagName + " element in table scope.");
                    }
                }
                generateImpliedEndTags();
                if ( !isElementA(getCurrentNode(), tagName)) {
                    if (isAllowParseErrors()) {
                        // do nothing?
                    } else {
                        throw new ParseErrorException("Expected current node to be a " + tagName + ", instead it was: "
                                + getCurrentNode().getTagName());
                    }
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, tagName));
                clearListOfActiveFormattingElementsUpToLastMarker();
                setInsertionMode(Parser.Mode.IN_ROW);
                return TOKEN_HANDLED;
            } // break;
            case "body": // fall through
            case "caption": // fall through
            case "col": // fall through
            case "colgroup": // fall through
            case "html": {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected end tag token in table cell: " + endTagToken);
                }
            } // break;
            case "table": // fall through
            case "tbody": // fall through
            case "tfoot": // fall through
            case "thead": // fall through
            case "tr": {
                if ( !hasParticularElementInTableScope(tagName)) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Expected to find " + tagName + " element in table scope.");
                    }
                }
                closeCell();
                return REPROCESS_TOKEN;
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
        return processUsingRulesFor(Parser.Mode.IN_BODY, token);
    }
    
}
