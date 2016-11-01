package org.silnith.parser.html5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.grammar.mode.AfterAfterBodyInsertionMode;
import org.silnith.parser.html5.grammar.mode.AfterAfterFramesetInsertionMode;
import org.silnith.parser.html5.grammar.mode.AfterBodyInsertionMode;
import org.silnith.parser.html5.grammar.mode.AfterFramesetInsertionMode;
import org.silnith.parser.html5.grammar.mode.AfterHeadInsertionMode;
import org.silnith.parser.html5.grammar.mode.BeforeHeadInsertionMode;
import org.silnith.parser.html5.grammar.mode.BeforeHtmlInsertionMode;
import org.silnith.parser.html5.grammar.mode.ForeignContentMode;
import org.silnith.parser.html5.grammar.mode.InBodyInsertionMode;
import org.silnith.parser.html5.grammar.mode.InCaptionInsertionMode;
import org.silnith.parser.html5.grammar.mode.InCellInsertionMode;
import org.silnith.parser.html5.grammar.mode.InColumnGroupInsertionMode;
import org.silnith.parser.html5.grammar.mode.InFramesetInsertionMode;
import org.silnith.parser.html5.grammar.mode.InHeadInsertionMode;
import org.silnith.parser.html5.grammar.mode.InHeadNoScriptInsertionMode;
import org.silnith.parser.html5.grammar.mode.InRowInsertionMode;
import org.silnith.parser.html5.grammar.mode.InSelectInTableInsertionMode;
import org.silnith.parser.html5.grammar.mode.InSelectInsertionMode;
import org.silnith.parser.html5.grammar.mode.InTableBodyInsertionMode;
import org.silnith.parser.html5.grammar.mode.InTableInsertionMode;
import org.silnith.parser.html5.grammar.mode.InTableTextInsertionMode;
import org.silnith.parser.html5.grammar.mode.InTemplateInsertionMode;
import org.silnith.parser.html5.grammar.mode.InitialInsertionMode;
import org.silnith.parser.html5.grammar.mode.InsertionMode;
import org.silnith.parser.html5.grammar.mode.TextInsertionMode;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.silnith.parser.html5.lexical.token.StartTagToken;
import org.silnith.parser.html5.lexical.token.TagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * An HTML5 parser. This object is not thread-safe, nor re-entrant. It is only
 * suitable for using to parse one token stream into a document. Each token
 * stream to be parsed should instantiate a new parser.
 *
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#tree-construction">8.2.5 Tree construction</a>
 */
public class Parser {
    
