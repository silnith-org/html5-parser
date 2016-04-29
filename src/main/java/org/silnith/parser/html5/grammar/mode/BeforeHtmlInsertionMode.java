package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.grammar.dom.AfterLastChildInsertionPosition;
import org.silnith.parser.html5.grammar.dom.InsertionPosition;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Applies the rules for the before html insertion mode.
 * <p>
 * When the user agent is to apply the rules for the "before html" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A DOCTYPE token
 *   <dd>Parse error. Ignore the token.
 *   <dt>A comment token
 *   <dd>Insert a comment as the last child of the Document object.
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
 *   <dd>Ignore the token.
 *   <dt>A start tag whose tag name is "html"
 *   <dd>
 *     Create an element for the token in the HTML namespace, with the Document as the intended parent. Append it to the Document object. Put this element in the stack of open elements.
 *     <p>If the Document is being loaded as part of navigation of a browsing context, then: if the newly created element has a manifest attribute whose value is not the empty string, then resolve the value of that attribute to an absolute URL, relative to the newly created element, and if that is successful, run the application cache selection algorithm with the result of applying the URL serializer algorithm to the resulting parsed URL with the exclude fragment flag set; otherwise, if there is no such attribute, or its value is the empty string, or resolving its value fails, run the application cache selection algorithm with no manifest. The algorithm must be passed the Document object.
 *     <p>Switch the insertion mode to "before head".
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "head", "body", "html", "br"
 *   <dd>Act as described in the "anything else" entry below.
 *   <dt>Any other end tag
 *   <dd>Parse error. Ignore the token.
 *   <dt>Anything else
 *   <dd>
 *     Create an html element whose ownerDocument is the Document object. Append it to the Document object. Put this element in the stack of open elements.
 *     <p>If the Document is being loaded as part of navigation of a browsing context, then: run the application cache selection algorithm with no manifest, passing it the Document object.
 *     <p>Switch the insertion mode to "before head", then reprocess the token.
 *   </dd>
 * </dl>
 * <p>The root element can end up being removed from the Document object, e.g. by scripts; nothing in particular happens in such cases, content continues being appended to the nodes as described in the next section.
 * 
 * @see org.silnith.parser.html5.Parser.Mode#BEFORE_HTML
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-before-html-insertion-mode">8.2.5.4.2 The "before html" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class BeforeHtmlInsertionMode extends InsertionMode {
    
    public BeforeHtmlInsertionMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        switch (token.getType()) {
        case DOCTYPE: {
            if (isAllowParseErrors()) {
                return IGNORE_TOKEN;
            } else {
                throw new ParseErrorException("Unexpected DOCTYPE token before html: " + token);
            }
        } // break;
        case COMMENT: {
            final CommentToken commentToken = (CommentToken) token;
            final InsertionPosition insertionPosition = new AfterLastChildInsertionPosition(getDocument());
            insertComment(commentToken, insertionPosition);
            return TOKEN_HANDLED;
        } // break;
        case CHARACTER: {
            final CharacterToken characterToken = (CharacterToken) token;
            final char character = characterToken.getCharacter();
            switch (character) {
            case CHARACTER_TABULATION: // fall through
            case LINE_FEED: // fall through
            case FORM_FEED: // fall through
            case CARRIAGE_RETURN: // fall through
            case SPACE: {
                return IGNORE_TOKEN;
            } // break;
            default: {
                return anythingElse(characterToken);
            } // break;
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                /*
                 * The correct algorithm, DOM throws up.
                 */
//                final Element htmlElement = createElementForToken(startTagToken,
//                        HTML_NAMESPACE, getDocument());
//                getDocument().appendChild(htmlElement);
                
                final Element htmlElement = createHtmlElement();
                for (final TagToken.Attribute attribute : startTagToken.getAttributes()) {
                    final String name = attribute.getName();
                    final String value = attribute.getValue();
                    htmlElement.setAttribute(name, value);
                }
                addToStackOfOpenElements(htmlElement);
                setInsertionMode(Parser.Mode.BEFORE_HEAD);
                return TOKEN_HANDLED;
            } // break;
            default: {
                return anythingElse(startTagToken);
            } // break;
            }
        } // break;
        case END_TAG: {
            final EndTagToken endTagToken = (EndTagToken) token;
            final String tagName = endTagToken.getTagName();
            switch (tagName) {
            case "head": // fall through
            case "body": // fall through
            case "html": // fall through
            case "br": {
                return anythingElse(endTagToken);
            } // break;
            default: {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected end tag before html: " + endTagToken);
                }
            } // break;
            }
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private Element createHtmlElement() {
        final Document document = getDocument();
//        final Element htmlElement = document.createElementNS(HTML_NAMESPACE, "html");
        final Element htmlElement = document.getDocumentElement();
//        document.appendChild(htmlElement);
        return htmlElement;
    }
    
    private boolean anythingElse(final Token token) {
        final Element htmlElement = createHtmlElement();
        addToStackOfOpenElements(htmlElement);
        setInsertionMode(Parser.Mode.BEFORE_HEAD);
        return REPROCESS_TOKEN;
    }
    
}
