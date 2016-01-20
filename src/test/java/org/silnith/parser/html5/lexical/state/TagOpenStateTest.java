package org.silnith.parser.html5.lexical.state;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.state.TagOpenState;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;

public class TagOpenStateTest {

	private Tokenizer tokenizer;

	private TagOpenState tagOpenState;

	@Test
	public void testGetNextTokensExclamationMark() throws IOException {
		tokenizer = new Tokenizer(new StringReader("!"));
		tokenizer.setState(Tokenizer.State.TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		tagOpenState = new TagOpenState(tokenizer);
		
		final List<Token> tokens = tagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.MARKUP_DECLARATION_OPEN, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensSolidus() throws IOException {
		tokenizer = new Tokenizer(new StringReader("/"));
		tokenizer.setState(Tokenizer.State.TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		tagOpenState = new TagOpenState(tokenizer);
		
		final List<Token> tokens = tagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.END_TAG_OPEN, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensLowercaseLetter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("a"));
		tokenizer.setState(Tokenizer.State.TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		tagOpenState = new TagOpenState(tokenizer);
		
		final List<Token> tokens = tagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
		
		final TagToken pendingToken = tokenizer.getPendingToken();
		assertEquals(Token.Type.START_TAG, pendingToken.getType());
		assertEquals("a", pendingToken.getTagName());
	}

	@Test
	public void testGetNextTokensUppercaseLetter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("Z"));
		tokenizer.setState(Tokenizer.State.TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		tagOpenState = new TagOpenState(tokenizer);
		
		final List<Token> tokens = tagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
		
		final TagToken pendingToken = tokenizer.getPendingToken();
		assertEquals(Token.Type.START_TAG, pendingToken.getType());
		assertEquals("z", pendingToken.getTagName());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensQuestionMark() throws IOException {
		tokenizer = new Tokenizer(new StringReader("?"));
		tokenizer.setState(Tokenizer.State.TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		tagOpenState = new TagOpenState(tokenizer);
		
		tagOpenState.getNextTokens();
	}

	@Test
	public void testGetNextTokensQuestionMarkAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("?"));
		tokenizer.setState(Tokenizer.State.TAG_OPEN);
		tokenizer.setAllowParseErrors(true);
		
		tagOpenState = new TagOpenState(tokenizer);
		
		final List<Token> tokens = tagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.BOGUS_COMMENT, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensInvalidCharacter() throws IOException {
		tokenizer = new Tokenizer(new StringReader(" "));
		tokenizer.setState(Tokenizer.State.TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		tagOpenState = new TagOpenState(tokenizer);
		
		tagOpenState.getNextTokens();
	}

	@Test
	public void testGetNextTokensInvalidCharacterAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader(" "));
		tokenizer.setState(Tokenizer.State.TAG_OPEN);
		tokenizer.setAllowParseErrors(true);
		
		tagOpenState = new TagOpenState(tokenizer);
		
		final List<Token> tokens = tagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('<', characterToken.getCharacter());
		
		assertEquals(Tokenizer.State.DATA, tokenizer.getState());
		assertEquals(' ', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
	}

}
