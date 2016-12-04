package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.EXCLAMATION_MARK;
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
 * Applies the comment end state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>">" (U+003E)
 *   <dd>Switch to the data state. Emit the comment token.
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Append two "-" (U+002D) characters and a U+FFFD REPLACEMENT CHARACTER character to the comment token's data. Switch to the comment state.
 *   <dt>"!" (U+0021)
 *   <dd>Parse error. Switch to the comment end bang state.
 *   <dt>"-" (U+002D)
 *   <dd>Parse error. Append a "-" (U+002D) character to the comment token's data.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Emit the comment token. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Parse error. Append two "-" (U+002D) characters and the current input character to the comment token's data. Switch to the comment state.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#COMMENT_END
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#comment-end-state">8.2.4.50 Comment end state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class CommentEndState extends TokenizerState {
    
    public CommentEndState(final Tokenizer tokenizer) {
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
        case GREATER_THAN_SIGN: {
            setTokenizerState(Tokenizer.State.DATA);
            final CommentToken commentToken = clearCommentToken();
            return one(commentToken);
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.COMMENT);
                appendToCommentToken(HYPHEN_MINUS, HYPHEN_MINUS, REPLACEMENT_CHARACTER);
                return NOTHING;
            } else {
                throw new ParseErrorException("Null character in comment end.");
            }
        } // break;
        case EXCLAMATION_MARK: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.COMMENT_END_BANG);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected exclamation mark in comment end.");
            }
        } // break;
        case HYPHEN_MINUS: {
            if (isAllowParseErrors()) {
                appendToCommentToken(HYPHEN_MINUS);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected '-' in comment end.");
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                final CommentToken commentToken = clearCommentToken();
                return one(commentToken);
            } else {
                throw new ParseErrorException("Unexpected end-of-file in comment end.");
            }
        } // break;
        default: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.COMMENT);
                appendToCommentToken(HYPHEN_MINUS, HYPHEN_MINUS, (char) ch);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected character in comment end: " + (char) ch);
            }
        } // break;
        }
    }
    
}
