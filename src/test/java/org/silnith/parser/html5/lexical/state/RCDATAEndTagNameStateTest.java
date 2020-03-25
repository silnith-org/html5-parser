package org.silnith.parser.html5.lexical.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;


@RunWith(MockitoJUnitRunner.class)
public class RCDATAEndTagNameStateTest {
    
    @Mock
    private Tokenizer tokenizer;
    
    @Mock
    private EndTagToken endTagToken;
    
    private RCDATAEndTagNameState rcdataEndTagNameState;
    
    @Test
    public void testGetNextTokensCharacterTabulationWithAppropriateEndTag() throws IOException {
//        when(tokenizer.getState()).thenReturn(Tokenizer.State.RCDATA_END_TAG_NAME);
//        when(tokenizer.isAllowParseErrors()).thenReturn(false);
        
        when(tokenizer.consume()).thenReturn((int) '\t', -1);
        when(tokenizer.isAppropriateEndTagToken(any(TagToken.class))).thenReturn(true);
        when(tokenizer.getPendingToken()).thenReturn(endTagToken);
        when(endTagToken.getType()).thenReturn(Token.Type.END_TAG);
        
        rcdataEndTagNameState = new RCDATAEndTagNameState(tokenizer);
        
        final List<Token> tokens = rcdataEndTagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        verify(tokenizer).consume();
        verify(tokenizer).isAppropriateEndTagToken(same(endTagToken));
        verify(tokenizer).setState(eq(Tokenizer.State.BEFORE_ATTRIBUTE_NAME));
        verify(tokenizer, never()).unconsume(anyInt());
    }
    
    @Test
    public void testGetNextTokensCharacterTabulationInappropriateEndTag() throws IOException {
//        when(tokenizer.getState()).thenReturn(Tokenizer.State.RCDATA_END_TAG_NAME);
//        when(tokenizer.isAllowParseErrors()).thenReturn(false);
        
        when(tokenizer.consume()).thenReturn((int) '\t', -1);
        when(tokenizer.isAppropriateEndTagToken(any(TagToken.class))).thenReturn(false);
        when(tokenizer.getPendingToken()).thenReturn(endTagToken);
        when(endTagToken.getType()).thenReturn(Token.Type.END_TAG);
        
        when(tokenizer.getTemporaryBuffer()).thenReturn("abc");
        
        rcdataEndTagNameState = new RCDATAEndTagNameState(tokenizer);
        
        final List<Token> tokens = rcdataEndTagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(5, tokens.size());
        
        final Token lessThanToken = tokens.get(0);
        assertEquals(Token.Type.CHARACTER, lessThanToken.getType());
        final CharacterToken lessThanCharacterToken = (CharacterToken) lessThanToken;
        assertEquals('<', lessThanCharacterToken.getCharacter());
        
        final Token solidusToken = tokens.get(1);
        assertEquals(Token.Type.CHARACTER, solidusToken.getType());
        final CharacterToken solidusCharacterToken = (CharacterToken) solidusToken;
        assertEquals('/', solidusCharacterToken.getCharacter());
        
        final Token aToken = tokens.get(2);
        assertEquals(Token.Type.CHARACTER, aToken.getType());
        final CharacterToken aCharacterToken = (CharacterToken) aToken;
        assertEquals('a', aCharacterToken.getCharacter());
        
        final Token bToken = tokens.get(3);
        assertEquals(Token.Type.CHARACTER, bToken.getType());
        final CharacterToken bCharacterToken = (CharacterToken) bToken;
        assertEquals('b', bCharacterToken.getCharacter());
        
        final Token cToken = tokens.get(4);
        assertEquals(Token.Type.CHARACTER, cToken.getType());
        final CharacterToken cCharacterToken = (CharacterToken) cToken;
        assertEquals('c', cCharacterToken.getCharacter());
        
        verify(tokenizer).consume();
        verify(tokenizer).unconsume(eq((int) '\t'));
        verify(tokenizer).setState(eq(Tokenizer.State.RCDATA));
        
        verify(tokenizer).getTemporaryBuffer();
        verify(tokenizer).clearTemporaryBuffer();
    }
    
    @Test
    public void testGetNextTokensLineFeedWithAppropriateEndTag() throws IOException {
//        when(tokenizer.getState()).thenReturn(Tokenizer.State.RCDATA_END_TAG_NAME);
//        when(tokenizer.isAllowParseErrors()).thenReturn(false);
        
        when(tokenizer.consume()).thenReturn((int) '\n', -1);
        when(tokenizer.isAppropriateEndTagToken(any(TagToken.class))).thenReturn(true);
        when(tokenizer.getPendingToken()).thenReturn(endTagToken);
        when(endTagToken.getType()).thenReturn(Token.Type.END_TAG);
        
        rcdataEndTagNameState = new RCDATAEndTagNameState(tokenizer);
        
        final List<Token> tokens = rcdataEndTagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        verify(tokenizer).consume();
        verify(tokenizer).isAppropriateEndTagToken(same(endTagToken));
        verify(tokenizer).setState(eq(Tokenizer.State.BEFORE_ATTRIBUTE_NAME));
        verify(tokenizer, never()).unconsume(anyInt());
    }
    
