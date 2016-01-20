package org.silnith.parser.html5.lexical.token;

/**
 * A token representing a start tag, a tag that opens an element.  This
 * may also be a self-closing tag, in which case there will be no
 * following end tag.
 * 
 * @see Token.Type#START_TAG
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class StartTagToken extends TagToken {

	public StartTagToken() {
		super();
	}

	@Override
	public Type getType() {
		return Token.Type.START_TAG;
	}

	@Override
	public String toString() {
		final StringBuilder value = new StringBuilder();
		value.append("StartTagToken ");
		value.append('<');
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
