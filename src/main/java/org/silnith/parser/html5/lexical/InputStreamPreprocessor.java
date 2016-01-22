package org.silnith.parser.html5.lexical;

import static org.silnith.parser.util.UnicodeCodePoints.CARRIAGE_RETURN;
import static org.silnith.parser.util.UnicodeCodePoints.LINE_FEED;

import java.io.IOException;
import java.io.Reader;

/**
 * This class handles preprocessing the input stream as described in <a href=
 * "http://www.w3.org/TR/html5/syntax.html#preprocessing-the-input-stream"
 * >8.2.2.5 Preprocessing the input stream</a>.
 *
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 * @see <a
 *      href="http://www.w3.org/TR/html5/syntax.html#preprocessing-the-input-stream">8.2.2.5
 *      Preprocessing the input stream</a>
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
            count++;
        }
        return count;
    }
    
    @Override
    public int read(final char[] cbuf, final int off, final int len)
            throws IOException {
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
            count++;
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
            count++;
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
