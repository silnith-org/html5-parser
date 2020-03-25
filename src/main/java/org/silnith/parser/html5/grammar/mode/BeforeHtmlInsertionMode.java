package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.html5.ParseErrors;
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
 * <p>When the user agent is to apply the rules for the "before html" insertion mode, the user agent must handle the token as follows:</p>
 * <dl>
 *   <dt>A DOCTYPE token</dt>
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 *   <dt>A comment token</dt>
 *   <dd>
 *     <p>Insert a comment as the last child of the Document object.</p>
 *   </dd>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE</dt>
 *   <dd>
 *     <p>Ignore the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "html"</dt>
 *   <dd>
 *     <p>Create an element for the token in the HTML namespace, with the Document as the intended parent. Append it to the Document object. Put this element in the stack of open elements.</p>
 *     <p>If the Document is being loaded as part of navigation of a browsing context, then: if the newly created element has a manifest attribute whose value is not the empty string, then resolve the value of that attribute to an absolute URL, relative to the newly created element, and if that is successful, run the application cache selection algorithm with the result of applying the URL serializer algorithm to the resulting parsed URL with the exclude fragment flag set; otherwise, if there is no such attribute, or its value is the empty string, or resolving its value fails, run the application cache selection algorithm with no manifest. The algorithm must be passed the Document object.</p>
 *     <p>Switch the insertion mode to "before head".</p>
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "head", "body", "html", "br"</dt>
 *   <dd>
 *     <p>Act as described in the "anything else" entry below.</p>
 *   </dd>
 *   <dt>Any other end tag</dt>
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 *   <dt>Anything else</dt>
 *   <dd>
 *     <p>Create an html element whose ownerDocument is the Document object. Append it to the Document object. Put this element in the stack of open elements.</p>
 *     <p>If the Document is being loaded as part of navigation of a browsing context, then: run the application cache selection algorithm with no manifest, passing it the Document object.</p>
 *     <p>Switch the insertion mode to "before head", then reprocess the token.</p>
 *   </dd>
 * </dl>
 * <p>The root element can end up being removed from the Document object, e.g. by scripts; nothing in particular happens in such cases, content continues being appended to the nodes as described in the next section.</p>
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
            reportParseError(ParseErrors.ADDITIONAL_DOCTYPE, "Unexpected DOCTYPE token before html: " + token);
            
            return IGNORE_TOKEN;
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
                /*
                 * TODO:
                 * If this is a navigation, do the manifest check and select the
                 * cache algorithm.
                 */
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
                reportParseError(ParseErrors.UNEXPECTED_END_TAG_BEFORE_HTML, "Unexpected end tag before html: " + endTagToken);
                
                return IGNORE_TOKEN;
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
//        document.replaceChild(document.getDocumentElement(), htmlElement);
        return htmlElement;
    }
    
    private boolean anythingElse(final Token token) {
        final Element htmlElement = createHtmlElement();
        addToStackOfOpenElements(htmlElement);
        /*
         * TODO:
         * If this is a navigation, do the manifest check and select the
         * cache algorithm.
         */
        setInsertionMode(Parser.Mode.BEFORE_HEAD);
        return REPROCESS_TOKEN;
    }
    
}
