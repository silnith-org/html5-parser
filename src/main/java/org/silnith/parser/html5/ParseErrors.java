package org.silnith.parser.html5;


public enum ParseErrors {
    /**
     * There is additional content beyond whitespace and comments following the closing &lt;/html&gt; tag.
     */
    UNEXPECTED_TOKEN_FOLLOWING_DOCUMENT,
    /**
     * There is additional content beyond whitespace and comments following the closing &lt;/html&gt; tag in a frameset document.
     */
    UNEXPECTED_TOKEN_FOLLOWING_FRAMESET_DOCUMENT,
    /**
     * There is a DOCTYPE token following the document body.
     */
    UNEXPECTED_DOCTYPE_FOLLOWING_BODY,
    /**
     * While parsing an HTML fragment, a closing tag for the "html" element was encountered.
     */
    UNEXPECTED_HTML_CLOSE_TAG_FOLLOWING_BODY_IN_HTML_FRAGMENT,
    /**
     * There is an unexpected token following the document body.
     */
    UNEXPECTED_TOKEN_FOLLOWING_BODY,
    /**
     * There is a DOCTYPE token following a frameset.
     */
    DOCTYPE_FOLLOWING_FRAMESET,
    /**
     * There is additional content beyond whitespace or comments following a frameset.
     */
    UNEXPECTED_TOKEN_FOLLOWING_FRAMESET,
    /**
     * There is a DOCTYPE following the document head.
     */
    DOCTYPE_FOLLOWING_HEAD,
    /**
     * One of the following elements appears after the head element:
     * <ul>
     *   <li>"base"
     *   <li>"basefont"
     *   <li>"bgsound"
     *   <li>"link"
     *   <li>"meta"
     *   <li>"noframes"
     *   <li>"script"
     *   <li>"style"
     *   <li>"template"
     *   <li>"title"
     * </ul>
     */
    UNEXPECTED_METADATA_ELEMENT_FOLLOWING_HEAD,
    /**
     * There is a start tag for a "head" element following another "head" element.
     */
    HEAD_FOLLOWING_HEAD,
    /**
     * There is an end tag following the "head" element that does not match.
     */
    UNEXPECTED_END_TAG_FOLLOWING_HEAD,
    /**
     * There is a DOCTYPE token before the "head" element.
     */
    DOCTYPE_BEFORE_HEAD,
    /**
     * There is an end tag before the "head" element.
     */
    UNEXPECTED_END_TAG_BEFORE_HEAD,
    /**
     * There is an additional DOCTYPE token before the "html" element.
     */
    ADDITIONAL_DOCTYPE,
    /**
     * There is an end tag before the "html" element.
     */
    UNEXPECTED_END_TAG_BEFORE_HTML,
    /**
     * There is a null (U+0000) character in foreign content.
     */
    NULL_CHARACTER_IN_FOREIGN_CONTENT,
    /**
     * There is a DOCTYPE token in foreign content.
     */
    DOCTYPE_IN_FOREIGN_CONTENT,
    /**
     * There is an unexpected element in foreign content.
     */
    UNEXPECTED_ELEMENT_IN_FOREIGN_CONTENT;
}
