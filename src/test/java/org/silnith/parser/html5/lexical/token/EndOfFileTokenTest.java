package org.silnith.parser.html5.lexical.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public class EndOfFileTokenTest {
    
    private EndOfFileToken endOfFileToken;
    
    @Test
    public void testGetType() {
        endOfFileToken = new EndOfFileToken();
        
        assertEquals(Token.Type.EOF, endOfFileToken.getType());
    }
    
    @Test
    public void testEndOfFileToken() {
        endOfFileToken = new EndOfFileToken();
        
        assertNotNull(endOfFileToken);
    }
    
}
