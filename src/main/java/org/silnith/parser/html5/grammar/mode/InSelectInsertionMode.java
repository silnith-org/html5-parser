package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.NULL;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the in select insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in select" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token that is U+0000 NULL
 *   <dd>
 *     <p>Parse error. Ignore the token.
 *   </dd>
 *   <dt>Any other character token
 *   <dd>
 *     <p>Insert the token's character.
 *   </dd>
 *   <dt>A comment token
 *   <dd>
 *     <p>Insert a comment.
 *   </dd>
 *   <dt>A DOCTYPE token
 *   <dd>
 *     <p>Parse error. Ignore the token.
 *   </dd>
 *   <dt>A start tag whose tag name is "html"
 *   <dd>
 *     <p>Process the token using the rules for the "in body" insertion mode.
 *   </dd>
 *   <dt>A start tag whose tag name is "option"
 *   <dd>
 *     <p>If the current node is an option element, pop that node from the stack of open elements.
 *     <p>Insert an HTML element for the token.
 *   </dd>
 *   <dt>A start tag whose tag name is "optgroup"
 *   <dd>
 *     <p>If the current node is an option element, pop that node from the stack of open elements.
 *     <p>If the current node is an optgroup element, pop that node from the stack of open elements.
 *     <p>Insert an HTML element for the token.
 *   </dd>
 *   <dt>An end tag whose tag name is "optgroup"
 *   <dd>
 *     <p>First, if the current node is an option element, and the node immediately before it in the stack of open elements is an optgroup element, then pop the current node from the stack of open elements.
 *     <p>If the current node is an optgroup element, then pop that node from the stack of open elements. Otherwise, this is a parse error; ignore the token.
 *   </dd>
 *   <dt>An end tag whose tag name is "option"
 *   <dd>
 *     <p>If the current node is an option element, then pop that node from the stack of open elements. Otherwise, this is a parse error; ignore the token.
 *   </dd>
 *   <dt>An end tag whose tag name is "select"
 *   <dd>
 *     <p>If the stack of open elements does not have a select element in select scope, this is a parse error; ignore the token. (fragment case)
 *     <p>Otherwise:
 *     <p>Pop elements from the stack of open elements until a select element has been popped from the stack.
 *     <p>Reset the insertion mode appropriately.
 *   </dd>
 *   <dt>A start tag whose tag name is "select"
 *   <dd>
 *     <p>Parse error.
 *     <p>Pop elements from the stack of open elements until a select element has been popped from the stack.
 *     <p>Reset the insertion mode appropriately.
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "input", "keygen", "textarea"
 *   <dd>
 *     <p>Parse error.
 *     <p>If the stack of open elements does not have a select element in select scope, ignore the token. (fragment case)
 *     <p>Pop elements from the stack of open elements until a select element has been popped from the stack.
 *     <p>Reset the insertion mode appropriately.
 *     <p>Reprocess the token.
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "script", "template"
 *   <dt>An end tag whose tag name is "template"
 *   <dd>
 *     <p>Process the token using the rules for the "in head" insertion mode.
 *   </dd>
 *   <dt>An end-of-file token
 *   <dd>
 *     <p>Process the token using the rules for the "in body" insertion mode.
 *   </dd>
 *   <dt>Anything else
 *   <dd>
 *     <p>Parse error. Ignore the token.
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_SELECT
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inselect">8.2.5.4.16 The "in select" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InSelectInsertionMode extends InsertionMode {
    
    public InSelectInsertionMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        switch (token.getType()) {
        case CHARACTER: {
            final CharacterToken characterToken = (CharacterToken) token;
            final char character = characterToken.getCharacter();
            switch (character) {
            case NULL: {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Null character in select.");
                }
            } // break;
            default: {
                insertCharacter(character);
                return TOKEN_HANDLED;
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
                throw new ParseErrorException("Unexpected DOCTYPE token in select: " + token);
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
            } // break;
            case "option": {
                if (isElementA(getCurrentNode(), "option")) {
                    popCurrentNode();
                }
                insertHTMLElement(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "optgroup": {
                if (isElementA(getCurrentNode(), "option")) {
                    popCurrentNode();
                }
                if (isElementA(getCurrentNode(), "optgroup")) {
                    popCurrentNode();
                }
                insertHTMLElement(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "select": {
                if (isAllowParseErrors()) {
                    Element popped;
                    do {
                        popped = popCurrentNode();
                    } while ( !isElementA(popped, "select"));
                    resetInsertionModeAppropriately();
                    return TOKEN_HANDLED;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in select: " + startTagToken);
                }
            } // break;
            case "input": // fall through
            case "keygen": // fall through
            case "textarea": {
                if (isAllowParseErrors()) {
                    if ( !hasParticularElementInSelectScope("select")) {
                        return IGNORE_TOKEN;
                    }
                    Element popped;
                    do {
                        popped = popCurrentNode();
                    } while ( !isElementA(popped, "select"));
                    resetInsertionModeAppropriately();
                    return REPROCESS_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in select: " + startTagToken);
                }
            } // break;
            case "template": // fall through
            case "script": {
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
            case "optgroup": {
                if (isElementA(getCurrentNode(), "option")
                        && isElementA(getNodeImmediatelyBeforeCurrentNode(), "optgroup")) {
                    popCurrentNode();
                }
                if (isElementA(getCurrentNode(), "optgroup")) {
                    popCurrentNode();
                    return TOKEN_HANDLED;
                } else {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Expected current node to be an optgroup element, instead was: "
                                + getCurrentNode().getTagName());
                    }
                }
            } // break;
            case "option": {
                if (isElementA(getCurrentNode(), "option")) {
                    popCurrentNode();
                    return TOKEN_HANDLED;
                } else {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Expected current node to be an option element, instead was: "
                                + getCurrentNode().getTagName());
                    }
                }
            } // break;
            case "select": {
                // verify stack of open elements has select element in select
// scope
                if ( !hasParticularElementInSelectScope("select")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Expected select element in select scope.");
                    }
                }
                Element poppedElement;
                do {
                    poppedElement = popCurrentNode();
                } while ( !isElementA(poppedElement, "select"));
                resetInsertionModeAppropriately();
                return TOKEN_HANDLED;
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
            return true;
        } else {
            throw new ParseErrorException("Unexpected token in select: " + token);
        }
    }
    
}
