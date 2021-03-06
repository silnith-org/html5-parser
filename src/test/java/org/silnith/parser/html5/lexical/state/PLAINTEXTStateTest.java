package org.silnith.parser.html5.lexical.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;


public class PLAINTEXTStateTest {
    
    private Tokenizer tokenizer;
    
    private PLAINTEXTState plaintextState;
    
    @Test(expected = ParseErrorException.class)
    public void testGetNextTokensNullCharacter() throws IOException {
        tokenizer = new Tokenizer(new StringReader("\u0000"));
        tokenizer.setState(Tokenizer.State.PLAINTEXT);
        tokenizer.setAllowParseErrors(false);
        
        plaintextState = new PLAINTEXTState(tokenizer);
        
        plaintextState.getNextTokens();
    }
    
    @Test
    public void testGetNextTokensNullCharacterAllowParseErrors() throws IOException {
        tokenizer = new Tokenizer(new StringReader("\u0000"));
        tokenizer.setState(Tokenizer.State.PLAINTEXT);
        tokenizer.setAllowParseErrors(true);
        
        plaintextState = new PLAINTEXTState(tokenizer);
        
        final List<Token> tokens = plaintextState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertEquals(Token.Type.CHARACTER, token.getType());
        final CharacterToken characterToken = (CharacterToken) token;
        assertEquals('\uFFFD', characterToken.getCharacter());
        
        assertEquals(Tokenizer.State.PLAINTEXT, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensEndOfFile() throws IOException {
        tokenizer = new Tokenizer(new StringReader(""));
        tokenizer.setState(Tokenizer.State.PLAINTEXT);
        tokenizer.setAllowParseErrors(false);
        
        plaintextState = new PLAINTEXTState(tokenizer);
        
        final List<Token> tokens = plaintextState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertEquals(Token.Type.EOF, token.getType());
        
        assertEquals(Tokenizer.State.PLAINTEXT, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensCharacters() throws IOException {
        tokenizer = new Tokenizer(new StringReader("abc"));
        tokenizer.setState(Tokenizer.State.PLAINTEXT);
        tokenizer.setAllowParseErrors(false);
        
        plaintextState = new PLAINTEXTState(tokenizer);
        
        final List<Token> tokens = plaintextState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertEquals(Token.Type.CHARACTER, token.getType());
        final CharacterToken characterToken = (CharacterToken) token;
        assertEquals('a', characterToken.getCharacter());
        
        assertEquals(Tokenizer.State.PLAINTEXT, tokenizer.getState());
        assertEquals('b', tokenizer.consume());
        assertEquals('c', tokenizer.consume());
        assertEquals( -1, tokenizer.consume());
    }
    
    @Test
    public void testGetNextTokensCharacterEndOfFile() throws IOException {
        tokenizer = new Tokenizer(new StringReader("a"));
        tokenizer.setState(Tokenizer.State.PLAINTEXT);
        tokenizer.setAllowParseErrors(false);
        
        plaintextState = new PLAINTEXTState(tokenizer);
        
        final List<Token> tokens = plaintextState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(1, tokens.size());
        final Token token = tokens.get(0);
        assertEquals(Token.Type.CHARACTER, token.getType());
        final CharacterToken characterToken = (CharacterToken) token;
        assertEquals('a', characterToken.getCharacter());
        
        assertEquals(Tokenizer.State.PLAINTEXT, tokenizer.getState());
        assertEquals( -1, tokenizer.consume());
    }
    
}
