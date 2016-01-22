package org.silnith.parser.html5.lexical.token;

/**
 * An abstract base class for all the various token types used during
 * tokenization.
 *
 * @see <a href="http://www.w3.org/TR/html5/syntax.html#tokenization">8.2.4
 *      Tokenization</a>
 * @see <a
 *      href="http://www.w3.org/TR/html5/syntax.html#parsing-main-inhtml">8.2.5.4
 *      The rules for parsing tokens in HTML content</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public abstract class Token {
    
    /**
     * The type of the concrete subclass this token implements.
     * 
     * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
     */
    public enum Type {
        /**
         * A token representing a DOCTYPE declaration.
         * 
         * @see DOCTYPEToken
         */
        DOCTYPE,
        /**
         * A token representing a start tag, a tag that opens an element. This
         * may also be a self-closing tag, in which case there will be no
         * following end tag.
         * 
         * @see StartTagToken
         */
        START_TAG,
        /**
         * A token representing an end tag that closes the element opened by a
         * prior start tag.
         * 
         * @see EndTagToken
         */
        END_TAG,
        /**
         * A token representing an HTML comment.
         * 
         * @see CommentToken
         */
        COMMENT,
        /**
         * A token representing a single character of text in a document.
         * 
         * @see CharacterToken
         */
        CHARACTER,
        /**
         * A token representing the end of the file. This will always be the
         * last token in the input stream.
         * 
         * @see EndOfFileToken
         */
        EOF
    }
    
    protected Token() {
        super();
    }
    
    /**
     * Returns a constant representing the concrete subclass this implements.
     * Based on this value, the client code may safely cast this instance to the
     * specified sub-type.
     * 
     * @return the type of this instance
     */
    public abstract Type getType();
    
}
