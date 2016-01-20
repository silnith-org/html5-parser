package org.silnith.parser.html5.lexical.token;

/**
 * A token representing the end of the file.  This will always be the
 * last token in the input stream.
 * 
 * @see Token.Type#EOF
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class EndOfFileToken extends Token {

	public EndOfFileToken() {
		super();
	}

	@Override
	public Type getType() {
		return Type.EOF;
	}

	@Override
	public String toString() {
		return "<EOF>";
	}

}