    /**
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insertion-mode">insertion mode</a>
     * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
     */
    public enum Mode {
        /**
         * @see org.silnith.parser.html5.grammar.mode.InitialInsertionMode
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-initial-insertion-mode">8.2.5.4.1 The "initial" insertion mode</a>
         */
        INITIAL,
        /**
         * @see org.silnith.parser.html5.grammar.mode.BeforeHtmlInsertionMode
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-before-html-insertion-mode">8.2.5.4.2 The "before html" insertion mode</a>
         */
        BEFORE_HTML,
        /**
         * @see org.silnith.parser.html5.grammar.mode.BeforeHeadInsertionMode
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-before-head-insertion-mode">8.2.5.4.3 The "before head" insertion mode</a>
         */
        BEFORE_HEAD,
        /**
         * @see org.silnith.parser.html5.grammar.mode.InHeadInsertionMode
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inhead">8.2.5.4.4 The "in head" insertion mode</a>
         */
        IN_HEAD,
        /**
         * @see org.silnith.parser.html5.grammar.mode.InHeadNoScriptInsertionMode
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inheadnoscript">8.2.5.4.5 The "in head noscript" insertion mode</a>
         */
        IN_HEAD_NOSCRIPT,
        /**
         * @see org.silnith.parser.html5.grammar.mode.AfterHeadInsertionMode
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-after-head-insertion-mode">8.2.5.4.6 The "after head" insertion mode</a>
         */
        AFTER_HEAD,
        /**
         * @see org.silnith.parser.html5.grammar.mode.InBodyInsertionMode
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inbody">8.2.5.4.7 The "in body" insertion mode</a>
         */
        IN_BODY,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-incdata">8.2.5.4.8 The "text" insertion mode</a>
         */
        TEXT,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intable">8.2.5.4.9 The "in table" insertion mode</a>
         */
        IN_TABLE,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intabletext">8.2.5.4.10 The "in table text" insertion mode</a>
         */
        IN_TABLE_TEXT,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-incaption">8.2.5.4.11 The "in caption" insertion mode</a>
         */
        IN_CAPTION,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-incolgroup">8.2.5.4.12 The "in column group" insertion mode</a>
         */
        IN_COLUMN_GROUP,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intbody">8.2.5.4.13 The "in table body" insertion mode</a>
         */
        IN_TABLE_BODY,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intr">8.2.5.4.14 The "in row" insertion mode</a>
         */
        IN_ROW,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intd">8.2.5.4.15 The "in cell" insertion mode</a>
         */
        IN_CELL,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inselect">8.2.5.4.16 The "in select" insertion mode</a>
         */
        IN_SELECT,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inselectintable">8.2.5.4.17 The "in select in table" insertion mode</a>
         */
        IN_SELECT_IN_TABLE,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-intemplate">8.2.5.4.18 The "in template" insertion mode</a>
         */
        IN_TEMPLATE,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-afterbody">8.2.5.4.19 The "after body" insertion mode</a>
         */
        AFTER_BODY,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-inframeset">8.2.5.4.20 The "in frameset" insertion mode</a>
         */
        IN_FRAMESET,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-afterframeset">8.2.5.4.21 The "after frameset" insertion mode</a>
         */
        AFTER_FRAMESET,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-after-after-body-insertion-mode">8.2.5.4.22 The "after after body" insertion mode</a>
         */
        AFTER_AFTER_BODY,
        /**
         * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-after-after-frameset-insertion-mode">8.2.5.4.23 The "after after frameset" insertion mode</a>
         */
        AFTER_AFTER_FRAMESET
    }
    
    public static class FormattingElement implements Map.Entry<StartTagToken, Element> {
        
        private final StartTagToken startTagToken;
        
        private final Element element;
        
        public FormattingElement(final StartTagToken startTagToken, final Element element) {
            super();
            this.startTagToken = startTagToken;
            this.element = element;
        }
        
        @Override
        public StartTagToken getKey() {
            return startTagToken;
        }
        
        @Override
        public Element getValue() {
            return element;
        }
        
        @Override
        public Element setValue(final Element value) {
            throw new UnsupportedOperationException();
        }
        
    }
    
    private final Tokenizer tokenizer;
    
    private final Map<Mode, InsertionMode> insertionModeMap;
    
    private final InsertionMode foreignContentMode;
    
    private final boolean allowParseErrors;
    
    private final boolean scriptingEnabled;
    
    private boolean fosterParenting;
    
    private boolean framesetOK;
    
    private boolean quirksMode;
    
    private Mode insertionMode;
    
    private Mode originalInsertionMode;
    
    private boolean stop;
    
    private final Document document;
    
    private Element headElementPointer;
    
    private Element formElementPointer;
    
    private final List<Element> stackOfOpenElements;
    
    public final List<FormattingElement> listOfActiveFormattingElements;
    
    private final List<Mode> stackOfTemplateInsertionModes;
    
