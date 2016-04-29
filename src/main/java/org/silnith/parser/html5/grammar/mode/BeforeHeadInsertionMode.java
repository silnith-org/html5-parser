package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the before head insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "before head" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
 *   <dd>Ignore the token.
 *   <dt>A comment token
 *   <dd>Insert a comment.
 *   <dt>A DOCTYPE token
 *   <dd>Parse error. Ignore the token.
 *   <dt>A start tag whose tag name is "html"
 *   <dd>Process the token using the rules for the "in body" insertion mode.
 *   <dt>A start tag whose tag name is "head"
 *   <dd>
 *     Insert an HTML element for the token.
 *     <p>Set the head element pointer to the newly created head element.
 *     <p>Switch the insertion mode to "in head".
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "head", "body", "html", "br"
 *   <dd>Act as described in the "anything else" entry below.
 *   <dt>Any other end tag
 *   <dd>Parse error. Ignore the token.
 *   <dt>Anything else
 *   <dd>
 *     Insert an HTML element for a "head" start tag token with no attributes.
 *     <p>Set the head element pointer to the newly created head element.
 *     <p>Switch the insertion mode to "in head".
 *     <p>Reprocess the current token.
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#BEFORE_HEAD
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-before-head-insertion-mode">8.2.5.4.3 The "before head" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class BeforeHeadInsertionMode extends InsertionMode {
    
    public BeforeHeadInsertionMode(final Parser parser) {
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
                return IGNORE_TOKEN;
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
                throw new ParseErrorException("Unexpected DOCTYPE token before head: " + token);
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
            } // break;
            case "head": {
                final Element headElement = insertHTMLElement(startTagToken);
                setHeadElementPointer(headElement);
                setInsertionMode(Parser.Mode.IN_HEAD);
                return TOKEN_HANDLED;
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
            case "head": // fall through
            case "body": // fall through
            case "html": // fall through
            case "br": {
                return anythingElse(endTagToken);
            } // break;
            default: {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected end tag token before head: " + endTagToken);
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
        final Element headElement = insertHTMLElement("head");
        setHeadElementPointer(headElement);
        setInsertionMode(Parser.Mode.IN_HEAD);
        return REPROCESS_TOKEN;
    }
    
}
