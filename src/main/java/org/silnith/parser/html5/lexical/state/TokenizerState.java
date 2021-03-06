package org.silnith.parser.html5.lexical.state;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.DOCTYPEToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public abstract class TokenizerState {
    
    /**
     * The value returned by {@link java.io.InputStream#read()} and
     * {@link java.io.Reader#read()} when the end of the input has been reached.
     */
    protected static final int EOF = -1;
    
    /**
     * A convenient constant when a tokenizer state wishes to return nothing at
     * all to the tokenizer.
     */
    protected static final List<Token> NOTHING = Collections.emptyList();
    
    private final Tokenizer tokenizer;
    
    public TokenizerState(final Tokenizer tokenizer) {
        super();
        this.tokenizer = tokenizer;
    }
    
    /**
     * Returns the maximum number of characters this tokenizer state will push
     * back to the tokenizer to be reconsumed by a different state. The
     * tokenizer is responsible for making sure it can handle unconsuming this
     * many characters, and the state is responsible for never pushing back more
     * than this number of characters.
     * <p>
     * The value returned by this method must be constant for the lifetime of
     * the tokenizer.
     * 
     * @return the maximum number of characters this state will push back
     */
    public abstract int getMaxPushback();
    
    /**
     * Consume the next character from the input stream (held by the tokenizer)
     * and return any and all resulting tokens. This may be an empty list. It
     * may never be {@code null}.
     * 
     * @return a list of tokens
     * @throws IOException if there was an error reading the input stream
     */
    public abstract List<Token> getNextTokens() throws IOException;
    
    /**
     * A convenience function for when a state wants to return only a single
     * token.
     * 
     * @param token the token to return
     * @return a list containing only that token
     */
    protected List<Token> one(final Token token) {
        return Collections.singletonList(token);
    }
    
    /**
     * Returns the lowercase version of the given uppercase ASCII letter. This
     * is only used in the lexer because HTML is defined in terms of ASCII
     * letters, it is completely inappropriate for any natural language
     * including English. For natural languages, use
     * {@link String#toLowerCase(java.util.Locale)}.
     * 
     * @param ch the uppercase ASCII letter
     * @return the lowercase ASCII letter
     */
    protected char toLower(final char ch) {
        return (char) (ch + 0x0020);
    }
    
    /**
     * Consume the next character from the input stream.
     * 
     * @return the next character, or -1 if there is no more input
     * @throws IOException if there was an error reading the input stream
     */
    protected int consume() throws IOException {
        return tokenizer.consume();
    }
    
    protected int consume(final char[] buf, final int len) throws IOException {
        return tokenizer.consume(buf, len);
    }
    
    protected int consume(final char[] buf, final int offset, final int len) throws IOException {
        return tokenizer.consume(buf, offset, len);
    }
    
    protected void unconsume(final int ch) throws IOException {
        if (ch != -1) {
            tokenizer.unconsume(ch);
        }
    }
    
    /**
     * Whether the tokenizer state should correct for parse errors gracefully or
     * throw an exception.
     * 
     * @return whether to handle parse errors gracefully
     */
    protected boolean isAllowParseErrors() {
        return tokenizer.isAllowParseErrors();
    }
    
    /**
     * Switches the tokenizer to the given state.
     * 
     * @param state the new state for the tokenizer
     */
    protected void setTokenizerState(final Tokenizer.State state) {
        tokenizer.setState(state);
    }
    
    /**
     * Returns whether the tokenizer currently has a pending tag token.
     * 
     * @return whether the tokenizer currently has a pending tag token
     */
    protected boolean hasPendingTag() {
        return tokenizer.getPendingToken() != null;
    }
    
    protected void setPendingTag(final TagToken tagToken) {
        tokenizer.setPendingToken(tagToken);
    }
    
    protected void appendToTagName(final char ch) {
        tokenizer.getPendingToken().appendToTagName(ch);
    }
    
    protected void createAttribute(final char firstCharacterOfName) {
        tokenizer.getPendingToken().createNewAttribute().appendToName(firstCharacterOfName);
    }
    
    protected void appendToAttributeName(final char ch) {
        tokenizer.getPendingToken().getCurrentAttribute().appendToName(ch);
    }
    
    protected void appendToAttributeValue(final char ch) {
        tokenizer.getPendingToken().getCurrentAttribute().appendToValue(ch);
    }
    
    /**
     * Clears any pending token in the tokenizer. Returns whatever was cleared.
     * 
     * @return the pending token that has been cleared
     */
    protected TagToken clearPendingTag() {
        final TagToken pendingToken = tokenizer.getPendingToken();
        tokenizer.clearPendingToken();
        return pendingToken;
    }
    
    /**
     * Returns {@code true} if there is a pending tag token, and it is of type
     * {@link org.silnith.parser.html5.lexical.token.StartTagToken}.
     * 
     * @return {@code true} if there is a pending start tag token
     */
    protected boolean isPendingStartTag() {
        return hasPendingTag() && tokenizer.getPendingToken().getType() == Token.Type.START_TAG;
    }
    
    /**
     * Returns {@code true} if there is a pending tag token, and it is of type
     * {@link org.silnith.parser.html5.lexical.token.EndTagToken}.
     * 
     * @return {@code true} if there is a pending end tag token
     */
    protected boolean isPendingEndTag() {
        return hasPendingTag() && tokenizer.getPendingToken().getType() == Token.Type.END_TAG;
    }
    
    /**
     * An appropriate end tag token is an end tag token whose tag name matches
     * the tag name of the last start tag to have been emitted from this
     * tokenizer, if any. If no start tag has been emitted from this tokenizer,
     * then no end tag token is appropriate.
     * 
     * @return whether the end tag is appropriate
     * @see Tokenizer#isAppropriateEndTagToken(TagToken)
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#appropriate-end-tag-token">appropriate end tag token</a>
     */
    protected boolean isAppropriateEndTag() {
        return tokenizer.isAppropriateEndTagToken(tokenizer.getPendingToken());
    }
    
    protected void createDOCTYPE(final char firstLetterOfName) {
        final DOCTYPEToken doctypeToken = tokenizer.createDOCTYPEToken();
        doctypeToken.setName(firstLetterOfName);
    }
    
    protected void appendToDOCTYPEName(final char ch) {
        tokenizer.getDOCTYPEToken().appendToName(ch);
    }
    
    protected void createPublicIdentifier() {
        tokenizer.getDOCTYPEToken().setPublicIdentifier("");
    }
    
    protected void appendToPublicIdentifier(final char ch) {
        tokenizer.getDOCTYPEToken().appendToPublicIdentifier(ch);
    }
    
    protected void createSystemIdentifier() {
        tokenizer.getDOCTYPEToken().setSystemIdentifier("");
    }
    
    protected void appendToSystemIdentifier(final char ch) {
        tokenizer.getDOCTYPEToken().appendToSystemIdentifier(ch);
    }
    
    protected void setForceQuirks() {
        tokenizer.getDOCTYPEToken().setForceQuirks();
    }
    
    protected DOCTYPEToken clearDOCTYPEToken() {
        final DOCTYPEToken doctypeToken = tokenizer.getDOCTYPEToken();
        tokenizer.clearDOCTYPEToken();
        return doctypeToken;
    }
    
    protected void createCommentToken() {
        tokenizer.createCommentToken();
    }
    
    protected void appendToCommentToken(final char... chars) {
        tokenizer.appendToCommentToken(chars);
    }
    
    protected CommentToken clearCommentToken() {
        final CommentToken commentToken = tokenizer.getCommentToken();
        tokenizer.clearCommentToken();
        return commentToken;
    }
    
    protected void createTemporaryBuffer() {
        tokenizer.createTemporaryBuffer();
    }
    
    protected String getTemporaryBuffer() {
        return tokenizer.getTemporaryBuffer();
    }
    
    protected void appendToTemporaryBuffer(final char ch) {
        tokenizer.appendToTemporaryBuffer(ch);
    }
    
    /**
     * Clears the temporary buffer and returns the previous contents.
     * 
     * @return the content of the temporary buffer before being cleared
     */
    protected String clearTemporaryBuffer() {
        final String temporaryBuffer = tokenizer.getTemporaryBuffer();
        tokenizer.clearTemporaryBuffer();
        return temporaryBuffer;
    }
    
}
