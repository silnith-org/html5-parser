package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.HYPHEN_MINUS;
import static org.silnith.parser.util.UnicodeCodePoints.LEFT_SQUARE_BRACKET;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the markup declaration open state logic.
 * <p>
 * If the next two characters are both "-" (U+002D) characters, consume those two characters, create a comment token whose data is the empty string, and switch to the comment start state.
 * <p>
 * Otherwise, if the next seven characters are an ASCII case-insensitive match for the word "DOCTYPE", then consume those characters and switch to the DOCTYPE state.
 * <p>
 * Otherwise, if there is an adjusted current node and it is not an element in the HTML namespace and the next seven characters are a case-sensitive match for the string "[CDATA[" (the five uppercase letters "CDATA" with a U+005B LEFT SQUARE BRACKET character before and after), then consume those characters and switch to the CDATA section state.
 * <p>
 * Otherwise, this is a parse error. Switch to the bogus comment state. The next character that is consumed, if any, is the first character that will be in the comment.
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#MARKUP_DECLARATION_OPEN
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#markup-declaration-open-state">8.2.4.45 Markup declaration open state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class MarkupDeclarationOpenState extends TokenizerState {
    
    public MarkupDeclarationOpenState(final Tokenizer tokenizer) {
        super(tokenizer);
    }
    
    @Override
    public int getMaxPushback() {
        return 7;
    }
    
    @Override
    public List<Token> getNextTokens() throws IOException {
        final char[] buf = new char[7];
        final int numRead = consume(buf, 7);
        
        if (numRead >= 2 && buf[0] == HYPHEN_MINUS && buf[1] == HYPHEN_MINUS) {
            for (int i = numRead - 1; i >= 2; i-- ) {
                unconsume(buf[i]);
            }
            createCommentToken();
            setTokenizerState(Tokenizer.State.COMMENT_START);
            return NOTHING;
        }
        
        if (numRead == 7 && (buf[0] == 'd' || buf[0] == 'D') && (buf[1] == 'o' || buf[1] == 'O')
                && (buf[2] == 'c' || buf[2] == 'C') && (buf[3] == 't' || buf[3] == 'T')
                && (buf[4] == 'y' || buf[4] == 'Y') && (buf[5] == 'p' || buf[5] == 'P')
                && (buf[6] == 'e' || buf[6] == 'E')) {
            setTokenizerState(Tokenizer.State.DOCTYPE);
            return NOTHING;
        }
        
        if (numRead == 7 && buf[0] == LEFT_SQUARE_BRACKET && buf[1] == 'C' && buf[2] == 'D' && buf[3] == 'A'
                && buf[4] == 'T' && buf[5] == 'A' && buf[6] == LEFT_SQUARE_BRACKET) {
            // TODO:
            // also need to check that there is an "adjusted current node"
            // and that said node is not an element in the HTML namespace
            setTokenizerState(Tokenizer.State.CDATA_SECTION);
            return NOTHING;
        }
        
        if (isAllowParseErrors()) {
            for (int i = numRead - 1; i >= 0; i-- ) {
                unconsume(buf[i]);
            }
            createCommentToken();
            setTokenizerState(Tokenizer.State.BOGUS_COMMENT);
            return NOTHING;
        } else {
            throw new ParseErrorException("Unknown markup declaration: " + String.valueOf(buf));
        }
    }
    
}
