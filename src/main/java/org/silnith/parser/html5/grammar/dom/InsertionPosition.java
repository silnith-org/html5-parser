package org.silnith.parser.html5.grammar.dom;

import org.w3c.dom.Node;

/**
 * A generic place that a node can be inserted.
 * 
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#appropriate-place-for-inserting-a-node">appropriate place for inserting a node</a>
 */
public interface InsertionPosition {
    
    Node getContainingNode();
    
    Node getNodeImmediatelyBefore();
    
    void insert(Node node);
    
}
