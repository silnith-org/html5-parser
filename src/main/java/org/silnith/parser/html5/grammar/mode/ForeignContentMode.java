package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.REPLACEMENT_CHARACTER;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.util.Locale;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.ParseErrors;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.TagToken.Attribute;
import org.w3c.dom.Element;
import org.silnith.parser.html5.lexical.token.Token;


/**
 * Applies the foreign content mode logic.
 * <p>When the user agent is to apply the rules for parsing tokens in foreign content, the user agent must handle the token as follows:</p>
 * <dl>
 *   <dt>A character token that is U+0000 NULL</dt>
 *   <dd>
 *     <p>Parse error. Insert a U+FFFD REPLACEMENT CHARACTER character.</p>
 *   </dd>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE</dt>
 *   <dd>
 *     <p>Insert the token's character.</p>
 *   </dd>
 *   <dt>Any other character token</dt>
 *   <dd>
 *     <p>Insert the token's character.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *   </dd>
 *   <dt>A comment token</dt>
 *   <dd>
 *     <p>Insert a comment.</p>
 *   </dd>
 *   <dt>A DOCTYPE token</dt>
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "b", "big", "blockquote", "body", "br", "center", "code", "dd", "div", "dl", "dt", "em", "embed", "h1", "h2", "h3", "h4", "h5", "h6", "head", "hr", "i", "img", "li", "listing", "main", "meta", "nobr", "ol", "p", "pre", "ruby", "s", "small", "span", "strong", "strike", "sub", "sup", "table", "tt", "u", "ul", "var"</dt>
 *   <dt>A start tag whose tag name is "font", if the token has any attributes named "color", "face", or "size"</dt>
 *   <dd>
 *     <p>Parse error.</p>
 *     <p>If the parser was originally created for the HTML fragment parsing algorithm, then act as described in the "any other start tag" entry below. (fragment case)</p>
 *     <p>Otherwise:</p>
 *     <p>Pop an element from the stack of open elements, and then keep popping more elements from the stack of open elements until the current node is a MathML text integration point, an HTML integration point, or an element in the HTML namespace.</p>
 *     <p>Then, reprocess the token.</p>
 *   </dd>
 *   <dt>Any other start tag</dt>
 *   <dd>
 *     <p>If the adjusted current node is an element in the MathML namespace, adjust MathML attributes for the token. (This fixes the case of MathML attributes that are not all lowercase.)</p>
 *     <p>If the adjusted current node is an element in the SVG namespace, and the token's tag name is one of the ones in the first column of the following table, change the tag name to the name given in the corresponding cell in the second column. (This fixes the case of SVG elements that are not all lowercase.)</p>
 *     <table>
 *       <thead>
 *         <tr>
 *           <th>Tag name
 *           <th>Element name
 *       <tbody>
 *         <tr>
 *           <td>altglyph
 *           <td>altGlyph
 *         <tr>
 *           <td>altglyphdef
 *           <td>altGlyphDef
 *         <tr>
 *           <td>altglyphitem
 *           <td>altGlyphItem
 *         <tr>
 *           <td>animatecolor
 *           <td>animateColor
 *         <tr>
 *           <td>animatemotion
 *           <td>animateMotion
 *         <tr>
 *           <td>animatetransform
 *           <td>animateTransform
 *         <tr>
 *           <td>clippath
 *           <td>clipPath
 *         <tr>
 *           <td>feblend
 *           <td>feBlend
 *         <tr>
 *           <td>fecolormatrix
 *           <td>feColorMatrix
 *         <tr>
 *           <td>fecomponenttransfer
 *           <td>feComponentTransfer
 *         <tr>
 *           <td>fecomposite
 *           <td>feComposite
 *         <tr>
 *           <td>feconvolvematrix
 *           <td>feConvolveMatrix
 *         <tr>
 *           <td>fediffuselighting
 *           <td>feDiffuseLighting
 *         <tr>
 *           <td>fedisplacementmap
 *           <td>feDisplacementMap
 *         <tr>
 *           <td>fedistantlight
 *           <td>feDistantLight
 *         <tr>
 *           <td>fedropshadow
 *           <td>feDropShadow
 *         <tr>
 *           <td>feflood
 *           <td>feFlood
 *         <tr>
 *           <td>fefunca
 *           <td>feFuncA
 *         <tr>
 *           <td>fefuncb
 *           <td>feFuncB
 *         <tr>
 *           <td>fefuncg
 *           <td>feFuncG
 *         <tr>
 *           <td>fefuncr
 *           <td>feFuncR
 *         <tr>
 *           <td>fegaussianblur
 *           <td>feGaussianBlur
 *         <tr>
 *           <td>feimage
 *           <td>feImage
 *         <tr>
 *           <td>femerge
 *           <td>feMerge
 *         <tr>
 *           <td>femergenode
 *           <td>feMergeNode
 *         <tr>
 *           <td>femorphology
 *           <td>feMorphology
 *         <tr>
 *           <td>feoffset
 *           <td>feOffset
 *         <tr>
 *           <td>fepointlight
 *           <td>fePointLight
 *         <tr>
 *           <td>fespecularlighting
 *           <td>feSpecularLighting
 *         <tr>
 *           <td>fespotlight
 *           <td>feSpotLight
 *         <tr>
 *           <td>fetile
 *           <td>feTile
 *         <tr>
 *           <td>feturbulence
 *           <td>feTurbulence
 *         <tr>
 *           <td>foreignobject
 *           <td>foreignObject
 *         <tr>
 *           <td>glyphref
 *           <td>glyphRef
 *         <tr>
 *           <td>lineargradient
 *           <td>linearGradient
 *         <tr>
 *           <td>radialgradient
 *           <td>radialGradient
 *         <tr>
 *           <td>textpath
 *           <td>textPath
 *     </table>
 *     <p>If the adjusted current node is an element in the SVG namespace, adjust SVG attributes for the token. (This fixes the case of SVG attributes that are not all lowercase.)</p>
 *     <p>Adjust foreign attributes for the token. (This fixes the use of namespaced attributes, in particular XLink in SVG.)</p>
 *     <p>Insert a foreign element for the token, in the same namespace as the adjusted current node.</p>
 *     <p>If the token has its self-closing flag set, then run the appropriate steps from the following list:</p>
 *     <dl>
 *       <dt>If the token's tag name is "script"</dt>
 *       <dd>
 *         <p>Acknowledge the token's self-closing flag, and then act as described in the steps for a "script" end tag below.</p>
 *       </dd>
 *       <dt>Otherwise</dt>
 *       <dd>
 *         <p>Pop the current node off the stack of open elements and acknowledge the token's self-closing flag.</p>
 *       </dd>
 *     </dl>
 *   </dd>
 *   <dt>An end tag whose tag name is "script", if the current node is a script element in the SVG namespace</dt>
 *   <dd>
 *     <p>Pop the current node off the stack of open elements.</p>
 *     <p>Let the old insertion point have the same value as the current insertion point. Let the insertion point be just before the next input character.</p>
 *     <p>Increment the parser's script nesting level by one. Set the parser pause flag to true.</p>
 *     <p>Process the script element according to the SVG rules, if the user agent supports SVG. [SVG]</p>
 *     <p>Even if this causes new characters to be inserted into the tokenizer, the parser will not be executed reentrantly, since the parser pause flag is true.</p>
 *     <p>Decrement the parser's script nesting level by one. If the parser's script nesting level is zero, then set the parser pause flag to false.</p>
 *     <p>Let the insertion point have the value of the old insertion point. (In other words, restore the insertion point to its previous value. This value might be the "undefined" value.)</p>
 *   </dd>
 *   <dt>Any other end tag</dt>
 *   <dd>
 *     <p>Run these steps:</p>
 *     <ol>
 *       <li>
 *         <p>Initialize node to be the current node (the bottommost node of the stack).</p>
 *       <li>
 *         <p>If node's tag name, converted to ASCII lowercase, is not the same as the tag name of the token, then this is a parse error.</p>
 *       <li>
 *         <p>Loop: If node is the topmost element in the stack of open elements, abort these steps. (fragment case)</p>
 *       <li>
 *         <p>If node's tag name, converted to ASCII lowercase, is the same as the tag name of the token, pop elements from the stack of open elements until node has been popped from the stack, and then abort these steps.</p>
 *       <li>
 *         <p>Set node to the previous entry in the stack of open elements.</p>
 *       <li>
 *         <p>If node is not an element in the HTML namespace, return to the step labeled loop.</p>
 *       <li>
 *         <p>Otherwise, process the token according to the rules given in the section corresponding to the current insertion mode in HTML content.</p>
 *     </ol>
 *   </dd>
 * </dl>
 * 
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inforeign">8.2.5.5 The rules for parsing tokens in foreign content</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class ForeignContentMode extends InsertionMode {
    
    public ForeignContentMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        switch (token.getType()) {
        case CHARACTER: {
            final CharacterToken characterToken = (CharacterToken) token;
            final char character = characterToken.getCharacter();
            switch (character) {
            case NULL: {
                reportParseError(ParseErrors.NULL_CHARACTER_IN_FOREIGN_CONTENT, "Null character in foreign content.");
                
                insertCharacter(REPLACEMENT_CHARACTER);
                return TOKEN_HANDLED;
            } // break;
            case CHARACTER_TABULATION: // fall through
            case LINE_FEED: // fall through
            case FORM_FEED: // fall through
            case CARRIAGE_RETURN: // fall through
            case SPACE: {
                insertCharacter(character);
                return TOKEN_HANDLED;
            } // break;
            default: {
                insertCharacter(character);
                setFramesetOKFlag(NOT_OK);
                return TOKEN_HANDLED;
            } // break;
            }
        } // break;
        case COMMENT: {
            final CommentToken commentToken = (CommentToken) token;
            insertComment(commentToken);
            return TOKEN_HANDLED;
        } // break;
        case DOCTYPE: {
            reportParseError(ParseErrors.DOCTYPE_IN_FOREIGN_CONTENT, "Unexpected DOCTYPE token in foreign content: " + token);
            
            return IGNORE_TOKEN;
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "b": // fall through
            case "big": // fall through
            case "blockquote": // fall through
            case "body": // fall through
            case "br": // fall through
            case "center": // fall through
            case "code": // fall through
            case "dd": // fall through
            case "div": // fall through
            case "dl": // fall through
            case "dt": // fall through
            case "em": // fall through
            case "embed": // fall through
            case "h1": // fall through
            case "h2": // fall through
            case "h3": // fall through
            case "h4": // fall through
            case "h5": // fall through
            case "h6": // fall through
            case "head": // fall through
            case "hr": // fall through
            case "i": // fall through
            case "img": // fall through
            case "li": // fall through
            case "listing": // fall through
            case "main": // fall through
            case "meta": // fall through
            case "nobr": // fall through
            case "ol": // fall through
            case "p": // fall through
            case "pre": // fall through
            case "ruby": // fall through
            case "s": // fall through
            case "small": // fall through
            case "span": // fall through
            case "strong": // fall through
            case "strike": // fall through
            case "sub": // fall through
            case "sup": // fall through
            case "table": // fall through
            case "tt": // fall through
            case "u": // fall through
            case "ul": // fall through
            case "var": {
                reportParseError(ParseErrors.UNEXPECTED_ELEMENT_IN_FOREIGN_CONTENT, "Unexpected start tag token in foreign content: " + token);
                
                if (isHTMLFragmentParsingAlgorithm()) {
                    return anyOtherStartTag(startTagToken);
                } else {
                    popCurrentNode();
                    while (!parser.isHTMLIntegrationPoint(getCurrentNode())
                            && !parser.isMathMLTextIntegrationPoint(getCurrentNode())
                            && !getCurrentNode().getNamespaceURI().equals(HTML_NAMESPACE)) {
                        popCurrentNode();
                    }
                    return REPROCESS_TOKEN;
                }
            } // break;
            case "font": {
                final Attribute colorAttribute = getAttributeNamed(startTagToken, "color");
                final Attribute faceAttribute = getAttributeNamed(startTagToken, "face");
                final Attribute sizeAttribute = getAttributeNamed(startTagToken, "size");
                if (colorAttribute != null || faceAttribute != null || sizeAttribute != null) {
                    reportParseError(ParseErrors.UNEXPECTED_ELEMENT_IN_FOREIGN_CONTENT, "Unexpected start tag token in foreign content: " + token);
                    
                    if (isHTMLFragmentParsingAlgorithm()) {
                        return anyOtherStartTag(startTagToken);
                    } else {
                        popCurrentNode();
                        while (!parser.isHTMLIntegrationPoint(getCurrentNode())
                                && !parser.isMathMLTextIntegrationPoint(getCurrentNode())
                                && !getCurrentNode().getNamespaceURI().equals(HTML_NAMESPACE)) {
                            popCurrentNode();
                        }
                        return REPROCESS_TOKEN;
                    }
                } else {
                    return anyOtherStartTag(startTagToken);
                }
            } // break;
            default: {
                return anyOtherStartTag(startTagToken);
            } // break;
            }
        } // break;
        case END_TAG: {
            final EndTagToken endTagToken = (EndTagToken) token;
            final String tagName = endTagToken.getTagName();
            switch (tagName) {
            case "script": {
                // if isElementA(getCurrentNode(), "script", SVG_NAMESPACE)
                // then do lots of stuff
                // else
                return anyOtherEndTag(endTagToken);
            } // break;
            default: {
                return anyOtherEndTag(endTagToken);
            } // break;
            }
        } // break;
        default: {
        }
            break;
        }
        return false;
    }
    
    private boolean anyOtherStartTag(final StartTagToken startTagToken) {
        final Element adjustedCurrentNode = getAdjustedCurrentNode();
        if (adjustedCurrentNode.getNamespaceURI().equals(MATHML_NAMESPACE)) {
            /*
             * TODO: adjust MathML attributes
             */
        }
        if (adjustedCurrentNode.getNamespaceURI().equals(SVG_NAMESPACE)) {
            /*
             * TODO: replace tag name using table provided, or leave it
             */
            /*
             * TODO: adjust SVG attributes.
             */
        }
        /*
         * TODO: adjust foreign attributes
         */
        insertForeignElement(startTagToken, adjustedCurrentNode.getNamespaceURI());
        if (startTagToken.isSelfClosing()) {
            if (startTagToken.getTagName().equals("script")) {
                /*
                 * run script end tag logic
                 */
            } else {
                popCurrentNode();
                acknowledgeTokenSelfClosingFlag(startTagToken);
            }
        }
        return TOKEN_HANDLED;
    }
    
    private boolean anyOtherEndTag(final EndTagToken endTagToken) {
        Element node = getCurrentNode();
        if (!node.getTagName().toLowerCase(Locale.ENGLISH).equals(endTagToken.getTagName())) {
//            reportParseError(errorType, message);
        }
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
