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

public class CharacterReferenceStateTest {

	private Tokenizer tokenizer;

	private CharacterReferenceState characterReferenceState;

	@Test
	public void testGetNextTokensCharacterTabulation() throws IOException {
		tokenizer = new Tokenizer(new StringReader("\t"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals('\t', tokenizer.consume());
	}

	@Test
	public void testGetNextTokensLineFeed() throws IOException {
		tokenizer = new Tokenizer(new StringReader("\n"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals('\n', tokenizer.consume());
	}

	@Test
	public void testGetNextTokensFormFeed() throws IOException {
		tokenizer = new Tokenizer(new StringReader("\f"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals('\f', tokenizer.consume());
	}

	@Test
	public void testGetNextTokensSpace() throws IOException {
		tokenizer = new Tokenizer(new StringReader(" "));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals(' ', tokenizer.consume());
	}

	@Test
	public void testGetNextTokensLessThanSign() throws IOException {
		tokenizer = new Tokenizer(new StringReader("<"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals('<', tokenizer.consume());
	}

	@Test
	public void testGetNextTokensAmpersand() throws IOException {
		tokenizer = new Tokenizer(new StringReader("&"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals('&', tokenizer.consume());
	}

	@Test
	public void testGetNextTokensEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader(""));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensAdditionalAllowedCharacter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("a"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer, 'a');
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals('a', tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignNumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#32;0"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals(' ', characterToken.getCharacter());
		
		assertEquals('0', tokenizer.consume());
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignNumberEndOfFile() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#32;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals(' ', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignNumberLeadingZeros() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#032;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals(' ', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignNumberVeryLongNumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#0000000000000000000000000000000000000000032;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals(' ', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNumberSignNotANumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#a;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test
	public void testGetNextTokensNumberSignNotANumberAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#a;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true); // SEE HERE
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals('#', tokenizer.consume());
		assertEquals('a', tokenizer.consume());
		assertEquals(';', tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNumberSignNumberNotANumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#32a;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test
	public void testGetNextTokensNumberSignNumberNotANumberAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#32a;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals(' ', characterToken.getCharacter());
		
		assertEquals('a', tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNumberSignNumberMissingSemicolon() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#32"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test
	public void testGetNextTokensNumberSignNumberMissingSemicolonAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#32"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals(' ', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignLowercaseXNumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#xc;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\f', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignUppercaseXNumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#Xc;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\f', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignLowercaseXNumberLeadingZeros() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#x0c;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\f', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignUppercaseXNumberLeadingZeros() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#X0c;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\f', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignLowercaseXNumberVeryLongNumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#x00000000000000000000000000000000000000000c;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\f', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignUppercaseXNumberVeryLongNumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#X00000000000000000000000000000000000000000c;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\f', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNumberSignLowercaseXNotANumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#xg;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNumberSignUppercaseXNotANumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#Xg;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test
	public void testGetNextTokensNumberSignLowercaseXNotANumberAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#xg;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true); // SEE HERE
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals('#', tokenizer.consume());
		assertEquals('x', tokenizer.consume());
		assertEquals('g', tokenizer.consume());
		assertEquals(';', tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignUppercaseXNotANumberAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#Xg;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true); // SEE HERE
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNull(tokens);
		
		assertEquals('#', tokenizer.consume());
		assertEquals('X', tokenizer.consume());
		assertEquals('g', tokenizer.consume());
		assertEquals(';', tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNumberLowercaseXSignNumberNotANumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#x20g;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNumberUppercaseXSignNumberNotANumber() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#X20g;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test
	public void testGetNextTokensNumberSignLowercaseXNumberNotANumberAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#x20g;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals(' ', characterToken.getCharacter());
		
		assertEquals('g', tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignUppercaseXNumberNotANumberAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#X20g;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals(' ', characterToken.getCharacter());
		
		assertEquals('g', tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNumberSignLowercaseXNumberMissingSemicolon() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#xc"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNumberSignUppercaseXNumberMissingSemicolon() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#Xc"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test
	public void testGetNextTokensNumberSignLowercaseXNumberMissingSemicolonAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#xc"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\f', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test
	public void testGetNextTokensNumberSignUppercaseXNumberMissingSemicolonAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#Xc"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\f', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensReplacedCharacterEuroSign() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#x80;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test
	public void testGetNextTokensReplacedCharacterEuroSignAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#x80;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\u20ac', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensDisallowedCharacterReplacementCharacter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#xD800;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test
	public void testGetNextTokensDisallowedCharacterReplacementCharacterAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#xD800;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		assertEquals('\ufffd', characterToken.getCharacter());
		
		assertEquals(-1, tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensDisallowedCharacter() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#xE;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensDisallowedCharacterAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("#xE;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		// This is still a parse error, according to the spec.  There is no fallback.
		characterReferenceState.getNextTokens();
	}

	/*
	 * The next three tests are taken directly from the spec.
	 * http://www.w3.org/TR/html5/syntax.html#consume-a-character-reference
	 */

	@Test
	public void testGetNextTokensNotIn() throws IOException {
		tokenizer = new Tokenizer(new StringReader("notin; "));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		// ∉ = \u2209
		assertEquals('∉', characterToken.getCharacter());
		
		assertEquals(' ', tokenizer.consume());
	}

	@Test(expected=ParseErrorException.class)
	public void testGetNextTokensNotIt() throws IOException {
		tokenizer = new Tokenizer(new StringReader("notit;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(false);
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		characterReferenceState.getNextTokens();
	}

	@Test
	public void testGetNextTokensNotItAllowParseErrors() throws IOException {
		tokenizer = new Tokenizer(new StringReader("notit;"));
		tokenizer.setState(Tokenizer.State.CHARACTER_REFERENCE_IN_DATA);
		tokenizer.setAllowParseErrors(true); // SEE HERE
		
		characterReferenceState = new CharacterReferenceState(tokenizer);
		
		final List<Token> tokens = characterReferenceState.getNextTokens();
		
		assertNotNull(tokens);
		assertEquals(1, tokens.size());
		final Token token = tokens.get(0);
		assertEquals(Token.Type.CHARACTER, token.getType());
		final CharacterToken characterToken = (CharacterToken) token;
		// ¬ = \u00ac
		assertEquals('¬', characterToken.getCharacter());
		
		assertEquals('i', tokenizer.consume());
		assertEquals('t', tokenizer.consume());
		assertEquals(';', tokenizer.consume());
	}

}
