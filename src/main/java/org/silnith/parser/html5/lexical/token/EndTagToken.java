package org.silnith.parser.html5.lexical.token;

/**
 * A token representing an end tag that closes the element opened by a
 * prior start tag.
 * 
 * @see Token.Type#END_TAG
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class EndTagToken extends TagToken {

	public EndTagToken() {
		super();
	}

	@Override
	public Type getType() {
		return Type.END_TAG;
	}

	@Override
	public String toString() {
		final StringBuilder value = new StringBuilder();
		value.append("EndTagToken ");
		value.append('<');
		value.append('/');
		value.append(getTagName());
		for (final Attribute attribute : getAttributes()) {
			value.append(' ');
			value.append(attribute.toString());
		}
		if (isSelfClosing()) {
			value.append('/');
		}
		value.append('>');
		return value.toString();
	}

}
