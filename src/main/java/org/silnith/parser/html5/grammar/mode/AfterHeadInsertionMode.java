package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.html5.ParseErrors;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the after head insertion mode logic.
 * <p>When the user agent is to apply the rules for the "after head" insertion mode, the user agent must handle the token as follows:</p>
 * <dl>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE</dt>
 *   <dd>
 *     <p>Insert the character.</p>
 *   </dd>
 *   <dt>A comment token</dt>
 *   <dd>
 *     <p>Insert a comment.</p>
 *   </dd>
 *   <dt>A DOCTYPE token</dt>
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "html"</dt>
 *   <dd>
 *     <p>Process the token using the rules for the "in body" insertion mode.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "body"</dt>
 *   <dd>
 *     <p>Insert an HTML element for the token.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *     <p>Switch the insertion mode to "in body".</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "frameset"</dt>
 *   <dd>
 *     <p>Insert an HTML element for the token.</p>
 *     <p>Switch the insertion mode to "in frameset".</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "template", "title"</dt>
 *   <dd>
 *     <p>Parse error.</p>
 *     <p>Push the node pointed to by the head element pointer onto the stack of open elements.</p>
 *     <p>Process the token using the rules for the "in head" insertion mode.</p>
 *     <p>Remove the node pointed to by the head element pointer from the stack of open elements. (It might not be the current node at this point.)</p>
 *   </dd>
 *   <dt>An end tag whose tag name is "template"</dt>
 *   <dd>
 *     <p>Process the token using the rules for the "in head" insertion mode.</p>
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "body", "html", "br"</dt>
 *   <dd>
 *     <p>Act as described in the "anything else" entry below.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "head"</dt>
 *   <dt>Any other end tag</dt>
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 *   <dt>Anything else</dt>
 *   <dd>
 *     <p>Insert an HTML element for a "body" start tag token with no attributes.</p>
 *     <p>Switch the insertion mode to "in body".</p>
 *     <p>Reprocess the current token.</p>
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#AFTER_HEAD
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-after-head-insertion-mode">8.2.5.4.6 The "after head" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class AfterHeadInsertionMode extends InsertionMode {
    
    public AfterHeadInsertionMode(final Parser parser) {
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
            reportParseError(ParseErrors.DOCTYPE_FOLLOWING_HEAD, "Unexpected DOCTYPE after head: " + token);
            
            return IGNORE_TOKEN;
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
            } // break;
            case "body": {
                insertHTMLElement(startTagToken);
                setFramesetOKFlag(NOT_OK);
                setInsertionMode(Parser.Mode.IN_BODY);
                return TOKEN_HANDLED;
            } // break;
            case "frameset": {
                insertHTMLElement(startTagToken);
                setInsertionMode(Parser.Mode.IN_FRAMESET);
                return TOKEN_HANDLED;
            } // break;
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
                reportParseError(ParseErrors.UNEXPECTED_METADATA_ELEMENT_FOLLOWING_HEAD, "Unexpected start tag token after head: " + startTagToken);
                
                assert getHeadElementPointer() != null;
                addToStackOfOpenElements(getHeadElementPointer());
                final boolean returnValue = processUsingRulesFor(Parser.Mode.IN_HEAD, startTagToken);
                Element popped;
                do {
                    /*
                     * TODO:
                     * From the verbiage it seems only the head element should be removed.
                     * Need to implement it that way, right now it removes everything
                     * above and including the head.
                     */
                    popped = popCurrentNode();
                } while (popped != getHeadElementPointer());
                return returnValue;
            } // break;
            case "head": {
                reportParseError(ParseErrors.HEAD_FOLLOWING_HEAD, "Unexpected start tag token after head: " + startTagToken);
                
                return IGNORE_TOKEN;
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
            case "template": {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, endTagToken);
            } // break;
            case "body": // fall through
            case "html": // fall through
            case "br": {
                return anythingElse(endTagToken);
            } // break;
            default: {
                reportParseError(ParseErrors.UNEXPECTED_END_TAG_FOLLOWING_HEAD, "Unexpected end tag token after head: " + endTagToken);
                
                return IGNORE_TOKEN;
            } // break;
            }
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private boolean anythingElse(final Token token) {
        insertHTMLElement("body");
        setInsertionMode(Parser.Mode.IN_BODY);
        return REPROCESS_TOKEN;
    }
    
}
