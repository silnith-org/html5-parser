package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.APOSTROPHE;
import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.DOCTYPEToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the doctype public identifier single quoted state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"'" (U+0027)
 *   <dd>Switch to the after DOCTYPE public identifier state.
 *   <dt>U+0000 NULL
 *   <dd>Parse error. Append a U+FFFD REPLACEMENT CHARACTER character to the current DOCTYPE token's public identifier.
 *   <dt>"&gt;" (U+003E)
 *   <dd>Parse error. Set the DOCTYPE token's force-quirks flag to on. Switch to the data state. Emit that DOCTYPE token.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Set the DOCTYPE token's force-quirks flag to on. Emit that DOCTYPE token. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Append the current input character to the current DOCTYPE token's public identifier.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#DOCTYPE_PUBLIC_IDENTIFIER_SINGLE_QUOTED
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#doctype-public-identifier-(single-quoted)-state">8.2.4.59 DOCTYPE public identifier (single-quoted) state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class DOCTYPEPublicIdentifierSingleQuotedState extends TokenizerState {
    
    public DOCTYPEPublicIdentifierSingleQuotedState(final Tokenizer tokenizer) {
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
        case APOSTROPHE: {
            setTokenizerState(Tokenizer.State.AFTER_DOCTYPE_PUBLIC_IDENTIFIER);
            return NOTHING;
        } // break;
        case NULL: {
            if (isAllowParseErrors()) {
                appendToPublicIdentifier(REPLACEMENT_CHARACTER);
                return NOTHING;
            } else {
                throw new ParseErrorException("Null character in DOCTYPE public identifier (single-quoted).");
            }
        } // break;
        case GREATER_THAN_SIGN: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                setForceQuirks();
                final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
                return one(doctypeToken);
            } else {
                throw new ParseErrorException("Unexpected '>' in DOCTYPE public identifier (single-quoted).");
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                setForceQuirks();
                final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
                return one(doctypeToken);
            } else {
                throw new ParseErrorException("Unexpected end-of-file in DOCTYPE public identifier (single-quoted).");
            }
        } // break;
        default: {
            appendToPublicIdentifier((char) ch);
            return NOTHING;
        } // break;
        }
    }
    
}
