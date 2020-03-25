package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.AMPERSAND;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the character reference in data state logic.
 * <p>
 * Switch to the data state.
 * <p>Attempt to consume a character reference, with no additional allowed character.
 * <p>If nothing is returned, emit a U+0026 AMPERSAND character (&amp;) token.
 * <p>Otherwise, emit the character tokens that were returned.
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#CHARACTER_REFERENCE_IN_DATA
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#character-reference-in-data-state">8.2.4.2 Character reference in data state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class CharacterReferenceInDataState extends TokenizerState {
    
    private final CharacterReferenceState characterReferenceState;
    
    public CharacterReferenceInDataState(final Tokenizer tokenizer) {
        super(tokenizer);
        this.characterReferenceState = new CharacterReferenceState(tokenizer);
    }
    
    @Override
    public int getMaxPushback() {
        return characterReferenceState.getMaxPushback();
    }
    
    @Override
    public List<Token> getNextTokens() throws IOException {
        setTokenizerState(Tokenizer.State.DATA);
        final List<Token> characterReference = characterReferenceState.getNextTokens();
        if (characterReference == null || characterReference.isEmpty()) {
            return one(new CharacterToken(AMPERSAND));
        } else {
            return characterReference;
        }
    }
    
}
