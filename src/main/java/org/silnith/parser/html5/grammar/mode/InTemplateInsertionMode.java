package org.silnith.parser.html5.grammar.mode;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the in template insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in template" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token
 *   <dt>A comment token
 *   <dt>A DOCTYPE token
 *   <dd>
 *     <p>Process the token using the rules for the "in body" insertion mode.
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "template", "title"
 *   <dt>An end tag whose tag name is "template"
 *   <dd>
 *     <p>Process the token using the rules for the "in head" insertion mode.
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "caption", "colgroup", "tbody", "tfoot", "thead"
 *   <dd>
 *     <p>Pop the current template insertion mode off the stack of template insertion modes.
 *     <p>Push "in table" onto the stack of template insertion modes so that it is the new current template insertion mode.
 *     <p>Switch the insertion mode to "in table", and reprocess the token.
 *   </dd>
 *   <dt>A start tag whose tag name is "col"
 *   <dd>
 *     <p>Pop the current template insertion mode off the stack of template insertion modes.
 *     <p>Push "in column group" onto the stack of template insertion modes so that it is the new current template insertion mode.
 *     <p>Switch the insertion mode to "in column group", and reprocess the token.
 *   </dd>
 *   <dt>A start tag whose tag name is "tr"
 *   <dd>
 *     <p>Pop the current template insertion mode off the stack of template insertion modes.
 *     <p>Push "in table body" onto the stack of template insertion modes so that it is the new current template insertion mode.
 *     <p>Switch the insertion mode to "in table body", and reprocess the token.
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "td", "th"
 *   <dd>
 *     <p>Pop the current template insertion mode off the stack of template insertion modes.
 *     <p>Push "in row" onto the stack of template insertion modes so that it is the new current template insertion mode.
 *     <p>Switch the insertion mode to "in row", and reprocess the token.
 *   </dd>
 *   <dt>Any other start tag
 *   <dd>
 *     <p>Pop the current template insertion mode off the stack of template insertion modes.
 *     <p>Push "in body" onto the stack of template insertion modes so that it is the new current template insertion mode.
 *     <p>Switch the insertion mode to "in body", and reprocess the token.
 *   </dd>
 *   <dt>Any other end tag
 *   <dd>
 *     <p>Parse error. Ignore the token.
 *   </dd>
 *   <dt>An end-of-file token
 *   <dd>
 *     <p>If there is no template element on the stack of open elements, then stop parsing. (fragment case)
 *     <p>Otherwise, this is a parse error.
 *     <p>Pop elements from the stack of open elements until a template element has been popped from the stack.
 *     <p>Clear the list of active formatting elements up to the last marker.
 *     <p>Pop the current template insertion mode off the stack of template insertion modes.
 *     <p>Reset the insertion mode appropriately.
 *     <p>Reprocess the token.
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_TEMPLATE
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intemplate">8.2.5.4.18 The "in template" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InTemplateInsertionMode extends InsertionMode {
    
    public InTemplateInsertionMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        switch (token.getType()) {
        case CHARACTER: // fall through
        case COMMENT: // fall through
        case DOCTYPE: {
            return processUsingRulesFor(Parser.Mode.IN_BODY, token);
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "base": // fall through
            case "basefont": // fall through
            case "bgsound": // fall through
            case "link": // fall through
            case "meta": // fall through
            case "noframes": // fall through
            case "script": // fall through
            case "style": // fall through
            case "template": // fall through
            case "title": {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, startTagToken);
            } // break;
            case "caption": // fall through
            case "colgroup": // fall through
            case "tbody": // fall through
            case "tfoot": // fall through
            case "thead": {
                popCurrentTemplateInsertionMode();
                pushOntoStackOfTemplateInsertionModes(Parser.Mode.IN_TABLE);
                setInsertionMode(Parser.Mode.IN_TABLE);
                return REPROCESS_TOKEN;
            } // break;
            case "col": {
                popCurrentTemplateInsertionMode();
                pushOntoStackOfTemplateInsertionModes(Parser.Mode.IN_COLUMN_GROUP);
                setInsertionMode(Parser.Mode.IN_COLUMN_GROUP);
                return REPROCESS_TOKEN;
            } // break;
            case "tr": {
                popCurrentTemplateInsertionMode();
                pushOntoStackOfTemplateInsertionModes(Parser.Mode.IN_TABLE_BODY);
                setInsertionMode(Parser.Mode.IN_TABLE_BODY);
                return REPROCESS_TOKEN;
            } // break;
            case "th": // fall through
            case "td": {
                popCurrentTemplateInsertionMode();
                pushOntoStackOfTemplateInsertionModes(Parser.Mode.IN_ROW);
                setInsertionMode(Parser.Mode.IN_ROW);
                return REPROCESS_TOKEN;
            } // break;
            default: {
                popCurrentTemplateInsertionMode();
                pushOntoStackOfTemplateInsertionModes(Parser.Mode.IN_BODY);
                setInsertionMode(Parser.Mode.IN_BODY);
                return REPROCESS_TOKEN;
            } // break;
            }
        } // break;
        case END_TAG: {
            final EndTagToken endTagToken = (EndTagToken) token;
            final String tagName = endTagToken.getTagName();
            switch (tagName) {
            case "template": {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, endTagToken);
            } // break;
            default: {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected end tag token in template: " + endTagToken);
                }
            } // break;
            }
        } // break;
        case EOF: {
            // if no template on stack of open elements, stop parsing
            if ( !isStackOfOpenElementsContains("template")) {
                stopParsing();
                return TOKEN_HANDLED;
            }
            if (isAllowParseErrors()) {
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, "template"));
                clearListOfActiveFormattingElementsUpToLastMarker();
                popCurrentTemplateInsertionMode();
                resetInsertionModeAppropriately();
                return REPROCESS_TOKEN;
            } else {
                throw new ParseErrorException("Unexpected end-of-file in template.");
            }
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private boolean anythingElse(final Token token) {
        throw new ParseErrorException();
    }
    
}
