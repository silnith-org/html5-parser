package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.html5.ParseErrors;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.grammar.dom.AfterLastChildInsertionPosition;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the after after frameset insertion mode logic.
 * <p>When the user agent is to apply the rules for the "after after frameset" insertion mode, the user agent must handle the token as follows:</p>
 * <dl>
 *   <dt>A comment token</dt>
 *   <dd>
 *     <p>Insert a comment as the last child of the Document object.</p>
 *   </dd>
 *   <dt>A DOCTYPE token</dt>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE</dt>
 *   <dt>A start tag whose tag name is "html"</dt>
 *   <dd>
 *     <p>Process the token using the rules for the "in body" insertion mode.</p>
 *   </dd>
 *   <dt>An end-of-file token</dt>
 *   <dd>
 *     <p>Stop parsing.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "noframes"</dt>
 *   <dd>
 *     <p>Process the token using the rules for the "in head" insertion mode.</p>
 *   </dd>
 *   <dt>Anything else</dt>
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#AFTER_AFTER_FRAMESET
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-after-after-frameset-insertion-mode">8.2.5.4.23 The "after after frameset" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class AfterAfterFramesetInsertionMode extends InsertionMode {
    
    public AfterAfterFramesetInsertionMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        switch (token.getType()) {
        case COMMENT: {
            final CommentToken commentToken = (CommentToken) token;
            insertComment(commentToken, new AfterLastChildInsertionPosition(getDocument()));
            return TOKEN_HANDLED;
        } // break;
        case DOCTYPE: {
            return processUsingRulesFor(Parser.Mode.IN_BODY, token);
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
                return processUsingRulesFor(Parser.Mode.IN_BODY, characterToken);
            } // break;
            default: {
                return anythingElse(characterToken);
            } // break;
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                return processUsingRulesFor(Parser.Mode.IN_BODY, startTagToken);
            } // break;
            case "noframes": {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, startTagToken);
            } // break;
            default: {
                return anythingElse(startTagToken);
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
        reportParseError(ParseErrors.UNEXPECTED_TOKEN_FOLLOWING_FRAMESET_DOCUMENT, "Unexpected token after after frameset: " + token);
        
        return IGNORE_TOKEN;
    }
    
}