    /**
     * Constructs a new parser that will read from the given tokenizer and
     * construct a document using the given DOM implementation. The DOM
     * implementation must support feature "Core 2.0" or greater.
     * 
     * @param tokenizer the tokenizer
     * @param domImplementation the DOM implementation
     */
    public Parser(final Tokenizer tokenizer, final DOMImplementation domImplementation) {
        super();
        
        if ( !domImplementation.hasFeature("Core", "2.0")) {
            throw new IllegalArgumentException("DOM implementation must support Core 2.0 or higher.");
        }
        
        this.tokenizer = tokenizer;
        this.insertionModeMap = new EnumMap<>(Mode.class);
        this.foreignContentMode = new ForeignContentMode(this);
        this.allowParseErrors = true;
        this.scriptingEnabled = false;
        this.fosterParenting = false;
        this.framesetOK = true;
        this.quirksMode = false;
        this.insertionMode = Mode.INITIAL;
        this.originalInsertionMode = null;
        this.stop = false;
        this.document = domImplementation.createDocument(InsertionMode.HTML_NAMESPACE, "html", null);
        this.headElementPointer = null;
        this.formElementPointer = null;
        this.stackOfOpenElements = new ArrayList<>();
        this.listOfActiveFormattingElements = new ArrayList<>();
        this.stackOfTemplateInsertionModes = new ArrayList<>();
        
        this.insertionModeMap.put(Mode.INITIAL, new InitialInsertionMode(this));
        this.insertionModeMap.put(Mode.BEFORE_HTML, new BeforeHtmlInsertionMode(this));
        this.insertionModeMap.put(Mode.BEFORE_HEAD, new BeforeHeadInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_HEAD, new InHeadInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_HEAD_NOSCRIPT, new InHeadNoScriptInsertionMode(this));
        this.insertionModeMap.put(Mode.AFTER_HEAD, new AfterHeadInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_BODY, new InBodyInsertionMode(this));
        this.insertionModeMap.put(Mode.TEXT, new TextInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_TABLE, new InTableInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_TABLE_TEXT, new InTableTextInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_CAPTION, new InCaptionInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_COLUMN_GROUP, new InColumnGroupInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_TABLE_BODY, new InTableBodyInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_ROW, new InRowInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_CELL, new InCellInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_SELECT, new InSelectInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_SELECT_IN_TABLE, new InSelectInTableInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_TEMPLATE, new InTemplateInsertionMode(this));
        this.insertionModeMap.put(Mode.AFTER_BODY, new AfterBodyInsertionMode(this));
        this.insertionModeMap.put(Mode.IN_FRAMESET, new InFramesetInsertionMode(this));
        this.insertionModeMap.put(Mode.AFTER_FRAMESET, new AfterFramesetInsertionMode(this));
        this.insertionModeMap.put(Mode.AFTER_AFTER_BODY, new AfterAfterBodyInsertionMode(this));
        this.insertionModeMap.put(Mode.AFTER_AFTER_FRAMESET, new AfterAfterFramesetInsertionMode(this));
    }
    
    /**
     * Whether to allow parse errors and use error-handling behavior.
     * 
     * @return whether parse errors will be suppressed
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parse-error">parse errors</a>
     */
    public boolean isAllowParseErrors() {
        return allowParseErrors;
    }
    
    /**
     * Returns whether this parser is parsing an HTML fragment as opposed to a
     * full document.
     * 
     * @return {@code true} if this parser is parsing a fragment
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#html-fragment-parsing-algorithm">HTML fragment parsing algorithm</a>
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#fragment-case">fragment case</a>
     */
    public boolean isHTMLFragmentParsingAlgorithm() {
        return false;
    }
    
    /**
     * Whether scripting is enabled for this parser.
     * 
     * @return whether scripting is enabled.
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#scripting-flag">scripting flag</a>
     */
    public boolean isScriptingEnabled() {
        return scriptingEnabled;
    }
    
    /**
     * @return {@code true} if foster parenting is enabled
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#foster-parent">foster parenting</a>
     */
    public boolean isFosterParentingEnabled() {
        return fosterParenting;
    }
    
    /**
     * @param enabled whether foster parenting should be enabled
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#foster-parent">foster parenting</a>
     */
    public void setFosterParentingEnabled(final boolean enabled) {
        this.fosterParenting = enabled;
    }
    
    /**
     * @return {@code true} if frameset is OK
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#frameset-ok-flag">frameset-ok flag</a>
     */
    public boolean isFramesetOkFlag() {
        return framesetOK;
    }
    
    /**
     * @param isOK whether frameset is OK
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#frameset-ok-flag">frameset-ok flag</a>
     */
    public void setFramesetOKFlag(final boolean isOK) {
        this.framesetOK = isOK;
    }
    
    /**
     * @return {@code true} if the parser is in quirks mode
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/infrastructure.html#quirks-mode">quirks mode</a>
     */
    public boolean isQuirksMode() {
        return quirksMode;
    }
    