    @Test
    public void testGetNextTokensLineFeedInappropriateEndTag() throws IOException {
//        when(tokenizer.getState()).thenReturn(Tokenizer.State.RCDATA_END_TAG_NAME);
//        when(tokenizer.isAllowParseErrors()).thenReturn(false);
        
        when(tokenizer.consume()).thenReturn((int) '\n', -1);
        when(tokenizer.isAppropriateEndTagToken(any(TagToken.class))).thenReturn(false);
        when(tokenizer.getPendingToken()).thenReturn(endTagToken);
        when(endTagToken.getType()).thenReturn(Token.Type.END_TAG);
        
        when(tokenizer.getTemporaryBuffer()).thenReturn("abc");
        
        rcdataEndTagNameState = new RCDATAEndTagNameState(tokenizer);
        
        final List<Token> tokens = rcdataEndTagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(5, tokens.size());
        
        final Token lessThanToken = tokens.get(0);
        assertEquals(Token.Type.CHARACTER, lessThanToken.getType());
        final CharacterToken lessThanCharacterToken = (CharacterToken) lessThanToken;
        assertEquals('<', lessThanCharacterToken.getCharacter());
        
        final Token solidusToken = tokens.get(1);
        assertEquals(Token.Type.CHARACTER, solidusToken.getType());
        final CharacterToken solidusCharacterToken = (CharacterToken) solidusToken;
        assertEquals('/', solidusCharacterToken.getCharacter());
        
        final Token aToken = tokens.get(2);
        assertEquals(Token.Type.CHARACTER, aToken.getType());
        final CharacterToken aCharacterToken = (CharacterToken) aToken;
        assertEquals('a', aCharacterToken.getCharacter());
        
        final Token bToken = tokens.get(3);
        assertEquals(Token.Type.CHARACTER, bToken.getType());
        final CharacterToken bCharacterToken = (CharacterToken) bToken;
        assertEquals('b', bCharacterToken.getCharacter());
        
        final Token cToken = tokens.get(4);
        assertEquals(Token.Type.CHARACTER, cToken.getType());
        final CharacterToken cCharacterToken = (CharacterToken) cToken;
        assertEquals('c', cCharacterToken.getCharacter());
        
        verify(tokenizer).consume();
        verify(tokenizer).unconsume(eq((int) '\n'));
        verify(tokenizer).setState(eq(Tokenizer.State.RCDATA));
        
        verify(tokenizer).getTemporaryBuffer();
        verify(tokenizer).clearTemporaryBuffer();
    }
    
    @Test
    public void testGetNextTokensFormFeedWithAppropriateEndTag() throws IOException {
//        when(tokenizer.getState()).thenReturn(Tokenizer.State.RCDATA_END_TAG_NAME);
//        when(tokenizer.isAllowParseErrors()).thenReturn(false);
        
        when(tokenizer.consume()).thenReturn((int) '\f', -1);
        when(tokenizer.isAppropriateEndTagToken(any(TagToken.class))).thenReturn(true);
        when(tokenizer.getPendingToken()).thenReturn(endTagToken);
        when(endTagToken.getType()).thenReturn(Token.Type.END_TAG);
        
        rcdataEndTagNameState = new RCDATAEndTagNameState(tokenizer);
        
        final List<Token> tokens = rcdataEndTagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        verify(tokenizer).consume();
        verify(tokenizer).isAppropriateEndTagToken(same(endTagToken));
        verify(tokenizer).setState(eq(Tokenizer.State.BEFORE_ATTRIBUTE_NAME));
        verify(tokenizer, never()).unconsume(anyInt());
    }
    
