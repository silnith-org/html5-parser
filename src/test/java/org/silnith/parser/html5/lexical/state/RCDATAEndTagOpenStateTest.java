package org.silnith.parser.html5.lexical.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;

public class RCDATAEndTagOpenStateTest {
    
    private Tokenizer tokenizer;
    
    private RCDATAEndTagOpenState rcdataEndTagOpenState;
    
    @Test
    public void testGetNextTokensLowercaseLetter() throws IOException {
        tokenizer = new Tokenizer(new StringReader("abc"));
        tokenizer.setState(Tokenizer.State.RCDATA_END_TAG_OPEN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataEndTagOpenState = new RCDATAEndTagOpenState(tokenizer);
        
        tokenizer.createTemporaryBuffer();
        
        final List<Token> tokens = rcdataEndTagOpenState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.RCDATA_END_TAG_NAME, tokenizer.getState());
        assertEquals('b', tokenizer.consume());
        assertEquals('c', tokenizer.consume());
        assertEquals(-1, tokenizer.consume());
        
        final TagToken pendingToken = tokenizer.getPendingToken();
        assertEquals(Token.Type.END_TAG, pendingToken.getType());
        assertEquals("a", pendingToken.getTagName());
        assertEquals("a", tokenizer.getTemporaryBuffer());
    }
    
    @Test
    public void testGetNextTokensLowercaseLetterEndOfFile() throws IOException {
        tokenizer = new Tokenizer(new StringReader("a"));
        tokenizer.setState(Tokenizer.State.RCDATA_END_TAG_OPEN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataEndTagOpenState = new RCDATAEndTagOpenState(tokenizer);
        
        tokenizer.createTemporaryBuffer();
        
        final List<Token> tokens = rcdataEndTagOpenState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.RCDATA_END_TAG_NAME, tokenizer.getState());
        assertEquals(-1, tokenizer.consume());
        
        final TagToken pendingToken = tokenizer.getPendingToken();
        assertEquals(Token.Type.END_TAG, pendingToken.getType());
        assertEquals("a", pendingToken.getTagName());
        assertEquals("a", tokenizer.getTemporaryBuffer());
    }
    
    @Test
    public void testGetNextTokensUppercaseLetter() throws IOException {
        tokenizer = new Tokenizer(new StringReader("ABC"));
        tokenizer.setState(Tokenizer.State.RCDATA_END_TAG_OPEN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataEndTagOpenState = new RCDATAEndTagOpenState(tokenizer);
        
        tokenizer.createTemporaryBuffer();
        
        final List<Token> tokens = rcdataEndTagOpenState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.RCDATA_END_TAG_NAME, tokenizer.getState());
        assertEquals('B', tokenizer.consume());
        assertEquals('C', tokenizer.consume());
        assertEquals(-1, tokenizer.consume());
        
        final TagToken pendingToken = tokenizer.getPendingToken();
        assertEquals(Token.Type.END_TAG, pendingToken.getType());
        assertEquals("a", pendingToken.getTagName());
        assertEquals("A", tokenizer.getTemporaryBuffer());
    }
    
    @Test
    public void testGetNextTokensUppercaseLetterEndOfFile() throws IOException {
        tokenizer = new Tokenizer(new StringReader("A"));
        tokenizer.setState(Tokenizer.State.RCDATA_END_TAG_OPEN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataEndTagOpenState = new RCDATAEndTagOpenState(tokenizer);
        
        tokenizer.createTemporaryBuffer();
        
        final List<Token> tokens = rcdataEndTagOpenState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.RCDATA_END_TAG_NAME, tokenizer.getState());
        assertEquals(-1, tokenizer.consume());
        
        final TagToken pendingToken = tokenizer.getPendingToken();
        assertEquals(Token.Type.END_TAG, pendingToken.getType());
        assertEquals("a", pendingToken.getTagName());
        assertEquals("A", tokenizer.getTemporaryBuffer());
    }
    
    @Test
    public void testGetNextTokensEndOfFile() throws IOException {
        tokenizer = new Tokenizer(new StringReader(""));
        tokenizer.setState(Tokenizer.State.RCDATA_END_TAG_OPEN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataEndTagOpenState = new RCDATAEndTagOpenState(tokenizer);
        
        tokenizer.createTemporaryBuffer();
        
        final List<Token> tokens = rcdataEndTagOpenState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(2, tokens.size());
        final Token firstToken = tokens.get(0);
        assertNotNull(firstToken);
        assertEquals(Token.Type.CHARACTER, firstToken.getType());
        final CharacterToken firstCharacter = (CharacterToken) firstToken;
        assertEquals('<', firstCharacter.getCharacter());
        final Token secondToken = tokens.get(1);
        assertNotNull(secondToken);
        assertEquals(Token.Type.CHARACTER, secondToken.getType());
        final CharacterToken secondCharacter = (CharacterToken) secondToken;
        assertEquals('/', secondCharacter.getCharacter());
        
        assertEquals(Tokenizer.State.RCDATA, tokenizer.getState());
        assertEquals(-1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensOtherCharacterEndOfFile() throws IOException {
        tokenizer = new Tokenizer(new StringReader("!"));
        tokenizer.setState(Tokenizer.State.RCDATA_END_TAG_OPEN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataEndTagOpenState = new RCDATAEndTagOpenState(tokenizer);
        
        tokenizer.createTemporaryBuffer();
        
        final List<Token> tokens = rcdataEndTagOpenState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(2, tokens.size());
        final Token firstToken = tokens.get(0);
        assertNotNull(firstToken);
        assertEquals(Token.Type.CHARACTER, firstToken.getType());
        final CharacterToken firstCharacter = (CharacterToken) firstToken;
        assertEquals('<', firstCharacter.getCharacter());
        final Token secondToken = tokens.get(1);
        assertNotNull(secondToken);
        assertEquals(Token.Type.CHARACTER, secondToken.getType());
        final CharacterToken secondCharacter = (CharacterToken) secondToken;
        assertEquals('/', secondCharacter.getCharacter());
        
        assertEquals(Tokenizer.State.RCDATA, tokenizer.getState());
        assertEquals('!', tokenizer.consume());
        assertEquals(-1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensOtherCharacter() throws IOException {
        tokenizer = new Tokenizer(new StringReader("!ab"));
        tokenizer.setState(Tokenizer.State.RCDATA_END_TAG_OPEN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataEndTagOpenState = new RCDATAEndTagOpenState(tokenizer);
        
        tokenizer.createTemporaryBuffer();
        
        final List<Token> tokens = rcdataEndTagOpenState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(2, tokens.size());
        final Token firstToken = tokens.get(0);
        assertNotNull(firstToken);
        assertEquals(Token.Type.CHARACTER, firstToken.getType());
        final CharacterToken firstCharacter = (CharacterToken) firstToken;
        assertEquals('<', firstCharacter.getCharacter());
        final Token secondToken = tokens.get(1);
        assertNotNull(secondToken);
        assertEquals(Token.Type.CHARACTER, secondToken.getType());
        final CharacterToken secondCharacter = (CharacterToken) secondToken;
        assertEquals('/', secondCharacter.getCharacter());
        
        assertEquals(Tokenizer.State.RCDATA, tokenizer.getState());
        assertEquals('!', tokenizer.consume());
        assertEquals('a', tokenizer.consume());
        assertEquals('b', tokenizer.consume());
        assertEquals(-1, tokenizer.consume());
    }
    
}
