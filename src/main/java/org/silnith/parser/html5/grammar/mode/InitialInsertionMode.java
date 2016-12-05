package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.grammar.dom.AfterLastChildInsertionPosition;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.DOCTYPEToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Document;


/**
 * Applies the rules for the initial insertion mode.
 * <p>
 * When the user agent is to apply the rules for the "initial" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
 *   <dd>
 *     <p>Ignore the token.
 *   </dd>
 *   <dt>A comment token
 *   <dd>
 *     <p>Insert a comment as the last child of the Document object.
 *   </dd>
 *   <dt>A DOCTYPE token
 *   <dd>
 *     <p>If the DOCTYPE token's name is not a case-sensitive match for the string "html", or the token's public identifier is not missing, or the token's system identifier is neither missing nor a case-sensitive match for the string "about:legacy-compat", and none of the sets of conditions in the following list are matched, then there is a parse error.
 *     <ul>
 *       <li>The DOCTYPE token's name is a case-sensitive match for the string "html", the token's public identifier is the case-sensitive string "-//W3C//DTD HTML 4.0//EN", and the token's system identifier is either missing or the case-sensitive string "http://www.w3.org/TR/REC-html40/strict.dtd".
 *       <li>The DOCTYPE token's name is a case-sensitive match for the string "html", the token's public identifier is the case-sensitive string "-//W3C//DTD HTML 4.01//EN", and the token's system identifier is either missing or the case-sensitive string "http://www.w3.org/TR/html4/strict.dtd".
 *       <li>The DOCTYPE token's name is a case-sensitive match for the string "html", the token's public identifier is the case-sensitive string "-//W3C//DTD XHTML 1.0 Strict//EN", and the token's system identifier is the case-sensitive string "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd".
 *       <li>The DOCTYPE token's name is a case-sensitive match for the string "html", the token's public identifier is the case-sensitive string "-//W3C//DTD XHTML 1.1//EN", and the token's system identifier is the case-sensitive string "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd".
 *     </ul>
 *     <p>Conformance checkers may, based on the values (including presence or lack thereof) of the DOCTYPE token's name, public identifier, or system identifier, switch to a conformance checking mode for another language (e.g. based on the DOCTYPE token a conformance checker could recognize that the document is an HTML4-era document, and defer to an HTML4 conformance checker.)
 *     <p>Append a DocumentType node to the Document node, with the name attribute set to the name given in the DOCTYPE token, or the empty string if the name was missing; the publicId attribute set to the public identifier given in the DOCTYPE token, or the empty string if the public identifier was missing; the systemId attribute set to the system identifier given in the DOCTYPE token, or the empty string if the system identifier was missing; and the other attributes specific to DocumentType objects set to null and empty lists as appropriate. Associate the DocumentType node with the Document object so that it is returned as the value of the doctype attribute of the Document object.
 *     <p>Then, if the document is not an iframe srcdoc document, and the DOCTYPE token matches one of the conditions in the following list, then set the Document to quirks mode:
 *     <ul>
 *       <li>The force-quirks flag is set to on.
 *       <li> The name is set to anything other than "html" (compared case-sensitively).
 *       <li> The public identifier starts with: "+//Silmaril//dtd html Pro v0r11 19970101//"
 *       <li> The public identifier starts with: "-//AdvaSoft Ltd//DTD HTML 3.0 asWedit + extensions//"
 *       <li> The public identifier starts with: "-//AS//DTD HTML 3.0 asWedit + extensions//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 2.0 Level 1//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 2.0 Level 2//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 2.0 Strict Level 1//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 2.0 Strict Level 2//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 2.0 Strict//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 2.0//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 2.1E//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 3.0//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 3.2 Final//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 3.2//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML 3//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML Level 0//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML Level 1//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML Level 2//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML Level 3//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML Strict Level 0//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML Strict Level 1//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML Strict Level 2//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML Strict Level 3//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML Strict//"
 *       <li> The public identifier starts with: "-//IETF//DTD HTML//"
 *       <li> The public identifier starts with: "-//Metrius//DTD Metrius Presentational//"
 *       <li> The public identifier starts with: "-//Microsoft//DTD Internet Explorer 2.0 HTML Strict//"
 *       <li> The public identifier starts with: "-//Microsoft//DTD Internet Explorer 2.0 HTML//"
 *       <li> The public identifier starts with: "-//Microsoft//DTD Internet Explorer 2.0 Tables//"
 *       <li> The public identifier starts with: "-//Microsoft//DTD Internet Explorer 3.0 HTML Strict//"
 *       <li> The public identifier starts with: "-//Microsoft//DTD Internet Explorer 3.0 HTML//"
 *       <li> The public identifier starts with: "-//Microsoft//DTD Internet Explorer 3.0 Tables//"
 *       <li> The public identifier starts with: "-//Netscape Comm. Corp.//DTD HTML//"
 *       <li> The public identifier starts with: "-//Netscape Comm. Corp.//DTD Strict HTML//"
 *       <li> The public identifier starts with: "-//O'Reilly and Associates//DTD HTML 2.0//"
 *       <li> The public identifier starts with: "-//O'Reilly and Associates//DTD HTML Extended 1.0//"
 *       <li> The public identifier starts with: "-//O'Reilly and Associates//DTD HTML Extended Relaxed 1.0//"
 *       <li> The public identifier starts with: "-//SoftQuad Software//DTD HoTMetaL PRO 6.0::19990601::extensions to HTML 4.0//"
 *       <li> The public identifier starts with: "-//SoftQuad//DTD HoTMetaL PRO 4.0::19971010::extensions to HTML 4.0//"
 *       <li> The public identifier starts with: "-//Spyglass//DTD HTML 2.0 Extended//"
 *       <li> The public identifier starts with: "-//SQ//DTD HTML 2.0 HoTMetaL + extensions//"
 *       <li> The public identifier starts with: "-//Sun Microsystems Corp.//DTD HotJava HTML//"
 *       <li> The public identifier starts with: "-//Sun Microsystems Corp.//DTD HotJava Strict HTML//"
 *       <li> The public identifier starts with: "-//W3C//DTD HTML 3 1995-03-24//"
 *       <li> The public identifier starts with: "-//W3C//DTD HTML 3.2 Draft//"
 *       <li> The public identifier starts with: "-//W3C//DTD HTML 3.2 Final//"
 *       <li> The public identifier starts with: "-//W3C//DTD HTML 3.2//"
 *       <li> The public identifier starts with: "-//W3C//DTD HTML 3.2S Draft//"
 *       <li> The public identifier starts with: "-//W3C//DTD HTML 4.0 Frameset//"
 *       <li> The public identifier starts with: "-//W3C//DTD HTML 4.0 Transitional//"
 *       <li> The public identifier starts with: "-//W3C//DTD HTML Experimental 19960712//"
 *       <li> The public identifier starts with: "-//W3C//DTD HTML Experimental 970421//"
 *       <li> The public identifier starts with: "-//W3C//DTD W3 HTML//"
 *       <li> The public identifier starts with: "-//W3O//DTD W3 HTML 3.0//"
 *       <li> The public identifier is set to: "-//W3O//DTD W3 HTML Strict 3.0//EN//"
 *       <li> The public identifier starts with: "-//WebTechs//DTD Mozilla HTML 2.0//"
 *       <li> The public identifier starts with: "-//WebTechs//DTD Mozilla HTML//"
 *       <li> The public identifier is set to: "-/W3C/DTD HTML 4.0 Transitional/EN"
 *       <li> The public identifier is set to: "HTML"
 *       <li> The system identifier is set to: "http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd"
 *       <li> The system identifier is missing and the public identifier starts with: "-//W3C//DTD HTML 4.01 Frameset//"
 *       <li> The system identifier is missing and the public identifier starts with: "-//W3C//DTD HTML 4.01 Transitional//"
 *     </ul>
 *     <p>Otherwise, if the document is not an iframe srcdoc document, and the DOCTYPE token matches one of the conditions in the following list, then set the Document to limited-quirks mode:
 *     <ul>
 *       <li> The public identifier starts with: "-//W3C//DTD XHTML 1.0 Frameset//"
 *       <li> The public identifier starts with: "-//W3C//DTD XHTML 1.0 Transitional//"
 *       <li> The system identifier is not missing and the public identifier starts with: "-//W3C//DTD HTML 4.01 Frameset//"
 *       <li> The system identifier is not missing and the public identifier starts with: "-//W3C//DTD HTML 4.01 Transitional//"
 *     </ul>
 *     <p>The system identifier and public identifier strings must be compared to the values given in the lists above in an ASCII case-insensitive manner. A system identifier whose value is the empty string is not considered missing for the purposes of the conditions above.
 *     <p>Then, switch the insertion mode to "before html".
 *   </dd>
 *   <dt>Anything else
 *   <dd>
 *     <p>If the document is not an iframe srcdoc document, then this is a parse error; set the Document to quirks mode.
 *     <p>In any case, switch the insertion mode to "before html", then reprocess the token.
 *   </dd>
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#INITIAL
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-initial-insertion-mode">8.2.5.4.1 The "initial" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InitialInsertionMode extends InsertionMode {
    
    public InitialInsertionMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        final Document document = getDocument();
        
        switch (token.getType()) {
        case CHARACTER: {
            final CharacterToken characterToken = (CharacterToken) token;
            final char content = characterToken.getCharacter();
            switch (content) {
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
        case COMMENT: {
            final CommentToken commentToken = (CommentToken) token;
            insertComment(commentToken, new AfterLastChildInsertionPosition(document));
            return TOKEN_HANDLED;
        } // break;
        case DOCTYPE: {
            final DOCTYPEToken doctypeToken = (DOCTYPEToken) token;
            final String name = doctypeToken.getName();
            final String publicIdentifier = doctypeToken.getPublicIdentifier();
            final String systemIdentifier = doctypeToken.getSystemIdentifier();
            
            if (name.equals("html")) {
                // valid
            } else if (publicIdentifier != null) {
                // valid
            } else if (systemIdentifier != null && !systemIdentifier.equals("about:legacy-compat")) {
                // valid
            } else if (name.equals("html")
                    && (publicIdentifier != null && publicIdentifier.equals("-//W3C//DTD HTML 4.0//EN"))
                    && (systemIdentifier == null
                            || systemIdentifier.equals("http://www.w3.org/TR/REC-html40/strict.dtd"))) {
                // valid
            } else if (name.equals("html")
                    && (publicIdentifier != null && publicIdentifier.equals("-//W3C//DTD HTML 4.01//EN"))
                    && (systemIdentifier == null || systemIdentifier.equals("http://www.w3.org/TR/html4/strict.dtd"))) {
                // valid
            } else if (name.equals("html")
                    && (publicIdentifier != null && publicIdentifier.equals("-//W3C//DTD XHTML 1.0 Strict//EN"))
                    && (systemIdentifier != null
                            && systemIdentifier.equals("http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"))) {
                // valid
            } else if (name.equals("html")
                    && (publicIdentifier != null && publicIdentifier.equals("-//W3C//DTD XHTML 1.1//EN"))
                    && (systemIdentifier != null
                            && systemIdentifier.equals("http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"))) {
                // valid
            } else {
                // parse error
            }
            
//            final DOMImplementation implementation = document.getImplementation();
//            final DocumentType documentType = implementation.createDocumentType(
//                    name == null ? "" : name,
//                            publicIdentifier == null ? "" : publicIdentifier,
//                                    systemIdentifier == null ? "" : systemIdentifier);
//            implementation.createDocument(HTML_NAMESPACE, "html", documentType);
//            document.appendChild(documentType);
            
            setInsertionMode(Parser.Mode.BEFORE_HTML);
            return TOKEN_HANDLED;
        } // break;
        default: {
            return anythingElse(token);
        } // break;
        }
    }
    
    private boolean anythingElse(final Token token) {
        final boolean isIframeSrcdocDocument = false;
        if (!isIframeSrcdocDocument) {
            if (isAllowParseErrors()) {
                // set document to quirks mode
            } else {
                throw new ParseErrorException("Unexpected content before document: " + token);
            }
        }
        setInsertionMode(Parser.Mode.BEFORE_HTML);
        return REPROCESS_TOKEN;
    }
    
}