    @Test
    public void testGetNextTokensFormFeedInappropriateEndTag() throws IOException {
//        when(tokenizer.getState()).thenReturn(Tokenizer.State.RCDATA_END_TAG_NAME);
//        when(tokenizer.isAllowParseErrors()).thenReturn(false);
        
        when(tokenizer.consume()).thenReturn((int) '\f', -1);
        when(tokenizer.isAppropriateEndTagToken(any(TagToken.class))).thenReturn(false);
        when(tokenizer.getPendingToken()).thenReturn(endTagToken);
        when(endTagToken.getType()).thenReturn(Token.Type.END_TAG);
        
        when(tokenizer.getTemporaryBuffer()).thenReturn("abc");
        
        rcdataEndTagNameState = new RCDATAEndTagNameState(tokenizer);
        
        final List<Token> tokens = rcdataEndTagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(5, tokens.size());
        
        final Token lessThanToken = tokens.get(0);
        assertEquals(Token.Type.CHARACTER, lessThanToken.getType());
        final CharacterToken lessThanCharacterToken = (CharacterToken) lessThanToken;
        assertEquals('<', lessThanCharacterToken.getCharacter());
        
        final Token solidusToken = tokens.get(1);
        assertEquals(Token.Type.CHARACTER, solidusToken.getType());
        final CharacterToken solidusCharacterToken = (CharacterToken) solidusToken;
        assertEquals('/', solidusCharacterToken.getCharacter());
        
        final Token aToken = tokens.get(2);
        assertEquals(Token.Type.CHARACTER, aToken.getType());
        final CharacterToken aCharacterToken = (CharacterToken) aToken;
        assertEquals('a', aCharacterToken.getCharacter());
        
        final Token bToken = tokens.get(3);
        assertEquals(Token.Type.CHARACTER, bToken.getType());
        final CharacterToken bCharacterToken = (CharacterToken) bToken;
        assertEquals('b', bCharacterToken.getCharacter());
        
        final Token cToken = tokens.get(4);
        assertEquals(Token.Type.CHARACTER, cToken.getType());
        final CharacterToken cCharacterToken = (CharacterToken) cToken;
        assertEquals('c', cCharacterToken.getCharacter());
        
        verify(tokenizer).consume();
        verify(tokenizer).unconsume(eq((int) '\f'));
        verify(tokenizer).setState(eq(Tokenizer.State.RCDATA));
        
        verify(tokenizer).getTemporaryBuffer();
        verify(tokenizer).clearTemporaryBuffer();
    }
    
    @Test
    public void testGetNextTokensSpaceWithAppropriateEndTag() throws IOException {
//        when(tokenizer.getState()).thenReturn(Tokenizer.State.RCDATA_END_TAG_NAME);
//        when(tokenizer.isAllowParseErrors()).thenReturn(false);
        
        when(tokenizer.consume()).thenReturn((int) ' ', -1);
        when(tokenizer.isAppropriateEndTagToken(any(TagToken.class))).thenReturn(true);
        when(tokenizer.getPendingToken()).thenReturn(endTagToken);
        when(endTagToken.getType()).thenReturn(Token.Type.END_TAG);
        
        rcdataEndTagNameState = new RCDATAEndTagNameState(tokenizer);
        
        final List<Token> tokens = rcdataEndTagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertTrue(tokens.isEmpty());
        
        verify(tokenizer).consume();
        verify(tokenizer).isAppropriateEndTagToken(same(endTagToken));
        verify(tokenizer).setState(eq(Tokenizer.State.BEFORE_ATTRIBUTE_NAME));
        verify(tokenizer, never()).unconsume(anyInt());
    }
    
    @Test
    public void testGetNextTokensSpaceInappropriateEndTag() throws IOException {
//        when(tokenizer.getState()).thenReturn(Tokenizer.State.RCDATA_END_TAG_NAME);
//        when(tokenizer.isAllowParseErrors()).thenReturn(false);
        
        when(tokenizer.consume()).thenReturn((int) ' ', -1);
        when(tokenizer.isAppropriateEndTagToken(any(TagToken.class))).thenReturn(false);
        when(tokenizer.getPendingToken()).thenReturn(endTagToken);
        when(endTagToken.getType()).thenReturn(Token.Type.END_TAG);
        
        when(tokenizer.getTemporaryBuffer()).thenReturn("abc");
        
        rcdataEndTagNameState = new RCDATAEndTagNameState(tokenizer);
        
        final List<Token> tokens = rcdataEndTagNameState.getNextTokens();
        
        assertNotNull(tokens);
        assertEquals(5, tokens.size());
        
        final Token lessThanToken = tokens.get(0);
        assertEquals(Token.Type.CHARACTER, lessThanToken.getType());
        final CharacterToken lessThanCharacterToken = (CharacterToken) lessThanToken;
        assertEquals('<', lessThanCharacterToken.getCharacter());
        
        final Token solidusToken = tokens.get(1);
        assertEquals(Token.Type.CHARACTER, solidusToken.getType());
        final CharacterToken solidusCharacterToken = (CharacterToken) solidusToken;
        assertEquals('/', solidusCharacterToken.getCharacter());
        
        final Token aToken = tokens.get(2);
        assertEquals(Token.Type.CHARACTER, aToken.getType());
        final CharacterToken aCharacterToken = (CharacterToken) aToken;
        assertEquals('a', aCharacterToken.getCharacter());
        
        final Token bToken = tokens.get(3);
        assertEquals(Token.Type.CHARACTER, bToken.getType());
        final CharacterToken bCharacterToken = (CharacterToken) bToken;
        assertEquals('b', bCharacterToken.getCharacter());
        
        final Token cToken = tokens.get(4);
        assertEquals(Token.Type.CHARACTER, cToken.getType());
        final CharacterToken cCharacterToken = (CharacterToken) cToken;
        assertEquals('c', cCharacterToken.getCharacter());
        
        verify(tokenizer).consume();
        verify(tokenizer).unconsume(eq((int) ' '));
        verify(tokenizer).setState(eq(Tokenizer.State.RCDATA));
        
        verify(tokenizer).getTemporaryBuffer();
        verify(tokenizer).clearTemporaryBuffer();
    }
    
}
