package org.silnith.parser.html5.grammar.mode;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the rules for the in caption insertion mode.
 * <p>
 * When the user agent is to apply the rules for the "in caption" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>An end tag whose tag name is "caption"
 *   <dd>
 *     <p>If the stack of open elements does not have a caption element in table scope, this is a parse error; ignore the token. (fragment case)
 *     <p>Otherwise:
 *     <p>Generate implied end tags.
 *     <p>Now, if the current node is not a caption element, then this is a parse error.
 *     <p>Pop elements from this stack until a caption element has been popped from the stack.
 *     <p>Clear the list of active formatting elements up to the last marker.
 *     <p>Switch the insertion mode to "in table".
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr"
 *   <dt>An end tag whose tag name is "table"
 *   <dd>
 *     <p>Parse error.
 *     <p>If the stack of open elements does not have a caption element in table scope, ignore the token. (fragment case)
 *     <p>Otherwise:
 *     <p>Pop elements from this stack until a caption element has been popped from the stack.
 *     <p>Clear the list of active formatting elements up to the last marker.
 *     <p>Switch the insertion mode to "in table".
 *     <p>Reprocess the token.
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr"
 *   <dd>
 *     <p>Parse error. Ignore the token.
 *   </dd>
 *   <dt>Anything else
 *   <dd>
 *     <p>Process the token using the rules for the "in body" insertion mode.
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_CAPTION
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-incaption">8.2.5.4.11 The "in caption" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InCaptionInsertionMode extends InsertionMode {
    
    public InCaptionInsertionMode(final Parser parser) {
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
                if ( !hasParticularElementInTableScope("caption")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException(
                                "Unexpected start tag token outside of caption element in table scope: "
                                        + startTagToken);
                    }
                }
                generateImpliedEndTags();
                if (isElementA(getCurrentNode(), "caption")) {
                    if (isAllowParseErrors()) {
                        // do nothing?
                    } else {
                        throw new ParseErrorException("Expected current node to be a caption element.");
                    }
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, "caption"));
                clearListOfActiveFormattingElementsUpToLastMarker();
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
            case "caption": {
                if ( !hasParticularElementInTableScope("caption")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException(
                                "Unexpected end tag token outside of caption element in table scope: " + endTagToken);
                    }
                }
                generateImpliedEndTags();
                if (isElementA(getCurrentNode(), "caption")) {
                    if (isAllowParseErrors()) {
                        // do nothing?
                    } else {
                        throw new ParseErrorException("Expected current node to be a caption element.");
                    }
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, "caption"));
                clearListOfActiveFormattingElementsUpToLastMarker();
                setInsertionMode(Parser.Mode.IN_TABLE);
                return TOKEN_HANDLED;
            } // break;
            case "table": {
                if ( !hasParticularElementInTableScope("caption")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException(
                                "Unexpected end tag token outside of caption element in table scope: " + endTagToken);
                    }
                }
                generateImpliedEndTags();
                if (isElementA(getCurrentNode(), "caption")) {
                    if (isAllowParseErrors()) {
                        // do nothing?
                    } else {
                        throw new ParseErrorException("Expected current node to be a caption element.");
                    }
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, "caption"));
                clearListOfActiveFormattingElementsUpToLastMarker();
                setInsertionMode(Parser.Mode.IN_TABLE);
                return REPROCESS_TOKEN;
            } // break;
            case "body": // fall through
            case "col": // fall through
            case "colgroup": // fall through
            case "html": // fall through
            case "tbody": // fall through
            case "td": // fall through
            case "tfoot": // fall through
            case "th": // fall through
            case "thead": // fall through
            case "tr": {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected end tag token in caption: " + endTagToken);
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
        return processUsingRulesFor(Parser.Mode.IN_BODY, token);
    }
    
}
