package org.silnith.parser.html5.grammar.mode;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the in table insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in table" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token, if the current node is table, tbody, tfoot, thead, or tr element
 *   <dd>
 *     <p>Let the pending table character tokens be an empty list of tokens.
 *     <p>Let the original insertion mode be the current insertion mode.
 *     <p>Switch the insertion mode to "in table text" and reprocess the token.
 *   </dd>
 *   <dt>A comment token
 *   <dd>
 *     <p>Insert a comment.
 *   </dd>
 *   <dt>A DOCTYPE token
 *   <dd>
 *     <p>Parse error. Ignore the token.
 *   </dd>
 *   <dt>A start tag whose tag name is "caption"
 *   <dd>
 *     <p>Clear the stack back to a table context. (See below.)
 *     <p>Insert a marker at the end of the list of active formatting elements.
 *     <p>Insert an HTML element for the token, then switch the insertion mode to "in caption".
 *   </dd>
 *   <dt>A start tag whose tag name is "colgroup"
 *   <dd>
 *     <p>Clear the stack back to a table context. (See below.)
 *     <p>Insert an HTML element for the token, then switch the insertion mode to "in column group".
 *   </dd>
 *   <dt>A start tag whose tag name is "col"
 *   <dd>
 *     <p>Clear the stack back to a table context. (See below.)
 *     <p>Insert an HTML element for a "colgroup" start tag token with no attributes, then switch the insertion mode to "in column group".
 *     <p>Reprocess the current token.
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "tbody", "tfoot", "thead"
 *   <dd>
 *     <p>Clear the stack back to a table context. (See below.)
 *     <p>Insert an HTML element for the token, then switch the insertion mode to "in table body".
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "td", "th", "tr"
 *   <dd>
 *     <p>Clear the stack back to a table context. (See below.)
 *     <p>Insert an HTML element for a "tbody" start tag token with no attributes, then switch the insertion mode to "in table body".
 *     <p>Reprocess the current token.
 *   </dd>
 *   <dt>A start tag whose tag name is "table"
 *   <dd>
 *     <p>Parse error.
 *     <p>If the stack of open elements does not have a table element in table scope, ignore the token.
 *     <p>Otherwise:
 *     <p>Pop elements from this stack until a table element has been popped from the stack.
 *     <p>Reset the insertion mode appropriately.
 *     <p>Reprocess the token.
 *   </dd>
 *   <dt>An end tag whose tag name is "table"
 *   <dd>
 *     <p>If the stack of open elements does not have a table element in table scope, this is a parse error; ignore the token.
 *     <p>Otherwise:
 *     <p>Pop elements from this stack until a table element has been popped from the stack.
 *     <p>Reset the insertion mode appropriately.
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr"
 *   <dd>
 *     <p>Parse error. Ignore the token.
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "style", "script", "template"
 *   <dt>An end tag whose tag name is "template"
 *   <dd>
 *     <p>Process the token using the rules for the "in head" insertion mode.
 *   </dd>
 *   <dt>A start tag whose tag name is "input"
 *   <dd>
 *     <p>If the token does not have an attribute with the name "type", or if it does, but that attribute's value is not an ASCII case-insensitive match for the string "hidden", then: act as described in the "anything else" entry below.
 *     <p>Otherwise:
 *     <p>Parse error.
 *     <p>Insert an HTML element for the token.
 *     <p>Pop that input element off the stack of open elements.
 *     <p>Acknowledge the token's self-closing flag, if it is set.
 *   </dd>
 *   <dt>A start tag whose tag name is "form"
 *   <dd>
 *     <p>Parse error.
 *     <p>If there is a template element on the stack of open elements, or if the form element pointer is not null, ignore the token.
 *     <p>Otherwise:
 *     <p>Insert an HTML element for the token, and set the form element pointer to point to the element created.
 *     <p>Pop that form element off the stack of open elements.
 *   </dd>
 *   <dt>An end-of-file token
 *   <dd>
 *     <p>Process the token using the rules for the "in body" insertion mode.
 *   </dd>
 *   <dt>Anything else
 *   <dd>
 *     <p>Parse error. Enable foster parenting, process the token using the rules for the "in body" insertion mode, and then disable foster parenting.
 *   </dd>
 * </dl>
 * <p>When the steps above require the UA to clear the stack back to a table context, it means that the UA must, while the current node is not a table, template, or html element, pop elements from the stack of open elements.
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_TABLE
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intable">8.2.5.4.9 The "in table" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InTableInsertionMode extends InsertionMode {
    
    public InTableInsertionMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        switch (token.getType()) {
        case CHARACTER: {
            final CharacterToken characterToken = (CharacterToken) token;
            final Element currentNode = getCurrentNode();
            switch (currentNode.getTagName()) {
            case "table": // fall through
            case "tbody": // fall through
            case "tfoot": // fall through
            case "thead": // fall through
            case "tr": {
                setPendingTableCharacterTokens();
                setOriginalInsertionMode(getInsertionMode());
                setInsertionMode(Parser.Mode.IN_TABLE_TEXT);
                return REPROCESS_TOKEN;
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
                throw new ParseErrorException("Unexpected DOCTYPE token in table: " + token);
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "caption": {
                clearStackBackToTableContext();
                insertMarkerAtEndOfListOfActiveFormattingElements();
                insertHTMLElement(startTagToken);
                setInsertionMode(Parser.Mode.IN_CAPTION);
                return TOKEN_HANDLED;
            } // break;
            case "colgroup": {
                clearStackBackToTableContext();
                insertHTMLElement(startTagToken);
                setInsertionMode(Parser.Mode.IN_COLUMN_GROUP);
                return TOKEN_HANDLED;
            } // break;
            case "col": {
                clearStackBackToTableContext();
                insertHTMLElement("colgroup");
                setInsertionMode(Parser.Mode.IN_COLUMN_GROUP);
                return REPROCESS_TOKEN;
            } // break;
            case "tbody": // fall through
            case "tfoot": // fall through
            case "thead": {
                clearStackBackToTableContext();
                insertHTMLElement(startTagToken);
                setInsertionMode(Parser.Mode.IN_TABLE_BODY);
                return TOKEN_HANDLED;
            } // break;
            case "td": // fall through
            case "th": // fall through
            case "tr": {
                clearStackBackToTableContext();
                insertHTMLElement("tbody");
                setInsertionMode(Parser.Mode.IN_TABLE_BODY);
                return REPROCESS_TOKEN;
            } // break;
            case "table": {
                if (isAllowParseErrors()) {
                    if ( !hasParticularElementInTableScope("table")) {
                        return IGNORE_TOKEN;
                    }
                    Element popped;
                    do {
                        popped = popCurrentNode();
                    } while ( !isElementA(popped, "table"));
                    resetInsertionModeAppropriately();
                    return REPROCESS_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in table: " + startTagToken);
                }
            } // break;
            case "style": // fall through
            case "script": // fall through
            case "template": {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, startTagToken);
            } // break;
            case "input": {
                final TagToken.Attribute typeAttribute = getAttributeNamed(startTagToken, "type");
                if (typeAttribute == null || typeAttribute.getValue().equalsIgnoreCase("hidden")) {
                    return anythingElse(startTagToken);
                }
                if (isAllowParseErrors()) {
                    insertHTMLElement(startTagToken);
                    popCurrentNode();
                    acknowledgeTokenSelfClosingFlag(startTagToken);
                    return TOKEN_HANDLED;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in table: " + startTagToken);
                }
            } // break;
            case "form": {
                if (isAllowParseErrors()) {
                    if (isStackOfOpenElementsContains("template") || getFormElementPointer() != null) {
                        return IGNORE_TOKEN;
                    }
                    final Element formElement = insertHTMLElement(startTagToken);
                    setFormElementPointer(formElement);
                    popCurrentNode();
                    return TOKEN_HANDLED;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in table: " + startTagToken);
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
            case "table": {
                if ( !hasParticularElementInTableScope("table")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException(
                                "End tag token for table with no matching start tag in table scope.");
                    }
                }
                Element poppedElement;
                do {
                    poppedElement = popCurrentNode();
                } while ( !isElementA(poppedElement, "table"));
                resetInsertionModeAppropriately();
                return TOKEN_HANDLED;
            } // break;
            case "body": // fall through
            case "caption": // fall through
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
                    throw new ParseErrorException("Unexpected end tag token in table: " + endTagToken);
                }
            } // break;
            case "template": {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, endTagToken);
            } // break;
            default: {
                return anythingElse(endTagToken);
            } // break;
            }
        } // break;
        case EOF: {
            return processUsingRulesFor(Parser.Mode.IN_BODY, token);
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private boolean anythingElse(final Token token) {
        if (isAllowParseErrors()) {
            enableFosterParenting();
            final boolean returnValue = processUsingRulesFor(Parser.Mode.IN_BODY, token);
            disableFosterParenting();
            return returnValue;
        } else {
            throw new ParseErrorException("Unexpected token in table: " + token);
        }
    }
    
}