    /**
     * @param quirksMode whether to set the parser in quirks mode
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/infrastructure.html#quirks-mode">quirks mode</a>
     */
    public void setQuirksMode(final boolean quirksMode) {
        this.quirksMode = quirksMode;
    }
    
    /**
     * Returns the current insertion mode.
     * 
     * @return the current insertion mode
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insertion-mode">insertion mode</a>
     */
    public Mode getInsertionMode() {
        return insertionMode;
    }
    
    /**
     * Sets the current insertion mode. This is the mode that will be used to
     * parse the next token to be emitted.
     * 
     * @param insertionMode the insertion mode for the next token
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#insertion-mode">insertion mode</a>
     */
    public void setInsertionMode(final Mode insertionMode) {
        if (insertionMode == null) {
            throw new NullPointerException();
        }
        if (insertionMode == Mode.TEXT || insertionMode == Mode.IN_TABLE_TEXT) {
            this.originalInsertionMode = this.insertionMode;
        }
        this.insertionMode = insertionMode;
    }
    
    /**
     * @return the original insertion mode
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#original-insertion-mode">original insertion mode</a>
     */
    public Mode getOriginalInsertionMode() {
        return originalInsertionMode;
    }
    
    /**
     * @param originalInsertionMode the original insertion mode
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#original-insertion-mode">original insertion mode</a>
     */
    public void setOriginalInsertionMode(final Mode originalInsertionMode) {
        if (originalInsertionMode == null) {
            throw new NullPointerException();
        }
        this.originalInsertionMode = originalInsertionMode;
    }
    
    /**
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stop-parsing">stop parsing</a>
     */
    public void stopParsing() {
        stop = true;
    }
    
    public Document getDocument() {
        return document;
    }
    
    /**
     * @return the {@code head} element, if any
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#head-element-pointer"><code>head</code> element pointer</a>
     */
    public Element getHeadElementPointer() {
        return headElementPointer;
    }
    
    /**
     * @param element the {@code head} element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#head-element-pointer"><code>head</code> element pointer</a>
     */
    public void setHeadElementPointer(final Element element) {
        this.headElementPointer = element;
    }
    
    /**
     * @return the current {@code form} element, if any
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#form-element-pointer"><code>form</code> element pointer</a>
     */
    public Element getFormElementPointer() {
        return formElementPointer;
    }
    
    /**
     * @param formElement the current {@code form} element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#form-element-pointer"><code>form</code> element pointer</a>
     */
    public void setFormElementPointer(final Element formElement) {
        this.formElementPointer = formElement;
    }
    
    /**
     * Returns the number of elements on the stack of open elements.
     * 
     * @return the number of elements on the stack of open elements
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>
     */
    public int getNumOpenElements() {
        return stackOfOpenElements.size();
    }
    
    /**
     * Returns an {@link Iterable} over the stack of open elements. The
     * direction of iteration is not guaranteed.
     * 
     * @return an {@link Iterable} over the stack of open elements
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>
     */
    public Iterable<Element> getOpenElementsIterable() {
        return stackOfOpenElements;
    }
    
