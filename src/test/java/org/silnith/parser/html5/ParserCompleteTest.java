package org.silnith.parser.html5;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.silnith.parser.html5.lexical.Tokenizer;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;


public class ParserCompleteTest {
    
    private static DOMImplementationRegistry registry;
    
    private DOMImplementation domImplementation;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        registry = DOMImplementationRegistry.newInstance();
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        registry = null;
    }
    
    @Before
    public void setUp() throws Exception {
        domImplementation = registry.getDOMImplementation("Core 2.0");
    }
    
    @After
    public void tearDown() throws Exception {
        domImplementation = null;
    }
    
    @Test
    public void testEmpty() {
        final InputStreamReader reader = new InputStreamReader(ParserCompleteTest.class.getResourceAsStream("empty.html"), Charset.forName("UTF-8"));
        final Tokenizer tokenizer = new Tokenizer(reader);
        final Parser parser = new Parser(tokenizer, domImplementation);
        
        final Document document = parser.parse();
        
        assertNotNull(document);
    }
    
}
