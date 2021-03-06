package org.silnith.parser.html5.lexical;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.state.AfterAttributeNameState;
import org.silnith.parser.html5.lexical.state.AfterAttributeValueQuotedState;
import org.silnith.parser.html5.lexical.state.AfterDOCTYPENameState;
import org.silnith.parser.html5.lexical.state.AfterDOCTYPEPublicIdentifierState;
import org.silnith.parser.html5.lexical.state.AfterDOCTYPEPublicKeywordState;
import org.silnith.parser.html5.lexical.state.AfterDOCTYPESystemIdentifierState;
import org.silnith.parser.html5.lexical.state.AfterDOCTYPESystemKeywordState;
import org.silnith.parser.html5.lexical.state.AttributeNameState;
import org.silnith.parser.html5.lexical.state.AttributeValueDoubleQuotedState;
import org.silnith.parser.html5.lexical.state.AttributeValueSingleQuotedState;
import org.silnith.parser.html5.lexical.state.AttributeValueUnquotedState;
import org.silnith.parser.html5.lexical.state.BeforeAttributeNameState;
import org.silnith.parser.html5.lexical.state.BeforeAttributeValueState;
import org.silnith.parser.html5.lexical.state.BeforeDOCTYPENameState;
import org.silnith.parser.html5.lexical.state.BeforeDOCTYPEPublicIdentifierState;
import org.silnith.parser.html5.lexical.state.BeforeDOCTYPESystemIdentifierState;
import org.silnith.parser.html5.lexical.state.BetweenDOCTYPEPublicAndSystemIdentifiersState;
import org.silnith.parser.html5.lexical.state.BogusCommentState;
import org.silnith.parser.html5.lexical.state.BogusDOCTYPEState;
import org.silnith.parser.html5.lexical.state.CDATASectionState;
import org.silnith.parser.html5.lexical.state.CharacterReferenceInDataState;
import org.silnith.parser.html5.lexical.state.CharacterReferenceInRCDATAState;
import org.silnith.parser.html5.lexical.state.CommentEndBangState;
import org.silnith.parser.html5.lexical.state.CommentEndDashState;
import org.silnith.parser.html5.lexical.state.CommentEndState;
import org.silnith.parser.html5.lexical.state.CommentStartDashState;
import org.silnith.parser.html5.lexical.state.CommentStartState;
import org.silnith.parser.html5.lexical.state.CommentState;
import org.silnith.parser.html5.lexical.state.DOCTYPENameState;
import org.silnith.parser.html5.lexical.state.DOCTYPEPublicIdentifierDoubleQuotedState;
import org.silnith.parser.html5.lexical.state.DOCTYPEPublicIdentifierSingleQuotedState;
import org.silnith.parser.html5.lexical.state.DOCTYPEState;
import org.silnith.parser.html5.lexical.state.DOCTYPESystemIdentifierDoubleQuotedState;
import org.silnith.parser.html5.lexical.state.DOCTYPESystemIdentifierSingleQuotedState;
import org.silnith.parser.html5.lexical.state.DataState;
import org.silnith.parser.html5.lexical.state.EndTagOpenState;
import org.silnith.parser.html5.lexical.state.MarkupDeclarationOpenState;
import org.silnith.parser.html5.lexical.state.PLAINTEXTState;
import org.silnith.parser.html5.lexical.state.RAWTEXTEndTagNameState;
import org.silnith.parser.html5.lexical.state.RAWTEXTEndTagOpenState;
import org.silnith.parser.html5.lexical.state.RAWTEXTLessThanSignState;
import org.silnith.parser.html5.lexical.state.RAWTEXTState;
import org.silnith.parser.html5.lexical.state.RCDATAEndTagNameState;
import org.silnith.parser.html5.lexical.state.RCDATAEndTagOpenState;
import org.silnith.parser.html5.lexical.state.RCDATALessThanSignState;
import org.silnith.parser.html5.lexical.state.RCDATAState;
import org.silnith.parser.html5.lexical.state.ScriptDataDoubleEscapeEndState;
import org.silnith.parser.html5.lexical.state.ScriptDataDoubleEscapeStartState;
import org.silnith.parser.html5.lexical.state.ScriptDataDoubleEscapedDashDashState;
import org.silnith.parser.html5.lexical.state.ScriptDataDoubleEscapedDashState;
import org.silnith.parser.html5.lexical.state.ScriptDataDoubleEscapedLessThanSignState;
import org.silnith.parser.html5.lexical.state.ScriptDataDoubleEscapedState;
import org.silnith.parser.html5.lexical.state.ScriptDataEndTagNameState;
import org.silnith.parser.html5.lexical.state.ScriptDataEndTagOpenState;
import org.silnith.parser.html5.lexical.state.ScriptDataEscapeStartDashState;
import org.silnith.parser.html5.lexical.state.ScriptDataEscapeStartState;
import org.silnith.parser.html5.lexical.state.ScriptDataEscapedDashDashState;
import org.silnith.parser.html5.lexical.state.ScriptDataEscapedDashState;
import org.silnith.parser.html5.lexical.state.ScriptDataEscapedEndTagNameState;
import org.silnith.parser.html5.lexical.state.ScriptDataEscapedEndTagOpenState;
import org.silnith.parser.html5.lexical.state.ScriptDataEscapedLessThanSignState;
import org.silnith.parser.html5.lexical.state.ScriptDataEscapedState;
import org.silnith.parser.html5.lexical.state.ScriptDataLessThanSignState;
import org.silnith.parser.html5.lexical.state.ScriptDataState;
import org.silnith.parser.html5.lexical.state.SelfClosingStartTagState;
import org.silnith.parser.html5.lexical.state.TagNameState;
import org.silnith.parser.html5.lexical.state.TagOpenState;
import org.silnith.parser.html5.lexical.state.TokenizerState;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.DOCTYPEToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Tokenizes an input stream.
 * <p>
 * Implementations must act as if they used the following state machine to tokenize HTML. The state machine must start in the data state. Most states consume a single character, which may have various side-effects, and either switches the state machine to a new state to reconsume the same character, or switches it to a new state to consume the next character, or stays in the same state to consume the next character. Some states have more complicated behavior and can consume several characters before switching to another state. In some cases, the tokenizer state is also changed by the tree construction stage.
 * <p>
 * The exact behavior of certain states depends on the insertion mode and the stack of open elements. Certain states also use a temporary buffer to track progress.
 * <p>
 * The output of the tokenization step is a series of zero or more of the following tokens: DOCTYPE, start tag, end tag, comment, character, end-of-file. DOCTYPE tokens have a name, a public identifier, a system identifier, and a force-quirks flag. When a DOCTYPE token is created, its name, public identifier, and system identifier must be marked as missing (which is a distinct state from the empty string), and the force-quirks flag must be set to off (its other state is on). Start and end tag tokens have a tag name, a self-closing flag, and a list of attributes, each of which has a name and a value. When a start or end tag token is created, its self-closing flag must be unset (its other state is that it be set), and its attributes list must be empty. Comment and character tokens have data.
 * <p>
 * When a token is emitted, it must immediately be handled by the tree construction stage. The tree construction stage can affect the state of the tokenization stage, and can insert additional characters into the stream. (For example, the script element can result in scripts executing and using the dynamic markup insertion APIs to insert characters into the stream being tokenized.)
 * <p>
 * When a start tag token is emitted with its self-closing flag set, if the flag is not acknowledged when it is processed by the tree construction stage, that is a parse error.
 * <p>
 * When an end tag token is emitted with attributes, that is a parse error.
 * <p>
 * When an end tag token is emitted with its self-closing flag set, that is a parse error.
 * <p>
 * An appropriate end tag token is an end tag token whose tag name matches the tag name of the last start tag to have been emitted from this tokenizer, if any. If no start tag has been emitted from this tokenizer, then no end tag token is appropriate.
 * <p>
 * Before each step of the tokenizer, the user agent must first check the parser pause flag. If it is true, then the tokenizer must abort the processing of any nested invocations of the tokenizer, yielding control back to the caller.
 * <p>
 * The tokenizer state machine consists of the states defined in the following subsections.
 *
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#tokenization">8.2.4 Tokenization</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class Tokenizer {
    
    public enum State {
        DATA,
        CHARACTER_REFERENCE_IN_DATA,
        RCDATA,
        CHARACTER_REFERENCE_IN_RCDATA,
        RAWTEXT,
        /**
         * @see org.silnith.parser.html5.lexical.state.ScriptDataState
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#script-data-state">8.2.4.6 Script data state</a>
         */
        SCRIPT_DATA,
        PLAINTEXT,
        TAG_OPEN,
        END_TAG_OPEN,
        TAG_NAME,
        RCDATA_LESS_THAN_SIGN,
        RCDATA_END_TAG_OPEN,
        RCDATA_END_TAG_NAME,
        RAWTEXT_LESS_THAN_SIGN,
        RAWTEXT_END_TAG_OPEN,
        RAWTEXT_END_TAG_NAME,
        SCRIPT_DATA_LESS_THAN_SIGN,
        SCRIPT_DATA_END_TAG_OPEN,
        SCRIPT_DATA_END_TAG_NAME,
        SCRIPT_DATA_ESCAPE_START,
        SCRIPT_DATA_ESCAPE_START_DASH,
        SCRIPT_DATA_ESCAPED,
        SCRIPT_DATA_ESCAPED_DASH,
        SCRIPT_DATA_ESCAPED_DASH_DASH,
        SCRIPT_DATA_ESCAPED_LESS_THAN_SIGN,
        SCRIPT_DATA_ESCAPED_END_TAG_OPEN,
        SCRIPT_DATA_ESCAPED_END_TAG_NAME,
        SCRIPT_DATA_DOUBLE_ESCAPE_START,
        SCRIPT_DATA_DOUBLE_ESCAPED,
        SCRIPT_DATA_DOUBLE_ESCAPED_DASH,
        SCRIPT_DATA_DOUBLE_ESCAPED_DASH_DASH,
        SCRIPT_DATA_DOUBLE_ESCAPED_LESS_THAN_SIGN,
        SCRIPT_DATA_DOUBLE_ESCAPE_END,
        BEFORE_ATTRIBUTE_NAME,
        ATTRIBUTE_NAME,
        AFTER_ATTRIBUTE_NAME,
        BEFORE_ATTRIBUTE_VALUE,
        ATTRIBUTE_VALUE_DOUBLE_QUOTED,
        ATTRIBUTE_VALUE_SINGLE_QUOTED,
        ATTRIBUTE_VALUE_UNQUOTED,
        AFTER_ATTRIBUTE_VALUE_QUOTED,
        SELF_CLOSING_START_TAG,
        BOGUS_COMMENT,
        MARKUP_DECLARATION_OPEN,
        COMMENT_START,
        COMMENT_START_DASH,
        COMMENT,
        COMMENT_END_DASH,
        COMMENT_END,
        COMMENT_END_BANG,
        DOCTYPE,
        BEFORE_DOCTYPE_NAME,
        DOCTYPE_NAME,
        AFTER_DOCTYPE_NAME,
        AFTER_DOCTYPE_PUBLIC_KEYWORD,
        BEFORE_DOCTYPE_PUBLIC_IDENTIFIER,
        DOCTYPE_PUBLIC_IDENTIFIER_DOUBLE_QUOTED,
        DOCTYPE_PUBLIC_IDENTIFIER_SINGLE_QUOTED,
        AFTER_DOCTYPE_PUBLIC_IDENTIFIER,
        BETWEEN_DOCTYPE_PUBLIC_AND_SYSTEM_IDENTIFIERS,
        AFTER_DOCTYPE_SYSTEM_KEYWORD,
        BEFORE_DOCTYPE_SYSTEM_IDENTIFIER,
        DOCTYPE_SYSTEM_IDENTIFIER_DOUBLE_QUOTED,
        DOCTYPE_SYSTEM_IDENTIFIER_SINGLE_QUOTED,
        AFTER_DOCTYPE_SYSTEM_IDENTIFIER,
        BOGUS_DOCTYPE,
        CDATA_SECTION
    }
    
    private final PushbackReader in;
    
    private State state;
    
    private final Map<State, TokenizerState> stateTokenizer;
    
    private boolean allowParseErrors;
    
    private final Queue<Token> tokenQueue;
    
    private StartTagToken lastStartTag;
    
    private boolean emittedSelfClosingStartTag;
    
    public Tokenizer(final Reader in) {
        super();
        if (in == null) {
            throw new NullPointerException();
        }
        this.state = State.DATA;
        this.stateTokenizer = new EnumMap<>(State.class);
        this.allowParseErrors = false;
        this.tokenQueue = new ArrayDeque<>();
        this.lastStartTag = null;
        this.emittedSelfClosingStartTag = false;
        
        this.stateTokenizer.put(State.DATA, new DataState(this));
        this.stateTokenizer.put(State.CHARACTER_REFERENCE_IN_DATA, new CharacterReferenceInDataState(this));
        this.stateTokenizer.put(State.RCDATA, new RCDATAState(this));
        this.stateTokenizer.put(State.CHARACTER_REFERENCE_IN_RCDATA, new CharacterReferenceInRCDATAState(this));
        this.stateTokenizer.put(State.RAWTEXT, new RAWTEXTState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA, new ScriptDataState(this));
        this.stateTokenizer.put(State.PLAINTEXT, new PLAINTEXTState(this));
        this.stateTokenizer.put(State.TAG_OPEN, new TagOpenState(this));
        this.stateTokenizer.put(State.END_TAG_OPEN, new EndTagOpenState(this));
        this.stateTokenizer.put(State.TAG_NAME, new TagNameState(this));
        this.stateTokenizer.put(State.RCDATA_LESS_THAN_SIGN, new RCDATALessThanSignState(this));
        this.stateTokenizer.put(State.RCDATA_END_TAG_OPEN, new RCDATAEndTagOpenState(this));
        this.stateTokenizer.put(State.RCDATA_END_TAG_NAME, new RCDATAEndTagNameState(this));
        this.stateTokenizer.put(State.RAWTEXT_LESS_THAN_SIGN, new RAWTEXTLessThanSignState(this));
        this.stateTokenizer.put(State.RAWTEXT_END_TAG_OPEN, new RAWTEXTEndTagOpenState(this));
        this.stateTokenizer.put(State.RAWTEXT_END_TAG_NAME, new RAWTEXTEndTagNameState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_LESS_THAN_SIGN, new ScriptDataLessThanSignState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_END_TAG_OPEN, new ScriptDataEndTagOpenState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_END_TAG_NAME, new ScriptDataEndTagNameState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_ESCAPE_START, new ScriptDataEscapeStartState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_ESCAPE_START_DASH, new ScriptDataEscapeStartDashState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_ESCAPED, new ScriptDataEscapedState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_ESCAPED_DASH, new ScriptDataEscapedDashState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_ESCAPED_DASH_DASH, new ScriptDataEscapedDashDashState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_ESCAPED_LESS_THAN_SIGN, new ScriptDataEscapedLessThanSignState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_ESCAPED_END_TAG_OPEN, new ScriptDataEscapedEndTagOpenState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_ESCAPED_END_TAG_NAME, new ScriptDataEscapedEndTagNameState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_DOUBLE_ESCAPE_START, new ScriptDataDoubleEscapeStartState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_DOUBLE_ESCAPED, new ScriptDataDoubleEscapedState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_DOUBLE_ESCAPED_DASH, new ScriptDataDoubleEscapedDashState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_DOUBLE_ESCAPED_DASH_DASH,
                new ScriptDataDoubleEscapedDashDashState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_DOUBLE_ESCAPED_LESS_THAN_SIGN,
                new ScriptDataDoubleEscapedLessThanSignState(this));
        this.stateTokenizer.put(State.SCRIPT_DATA_DOUBLE_ESCAPE_END, new ScriptDataDoubleEscapeEndState(this));
        this.stateTokenizer.put(State.BEFORE_ATTRIBUTE_NAME, new BeforeAttributeNameState(this));
        this.stateTokenizer.put(State.ATTRIBUTE_NAME, new AttributeNameState(this));
        this.stateTokenizer.put(State.AFTER_ATTRIBUTE_NAME, new AfterAttributeNameState(this));
        this.stateTokenizer.put(State.BEFORE_ATTRIBUTE_VALUE, new BeforeAttributeValueState(this));
        this.stateTokenizer.put(State.ATTRIBUTE_VALUE_DOUBLE_QUOTED, new AttributeValueDoubleQuotedState(this));
        this.stateTokenizer.put(State.ATTRIBUTE_VALUE_SINGLE_QUOTED, new AttributeValueSingleQuotedState(this));
        this.stateTokenizer.put(State.ATTRIBUTE_VALUE_UNQUOTED, new AttributeValueUnquotedState(this));
        this.stateTokenizer.put(State.AFTER_ATTRIBUTE_VALUE_QUOTED, new AfterAttributeValueQuotedState(this));
        this.stateTokenizer.put(State.SELF_CLOSING_START_TAG, new SelfClosingStartTagState(this));
        this.stateTokenizer.put(State.BOGUS_COMMENT, new BogusCommentState(this));
        this.stateTokenizer.put(State.MARKUP_DECLARATION_OPEN, new MarkupDeclarationOpenState(this));
        this.stateTokenizer.put(State.COMMENT_START, new CommentStartState(this));
        this.stateTokenizer.put(State.COMMENT_START_DASH, new CommentStartDashState(this));
        this.stateTokenizer.put(State.COMMENT, new CommentState(this));
        this.stateTokenizer.put(State.COMMENT_END_DASH, new CommentEndDashState(this));
        this.stateTokenizer.put(State.COMMENT_END, new CommentEndState(this));
        this.stateTokenizer.put(State.COMMENT_END_BANG, new CommentEndBangState(this));
        this.stateTokenizer.put(State.DOCTYPE, new DOCTYPEState(this));
        this.stateTokenizer.put(State.BEFORE_DOCTYPE_NAME, new BeforeDOCTYPENameState(this));
        this.stateTokenizer.put(State.DOCTYPE_NAME, new DOCTYPENameState(this));
        this.stateTokenizer.put(State.AFTER_DOCTYPE_NAME, new AfterDOCTYPENameState(this));
        this.stateTokenizer.put(State.AFTER_DOCTYPE_PUBLIC_KEYWORD, new AfterDOCTYPEPublicKeywordState(this));
        this.stateTokenizer.put(State.BEFORE_DOCTYPE_PUBLIC_IDENTIFIER, new BeforeDOCTYPEPublicIdentifierState(this));
        this.stateTokenizer.put(State.DOCTYPE_PUBLIC_IDENTIFIER_DOUBLE_QUOTED,
                new DOCTYPEPublicIdentifierDoubleQuotedState(this));
        this.stateTokenizer.put(State.DOCTYPE_PUBLIC_IDENTIFIER_SINGLE_QUOTED,
                new DOCTYPEPublicIdentifierSingleQuotedState(this));
        this.stateTokenizer.put(State.AFTER_DOCTYPE_PUBLIC_IDENTIFIER, new AfterDOCTYPEPublicIdentifierState(this));
        this.stateTokenizer.put(State.BETWEEN_DOCTYPE_PUBLIC_AND_SYSTEM_IDENTIFIERS,
                new BetweenDOCTYPEPublicAndSystemIdentifiersState(this));
        this.stateTokenizer.put(State.AFTER_DOCTYPE_SYSTEM_KEYWORD, new AfterDOCTYPESystemKeywordState(this));
        this.stateTokenizer.put(State.BEFORE_DOCTYPE_SYSTEM_IDENTIFIER, new BeforeDOCTYPESystemIdentifierState(this));
        this.stateTokenizer.put(State.DOCTYPE_SYSTEM_IDENTIFIER_DOUBLE_QUOTED,
                new DOCTYPESystemIdentifierDoubleQuotedState(this));
        this.stateTokenizer.put(State.DOCTYPE_SYSTEM_IDENTIFIER_SINGLE_QUOTED,
                new DOCTYPESystemIdentifierSingleQuotedState(this));
        this.stateTokenizer.put(State.AFTER_DOCTYPE_SYSTEM_IDENTIFIER, new AfterDOCTYPESystemIdentifierState(this));
        this.stateTokenizer.put(State.BOGUS_DOCTYPE, new BogusDOCTYPEState(this));
        this.stateTokenizer.put(State.CDATA_SECTION, new CDATASectionState(this));
        
        int maxPushback = 1;
        for (final TokenizerState state : this.stateTokenizer.values()) {
            maxPushback = Math.max(maxPushback, state.getMaxPushback());
        }
        this.in = new PushbackReader(new InputStreamPreprocessor(in), maxPushback);
    }
    
    public void setAllowParseErrors(final boolean allowParseErrors) {
        this.allowParseErrors = allowParseErrors;
    }
    
    public boolean isAllowParseErrors() {
        return allowParseErrors;
    }
    
    public int consume() throws IOException {
        return in.read();
    }
    
    public int consume(final char[] buf, final int len) throws IOException {
        if (len > buf.length) {
            throw new IllegalStateException();
        }
        return in.read(buf, 0, len);
    }
    
    public int consume(final char[] buf, final int offset, final int len) throws IOException {
        if (len + offset > buf.length) {
            throw new IllegalStateException();
        }
        return in.read(buf, offset, len);
    }
    
    public void unconsume(final int ch) throws IOException {
        in.unread(ch);
    }
    
    public void setState(final State state) {
        if (this.state == State.ATTRIBUTE_NAME && state != State.ATTRIBUTE_NAME) {
            pendingToken.validateCurrentAttributeNameUnique(allowParseErrors);
        }
        this.state = state;
    }
    
    public State getState() {
        return state;
    }
    
    public Token getNextToken() throws IOException {
        if (emittedSelfClosingStartTag && !isAllowParseErrors()) {
            throw new ParseErrorException("Emitted a self-closing start tag that was not acknowledged.");
        }
        
        int count = 0;
        while (tokenQueue.isEmpty()) {
            tokenQueue.addAll(dispatch());
            // TODO: Remove this once I'm confident in the lexer.
            if (count++ > 8192) {
                throw new ParseErrorException("Too many stack frames!");
            }
        }
        final Token nextToken = tokenQueue.remove();
        
        emittedSelfClosingStartTag = false;
        if (nextToken.getType() == Token.Type.START_TAG) {
            lastStartTag = (StartTagToken) nextToken;
            
            if (lastStartTag.isSelfClosing()) {
                emittedSelfClosingStartTag = true;
            }
        } else if (nextToken.getType() == Token.Type.END_TAG) {
            final EndTagToken endTagToken = (EndTagToken) nextToken;
            if ( !endTagToken.getAttributes().isEmpty()) {
                throw new ParseErrorException("Emitted end tag token with attributes: " + endTagToken);
            }
            if (endTagToken.isSelfClosing()) {
                throw new ParseErrorException("Emitted end tag token with the self-closing flag set: " + endTagToken);
            }
        }
        
        return nextToken;
    }
    
    public void acknowledgeSelfClosingFlag() {
        if ( !emittedSelfClosingStartTag && !isAllowParseErrors()) {
            throw new ParseErrorException(
                    "Acknowledged a self-closing tag when the last tag to be emitted did not have the self-closing flag set.");
        }
        emittedSelfClosingStartTag = false;
    }
    
    protected List<Token> dispatch() throws IOException {
        final TokenizerState tokenizerState = stateTokenizer.get(state);
        assert tokenizerState != null : state;
        
        return tokenizerState.getNextTokens();
    }
    
    private TagToken pendingToken;
    
    public TagToken getPendingToken() {
        return pendingToken;
    }
    
    public void setPendingToken(final TagToken token) {
        pendingToken = token;
    }
    
    public void clearPendingToken() {
        pendingToken = null;
    }
    
    private StringBuilder temporaryBuffer;
    
    public void createTemporaryBuffer() {
        this.temporaryBuffer = new StringBuilder();
    }
    
    public void createTemporaryBuffer(final char character) {
        createTemporaryBuffer();
        appendToTemporaryBuffer(character);
    }
    
    public void appendToTemporaryBuffer(final char character) {
        this.temporaryBuffer.append(character);
    }
    
    public String getTemporaryBuffer() {
        return temporaryBuffer.toString();
    }
    
    public void clearTemporaryBuffer() {
        temporaryBuffer = null;
    }
    
    private CommentToken commentToken = null;
    
    public void createCommentToken() {
        this.commentToken = new CommentToken();
    }
    
    public CommentToken getCommentToken() {
        return commentToken;
    }
    
    public void clearCommentToken() {
        commentToken = null;
    }
    
    public void appendToCommentToken(final char... ch) {
        commentToken.append(ch);
    }
    
    private DOCTYPEToken doctypeToken;
    
    public DOCTYPEToken createDOCTYPEToken() {
        doctypeToken = new DOCTYPEToken();
        return doctypeToken;
    }
    
    public DOCTYPEToken getDOCTYPEToken() {
        return doctypeToken;
    }
    
    public void clearDOCTYPEToken() {
        doctypeToken = null;
    }
    
    /**
     * An appropriate end tag token is an end tag token whose tag name matches the tag name of the last start tag to have been emitted from this tokenizer, if any. If no start tag has been emitted from this tokenizer, then no end tag token is appropriate.
     * 
     * @param tagToken the end tag token to check for appropriateness
     * @return whether the end tag is appropriate
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#appropriate-end-tag-token">appropriate end tag token</a>
     */
    public boolean isAppropriateEndTagToken(final TagToken tagToken) {
        if (tagToken == null) {
            throw new IllegalStateException("The pending token is null.");
        }
        if (tagToken.getType() != Token.Type.END_TAG) {
            throw new IllegalStateException(
                    "The pending token should be an end tag token, instead it is: " + tagToken.getType());
        }
        final String startTagName = lastStartTag.getTagName();
        final String endTagName = tagToken.getTagName();
        return (endTagName.equals(startTagName));
    }
    
}
