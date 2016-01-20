package org.silnith.parser.html5.lexical.state;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.Token;

public class ScriptDataStateTest {

	private Tokenizer tokenizer;

	private ScriptDataState scriptDataState;

	@Test
	public void testGetNextTokensLessThanSign() throws IOException {
		tokenizer = new Tokenizer(new StringReader("<"));
		tokenizer.setState(Tokenizer.State.SCRIPT_DATA);
		tokenizer.setAllowParseErrors(false);
		
		scriptDataState = new ScriptDataState(tokenizer);
		
		final List<Token> tokens = scriptDataState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.SCRIPT_DATA_LESS_THAN_SIGN, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNullCharacter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("\u0000"));
		tokenizer.setState(Tokenizer.State.SCRIPT_DATA);
		tokenizer.setAllowParseErrors(false);
		
		scriptDataState = new ScriptDataState(tokenizer);
		
		scriptDataState.getNextTokens();
	}

	@Test
	public void testGetNextTokensNullCharacterAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("\u0000"));
		tokenizer.setState(Tokenizer.State.SCRIPT_DATA);
		tokenizer.setAllowParseErrors(true);
		
		scriptDataState = new ScriptDataState(tokenizer);
		
		final List<Token> tokens = scriptDataState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\uFFFD', characterToken.getCharacter());
		
		assertEquals(Tokenizer.State.SCRIPT_DATA, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader(""));
		tokenizer.setState(Tokenizer.State.SCRIPT_DATA);
		tokenizer.setAllowParseErrors(false);
		
		scriptDataState = new ScriptDataState(tokenizer);
		
		final List<Token> tokens = scriptDataState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.EOF, token.getType());
		
		assertEquals(Tokenizer.State.SCRIPT_DATA, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokens() throws IOException {
		tokenizer = new Tokenizer(new StringReader("a"));
		tokenizer.setState(Tokenizer.State.SCRIPT_DATA);
		tokenizer.setAllowParseErrors(false);
		
		scriptDataState = new ScriptDataState(tokenizer);
		
		final List<Token> tokens = scriptDataState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('a', characterToken.getCharacter());
		
		assertEquals(Tokenizer.State.SCRIPT_DATA, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

}
