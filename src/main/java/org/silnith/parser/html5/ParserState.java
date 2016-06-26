package org.silnith.parser.html5;

import java.util.ArrayList;
import java.util.List;

import org.silnith.parser.html5.Parser.FormattingElement;
import org.w3c.dom.Element;

/**
 * Contains all of the state associated with a specific parser instance.
 * 
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parse-state">8.2.3 Parse state</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class ParserState {
    
    private Parser.Mode insertionMode;
    
    private final List<Element> stackOfOpenElements;
    
    private final List<FormattingElement> listOfActiveFormattingElements;
    
    private Element headElementPointer;
    
    private Element formElementPointer;
    
    private boolean scriptingFlag;
    
    private boolean framesetOKFlag;
    
    public ParserState() {
        super();
        this.insertionMode = Parser.Mode.INITIAL;
        this.stackOfOpenElements = new ArrayList<>();
        this.listOfActiveFormattingElements = new ArrayList<>();
        this.headElementPointer = null;
        this.formElementPointer = null;
        this.scriptingFlag = false;
        this.framesetOKFlag = false;
    }
    
    /**
     * Gets the current insertion mode.
     * <p>
     * The insertion mode is a state variable that controls the primary operation of the tree construction stage.
     * <p>
     * Initially, the insertion mode is "initial". It can change to "before html", "before head", "in head", "in head noscript", "after head", "in body", "text", "in table", "in table text", "in caption", "in column group", "in table body", "in row", "in cell", "in select", "in select in table", "in template", "after body", "in frameset", "after frameset", "after after body", and "after after frameset" during the course of the parsing, as described in the tree construction stage. The insertion mode affects how tokens are processed and whether CDATA sections are supported.
     * <p>
     * Several of these modes, namely "in head", "in body", "in table", and "in select", are special, in that the other modes defer to them at various times. When the algorithm below says that the user agent is to do something "using the rules for the m insertion mode", where m is one of these modes, the user agent must use the rules described under the m insertion mode's section, but must leave the insertion mode unchanged unless the rules in m themselves switch the insertion mode to a new value.
     * 
     * @return the current insertion mode
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-insertion-mode">8.2.3.1 The insertion mode</a>
     */
    public Parser.Mode getInsertionMode() {
        return insertionMode;
    }
    
    /**
     * Resets the insertion mode appropriately.
     * <p>
     * When the steps below require the UA to reset the insertion mode appropriately, it means the UA must follow these steps:
     * <ol>
     *   <li>Let <var>last</var> be false.
     *   <li>Let <var>node</var> be the last node in the stack of open elements.
     *   <li><dfn>Loop</dfn>: If <var>node</var> is the first node in the stack of open elements, then set <var>last</var> to true, and, if the parser was originally created as part of the HTML fragment parsing algorithm (fragment case) set <var>node</var> to the <var>context</var> element.
     *   <li>
     *     If <var>node</var> is a <code>select</code> element, run these substeps:
     *     <ol>
     *       <li>If <var>last</var> is true, jump to the step below labeled <var>done</var>.
     *       <li>Let <var>ancestor</var> be <var>node</var>.
     *       <li><dfn>Loop</dfn>: If <var>ancestor</var> is the first node in the stack of open elements, jump to the step below labeled <var>done</var>.
     *       <li>Let <var>ancestor</var> be the node before <var>ancestor</var> in the stack of open elements.
     *       <li>If <var>ancestor</var> is a <code>template</code> node, jump to the step below labeled <var>done</var>.
     *       <li>If <var>ancestor</var> is a <code>table</code> node, switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_SELECT_IN_TABLE in select in table}" and abort these steps.
     *       <li>Jump back to the step labeled <var>loop</var>.
     *       <li><dfn>Done</dfn>: Switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_SELECT in select}" and abort these steps.
     *     </ol>
     *   </li>
     *   <li>If <var>node</var> is a <code>td</code> or <code>th</code> element and <var>last</var> is false, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_CELL in cell}" and abort these steps.
     *   <li>If <var>node</var> is a <code>tr</code> element, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_ROW in row}" and abort these steps.
     *   <li>If <var>node</var> is a <code>tbody</code>, <code>thead</code>, or <code>tfoot</code> element, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_TABLE_BODY in table body}" and abort these steps.
     *   <li>If <var>node</var> is a <code>caption</code> element, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_CAPTION in caption}" and abort these steps.
     *   <li>If <var>node</var> is a <code>colgroup</code> element, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_COLUMN_GROUP in column group}" and abort these steps.
     *   <li>If <var>node</var> is a <code>table</code> element, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_TABLE in table}" and abort these steps.
     *   <li>If <var>node</var> is a <code>template</code> element, then switch the insertion mode to the current template insertion mode and abort these steps.
     *   <li>If <var>node</var> is a <code>head</code> element and last is true, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_BODY in body}" ("{@linkplain org.silnith.parser.html5.Parser.Mode#IN_BODY in body}"! <em>not "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_HEAD in head}"</em>!) and abort these steps. (fragment case)
     *   <li>If <var>node</var> is a <code>head</code> element and last is false, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_HEAD in head}" and abort these steps.
     *   <li>If <var>node</var> is a <code>body</code> element, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_BODY in body}" and abort these steps.
     *   <li>If <var>node</var> is a <code>frameset</code> element, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_FRAMESET in frameset}" and abort these steps. (fragment case)
     *   <li>
     *     If <var>node</var> is an <code>html</code> element, run these substeps:
     *     <ol>
     *       <li>If the <code>head</code> element pointer is null, switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#BEFORE_HEAD before head}" and abort these steps. (fragment case)
     *       <li>Otherwise, the <code>head</code> element pointer is not null, switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#AFTER_HEAD after head}" and abort these steps.
     *     </ol>
     *   </li>
     *   <li>If last is true, then switch the insertion mode to "{@linkplain org.silnith.parser.html5.Parser.Mode#IN_BODY in body}" and abort these steps. (fragment case)
     *   <li>Let <var>node</var> now be the node before <var>node</var> in the stack of open elements.
     *   <li>Return to the step labeled <var>loop</var>.
     * </ol>
     * 
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#reset-the-insertion-mode-appropriately">reset the insertion mode appropriately</a>
     */
    public void resetInsertionModeAppropriately() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Gets the stack of open elements.
     * <p>Initially, the stack of open elements is empty. The stack grows downwards; the topmost node on the stack is the first one added to the stack, and the bottommost node of the stack is the most recently added node in the stack (notwithstanding when the stack is manipulated in a random access fashion as part of the handling for misnested tags).
     * <p>The html node, however it is created, is the topmost node of the stack. It only gets popped off the stack when the parser finishes.
     * <p>The current node is the bottommost node in this stack of open elements.
     * <p>The adjusted current node is the context element if the stack of open elements has only one element in it and the parser was created by the HTML fragment parsing algorithm; otherwise, the adjusted current node is the current node.
     * <p>Elements in the stack of open elements fall into the following categories:
     * <dl>
     *   <dt>Special
     *   <dd>The following elements have varying levels of special parsing rules: HTML's address, applet, area, article, aside, base, basefont, bgsound, blockquote, body, br, button, caption, center, col, colgroup, dd, details, dir, div, dl, dt, embed, fieldset, figcaption, figure, footer, form, frame, frameset, h1, h2, h3, h4, h5, h6, head, header, hgroup, hr, html, iframe, img, input, isindex, li, link, listing, main, marquee, meta, nav, noembed, noframes, noscript, object, ol, p, param, plaintext, pre, script, section, select, source, style, summary, table, tbody, td, template, textarea, tfoot, th, thead, title, tr, track, ul, wbr, and xmp; MathML's mi, mo, mn, ms, mtext, and annotation-xml; and SVG's foreignObject, desc, and title.
     *   <dt>Formatting
     *   <dd>The following HTML elements are those that end up in the list of active formatting elements: a, b, big, code, em, font, i, nobr, s, small, strike, strong, tt, and u.
     *   <dt>Ordinary
     *   <dd>All other elements found while parsing an HTML document.
     * </dl>
     * 
     * @return the stack of open elements
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-stack-of-open-elements">8.2.3.2 The stack of open elements</a>
     */
    public List<Element> getStackOfOpenElements() {
        return stackOfOpenElements;
    }
    
    /**
     * Gets the list of active formatting elements.
     * <p>Initially, the list of active formatting elements is empty. It is used to handle mis-nested formatting element tags.
     * <p>The list contains elements in the formatting category, and scope markers. The scope markers are inserted when entering applet elements, buttons, object elements, marquees, table cells, and table captions, and are used to prevent formatting from "leaking" into applet elements, buttons, object elements, marquees, and tables.
     * <p>In addition, each element in the list of active formatting elements is associated with the token for which it was created, so that further elements can be created for that token if necessary.
     * 
     * @return the list of active formatting elements
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-list-of-active-formatting-elements">8.2.3.3 The list of active formatting elements</a>
     */
    public List<FormattingElement> getListOfActiveFormattingElements() {
        return listOfActiveFormattingElements;
    }
    
    /**
     * Gets the head element pointer.
     * <p>
     * Once a <code>head</code> element has been parsed (whether implicitly or explicitly) the <code>head</code> element pointer gets set to point to this node.
     * 
     * @return the head element pointer
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-element-pointers">8.2.3.4 The element pointers</a>
     */
    public Element getHeadElementPointer() {
        return headElementPointer;
    }
    
    /**
     * Gets the form element pointer.
     * <p>
     * The <code>form</code> element pointer points to the last <code>form</code> element that was opened and whose end tag has not yet been seen. It is used to make form controls associate with forms in the face of dramatically bad markup, for historical reasons. It is ignored inside <code>template</code> elements.
     * 
     * @return the form element pointer
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#the-element-pointers">8.2.3.4 The element pointers</a>
     */
    public Element getFormElementPointer() {
        return formElementPointer;
    }
    
    /**
     * Gets the scripting flag.
     * <p>
     * The scripting flag is set to "enabled" if scripting was enabled for the <code>Document</code> with which the parser is associated when the parser was created, and "disabled" otherwise.
     * 
     * @return the scripting flag
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#other-parsing-state-flags">8.2.3.5 Other parsing state flags</a>
     */
    public boolean isScriptingFlag() {
        return scriptingFlag;
    }
    
    /**
     * Gets the frameset-ok flag.
     * <p>
     * The frameset-ok flag is set to "ok" when the parser is created. It is set to "not ok" after certain tokens are seen.
     * 
     * @return the frameset-ok flag
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#other-parsing-state-flags">8.2.3.5 Other parsing state flags</a>
     */
    public boolean isFramesetOKFlag() {
        return framesetOKFlag;
    }
    
}
