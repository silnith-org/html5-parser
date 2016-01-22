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
import org.silnith.parser.html5.lexical.token.Token;

public class RCDATALessThanSignStateTest {
    
    private Tokenizer tokenizer;
    
    private RCDATALessThanSignState rcdataLessThanSignState;
    
    @Test
    public void testGetNextTokensSolidus() throws IOException {
        tokenizer = new Tokenizer(new StringReader("/foo"));
        tokenizer.setState(Tokenizer.State.RCDATA_LESS_THAN_SIGN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataLessThanSignState = new RCDATALessThanSignState(tokenizer);
        
        final List<Token> tokens = rcdataLessThanSignState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.RCDATA_END_TAG_OPEN, tokenizer.getState());
        assertEquals('f', tokenizer.consume());
        assertEquals('o', tokenizer.consume());
        assertEquals('o', tokenizer.consume());
        assertEquals(-1, tokenizer.consume());
        
        assertEquals("", tokenizer.getTemporaryBuffer());
    }
    
    @Test
    public void testGetNextTokensSolidusEndOfFile() throws IOException {
        tokenizer = new Tokenizer(new StringReader("/"));
        tokenizer.setState(Tokenizer.State.RCDATA_LESS_THAN_SIGN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataLessThanSignState = new RCDATALessThanSignState(tokenizer);
        
        final List<Token> tokens = rcdataLessThanSignState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        assertEquals(Tokenizer.State.RCDATA_END_TAG_OPEN, tokenizer.getState());
        assertEquals(-1, tokenizer.consume());
        
        assertEquals("", tokenizer.getTemporaryBuffer());
    }
    
    @Test
    public void testGetNextTokensCharacter() throws IOException {
        tokenizer = new Tokenizer(new StringReader("f"));
        tokenizer.setState(Tokenizer.State.RCDATA_LESS_THAN_SIGN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataLessThanSignState = new RCDATALessThanSignState(tokenizer);
        
        final List<Token> tokens = rcdataLessThanSignState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertEquals(Token.Type.CHARACTER, token.getType());
        final CharacterToken characterToken = (CharacterToken) token;
        assertEquals('<', characterToken.getCharacter());
        
        assertEquals(Tokenizer.State.RCDATA, tokenizer.getState());
        assertEquals('f', tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensEndOfFile() throws IOException {
        tokenizer = new Tokenizer(new StringReader(""));
        tokenizer.setState(Tokenizer.State.RCDATA_LESS_THAN_SIGN);
        tokenizer.setAllowParseErrors(false);
        
        rcdataLessThanSignState = new RCDATALessThanSignState(tokenizer);
        
        final List<Token> tokens = rcdataLessThanSignState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertEquals(Token.Type.CHARACTER, token.getType());
        final CharacterToken characterToken = (CharacterToken) token;
        assertEquals('<', characterToken.getCharacter());
        
        assertEquals(Tokenizer.State.RCDATA, tokenizer.getState());
        assertEquals(-1, tokenizer.consume());
    }
    
}
