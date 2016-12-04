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


/**
 * Applies the in column group insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in column group" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
 *   <dd>Insert the character.
 *   <dt>A comment token
 *   <dd>Insert a comment.
 *   <dt>A DOCTYPE token
 *   <dd>Parse error. Ignore the token.
 *   <dt>A start tag whose tag name is "html"
 *   <dd>Process the token using the rules for the "in body" insertion mode.
 *   <dt>A start tag whose tag name is "col"
 *   <dd>
 *     Insert an HTML element for the token. Immediately pop the current node off the stack of open elements.
 *     <p>Acknowledge the token's self-closing flag, if it is set.
 *   <dt>An end tag whose tag name is "colgroup"
 *   <dd>
 *     If the current node is not a colgroup element, then this is a parse error; ignore the token.
 *     <p>Otherwise, pop the current node from the stack of open elements. Switch the insertion mode to "in table".
 *   <dt>An end tag whose tag name is "col"
 *   <dd>Parse error. Ignore the token.
 *   <dt>A start tag whose tag name is "template"
 *   <dt>An end tag whose tag name is "template"
 *   <dd>Process the token using the rules for the "in head" insertion mode.
 *   <dt>An end-of-file token
 *   <dd>Process the token using the rules for the "in body" insertion mode.
 *   <dt>Anything else
 *   <dd>
 *     If the current node is not a colgroup element, then this is a parse error; ignore the token.
 *     <p>Otherwise, pop the current node from the stack of open elements.
 *     <p>Switch the insertion mode to "in table".
 *     <p>Reprocess the token.
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_COLUMN_GROUP
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-incolgroup">8.2.5.4.12 The "in column group" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InColumnGroupInsertionMode extends InsertionMode {
    
    public InColumnGroupInsertionMode(final Parser parser) {
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
                throw new ParseErrorException("Unexpected DOCTYPE token in column group: " + token);
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
            } // break;
            case "col": {
                insertHTMLElement(startTagToken);
                popCurrentNode();
                acknowledgeTokenSelfClosingFlag(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "template": {
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
            case "colgroup": {
                if ( !isElementA(getCurrentNode(), "colgroup")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Expected current node to be a colgroup element, instead it was: "
                                + getCurrentNode().getTagName());
                    }
                }
                popCurrentNode();
                setInsertionMode(Parser.Mode.IN_TABLE);
                return TOKEN_HANDLED;
            } // break;
            case "col": {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected end tag token in column group: " + endTagToken);
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
        if ( !isElementA(getCurrentNode(), "colgroup")) {
            if (isAllowParseErrors()) {
                return IGNORE_TOKEN;
            } else {
                throw new ParseErrorException("Expected current node to be a colgroup element, instead it was: "
                        + getCurrentNode().getTagName());
            }
        }
        popCurrentNode();
        setInsertionMode(Parser.Mode.IN_TABLE);
        return REPROCESS_TOKEN;
    }
    
}
