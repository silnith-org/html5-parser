package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.grammar.dom.InsertionPosition;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the in head insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in head" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
 *   <dd>Insert the character.
 *   <dt>A comment token
 *   <dd>Insert a comment.
 *   <dt>A DOCTYPE token
 *   <dd>Parse error. Ignore the token.
 *   <dt>A start tag whose tag name is "html"
 *   <dd>Process the token using the rules for the "in body" insertion mode.
 *   <dt>A start tag whose tag name is one of: "base", "basefont", "bgsound", "link"
 *   <dd>
 *     Insert an HTML element for the token. Immediately pop the current node off the stack of open elements.
 *     <p>Acknowledge the token's self-closing flag, if it is set.
 *   </dd>
 *   <dt>A start tag whose tag name is "meta"
 *   <dd>
 *     Insert an HTML element for the token. Immediately pop the current node off the stack of open elements.
 *     <p>Acknowledge the token's self-closing flag, if it is set.
 *     <p>If the element has a charset attribute, and getting an encoding from its value results in a supported ASCII-compatible character encoding or a UTF-16 encoding, and the confidence is currently tentative, then change the encoding to the resulting encoding.
 *     <p>Otherwise, if the element has an http-equiv attribute whose value is an ASCII case-insensitive match for the string "Content-Type", and the element has a content attribute, and applying the algorithm for extracting a character encoding from a meta element to that attribute's value returns a supported ASCII-compatible character encoding or a UTF-16 encoding, and the confidence is currently tentative, then change the encoding to the extracted encoding.
 *   </dd>
 *   <dt>A start tag whose tag name is "title"
 *   <dd>Follow the generic RCDATA element parsing algorithm.
 *   <dt>A start tag whose tag name is "noscript", if the scripting flag is enabled
 *   <dt>A start tag whose tag name is one of: "noframes", "style"
 *   <dd>Follow the generic raw text element parsing algorithm.
 *   <dt>A start tag whose tag name is "noscript", if the scripting flag is disabled
 *   <dd>
 *     Insert an HTML element for the token.
 *     <p>Switch the insertion mode to "in head noscript".
 *   </dd>
 *   <dt>A start tag whose tag name is "script"
 *   <dd>
 *     Run these steps:
 *     <ol>
 *       <li>Let the adjusted insertion location be the appropriate place for inserting a node.
 *       <li>Create an element for the token in the HTML namespace, with the intended parent being the element in which the adjusted insertion location finds itself.
 *       <li>Mark the element as being "parser-inserted" and unset the element's "force-async" flag.
 *       <li>If the parser was originally created for the HTML fragment parsing algorithm, then mark the script element as "already started". (fragment case)
 *       <li>Insert the newly created element at the adjusted insertion location.
 *       <li>Push the element onto the stack of open elements so that it is the new current node.
 *       <li>Switch the tokenizer to the script data state.
 *       <li>Let the original insertion mode be the current insertion mode.
 *       <li>Switch the insertion mode to "text".
 *     </ol>
 *   </dd>
 *   <dt>An end tag whose tag name is "head"
 *   <dd>
 *     Pop the current node (which will be the head element) off the stack of open elements.
 *     <p>Switch the insertion mode to "after head".
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "body", "html", "br"
 *   <dd>Act as described in the "anything else" entry below.
 *   <dt>A start tag whose tag name is "template"
 *   <dd>
 *     Insert an HTML element for the token.
 *     <p>Insert a marker at the end of the list of active formatting elements.
 *     <p>Set the frameset-ok flag to "not ok".
 *     <p>Switch the insertion mode to "in template".
 *     <p>Push "in template" onto the stack of template insertion modes so that it is the new current template insertion mode.
 *   </dd>
 *   <dt>An end tag whose tag name is "template"
 *   <dd>
 *     If there is no template element on the stack of open elements, then this is a parse error; ignore the token.
 *     <p>Otherwise, run these steps:
 *     <ol>
 *       <li>Generate implied end tags.
 *       <li>If the current node is not a template element, then this is a parse error.
 *       <li>Pop elements from the stack of open elements until a template element has been popped from the stack.
 *       <li>Clear the list of active formatting elements up to the last marker.
 *       <li>Pop the current template insertion mode off the stack of template insertion modes.
 *       <li>Reset the insertion mode appropriately.
 *     </ol>
 *   </dd>
 *   <dt>A start tag whose tag name is "head"
 *   <dt>Any other end tag
 *   <dd>Parse error. Ignore the token.
 *   <dt>Anything else
 *   <dd>
 *     Pop the current node (which will be the head element) off the stack of open elements.
 *     <p>Switch the insertion mode to "after head".
 *     <p>Reprocess the token.
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_HEAD
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inhead">8.2.5.4.4 The "in head" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InHeadInsertionMode extends InsertionMode {
    
    public InHeadInsertionMode(final Parser parser) {
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
                throw new ParseErrorException("Unexpected DOCTYPE in head: " + token);
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
            } // break;
            case "base": // fall through
            case "basefont": // fall through
            case "bgsound": // fall through
            case "link": {
                final Element element = insertHTMLElement(startTagToken);
                final Element popped = popCurrentNode();
                
                assert element == popped;
                
                acknowledgeTokenSelfClosingFlag(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "meta": {
                final Element element = insertHTMLElement(startTagToken);
                final Element popped = popCurrentNode();
                
                assert element == popped;
                
                acknowledgeTokenSelfClosingFlag(startTagToken);
                // TODO: handle charset special behavior
                return TOKEN_HANDLED;
            } // break;
            case "title": {
                genericRCDATAElementParsingAlgorithm(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "noframes": // fall through
            case "style": {
                genericRawTextElementParsingAlgorithm(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "noscript": {
                if (isScriptingEnabled()) {
                    genericRawTextElementParsingAlgorithm(startTagToken);
                    return TOKEN_HANDLED;
                } else {
                    insertHTMLElement(startTagToken);
                    setInsertionMode(Parser.Mode.IN_HEAD_NOSCRIPT);
                    return TOKEN_HANDLED;
                }
            } // break;
            case "script": {
                final InsertionPosition adjustedInsertionLocation = getAppropriatePlaceForInsertingNode();
                final Element element = createElementForToken(startTagToken, HTML_NAMESPACE,
                        adjustedInsertionLocation.getContainingNode());
                // mark as parser-inserted
//                final HTMLScriptElement htmlScriptElement = (HTMLScriptElement) element;
                // unset "force-async" flag
                if (parser.isHTMLFragmentParsingAlgorithm()) {
                    // mark the script as already started
                }
                adjustedInsertionLocation.insert(element);
                addToStackOfOpenElements(element);
                assert element == getCurrentNode();
                setTokenizerState(Tokenizer.State.SCRIPT_DATA);
                setOriginalInsertionMode(getInsertionMode());
                setInsertionMode(Parser.Mode.TEXT);
                return TOKEN_HANDLED;
            } // break;
            case "template": {
                insertHTMLElement(startTagToken);
                insertMarkerAtEndOfListOfActiveFormattingElements();
                setFramesetOKFlag(NOT_OK);
                setInsertionMode(Parser.Mode.IN_TEMPLATE);
                pushOntoStackOfTemplateInsertionModes(Parser.Mode.IN_TEMPLATE);
                assert Parser.Mode.IN_TEMPLATE == getCurrentTemplateInsertionMode();
                return TOKEN_HANDLED;
            } // break;
            case "head": {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in head: " + startTagToken);
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
            case "head": {
                final Element head = popCurrentNode();
                assert isElementA(head, "head");
                setInsertionMode(Parser.Mode.AFTER_HEAD);
                return TOKEN_HANDLED;
            } // break;
            case "body": // fall through
            case "html": // fall through
            case "br": {
                return anythingElse(endTagToken);
            } // break;
            case "template": {
                // confirm stack of open elements contains a "template"
                if ( !isStackOfOpenElementsContains("template")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException(
                                "Expected to find a template element on the stack of open elements.");
                    }
                }
                generateImpliedEndTags();
                if (isAllowParseErrors() && !isElementA(getCurrentNode(), "template")) {
                    throw new ParseErrorException(
                            "Expected current element to be a template element, was: " + getCurrentNode().getTagName());
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, "template"));
                clearListOfActiveFormattingElementsUpToLastMarker();
                popCurrentTemplateInsertionMode();
                resetInsertionModeAppropriately();
                return TOKEN_HANDLED;
            } // break;
            default: {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected end tag token in head: " + endTagToken);
                }
            } // break;
            }
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private boolean anythingElse(final Token token) {
        final Element popped = popCurrentNode();
        assert isElementA(popped, "head");
        setInsertionMode(Parser.Mode.AFTER_HEAD);
        return REPROCESS_TOKEN;
    }
    
}
