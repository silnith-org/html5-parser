package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.CHARACTER_TABULATION;
import static org.silnith.parser.util.UnicodeCodePoints.FORM_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;
import static org.silnith.parser.util.UnicodeCodePoints.NULL;
import static org.silnith.parser.util.UnicodeCodePoints.SPACE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.Parser.FormattingElement;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Applies the in body insertion mode logic.
 * <p>When the user agent is to apply the rules for the "in body" insertion mode, the user agent must handle the token as follows:</p>
 * <dl>
 *   <dt>A character token that is U+0000 NULL
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 *   <dt>A character token that is one of U+0009 CHARACTER TABULATION, "LF" (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert the token's character.</p>
 *   </dd>
 *   <dt>Any other character token
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert the token's character.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *   </dd>
 *   <dt>A comment token
 *   <dd>
 *     <p>Insert a comment.</p>
 *   </dd>
 *   <dt>A DOCTYPE token
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "html"
 *   <dd>
 *     <p>Parse error.</p>
 *     <p>If there is a template element on the stack of open elements, then ignore the token.</p>
 *     <p>Otherwise, for each attribute on the token, check to see if the attribute is already present on the top element of the stack of open elements. If it is not, add the attribute and its corresponding value to that element.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "template", "title"
 *   <dt>An end tag whose tag name is "template"
 *   <dd>
 *     <p>Process the token using the rules for the "in head" insertion mode.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "body"
 *   <dd>
 *     <p>Parse error.</p>
 *     <p>If the second element on the stack of open elements is not a body element, if the stack of open elements has only one node on it, or if there is a template element on the stack of open elements, then ignore the token. (fragment case)</p>
 *     <p>Otherwise, set the frameset-ok flag to "not ok"; then, for each attribute on the token, check to see if the attribute is already present on the body element (the second element) on the stack of open elements, and if it is not, add the attribute and its corresponding value to that element.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "frameset"
 *   <dd>
 *     <p>Parse error.</p>
 *     <p>If the stack of open elements has only one node on it, or if the second element on the stack of open elements is not a body element, then ignore the token. (fragment case)</p>
 *     <p>If the frameset-ok flag is set to "not ok", ignore the token.</p>
 *     <p>Otherwise, run the following steps:</p>
 *     <ol>
 *       <li>Remove the second element on the stack of open elements from its parent node, if it has one.
 *       <li>Pop all the nodes from the bottom of the stack of open elements, from the current node up to, but not including, the root html element.
 *       <li>Insert an HTML element for the token.
 *       <li>Switch the insertion mode to "in frameset".
 *     </ol>
 *   </dd>
 *   <dt>An end-of-file token
 *   <dd>
 *     <p>If there is a node in the stack of open elements that is not either a dd element, a dt element, an li element, a p element, a tbody element, a td element, a tfoot element, a th element, a thead element, a tr element, the body element, or the html element, then this is a parse error.</p>
 *     <p>If the stack of template insertion modes is not empty, then process the token using the rules for the "in template" insertion mode.</p>
 *     <p>Otherwise, stop parsing.</p>
 *   </dd>
 *   <dt>An end tag whose tag name is "body"
 *   <dd>
 *     <p>If the stack of open elements does not have a body element in scope, this is a parse error; ignore the token.</p>
 *     <p>Otherwise, if there is a node in the stack of open elements that is not either a dd element, a dt element, an li element, an optgroup element, an option element, a p element, an rb element, an rp element, an rt element, an rtc element, a tbody element, a td element, a tfoot element, a th element, a thead element, a tr element, the body element, or the html element, then this is a parse error.</p>
 *     <p>Switch the insertion mode to "after body".</p>
 *   </dd>
 *   <dt>An end tag whose tag name is "html"
 *   <dd>
 *     <p>If the stack of open elements does not have a body element in scope, this is a parse error; ignore the token.</p>
 *     <p>Otherwise, if there is a node in the stack of open elements that is not either a dd element, a dt element, an li element, an optgroup element, an option element, a p element, an rb element, an rp element, an rt element, an rtc element, a tbody element, a td element, a tfoot element, a th element, a thead element, a tr element, the body element, or the html element, then this is a parse error.</p>
 *     <p>Switch the insertion mode to "after body".</p>
 *     <p>Reprocess the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "address", "article", "aside", "blockquote", "center", "details", "dialog", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "main", "nav", "ol", "p", "section", "summary", "ul"
 *   <dd>
 *     <p>If the stack of open elements has a p element in button scope, then close a p element.</p>
 *     <p>Insert an HTML element for the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "h1", "h2", "h3", "h4", "h5", "h6"
 *   <dd>
 *     <p>If the stack of open elements has a p element in button scope, then close a p element.</p>
 *     <p>If the current node is an HTML element whose tag name is one of "h1", "h2", "h3", "h4", "h5", or "h6", then this is a parse error; pop the current node off the stack of open elements.</p>
 *     <p>Insert an HTML element for the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "pre", "listing"
 *   <dd>
 *     <p>If the stack of open elements has a p element in button scope, then close a p element.</p>
 *     <p>Insert an HTML element for the token.</p>
 *     <p>If the next token is a "LF" (U+000A) character token, then ignore that token and move on to the next one. (Newlines at the start of pre blocks are ignored as an authoring convenience.)</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "form"
 *   <dd>
 *     <p>If the form element pointer is not null, and there is no template element on the stack of open elements, then this is a parse error; ignore the token.</p>
 *     <p>Otherwise:</p>
 *     <p>If the stack of open elements has a p element in button scope, then close a p element.</p>
 *     <p>Insert an HTML element for the token, and, if there is no template element on the stack of open elements, set the form element pointer to point to the element created.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "li"
 *   <dd>
 *     <p>Run these steps:</p>
 *     <ol>
 *       <li>Set the frameset-ok flag to "not ok".
 *       <li>Initialize node to be the current node (the bottommost node of the stack).
 *       <li>
 *         Loop: If node is an li element, then run these substeps:
 *         <ol>
 *           <li>Generate implied end tags, except for li elements.
 *           <li>If the current node is not an li element, then this is a parse error.
 *           <li>Pop elements from the stack of open elements until an li element has been popped from the stack.
 *           <li>Jump to the step labeled done below.
 *         </ol>
 *       </li>
 *       <li>If node is in the special category, but is not an address, div, or p element, then jump to the step labeled done below.
 *       <li>Otherwise, set node to the previous entry in the stack of open elements and return to the step labeled loop.
 *       <li>Done: If the stack of open elements has a p element in button scope, then close a p element.
 *       <li>Finally, insert an HTML element for the token.
 *     </ol>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "dd", "dt"
 *   <dd>
 *     <p>Run these steps:</p>
 *     <ol>
 *       <li>Set the frameset-ok flag to "not ok".
 *       <li>Initialize node to be the current node (the bottommost node of the stack).
 *       <li>
 *         Loop: If node is a dd element, then run these substeps:
 *         <ol>
 *           <li>Generate implied end tags, except for dd elements.
 *           <li>If the current node is not a dd element, then this is a parse error.
 *           <li>Pop elements from the stack of open elements until a dd element has been popped from the stack.
 *           <li>Jump to the step labeled done below.
 *         </ol>
 *       </li>
 *       <li>
 *         If node is a dt element, then run these substeps:
 *         <ol>
 *           <li>Generate implied end tags, except for dt elements.
 *           <li>If the current node is not a dt element, then this is a parse error.
 *           <li>Pop elements from the stack of open elements until a dt element has been popped from the stack.
 *           <li>Jump to the step labeled done below.
 *         </ol>
 *       </li>
 *       <li>If node is in the special category, but is not an address, div, or p element, then jump to the step labeled done below.
 *       <li>Otherwise, set node to the previous entry in the stack of open elements and return to the step labeled loop.
 *       <li>Done: If the stack of open elements has a p element in button scope, then close a p element.
 *       <li>Finally, insert an HTML element for the token.
 *     </ol>
 *   </dd>
 *   <dt>A start tag whose tag name is "plaintext"
 *   <dd>
 *     <p>If the stack of open elements has a p element in button scope, then close a p element.</p>
 *     <p>Insert an HTML element for the token.</p>
 *     <p>Switch the tokenizer to the PLAINTEXT state.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "button"
 *   <dd>
 *     <ol>
 *       <li>
 *         If the stack of open elements has a button element in scope, then run these substeps:
 *         <ol>
 *           <li>Parse error.
 *           <li>Generate implied end tags.
 *           <li>Pop elements from the stack of open elements until a button element has been popped from the stack.
 *         </ol>
 *       </li>
 *       <li>Reconstruct the active formatting elements, if any.
 *       <li>Insert an HTML element for the token.
 *       <li>Set the frameset-ok flag to "not ok".
 *     </ol>
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "address", "article", "aside", "blockquote", "button", "center", "details", "dialog", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "main", "nav", "ol", "pre", "section", "summary", "ul"
 *   <dd>
 *     <p>If the stack of open elements does not have an element in scope that is an HTML element and with the same tag name as that of the token, then this is a parse error; ignore the token.</p>
 *     <p>Otherwise, run these steps:</p>
 *     <ol>
 *       <li>Generate implied end tags.
 *       <li>If the current node is not an HTML element with the same tag name as that of the token, then this is a parse error.
 *       <li>Pop elements from the stack of open elements until an HTML element with the same tag name as the token has been popped from the stack.
 *     </ol>
 *   </dd>
 *   <dt>An end tag whose tag name is "form"
 *   <dd>
 *     <p>If there is no template element on the stack of open elements, then run these substeps:</p>
 *     <ol>
 *       <li>Let node be the element that the form element pointer is set to, or null if it is not set to an element.
 *       <li>Set the form element pointer to null. Otherwise, let node be null.
 *       <li>If node is null or if the stack of open elements does not have node in scope, then this is a parse error; abort these steps and ignore the token.
 *       <li>Generate implied end tags.
 *       <li>If the current node is not node, then this is a parse error.
 *       <li>Remove node from the stack of open elements.
 *     </ol>
 *     <p>If there is a template element on the stack of open elements, then run these substeps instead:</p>
 *     <ol>
 *       <li>If the stack of open elements does not have a form element in scope, then this is a parse error; abort these steps and ignore the token.
 *       <li>Generate implied end tags.
 *       <li>If the current node is not a form element, then this is a parse error.
 *       <li>Pop elements from the stack of open elements until a form element has been popped from the stack.
 *     </ol>
 *   </dd>
 *   <dt>An end tag whose tag name is "p"
 *   <dd>
 *     <p>If the stack of open elements does not have a p element in button scope, then this is a parse error; insert an HTML element for a "p" start tag token with no attributes.</p>
 *     <p>Close a p element.</p>
 *   </dd>
 *   <dt>An end tag whose tag name is "li"
 *   <dd>
 *     <p>If the stack of open elements does not have an li element in list item scope, then this is a parse error; ignore the token.</p>
 *     <p>Otherwise, run these steps:</p>
 *     <ol>
 *       <li>Generate implied end tags, except for li elements.
 *       <li>If the current node is not an li element, then this is a parse error.
 *       <li>Pop elements from the stack of open elements until an li element has been popped from the stack.
 *     </ol>
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "dd", "dt"
 *   <dd>
 *     <p>If the stack of open elements does not have an element in scope that is an HTML element and with the same tag name as that of the token, then this is a parse error; ignore the token.</p>
 *     <p>Otherwise, run these steps:</p>
 *     <ol>
 *       <li>Generate implied end tags, except for HTML elements with the same tag name as the token.
 *       <li>If the current node is not an HTML element with the same tag name as that of the token, then this is a parse error.
 *       <li>Pop elements from the stack of open elements until an HTML element with the same tag name as the token has been popped from the stack.
 *     </ol>
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "h1", "h2", "h3", "h4", "h5", "h6"
 *   <dd>
 *     <p>If the stack of open elements does not have an element in scope that is an HTML element and whose tag name is one of "h1", "h2", "h3", "h4", "h5", or "h6", then this is a parse error; ignore the token.</p>
 *     <p>Otherwise, run these steps:</p>
 *     <ol>
 *       <li>Generate implied end tags.
 *       <li>If the current node is not an HTML element with the same tag name as that of the token, then this is a parse error.
 *       <li>Pop elements from the stack of open elements until an HTML element whose tag name is one of "h1", "h2", "h3", "h4", "h5", or "h6" has been popped from the stack.
 *     </ol>
 *   </dd>
 *   <dt>An end tag whose tag name is "sarcasm"
 *   <dd>
 *     <p>Take a deep breath, then act as described in the "any other end tag" entry below.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "a"
 *   <dd>
 *     <p>If the list of active formatting elements contains an a element between the end of the list and the last marker on the list (or the start of the list if there is no marker on the list), then this is a parse error; run the adoption agency algorithm for the tag name "a", then remove that element from the list of active formatting elements and the stack of open elements if the adoption agency algorithm didn't already remove it (it might not have if the element is not in table scope).</p>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for the token. Push onto the list of active formatting elements that element.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "b", "big", "code", "em", "font", "i", "s", "small", "strike", "strong", "tt", "u"
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for the token. Push onto the list of active formatting elements that element.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "nobr"
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>If the stack of open elements has a nobr element in scope, then this is a parse error; run the adoption agency algorithm for the tag name "nobr", then once again reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for the token. Push onto the list of active formatting elements that element.</p>
 *   </dd>
 *   <dt>An end tag whose tag name is one of: "a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"
 *   <dd>
 *     <p>Run the adoption agency algorithm for the token's tag name.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "applet", "marquee", "object"
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for the token.</p>
 *     <p>Insert a marker at the end of the list of active formatting elements.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *   </dd>
 *   <dt>An end tag token whose tag name is one of: "applet", "marquee", "object"
 *   <dd>
 *     <p>If the stack of open elements does not have an element in scope that is an HTML element and with the same tag name as that of the token, then this is a parse error; ignore the token.</p>
 *     <p>Otherwise, run these steps:</p>
 *     <ol>
 *       <li>Generate implied end tags.
 *       <li>If the current node is not an HTML element with the same tag name as that of the token, then this is a parse error.
 *       <li>Pop elements from the stack of open elements until an HTML element with the same tag name as the token has been popped from the stack.
 *       <li>Clear the list of active formatting elements up to the last marker.
 *     </ol>
 *   </dd>
 *   <dt>A start tag whose tag name is "table"
 *   <dd>
 *     <p>If the Document is not set to quirks mode, and the stack of open elements has a p element in button scope, then close a p element.</p>
 *     <p>Insert an HTML element for the token.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *     <p>Switch the insertion mode to "in table".</p>
 *   </dd>
 *   <dt>An end tag whose tag name is "br"
 *   <dd>
 *     <p>Parse error. Act as described in the next entry, as if this was a "br" start tag token, rather than an end tag token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "area", "br", "embed", "img", "keygen", "wbr"
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for the token. Immediately pop the current node off the stack of open elements.</p>
 *     <p>Acknowledge the token's self-closing flag, if it is set.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "input"
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for the token. Immediately pop the current node off the stack of open elements.</p>
 *     <p>Acknowledge the token's self-closing flag, if it is set.</p>
 *     <p>If the token does not have an attribute with the name "type", or if it does, but that attribute's value is not an ASCII case-insensitive match for the string "hidden", then: set the frameset-ok flag to "not ok".</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "param", "source", "track"
 *   <dd>
 *     <p>Insert an HTML element for the token. Immediately pop the current node off the stack of open elements.</p>
 *     <p>Acknowledge the token's self-closing flag, if it is set.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "hr"
 *   <dd>
 *     <p>If the stack of open elements has a p element in button scope, then close a p element.</p>
 *     <p>Insert an HTML element for the token. Immediately pop the current node off the stack of open elements.</p>
 *     <p>Acknowledge the token's self-closing flag, if it is set.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "image"
 *   <dd>
 *     <p>Parse error. Change the token's tag name to "img" and reprocess it. (Don't ask.)</p>
 *   <dt>A start tag whose tag name is "isindex"
 *   <dd>
 *     <p>Parse error.</p>
 *     <p>If there is no template element on the stack of open elements and the form element pointer is not null, then ignore the token.</p>
 *     <p>Otherwise:</p>
 *     <p>Acknowledge the token's self-closing flag, if it is set.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *     <p>If the stack of open elements has a p element in button scope, then close a p element.</p>
 *     <p>Insert an HTML element for a "form" start tag token with no attributes, and, if there is no template element on the stack of open elements, set the form element pointer to point to the element created.</p>
 *     <p>If the token has an attribute called "action", set the action attribute on the resulting form element to the value of the "action" attribute of the token.</p>
 *     <p>Insert an HTML element for an "hr" start tag token with no attributes. Immediately pop the current node off the stack of open elements.</p>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for a "label" start tag token with no attributes.</p>
 *     <p>Insert characters (see below for what they should say).</p>
 *     <p>Insert an HTML element for an "input" start tag token with all the attributes from the "isindex" token except "name", "action", and "prompt", and with an attribute named "name" with the value "isindex". (This creates an input element with the name attribute set to the magic balue "isindex".) Immediately pop the current node off the stack of open elements.</p>
 *     <p>Insert more characters (see below for what they should say).</p>
 *     <p>Pop the current node (which will be the label element created earlier) off the stack of open elements.</p>
 *     <p>Insert an HTML element for an "hr" start tag token with no attributes. Immediately pop the current node off the stack of open elements.</p>
 *     <p>Pop the current node (which will be the form element created earlier) off the stack of open elements, and, if there is no template element on the stack of open elements, set the form element pointer back to null.</p>
 *     <p>Prompt: If the token has an attribute with the name "prompt", then the first stream of characters must be the same string as given in that attribute, and the second stream of characters must be empty. Otherwise, the two streams of character tokens together should, together with the input element, express the equivalent of "This is a searchable index. Enter search keywords: (input field)" in the user's preferred language.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "textarea"
 *   <dd>
 *     <p>Run these steps:</p>
 *     <ol>
 *       <li>Insert an HTML element for the token.
 *       <li>If the next token is a "LF" (U+000A) character token, then ignore that token and move on to the next one. (Newlines at the start of textarea elements are ignored as an authoring convenience.)
 *       <li>Switch the tokenizer to the RCDATA state.
 *       <li>Let the original insertion mode be the current insertion mode.
 *       <li>Set the frameset-ok flag to "not ok".
 *       <li>Switch the insertion mode to "text".
 *     </ol>
 *   </dd>
 *   <dt>A start tag whose tag name is "xmp"
 *   <dd>
 *     <p>If the stack of open elements has a p element in button scope, then close a p element.</p>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *     <p>Follow the generic raw text element parsing algorithm.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "iframe"
 *   <dd>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *     <p>Follow the generic raw text element parsing algorithm.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "noembed"
 *   <dt>A start tag whose tag name is "noscript", if the scripting flag is enabled
 *   <dd>
 *     <p>Follow the generic raw text element parsing algorithm.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "select"
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for the token.</p>
 *     <p>Set the frameset-ok flag to "not ok".</p>
 *     <p>If the insertion mode is one of "in table", "in caption", "in table body", "in row", or "in cell", then switch the insertion mode to "in select in table". Otherwise, switch the insertion mode to "in select".</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "optgroup", "option"
 *   <dd>
 *     <p>If the current node is an option element, then pop the current node off the stack of open elements.</p>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "rb", "rp", "rtc"
 *   <dd>
 *     <p>If the stack of open elements has a ruby element in scope, then generate implied end tags. If the current node is not then a ruby element, this is a parse error.</p>
 *     <p>Insert an HTML element for the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "rt"
 *   <dd>
 *     <p>If the stack of open elements has a ruby element in scope, then generate implied end tags, except for rtc elements. If the current node is not then a ruby element or an rtc element, this is a parse error.</p>
 *     <p>Insert an HTML element for the token.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "math"
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Adjust MathML attributes for the token. (This fixes the case of MathML attributes that are not all lowercase.)</p>
 *     <p>Adjust foreign attributes for the token. (This fixes the use of namespaced attributes, in particular XLink.)</p>
 *     <p>Insert a foreign element for the token, in the MathML namespace.</p>
 *     <p>If the token has its self-closing flag set, pop the current node off the stack of open elements and acknowledge the token's self-closing flag.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is "svg"
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Adjust SVG attributes for the token. (This fixes the case of SVG attributes that are not all lowercase.)</p>
 *     <p>Adjust foreign attributes for the token. (This fixes the use of namespaced attributes, in particular XLink in SVG.)</p>
 *     <p>Insert a foreign element for the token, in the SVG namespace.</p>
 *     <p>If the token has its self-closing flag set, pop the current node off the stack of open elements and acknowledge the token's self-closing flag.</p>
 *   </dd>
 *   <dt>A start tag whose tag name is one of: "caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr"
 *   <dd>
 *     <p>Parse error. Ignore the token.</p>
 *   </dd>
 *   <dt>Any other start tag
 *   <dd>
 *     <p>Reconstruct the active formatting elements, if any.</p>
 *     <p>Insert an HTML element for the token.</p>
 *   </dd>
 *   <dt>Any other end tag
 *   <dd>
 *     <p>Run these steps:</p>
 *     <ol>
 *       <li>Initialize node to be the current node (the bottommost node of the stack).
 *       <li>
 *         Loop: If node is an HTML element with the same tag name as the token, then:
 *         <ol>
 *           <li>Generate implied end tags, except for HTML elements with the same tag name as the token.
 *           <li>If node is not the current node, then this is a parse error.
 *           <li>Pop all the nodes from the current node up to node, including node, then stop these steps.
 *         </ol>
 *       </li>
 *       <li>Otherwise, if node is in the special category, then this is a parse error; ignore the token, and abort these steps.
 *       <li>Set node to the previous entry in the stack of open elements.
 *       <li>Return to the step labeled loop.
 *     </ol>
 *   </dd>
 * </dl>
 * <p>When the steps above say the user agent is to close a p element, it means that the user agent must run the following steps:</p>
 * <ol>
 *   <li>Generate implied end tags, except for p elements.
 *   <li>If the current node is not a p element, then this is a parse error.
 *   <li>Pop elements from the stack of open elements until a p element has been popped from the stack.
 * </ol>
 * <p>The adoption agency algorithm, which takes as its only argument a tag name subject for which the algorithm is being run, consists of the following steps:</p>
 * <ol>
 *   <li>
 *     If the current node is an HTML element whose tag name is subject, then run these substeps:
 *     <ol>
 *       <li>Let element be the current node.
 *       <li>Pop element off the stack of open elements.
 *       <li>If element is also in the list of active formatting elements, remove the element from the list.
 *       <li>Abort the adoption agency algorithm.
 *     </ol>
 *   </li>
 *   <li>Let outer loop counter be zero.
 *   <li>Outer loop: If outer loop counter is greater than or equal to eight, then abort these steps.
 *   <li>Increment outer loop counter by one.
 *   <li>
 *     Let formatting element be the last element in the list of active formatting elements that:
 *     <ul>
 *       <li>is between the end of the list and the last scope marker in the list, if any, or the start of the list otherwise, and
 *       <li>has the tag name subject.
 *     </ul>
 *     <p>If there is no such element, then abort these steps and instead act as described in the "any other end tag" entry above.
 *   </li>
 *   <li>If formatting element is not in the stack of open elements, then this is a parse error; remove the element from the list, and abort these steps.
 *   <li>If formatting element is in the stack of open elements, but the element is not in scope, then this is a parse error; abort these steps.
 *   <li>If formatting element is not the current node, this is a parse error. (But do not abort these steps.)
 *   <li>Let furthest block be the topmost node in the stack of open elements that is lower in the stack than formatting element, and is an element in the special category. There might not be one.
 *   <li>If there is no furthest block, then the UA must first pop all the nodes from the bottom of the stack of open elements, from the current node up to and including formatting element, then remove formatting element from the list of active formatting elements, and finally abort these steps.
 *   <li>Let common ancestor be the element immediately above formatting element in the stack of open elements.
 *   <li>Let a bookmark note the position of formatting element in the list of active formatting elements relative to the elements on either side of it in the list.
 *   <li>
 *     Let node and last node be furthest block. Follow these steps:
 *     <ol>
 *       <li>Let inner loop counter be zero.
 *       <li>Inner loop: Increment inner loop counter by one.
 *       <li>Let node be the element immediately above node in the stack of open elements, or if node is no longer in the stack of open elements (e.g. because it got removed by this algorithm), the element that was immediately above node in the stack of open elements before node was removed.
 *       <li>If node is formatting element, then go to the next step in the overall algorithm.
 *       <li>If inner loop counter is greater than three and node is in the list of active formatting elements, then remove node from the list of active formatting elements.
 *       <li>If node is not in the list of active formatting elements, then remove node from the stack of open elements and then go back to the step labeled inner loop.
 *       <li>Create an element for the token for which the element node was created, in the HTML namespace, with common ancestor as the intended parent; replace the entry for node in the list of active formatting elements with an entry for the new element, replace the entry for node in the stack of open elements with an entry for the new element, and let node be the new element.
 *       <li>If last node is furthest block, then move the aforementioned bookmark to be immediately after the new node in the list of active formatting elements.
 *       <li>Insert last node into node, first removing it from its previous parent node if any.
 *       <li>Let last node be node.
 *       <li>Return to the step labeled inner loop.
 *     </ol>
 *   </li>
 *   <li>Insert whatever last node ended up being in the previous step at the appropriate place for inserting a node, but using common ancestor as the override target.
 *   <li>Create an element for the token for which formatting element was created, in the HTML namespace, with furthest block as the intended parent.
 *   <li>Take all of the child nodes of furthest block and append them to the element created in the last step.
 *   <li>Append that new element to furthest block.
 *   <li>Remove formatting element from the list of active formatting elements, and insert the new element into the list of active formatting elements at the position of the aforementioned bookmark.
 *   <li>Remove formatting element from the stack of open elements, and insert the new element into the stack of open elements immediately below the position of furthest block in that stack.
 *   <li>Jump back to the step labeled outer loop.
 * </ol>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#IN_BODY
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inbody">8.2.5.4.7 The "in body" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InBodyInsertionMode extends InsertionMode {
    
    public InBodyInsertionMode(final Parser parser) {
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
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Null character in body.");
                }
            } // break;
            case CHARACTER_TABULATION: // fall through
            case LINE_FEED: // fall through
            case FORM_FEED: // fall through
            case CARRIAGE_RETURN: // fall through
            case SPACE: {
                reconstructActiveFormattingElements();
                insertCharacter(character);
                return TOKEN_HANDLED;
            } // break;
            default: {
                reconstructActiveFormattingElements();
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
            if (isAllowParseErrors()) {
                return IGNORE_TOKEN;
            } else {
                throw new ParseErrorException("Unexpected DOCTYPE in body: " + token);
            }
        } // break;
        case START_TAG: {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            switch (tagName) {
            case "html": {
                if (isAllowParseErrors()) {
                    if (isStackOfOpenElementsContains("template")) {
                        return IGNORE_TOKEN;
                    }
                    for (final TagToken.Attribute attribute : startTagToken.getAttributes()) {
                        final String name = attribute.getName();
                        final Element htmlElement = getFirstElementInStackOfOpenElements();
                        if ( !htmlElement.hasAttribute(name)) {
                            htmlElement.setAttribute(name, attribute.getValue());
                        }
                    }
                    return TOKEN_HANDLED;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in body: " + startTagToken);
                }
            } // break;
            case "base": // fall through
            case "basefont": // fall through
            case "bgsound": // fall through
            case "link": // fall through
            case "meta": // fall through
            case "noframes": // fall through
            case "script": // fall through
            case "style": // fall through
            case "template": // fall through
            case "title": {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, startTagToken);
            } // break;
            case "body": {
                if (isAllowParseErrors()) {
                    if (isStackOfOpenElementsHasOnlyOneNode()) {
                        return IGNORE_TOKEN;
                    }
                    final Element bodyElement = getSecondElementOfStackOfOpenElements();
                    if ( !isElementA(bodyElement, "body")) {
                        return IGNORE_TOKEN;
                    }
                    if (isStackOfOpenElementsContains("template")) {
                        return IGNORE_TOKEN;
                    }
                    setFramesetOKFlag(NOT_OK);
                    for (final TagToken.Attribute attribute : startTagToken.getAttributes()) {
                        final String name = attribute.getName();
                        if ( !bodyElement.hasAttribute(name)) {
                            bodyElement.setAttribute(name, attribute.getValue());
                        }
                    }
                    return TOKEN_HANDLED;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in body: " + startTagToken);
                }
            } // break;
            case "frameset": {
                if (isAllowParseErrors()) {
                    if (isStackOfOpenElementsHasOnlyOneNode()) {
                        return IGNORE_TOKEN;
                    }
                    final Element bodyElement = getSecondElementOfStackOfOpenElements();
                    if ( !isElementA(bodyElement, "body")) {
                        return IGNORE_TOKEN;
                    }
                    if ( !isFramesetOkFlag()) {
                        return IGNORE_TOKEN;
                    }
                    if (bodyElement.getParentNode() != null) {
                        bodyElement.getParentNode().removeChild(bodyElement);
                    }
                    Element popped;
                    do {
                        popped = popCurrentNode();
                    } while (popped != bodyElement);
                    insertHTMLElement(startTagToken);
                    setInsertionMode(Parser.Mode.IN_FRAMESET);
                    return TOKEN_HANDLED;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in body: " + token);
                }
            } // break;
            case "address": // fall through
            case "article": // fall through
            case "aside": // fall through
            case "blockquote": // fall through
            case "center": // fall through
            case "details": // fall through
            case "dialog": // fall through
            case "dir": // fall through
            case "div": // fall through
            case "dl": // fall through
            case "fieldset": // fall through
            case "figcaption": // fall through
            case "figure": // fall through
            case "footer": // fall through
            case "header": // fall through
            case "hgroup": // fall through
            case "main": // fall through
            case "menu": // fall through
            case "nav": // fall through
            case "ol": // fall through
            case "p": // fall through
            case "section": // fall through
            case "summary": // fall through
            case "ul": {
                if (hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                insertHTMLElement(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "h1": // fall through
            case "h2": // fall through
            case "h3": // fall through
            case "h4": // fall through
            case "h5": // fall through
            case "h6": {
                if (hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                // if current node is h1, h2, h3, h4, h5, h6 element, parse
// error
                if (isElementA(getCurrentNode(), "h1", "h2", "h3", "h4", "h5", "h6")) {
                    if (isAllowParseErrors()) {
                        popCurrentNode();
                        break;
                    } else {
                        throw new ParseErrorException("Found " + startTagToken.getTagName() + " nested inside "
                                + getCurrentNode().getTagName());
                    }
                }
                insertHTMLElement(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "pre": // fall through
            case "listing": {
                if (hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                insertHTMLElement(startTagToken);
                // peek next token, if it is LINE_FEED character, ignore it
                setFramesetOKFlag(NOT_OK);
                return TOKEN_HANDLED;
            } // break;
            case "form": {
                if (getFormElementPointer() != null && !isStackOfOpenElementsContains("template")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException();
                    }
                }
                if (hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                final Element formElement = insertHTMLElement(startTagToken);
                if ( !isStackOfOpenElementsContains("template")) {
                    setFormElementPointer(formElement);
                }
                return TOKEN_HANDLED;
            } // break;
            case "li": {
                setFramesetOKFlag(NOT_OK);
                int index = getStackOfOpenElementsSize() - 1;
                while (index >= 0) {
                    final Element node = getOpenElement(index);
                    if (isElementA(node, "li")) {
                        generateImpliedEndTagsExcept("li");
                        if ( !isElementA(getCurrentNode(), "li")) {
                            if (isAllowParseErrors()) {
                                // do nothing?
                            } else {
                                throw new ParseErrorException(
                                        "Unclosed element inside li element: " + getCurrentNode().getTagName());
                            }
                        }
                        Element popped = popCurrentNode();
                        do {
                            popped = popCurrentNode();
                        } while ( !isElementA(popped, "li"));
                        break;
                    } else if (isSpecialCategoryExcept(node.getTagName(), "address", "div", "p")) {
                        break;
                    } else {
                        // loop
                        index-- ;
                    }
                }
                if (hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                insertHTMLElement(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "dd": // fall through
            case "dt": {
                setFramesetOKFlag(NOT_OK);
                int index = getStackOfOpenElementsSize() - 1;
                while (index >= 0) {
                    final Element node = getOpenElement(index);
                    if (isElementA(node, "dd")) {
                        generateImpliedEndTagsExcept("dd");
                        if ( !isElementA(getCurrentNode(), "dd")) {
                            if (isAllowParseErrors()) {
                                // do nothing?
                            } else {
                                throw new ParseErrorException(
                                        "Unclosed element inside dd element: " + getCurrentNode().getTagName());
                            }
                        }
                        Element popped = popCurrentNode();
                        do {
                            popped = popCurrentNode();
                        } while ( !isElementA(popped, "dd"));
                        break;
                    } else if (isElementA(node, "dt")) {
                        generateImpliedEndTagsExcept("dt");
                        if ( !isElementA(getCurrentNode(), "dt")) {
                            if (isAllowParseErrors()) {
                                // do nothing?
                            } else {
                                throw new ParseErrorException(
                                        "Unclosed element inside dt element: " + getCurrentNode().getTagName());
                            }
                        }
                        Element popped = popCurrentNode();
                        do {
                            popped = popCurrentNode();
                        } while ( !isElementA(popped, "dt"));
                        break;
                    } else if (isSpecialCategoryExcept(node.getTagName(), "address", "div", "p")) {
                        break;
                    } else {
                        // loop
                        index-- ;
                    }
                }
                if (hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                insertHTMLElement(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "plaintext": {
                if (hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                insertHTMLElement(startTagToken);
                setTokenizerState(Tokenizer.State.PLAINTEXT);
                return TOKEN_HANDLED;
                /*
                 * There is no way to switch out of the plaintext state.
                 */
            } // break;
            case "button": {
                // if stack of open elements has a button, parse error
                if (hasParticularElementInScope("button")) {
                    if (isAllowParseErrors()) {
                        generateImpliedEndTags();
                        Element popped;
                        do {
                            popped = popCurrentNode();
                        } while ( !isElementA(popped, "button"));
                    } else {
                        throw new ParseErrorException("Attempt to nest button element inside another button element.");
                    }
                }
                reconstructActiveFormattingElements();
                insertHTMLElement(startTagToken);
                setFramesetOKFlag(NOT_OK);
                return TOKEN_HANDLED;
            } // break;
            case "a": {
                // if list of active formatting elements contains "a" after last
// marker
                if (isListOfActiveFormattingElementsContainsAfterLastMarker("a")) {
                    if (isAllowParseErrors()) {
                        adoptionAgencyAlgorithm("a");
                        // remove the anchor element from the list of active
// formatting elements
                        // remove the anchor element from the stack of open
// elements, if it is still there
                    } else {
                        throw new ParseErrorException();
                    }
                }
                reconstructActiveFormattingElements();
                final Element anchorElement = insertHTMLElement(startTagToken);
                pushOntoListOfActiveFormattingElements(startTagToken, anchorElement);
                return TOKEN_HANDLED;
            } // break;
            case "b": // fall through
            case "big": // fall through
            case "code": // fall through
            case "em": // fall through
            case "font": // fall through
            case "i": // fall through
            case "s": // fall through
            case "small": // fall through
            case "strike": // fall through
            case "strong": // fall through
            case "tt": // fall through
            case "u": {
                reconstructActiveFormattingElements();
                final Element formattingElement = insertHTMLElement(startTagToken);
                pushOntoListOfActiveFormattingElements(startTagToken, formattingElement);
                return TOKEN_HANDLED;
            } // break;
            case "nobr": {
                reconstructActiveFormattingElements();
                if (hasParticularElementInScope("nobr")) {
                    if (isAllowParseErrors()) {
                        adoptionAgencyAlgorithm("nobr");
                        reconstructActiveFormattingElements();
                    } else {
                        throw new ParseErrorException("Attempt to nest nobr elements in body.");
                    }
                }
                final Element nobrElement = insertHTMLElement(startTagToken);
                pushOntoListOfActiveFormattingElements(startTagToken, nobrElement);
                return TOKEN_HANDLED;
            } // break;
            case "applet": // fall through
            case "marquee": // fall through
            case "object": {
                reconstructActiveFormattingElements();
                insertHTMLElement(startTagToken);
                insertMarkerAtEndOfListOfActiveFormattingElements();
                setFramesetOKFlag(NOT_OK);
                return TOKEN_HANDLED;
            } // break;
            case "table": {
                if ( !isQuirksMode() && hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                insertHTMLElement(startTagToken);
                setFramesetOKFlag(NOT_OK);
                setInsertionMode(Parser.Mode.IN_TABLE);
                return TOKEN_HANDLED;
            } // break;
            case "area": // fall through
            case "br": // fall through
            case "embed": // fall through
            case "img": // fall through
            case "keygen": // fall through
            case "wbr": {
                reconstructActiveFormattingElements();
                insertHTMLElement(startTagToken);
                popCurrentNode();
                acknowledgeTokenSelfClosingFlag(startTagToken);
                setFramesetOKFlag(NOT_OK);
                return TOKEN_HANDLED;
            } // break;
            case "input": {
                reconstructActiveFormattingElements();
                insertHTMLElement(startTagToken);
                popCurrentNode();
                acknowledgeTokenSelfClosingFlag(startTagToken);
                final TagToken.Attribute typeAttribute = getAttributeNamed(startTagToken, "type");
                if (typeAttribute == null || !typeAttribute.getValue().equalsIgnoreCase("hidden")) {
                    setFramesetOKFlag(NOT_OK);
                }
                return TOKEN_HANDLED;
            } // break;
            case "menuitem": // fall through
            case "param": // fall through
            case "source": // fall through
            case "track": {
                insertHTMLElement(startTagToken);
                popCurrentNode();
                acknowledgeTokenSelfClosingFlag(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "hr": {
                if (hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                insertHTMLElement(startTagToken);
                popCurrentNode();
                acknowledgeTokenSelfClosingFlag(startTagToken);
                setFramesetOKFlag(NOT_OK);
                return TOKEN_HANDLED;
            } // break;
            case "image": {
                throw new ParseErrorException("Unrecognized start tag token (did you mean \"img\"?): " + startTagToken);
            } // break;
            case "isindex": {
                if (isAllowParseErrors()) {
                    if ( !isStackOfOpenElementsContains("template") && getFormElementPointer() != null) {
                        return IGNORE_TOKEN;
                    }
                    acknowledgeTokenSelfClosingFlag(startTagToken);
                    setFramesetOKFlag(NOT_OK);
                    if (hasParticularElementInButtonScope("p")) {
                        closePElement();
                    }
                    final Element formElement = insertHTMLElement("form");
                    if ( !isStackOfOpenElementsContains("template")) {
                        setFormElementPointer(formElement);
                    }
                    final TagToken.Attribute actionAttribute = getAttributeNamed(startTagToken, "action");
                    if (actionAttribute != null) {
                        formElement.setAttribute("action", actionAttribute.getValue());
                    }
                    insertHTMLElement("hr");
                    popCurrentNode();
                    reconstructActiveFormattingElements();
                    insertHTMLElement("label");
                    // TODO: localize string below
                    for (final char ch : "This is a searchable index. Enter search keywords: ".toCharArray()) {
                        insertCharacter(ch);
                    }
                    final Element inputElement = insertHTMLElement("input");
                    for (final TagToken.Attribute attr : startTagToken.getAttributes()) {
                        final String name = attr.getName();
                        if ( !name.equals("name") && !name.equals("action") && !name.equals("prompt")) {
                            inputElement.setAttribute(name, attr.getValue());
                        }
                    }
                    inputElement.setAttribute("name", "isindex");
                    popCurrentNode();
                    // TODO: localize string above
                    popCurrentNode();
                    insertHTMLElement("hr");
                    popCurrentNode();
                    popCurrentNode();
                    if ( !isStackOfOpenElementsContains("template")) {
                        setFormElementPointer(null);
                    }
                    return TOKEN_HANDLED;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in body: " + startTagToken);
                }
            } // break;
            case "textarea": {
                insertHTMLElement(startTagToken);
                // if next token is LINE_FEED, ignore it
                setTokenizerState(Tokenizer.State.RCDATA);
                setOriginalInsertionMode(getInsertionMode());
                setFramesetOKFlag(NOT_OK);
                setInsertionMode(Parser.Mode.TEXT);
                return TOKEN_HANDLED;
            } // break;
            case "xmp": {
                if (hasParticularElementInButtonScope("p")) {
                    closePElement();
                }
                reconstructActiveFormattingElements();
                setFramesetOKFlag(NOT_OK);
                genericRawTextElementParsingAlgorithm(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "iframe": {
                setFramesetOKFlag(NOT_OK);
                genericRawTextElementParsingAlgorithm(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "noembed": {
                genericRawTextElementParsingAlgorithm(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "noscript": {
                if (isScriptingEnabled()) {
                    genericRawTextElementParsingAlgorithm(startTagToken);
                    return TOKEN_HANDLED;
                } else {
                    return anyOtherStartTag(startTagToken);
                }
            } // break;
            case "select": {
                reconstructActiveFormattingElements();
                insertHTMLElement(startTagToken);
                setFramesetOKFlag(NOT_OK);
                switch (getInsertionMode()) {
                case IN_TABLE: // fall through
                case IN_CAPTION: // fall through
                case IN_TABLE_BODY: // fall through
                case IN_ROW: // fall through
                case IN_CELL: {
                    setInsertionMode(Parser.Mode.IN_SELECT_IN_TABLE);
                }
                    break;
                default: {
                    setInsertionMode(Parser.Mode.IN_SELECT);
                }
                    break;
                }
                return TOKEN_HANDLED;
            } // break;
            case "optgroup": // fall through
            case "option": {
                if (isElementA(getCurrentNode(), "option")) {
                    popCurrentNode();
                }
                return anyOtherStartTag(startTagToken);
            } // break;
            case "rp": // fall through
            case "rt": {
                if (hasParticularElementInScope("ruby")) {
                    generateImpliedEndTags();
                }
                if (isAllowParseErrors() && !isElementA(getCurrentNode(), "ruby")) {
                    throw new ParseErrorException();
                }
                insertHTMLElement(startTagToken);
                return TOKEN_HANDLED;
            } // break;
            case "math": {
                reconstructActiveFormattingElements();
                adjustMathMLAttributes(startTagToken);
                adjustForeignAttributes(startTagToken);
                insertForeignElement(startTagToken, MATHML_NAMESPACE);
                if (startTagToken.isSelfClosing()) {
                    popCurrentNode();
                    acknowledgeTokenSelfClosingFlag(startTagToken);
                }
                return TOKEN_HANDLED;
            } // break;
            case "svg": {
                reconstructActiveFormattingElements();
                adjustSVGAttributes(startTagToken);
                adjustForeignAttributes(startTagToken);
                insertForeignElement(startTagToken, SVG_NAMESPACE);
                if (startTagToken.isSelfClosing()) {
                    popCurrentNode();
                    acknowledgeTokenSelfClosingFlag(startTagToken);
                }
                return TOKEN_HANDLED;
            } // break;
            case "caption": // fall through
            case "col": // fall through
            case "colgroup": // fall through
            case "frame": // fall through
            case "head": // fall through
            case "tbody": // fall through
            case "td": // fall through
            case "tfoot": // fall through
            case "th": // fall through
            case "thead": // fall through
            case "tr": {
                if (isAllowParseErrors()) {
                    return IGNORE_TOKEN;
                } else {
                    throw new ParseErrorException("Unexpected start tag token in body: " + startTagToken);
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
            case "template": {
                return processUsingRulesFor(Parser.Mode.IN_HEAD, endTagToken);
            } // break;
            case "body": {
                if ( !hasParticularElementInScope("body")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Body end tag token encountered without body start tag token.");
                    }
                }
                final Set<String> acceptable = new HashSet<>(Arrays.asList("dd", "dt", "li", "optgroup", "option", "p",
                        "rp", "rt", "tbody", "td", "tfoot", "th", "thead", "tr", "body", "html"));
                if (isStackOfOpenElementsContainsOtherThan(acceptable)) {
                    throw new ParseErrorException("Unclosed element at body end.");
                }
//                for (final Element openElement : getStackOfOpenElements()) {
//                    if ( !acceptable.contains(openElement.getTagName())) {
//                        throw new ParseErrorException("Unclosed element at body end: " +
//                                openElement.getTagName());
//                    }
//                }
                setInsertionMode(Parser.Mode.AFTER_BODY);
                return TOKEN_HANDLED;
            } // break;
            case "html": {
                if ( !hasParticularElementInScope("body")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Body end tag token encountered without body start tag token.");
                    }
                }
                final Set<String> acceptable = new HashSet<>(Arrays.asList("dd", "dt", "li", "optgroup", "option", "p",
                        "rp", "rt", "tbody", "td", "tfoot", "th", "thead", "tr", "body", "html"));
                if (isStackOfOpenElementsContainsOtherThan(acceptable)) {
                    throw new ParseErrorException("Unclosed element at body end.");
                }
//                for (final Element openElement : getStackOfOpenElements()) {
//                    if ( !acceptable.contains(openElement.getTagName())) {
//                        throw new ParseErrorException("Unclosed element at html end: " +
//                                openElement.getTagName());
//                    }
//                }
                setInsertionMode(Parser.Mode.AFTER_BODY);
                return REPROCESS_TOKEN;
            } // break;
            case "address": // fall through
            case "article": // fall through
            case "aside": // fall through
            case "blockquote": // fall through
            case "button": // fall through
            case "center": // fall through
            case "details": // fall through
            case "dialog": // fall through
            case "dir": // fall through
            case "div": // fall through
            case "dl": // fall through
            case "fieldset": // fall through
            case "figcaption": // fall through
            case "figure": // fall through
            case "footer": // fall through
            case "header": // fall through
            case "hgroup": // fall through
            case "listing": // fall through
            case "main": // fall through
            case "menu": // fall through
            case "nav": // fall through
            case "ol": // fall through
            case "pre": // fall through
            case "section": // fall through
            case "summary": // fall through
            case "ul": {
                if ( !hasParticularElementInScope(tagName)) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("End tag token without matching open element: " + endTagToken);
                    }
                }
                generateImpliedEndTags();
                if ( !isElementA(getCurrentNode(), tagName)) {
                    if (isAllowParseErrors()) {
                        // do nothing?
                    } else {
                        throw new ParseErrorException("End tag token does not match current open element ("
                                + getCurrentNode().getTagName() + "): " + endTagToken);
                    }
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, tagName));
                return TOKEN_HANDLED;
            } // break;
            case "form": {
                // do wacky shit
                if ( !isStackOfOpenElementsContains("template")) {
                    final Element node = getFormElementPointer();
                    setFormElementPointer(null);
                    if (node == null || !hasParticularElementInScope(node.getTagName())) {
                        if (isAllowParseErrors()) {
                            return IGNORE_TOKEN;
                        } else {
                            throw new ParseErrorException("uhhhh");
                        }
                    }
                    generateImpliedEndTags();
                    if (node != getCurrentNode()) {
                        throw new ParseErrorException();
                    }
                    removeNodeFromStackOfOpenElements(node);
                } else {
                    if ( !hasParticularElementInScope("form")) {
                        if (isAllowParseErrors()) {
                            return IGNORE_TOKEN;
                        } else {
                            throw new ParseErrorException();
                        }
                    }
                    generateImpliedEndTags();
                    if ( !isElementA(getCurrentNode(), "form")) {
                        if (isAllowParseErrors()) {
                            // do nothing?
                        } else {
                            throw new ParseErrorException();
                        }
                    }
                    Element popped;
                    do {
                        popped = popCurrentNode();
                    } while ( !isElementA(popped, "form"));
                }
                return TOKEN_HANDLED;
            } // break;
            case "p": {
                if ( !hasParticularElementInButtonScope("p")) {
                    if (isAllowParseErrors()) {
                        insertHTMLElement("p");
                    } else {
                        throw new ParseErrorException(
                                "End tag token for p element with no matching p element in scope.");
                    }
                }
                closePElement();
                return TOKEN_HANDLED;
            } // break;
            case "li": {
                if ( !hasParticularElementInListItemScope("li")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("End tag token for li with no matching li element in scope.");
                    }
                }
                generateImpliedEndTagsExcept("li");
                if ( !isElementA(getCurrentNode(), "li")) {
                    if (isAllowParseErrors()) {
                        // do nothing?
                    } else {
                        throw new ParseErrorException("End tag token for li element when current element is: "
                                + getCurrentNode().getTagName());
                    }
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, "li"));
                return TOKEN_HANDLED;
            } // break;
            case "dd": // fall through
            case "dt": {
                if ( !hasParticularElementInListItemScope(tagName)) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException(
                                "End tag token for " + tagName + " with no matching " + tagName + " element in scope.");
                    }
                }
                generateImpliedEndTagsExcept(tagName);
                if ( !isElementA(getCurrentNode(), tagName)) {
                    if (isAllowParseErrors()) {
                        // do nothing?
                    } else {
                        throw new ParseErrorException("End tag token for " + tagName
                                + " element when current element is: " + getCurrentNode().getTagName());
                    }
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, tagName));
                return TOKEN_HANDLED;
            } // break;
            case "h1": // fall through
            case "h2": // fall through
            case "h3": // fall through
            case "h4": // fall through
            case "h5": // fall through
            case "h6": {
                if ( !hasParticularElementInScope("h1") && !hasParticularElementInScope("h2")
                        && !hasParticularElementInScope("h3") && !hasParticularElementInScope("h4")
                        && !hasParticularElementInScope("h5") && !hasParticularElementInScope("h6")) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException(
                                "End tag token for " + tagName + " element with no heading element in scope.");
                    }
                }
                generateImpliedEndTags();
                if ( !isElementA(getCurrentNode(), tagName)) {
                    if (isAllowParseErrors()) {
                        // do nothing?
                    } else {
                        throw new ParseErrorException("End tag token for " + tagName
                                + " element when current element is: " + getCurrentNode().getTagName());
                    }
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, "h1", "h2", "h3", "h4", "h5", "h6"));
                return TOKEN_HANDLED;
            } // break;
            case "a": // fall through
            case "b": // fall through
            case "big": // fall through
            case "code": // fall through
            case "em": // fall through
            case "font": // fall through
            case "i": // fall through
            case "nobr": // fall through
            case "s": // fall through
            case "small": // fall through
            case "strike": // fall through
            case "strong": // fall through
            case "tt": // fall through
            case "u": {
                adoptionAgencyAlgorithm(tagName);
                return TOKEN_HANDLED;
            } // break;
            case "applet": // fall through
            case "marquee": // fall through
            case "object": {
                if ( !isStackOfOpenElementsContains(tagName)) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException("Unexpected end tag token in body: " + endTagToken);
                    }
                }
                generateImpliedEndTags();
                if ( !isElementA(getCurrentNode(), tagName)) {
                    if (isAllowParseErrors()) {
                        // do nothing?
                    } else {
                        throw new ParseErrorException();
                    }
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while ( !isElementA(popped, tagName));
                clearListOfActiveFormattingElementsUpToLastMarker();
                return TOKEN_HANDLED;
            } // break;
            case "br": {
                if (isAllowParseErrors()) {
                    reconstructActiveFormattingElements();
                    insertHTMLElement("br");
                    popCurrentNode();
                    setFramesetOKFlag(NOT_OK);
                    return TOKEN_HANDLED;
                } else {
                    throw new ParseErrorException();
                }
            } // break;
            default: {
                return anyOtherEndTag(tagName);
            } // break;
            }
        } // break;
        case EOF: {
            // handle stack of open elements
            final Set<String> acceptable = new HashSet<>(
                    Arrays.asList("dd", "dt", "li", "p", "tbody", "td", "tfoot", "th", "thead", "tr", "body", "html"));
            if (isStackOfOpenElementsContainsOtherThan(acceptable)) {
                if (isAllowParseErrors()) {
                    // do nothing?
                } else {
                    throw new ParseErrorException("Unclosed element at end-of-file.");
                }
            }
//            for (final Element openElement : getStackOfOpenElements()) {
//                if ( !acceptable.contains(openElement.getTagName())) {
//                    if (isAllowParseErrors()) {
//                        // do nothing?
//                    } else {
//                        throw new ParseErrorException("Unclosed element at end-of-file: " +
//                                openElement.getTagName());
//                    }
//                }
//            }
            if ( !isStackOfTemplateInsertionModesEmpty()) {
                return processUsingRulesFor(Parser.Mode.IN_TEMPLATE, token);
            }
            stopParsing();
            return TOKEN_HANDLED;
        } // break;
        default: {
            throw new ParseErrorException();
        } // break;
        }
    }
    
    private boolean anyOtherStartTag(final StartTagToken startTagToken) {
        reconstructActiveFormattingElements();
        insertHTMLElement(startTagToken);
        return TOKEN_HANDLED;
    }
    
    private boolean anyOtherEndTag(final String tagName) {
        int index = getStackOfOpenElementsSize() - 1;
        while (index >= 0) {
            final Element node = getOpenElement(index);
            if (isElementA(node, tagName)) {
                generateImpliedEndTagsExcept(tagName);
                if (node != getCurrentNode() && !isAllowParseErrors()) {
                    throw new ParseErrorException();
                }
                Element popped;
                do {
                    popped = popCurrentNode();
                } while (popped != node);
                return TOKEN_HANDLED;
            } else {
                if (isSpecialCategory(node)) {
                    if (isAllowParseErrors()) {
                        return IGNORE_TOKEN;
                    } else {
                        throw new ParseErrorException();
                    }
                } else {
                    // loop
                    index-- ;
                }
            }
        }
        return TOKEN_HANDLED;
    }
    
    /**
     * @param subject
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#adoption-agency-algorithm">
     *      adoption agency algorithm</a>
     */
    protected void adoptionAgencyAlgorithm(final String subject) {
        // 1
        final Element currentNode = getCurrentNode();
        if (isElementA(currentNode, subject) && !listOfActiveFormattingElementsContains(currentNode)) {
            popCurrentNode();
            return;
        }
        // 2
//        int outerLoopCounter = 0;
        // 3
        // OUTER LOOP:
        for (int outerLoopCounter = 0; outerLoopCounter < 8; outerLoopCounter++ ) {
//            if (outerLoopCounter >= 8) {
//                return;
//            }
            // 4
//            outerLoopCounter++;
            // 5
            int formattingElementIndexInList = parser.listOfActiveFormattingElements.size() - 1;
            StartTagToken formattingElementToken = null;
            Element formattingElement = null;
            while (formattingElementIndexInList >= 0) {
                final FormattingElement temp = parser.listOfActiveFormattingElements.get(formattingElementIndexInList);
                if (parser.isMarker(temp)) {
                    break;
                }
                final StartTagToken tok = temp.getKey();
                final Element el = temp.getValue();
                if (isElementA(el, subject)) {
                    formattingElementToken = tok;
                    formattingElement = el;
                    break;
                }
                formattingElementIndexInList-- ;
            }
            if (formattingElement == null) {
                anyOtherEndTag(subject);
                return;
            }
            // 6
            final int formattingElementIndexInStack = parser.getIndexOfOpenElement(formattingElement);
            if (formattingElementIndexInStack == -1) {
                if (isAllowParseErrors()) {
                    assert parser.listOfActiveFormattingElements.get(formattingElementIndexInList) == formattingElement;
                    parser.listOfActiveFormattingElements.remove(formattingElementIndexInList);
                    return;
                } else {
                    throw new ParseErrorException();
                }
            }
            // 7
            assert parser.containsOpenElement(formattingElement);
            if ( !hasParticularElementInScope(subject)) {
                if (isAllowParseErrors()) {
                    return;
                } else {
                    throw new ParseErrorException();
                }
            }
            // 8
            if (formattingElement != getCurrentNode() && !isAllowParseErrors()) {
                throw new ParseErrorException();
            }
            // 9
            Element furthestBlock = null;
            int furthestBlockIndex = formattingElementIndexInStack + 1;
            while (furthestBlockIndex < parser.getNumOpenElements()) {
                final Element temp = parser.getOpenElement(furthestBlockIndex);
                if (isSpecialCategory(temp)) {
                    furthestBlock = temp;
                    break;
                }
                furthestBlockIndex++ ;
            }
            // 10
            if (furthestBlock == null) {
                Element popped;
                do {
                    popped = popCurrentNode();
                } while (popped != formattingElement);
                parser.listOfActiveFormattingElements.remove(formattingElementIndexInList);
                return;
            }
            // 11
            final int commonAncestorIndex = formattingElementIndexInStack - 1;
            final Element commonAncestor = parser.getOpenElement(commonAncestorIndex);
            // 12
            int bookmark = indexOfInActiveList(formattingElement);
            FormattingElement beforeBookmark;
            if (bookmark > 0) {
                beforeBookmark = parser.listOfActiveFormattingElements.get(bookmark - 1);
            } else {
                beforeBookmark = null;
            }
            FormattingElement afterBookmark;
            if (bookmark + 1 < parser.listOfActiveFormattingElements.size()) {
                afterBookmark = parser.listOfActiveFormattingElements.get(bookmark + 1);
            } else {
                afterBookmark = null;
            }
            // 13
            int nodeIndex = furthestBlockIndex;
            int lastNodeIndex = furthestBlockIndex;
            Element lastNode = parser.getOpenElement(lastNodeIndex);
            // 13.1
            int innerLoopCounter = 0;
            // 13.2
            // INNER LOOP:
            while (true) {
                innerLoopCounter++ ;
                // 13.3
                nodeIndex-- ;
                Element node = parser.getOpenElement(nodeIndex);
                // 13.4
                if (node == formattingElement) {
                    // go to 14:
                    break;
                }
                // 13.5
                if (innerLoopCounter > 3 && listOfActiveFormattingElementsContains(node)) {
                    removeFromListOfActiveFormattingElements(node);
                }
                // 13.6
                if ( !listOfActiveFormattingElementsContains(node)) {
                    parser.removeOpenElement(node);
                    // go to INNER LOOP:
                    continue;
                }
                // 13.7
                int indexInFormattingList = indexOfInActiveList(node);
                final FormattingElement wrapped = parser.listOfActiveFormattingElements.get(indexInFormattingList);
                final StartTagToken nodeToken = wrapped.getKey();
                final Element newElement = createElementForToken(nodeToken, HTML_NAMESPACE, commonAncestor);
                final FormattingElement wrapper = new FormattingElement(nodeToken, newElement);
                indexInFormattingList = indexOfInActiveList(node);
                parser.listOfActiveFormattingElements.set(indexInFormattingList, wrapper);
                parser.setOpenElement(nodeIndex, newElement);
                node = newElement;
                // 13.8
                if (lastNode == furthestBlock) {
                    // TODO: bookmark moved after node in
// parser.listOfActiveFormattingElements
                    bookmark = indexInFormattingList;
                    if (bookmark > 0) {
                        beforeBookmark = parser.listOfActiveFormattingElements.get(bookmark - 1);
                    } else {
                        beforeBookmark = null;
                    }
                    if (bookmark + 1 < parser.listOfActiveFormattingElements.size()) {
                        afterBookmark = parser.listOfActiveFormattingElements.get(bookmark + 1);
                    } else {
                        afterBookmark = null;
                    }
                }
                // 13.9
                node.appendChild(lastNode);
                // 13.10
                lastNodeIndex = nodeIndex;
                lastNode = node;
                // 13.11
                // go to INNER LOOP:
            }
            // 14
            getAppropriatePlaceForInsertingNode(commonAncestor).insert(lastNode);
            // 15
            final Element thatNewElement = createElementForToken(formattingElementToken, HTML_NAMESPACE, furthestBlock);
            // 16
            final NodeList childrenToMove = furthestBlock.getChildNodes();
            for (int i = 0; i < childrenToMove.getLength(); i++ ) {
                thatNewElement.appendChild(childrenToMove.item(i));
            }
            // 17
            furthestBlock.appendChild(thatNewElement);
            // 18
            removeFromListOfActiveFormattingElements(formattingElement);
            // insert thatNewElement at bookmark
            final FormattingElement thatNewWrapper = new FormattingElement(formattingElementToken, thatNewElement);
            if (beforeBookmark != null) {
                final int indexOfBefore = parser.listOfActiveFormattingElements.indexOf(beforeBookmark);
                parser.listOfActiveFormattingElements.add(indexOfBefore + 1, thatNewWrapper);
            } else {
                final int indexOfAfter = parser.listOfActiveFormattingElements.indexOf(afterBookmark);
                parser.listOfActiveFormattingElements.add(indexOfAfter, thatNewWrapper);
            }
            // 19
            parser.removeOpenElement(formattingElement);
            furthestBlockIndex = parser.getIndexOfOpenElement(furthestBlock);
            parser.insertOpenElement(furthestBlockIndex, thatNewElement);
            // 20
            // go to OUTER LOOP:
        }
    }
    
    private int indexOfInActiveList(final Element formattingElement) {
        for (int i = 0; i < parser.listOfActiveFormattingElements.size(); i++ ) {
            final FormattingElement wrapper = parser.listOfActiveFormattingElements.get(i);
            if (parser.isMarker(wrapper)) {
                continue;
            }
            final Element element = wrapper.getValue();
            if (element == formattingElement) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean listOfActiveFormattingElementsContains(final Element formattingElement) {
        for (final FormattingElement wrapper : parser.listOfActiveFormattingElements) {
            if (parser.isMarker(wrapper)) {
                continue;
            }
            final Element element = wrapper.getValue();
            if (element == formattingElement) {
                return true;
            }
        }
        return false;
    }
    
    private void removeFromListOfActiveFormattingElements(final Element formattingElement) {
        final Iterator<FormattingElement> iterator = parser.listOfActiveFormattingElements.iterator();
        while (iterator.hasNext()) {
            final FormattingElement wrapper = iterator.next();
            if (parser.isMarker(wrapper)) {
                continue;
            }
            final Element element = wrapper.getValue();
            if (formattingElement == element) {
                iterator.remove();
//                return;
            }
        }
    }
    
    /**
     * @param target
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#close-a-p-element">
     *      close a p element</a>
     */
    private void closePElement() {
        generateImpliedEndTagsExcept("p");
        if ( !getCurrentNode().getTagName().equals("p")) {
            if (isAllowParseErrors()) {
                // do nothing?
            } else {
                throw new ParseErrorException("Expected current node to be a p element when closing element, was: "
                        + getCurrentNode().getTagName());
            }
        }
        Element popped;
        do {
            popped = popCurrentNode();
        } while ( !isElementA(popped, "p"));
    }
    
}
