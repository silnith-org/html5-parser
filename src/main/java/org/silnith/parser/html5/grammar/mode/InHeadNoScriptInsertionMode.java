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
 * Applies the in head no script insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in head noscript" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A DOCTYPE token
 *   <dd>Parse error. Ignore the token.
 *   <dt>A start tag whose tag name is "html"
 *   <dd>Process the token using the rules for the "in body" insertion mode.
 *   <dt>An end tag whose tag name is "noscript"
 *   <dd>
 *     Pop the current node (which will be a noscript element) from the stack of open elements; the new current node will be a head element.
 *     <p>Switch the insertion mode to "in head".
 *   </dd>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
 *   <dt>A comment token
 *   <dt>A start tag whose tag name is one of: "basefont", "bgsound", "link", "meta", "noframes", "style"
 *   <dd>Process the token using the rules for the "in head" insertion mode.
 *   <dt>An end tag whose tag name is "br"
 *   <dd>Act as described in the "anything else" entry below.
 *   <dt>A start tag whose tag name is one of: "head", "noscript"
 *   <dt>Any other end tag
 *   <dd>Parse error. Ignore the token.
 *   <dt>Anything else
 *   <dd>
 *     Parse error.
 *     <p>Pop the current node (which will be a noscript element) from the stack of open elements; the new current node will be a head element.
 *     <p>Switch the insertion mode to "in head".
 *     <p>Reprocess the token.
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_HEAD_NOSCRIPT
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inheadnoscript">8.2.5.4.5 The "in head noscript" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InHeadNoScriptInsertionMode extends InsertionMode {
    
    public InHeadNoScriptInsertionMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        switch (token.getType()) {
        case DOCTYPE: {
            if (isAllowParseErrors()) {
                return IGNORE_TOKEN;
            } else {
                throw new ParseErrorException("Unexpected DOCTYPE in head (no script): " + token);
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
            } // break;
            case "basefont": // fall through
            case "bgsound": // fall through
            case "link": // fall through
            case "meta": // fall through
            case "noframes": // fall through
            case "style": {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, startTagToken);
            } // break;
            case "head": // fall through
            case "noscript": {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in head (no script): " + startTagToken);
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
            case "noscript": {
                final Element popped = popCurrentNode();
                assert isElementA(popped, "noscript");
                assert isElementA(getCurrentNode(), "head");
                setInsertionMode(Parser.Mode.IN_HEAD);
                return TOKEN_HANDLED;
            } // break;
            case "br": {
                return anythingElse(endTagToken);
            } // break;
            default: {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected end tag token in head (no script): " + endTagToken);
                }
            } // break;
            }
        } // break;
        case CHARACTER: {
            final CharacterToken characterToken = (CharacterToken) token;
            final char character = characterToken.getCharacter();
            switch (character) {
            case CHARACTER_TABULATION: // fall through
            case LINE_FEED: // fall through
            case FORM_FEED: // fall through
            case CARRIAGE_RETURN: // fall through
            case SPACE: {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, characterToken);
            } // break;
            default: {
                return anythingElse(characterToken);
            } // break;
            }
        } // break;
        case COMMENT: {
            final CommentToken commentToken = (CommentToken) token;
            return processUsingRulesFor(Parser.Mode.IN_HEAD, commentToken);
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private boolean anythingElse(final Token token) {
        if (isAllowParseErrors()) {
            final Element popped = popCurrentNode();
            assert isElementA(popped, "noscript");
            assert isElementA(getCurrentNode(), "head");
            setInsertionMode(Parser.Mode.IN_HEAD);
            return REPROCESS_TOKEN;
        } else {
            throw new ParseErrorException("Unexpected token in head (no script): " + token);
        }
    }
    
}
