package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.HYPHEN_MINUS;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the comment state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"-" (U+002D)
 *   <dd>Switch to the comment end dash state
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Append a U+FFFD REPLACEMENT CHARACTER character to the comment token's data.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Emit the comment token. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Append the current input character to the comment token's data.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#COMMENT
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#comment-state">8.2.4.48 Comment state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class CommentState extends TokenizerState {
    
    public CommentState(final Tokenizer tokenizer) {
        super(tokenizer);
    }
    
    @Override
    public int getMaxPushback() {
        return 0;
    }
    
    @Override
    public List<Token> getNextTokens() throws IOException {
        final int ch = consume();
        switch (ch) {
        case HYPHEN_MINUS: {
            setTokenizerState(Tokenizer.State.COMMENT_END_DASH);
            return NOTHING;
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                appendToCommentToken(REPLACEMENT_CHARACTER);
                return NOTHING;
            } else {
                throw new ParseErrorException();
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                final CommentToken commentToken = clearCommentToken();
                return one(commentToken);
            } else {
                throw new ParseErrorException();
            }
        } // break;
        default: {
            appendToCommentToken((char) ch);
            return NOTHING;
        } // break;
        }
    }
    
}
