package org.silnith.parser.html5.lexical.token;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CharacterTokenTest {

	private CharacterToken characterToken;

	@Test
	public void testCharacterTokenLetter() {
		characterToken = new CharacterToken('a');
		
		assertNotNull(characterToken);
	}

	@Test
	public void testCharacterTokenNumber() {
		characterToken = new CharacterToken('9');
		
		assertNotNull(characterToken);
	}

	@Test
	public void testCharacterTokenReplacementCharacter() {
		characterToken = new CharacterToken('\ufffd');
		
		assertNotNull(characterToken);
	}

	@Test
	public void testGetType() {
		characterToken = new CharacterToken('a');
		
		assertEquals(Token.Type.CHARACTER, characterToken.getType());
	}

	@Test
	public void testGetCharacterLetter() {
		characterToken = new CharacterToken('a');
		
		assertEquals('a', characterToken.getCharacter());
	}

	@Test
	public void testGetCharacterNumber() {
		characterToken = new CharacterToken('9');
		
		assertEquals('9', characterToken.getCharacter());
	}

	@Test
	public void testGetCharacterReplacementCharacter() {
		characterToken = new CharacterToken('\ufffd');
		
		assertEquals('\ufffd', characterToken.getCharacter());
	}

	@Test
	public void testToTokensCharArraySingle() {
		final List<Token> tokens = CharacterToken.toTokens('a');
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertNotNull(token);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken charToken = (CharacterToken) token;
		assertEquals('a', charToken.getCharacter());
	}

	@Test
	public void testToTokensCharArrayMultiple() {
		final List<Token> tokens = CharacterToken.toTokens('a', '9', '\ufffd');
		
		assertNotNull(tokens);
		assertEquals(3, tokens.size());
		
		final Token token1 = tokens.get(0);
		assertNotNull(token1);
		assertEquals(Token.Type.CHARACTER, token1.getType());
		final CharacterToken charToken1 = (CharacterToken) token1;
		assertEquals('a', charToken1.getCharacter());
		
		final Token token2 = tokens.get(1);
		assertNotNull(token2);
		assertEquals(Token.Type.CHARACTER, token2.getType());
		final CharacterToken charToken2 = (CharacterToken) token2;
		assertEquals('9', charToken2.getCharacter());
		
		final Token token3 = tokens.get(2);
		assertNotNull(token3);
		assertEquals(Token.Type.CHARACTER, token3.getType());
		final CharacterToken charToken3 = (CharacterToken) token3;
		assertEquals('\ufffd', charToken3.getCharacter());
	}

	@Test
	public void testToTokensCharArrayEmpty() {
		final List<Token> tokens = CharacterToken.toTokens(new char[0]);
		
		assertNotNull(tokens);
		assertEquals(0, tokens.size());
	}

	@Test
	public void testToTokensStringSingle() {
		final List<Token> tokens = CharacterToken.toTokens("a");
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertNotNull(token);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken charToken = (CharacterToken) token;
		assertEquals('a', charToken.getCharacter());
	}

	@Test
	public void testToTokensStringMultiple() {
		final List<Token> tokens = CharacterToken.toTokens("a9\ufffd");
		
		assertNotNull(tokens);
		assertEquals(3, tokens.size());
		
		final Token token1 = tokens.get(0);
		assertNotNull(token1);
		assertEquals(Token.Type.CHARACTER, token1.getType());
		final CharacterToken charToken1 = (CharacterToken) token1;
		assertEquals('a', charToken1.getCharacter());
		
		final Token token2 = tokens.get(1);
		assertNotNull(token2);
		assertEquals(Token.Type.CHARACTER, token2.getType());
		final CharacterToken charToken2 = (CharacterToken) token2;
		assertEquals('9', charToken2.getCharacter());
		
		final Token token3 = tokens.get(2);
		assertNotNull(token3);
		assertEquals(Token.Type.CHARACTER, token3.getType());
		final CharacterToken charToken3 = (CharacterToken) token3;
		assertEquals('\ufffd', charToken3.getCharacter());
	}

	@Test
	public void testToTokensStringEmpty() {
		final List<Token> tokens = CharacterToken.toTokens("");
		
		assertNotNull(tokens);
		assertEquals(0, tokens.size());
	}

	@Test
	public void testToTokensCharSequenceSingle() {
		final List<Token> tokens = CharacterToken.toTokens(new StringBuilder("a"));
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertNotNull(token);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken charToken = (CharacterToken) token;
		assertEquals('a', charToken.getCharacter());
	}

	@Test
	public void testToTokensCharSequenceMultiple() {
		final List<Token> tokens = CharacterToken.toTokens(new StringBuilder("a9\ufffd"));
		
		assertNotNull(tokens);
		assertEquals(3, tokens.size());
		
		final Token token1 = tokens.get(0);
		assertNotNull(token1);
		assertEquals(Token.Type.CHARACTER, token1.getType());
		final CharacterToken charToken1 = (CharacterToken) token1;
		assertEquals('a', charToken1.getCharacter());
		
		final Token token2 = tokens.get(1);
		assertNotNull(token2);
		assertEquals(Token.Type.CHARACTER, token2.getType());
		final CharacterToken charToken2 = (CharacterToken) token2;
		assertEquals('9', charToken2.getCharacter());
		
		final Token token3 = tokens.get(2);
		assertNotNull(token3);
		assertEquals(Token.Type.CHARACTER, token3.getType());
		final CharacterToken charToken3 = (CharacterToken) token3;
		assertEquals('\ufffd', charToken3.getCharacter());
	}

	@Test
	public void testToTokensCharSequenceEmpty() {
		final List<Token> tokens = CharacterToken.toTokens(new StringBuilder());
		
		assertNotNull(tokens);
		assertEquals(0, tokens.size());
	}

}
