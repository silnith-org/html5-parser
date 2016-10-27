package org.silnith.parser.html5.grammar.mode;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.silnith.parser.html5.Parser;
import org.silnith.parser.html5.lexical.token.Token;


@RunWith(MockitoJUnitRunner.class)
public class InsertionModeTest {
    
    private static class FakeInsertionMode extends InsertionMode {
        
        public FakeInsertionMode(final Parser parser) {
            super(parser);
        }

        @Override
        public boolean insert(final Token token) {
            throw new UnsupportedOperationException();
        }
        
    }
    
    @Mock
    private Parser parser;
    
    private InsertionMode insertionMode;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception {
        insertionMode = new FakeInsertionMode(parser);
    }
    
    @After
    public void tearDown() throws Exception {
        insertionMode = null;
    }
    
    @Test
    public void testReconstructActiveFormattingElements() {
        // set up
        
        // method under test
        insertionMode.reconstructActiveFormattingElements();
        
        // verify
        fail("Not yet implemented");
    }
    
}
