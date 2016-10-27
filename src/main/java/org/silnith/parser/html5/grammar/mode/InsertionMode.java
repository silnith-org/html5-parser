package org.silnith.parser.html5.grammar.mode;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.Parser.FormattingElement;
import org.silnith.parser.html5.Parser.Mode;
import org.silnith.parser.html5.grammar.dom.AfterLastChildInsertionPosition;
import org.silnith.parser.html5.grammar.dom.InsertionPosition;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.CommentToken;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;


/**
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insertion-mode">insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public abstract class InsertionMode {
    
    /**
     * The HTML namespace.
     * 
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/infrastructure.html#html-namespace-0">HTML namespace</a>
     */
    public static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/infrastructure.html#mathml-namespace">
     *      MathML namespace</a>
     */
    public static final String MATHML_NAMESPACE = "http://www.w3.org/1998/Math/MathML";
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/infrastructure.html#svg-namespace">SVG
     *      namespace</a>
     */
    public static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/infrastructure.html#xlink-namespace">
     *      XLink namespace</a>
     */
    public static final String XLINK_NAMESPACE = "http://www.w3.org/1999/xlink";
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/infrastructure.html#xml-namespace">XMLNS
     *      namespace</a>
     */
    public static final String XML_NAMESPACE = "http://www.w3.org/XML/1998/namespace";
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/infrastructure.html#xmlns-namespace">
     *      XMLNS namespace</a>
     */
    public static final String XMLNS_NAMESPACE = "http://www.w3.org/2000/xmlns/";
    
    /**
     * A constant for insertion modes to return when the token has been ignored.
     */
    protected static final boolean IGNORE_TOKEN = true;
    
    /**
     * A constant for insertion modes to return when the token has been handled.
     */
    protected static final boolean TOKEN_HANDLED = true;
    
    /**
     * A constant for insertion modes to return when the token needs to be
     * reprocessed by the parser.
     */
    protected static final boolean REPROCESS_TOKEN = false;
    
    /**
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#frameset-ok-flag">
     *      frameset-ok flag</a>
     */
    protected static final boolean NOT_OK = false;
    
    private static final Set<String> SPECIFIC_SCOPE;
    
    private static final Set<String> BUTTON_SCOPE;
    
    private static final Set<String> LIST_ITEM_SCOPE;
    
    private static final Set<String> TABLE_SCOPE;
    
    private static final Set<String> IMPLIED_END_TAGS;
    
    private static final Set<String> SPECIAL_ELEMENTS;
    
    private static final Set<String> FORMATTING_ELEMENTS;
    
    static {
        SPECIFIC_SCOPE = Collections.unmodifiableSet(new HashSet<>(
                Arrays.asList("applet", "caption", "html", "table", "td", "th", "marquee", "object", "template")));
        final Set<String> buttonScope = new HashSet<>(SPECIFIC_SCOPE);
        buttonScope.add("button");
        BUTTON_SCOPE = Collections.unmodifiableSet(buttonScope);
        final Set<String> listItemScope = new HashSet<>(SPECIFIC_SCOPE);
        listItemScope.add("ol");
        listItemScope.add("ul");
        LIST_ITEM_SCOPE = Collections.unmodifiableSet(listItemScope);
        TABLE_SCOPE = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("html", "table", "template")));
        IMPLIED_END_TAGS = Collections.unmodifiableSet(
                new HashSet<>(Arrays.asList("dd", "dt", "li", "option", "optgroup", "p", "rp", "rt")));
        SPECIAL_ELEMENTS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("address", "applet", "area",
                "article", "aside", "base", "basefont", "bgsound", "blockquote", "body", "br", "button", "caption",
                "center", "col", "colgroup", "dd", "details", "dir", "div", "dl", "dt", "embed", "fieldset",
                "figcaption", "figure", "footer", "form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6",
                "head", "header", "hgroup", "hr", "html", "iframe", "img", "input", "isindex", "li", "link", "listing",
                "main", "marquee", "menu", "menuitem", "meta", "nav", "noembed", "noframes", "noscript", "object", "ol",
                "p", "param", "plaintext", "pre", "script", "section", "select", "source", "style", "summary", "table",
                "tbody", "td", "template", "textarea", "tfoot", "th", "thead", "title", "tr", "track", "ul", "wbr",
                "xmp")));
        FORMATTING_ELEMENTS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("a", "b", "big", "code", "em",
                "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u")));
    }
    
    /**
     * All state should be saved in the parser. State needs to be shared across
     * many insertion modes.
     */
    protected final Parser parser;
    
    public InsertionMode(final Parser parser) {
        super();
        this.parser = parser;
    }
    
    /**
     * Insert the given token into the document.
     * 
     * @param token the token to insert into the document
     * @return whether the token was handled. {@code false} means the token has
     *         not been handled and needs to be passed to the next insertion
     *         mode.
     */
    public abstract boolean insert(final Token token);
    
    /**
     * Process the token using the rules for the given insertion mode.
     * <p>
     * Several of these modes, namely "in head", "in body", "in table", and "in select", are special, in that the other modes defer to them at various times. When the algorithm below says that the user agent is to do something "using the rules for the <var>m</var> insertion mode", where <var>m</var> is one of these modes, the user agent must use the rules described under the <var>m</var> insertion mode's section, but must leave the insertion mode unchanged unless the rules in <var>m</var> themselves switch the insertion mode to a new value.
     * 
     * @param mode the mode to use to process the token
     * @param token the token to process
     * @return whether the token was handled. {@code false} means the token
     *         needs to be passed to the next insertion mode.
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#using-the-rules-for">using the rules for</a>
     */
    protected boolean processUsingRulesFor(final Mode mode, final Token token) {
        return parser.processUsingRulesFor(mode, token);
    }
    
    /**
     * Whether to allow parse errors and use error-handling behavior.
     * 
     * @return whether parse errors will be suppressed
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#parse-error">parse
     *      errors</a>
     */
    protected boolean isAllowParseErrors() {
        return parser.isAllowParseErrors();
    }
    
    /**
     * @return whether this is the HTML fragment parsing algorithm
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#html-fragment-parsing-algorithm">
     *      HTML fragment parsing algorithm</a>
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#fragment-case">
     *      fragment case</a>
     */
    protected boolean isHTMLFragmentParsingAlgorithm() {
        return parser.isHTMLFragmentParsingAlgorithm();
    }
    
    /**
     * Whether scripting is enabled for this parser.
     * <p>
     * The scripting flag is set to "enabled" if scripting was enabled for the Document with which the parser is associated when the parser was created, and "disabled" otherwise.
     * 
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#other-parsing-state-flags">8.2.3.5 Other parsing state flags</a>
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#scripting-flag">scripting flag</a>
     */
    protected boolean isScriptingEnabled() {
        return parser.isScriptingEnabled();
    }
    
    /**
     * @return whether this is quirks mode
     * @see <a href=
     *      "http://www.w3.org/TR/html5/infrastructure.html#quirks-mode">quirks
     *      mode</a>
     */
    protected boolean isQuirksMode() {
        return parser.isQuirksMode();
    }
    
    /**
     * The document currently being constructed.
     * 
     * @return the current document
     */
    protected Document getDocument() {
        return parser.getDocument();
    }
    
    /**
     * @return whether foster parenting is enabled
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#foster-parent">
     *      foster parenting</a>
     */
    protected boolean isFosterParentingEnabled() {
        return parser.isFosterParentingEnabled();
    }
    
    /**
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#foster-parent">
     *      foster parenting</a>
     */
    protected void enableFosterParenting() {
        parser.setFosterParentingEnabled(true);
    }
    
    /**
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#foster-parent">
     *      foster parenting</a>
     */
    protected void disableFosterParenting() {
        parser.setFosterParentingEnabled(false);
    }
    
    /**
     * @return
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#frameset-ok-flag">
     *      frameset-ok flag</a>
     */
    protected boolean isFramesetOkFlag() {
        return parser.isFramesetOkFlag();
    }
    
    /**
     * @param isOK
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#frameset-ok-flag">
     *      frameset-ok flag</a>
     */
    protected void setFramesetOKFlag(final boolean isOK) {
        parser.setFramesetOKFlag(isOK);
    }
    
    /**
     * Returns the head element pointer.
     * <p>
     * Once a head element has been parsed (whether implicitly or explicitly) the head element pointer gets set to point to this node.
     * 
     * @return the current head element, if any
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#head-element-pointer"><code>head</code> element pointer</a>
     */
    protected Element getHeadElementPointer() {
        return parser.getHeadElementPointer();
    }
    
    /**
     * Sets the head element pointer.
     * <p>
     * Once a head element has been parsed (whether implicitly or explicitly) the head element pointer gets set to point to this node.
     * 
     * @param element the current head element, if any
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#head-element-pointer"><code>head</code> element pointer</a>
     */
    protected void setHeadElementPointer(final Element element) {
        parser.setHeadElementPointer(element);
    }
    
    /**
     * Returns the form element pointer.
     * <p>
     * The form element pointer points to the last form element that was opened and whose end tag has not yet been seen. It is used to make form controls associate with forms in the face of dramatically bad markup, for historical reasons. It is ignored inside template elements.
     * 
     * @return the current form element, if any
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#form-element-pointer"><code>form</code> element pointer</a>
     */
    protected Element getFormElementPointer() {
        return parser.getFormElementPointer();
    }
    
    /**
     * Sets the form element pointer.
     * <p>
     * The form element pointer points to the last form element that was opened and whose end tag has not yet been seen. It is used to make form controls associate with forms in the face of dramatically bad markup, for historical reasons. It is ignored inside template elements.
     * 
     * @param formElement the current form element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#form-element-pointer"><code>form</code> element pointer</a>
     */
    protected void setFormElementPointer(final Element formElement) {
        parser.setFormElementPointer(formElement);
    }
    
    /**
     * Returns the current insertion mode.
     * <p>
     * The insertion mode is a state variable that controls the primary operation of the tree construction stage.
     * <p>
     * Initially, the insertion mode is "initial". It can change to "before html", "before head", "in head", "in head noscript", "after head", "in body", "text", "in table", "in table text", "in caption", "in column group", "in table body", "in row", "in cell", "in select", "in select in table", "in template", "after body", "in frameset", "after frameset", "after after body", and "after after frameset" during the course of the parsing, as described in the tree construction stage. The insertion mode affects how tokens are processed and whether CDATA sections are supported.
     * 
     * @return the current insertion mode
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insertion-mode">insertion mode</a>
     */
    protected Mode getInsertionMode() {
        return parser.getInsertionMode();
    }
    
    /**
     * Changes the insertion mode for the current parser.
     * 
     * @param insertionMode the mode to shift into
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insertion-mode">insertion mode</a>
     */
    protected void setInsertionMode(final Mode insertionMode) {
        parser.setInsertionMode(insertionMode);
    }
    
    /**
     * Gets the original insertion mode.
     * 
     * @return the original insertion mode
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#original-insertion-mode">original insertion mode</a>
     */
    protected Mode getOriginalInsertionMode() {
        return parser.getOriginalInsertionMode();
    }
    
    /**
     * Sets the original insertion mode.
     * <p>
     * When the insertion mode is switched to "text" or "in table text", the original insertion mode is also set. This is the insertion mode to which the tree construction stage will return.
     * 
     * @param originalInsertionMode the original insertion mode
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#original-insertion-mode">original insertion mode</a>
     */
    protected void setOriginalInsertionMode(final Mode originalInsertionMode) {
        parser.setOriginalInsertionMode(originalInsertionMode);
    }
    
    /**
     * Reconstructs the active formatting elements.
     * <p>
     * When the steps below require the UA to reconstruct the active formatting elements, the UA must perform the following steps:
     * <ol>
     *   <li>If there are no entries in the list of active formatting elements, then there is nothing to reconstruct; stop this algorithm.
     *   <li>If the last (most recently added) entry in the list of active formatting elements is a marker, or if it is an element that is in the stack of open elements, then there is nothing to reconstruct; stop this algorithm.
     *   <li>Let <var>entry</var> be the last (most recently added) element in the list of active formatting elements.
     *   <li><dfn>Rewind</dfn>: If there are no entries before <var>entry</var> in the list of active formatting elements, then jump to the step labeled <var>create</var>.
     *   <li>Let <var>entry</var> be the entry one earlier than <var>entry</var> in the list of active formatting elements.
     *   <li>If <var>entry</var> is neither a marker nor an element that is also in the stack of open elements, go to the step labeled <var>rewind</var>.
     *   <li><dfn>Advance</dfn>: Let <var>entry</var> be the element one later than <var>entry</var> in the list of active formatting elements.
     *   <li><dfn>Create</dfn>: Insert an HTML element for the token for which the element <var>entry</var> was created, to obtain <var>new element</var>.
     *   <li>Replace the entry for <var>entry</var> in the list with an entry for <var>new element</var>.
     *   <li>If the entry for <var>new element</var> in the list of active formatting elements is not the last entry in the list, return to the step labeled <var>advance</var>.
     * </ol>
     * <p>This has the effect of reopening all the formatting elements that were opened in the current body, cell, or caption (whichever is youngest) that haven't been explicitly closed.
     * 
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#reconstruct-the-active-formatting-elements">reconstruct the active formatting elements</a>
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-list-of-active-formatting-elements">8.2.3.3 The list of active formatting elements</a>
     */
    protected void reconstructActiveFormattingElements() {
        final List<FormattingElement> listOfActiveFormattingElements = parser.listOfActiveFormattingElements;
        if (listOfActiveFormattingElements.isEmpty()) {
            return;
        }
        final FormattingElement mostRecentlyAddedEntry = listOfActiveFormattingElements.get(listOfActiveFormattingElements.size() - 1);
        if (parser.isMarker(mostRecentlyAddedEntry)) {
            return;
        }
        if (parser.containsOpenElement(mostRecentlyAddedEntry.getValue())) {
            return;
        }
        
        final ListIterator<FormattingElement> entryIter =
                listOfActiveFormattingElements.listIterator(listOfActiveFormattingElements.size());
        assert entryIter.hasPrevious();
        FormattingElement entry = null;
        while (entryIter.hasPrevious()) {
            entry = entryIter.previous();
            if (parser.isMarker(entry) || parser.containsOpenElement(entry.getValue())) {
                entry = entryIter.next();
                break;
            }
        }
        // entry is the first element to be re-opened
        do {
            final StartTagToken startTagToken = entry.getKey();
            final Element newElement = insertHTMLElement(startTagToken);
            entryIter.set(new FormattingElement(startTagToken, newElement));
            if ( !entryIter.hasNext()) {
                return;
            }
            entry = entryIter.next();
        } while (true);
    }
    
    /**
     * Resets the insertion mode appropriately based on the stack of open
     * elements.
     * 
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#reset-the-insertion-mode-appropriately">
     *      reset the insertion mode appropriately</a>
     */
    protected void resetInsertionModeAppropriately() {
        for (int index = getStackOfOpenElementsSize() - 1; index >= 0; index-- ) {
            final Element node = getOpenElement(index);
            final boolean last = (index == 0);
            // TODO: fragment -> context
            if (isElementA(node, "select")) {
                if (last) {
                    setInsertionMode(Mode.IN_SELECT);
                    return;
                }
                for (int ancestorIndex = index; ancestorIndex >= 0; ancestorIndex-- ) {
                    final Element ancestor = getOpenElement(ancestorIndex);
                    if (isElementA(ancestor, "template")) {
                        break;
                    }
                    if (isElementA(ancestor, "table")) {
                        setInsertionMode(Parser.Mode.IN_SELECT_IN_TABLE);
                        return;
                    }
                }
                setInsertionMode(Parser.Mode.IN_SELECT);
                return;
            }
            if (isElementA(node, "td", "th") && !last) {
                setInsertionMode(Mode.IN_CELL);
                return;
            }
            if (isElementA(node, "tr")) {
                setInsertionMode(Mode.IN_ROW);
                return;
            }
            if (isElementA(node, "tbody", "thead", "tfoot")) {
                setInsertionMode(Mode.IN_TABLE_BODY);
                return;
            }
            if (isElementA(node, "caption")) {
                setInsertionMode(Mode.IN_CAPTION);
                return;
            }
            if (isElementA(node, "colgroup")) {
                setInsertionMode(Mode.IN_COLUMN_GROUP);
                return;
            }
            if (isElementA(node, "table")) {
                setInsertionMode(Mode.IN_TABLE);
                return;
            }
            if (isElementA(node, "template")) {
                setInsertionMode(getCurrentTemplateInsertionMode());
                return;
            }
            if (isElementA(node, "head") && !last) {
                setInsertionMode(Mode.IN_HEAD);
                return;
            }
            if (isElementA(node, "body")) {
                setInsertionMode(Mode.IN_BODY);
                return;
            }
            if (isElementA(node, "frameset")) {
                setInsertionMode(Mode.IN_FRAMESET);
                return;
            }
            if (isElementA(node, "html")) {
                if (getHeadElementPointer() == null) {
                    setInsertionMode(Mode.BEFORE_HEAD);
                    return;
                } else {
                    setInsertionMode(Mode.AFTER_HEAD);
                    return;
                }
            }
            if (last) {
                setInsertionMode(Mode.IN_BODY);
                return;
            }
        }
        assert false;
        throw new IllegalStateException("Should have exited loop in the last check of the loop above.");
    }
    
    protected void setTokenizerState(final Tokenizer.State state) {
        parser.setTokenizerState(state);
    }
    
    /**
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#adjusted-current-node">
     *      adjusted current node</a>
     */
    protected Element getAdjustedCurrentNode() {
        if (getStackOfOpenElementsSize() == 1 && isHTMLFragmentParsingAlgorithm()) {
            throw new UnsupportedOperationException();
        } else {
            return getCurrentNode();
        }
    }
    
    /**
     * Returns the current node.
     * <p>
     * The current node is the bottommost node in this
     * <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>.
     * 
     * @return the current node
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#current-node">current node</a>
     */
    protected Element getCurrentNode() {
        return parser.getCurrentOpenElement();
    }
    
    /**
     * Pops the current node.
     * <p>
     * The current node is the bottommost node in this
     * <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>.
     * 
     * @return the current node
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#current-node">current node</a>
     */
    protected Element popCurrentNode() {
        return parser.popOpenElement();
    }
    
    /**
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-open-elements">
     *      stack of open elements</a>
     */
    protected int getStackOfOpenElementsSize() {
        return parser.getNumOpenElements();
    }
    
    /**
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-open-elements">
     *      stack of open elements</a>
     */
    protected Element getFirstElementInStackOfOpenElements() {
        return getOpenElement(0);
    }
    
    /**
     * @param tagName
     * @return
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>
     */
    protected boolean isStackOfOpenElementsContains(final String tagName) {
        for (final Element element : parser.getOpenElementsIterable()) {
            if (isElementA(element, tagName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-open-elements">
     *      stack of open elements</a>
     */
    protected Element getNodeImmediatelyBeforeCurrentNode() {
        return getOpenElement(getStackOfOpenElementsSize() - 2);
    }
    
    /**
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-open-elements">
     *      stack of open elements</a>
     */
    protected boolean isStackOfOpenElementsHasOnlyOneNode() {
        return getStackOfOpenElementsSize() == 1;
    }
    
    /**
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-open-elements">
     *      stack of open elements</a>
     */
    protected Element getSecondElementOfStackOfOpenElements() {
        return getOpenElement(1);
    }
    
    /**
     * @param element
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-open-elements">
     *      stack of open elements</a>
     */
    protected void removeNodeFromStackOfOpenElements(final Element element) {
        parser.removeOpenElement(element);
    }
    
    /**
     * @param tagNames
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-open-elements">
     *      stack of open elements</a>
     */
    protected boolean isStackOfOpenElementsContainsOtherThan(final Collection<String> tagNames) {
        for (final Element element : parser.getOpenElementsIterable()) {
            if ( !isElementA(element, tagNames)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param index
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-open-elements">
     *      stack of open elements</a>
     */
    protected Element getOpenElement(final int index) {
        return parser.getOpenElement(index);
    }
    
    /**
     * Adds the element to the stack of open elements.
     * <p>
     * Initially, the stack of open elements is empty. The stack grows downwards; the topmost node on the stack is the first one added to the stack, and the bottommost node of the stack is the most recently added node in the stack (notwithstanding when the stack is manipulated in a random access fashion as part of the handling for misnested tags).
     * <p>
     * The html node, however it is created, is the topmost node of the stack. It only gets popped off the stack when the parser finishes.
     * 
     * @param element the open element to add to the stack
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>
     */
    protected void addToStackOfOpenElements(final Element element) {
        parser.pushOpenElement(element);
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#clear-the-stack-back-to-a-table-context">
     *      clear the stack back to a table context</a>
     */
    protected void clearStackBackToTableContext() {
        while (isElementA(getCurrentNode(), "table", "template", "html")) {
            popCurrentNode();
        }
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#clear-the-stack-back-to-a-table-body-context">
     *      clear the stack back to a table body context</a>
     */
    protected void clearStackBackToTableBodyContext() {
        while (isElementA(getCurrentNode(), "tbody", "tfoot", "thead", "template", "html")) {
            popCurrentNode();
        }
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#clear-the-stack-back-to-a-table-row-context">
     *      clear the stack back to a table row context</a>
     */
    protected void clearStackBackToTableRowContext() {
        while (isElementA(getCurrentNode(), "tr", "template", "html")) {
            popCurrentNode();
        }
    }
    
    /**
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-template-insertion-modes">
     *      stack of template insertion modes</a>
     */
    protected boolean isStackOfTemplateInsertionModesEmpty() {
        return parser.getNumTemplateInsertionModes() == 0;
    }
    
    /**
     * @param mode
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#stack-of-template-insertion-modes">
     *      stack of template insertion modes</a>
     */
    protected void pushOntoStackOfTemplateInsertionModes(final Parser.Mode mode) {
        parser.pushTemplateInsertionMode(mode);
    }
    
    /**
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#current-template-insertion-mode">
     *      current template insertion mode</a>
     */
    protected Parser.Mode popCurrentTemplateInsertionMode() {
        return parser.popTemplateInsertionMode();
    }
    
    /**
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#current-template-insertion-mode">
     *      current template insertion mode</a>
     */
    protected Parser.Mode getCurrentTemplateInsertionMode() {
        return parser.getCurrentTemplateInsertionMode();
    }
    
    protected boolean isListOfActiveFormattingElementsContainsAfterLastMarker(final String... tagNames) {
        return isListOfActiveFormattingElementsContainsAfterLastMarker(Arrays.asList(tagNames));
    }
    
    protected boolean isListOfActiveFormattingElementsContainsAfterLastMarker(final Collection<String> tagNames) {
        throw new UnsupportedOperationException();
        // TODO: ??
//        return false;
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#list-of-active-formatting-elements">
     *      list of active formatting elements</a>
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#push-onto-the-list-of-active-formatting-elements">
     *      push onto the list of active formatting elements</a>
     */
    protected void pushOntoListOfActiveFormattingElements(final StartTagToken startTagToken, final Element element) {
        if (startTagToken == null) {
            throw new NullPointerException();
        }
        if (element == null) {
            throw new NullPointerException();
        }
        parser.pushOntoListOfActiveFormattingElements(startTagToken, element);
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#list-of-active-formatting-elements">
     *      list of active formatting elements</a>
     */
    protected void insertMarkerAtEndOfListOfActiveFormattingElements() {
        parser.addMarkerToListOfActiveFormattingElements();
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#clear-the-list-of-active-formatting-elements-up-to-the-last-marker">
     *      clear the list of active formatting elements up to the last
     *      marker</a>
     */
    protected void clearListOfActiveFormattingElementsUpToLastMarker() {
        parser.clearActiveFormattingElements();
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#pending-table-character-tokens">
     *      pending table character tokens</a>
     * @see <a href=
     *      "http://www.w3.org/TR/html5/infrastructure.html#space-character">
     *      space characters</a>
     */
    protected boolean isPendingTableCharacterTokensListContainsCharactersThatAreNotSpaceCharacters() {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    protected List<CharacterToken> getPendingTableCharacterTokens() {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    protected void setPendingTableCharacterTokens() {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    /**
     * @param characterToken
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#pending-table-character-tokens">
     *      pending table character tokens</a>
     */
    protected void appendToPendingTableCharacterTokens(final CharacterToken characterToken) {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    /**
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#stop-parsing">stop
     *      parsing</a>
     */
    protected void stopParsing() {
        parser.stopParsing();
    }
    
    /**
     * Returns the appropriate place for inserting a node into the document.
     * <p>
     * The appropriate place for inserting a node, optionally using a particular <var>override target</var>, is the position in an element returned by running the following steps:
     * <ol>
     *   <li>
     *     If there was an <var>override target</var> specified, then let <var>target</var> be the <var>override target</var>.
     *     <p>Otherwise, let <var>target</var> be the current node.
     *   </li>
     *   <li>
     *     Determine the <var>adjusted insertion location</var> using the first matching steps from the following list:
     *     <dl>
     *       <dt>If foster parenting is enabled and target is a table, tbody, tfoot, thead, or tr element
     *       <dd>
     *         Run these substeps:
     *         <ol>
     *           <li>Let <var>last template</var> be the last template element in the stack of open elements, if any.
     *           <li>Let <var>last table</var> be the last table element in the stack of open elements, if any.
     *           <li>If there is a <var>last template</var> and either there is no <var>last table</var>, or there is one, but <var>last template</var> is lower (more recently added) than <var>last table</var> in the stack of open elements, then: let <var>adjusted insertion location</var> be inside <var>last template</var>'s template contents, after its last child (if any), and abort these substeps.
     *           <li>If there is no <var>last table</var>, then let <var>adjusted insertion location</var> be inside the first element in the stack of open elements (the html element), after its last child (if any), and abort these substeps. (fragment case)
     *           <li>If <var>last table</var> has a parent element, then let <var>adjusted insertion location</var> be inside <var>last table</var>'s parent element, immediately before <var>last table</var>, and abort these substeps.
     *           <li>Let <var>previous element</var> be the element immediately above <var>last table</var> in the stack of open elements.
     *           <li>Let <var>adjusted insertion location</var> be inside <var>previous element</var>, after its last child (if any).
     *         </ol>
     *       </dd>
     *       <dt>Otherwise
     *       <dd>Let <var>adjusted insertion location</var> be inside <var>target</var>, after its last child (if any).
     *     </dl>
     *   </li>
     *   <li>If the <var>adjusted insertion location</var> is inside a template element, let it instead be inside the template element's template contents, after its last child (if any).
     *   <li>Return the <var>adjusted insertion location</var>.
     * </ol>
     * 
     * @return an insertion position
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#appropriate-place-for-inserting-a-node">appropriate place for inserting a node</a>
     */
    protected InsertionPosition getAppropriatePlaceForInsertingNode() {
        return getAppropriatePlaceForInsertingNode(getCurrentNode());
    }
    
    /**
     * Returns the appropriate place for inserting a node into the document.
     * <p>
     * The appropriate place for inserting a node, optionally using a particular <var>override target</var>, is the position in an element returned by running the following steps:
     * <ol>
     *   <li>
     *     If there was an <var>override target</var> specified, then let <var>target</var> be the <var>override target</var>.
     *     <p>Otherwise, let <var>target</var> be the current node.
     *   </li>
     *   <li>
     *     Determine the <var>adjusted insertion location</var> using the first matching steps from the following list:
     *     <dl>
     *       <dt>If foster parenting is enabled and target is a table, tbody, tfoot, thead, or tr element
     *       <dd>
     *         Run these substeps:
     *         <ol>
     *           <li>Let <var>last template</var> be the last template element in the stack of open elements, if any.
     *           <li>Let <var>last table</var> be the last table element in the stack of open elements, if any.
     *           <li>If there is a <var>last template</var> and either there is no <var>last table</var>, or there is one, but <var>last template</var> is lower (more recently added) than <var>last table</var> in the stack of open elements, then: let <var>adjusted insertion location</var> be inside <var>last template</var>'s template contents, after its last child (if any), and abort these substeps.
     *           <li>If there is no <var>last table</var>, then let <var>adjusted insertion location</var> be inside the first element in the stack of open elements (the html element), after its last child (if any), and abort these substeps. (fragment case)
     *           <li>If <var>last table</var> has a parent element, then let <var>adjusted insertion location</var> be inside <var>last table</var>'s parent element, immediately before <var>last table</var>, and abort these substeps.
     *           <li>Let <var>previous element</var> be the element immediately above <var>last table</var> in the stack of open elements.
     *           <li>Let <var>adjusted insertion location</var> be inside <var>previous element</var>, after its last child (if any).
     *         </ol>
     *       </dd>
     *       <dt>Otherwise
     *       <dd>Let <var>adjusted insertion location</var> be inside <var>target</var>, after its last child (if any).
     *     </dl>
     *   </li>
     *   <li>If the <var>adjusted insertion location</var> is inside a template element, let it instead be inside the template element's template contents, after its last child (if any).
     *   <li>Return the <var>adjusted insertion location</var>.
     * </ol>
     * 
     * @param overrideTarget the override target
     * @return an insertion position
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#appropriate-place-for-inserting-a-node">appropriate place for inserting a node</a>
     */
    protected InsertionPosition getAppropriatePlaceForInsertingNode(final Node overrideTarget) {
        final Node target = overrideTarget;
        
        final InsertionPosition adjustedInsertionLocation;
        
        final Set<String> tableContainers = new HashSet<>(Arrays.asList("table", "tbody", "tfoot", "thead", "tr"));
        if (isFosterParentingEnabled() && target.getNodeType() == Node.ELEMENT_NODE
                && tableContainers.contains(target.getNodeName())) {
            // find last template in stack of open elements
            // find last table in stack of open elements
            throw new UnsupportedOperationException();
        } else {
            adjustedInsertionLocation = new AfterLastChildInsertionPosition(target);
        }
        
        if (adjustedInsertionLocation.getContainingNode().getNodeType() == Node.ELEMENT_NODE
                && adjustedInsertionLocation.getContainingNode().getNodeName().equals("template")) {
            // return position inside "template contents", after last child
            throw new UnsupportedOperationException();
        } else {
            return adjustedInsertionLocation;
        }
    }
    
    /**
     * Inserts a character.
     * <p>
     * When the steps below require the user agent to insert a character while processing a token, the user agent must run the following steps:
     * <ol>
     *   <li>Let <var>data</var> be the characters passed to the algorithm, or, if no characters were explicitly specified, the character of the character token being processed.
     *   <li>Let the <var>adjusted insertion location</var> be the appropriate place for inserting a node.
     *   <li>If the <var>adjusted insertion location</var> is in a Document node, then abort these steps.
     *   <li>
     *     If there is a Text node immediately before the <var>adjusted insertion location</var>, then append <var>data</var> to that Text node's data.
     *     <p>Otherwise, create a new Text node whose data is <var>data</var> and whose ownerDocument is the same as that of the element in which the <var>adjusted insertion location</var> finds itself, and insert the newly created node at the <var>adjusted insertion location</var>.
     *   </li>
     * </ol>
     * 
     * @param characterToken the character token
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insert-a-character">insert a character</a>
     */
    protected void insertCharacter(final CharacterToken characterToken) {
        insertCharacter(characterToken.getCharacter());
    }
    
    /**
     * Inserts a character.
     * <p>
     * When the steps below require the user agent to insert a character while processing a token, the user agent must run the following steps:
     * <ol>
     *   <li>Let <var>data</var> be the characters passed to the algorithm, or, if no characters were explicitly specified, the character of the character token being processed.
     *   <li>Let the <var>adjusted insertion location</var> be the appropriate place for inserting a node.
     *   <li>If the <var>adjusted insertion location</var> is in a Document node, then abort these steps.
     *   <li>
     *     If there is a Text node immediately before the <var>adjusted insertion location</var>, then append <var>data</var> to that Text node's data.
     *     <p>Otherwise, create a new Text node whose data is <var>data</var> and whose ownerDocument is the same as that of the element in which the <var>adjusted insertion location</var> finds itself, and insert the newly created node at the <var>adjusted insertion location</var>.
     *   </li>
     * </ol>
     * 
     * @param character the character
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insert-a-character">insert a character</a>
     */
    protected void insertCharacter(final char character) {
        final String data = String.valueOf(character);
        final InsertionPosition adjustedInsertionLocation = getAppropriatePlaceForInsertingNode();
        if (adjustedInsertionLocation.getContainingNode().getNodeType() == Node.DOCUMENT_NODE) {
            // abort
            return;
        }
        final Node nodeImmediatelyBefore = adjustedInsertionLocation.getNodeImmediatelyBefore();
        if (nodeImmediatelyBefore != null && nodeImmediatelyBefore.getNodeType() == Node.TEXT_NODE) {
            final Text textNode = (Text) nodeImmediatelyBefore;
            textNode.appendData(data);
        } else {
            final Text textNode = adjustedInsertionLocation.getContainingNode().getOwnerDocument().createTextNode(data);
            adjustedInsertionLocation.insert(textNode);
        }
    }
    
    /**
     * Insert a comment.
     * <p>
     * When the steps below require the user agent to insert a comment while processing a comment token, optionally with an explicitly insertion position <var>position</var>, the user agent must run the following steps:
     * <ol>
     *   <li>Let <var>data</var> be the data given in the comment token being processed.
     *   <li>If <var>position</var> was specified, then let the <var>adjusted insertion location</var> be <var>position</var>. Otherwise, let <var>adjusted insertion location</var> be the appropriate place for inserting a node.
     *   <li>Create a Comment node whose data attribute is set to <var>data</var> and whose ownerDocument is the same as that of the node in which the <var>adjusted insertion location</var> finds itself.
     *   <li>Insert the newly created node at the <var>adjusted insertion location</var>.
     * </ol>
     * <p>DOM mutation events must not fire for changes caused by the UA parsing the document. This includes the parsing of any content inserted using document.write() and document.writeln() calls.
     * <p>However, mutation observers <em>do</em> fire, as required by the DOM specification.
     * 
     * @param commentToken the comment token to insert
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insert-a-comment">insert a comment</a>
     */
    protected void insertComment(final CommentToken commentToken) {
        insertComment(commentToken, getAppropriatePlaceForInsertingNode());
    }
    
    /**
     * Insert a comment.
     * <p>
     * When the steps below require the user agent to insert a comment while processing a comment token, optionally with an explicitly insertion position <var>position</var>, the user agent must run the following steps:
     * <ol>
     *   <li>Let <var>data</var> be the data given in the comment token being processed.
     *   <li>If <var>position</var> was specified, then let the <var>adjusted insertion location</var> be <var>position</var>. Otherwise, let <var>adjusted insertion location</var> be the appropriate place for inserting a node.
     *   <li>Create a Comment node whose data attribute is set to <var>data</var> and whose ownerDocument is the same as that of the node in which the <var>adjusted insertion location</var> finds itself.
     *   <li>Insert the newly created node at the <var>adjusted insertion location</var>.
     * </ol>
     * 
     * @param commentToken the comment token to insert
     * @param position the position to insert the comment
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insert-a-comment">insert a comment</a>
     */
    protected void insertComment(final CommentToken commentToken, final InsertionPosition position) {
        final String data = commentToken.getContent();
        final InsertionPosition adjustedInsertionLocation = position;
        final Node containingNode = adjustedInsertionLocation.getContainingNode();
        final Document ownerDocument;
        if (containingNode.getNodeType() == Node.DOCUMENT_NODE) {
            /*
             * This is against the "spec", but it appears the "owner document"
             * of the document itself is null, so special-casing this for the
             * case where websites put comments before any markup.
             */
            ownerDocument = (Document) containingNode;
        } else {
            ownerDocument = containingNode.getOwnerDocument();
        }
        final Comment commentNode = ownerDocument.createComment(data);
        adjustedInsertionLocation.insert(commentNode);
        
        // do not fire DOM mutation events
        // do fire mutation observers
    }
    
    /**
     * Creates an element for the given token.
     * <p>When the steps below require the UA to create an element for a token in a particular <var>given namespace</var> and with a particular <var>intended parent</var>, the UA must run the following steps:
     * <ol>
     *   <li>
     *     Create a node implementing the interface appropriate for the element type corresponding to the tag name of the token in given namespace (as given in the specification that defines that element, e.g. for an a element in the HTML namespace, this specification defines it to be the HTMLAnchorElement interface), with the tag name being the name of that element, with the node being in the given namespace, and with the attributes on the node being those given in the given token.
     *     <p>The interface appropriate for an element in the HTML namespace that is not defined in this specification (or other applicable specifications) is HTMLUnknownElement. Elements in other namespaces whose interface is not defined by that namespace's specification must use the interface Element.
     *     <p>The ownerDocument of the newly created element must be the same as that of the intended parent.
     *   </li>
     *   <li>If the newly created element has an xmlns attribute in the XMLNS namespace whose value is not exactly the same as the element's namespace, that is a parse error. Similarly, if the newly created element has an xmlns:xlink attribute in the XMLNS namespace whose value is not the XLink Namespace, that is a parse error.
     *   <li>If the newly created element is a resettable element, invoke its reset algorithm. (This initializes the element's value and checkedness based on the element's attributes.)
     *   <li>If the element is a form-associated element, and the form element pointer is not null, and there is no template element on the stack of open elements, and the newly created element is either not reassociateable or doesn't have a form attribute, and the intended parent is in the same home subtree as the element pointed to by the form element pointer, associate the newly created element with the form element pointed to by the form element pointer, and suppress the running of the reset the form owner algorithm when the parser subsequently attempts to insert the element.
     *   <li>Return the newly created element.
     * </ol>
     * 
     * @param startTagToken the token to create the element for
     * @param givenNamespace the namespace for the new element
     * @param intendedParent the element that will have the new element as a child
     * @return the newly created element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#create-an-element-for-the-token">create an element for a token</a>
     */
    protected Element createElementForToken(final StartTagToken startTagToken, final String givenNamespace,
            final Node intendedParent) {
        final Document document = intendedParent.getOwnerDocument();
        final Element element = document.createElementNS(givenNamespace, startTagToken.getTagName());
        for (final TagToken.Attribute attribute : startTagToken.getAttributes()) {
            final String name = attribute.getName();
            final String value = attribute.getValue();
            element.setAttribute(name, value);
        }
        // check for xmlns attribute
        // check for xmlns:xlink attribute
        if (isResettable(element)) {
            invokeResetAlgorithm(element);
        }
        // check if form-associated element
        if (isFormAssociatedElement(element) && getFormElementPointer() != null
                && !isStackOfOpenElementsContains("template")) {
            // check not reassociateable or no form attribute
            // check intended parent in same home subtree as form pointer
            // associate with form
        }
        return element;
    }
    
    /**
     * Inserts a foreign element.
     * <p>
     * When the steps below require the user agent to insert a foreign element for a token in a given namespace, the user agent must run these steps:
     * <ol>
     *   <li>Let the <var>adjusted insertion location</var> be the appropriate place for inserting a node.
     *   <li>Create an element for the token in the given namespace, with the intended parent being the element in which the <var>adjusted insertion location</var> finds itself.
     *   <li>If it is possible to insert an element at the <var>adjusted insertion location</var>, then insert the newly created element at the <var>adjusted insertion location</var>.
     *   <li>Push the element onto the stack of open elements so that it is the new current node.
     *   <li>Return the newly created element.
     * </ol>
     * 
     * @param startTagToken the tag token for the new element
     * @param givenNamespace the target namespace for the new element
     * @return the newly inserted element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insert-a-foreign-element">insert a foreign element</a>
     */
    protected Element insertForeignElement(final StartTagToken startTagToken, final String givenNamespace) {
        final InsertionPosition adjustedInsertionLocation = getAppropriatePlaceForInsertingNode();
        final Element element = createElementForToken(startTagToken, givenNamespace, adjustedInsertionLocation.getContainingNode());
        try {
            adjustedInsertionLocation.insert(element);
        } catch (final RuntimeException e) {
            // drop it on the floor
        }
        addToStackOfOpenElements(element);
        
        assert getCurrentNode() == element;
        
        return element;
    }
    
    /**
     * Inserts an HTML element.
     * <p>
     * When the steps below require the user agent to insert an HTML element for a token, the user agent must insert a foreign element for the token, in the HTML namespace.
     * 
     * @param startTagToken the tag token for the new element
     * @return the newly inserted element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insert-an-html-element">insert an HTML element</a>
     */
    protected Element insertHTMLElement(final StartTagToken startTagToken) {
        final Element element = insertForeignElement(startTagToken, HTML_NAMESPACE);
        // TODO: It is unclear from the CSS DOM whether this is supposed to
// happen automatically or not.
//        if (element.hasAttributeNS(null, "style") && element.isSupported("CSS",
//                "2.0")) {
//            final ElementCSSInlineStyle elementCSSInlineStyle = (ElementCSSInlineStyle)
//                    element;
//            elementCSSInlineStyle.getStyle().setCssText(element.getAttributeNS(null,
//                    "style"));
//        }
        return element;
    }

    /**
     * Inserts an HTML element.
     * <p>
     * When the steps below require the user agent to insert an HTML element for a token, the user agent must insert a foreign element for the token, in the HTML namespace.
     * 
     * @param tagName the name of the tag to create and insert
     * @return the newly inserted element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insert-an-html-element">insert an HTML element</a>
     */
    protected Element insertHTMLElement(final String tagName) {
        final StartTagToken startTagToken = new StartTagToken();
        for (final char character : tagName.toCharArray()) {
            startTagToken.appendToTagName(character);
        }
        return insertHTMLElement(startTagToken);
//        return insertForeignElement(startTagToken, HTML_NAMESPACE);
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#generate-implied-end-tags">
     *      generate implied end tags</a>
     */
    protected void generateImpliedEndTagsExcept(final Collection<String> excludedTags) {
        final Set<String> implied = new HashSet<>(IMPLIED_END_TAGS);
        implied.removeAll(excludedTags);
        while (implied.contains(getCurrentNode().getTagName())) {
            popCurrentNode();
        }
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#generate-implied-end-tags">
     *      generate implied end tags</a>
     */
    protected void generateImpliedEndTagsExcept(final String... excludedTags) {
        generateImpliedEndTagsExcept(Arrays.asList(excludedTags));
    }
    
    /**
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#generate-implied-end-tags">
     *      generate implied end tags</a>
     */
    protected void generateImpliedEndTags() {
        while (IMPLIED_END_TAGS.contains(getCurrentNode().getTagName())) {
            popCurrentNode();
        }
    }
    
    /**
     * @see <a href="http://www.w3.org/TR/html5/syntax.html#close-the-cell">
     *      close the cell</a>
     */
    protected void closeCell() {
        generateImpliedEndTags();
        if ( !isElementA(getCurrentNode(), "td", "th")) {
            if (isAllowParseErrors()) {
                // do nothing?
            } else {
                throw new ParseErrorException(
                        "Expected current node to be a table cell, instead it was: " + getCurrentNode().getTagName());
            }
        }
        // pop stack until popped "td" or "th"
        Element popped;
        do {
            popped = popCurrentNode();
        } while ( !isElementA(popped, "td", "th"));
        clearListOfActiveFormattingElementsUpToLastMarker();
        setInsertionMode(Parser.Mode.IN_ROW);
    }
    
    /**
     * Runs the generic raw text element parsing algorithm.
     * <p>
     * The generic raw text element parsing algorithm and the generic RCDATA element parsing algorithm consist of the following steps. These algorithms are always invoked in response to a start tag token.
     * <ol>
     *   <li>Insert an HTML element for the token.
     *   <li>Switch the tokenizer to the RAWTEXT state.
     *   <li>Let the original insertion mode be the current insertion mode.
     *   <li>Then, switch the insertion mode to "text".
     * </ol>
     * 
     * @param startTagToken the start tag token
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#generic-raw-text-element-parsing-algorithm">generic raw text element parsing algorithm</a>
     */
    protected void genericRawTextElementParsingAlgorithm(final StartTagToken startTagToken) {
        insertHTMLElement(startTagToken);
        setTokenizerState(Tokenizer.State.RAWTEXT);
        setOriginalInsertionMode(getInsertionMode());
        setInsertionMode(Parser.Mode.TEXT);
    }
    
    /**
     * Runs the generic RCDATA element parsing algorithm.
     * <p>
     * The generic raw text element parsing algorithm and the generic RCDATA element parsing algorithm consist of the following steps. These algorithms are always invoked in response to a start tag token.
     * <ol>
     *   <li>Insert an HTML element for the token.
     *   <li>Switch the tokenizer to the RCDATA state.
     *   <li>Let the original insertion mode be the current insertion mode.
     *   <li>Then, switch the insertion mode to "text".
     * </ol>
     * 
     * @param startTagToken the start tag token
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#generic-rcdata-element-parsing-algorithm">generic RCDATA element parsing algorithm</a>
     */
    protected void genericRCDATAElementParsingAlgorithm(final StartTagToken startTagToken) {
        insertHTMLElement(startTagToken);
        setTokenizerState(Tokenizer.State.RCDATA);
        setOriginalInsertionMode(getInsertionMode());
        setInsertionMode(Parser.Mode.TEXT);
    }
    
    /**
     * @param startTagToken
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#adjust-mathml-attributes">
     *      adjust MathML attributes</a>
     */
    protected void adjustMathMLAttributes(final StartTagToken startTagToken) {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    /**
     * @param startTagToken
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#adjust-foreign-attributes">
     *      adjust foreign attributes</a>
     */
    protected void adjustForeignAttributes(final StartTagToken startTagToken) {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    /**
     * @param startTagToken
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#adjust-svg-attributes">
     *      adjust SVG attributes</a>
     */
    protected void adjustSVGAttributes(final StartTagToken startTagToken) {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    /**
     * @param target
     * @param elementTypes
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#has-an-element-in-the-specific-scope">
     *      have an element <var>target node</var> in a specific scope</a>
     */
    protected boolean hasElementInScope(final String target, final Collection<String> elementTypes) {
        for (int index = getStackOfOpenElementsSize() - 1; index >= 0; index-- ) {
            final Element node = getOpenElement(index);
            if (isElementA(node, target)) {
                return true;
            }
            if (isElementA(node, elementTypes)) {
                return false;
            }
        }
        throw new ParseErrorException("Should have found an html element in the stack of open elements.");
    }
    
    /**
     * @param target
     * @param elementTypes
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#has-an-element-in-the-specific-scope">
     *      have an element <var>target node</var> in a specific scope</a>
     */
    protected boolean hasElementInScope(final String target, final String... elementTypes) {
        return hasElementInScope(target, Arrays.asList(elementTypes));
    }
    
    /**
     * @param target
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#has-an-element-in-scope">
     *      have a particular element in scope</a>
     */
    protected boolean hasParticularElementInScope(final String target) {
        return hasElementInScope(target, SPECIFIC_SCOPE);
    }
    
    protected boolean hasParticularElementInButtonScope(final String target) {
        return hasElementInScope(target, BUTTON_SCOPE);
    }
    
    protected boolean hasParticularElementInListItemScope(final String target) {
        return hasElementInScope(target, LIST_ITEM_SCOPE);
    }
    
    protected boolean hasParticularElementInTableScope(final String target) {
        return hasElementInScope(target, TABLE_SCOPE);
    }
    
    /**
     * @param target
     * @return
     * @see <a href=
     *      "http://www.w3.org/TR/html5/syntax.html#has-an-element-in-select-scope">
     *      have a particular element in select scope</a>
     */
    protected boolean hasParticularElementInSelectScope(final String target) {
        for (int index = getStackOfOpenElementsSize() - 1; index >= 0; index-- ) {
            final Element node = getOpenElement(index);
            if (isElementA(node, target)) {
                return true;
            }
            if ( !isElementA(node, "option", "optgroup")) {
                return false;
            }
        }
        throw new ParseErrorException("Should have found an html element in the stack of open elements.");
    }
    
    /**
     * Acknowledges the token's self-closing flag, if set.
     * <p>
     * When a start tag token is emitted with its self-closing flag set, if the flag is not acknowledged when it is processed by the tree construction stage, that is a parse error.
     * 
     * @param startTagToken the start tag token
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#acknowledge-self-closing-flag">Acknowledge the token&#x27;s self-closing flag</a>
     */
    protected void acknowledgeTokenSelfClosingFlag(final StartTagToken startTagToken) {
        if (startTagToken.isSelfClosing()) {
            parser.acknowledgeSelfClosingFlag();
        }
    }
    
    protected boolean isElementA(final Element element, final Collection<String> tagNames) {
        return tagNames.contains(element.getTagName());
    }
    
    protected boolean isElementA(final Element element, final String... tagNames) {
        return isElementA(element, Arrays.asList(tagNames));
    }
    
    protected boolean isSpecialCategory(final Element element) {
        return isElementA(element, SPECIAL_ELEMENTS);
    }
    
    protected boolean isSpecialCategory(final String tagName) {
        return SPECIAL_ELEMENTS.contains(tagName);
    }
    
    protected boolean isSpecialCategoryExcept(final String tagName, final Collection<String> excluded) {
        final Set<String> set = new HashSet<>(SPECIAL_ELEMENTS);
        set.removeAll(excluded);
        return set.contains(tagName);
    }
    
    protected boolean isSpecialCategoryExcept(final String tagName, final String... excluded) {
        return isSpecialCategoryExcept(tagName, Arrays.asList(excluded));
    }
    
    protected boolean isFormattingCategory(final String tagName) {
        return FORMATTING_ELEMENTS.contains(tagName);
    }
    
    /**
     * Returns whether an element can be affected by a form reset.
     * <p>
     * Denotes elements that can be affected when a form element is reset.
     * <ul>
     *   <li>input
     *   <li>keygen
     *   <li>output
     *   <li>select
     *   <li>textarea
     * </ul>
     * 
     * @param element the element to check
     * @return {@code true} if the element can be affected by a form reset
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/forms.html#category-reset">Resettable elements</a>
     */
    protected boolean isResettable(final Element element) {
        final String namespaceURI = element.getNamespaceURI();
        final String localName = element.getLocalName();
        if (HTML_NAMESPACE.equals(namespaceURI)) {
            switch (localName) {
            case "input": // fall through
            case "keygen": // fall through
            case "output": // fall through
            case "select": // fall through
            case "textarea": {
                return true;
            }
            default: {
                return false;
            }
            }
        } else {
            return false;
        }
    }
    
    /**
     * Resets a form element.
     * <p>
     * When a form element <var>form</var> is reset, the user agent must fire a simple event named reset, that bubbles and is cancelable, at <var>form</var>, and then, if that event is not canceled, must invoke the reset algorithm of each resettable element whose form owner is <var>form</var>.
     * <p>
     * Each resettable element defines its own reset algorithm. Changes made to form controls as part of these algorithms do not count as changes caused by the user (and thus, e.g., do not cause input events to fire).
     * 
     * @param element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/forms.html#concept-form-reset-control">4.10.23 Resetting a form</a>
     */
    protected void invokeResetAlgorithm(final Element element) {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns whether an element can be associated with a form.
     * <p>
     * A number of the elements are form-associated elements, which means they can have a form owner.
     * <ul>
     *   <li>button
     *   <li>fieldset
     *   <li>input
     *   <li>keygen
     *   <li>label
     *   <li>object
     *   <li>output
     *   <li>select
     *   <li>textarea
     *   <li>img
     * </ul> 
     * 
     * @param element the element to check
     * @return {@code true} if the element can be associated with a form
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/forms.html#form-associated-element">form-associated elements</a>
     */
    protected boolean isFormAssociatedElement(final Element element) {
        final String namespaceURI = element.getNamespaceURI();
        final String localName = element.getLocalName();
        if (HTML_NAMESPACE.equals(namespaceURI)) {
            switch (localName) {
            case "button": // fall through
            case "fieldset": // fall through
            case "input": // fall through
            case "keygen": // fall through
            case "label": // fall through
            case "object": // fall through
            case "output": // fall through
            case "select": // fall through
            case "textarea": // fall through
            case "img": {
                return true;
            }
            default: {
                return false;
            }
            }
        }
        return false;
    }
    
    /**
     * Returns whether an element is reassociateable.
     * <p>
     * Denotes elements that have a form content attribute, and a matching form IDL attribute, that allow authors to specify an explicit form owner.
     * <ul>
     *   <li>button
     *   <li>fieldset
     *   <li>input
     *   <li>keygen
     *   <li>label
     *   <li>object
     *   <li>output
     *   <li>select
     *   <li>textarea
     * </ul> 
     * 
     * @param element the element to check
     * @return {@code true} if the element is reassociateable
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/forms.html#category-form-attr">Reassociateable elements</a>
     */
    protected boolean isReassociateableElement(final Element element) {
        final String namespaceURI = element.getNamespaceURI();
        final String localName = element.getLocalName();
        if (HTML_NAMESPACE.equals(namespaceURI)) {
            switch (localName) {
            case "button": // fall through
            case "fieldset": // fall through
            case "input": // fall through
            case "keygen": // fall through
            case "label": // fall through
            case "object": // fall through
            case "output": // fall through
            case "select": // fall through
            case "textarea": {
                return true;
            }
            default: {
                return false;
            }
            }
        }
        return false;
    }
    
    protected TagToken.Attribute getAttributeNamed(final StartTagToken startTagToken, final String attributeName) {
        for (final TagToken.Attribute attribute : startTagToken.getAttributes()) {
            final String name = attribute.getName();
            if (name.equals(attributeName)) {
                return attribute;
            }
        }
        return null;
    }
    
}
