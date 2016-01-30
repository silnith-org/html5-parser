package org.silnith.parser.html5.lexical.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.TagToken.Attribute;
import org.silnith.parser.html5.lexical.token.Token;


public class TagNameStateTest {
    
    private Tokenizer tokenizer;
    
    private TagNameState tagNameState;
    
    @Test
    public void testGetNextTokensCharacterTabulation() throws IOException {
        tokenizer = new Tokenizer(new StringReader("\t"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.BEFORE_ATTRIBUTE_NAME, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensLineFeed() throws IOException {
        tokenizer = new Tokenizer(new StringReader("\n"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.BEFORE_ATTRIBUTE_NAME, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensFormFeed() throws IOException {
        tokenizer = new Tokenizer(new StringReader("\f"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.BEFORE_ATTRIBUTE_NAME, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensSpace() throws IOException {
        tokenizer = new Tokenizer(new StringReader(" "));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.BEFORE_ATTRIBUTE_NAME, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensSolidus() throws IOException {
        tokenizer = new Tokenizer(new StringReader("/"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.SELF_CLOSING_START_TAG, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensGreaterThanSignStartTag() throws IOException {
        tokenizer = new Tokenizer(new StringReader(">"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        final TagToken pendingToken = new StartTagToken();
        pendingToken.appendToTagName('f');
        pendingToken.appendToTagName('o');
        pendingToken.appendToTagName('o');
        tokenizer.setPendingToken(pendingToken);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertNotNull(token);
        assertEquals(Token.Type.START_TAG, token.getType());
        final StartTagToken startTagToken = (StartTagToken) token;
        assertEquals("foo", startTagToken.getTagName());
        final List<Attribute> attributes = startTagToken.getAttributes();
        assertNotNull(attributes);
        assertTrue(attributes.isEmpty());
        
        assertEquals(Tokenizer.State.DATA, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensGreaterThanSignEndTag() throws IOException {
        tokenizer = new Tokenizer(new StringReader(">"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        final TagToken pendingToken = new EndTagToken();
        pendingToken.appendToTagName('f');
        pendingToken.appendToTagName('o');
        pendingToken.appendToTagName('o');
        tokenizer.setPendingToken(pendingToken);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertNotNull(token);
        assertEquals(Token.Type.END_TAG, token.getType());
        final EndTagToken endTagToken = (EndTagToken) token;
        assertEquals("foo", endTagToken.getTagName());
        final List<Attribute> attributes = endTagToken.getAttributes();
        assertNotNull(attributes);
        assertTrue(attributes.isEmpty());
        
        assertEquals(Tokenizer.State.DATA, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensUppercaseLetterStartTag() throws IOException {
        tokenizer = new Tokenizer(new StringReader("K"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        final TagToken pendingToken = new StartTagToken();
        pendingToken.appendToTagName('f');
        pendingToken.appendToTagName('o');
        pendingToken.appendToTagName('o');
        tokenizer.setPendingToken(pendingToken);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
        
        assertEquals("fook", pendingToken.getTagName());
    }
    
    @Test
    public void testGetNextTokensLowercaseLetterStartTag() throws IOException {
        tokenizer = new Tokenizer(new StringReader("k"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        final TagToken pendingToken = new StartTagToken();
        pendingToken.appendToTagName('f');
        pendingToken.appendToTagName('o');
        pendingToken.appendToTagName('o');
        tokenizer.setPendingToken(pendingToken);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
        
        assertEquals("fook", pendingToken.getTagName());
    }
    
    @Test(expected = ParseErrorException.class)
    public void testGetNextTokensNullCharacterStartTag() throws IOException {
        tokenizer = new Tokenizer(new StringReader("\u0000"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        final TagToken pendingToken = new StartTagToken();
        pendingToken.appendToTagName('f');
        pendingToken.appendToTagName('o');
        pendingToken.appendToTagName('o');
        tokenizer.setPendingToken(pendingToken);
        
        tagNameState = new TagNameState(tokenizer);
        
        tagNameState.getNextTokens();
    }
    
    @Test
    public void testGetNextTokensNullCharacterStartTagAllowParseErrors() throws IOException {
        tokenizer = new Tokenizer(new StringReader("\u0000"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(true);
        
        final TagToken pendingToken = new StartTagToken();
        pendingToken.appendToTagName('f');
        pendingToken.appendToTagName('o');
        pendingToken.appendToTagName('o');
        tokenizer.setPendingToken(pendingToken);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
        
        assertEquals("foo\uFFFD", pendingToken.getTagName());
    }
    
    @Test(expected = ParseErrorException.class)
    public void testGetNextTokensEndOfFileStartTag() throws IOException {
        tokenizer = new Tokenizer(new StringReader(""));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        final TagToken pendingToken = new StartTagToken();
        pendingToken.appendToTagName('f');
        pendingToken.appendToTagName('o');
        pendingToken.appendToTagName('o');
        tokenizer.setPendingToken(pendingToken);
        
        tagNameState = new TagNameState(tokenizer);
        
        tagNameState.getNextTokens();
    }
    
    @Test
    public void testGetNextTokensEndOfFileStartTagAllowParseErrors() throws IOException {
        tokenizer = new Tokenizer(new StringReader(""));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(true);
        
        final TagToken pendingToken = new StartTagToken();
        pendingToken.appendToTagName('f');
        pendingToken.appendToTagName('o');
        pendingToken.appendToTagName('o');
        tokenizer.setPendingToken(pendingToken);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.DATA, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensUnusualCharacterStartTag() throws IOException {
        tokenizer = new Tokenizer(new StringReader("\u201c"));
        tokenizer.setState(Tokenizer.State.TAG_NAME);
        tokenizer.setAllowParseErrors(false);
        
        final TagToken pendingToken = new StartTagToken();
        pendingToken.appendToTagName('f');
        pendingToken.appendToTagName('o');
        pendingToken.appendToTagName('o');
        tokenizer.setPendingToken(pendingToken);
        
        tagNameState = new TagNameState(tokenizer);
        
        final List<Token> tokens = tagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
        
        assertEquals("foo\u201c", pendingToken.getTagName());
    }
    
}
