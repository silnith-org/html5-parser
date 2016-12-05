package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.ParseErrors;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.grammar.dom.AfterLastChildInsertionPosition;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the after body insertion mode logic.
 * <p>When the user agent is to apply the rules for the "after body" insertion mode, the user agent must handle the token as follows:</p>
 * <dl>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE</dt>
 *   <dd>
 *     <p>Process the token using the rules for the "in body" insertion mode.</p>
 *   </dd>
 *   <dt>A comment token</dt>
 *   <dd>
 *     <p>Insert a comment as the last child of the first element in the stack of open elements (the html element).</p>
 *   </dd>
 *   <dt>A DOCTYPE token</dt>
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "html"</dt>
 *   <dd>
 *     <p>Process the token using the rules for the "in body" insertion mode.</p>
 *   </dd>
 *   <dt>An end tag whose tag name is "html"</dt>
 *   <dd>
 *     <p>If the parser was originally created as part of the HTML fragment parsing algorithm, this is a parse error; ignore the token. (fragment case)</p>
 *     <p>Otherwise, switch the insertion mode to "after after body".</p>
 *   </dd>
 *   <dt>An end-of-file token</dt>
 *   <dd>
 *     <p>Stop parsing.</p>
 *   </dd>
 *   <dt>Anything else</dt>
 *   <dd>
 *     <p>Parse error. Switch the insertion mode to "in body" and reprocess the token.</p>
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#AFTER_BODY
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-afterbody">8.2.5.4.19 The "after body" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class AfterBodyInsertionMode extends InsertionMode {
    
    public AfterBodyInsertionMode(final Parser parser) {
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
                return processUsingRulesFor(Parser.Mode.IN_BODY, characterToken);
            } // break;
            default: {
                return anythingElse(characterToken);
            } // break;
            }
        } // break;
        case COMMENT: {
            final CommentToken commentToken = (CommentToken) token;
            final Element htmlElement = getFirstElementInStackOfOpenElements();
            assert htmlElement.getTagName().equals("html");
            insertComment(commentToken, new AfterLastChildInsertionPosition(htmlElement));
            return TOKEN_HANDLED;
        } // break;
        case DOCTYPE: {
            reportParseError(ParseErrors.UNEXPECTED_DOCTYPE_FOLLOWING_BODY, "Unexpected DOCTYPE token after body: " + token);
            
            return IGNORE_TOKEN;
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
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
            case "html": {
                if (isHTMLFragmentParsingAlgorithm()) {
                    reportParseError(ParseErrors.UNEXPECTED_HTML_CLOSE_TAG_FOLLOWING_BODY_IN_HTML_FRAGMENT, "Unexpected html close tag following body while parsing an HTML fragment.");
                    
                    return IGNORE_TOKEN;
                } else {
                    setInsertionMode(Parser.Mode.AFTER_AFTER_BODY);
                    return TOKEN_HANDLED;
                }
            } // break;
            default: {
                return anythingElse(endTagToken);
            } // break;
            }
        } // break;
        case EOF: {
            stopParsing();
            return TOKEN_HANDLED;
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private boolean anythingElse(final Token token) {
        reportParseError(ParseErrors.UNEXPECTED_TOKEN_FOLLOWING_BODY, "Unexpected token after body: " + token);
        
        setInsertionMode(Parser.Mode.IN_BODY);
        return REPROCESS_TOKEN;
    }
    
}
