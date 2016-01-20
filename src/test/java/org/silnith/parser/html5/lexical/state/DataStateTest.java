package org.silnith.parser.html5.lexical.state;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.state.DataState;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;

public class DataStateTest {

	private Tokenizer tokenizer;

	private DataState dataState;

	@Test
	public void testGetNextTokensAmpersand() throws IOException {
		tokenizer = new Tokenizer(new StringReader("&foo"));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(false);
		
		dataState = new DataState(tokenizer);
		
		final List<Token> tokens = dataState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA, tokenizer.getState());
		assertEquals('f', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensAmpersandEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader("&"));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(false);
		
		dataState = new DataState(tokenizer);
		
		final List<Token> tokens = dataState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensLessThan() throws IOException {
		tokenizer = new Tokenizer(new StringReader("<foo"));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(false);
		
		dataState = new DataState(tokenizer);
		
		final List<Token> tokens = dataState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.TAG_OPEN, tokenizer.getState());
		assertEquals('f', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensLessThanEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader("<"));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(false);
		
		dataState = new DataState(tokenizer);
		
		final List<Token> tokens = dataState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.TAG_OPEN, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader(""));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(false);
		
		dataState = new DataState(tokenizer);
		
		final List<Token> tokens = dataState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.EOF, token.getType());
		
		assertEquals(Tokenizer.State.DATA, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensCharacter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("abc"));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(false);
		
		dataState = new DataState(tokenizer);
		
		final List<Token> tokens = dataState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('a', characterToken.getCharacter());
		
		assertEquals(Tokenizer.State.DATA, tokenizer.getState());
		assertEquals('b', tokenizer.consume());
		assertEquals('c', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensCharacterEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader("a"));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(false);
		
		dataState = new DataState(tokenizer);
		
		final List<Token> tokens = dataState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('a', characterToken.getCharacter());
		
		assertEquals(Tokenizer.State.DATA, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNullCharacter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("\u0000"));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(false);
		
		dataState = new DataState(tokenizer);
		
		dataState.getNextTokens();
	}

	@Test
	public void testGetNextTokensNullCharacterAllowParseError() throws IOException {
		tokenizer = new Tokenizer(new StringReader("\u0000foo"));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(true);
		
		dataState = new DataState(tokenizer);
		
		final List<Token> tokens = dataState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\u0000', characterToken.getCharacter());
		
		assertEquals(Tokenizer.State.DATA, tokenizer.getState());
		assertEquals('f', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNullCharacterEndOfFileAllowParseError() throws IOException {
		tokenizer = new Tokenizer(new StringReader("\u0000"));
		tokenizer.setState(Tokenizer.State.DATA);
		tokenizer.setAllowParseErrors(true);
		
		dataState = new DataState(tokenizer);
		
		final List<Token> tokens = dataState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\u0000', characterToken.getCharacter());
		
		assertEquals(Tokenizer.State.DATA, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

}
