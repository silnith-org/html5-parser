package org.silnith.parser.html5.lexical.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CommentTokenTest {
    
    private CommentToken commentToken;
    
    @Test
    public void testGetType() {
        commentToken = new CommentToken();
        
        assertEquals(Token.Type.COMMENT, commentToken.getType());
    }
    
    @Test
    public void testCommentToken() {
        commentToken = new CommentToken();
        
        assertNotNull(commentToken);
    }
    
    @Test
    public void testCommentTokenStringEmpty() {
        commentToken = new CommentToken("");
        
        assertNotNull(commentToken);
    }
    
    @Test
    public void testCommentTokenStringNotEmpty() {
        commentToken = new CommentToken("abc 123");
        
        assertNotNull(commentToken);
    }
    
    @Test(expected = NullPointerException.class)
    public void testCommentTokenStringNull() {
        new CommentToken(null);
    }
    
    @Test
    public void testAppendChar() {
        commentToken = new CommentToken();
        
        commentToken.append('a');
        
        assertEquals("a", commentToken.getContent());
    }
    
    @Test
    public void testAppendCharZero() {
        commentToken = new CommentToken();
        
        commentToken.append((char) 0);
        
        assertEquals("\u0000", commentToken.getContent());
    }
    
    @Test
    public void testAppendCharReplacementCharacter() {
        commentToken = new CommentToken();
        
        commentToken.append('\ufffd');
        
        assertEquals("\ufffd", commentToken.getContent());
    }
    
    @Test
    public void testAppendCharSurrogatePair() {
        commentToken = new CommentToken();
        
        commentToken.append('\u2267');
        commentToken.append('\u0338');
        
        assertEquals("\u2267\u0338", commentToken.getContent());
    }
    
    @Test
    public void testAppendCharArrayEmpty() {
        commentToken = new CommentToken();
        
        commentToken.append(new char[0]);
        
        assertEquals("", commentToken.getContent());
    }
    
    @Test
    public void testAppendCharArraySingle() {
        commentToken = new CommentToken();
        
        commentToken.append(new char[] { 'a' });
        
        assertEquals("a", commentToken.getContent());
    }
    
    @Test
    public void testAppendCharArraySurrogatePair() {
        commentToken = new CommentToken();
        
        commentToken.append(new char[] { '\u2267', '\u0338' });
        
        assertEquals("\u2267\u0338", commentToken.getContent());
    }
    
    @Test
    public void testAppendCharArrayShorthandSurrogatePair() {
        commentToken = new CommentToken();
        
        commentToken.append('\u2267', '\u0338');
        
        assertEquals("\u2267\u0338", commentToken.getContent());
    }
    
    @Test
    public void testGetContentDefault() {
        commentToken = new CommentToken();
        
        assertEquals("", commentToken.getContent());
    }
    
    @Test
    public void testGetContentEmpty() {
        commentToken = new CommentToken("");
        
        assertEquals("", commentToken.getContent());
    }
    
    @Test
    public void testGetContentFull() {
        commentToken = new CommentToken("abc 123");
        
        assertEquals("abc 123", commentToken.getContent());
    }
    
}
