package org.silnith.parser.html5.lexical;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;

import java.io.IOException;
import java.io.Reader;


/**
 * Preprocesses the input stream.
 * <p>
 * The input stream consists of the characters pushed into it as the input byte stream is decoded or from the various APIs that directly manipulate the input stream.
 * <p>
 * One leading U+FEFF BYTE ORDER MARK character must be ignored if any are present in the input stream.
 * <p>
 * Any occurrences of any characters in the ranges U+0001 to U+0008, U+000E to U+001F, U+007F to U+009F, U+FDD0 to U+FDEF, and characters U+000B, U+FFFE, U+FFFF, U+1FFFE, U+1FFFF, U+2FFFE, U+2FFFF, U+3FFFE, U+3FFFF, U+4FFFE, U+4FFFF, U+5FFFE, U+5FFFF, U+6FFFE, U+6FFFF, U+7FFFE, U+7FFFF, U+8FFFE, U+8FFFF, U+9FFFE, U+9FFFF, U+AFFFE, U+AFFFF, U+BFFFE, U+BFFFF, U+CFFFE, U+CFFFF, U+DFFFE, U+DFFFF, U+EFFFE, U+EFFFF, U+FFFFE, U+FFFFF, U+10FFFE, and U+10FFFF are parse errors. These are all control characters or permanently undefined Unicode characters (noncharacters).
 * <p>
 * Any character that is a not a Unicode character, i.e. any isolated surrogate, is a parse error. (These can only find their way into the input stream via script APIs such as document.write().)
 * <p>
 * "CR" (U+000D) characters and "LF" (U+000A) characters are treated specially. All CR characters must be converted to LF characters, and any LF characters that immediately follow a CR character must be ignored. Thus, newlines in HTML DOMs are represented by LF characters, and there are never any CR characters in the input to the tokenization stage.
 * <p>
 * The next input character is the first character in the input stream that has not yet been consumed or explicitly ignored by the requirements in this section. Initially, the next input character is the first character in the input. The current input character is the last character to have been consumed.
 * <p>
 * The insertion point is the position (just before a character or just before the end of the input stream) where content inserted using document.write() is actually inserted. The insertion point is relative to the position of the character immediately after it, it is not an absolute offset into the input stream. Initially, the insertion point is undefined.
 * <p>
 * The "EOF" character in the tables below is a conceptual character representing the end of the input stream. If the parser is a script-created parser, then the end of the input stream is reached when an explicit "EOF" character (inserted by the document.close() method) is consumed. Otherwise, the "EOF" character is not a real character in the stream, but rather the lack of any further characters.
 *
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#preprocessing-the-input-stream">8.2.2.5 Preprocessing the input stream</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
public class InputStreamPreprocessor extends Reader {
    
    private final Reader in;
    
    private boolean skipNextLineFeed;
    
    public InputStreamPreprocessor(final Reader in) {
        super(in);
        this.in = in;
        this.skipNextLineFeed = false;
    }
    
    @Override
    public int read() throws IOException {
        final int ch = in.read();
        switch (ch) {
        case -1: {
            skipNextLineFeed = false;
            return -1;
        } // break;
        case CARRIAGE_RETURN: {
            skipNextLineFeed = true;
            return LINE_FEED;
        } // break;
        case LINE_FEED: {
            if (skipNextLineFeed) {
                skipNextLineFeed = false;
                return in.read();
            } else {
                return LINE_FEED;
            }
        } // break;
        default: {
            return ch;
        } // break;
        }
    }
    
    @Override
    public int read(final char[] cbuf) throws IOException {
        if (cbuf.length == 0) {
            return 0;
        }
        int ch = read();
        if (ch == -1) {
            return -1;
        }
        int count = 1;
        cbuf[0] = (char) ch;
        while (count < cbuf.length && ready()) {
            ch = read();
            if (ch == -1) {
                break;
            }
            cbuf[count] = (char) ch;
            count++ ;
        }
        return count;
    }
    
    @Override
    public int read(final char[] cbuf, final int off, final int len) throws IOException {
        if (len < 0) {
            throw new IllegalArgumentException();
        }
        if (len == 0) {
            return 0;
        }
        int ch = read();
        if (ch == -1) {
            return -1;
        }
        int count = 1;
        cbuf[off] = (char) ch;
        while (count < len && ready()) {
            ch = read();
            if (ch == -1) {
                break;
            }
            cbuf[off + count] = (char) ch;
            count++ ;
        }
        return count;
    }
    
    @Override
    public long skip(final long n) throws IOException {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        long count = 0;
        while (count < n) {
            final int ch = read();
            if (ch == -1) {
                break;
            }
            count++ ;
        }
        return count;
    }
    
    @Override
    public boolean ready() throws IOException {
        return in.ready();
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public void mark(final int readAheadLimit) throws IOException {
        throw new IOException("Mark not supported.");
    }
    
    @Override
    public void reset() throws IOException {
        in.reset();
        skipNextLineFeed = false;
    }
    
    @Override
    public void close() throws IOException {
        in.close();
    }
    
}
