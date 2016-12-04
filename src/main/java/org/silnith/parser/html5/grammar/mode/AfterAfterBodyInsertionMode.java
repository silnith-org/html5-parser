package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.grammar.dom.AfterLastChildInsertionPosition;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the after after body insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "after after body" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A comment token
 *   <dd>Insert a comment as the last child of the Document object.
 *   <dt>A DOCTYPE token
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
 *   <dt>A start tag whose tag name is "html"
 *   <dd>Process the token using the rules for the "in body" insertion mode.
 *   <dt>An end-of-file token
 *   <dd>Stop parsing.
 *   <dt>Anything else
 *   <dd>Parse error. Switch the insertion mode to "in body" and reprocess the token.
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#AFTER_AFTER_BODY
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-after-after-body-insertion-mode">8.2.5.4.22 The "after after body" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class AfterAfterBodyInsertionMode extends InsertionMode {
    
    public AfterAfterBodyInsertionMode(final Parser parser) {
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
        if (isAllowParseErrors()) {
            setInsertionMode(Parser.Mode.IN_BODY);
            return REPROCESS_TOKEN;
        } else {
            throw new ParseErrorException("Unexpected token in after after body mode: " + token);
        }
    }
    
}
