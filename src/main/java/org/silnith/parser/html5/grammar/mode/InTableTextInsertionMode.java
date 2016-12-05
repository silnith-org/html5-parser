package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.NULL;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the in table text insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "in table text" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token that is U+0000 NULL
 *   <dd>
 *     <p>Parse error. Ignore the token.
 *   </dd>
 *   <dt>Any other character token
 *   <dd>
 *     <p>Append the character token to the pending table character tokens list.
 *   </dd>
 *   <dt>Anything else
 *   <dd>
 *     <p>If any of the tokens in the pending table character tokens list are character tokens that are not space characters, then reprocess the character tokens in the pending table character tokens list using the rules given in the "anything else" entry in the "in table" insertion mode.
 *     <p>Otherwise, insert the characters given by the pending table character tokens list.
 *     <p>Switch the insertion mode to the original insertion mode and reprocess the token.
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_TABLE_TEXT
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intabletext">8.2.5.4.10 The "in table text" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InTableTextInsertionMode extends InsertionMode {
    
    public InTableTextInsertionMode(final Parser parser) {
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
                    throw new ParseErrorException("Null character in table text.");
                }
            } // break;
            default: {
                appendToPendingTableCharacterTokens(characterToken);
                return TOKEN_HANDLED;
            } // break;
            }
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private boolean anythingElse(final Token token) {
        // if pending table character tokens contains non-space characters
        if (isPendingTableCharacterTokensListContainsCharactersThatAreNotSpaceCharacters()) {
            // reprocess the pending table character tokens according to the
            // "anything else" case of the IN_TABLE mode
            if (isAllowParseErrors()) {
                enableFosterParenting();
                final boolean returnValue = processUsingRulesFor(Parser.Mode.IN_BODY, token);
                disableFosterParenting();
                return returnValue;
            } else {
                throw new ParseErrorException("Unexpected token in table text: " + token);
            }
        }
        for (final CharacterToken characterToken : getPendingTableCharacterTokens()) {
            insertCharacter(characterToken);
        }
        /*
         * The "spec" does not actually say to clear the pending table character
         * token list at this point. I am assuming it was implied.
         */
        setPendingTableCharacterTokens();
        setInsertionMode(getOriginalInsertionMode());
        return REPROCESS_TOKEN;
    }
    
}
