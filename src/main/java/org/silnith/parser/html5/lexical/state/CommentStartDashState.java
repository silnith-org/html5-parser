package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
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
 * Applies the comment start dash state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"-" (U+002D)
 *   <dd>Switch to the comment end state
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Append a "-" (U+002D) character and a U+FFFD REPLACEMENT CHARACTER character to the comment token's data. Switch to the comment state.
 *   <dt>">" (U+003E)
 *   <dd>Parse error. Switch to the data state. Emit the comment token.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Emit the comment token. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Append a "-" (U+002D) character and the current input character to the comment token's data. Switch to the comment state.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#COMMENT_START_DASH
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#comment-start-dash-state">8.2.4.47 Comment start dash state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class CommentStartDashState extends TokenizerState {
    
    public CommentStartDashState(final Tokenizer tokenizer) {
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
            setTokenizerState(Tokenizer.State.COMMENT_END);
            return NOTHING;
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                appendToCommentToken(HYPHEN_MINUS, REPLACEMENT_CHARACTER);
                setTokenizerState(Tokenizer.State.COMMENT);
                return NOTHING;
            } else {
                throw new ParseErrorException("Null character in comment start dash state.");
            }
        } // break;
        case GREATER_THAN_SIGN: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                final CommentToken commentToken = clearCommentToken();
                return one(commentToken);
            } else {
                throw new ParseErrorException("Unexpected '>' in comment start dash state.");
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                final CommentToken commentToken = clearCommentToken();
                return one(commentToken);
            } else {
                throw new ParseErrorException("Unexpected end-of-file in comment start dash state.");
            }
        } // break;
        default: {
            appendToCommentToken(HYPHEN_MINUS, (char) ch);
            setTokenizerState(Tokenizer.State.COMMENT);
            return NOTHING;
        } // break;
        }
    }
    
}
