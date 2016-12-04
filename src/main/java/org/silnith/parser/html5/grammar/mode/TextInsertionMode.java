package org.silnith.parser.html5.grammar.mode;

import static org.silnith.parser.util.UnicodeCodePoints.NULL;

import org.silnith.parser.ParseErrorException;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.CharacterToken;
import org.silnith.parser.html5.lexical.token.EndTagToken;
import org.silnith.parser.html5.lexical.token.Token;
import org.w3c.dom.Element;


/**
 * Applies the text insertion mode logic.
 * <p>
 * When the user agent is to apply the rules for the "text" insertion mode, the user agent must handle the token as follows:
 * <dl>
 *   <dt>A character token
 *   <dd>Insert the token's character.
 *   <dt>An end-of-file token
 *   <dd>
 *     Parse error.
 *     <p>If the current node is a script element, mark the script element as "already started".
 *     <p>Pop the current node off the stack of open elements.
 *     <p>Switch the insertion mode to the original insertion mode and reprocess the token.
 *   <dt>An end tag whose tag name is "script"
 *   <dd>
 *     Perform a microtask checkpoint.
 *     <p>Provide a stable state.
 *     <p>Let script be the current node (which will be a script element).
 *     <p>Pop the current node off the stack of open elements.
 *     <p>Switch the insertion mode to the original insertion mode.
 *     <p>Let the old insertion point have the same value as the current insertion point. Let the insertion point be just before the next input character.
 *     <p>Increment the parser's script nesting level by one.
 *     <p>Prepare the script. This might cause some script to execute, which might cause new characters to be inserted into the tokenizer, and might cause the tokenizer to output more tokens, resulting in a reentrant invocation of the parser.
 *     <p>Decrement the parser's script nesting level by one. If the parser's script nesting level is zero, then set the parser pause flag to false.
 *     <p>Let the insertion point have the value of the old insertion point. (In other words, restore the insertion point to its previous value. This value might be the "undefined" value.)
 *     <p>At this stage, if there is a pending parsing-blocking script, then:
 *     <dl>
 *       <dt>If the script nesting level is not zero:
 *       <dd>
 *         Set the parser pause flag to true, and abort the processing of any nested invocations of the tokenizer, yielding control back to the caller. (Tokenization will resume when the caller returns to the "outer" tree construction stage.)
 *         <p>The tree construction stage of this particular parser is being called reentrantly, say from a call to document.write().
 *       <dt>Otherwise:
 *       <dd>
 *         Run these steps:
 *         <ol>
 *           <li>Let the script be the pending parsing-blocking script. There is no longer a pending parsing-blocking script.
 *           <li>Block the tokenizer for this instance of the HTML parser, such that the event loop will not run tasks that invoke the tokenizer.
 *           <li>If the parser's Document has a style sheet that is blocking scripts or the script's "ready to be parser-executed" flag is not set: spin the event loop until the parser's Document has no style sheet that is blocking scripts and the script's "ready to be parser-executed" flag is set.
 *           <li>If this parser has been aborted in the meantime, abort these steps.
 *           <li>Unblock the tokenizer for this instance of the HTML parser, such that tasks that invoke the tokenizer can again be run.
 *           <li>Let the insertion point be just before the next input character.
 *           <li>Increment the parser's script nesting level by one (it should be zero before this step, so this sets it to one).
 *           <li>Execute the script.
 *           <li>Decrement the parser's script nesting level by one. If the parser's script nesting level is zero (which it always should be at this point), then set the parser pause flag to false.
 *           <li>Let the insertion point be undefined again.
 *           <li>If there is once again a pending parsing-blocking script, then repeat these steps from step 1.
 *         </ol>
 *     </dl>
 *   <dt>Any other end tag
 *   <dd>
 *     Pop the current node off the stack of open elements.
 *     <p>Switch the insertion mode to the original insertion mode.
 * </dl>
 * 
 * @see org.silnith.parser.html5.Parser.Mode#TEXT
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing-main-incdata">8.2.5.4.8 The "text" insertion mode</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class TextInsertionMode extends InsertionMode {
    
    public TextInsertionMode(final Parser parser) {
        super(parser);
    }
    
    @Override
    public boolean insert(final Token token) {
        switch (token.getType()) {
        case CHARACTER: {
            final CharacterToken characterToken = (CharacterToken) token;
            assert characterToken.getCharacter() != NULL;
            insertCharacter(characterToken);
            return true;
        } // break;
        case EOF: {
            if (isAllowParseErrors()) {
                // if current element is a script, mark it as "already started"
                popCurrentNode();
                setInsertionMode(getOriginalInsertionMode());
                return REPROCESS_TOKEN;
            } else {
                throw new ParseErrorException("Unexpected end-of-file in text inserting mode.");
            }
        } // break;
        case END_TAG: {
            final EndTagToken endTagToken = (EndTagToken) token;
            final String tagName = endTagToken.getTagName();
            switch (tagName) {
            case "script": {
                // do lots and lots and lots of shit
                // if stack of script settings is empty, perform a microtask
// checkpoint
                // if stack of script settings is empty, provide a stable state
                final Element script = getCurrentNode();
                assert isElementA(script, "script");
                popCurrentNode();
                setInsertionMode(getOriginalInsertionMode());
//                InsertionPosition oldInsertionPosition = current insertion position
                // increment script nesting level
                // prepare script
                // decrement script nesting level
                // set insertion point to old insertion point
                // if pending parsing-blocking script, do stuff
                return TOKEN_HANDLED;
            } // break;
            default: {
                popCurrentNode();
                setInsertionMode(getOriginalInsertionMode());
                return true;
            } // break;
            }
        } // break;
        default: {
            return defaultCase(token);
        } // break;
        }
    }
    
    private boolean defaultCase(final Token token) {
        throw new ParseErrorException("Unexpected token in text insertion mode: " + token);
//        return false;
    }
    
}
