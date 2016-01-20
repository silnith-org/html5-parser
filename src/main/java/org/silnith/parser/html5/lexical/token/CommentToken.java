package org.silnith.parser.html5.lexical.token;

/**
 * A token representing an HTML comment.
 * 
 * @see Token.Type#COMMENT
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class CommentToken extends Token {

	private StringBuilder content;

	public CommentToken(final String content) {
		this.content = new StringBuilder(content);
	}

	public CommentToken() {
		this.content = new StringBuilder();
	}

	public void append(final char... ch) {
		content.append(ch);
	}

	public String getContent() {
		return content.toString();
	}

	@Override
	public Type getType() {
		return Type.COMMENT;
	}

	@Override
	public String toString() {
		return "CommentToken <!--" + content + "-->";
	}

}
