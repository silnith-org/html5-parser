package org.silnith.parser.html5.grammar.dom;

import org.w3c.dom.Node;

/**
 * 
 * 
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#appropriate-place-for-inserting-a-node">appropriate place for inserting a node</a>
 */
public class AfterLastChildInsertionPosition implements InsertionPosition {
    
    private final Node parentNode;
    
    public AfterLastChildInsertionPosition(final Node parentNode) {
        super();
        this.parentNode = parentNode;
    }
    
    @Override
    public Node getContainingNode() {
        return parentNode;
    }
    
    @Override
    public Node getNodeImmediatelyBefore() {
        return parentNode.getLastChild();
    }
    
    @Override
    public void insert(final Node node) {
        parentNode.appendChild(node);
    }
    
}
