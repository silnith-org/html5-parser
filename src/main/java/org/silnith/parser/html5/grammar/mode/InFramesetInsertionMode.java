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
 * Applies the in frameset insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in frameset" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
 *   <dd>Insert the character.
 *   <dt>A comment token
 *   <dd>Insert a comment.
 *   <dt>A DOCTYPE token
 *   <dd>Parse error. Ignore the token.
 *   <dt>A start tag whose tag name is "html"
 *   <dd>Process the token using the rules for the "in body" insertion mode.
 *   <dt>A start tag whose tag name is "frameset"
 *   <dd>Insert an HTML element for the token.
 *   <dt>An end tag whose tag name is "frameset"
 *   <dd>
 *     If the current node is the root html element, then this is a parse error; ignore the token. (fragment case)
 *     <p>Otherwise, pop the current node from the stack of open elements.
 *     <p>If the parser was not originally created as part of the HTML fragment parsing algorithm (fragment case), and the current node is no longer a frameset element, then switch the insertion mode to "after frameset".
 *   <dt>A start tag whose tag name is "frame"
 *   <dd>
 *     Insert an HTML element for the token. Immediately pop the current node off the stack of open elements.
 *     <p>Acknowledge the token's self-closing flag, if it is set.
 *   <dt>A start tag whose tag name is "noframes"
 *   <dd>Process the token using the rules for the "in head" insertion mode.
 *   <dt>An end-of-file token
 *   <dd>
 *     If the current node is not the root html element, then this is a parse error.
 *     <p>Stop parsing.
 *   <dt>Anything else
 *   <dd>Parse error. Ignore the token.
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_FRAMESET
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inframeset">8.2.5.4.20 The "in frameset" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InFramesetInsertionMode extends InsertionMode {
    
    public InFramesetInsertionMode(final Parser parser) {
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
                throw new ParseErrorException("Unexpected DOCTYPE token in frameset: " + token);
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
            } // break;
            case "frameset": {
                insertHTMLElement(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "frame": {
                insertHTMLElement(startTagToken);
                popCurrentNode();
                // acknowledge self-closing flag
                acknowledgeTokenSelfClosingFlag(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "noframes": {
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
            case "frameset": {
                if (isElementA(getCurrentNode(), "html")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Unexpected end tag token in document fragment: " + endTagToken);
                    }
                }
                popCurrentNode();
                if ( !isHTMLFragmentParsingAlgorithm() && !isElementA(getCurrentNode(), "frameset")) {
                    setInsertionMode(Parser.Mode.AFTER_FRAMESET);
                }
                return TOKEN_HANDLED;
            } // break;
            default: {
                return anythingElse(endTagToken);
            } // break;
            }
        } // break;
        case EOF: {
            if ( !isElementA(getCurrentNode(), "html")) {
                if (isAllowParseErrors()) {
                    // do nothing?
                } else {
                    throw new ParseErrorException("Unexpected end-of-file with unclosed elements.");
                }
            }
            stopParsing();
            return TOKEN_HANDLED;
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private boolean anythingElse(final Token token) {
        if (isAllowParseErrors()) {
            return IGNORE_TOKEN;
        } else {
            throw new ParseErrorException("Unexpected token in frameset: " + token);
        }
    }
    
}
