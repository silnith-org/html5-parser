/**
 * Contains the classes responsible for handling an input stream and converting
 * it into a stream of tokens.
 * <p>
 * The input to the HTML parsing process consists of a stream of Unicode code points, which is passed through a tokenization stage followed by a tree construction stage. The output is a Document object.
 * <p>
 * In the common case, the data handled by the tokenization stage comes from the network, but it can also come from script running in the user agent, e.g. using the document.write() API.
 * <p>
 * There is only one set of states for the tokenizer stage and the tree construction stage, but the tree construction stage is reentrant, meaning that while the tree construction stage is handling one token, the tokenizer might be resumed, causing further tokens to be emitted and processed before the first token's processing is complete.
 * <p>
 * To handle these cases, parsers have a script nesting level, which must be initially set to zero, and a parser pause flag, which must be initially set to false.
 * 
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#parsing">8.2 Parsing HTML documents</a>
 * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#overview-of-the-parsing-model">8.2.1 Overview of the parsing model</a>
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
package org.silnith.parser.html5.lexical;
