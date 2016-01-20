package org.silnith.parser.html5.lexical.state;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;

public class EndTagOpenStateTest {

	private Tokenizer tokenizer;

	private EndTagOpenState endTagOpenState;

	@Test
	public void testGetNextTokensLowercaseLetter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("abc"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		final List<Token> tokens = endTagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
		assertEquals('b', tokenizer.consume());
		assertEquals('c', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
		
		final TagToken pendingToken = tokenizer.getPendingToken();
		assertNotNull(pendingToken);
		assertEquals(Token.Type.END_TAG, pendingToken.getType());
		assertEquals("a", pendingToken.getTagName());
	}

	@Test
	public void testGetNextTokensLowercaseLetterEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader("a"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		final List<Token> tokens = endTagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
		
		final TagToken pendingToken = tokenizer.getPendingToken();
		assertNotNull(pendingToken);
		assertEquals(Token.Type.END_TAG, pendingToken.getType());
		assertEquals("a", pendingToken.getTagName());
	}

	@Test
	public void testGetNextTokensUppercaseLetter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("ABC"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		final List<Token> tokens = endTagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
		assertEquals('B', tokenizer.consume());
		assertEquals('C', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
		
		final TagToken pendingToken = tokenizer.getPendingToken();
		assertNotNull(pendingToken);
		assertEquals(Token.Type.END_TAG, pendingToken.getType());
		assertEquals("a", pendingToken.getTagName());
	}

	@Test
	public void testGetNextTokensUppercaseLetterEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader("A"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		final List<Token> tokens = endTagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.TAG_NAME, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
		
		final TagToken pendingToken = tokenizer.getPendingToken();
		assertNotNull(pendingToken);
		assertEquals(Token.Type.END_TAG, pendingToken.getType());
		assertEquals("a", pendingToken.getTagName());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensGreaterThanSign() throws IOException {
		tokenizer = new Tokenizer(new StringReader(">"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		endTagOpenState.getNextTokens();
	}

	@Test
	public void testGetNextTokensGreaterThanSignAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader(">foo"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(true);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		final List<Token> tokens = endTagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.DATA, tokenizer.getState());
		assertEquals('f', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensGreaterThanSignEndOfFileAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader(">"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(true);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		final List<Token> tokens = endTagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.DATA, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader(""));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		endTagOpenState.getNextTokens();
	}

	@Test
	public void testGetNextTokensEndOfFileAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader(""));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(true);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		final List<Token> tokens = endTagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(2, tokens.size());
		final Token firstToken = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, firstToken.getType());
		final CharacterToken firstCharacter = (CharacterToken) firstToken;
		assertEquals('<', firstCharacter.getCharacter());
		final Token secondToken = tokens.get(1);
		assertEquals(Token.Type.CHARACTER, secondToken.getType());
		final CharacterToken secondCharacter = (CharacterToken) secondToken;
		assertEquals('/', secondCharacter.getCharacter());
		
		assertEquals(Tokenizer.State.DATA, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensIllegalCharacter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("<"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(false);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		endTagOpenState.getNextTokens();
	}

	@Test
	public void testGetNextTokensIllegalCharacterAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("<foo"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(true);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		final List<Token> tokens = endTagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.BOGUS_COMMENT, tokenizer.getState());
		assertEquals('f', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals('o', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensIllegalCharacterEndOfFileAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("<"));
		tokenizer.setState(Tokenizer.State.END_TAG_OPEN);
		tokenizer.setAllowParseErrors(true);
		
		endTagOpenState = new EndTagOpenState(tokenizer);
		
		final List<Token> tokens = endTagOpenState.getNextTokens();
		
		assertNotNull(tokens);
		assertTrue(tokens.isEmpty());
		
		assertEquals(Tokenizer.State.BOGUS_COMMENT, tokenizer.getState());
		assertEquals(-1, tokenizer.consume());
	}

}
