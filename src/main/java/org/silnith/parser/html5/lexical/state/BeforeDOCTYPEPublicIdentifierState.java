package org.silnith.parser.html5.lexical.state;

import static org.silnith.parser.util.UnicodeCodePoints.APOSTROPHE;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.GREATER_THAN_SIGN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.QUOTATION_MARK;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.io.IOException;
import java.util.List;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.DOCTYPEToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the before doctype public identifier state logic.
 * <p>
 * Consume the next input character:
 * <dl>
 *   <dt>"tab" (U+0009)
 *   <dt>"LF" (U+000A)
 *   <dt>"FF" (U+000C)
 *   <dt>U+0020 SPACE
 *   <dd>Ignore the character.
 *   <dt>U+0022 QUOTATION MARK (")
 *   <dd>Set the DOCTYPE token's public identifier to the empty string (not missing), then switch to the DOCTYPE public identifier (double-quoted) state.
 *   <dt>"'" (U+0027)
 *   <dd>Set the DOCTYPE token's public identifier to the empty string (not missing), then switch to the DOCTYPE public identifier (single-quoted) state.
 *   <dt>">" (U+003E)
 *   <dd>Parse error. Set the DOCTYPE token's force-quirks flag to on. Switch to the data state. Emit that DOCTYPE token.
 *   <dt>EOF
 *   <dd>Parse error. Switch to the data state. Set the DOCTYPE token's force-quirks flag to on. Emit that DOCTYPE token. Reconsume the EOF character.
 *   <dt>Anything else
 *   <dd>Parse error. Set the DOCTYPE token's force-quirks flag to on. Switch to the bogus DOCTYPE state.
 * </dl> 
 * 
 * @see org.silnith.parser.html5.lexical.Tokenizer.State#BEFORE_DOCTYPE_PUBLIC_IDENTIFIER
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#before-doctype-public-identifier-state">8.2.4.57 Before DOCTYPE public identifier state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class BeforeDOCTYPEPublicIdentifierState extends TokenizerState {
    
    public BeforeDOCTYPEPublicIdentifierState(final Tokenizer tokenizer) {
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
        case CHARACTER_TABULATION: // fall through
        case LINE_FEED: // fall through
        case FORM_FEED: // fall through
        case SPACE: {
            // ignore character
            return NOTHING;
        } // break;
        case QUOTATION_MARK: {
            createPublicIdentifier();
            setTokenizerState(Tokenizer.State.DOCTYPE_PUBLIC_IDENTIFIER_DOUBLE_QUOTED);
            return NOTHING;
        } // break;
        case APOSTROPHE: {
            createPublicIdentifier();
            setTokenizerState(Tokenizer.State.DOCTYPE_PUBLIC_IDENTIFIER_SINGLE_QUOTED);
            return NOTHING;
        } // break;
        case GREATER_THAN_SIGN: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                setForceQuirks();
                final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
                return one(doctypeToken);
            } else {
                throw new ParseErrorException("Unexpected '>' before DOCTYPE public identifier.");
            }
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                setTokenizerState(Tokenizer.State.DATA);
                setForceQuirks();
                final DOCTYPEToken doctypeToken = clearDOCTYPEToken();
                return one(doctypeToken);
            } else {
                throw new ParseErrorException("Unexpected end-of-file before DOCTYPE public identifier.");
            }
        } // break;
        default: {
            if (isAllowParseErrors()) {
                setForceQuirks();
                setTokenizerState(Tokenizer.State.BOGUS_DOCTYPE);
                return NOTHING;
            } else {
                throw new ParseErrorException("Unexpected character before DOCTYPE public identifier: " + (char) ch);
            }
        } // break;
        }
    }
    
}