    /**
     * Adds the given element to the stack of open elements. This will be the
     * new current open element.
     * 
     * @param element the element to add to the stack of open elements
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#current-node">current node</a>
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>
     */
    public void pushOpenElement(final Element element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null.");
        }
        stackOfOpenElements.add(element);
    }
    
    /**
     * Removes and returns the current open element from the stack of open
     * elements.
     * 
     * @return the current open element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#current-node">current node</a>
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>
     */
    public Element popOpenElement() {
        return stackOfOpenElements.remove(getNumOpenElements() - 1);
    }
    
    /**
     * Retrieves, but does not remove, the current open element.
     * 
     * @return the current open element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#current-node">current node</a>
     */
    public Element getCurrentOpenElement() {
        return getOpenElement(getNumOpenElements() - 1);
    }
    
    /**
     * Retrieves, but does not remove, the open element at the given index.
     * Indices start at {@code 0} for the top of the stack, which is the root
     * element (the {@code html} element).
     * 
     * @param index the index of the open element to return
     * @return the open element at the given index
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>
     */
    public Element getOpenElement(final int index) {
        return stackOfOpenElements.get(index);
    }
    
    public Element setOpenElement(final int index, final Element element) {
        return stackOfOpenElements.set(index, element);
    }
    
    public void insertOpenElement(final int index, final Element element) {
        stackOfOpenElements.add(index, element);
    }
    
    public Element replaceOpenElement(final Element oldElement, final Element newElement) {
        return stackOfOpenElements.set(stackOfOpenElements.indexOf(oldElement), newElement);
    }
    
    /**
     * Returns whether the given element appears in the stack of open elements.
     * 
     * @param element the element to find
     * @return whether the given element appears in the stack of open elements
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>
     */
    public boolean containsOpenElement(final Element element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null.");
        }
        return stackOfOpenElements.contains(element);
    }
    
    public int getIndexOfOpenElement(final Element element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null.");
        }
        return stackOfOpenElements.indexOf(element);
    }
    
    /**
     * Removes the given element from the stack of open elements.
     * 
     * @param element the element to remove
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#stack-of-open-elements">stack of open elements</a>
     */
    public void removeOpenElement(final Element element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null.");
        }
        stackOfOpenElements.remove(element);
    }
    
    /**
     * @return the adjusted current {@link org.w3c.dom.Node}
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#adjusted-current-node">adjusted current node</a>
     */
    public Element getAdjustedCurrentNode() {
        if (getNumOpenElements() == 1 && isHTMLFragmentParsingAlgorithm()) {
            throw new UnsupportedOperationException();
        } else {
            return getCurrentOpenElement();
        }
    }
    
    public boolean isActiveFormattingElementsContains(final Element element) {
        for (final FormattingElement formattingElement : listOfActiveFormattingElements) {
            if ( !isMarker(formattingElement)) {
                if (formattingElement.getValue() == element) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static class StartTokenComparable {
        
        private final String tagName;
        
        private final String namespace;
        
        private final Map<String, String> attributes;
        
        public StartTokenComparable(final StartTagToken startTagToken, final Element element) {
            super();
            this.tagName = element.getTagName();
            this.namespace = element.getNamespaceURI();
            this.attributes = new HashMap<>();
            for (final TagToken.Attribute attr : startTagToken.getAttributes()) {
                this.attributes.put(attr.getName(), attr.getValue());
            }
        }
        
        @Override
        public boolean equals(final Object object) {
            if (object instanceof StartTokenComparable) {
                final StartTokenComparable other = (StartTokenComparable) object;
                return tagName.equals(other.tagName) && namespace.equals(other.namespace)
                        && attributes.equals(other.attributes);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return tagName.hashCode() ^ namespace.hashCode() ^ attributes.hashCode();
        }
        
    }
    
    public void addMarkerToListOfActiveFormattingElements() {
        listOfActiveFormattingElements.add(getMarker());
    }
    
    /**
     * Pushes an element onto the list of active formatting elements.
     * <p>
     * When the steps below require the UA to push onto the list of active
     * formatting elements an element <var>element</var>, the UA must perform
     * the following steps:
     * <ol>
     *   <li>If there are already three elements in the list of active
     *     formatting elements after the last list marker, if any, or anywhere
     *     in the list if there are no list markers, that have the same tag
     *     name, namespace, and attributes as <var>element</var>, then remove
     *     the earliest such element from the list of active formatting
     *     elements. For these purposes, the attributes must be compared as they
     *     were when the elements were created by the parser; two elements have
     *     the same attributes if all their parsed attributes can be paired such
     *     that the two attributes in each pair have identical names,
     *     namespaces, and values (the order of the attributes does not matter).</li>
     *   <li>Add <var>element</var> to the list of active formatting elements.</li>
     * </ol>
     * 
     * @param startTagToken the start tag for the new element
     * @param element the new element
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#push-onto-the-list-of-active-formatting-elements">push onto the list of active formatting elements</a>
     */
    public void pushOntoListOfActiveFormattingElements(final StartTagToken startTagToken, final Element element) {
        final StartTokenComparable expected = new StartTokenComparable(startTagToken, element);
        int count = 0;
        final ListIterator<FormattingElement> iter =
                listOfActiveFormattingElements.listIterator(listOfActiveFormattingElements.size());
        while (iter.hasPrevious()) {
            final FormattingElement previous = iter.previous();
            if (isMarker(previous)) {
                iter.next();
                break;
            }
            final StartTokenComparable actual = new StartTokenComparable(previous.getKey(), previous.getValue());
            if (expected.equals(actual)) {
                count++ ;
            }
        }
        if (count >= 3) {
            while (iter.hasNext()) {
                final FormattingElement next = iter.next();
                if (isMarker(next)) {
                    continue;
                }
                final StartTokenComparable actual = new StartTokenComparable(next.getKey(), next.getValue());
                if (expected.equals(actual)) {
                    iter.remove();
                    break;
                }
            }
        }
        listOfActiveFormattingElements.add(new FormattingElement(startTagToken, element));
    }
    
    /**
     * Returns whether this element is a marker in the list of active formatting
     * elements.
     * 
     * @param element the element to check
     * @return {@code true} if the element is a marker
     */
    public boolean isMarker(final FormattingElement element) {
        return element == null;
    }
    
    private FormattingElement getMarker() {
        return null;
    }
    
    /**
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#clear-the-list-of-active-formatting-elements-up-to-the-last-marker">clear the list of active formatting elements up to the last marker</a>
     */
    public void clearActiveFormattingElements() {
        FormattingElement popped;
        do {
            popped = listOfActiveFormattingElements.remove(listOfActiveFormattingElements.size() - 1);
        } while ( !isMarker(popped) && !listOfActiveFormattingElements.isEmpty());
    }
    
    public void pushTemplateInsertionMode(final Mode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("Mode cannot be null.");
        }
        stackOfTemplateInsertionModes.add(mode);
    }
    
    public Mode popTemplateInsertionMode() {
        return stackOfTemplateInsertionModes.remove(getNumTemplateInsertionModes() - 1);
    }
    
    public Mode getCurrentTemplateInsertionMode() {
        return stackOfTemplateInsertionModes.get(getNumTemplateInsertionModes() - 1);
    }
    
    public int getNumTemplateInsertionModes() {
        return stackOfTemplateInsertionModes.size();
    }
    
    public void setTokenizerState(final Tokenizer.State state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null.");
        }
        tokenizer.setState(state);
    }
    
    public Token getNextToken() throws IOException {
        return tokenizer.getNextToken();
    }
    
    /**
     * @param element the element to check
     * @return {@code true} if it is a MathML integration point
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#mathml-text-integration-point">MathML text integration point</a>
     */
    public boolean isMathMLTextIntegrationPoint(final Element element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        final String namespace = element.getNamespaceURI();
        final String tagName = element.getTagName();
        if (InsertionMode.MATHML_NAMESPACE.equals(namespace)) {
            if ("mi".equals(tagName)) {
                return true;
            }
            if ("mo".equals(tagName)) {
                return true;
            }
            if ("mn".equals(tagName)) {
                return true;
            }
            if ("ms".equals(tagName)) {
                return true;
            }
            if ("mtext".equals(tagName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param element the element to check
     * @return {@code true} if it is an HTML integration point
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#html-integration-point">HTML integration point</a>
     */
    public boolean isHTMLIntegrationPoint(final Element element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        final String namespace = element.getNamespaceURI();
        final String tagName = element.getTagName();
        final String encodingAttribute = element.getAttribute("encoding");
        
        if (InsertionMode.MATHML_NAMESPACE.equals(namespace) && "annotation-xml".equals(tagName)
                && "text/html".equalsIgnoreCase(encodingAttribute)) {
            return true;
        }
        if (InsertionMode.MATHML_NAMESPACE.equals(namespace) && "annotation-xml".equals(tagName)
                && "application/xhtml+xml".equalsIgnoreCase(encodingAttribute)) {
            return true;
        }
        if (InsertionMode.SVG_NAMESPACE.equals(namespace) && "foreignObject".equals(tagName)) {
            return true;
        }
        if (InsertionMode.SVG_NAMESPACE.equals(namespace) && "desc".equals(tagName)) {
            return true;
        }
        if (InsertionMode.SVG_NAMESPACE.equals(namespace) && "title".equals(tagName)) {
            return true;
        }
        return false;
    }
    
    /**
     * Dispatch the next token to the current mode. This is prototype code and
     * not used.
     * 
     * @param token the token to dispatch
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#tree-construction-dispatcher">tree construction dispatcher</a>
     */
    protected boolean treeConstructionDispatcher(final Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        final Element adjustedCurrentNode = getAdjustedCurrentNode();
        
        if (adjustedCurrentNode == null) {
            return processUsingRulesFor(insertionMode, token);
        }
        
        final String namespace = adjustedCurrentNode.getNamespaceURI();
        
        if (InsertionMode.HTML_NAMESPACE.equals(namespace)) {
            return processUsingRulesFor(insertionMode, token);
        }
        
        if (isMathMLTextIntegrationPoint(adjustedCurrentNode) && token.getType() == Token.Type.START_TAG) {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            if ( !"mglyph".equals(tagName) && !"malignmark".equals(tagName)) {
                return processUsingRulesFor(insertionMode, token);
            }
        }
        
        if (isMathMLTextIntegrationPoint(adjustedCurrentNode) && token.getType() == Token.Type.CHARACTER) {
            return processUsingRulesFor(insertionMode, token);
        }
        
        if (InsertionMode.MATHML_NAMESPACE.equals(namespace)
                && "annotation-xml".equals(adjustedCurrentNode.getTagName())
                && token.getType() == Token.Type.START_TAG) {
            final StartTagToken startTagToken = (StartTagToken) token;
            final String tagName = startTagToken.getTagName();
            if ("svg".equals(tagName)) {
                return processUsingRulesFor(insertionMode, token);
            }
        }
        
        if (isHTMLIntegrationPoint(adjustedCurrentNode) && token.getType() == Token.Type.START_TAG) {
            return processUsingRulesFor(insertionMode, token);
        }
        
        if (isHTMLIntegrationPoint(adjustedCurrentNode) && token.getType() == Token.Type.CHARACTER) {
            return processUsingRulesFor(insertionMode, token);
        }
        
        if (token.getType() == Token.Type.EOF) {
            return processUsingRulesFor(insertionMode, token);
        }
        
        return insertForeignContent(token);
    }
    
    private boolean insertForeignContent(final Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        return foreignContentMode.insert(token);
    }
    
    /**
     * Acknowledge the self-closing flag.
     * <p>
     * When a start tag token is emitted with its self-closing flag set, if the flag is not acknowledged when it is processed by the tree construction stage, that is a parse error.
     * 
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#acknowledge-self-closing-flag">acknowledge the token&#x27;s self-closing flag</a>
     */
    public void acknowledgeSelfClosingFlag() {
        tokenizer.acknowledgeSelfClosingFlag();
    }
    
    public void emitToken() throws IOException {
        final Token token = getNextToken();
        int count = 0;
        boolean accepted;
        do {
//            accepted = treeConstructionDispatcher(token);
            accepted = processUsingRulesFor(insertionMode, token);
            if (count++ > 1024) {
                System.out.println(count + " : " + token);
                throw new ParseErrorException("Too many stack frames emitting token in parser.");
            }
        } while ( !accepted);
//        if (count > 1) {
//            System.out.println(count + " : " + token);
//        } else {
//            System.out.println(token);
//        }
    }
    
    /**
     * Process the token using the rules for the given insertion mode. This is
     * used when one insertion mode delegates to another insertion mode.
     * 
     * @param mode the mode to use to process the token
     * @param token the token to process
     * @return whether the token was handled. {@code false} means the token
     *         needs to be passed to the next insertion mode.
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#using-the-rules-for">using the rules for</a>
     */
    public boolean processUsingRulesFor(final Mode mode, final Token token) {
        if (mode == null) {
            throw new IllegalArgumentException("Mode cannot be null");
        }
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        final InsertionMode modeHandler = insertionModeMap.get(mode);
        return modeHandler.insert(token);
    }
    
    public Document parse() {
        try {
            while ( !stop) {
                emitToken();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return document;
    }
    
}
