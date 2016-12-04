package org.silnith.parser.html5.lexical.state;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Contains collections of entity reference names, disallowed characters, and
 * characters that should be mapped to replacements.
 *
 * @author <a href="mailto:silnith@gmail.com">Kent Rosenkoetter</a>
 */
/* package */class CharacterReferenceData {
    
    /**
     * The character replacement map.
     * <p>
     * If that number is one of the numbers in the first column of the following table, then this is a parse error. Find the row with that number in the first column, and return a character token for the Unicode character given in the second column of that row.
     * <table>
     *   <thead>
     *     <tr>
     *       <th>Number
     *       <th colspan="2">Unicode character
     *   <tbody>
     *     <tr>
     *       <td>0x00
     *       <td>U+FFFD
     *       <td>REPLACEMENT CHARACTER
     *     <tr>
     *       <td>0x80
     *       <td>U+20AC
     *       <td>EURO SIGN (€)
     *     <tr>
     *       <td>0x82
     *       <td>U+201A
     *       <td>SINGLE LOW-9 QUOTATION MARK (‚)
     *     <tr>
     *       <td>0x83
     *       <td>U+0192
     *       <td>LATIN SMALL LETTER F WITH HOOK (ƒ)
     *     <tr>
     *       <td>0x84
     *       <td>U+201E
     *       <td>DOUBLE LOW-9 QUOTATION MARK („)
     *     <tr>
     *       <td>0x85
     *       <td>U+2026
     *       <td>HORIZONTAL ELLIPSIS (…)
     *     <tr>
     *       <td>0x86
     *       <td>U+2020
     *       <td>DAGGER (†)
     *     <tr>
     *       <td>0x87
     *       <td>U+2021
     *       <td>DOUBLE DAGGER (‡)
     *     <tr>
     *       <td>0x88
     *       <td>U+02C6
     *       <td>MODIFIER LETTER CIRCUMFLEX ACCENT (ˆ)
     *     <tr>
     *       <td>0x89
     *       <td>U+2030
     *       <td>PER MILLE SIGN (‰)
     *     <tr>
     *       <td>0x8A
     *       <td>U+0160
     *       <td>LATIN CAPITAL LETTER S WITH CARON (Š)
     *     <tr>
     *       <td>0x8B
     *       <td>U+2039
     *       <td>SINGLE LEFT-POINTING ANGLE QUOTATION MARK (‹)
     *     <tr>
     *       <td>0x8C
     *       <td>U+0152
     *       <td>LATIN CAPITAL LIGATURE OE (Œ)
     *     <tr>
     *       <td>0x8E
     *       <td>U+017D
     *       <td>LATIN CAPITAL LETTER Z WITH CARON (Ž)
     *     <tr>
     *       <td>0x91
     *       <td>U+2018
     *       <td>LEFT SINGLE QUOTATION MARK (‘)
     *     <tr>
     *       <td>0x92
     *       <td>U+2019
     *       <td>RIGHT SINGLE QUOTATION MARK (’)
     *     <tr>
     *       <td>0x93
     *       <td>U+201C
     *       <td>LEFT DOUBLE QUOTATION MARK (“)
     *     <tr>
     *       <td>0x94
     *       <td>U+201D
     *       <td>RIGHT DOUBLE QUOTATION MARK (”)
     *     <tr>
     *       <td>0x95
     *       <td>U+2022
     *       <td>BULLET (•)
     *     <tr>
     *       <td>0x96
     *       <td>U+2013
     *       <td>EN DASH (–)
     *     <tr>
     *       <td>0x97
     *       <td>U+2014
     *       <td>EM DASH (—)
     *     <tr>
     *       <td>0x98
     *       <td>U+02DC
     *       <td>SMALL TILDE (˜)
     *     <tr>
     *       <td>0x99
     *       <td>U+2122
     *       <td>TRADE MARK SIGN (™)
     *     <tr>
     *       <td>0x9A
     *       <td>U+0161
     *       <td>LATIN SMALL LETTER S WITH CARON (š)
     *     <tr>
     *       <td>0x9B
     *       <td>U+203A
     *       <td>SINGLE RIGHT-POINTING ANGLE QUOTATION MARK (›)
     *     <tr>
     *       <td>0x9C
     *       <td>U+0153
     *       <td>LATIN SMALL LIGATURE OE (œ)
     *     <tr>
     *       <td>0x9E
     *       <td>U+017E
     *       <td>LATIN SMALL LETTER Z WITH CARON (ž)
     *     <tr>
     *       <td>0x9F
     *       <td>U+0178
     *       <td>LATIN CAPITAL LETTER Y WITH DIAERESIS (Ÿ)
     * </table>
     * <p>
     * Otherwise, if the number is in the range 0xD800 to 0xDFFF or is greater than 0x10FFFF, then this is a parse error. Return a U+FFFD REPLACEMENT CHARACTER character token.
     * <p>
     * Otherwise, return a character token for the Unicode character whose code point is that number. Additionally, if the number is in the range 0x0001 to 0x0008, 0x000D to 0x001F, 0x007F to 0x009F, 0xFDD0 to 0xFDEF, or is one of 0x000B, 0xFFFE, 0xFFFF, 0x1FFFE, 0x1FFFF, 0x2FFFE, 0x2FFFF, 0x3FFFE, 0x3FFFF, 0x4FFFE, 0x4FFFF, 0x5FFFE, 0x5FFFF, 0x6FFFE, 0x6FFFF, 0x7FFFE, 0x7FFFF, 0x8FFFE, 0x8FFFF, 0x9FFFE, 0x9FFFF, 0xAFFFE, 0xAFFFF, 0xBFFFE, 0xBFFFF, 0xCFFFE, 0xCFFFF, 0xDFFFE, 0xDFFFF, 0xEFFFE, 0xEFFFF, 0xFFFFE, 0xFFFFF, 0x10FFFE, or 0x10FFFF, then this is a parse error.
     * 
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#tokenizing-character-references">8.2.4.69 Tokenizing character references</a>
     */
    /* package */static final Map<Integer, Character> replacementMap;
    
    /**
     * The disallowed characters.  These must be considered parse errors.
     * <p>
     * Any occurrences of any characters in the ranges U+0001 to U+0008, U+000E to U+001F, U+007F to U+009F, U+FDD0 to U+FDEF, and characters U+000B, U+FFFE, U+FFFF, U+1FFFE, U+1FFFF, U+2FFFE, U+2FFFF, U+3FFFE, U+3FFFF, U+4FFFE, U+4FFFF, U+5FFFE, U+5FFFF, U+6FFFE, U+6FFFF, U+7FFFE, U+7FFFF, U+8FFFE, U+8FFFF, U+9FFFE, U+9FFFF, U+AFFFE, U+AFFFF, U+BFFFE, U+BFFFF, U+CFFFE, U+CFFFF, U+DFFFE, U+DFFFF, U+EFFFE, U+EFFFF, U+FFFFE, U+FFFFF, U+10FFFE, and U+10FFFF are parse errors. These are all control characters or permanently undefined Unicode characters (noncharacters).
     * 
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#preprocessing-the-input-stream">8.2.2.5 Preprocessing the input stream</a>
     */
    /* package */static final Set<Integer> disallowedCharacters;
    
    /**
     * The map of named character references.
     * <p>This table lists the character reference names that are supported by HTML, and the code points to which they refer. It is referenced by the previous sections.</p>
     * <table>
     *   <thead>
     *     <tr><th>Name</th><th>Character(s)</th><th>Glyph</th></tr>
     *   </thead>
     *   <tbody>
     *     <tr><td><code>Aacute;</code></td><td>U+000C1</td><td>Á</td></tr>
     *     <tr><td><code>Aacute</code></td><td>U+000C1</td><td>Á</td></tr>
     *     <tr><td><code>aacute;</code></td><td>U+000E1</td><td>á</td></tr>
     *     <tr><td><code>aacute</code></td><td>U+000E1</td><td>á</td></tr>
     *     <tr><td><code>Abreve;</code></td><td>U+00102</td><td>Ă</td></tr>
     *     <tr><td><code>abreve;</code></td><td>U+00103</td><td>ă</td></tr>
     *     <tr><td><code>ac;</code></td><td>U+0223E</td><td>∾</td></tr>
     *     <tr><td><code>acd;</code></td><td>U+0223F</td><td>∿</td></tr>
     *     <tr><td><code>acE;</code></td><td>U+0223E U+00333</td><td>∾̳</td></tr>
     *     <tr><td><code>Acirc;</code></td><td>U+000C2</td><td>Â</td></tr>
     *     <tr><td><code>Acirc</code></td><td>U+000C2</td><td>Â</td></tr>
     *     <tr><td><code>acirc;</code></td><td>U+000E2</td><td>â</td></tr>
     *     <tr><td><code>acirc</code></td><td>U+000E2</td><td>â</td></tr>
     *     <tr><td><code>acute;</code></td><td>U+000B4</td><td>´</td></tr>
     *     <tr><td><code>acute</code></td><td>U+000B4</td><td>´</td></tr>
     *     <tr><td><code>Acy;</code></td><td>U+00410</td><td>А</td></tr>
     *     <tr><td><code>acy;</code></td><td>U+00430</td><td>а</td></tr>
     *     <tr><td><code>AElig;</code></td><td>U+000C6</td><td>Æ</td></tr>
     *     <tr><td><code>AElig</code></td><td>U+000C6</td><td>Æ</td></tr>
     *     <tr><td><code>aelig;</code></td><td>U+000E6</td><td>æ</td></tr>
     *     <tr><td><code>aelig</code></td><td>U+000E6</td><td>æ</td></tr>
     *     <tr><td><code>af;</code></td><td>U+02061</td><td>⁡</td></tr>
     *     <tr><td><code>Afr;</code></td><td>U+1D504</td><td>𝔄</td></tr>
     *     <tr><td><code>afr;</code></td><td>U+1D51E</td><td>𝔞</td></tr>
     *     <tr><td><code>Agrave;</code></td><td>U+000C0</td><td>À</td></tr>
     *     <tr><td><code>Agrave</code></td><td>U+000C0</td><td>À</td></tr>
     *     <tr><td><code>agrave;</code></td><td>U+000E0</td><td>à</td></tr>
     *     <tr><td><code>agrave</code></td><td>U+000E0</td><td>à</td></tr>
     *     <tr><td><code>alefsym;</code></td><td>U+02135</td><td>ℵ</td></tr>
     *     <tr><td><code>aleph;</code></td><td>U+02135</td><td>ℵ</td></tr>
     *     <tr><td><code>Alpha;</code></td><td>U+00391</td><td>Α</td></tr>
     *     <tr><td><code>alpha;</code></td><td>U+003B1</td><td>α</td></tr>
     *     <tr><td><code>Amacr;</code></td><td>U+00100</td><td>Ā</td></tr>
     *     <tr><td><code>amacr;</code></td><td>U+00101</td><td>ā</td></tr>
     *     <tr><td><code>amalg;</code></td><td>U+02A3F</td><td>⨿</td></tr>
     *     <tr><td><code>AMP;</code></td><td>U+00026</td><td>&amp;</td></tr>
     *     <tr><td><code>AMP</code></td><td>U+00026</td><td>&amp;</td></tr>
     *     <tr><td><code>amp;</code></td><td>U+00026</td><td>&amp;</td></tr>
     *     <tr><td><code>amp</code></td><td>U+00026</td><td>&amp;</td></tr>
     *     <tr><td><code>And;</code></td><td>U+02A53</td><td>⩓</td></tr>
     *     <tr><td><code>and;</code></td><td>U+02227</td><td>∧</td></tr>
     *     <tr><td><code>andand;</code></td><td>U+02A55</td><td>⩕</td></tr>
     *     <tr><td><code>andd;</code></td><td>U+02A5C</td><td>⩜</td></tr>
     *     <tr><td><code>andslope;</code></td><td>U+02A58</td><td>⩘</td></tr>
     *     <tr><td><code>andv;</code></td><td>U+02A5A</td><td>⩚</td></tr>
     *     <tr><td><code>ang;</code></td><td>U+02220</td><td>∠</td></tr>
     *     <tr><td><code>ange;</code></td><td>U+029A4</td><td>⦤</td></tr>
     *     <tr><td><code>angle;</code></td><td>U+02220</td><td>∠</td></tr>
     *     <tr><td><code>angmsd;</code></td><td>U+02221</td><td>∡</td></tr>
     *     <tr><td><code>angmsdaa;</code></td><td>U+029A8</td><td>⦨</td></tr>
     *     <tr><td><code>angmsdab;</code></td><td>U+029A9</td><td>⦩</td></tr>
     *     <tr><td><code>angmsdac;</code></td><td>U+029AA</td><td>⦪</td></tr>
     *     <tr><td><code>angmsdad;</code></td><td>U+029AB</td><td>⦫</td></tr>
     *     <tr><td><code>angmsdae;</code></td><td>U+029AC</td><td>⦬</td></tr>
     *     <tr><td><code>angmsdaf;</code></td><td>U+029AD</td><td>⦭</td></tr>
     *     <tr><td><code>angmsdag;</code></td><td>U+029AE</td><td>⦮</td></tr>
     *     <tr><td><code>angmsdah;</code></td><td>U+029AF</td><td>⦯</td></tr>
     *     <tr><td><code>angrt;</code></td><td>U+0221F</td><td>∟</td></tr>
     *     <tr><td><code>angrtvb;</code></td><td>U+022BE</td><td>⊾</td></tr>
     *     <tr><td><code>angrtvbd;</code></td><td>U+0299D</td><td>⦝</td></tr>
     *     <tr><td><code>angsph;</code></td><td>U+02222</td><td>∢</td></tr>
     *     <tr><td><code>angst;</code></td><td>U+000C5</td><td>Å</td></tr>
     *     <tr><td><code>angzarr;</code></td><td>U+0237C</td><td>⍼</td></tr>
     *     <tr><td><code>Aogon;</code></td><td>U+00104</td><td>Ą</td></tr>
     *     <tr><td><code>aogon;</code></td><td>U+00105</td><td>ą</td></tr>
     *     <tr><td><code>Aopf;</code></td><td>U+1D538</td><td>𝔸</td></tr>
     *     <tr><td><code>aopf;</code></td><td>U+1D552</td><td>𝕒</td></tr>
     *     <tr><td><code>ap;</code></td><td>U+02248</td><td>≈</td></tr>
     *     <tr><td><code>apacir;</code></td><td>U+02A6F</td><td>⩯</td></tr>
     *     <tr><td><code>apE;</code></td><td>U+02A70</td><td>⩰</td></tr>
     *     <tr><td><code>ape;</code></td><td>U+0224A</td><td>≊</td></tr>
     *     <tr><td><code>apid;</code></td><td>U+0224B</td><td>≋</td></tr>
     *     <tr><td><code>apos;</code></td><td>U+00027</td><td>'</td></tr>
     *     <tr><td><code>ApplyFunction;</code></td><td>U+02061</td><td>⁡</td></tr>
     *     <tr><td><code>approx;</code></td><td>U+02248</td><td>≈</td></tr>
     *     <tr><td><code>approxeq;</code></td><td>U+0224A</td><td>≊</td></tr>
     *     <tr><td><code>Aring;</code></td><td>U+000C5</td><td>Å</td></tr>
     *     <tr><td><code>Aring</code></td><td>U+000C5</td><td>Å</td></tr>
     *     <tr><td><code>aring;</code></td><td>U+000E5</td><td>å</td></tr>
     *     <tr><td><code>aring</code></td><td>U+000E5</td><td>å</td></tr>
     *     <tr><td><code>Ascr;</code></td><td>U+1D49C</td><td>𝒜</td></tr>
     *     <tr><td><code>ascr;</code></td><td>U+1D4B6</td><td>𝒶</td></tr>
     *     <tr><td><code>Assign;</code></td><td>U+02254</td><td>≔</td></tr>
     *     <tr><td><code>ast;</code></td><td>U+0002A</td><td>*</td></tr>
     *     <tr><td><code>asymp;</code></td><td>U+02248</td><td>≈</td></tr>
     *     <tr><td><code>asympeq;</code></td><td>U+0224D</td><td>≍</td></tr>
     *     <tr><td><code>Atilde;</code></td><td>U+000C3</td><td>Ã</td></tr>
     *     <tr><td><code>Atilde</code></td><td>U+000C3</td><td>Ã</td></tr>
     *     <tr><td><code>atilde;</code></td><td>U+000E3</td><td>ã</td></tr>
     *     <tr><td><code>atilde</code></td><td>U+000E3</td><td>ã</td></tr>
     *     <tr><td><code>Auml;</code></td><td>U+000C4</td><td>Ä</td></tr>
     *     <tr><td><code>Auml</code></td><td>U+000C4</td><td>Ä</td></tr>
     *     <tr><td><code>auml;</code></td><td>U+000E4</td><td>ä</td></tr>
     *     <tr><td><code>auml</code></td><td>U+000E4</td><td>ä</td></tr>
     *     <tr><td><code>awconint;</code></td><td>U+02233</td><td>∳</td></tr>
     *     <tr><td><code>awint;</code></td><td>U+02A11</td><td>⨑</td></tr>
     *     <tr><td><code>backcong;</code></td><td>U+0224C</td><td>≌</td></tr>
     *     <tr><td><code>backepsilon;</code></td><td>U+003F6</td><td>϶</td></tr>
     *     <tr><td><code>backprime;</code></td><td>U+02035</td><td>‵</td></tr>
     *     <tr><td><code>backsim;</code></td><td>U+0223D</td><td>∽</td></tr>
     *     <tr><td><code>backsimeq;</code></td><td>U+022CD</td><td>⋍</td></tr>
     *     <tr><td><code>Backslash;</code></td><td>U+02216</td><td>∖</td></tr>
     *     <tr><td><code>Barv;</code></td><td>U+02AE7</td><td>⫧</td></tr>
     *     <tr><td><code>barvee;</code></td><td>U+022BD</td><td>⊽</td></tr>
     *     <tr><td><code>Barwed;</code></td><td>U+02306</td><td>⌆</td></tr>
     *     <tr><td><code>barwed;</code></td><td>U+02305</td><td>⌅</td></tr>
     *     <tr><td><code>barwedge;</code></td><td>U+02305</td><td>⌅</td></tr>
     *     <tr><td><code>bbrk;</code></td><td>U+023B5</td><td>⎵</td></tr>
     *     <tr><td><code>bbrktbrk;</code></td><td>U+023B6</td><td>⎶</td></tr>
     *     <tr><td><code>bcong;</code></td><td>U+0224C</td><td>≌</td></tr>
     *     <tr><td><code>Bcy;</code></td><td>U+00411</td><td>Б</td></tr>
     *     <tr><td><code>bcy;</code></td><td>U+00431</td><td>б</td></tr>
     *     <tr><td><code>bdquo;</code></td><td>U+0201E</td><td>„</td></tr>
     *     <tr><td><code>becaus;</code></td><td>U+02235</td><td>∵</td></tr>
     *     <tr><td><code>Because;</code></td><td>U+02235</td><td>∵</td></tr>
     *     <tr><td><code>because;</code></td><td>U+02235</td><td>∵</td></tr>
     *     <tr><td><code>bemptyv;</code></td><td>U+029B0</td><td>⦰</td></tr>
     *     <tr><td><code>bepsi;</code></td><td>U+003F6</td><td>϶</td></tr>
     *     <tr><td><code>bernou;</code></td><td>U+0212C</td><td>ℬ</td></tr>
     *     <tr><td><code>Bernoullis;</code></td><td>U+0212C</td><td>ℬ</td></tr>
     *     <tr><td><code>Beta;</code></td><td>U+00392</td><td>Β</td></tr>
     *     <tr><td><code>beta;</code></td><td>U+003B2</td><td>β</td></tr>
     *     <tr><td><code>beth;</code></td><td>U+02136</td><td>ℶ</td></tr>
     *     <tr><td><code>between;</code></td><td>U+0226C</td><td>≬</td></tr>
     *     <tr><td><code>Bfr;</code></td><td>U+1D505</td><td>𝔅</td></tr>
     *     <tr><td><code>bfr;</code></td><td>U+1D51F</td><td>𝔟</td></tr>
     *     <tr><td><code>bigcap;</code></td><td>U+022C2</td><td>⋂</td></tr>
     *     <tr><td><code>bigcirc;</code></td><td>U+025EF</td><td>◯</td></tr>
     *     <tr><td><code>bigcup;</code></td><td>U+022C3</td><td>⋃</td></tr>
     *     <tr><td><code>bigodot;</code></td><td>U+02A00</td><td>⨀</td></tr>
     *     <tr><td><code>bigoplus;</code></td><td>U+02A01</td><td>⨁</td></tr>
     *     <tr><td><code>bigotimes;</code></td><td>U+02A02</td><td>⨂</td></tr>
     *     <tr><td><code>bigsqcup;</code></td><td>U+02A06</td><td>⨆</td></tr>
     *     <tr><td><code>bigstar;</code></td><td>U+02605</td><td>★</td></tr>
     *     <tr><td><code>bigtriangledown;</code></td><td>U+025BD</td><td>▽</td></tr>
     *     <tr><td><code>bigtriangleup;</code></td><td>U+025B3</td><td>△</td></tr>
     *     <tr><td><code>biguplus;</code></td><td>U+02A04</td><td>⨄</td></tr>
     *     <tr><td><code>bigvee;</code></td><td>U+022C1</td><td>⋁</td></tr>
     *     <tr><td><code>bigwedge;</code></td><td>U+022C0</td><td>⋀</td></tr>
     *     <tr><td><code>bkarow;</code></td><td>U+0290D</td><td>⤍</td></tr>
     *     <tr><td><code>blacklozenge;</code></td><td>U+029EB</td><td>⧫</td></tr>
     *     <tr><td><code>blacksquare;</code></td><td>U+025AA</td><td>▪</td></tr>
     *     <tr><td><code>blacktriangle;</code></td><td>U+025B4</td><td>▴</td></tr>
     *     <tr><td><code>blacktriangledown;</code></td><td>U+025BE</td><td>▾</td></tr>
     *     <tr><td><code>blacktriangleleft;</code></td><td>U+025C2</td><td>◂</td></tr>
     *     <tr><td><code>blacktriangleright;</code></td><td>U+025B8</td><td>▸</td></tr>
     *     <tr><td><code>blank;</code></td><td>U+02423</td><td>␣</td></tr>
     *     <tr><td><code>blk12;</code></td><td>U+02592</td><td>▒</td></tr>
     *     <tr><td><code>blk14;</code></td><td>U+02591</td><td>░</td></tr>
     *     <tr><td><code>blk34;</code></td><td>U+02593</td><td>▓</td></tr>
     *     <tr><td><code>block;</code></td><td>U+02588</td><td>█</td></tr>
     *     <tr><td><code>bne;</code></td><td>U+0003D U+020E5</td><td>=⃥</td></tr>
     *     <tr><td><code>bnequiv;</code></td><td>U+02261 U+020E5</td><td>≡⃥</td></tr>
     *     <tr><td><code>bNot;</code></td><td>U+02AED</td><td>⫭</td></tr>
     *     <tr><td><code>bnot;</code></td><td>U+02310</td><td>⌐</td></tr>
     *     <tr><td><code>Bopf;</code></td><td>U+1D539</td><td>𝔹</td></tr>
     *     <tr><td><code>bopf;</code></td><td>U+1D553</td><td>𝕓</td></tr>
     *     <tr><td><code>bot;</code></td><td>U+022A5</td><td>⊥</td></tr>
     *     <tr><td><code>bottom;</code></td><td>U+022A5</td><td>⊥</td></tr>
     *     <tr><td><code>bowtie;</code></td><td>U+022C8</td><td>⋈</td></tr>
     *     <tr><td><code>boxbox;</code></td><td>U+029C9</td><td>⧉</td></tr>
     *     <tr><td><code>boxDL;</code></td><td>U+02557</td><td>╗</td></tr>
     *     <tr><td><code>boxDl;</code></td><td>U+02556</td><td>╖</td></tr>
     *     <tr><td><code>boxdL;</code></td><td>U+02555</td><td>╕</td></tr>
     *     <tr><td><code>boxdl;</code></td><td>U+02510</td><td>┐</td></tr>
     *     <tr><td><code>boxDR;</code></td><td>U+02554</td><td>╔</td></tr>
     *     <tr><td><code>boxDr;</code></td><td>U+02553</td><td>╓</td></tr>
     *     <tr><td><code>boxdR;</code></td><td>U+02552</td><td>╒</td></tr>
     *     <tr><td><code>boxdr;</code></td><td>U+0250C</td><td>┌</td></tr>
     *     <tr><td><code>boxH;</code></td><td>U+02550</td><td>═</td></tr>
     *     <tr><td><code>boxh;</code></td><td>U+02500</td><td>─</td></tr>
     *     <tr><td><code>boxHD;</code></td><td>U+02566</td><td>╦</td></tr>
     *     <tr><td><code>boxHd;</code></td><td>U+02564</td><td>╤</td></tr>
     *     <tr><td><code>boxhD;</code></td><td>U+02565</td><td>╥</td></tr>
     *     <tr><td><code>boxhd;</code></td><td>U+0252C</td><td>┬</td></tr>
     *     <tr><td><code>boxHU;</code></td><td>U+02569</td><td>╩</td></tr>
     *     <tr><td><code>boxHu;</code></td><td>U+02567</td><td>╧</td></tr>
     *     <tr><td><code>boxhU;</code></td><td>U+02568</td><td>╨</td></tr>
     *     <tr><td><code>boxhu;</code></td><td>U+02534</td><td>┴</td></tr>
     *     <tr><td><code>boxminus;</code></td><td>U+0229F</td><td>⊟</td></tr>
     *     <tr><td><code>boxplus;</code></td><td>U+0229E</td><td>⊞</td></tr>
     *     <tr><td><code>boxtimes;</code></td><td>U+022A0</td><td>⊠</td></tr>
     *     <tr><td><code>boxUL;</code></td><td>U+0255D</td><td>╝</td></tr>
     *     <tr><td><code>boxUl;</code></td><td>U+0255C</td><td>╜</td></tr>
     *     <tr><td><code>boxuL;</code></td><td>U+0255B</td><td>╛</td></tr>
     *     <tr><td><code>boxul;</code></td><td>U+02518</td><td>┘</td></tr>
     *     <tr><td><code>boxUR;</code></td><td>U+0255A</td><td>╚</td></tr>
     *     <tr><td><code>boxUr;</code></td><td>U+02559</td><td>╙</td></tr>
     *     <tr><td><code>boxuR;</code></td><td>U+02558</td><td>╘</td></tr>
     *     <tr><td><code>boxur;</code></td><td>U+02514</td><td>└</td></tr>
     *     <tr><td><code>boxV;</code></td><td>U+02551</td><td>║</td></tr>
     *     <tr><td><code>boxv;</code></td><td>U+02502</td><td>│</td></tr>
     *     <tr><td><code>boxVH;</code></td><td>U+0256C</td><td>╬</td></tr>
     *     <tr><td><code>boxVh;</code></td><td>U+0256B</td><td>╫</td></tr>
     *     <tr><td><code>boxvH;</code></td><td>U+0256A</td><td>╪</td></tr>
     *     <tr><td><code>boxvh;</code></td><td>U+0253C</td><td>┼</td></tr>
     *     <tr><td><code>boxVL;</code></td><td>U+02563</td><td>╣</td></tr>
     *     <tr><td><code>boxVl;</code></td><td>U+02562</td><td>╢</td></tr>
     *     <tr><td><code>boxvL;</code></td><td>U+02561</td><td>╡</td></tr>
     *     <tr><td><code>boxvl;</code></td><td>U+02524</td><td>┤</td></tr>
     *     <tr><td><code>boxVR;</code></td><td>U+02560</td><td>╠</td></tr>
     *     <tr><td><code>boxVr;</code></td><td>U+0255F</td><td>╟</td></tr>
     *     <tr><td><code>boxvR;</code></td><td>U+0255E</td><td>╞</td></tr>
     *     <tr><td><code>boxvr;</code></td><td>U+0251C</td><td>├</td></tr>
     *     <tr><td><code>bprime;</code></td><td>U+02035</td><td>‵</td></tr>
     *     <tr><td><code>Breve;</code></td><td>U+002D8</td><td>˘</td></tr>
     *     <tr><td><code>breve;</code></td><td>U+002D8</td><td>˘</td></tr>
     *     <tr><td><code>brvbar;</code></td><td>U+000A6</td><td>¦</td></tr>
     *     <tr><td><code>brvbar</code></td><td>U+000A6</td><td>¦</td></tr>
     *     <tr><td><code>Bscr;</code></td><td>U+0212C</td><td>ℬ</td></tr>
     *     <tr><td><code>bscr;</code></td><td>U+1D4B7</td><td>𝒷</td></tr>
     *     <tr><td><code>bsemi;</code></td><td>U+0204F</td><td>⁏</td></tr>
     *     <tr><td><code>bsim;</code></td><td>U+0223D</td><td>∽</td></tr>
     *     <tr><td><code>bsime;</code></td><td>U+022CD</td><td>⋍</td></tr>
     *     <tr><td><code>bsol;</code></td><td>U+0005C</td><td>\</td></tr>
     *     <tr><td><code>bsolb;</code></td><td>U+029C5</td><td>⧅</td></tr>
     *     <tr><td><code>bsolhsub;</code></td><td>U+027C8</td><td>⟈</td></tr>
     *     <tr><td><code>bull;</code></td><td>U+02022</td><td>•</td></tr>
     *     <tr><td><code>bullet;</code></td><td>U+02022</td><td>•</td></tr>
     *     <tr><td><code>bump;</code></td><td>U+0224E</td><td>≎</td></tr>
     *     <tr><td><code>bumpE;</code></td><td>U+02AAE</td><td>⪮</td></tr>
     *     <tr><td><code>bumpe;</code></td><td>U+0224F</td><td>≏</td></tr>
     *     <tr><td><code>Bumpeq;</code></td><td>U+0224E</td><td>≎</td></tr>
     *     <tr><td><code>bumpeq;</code></td><td>U+0224F</td><td>≏</td></tr>
     *     <tr><td><code>Cacute;</code></td><td>U+00106</td><td>Ć</td></tr>
     *     <tr><td><code>cacute;</code></td><td>U+00107</td><td>ć</td></tr>
     *     <tr><td><code>Cap;</code></td><td>U+022D2</td><td>⋒</td></tr>
     *     <tr><td><code>cap;</code></td><td>U+02229</td><td>∩</td></tr>
     *     <tr><td><code>capand;</code></td><td>U+02A44</td><td>⩄</td></tr>
     *     <tr><td><code>capbrcup;</code></td><td>U+02A49</td><td>⩉</td></tr>
     *     <tr><td><code>capcap;</code></td><td>U+02A4B</td><td>⩋</td></tr>
     *     <tr><td><code>capcup;</code></td><td>U+02A47</td><td>⩇</td></tr>
     *     <tr><td><code>capdot;</code></td><td>U+02A40</td><td>⩀</td></tr>
     *     <tr><td><code>CapitalDifferentialD;</code></td><td>U+02145</td><td>ⅅ</td></tr>
     *     <tr><td><code>caps;</code></td><td>U+02229 U+0FE00</td><td>∩︀</td></tr>
     *     <tr><td><code>caret;</code></td><td>U+02041</td><td>⁁</td></tr>
     *     <tr><td><code>caron;</code></td><td>U+002C7</td><td>ˇ</td></tr>
     *     <tr><td><code>Cayleys;</code></td><td>U+0212D</td><td>ℭ</td></tr>
     *     <tr><td><code>ccaps;</code></td><td>U+02A4D</td><td>⩍</td></tr>
     *     <tr><td><code>Ccaron;</code></td><td>U+0010C</td><td>Č</td></tr>
     *     <tr><td><code>ccaron;</code></td><td>U+0010D</td><td>č</td></tr>
     *     <tr><td><code>Ccedil;</code></td><td>U+000C7</td><td>Ç</td></tr>
     *     <tr><td><code>Ccedil</code></td><td>U+000C7</td><td>Ç</td></tr>
     *     <tr><td><code>ccedil;</code></td><td>U+000E7</td><td>ç</td></tr>
     *     <tr><td><code>ccedil</code></td><td>U+000E7</td><td>ç</td></tr>
     *     <tr><td><code>Ccirc;</code></td><td>U+00108</td><td>Ĉ</td></tr>
     *     <tr><td><code>ccirc;</code></td><td>U+00109</td><td>ĉ</td></tr>
     *     <tr><td><code>Cconint;</code></td><td>U+02230</td><td>∰</td></tr>
     *     <tr><td><code>ccups;</code></td><td>U+02A4C</td><td>⩌</td></tr>
     *     <tr><td><code>ccupssm;</code></td><td>U+02A50</td><td>⩐</td></tr>
     *     <tr><td><code>Cdot;</code></td><td>U+0010A</td><td>Ċ</td></tr>
     *     <tr><td><code>cdot;</code></td><td>U+0010B</td><td>ċ</td></tr>
     *     <tr><td><code>cedil;</code></td><td>U+000B8</td><td>¸</td></tr>
     *     <tr><td><code>cedil</code></td><td>U+000B8</td><td>¸</td></tr>
     *     <tr><td><code>Cedilla;</code></td><td>U+000B8</td><td>¸</td></tr>
     *     <tr><td><code>cemptyv;</code></td><td>U+029B2</td><td>⦲</td></tr>
     *     <tr><td><code>cent;</code></td><td>U+000A2</td><td>¢</td></tr>
     *     <tr><td><code>cent</code></td><td>U+000A2</td><td>¢</td></tr>
     *     <tr><td><code>CenterDot;</code></td><td>U+000B7</td><td>·</td></tr>
     *     <tr><td><code>centerdot;</code></td><td>U+000B7</td><td>·</td></tr>
     *     <tr><td><code>Cfr;</code></td><td>U+0212D</td><td>ℭ</td></tr>
     *     <tr><td><code>cfr;</code></td><td>U+1D520</td><td>𝔠</td></tr>
     *     <tr><td><code>CHcy;</code></td><td>U+00427</td><td>Ч</td></tr>
     *     <tr><td><code>chcy;</code></td><td>U+00447</td><td>ч</td></tr>
     *     <tr><td><code>check;</code></td><td>U+02713</td><td>✓</td></tr>
     *     <tr><td><code>checkmark;</code></td><td>U+02713</td><td>✓</td></tr>
     *     <tr><td><code>Chi;</code></td><td>U+003A7</td><td>Χ</td></tr>
     *     <tr><td><code>chi;</code></td><td>U+003C7</td><td>χ</td></tr>
     *     <tr><td><code>cir;</code></td><td>U+025CB</td><td>○</td></tr>
     *     <tr><td><code>circ;</code></td><td>U+002C6</td><td>ˆ</td></tr>
     *     <tr><td><code>circeq;</code></td><td>U+02257</td><td>≗</td></tr>
     *     <tr><td><code>circlearrowleft;</code></td><td>U+021BA</td><td>↺</td></tr>
     *     <tr><td><code>circlearrowright;</code></td><td>U+021BB</td><td>↻</td></tr>
     *     <tr><td><code>circledast;</code></td><td>U+0229B</td><td>⊛</td></tr>
     *     <tr><td><code>circledcirc;</code></td><td>U+0229A</td><td>⊚</td></tr>
     *     <tr><td><code>circleddash;</code></td><td>U+0229D</td><td>⊝</td></tr>
     *     <tr><td><code>CircleDot;</code></td><td>U+02299</td><td>⊙</td></tr>
     *     <tr><td><code>circledR;</code></td><td>U+000AE</td><td>®</td></tr>
     *     <tr><td><code>circledS;</code></td><td>U+024C8</td><td>Ⓢ</td></tr>
     *     <tr><td><code>CircleMinus;</code></td><td>U+02296</td><td>⊖</td></tr>
     *     <tr><td><code>CirclePlus;</code></td><td>U+02295</td><td>⊕</td></tr>
     *     <tr><td><code>CircleTimes;</code></td><td>U+02297</td><td>⊗</td></tr>
     *     <tr><td><code>cirE;</code></td><td>U+029C3</td><td>⧃</td></tr>
     *     <tr><td><code>cire;</code></td><td>U+02257</td><td>≗</td></tr>
     *     <tr><td><code>cirfnint;</code></td><td>U+02A10</td><td>⨐</td></tr>
     *     <tr><td><code>cirmid;</code></td><td>U+02AEF</td><td>⫯</td></tr>
     *     <tr><td><code>cirscir;</code></td><td>U+029C2</td><td>⧂</td></tr>
     *     <tr><td><code>ClockwiseContourIntegral;</code></td><td>U+02232</td><td>∲</td></tr>
     *     <tr><td><code>CloseCurlyDoubleQuote;</code></td><td>U+0201D</td><td>”</td></tr>
     *     <tr><td><code>CloseCurlyQuote;</code></td><td>U+02019</td><td>’</td></tr>
     *     <tr><td><code>clubs;</code></td><td>U+02663</td><td>♣</td></tr>
     *     <tr><td><code>clubsuit;</code></td><td>U+02663</td><td>♣</td></tr>
     *     <tr><td><code>Colon;</code></td><td>U+02237</td><td>∷</td></tr>
     *     <tr><td><code>colon;</code></td><td>U+0003A</td><td>:</td></tr>
     *     <tr><td><code>Colone;</code></td><td>U+02A74</td><td>⩴</td></tr>
     *     <tr><td><code>colone;</code></td><td>U+02254</td><td>≔</td></tr>
     *     <tr><td><code>coloneq;</code></td><td>U+02254</td><td>≔</td></tr>
     *     <tr><td><code>comma;</code></td><td>U+0002C</td><td>,</td></tr>
     *     <tr><td><code>commat;</code></td><td>U+00040</td><td>@</td></tr>
     *     <tr><td><code>comp;</code></td><td>U+02201</td><td>∁</td></tr>
     *     <tr><td><code>compfn;</code></td><td>U+02218</td><td>∘</td></tr>
     *     <tr><td><code>complement;</code></td><td>U+02201</td><td>∁</td></tr>
     *     <tr><td><code>complexes;</code></td><td>U+02102</td><td>ℂ</td></tr>
     *     <tr><td><code>cong;</code></td><td>U+02245</td><td>≅</td></tr>
     *     <tr><td><code>congdot;</code></td><td>U+02A6D</td><td>⩭</td></tr>
     *     <tr><td><code>Congruent;</code></td><td>U+02261</td><td>≡</td></tr>
     *     <tr><td><code>Conint;</code></td><td>U+0222F</td><td>∯</td></tr>
     *     <tr><td><code>conint;</code></td><td>U+0222E</td><td>∮</td></tr>
     *     <tr><td><code>ContourIntegral;</code></td><td>U+0222E</td><td>∮</td></tr>
     *     <tr><td><code>Copf;</code></td><td>U+02102</td><td>ℂ</td></tr>
     *     <tr><td><code>copf;</code></td><td>U+1D554</td><td>𝕔</td></tr>
     *     <tr><td><code>coprod;</code></td><td>U+02210</td><td>∐</td></tr>
     *     <tr><td><code>Coproduct;</code></td><td>U+02210</td><td>∐</td></tr>
     *     <tr><td><code>COPY;</code></td><td>U+000A9</td><td>©</td></tr>
     *     <tr><td><code>COPY</code></td><td>U+000A9</td><td>©</td></tr>
     *     <tr><td><code>copy;</code></td><td>U+000A9</td><td>©</td></tr>
     *     <tr><td><code>copy</code></td><td>U+000A9</td><td>©</td></tr>
     *     <tr><td><code>copysr;</code></td><td>U+02117</td><td>℗</td></tr>
     *     <tr><td><code>CounterClockwiseContourIntegral;</code></td><td>U+02233</td><td>∳</td></tr>
     *     <tr><td><code>crarr;</code></td><td>U+021B5</td><td>↵</td></tr>
     *     <tr><td><code>Cross;</code></td><td>U+02A2F</td><td>⨯</td></tr>
     *     <tr><td><code>cross;</code></td><td>U+02717</td><td>✗</td></tr>
     *     <tr><td><code>Cscr;</code></td><td>U+1D49E</td><td>𝒞</td></tr>
     *     <tr><td><code>cscr;</code></td><td>U+1D4B8</td><td>𝒸</td></tr>
     *     <tr><td><code>csub;</code></td><td>U+02ACF</td><td>⫏</td></tr>
     *     <tr><td><code>csube;</code></td><td>U+02AD1</td><td>⫑</td></tr>
     *     <tr><td><code>csup;</code></td><td>U+02AD0</td><td>⫐</td></tr>
     *     <tr><td><code>csupe;</code></td><td>U+02AD2</td><td>⫒</td></tr>
     *     <tr><td><code>ctdot;</code></td><td>U+022EF</td><td>⋯</td></tr>
     *     <tr><td><code>cudarrl;</code></td><td>U+02938</td><td>⤸</td></tr>
     *     <tr><td><code>cudarrr;</code></td><td>U+02935</td><td>⤵</td></tr>
     *     <tr><td><code>cuepr;</code></td><td>U+022DE</td><td>⋞</td></tr>
     *     <tr><td><code>cuesc;</code></td><td>U+022DF</td><td>⋟</td></tr>
     *     <tr><td><code>cularr;</code></td><td>U+021B6</td><td>↶</td></tr>
     *     <tr><td><code>cularrp;</code></td><td>U+0293D</td><td>⤽</td></tr>
     *     <tr><td><code>Cup;</code></td><td>U+022D3</td><td>⋓</td></tr>
     *     <tr><td><code>cup;</code></td><td>U+0222A</td><td>∪</td></tr>
     *     <tr><td><code>cupbrcap;</code></td><td>U+02A48</td><td>⩈</td></tr>
     *     <tr><td><code>CupCap;</code></td><td>U+0224D</td><td>≍</td></tr>
     *     <tr><td><code>cupcap;</code></td><td>U+02A46</td><td>⩆</td></tr>
     *     <tr><td><code>cupcup;</code></td><td>U+02A4A</td><td>⩊</td></tr>
     *     <tr><td><code>cupdot;</code></td><td>U+0228D</td><td>⊍</td></tr>
     *     <tr><td><code>cupor;</code></td><td>U+02A45</td><td>⩅</td></tr>
     *     <tr><td><code>cups;</code></td><td>U+0222A U+0FE00</td><td>∪︀</td></tr>
     *     <tr><td><code>curarr;</code></td><td>U+021B7</td><td>↷</td></tr>
     *     <tr><td><code>curarrm;</code></td><td>U+0293C</td><td>⤼</td></tr>
     *     <tr><td><code>curlyeqprec;</code></td><td>U+022DE</td><td>⋞</td></tr>
     *     <tr><td><code>curlyeqsucc;</code></td><td>U+022DF</td><td>⋟</td></tr>
     *     <tr><td><code>curlyvee;</code></td><td>U+022CE</td><td>⋎</td></tr>
     *     <tr><td><code>curlywedge;</code></td><td>U+022CF</td><td>⋏</td></tr>
     *     <tr><td><code>curren;</code></td><td>U+000A4</td><td>¤</td></tr>
     *     <tr><td><code>curren</code></td><td>U+000A4</td><td>¤</td></tr>
     *     <tr><td><code>curvearrowleft;</code></td><td>U+021B6</td><td>↶</td></tr>
     *     <tr><td><code>curvearrowright;</code></td><td>U+021B7</td><td>↷</td></tr>
     *     <tr><td><code>cuvee;</code></td><td>U+022CE</td><td>⋎</td></tr>
     *     <tr><td><code>cuwed;</code></td><td>U+022CF</td><td>⋏</td></tr>
     *     <tr><td><code>cwconint;</code></td><td>U+02232</td><td>∲</td></tr>
     *     <tr><td><code>cwint;</code></td><td>U+02231</td><td>∱</td></tr>
     *     <tr><td><code>cylcty;</code></td><td>U+0232D</td><td>⌭</td></tr>
     *     <tr><td><code>Dagger;</code></td><td>U+02021</td><td>‡</td></tr>
     *     <tr><td><code>dagger;</code></td><td>U+02020</td><td>†</td></tr>
     *     <tr><td><code>daleth;</code></td><td>U+02138</td><td>ℸ</td></tr>
     *     <tr><td><code>Darr;</code></td><td>U+021A1</td><td>↡</td></tr>
     *     <tr><td><code>dArr;</code></td><td>U+021D3</td><td>⇓</td></tr>
     *     <tr><td><code>darr;</code></td><td>U+02193</td><td>↓</td></tr>
     *     <tr><td><code>dash;</code></td><td>U+02010</td><td>‐</td></tr>
     *     <tr><td><code>Dashv;</code></td><td>U+02AE4</td><td>⫤</td></tr>
     *     <tr><td><code>dashv;</code></td><td>U+022A3</td><td>⊣</td></tr>
     *     <tr><td><code>dbkarow;</code></td><td>U+0290F</td><td>⤏</td></tr>
     *     <tr><td><code>dblac;</code></td><td>U+002DD</td><td>˝</td></tr>
     *     <tr><td><code>Dcaron;</code></td><td>U+0010E</td><td>Ď</td></tr>
     *     <tr><td><code>dcaron;</code></td><td>U+0010F</td><td>ď</td></tr>
     *     <tr><td><code>Dcy;</code></td><td>U+00414</td><td>Д</td></tr>
     *     <tr><td><code>dcy;</code></td><td>U+00434</td><td>д</td></tr>
     *     <tr><td><code>DD;</code></td><td>U+02145</td><td>ⅅ</td></tr>
     *     <tr><td><code>dd;</code></td><td>U+02146</td><td>ⅆ</td></tr>
     *     <tr><td><code>ddagger;</code></td><td>U+02021</td><td>‡</td></tr>
     *     <tr><td><code>ddarr;</code></td><td>U+021CA</td><td>⇊</td></tr>
     *     <tr><td><code>DDotrahd;</code></td><td>U+02911</td><td>⤑</td></tr>
     *     <tr><td><code>ddotseq;</code></td><td>U+02A77</td><td>⩷</td></tr>
     *     <tr><td><code>deg;</code></td><td>U+000B0</td><td>°</td></tr>
     *     <tr><td><code>deg</code></td><td>U+000B0</td><td>°</td></tr>
     *     <tr><td><code>Del;</code></td><td>U+02207</td><td>∇</td></tr>
     *     <tr><td><code>Delta;</code></td><td>U+00394</td><td>Δ</td></tr>
     *     <tr><td><code>delta;</code></td><td>U+003B4</td><td>δ</td></tr>
     *     <tr><td><code>demptyv;</code></td><td>U+029B1</td><td>⦱</td></tr>
     *     <tr><td><code>dfisht;</code></td><td>U+0297F</td><td>⥿</td></tr>
     *     <tr><td><code>Dfr;</code></td><td>U+1D507</td><td>𝔇</td></tr>
     *     <tr><td><code>dfr;</code></td><td>U+1D521</td><td>𝔡</td></tr>
     *     <tr><td><code>dHar;</code></td><td>U+02965</td><td>⥥</td></tr>
     *     <tr><td><code>dharl;</code></td><td>U+021C3</td><td>⇃</td></tr>
     *     <tr><td><code>dharr;</code></td><td>U+021C2</td><td>⇂</td></tr>
     *     <tr><td><code>DiacriticalAcute;</code></td><td>U+000B4</td><td>´</td></tr>
     *     <tr><td><code>DiacriticalDot;</code></td><td>U+002D9</td><td>˙</td></tr>
     *     <tr><td><code>DiacriticalDoubleAcute;</code></td><td>U+002DD</td><td>˝</td></tr>
     *     <tr><td><code>DiacriticalGrave;</code></td><td>U+00060</td><td>`</td></tr>
     *     <tr><td><code>DiacriticalTilde;</code></td><td>U+002DC</td><td>˜</td></tr>
     *     <tr><td><code>diam;</code></td><td>U+022C4</td><td>⋄</td></tr>
     *     <tr><td><code>Diamond;</code></td><td>U+022C4</td><td>⋄</td></tr>
     *     <tr><td><code>diamond;</code></td><td>U+022C4</td><td>⋄</td></tr>
     *     <tr><td><code>diamondsuit;</code></td><td>U+02666</td><td>♦</td></tr>
     *     <tr><td><code>diams;</code></td><td>U+02666</td><td>♦</td></tr>
     *     <tr><td><code>die;</code></td><td>U+000A8</td><td>¨</td></tr>
     *     <tr><td><code>DifferentialD;</code></td><td>U+02146</td><td>ⅆ</td></tr>
     *     <tr><td><code>digamma;</code></td><td>U+003DD</td><td>ϝ</td></tr>
     *     <tr><td><code>disin;</code></td><td>U+022F2</td><td>⋲</td></tr>
     *     <tr><td><code>div;</code></td><td>U+000F7</td><td>÷</td></tr>
     *     <tr><td><code>divide;</code></td><td>U+000F7</td><td>÷</td></tr>
     *     <tr><td><code>divide</code></td><td>U+000F7</td><td>÷</td></tr>
     *     <tr><td><code>divideontimes;</code></td><td>U+022C7</td><td>⋇</td></tr>
     *     <tr><td><code>divonx;</code></td><td>U+022C7</td><td>⋇</td></tr>
     *     <tr><td><code>DJcy;</code></td><td>U+00402</td><td>Ђ</td></tr>
     *     <tr><td><code>djcy;</code></td><td>U+00452</td><td>ђ</td></tr>
     *     <tr><td><code>dlcorn;</code></td><td>U+0231E</td><td>⌞</td></tr>
     *     <tr><td><code>dlcrop;</code></td><td>U+0230D</td><td>⌍</td></tr>
     *     <tr><td><code>dollar;</code></td><td>U+00024</td><td>$</td></tr>
     *     <tr><td><code>Dopf;</code></td><td>U+1D53B</td><td>𝔻</td></tr>
     *     <tr><td><code>dopf;</code></td><td>U+1D555</td><td>𝕕</td></tr>
     *     <tr><td><code>Dot;</code></td><td>U+000A8</td><td>¨</td></tr>
     *     <tr><td><code>dot;</code></td><td>U+002D9</td><td>˙</td></tr>
     *     <tr><td><code>DotDot;</code></td><td>U+020DC</td><td>◌⃜</td></tr>
     *     <tr><td><code>doteq;</code></td><td>U+02250</td><td>≐</td></tr>
     *     <tr><td><code>doteqdot;</code></td><td>U+02251</td><td>≑</td></tr>
     *     <tr><td><code>DotEqual;</code></td><td>U+02250</td><td>≐</td></tr>
     *     <tr><td><code>dotminus;</code></td><td>U+02238</td><td>∸</td></tr>
     *     <tr><td><code>dotplus;</code></td><td>U+02214</td><td>∔</td></tr>
     *     <tr><td><code>dotsquare;</code></td><td>U+022A1</td><td>⊡</td></tr>
     *     <tr><td><code>doublebarwedge;</code></td><td>U+02306</td><td>⌆</td></tr>
     *     <tr><td><code>DoubleContourIntegral;</code></td><td>U+0222F</td><td>∯</td></tr>
     *     <tr><td><code>DoubleDot;</code></td><td>U+000A8</td><td>¨</td></tr>
     *     <tr><td><code>DoubleDownArrow;</code></td><td>U+021D3</td><td>⇓</td></tr>
     *     <tr><td><code>DoubleLeftArrow;</code></td><td>U+021D0</td><td>⇐</td></tr>
     *     <tr><td><code>DoubleLeftRightArrow;</code></td><td>U+021D4</td><td>⇔</td></tr>
     *     <tr><td><code>DoubleLeftTee;</code></td><td>U+02AE4</td><td>⫤</td></tr>
     *     <tr><td><code>DoubleLongLeftArrow;</code></td><td>U+027F8</td><td>⟸</td></tr>
     *     <tr><td><code>DoubleLongLeftRightArrow;</code></td><td>U+027FA</td><td>⟺</td></tr>
     *     <tr><td><code>DoubleLongRightArrow;</code></td><td>U+027F9</td><td>⟹</td></tr>
     *     <tr><td><code>DoubleRightArrow;</code></td><td>U+021D2</td><td>⇒</td></tr>
     *     <tr><td><code>DoubleRightTee;</code></td><td>U+022A8</td><td>⊨</td></tr>
     *     <tr><td><code>DoubleUpArrow;</code></td><td>U+021D1</td><td>⇑</td></tr>
     *     <tr><td><code>DoubleUpDownArrow;</code></td><td>U+021D5</td><td>⇕</td></tr>
     *     <tr><td><code>DoubleVerticalBar;</code></td><td>U+02225</td><td>∥</td></tr>
     *     <tr><td><code>DownArrow;</code></td><td>U+02193</td><td>↓</td></tr>
     *     <tr><td><code>Downarrow;</code></td><td>U+021D3</td><td>⇓</td></tr>
     *     <tr><td><code>downarrow;</code></td><td>U+02193</td><td>↓</td></tr>
     *     <tr><td><code>DownArrowBar;</code></td><td>U+02913</td><td>⤓</td></tr>
     *     <tr><td><code>DownArrowUpArrow;</code></td><td>U+021F5</td><td>⇵</td></tr>
     *     <tr><td><code>DownBreve;</code></td><td>U+00311</td><td>◌̑</td></tr>
     *     <tr><td><code>downdownarrows;</code></td><td>U+021CA</td><td>⇊</td></tr>
     *     <tr><td><code>downharpoonleft;</code></td><td>U+021C3</td><td>⇃</td></tr>
     *     <tr><td><code>downharpoonright;</code></td><td>U+021C2</td><td>⇂</td></tr>
     *     <tr><td><code>DownLeftRightVector;</code></td><td>U+02950</td><td>⥐</td></tr>
     *     <tr><td><code>DownLeftTeeVector;</code></td><td>U+0295E</td><td>⥞</td></tr>
     *     <tr><td><code>DownLeftVector;</code></td><td>U+021BD</td><td>↽</td></tr>
     *     <tr><td><code>DownLeftVectorBar;</code></td><td>U+02956</td><td>⥖</td></tr>
     *     <tr><td><code>DownRightTeeVector;</code></td><td>U+0295F</td><td>⥟</td></tr>
     *     <tr><td><code>DownRightVector;</code></td><td>U+021C1</td><td>⇁</td></tr>
     *     <tr><td><code>DownRightVectorBar;</code></td><td>U+02957</td><td>⥗</td></tr>
     *     <tr><td><code>DownTee;</code></td><td>U+022A4</td><td>⊤</td></tr>
     *     <tr><td><code>DownTeeArrow;</code></td><td>U+021A7</td><td>↧</td></tr>
     *     <tr><td><code>drbkarow;</code></td><td>U+02910</td><td>⤐</td></tr>
     *     <tr><td><code>drcorn;</code></td><td>U+0231F</td><td>⌟</td></tr>
     *     <tr><td><code>drcrop;</code></td><td>U+0230C</td><td>⌌</td></tr>
     *     <tr><td><code>Dscr;</code></td><td>U+1D49F</td><td>𝒟</td></tr>
     *     <tr><td><code>dscr;</code></td><td>U+1D4B9</td><td>𝒹</td></tr>
     *     <tr><td><code>DScy;</code></td><td>U+00405</td><td>Ѕ</td></tr>
     *     <tr><td><code>dscy;</code></td><td>U+00455</td><td>ѕ</td></tr>
     *     <tr><td><code>dsol;</code></td><td>U+029F6</td><td>⧶</td></tr>
     *     <tr><td><code>Dstrok;</code></td><td>U+00110</td><td>Đ</td></tr>
     *     <tr><td><code>dstrok;</code></td><td>U+00111</td><td>đ</td></tr>
     *     <tr><td><code>dtdot;</code></td><td>U+022F1</td><td>⋱</td></tr>
     *     <tr><td><code>dtri;</code></td><td>U+025BF</td><td>▿</td></tr>
     *     <tr><td><code>dtrif;</code></td><td>U+025BE</td><td>▾</td></tr>
     *     <tr><td><code>duarr;</code></td><td>U+021F5</td><td>⇵</td></tr>
     *     <tr><td><code>duhar;</code></td><td>U+0296F</td><td>⥯</td></tr>
     *     <tr><td><code>dwangle;</code></td><td>U+029A6</td><td>⦦</td></tr>
     *     <tr><td><code>DZcy;</code></td><td>U+0040F</td><td>Џ</td></tr>
     *     <tr><td><code>dzcy;</code></td><td>U+0045F</td><td>џ</td></tr>
     *     <tr><td><code>dzigrarr;</code></td><td>U+027FF</td><td>⟿</td></tr>
     *     <tr><td><code>Eacute;</code></td><td>U+000C9</td><td>É</td></tr>
     *     <tr><td><code>Eacute</code></td><td>U+000C9</td><td>É</td></tr>
     *     <tr><td><code>eacute;</code></td><td>U+000E9</td><td>é</td></tr>
     *     <tr><td><code>eacute</code></td><td>U+000E9</td><td>é</td></tr>
     *     <tr><td><code>easter;</code></td><td>U+02A6E</td><td>⩮</td></tr>
     *     <tr><td><code>Ecaron;</code></td><td>U+0011A</td><td>Ě</td></tr>
     *     <tr><td><code>ecaron;</code></td><td>U+0011B</td><td>ě</td></tr>
     *     <tr><td><code>ecir;</code></td><td>U+02256</td><td>≖</td></tr>
     *     <tr><td><code>Ecirc;</code></td><td>U+000CA</td><td>Ê</td></tr>
     *     <tr><td><code>Ecirc</code></td><td>U+000CA</td><td>Ê</td></tr>
     *     <tr><td><code>ecirc;</code></td><td>U+000EA</td><td>ê</td></tr>
     *     <tr><td><code>ecirc</code></td><td>U+000EA</td><td>ê</td></tr>
     *     <tr><td><code>ecolon;</code></td><td>U+02255</td><td>≕</td></tr>
     *     <tr><td><code>Ecy;</code></td><td>U+0042D</td><td>Э</td></tr>
     *     <tr><td><code>ecy;</code></td><td>U+0044D</td><td>э</td></tr>
     *     <tr><td><code>eDDot;</code></td><td>U+02A77</td><td>⩷</td></tr>
     *     <tr><td><code>Edot;</code></td><td>U+00116</td><td>Ė</td></tr>
     *     <tr><td><code>eDot;</code></td><td>U+02251</td><td>≑</td></tr>
     *     <tr><td><code>edot;</code></td><td>U+00117</td><td>ė</td></tr>
     *     <tr><td><code>ee;</code></td><td>U+02147</td><td>ⅇ</td></tr>
     *     <tr><td><code>efDot;</code></td><td>U+02252</td><td>≒</td></tr>
     *     <tr><td><code>Efr;</code></td><td>U+1D508</td><td>𝔈</td></tr>
     *     <tr><td><code>efr;</code></td><td>U+1D522</td><td>𝔢</td></tr>
     *     <tr><td><code>eg;</code></td><td>U+02A9A</td><td>⪚</td></tr>
     *     <tr><td><code>Egrave;</code></td><td>U+000C8</td><td>È</td></tr>
     *     <tr><td><code>Egrave</code></td><td>U+000C8</td><td>È</td></tr>
     *     <tr><td><code>egrave;</code></td><td>U+000E8</td><td>è</td></tr>
     *     <tr><td><code>egrave</code></td><td>U+000E8</td><td>è</td></tr>
     *     <tr><td><code>egs;</code></td><td>U+02A96</td><td>⪖</td></tr>
     *     <tr><td><code>egsdot;</code></td><td>U+02A98</td><td>⪘</td></tr>
     *     <tr><td><code>el;</code></td><td>U+02A99</td><td>⪙</td></tr>
     *     <tr><td><code>Element;</code></td><td>U+02208</td><td>∈</td></tr>
     *     <tr><td><code>elinters;</code></td><td>U+023E7</td><td>⏧</td></tr>
     *     <tr><td><code>ell;</code></td><td>U+02113</td><td>ℓ</td></tr>
     *     <tr><td><code>els;</code></td><td>U+02A95</td><td>⪕</td></tr>
     *     <tr><td><code>elsdot;</code></td><td>U+02A97</td><td>⪗</td></tr>
     *     <tr><td><code>Emacr;</code></td><td>U+00112</td><td>Ē</td></tr>
     *     <tr><td><code>emacr;</code></td><td>U+00113</td><td>ē</td></tr>
     *     <tr><td><code>empty;</code></td><td>U+02205</td><td>∅</td></tr>
     *     <tr><td><code>emptyset;</code></td><td>U+02205</td><td>∅</td></tr>
     *     <tr><td><code>EmptySmallSquare;</code></td><td>U+025FB</td><td>◻</td></tr>
     *     <tr><td><code>emptyv;</code></td><td>U+02205</td><td>∅</td></tr>
     *     <tr><td><code>EmptyVerySmallSquare;</code></td><td>U+025AB</td><td>▫</td></tr>
     *     <tr><td><code>emsp;</code></td><td>U+02003</td><td> </td></tr>
     *     <tr><td><code>emsp13;</code></td><td>U+02004</td><td> </td></tr>
     *     <tr><td><code>emsp14;</code></td><td>U+02005</td><td> </td></tr>
     *     <tr><td><code>ENG;</code></td><td>U+0014A</td><td>Ŋ</td></tr>
     *     <tr><td><code>eng;</code></td><td>U+0014B</td><td>ŋ</td></tr>
     *     <tr><td><code>ensp;</code></td><td>U+02002</td><td> </td></tr>
     *     <tr><td><code>Eogon;</code></td><td>U+00118</td><td>Ę</td></tr>
     *     <tr><td><code>eogon;</code></td><td>U+00119</td><td>ę</td></tr>
     *     <tr><td><code>Eopf;</code></td><td>U+1D53C</td><td>𝔼</td></tr>
     *     <tr><td><code>eopf;</code></td><td>U+1D556</td><td>𝕖</td></tr>
     *     <tr><td><code>epar;</code></td><td>U+022D5</td><td>⋕</td></tr>
     *     <tr><td><code>eparsl;</code></td><td>U+029E3</td><td>⧣</td></tr>
     *     <tr><td><code>eplus;</code></td><td>U+02A71</td><td>⩱</td></tr>
     *     <tr><td><code>epsi;</code></td><td>U+003B5</td><td>ε</td></tr>
     *     <tr><td><code>Epsilon;</code></td><td>U+00395</td><td>Ε</td></tr>
     *     <tr><td><code>epsilon;</code></td><td>U+003B5</td><td>ε</td></tr>
     *     <tr><td><code>epsiv;</code></td><td>U+003F5</td><td>ϵ</td></tr>
     *     <tr><td><code>eqcirc;</code></td><td>U+02256</td><td>≖</td></tr>
     *     <tr><td><code>eqcolon;</code></td><td>U+02255</td><td>≕</td></tr>
     *     <tr><td><code>eqsim;</code></td><td>U+02242</td><td>≂</td></tr>
     *     <tr><td><code>eqslantgtr;</code></td><td>U+02A96</td><td>⪖</td></tr>
     *     <tr><td><code>eqslantless;</code></td><td>U+02A95</td><td>⪕</td></tr>
     *     <tr><td><code>Equal;</code></td><td>U+02A75</td><td>⩵</td></tr>
     *     <tr><td><code>equals;</code></td><td>U+0003D</td><td>=</td></tr>
     *     <tr><td><code>EqualTilde;</code></td><td>U+02242</td><td>≂</td></tr>
     *     <tr><td><code>equest;</code></td><td>U+0225F</td><td>≟</td></tr>
     *     <tr><td><code>Equilibrium;</code></td><td>U+021CC</td><td>⇌</td></tr>
     *     <tr><td><code>equiv;</code></td><td>U+02261</td><td>≡</td></tr>
     *     <tr><td><code>equivDD;</code></td><td>U+02A78</td><td>⩸</td></tr>
     *     <tr><td><code>eqvparsl;</code></td><td>U+029E5</td><td>⧥</td></tr>
     *     <tr><td><code>erarr;</code></td><td>U+02971</td><td>⥱</td></tr>
     *     <tr><td><code>erDot;</code></td><td>U+02253</td><td>≓</td></tr>
     *     <tr><td><code>Escr;</code></td><td>U+02130</td><td>ℰ</td></tr>
     *     <tr><td><code>escr;</code></td><td>U+0212F</td><td>ℯ</td></tr>
     *     <tr><td><code>esdot;</code></td><td>U+02250</td><td>≐</td></tr>
     *     <tr><td><code>Esim;</code></td><td>U+02A73</td><td>⩳</td></tr>
     *     <tr><td><code>esim;</code></td><td>U+02242</td><td>≂</td></tr>
     *     <tr><td><code>Eta;</code></td><td>U+00397</td><td>Η</td></tr>
     *     <tr><td><code>eta;</code></td><td>U+003B7</td><td>η</td></tr>
     *     <tr><td><code>ETH;</code></td><td>U+000D0</td><td>Ð</td></tr>
     *     <tr><td><code>ETH</code></td><td>U+000D0</td><td>Ð</td></tr>
     *     <tr><td><code>eth;</code></td><td>U+000F0</td><td>ð</td></tr>
     *     <tr><td><code>eth</code></td><td>U+000F0</td><td>ð</td></tr>
     *     <tr><td><code>Euml;</code></td><td>U+000CB</td><td>Ë</td></tr>
     *     <tr><td><code>Euml</code></td><td>U+000CB</td><td>Ë</td></tr>
     *     <tr><td><code>euml;</code></td><td>U+000EB</td><td>ë</td></tr>
     *     <tr><td><code>euml</code></td><td>U+000EB</td><td>ë</td></tr>
     *     <tr><td><code>euro;</code></td><td>U+020AC</td><td>€</td></tr>
     *     <tr><td><code>excl;</code></td><td>U+00021</td><td>!</td></tr>
     *     <tr><td><code>exist;</code></td><td>U+02203</td><td>∃</td></tr>
     *     <tr><td><code>Exists;</code></td><td>U+02203</td><td>∃</td></tr>
     *     <tr><td><code>expectation;</code></td><td>U+02130</td><td>ℰ</td></tr>
     *     <tr><td><code>ExponentialE;</code></td><td>U+02147</td><td>ⅇ</td></tr>
     *     <tr><td><code>exponentiale;</code></td><td>U+02147</td><td>ⅇ</td></tr>
     *     <tr><td><code>fallingdotseq;</code></td><td>U+02252</td><td>≒</td></tr>
     *     <tr><td><code>Fcy;</code></td><td>U+00424</td><td>Ф</td></tr>
     *     <tr><td><code>fcy;</code></td><td>U+00444</td><td>ф</td></tr>
     *     <tr><td><code>female;</code></td><td>U+02640</td><td>♀</td></tr>
     *     <tr><td><code>ffilig;</code></td><td>U+0FB03</td><td>ﬃ</td></tr>
     *     <tr><td><code>fflig;</code></td><td>U+0FB00</td><td>ﬀ</td></tr>
     *     <tr><td><code>ffllig;</code></td><td>U+0FB04</td><td>ﬄ</td></tr>
     *     <tr><td><code>Ffr;</code></td><td>U+1D509</td><td>𝔉</td></tr>
     *     <tr><td><code>ffr;</code></td><td>U+1D523</td><td>𝔣</td></tr>
     *     <tr><td><code>filig;</code></td><td>U+0FB01</td><td>ﬁ</td></tr>
     *     <tr><td><code>FilledSmallSquare;</code></td><td>U+025FC</td><td>◼</td></tr>
     *     <tr><td><code>FilledVerySmallSquare;</code></td><td>U+025AA</td><td>▪</td></tr>
     *     <tr><td><code>fjlig;</code></td><td>U+00066 U+0006A</td><td>fj</td></tr>
     *     <tr><td><code>flat;</code></td><td>U+0266D</td><td>♭</td></tr>
     *     <tr><td><code>fllig;</code></td><td>U+0FB02</td><td>ﬂ</td></tr>
     *     <tr><td><code>fltns;</code></td><td>U+025B1</td><td>▱</td></tr>
     *     <tr><td><code>fnof;</code></td><td>U+00192</td><td>ƒ</td></tr>
     *     <tr><td><code>Fopf;</code></td><td>U+1D53D</td><td>𝔽</td></tr>
     *     <tr><td><code>fopf;</code></td><td>U+1D557</td><td>𝕗</td></tr>
     *     <tr><td><code>ForAll;</code></td><td>U+02200</td><td>∀</td></tr>
     *     <tr><td><code>forall;</code></td><td>U+02200</td><td>∀</td></tr>
     *     <tr><td><code>fork;</code></td><td>U+022D4</td><td>⋔</td></tr>
     *     <tr><td><code>forkv;</code></td><td>U+02AD9</td><td>⫙</td></tr>
     *     <tr><td><code>Fouriertrf;</code></td><td>U+02131</td><td>ℱ</td></tr>
     *     <tr><td><code>fpartint;</code></td><td>U+02A0D</td><td>⨍</td></tr>
     *     <tr><td><code>frac12;</code></td><td>U+000BD</td><td>½</td></tr>
     *     <tr><td><code>frac12</code></td><td>U+000BD</td><td>½</td></tr>
     *     <tr><td><code>frac13;</code></td><td>U+02153</td><td>⅓</td></tr>
     *     <tr><td><code>frac14;</code></td><td>U+000BC</td><td>¼</td></tr>
     *     <tr><td><code>frac14</code></td><td>U+000BC</td><td>¼</td></tr>
     *     <tr><td><code>frac15;</code></td><td>U+02155</td><td>⅕</td></tr>
     *     <tr><td><code>frac16;</code></td><td>U+02159</td><td>⅙</td></tr>
     *     <tr><td><code>frac18;</code></td><td>U+0215B</td><td>⅛</td></tr>
     *     <tr><td><code>frac23;</code></td><td>U+02154</td><td>⅔</td></tr>
     *     <tr><td><code>frac25;</code></td><td>U+02156</td><td>⅖</td></tr>
     *     <tr><td><code>frac34;</code></td><td>U+000BE</td><td>¾</td></tr>
     *     <tr><td><code>frac34</code></td><td>U+000BE</td><td>¾</td></tr>
     *     <tr><td><code>frac35;</code></td><td>U+02157</td><td>⅗</td></tr>
     *     <tr><td><code>frac38;</code></td><td>U+0215C</td><td>⅜</td></tr>
     *     <tr><td><code>frac45;</code></td><td>U+02158</td><td>⅘</td></tr>
     *     <tr><td><code>frac56;</code></td><td>U+0215A</td><td>⅚</td></tr>
     *     <tr><td><code>frac58;</code></td><td>U+0215D</td><td>⅝</td></tr>
     *     <tr><td><code>frac78;</code></td><td>U+0215E</td><td>⅞</td></tr>
     *     <tr><td><code>frasl;</code></td><td>U+02044</td><td>⁄</td></tr>
     *     <tr><td><code>frown;</code></td><td>U+02322</td><td>⌢</td></tr>
     *     <tr><td><code>Fscr;</code></td><td>U+02131</td><td>ℱ</td></tr>
     *     <tr><td><code>fscr;</code></td><td>U+1D4BB</td><td>𝒻</td></tr>
     *     <tr><td><code>gacute;</code></td><td>U+001F5</td><td>ǵ</td></tr>
     *     <tr><td><code>Gamma;</code></td><td>U+00393</td><td>Γ</td></tr>
     *     <tr><td><code>gamma;</code></td><td>U+003B3</td><td>γ</td></tr>
     *     <tr><td><code>Gammad;</code></td><td>U+003DC</td><td>Ϝ</td></tr>
     *     <tr><td><code>gammad;</code></td><td>U+003DD</td><td>ϝ</td></tr>
     *     <tr><td><code>gap;</code></td><td>U+02A86</td><td>⪆</td></tr>
     *     <tr><td><code>Gbreve;</code></td><td>U+0011E</td><td>Ğ</td></tr>
     *     <tr><td><code>gbreve;</code></td><td>U+0011F</td><td>ğ</td></tr>
     *     <tr><td><code>Gcedil;</code></td><td>U+00122</td><td>Ģ</td></tr>
     *     <tr><td><code>Gcirc;</code></td><td>U+0011C</td><td>Ĝ</td></tr>
     *     <tr><td><code>gcirc;</code></td><td>U+0011D</td><td>ĝ</td></tr>
     *     <tr><td><code>Gcy;</code></td><td>U+00413</td><td>Г</td></tr>
     *     <tr><td><code>gcy;</code></td><td>U+00433</td><td>г</td></tr>
     *     <tr><td><code>Gdot;</code></td><td>U+00120</td><td>Ġ</td></tr>
     *     <tr><td><code>gdot;</code></td><td>U+00121</td><td>ġ</td></tr>
     *     <tr><td><code>gE;</code></td><td>U+02267</td><td>≧</td></tr>
     *     <tr><td><code>ge;</code></td><td>U+02265</td><td>≥</td></tr>
     *     <tr><td><code>gEl;</code></td><td>U+02A8C</td><td>⪌</td></tr>
     *     <tr><td><code>gel;</code></td><td>U+022DB</td><td>⋛</td></tr>
     *     <tr><td><code>geq;</code></td><td>U+02265</td><td>≥</td></tr>
     *     <tr><td><code>geqq;</code></td><td>U+02267</td><td>≧</td></tr>
     *     <tr><td><code>geqslant;</code></td><td>U+02A7E</td><td>⩾</td></tr>
     *     <tr><td><code>ges;</code></td><td>U+02A7E</td><td>⩾</td></tr>
     *     <tr><td><code>gescc;</code></td><td>U+02AA9</td><td>⪩</td></tr>
     *     <tr><td><code>gesdot;</code></td><td>U+02A80</td><td>⪀</td></tr>
     *     <tr><td><code>gesdoto;</code></td><td>U+02A82</td><td>⪂</td></tr>
     *     <tr><td><code>gesdotol;</code></td><td>U+02A84</td><td>⪄</td></tr>
     *     <tr><td><code>gesl;</code></td><td>U+022DB U+0FE00</td><td>⋛︀</td></tr>
     *     <tr><td><code>gesles;</code></td><td>U+02A94</td><td>⪔</td></tr>
     *     <tr><td><code>Gfr;</code></td><td>U+1D50A</td><td>𝔊</td></tr>
     *     <tr><td><code>gfr;</code></td><td>U+1D524</td><td>𝔤</td></tr>
     *     <tr><td><code>Gg;</code></td><td>U+022D9</td><td>⋙</td></tr>
     *     <tr><td><code>gg;</code></td><td>U+0226B</td><td>≫</td></tr>
     *     <tr><td><code>ggg;</code></td><td>U+022D9</td><td>⋙</td></tr>
     *     <tr><td><code>gimel;</code></td><td>U+02137</td><td>ℷ</td></tr>
     *     <tr><td><code>GJcy;</code></td><td>U+00403</td><td>Ѓ</td></tr>
     *     <tr><td><code>gjcy;</code></td><td>U+00453</td><td>ѓ</td></tr>
     *     <tr><td><code>gl;</code></td><td>U+02277</td><td>≷</td></tr>
     *     <tr><td><code>gla;</code></td><td>U+02AA5</td><td>⪥</td></tr>
     *     <tr><td><code>glE;</code></td><td>U+02A92</td><td>⪒</td></tr>
     *     <tr><td><code>glj;</code></td><td>U+02AA4</td><td>⪤</td></tr>
     *     <tr><td><code>gnap;</code></td><td>U+02A8A</td><td>⪊</td></tr>
     *     <tr><td><code>gnapprox;</code></td><td>U+02A8A</td><td>⪊</td></tr>
     *     <tr><td><code>gnE;</code></td><td>U+02269</td><td>≩</td></tr>
     *     <tr><td><code>gne;</code></td><td>U+02A88</td><td>⪈</td></tr>
     *     <tr><td><code>gneq;</code></td><td>U+02A88</td><td>⪈</td></tr>
     *     <tr><td><code>gneqq;</code></td><td>U+02269</td><td>≩</td></tr>
     *     <tr><td><code>gnsim;</code></td><td>U+022E7</td><td>⋧</td></tr>
     *     <tr><td><code>Gopf;</code></td><td>U+1D53E</td><td>𝔾</td></tr>
     *     <tr><td><code>gopf;</code></td><td>U+1D558</td><td>𝕘</td></tr>
     *     <tr><td><code>grave;</code></td><td>U+00060</td><td>`</td></tr>
     *     <tr><td><code>GreaterEqual;</code></td><td>U+02265</td><td>≥</td></tr>
     *     <tr><td><code>GreaterEqualLess;</code></td><td>U+022DB</td><td>⋛</td></tr>
     *     <tr><td><code>GreaterFullEqual;</code></td><td>U+02267</td><td>≧</td></tr>
     *     <tr><td><code>GreaterGreater;</code></td><td>U+02AA2</td><td>⪢</td></tr>
     *     <tr><td><code>GreaterLess;</code></td><td>U+02277</td><td>≷</td></tr>
     *     <tr><td><code>GreaterSlantEqual;</code></td><td>U+02A7E</td><td>⩾</td></tr>
     *     <tr><td><code>GreaterTilde;</code></td><td>U+02273</td><td>≳</td></tr>
     *     <tr><td><code>Gscr;</code></td><td>U+1D4A2</td><td>𝒢</td></tr>
     *     <tr><td><code>gscr;</code></td><td>U+0210A</td><td>ℊ</td></tr>
     *     <tr><td><code>gsim;</code></td><td>U+02273</td><td>≳</td></tr>
     *     <tr><td><code>gsime;</code></td><td>U+02A8E</td><td>⪎</td></tr>
     *     <tr><td><code>gsiml;</code></td><td>U+02A90</td><td>⪐</td></tr>
     *     <tr><td><code>GT;</code></td><td>U+0003E</td><td>&gt;</td></tr>
     *     <tr><td><code>GT</code></td><td>U+0003E</td><td>&gt;</td></tr>
     *     <tr><td><code>Gt;</code></td><td>U+0226B</td><td>≫</td></tr>
     *     <tr><td><code>gt;</code></td><td>U+0003E</td><td>&gt;</td></tr>
     *     <tr><td><code>gt</code></td><td>U+0003E</td><td>&gt;</td></tr>
     *     <tr><td><code>gtcc;</code></td><td>U+02AA7</td><td>⪧</td></tr>
     *     <tr><td><code>gtcir;</code></td><td>U+02A7A</td><td>⩺</td></tr>
     *     <tr><td><code>gtdot;</code></td><td>U+022D7</td><td>⋗</td></tr>
     *     <tr><td><code>gtlPar;</code></td><td>U+02995</td><td>⦕</td></tr>
     *     <tr><td><code>gtquest;</code></td><td>U+02A7C</td><td>⩼</td></tr>
     *     <tr><td><code>gtrapprox;</code></td><td>U+02A86</td><td>⪆</td></tr>
     *     <tr><td><code>gtrarr;</code></td><td>U+02978</td><td>⥸</td></tr>
     *     <tr><td><code>gtrdot;</code></td><td>U+022D7</td><td>⋗</td></tr>
     *     <tr><td><code>gtreqless;</code></td><td>U+022DB</td><td>⋛</td></tr>
     *     <tr><td><code>gtreqqless;</code></td><td>U+02A8C</td><td>⪌</td></tr>
     *     <tr><td><code>gtrless;</code></td><td>U+02277</td><td>≷</td></tr>
     *     <tr><td><code>gtrsim;</code></td><td>U+02273</td><td>≳</td></tr>
     *     <tr><td><code>gvertneqq;</code></td><td>U+02269 U+0FE00</td><td>≩︀</td></tr>
     *     <tr><td><code>gvnE;</code></td><td>U+02269 U+0FE00</td><td>≩︀</td></tr>
     *     <tr><td><code>Hacek;</code></td><td>U+002C7</td><td>ˇ</td></tr>
     *     <tr><td><code>hairsp;</code></td><td>U+0200A</td><td> </td></tr>
     *     <tr><td><code>half;</code></td><td>U+000BD</td><td>½</td></tr>
     *     <tr><td><code>hamilt;</code></td><td>U+0210B</td><td>ℋ</td></tr>
     *     <tr><td><code>HARDcy;</code></td><td>U+0042A</td><td>Ъ</td></tr>
     *     <tr><td><code>hardcy;</code></td><td>U+0044A</td><td>ъ</td></tr>
     *     <tr><td><code>hArr;</code></td><td>U+021D4</td><td>⇔</td></tr>
     *     <tr><td><code>harr;</code></td><td>U+02194</td><td>↔</td></tr>
     *     <tr><td><code>harrcir;</code></td><td>U+02948</td><td>⥈</td></tr>
     *     <tr><td><code>harrw;</code></td><td>U+021AD</td><td>↭</td></tr>
     *     <tr><td><code>Hat;</code></td><td>U+0005E</td><td>^</td></tr>
     *     <tr><td><code>hbar;</code></td><td>U+0210F</td><td>ℏ</td></tr>
     *     <tr><td><code>Hcirc;</code></td><td>U+00124</td><td>Ĥ</td></tr>
     *     <tr><td><code>hcirc;</code></td><td>U+00125</td><td>ĥ</td></tr>
     *     <tr><td><code>hearts;</code></td><td>U+02665</td><td>♥</td></tr>
     *     <tr><td><code>heartsuit;</code></td><td>U+02665</td><td>♥</td></tr>
     *     <tr><td><code>hellip;</code></td><td>U+02026</td><td>…</td></tr>
     *     <tr><td><code>hercon;</code></td><td>U+022B9</td><td>⊹</td></tr>
     *     <tr><td><code>Hfr;</code></td><td>U+0210C</td><td>ℌ</td></tr>
     *     <tr><td><code>hfr;</code></td><td>U+1D525</td><td>𝔥</td></tr>
     *     <tr><td><code>HilbertSpace;</code></td><td>U+0210B</td><td>ℋ</td></tr>
     *     <tr><td><code>hksearow;</code></td><td>U+02925</td><td>⤥</td></tr>
     *     <tr><td><code>hkswarow;</code></td><td>U+02926</td><td>⤦</td></tr>
     *     <tr><td><code>hoarr;</code></td><td>U+021FF</td><td>⇿</td></tr>
     *     <tr><td><code>homtht;</code></td><td>U+0223B</td><td>∻</td></tr>
     *     <tr><td><code>hookleftarrow;</code></td><td>U+021A9</td><td>↩</td></tr>
     *     <tr><td><code>hookrightarrow;</code></td><td>U+021AA</td><td>↪</td></tr>
     *     <tr><td><code>Hopf;</code></td><td>U+0210D</td><td>ℍ</td></tr>
     *     <tr><td><code>hopf;</code></td><td>U+1D559</td><td>𝕙</td></tr>
     *     <tr><td><code>horbar;</code></td><td>U+02015</td><td>―</td></tr>
     *     <tr><td><code>HorizontalLine;</code></td><td>U+02500</td><td>─</td></tr>
     *     <tr><td><code>Hscr;</code></td><td>U+0210B</td><td>ℋ</td></tr>
     *     <tr><td><code>hscr;</code></td><td>U+1D4BD</td><td>𝒽</td></tr>
     *     <tr><td><code>hslash;</code></td><td>U+0210F</td><td>ℏ</td></tr>
     *     <tr><td><code>Hstrok;</code></td><td>U+00126</td><td>Ħ</td></tr>
     *     <tr><td><code>hstrok;</code></td><td>U+00127</td><td>ħ</td></tr>
     *     <tr><td><code>HumpDownHump;</code></td><td>U+0224E</td><td>≎</td></tr>
     *     <tr><td><code>HumpEqual;</code></td><td>U+0224F</td><td>≏</td></tr>
     *     <tr><td><code>hybull;</code></td><td>U+02043</td><td>⁃</td></tr>
     *     <tr><td><code>hyphen;</code></td><td>U+02010</td><td>‐</td></tr>
     *     <tr><td><code>Iacute;</code></td><td>U+000CD</td><td>Í</td></tr>
     *     <tr><td><code>Iacute</code></td><td>U+000CD</td><td>Í</td></tr>
     *     <tr><td><code>iacute;</code></td><td>U+000ED</td><td>í</td></tr>
     *     <tr><td><code>iacute</code></td><td>U+000ED</td><td>í</td></tr>
     *     <tr><td><code>ic;</code></td><td>U+02063</td><td>⁣</td></tr>
     *     <tr><td><code>Icirc;</code></td><td>U+000CE</td><td>Î</td></tr>
     *     <tr><td><code>Icirc</code></td><td>U+000CE</td><td>Î</td></tr>
     *     <tr><td><code>icirc;</code></td><td>U+000EE</td><td>î</td></tr>
     *     <tr><td><code>icirc</code></td><td>U+000EE</td><td>î</td></tr>
     *     <tr><td><code>Icy;</code></td><td>U+00418</td><td>И</td></tr>
     *     <tr><td><code>icy;</code></td><td>U+00438</td><td>и</td></tr>
     *     <tr><td><code>Idot;</code></td><td>U+00130</td><td>İ</td></tr>
     *     <tr><td><code>IEcy;</code></td><td>U+00415</td><td>Е</td></tr>
     *     <tr><td><code>iecy;</code></td><td>U+00435</td><td>е</td></tr>
     *     <tr><td><code>iexcl;</code></td><td>U+000A1</td><td>¡</td></tr>
     *     <tr><td><code>iexcl</code></td><td>U+000A1</td><td>¡</td></tr>
     *     <tr><td><code>iff;</code></td><td>U+021D4</td><td>⇔</td></tr>
     *     <tr><td><code>Ifr;</code></td><td>U+02111</td><td>ℑ</td></tr>
     *     <tr><td><code>ifr;</code></td><td>U+1D526</td><td>𝔦</td></tr>
     *     <tr><td><code>Igrave;</code></td><td>U+000CC</td><td>Ì</td></tr>
     *     <tr><td><code>Igrave</code></td><td>U+000CC</td><td>Ì</td></tr>
     *     <tr><td><code>igrave;</code></td><td>U+000EC</td><td>ì</td></tr>
     *     <tr><td><code>igrave</code></td><td>U+000EC</td><td>ì</td></tr>
     *     <tr><td><code>ii;</code></td><td>U+02148</td><td>ⅈ</td></tr>
     *     <tr><td><code>iiiint;</code></td><td>U+02A0C</td><td>⨌</td></tr>
     *     <tr><td><code>iiint;</code></td><td>U+0222D</td><td>∭</td></tr>
     *     <tr><td><code>iinfin;</code></td><td>U+029DC</td><td>⧜</td></tr>
     *     <tr><td><code>iiota;</code></td><td>U+02129</td><td>℩</td></tr>
     *     <tr><td><code>IJlig;</code></td><td>U+00132</td><td>Ĳ</td></tr>
     *     <tr><td><code>ijlig;</code></td><td>U+00133</td><td>ĳ</td></tr>
     *     <tr><td><code>Im;</code></td><td>U+02111</td><td>ℑ</td></tr>
     *     <tr><td><code>Imacr;</code></td><td>U+0012A</td><td>Ī</td></tr>
     *     <tr><td><code>imacr;</code></td><td>U+0012B</td><td>ī</td></tr>
     *     <tr><td><code>image;</code></td><td>U+02111</td><td>ℑ</td></tr>
     *     <tr><td><code>ImaginaryI;</code></td><td>U+02148</td><td>ⅈ</td></tr>
     *     <tr><td><code>imagline;</code></td><td>U+02110</td><td>ℐ</td></tr>
     *     <tr><td><code>imagpart;</code></td><td>U+02111</td><td>ℑ</td></tr>
     *     <tr><td><code>imath;</code></td><td>U+00131</td><td>ı</td></tr>
     *     <tr><td><code>imof;</code></td><td>U+022B7</td><td>⊷</td></tr>
     *     <tr><td><code>imped;</code></td><td>U+001B5</td><td>Ƶ</td></tr>
     *     <tr><td><code>Implies;</code></td><td>U+021D2</td><td>⇒</td></tr>
     *     <tr><td><code>in;</code></td><td>U+02208</td><td>∈</td></tr>
     *     <tr><td><code>incare;</code></td><td>U+02105</td><td>℅</td></tr>
     *     <tr><td><code>infin;</code></td><td>U+0221E</td><td>∞</td></tr>
     *     <tr><td><code>infintie;</code></td><td>U+029DD</td><td>⧝</td></tr>
     *     <tr><td><code>inodot;</code></td><td>U+00131</td><td>ı</td></tr>
     *     <tr><td><code>Int;</code></td><td>U+0222C</td><td>∬</td></tr>
     *     <tr><td><code>int;</code></td><td>U+0222B</td><td>∫</td></tr>
     *     <tr><td><code>intcal;</code></td><td>U+022BA</td><td>⊺</td></tr>
     *     <tr><td><code>integers;</code></td><td>U+02124</td><td>ℤ</td></tr>
     *     <tr><td><code>Integral;</code></td><td>U+0222B</td><td>∫</td></tr>
     *     <tr><td><code>intercal;</code></td><td>U+022BA</td><td>⊺</td></tr>
     *     <tr><td><code>Intersection;</code></td><td>U+022C2</td><td>⋂</td></tr>
     *     <tr><td><code>intlarhk;</code></td><td>U+02A17</td><td>⨗</td></tr>
     *     <tr><td><code>intprod;</code></td><td>U+02A3C</td><td>⨼</td></tr>
     *     <tr><td><code>InvisibleComma;</code></td><td>U+02063</td><td>⁣</td></tr>
     *     <tr><td><code>InvisibleTimes;</code></td><td>U+02062</td><td>⁢</td></tr>
     *     <tr><td><code>IOcy;</code></td><td>U+00401</td><td>Ё</td></tr>
     *     <tr><td><code>iocy;</code></td><td>U+00451</td><td>ё</td></tr>
     *     <tr><td><code>Iogon;</code></td><td>U+0012E</td><td>Į</td></tr>
     *     <tr><td><code>iogon;</code></td><td>U+0012F</td><td>į</td></tr>
     *     <tr><td><code>Iopf;</code></td><td>U+1D540</td><td>𝕀</td></tr>
     *     <tr><td><code>iopf;</code></td><td>U+1D55A</td><td>𝕚</td></tr>
     *     <tr><td><code>Iota;</code></td><td>U+00399</td><td>Ι</td></tr>
     *     <tr><td><code>iota;</code></td><td>U+003B9</td><td>ι</td></tr>
     *     <tr><td><code>iprod;</code></td><td>U+02A3C</td><td>⨼</td></tr>
     *     <tr><td><code>iquest;</code></td><td>U+000BF</td><td>¿</td></tr>
     *     <tr><td><code>iquest</code></td><td>U+000BF</td><td>¿</td></tr>
     *     <tr><td><code>Iscr;</code></td><td>U+02110</td><td>ℐ</td></tr>
     *     <tr><td><code>iscr;</code></td><td>U+1D4BE</td><td>𝒾</td></tr>
     *     <tr><td><code>isin;</code></td><td>U+02208</td><td>∈</td></tr>
     *     <tr><td><code>isindot;</code></td><td>U+022F5</td><td>⋵</td></tr>
     *     <tr><td><code>isinE;</code></td><td>U+022F9</td><td>⋹</td></tr>
     *     <tr><td><code>isins;</code></td><td>U+022F4</td><td>⋴</td></tr>
     *     <tr><td><code>isinsv;</code></td><td>U+022F3</td><td>⋳</td></tr>
     *     <tr><td><code>isinv;</code></td><td>U+02208</td><td>∈</td></tr>
     *     <tr><td><code>it;</code></td><td>U+02062</td><td>⁢</td></tr>
     *     <tr><td><code>Itilde;</code></td><td>U+00128</td><td>Ĩ</td></tr>
     *     <tr><td><code>itilde;</code></td><td>U+00129</td><td>ĩ</td></tr>
     *     <tr><td><code>Iukcy;</code></td><td>U+00406</td><td>І</td></tr>
     *     <tr><td><code>iukcy;</code></td><td>U+00456</td><td>і</td></tr>
     *     <tr><td><code>Iuml;</code></td><td>U+000CF</td><td>Ï</td></tr>
     *     <tr><td><code>Iuml</code></td><td>U+000CF</td><td>Ï</td></tr>
     *     <tr><td><code>iuml;</code></td><td>U+000EF</td><td>ï</td></tr>
     *     <tr><td><code>iuml</code></td><td>U+000EF</td><td>ï</td></tr>
     *     <tr><td><code>Jcirc;</code></td><td>U+00134</td><td>Ĵ</td></tr>
     *     <tr><td><code>jcirc;</code></td><td>U+00135</td><td>ĵ</td></tr>
     *     <tr><td><code>Jcy;</code></td><td>U+00419</td><td>Й</td></tr>
     *     <tr><td><code>jcy;</code></td><td>U+00439</td><td>й</td></tr>
     *     <tr><td><code>Jfr;</code></td><td>U+1D50D</td><td>𝔍</td></tr>
     *     <tr><td><code>jfr;</code></td><td>U+1D527</td><td>𝔧</td></tr>
     *     <tr><td><code>jmath;</code></td><td>U+00237</td><td>ȷ</td></tr>
     *     <tr><td><code>Jopf;</code></td><td>U+1D541</td><td>𝕁</td></tr>
     *     <tr><td><code>jopf;</code></td><td>U+1D55B</td><td>𝕛</td></tr>
     *     <tr><td><code>Jscr;</code></td><td>U+1D4A5</td><td>𝒥</td></tr>
     *     <tr><td><code>jscr;</code></td><td>U+1D4BF</td><td>𝒿</td></tr>
     *     <tr><td><code>Jsercy;</code></td><td>U+00408</td><td>Ј</td></tr>
     *     <tr><td><code>jsercy;</code></td><td>U+00458</td><td>ј</td></tr>
     *     <tr><td><code>Jukcy;</code></td><td>U+00404</td><td>Є</td></tr>
     *     <tr><td><code>jukcy;</code></td><td>U+00454</td><td>є</td></tr>
     *     <tr><td><code>Kappa;</code></td><td>U+0039A</td><td>Κ</td></tr>
     *     <tr><td><code>kappa;</code></td><td>U+003BA</td><td>κ</td></tr>
     *     <tr><td><code>kappav;</code></td><td>U+003F0</td><td>ϰ</td></tr>
     *     <tr><td><code>Kcedil;</code></td><td>U+00136</td><td>Ķ</td></tr>
     *     <tr><td><code>kcedil;</code></td><td>U+00137</td><td>ķ</td></tr>
     *     <tr><td><code>Kcy;</code></td><td>U+0041A</td><td>К</td></tr>
     *     <tr><td><code>kcy;</code></td><td>U+0043A</td><td>к</td></tr>
     *     <tr><td><code>Kfr;</code></td><td>U+1D50E</td><td>𝔎</td></tr>
     *     <tr><td><code>kfr;</code></td><td>U+1D528</td><td>𝔨</td></tr>
     *     <tr><td><code>kgreen;</code></td><td>U+00138</td><td>ĸ</td></tr>
     *     <tr><td><code>KHcy;</code></td><td>U+00425</td><td>Х</td></tr>
     *     <tr><td><code>khcy;</code></td><td>U+00445</td><td>х</td></tr>
     *     <tr><td><code>KJcy;</code></td><td>U+0040C</td><td>Ќ</td></tr>
     *     <tr><td><code>kjcy;</code></td><td>U+0045C</td><td>ќ</td></tr>
     *     <tr><td><code>Kopf;</code></td><td>U+1D542</td><td>𝕂</td></tr>
     *     <tr><td><code>kopf;</code></td><td>U+1D55C</td><td>𝕜</td></tr>
     *     <tr><td><code>Kscr;</code></td><td>U+1D4A6</td><td>𝒦</td></tr>
     *     <tr><td><code>kscr;</code></td><td>U+1D4C0</td><td>𝓀</td></tr>
     *     <tr><td><code>lAarr;</code></td><td>U+021DA</td><td>⇚</td></tr>
     *     <tr><td><code>Lacute;</code></td><td>U+00139</td><td>Ĺ</td></tr>
     *     <tr><td><code>lacute;</code></td><td>U+0013A</td><td>ĺ</td></tr>
     *     <tr><td><code>laemptyv;</code></td><td>U+029B4</td><td>⦴</td></tr>
     *     <tr><td><code>lagran;</code></td><td>U+02112</td><td>ℒ</td></tr>
     *     <tr><td><code>Lambda;</code></td><td>U+0039B</td><td>Λ</td></tr>
     *     <tr><td><code>lambda;</code></td><td>U+003BB</td><td>λ</td></tr>
     *     <tr><td><code>Lang;</code></td><td>U+027EA</td><td>⟪</td></tr>
     *     <tr><td><code>lang;</code></td><td>U+027E8</td><td>⟨</td></tr>
     *     <tr><td><code>langd;</code></td><td>U+02991</td><td>⦑</td></tr>
     *     <tr><td><code>langle;</code></td><td>U+027E8</td><td>〈</td></tr>
     *     <tr><td><code>lap;</code></td><td>U+02A85</td><td>⪅</td></tr>
     *     <tr><td><code>Laplacetrf;</code></td><td>U+02112</td><td>ℒ</td></tr>
     *     <tr><td><code>laquo;</code></td><td>U+000AB</td><td>«</td></tr>
     *     <tr><td><code>laquo</code></td><td>U+000AB</td><td>«</td></tr>
     *     <tr><td><code>Larr;</code></td><td>U+0219E</td><td>↞</td></tr>
     *     <tr><td><code>lArr;</code></td><td>U+021D0</td><td>⇐</td></tr>
     *     <tr><td><code>larr;</code></td><td>U+02190</td><td>←</td></tr>
     *     <tr><td><code>larrb;</code></td><td>U+021E4</td><td>⇤</td></tr>
     *     <tr><td><code>larrbfs;</code></td><td>U+0291F</td><td>⤟</td></tr>
     *     <tr><td><code>larrfs;</code></td><td>U+0291D</td><td>⤝</td></tr>
     *     <tr><td><code>larrhk;</code></td><td>U+021A9</td><td>↩</td></tr>
     *     <tr><td><code>larrlp;</code></td><td>U+021AB</td><td>↫</td></tr>
     *     <tr><td><code>larrpl;</code></td><td>U+02939</td><td>⤹</td></tr>
     *     <tr><td><code>larrsim;</code></td><td>U+02973</td><td>⥳</td></tr>
     *     <tr><td><code>larrtl;</code></td><td>U+021A2</td><td>↢</td></tr>
     *     <tr><td><code>lat;</code></td><td>U+02AAB</td><td>⪫</td></tr>
     *     <tr><td><code>lAtail;</code></td><td>U+0291B</td><td>⤛</td></tr>
     *     <tr><td><code>latail;</code></td><td>U+02919</td><td>⤙</td></tr>
     *     <tr><td><code>late;</code></td><td>U+02AAD</td><td>⪭</td></tr>
     *     <tr><td><code>lates;</code></td><td>U+02AAD U+0FE00</td><td>⪭︀</td></tr>
     *     <tr><td><code>lBarr;</code></td><td>U+0290E</td><td>⤎</td></tr>
     *     <tr><td><code>lbarr;</code></td><td>U+0290C</td><td>⤌</td></tr>
     *     <tr><td><code>lbbrk;</code></td><td>U+02772</td><td>❲</td></tr>
     *     <tr><td><code>lbrace;</code></td><td>U+0007B</td><td>{</td></tr>
     *     <tr><td><code>lbrack;</code></td><td>U+0005B</td><td>[</td></tr>
     *     <tr><td><code>lbrke;</code></td><td>U+0298B</td><td>⦋</td></tr>
     *     <tr><td><code>lbrksld;</code></td><td>U+0298F</td><td>⦏</td></tr>
     *     <tr><td><code>lbrkslu;</code></td><td>U+0298D</td><td>⦍</td></tr>
     *     <tr><td><code>Lcaron;</code></td><td>U+0013D</td><td>Ľ</td></tr>
     *     <tr><td><code>lcaron;</code></td><td>U+0013E</td><td>ľ</td></tr>
     *     <tr><td><code>Lcedil;</code></td><td>U+0013B</td><td>Ļ</td></tr>
     *     <tr><td><code>lcedil;</code></td><td>U+0013C</td><td>ļ</td></tr>
     *     <tr><td><code>lceil;</code></td><td>U+02308</td><td>⌈</td></tr>
     *     <tr><td><code>lcub;</code></td><td>U+0007B</td><td>{</td></tr>
     *     <tr><td><code>Lcy;</code></td><td>U+0041B</td><td>Л</td></tr>
     *     <tr><td><code>lcy;</code></td><td>U+0043B</td><td>л</td></tr>
     *     <tr><td><code>ldca;</code></td><td>U+02936</td><td>⤶</td></tr>
     *     <tr><td><code>ldquo;</code></td><td>U+0201C</td><td>“</td></tr>
     *     <tr><td><code>ldquor;</code></td><td>U+0201E</td><td>„</td></tr>
     *     <tr><td><code>ldrdhar;</code></td><td>U+02967</td><td>⥧</td></tr>
     *     <tr><td><code>ldrushar;</code></td><td>U+0294B</td><td>⥋</td></tr>
     *     <tr><td><code>ldsh;</code></td><td>U+021B2</td><td>↲</td></tr>
     *     <tr><td><code>lE;</code></td><td>U+02266</td><td>≦</td></tr>
     *     <tr><td><code>le;</code></td><td>U+02264</td><td>≤</td></tr>
     *     <tr><td><code>LeftAngleBracket;</code></td><td>U+027E8</td><td>〈</td></tr>
     *     <tr><td><code>LeftArrow;</code></td><td>U+02190</td><td>←</td></tr>
     *     <tr><td><code>Leftarrow;</code></td><td>U+021D0</td><td>⇐</td></tr>
     *     <tr><td><code>leftarrow;</code></td><td>U+02190</td><td>←</td></tr>
     *     <tr><td><code>LeftArrowBar;</code></td><td>U+021E4</td><td>⇤</td></tr>
     *     <tr><td><code>LeftArrowRightArrow;</code></td><td>U+021C6</td><td>⇆</td></tr>
     *     <tr><td><code>leftarrowtail;</code></td><td>U+021A2</td><td>↢</td></tr>
     *     <tr><td><code>LeftCeiling;</code></td><td>U+02308</td><td>⌈</td></tr>
     *     <tr><td><code>LeftDoubleBracket;</code></td><td>U+027E6</td><td>⟦</td></tr>
     *     <tr><td><code>LeftDownTeeVector;</code></td><td>U+02961</td><td>⥡</td></tr>
     *     <tr><td><code>LeftDownVector;</code></td><td>U+021C3</td><td>⇃</td></tr>
     *     <tr><td><code>LeftDownVectorBar;</code></td><td>U+02959</td><td>⥙</td></tr>
     *     <tr><td><code>LeftFloor;</code></td><td>U+0230A</td><td>⌊</td></tr>
     *     <tr><td><code>leftharpoondown;</code></td><td>U+021BD</td><td>↽</td></tr>
     *     <tr><td><code>leftharpoonup;</code></td><td>U+021BC</td><td>↼</td></tr>
     *     <tr><td><code>leftleftarrows;</code></td><td>U+021C7</td><td>⇇</td></tr>
     *     <tr><td><code>LeftRightArrow;</code></td><td>U+02194</td><td>↔</td></tr>
     *     <tr><td><code>Leftrightarrow;</code></td><td>U+021D4</td><td>⇔</td></tr>
     *     <tr><td><code>leftrightarrow;</code></td><td>U+02194</td><td>↔</td></tr>
     *     <tr><td><code>leftrightarrows;</code></td><td>U+021C6</td><td>⇆</td></tr>
     *     <tr><td><code>leftrightharpoons;</code></td><td>U+021CB</td><td>⇋</td></tr>
     *     <tr><td><code>leftrightsquigarrow;</code></td><td>U+021AD</td><td>↭</td></tr>
     *     <tr><td><code>LeftRightVector;</code></td><td>U+0294E</td><td>⥎</td></tr>
     *     <tr><td><code>LeftTee;</code></td><td>U+022A3</td><td>⊣</td></tr>
     *     <tr><td><code>LeftTeeArrow;</code></td><td>U+021A4</td><td>↤</td></tr>
     *     <tr><td><code>LeftTeeVector;</code></td><td>U+0295A</td><td>⥚</td></tr>
     *     <tr><td><code>leftthreetimes;</code></td><td>U+022CB</td><td>⋋</td></tr>
     *     <tr><td><code>LeftTriangle;</code></td><td>U+022B2</td><td>⊲</td></tr>
     *     <tr><td><code>LeftTriangleBar;</code></td><td>U+029CF</td><td>⧏</td></tr>
     *     <tr><td><code>LeftTriangleEqual;</code></td><td>U+022B4</td><td>⊴</td></tr>
     *     <tr><td><code>LeftUpDownVector;</code></td><td>U+02951</td><td>⥑</td></tr>
     *     <tr><td><code>LeftUpTeeVector;</code></td><td>U+02960</td><td>⥠</td></tr>
     *     <tr><td><code>LeftUpVector;</code></td><td>U+021BF</td><td>↿</td></tr>
     *     <tr><td><code>LeftUpVectorBar;</code></td><td>U+02958</td><td>⥘</td></tr>
     *     <tr><td><code>LeftVector;</code></td><td>U+021BC</td><td>↼</td></tr>
     *     <tr><td><code>LeftVectorBar;</code></td><td>U+02952</td><td>⥒</td></tr>
     *     <tr><td><code>lEg;</code></td><td>U+02A8B</td><td>⪋</td></tr>
     *     <tr><td><code>leg;</code></td><td>U+022DA</td><td>⋚</td></tr>
     *     <tr><td><code>leq;</code></td><td>U+02264</td><td>≤</td></tr>
     *     <tr><td><code>leqq;</code></td><td>U+02266</td><td>≦</td></tr>
     *     <tr><td><code>leqslant;</code></td><td>U+02A7D</td><td>⩽</td></tr>
     *     <tr><td><code>les;</code></td><td>U+02A7D</td><td>⩽</td></tr>
     *     <tr><td><code>lescc;</code></td><td>U+02AA8</td><td>⪨</td></tr>
     *     <tr><td><code>lesdot;</code></td><td>U+02A7F</td><td>⩿</td></tr>
     *     <tr><td><code>lesdoto;</code></td><td>U+02A81</td><td>⪁</td></tr>
     *     <tr><td><code>lesdotor;</code></td><td>U+02A83</td><td>⪃</td></tr>
     *     <tr><td><code>lesg;</code></td><td>U+022DA U+0FE00</td><td>⋚︀</td></tr>
     *     <tr><td><code>lesges;</code></td><td>U+02A93</td><td>⪓</td></tr>
     *     <tr><td><code>lessapprox;</code></td><td>U+02A85</td><td>⪅</td></tr>
     *     <tr><td><code>lessdot;</code></td><td>U+022D6</td><td>⋖</td></tr>
     *     <tr><td><code>lesseqgtr;</code></td><td>U+022DA</td><td>⋚</td></tr>
     *     <tr><td><code>lesseqqgtr;</code></td><td>U+02A8B</td><td>⪋</td></tr>
     *     <tr><td><code>LessEqualGreater;</code></td><td>U+022DA</td><td>⋚</td></tr>
     *     <tr><td><code>LessFullEqual;</code></td><td>U+02266</td><td>≦</td></tr>
     *     <tr><td><code>LessGreater;</code></td><td>U+02276</td><td>≶</td></tr>
     *     <tr><td><code>lessgtr;</code></td><td>U+02276</td><td>≶</td></tr>
     *     <tr><td><code>LessLess;</code></td><td>U+02AA1</td><td>⪡</td></tr>
     *     <tr><td><code>lesssim;</code></td><td>U+02272</td><td>≲</td></tr>
     *     <tr><td><code>LessSlantEqual;</code></td><td>U+02A7D</td><td>⩽</td></tr>
     *     <tr><td><code>LessTilde;</code></td><td>U+02272</td><td>≲</td></tr>
     *     <tr><td><code>lfisht;</code></td><td>U+0297C</td><td>⥼</td></tr>
     *     <tr><td><code>lfloor;</code></td><td>U+0230A</td><td>⌊</td></tr>
     *     <tr><td><code>Lfr;</code></td><td>U+1D50F</td><td>𝔏</td></tr>
     *     <tr><td><code>lfr;</code></td><td>U+1D529</td><td>𝔩</td></tr>
     *     <tr><td><code>lg;</code></td><td>U+02276</td><td>≶</td></tr>
     *     <tr><td><code>lgE;</code></td><td>U+02A91</td><td>⪑</td></tr>
     *     <tr><td><code>lHar;</code></td><td>U+02962</td><td>⥢</td></tr>
     *     <tr><td><code>lhard;</code></td><td>U+021BD</td><td>↽</td></tr>
     *     <tr><td><code>lharu;</code></td><td>U+021BC</td><td>↼</td></tr>
     *     <tr><td><code>lharul;</code></td><td>U+0296A</td><td>⥪</td></tr>
     *     <tr><td><code>lhblk;</code></td><td>U+02584</td><td>▄</td></tr>
     *     <tr><td><code>LJcy;</code></td><td>U+00409</td><td>Љ</td></tr>
     *     <tr><td><code>ljcy;</code></td><td>U+00459</td><td>љ</td></tr>
     *     <tr><td><code>Ll;</code></td><td>U+022D8</td><td>⋘</td></tr>
     *     <tr><td><code>ll;</code></td><td>U+0226A</td><td>≪</td></tr>
     *     <tr><td><code>llarr;</code></td><td>U+021C7</td><td>⇇</td></tr>
     *     <tr><td><code>llcorner;</code></td><td>U+0231E</td><td>⌞</td></tr>
     *     <tr><td><code>Lleftarrow;</code></td><td>U+021DA</td><td>⇚</td></tr>
     *     <tr><td><code>llhard;</code></td><td>U+0296B</td><td>⥫</td></tr>
     *     <tr><td><code>lltri;</code></td><td>U+025FA</td><td>◺</td></tr>
     *     <tr><td><code>Lmidot;</code></td><td>U+0013F</td><td>Ŀ</td></tr>
     *     <tr><td><code>lmidot;</code></td><td>U+00140</td><td>ŀ</td></tr>
     *     <tr><td><code>lmoust;</code></td><td>U+023B0</td><td>⎰</td></tr>
     *     <tr><td><code>lmoustache;</code></td><td>U+023B0</td><td>⎰</td></tr>
     *     <tr><td><code>lnap;</code></td><td>U+02A89</td><td>⪉</td></tr>
     *     <tr><td><code>lnapprox;</code></td><td>U+02A89</td><td>⪉</td></tr>
     *     <tr><td><code>lnE;</code></td><td>U+02268</td><td>≨</td></tr>
     *     <tr><td><code>lne;</code></td><td>U+02A87</td><td>⪇</td></tr>
     *     <tr><td><code>lneq;</code></td><td>U+02A87</td><td>⪇</td></tr>
     *     <tr><td><code>lneqq;</code></td><td>U+02268</td><td>≨</td></tr>
     *     <tr><td><code>lnsim;</code></td><td>U+022E6</td><td>⋦</td></tr>
     *     <tr><td><code>loang;</code></td><td>U+027EC</td><td>⟬</td></tr>
     *     <tr><td><code>loarr;</code></td><td>U+021FD</td><td>⇽</td></tr>
     *     <tr><td><code>lobrk;</code></td><td>U+027E6</td><td>⟦</td></tr>
     *     <tr><td><code>LongLeftArrow;</code></td><td>U+027F5</td><td>⟵</td></tr>
     *     <tr><td><code>Longleftarrow;</code></td><td>U+027F8</td><td>⟸</td></tr>
     *     <tr><td><code>longleftarrow;</code></td><td>U+027F5</td><td>⟵</td></tr>
     *     <tr><td><code>LongLeftRightArrow;</code></td><td>U+027F7</td><td>⟷</td></tr>
     *     <tr><td><code>Longleftrightarrow;</code></td><td>U+027FA</td><td>⟺</td></tr>
     *     <tr><td><code>longleftrightarrow;</code></td><td>U+027F7</td><td>⟷</td></tr>
     *     <tr><td><code>longmapsto;</code></td><td>U+027FC</td><td>⟼</td></tr>
     *     <tr><td><code>LongRightArrow;</code></td><td>U+027F6</td><td>⟶</td></tr>
     *     <tr><td><code>Longrightarrow;</code></td><td>U+027F9</td><td>⟹</td></tr>
     *     <tr><td><code>longrightarrow;</code></td><td>U+027F6</td><td>⟶</td></tr>
     *     <tr><td><code>looparrowleft;</code></td><td>U+021AB</td><td>↫</td></tr>
     *     <tr><td><code>looparrowright;</code></td><td>U+021AC</td><td>↬</td></tr>
     *     <tr><td><code>lopar;</code></td><td>U+02985</td><td>⦅</td></tr>
     *     <tr><td><code>Lopf;</code></td><td>U+1D543</td><td>𝕃</td></tr>
     *     <tr><td><code>lopf;</code></td><td>U+1D55D</td><td>𝕝</td></tr>
     *     <tr><td><code>loplus;</code></td><td>U+02A2D</td><td>⨭</td></tr>
     *     <tr><td><code>lotimes;</code></td><td>U+02A34</td><td>⨴</td></tr>
     *     <tr><td><code>lowast;</code></td><td>U+02217</td><td>∗</td></tr>
     *     <tr><td><code>lowbar;</code></td><td>U+0005F</td><td>_</td></tr>
     *     <tr><td><code>LowerLeftArrow;</code></td><td>U+02199</td><td>↙</td></tr>
     *     <tr><td><code>LowerRightArrow;</code></td><td>U+02198</td><td>↘</td></tr>
     *     <tr><td><code>loz;</code></td><td>U+025CA</td><td>◊</td></tr>
     *     <tr><td><code>lozenge;</code></td><td>U+025CA</td><td>◊</td></tr>
     *     <tr><td><code>lozf;</code></td><td>U+029EB</td><td>⧫</td></tr>
     *     <tr><td><code>lpar;</code></td><td>U+00028</td><td>(</td></tr>
     *     <tr><td><code>lparlt;</code></td><td>U+02993</td><td>⦓</td></tr>
     *     <tr><td><code>lrarr;</code></td><td>U+021C6</td><td>⇆</td></tr>
     *     <tr><td><code>lrcorner;</code></td><td>U+0231F</td><td>⌟</td></tr>
     *     <tr><td><code>lrhar;</code></td><td>U+021CB</td><td>⇋</td></tr>
     *     <tr><td><code>lrhard;</code></td><td>U+0296D</td><td>⥭</td></tr>
     *     <tr><td><code>lrm;</code></td><td>U+0200E</td><td>‎</td></tr>
     *     <tr><td><code>lrtri;</code></td><td>U+022BF</td><td>⊿</td></tr>
     *     <tr><td><code>lsaquo;</code></td><td>U+02039</td><td>‹</td></tr>
     *     <tr><td><code>Lscr;</code></td><td>U+02112</td><td>ℒ</td></tr>
     *     <tr><td><code>lscr;</code></td><td>U+1D4C1</td><td>𝓁</td></tr>
     *     <tr><td><code>Lsh;</code></td><td>U+021B0</td><td>↰</td></tr>
     *     <tr><td><code>lsh;</code></td><td>U+021B0</td><td>↰</td></tr>
     *     <tr><td><code>lsim;</code></td><td>U+02272</td><td>≲</td></tr>
     *     <tr><td><code>lsime;</code></td><td>U+02A8D</td><td>⪍</td></tr>
     *     <tr><td><code>lsimg;</code></td><td>U+02A8F</td><td>⪏</td></tr>
     *     <tr><td><code>lsqb;</code></td><td>U+0005B</td><td>[</td></tr>
     *     <tr><td><code>lsquo;</code></td><td>U+02018</td><td>‘</td></tr>
     *     <tr><td><code>lsquor;</code></td><td>U+0201A</td><td>‚</td></tr>
     *     <tr><td><code>Lstrok;</code></td><td>U+00141</td><td>Ł</td></tr>
     *     <tr><td><code>lstrok;</code></td><td>U+00142</td><td>ł</td></tr>
     *     <tr><td><code>LT;</code></td><td>U+0003C</td><td>&lt;</td></tr>
     *     <tr><td><code>LT</code></td><td>U+0003C</td><td>&lt;</td></tr>
     *     <tr><td><code>Lt;</code></td><td>U+0226A</td><td>≪</td></tr>
     *     <tr><td><code>lt;</code></td><td>U+0003C</td><td>&lt;</td></tr>
     *     <tr><td><code>lt</code></td><td>U+0003C</td><td>&lt;</td></tr>
     *     <tr><td><code>ltcc;</code></td><td>U+02AA6</td><td>⪦</td></tr>
     *     <tr><td><code>ltcir;</code></td><td>U+02A79</td><td>⩹</td></tr>
     *     <tr><td><code>ltdot;</code></td><td>U+022D6</td><td>⋖</td></tr>
     *     <tr><td><code>lthree;</code></td><td>U+022CB</td><td>⋋</td></tr>
     *     <tr><td><code>ltimes;</code></td><td>U+022C9</td><td>⋉</td></tr>
     *     <tr><td><code>ltlarr;</code></td><td>U+02976</td><td>⥶</td></tr>
     *     <tr><td><code>ltquest;</code></td><td>U+02A7B</td><td>⩻</td></tr>
     *     <tr><td><code>ltri;</code></td><td>U+025C3</td><td>◃</td></tr>
     *     <tr><td><code>ltrie;</code></td><td>U+022B4</td><td>⊴</td></tr>
     *     <tr><td><code>ltrif;</code></td><td>U+025C2</td><td>◂</td></tr>
     *     <tr><td><code>ltrPar;</code></td><td>U+02996</td><td>⦖</td></tr>
     *     <tr><td><code>lurdshar;</code></td><td>U+0294A</td><td>⥊</td></tr>
     *     <tr><td><code>luruhar;</code></td><td>U+02966</td><td>⥦</td></tr>
     *     <tr><td><code>lvertneqq;</code></td><td>U+02268 U+0FE00</td><td>≨︀</td></tr>
     *     <tr><td><code>lvnE;</code></td><td>U+02268 U+0FE00</td><td>≨︀</td></tr>
     *     <tr><td><code>macr;</code></td><td>U+000AF</td><td>¯</td></tr>
     *     <tr><td><code>macr</code></td><td>U+000AF</td><td>¯</td></tr>
     *     <tr><td><code>male;</code></td><td>U+02642</td><td>♂</td></tr>
     *     <tr><td><code>malt;</code></td><td>U+02720</td><td>✠</td></tr>
     *     <tr><td><code>maltese;</code></td><td>U+02720</td><td>✠</td></tr>
     *     <tr><td><code>Map;</code></td><td>U+02905</td><td>⤅</td></tr>
     *     <tr><td><code>map;</code></td><td>U+021A6</td><td>↦</td></tr>
     *     <tr><td><code>mapsto;</code></td><td>U+021A6</td><td>↦</td></tr>
     *     <tr><td><code>mapstodown;</code></td><td>U+021A7</td><td>↧</td></tr>
     *     <tr><td><code>mapstoleft;</code></td><td>U+021A4</td><td>↤</td></tr>
     *     <tr><td><code>mapstoup;</code></td><td>U+021A5</td><td>↥</td></tr>
     *     <tr><td><code>marker;</code></td><td>U+025AE</td><td>▮</td></tr>
     *     <tr><td><code>mcomma;</code></td><td>U+02A29</td><td>⨩</td></tr>
     *     <tr><td><code>Mcy;</code></td><td>U+0041C</td><td>М</td></tr>
     *     <tr><td><code>mcy;</code></td><td>U+0043C</td><td>м</td></tr>
     *     <tr><td><code>mdash;</code></td><td>U+02014</td><td>—</td></tr>
     *     <tr><td><code>mDDot;</code></td><td>U+0223A</td><td>∺</td></tr>
     *     <tr><td><code>measuredangle;</code></td><td>U+02221</td><td>∡</td></tr>
     *     <tr><td><code>MediumSpace;</code></td><td>U+0205F</td><td> </td></tr>
     *     <tr><td><code>Mellintrf;</code></td><td>U+02133</td><td>ℳ</td></tr>
     *     <tr><td><code>Mfr;</code></td><td>U+1D510</td><td>𝔐</td></tr>
     *     <tr><td><code>mfr;</code></td><td>U+1D52A</td><td>𝔪</td></tr>
     *     <tr><td><code>mho;</code></td><td>U+02127</td><td>℧</td></tr>
     *     <tr><td><code>micro;</code></td><td>U+000B5</td><td>µ</td></tr>
     *     <tr><td><code>micro</code></td><td>U+000B5</td><td>µ</td></tr>
     *     <tr><td><code>mid;</code></td><td>U+02223</td><td>∣</td></tr>
     *     <tr><td><code>midast;</code></td><td>U+0002A</td><td>*</td></tr>
     *     <tr><td><code>midcir;</code></td><td>U+02AF0</td><td>⫰</td></tr>
     *     <tr><td><code>middot;</code></td><td>U+000B7</td><td>·</td></tr>
     *     <tr><td><code>middot</code></td><td>U+000B7</td><td>·</td></tr>
     *     <tr><td><code>minus;</code></td><td>U+02212</td><td>−</td></tr>
     *     <tr><td><code>minusb;</code></td><td>U+0229F</td><td>⊟</td></tr>
     *     <tr><td><code>minusd;</code></td><td>U+02238</td><td>∸</td></tr>
     *     <tr><td><code>minusdu;</code></td><td>U+02A2A</td><td>⨪</td></tr>
     *     <tr><td><code>MinusPlus;</code></td><td>U+02213</td><td>∓</td></tr>
     *     <tr><td><code>mlcp;</code></td><td>U+02ADB</td><td>⫛</td></tr>
     *     <tr><td><code>mldr;</code></td><td>U+02026</td><td>…</td></tr>
     *     <tr><td><code>mnplus;</code></td><td>U+02213</td><td>∓</td></tr>
     *     <tr><td><code>models;</code></td><td>U+022A7</td><td>⊧</td></tr>
     *     <tr><td><code>Mopf;</code></td><td>U+1D544</td><td>𝕄</td></tr>
     *     <tr><td><code>mopf;</code></td><td>U+1D55E</td><td>𝕞</td></tr>
     *     <tr><td><code>mp;</code></td><td>U+02213</td><td>∓</td></tr>
     *     <tr><td><code>Mscr;</code></td><td>U+02133</td><td>ℳ</td></tr>
     *     <tr><td><code>mscr;</code></td><td>U+1D4C2</td><td>𝓂</td></tr>
     *     <tr><td><code>mstpos;</code></td><td>U+0223E</td><td>∾</td></tr>
     *     <tr><td><code>Mu;</code></td><td>U+0039C</td><td>Μ</td></tr>
     *     <tr><td><code>mu;</code></td><td>U+003BC</td><td>μ</td></tr>
     *     <tr><td><code>multimap;</code></td><td>U+022B8</td><td>⊸</td></tr>
     *     <tr><td><code>mumap;</code></td><td>U+022B8</td><td>⊸</td></tr>
     *     <tr><td><code>nabla;</code></td><td>U+02207</td><td>∇</td></tr>
     *     <tr><td><code>Nacute;</code></td><td>U+00143</td><td>Ń</td></tr>
     *     <tr><td><code>nacute;</code></td><td>U+00144</td><td>ń</td></tr>
     *     <tr><td><code>nang;</code></td><td>U+02220 U+020D2</td><td>∠⃒</td></tr>
     *     <tr><td><code>nap;</code></td><td>U+02249</td><td>≉</td></tr>
     *     <tr><td><code>napE;</code></td><td>U+02A70 U+00338</td><td>⩰̸</td></tr>
     *     <tr><td><code>napid;</code></td><td>U+0224B U+00338</td><td>≋̸</td></tr>
     *     <tr><td><code>napos;</code></td><td>U+00149</td><td>ŉ</td></tr>
     *     <tr><td><code>napprox;</code></td><td>U+02249</td><td>≉</td></tr>
     *     <tr><td><code>natur;</code></td><td>U+0266E</td><td>♮</td></tr>
     *     <tr><td><code>natural;</code></td><td>U+0266E</td><td>♮</td></tr>
     *     <tr><td><code>naturals;</code></td><td>U+02115</td><td>ℕ</td></tr>
     *     <tr><td><code>nbsp;</code></td><td>U+000A0</td><td>&nbsp;</td></tr>
     *     <tr><td><code>nbsp</code></td><td>U+000A0</td><td>&nbsp;</td></tr>
     *     <tr><td><code>nbump;</code></td><td>U+0224E U+00338</td><td>≎̸</td></tr>
     *     <tr><td><code>nbumpe;</code></td><td>U+0224F U+00338</td><td>≏̸</td></tr>
     *     <tr><td><code>ncap;</code></td><td>U+02A43</td><td>⩃</td></tr>
     *     <tr><td><code>Ncaron;</code></td><td>U+00147</td><td>Ň</td></tr>
     *     <tr><td><code>ncaron;</code></td><td>U+00148</td><td>ň</td></tr>
     *     <tr><td><code>Ncedil;</code></td><td>U+00145</td><td>Ņ</td></tr>
     *     <tr><td><code>ncedil;</code></td><td>U+00146</td><td>ņ</td></tr>
     *     <tr><td><code>ncong;</code></td><td>U+02247</td><td>≇</td></tr>
     *     <tr><td><code>ncongdot;</code></td><td>U+02A6D U+00338</td><td>⩭̸</td></tr>
     *     <tr><td><code>ncup;</code></td><td>U+02A42</td><td>⩂</td></tr>
     *     <tr><td><code>Ncy;</code></td><td>U+0041D</td><td>Н</td></tr>
     *     <tr><td><code>ncy;</code></td><td>U+0043D</td><td>н</td></tr>
     *     <tr><td><code>ndash;</code></td><td>U+02013</td><td>–</td></tr>
     *     <tr><td><code>ne;</code></td><td>U+02260</td><td>≠</td></tr>
     *     <tr><td><code>nearhk;</code></td><td>U+02924</td><td>⤤</td></tr>
     *     <tr><td><code>neArr;</code></td><td>U+021D7</td><td>⇗</td></tr>
     *     <tr><td><code>nearr;</code></td><td>U+02197</td><td>↗</td></tr>
     *     <tr><td><code>nearrow;</code></td><td>U+02197</td><td>↗</td></tr>
     *     <tr><td><code>nedot;</code></td><td>U+02250 U+00338</td><td>≐̸</td></tr>
     *     <tr><td><code>NegativeMediumSpace;</code></td><td>U+0200B</td><td>​</td></tr>
     *     <tr><td><code>NegativeThickSpace;</code></td><td>U+0200B</td><td>​</td></tr>
     *     <tr><td><code>NegativeThinSpace;</code></td><td>U+0200B</td><td>​</td></tr>
     *     <tr><td><code>NegativeVeryThinSpace;</code></td><td>U+0200B</td><td>​</td></tr>
     *     <tr><td><code>nequiv;</code></td><td>U+02262</td><td>≢</td></tr>
     *     <tr><td><code>nesear;</code></td><td>U+02928</td><td>⤨</td></tr>
     *     <tr><td><code>nesim;</code></td><td>U+02242 U+00338</td><td>≂̸</td></tr>
     *     <tr><td><code>NestedGreaterGreater;</code></td><td>U+0226B</td><td>≫</td></tr>
     *     <tr><td><code>NestedLessLess;</code></td><td>U+0226A</td><td>≪</td></tr>
     *     <tr><td><code>NewLine;</code></td><td>U+0000A</td><td>␊</td></tr>
     *     <tr><td><code>nexist;</code></td><td>U+02204</td><td>∄</td></tr>
     *     <tr><td><code>nexists;</code></td><td>U+02204</td><td>∄</td></tr>
     *     <tr><td><code>Nfr;</code></td><td>U+1D511</td><td>𝔑</td></tr>
     *     <tr><td><code>nfr;</code></td><td>U+1D52B</td><td>𝔫</td></tr>
     *     <tr><td><code>ngE;</code></td><td>U+02267 U+00338</td><td>≧̸</td></tr>
     *     <tr><td><code>nge;</code></td><td>U+02271</td><td>≱</td></tr>
     *     <tr><td><code>ngeq;</code></td><td>U+02271</td><td>≱</td></tr>
     *     <tr><td><code>ngeqq;</code></td><td>U+02267 U+00338</td><td>≧̸</td></tr>
     *     <tr><td><code>ngeqslant;</code></td><td>U+02A7E U+00338</td><td>⩾̸</td></tr>
     *     <tr><td><code>nges;</code></td><td>U+02A7E U+00338</td><td>⩾̸</td></tr>
     *     <tr><td><code>nGg;</code></td><td>U+022D9 U+00338</td><td>⋙̸</td></tr>
     *     <tr><td><code>ngsim;</code></td><td>U+02275</td><td>≵</td></tr>
     *     <tr><td><code>nGt;</code></td><td>U+0226B U+020D2</td><td>≫⃒</td></tr>
     *     <tr><td><code>ngt;</code></td><td>U+0226F</td><td>≯</td></tr>
     *     <tr><td><code>ngtr;</code></td><td>U+0226F</td><td>≯</td></tr>
     *     <tr><td><code>nGtv;</code></td><td>U+0226B U+00338</td><td>≫̸</td></tr>
     *     <tr><td><code>nhArr;</code></td><td>U+021CE</td><td>⇎</td></tr>
     *     <tr><td><code>nharr;</code></td><td>U+021AE</td><td>↮</td></tr>
     *     <tr><td><code>nhpar;</code></td><td>U+02AF2</td><td>⫲</td></tr>
     *     <tr><td><code>ni;</code></td><td>U+0220B</td><td>∋</td></tr>
     *     <tr><td><code>nis;</code></td><td>U+022FC</td><td>⋼</td></tr>
     *     <tr><td><code>nisd;</code></td><td>U+022FA</td><td>⋺</td></tr>
     *     <tr><td><code>niv;</code></td><td>U+0220B</td><td>∋</td></tr>
     *     <tr><td><code>NJcy;</code></td><td>U+0040A</td><td>Њ</td></tr>
     *     <tr><td><code>njcy;</code></td><td>U+0045A</td><td>њ</td></tr>
     *     <tr><td><code>nlArr;</code></td><td>U+021CD</td><td>⇍</td></tr>
     *     <tr><td><code>nlarr;</code></td><td>U+0219A</td><td>↚</td></tr>
     *     <tr><td><code>nldr;</code></td><td>U+02025</td><td>‥</td></tr>
     *     <tr><td><code>nlE;</code></td><td>U+02266 U+00338</td><td>≦̸</td></tr>
     *     <tr><td><code>nle;</code></td><td>U+02270</td><td>≰</td></tr>
     *     <tr><td><code>nLeftarrow;</code></td><td>U+021CD</td><td>⇍</td></tr>
     *     <tr><td><code>nleftarrow;</code></td><td>U+0219A</td><td>↚</td></tr>
     *     <tr><td><code>nLeftrightarrow;</code></td><td>U+021CE</td><td>⇎</td></tr>
     *     <tr><td><code>nleftrightarrow;</code></td><td>U+021AE</td><td>↮</td></tr>
     *     <tr><td><code>nleq;</code></td><td>U+02270</td><td>≰</td></tr>
     *     <tr><td><code>nleqq;</code></td><td>U+02266 U+00338</td><td>≦̸</td></tr>
     *     <tr><td><code>nleqslant;</code></td><td>U+02A7D U+00338</td><td>⩽̸</td></tr>
     *     <tr><td><code>nles;</code></td><td>U+02A7D U+00338</td><td>⩽̸</td></tr>
     *     <tr><td><code>nless;</code></td><td>U+0226E</td><td>≮</td></tr>
     *     <tr><td><code>nLl;</code></td><td>U+022D8 U+00338</td><td>⋘̸</td></tr>
     *     <tr><td><code>nlsim;</code></td><td>U+02274</td><td>≴</td></tr>
     *     <tr><td><code>nLt;</code></td><td>U+0226A U+020D2</td><td>≪⃒</td></tr>
     *     <tr><td><code>nlt;</code></td><td>U+0226E</td><td>≮</td></tr>
     *     <tr><td><code>nltri;</code></td><td>U+022EA</td><td>⋪</td></tr>
     *     <tr><td><code>nltrie;</code></td><td>U+022EC</td><td>⋬</td></tr>
     *     <tr><td><code>nLtv;</code></td><td>U+0226A U+00338</td><td>≪̸</td></tr>
     *     <tr><td><code>nmid;</code></td><td>U+02224</td><td>∤</td></tr>
     *     <tr><td><code>NoBreak;</code></td><td>U+02060</td><td>⁠</td></tr>
     *     <tr><td><code>NonBreakingSpace;</code></td><td>U+000A0</td><td>&nbsp;</td></tr>
     *     <tr><td><code>Nopf;</code></td><td>U+02115</td><td>ℕ</td></tr>
     *     <tr><td><code>nopf;</code></td><td>U+1D55F</td><td>𝕟</td></tr>
     *     <tr><td><code>Not;</code></td><td>U+02AEC</td><td>⫬</td></tr>
     *     <tr><td><code>not;</code></td><td>U+000AC</td><td>¬</td></tr>
     *     <tr><td><code>not</code></td><td>U+000AC</td><td>¬</td></tr>
     *     <tr><td><code>NotCongruent;</code></td><td>U+02262</td><td>≢</td></tr>
     *     <tr><td><code>NotCupCap;</code></td><td>U+0226D</td><td>≭</td></tr>
     *     <tr><td><code>NotDoubleVerticalBar;</code></td><td>U+02226</td><td>∦</td></tr>
     *     <tr><td><code>NotElement;</code></td><td>U+02209</td><td>∉</td></tr>
     *     <tr><td><code>NotEqual;</code></td><td>U+02260</td><td>≠</td></tr>
     *     <tr><td><code>NotEqualTilde;</code></td><td>U+02242 U+00338</td><td>≂̸</td></tr>
     *     <tr><td><code>NotExists;</code></td><td>U+02204</td><td>∄</td></tr>
     *     <tr><td><code>NotGreater;</code></td><td>U+0226F</td><td>≯</td></tr>
     *     <tr><td><code>NotGreaterEqual;</code></td><td>U+02271</td><td>≱</td></tr>
     *     <tr><td><code>NotGreaterFullEqual;</code></td><td>U+02267 U+00338</td><td>≧̸</td></tr>
     *     <tr><td><code>NotGreaterGreater;</code></td><td>U+0226B U+00338</td><td>≫̸</td></tr>
     *     <tr><td><code>NotGreaterLess;</code></td><td>U+02279</td><td>≹</td></tr>
     *     <tr><td><code>NotGreaterSlantEqual;</code></td><td>U+02A7E U+00338</td><td>⩾̸</td></tr>
     *     <tr><td><code>NotGreaterTilde;</code></td><td>U+02275</td><td>≵</td></tr>
     *     <tr><td><code>NotHumpDownHump;</code></td><td>U+0224E U+00338</td><td>≎̸</td></tr>
     *     <tr><td><code>NotHumpEqual;</code></td><td>U+0224F U+00338</td><td>≏̸</td></tr>
     *     <tr><td><code>notin;</code></td><td>U+02209</td><td>∉</td></tr>
     *     <tr><td><code>notindot;</code></td><td>U+022F5 U+00338</td><td>⋵̸</td></tr>
     *     <tr><td><code>notinE;</code></td><td>U+022F9 U+00338</td><td>⋹̸</td></tr>
     *     <tr><td><code>notinva;</code></td><td>U+02209</td><td>∉</td></tr>
     *     <tr><td><code>notinvb;</code></td><td>U+022F7</td><td>⋷</td></tr>
     *     <tr><td><code>notinvc;</code></td><td>U+022F6</td><td>⋶</td></tr>
     *     <tr><td><code>NotLeftTriangle;</code></td><td>U+022EA</td><td>⋪</td></tr>
     *     <tr><td><code>NotLeftTriangleBar;</code></td><td>U+029CF U+00338</td><td>⧏̸</td></tr>
     *     <tr><td><code>NotLeftTriangleEqual;</code></td><td>U+022EC</td><td>⋬</td></tr>
     *     <tr><td><code>NotLess;</code></td><td>U+0226E</td><td>≮</td></tr>
     *     <tr><td><code>NotLessEqual;</code></td><td>U+02270</td><td>≰</td></tr>
     *     <tr><td><code>NotLessGreater;</code></td><td>U+02278</td><td>≸</td></tr>
     *     <tr><td><code>NotLessLess;</code></td><td>U+0226A U+00338</td><td>≪̸</td></tr>
     *     <tr><td><code>NotLessSlantEqual;</code></td><td>U+02A7D U+00338</td><td>⩽̸</td></tr>
     *     <tr><td><code>NotLessTilde;</code></td><td>U+02274</td><td>≴</td></tr>
     *     <tr><td><code>NotNestedGreaterGreater;</code></td><td>U+02AA2 U+00338</td><td>⪢̸</td></tr>
     *     <tr><td><code>NotNestedLessLess;</code></td><td>U+02AA1 U+00338</td><td>⪡̸</td></tr>
     *     <tr><td><code>notni;</code></td><td>U+0220C</td><td>∌</td></tr>
     *     <tr><td><code>notniva;</code></td><td>U+0220C</td><td>∌</td></tr>
     *     <tr><td><code>notnivb;</code></td><td>U+022FE</td><td>⋾</td></tr>
     *     <tr><td><code>notnivc;</code></td><td>U+022FD</td><td>⋽</td></tr>
     *     <tr><td><code>NotPrecedes;</code></td><td>U+02280</td><td>⊀</td></tr>
     *     <tr><td><code>NotPrecedesEqual;</code></td><td>U+02AAF U+00338</td><td>⪯̸</td></tr>
     *     <tr><td><code>NotPrecedesSlantEqual;</code></td><td>U+022E0</td><td>⋠</td></tr>
     *     <tr><td><code>NotReverseElement;</code></td><td>U+0220C</td><td>∌</td></tr>
     *     <tr><td><code>NotRightTriangle;</code></td><td>U+022EB</td><td>⋫</td></tr>
     *     <tr><td><code>NotRightTriangleBar;</code></td><td>U+029D0 U+00338</td><td>⧐̸</td></tr>
     *     <tr><td><code>NotRightTriangleEqual;</code></td><td>U+022ED</td><td>⋭</td></tr>
     *     <tr><td><code>NotSquareSubset;</code></td><td>U+0228F U+00338</td><td>⊏̸</td></tr>
     *     <tr><td><code>NotSquareSubsetEqual;</code></td><td>U+022E2</td><td>⋢</td></tr>
     *     <tr><td><code>NotSquareSuperset;</code></td><td>U+02290 U+00338</td><td>⊐̸</td></tr>
     *     <tr><td><code>NotSquareSupersetEqual;</code></td><td>U+022E3</td><td>⋣</td></tr>
     *     <tr><td><code>NotSubset;</code></td><td>U+02282 U+020D2</td><td>⊂⃒</td></tr>
     *     <tr><td><code>NotSubsetEqual;</code></td><td>U+02288</td><td>⊈</td></tr>
     *     <tr><td><code>NotSucceeds;</code></td><td>U+02281</td><td>⊁</td></tr>
     *     <tr><td><code>NotSucceedsEqual;</code></td><td>U+02AB0 U+00338</td><td>⪰̸</td></tr>
     *     <tr><td><code>NotSucceedsSlantEqual;</code></td><td>U+022E1</td><td>⋡</td></tr>
     *     <tr><td><code>NotSucceedsTilde;</code></td><td>U+0227F U+00338</td><td>≿̸</td></tr>
     *     <tr><td><code>NotSuperset;</code></td><td>U+02283 U+020D2</td><td>⊃⃒</td></tr>
     *     <tr><td><code>NotSupersetEqual;</code></td><td>U+02289</td><td>⊉</td></tr>
     *     <tr><td><code>NotTilde;</code></td><td>U+02241</td><td>≁</td></tr>
     *     <tr><td><code>NotTildeEqual;</code></td><td>U+02244</td><td>≄</td></tr>
     *     <tr><td><code>NotTildeFullEqual;</code></td><td>U+02247</td><td>≇</td></tr>
     *     <tr><td><code>NotTildeTilde;</code></td><td>U+02249</td><td>≉</td></tr>
     *     <tr><td><code>NotVerticalBar;</code></td><td>U+02224</td><td>∤</td></tr>
     *     <tr><td><code>npar;</code></td><td>U+02226</td><td>∦</td></tr>
     *     <tr><td><code>nparallel;</code></td><td>U+02226</td><td>∦</td></tr>
     *     <tr><td><code>nparsl;</code></td><td>U+02AFD U+020E5</td><td>⫽⃥</td></tr>
     *     <tr><td><code>npart;</code></td><td>U+02202 U+00338</td><td>∂̸</td></tr>
     *     <tr><td><code>npolint;</code></td><td>U+02A14</td><td>⨔</td></tr>
     *     <tr><td><code>npr;</code></td><td>U+02280</td><td>⊀</td></tr>
     *     <tr><td><code>nprcue;</code></td><td>U+022E0</td><td>⋠</td></tr>
     *     <tr><td><code>npre;</code></td><td>U+02AAF U+00338</td><td>⪯̸</td></tr>
     *     <tr><td><code>nprec;</code></td><td>U+02280</td><td>⊀</td></tr>
     *     <tr><td><code>npreceq;</code></td><td>U+02AAF U+00338</td><td>⪯̸</td></tr>
     *     <tr><td><code>nrArr;</code></td><td>U+021CF</td><td>⇏</td></tr>
     *     <tr><td><code>nrarr;</code></td><td>U+0219B</td><td>↛</td></tr>
     *     <tr><td><code>nrarrc;</code></td><td>U+02933 U+00338</td><td>⤳̸</td></tr>
     *     <tr><td><code>nrarrw;</code></td><td>U+0219D U+00338</td><td>↝̸</td></tr>
     *     <tr><td><code>nRightarrow;</code></td><td>U+021CF</td><td>⇏</td></tr>
     *     <tr><td><code>nrightarrow;</code></td><td>U+0219B</td><td>↛</td></tr>
     *     <tr><td><code>nrtri;</code></td><td>U+022EB</td><td>⋫</td></tr>
     *     <tr><td><code>nrtrie;</code></td><td>U+022ED</td><td>⋭</td></tr>
     *     <tr><td><code>nsc;</code></td><td>U+02281</td><td>⊁</td></tr>
     *     <tr><td><code>nsccue;</code></td><td>U+022E1</td><td>⋡</td></tr>
     *     <tr><td><code>nsce;</code></td><td>U+02AB0 U+00338</td><td>⪰̸</td></tr>
     *     <tr><td><code>Nscr;</code></td><td>U+1D4A9</td><td>𝒩</td></tr>
     *     <tr><td><code>nscr;</code></td><td>U+1D4C3</td><td>𝓃</td></tr>
     *     <tr><td><code>nshortmid;</code></td><td>U+02224</td><td>∤</td></tr>
     *     <tr><td><code>nshortparallel;</code></td><td>U+02226</td><td>∦</td></tr>
     *     <tr><td><code>nsim;</code></td><td>U+02241</td><td>≁</td></tr>
     *     <tr><td><code>nsime;</code></td><td>U+02244</td><td>≄</td></tr>
     *     <tr><td><code>nsimeq;</code></td><td>U+02244</td><td>≄</td></tr>
     *     <tr><td><code>nsmid;</code></td><td>U+02224</td><td>∤</td></tr>
     *     <tr><td><code>nspar;</code></td><td>U+02226</td><td>∦</td></tr>
     *     <tr><td><code>nsqsube;</code></td><td>U+022E2</td><td>⋢</td></tr>
     *     <tr><td><code>nsqsupe;</code></td><td>U+022E3</td><td>⋣</td></tr>
     *     <tr><td><code>nsub;</code></td><td>U+02284</td><td>⊄</td></tr>
     *     <tr><td><code>nsubE;</code></td><td>U+02AC5 U+00338</td><td>⫅̸</td></tr>
     *     <tr><td><code>nsube;</code></td><td>U+02288</td><td>⊈</td></tr>
     *     <tr><td><code>nsubset;</code></td><td>U+02282 U+020D2</td><td>⊂⃒</td></tr>
     *     <tr><td><code>nsubseteq;</code></td><td>U+02288</td><td>⊈</td></tr>
     *     <tr><td><code>nsubseteqq;</code></td><td>U+02AC5 U+00338</td><td>⫅̸</td></tr>
     *     <tr><td><code>nsucc;</code></td><td>U+02281</td><td>⊁</td></tr>
     *     <tr><td><code>nsucceq;</code></td><td>U+02AB0 U+00338</td><td>⪰̸</td></tr>
     *     <tr><td><code>nsup;</code></td><td>U+02285</td><td>⊅</td></tr>
     *     <tr><td><code>nsupE;</code></td><td>U+02AC6 U+00338</td><td>⫆̸</td></tr>
     *     <tr><td><code>nsupe;</code></td><td>U+02289</td><td>⊉</td></tr>
     *     <tr><td><code>nsupset;</code></td><td>U+02283 U+020D2</td><td>⊃⃒</td></tr>
     *     <tr><td><code>nsupseteq;</code></td><td>U+02289</td><td>⊉</td></tr>
     *     <tr><td><code>nsupseteqq;</code></td><td>U+02AC6 U+00338</td><td>⫆̸</td></tr>
     *     <tr><td><code>ntgl;</code></td><td>U+02279</td><td>≹</td></tr>
     *     <tr><td><code>Ntilde;</code></td><td>U+000D1</td><td>Ñ</td></tr>
     *     <tr><td><code>Ntilde</code></td><td>U+000D1</td><td>Ñ</td></tr>
     *     <tr><td><code>ntilde;</code></td><td>U+000F1</td><td>ñ</td></tr>
     *     <tr><td><code>ntilde</code></td><td>U+000F1</td><td>ñ</td></tr>
     *     <tr><td><code>ntlg;</code></td><td>U+02278</td><td>≸</td></tr>
     *     <tr><td><code>ntriangleleft;</code></td><td>U+022EA</td><td>⋪</td></tr>
     *     <tr><td><code>ntrianglelefteq;</code></td><td>U+022EC</td><td>⋬</td></tr>
     *     <tr><td><code>ntriangleright;</code></td><td>U+022EB</td><td>⋫</td></tr>
     *     <tr><td><code>ntrianglerighteq;</code></td><td>U+022ED</td><td>⋭</td></tr>
     *     <tr><td><code>Nu;</code></td><td>U+0039D</td><td>Ν</td></tr>
     *     <tr><td><code>nu;</code></td><td>U+003BD</td><td>ν</td></tr>
     *     <tr><td><code>num;</code></td><td>U+00023</td><td>#</td></tr>
     *     <tr><td><code>numero;</code></td><td>U+02116</td><td>№</td></tr>
     *     <tr><td><code>numsp;</code></td><td>U+02007</td><td> </td></tr>
     *     <tr><td><code>nvap;</code></td><td>U+0224D U+020D2</td><td>≍⃒</td></tr>
     *     <tr><td><code>nVDash;</code></td><td>U+022AF</td><td>⊯</td></tr>
     *     <tr><td><code>nVdash;</code></td><td>U+022AE</td><td>⊮</td></tr>
     *     <tr><td><code>nvDash;</code></td><td>U+022AD</td><td>⊭</td></tr>
     *     <tr><td><code>nvdash;</code></td><td>U+022AC</td><td>⊬</td></tr>
     *     <tr><td><code>nvge;</code></td><td>U+02265 U+020D2</td><td>≥⃒</td></tr>
     *     <tr><td><code>nvgt;</code></td><td>U+0003E U+020D2</td><td>&gt;⃒</td></tr>
     *     <tr><td><code>nvHarr;</code></td><td>U+02904</td><td>⤄</td></tr>
     *     <tr><td><code>nvinfin;</code></td><td>U+029DE</td><td>⧞</td></tr>
     *     <tr><td><code>nvlArr;</code></td><td>U+02902</td><td>⤂</td></tr>
     *     <tr><td><code>nvle;</code></td><td>U+02264 U+020D2</td><td>≤⃒</td></tr>
     *     <tr><td><code>nvlt;</code></td><td>U+0003C U+020D2</td><td>&lt;⃒</td></tr>
     *     <tr><td><code>nvltrie;</code></td><td>U+022B4 U+020D2</td><td>⊴⃒</td></tr>
     *     <tr><td><code>nvrArr;</code></td><td>U+02903</td><td>⤃</td></tr>
     *     <tr><td><code>nvrtrie;</code></td><td>U+022B5 U+020D2</td><td>⊵⃒</td></tr>
     *     <tr><td><code>nvsim;</code></td><td>U+0223C U+020D2</td><td>∼⃒</td></tr>
     *     <tr><td><code>nwarhk;</code></td><td>U+02923</td><td>⤣</td></tr>
     *     <tr><td><code>nwArr;</code></td><td>U+021D6</td><td>⇖</td></tr>
     *     <tr><td><code>nwarr;</code></td><td>U+02196</td><td>↖</td></tr>
     *     <tr><td><code>nwarrow;</code></td><td>U+02196</td><td>↖</td></tr>
     *     <tr><td><code>nwnear;</code></td><td>U+02927</td><td>⤧</td></tr>
     *     <tr><td><code>Oacute;</code></td><td>U+000D3</td><td>Ó</td></tr>
     *     <tr><td><code>Oacute</code></td><td>U+000D3</td><td>Ó</td></tr>
     *     <tr><td><code>oacute;</code></td><td>U+000F3</td><td>ó</td></tr>
     *     <tr><td><code>oacute</code></td><td>U+000F3</td><td>ó</td></tr>
     *     <tr><td><code>oast;</code></td><td>U+0229B</td><td>⊛</td></tr>
     *     <tr><td><code>ocir;</code></td><td>U+0229A</td><td>⊚</td></tr>
     *     <tr><td><code>Ocirc;</code></td><td>U+000D4</td><td>Ô</td></tr>
     *     <tr><td><code>Ocirc</code></td><td>U+000D4</td><td>Ô</td></tr>
     *     <tr><td><code>ocirc;</code></td><td>U+000F4</td><td>ô</td></tr>
     *     <tr><td><code>ocirc</code></td><td>U+000F4</td><td>ô</td></tr>
     *     <tr><td><code>Ocy;</code></td><td>U+0041E</td><td>О</td></tr>
     *     <tr><td><code>ocy;</code></td><td>U+0043E</td><td>о</td></tr>
     *     <tr><td><code>odash;</code></td><td>U+0229D</td><td>⊝</td></tr>
     *     <tr><td><code>Odblac;</code></td><td>U+00150</td><td>Ő</td></tr>
     *     <tr><td><code>odblac;</code></td><td>U+00151</td><td>ő</td></tr>
     *     <tr><td><code>odiv;</code></td><td>U+02A38</td><td>⨸</td></tr>
     *     <tr><td><code>odot;</code></td><td>U+02299</td><td>⊙</td></tr>
     *     <tr><td><code>odsold;</code></td><td>U+029BC</td><td>⦼</td></tr>
     *     <tr><td><code>OElig;</code></td><td>U+00152</td><td>Œ</td></tr>
     *     <tr><td><code>oelig;</code></td><td>U+00153</td><td>œ</td></tr>
     *     <tr><td><code>ofcir;</code></td><td>U+029BF</td><td>⦿</td></tr>
     *     <tr><td><code>Ofr;</code></td><td>U+1D512</td><td>𝔒</td></tr>
     *     <tr><td><code>ofr;</code></td><td>U+1D52C</td><td>𝔬</td></tr>
     *     <tr><td><code>ogon;</code></td><td>U+002DB</td><td>˛</td></tr>
     *     <tr><td><code>Ograve;</code></td><td>U+000D2</td><td>Ò</td></tr>
     *     <tr><td><code>Ograve</code></td><td>U+000D2</td><td>Ò</td></tr>
     *     <tr><td><code>ograve;</code></td><td>U+000F2</td><td>ò</td></tr>
     *     <tr><td><code>ograve</code></td><td>U+000F2</td><td>ò</td></tr>
     *     <tr><td><code>ogt;</code></td><td>U+029C1</td><td>⧁</td></tr>
     *     <tr><td><code>ohbar;</code></td><td>U+029B5</td><td>⦵</td></tr>
     *     <tr><td><code>ohm;</code></td><td>U+003A9</td><td>Ω</td></tr>
     *     <tr><td><code>oint;</code></td><td>U+0222E</td><td>∮</td></tr>
     *     <tr><td><code>olarr;</code></td><td>U+021BA</td><td>↺</td></tr>
     *     <tr><td><code>olcir;</code></td><td>U+029BE</td><td>⦾</td></tr>
     *     <tr><td><code>olcross;</code></td><td>U+029BB</td><td>⦻</td></tr>
     *     <tr><td><code>oline;</code></td><td>U+0203E</td><td>‾</td></tr>
     *     <tr><td><code>olt;</code></td><td>U+029C0</td><td>⧀</td></tr>
     *     <tr><td><code>Omacr;</code></td><td>U+0014C</td><td>Ō</td></tr>
     *     <tr><td><code>omacr;</code></td><td>U+0014D</td><td>ō</td></tr>
     *     <tr><td><code>Omega;</code></td><td>U+003A9</td><td>Ω</td></tr>
     *     <tr><td><code>omega;</code></td><td>U+003C9</td><td>ω</td></tr>
     *     <tr><td><code>Omicron;</code></td><td>U+0039F</td><td>Ο</td></tr>
     *     <tr><td><code>omicron;</code></td><td>U+003BF</td><td>ο</td></tr>
     *     <tr><td><code>omid;</code></td><td>U+029B6</td><td>⦶</td></tr>
     *     <tr><td><code>ominus;</code></td><td>U+02296</td><td>⊖</td></tr>
     *     <tr><td><code>Oopf;</code></td><td>U+1D546</td><td>𝕆</td></tr>
     *     <tr><td><code>oopf;</code></td><td>U+1D560</td><td>𝕠</td></tr>
     *     <tr><td><code>opar;</code></td><td>U+029B7</td><td>⦷</td></tr>
     *     <tr><td><code>OpenCurlyDoubleQuote;</code></td><td>U+0201C</td><td>“</td></tr>
     *     <tr><td><code>OpenCurlyQuote;</code></td><td>U+02018</td><td>‘</td></tr>
     *     <tr><td><code>operp;</code></td><td>U+029B9</td><td>⦹</td></tr>
     *     <tr><td><code>oplus;</code></td><td>U+02295</td><td>⊕</td></tr>
     *     <tr><td><code>Or;</code></td><td>U+02A54</td><td>⩔</td></tr>
     *     <tr><td><code>or;</code></td><td>U+02228</td><td>∨</td></tr>
     *     <tr><td><code>orarr;</code></td><td>U+021BB</td><td>↻</td></tr>
     *     <tr><td><code>ord;</code></td><td>U+02A5D</td><td>⩝</td></tr>
     *     <tr><td><code>order;</code></td><td>U+02134</td><td>ℴ</td></tr>
     *     <tr><td><code>orderof;</code></td><td>U+02134</td><td>ℴ</td></tr>
     *     <tr><td><code>ordf;</code></td><td>U+000AA</td><td>ª</td></tr>
     *     <tr><td><code>ordf</code></td><td>U+000AA</td><td>ª</td></tr>
     *     <tr><td><code>ordm;</code></td><td>U+000BA</td><td>º</td></tr>
     *     <tr><td><code>ordm</code></td><td>U+000BA</td><td>º</td></tr>
     *     <tr><td><code>origof;</code></td><td>U+022B6</td><td>⊶</td></tr>
     *     <tr><td><code>oror;</code></td><td>U+02A56</td><td>⩖</td></tr>
     *     <tr><td><code>orslope;</code></td><td>U+02A57</td><td>⩗</td></tr>
     *     <tr><td><code>orv;</code></td><td>U+02A5B</td><td>⩛</td></tr>
     *     <tr><td><code>oS;</code></td><td>U+024C8</td><td>Ⓢ</td></tr>
     *     <tr><td><code>Oscr;</code></td><td>U+1D4AA</td><td>𝒪</td></tr>
     *     <tr><td><code>oscr;</code></td><td>U+02134</td><td>ℴ</td></tr>
     *     <tr><td><code>Oslash;</code></td><td>U+000D8</td><td>Ø</td></tr>
     *     <tr><td><code>Oslash</code></td><td>U+000D8</td><td>Ø</td></tr>
     *     <tr><td><code>oslash;</code></td><td>U+000F8</td><td>ø</td></tr>
     *     <tr><td><code>oslash</code></td><td>U+000F8</td><td>ø</td></tr>
     *     <tr><td><code>osol;</code></td><td>U+02298</td><td>⊘</td></tr>
     *     <tr><td><code>Otilde;</code></td><td>U+000D5</td><td>Õ</td></tr>
     *     <tr><td><code>Otilde</code></td><td>U+000D5</td><td>Õ</td></tr>
     *     <tr><td><code>otilde;</code></td><td>U+000F5</td><td>õ</td></tr>
     *     <tr><td><code>otilde</code></td><td>U+000F5</td><td>õ</td></tr>
     *     <tr><td><code>Otimes;</code></td><td>U+02A37</td><td>⨷</td></tr>
     *     <tr><td><code>otimes;</code></td><td>U+02297</td><td>⊗</td></tr>
     *     <tr><td><code>otimesas;</code></td><td>U+02A36</td><td>⨶</td></tr>
     *     <tr><td><code>Ouml;</code></td><td>U+000D6</td><td>Ö</td></tr>
     *     <tr><td><code>Ouml</code></td><td>U+000D6</td><td>Ö</td></tr>
     *     <tr><td><code>ouml;</code></td><td>U+000F6</td><td>ö</td></tr>
     *     <tr><td><code>ouml</code></td><td>U+000F6</td><td>ö</td></tr>
     *     <tr><td><code>ovbar;</code></td><td>U+0233D</td><td>⌽</td></tr>
     *     <tr><td><code>OverBar;</code></td><td>U+0203E</td><td>‾</td></tr>
     *     <tr><td><code>OverBrace;</code></td><td>U+023DE</td><td>⏞</td></tr>
     *     <tr><td><code>OverBracket;</code></td><td>U+023B4</td><td>⎴</td></tr>
     *     <tr><td><code>OverParenthesis;</code></td><td>U+023DC</td><td>⏜</td></tr>
     *     <tr><td><code>par;</code></td><td>U+02225</td><td>∥</td></tr>
     *     <tr><td><code>para;</code></td><td>U+000B6</td><td>¶</td></tr>
     *     <tr><td><code>para</code></td><td>U+000B6</td><td>¶</td></tr>
     *     <tr><td><code>parallel;</code></td><td>U+02225</td><td>∥</td></tr>
     *     <tr><td><code>parsim;</code></td><td>U+02AF3</td><td>⫳</td></tr>
     *     <tr><td><code>parsl;</code></td><td>U+02AFD</td><td>⫽</td></tr>
     *     <tr><td><code>part;</code></td><td>U+02202</td><td>∂</td></tr>
     *     <tr><td><code>PartialD;</code></td><td>U+02202</td><td>∂</td></tr>
     *     <tr><td><code>Pcy;</code></td><td>U+0041F</td><td>П</td></tr>
     *     <tr><td><code>pcy;</code></td><td>U+0043F</td><td>п</td></tr>
     *     <tr><td><code>percnt;</code></td><td>U+00025</td><td>%</td></tr>
     *     <tr><td><code>period;</code></td><td>U+0002E</td><td>.</td></tr>
     *     <tr><td><code>permil;</code></td><td>U+02030</td><td>‰</td></tr>
     *     <tr><td><code>perp;</code></td><td>U+022A5</td><td>⊥</td></tr>
     *     <tr><td><code>pertenk;</code></td><td>U+02031</td><td>‱</td></tr>
     *     <tr><td><code>Pfr;</code></td><td>U+1D513</td><td>𝔓</td></tr>
     *     <tr><td><code>pfr;</code></td><td>U+1D52D</td><td>𝔭</td></tr>
     *     <tr><td><code>Phi;</code></td><td>U+003A6</td><td>Φ</td></tr>
     *     <tr><td><code>phi;</code></td><td>U+003C6</td><td>φ</td></tr>
     *     <tr><td><code>phiv;</code></td><td>U+003D5</td><td>ϕ</td></tr>
     *     <tr><td><code>phmmat;</code></td><td>U+02133</td><td>ℳ</td></tr>
     *     <tr><td><code>phone;</code></td><td>U+0260E</td><td>☎</td></tr>
     *     <tr><td><code>Pi;</code></td><td>U+003A0</td><td>Π</td></tr>
     *     <tr><td><code>pi;</code></td><td>U+003C0</td><td>π</td></tr>
     *     <tr><td><code>pitchfork;</code></td><td>U+022D4</td><td>⋔</td></tr>
     *     <tr><td><code>piv;</code></td><td>U+003D6</td><td>ϖ</td></tr>
     *     <tr><td><code>planck;</code></td><td>U+0210F</td><td>ℏ</td></tr>
     *     <tr><td><code>planckh;</code></td><td>U+0210E</td><td>ℎ</td></tr>
     *     <tr><td><code>plankv;</code></td><td>U+0210F</td><td>ℏ</td></tr>
     *     <tr><td><code>plus;</code></td><td>U+0002B</td><td>+</td></tr>
     *     <tr><td><code>plusacir;</code></td><td>U+02A23</td><td>⨣</td></tr>
     *     <tr><td><code>plusb;</code></td><td>U+0229E</td><td>⊞</td></tr>
     *     <tr><td><code>pluscir;</code></td><td>U+02A22</td><td>⨢</td></tr>
     *     <tr><td><code>plusdo;</code></td><td>U+02214</td><td>∔</td></tr>
     *     <tr><td><code>plusdu;</code></td><td>U+02A25</td><td>⨥</td></tr>
     *     <tr><td><code>pluse;</code></td><td>U+02A72</td><td>⩲</td></tr>
     *     <tr><td><code>PlusMinus;</code></td><td>U+000B1</td><td>±</td></tr>
     *     <tr><td><code>plusmn;</code></td><td>U+000B1</td><td>±</td></tr>
     *     <tr><td><code>plusmn</code></td><td>U+000B1</td><td>±</td></tr>
     *     <tr><td><code>plussim;</code></td><td>U+02A26</td><td>⨦</td></tr>
     *     <tr><td><code>plustwo;</code></td><td>U+02A27</td><td>⨧</td></tr>
     *     <tr><td><code>pm;</code></td><td>U+000B1</td><td>±</td></tr>
     *     <tr><td><code>Poincareplane;</code></td><td>U+0210C</td><td>ℌ</td></tr>
     *     <tr><td><code>pointint;</code></td><td>U+02A15</td><td>⨕</td></tr>
     *     <tr><td><code>Popf;</code></td><td>U+02119</td><td>ℙ</td></tr>
     *     <tr><td><code>popf;</code></td><td>U+1D561</td><td>𝕡</td></tr>
     *     <tr><td><code>pound;</code></td><td>U+000A3</td><td>£</td></tr>
     *     <tr><td><code>pound</code></td><td>U+000A3</td><td>£</td></tr>
     *     <tr><td><code>Pr;</code></td><td>U+02ABB</td><td>⪻</td></tr>
     *     <tr><td><code>pr;</code></td><td>U+0227A</td><td>≺</td></tr>
     *     <tr><td><code>prap;</code></td><td>U+02AB7</td><td>⪷</td></tr>
     *     <tr><td><code>prcue;</code></td><td>U+0227C</td><td>≼</td></tr>
     *     <tr><td><code>prE;</code></td><td>U+02AB3</td><td>⪳</td></tr>
     *     <tr><td><code>pre;</code></td><td>U+02AAF</td><td>⪯</td></tr>
     *     <tr><td><code>prec;</code></td><td>U+0227A</td><td>≺</td></tr>
     *     <tr><td><code>precapprox;</code></td><td>U+02AB7</td><td>⪷</td></tr>
     *     <tr><td><code>preccurlyeq;</code></td><td>U+0227C</td><td>≼</td></tr>
     *     <tr><td><code>Precedes;</code></td><td>U+0227A</td><td>≺</td></tr>
     *     <tr><td><code>PrecedesEqual;</code></td><td>U+02AAF</td><td>⪯</td></tr>
     *     <tr><td><code>PrecedesSlantEqual;</code></td><td>U+0227C</td><td>≼</td></tr>
     *     <tr><td><code>PrecedesTilde;</code></td><td>U+0227E</td><td>≾</td></tr>
     *     <tr><td><code>preceq;</code></td><td>U+02AAF</td><td>⪯</td></tr>
     *     <tr><td><code>precnapprox;</code></td><td>U+02AB9</td><td>⪹</td></tr>
     *     <tr><td><code>precneqq;</code></td><td>U+02AB5</td><td>⪵</td></tr>
     *     <tr><td><code>precnsim;</code></td><td>U+022E8</td><td>⋨</td></tr>
     *     <tr><td><code>precsim;</code></td><td>U+0227E</td><td>≾</td></tr>
     *     <tr><td><code>Prime;</code></td><td>U+02033</td><td>″</td></tr>
     *     <tr><td><code>prime;</code></td><td>U+02032</td><td>′</td></tr>
     *     <tr><td><code>primes;</code></td><td>U+02119</td><td>ℙ</td></tr>
     *     <tr><td><code>prnap;</code></td><td>U+02AB9</td><td>⪹</td></tr>
     *     <tr><td><code>prnE;</code></td><td>U+02AB5</td><td>⪵</td></tr>
     *     <tr><td><code>prnsim;</code></td><td>U+022E8</td><td>⋨</td></tr>
     *     <tr><td><code>prod;</code></td><td>U+0220F</td><td>∏</td></tr>
     *     <tr><td><code>Product;</code></td><td>U+0220F</td><td>∏</td></tr>
     *     <tr><td><code>profalar;</code></td><td>U+0232E</td><td>⌮</td></tr>
     *     <tr><td><code>profline;</code></td><td>U+02312</td><td>⌒</td></tr>
     *     <tr><td><code>profsurf;</code></td><td>U+02313</td><td>⌓</td></tr>
     *     <tr><td><code>prop;</code></td><td>U+0221D</td><td>∝</td></tr>
     *     <tr><td><code>Proportion;</code></td><td>U+02237</td><td>∷</td></tr>
     *     <tr><td><code>Proportional;</code></td><td>U+0221D</td><td>∝</td></tr>
     *     <tr><td><code>propto;</code></td><td>U+0221D</td><td>∝</td></tr>
     *     <tr><td><code>prsim;</code></td><td>U+0227E</td><td>≾</td></tr>
     *     <tr><td><code>prurel;</code></td><td>U+022B0</td><td>⊰</td></tr>
     *     <tr><td><code>Pscr;</code></td><td>U+1D4AB</td><td>𝒫</td></tr>
     *     <tr><td><code>pscr;</code></td><td>U+1D4C5</td><td>𝓅</td></tr>
     *     <tr><td><code>Psi;</code></td><td>U+003A8</td><td>Ψ</td></tr>
     *     <tr><td><code>psi;</code></td><td>U+003C8</td><td>ψ</td></tr>
     *     <tr><td><code>puncsp;</code></td><td>U+02008</td><td> </td></tr>
     *     <tr><td><code>Qfr;</code></td><td>U+1D514</td><td>𝔔</td></tr>
     *     <tr><td><code>qfr;</code></td><td>U+1D52E</td><td>𝔮</td></tr>
     *     <tr><td><code>qint;</code></td><td>U+02A0C</td><td>⨌</td></tr>
     *     <tr><td><code>Qopf;</code></td><td>U+0211A</td><td>ℚ</td></tr>
     *     <tr><td><code>qopf;</code></td><td>U+1D562</td><td>𝕢</td></tr>
     *     <tr><td><code>qprime;</code></td><td>U+02057</td><td>⁗</td></tr>
     *     <tr><td><code>Qscr;</code></td><td>U+1D4AC</td><td>𝒬</td></tr>
     *     <tr><td><code>qscr;</code></td><td>U+1D4C6</td><td>𝓆</td></tr>
     *     <tr><td><code>quaternions;</code></td><td>U+0210D</td><td>ℍ</td></tr>
     *     <tr><td><code>quatint;</code></td><td>U+02A16</td><td>⨖</td></tr>
     *     <tr><td><code>quest;</code></td><td>U+0003F</td><td>?</td></tr>
     *     <tr><td><code>questeq;</code></td><td>U+0225F</td><td>≟</td></tr>
     *     <tr><td><code>QUOT;</code></td><td>U+00022</td><td>"</td></tr>
     *     <tr><td><code>QUOT</code></td><td>U+00022</td><td>"</td></tr>
     *     <tr><td><code>quot;</code></td><td>U+00022</td><td>"</td></tr>
     *     <tr><td><code>quot</code></td><td>U+00022</td><td>"</td></tr>
     *     <tr><td><code>rAarr;</code></td><td>U+021DB</td><td>⇛</td></tr>
     *     <tr><td><code>race;</code></td><td>U+0223D U+00331</td><td>∽̱</td></tr>
     *     <tr><td><code>Racute;</code></td><td>U+00154</td><td>Ŕ</td></tr>
     *     <tr><td><code>racute;</code></td><td>U+00155</td><td>ŕ</td></tr>
     *     <tr><td><code>radic;</code></td><td>U+0221A</td><td>√</td></tr>
     *     <tr><td><code>raemptyv;</code></td><td>U+029B3</td><td>⦳</td></tr>
     *     <tr><td><code>Rang;</code></td><td>U+027EB</td><td>⟫</td></tr>
     *     <tr><td><code>rang;</code></td><td>U+027E9</td><td>⟩</td></tr>
     *     <tr><td><code>rangd;</code></td><td>U+02992</td><td>⦒</td></tr>
     *     <tr><td><code>range;</code></td><td>U+029A5</td><td>⦥</td></tr>
     *     <tr><td><code>rangle;</code></td><td>U+027E9</td><td>〉</td></tr>
     *     <tr><td><code>raquo;</code></td><td>U+000BB</td><td>»</td></tr>
     *     <tr><td><code>raquo</code></td><td>U+000BB</td><td>»</td></tr>
     *     <tr><td><code>Rarr;</code></td><td>U+021A0</td><td>↠</td></tr>
     *     <tr><td><code>rArr;</code></td><td>U+021D2</td><td>⇒</td></tr>
     *     <tr><td><code>rarr;</code></td><td>U+02192</td><td>→</td></tr>
     *     <tr><td><code>rarrap;</code></td><td>U+02975</td><td>⥵</td></tr>
     *     <tr><td><code>rarrb;</code></td><td>U+021E5</td><td>⇥</td></tr>
     *     <tr><td><code>rarrbfs;</code></td><td>U+02920</td><td>⤠</td></tr>
     *     <tr><td><code>rarrc;</code></td><td>U+02933</td><td>⤳</td></tr>
     *     <tr><td><code>rarrfs;</code></td><td>U+0291E</td><td>⤞</td></tr>
     *     <tr><td><code>rarrhk;</code></td><td>U+021AA</td><td>↪</td></tr>
     *     <tr><td><code>rarrlp;</code></td><td>U+021AC</td><td>↬</td></tr>
     *     <tr><td><code>rarrpl;</code></td><td>U+02945</td><td>⥅</td></tr>
     *     <tr><td><code>rarrsim;</code></td><td>U+02974</td><td>⥴</td></tr>
     *     <tr><td><code>Rarrtl;</code></td><td>U+02916</td><td>⤖</td></tr>
     *     <tr><td><code>rarrtl;</code></td><td>U+021A3</td><td>↣</td></tr>
     *     <tr><td><code>rarrw;</code></td><td>U+0219D</td><td>↝</td></tr>
     *     <tr><td><code>rAtail;</code></td><td>U+0291C</td><td>⤜</td></tr>
     *     <tr><td><code>ratail;</code></td><td>U+0291A</td><td>⤚</td></tr>
     *     <tr><td><code>ratio;</code></td><td>U+02236</td><td>∶</td></tr>
     *     <tr><td><code>rationals;</code></td><td>U+0211A</td><td>ℚ</td></tr>
     *     <tr><td><code>RBarr;</code></td><td>U+02910</td><td>⤐</td></tr>
     *     <tr><td><code>rBarr;</code></td><td>U+0290F</td><td>⤏</td></tr>
     *     <tr><td><code>rbarr;</code></td><td>U+0290D</td><td>⤍</td></tr>
     *     <tr><td><code>rbbrk;</code></td><td>U+02773</td><td>❳</td></tr>
     *     <tr><td><code>rbrace;</code></td><td>U+0007D</td><td>}</td></tr>
     *     <tr><td><code>rbrack;</code></td><td>U+0005D</td><td>]</td></tr>
     *     <tr><td><code>rbrke;</code></td><td>U+0298C</td><td>⦌</td></tr>
     *     <tr><td><code>rbrksld;</code></td><td>U+0298E</td><td>⦎</td></tr>
     *     <tr><td><code>rbrkslu;</code></td><td>U+02990</td><td>⦐</td></tr>
     *     <tr><td><code>Rcaron;</code></td><td>U+00158</td><td>Ř</td></tr>
     *     <tr><td><code>rcaron;</code></td><td>U+00159</td><td>ř</td></tr>
     *     <tr><td><code>Rcedil;</code></td><td>U+00156</td><td>Ŗ</td></tr>
     *     <tr><td><code>rcedil;</code></td><td>U+00157</td><td>ŗ</td></tr>
     *     <tr><td><code>rceil;</code></td><td>U+02309</td><td>⌉</td></tr>
     *     <tr><td><code>rcub;</code></td><td>U+0007D</td><td>}</td></tr>
     *     <tr><td><code>Rcy;</code></td><td>U+00420</td><td>Р</td></tr>
     *     <tr><td><code>rcy;</code></td><td>U+00440</td><td>р</td></tr>
     *     <tr><td><code>rdca;</code></td><td>U+02937</td><td>⤷</td></tr>
     *     <tr><td><code>rdldhar;</code></td><td>U+02969</td><td>⥩</td></tr>
     *     <tr><td><code>rdquo;</code></td><td>U+0201D</td><td>”</td></tr>
     *     <tr><td><code>rdquor;</code></td><td>U+0201D</td><td>”</td></tr>
     *     <tr><td><code>rdsh;</code></td><td>U+021B3</td><td>↳</td></tr>
     *     <tr><td><code>Re;</code></td><td>U+0211C</td><td>ℜ</td></tr>
     *     <tr><td><code>real;</code></td><td>U+0211C</td><td>ℜ</td></tr>
     *     <tr><td><code>realine;</code></td><td>U+0211B</td><td>ℛ</td></tr>
     *     <tr><td><code>realpart;</code></td><td>U+0211C</td><td>ℜ</td></tr>
     *     <tr><td><code>reals;</code></td><td>U+0211D</td><td>ℝ</td></tr>
     *     <tr><td><code>rect;</code></td><td>U+025AD</td><td>▭</td></tr>
     *     <tr><td><code>REG;</code></td><td>U+000AE</td><td>®</td></tr>
     *     <tr><td><code>REG</code></td><td>U+000AE</td><td>®</td></tr>
     *     <tr><td><code>reg;</code></td><td>U+000AE</td><td>®</td></tr>
     *     <tr><td><code>reg</code></td><td>U+000AE</td><td>®</td></tr>
     *     <tr><td><code>ReverseElement;</code></td><td>U+0220B</td><td>∋</td></tr>
     *     <tr><td><code>ReverseEquilibrium;</code></td><td>U+021CB</td><td>⇋</td></tr>
     *     <tr><td><code>ReverseUpEquilibrium;</code></td><td>U+0296F</td><td>⥯</td></tr>
     *     <tr><td><code>rfisht;</code></td><td>U+0297D</td><td>⥽</td></tr>
     *     <tr><td><code>rfloor;</code></td><td>U+0230B</td><td>⌋</td></tr>
     *     <tr><td><code>Rfr;</code></td><td>U+0211C</td><td>ℜ</td></tr>
     *     <tr><td><code>rfr;</code></td><td>U+1D52F</td><td>𝔯</td></tr>
     *     <tr><td><code>rHar;</code></td><td>U+02964</td><td>⥤</td></tr>
     *     <tr><td><code>rhard;</code></td><td>U+021C1</td><td>⇁</td></tr>
     *     <tr><td><code>rharu;</code></td><td>U+021C0</td><td>⇀</td></tr>
     *     <tr><td><code>rharul;</code></td><td>U+0296C</td><td>⥬</td></tr>
     *     <tr><td><code>Rho;</code></td><td>U+003A1</td><td>Ρ</td></tr>
     *     <tr><td><code>rho;</code></td><td>U+003C1</td><td>ρ</td></tr>
     *     <tr><td><code>rhov;</code></td><td>U+003F1</td><td>ϱ</td></tr>
     *     <tr><td><code>RightAngleBracket;</code></td><td>U+027E9</td><td>〉</td></tr>
     *     <tr><td><code>RightArrow;</code></td><td>U+02192</td><td>→</td></tr>
     *     <tr><td><code>Rightarrow;</code></td><td>U+021D2</td><td>⇒</td></tr>
     *     <tr><td><code>rightarrow;</code></td><td>U+02192</td><td>→</td></tr>
     *     <tr><td><code>RightArrowBar;</code></td><td>U+021E5</td><td>⇥</td></tr>
     *     <tr><td><code>RightArrowLeftArrow;</code></td><td>U+021C4</td><td>⇄</td></tr>
     *     <tr><td><code>rightarrowtail;</code></td><td>U+021A3</td><td>↣</td></tr>
     *     <tr><td><code>RightCeiling;</code></td><td>U+02309</td><td>⌉</td></tr>
     *     <tr><td><code>RightDoubleBracket;</code></td><td>U+027E7</td><td>⟧</td></tr>
     *     <tr><td><code>RightDownTeeVector;</code></td><td>U+0295D</td><td>⥝</td></tr>
     *     <tr><td><code>RightDownVector;</code></td><td>U+021C2</td><td>⇂</td></tr>
     *     <tr><td><code>RightDownVectorBar;</code></td><td>U+02955</td><td>⥕</td></tr>
     *     <tr><td><code>RightFloor;</code></td><td>U+0230B</td><td>⌋</td></tr>
     *     <tr><td><code>rightharpoondown;</code></td><td>U+021C1</td><td>⇁</td></tr>
     *     <tr><td><code>rightharpoonup;</code></td><td>U+021C0</td><td>⇀</td></tr>
     *     <tr><td><code>rightleftarrows;</code></td><td>U+021C4</td><td>⇄</td></tr>
     *     <tr><td><code>rightleftharpoons;</code></td><td>U+021CC</td><td>⇌</td></tr>
     *     <tr><td><code>rightrightarrows;</code></td><td>U+021C9</td><td>⇉</td></tr>
     *     <tr><td><code>rightsquigarrow;</code></td><td>U+0219D</td><td>↝</td></tr>
     *     <tr><td><code>RightTee;</code></td><td>U+022A2</td><td>⊢</td></tr>
     *     <tr><td><code>RightTeeArrow;</code></td><td>U+021A6</td><td>↦</td></tr>
     *     <tr><td><code>RightTeeVector;</code></td><td>U+0295B</td><td>⥛</td></tr>
     *     <tr><td><code>rightthreetimes;</code></td><td>U+022CC</td><td>⋌</td></tr>
     *     <tr><td><code>RightTriangle;</code></td><td>U+022B3</td><td>⊳</td></tr>
     *     <tr><td><code>RightTriangleBar;</code></td><td>U+029D0</td><td>⧐</td></tr>
     *     <tr><td><code>RightTriangleEqual;</code></td><td>U+022B5</td><td>⊵</td></tr>
     *     <tr><td><code>RightUpDownVector;</code></td><td>U+0294F</td><td>⥏</td></tr>
     *     <tr><td><code>RightUpTeeVector;</code></td><td>U+0295C</td><td>⥜</td></tr>
     *     <tr><td><code>RightUpVector;</code></td><td>U+021BE</td><td>↾</td></tr>
     *     <tr><td><code>RightUpVectorBar;</code></td><td>U+02954</td><td>⥔</td></tr>
     *     <tr><td><code>RightVector;</code></td><td>U+021C0</td><td>⇀</td></tr>
     *     <tr><td><code>RightVectorBar;</code></td><td>U+02953</td><td>⥓</td></tr>
     *     <tr><td><code>ring;</code></td><td>U+002DA</td><td>˚</td></tr>
     *     <tr><td><code>risingdotseq;</code></td><td>U+02253</td><td>≓</td></tr>
     *     <tr><td><code>rlarr;</code></td><td>U+021C4</td><td>⇄</td></tr>
     *     <tr><td><code>rlhar;</code></td><td>U+021CC</td><td>⇌</td></tr>
     *     <tr><td><code>rlm;</code></td><td>U+0200F</td><td>‏</td></tr>
     *     <tr><td><code>rmoust;</code></td><td>U+023B1</td><td>⎱</td></tr>
     *     <tr><td><code>rmoustache;</code></td><td>U+023B1</td><td>⎱</td></tr>
     *     <tr><td><code>rnmid;</code></td><td>U+02AEE</td><td>⫮</td></tr>
     *     <tr><td><code>roang;</code></td><td>U+027ED</td><td>⟭</td></tr>
     *     <tr><td><code>roarr;</code></td><td>U+021FE</td><td>⇾</td></tr>
     *     <tr><td><code>robrk;</code></td><td>U+027E7</td><td>⟧</td></tr>
     *     <tr><td><code>ropar;</code></td><td>U+02986</td><td>⦆</td></tr>
     *     <tr><td><code>Ropf;</code></td><td>U+0211D</td><td>ℝ</td></tr>
     *     <tr><td><code>ropf;</code></td><td>U+1D563</td><td>𝕣</td></tr>
     *     <tr><td><code>roplus;</code></td><td>U+02A2E</td><td>⨮</td></tr>
     *     <tr><td><code>rotimes;</code></td><td>U+02A35</td><td>⨵</td></tr>
     *     <tr><td><code>RoundImplies;</code></td><td>U+02970</td><td>⥰</td></tr>
     *     <tr><td><code>rpar;</code></td><td>U+00029</td><td>)</td></tr>
     *     <tr><td><code>rpargt;</code></td><td>U+02994</td><td>⦔</td></tr>
     *     <tr><td><code>rppolint;</code></td><td>U+02A12</td><td>⨒</td></tr>
     *     <tr><td><code>rrarr;</code></td><td>U+021C9</td><td>⇉</td></tr>
     *     <tr><td><code>Rrightarrow;</code></td><td>U+021DB</td><td>⇛</td></tr>
     *     <tr><td><code>rsaquo;</code></td><td>U+0203A</td><td>›</td></tr>
     *     <tr><td><code>Rscr;</code></td><td>U+0211B</td><td>ℛ</td></tr>
     *     <tr><td><code>rscr;</code></td><td>U+1D4C7</td><td>𝓇</td></tr>
     *     <tr><td><code>Rsh;</code></td><td>U+021B1</td><td>↱</td></tr>
     *     <tr><td><code>rsh;</code></td><td>U+021B1</td><td>↱</td></tr>
     *     <tr><td><code>rsqb;</code></td><td>U+0005D</td><td>]</td></tr>
     *     <tr><td><code>rsquo;</code></td><td>U+02019</td><td>’</td></tr>
     *     <tr><td><code>rsquor;</code></td><td>U+02019</td><td>’</td></tr>
     *     <tr><td><code>rthree;</code></td><td>U+022CC</td><td>⋌</td></tr>
     *     <tr><td><code>rtimes;</code></td><td>U+022CA</td><td>⋊</td></tr>
     *     <tr><td><code>rtri;</code></td><td>U+025B9</td><td>▹</td></tr>
     *     <tr><td><code>rtrie;</code></td><td>U+022B5</td><td>⊵</td></tr>
     *     <tr><td><code>rtrif;</code></td><td>U+025B8</td><td>▸</td></tr>
     *     <tr><td><code>rtriltri;</code></td><td>U+029CE</td><td>⧎</td></tr>
     *     <tr><td><code>RuleDelayed;</code></td><td>U+029F4</td><td>⧴</td></tr>
     *     <tr><td><code>ruluhar;</code></td><td>U+02968</td><td>⥨</td></tr>
     *     <tr><td><code>rx;</code></td><td>U+0211E</td><td>℞</td></tr>
     *     <tr><td><code>Sacute;</code></td><td>U+0015A</td><td>Ś</td></tr>
     *     <tr><td><code>sacute;</code></td><td>U+0015B</td><td>ś</td></tr>
     *     <tr><td><code>sbquo;</code></td><td>U+0201A</td><td>‚</td></tr>
     *     <tr><td><code>Sc;</code></td><td>U+02ABC</td><td>⪼</td></tr>
     *     <tr><td><code>sc;</code></td><td>U+0227B</td><td>≻</td></tr>
     *     <tr><td><code>scap;</code></td><td>U+02AB8</td><td>⪸</td></tr>
     *     <tr><td><code>Scaron;</code></td><td>U+00160</td><td>Š</td></tr>
     *     <tr><td><code>scaron;</code></td><td>U+00161</td><td>š</td></tr>
     *     <tr><td><code>sccue;</code></td><td>U+0227D</td><td>≽</td></tr>
     *     <tr><td><code>scE;</code></td><td>U+02AB4</td><td>⪴</td></tr>
     *     <tr><td><code>sce;</code></td><td>U+02AB0</td><td>⪰</td></tr>
     *     <tr><td><code>Scedil;</code></td><td>U+0015E</td><td>Ş</td></tr>
     *     <tr><td><code>scedil;</code></td><td>U+0015F</td><td>ş</td></tr>
     *     <tr><td><code>Scirc;</code></td><td>U+0015C</td><td>Ŝ</td></tr>
     *     <tr><td><code>scirc;</code></td><td>U+0015D</td><td>ŝ</td></tr>
     *     <tr><td><code>scnap;</code></td><td>U+02ABA</td><td>⪺</td></tr>
     *     <tr><td><code>scnE;</code></td><td>U+02AB6</td><td>⪶</td></tr>
     *     <tr><td><code>scnsim;</code></td><td>U+022E9</td><td>⋩</td></tr>
     *     <tr><td><code>scpolint;</code></td><td>U+02A13</td><td>⨓</td></tr>
     *     <tr><td><code>scsim;</code></td><td>U+0227F</td><td>≿</td></tr>
     *     <tr><td><code>Scy;</code></td><td>U+00421</td><td>С</td></tr>
     *     <tr><td><code>scy;</code></td><td>U+00441</td><td>с</td></tr>
     *     <tr><td><code>sdot;</code></td><td>U+022C5</td><td>⋅</td></tr>
     *     <tr><td><code>sdotb;</code></td><td>U+022A1</td><td>⊡</td></tr>
     *     <tr><td><code>sdote;</code></td><td>U+02A66</td><td>⩦</td></tr>
     *     <tr><td><code>searhk;</code></td><td>U+02925</td><td>⤥</td></tr>
     *     <tr><td><code>seArr;</code></td><td>U+021D8</td><td>⇘</td></tr>
     *     <tr><td><code>searr;</code></td><td>U+02198</td><td>↘</td></tr>
     *     <tr><td><code>searrow;</code></td><td>U+02198</td><td>↘</td></tr>
     *     <tr><td><code>sect;</code></td><td>U+000A7</td><td>§</td></tr>
     *     <tr><td><code>sect</code></td><td>U+000A7</td><td>§</td></tr>
     *     <tr><td><code>semi;</code></td><td>U+0003B</td><td>;</td></tr>
     *     <tr><td><code>seswar;</code></td><td>U+02929</td><td>⤩</td></tr>
     *     <tr><td><code>setminus;</code></td><td>U+02216</td><td>∖</td></tr>
     *     <tr><td><code>setmn;</code></td><td>U+02216</td><td>∖</td></tr>
     *     <tr><td><code>sext;</code></td><td>U+02736</td><td>✶</td></tr>
     *     <tr><td><code>Sfr;</code></td><td>U+1D516</td><td>𝔖</td></tr>
     *     <tr><td><code>sfr;</code></td><td>U+1D530</td><td>𝔰</td></tr>
     *     <tr><td><code>sfrown;</code></td><td>U+02322</td><td>⌢</td></tr>
     *     <tr><td><code>sharp;</code></td><td>U+0266F</td><td>♯</td></tr>
     *     <tr><td><code>SHCHcy;</code></td><td>U+00429</td><td>Щ</td></tr>
     *     <tr><td><code>shchcy;</code></td><td>U+00449</td><td>щ</td></tr>
     *     <tr><td><code>SHcy;</code></td><td>U+00428</td><td>Ш</td></tr>
     *     <tr><td><code>shcy;</code></td><td>U+00448</td><td>ш</td></tr>
     *     <tr><td><code>ShortDownArrow;</code></td><td>U+02193</td><td>↓</td></tr>
     *     <tr><td><code>ShortLeftArrow;</code></td><td>U+02190</td><td>←</td></tr>
     *     <tr><td><code>shortmid;</code></td><td>U+02223</td><td>∣</td></tr>
     *     <tr><td><code>shortparallel;</code></td><td>U+02225</td><td>∥</td></tr>
     *     <tr><td><code>ShortRightArrow;</code></td><td>U+02192</td><td>→</td></tr>
     *     <tr><td><code>ShortUpArrow;</code></td><td>U+02191</td><td>↑</td></tr>
     *     <tr><td><code>shy;</code></td><td>U+000AD</td><td>­</td></tr>
     *     <tr><td><code>shy</code></td><td>U+000AD</td><td>­</td></tr>
     *     <tr><td><code>Sigma;</code></td><td>U+003A3</td><td>Σ</td></tr>
     *     <tr><td><code>sigma;</code></td><td>U+003C3</td><td>σ</td></tr>
     *     <tr><td><code>sigmaf;</code></td><td>U+003C2</td><td>ς</td></tr>
     *     <tr><td><code>sigmav;</code></td><td>U+003C2</td><td>ς</td></tr>
     *     <tr><td><code>sim;</code></td><td>U+0223C</td><td>∼</td></tr>
     *     <tr><td><code>simdot;</code></td><td>U+02A6A</td><td>⩪</td></tr>
     *     <tr><td><code>sime;</code></td><td>U+02243</td><td>≃</td></tr>
     *     <tr><td><code>simeq;</code></td><td>U+02243</td><td>≃</td></tr>
     *     <tr><td><code>simg;</code></td><td>U+02A9E</td><td>⪞</td></tr>
     *     <tr><td><code>simgE;</code></td><td>U+02AA0</td><td>⪠</td></tr>
     *     <tr><td><code>siml;</code></td><td>U+02A9D</td><td>⪝</td></tr>
     *     <tr><td><code>simlE;</code></td><td>U+02A9F</td><td>⪟</td></tr>
     *     <tr><td><code>simne;</code></td><td>U+02246</td><td>≆</td></tr>
     *     <tr><td><code>simplus;</code></td><td>U+02A24</td><td>⨤</td></tr>
     *     <tr><td><code>simrarr;</code></td><td>U+02972</td><td>⥲</td></tr>
     *     <tr><td><code>slarr;</code></td><td>U+02190</td><td>←</td></tr>
     *     <tr><td><code>SmallCircle;</code></td><td>U+02218</td><td>∘</td></tr>
     *     <tr><td><code>smallsetminus;</code></td><td>U+02216</td><td>∖</td></tr>
     *     <tr><td><code>smashp;</code></td><td>U+02A33</td><td>⨳</td></tr>
     *     <tr><td><code>smeparsl;</code></td><td>U+029E4</td><td>⧤</td></tr>
     *     <tr><td><code>smid;</code></td><td>U+02223</td><td>∣</td></tr>
     *     <tr><td><code>smile;</code></td><td>U+02323</td><td>⌣</td></tr>
     *     <tr><td><code>smt;</code></td><td>U+02AAA</td><td>⪪</td></tr>
     *     <tr><td><code>smte;</code></td><td>U+02AAC</td><td>⪬</td></tr>
     *     <tr><td><code>smtes;</code></td><td>U+02AAC U+0FE00</td><td>⪬︀</td></tr>
     *     <tr><td><code>SOFTcy;</code></td><td>U+0042C</td><td>Ь</td></tr>
     *     <tr><td><code>softcy;</code></td><td>U+0044C</td><td>ь</td></tr>
     *     <tr><td><code>sol;</code></td><td>U+0002F</td><td>/</td></tr>
     *     <tr><td><code>solb;</code></td><td>U+029C4</td><td>⧄</td></tr>
     *     <tr><td><code>solbar;</code></td><td>U+0233F</td><td>⌿</td></tr>
     *     <tr><td><code>Sopf;</code></td><td>U+1D54A</td><td>𝕊</td></tr>
     *     <tr><td><code>sopf;</code></td><td>U+1D564</td><td>𝕤</td></tr>
     *     <tr><td><code>spades;</code></td><td>U+02660</td><td>♠</td></tr>
     *     <tr><td><code>spadesuit;</code></td><td>U+02660</td><td>♠</td></tr>
     *     <tr><td><code>spar;</code></td><td>U+02225</td><td>∥</td></tr>
     *     <tr><td><code>sqcap;</code></td><td>U+02293</td><td>⊓</td></tr>
     *     <tr><td><code>sqcaps;</code></td><td>U+02293 U+0FE00</td><td>⊓︀</td></tr>
     *     <tr><td><code>sqcup;</code></td><td>U+02294</td><td>⊔</td></tr>
     *     <tr><td><code>sqcups;</code></td><td>U+02294 U+0FE00</td><td>⊔︀</td></tr>
     *     <tr><td><code>Sqrt;</code></td><td>U+0221A</td><td>√</td></tr>
     *     <tr><td><code>sqsub;</code></td><td>U+0228F</td><td>⊏</td></tr>
     *     <tr><td><code>sqsube;</code></td><td>U+02291</td><td>⊑</td></tr>
     *     <tr><td><code>sqsubset;</code></td><td>U+0228F</td><td>⊏</td></tr>
     *     <tr><td><code>sqsubseteq;</code></td><td>U+02291</td><td>⊑</td></tr>
     *     <tr><td><code>sqsup;</code></td><td>U+02290</td><td>⊐</td></tr>
     *     <tr><td><code>sqsupe;</code></td><td>U+02292</td><td>⊒</td></tr>
     *     <tr><td><code>sqsupset;</code></td><td>U+02290</td><td>⊐</td></tr>
     *     <tr><td><code>sqsupseteq;</code></td><td>U+02292</td><td>⊒</td></tr>
     *     <tr><td><code>squ;</code></td><td>U+025A1</td><td>□</td></tr>
     *     <tr><td><code>Square;</code></td><td>U+025A1</td><td>□</td></tr>
     *     <tr><td><code>square;</code></td><td>U+025A1</td><td>□</td></tr>
     *     <tr><td><code>SquareIntersection;</code></td><td>U+02293</td><td>⊓</td></tr>
     *     <tr><td><code>SquareSubset;</code></td><td>U+0228F</td><td>⊏</td></tr>
     *     <tr><td><code>SquareSubsetEqual;</code></td><td>U+02291</td><td>⊑</td></tr>
     *     <tr><td><code>SquareSuperset;</code></td><td>U+02290</td><td>⊐</td></tr>
     *     <tr><td><code>SquareSupersetEqual;</code></td><td>U+02292</td><td>⊒</td></tr>
     *     <tr><td><code>SquareUnion;</code></td><td>U+02294</td><td>⊔</td></tr>
     *     <tr><td><code>squarf;</code></td><td>U+025AA</td><td>▪</td></tr>
     *     <tr><td><code>squf;</code></td><td>U+025AA</td><td>▪</td></tr>
     *     <tr><td><code>srarr;</code></td><td>U+02192</td><td>→</td></tr>
     *     <tr><td><code>Sscr;</code></td><td>U+1D4AE</td><td>𝒮</td></tr>
     *     <tr><td><code>sscr;</code></td><td>U+1D4C8</td><td>𝓈</td></tr>
     *     <tr><td><code>ssetmn;</code></td><td>U+02216</td><td>∖</td></tr>
     *     <tr><td><code>ssmile;</code></td><td>U+02323</td><td>⌣</td></tr>
     *     <tr><td><code>sstarf;</code></td><td>U+022C6</td><td>⋆</td></tr>
     *     <tr><td><code>Star;</code></td><td>U+022C6</td><td>⋆</td></tr>
     *     <tr><td><code>star;</code></td><td>U+02606</td><td>☆</td></tr>
     *     <tr><td><code>starf;</code></td><td>U+02605</td><td>★</td></tr>
     *     <tr><td><code>straightepsilon;</code></td><td>U+003F5</td><td>ϵ</td></tr>
     *     <tr><td><code>straightphi;</code></td><td>U+003D5</td><td>ϕ</td></tr>
     *     <tr><td><code>strns;</code></td><td>U+000AF</td><td>¯</td></tr>
     *     <tr><td><code>Sub;</code></td><td>U+022D0</td><td>⋐</td></tr>
     *     <tr><td><code>sub;</code></td><td>U+02282</td><td>⊂</td></tr>
     *     <tr><td><code>subdot;</code></td><td>U+02ABD</td><td>⪽</td></tr>
     *     <tr><td><code>subE;</code></td><td>U+02AC5</td><td>⫅</td></tr>
     *     <tr><td><code>sube;</code></td><td>U+02286</td><td>⊆</td></tr>
     *     <tr><td><code>subedot;</code></td><td>U+02AC3</td><td>⫃</td></tr>
     *     <tr><td><code>submult;</code></td><td>U+02AC1</td><td>⫁</td></tr>
     *     <tr><td><code>subnE;</code></td><td>U+02ACB</td><td>⫋</td></tr>
     *     <tr><td><code>subne;</code></td><td>U+0228A</td><td>⊊</td></tr>
     *     <tr><td><code>subplus;</code></td><td>U+02ABF</td><td>⪿</td></tr>
     *     <tr><td><code>subrarr;</code></td><td>U+02979</td><td>⥹</td></tr>
     *     <tr><td><code>Subset;</code></td><td>U+022D0</td><td>⋐</td></tr>
     *     <tr><td><code>subset;</code></td><td>U+02282</td><td>⊂</td></tr>
     *     <tr><td><code>subseteq;</code></td><td>U+02286</td><td>⊆</td></tr>
     *     <tr><td><code>subseteqq;</code></td><td>U+02AC5</td><td>⫅</td></tr>
     *     <tr><td><code>SubsetEqual;</code></td><td>U+02286</td><td>⊆</td></tr>
     *     <tr><td><code>subsetneq;</code></td><td>U+0228A</td><td>⊊</td></tr>
     *     <tr><td><code>subsetneqq;</code></td><td>U+02ACB</td><td>⫋</td></tr>
     *     <tr><td><code>subsim;</code></td><td>U+02AC7</td><td>⫇</td></tr>
     *     <tr><td><code>subsub;</code></td><td>U+02AD5</td><td>⫕</td></tr>
     *     <tr><td><code>subsup;</code></td><td>U+02AD3</td><td>⫓</td></tr>
     *     <tr><td><code>succ;</code></td><td>U+0227B</td><td>≻</td></tr>
     *     <tr><td><code>succapprox;</code></td><td>U+02AB8</td><td>⪸</td></tr>
     *     <tr><td><code>succcurlyeq;</code></td><td>U+0227D</td><td>≽</td></tr>
     *     <tr><td><code>Succeeds;</code></td><td>U+0227B</td><td>≻</td></tr>
     *     <tr><td><code>SucceedsEqual;</code></td><td>U+02AB0</td><td>⪰</td></tr>
     *     <tr><td><code>SucceedsSlantEqual;</code></td><td>U+0227D</td><td>≽</td></tr>
     *     <tr><td><code>SucceedsTilde;</code></td><td>U+0227F</td><td>≿</td></tr>
     *     <tr><td><code>succeq;</code></td><td>U+02AB0</td><td>⪰</td></tr>
     *     <tr><td><code>succnapprox;</code></td><td>U+02ABA</td><td>⪺</td></tr>
     *     <tr><td><code>succneqq;</code></td><td>U+02AB6</td><td>⪶</td></tr>
     *     <tr><td><code>succnsim;</code></td><td>U+022E9</td><td>⋩</td></tr>
     *     <tr><td><code>succsim;</code></td><td>U+0227F</td><td>≿</td></tr>
     *     <tr><td><code>SuchThat;</code></td><td>U+0220B</td><td>∋</td></tr>
     *     <tr><td><code>Sum;</code></td><td>U+02211</td><td>∑</td></tr>
     *     <tr><td><code>sum;</code></td><td>U+02211</td><td>∑</td></tr>
     *     <tr><td><code>sung;</code></td><td>U+0266A</td><td>♪</td></tr>
     *     <tr><td><code>Sup;</code></td><td>U+022D1</td><td>⋑</td></tr>
     *     <tr><td><code>sup;</code></td><td>U+02283</td><td>⊃</td></tr>
     *     <tr><td><code>sup1;</code></td><td>U+000B9</td><td>¹</td></tr>
     *     <tr><td><code>sup1</code></td><td>U+000B9</td><td>¹</td></tr>
     *     <tr><td><code>sup2;</code></td><td>U+000B2</td><td>²</td></tr>
     *     <tr><td><code>sup2</code></td><td>U+000B2</td><td>²</td></tr>
     *     <tr><td><code>sup3;</code></td><td>U+000B3</td><td>³</td></tr>
     *     <tr><td><code>sup3</code></td><td>U+000B3</td><td>³</td></tr>
     *     <tr><td><code>supdot;</code></td><td>U+02ABE</td><td>⪾</td></tr>
     *     <tr><td><code>supdsub;</code></td><td>U+02AD8</td><td>⫘</td></tr>
     *     <tr><td><code>supE;</code></td><td>U+02AC6</td><td>⫆</td></tr>
     *     <tr><td><code>supe;</code></td><td>U+02287</td><td>⊇</td></tr>
     *     <tr><td><code>supedot;</code></td><td>U+02AC4</td><td>⫄</td></tr>
     *     <tr><td><code>Superset;</code></td><td>U+02283</td><td>⊃</td></tr>
     *     <tr><td><code>SupersetEqual;</code></td><td>U+02287</td><td>⊇</td></tr>
     *     <tr><td><code>suphsol;</code></td><td>U+027C9</td><td>⟉</td></tr>
     *     <tr><td><code>suphsub;</code></td><td>U+02AD7</td><td>⫗</td></tr>
     *     <tr><td><code>suplarr;</code></td><td>U+0297B</td><td>⥻</td></tr>
     *     <tr><td><code>supmult;</code></td><td>U+02AC2</td><td>⫂</td></tr>
     *     <tr><td><code>supnE;</code></td><td>U+02ACC</td><td>⫌</td></tr>
     *     <tr><td><code>supne;</code></td><td>U+0228B</td><td>⊋</td></tr>
     *     <tr><td><code>supplus;</code></td><td>U+02AC0</td><td>⫀</td></tr>
     *     <tr><td><code>Supset;</code></td><td>U+022D1</td><td>⋑</td></tr>
     *     <tr><td><code>supset;</code></td><td>U+02283</td><td>⊃</td></tr>
     *     <tr><td><code>supseteq;</code></td><td>U+02287</td><td>⊇</td></tr>
     *     <tr><td><code>supseteqq;</code></td><td>U+02AC6</td><td>⫆</td></tr>
     *     <tr><td><code>supsetneq;</code></td><td>U+0228B</td><td>⊋</td></tr>
     *     <tr><td><code>supsetneqq;</code></td><td>U+02ACC</td><td>⫌</td></tr>
     *     <tr><td><code>supsim;</code></td><td>U+02AC8</td><td>⫈</td></tr>
     *     <tr><td><code>supsub;</code></td><td>U+02AD4</td><td>⫔</td></tr>
     *     <tr><td><code>supsup;</code></td><td>U+02AD6</td><td>⫖</td></tr>
     *     <tr><td><code>swarhk;</code></td><td>U+02926</td><td>⤦</td></tr>
     *     <tr><td><code>swArr;</code></td><td>U+021D9</td><td>⇙</td></tr>
     *     <tr><td><code>swarr;</code></td><td>U+02199</td><td>↙</td></tr>
     *     <tr><td><code>swarrow;</code></td><td>U+02199</td><td>↙</td></tr>
     *     <tr><td><code>swnwar;</code></td><td>U+0292A</td><td>⤪</td></tr>
     *     <tr><td><code>szlig;</code></td><td>U+000DF</td><td>ß</td></tr>
     *     <tr><td><code>szlig</code></td><td>U+000DF</td><td>ß</td></tr>
     *     <tr><td><code>Tab;</code></td><td>U+00009</td><td>␉</td></tr>
     *     <tr><td><code>target;</code></td><td>U+02316</td><td>⌖</td></tr>
     *     <tr><td><code>Tau;</code></td><td>U+003A4</td><td>Τ</td></tr>
     *     <tr><td><code>tau;</code></td><td>U+003C4</td><td>τ</td></tr>
     *     <tr><td><code>tbrk;</code></td><td>U+023B4</td><td>⎴</td></tr>
     *     <tr><td><code>Tcaron;</code></td><td>U+00164</td><td>Ť</td></tr>
     *     <tr><td><code>tcaron;</code></td><td>U+00165</td><td>ť</td></tr>
     *     <tr><td><code>Tcedil;</code></td><td>U+00162</td><td>Ţ</td></tr>
     *     <tr><td><code>tcedil;</code></td><td>U+00163</td><td>ţ</td></tr>
     *     <tr><td><code>Tcy;</code></td><td>U+00422</td><td>Т</td></tr>
     *     <tr><td><code>tcy;</code></td><td>U+00442</td><td>т</td></tr>
     *     <tr><td><code>tdot;</code></td><td>U+020DB</td><td>◌⃛</td></tr>
     *     <tr><td><code>telrec;</code></td><td>U+02315</td><td>⌕</td></tr>
     *     <tr><td><code>Tfr;</code></td><td>U+1D517</td><td>𝔗</td></tr>
     *     <tr><td><code>tfr;</code></td><td>U+1D531</td><td>𝔱</td></tr>
     *     <tr><td><code>there4;</code></td><td>U+02234</td><td>∴</td></tr>
     *     <tr><td><code>Therefore;</code></td><td>U+02234</td><td>∴</td></tr>
     *     <tr><td><code>therefore;</code></td><td>U+02234</td><td>∴</td></tr>
     *     <tr><td><code>Theta;</code></td><td>U+00398</td><td>Θ</td></tr>
     *     <tr><td><code>theta;</code></td><td>U+003B8</td><td>θ</td></tr>
     *     <tr><td><code>thetasym;</code></td><td>U+003D1</td><td>ϑ</td></tr>
     *     <tr><td><code>thetav;</code></td><td>U+003D1</td><td>ϑ</td></tr>
     *     <tr><td><code>thickapprox;</code></td><td>U+02248</td><td>≈</td></tr>
     *     <tr><td><code>thicksim;</code></td><td>U+0223C</td><td>∼</td></tr>
     *     <tr><td><code>ThickSpace;</code></td><td>U+0205F U+0200A</td><td>  </td></tr>
     *     <tr><td><code>thinsp;</code></td><td>U+02009</td><td> </td></tr>
     *     <tr><td><code>ThinSpace;</code></td><td>U+02009</td><td> </td></tr>
     *     <tr><td><code>thkap;</code></td><td>U+02248</td><td>≈</td></tr>
     *     <tr><td><code>thksim;</code></td><td>U+0223C</td><td>∼</td></tr>
     *     <tr><td><code>THORN;</code></td><td>U+000DE</td><td>Þ</td></tr>
     *     <tr><td><code>THORN</code></td><td>U+000DE</td><td>Þ</td></tr>
     *     <tr><td><code>thorn;</code></td><td>U+000FE</td><td>þ</td></tr>
     *     <tr><td><code>thorn</code></td><td>U+000FE</td><td>þ</td></tr>
     *     <tr><td><code>Tilde;</code></td><td>U+0223C</td><td>∼</td></tr>
     *     <tr><td><code>tilde;</code></td><td>U+002DC</td><td>˜</td></tr>
     *     <tr><td><code>TildeEqual;</code></td><td>U+02243</td><td>≃</td></tr>
     *     <tr><td><code>TildeFullEqual;</code></td><td>U+02245</td><td>≅</td></tr>
     *     <tr><td><code>TildeTilde;</code></td><td>U+02248</td><td>≈</td></tr>
     *     <tr><td><code>times;</code></td><td>U+000D7</td><td>×</td></tr>
     *     <tr><td><code>times</code></td><td>U+000D7</td><td>×</td></tr>
     *     <tr><td><code>timesb;</code></td><td>U+022A0</td><td>⊠</td></tr>
     *     <tr><td><code>timesbar;</code></td><td>U+02A31</td><td>⨱</td></tr>
     *     <tr><td><code>timesd;</code></td><td>U+02A30</td><td>⨰</td></tr>
     *     <tr><td><code>tint;</code></td><td>U+0222D</td><td>∭</td></tr>
     *     <tr><td><code>toea;</code></td><td>U+02928</td><td>⤨</td></tr>
     *     <tr><td><code>top;</code></td><td>U+022A4</td><td>⊤</td></tr>
     *     <tr><td><code>topbot;</code></td><td>U+02336</td><td>⌶</td></tr>
     *     <tr><td><code>topcir;</code></td><td>U+02AF1</td><td>⫱</td></tr>
     *     <tr><td><code>Topf;</code></td><td>U+1D54B</td><td>𝕋</td></tr>
     *     <tr><td><code>topf;</code></td><td>U+1D565</td><td>𝕥</td></tr>
     *     <tr><td><code>topfork;</code></td><td>U+02ADA</td><td>⫚</td></tr>
     *     <tr><td><code>tosa;</code></td><td>U+02929</td><td>⤩</td></tr>
     *     <tr><td><code>tprime;</code></td><td>U+02034</td><td>‴</td></tr>
     *     <tr><td><code>TRADE;</code></td><td>U+02122</td><td>™</td></tr>
     *     <tr><td><code>trade;</code></td><td>U+02122</td><td>™</td></tr>
     *     <tr><td><code>triangle;</code></td><td>U+025B5</td><td>▵</td></tr>
     *     <tr><td><code>triangledown;</code></td><td>U+025BF</td><td>▿</td></tr>
     *     <tr><td><code>triangleleft;</code></td><td>U+025C3</td><td>◃</td></tr>
     *     <tr><td><code>trianglelefteq;</code></td><td>U+022B4</td><td>⊴</td></tr>
     *     <tr><td><code>triangleq;</code></td><td>U+0225C</td><td>≜</td></tr>
     *     <tr><td><code>triangleright;</code></td><td>U+025B9</td><td>▹</td></tr>
     *     <tr><td><code>trianglerighteq;</code></td><td>U+022B5</td><td>⊵</td></tr>
     *     <tr><td><code>tridot;</code></td><td>U+025EC</td><td>◬</td></tr>
     *     <tr><td><code>trie;</code></td><td>U+0225C</td><td>≜</td></tr>
     *     <tr><td><code>triminus;</code></td><td>U+02A3A</td><td>⨺</td></tr>
     *     <tr><td><code>TripleDot;</code></td><td>U+020DB</td><td>◌⃛</td></tr>
     *     <tr><td><code>triplus;</code></td><td>U+02A39</td><td>⨹</td></tr>
     *     <tr><td><code>trisb;</code></td><td>U+029CD</td><td>⧍</td></tr>
     *     <tr><td><code>tritime;</code></td><td>U+02A3B</td><td>⨻</td></tr>
     *     <tr><td><code>trpezium;</code></td><td>U+023E2</td><td>⏢</td></tr>
     *     <tr><td><code>Tscr;</code></td><td>U+1D4AF</td><td>𝒯</td></tr>
     *     <tr><td><code>tscr;</code></td><td>U+1D4C9</td><td>𝓉</td></tr>
     *     <tr><td><code>TScy;</code></td><td>U+00426</td><td>Ц</td></tr>
     *     <tr><td><code>tscy;</code></td><td>U+00446</td><td>ц</td></tr>
     *     <tr><td><code>TSHcy;</code></td><td>U+0040B</td><td>Ћ</td></tr>
     *     <tr><td><code>tshcy;</code></td><td>U+0045B</td><td>ћ</td></tr>
     *     <tr><td><code>Tstrok;</code></td><td>U+00166</td><td>Ŧ</td></tr>
     *     <tr><td><code>tstrok;</code></td><td>U+00167</td><td>ŧ</td></tr>
     *     <tr><td><code>twixt;</code></td><td>U+0226C</td><td>≬</td></tr>
     *     <tr><td><code>twoheadleftarrow;</code></td><td>U+0219E</td><td>↞</td></tr>
     *     <tr><td><code>twoheadrightarrow;</code></td><td>U+021A0</td><td>↠</td></tr>
     *     <tr><td><code>Uacute;</code></td><td>U+000DA</td><td>Ú</td></tr>
     *     <tr><td><code>Uacute</code></td><td>U+000DA</td><td>Ú</td></tr>
     *     <tr><td><code>uacute;</code></td><td>U+000FA</td><td>ú</td></tr>
     *     <tr><td><code>uacute</code></td><td>U+000FA</td><td>ú</td></tr>
     *     <tr><td><code>Uarr;</code></td><td>U+0219F</td><td>↟</td></tr>
     *     <tr><td><code>uArr;</code></td><td>U+021D1</td><td>⇑</td></tr>
     *     <tr><td><code>uarr;</code></td><td>U+02191</td><td>↑</td></tr>
     *     <tr><td><code>Uarrocir;</code></td><td>U+02949</td><td>⥉</td></tr>
     *     <tr><td><code>Ubrcy;</code></td><td>U+0040E</td><td>Ў</td></tr>
     *     <tr><td><code>ubrcy;</code></td><td>U+0045E</td><td>ў</td></tr>
     *     <tr><td><code>Ubreve;</code></td><td>U+0016C</td><td>Ŭ</td></tr>
     *     <tr><td><code>ubreve;</code></td><td>U+0016D</td><td>ŭ</td></tr>
     *     <tr><td><code>Ucirc;</code></td><td>U+000DB</td><td>Û</td></tr>
     *     <tr><td><code>Ucirc</code></td><td>U+000DB</td><td>Û</td></tr>
     *     <tr><td><code>ucirc;</code></td><td>U+000FB</td><td>û</td></tr>
     *     <tr><td><code>ucirc</code></td><td>U+000FB</td><td>û</td></tr>
     *     <tr><td><code>Ucy;</code></td><td>U+00423</td><td>У</td></tr>
     *     <tr><td><code>ucy;</code></td><td>U+00443</td><td>у</td></tr>
     *     <tr><td><code>udarr;</code></td><td>U+021C5</td><td>⇅</td></tr>
     *     <tr><td><code>Udblac;</code></td><td>U+00170</td><td>Ű</td></tr>
     *     <tr><td><code>udblac;</code></td><td>U+00171</td><td>ű</td></tr>
     *     <tr><td><code>udhar;</code></td><td>U+0296E</td><td>⥮</td></tr>
     *     <tr><td><code>ufisht;</code></td><td>U+0297E</td><td>⥾</td></tr>
     *     <tr><td><code>Ufr;</code></td><td>U+1D518</td><td>𝔘</td></tr>
     *     <tr><td><code>ufr;</code></td><td>U+1D532</td><td>𝔲</td></tr>
     *     <tr><td><code>Ugrave;</code></td><td>U+000D9</td><td>Ù</td></tr>
     *     <tr><td><code>Ugrave</code></td><td>U+000D9</td><td>Ù</td></tr>
     *     <tr><td><code>ugrave;</code></td><td>U+000F9</td><td>ù</td></tr>
     *     <tr><td><code>ugrave</code></td><td>U+000F9</td><td>ù</td></tr>
     *     <tr><td><code>uHar;</code></td><td>U+02963</td><td>⥣</td></tr>
     *     <tr><td><code>uharl;</code></td><td>U+021BF</td><td>↿</td></tr>
     *     <tr><td><code>uharr;</code></td><td>U+021BE</td><td>↾</td></tr>
     *     <tr><td><code>uhblk;</code></td><td>U+02580</td><td>▀</td></tr>
     *     <tr><td><code>ulcorn;</code></td><td>U+0231C</td><td>⌜</td></tr>
     *     <tr><td><code>ulcorner;</code></td><td>U+0231C</td><td>⌜</td></tr>
     *     <tr><td><code>ulcrop;</code></td><td>U+0230F</td><td>⌏</td></tr>
     *     <tr><td><code>ultri;</code></td><td>U+025F8</td><td>◸</td></tr>
     *     <tr><td><code>Umacr;</code></td><td>U+0016A</td><td>Ū</td></tr>
     *     <tr><td><code>umacr;</code></td><td>U+0016B</td><td>ū</td></tr>
     *     <tr><td><code>uml;</code></td><td>U+000A8</td><td>¨</td></tr>
     *     <tr><td><code>uml</code></td><td>U+000A8</td><td>¨</td></tr>
     *     <tr><td><code>UnderBar;</code></td><td>U+0005F</td><td>_</td></tr>
     *     <tr><td><code>UnderBrace;</code></td><td>U+023DF</td><td>⏟</td></tr>
     *     <tr><td><code>UnderBracket;</code></td><td>U+023B5</td><td>⎵</td></tr>
     *     <tr><td><code>UnderParenthesis;</code></td><td>U+023DD</td><td>⏝</td></tr>
     *     <tr><td><code>Union;</code></td><td>U+022C3</td><td>⋃</td></tr>
     *     <tr><td><code>UnionPlus;</code></td><td>U+0228E</td><td>⊎</td></tr>
     *     <tr><td><code>Uogon;</code></td><td>U+00172</td><td>Ų</td></tr>
     *     <tr><td><code>uogon;</code></td><td>U+00173</td><td>ų</td></tr>
     *     <tr><td><code>Uopf;</code></td><td>U+1D54C</td><td>𝕌</td></tr>
     *     <tr><td><code>uopf;</code></td><td>U+1D566</td><td>𝕦</td></tr>
     *     <tr><td><code>UpArrow;</code></td><td>U+02191</td><td>↑</td></tr>
     *     <tr><td><code>Uparrow;</code></td><td>U+021D1</td><td>⇑</td></tr>
     *     <tr><td><code>uparrow;</code></td><td>U+02191</td><td>↑</td></tr>
     *     <tr><td><code>UpArrowBar;</code></td><td>U+02912</td><td>⤒</td></tr>
     *     <tr><td><code>UpArrowDownArrow;</code></td><td>U+021C5</td><td>⇅</td></tr>
     *     <tr><td><code>UpDownArrow;</code></td><td>U+02195</td><td>↕</td></tr>
     *     <tr><td><code>Updownarrow;</code></td><td>U+021D5</td><td>⇕</td></tr>
     *     <tr><td><code>updownarrow;</code></td><td>U+02195</td><td>↕</td></tr>
     *     <tr><td><code>UpEquilibrium;</code></td><td>U+0296E</td><td>⥮</td></tr>
     *     <tr><td><code>upharpoonleft;</code></td><td>U+021BF</td><td>↿</td></tr>
     *     <tr><td><code>upharpoonright;</code></td><td>U+021BE</td><td>↾</td></tr>
     *     <tr><td><code>uplus;</code></td><td>U+0228E</td><td>⊎</td></tr>
     *     <tr><td><code>UpperLeftArrow;</code></td><td>U+02196</td><td>↖</td></tr>
     *     <tr><td><code>UpperRightArrow;</code></td><td>U+02197</td><td>↗</td></tr>
     *     <tr><td><code>Upsi;</code></td><td>U+003D2</td><td>ϒ</td></tr>
     *     <tr><td><code>upsi;</code></td><td>U+003C5</td><td>υ</td></tr>
     *     <tr><td><code>upsih;</code></td><td>U+003D2</td><td>ϒ</td></tr>
     *     <tr><td><code>Upsilon;</code></td><td>U+003A5</td><td>Υ</td></tr>
     *     <tr><td><code>upsilon;</code></td><td>U+003C5</td><td>υ</td></tr>
     *     <tr><td><code>UpTee;</code></td><td>U+022A5</td><td>⊥</td></tr>
     *     <tr><td><code>UpTeeArrow;</code></td><td>U+021A5</td><td>↥</td></tr>
     *     <tr><td><code>upuparrows;</code></td><td>U+021C8</td><td>⇈</td></tr>
     *     <tr><td><code>urcorn;</code></td><td>U+0231D</td><td>⌝</td></tr>
     *     <tr><td><code>urcorner;</code></td><td>U+0231D</td><td>⌝</td></tr>
     *     <tr><td><code>urcrop;</code></td><td>U+0230E</td><td>⌎</td></tr>
     *     <tr><td><code>Uring;</code></td><td>U+0016E</td><td>Ů</td></tr>
     *     <tr><td><code>uring;</code></td><td>U+0016F</td><td>ů</td></tr>
     *     <tr><td><code>urtri;</code></td><td>U+025F9</td><td>◹</td></tr>
     *     <tr><td><code>Uscr;</code></td><td>U+1D4B0</td><td>𝒰</td></tr>
     *     <tr><td><code>uscr;</code></td><td>U+1D4CA</td><td>𝓊</td></tr>
     *     <tr><td><code>utdot;</code></td><td>U+022F0</td><td>⋰</td></tr>
     *     <tr><td><code>Utilde;</code></td><td>U+00168</td><td>Ũ</td></tr>
     *     <tr><td><code>utilde;</code></td><td>U+00169</td><td>ũ</td></tr>
     *     <tr><td><code>utri;</code></td><td>U+025B5</td><td>▵</td></tr>
     *     <tr><td><code>utrif;</code></td><td>U+025B4</td><td>▴</td></tr>
     *     <tr><td><code>uuarr;</code></td><td>U+021C8</td><td>⇈</td></tr>
     *     <tr><td><code>Uuml;</code></td><td>U+000DC</td><td>Ü</td></tr>
     *     <tr><td><code>Uuml</code></td><td>U+000DC</td><td>Ü</td></tr>
     *     <tr><td><code>uuml;</code></td><td>U+000FC</td><td>ü</td></tr>
     *     <tr><td><code>uuml</code></td><td>U+000FC</td><td>ü</td></tr>
     *     <tr><td><code>uwangle;</code></td><td>U+029A7</td><td>⦧</td></tr>
     *     <tr><td><code>vangrt;</code></td><td>U+0299C</td><td>⦜</td></tr>
     *     <tr><td><code>varepsilon;</code></td><td>U+003F5</td><td>ϵ</td></tr>
     *     <tr><td><code>varkappa;</code></td><td>U+003F0</td><td>ϰ</td></tr>
     *     <tr><td><code>varnothing;</code></td><td>U+02205</td><td>∅</td></tr>
     *     <tr><td><code>varphi;</code></td><td>U+003D5</td><td>ϕ</td></tr>
     *     <tr><td><code>varpi;</code></td><td>U+003D6</td><td>ϖ</td></tr>
     *     <tr><td><code>varpropto;</code></td><td>U+0221D</td><td>∝</td></tr>
     *     <tr><td><code>vArr;</code></td><td>U+021D5</td><td>⇕</td></tr>
     *     <tr><td><code>varr;</code></td><td>U+02195</td><td>↕</td></tr>
     *     <tr><td><code>varrho;</code></td><td>U+003F1</td><td>ϱ</td></tr>
     *     <tr><td><code>varsigma;</code></td><td>U+003C2</td><td>ς</td></tr>
     *     <tr><td><code>varsubsetneq;</code></td><td>U+0228A U+0FE00</td><td>⊊︀</td></tr>
     *     <tr><td><code>varsubsetneqq;</code></td><td>U+02ACB U+0FE00</td><td>⫋︀</td></tr>
     *     <tr><td><code>varsupsetneq;</code></td><td>U+0228B U+0FE00</td><td>⊋︀</td></tr>
     *     <tr><td><code>varsupsetneqq;</code></td><td>U+02ACC U+0FE00</td><td>⫌︀</td></tr>
     *     <tr><td><code>vartheta;</code></td><td>U+003D1</td><td>ϑ</td></tr>
     *     <tr><td><code>vartriangleleft;</code></td><td>U+022B2</td><td>⊲</td></tr>
     *     <tr><td><code>vartriangleright;</code></td><td>U+022B3</td><td>⊳</td></tr>
     *     <tr><td><code>Vbar;</code></td><td>U+02AEB</td><td>⫫</td></tr>
     *     <tr><td><code>vBar;</code></td><td>U+02AE8</td><td>⫨</td></tr>
     *     <tr><td><code>vBarv;</code></td><td>U+02AE9</td><td>⫩</td></tr>
     *     <tr><td><code>Vcy;</code></td><td>U+00412</td><td>В</td></tr>
     *     <tr><td><code>vcy;</code></td><td>U+00432</td><td>в</td></tr>
     *     <tr><td><code>VDash;</code></td><td>U+022AB</td><td>⊫</td></tr>
     *     <tr><td><code>Vdash;</code></td><td>U+022A9</td><td>⊩</td></tr>
     *     <tr><td><code>vDash;</code></td><td>U+022A8</td><td>⊨</td></tr>
     *     <tr><td><code>vdash;</code></td><td>U+022A2</td><td>⊢</td></tr>
     *     <tr><td><code>Vdashl;</code></td><td>U+02AE6</td><td>⫦</td></tr>
     *     <tr><td><code>Vee;</code></td><td>U+022C1</td><td>⋁</td></tr>
     *     <tr><td><code>vee;</code></td><td>U+02228</td><td>∨</td></tr>
     *     <tr><td><code>veebar;</code></td><td>U+022BB</td><td>⊻</td></tr>
     *     <tr><td><code>veeeq;</code></td><td>U+0225A</td><td>≚</td></tr>
     *     <tr><td><code>vellip;</code></td><td>U+022EE</td><td>⋮</td></tr>
     *     <tr><td><code>Verbar;</code></td><td>U+02016</td><td>‖</td></tr>
     *     <tr><td><code>verbar;</code></td><td>U+0007C</td><td>|</td></tr>
     *     <tr><td><code>Vert;</code></td><td>U+02016</td><td>‖</td></tr>
     *     <tr><td><code>vert;</code></td><td>U+0007C</td><td>|</td></tr>
     *     <tr><td><code>VerticalBar;</code></td><td>U+02223</td><td>∣</td></tr>
     *     <tr><td><code>VerticalLine;</code></td><td>U+0007C</td><td>|</td></tr>
     *     <tr><td><code>VerticalSeparator;</code></td><td>U+02758</td><td>❘</td></tr>
     *     <tr><td><code>VerticalTilde;</code></td><td>U+02240</td><td>≀</td></tr>
     *     <tr><td><code>VeryThinSpace;</code></td><td>U+0200A</td><td> </td></tr>
     *     <tr><td><code>Vfr;</code></td><td>U+1D519</td><td>𝔙</td></tr>
     *     <tr><td><code>vfr;</code></td><td>U+1D533</td><td>𝔳</td></tr>
     *     <tr><td><code>vltri;</code></td><td>U+022B2</td><td>⊲</td></tr>
     *     <tr><td><code>vnsub;</code></td><td>U+02282 U+020D2</td><td>⊂⃒</td></tr>
     *     <tr><td><code>vnsup;</code></td><td>U+02283 U+020D2</td><td>⊃⃒</td></tr>
     *     <tr><td><code>Vopf;</code></td><td>U+1D54D</td><td>𝕍</td></tr>
     *     <tr><td><code>vopf;</code></td><td>U+1D567</td><td>𝕧</td></tr>
     *     <tr><td><code>vprop;</code></td><td>U+0221D</td><td>∝</td></tr>
     *     <tr><td><code>vrtri;</code></td><td>U+022B3</td><td>⊳</td></tr>
     *     <tr><td><code>Vscr;</code></td><td>U+1D4B1</td><td>𝒱</td></tr>
     *     <tr><td><code>vscr;</code></td><td>U+1D4CB</td><td>𝓋</td></tr>
     *     <tr><td><code>vsubnE;</code></td><td>U+02ACB U+0FE00</td><td>⫋︀</td></tr>
     *     <tr><td><code>vsubne;</code></td><td>U+0228A U+0FE00</td><td>⊊︀</td></tr>
     *     <tr><td><code>vsupnE;</code></td><td>U+02ACC U+0FE00</td><td>⫌︀</td></tr>
     *     <tr><td><code>vsupne;</code></td><td>U+0228B U+0FE00</td><td>⊋︀</td></tr>
     *     <tr><td><code>Vvdash;</code></td><td>U+022AA</td><td>⊪</td></tr>
     *     <tr><td><code>vzigzag;</code></td><td>U+0299A</td><td>⦚</td></tr>
     *     <tr><td><code>Wcirc;</code></td><td>U+00174</td><td>Ŵ</td></tr>
     *     <tr><td><code>wcirc;</code></td><td>U+00175</td><td>ŵ</td></tr>
     *     <tr><td><code>wedbar;</code></td><td>U+02A5F</td><td>⩟</td></tr>
     *     <tr><td><code>Wedge;</code></td><td>U+022C0</td><td>⋀</td></tr>
     *     <tr><td><code>wedge;</code></td><td>U+02227</td><td>∧</td></tr>
     *     <tr><td><code>wedgeq;</code></td><td>U+02259</td><td>≙</td></tr>
     *     <tr><td><code>weierp;</code></td><td>U+02118</td><td>℘</td></tr>
     *     <tr><td><code>Wfr;</code></td><td>U+1D51A</td><td>𝔚</td></tr>
     *     <tr><td><code>wfr;</code></td><td>U+1D534</td><td>𝔴</td></tr>
     *     <tr><td><code>Wopf;</code></td><td>U+1D54E</td><td>𝕎</td></tr>
     *     <tr><td><code>wopf;</code></td><td>U+1D568</td><td>𝕨</td></tr>
     *     <tr><td><code>wp;</code></td><td>U+02118</td><td>℘</td></tr>
     *     <tr><td><code>wr;</code></td><td>U+02240</td><td>≀</td></tr>
     *     <tr><td><code>wreath;</code></td><td>U+02240</td><td>≀</td></tr>
     *     <tr><td><code>Wscr;</code></td><td>U+1D4B2</td><td>𝒲</td></tr>
     *     <tr><td><code>wscr;</code></td><td>U+1D4CC</td><td>𝓌</td></tr>
     *     <tr><td><code>xcap;</code></td><td>U+022C2</td><td>⋂</td></tr>
     *     <tr><td><code>xcirc;</code></td><td>U+025EF</td><td>◯</td></tr>
     *     <tr><td><code>xcup;</code></td><td>U+022C3</td><td>⋃</td></tr>
     *     <tr><td><code>xdtri;</code></td><td>U+025BD</td><td>▽</td></tr>
     *     <tr><td><code>Xfr;</code></td><td>U+1D51B</td><td>𝔛</td></tr>
     *     <tr><td><code>xfr;</code></td><td>U+1D535</td><td>𝔵</td></tr>
     *     <tr><td><code>xhArr;</code></td><td>U+027FA</td><td>⟺</td></tr>
     *     <tr><td><code>xharr;</code></td><td>U+027F7</td><td>⟷</td></tr>
     *     <tr><td><code>Xi;</code></td><td>U+0039E</td><td>Ξ</td></tr>
     *     <tr><td><code>xi;</code></td><td>U+003BE</td><td>ξ</td></tr>
     *     <tr><td><code>xlArr;</code></td><td>U+027F8</td><td>⟸</td></tr>
     *     <tr><td><code>xlarr;</code></td><td>U+027F5</td><td>⟵</td></tr>
     *     <tr><td><code>xmap;</code></td><td>U+027FC</td><td>⟼</td></tr>
     *     <tr><td><code>xnis;</code></td><td>U+022FB</td><td>⋻</td></tr>
     *     <tr><td><code>xodot;</code></td><td>U+02A00</td><td>⨀</td></tr>
     *     <tr><td><code>Xopf;</code></td><td>U+1D54F</td><td>𝕏</td></tr>
     *     <tr><td><code>xopf;</code></td><td>U+1D569</td><td>𝕩</td></tr>
     *     <tr><td><code>xoplus;</code></td><td>U+02A01</td><td>⨁</td></tr>
     *     <tr><td><code>xotime;</code></td><td>U+02A02</td><td>⨂</td></tr>
     *     <tr><td><code>xrArr;</code></td><td>U+027F9</td><td>⟹</td></tr>
     *     <tr><td><code>xrarr;</code></td><td>U+027F6</td><td>⟶</td></tr>
     *     <tr><td><code>Xscr;</code></td><td>U+1D4B3</td><td>𝒳</td></tr>
     *     <tr><td><code>xscr;</code></td><td>U+1D4CD</td><td>𝓍</td></tr>
     *     <tr><td><code>xsqcup;</code></td><td>U+02A06</td><td>⨆</td></tr>
     *     <tr><td><code>xuplus;</code></td><td>U+02A04</td><td>⨄</td></tr>
     *     <tr><td><code>xutri;</code></td><td>U+025B3</td><td>△</td></tr>
     *     <tr><td><code>xvee;</code></td><td>U+022C1</td><td>⋁</td></tr>
     *     <tr><td><code>xwedge;</code></td><td>U+022C0</td><td>⋀</td></tr>
     *     <tr><td><code>Yacute;</code></td><td>U+000DD</td><td>Ý</td></tr>
     *     <tr><td><code>Yacute</code></td><td>U+000DD</td><td>Ý</td></tr>
     *     <tr><td><code>yacute;</code></td><td>U+000FD</td><td>ý</td></tr>
     *     <tr><td><code>yacute</code></td><td>U+000FD</td><td>ý</td></tr>
     *     <tr><td><code>YAcy;</code></td><td>U+0042F</td><td>Я</td></tr>
     *     <tr><td><code>yacy;</code></td><td>U+0044F</td><td>я</td></tr>
     *     <tr><td><code>Ycirc;</code></td><td>U+00176</td><td>Ŷ</td></tr>
     *     <tr><td><code>ycirc;</code></td><td>U+00177</td><td>ŷ</td></tr>
     *     <tr><td><code>Ycy;</code></td><td>U+0042B</td><td>Ы</td></tr>
     *     <tr><td><code>ycy;</code></td><td>U+0044B</td><td>ы</td></tr>
     *     <tr><td><code>yen;</code></td><td>U+000A5</td><td>¥</td></tr>
     *     <tr><td><code>yen</code></td><td>U+000A5</td><td>¥</td></tr>
     *     <tr><td><code>Yfr;</code></td><td>U+1D51C</td><td>𝔜</td></tr>
     *     <tr><td><code>yfr;</code></td><td>U+1D536</td><td>𝔶</td></tr>
     *     <tr><td><code>YIcy;</code></td><td>U+00407</td><td>Ї</td></tr>
     *     <tr><td><code>yicy;</code></td><td>U+00457</td><td>ї</td></tr>
     *     <tr><td><code>Yopf;</code></td><td>U+1D550</td><td>𝕐</td></tr>
     *     <tr><td><code>yopf;</code></td><td>U+1D56A</td><td>𝕪</td></tr>
     *     <tr><td><code>Yscr;</code></td><td>U+1D4B4</td><td>𝒴</td></tr>
     *     <tr><td><code>yscr;</code></td><td>U+1D4CE</td><td>𝓎</td></tr>
     *     <tr><td><code>YUcy;</code></td><td>U+0042E</td><td>Ю</td></tr>
     *     <tr><td><code>yucy;</code></td><td>U+0044E</td><td>ю</td></tr>
     *     <tr><td><code>Yuml;</code></td><td>U+00178</td><td>Ÿ</td></tr>
     *     <tr><td><code>yuml;</code></td><td>U+000FF</td><td>ÿ</td></tr>
     *     <tr><td><code>yuml</code></td><td>U+000FF</td><td>ÿ</td></tr>
     *     <tr><td><code>Zacute;</code></td><td>U+00179</td><td>Ź</td></tr>
     *     <tr><td><code>zacute;</code></td><td>U+0017A</td><td>ź</td></tr>
     *     <tr><td><code>Zcaron;</code></td><td>U+0017D</td><td>Ž</td></tr>
     *     <tr><td><code>zcaron;</code></td><td>U+0017E</td><td>ž</td></tr>
     *     <tr><td><code>Zcy;</code></td><td>U+00417</td><td>З</td></tr>
     *     <tr><td><code>zcy;</code></td><td>U+00437</td><td>з</td></tr>
     *     <tr><td><code>Zdot;</code></td><td>U+0017B</td><td>Ż</td></tr>
     *     <tr><td><code>zdot;</code></td><td>U+0017C</td><td>ż</td></tr>
     *     <tr><td><code>zeetrf;</code></td><td>U+02128</td><td>ℨ</td></tr>
     *     <tr><td><code>ZeroWidthSpace;</code></td><td>U+0200B</td><td>​</td></tr>
     *     <tr><td><code>Zeta;</code></td><td>U+00396</td><td>Ζ</td></tr>
     *     <tr><td><code>zeta;</code></td><td>U+003B6</td><td>ζ</td></tr>
     *     <tr><td><code>Zfr;</code></td><td>U+02128</td><td>ℨ</td></tr>
     *     <tr><td><code>zfr;</code></td><td>U+1D537</td><td>𝔷</td></tr>
     *     <tr><td><code>ZHcy;</code></td><td>U+00416</td><td>Ж</td></tr>
     *     <tr><td><code>zhcy;</code></td><td>U+00436</td><td>ж</td></tr>
     *     <tr><td><code>zigrarr;</code></td><td>U+021DD</td><td>⇝</td></tr>
     *     <tr><td><code>Zopf;</code></td><td>U+02124</td><td>ℤ</td></tr>
     *     <tr><td><code>zopf;</code></td><td>U+1D56B</td><td>𝕫</td></tr>
     *     <tr><td><code>Zscr;</code></td><td>U+1D4B5</td><td>𝒵</td></tr>
     *     <tr><td><code>zscr;</code></td><td>U+1D4CF</td><td>𝓏</td></tr>
     *     <tr><td><code>zwj;</code></td><td>U+0200D</td><td>‍</td></tr>
     *     <tr><td><code>zwnj;</code></td><td>U+0200C</td><td>‌</td></tr>
     *   </tbody>
     * </table>
     * 
     * @see <a href="https://www.w3.org/TR/2014/REC-html5-20141028/syntax.html#named-character-references">8.5 Named character references</a>
     */
    /* package */static final SortedMap<String, int[]> entityMap;
    
    /* package */static final int longestEntityName;
    
    static {
        replacementMap = new HashMap<>();
        replacementMap.put(0x00, '\uFFFD');
        replacementMap.put(0x80, '\u20AC');
        replacementMap.put(0x82, '\u201A');
        replacementMap.put(0x83, '\u0192');
        replacementMap.put(0x84, '\u201E');
        replacementMap.put(0x85, '\u2026');
        replacementMap.put(0x86, '\u2020');
        replacementMap.put(0x87, '\u2021');
        replacementMap.put(0x88, '\u02C6');
        replacementMap.put(0x89, '\u2030');
        replacementMap.put(0x8A, '\u0160');
        replacementMap.put(0x8B, '\u2039');
        replacementMap.put(0x8C, '\u0152');
        replacementMap.put(0x8E, '\u017D');
        replacementMap.put(0x91, '\u2018');
        replacementMap.put(0x92, '\u2019');
        replacementMap.put(0x93, '\u201C');
        replacementMap.put(0x94, '\u201D');
        replacementMap.put(0x95, '\u2022');
        replacementMap.put(0x96, '\u2013');
        replacementMap.put(0x97, '\u2014');
        replacementMap.put(0x98, '\u02DC');
        replacementMap.put(0x99, '\u2122');
        replacementMap.put(0x9A, '\u0161');
        replacementMap.put(0x9B, '\u203A');
        replacementMap.put(0x9C, '\u0153');
        replacementMap.put(0x9E, '\u017E');
        replacementMap.put(0x9F, '\u0178');
        for (int i = 0xD800; i <= 0xDFFF; i++ ) {
            replacementMap.put(i, '\uFFFD');
        }
        
        disallowedCharacters = new HashSet<>();
        for (int i = 0x0001; i <= 0x0008; i++ ) {
            disallowedCharacters.add(i);
        }
        for (int i = 0x000D; i <= 0x001F; i++ ) {
            disallowedCharacters.add(i);
        }
        for (int i = 0x007F; i <= 0x009F; i++ ) {
            disallowedCharacters.add(i);
        }
        for (int i = 0xFDD0; i <= 0xFDEF; i++ ) {
            disallowedCharacters.add(i);
        }
        disallowedCharacters.add(0x000B);
        disallowedCharacters.add(0xFFFE);
        disallowedCharacters.add(0xFFFF);
        disallowedCharacters.add(0x1FFFE);
        disallowedCharacters.add(0x1FFFF);
        disallowedCharacters.add(0x2FFFE);
        disallowedCharacters.add(0x2FFFF);
        disallowedCharacters.add(0x3FFFE);
        disallowedCharacters.add(0x3FFFF);
        disallowedCharacters.add(0x4FFFE);
        disallowedCharacters.add(0x4FFFF);
        disallowedCharacters.add(0x5FFFE);
        disallowedCharacters.add(0x5FFFF);
        disallowedCharacters.add(0x6FFFE);
        disallowedCharacters.add(0x6FFFF);
        disallowedCharacters.add(0x7FFFE);
        disallowedCharacters.add(0x7FFFF);
        disallowedCharacters.add(0x8FFFE);
        disallowedCharacters.add(0x8FFFF);
        disallowedCharacters.add(0x9FFFE);
        disallowedCharacters.add(0x9FFFF);
        disallowedCharacters.add(0xAFFFE);
        disallowedCharacters.add(0xAFFFF);
        disallowedCharacters.add(0xBFFFE);
        disallowedCharacters.add(0xBFFFF);
        disallowedCharacters.add(0xCFFFE);
        disallowedCharacters.add(0xCFFFF);
        disallowedCharacters.add(0xDFFFE);
        disallowedCharacters.add(0xDFFFF);
        disallowedCharacters.add(0xEFFFE);
        disallowedCharacters.add(0xEFFFF);
        disallowedCharacters.add(0xFFFFE);
        disallowedCharacters.add(0xFFFFF);
        disallowedCharacters.add(0x10FFFE);
        disallowedCharacters.add(0x10FFFF);
        
        entityMap = new TreeMap<>();
        // Skipping entity missing semicolon: &AElig = 0x00C6
        entityMap.put("AElig", new int[] { 0x00C6 });
        // Skipping entity missing semicolon: &AMP = 0x0026
        entityMap.put("AMP", new int[] { 0x0026 });
        // Skipping entity missing semicolon: &Aacute = 0x00C1
        entityMap.put("Aacute", new int[] { 0x00C1 });
        entityMap.put("Abreve", new int[] { 0x0102 });
        // Skipping entity missing semicolon: &Acirc = 0x00C2
        entityMap.put("Acirc", new int[] { 0x00C2 });
        entityMap.put("Acy", new int[] { 0x0410 });
        entityMap.put("Afr", new int[] { 0x1D504 });
        // Skipping entity missing semicolon: &Agrave = 0x00C0
        entityMap.put("Agrave", new int[] { 0x00C0 });
        entityMap.put("Alpha", new int[] { 0x0391 });
        entityMap.put("Amacr", new int[] { 0x0100 });
        entityMap.put("And", new int[] { 0x2A53 });
        entityMap.put("Aogon", new int[] { 0x0104 });
        entityMap.put("Aopf", new int[] { 0x1D538 });
        entityMap.put("ApplyFunction", new int[] { 0x2061 });
        // Skipping entity missing semicolon: &Aring = 0x00C5
        entityMap.put("Aring", new int[] { 0x00C5 });
        entityMap.put("Ascr", new int[] { 0x1D49C });
        entityMap.put("Assign", new int[] { 0x2254 });
        // Skipping entity missing semicolon: &Atilde = 0x00C3
        entityMap.put("Atilde", new int[] { 0x00C3 });
        // Skipping entity missing semicolon: &Auml = 0x00C4
        entityMap.put("Auml", new int[] { 0x00C4 });
        entityMap.put("Backslash", new int[] { 0x2216 });
        entityMap.put("Barv", new int[] { 0x2AE7 });
        entityMap.put("Barwed", new int[] { 0x2306 });
        entityMap.put("Bcy", new int[] { 0x0411 });
        entityMap.put("Because", new int[] { 0x2235 });
        entityMap.put("Bernoullis", new int[] { 0x212C });
        entityMap.put("Beta", new int[] { 0x0392 });
        entityMap.put("Bfr", new int[] { 0x1D505 });
        entityMap.put("Bopf", new int[] { 0x1D539 });
        entityMap.put("Breve", new int[] { 0x02D8 });
        entityMap.put("Bscr", new int[] { 0x212C });
        entityMap.put("Bumpeq", new int[] { 0x224E });
        entityMap.put("CHcy", new int[] { 0x0427 });
        // Skipping entity missing semicolon: &COPY = 0x00A9
        entityMap.put("COPY", new int[] { 0x00A9 });
        entityMap.put("Cacute", new int[] { 0x0106 });
        entityMap.put("Cap", new int[] { 0x22D2 });
        entityMap.put("CapitalDifferentialD", new int[] { 0x2145 });
        entityMap.put("Cayleys", new int[] { 0x212D });
        entityMap.put("Ccaron", new int[] { 0x010C });
        // Skipping entity missing semicolon: &Ccedil = 0x00C7
        entityMap.put("Ccedil", new int[] { 0x00C7 });
        entityMap.put("Ccirc", new int[] { 0x0108 });
        entityMap.put("Cconint", new int[] { 0x2230 });
        entityMap.put("Cdot", new int[] { 0x010A });
        entityMap.put("Cedilla", new int[] { 0x00B8 });
        entityMap.put("CenterDot", new int[] { 0x00B7 });
        entityMap.put("Cfr", new int[] { 0x212D });
        entityMap.put("Chi", new int[] { 0x03A7 });
        entityMap.put("CircleDot", new int[] { 0x2299 });
        entityMap.put("CircleMinus", new int[] { 0x2296 });
        entityMap.put("CirclePlus", new int[] { 0x2295 });
        entityMap.put("CircleTimes", new int[] { 0x2297 });
        entityMap.put("ClockwiseContourIntegral", new int[] { 0x2232 });
        entityMap.put("CloseCurlyDoubleQuote", new int[] { 0x201D });
        entityMap.put("CloseCurlyQuote", new int[] { 0x2019 });
        entityMap.put("Colon", new int[] { 0x2237 });
        entityMap.put("Colone", new int[] { 0x2A74 });
        entityMap.put("Congruent", new int[] { 0x2261 });
        entityMap.put("Conint", new int[] { 0x222F });
        entityMap.put("ContourIntegral", new int[] { 0x222E });
        entityMap.put("Copf", new int[] { 0x2102 });
        entityMap.put("Coproduct", new int[] { 0x2210 });
        entityMap.put("CounterClockwiseContourIntegral", new int[] { 0x2233 });
        entityMap.put("Cross", new int[] { 0x2A2F });
        entityMap.put("Cscr", new int[] { 0x1D49E });
        entityMap.put("Cup", new int[] { 0x22D3 });
        entityMap.put("CupCap", new int[] { 0x224D });
        entityMap.put("DD", new int[] { 0x2145 });
        entityMap.put("DDotrahd", new int[] { 0x2911 });
        entityMap.put("DJcy", new int[] { 0x0402 });
        entityMap.put("DScy", new int[] { 0x0405 });
        entityMap.put("DZcy", new int[] { 0x040F });
        entityMap.put("Dagger", new int[] { 0x2021 });
        entityMap.put("Darr", new int[] { 0x21A1 });
        entityMap.put("Dashv", new int[] { 0x2AE4 });
        entityMap.put("Dcaron", new int[] { 0x010E });
        entityMap.put("Dcy", new int[] { 0x0414 });
        entityMap.put("Del", new int[] { 0x2207 });
        entityMap.put("Delta", new int[] { 0x0394 });
        entityMap.put("Dfr", new int[] { 0x1D507 });
        entityMap.put("DiacriticalAcute", new int[] { 0x00B4 });
        entityMap.put("DiacriticalDot", new int[] { 0x02D9 });
        entityMap.put("DiacriticalDoubleAcute", new int[] { 0x02DD });
        entityMap.put("DiacriticalGrave", new int[] { 0x0060 });
        entityMap.put("DiacriticalTilde", new int[] { 0x02DC });
        entityMap.put("Diamond", new int[] { 0x22C4 });
        entityMap.put("DifferentialD", new int[] { 0x2146 });
        entityMap.put("Dopf", new int[] { 0x1D53B });
        entityMap.put("Dot", new int[] { 0x00A8 });
        entityMap.put("DotDot", new int[] { 0x20DC });
        entityMap.put("DotEqual", new int[] { 0x2250 });
        entityMap.put("DoubleContourIntegral", new int[] { 0x222F });
        entityMap.put("DoubleDot", new int[] { 0x00A8 });
        entityMap.put("DoubleDownArrow", new int[] { 0x21D3 });
        entityMap.put("DoubleLeftArrow", new int[] { 0x21D0 });
        entityMap.put("DoubleLeftRightArrow", new int[] { 0x21D4 });
        entityMap.put("DoubleLeftTee", new int[] { 0x2AE4 });
        entityMap.put("DoubleLongLeftArrow", new int[] { 0x27F8 });
        entityMap.put("DoubleLongLeftRightArrow", new int[] { 0x27FA });
        entityMap.put("DoubleLongRightArrow", new int[] { 0x27F9 });
        entityMap.put("DoubleRightArrow", new int[] { 0x21D2 });
        entityMap.put("DoubleRightTee", new int[] { 0x22A8 });
        entityMap.put("DoubleUpArrow", new int[] { 0x21D1 });
        entityMap.put("DoubleUpDownArrow", new int[] { 0x21D5 });
        entityMap.put("DoubleVerticalBar", new int[] { 0x2225 });
        entityMap.put("DownArrow", new int[] { 0x2193 });
        entityMap.put("DownArrowBar", new int[] { 0x2913 });
        entityMap.put("DownArrowUpArrow", new int[] { 0x21F5 });
        entityMap.put("DownBreve", new int[] { 0x0311 });
        entityMap.put("DownLeftRightVector", new int[] { 0x2950 });
        entityMap.put("DownLeftTeeVector", new int[] { 0x295E });
        entityMap.put("DownLeftVector", new int[] { 0x21BD });
        entityMap.put("DownLeftVectorBar", new int[] { 0x2956 });
        entityMap.put("DownRightTeeVector", new int[] { 0x295F });
        entityMap.put("DownRightVector", new int[] { 0x21C1 });
        entityMap.put("DownRightVectorBar", new int[] { 0x2957 });
        entityMap.put("DownTee", new int[] { 0x22A4 });
        entityMap.put("DownTeeArrow", new int[] { 0x21A7 });
        entityMap.put("Downarrow", new int[] { 0x21D3 });
        entityMap.put("Dscr", new int[] { 0x1D49F });
        entityMap.put("Dstrok", new int[] { 0x0110 });
        entityMap.put("ENG", new int[] { 0x014A });
        // Skipping entity missing semicolon: &ETH = 0x00D0
        entityMap.put("ETH", new int[] { 0x00D0 });
        // Skipping entity missing semicolon: &Eacute = 0x00C9
        entityMap.put("Eacute", new int[] { 0x00C9 });
        entityMap.put("Ecaron", new int[] { 0x011A });
        // Skipping entity missing semicolon: &Ecirc = 0x00CA
        entityMap.put("Ecirc", new int[] { 0x00CA });
        entityMap.put("Ecy", new int[] { 0x042D });
        entityMap.put("Edot", new int[] { 0x0116 });
        entityMap.put("Efr", new int[] { 0x1D508 });
        // Skipping entity missing semicolon: &Egrave = 0x00C8
        entityMap.put("Egrave", new int[] { 0x00C8 });
        entityMap.put("Element", new int[] { 0x2208 });
        entityMap.put("Emacr", new int[] { 0x0112 });
        entityMap.put("EmptySmallSquare", new int[] { 0x25FB });
        entityMap.put("EmptyVerySmallSquare", new int[] { 0x25AB });
        entityMap.put("Eogon", new int[] { 0x0118 });
        entityMap.put("Eopf", new int[] { 0x1D53C });
        entityMap.put("Epsilon", new int[] { 0x0395 });
        entityMap.put("Equal", new int[] { 0x2A75 });
        entityMap.put("EqualTilde", new int[] { 0x2242 });
        entityMap.put("Equilibrium", new int[] { 0x21CC });
        entityMap.put("Escr", new int[] { 0x2130 });
        entityMap.put("Esim", new int[] { 0x2A73 });
        entityMap.put("Eta", new int[] { 0x0397 });
        // Skipping entity missing semicolon: &Euml = 0x00CB
        entityMap.put("Euml", new int[] { 0x00CB });
        entityMap.put("Exists", new int[] { 0x2203 });
        entityMap.put("ExponentialE", new int[] { 0x2147 });
        entityMap.put("Fcy", new int[] { 0x0424 });
        entityMap.put("Ffr", new int[] { 0x1D509 });
        entityMap.put("FilledSmallSquare", new int[] { 0x25FC });
        entityMap.put("FilledVerySmallSquare", new int[] { 0x25AA });
        entityMap.put("Fopf", new int[] { 0x1D53D });
        entityMap.put("ForAll", new int[] { 0x2200 });
        entityMap.put("Fouriertrf", new int[] { 0x2131 });
        entityMap.put("Fscr", new int[] { 0x2131 });
        entityMap.put("GJcy", new int[] { 0x0403 });
        // Skipping entity missing semicolon: &GT = 0x003E
        entityMap.put("GT", new int[] { 0x003E });
        entityMap.put("Gamma", new int[] { 0x0393 });
        entityMap.put("Gammad", new int[] { 0x03DC });
        entityMap.put("Gbreve", new int[] { 0x011E });
        entityMap.put("Gcedil", new int[] { 0x0122 });
        entityMap.put("Gcirc", new int[] { 0x011C });
        entityMap.put("Gcy", new int[] { 0x0413 });
        entityMap.put("Gdot", new int[] { 0x0120 });
        entityMap.put("Gfr", new int[] { 0x1D50A });
        entityMap.put("Gg", new int[] { 0x22D9 });
        entityMap.put("Gopf", new int[] { 0x1D53E });
        entityMap.put("GreaterEqual", new int[] { 0x2265 });
        entityMap.put("GreaterEqualLess", new int[] { 0x22DB });
        entityMap.put("GreaterFullEqual", new int[] { 0x2267 });
        entityMap.put("GreaterGreater", new int[] { 0x2AA2 });
        entityMap.put("GreaterLess", new int[] { 0x2277 });
        entityMap.put("GreaterSlantEqual", new int[] { 0x2A7E });
        entityMap.put("GreaterTilde", new int[] { 0x2273 });
        entityMap.put("Gscr", new int[] { 0x1D4A2 });
        entityMap.put("Gt", new int[] { 0x226B });
        entityMap.put("HARDcy", new int[] { 0x042A });
        entityMap.put("Hacek", new int[] { 0x02C7 });
        entityMap.put("Hat", new int[] { 0x005E });
        entityMap.put("Hcirc", new int[] { 0x0124 });
        entityMap.put("Hfr", new int[] { 0x210C });
        entityMap.put("HilbertSpace", new int[] { 0x210B });
        entityMap.put("Hopf", new int[] { 0x210D });
        entityMap.put("HorizontalLine", new int[] { 0x2500 });
        entityMap.put("Hscr", new int[] { 0x210B });
        entityMap.put("Hstrok", new int[] { 0x0126 });
        entityMap.put("HumpDownHump", new int[] { 0x224E });
        entityMap.put("HumpEqual", new int[] { 0x224F });
        entityMap.put("IEcy", new int[] { 0x0415 });
        entityMap.put("IJlig", new int[] { 0x0132 });
        entityMap.put("IOcy", new int[] { 0x0401 });
        // Skipping entity missing semicolon: &Iacute = 0x00CD
        entityMap.put("Iacute", new int[] { 0x00CD });
        // Skipping entity missing semicolon: &Icirc = 0x00CE
        entityMap.put("Icirc", new int[] { 0x00CE });
        entityMap.put("Icy", new int[] { 0x0418 });
        entityMap.put("Idot", new int[] { 0x0130 });
        entityMap.put("Ifr", new int[] { 0x2111 });
        // Skipping entity missing semicolon: &Igrave = 0x00CC
        entityMap.put("Igrave", new int[] { 0x00CC });
        entityMap.put("Im", new int[] { 0x2111 });
        entityMap.put("Imacr", new int[] { 0x012A });
        entityMap.put("ImaginaryI", new int[] { 0x2148 });
        entityMap.put("Implies", new int[] { 0x21D2 });
        entityMap.put("Int", new int[] { 0x222C });
        entityMap.put("Integral", new int[] { 0x222B });
        entityMap.put("Intersection", new int[] { 0x22C2 });
        entityMap.put("InvisibleComma", new int[] { 0x2063 });
        entityMap.put("InvisibleTimes", new int[] { 0x2062 });
        entityMap.put("Iogon", new int[] { 0x012E });
        entityMap.put("Iopf", new int[] { 0x1D540 });
        entityMap.put("Iota", new int[] { 0x0399 });
        entityMap.put("Iscr", new int[] { 0x2110 });
        entityMap.put("Itilde", new int[] { 0x0128 });
        entityMap.put("Iukcy", new int[] { 0x0406 });
        // Skipping entity missing semicolon: &Iuml = 0x00CF
        entityMap.put("Iuml", new int[] { 0x00CF });
        entityMap.put("Jcirc", new int[] { 0x0134 });
        entityMap.put("Jcy", new int[] { 0x0419 });
        entityMap.put("Jfr", new int[] { 0x1D50D });
        entityMap.put("Jopf", new int[] { 0x1D541 });
        entityMap.put("Jscr", new int[] { 0x1D4A5 });
        entityMap.put("Jsercy", new int[] { 0x0408 });
        entityMap.put("Jukcy", new int[] { 0x0404 });
        entityMap.put("KHcy", new int[] { 0x0425 });
        entityMap.put("KJcy", new int[] { 0x040C });
        entityMap.put("Kappa", new int[] { 0x039A });
        entityMap.put("Kcedil", new int[] { 0x0136 });
        entityMap.put("Kcy", new int[] { 0x041A });
        entityMap.put("Kfr", new int[] { 0x1D50E });
        entityMap.put("Kopf", new int[] { 0x1D542 });
        entityMap.put("Kscr", new int[] { 0x1D4A6 });
        entityMap.put("LJcy", new int[] { 0x0409 });
        // Skipping entity missing semicolon: &LT = 0x003C
        entityMap.put("LT", new int[] { 0x003C });
        entityMap.put("Lacute", new int[] { 0x0139 });
        entityMap.put("Lambda", new int[] { 0x039B });
        entityMap.put("Lang", new int[] { 0x27EA });
        entityMap.put("Laplacetrf", new int[] { 0x2112 });
        entityMap.put("Larr", new int[] { 0x219E });
        entityMap.put("Lcaron", new int[] { 0x013D });
        entityMap.put("Lcedil", new int[] { 0x013B });
        entityMap.put("Lcy", new int[] { 0x041B });
        entityMap.put("LeftAngleBracket", new int[] { 0x27E8 });
        entityMap.put("LeftArrow", new int[] { 0x2190 });
        entityMap.put("LeftArrowBar", new int[] { 0x21E4 });
        entityMap.put("LeftArrowRightArrow", new int[] { 0x21C6 });
        entityMap.put("LeftCeiling", new int[] { 0x2308 });
        entityMap.put("LeftDoubleBracket", new int[] { 0x27E6 });
        entityMap.put("LeftDownTeeVector", new int[] { 0x2961 });
        entityMap.put("LeftDownVector", new int[] { 0x21C3 });
        entityMap.put("LeftDownVectorBar", new int[] { 0x2959 });
        entityMap.put("LeftFloor", new int[] { 0x230A });
        entityMap.put("LeftRightArrow", new int[] { 0x2194 });
        entityMap.put("LeftRightVector", new int[] { 0x294E });
        entityMap.put("LeftTee", new int[] { 0x22A3 });
        entityMap.put("LeftTeeArrow", new int[] { 0x21A4 });
        entityMap.put("LeftTeeVector", new int[] { 0x295A });
        entityMap.put("LeftTriangle", new int[] { 0x22B2 });
        entityMap.put("LeftTriangleBar", new int[] { 0x29CF });
        entityMap.put("LeftTriangleEqual", new int[] { 0x22B4 });
        entityMap.put("LeftUpDownVector", new int[] { 0x2951 });
        entityMap.put("LeftUpTeeVector", new int[] { 0x2960 });
        entityMap.put("LeftUpVector", new int[] { 0x21BF });
        entityMap.put("LeftUpVectorBar", new int[] { 0x2958 });
        entityMap.put("LeftVector", new int[] { 0x21BC });
        entityMap.put("LeftVectorBar", new int[] { 0x2952 });
        entityMap.put("Leftarrow", new int[] { 0x21D0 });
        entityMap.put("Leftrightarrow", new int[] { 0x21D4 });
        entityMap.put("LessEqualGreater", new int[] { 0x22DA });
        entityMap.put("LessFullEqual", new int[] { 0x2266 });
        entityMap.put("LessGreater", new int[] { 0x2276 });
        entityMap.put("LessLess", new int[] { 0x2AA1 });
        entityMap.put("LessSlantEqual", new int[] { 0x2A7D });
        entityMap.put("LessTilde", new int[] { 0x2272 });
        entityMap.put("Lfr", new int[] { 0x1D50F });
        entityMap.put("Ll", new int[] { 0x22D8 });
        entityMap.put("Lleftarrow", new int[] { 0x21DA });
        entityMap.put("Lmidot", new int[] { 0x013F });
        entityMap.put("LongLeftArrow", new int[] { 0x27F5 });
        entityMap.put("LongLeftRightArrow", new int[] { 0x27F7 });
        entityMap.put("LongRightArrow", new int[] { 0x27F6 });
        entityMap.put("Longleftarrow", new int[] { 0x27F8 });
        entityMap.put("Longleftrightarrow", new int[] { 0x27FA });
        entityMap.put("Longrightarrow", new int[] { 0x27F9 });
        entityMap.put("Lopf", new int[] { 0x1D543 });
        entityMap.put("LowerLeftArrow", new int[] { 0x2199 });
        entityMap.put("LowerRightArrow", new int[] { 0x2198 });
        entityMap.put("Lscr", new int[] { 0x2112 });
        entityMap.put("Lsh", new int[] { 0x21B0 });
        entityMap.put("Lstrok", new int[] { 0x0141 });
        entityMap.put("Lt", new int[] { 0x226A });
        entityMap.put("Map", new int[] { 0x2905 });
        entityMap.put("Mcy", new int[] { 0x041C });
        entityMap.put("MediumSpace", new int[] { 0x205F });
        entityMap.put("Mellintrf", new int[] { 0x2133 });
        entityMap.put("Mfr", new int[] { 0x1D510 });
        entityMap.put("MinusPlus", new int[] { 0x2213 });
        entityMap.put("Mopf", new int[] { 0x1D544 });
        entityMap.put("Mscr", new int[] { 0x2133 });
        entityMap.put("Mu", new int[] { 0x039C });
        entityMap.put("NJcy", new int[] { 0x040A });
        entityMap.put("Nacute", new int[] { 0x0143 });
        entityMap.put("Ncaron", new int[] { 0x0147 });
        entityMap.put("Ncedil", new int[] { 0x0145 });
        entityMap.put("Ncy", new int[] { 0x041D });
        entityMap.put("NegativeMediumSpace", new int[] { 0x200B });
        entityMap.put("NegativeThickSpace", new int[] { 0x200B });
        entityMap.put("NegativeThinSpace", new int[] { 0x200B });
        entityMap.put("NegativeVeryThinSpace", new int[] { 0x200B });
        entityMap.put("NestedGreaterGreater", new int[] { 0x226B });
        entityMap.put("NestedLessLess", new int[] { 0x226A });
        entityMap.put("NewLine", new int[] { 0x000A });
        entityMap.put("Nfr", new int[] { 0x1D511 });
        entityMap.put("NoBreak", new int[] { 0x2060 });
        entityMap.put("NonBreakingSpace", new int[] { 0x00A0 });
        entityMap.put("Nopf", new int[] { 0x2115 });
        entityMap.put("Not", new int[] { 0x2AEC });
        entityMap.put("NotCongruent", new int[] { 0x2262 });
        entityMap.put("NotCupCap", new int[] { 0x226D });
        entityMap.put("NotDoubleVerticalBar", new int[] { 0x2226 });
        entityMap.put("NotElement", new int[] { 0x2209 });
        entityMap.put("NotEqual", new int[] { 0x2260 });
        entityMap.put("NotEqualTilde", new int[] { 0x2242, 0x0338 });
        entityMap.put("NotExists", new int[] { 0x2204 });
        entityMap.put("NotGreater", new int[] { 0x226F });
        entityMap.put("NotGreaterEqual", new int[] { 0x2271 });
        entityMap.put("NotGreaterFullEqual", new int[] { 0x2267, 0x0338 });
        entityMap.put("NotGreaterGreater", new int[] { 0x226B, 0x0338 });
        entityMap.put("NotGreaterLess", new int[] { 0x2279 });
        entityMap.put("NotGreaterSlantEqual", new int[] { 0x2A7E, 0x0338 });
        entityMap.put("NotGreaterTilde", new int[] { 0x2275 });
        entityMap.put("NotHumpDownHump", new int[] { 0x224E, 0x0338 });
        entityMap.put("NotHumpEqual", new int[] { 0x224F, 0x0338 });
        entityMap.put("NotLeftTriangle", new int[] { 0x22EA });
        entityMap.put("NotLeftTriangleBar", new int[] { 0x29CF, 0x0338 });
        entityMap.put("NotLeftTriangleEqual", new int[] { 0x22EC });
        entityMap.put("NotLess", new int[] { 0x226E });
        entityMap.put("NotLessEqual", new int[] { 0x2270 });
        entityMap.put("NotLessGreater", new int[] { 0x2278 });
        entityMap.put("NotLessLess", new int[] { 0x226A, 0x0338 });
        entityMap.put("NotLessSlantEqual", new int[] { 0x2A7D, 0x0338 });
        entityMap.put("NotLessTilde", new int[] { 0x2274 });
        entityMap.put("NotNestedGreaterGreater", new int[] { 0x2AA2, 0x0338 });
        entityMap.put("NotNestedLessLess", new int[] { 0x2AA1, 0x0338 });
        entityMap.put("NotPrecedes", new int[] { 0x2280 });
        entityMap.put("NotPrecedesEqual", new int[] { 0x2AAF, 0x0338 });
        entityMap.put("NotPrecedesSlantEqual", new int[] { 0x22E0 });
        entityMap.put("NotReverseElement", new int[] { 0x220C });
        entityMap.put("NotRightTriangle", new int[] { 0x22EB });
        entityMap.put("NotRightTriangleBar", new int[] { 0x29D0, 0x0338 });
        entityMap.put("NotRightTriangleEqual", new int[] { 0x22ED });
        entityMap.put("NotSquareSubset", new int[] { 0x228F, 0x0338 });
        entityMap.put("NotSquareSubsetEqual", new int[] { 0x22E2 });
        entityMap.put("NotSquareSuperset", new int[] { 0x2290, 0x0338 });
        entityMap.put("NotSquareSupersetEqual", new int[] { 0x22E3 });
        entityMap.put("NotSubset", new int[] { 0x2282, 0x20D2 });
        entityMap.put("NotSubsetEqual", new int[] { 0x2288 });
        entityMap.put("NotSucceeds", new int[] { 0x2281 });
        entityMap.put("NotSucceedsEqual", new int[] { 0x2AB0, 0x0338 });
        entityMap.put("NotSucceedsSlantEqual", new int[] { 0x22E1 });
        entityMap.put("NotSucceedsTilde", new int[] { 0x227F, 0x0338 });
        entityMap.put("NotSuperset", new int[] { 0x2283, 0x20D2 });
        entityMap.put("NotSupersetEqual", new int[] { 0x2289 });
        entityMap.put("NotTilde", new int[] { 0x2241 });
        entityMap.put("NotTildeEqual", new int[] { 0x2244 });
        entityMap.put("NotTildeFullEqual", new int[] { 0x2247 });
        entityMap.put("NotTildeTilde", new int[] { 0x2249 });
        entityMap.put("NotVerticalBar", new int[] { 0x2224 });
        entityMap.put("Nscr", new int[] { 0x1D4A9 });
        // Skipping entity missing semicolon: &Ntilde = 0x00D1
        entityMap.put("Ntilde", new int[] { 0x00D1 });
        entityMap.put("Nu", new int[] { 0x039D });
        entityMap.put("OElig", new int[] { 0x0152 });
        // Skipping entity missing semicolon: &Oacute = 0x00D3
        entityMap.put("Oacute", new int[] { 0x00D3 });
        // Skipping entity missing semicolon: &Ocirc = 0x00D4
        entityMap.put("Ocirc", new int[] { 0x00D4 });
        entityMap.put("Ocy", new int[] { 0x041E });
        entityMap.put("Odblac", new int[] { 0x0150 });
        entityMap.put("Ofr", new int[] { 0x1D512 });
        // Skipping entity missing semicolon: &Ograve = 0x00D2
        entityMap.put("Ograve", new int[] { 0x00D2 });
        entityMap.put("Omacr", new int[] { 0x014C });
        entityMap.put("Omega", new int[] { 0x03A9 });
        entityMap.put("Omicron", new int[] { 0x039F });
        entityMap.put("Oopf", new int[] { 0x1D546 });
        entityMap.put("OpenCurlyDoubleQuote", new int[] { 0x201C });
        entityMap.put("OpenCurlyQuote", new int[] { 0x2018 });
        entityMap.put("Or", new int[] { 0x2A54 });
        entityMap.put("Oscr", new int[] { 0x1D4AA });
        // Skipping entity missing semicolon: &Oslash = 0x00D8
        entityMap.put("Oslash", new int[] { 0x00D8 });
        // Skipping entity missing semicolon: &Otilde = 0x00D5
        entityMap.put("Otilde", new int[] { 0x00D5 });
        entityMap.put("Otimes", new int[] { 0x2A37 });
        // Skipping entity missing semicolon: &Ouml = 0x00D6
        entityMap.put("Ouml", new int[] { 0x00D6 });
        entityMap.put("OverBar", new int[] { 0x203E });
        entityMap.put("OverBrace", new int[] { 0x23DE });
        entityMap.put("OverBracket", new int[] { 0x23B4 });
        entityMap.put("OverParenthesis", new int[] { 0x23DC });
        entityMap.put("PartialD", new int[] { 0x2202 });
        entityMap.put("Pcy", new int[] { 0x041F });
        entityMap.put("Pfr", new int[] { 0x1D513 });
        entityMap.put("Phi", new int[] { 0x03A6 });
        entityMap.put("Pi", new int[] { 0x03A0 });
        entityMap.put("PlusMinus", new int[] { 0x00B1 });
        entityMap.put("Poincareplane", new int[] { 0x210C });
        entityMap.put("Popf", new int[] { 0x2119 });
        entityMap.put("Pr", new int[] { 0x2ABB });
        entityMap.put("Precedes", new int[] { 0x227A });
        entityMap.put("PrecedesEqual", new int[] { 0x2AAF });
        entityMap.put("PrecedesSlantEqual", new int[] { 0x227C });
        entityMap.put("PrecedesTilde", new int[] { 0x227E });
        entityMap.put("Prime", new int[] { 0x2033 });
        entityMap.put("Product", new int[] { 0x220F });
        entityMap.put("Proportion", new int[] { 0x2237 });
        entityMap.put("Proportional", new int[] { 0x221D });
        entityMap.put("Pscr", new int[] { 0x1D4AB });
        entityMap.put("Psi", new int[] { 0x03A8 });
        // Skipping entity missing semicolon: &QUOT = 0x0022
        entityMap.put("QUOT", new int[] { 0x0022 });
        entityMap.put("Qfr", new int[] { 0x1D514 });
        entityMap.put("Qopf", new int[] { 0x211A });
        entityMap.put("Qscr", new int[] { 0x1D4AC });
        entityMap.put("RBarr", new int[] { 0x2910 });
        // Skipping entity missing semicolon: &REG = 0x00AE
        entityMap.put("REG", new int[] { 0x00AE });
        entityMap.put("Racute", new int[] { 0x0154 });
        entityMap.put("Rang", new int[] { 0x27EB });
        entityMap.put("Rarr", new int[] { 0x21A0 });
        entityMap.put("Rarrtl", new int[] { 0x2916 });
        entityMap.put("Rcaron", new int[] { 0x0158 });
        entityMap.put("Rcedil", new int[] { 0x0156 });
        entityMap.put("Rcy", new int[] { 0x0420 });
        entityMap.put("Re", new int[] { 0x211C });
        entityMap.put("ReverseElement", new int[] { 0x220B });
        entityMap.put("ReverseEquilibrium", new int[] { 0x21CB });
        entityMap.put("ReverseUpEquilibrium", new int[] { 0x296F });
        entityMap.put("Rfr", new int[] { 0x211C });
        entityMap.put("Rho", new int[] { 0x03A1 });
        entityMap.put("RightAngleBracket", new int[] { 0x27E9 });
        entityMap.put("RightArrow", new int[] { 0x2192 });
        entityMap.put("RightArrowBar", new int[] { 0x21E5 });
        entityMap.put("RightArrowLeftArrow", new int[] { 0x21C4 });
        entityMap.put("RightCeiling", new int[] { 0x2309 });
        entityMap.put("RightDoubleBracket", new int[] { 0x27E7 });
        entityMap.put("RightDownTeeVector", new int[] { 0x295D });
        entityMap.put("RightDownVector", new int[] { 0x21C2 });
        entityMap.put("RightDownVectorBar", new int[] { 0x2955 });
        entityMap.put("RightFloor", new int[] { 0x230B });
        entityMap.put("RightTee", new int[] { 0x22A2 });
        entityMap.put("RightTeeArrow", new int[] { 0x21A6 });
        entityMap.put("RightTeeVector", new int[] { 0x295B });
        entityMap.put("RightTriangle", new int[] { 0x22B3 });
        entityMap.put("RightTriangleBar", new int[] { 0x29D0 });
        entityMap.put("RightTriangleEqual", new int[] { 0x22B5 });
        entityMap.put("RightUpDownVector", new int[] { 0x294F });
        entityMap.put("RightUpTeeVector", new int[] { 0x295C });
        entityMap.put("RightUpVector", new int[] { 0x21BE });
        entityMap.put("RightUpVectorBar", new int[] { 0x2954 });
        entityMap.put("RightVector", new int[] { 0x21C0 });
        entityMap.put("RightVectorBar", new int[] { 0x2953 });
        entityMap.put("Rightarrow", new int[] { 0x21D2 });
        entityMap.put("Ropf", new int[] { 0x211D });
        entityMap.put("RoundImplies", new int[] { 0x2970 });
        entityMap.put("Rrightarrow", new int[] { 0x21DB });
        entityMap.put("Rscr", new int[] { 0x211B });
        entityMap.put("Rsh", new int[] { 0x21B1 });
        entityMap.put("RuleDelayed", new int[] { 0x29F4 });
        entityMap.put("SHCHcy", new int[] { 0x0429 });
        entityMap.put("SHcy", new int[] { 0x0428 });
        entityMap.put("SOFTcy", new int[] { 0x042C });
        entityMap.put("Sacute", new int[] { 0x015A });
        entityMap.put("Sc", new int[] { 0x2ABC });
        entityMap.put("Scaron", new int[] { 0x0160 });
        entityMap.put("Scedil", new int[] { 0x015E });
        entityMap.put("Scirc", new int[] { 0x015C });
        entityMap.put("Scy", new int[] { 0x0421 });
        entityMap.put("Sfr", new int[] { 0x1D516 });
        entityMap.put("ShortDownArrow", new int[] { 0x2193 });
        entityMap.put("ShortLeftArrow", new int[] { 0x2190 });
        entityMap.put("ShortRightArrow", new int[] { 0x2192 });
        entityMap.put("ShortUpArrow", new int[] { 0x2191 });
        entityMap.put("Sigma", new int[] { 0x03A3 });
        entityMap.put("SmallCircle", new int[] { 0x2218 });
        entityMap.put("Sopf", new int[] { 0x1D54A });
        entityMap.put("Sqrt", new int[] { 0x221A });
        entityMap.put("Square", new int[] { 0x25A1 });
        entityMap.put("SquareIntersection", new int[] { 0x2293 });
        entityMap.put("SquareSubset", new int[] { 0x228F });
        entityMap.put("SquareSubsetEqual", new int[] { 0x2291 });
        entityMap.put("SquareSuperset", new int[] { 0x2290 });
        entityMap.put("SquareSupersetEqual", new int[] { 0x2292 });
        entityMap.put("SquareUnion", new int[] { 0x2294 });
        entityMap.put("Sscr", new int[] { 0x1D4AE });
        entityMap.put("Star", new int[] { 0x22C6 });
        entityMap.put("Sub", new int[] { 0x22D0 });
        entityMap.put("Subset", new int[] { 0x22D0 });
        entityMap.put("SubsetEqual", new int[] { 0x2286 });
        entityMap.put("Succeeds", new int[] { 0x227B });
        entityMap.put("SucceedsEqual", new int[] { 0x2AB0 });
        entityMap.put("SucceedsSlantEqual", new int[] { 0x227D });
        entityMap.put("SucceedsTilde", new int[] { 0x227F });
        entityMap.put("SuchThat", new int[] { 0x220B });
        entityMap.put("Sum", new int[] { 0x2211 });
        entityMap.put("Sup", new int[] { 0x22D1 });
        entityMap.put("Superset", new int[] { 0x2283 });
        entityMap.put("SupersetEqual", new int[] { 0x2287 });
        entityMap.put("Supset", new int[] { 0x22D1 });
        // Skipping entity missing semicolon: &THORN = 0x00DE
        entityMap.put("THORN", new int[] { 0x00DE });
        entityMap.put("TRADE", new int[] { 0x2122 });
        entityMap.put("TSHcy", new int[] { 0x040B });
        entityMap.put("TScy", new int[] { 0x0426 });
        entityMap.put("Tab", new int[] { 0x0009 });
        entityMap.put("Tau", new int[] { 0x03A4 });
        entityMap.put("Tcaron", new int[] { 0x0164 });
        entityMap.put("Tcedil", new int[] { 0x0162 });
        entityMap.put("Tcy", new int[] { 0x0422 });
        entityMap.put("Tfr", new int[] { 0x1D517 });
        entityMap.put("Therefore", new int[] { 0x2234 });
        entityMap.put("Theta", new int[] { 0x0398 });
        entityMap.put("ThickSpace", new int[] { 0x205F, 0x200A });
        entityMap.put("ThinSpace", new int[] { 0x2009 });
        entityMap.put("Tilde", new int[] { 0x223C });
        entityMap.put("TildeEqual", new int[] { 0x2243 });
        entityMap.put("TildeFullEqual", new int[] { 0x2245 });
        entityMap.put("TildeTilde", new int[] { 0x2248 });
        entityMap.put("Topf", new int[] { 0x1D54B });
        entityMap.put("TripleDot", new int[] { 0x20DB });
        entityMap.put("Tscr", new int[] { 0x1D4AF });
        entityMap.put("Tstrok", new int[] { 0x0166 });
        // Skipping entity missing semicolon: &Uacute = 0x00DA
        entityMap.put("Uacute", new int[] { 0x00DA });
        entityMap.put("Uarr", new int[] { 0x219F });
        entityMap.put("Uarrocir", new int[] { 0x2949 });
        entityMap.put("Ubrcy", new int[] { 0x040E });
        entityMap.put("Ubreve", new int[] { 0x016C });
        // Skipping entity missing semicolon: &Ucirc = 0x00DB
        entityMap.put("Ucirc", new int[] { 0x00DB });
        entityMap.put("Ucy", new int[] { 0x0423 });
        entityMap.put("Udblac", new int[] { 0x0170 });
        entityMap.put("Ufr", new int[] { 0x1D518 });
        // Skipping entity missing semicolon: &Ugrave = 0x00D9
        entityMap.put("Ugrave", new int[] { 0x00D9 });
        entityMap.put("Umacr", new int[] { 0x016A });
        entityMap.put("UnderBar", new int[] { 0x005F });
        entityMap.put("UnderBrace", new int[] { 0x23DF });
        entityMap.put("UnderBracket", new int[] { 0x23B5 });
        entityMap.put("UnderParenthesis", new int[] { 0x23DD });
        entityMap.put("Union", new int[] { 0x22C3 });
        entityMap.put("UnionPlus", new int[] { 0x228E });
        entityMap.put("Uogon", new int[] { 0x0172 });
        entityMap.put("Uopf", new int[] { 0x1D54C });
        entityMap.put("UpArrow", new int[] { 0x2191 });
        entityMap.put("UpArrowBar", new int[] { 0x2912 });
        entityMap.put("UpArrowDownArrow", new int[] { 0x21C5 });
        entityMap.put("UpDownArrow", new int[] { 0x2195 });
        entityMap.put("UpEquilibrium", new int[] { 0x296E });
        entityMap.put("UpTee", new int[] { 0x22A5 });
        entityMap.put("UpTeeArrow", new int[] { 0x21A5 });
        entityMap.put("Uparrow", new int[] { 0x21D1 });
        entityMap.put("Updownarrow", new int[] { 0x21D5 });
        entityMap.put("UpperLeftArrow", new int[] { 0x2196 });
        entityMap.put("UpperRightArrow", new int[] { 0x2197 });
        entityMap.put("Upsi", new int[] { 0x03D2 });
        entityMap.put("Upsilon", new int[] { 0x03A5 });
        entityMap.put("Uring", new int[] { 0x016E });
        entityMap.put("Uscr", new int[] { 0x1D4B0 });
        entityMap.put("Utilde", new int[] { 0x0168 });
        // Skipping entity missing semicolon: &Uuml = 0x00DC
        entityMap.put("Uuml", new int[] { 0x00DC });
        entityMap.put("VDash", new int[] { 0x22AB });
        entityMap.put("Vbar", new int[] { 0x2AEB });
        entityMap.put("Vcy", new int[] { 0x0412 });
        entityMap.put("Vdash", new int[] { 0x22A9 });
        entityMap.put("Vdashl", new int[] { 0x2AE6 });
        entityMap.put("Vee", new int[] { 0x22C1 });
        entityMap.put("Verbar", new int[] { 0x2016 });
        entityMap.put("Vert", new int[] { 0x2016 });
        entityMap.put("VerticalBar", new int[] { 0x2223 });
        entityMap.put("VerticalLine", new int[] { 0x007C });
        entityMap.put("VerticalSeparator", new int[] { 0x2758 });
        entityMap.put("VerticalTilde", new int[] { 0x2240 });
        entityMap.put("VeryThinSpace", new int[] { 0x200A });
        entityMap.put("Vfr", new int[] { 0x1D519 });
        entityMap.put("Vopf", new int[] { 0x1D54D });
        entityMap.put("Vscr", new int[] { 0x1D4B1 });
        entityMap.put("Vvdash", new int[] { 0x22AA });
        entityMap.put("Wcirc", new int[] { 0x0174 });
        entityMap.put("Wedge", new int[] { 0x22C0 });
        entityMap.put("Wfr", new int[] { 0x1D51A });
        entityMap.put("Wopf", new int[] { 0x1D54E });
        entityMap.put("Wscr", new int[] { 0x1D4B2 });
        entityMap.put("Xfr", new int[] { 0x1D51B });
        entityMap.put("Xi", new int[] { 0x039E });
        entityMap.put("Xopf", new int[] { 0x1D54F });
        entityMap.put("Xscr", new int[] { 0x1D4B3 });
        entityMap.put("YAcy", new int[] { 0x042F });
        entityMap.put("YIcy", new int[] { 0x0407 });
        entityMap.put("YUcy", new int[] { 0x042E });
        // Skipping entity missing semicolon: &Yacute = 0x00DD
        entityMap.put("Yacute", new int[] { 0x00DD });
        entityMap.put("Ycirc", new int[] { 0x0176 });
        entityMap.put("Ycy", new int[] { 0x042B });
        entityMap.put("Yfr", new int[] { 0x1D51C });
        entityMap.put("Yopf", new int[] { 0x1D550 });
        entityMap.put("Yscr", new int[] { 0x1D4B4 });
        entityMap.put("Yuml", new int[] { 0x0178 });
        entityMap.put("ZHcy", new int[] { 0x0416 });
        entityMap.put("Zacute", new int[] { 0x0179 });
        entityMap.put("Zcaron", new int[] { 0x017D });
        entityMap.put("Zcy", new int[] { 0x0417 });
        entityMap.put("Zdot", new int[] { 0x017B });
        entityMap.put("ZeroWidthSpace", new int[] { 0x200B });
        entityMap.put("Zeta", new int[] { 0x0396 });
        entityMap.put("Zfr", new int[] { 0x2128 });
        entityMap.put("Zopf", new int[] { 0x2124 });
        entityMap.put("Zscr", new int[] { 0x1D4B5 });
        // Skipping entity missing semicolon: &aacute = 0x00E1
        entityMap.put("aacute", new int[] { 0x00E1 });
        entityMap.put("abreve", new int[] { 0x0103 });
        entityMap.put("ac", new int[] { 0x223E });
        entityMap.put("acE", new int[] { 0x223E, 0x0333 });
        entityMap.put("acd", new int[] { 0x223F });
        // Skipping entity missing semicolon: &acirc = 0x00E2
        entityMap.put("acirc", new int[] { 0x00E2 });
        // Skipping entity missing semicolon: &acute = 0x00B4
        entityMap.put("acute", new int[] { 0x00B4 });
        entityMap.put("acy", new int[] { 0x0430 });
        // Skipping entity missing semicolon: &aelig = 0x00E6
        entityMap.put("aelig", new int[] { 0x00E6 });
        entityMap.put("af", new int[] { 0x2061 });
        entityMap.put("afr", new int[] { 0x1D51E });
        // Skipping entity missing semicolon: &agrave = 0x00E0
        entityMap.put("agrave", new int[] { 0x00E0 });
        entityMap.put("alefsym", new int[] { 0x2135 });
        entityMap.put("aleph", new int[] { 0x2135 });
        entityMap.put("alpha", new int[] { 0x03B1 });
        entityMap.put("amacr", new int[] { 0x0101 });
        entityMap.put("amalg", new int[] { 0x2A3F });
        // Skipping entity missing semicolon: &amp = 0x0026
        entityMap.put("amp", new int[] { 0x0026 });
        entityMap.put("and", new int[] { 0x2227 });
        entityMap.put("andand", new int[] { 0x2A55 });
        entityMap.put("andd", new int[] { 0x2A5C });
        entityMap.put("andslope", new int[] { 0x2A58 });
        entityMap.put("andv", new int[] { 0x2A5A });
        entityMap.put("ang", new int[] { 0x2220 });
        entityMap.put("ange", new int[] { 0x29A4 });
        entityMap.put("angle", new int[] { 0x2220 });
        entityMap.put("angmsd", new int[] { 0x2221 });
        entityMap.put("angmsdaa", new int[] { 0x29A8 });
        entityMap.put("angmsdab", new int[] { 0x29A9 });
        entityMap.put("angmsdac", new int[] { 0x29AA });
        entityMap.put("angmsdad", new int[] { 0x29AB });
        entityMap.put("angmsdae", new int[] { 0x29AC });
        entityMap.put("angmsdaf", new int[] { 0x29AD });
        entityMap.put("angmsdag", new int[] { 0x29AE });
        entityMap.put("angmsdah", new int[] { 0x29AF });
        entityMap.put("angrt", new int[] { 0x221F });
        entityMap.put("angrtvb", new int[] { 0x22BE });
        entityMap.put("angrtvbd", new int[] { 0x299D });
        entityMap.put("angsph", new int[] { 0x2222 });
        entityMap.put("angst", new int[] { 0x00C5 });
        entityMap.put("angzarr", new int[] { 0x237C });
        entityMap.put("aogon", new int[] { 0x0105 });
        entityMap.put("aopf", new int[] { 0x1D552 });
        entityMap.put("ap", new int[] { 0x2248 });
        entityMap.put("apE", new int[] { 0x2A70 });
        entityMap.put("apacir", new int[] { 0x2A6F });
        entityMap.put("ape", new int[] { 0x224A });
        entityMap.put("apid", new int[] { 0x224B });
        entityMap.put("apos", new int[] { 0x0027 });
        entityMap.put("approx", new int[] { 0x2248 });
        entityMap.put("approxeq", new int[] { 0x224A });
        // Skipping entity missing semicolon: &aring = 0x00E5
        entityMap.put("aring", new int[] { 0x00E5 });
        entityMap.put("ascr", new int[] { 0x1D4B6 });
        entityMap.put("ast", new int[] { 0x002A });
        entityMap.put("asymp", new int[] { 0x2248 });
        entityMap.put("asympeq", new int[] { 0x224D });
        // Skipping entity missing semicolon: &atilde = 0x00E3
        entityMap.put("atilde", new int[] { 0x00E3 });
        // Skipping entity missing semicolon: &auml = 0x00E4
        entityMap.put("auml", new int[] { 0x00E4 });
        entityMap.put("awconint", new int[] { 0x2233 });
        entityMap.put("awint", new int[] { 0x2A11 });
        entityMap.put("bNot", new int[] { 0x2AED });
        entityMap.put("backcong", new int[] { 0x224C });
        entityMap.put("backepsilon", new int[] { 0x03F6 });
        entityMap.put("backprime", new int[] { 0x2035 });
        entityMap.put("backsim", new int[] { 0x223D });
        entityMap.put("backsimeq", new int[] { 0x22CD });
        entityMap.put("barvee", new int[] { 0x22BD });
        entityMap.put("barwed", new int[] { 0x2305 });
        entityMap.put("barwedge", new int[] { 0x2305 });
        entityMap.put("bbrk", new int[] { 0x23B5 });
        entityMap.put("bbrktbrk", new int[] { 0x23B6 });
        entityMap.put("bcong", new int[] { 0x224C });
        entityMap.put("bcy", new int[] { 0x0431 });
        entityMap.put("bdquo", new int[] { 0x201E });
        entityMap.put("becaus", new int[] { 0x2235 });
        entityMap.put("because", new int[] { 0x2235 });
        entityMap.put("bemptyv", new int[] { 0x29B0 });
        entityMap.put("bepsi", new int[] { 0x03F6 });
        entityMap.put("bernou", new int[] { 0x212C });
        entityMap.put("beta", new int[] { 0x03B2 });
        entityMap.put("beth", new int[] { 0x2136 });
        entityMap.put("between", new int[] { 0x226C });
        entityMap.put("bfr", new int[] { 0x1D51F });
        entityMap.put("bigcap", new int[] { 0x22C2 });
        entityMap.put("bigcirc", new int[] { 0x25EF });
        entityMap.put("bigcup", new int[] { 0x22C3 });
        entityMap.put("bigodot", new int[] { 0x2A00 });
        entityMap.put("bigoplus", new int[] { 0x2A01 });
        entityMap.put("bigotimes", new int[] { 0x2A02 });
        entityMap.put("bigsqcup", new int[] { 0x2A06 });
        entityMap.put("bigstar", new int[] { 0x2605 });
        entityMap.put("bigtriangledown", new int[] { 0x25BD });
        entityMap.put("bigtriangleup", new int[] { 0x25B3 });
        entityMap.put("biguplus", new int[] { 0x2A04 });
        entityMap.put("bigvee", new int[] { 0x22C1 });
        entityMap.put("bigwedge", new int[] { 0x22C0 });
        entityMap.put("bkarow", new int[] { 0x290D });
        entityMap.put("blacklozenge", new int[] { 0x29EB });
        entityMap.put("blacksquare", new int[] { 0x25AA });
        entityMap.put("blacktriangle", new int[] { 0x25B4 });
        entityMap.put("blacktriangledown", new int[] { 0x25BE });
        entityMap.put("blacktriangleleft", new int[] { 0x25C2 });
        entityMap.put("blacktriangleright", new int[] { 0x25B8 });
        entityMap.put("blank", new int[] { 0x2423 });
        entityMap.put("blk12", new int[] { 0x2592 });
        entityMap.put("blk14", new int[] { 0x2591 });
        entityMap.put("blk34", new int[] { 0x2593 });
        entityMap.put("block", new int[] { 0x2588 });
        entityMap.put("bne", new int[] { 0x003D, 0x20E5 });
        entityMap.put("bnequiv", new int[] { 0x2261, 0x20E5 });
        entityMap.put("bnot", new int[] { 0x2310 });
        entityMap.put("bopf", new int[] { 0x1D553 });
        entityMap.put("bot", new int[] { 0x22A5 });
        entityMap.put("bottom", new int[] { 0x22A5 });
        entityMap.put("bowtie", new int[] { 0x22C8 });
        entityMap.put("boxDL", new int[] { 0x2557 });
        entityMap.put("boxDR", new int[] { 0x2554 });
        entityMap.put("boxDl", new int[] { 0x2556 });
        entityMap.put("boxDr", new int[] { 0x2553 });
        entityMap.put("boxH", new int[] { 0x2550 });
        entityMap.put("boxHD", new int[] { 0x2566 });
        entityMap.put("boxHU", new int[] { 0x2569 });
        entityMap.put("boxHd", new int[] { 0x2564 });
        entityMap.put("boxHu", new int[] { 0x2567 });
        entityMap.put("boxUL", new int[] { 0x255D });
        entityMap.put("boxUR", new int[] { 0x255A });
        entityMap.put("boxUl", new int[] { 0x255C });
        entityMap.put("boxUr", new int[] { 0x2559 });
        entityMap.put("boxV", new int[] { 0x2551 });
        entityMap.put("boxVH", new int[] { 0x256C });
        entityMap.put("boxVL", new int[] { 0x2563 });
        entityMap.put("boxVR", new int[] { 0x2560 });
        entityMap.put("boxVh", new int[] { 0x256B });
        entityMap.put("boxVl", new int[] { 0x2562 });
        entityMap.put("boxVr", new int[] { 0x255F });
        entityMap.put("boxbox", new int[] { 0x29C9 });
        entityMap.put("boxdL", new int[] { 0x2555 });
        entityMap.put("boxdR", new int[] { 0x2552 });
        entityMap.put("boxdl", new int[] { 0x2510 });
        entityMap.put("boxdr", new int[] { 0x250C });
        entityMap.put("boxh", new int[] { 0x2500 });
        entityMap.put("boxhD", new int[] { 0x2565 });
        entityMap.put("boxhU", new int[] { 0x2568 });
        entityMap.put("boxhd", new int[] { 0x252C });
        entityMap.put("boxhu", new int[] { 0x2534 });
        entityMap.put("boxminus", new int[] { 0x229F });
        entityMap.put("boxplus", new int[] { 0x229E });
        entityMap.put("boxtimes", new int[] { 0x22A0 });
        entityMap.put("boxuL", new int[] { 0x255B });
        entityMap.put("boxuR", new int[] { 0x2558 });
        entityMap.put("boxul", new int[] { 0x2518 });
        entityMap.put("boxur", new int[] { 0x2514 });
        entityMap.put("boxv", new int[] { 0x2502 });
        entityMap.put("boxvH", new int[] { 0x256A });
        entityMap.put("boxvL", new int[] { 0x2561 });
        entityMap.put("boxvR", new int[] { 0x255E });
        entityMap.put("boxvh", new int[] { 0x253C });
        entityMap.put("boxvl", new int[] { 0x2524 });
        entityMap.put("boxvr", new int[] { 0x251C });
        entityMap.put("bprime", new int[] { 0x2035 });
        entityMap.put("breve", new int[] { 0x02D8 });
        // Skipping entity missing semicolon: &brvbar = 0x00A6
        entityMap.put("brvbar", new int[] { 0x00A6 });
        entityMap.put("bscr", new int[] { 0x1D4B7 });
        entityMap.put("bsemi", new int[] { 0x204F });
        entityMap.put("bsim", new int[] { 0x223D });
        entityMap.put("bsime", new int[] { 0x22CD });
        entityMap.put("bsol", new int[] { 0x005C });
        entityMap.put("bsolb", new int[] { 0x29C5 });
        entityMap.put("bsolhsub", new int[] { 0x27C8 });
        entityMap.put("bull", new int[] { 0x2022 });
        entityMap.put("bullet", new int[] { 0x2022 });
        entityMap.put("bump", new int[] { 0x224E });
        entityMap.put("bumpE", new int[] { 0x2AAE });
        entityMap.put("bumpe", new int[] { 0x224F });
        entityMap.put("bumpeq", new int[] { 0x224F });
        entityMap.put("cacute", new int[] { 0x0107 });
        entityMap.put("cap", new int[] { 0x2229 });
        entityMap.put("capand", new int[] { 0x2A44 });
        entityMap.put("capbrcup", new int[] { 0x2A49 });
        entityMap.put("capcap", new int[] { 0x2A4B });
        entityMap.put("capcup", new int[] { 0x2A47 });
        entityMap.put("capdot", new int[] { 0x2A40 });
        entityMap.put("caps", new int[] { 0x2229, 0xFE00 });
        entityMap.put("caret", new int[] { 0x2041 });
        entityMap.put("caron", new int[] { 0x02C7 });
        entityMap.put("ccaps", new int[] { 0x2A4D });
        entityMap.put("ccaron", new int[] { 0x010D });
        // Skipping entity missing semicolon: &ccedil = 0x00E7
        entityMap.put("ccedil", new int[] { 0x00E7 });
        entityMap.put("ccirc", new int[] { 0x0109 });
        entityMap.put("ccups", new int[] { 0x2A4C });
        entityMap.put("ccupssm", new int[] { 0x2A50 });
        entityMap.put("cdot", new int[] { 0x010B });
        // Skipping entity missing semicolon: &cedil = 0x00B8
        entityMap.put("cedil", new int[] { 0x00B8 });
        entityMap.put("cemptyv", new int[] { 0x29B2 });
        // Skipping entity missing semicolon: &cent = 0x00A2
        entityMap.put("cent", new int[] { 0x00A2 });
        entityMap.put("centerdot", new int[] { 0x00B7 });
        entityMap.put("cfr", new int[] { 0x1D520 });
        entityMap.put("chcy", new int[] { 0x0447 });
        entityMap.put("check", new int[] { 0x2713 });
        entityMap.put("checkmark", new int[] { 0x2713 });
        entityMap.put("chi", new int[] { 0x03C7 });
        entityMap.put("cir", new int[] { 0x25CB });
        entityMap.put("cirE", new int[] { 0x29C3 });
        entityMap.put("circ", new int[] { 0x02C6 });
        entityMap.put("circeq", new int[] { 0x2257 });
        entityMap.put("circlearrowleft", new int[] { 0x21BA });
        entityMap.put("circlearrowright", new int[] { 0x21BB });
        entityMap.put("circledR", new int[] { 0x00AE });
        entityMap.put("circledS", new int[] { 0x24C8 });
        entityMap.put("circledast", new int[] { 0x229B });
        entityMap.put("circledcirc", new int[] { 0x229A });
        entityMap.put("circleddash", new int[] { 0x229D });
        entityMap.put("cire", new int[] { 0x2257 });
        entityMap.put("cirfnint", new int[] { 0x2A10 });
        entityMap.put("cirmid", new int[] { 0x2AEF });
        entityMap.put("cirscir", new int[] { 0x29C2 });
        entityMap.put("clubs", new int[] { 0x2663 });
        entityMap.put("clubsuit", new int[] { 0x2663 });
        entityMap.put("colon", new int[] { 0x003A });
        entityMap.put("colone", new int[] { 0x2254 });
        entityMap.put("coloneq", new int[] { 0x2254 });
        entityMap.put("comma", new int[] { 0x002C });
        entityMap.put("commat", new int[] { 0x0040 });
        entityMap.put("comp", new int[] { 0x2201 });
        entityMap.put("compfn", new int[] { 0x2218 });
        entityMap.put("complement", new int[] { 0x2201 });
        entityMap.put("complexes", new int[] { 0x2102 });
        entityMap.put("cong", new int[] { 0x2245 });
        entityMap.put("congdot", new int[] { 0x2A6D });
        entityMap.put("conint", new int[] { 0x222E });
        entityMap.put("copf", new int[] { 0x1D554 });
        entityMap.put("coprod", new int[] { 0x2210 });
        // Skipping entity missing semicolon: &copy = 0x00A9
        entityMap.put("copy", new int[] { 0x00A9 });
        entityMap.put("copysr", new int[] { 0x2117 });
        entityMap.put("crarr", new int[] { 0x21B5 });
        entityMap.put("cross", new int[] { 0x2717 });
        entityMap.put("cscr", new int[] { 0x1D4B8 });
        entityMap.put("csub", new int[] { 0x2ACF });
        entityMap.put("csube", new int[] { 0x2AD1 });
        entityMap.put("csup", new int[] { 0x2AD0 });
        entityMap.put("csupe", new int[] { 0x2AD2 });
        entityMap.put("ctdot", new int[] { 0x22EF });
        entityMap.put("cudarrl", new int[] { 0x2938 });
        entityMap.put("cudarrr", new int[] { 0x2935 });
        entityMap.put("cuepr", new int[] { 0x22DE });
        entityMap.put("cuesc", new int[] { 0x22DF });
        entityMap.put("cularr", new int[] { 0x21B6 });
        entityMap.put("cularrp", new int[] { 0x293D });
        entityMap.put("cup", new int[] { 0x222A });
        entityMap.put("cupbrcap", new int[] { 0x2A48 });
        entityMap.put("cupcap", new int[] { 0x2A46 });
        entityMap.put("cupcup", new int[] { 0x2A4A });
        entityMap.put("cupdot", new int[] { 0x228D });
        entityMap.put("cupor", new int[] { 0x2A45 });
        entityMap.put("cups", new int[] { 0x222A, 0xFE00 });
        entityMap.put("curarr", new int[] { 0x21B7 });
        entityMap.put("curarrm", new int[] { 0x293C });
        entityMap.put("curlyeqprec", new int[] { 0x22DE });
        entityMap.put("curlyeqsucc", new int[] { 0x22DF });
        entityMap.put("curlyvee", new int[] { 0x22CE });
        entityMap.put("curlywedge", new int[] { 0x22CF });
        // Skipping entity missing semicolon: &curren = 0x00A4
        entityMap.put("curren", new int[] { 0x00A4 });
        entityMap.put("curvearrowleft", new int[] { 0x21B6 });
        entityMap.put("curvearrowright", new int[] { 0x21B7 });
        entityMap.put("cuvee", new int[] { 0x22CE });
        entityMap.put("cuwed", new int[] { 0x22CF });
        entityMap.put("cwconint", new int[] { 0x2232 });
        entityMap.put("cwint", new int[] { 0x2231 });
        entityMap.put("cylcty", new int[] { 0x232D });
        entityMap.put("dArr", new int[] { 0x21D3 });
        entityMap.put("dHar", new int[] { 0x2965 });
        entityMap.put("dagger", new int[] { 0x2020 });
        entityMap.put("daleth", new int[] { 0x2138 });
        entityMap.put("darr", new int[] { 0x2193 });
        entityMap.put("dash", new int[] { 0x2010 });
        entityMap.put("dashv", new int[] { 0x22A3 });
        entityMap.put("dbkarow", new int[] { 0x290F });
        entityMap.put("dblac", new int[] { 0x02DD });
        entityMap.put("dcaron", new int[] { 0x010F });
        entityMap.put("dcy", new int[] { 0x0434 });
        entityMap.put("dd", new int[] { 0x2146 });
        entityMap.put("ddagger", new int[] { 0x2021 });
        entityMap.put("ddarr", new int[] { 0x21CA });
        entityMap.put("ddotseq", new int[] { 0x2A77 });
        // Skipping entity missing semicolon: &deg = 0x00B0
        entityMap.put("deg", new int[] { 0x00B0 });
        entityMap.put("delta", new int[] { 0x03B4 });
        entityMap.put("demptyv", new int[] { 0x29B1 });
        entityMap.put("dfisht", new int[] { 0x297F });
        entityMap.put("dfr", new int[] { 0x1D521 });
        entityMap.put("dharl", new int[] { 0x21C3 });
        entityMap.put("dharr", new int[] { 0x21C2 });
        entityMap.put("diam", new int[] { 0x22C4 });
        entityMap.put("diamond", new int[] { 0x22C4 });
        entityMap.put("diamondsuit", new int[] { 0x2666 });
        entityMap.put("diams", new int[] { 0x2666 });
        entityMap.put("die", new int[] { 0x00A8 });
        entityMap.put("digamma", new int[] { 0x03DD });
        entityMap.put("disin", new int[] { 0x22F2 });
        entityMap.put("div", new int[] { 0x00F7 });
        // Skipping entity missing semicolon: &divide = 0x00F7
        entityMap.put("divide", new int[] { 0x00F7 });
        entityMap.put("divideontimes", new int[] { 0x22C7 });
        entityMap.put("divonx", new int[] { 0x22C7 });
        entityMap.put("djcy", new int[] { 0x0452 });
        entityMap.put("dlcorn", new int[] { 0x231E });
        entityMap.put("dlcrop", new int[] { 0x230D });
        entityMap.put("dollar", new int[] { 0x0024 });
        entityMap.put("dopf", new int[] { 0x1D555 });
        entityMap.put("dot", new int[] { 0x02D9 });
        entityMap.put("doteq", new int[] { 0x2250 });
        entityMap.put("doteqdot", new int[] { 0x2251 });
        entityMap.put("dotminus", new int[] { 0x2238 });
        entityMap.put("dotplus", new int[] { 0x2214 });
        entityMap.put("dotsquare", new int[] { 0x22A1 });
        entityMap.put("doublebarwedge", new int[] { 0x2306 });
        entityMap.put("downarrow", new int[] { 0x2193 });
        entityMap.put("downdownarrows", new int[] { 0x21CA });
        entityMap.put("downharpoonleft", new int[] { 0x21C3 });
        entityMap.put("downharpoonright", new int[] { 0x21C2 });
        entityMap.put("drbkarow", new int[] { 0x2910 });
        entityMap.put("drcorn", new int[] { 0x231F });
        entityMap.put("drcrop", new int[] { 0x230C });
        entityMap.put("dscr", new int[] { 0x1D4B9 });
        entityMap.put("dscy", new int[] { 0x0455 });
        entityMap.put("dsol", new int[] { 0x29F6 });
        entityMap.put("dstrok", new int[] { 0x0111 });
        entityMap.put("dtdot", new int[] { 0x22F1 });
        entityMap.put("dtri", new int[] { 0x25BF });
        entityMap.put("dtrif", new int[] { 0x25BE });
        entityMap.put("duarr", new int[] { 0x21F5 });
        entityMap.put("duhar", new int[] { 0x296F });
        entityMap.put("dwangle", new int[] { 0x29A6 });
        entityMap.put("dzcy", new int[] { 0x045F });
        entityMap.put("dzigrarr", new int[] { 0x27FF });
        entityMap.put("eDDot", new int[] { 0x2A77 });
        entityMap.put("eDot", new int[] { 0x2251 });
        // Skipping entity missing semicolon: &eacute = 0x00E9
        entityMap.put("eacute", new int[] { 0x00E9 });
        entityMap.put("easter", new int[] { 0x2A6E });
        entityMap.put("ecaron", new int[] { 0x011B });
        entityMap.put("ecir", new int[] { 0x2256 });
        // Skipping entity missing semicolon: &ecirc = 0x00EA
        entityMap.put("ecirc", new int[] { 0x00EA });
        entityMap.put("ecolon", new int[] { 0x2255 });
        entityMap.put("ecy", new int[] { 0x044D });
        entityMap.put("edot", new int[] { 0x0117 });
        entityMap.put("ee", new int[] { 0x2147 });
        entityMap.put("efDot", new int[] { 0x2252 });
        entityMap.put("efr", new int[] { 0x1D522 });
        entityMap.put("eg", new int[] { 0x2A9A });
        // Skipping entity missing semicolon: &egrave = 0x00E8
        entityMap.put("egrave", new int[] { 0x00E8 });
        entityMap.put("egs", new int[] { 0x2A96 });
        entityMap.put("egsdot", new int[] { 0x2A98 });
        entityMap.put("el", new int[] { 0x2A99 });
        entityMap.put("elinters", new int[] { 0x23E7 });
        entityMap.put("ell", new int[] { 0x2113 });
        entityMap.put("els", new int[] { 0x2A95 });
        entityMap.put("elsdot", new int[] { 0x2A97 });
        entityMap.put("emacr", new int[] { 0x0113 });
        entityMap.put("empty", new int[] { 0x2205 });
        entityMap.put("emptyset", new int[] { 0x2205 });
        entityMap.put("emptyv", new int[] { 0x2205 });
        entityMap.put("emsp13", new int[] { 0x2004 });
        entityMap.put("emsp14", new int[] { 0x2005 });
        entityMap.put("emsp", new int[] { 0x2003 });
        entityMap.put("eng", new int[] { 0x014B });
        entityMap.put("ensp", new int[] { 0x2002 });
        entityMap.put("eogon", new int[] { 0x0119 });
        entityMap.put("eopf", new int[] { 0x1D556 });
        entityMap.put("epar", new int[] { 0x22D5 });
        entityMap.put("eparsl", new int[] { 0x29E3 });
        entityMap.put("eplus", new int[] { 0x2A71 });
        entityMap.put("epsi", new int[] { 0x03B5 });
        entityMap.put("epsilon", new int[] { 0x03B5 });
        entityMap.put("epsiv", new int[] { 0x03F5 });
        entityMap.put("eqcirc", new int[] { 0x2256 });
        entityMap.put("eqcolon", new int[] { 0x2255 });
        entityMap.put("eqsim", new int[] { 0x2242 });
        entityMap.put("eqslantgtr", new int[] { 0x2A96 });
        entityMap.put("eqslantless", new int[] { 0x2A95 });
        entityMap.put("equals", new int[] { 0x003D });
        entityMap.put("equest", new int[] { 0x225F });
        entityMap.put("equiv", new int[] { 0x2261 });
        entityMap.put("equivDD", new int[] { 0x2A78 });
        entityMap.put("eqvparsl", new int[] { 0x29E5 });
        entityMap.put("erDot", new int[] { 0x2253 });
        entityMap.put("erarr", new int[] { 0x2971 });
        entityMap.put("escr", new int[] { 0x212F });
        entityMap.put("esdot", new int[] { 0x2250 });
        entityMap.put("esim", new int[] { 0x2242 });
        entityMap.put("eta", new int[] { 0x03B7 });
        // Skipping entity missing semicolon: &eth = 0x00F0
        entityMap.put("eth", new int[] { 0x00F0 });
        // Skipping entity missing semicolon: &euml = 0x00EB
        entityMap.put("euml", new int[] { 0x00EB });
        entityMap.put("euro", new int[] { 0x20AC });
        entityMap.put("excl", new int[] { 0x0021 });
        entityMap.put("exist", new int[] { 0x2203 });
        entityMap.put("expectation", new int[] { 0x2130 });
        entityMap.put("exponentiale", new int[] { 0x2147 });
        entityMap.put("fallingdotseq", new int[] { 0x2252 });
        entityMap.put("fcy", new int[] { 0x0444 });
        entityMap.put("female", new int[] { 0x2640 });
        entityMap.put("ffilig", new int[] { 0xFB03 });
        entityMap.put("fflig", new int[] { 0xFB00 });
        entityMap.put("ffllig", new int[] { 0xFB04 });
        entityMap.put("ffr", new int[] { 0x1D523 });
        entityMap.put("filig", new int[] { 0xFB01 });
        entityMap.put("fjlig", new int[] { 0x0066, 0x006A });
        entityMap.put("flat", new int[] { 0x266D });
        entityMap.put("fllig", new int[] { 0xFB02 });
        entityMap.put("fltns", new int[] { 0x25B1 });
        entityMap.put("fnof", new int[] { 0x0192 });
        entityMap.put("fopf", new int[] { 0x1D557 });
        entityMap.put("forall", new int[] { 0x2200 });
        entityMap.put("fork", new int[] { 0x22D4 });
        entityMap.put("forkv", new int[] { 0x2AD9 });
        entityMap.put("fpartint", new int[] { 0x2A0D });
        // Skipping entity missing semicolon: &frac12 = 0x00BD
        entityMap.put("frac12", new int[] { 0x00BD });
        entityMap.put("frac13", new int[] { 0x2153 });
        // Skipping entity missing semicolon: &frac14 = 0x00BC
        entityMap.put("frac14", new int[] { 0x00BC });
        entityMap.put("frac15", new int[] { 0x2155 });
        entityMap.put("frac16", new int[] { 0x2159 });
        entityMap.put("frac18", new int[] { 0x215B });
        entityMap.put("frac23", new int[] { 0x2154 });
        entityMap.put("frac25", new int[] { 0x2156 });
        // Skipping entity missing semicolon: &frac34 = 0x00BE
        entityMap.put("frac34", new int[] { 0x00BE });
        entityMap.put("frac35", new int[] { 0x2157 });
        entityMap.put("frac38", new int[] { 0x215C });
        entityMap.put("frac45", new int[] { 0x2158 });
        entityMap.put("frac56", new int[] { 0x215A });
        entityMap.put("frac58", new int[] { 0x215D });
        entityMap.put("frac78", new int[] { 0x215E });
        entityMap.put("frasl", new int[] { 0x2044 });
        entityMap.put("frown", new int[] { 0x2322 });
        entityMap.put("fscr", new int[] { 0x1D4BB });
        entityMap.put("gE", new int[] { 0x2267 });
        entityMap.put("gEl", new int[] { 0x2A8C });
        entityMap.put("gacute", new int[] { 0x01F5 });
        entityMap.put("gamma", new int[] { 0x03B3 });
        entityMap.put("gammad", new int[] { 0x03DD });
        entityMap.put("gap", new int[] { 0x2A86 });
        entityMap.put("gbreve", new int[] { 0x011F });
        entityMap.put("gcirc", new int[] { 0x011D });
        entityMap.put("gcy", new int[] { 0x0433 });
        entityMap.put("gdot", new int[] { 0x0121 });
        entityMap.put("ge", new int[] { 0x2265 });
        entityMap.put("gel", new int[] { 0x22DB });
        entityMap.put("geq", new int[] { 0x2265 });
        entityMap.put("geqq", new int[] { 0x2267 });
        entityMap.put("geqslant", new int[] { 0x2A7E });
        entityMap.put("ges", new int[] { 0x2A7E });
        entityMap.put("gescc", new int[] { 0x2AA9 });
        entityMap.put("gesdot", new int[] { 0x2A80 });
        entityMap.put("gesdoto", new int[] { 0x2A82 });
        entityMap.put("gesdotol", new int[] { 0x2A84 });
        entityMap.put("gesl", new int[] { 0x22DB, 0xFE00 });
        entityMap.put("gesles", new int[] { 0x2A94 });
        entityMap.put("gfr", new int[] { 0x1D524 });
        entityMap.put("gg", new int[] { 0x226B });
        entityMap.put("ggg", new int[] { 0x22D9 });
        entityMap.put("gimel", new int[] { 0x2137 });
        entityMap.put("gjcy", new int[] { 0x0453 });
        entityMap.put("gl", new int[] { 0x2277 });
        entityMap.put("glE", new int[] { 0x2A92 });
        entityMap.put("gla", new int[] { 0x2AA5 });
        entityMap.put("glj", new int[] { 0x2AA4 });
        entityMap.put("gnE", new int[] { 0x2269 });
        entityMap.put("gnap", new int[] { 0x2A8A });
        entityMap.put("gnapprox", new int[] { 0x2A8A });
        entityMap.put("gne", new int[] { 0x2A88 });
        entityMap.put("gneq", new int[] { 0x2A88 });
        entityMap.put("gneqq", new int[] { 0x2269 });
        entityMap.put("gnsim", new int[] { 0x22E7 });
        entityMap.put("gopf", new int[] { 0x1D558 });
        entityMap.put("grave", new int[] { 0x0060 });
        entityMap.put("gscr", new int[] { 0x210A });
        entityMap.put("gsim", new int[] { 0x2273 });
        entityMap.put("gsime", new int[] { 0x2A8E });
        entityMap.put("gsiml", new int[] { 0x2A90 });
        // Skipping entity missing semicolon: &gt = 0x003E
        entityMap.put("gt", new int[] { 0x003E });
        entityMap.put("gtcc", new int[] { 0x2AA7 });
        entityMap.put("gtcir", new int[] { 0x2A7A });
        entityMap.put("gtdot", new int[] { 0x22D7 });
        entityMap.put("gtlPar", new int[] { 0x2995 });
        entityMap.put("gtquest", new int[] { 0x2A7C });
        entityMap.put("gtrapprox", new int[] { 0x2A86 });
        entityMap.put("gtrarr", new int[] { 0x2978 });
        entityMap.put("gtrdot", new int[] { 0x22D7 });
        entityMap.put("gtreqless", new int[] { 0x22DB });
        entityMap.put("gtreqqless", new int[] { 0x2A8C });
        entityMap.put("gtrless", new int[] { 0x2277 });
        entityMap.put("gtrsim", new int[] { 0x2273 });
        entityMap.put("gvertneqq", new int[] { 0x2269, 0xFE00 });
        entityMap.put("gvnE", new int[] { 0x2269, 0xFE00 });
        entityMap.put("hArr", new int[] { 0x21D4 });
        entityMap.put("hairsp", new int[] { 0x200A });
        entityMap.put("half", new int[] { 0x00BD });
        entityMap.put("hamilt", new int[] { 0x210B });
        entityMap.put("hardcy", new int[] { 0x044A });
        entityMap.put("harr", new int[] { 0x2194 });
        entityMap.put("harrcir", new int[] { 0x2948 });
        entityMap.put("harrw", new int[] { 0x21AD });
        entityMap.put("hbar", new int[] { 0x210F });
        entityMap.put("hcirc", new int[] { 0x0125 });
        entityMap.put("hearts", new int[] { 0x2665 });
        entityMap.put("heartsuit", new int[] { 0x2665 });
        entityMap.put("hellip", new int[] { 0x2026 });
        entityMap.put("hercon", new int[] { 0x22B9 });
        entityMap.put("hfr", new int[] { 0x1D525 });
        entityMap.put("hksearow", new int[] { 0x2925 });
        entityMap.put("hkswarow", new int[] { 0x2926 });
        entityMap.put("hoarr", new int[] { 0x21FF });
        entityMap.put("homtht", new int[] { 0x223B });
        entityMap.put("hookleftarrow", new int[] { 0x21A9 });
        entityMap.put("hookrightarrow", new int[] { 0x21AA });
        entityMap.put("hopf", new int[] { 0x1D559 });
        entityMap.put("horbar", new int[] { 0x2015 });
        entityMap.put("hscr", new int[] { 0x1D4BD });
        entityMap.put("hslash", new int[] { 0x210F });
        entityMap.put("hstrok", new int[] { 0x0127 });
        entityMap.put("hybull", new int[] { 0x2043 });
        entityMap.put("hyphen", new int[] { 0x2010 });
        // Skipping entity missing semicolon: &iacute = 0x00ED
        entityMap.put("iacute", new int[] { 0x00ED });
        entityMap.put("ic", new int[] { 0x2063 });
        // Skipping entity missing semicolon: &icirc = 0x00EE
        entityMap.put("icirc", new int[] { 0x00EE });
        entityMap.put("icy", new int[] { 0x0438 });
        entityMap.put("iecy", new int[] { 0x0435 });
        // Skipping entity missing semicolon: &iexcl = 0x00A1
        entityMap.put("iexcl", new int[] { 0x00A1 });
        entityMap.put("iff", new int[] { 0x21D4 });
        entityMap.put("ifr", new int[] { 0x1D526 });
        // Skipping entity missing semicolon: &igrave = 0x00EC
        entityMap.put("igrave", new int[] { 0x00EC });
        entityMap.put("ii", new int[] { 0x2148 });
        entityMap.put("iiiint", new int[] { 0x2A0C });
        entityMap.put("iiint", new int[] { 0x222D });
        entityMap.put("iinfin", new int[] { 0x29DC });
        entityMap.put("iiota", new int[] { 0x2129 });
        entityMap.put("ijlig", new int[] { 0x0133 });
        entityMap.put("imacr", new int[] { 0x012B });
        entityMap.put("image", new int[] { 0x2111 });
        entityMap.put("imagline", new int[] { 0x2110 });
        entityMap.put("imagpart", new int[] { 0x2111 });
        entityMap.put("imath", new int[] { 0x0131 });
        entityMap.put("imof", new int[] { 0x22B7 });
        entityMap.put("imped", new int[] { 0x01B5 });
        entityMap.put("in", new int[] { 0x2208 });
        entityMap.put("incare", new int[] { 0x2105 });
        entityMap.put("infin", new int[] { 0x221E });
        entityMap.put("infintie", new int[] { 0x29DD });
        entityMap.put("inodot", new int[] { 0x0131 });
        entityMap.put("int", new int[] { 0x222B });
        entityMap.put("intcal", new int[] { 0x22BA });
        entityMap.put("integers", new int[] { 0x2124 });
        entityMap.put("intercal", new int[] { 0x22BA });
        entityMap.put("intlarhk", new int[] { 0x2A17 });
        entityMap.put("intprod", new int[] { 0x2A3C });
        entityMap.put("iocy", new int[] { 0x0451 });
        entityMap.put("iogon", new int[] { 0x012F });
        entityMap.put("iopf", new int[] { 0x1D55A });
        entityMap.put("iota", new int[] { 0x03B9 });
        entityMap.put("iprod", new int[] { 0x2A3C });
        // Skipping entity missing semicolon: &iquest = 0x00BF
        entityMap.put("iquest", new int[] { 0x00BF });
        entityMap.put("iscr", new int[] { 0x1D4BE });
        entityMap.put("isin", new int[] { 0x2208 });
        entityMap.put("isinE", new int[] { 0x22F9 });
        entityMap.put("isindot", new int[] { 0x22F5 });
        entityMap.put("isins", new int[] { 0x22F4 });
        entityMap.put("isinsv", new int[] { 0x22F3 });
        entityMap.put("isinv", new int[] { 0x2208 });
        entityMap.put("it", new int[] { 0x2062 });
        entityMap.put("itilde", new int[] { 0x0129 });
        entityMap.put("iukcy", new int[] { 0x0456 });
        // Skipping entity missing semicolon: &iuml = 0x00EF
        entityMap.put("iuml", new int[] { 0x00EF });
        entityMap.put("jcirc", new int[] { 0x0135 });
        entityMap.put("jcy", new int[] { 0x0439 });
        entityMap.put("jfr", new int[] { 0x1D527 });
        entityMap.put("jmath", new int[] { 0x0237 });
        entityMap.put("jopf", new int[] { 0x1D55B });
        entityMap.put("jscr", new int[] { 0x1D4BF });
        entityMap.put("jsercy", new int[] { 0x0458 });
        entityMap.put("jukcy", new int[] { 0x0454 });
        entityMap.put("kappa", new int[] { 0x03BA });
        entityMap.put("kappav", new int[] { 0x03F0 });
        entityMap.put("kcedil", new int[] { 0x0137 });
        entityMap.put("kcy", new int[] { 0x043A });
        entityMap.put("kfr", new int[] { 0x1D528 });
        entityMap.put("kgreen", new int[] { 0x0138 });
        entityMap.put("khcy", new int[] { 0x0445 });
        entityMap.put("kjcy", new int[] { 0x045C });
        entityMap.put("kopf", new int[] { 0x1D55C });
        entityMap.put("kscr", new int[] { 0x1D4C0 });
        entityMap.put("lAarr", new int[] { 0x21DA });
        entityMap.put("lArr", new int[] { 0x21D0 });
        entityMap.put("lAtail", new int[] { 0x291B });
        entityMap.put("lBarr", new int[] { 0x290E });
        entityMap.put("lE", new int[] { 0x2266 });
        entityMap.put("lEg", new int[] { 0x2A8B });
        entityMap.put("lHar", new int[] { 0x2962 });
        entityMap.put("lacute", new int[] { 0x013A });
        entityMap.put("laemptyv", new int[] { 0x29B4 });
        entityMap.put("lagran", new int[] { 0x2112 });
        entityMap.put("lambda", new int[] { 0x03BB });
        entityMap.put("lang", new int[] { 0x27E8 });
        entityMap.put("langd", new int[] { 0x2991 });
        entityMap.put("langle", new int[] { 0x27E8 });
        entityMap.put("lap", new int[] { 0x2A85 });
        // Skipping entity missing semicolon: &laquo = 0x00AB
        entityMap.put("laquo", new int[] { 0x00AB });
        entityMap.put("larr", new int[] { 0x2190 });
        entityMap.put("larrb", new int[] { 0x21E4 });
        entityMap.put("larrbfs", new int[] { 0x291F });
        entityMap.put("larrfs", new int[] { 0x291D });
        entityMap.put("larrhk", new int[] { 0x21A9 });
        entityMap.put("larrlp", new int[] { 0x21AB });
        entityMap.put("larrpl", new int[] { 0x2939 });
        entityMap.put("larrsim", new int[] { 0x2973 });
        entityMap.put("larrtl", new int[] { 0x21A2 });
        entityMap.put("lat", new int[] { 0x2AAB });
        entityMap.put("latail", new int[] { 0x2919 });
        entityMap.put("late", new int[] { 0x2AAD });
        entityMap.put("lates", new int[] { 0x2AAD, 0xFE00 });
        entityMap.put("lbarr", new int[] { 0x290C });
        entityMap.put("lbbrk", new int[] { 0x2772 });
        entityMap.put("lbrace", new int[] { 0x007B });
        entityMap.put("lbrack", new int[] { 0x005B });
        entityMap.put("lbrke", new int[] { 0x298B });
        entityMap.put("lbrksld", new int[] { 0x298F });
        entityMap.put("lbrkslu", new int[] { 0x298D });
        entityMap.put("lcaron", new int[] { 0x013E });
        entityMap.put("lcedil", new int[] { 0x013C });
        entityMap.put("lceil", new int[] { 0x2308 });
        entityMap.put("lcub", new int[] { 0x007B });
        entityMap.put("lcy", new int[] { 0x043B });
        entityMap.put("ldca", new int[] { 0x2936 });
        entityMap.put("ldquo", new int[] { 0x201C });
        entityMap.put("ldquor", new int[] { 0x201E });
        entityMap.put("ldrdhar", new int[] { 0x2967 });
        entityMap.put("ldrushar", new int[] { 0x294B });
        entityMap.put("ldsh", new int[] { 0x21B2 });
        entityMap.put("le", new int[] { 0x2264 });
        entityMap.put("leftarrow", new int[] { 0x2190 });
        entityMap.put("leftarrowtail", new int[] { 0x21A2 });
        entityMap.put("leftharpoondown", new int[] { 0x21BD });
        entityMap.put("leftharpoonup", new int[] { 0x21BC });
        entityMap.put("leftleftarrows", new int[] { 0x21C7 });
        entityMap.put("leftrightarrow", new int[] { 0x2194 });
        entityMap.put("leftrightarrows", new int[] { 0x21C6 });
        entityMap.put("leftrightharpoons", new int[] { 0x21CB });
        entityMap.put("leftrightsquigarrow", new int[] { 0x21AD });
        entityMap.put("leftthreetimes", new int[] { 0x22CB });
        entityMap.put("leg", new int[] { 0x22DA });
        entityMap.put("leq", new int[] { 0x2264 });
        entityMap.put("leqq", new int[] { 0x2266 });
        entityMap.put("leqslant", new int[] { 0x2A7D });
        entityMap.put("les", new int[] { 0x2A7D });
        entityMap.put("lescc", new int[] { 0x2AA8 });
        entityMap.put("lesdot", new int[] { 0x2A7F });
        entityMap.put("lesdoto", new int[] { 0x2A81 });
        entityMap.put("lesdotor", new int[] { 0x2A83 });
        entityMap.put("lesg", new int[] { 0x22DA, 0xFE00 });
        entityMap.put("lesges", new int[] { 0x2A93 });
        entityMap.put("lessapprox", new int[] { 0x2A85 });
        entityMap.put("lessdot", new int[] { 0x22D6 });
        entityMap.put("lesseqgtr", new int[] { 0x22DA });
        entityMap.put("lesseqqgtr", new int[] { 0x2A8B });
        entityMap.put("lessgtr", new int[] { 0x2276 });
        entityMap.put("lesssim", new int[] { 0x2272 });
        entityMap.put("lfisht", new int[] { 0x297C });
        entityMap.put("lfloor", new int[] { 0x230A });
        entityMap.put("lfr", new int[] { 0x1D529 });
        entityMap.put("lg", new int[] { 0x2276 });
        entityMap.put("lgE", new int[] { 0x2A91 });
        entityMap.put("lhard", new int[] { 0x21BD });
        entityMap.put("lharu", new int[] { 0x21BC });
        entityMap.put("lharul", new int[] { 0x296A });
        entityMap.put("lhblk", new int[] { 0x2584 });
        entityMap.put("ljcy", new int[] { 0x0459 });
        entityMap.put("ll", new int[] { 0x226A });
        entityMap.put("llarr", new int[] { 0x21C7 });
        entityMap.put("llcorner", new int[] { 0x231E });
        entityMap.put("llhard", new int[] { 0x296B });
        entityMap.put("lltri", new int[] { 0x25FA });
        entityMap.put("lmidot", new int[] { 0x0140 });
        entityMap.put("lmoust", new int[] { 0x23B0 });
        entityMap.put("lmoustache", new int[] { 0x23B0 });
        entityMap.put("lnE", new int[] { 0x2268 });
        entityMap.put("lnap", new int[] { 0x2A89 });
        entityMap.put("lnapprox", new int[] { 0x2A89 });
        entityMap.put("lne", new int[] { 0x2A87 });
        entityMap.put("lneq", new int[] { 0x2A87 });
        entityMap.put("lneqq", new int[] { 0x2268 });
        entityMap.put("lnsim", new int[] { 0x22E6 });
        entityMap.put("loang", new int[] { 0x27EC });
        entityMap.put("loarr", new int[] { 0x21FD });
        entityMap.put("lobrk", new int[] { 0x27E6 });
        entityMap.put("longleftarrow", new int[] { 0x27F5 });
        entityMap.put("longleftrightarrow", new int[] { 0x27F7 });
        entityMap.put("longmapsto", new int[] { 0x27FC });
        entityMap.put("longrightarrow", new int[] { 0x27F6 });
        entityMap.put("looparrowleft", new int[] { 0x21AB });
        entityMap.put("looparrowright", new int[] { 0x21AC });
        entityMap.put("lopar", new int[] { 0x2985 });
        entityMap.put("lopf", new int[] { 0x1D55D });
        entityMap.put("loplus", new int[] { 0x2A2D });
        entityMap.put("lotimes", new int[] { 0x2A34 });
        entityMap.put("lowast", new int[] { 0x2217 });
        entityMap.put("lowbar", new int[] { 0x005F });
        entityMap.put("loz", new int[] { 0x25CA });
        entityMap.put("lozenge", new int[] { 0x25CA });
        entityMap.put("lozf", new int[] { 0x29EB });
        entityMap.put("lpar", new int[] { 0x0028 });
        entityMap.put("lparlt", new int[] { 0x2993 });
        entityMap.put("lrarr", new int[] { 0x21C6 });
        entityMap.put("lrcorner", new int[] { 0x231F });
        entityMap.put("lrhar", new int[] { 0x21CB });
        entityMap.put("lrhard", new int[] { 0x296D });
        entityMap.put("lrm", new int[] { 0x200E });
        entityMap.put("lrtri", new int[] { 0x22BF });
        entityMap.put("lsaquo", new int[] { 0x2039 });
        entityMap.put("lscr", new int[] { 0x1D4C1 });
        entityMap.put("lsh", new int[] { 0x21B0 });
        entityMap.put("lsim", new int[] { 0x2272 });
        entityMap.put("lsime", new int[] { 0x2A8D });
        entityMap.put("lsimg", new int[] { 0x2A8F });
        entityMap.put("lsqb", new int[] { 0x005B });
        entityMap.put("lsquo", new int[] { 0x2018 });
        entityMap.put("lsquor", new int[] { 0x201A });
        entityMap.put("lstrok", new int[] { 0x0142 });
        // Skipping entity missing semicolon: &lt = 0x003C
        entityMap.put("lt", new int[] { 0x003C });
        entityMap.put("ltcc", new int[] { 0x2AA6 });
        entityMap.put("ltcir", new int[] { 0x2A79 });
        entityMap.put("ltdot", new int[] { 0x22D6 });
        entityMap.put("lthree", new int[] { 0x22CB });
        entityMap.put("ltimes", new int[] { 0x22C9 });
        entityMap.put("ltlarr", new int[] { 0x2976 });
        entityMap.put("ltquest", new int[] { 0x2A7B });
        entityMap.put("ltrPar", new int[] { 0x2996 });
        entityMap.put("ltri", new int[] { 0x25C3 });
        entityMap.put("ltrie", new int[] { 0x22B4 });
        entityMap.put("ltrif", new int[] { 0x25C2 });
        entityMap.put("lurdshar", new int[] { 0x294A });
        entityMap.put("luruhar", new int[] { 0x2966 });
        entityMap.put("lvertneqq", new int[] { 0x2268, 0xFE00 });
        entityMap.put("lvnE", new int[] { 0x2268, 0xFE00 });
        entityMap.put("mDDot", new int[] { 0x223A });
        // Skipping entity missing semicolon: &macr = 0x00AF
        entityMap.put("macr", new int[] { 0x00AF });
        entityMap.put("male", new int[] { 0x2642 });
        entityMap.put("malt", new int[] { 0x2720 });
        entityMap.put("maltese", new int[] { 0x2720 });
        entityMap.put("map", new int[] { 0x21A6 });
        entityMap.put("mapsto", new int[] { 0x21A6 });
        entityMap.put("mapstodown", new int[] { 0x21A7 });
        entityMap.put("mapstoleft", new int[] { 0x21A4 });
        entityMap.put("mapstoup", new int[] { 0x21A5 });
        entityMap.put("marker", new int[] { 0x25AE });
        entityMap.put("mcomma", new int[] { 0x2A29 });
        entityMap.put("mcy", new int[] { 0x043C });
        entityMap.put("mdash", new int[] { 0x2014 });
        entityMap.put("measuredangle", new int[] { 0x2221 });
        entityMap.put("mfr", new int[] { 0x1D52A });
        entityMap.put("mho", new int[] { 0x2127 });
        // Skipping entity missing semicolon: &micro = 0x00B5
        entityMap.put("micro", new int[] { 0x00B5 });
        entityMap.put("mid", new int[] { 0x2223 });
        entityMap.put("midast", new int[] { 0x002A });
        entityMap.put("midcir", new int[] { 0x2AF0 });
        // Skipping entity missing semicolon: &middot = 0x00B7
        entityMap.put("middot", new int[] { 0x00B7 });
        entityMap.put("minus", new int[] { 0x2212 });
        entityMap.put("minusb", new int[] { 0x229F });
        entityMap.put("minusd", new int[] { 0x2238 });
        entityMap.put("minusdu", new int[] { 0x2A2A });
        entityMap.put("mlcp", new int[] { 0x2ADB });
        entityMap.put("mldr", new int[] { 0x2026 });
        entityMap.put("mnplus", new int[] { 0x2213 });
        entityMap.put("models", new int[] { 0x22A7 });
        entityMap.put("mopf", new int[] { 0x1D55E });
        entityMap.put("mp", new int[] { 0x2213 });
        entityMap.put("mscr", new int[] { 0x1D4C2 });
        entityMap.put("mstpos", new int[] { 0x223E });
        entityMap.put("mu", new int[] { 0x03BC });
        entityMap.put("multimap", new int[] { 0x22B8 });
        entityMap.put("mumap", new int[] { 0x22B8 });
        entityMap.put("nGg", new int[] { 0x22D9, 0x0338 });
        entityMap.put("nGt", new int[] { 0x226B, 0x20D2 });
        entityMap.put("nGtv", new int[] { 0x226B, 0x0338 });
        entityMap.put("nLeftarrow", new int[] { 0x21CD });
        entityMap.put("nLeftrightarrow", new int[] { 0x21CE });
        entityMap.put("nLl", new int[] { 0x22D8, 0x0338 });
        entityMap.put("nLt", new int[] { 0x226A, 0x20D2 });
        entityMap.put("nLtv", new int[] { 0x226A, 0x0338 });
        entityMap.put("nRightarrow", new int[] { 0x21CF });
        entityMap.put("nVDash", new int[] { 0x22AF });
        entityMap.put("nVdash", new int[] { 0x22AE });
        entityMap.put("nabla", new int[] { 0x2207 });
        entityMap.put("nacute", new int[] { 0x0144 });
        entityMap.put("nang", new int[] { 0x2220, 0x20D2 });
        entityMap.put("nap", new int[] { 0x2249 });
        entityMap.put("napE", new int[] { 0x2A70, 0x0338 });
        entityMap.put("napid", new int[] { 0x224B, 0x0338 });
        entityMap.put("napos", new int[] { 0x0149 });
        entityMap.put("napprox", new int[] { 0x2249 });
        entityMap.put("natur", new int[] { 0x266E });
        entityMap.put("natural", new int[] { 0x266E });
        entityMap.put("naturals", new int[] { 0x2115 });
        // Skipping entity missing semicolon: &nbsp = 0x00A0
        entityMap.put("nbsp", new int[] { 0x00A0 });
        entityMap.put("nbump", new int[] { 0x224E, 0x0338 });
        entityMap.put("nbumpe", new int[] { 0x224F, 0x0338 });
        entityMap.put("ncap", new int[] { 0x2A43 });
        entityMap.put("ncaron", new int[] { 0x0148 });
        entityMap.put("ncedil", new int[] { 0x0146 });
        entityMap.put("ncong", new int[] { 0x2247 });
        entityMap.put("ncongdot", new int[] { 0x2A6D, 0x0338 });
        entityMap.put("ncup", new int[] { 0x2A42 });
        entityMap.put("ncy", new int[] { 0x043D });
        entityMap.put("ndash", new int[] { 0x2013 });
        entityMap.put("ne", new int[] { 0x2260 });
        entityMap.put("neArr", new int[] { 0x21D7 });
        entityMap.put("nearhk", new int[] { 0x2924 });
        entityMap.put("nearr", new int[] { 0x2197 });
        entityMap.put("nearrow", new int[] { 0x2197 });
        entityMap.put("nedot", new int[] { 0x2250, 0x0338 });
        entityMap.put("nequiv", new int[] { 0x2262 });
        entityMap.put("nesear", new int[] { 0x2928 });
        entityMap.put("nesim", new int[] { 0x2242, 0x0338 });
        entityMap.put("nexist", new int[] { 0x2204 });
        entityMap.put("nexists", new int[] { 0x2204 });
        entityMap.put("nfr", new int[] { 0x1D52B });
        entityMap.put("ngE", new int[] { 0x2267, 0x0338 });
        entityMap.put("nge", new int[] { 0x2271 });
        entityMap.put("ngeq", new int[] { 0x2271 });
        entityMap.put("ngeqq", new int[] { 0x2267, 0x0338 });
        entityMap.put("ngeqslant", new int[] { 0x2A7E, 0x0338 });
        entityMap.put("nges", new int[] { 0x2A7E, 0x0338 });
        entityMap.put("ngsim", new int[] { 0x2275 });
        entityMap.put("ngt", new int[] { 0x226F });
        entityMap.put("ngtr", new int[] { 0x226F });
        entityMap.put("nhArr", new int[] { 0x21CE });
        entityMap.put("nharr", new int[] { 0x21AE });
        entityMap.put("nhpar", new int[] { 0x2AF2 });
        entityMap.put("ni", new int[] { 0x220B });
        entityMap.put("nis", new int[] { 0x22FC });
        entityMap.put("nisd", new int[] { 0x22FA });
        entityMap.put("niv", new int[] { 0x220B });
        entityMap.put("njcy", new int[] { 0x045A });
        entityMap.put("nlArr", new int[] { 0x21CD });
        entityMap.put("nlE", new int[] { 0x2266, 0x0338 });
        entityMap.put("nlarr", new int[] { 0x219A });
        entityMap.put("nldr", new int[] { 0x2025 });
        entityMap.put("nle", new int[] { 0x2270 });
        entityMap.put("nleftarrow", new int[] { 0x219A });
        entityMap.put("nleftrightarrow", new int[] { 0x21AE });
        entityMap.put("nleq", new int[] { 0x2270 });
        entityMap.put("nleqq", new int[] { 0x2266, 0x0338 });
        entityMap.put("nleqslant", new int[] { 0x2A7D, 0x0338 });
        entityMap.put("nles", new int[] { 0x2A7D, 0x0338 });
        entityMap.put("nless", new int[] { 0x226E });
        entityMap.put("nlsim", new int[] { 0x2274 });
        entityMap.put("nlt", new int[] { 0x226E });
        entityMap.put("nltri", new int[] { 0x22EA });
        entityMap.put("nltrie", new int[] { 0x22EC });
        entityMap.put("nmid", new int[] { 0x2224 });
        entityMap.put("nopf", new int[] { 0x1D55F });
        // Skipping entity missing semicolon: &not = 0x00AC
        entityMap.put("not", new int[] { 0x00AC });
        entityMap.put("notin", new int[] { 0x2209 });
        entityMap.put("notinE", new int[] { 0x22F9, 0x0338 });
        entityMap.put("notindot", new int[] { 0x22F5, 0x0338 });
        entityMap.put("notinva", new int[] { 0x2209 });
        entityMap.put("notinvb", new int[] { 0x22F7 });
        entityMap.put("notinvc", new int[] { 0x22F6 });
        entityMap.put("notni", new int[] { 0x220C });
        entityMap.put("notniva", new int[] { 0x220C });
        entityMap.put("notnivb", new int[] { 0x22FE });
        entityMap.put("notnivc", new int[] { 0x22FD });
        entityMap.put("npar", new int[] { 0x2226 });
        entityMap.put("nparallel", new int[] { 0x2226 });
        entityMap.put("nparsl", new int[] { 0x2AFD, 0x20E5 });
        entityMap.put("npart", new int[] { 0x2202, 0x0338 });
        entityMap.put("npolint", new int[] { 0x2A14 });
        entityMap.put("npr", new int[] { 0x2280 });
        entityMap.put("nprcue", new int[] { 0x22E0 });
        entityMap.put("npre", new int[] { 0x2AAF, 0x0338 });
        entityMap.put("nprec", new int[] { 0x2280 });
        entityMap.put("npreceq", new int[] { 0x2AAF, 0x0338 });
        entityMap.put("nrArr", new int[] { 0x21CF });
        entityMap.put("nrarr", new int[] { 0x219B });
        entityMap.put("nrarrc", new int[] { 0x2933, 0x0338 });
        entityMap.put("nrarrw", new int[] { 0x219D, 0x0338 });
        entityMap.put("nrightarrow", new int[] { 0x219B });
        entityMap.put("nrtri", new int[] { 0x22EB });
        entityMap.put("nrtrie", new int[] { 0x22ED });
        entityMap.put("nsc", new int[] { 0x2281 });
        entityMap.put("nsccue", new int[] { 0x22E1 });
        entityMap.put("nsce", new int[] { 0x2AB0, 0x0338 });
        entityMap.put("nscr", new int[] { 0x1D4C3 });
        entityMap.put("nshortmid", new int[] { 0x2224 });
        entityMap.put("nshortparallel", new int[] { 0x2226 });
        entityMap.put("nsim", new int[] { 0x2241 });
        entityMap.put("nsime", new int[] { 0x2244 });
        entityMap.put("nsimeq", new int[] { 0x2244 });
        entityMap.put("nsmid", new int[] { 0x2224 });
        entityMap.put("nspar", new int[] { 0x2226 });
        entityMap.put("nsqsube", new int[] { 0x22E2 });
        entityMap.put("nsqsupe", new int[] { 0x22E3 });
        entityMap.put("nsub", new int[] { 0x2284 });
        entityMap.put("nsubE", new int[] { 0x2AC5, 0x0338 });
        entityMap.put("nsube", new int[] { 0x2288 });
        entityMap.put("nsubset", new int[] { 0x2282, 0x20D2 });
        entityMap.put("nsubseteq", new int[] { 0x2288 });
        entityMap.put("nsubseteqq", new int[] { 0x2AC5, 0x0338 });
        entityMap.put("nsucc", new int[] { 0x2281 });
        entityMap.put("nsucceq", new int[] { 0x2AB0, 0x0338 });
        entityMap.put("nsup", new int[] { 0x2285 });
        entityMap.put("nsupE", new int[] { 0x2AC6, 0x0338 });
        entityMap.put("nsupe", new int[] { 0x2289 });
        entityMap.put("nsupset", new int[] { 0x2283, 0x20D2 });
        entityMap.put("nsupseteq", new int[] { 0x2289 });
        entityMap.put("nsupseteqq", new int[] { 0x2AC6, 0x0338 });
        entityMap.put("ntgl", new int[] { 0x2279 });
        // Skipping entity missing semicolon: &ntilde = 0x00F1
        entityMap.put("ntilde", new int[] { 0x00F1 });
        entityMap.put("ntlg", new int[] { 0x2278 });
        entityMap.put("ntriangleleft", new int[] { 0x22EA });
        entityMap.put("ntrianglelefteq", new int[] { 0x22EC });
        entityMap.put("ntriangleright", new int[] { 0x22EB });
        entityMap.put("ntrianglerighteq", new int[] { 0x22ED });
        entityMap.put("nu", new int[] { 0x03BD });
        entityMap.put("num", new int[] { 0x0023 });
        entityMap.put("numero", new int[] { 0x2116 });
        entityMap.put("numsp", new int[] { 0x2007 });
        entityMap.put("nvDash", new int[] { 0x22AD });
        entityMap.put("nvHarr", new int[] { 0x2904 });
        entityMap.put("nvap", new int[] { 0x224D, 0x20D2 });
        entityMap.put("nvdash", new int[] { 0x22AC });
        entityMap.put("nvge", new int[] { 0x2265, 0x20D2 });
        entityMap.put("nvgt", new int[] { 0x003E, 0x20D2 });
        entityMap.put("nvinfin", new int[] { 0x29DE });
        entityMap.put("nvlArr", new int[] { 0x2902 });
        entityMap.put("nvle", new int[] { 0x2264, 0x20D2 });
        entityMap.put("nvlt", new int[] { 0x003C, 0x20D2 });
        entityMap.put("nvltrie", new int[] { 0x22B4, 0x20D2 });
        entityMap.put("nvrArr", new int[] { 0x2903 });
        entityMap.put("nvrtrie", new int[] { 0x22B5, 0x20D2 });
        entityMap.put("nvsim", new int[] { 0x223C, 0x20D2 });
        entityMap.put("nwArr", new int[] { 0x21D6 });
        entityMap.put("nwarhk", new int[] { 0x2923 });
        entityMap.put("nwarr", new int[] { 0x2196 });
        entityMap.put("nwarrow", new int[] { 0x2196 });
        entityMap.put("nwnear", new int[] { 0x2927 });
        entityMap.put("oS", new int[] { 0x24C8 });
        // Skipping entity missing semicolon: &oacute = 0x00F3
        entityMap.put("oacute", new int[] { 0x00F3 });
        entityMap.put("oast", new int[] { 0x229B });
        entityMap.put("ocir", new int[] { 0x229A });
        // Skipping entity missing semicolon: &ocirc = 0x00F4
        entityMap.put("ocirc", new int[] { 0x00F4 });
        entityMap.put("ocy", new int[] { 0x043E });
        entityMap.put("odash", new int[] { 0x229D });
        entityMap.put("odblac", new int[] { 0x0151 });
        entityMap.put("odiv", new int[] { 0x2A38 });
        entityMap.put("odot", new int[] { 0x2299 });
        entityMap.put("odsold", new int[] { 0x29BC });
        entityMap.put("oelig", new int[] { 0x0153 });
        entityMap.put("ofcir", new int[] { 0x29BF });
        entityMap.put("ofr", new int[] { 0x1D52C });
        entityMap.put("ogon", new int[] { 0x02DB });
        // Skipping entity missing semicolon: &ograve = 0x00F2
        entityMap.put("ograve", new int[] { 0x00F2 });
        entityMap.put("ogt", new int[] { 0x29C1 });
        entityMap.put("ohbar", new int[] { 0x29B5 });
        entityMap.put("ohm", new int[] { 0x03A9 });
        entityMap.put("oint", new int[] { 0x222E });
        entityMap.put("olarr", new int[] { 0x21BA });
        entityMap.put("olcir", new int[] { 0x29BE });
        entityMap.put("olcross", new int[] { 0x29BB });
        entityMap.put("oline", new int[] { 0x203E });
        entityMap.put("olt", new int[] { 0x29C0 });
        entityMap.put("omacr", new int[] { 0x014D });
        entityMap.put("omega", new int[] { 0x03C9 });
        entityMap.put("omicron", new int[] { 0x03BF });
        entityMap.put("omid", new int[] { 0x29B6 });
        entityMap.put("ominus", new int[] { 0x2296 });
        entityMap.put("oopf", new int[] { 0x1D560 });
        entityMap.put("opar", new int[] { 0x29B7 });
        entityMap.put("operp", new int[] { 0x29B9 });
        entityMap.put("oplus", new int[] { 0x2295 });
        entityMap.put("or", new int[] { 0x2228 });
        entityMap.put("orarr", new int[] { 0x21BB });
        entityMap.put("ord", new int[] { 0x2A5D });
        entityMap.put("order", new int[] { 0x2134 });
        entityMap.put("orderof", new int[] { 0x2134 });
        // Skipping entity missing semicolon: &ordf = 0x00AA
        entityMap.put("ordf", new int[] { 0x00AA });
        // Skipping entity missing semicolon: &ordm = 0x00BA
        entityMap.put("ordm", new int[] { 0x00BA });
        entityMap.put("origof", new int[] { 0x22B6 });
        entityMap.put("oror", new int[] { 0x2A56 });
        entityMap.put("orslope", new int[] { 0x2A57 });
        entityMap.put("orv", new int[] { 0x2A5B });
        entityMap.put("oscr", new int[] { 0x2134 });
        // Skipping entity missing semicolon: &oslash = 0x00F8
        entityMap.put("oslash", new int[] { 0x00F8 });
        entityMap.put("osol", new int[] { 0x2298 });
        // Skipping entity missing semicolon: &otilde = 0x00F5
        entityMap.put("otilde", new int[] { 0x00F5 });
        entityMap.put("otimes", new int[] { 0x2297 });
        entityMap.put("otimesas", new int[] { 0x2A36 });
        // Skipping entity missing semicolon: &ouml = 0x00F6
        entityMap.put("ouml", new int[] { 0x00F6 });
        entityMap.put("ovbar", new int[] { 0x233D });
        entityMap.put("par", new int[] { 0x2225 });
        // Skipping entity missing semicolon: &para = 0x00B6
        entityMap.put("para", new int[] { 0x00B6 });
        entityMap.put("parallel", new int[] { 0x2225 });
        entityMap.put("parsim", new int[] { 0x2AF3 });
        entityMap.put("parsl", new int[] { 0x2AFD });
        entityMap.put("part", new int[] { 0x2202 });
        entityMap.put("pcy", new int[] { 0x043F });
        entityMap.put("percnt", new int[] { 0x0025 });
        entityMap.put("period", new int[] { 0x002E });
        entityMap.put("permil", new int[] { 0x2030 });
        entityMap.put("perp", new int[] { 0x22A5 });
        entityMap.put("pertenk", new int[] { 0x2031 });
        entityMap.put("pfr", new int[] { 0x1D52D });
        entityMap.put("phi", new int[] { 0x03C6 });
        entityMap.put("phiv", new int[] { 0x03D5 });
        entityMap.put("phmmat", new int[] { 0x2133 });
        entityMap.put("phone", new int[] { 0x260E });
        entityMap.put("pi", new int[] { 0x03C0 });
        entityMap.put("pitchfork", new int[] { 0x22D4 });
        entityMap.put("piv", new int[] { 0x03D6 });
        entityMap.put("planck", new int[] { 0x210F });
        entityMap.put("planckh", new int[] { 0x210E });
        entityMap.put("plankv", new int[] { 0x210F });
        entityMap.put("plus", new int[] { 0x002B });
        entityMap.put("plusacir", new int[] { 0x2A23 });
        entityMap.put("plusb", new int[] { 0x229E });
        entityMap.put("pluscir", new int[] { 0x2A22 });
        entityMap.put("plusdo", new int[] { 0x2214 });
        entityMap.put("plusdu", new int[] { 0x2A25 });
        entityMap.put("pluse", new int[] { 0x2A72 });
        // Skipping entity missing semicolon: &plusmn = 0x00B1
        entityMap.put("plusmn", new int[] { 0x00B1 });
        entityMap.put("plussim", new int[] { 0x2A26 });
        entityMap.put("plustwo", new int[] { 0x2A27 });
        entityMap.put("pm", new int[] { 0x00B1 });
        entityMap.put("pointint", new int[] { 0x2A15 });
        entityMap.put("popf", new int[] { 0x1D561 });
        // Skipping entity missing semicolon: &pound = 0x00A3
        entityMap.put("pound", new int[] { 0x00A3 });
        entityMap.put("pr", new int[] { 0x227A });
        entityMap.put("prE", new int[] { 0x2AB3 });
        entityMap.put("prap", new int[] { 0x2AB7 });
        entityMap.put("prcue", new int[] { 0x227C });
        entityMap.put("pre", new int[] { 0x2AAF });
        entityMap.put("prec", new int[] { 0x227A });
        entityMap.put("precapprox", new int[] { 0x2AB7 });
        entityMap.put("preccurlyeq", new int[] { 0x227C });
        entityMap.put("preceq", new int[] { 0x2AAF });
        entityMap.put("precnapprox", new int[] { 0x2AB9 });
        entityMap.put("precneqq", new int[] { 0x2AB5 });
        entityMap.put("precnsim", new int[] { 0x22E8 });
        entityMap.put("precsim", new int[] { 0x227E });
        entityMap.put("prime", new int[] { 0x2032 });
        entityMap.put("primes", new int[] { 0x2119 });
        entityMap.put("prnE", new int[] { 0x2AB5 });
        entityMap.put("prnap", new int[] { 0x2AB9 });
        entityMap.put("prnsim", new int[] { 0x22E8 });
        entityMap.put("prod", new int[] { 0x220F });
        entityMap.put("profalar", new int[] { 0x232E });
        entityMap.put("profline", new int[] { 0x2312 });
        entityMap.put("profsurf", new int[] { 0x2313 });
        entityMap.put("prop", new int[] { 0x221D });
        entityMap.put("propto", new int[] { 0x221D });
        entityMap.put("prsim", new int[] { 0x227E });
        entityMap.put("prurel", new int[] { 0x22B0 });
        entityMap.put("pscr", new int[] { 0x1D4C5 });
        entityMap.put("psi", new int[] { 0x03C8 });
        entityMap.put("puncsp", new int[] { 0x2008 });
        entityMap.put("qfr", new int[] { 0x1D52E });
        entityMap.put("qint", new int[] { 0x2A0C });
        entityMap.put("qopf", new int[] { 0x1D562 });
        entityMap.put("qprime", new int[] { 0x2057 });
        entityMap.put("qscr", new int[] { 0x1D4C6 });
        entityMap.put("quaternions", new int[] { 0x210D });
        entityMap.put("quatint", new int[] { 0x2A16 });
        entityMap.put("quest", new int[] { 0x003F });
        entityMap.put("questeq", new int[] { 0x225F });
        // Skipping entity missing semicolon: &quot = 0x0022
        entityMap.put("quot", new int[] { 0x0022 });
        entityMap.put("rAarr", new int[] { 0x21DB });
        entityMap.put("rArr", new int[] { 0x21D2 });
        entityMap.put("rAtail", new int[] { 0x291C });
        entityMap.put("rBarr", new int[] { 0x290F });
        entityMap.put("rHar", new int[] { 0x2964 });
        entityMap.put("race", new int[] { 0x223D, 0x0331 });
        entityMap.put("racute", new int[] { 0x0155 });
        entityMap.put("radic", new int[] { 0x221A });
        entityMap.put("raemptyv", new int[] { 0x29B3 });
        entityMap.put("rang", new int[] { 0x27E9 });
        entityMap.put("rangd", new int[] { 0x2992 });
        entityMap.put("range", new int[] { 0x29A5 });
        entityMap.put("rangle", new int[] { 0x27E9 });
        // Skipping entity missing semicolon: &raquo = 0x00BB
        entityMap.put("raquo", new int[] { 0x00BB });
        entityMap.put("rarr", new int[] { 0x2192 });
        entityMap.put("rarrap", new int[] { 0x2975 });
        entityMap.put("rarrb", new int[] { 0x21E5 });
        entityMap.put("rarrbfs", new int[] { 0x2920 });
        entityMap.put("rarrc", new int[] { 0x2933 });
        entityMap.put("rarrfs", new int[] { 0x291E });
        entityMap.put("rarrhk", new int[] { 0x21AA });
        entityMap.put("rarrlp", new int[] { 0x21AC });
        entityMap.put("rarrpl", new int[] { 0x2945 });
        entityMap.put("rarrsim", new int[] { 0x2974 });
        entityMap.put("rarrtl", new int[] { 0x21A3 });
        entityMap.put("rarrw", new int[] { 0x219D });
        entityMap.put("ratail", new int[] { 0x291A });
        entityMap.put("ratio", new int[] { 0x2236 });
        entityMap.put("rationals", new int[] { 0x211A });
        entityMap.put("rbarr", new int[] { 0x290D });
        entityMap.put("rbbrk", new int[] { 0x2773 });
        entityMap.put("rbrace", new int[] { 0x007D });
        entityMap.put("rbrack", new int[] { 0x005D });
        entityMap.put("rbrke", new int[] { 0x298C });
        entityMap.put("rbrksld", new int[] { 0x298E });
        entityMap.put("rbrkslu", new int[] { 0x2990 });
        entityMap.put("rcaron", new int[] { 0x0159 });
        entityMap.put("rcedil", new int[] { 0x0157 });
        entityMap.put("rceil", new int[] { 0x2309 });
        entityMap.put("rcub", new int[] { 0x007D });
        entityMap.put("rcy", new int[] { 0x0440 });
        entityMap.put("rdca", new int[] { 0x2937 });
        entityMap.put("rdldhar", new int[] { 0x2969 });
        entityMap.put("rdquo", new int[] { 0x201D });
        entityMap.put("rdquor", new int[] { 0x201D });
        entityMap.put("rdsh", new int[] { 0x21B3 });
        entityMap.put("real", new int[] { 0x211C });
        entityMap.put("realine", new int[] { 0x211B });
        entityMap.put("realpart", new int[] { 0x211C });
        entityMap.put("reals", new int[] { 0x211D });
        entityMap.put("rect", new int[] { 0x25AD });
        // Skipping entity missing semicolon: &reg = 0x00AE
        entityMap.put("reg", new int[] { 0x00AE });
        entityMap.put("rfisht", new int[] { 0x297D });
        entityMap.put("rfloor", new int[] { 0x230B });
        entityMap.put("rfr", new int[] { 0x1D52F });
        entityMap.put("rhard", new int[] { 0x21C1 });
        entityMap.put("rharu", new int[] { 0x21C0 });
        entityMap.put("rharul", new int[] { 0x296C });
        entityMap.put("rho", new int[] { 0x03C1 });
        entityMap.put("rhov", new int[] { 0x03F1 });
        entityMap.put("rightarrow", new int[] { 0x2192 });
        entityMap.put("rightarrowtail", new int[] { 0x21A3 });
        entityMap.put("rightharpoondown", new int[] { 0x21C1 });
        entityMap.put("rightharpoonup", new int[] { 0x21C0 });
        entityMap.put("rightleftarrows", new int[] { 0x21C4 });
        entityMap.put("rightleftharpoons", new int[] { 0x21CC });
        entityMap.put("rightrightarrows", new int[] { 0x21C9 });
        entityMap.put("rightsquigarrow", new int[] { 0x219D });
        entityMap.put("rightthreetimes", new int[] { 0x22CC });
        entityMap.put("ring", new int[] { 0x02DA });
        entityMap.put("risingdotseq", new int[] { 0x2253 });
        entityMap.put("rlarr", new int[] { 0x21C4 });
        entityMap.put("rlhar", new int[] { 0x21CC });
        entityMap.put("rlm", new int[] { 0x200F });
        entityMap.put("rmoust", new int[] { 0x23B1 });
        entityMap.put("rmoustache", new int[] { 0x23B1 });
        entityMap.put("rnmid", new int[] { 0x2AEE });
        entityMap.put("roang", new int[] { 0x27ED });
        entityMap.put("roarr", new int[] { 0x21FE });
        entityMap.put("robrk", new int[] { 0x27E7 });
        entityMap.put("ropar", new int[] { 0x2986 });
        entityMap.put("ropf", new int[] { 0x1D563 });
        entityMap.put("roplus", new int[] { 0x2A2E });
        entityMap.put("rotimes", new int[] { 0x2A35 });
        entityMap.put("rpar", new int[] { 0x0029 });
        entityMap.put("rpargt", new int[] { 0x2994 });
        entityMap.put("rppolint", new int[] { 0x2A12 });
        entityMap.put("rrarr", new int[] { 0x21C9 });
        entityMap.put("rsaquo", new int[] { 0x203A });
        entityMap.put("rscr", new int[] { 0x1D4C7 });
        entityMap.put("rsh", new int[] { 0x21B1 });
        entityMap.put("rsqb", new int[] { 0x005D });
        entityMap.put("rsquo", new int[] { 0x2019 });
        entityMap.put("rsquor", new int[] { 0x2019 });
        entityMap.put("rthree", new int[] { 0x22CC });
        entityMap.put("rtimes", new int[] { 0x22CA });
        entityMap.put("rtri", new int[] { 0x25B9 });
        entityMap.put("rtrie", new int[] { 0x22B5 });
        entityMap.put("rtrif", new int[] { 0x25B8 });
        entityMap.put("rtriltri", new int[] { 0x29CE });
        entityMap.put("ruluhar", new int[] { 0x2968 });
        entityMap.put("rx", new int[] { 0x211E });
        entityMap.put("sacute", new int[] { 0x015B });
        entityMap.put("sbquo", new int[] { 0x201A });
        entityMap.put("sc", new int[] { 0x227B });
        entityMap.put("scE", new int[] { 0x2AB4 });
        entityMap.put("scap", new int[] { 0x2AB8 });
        entityMap.put("scaron", new int[] { 0x0161 });
        entityMap.put("sccue", new int[] { 0x227D });
        entityMap.put("sce", new int[] { 0x2AB0 });
        entityMap.put("scedil", new int[] { 0x015F });
        entityMap.put("scirc", new int[] { 0x015D });
        entityMap.put("scnE", new int[] { 0x2AB6 });
        entityMap.put("scnap", new int[] { 0x2ABA });
        entityMap.put("scnsim", new int[] { 0x22E9 });
        entityMap.put("scpolint", new int[] { 0x2A13 });
        entityMap.put("scsim", new int[] { 0x227F });
        entityMap.put("scy", new int[] { 0x0441 });
        entityMap.put("sdot", new int[] { 0x22C5 });
        entityMap.put("sdotb", new int[] { 0x22A1 });
        entityMap.put("sdote", new int[] { 0x2A66 });
        entityMap.put("seArr", new int[] { 0x21D8 });
        entityMap.put("searhk", new int[] { 0x2925 });
        entityMap.put("searr", new int[] { 0x2198 });
        entityMap.put("searrow", new int[] { 0x2198 });
        // Skipping entity missing semicolon: &sect = 0x00A7
        entityMap.put("sect", new int[] { 0x00A7 });
        entityMap.put("semi", new int[] { 0x003B });
        entityMap.put("seswar", new int[] { 0x2929 });
        entityMap.put("setminus", new int[] { 0x2216 });
        entityMap.put("setmn", new int[] { 0x2216 });
        entityMap.put("sext", new int[] { 0x2736 });
        entityMap.put("sfr", new int[] { 0x1D530 });
        entityMap.put("sfrown", new int[] { 0x2322 });
        entityMap.put("sharp", new int[] { 0x266F });
        entityMap.put("shchcy", new int[] { 0x0449 });
        entityMap.put("shcy", new int[] { 0x0448 });
        entityMap.put("shortmid", new int[] { 0x2223 });
        entityMap.put("shortparallel", new int[] { 0x2225 });
        // Skipping entity missing semicolon: &shy = 0x00AD
        entityMap.put("shy", new int[] { 0x00AD });
        entityMap.put("sigma", new int[] { 0x03C3 });
        entityMap.put("sigmaf", new int[] { 0x03C2 });
        entityMap.put("sigmav", new int[] { 0x03C2 });
        entityMap.put("sim", new int[] { 0x223C });
        entityMap.put("simdot", new int[] { 0x2A6A });
        entityMap.put("sime", new int[] { 0x2243 });
        entityMap.put("simeq", new int[] { 0x2243 });
        entityMap.put("simg", new int[] { 0x2A9E });
        entityMap.put("simgE", new int[] { 0x2AA0 });
        entityMap.put("siml", new int[] { 0x2A9D });
        entityMap.put("simlE", new int[] { 0x2A9F });
        entityMap.put("simne", new int[] { 0x2246 });
        entityMap.put("simplus", new int[] { 0x2A24 });
        entityMap.put("simrarr", new int[] { 0x2972 });
        entityMap.put("slarr", new int[] { 0x2190 });
        entityMap.put("smallsetminus", new int[] { 0x2216 });
        entityMap.put("smashp", new int[] { 0x2A33 });
        entityMap.put("smeparsl", new int[] { 0x29E4 });
        entityMap.put("smid", new int[] { 0x2223 });
        entityMap.put("smile", new int[] { 0x2323 });
        entityMap.put("smt", new int[] { 0x2AAA });
        entityMap.put("smte", new int[] { 0x2AAC });
        entityMap.put("smtes", new int[] { 0x2AAC, 0xFE00 });
        entityMap.put("softcy", new int[] { 0x044C });
        entityMap.put("sol", new int[] { 0x002F });
        entityMap.put("solb", new int[] { 0x29C4 });
        entityMap.put("solbar", new int[] { 0x233F });
        entityMap.put("sopf", new int[] { 0x1D564 });
        entityMap.put("spades", new int[] { 0x2660 });
        entityMap.put("spadesuit", new int[] { 0x2660 });
        entityMap.put("spar", new int[] { 0x2225 });
        entityMap.put("sqcap", new int[] { 0x2293 });
        entityMap.put("sqcaps", new int[] { 0x2293, 0xFE00 });
        entityMap.put("sqcup", new int[] { 0x2294 });
        entityMap.put("sqcups", new int[] { 0x2294, 0xFE00 });
        entityMap.put("sqsub", new int[] { 0x228F });
        entityMap.put("sqsube", new int[] { 0x2291 });
        entityMap.put("sqsubset", new int[] { 0x228F });
        entityMap.put("sqsubseteq", new int[] { 0x2291 });
        entityMap.put("sqsup", new int[] { 0x2290 });
        entityMap.put("sqsupe", new int[] { 0x2292 });
        entityMap.put("sqsupset", new int[] { 0x2290 });
        entityMap.put("sqsupseteq", new int[] { 0x2292 });
        entityMap.put("squ", new int[] { 0x25A1 });
        entityMap.put("square", new int[] { 0x25A1 });
        entityMap.put("squarf", new int[] { 0x25AA });
        entityMap.put("squf", new int[] { 0x25AA });
        entityMap.put("srarr", new int[] { 0x2192 });
        entityMap.put("sscr", new int[] { 0x1D4C8 });
        entityMap.put("ssetmn", new int[] { 0x2216 });
        entityMap.put("ssmile", new int[] { 0x2323 });
        entityMap.put("sstarf", new int[] { 0x22C6 });
        entityMap.put("star", new int[] { 0x2606 });
        entityMap.put("starf", new int[] { 0x2605 });
        entityMap.put("straightepsilon", new int[] { 0x03F5 });
        entityMap.put("straightphi", new int[] { 0x03D5 });
        entityMap.put("strns", new int[] { 0x00AF });
        entityMap.put("sub", new int[] { 0x2282 });
        entityMap.put("subE", new int[] { 0x2AC5 });
        entityMap.put("subdot", new int[] { 0x2ABD });
        entityMap.put("sube", new int[] { 0x2286 });
        entityMap.put("subedot", new int[] { 0x2AC3 });
        entityMap.put("submult", new int[] { 0x2AC1 });
        entityMap.put("subnE", new int[] { 0x2ACB });
        entityMap.put("subne", new int[] { 0x228A });
        entityMap.put("subplus", new int[] { 0x2ABF });
        entityMap.put("subrarr", new int[] { 0x2979 });
        entityMap.put("subset", new int[] { 0x2282 });
        entityMap.put("subseteq", new int[] { 0x2286 });
        entityMap.put("subseteqq", new int[] { 0x2AC5 });
        entityMap.put("subsetneq", new int[] { 0x228A });
        entityMap.put("subsetneqq", new int[] { 0x2ACB });
        entityMap.put("subsim", new int[] { 0x2AC7 });
        entityMap.put("subsub", new int[] { 0x2AD5 });
        entityMap.put("subsup", new int[] { 0x2AD3 });
        entityMap.put("succ", new int[] { 0x227B });
        entityMap.put("succapprox", new int[] { 0x2AB8 });
        entityMap.put("succcurlyeq", new int[] { 0x227D });
        entityMap.put("succeq", new int[] { 0x2AB0 });
        entityMap.put("succnapprox", new int[] { 0x2ABA });
        entityMap.put("succneqq", new int[] { 0x2AB6 });
        entityMap.put("succnsim", new int[] { 0x22E9 });
        entityMap.put("succsim", new int[] { 0x227F });
        entityMap.put("sum", new int[] { 0x2211 });
        entityMap.put("sung", new int[] { 0x266A });
        // Skipping entity missing semicolon: &sup1 = 0x00B9
        entityMap.put("sup1", new int[] { 0x00B9 });
        // Skipping entity missing semicolon: &sup2 = 0x00B2
        entityMap.put("sup2", new int[] { 0x00B2 });
        // Skipping entity missing semicolon: &sup3 = 0x00B3
        entityMap.put("sup3", new int[] { 0x00B3 });
        entityMap.put("sup", new int[] { 0x2283 });
        entityMap.put("supE", new int[] { 0x2AC6 });
        entityMap.put("supdot", new int[] { 0x2ABE });
        entityMap.put("supdsub", new int[] { 0x2AD8 });
        entityMap.put("supe", new int[] { 0x2287 });
        entityMap.put("supedot", new int[] { 0x2AC4 });
        entityMap.put("suphsol", new int[] { 0x27C9 });
        entityMap.put("suphsub", new int[] { 0x2AD7 });
        entityMap.put("suplarr", new int[] { 0x297B });
        entityMap.put("supmult", new int[] { 0x2AC2 });
        entityMap.put("supnE", new int[] { 0x2ACC });
        entityMap.put("supne", new int[] { 0x228B });
        entityMap.put("supplus", new int[] { 0x2AC0 });
        entityMap.put("supset", new int[] { 0x2283 });
        entityMap.put("supseteq", new int[] { 0x2287 });
        entityMap.put("supseteqq", new int[] { 0x2AC6 });
        entityMap.put("supsetneq", new int[] { 0x228B });
        entityMap.put("supsetneqq", new int[] { 0x2ACC });
        entityMap.put("supsim", new int[] { 0x2AC8 });
        entityMap.put("supsub", new int[] { 0x2AD4 });
        entityMap.put("supsup", new int[] { 0x2AD6 });
        entityMap.put("swArr", new int[] { 0x21D9 });
        entityMap.put("swarhk", new int[] { 0x2926 });
        entityMap.put("swarr", new int[] { 0x2199 });
        entityMap.put("swarrow", new int[] { 0x2199 });
        entityMap.put("swnwar", new int[] { 0x292A });
        // Skipping entity missing semicolon: &szlig = 0x00DF
        entityMap.put("szlig", new int[] { 0x00DF });
        entityMap.put("target", new int[] { 0x2316 });
        entityMap.put("tau", new int[] { 0x03C4 });
        entityMap.put("tbrk", new int[] { 0x23B4 });
        entityMap.put("tcaron", new int[] { 0x0165 });
        entityMap.put("tcedil", new int[] { 0x0163 });
        entityMap.put("tcy", new int[] { 0x0442 });
        entityMap.put("tdot", new int[] { 0x20DB });
        entityMap.put("telrec", new int[] { 0x2315 });
        entityMap.put("tfr", new int[] { 0x1D531 });
        entityMap.put("there4", new int[] { 0x2234 });
        entityMap.put("therefore", new int[] { 0x2234 });
        entityMap.put("theta", new int[] { 0x03B8 });
        entityMap.put("thetasym", new int[] { 0x03D1 });
        entityMap.put("thetav", new int[] { 0x03D1 });
        entityMap.put("thickapprox", new int[] { 0x2248 });
        entityMap.put("thicksim", new int[] { 0x223C });
        entityMap.put("thinsp", new int[] { 0x2009 });
        entityMap.put("thkap", new int[] { 0x2248 });
        entityMap.put("thksim", new int[] { 0x223C });
        // Skipping entity missing semicolon: &thorn = 0x00FE
        entityMap.put("thorn", new int[] { 0x00FE });
        entityMap.put("tilde", new int[] { 0x02DC });
        // Skipping entity missing semicolon: &times = 0x00D7
        entityMap.put("times", new int[] { 0x00D7 });
        entityMap.put("timesb", new int[] { 0x22A0 });
        entityMap.put("timesbar", new int[] { 0x2A31 });
        entityMap.put("timesd", new int[] { 0x2A30 });
        entityMap.put("tint", new int[] { 0x222D });
        entityMap.put("toea", new int[] { 0x2928 });
        entityMap.put("top", new int[] { 0x22A4 });
        entityMap.put("topbot", new int[] { 0x2336 });
        entityMap.put("topcir", new int[] { 0x2AF1 });
        entityMap.put("topf", new int[] { 0x1D565 });
        entityMap.put("topfork", new int[] { 0x2ADA });
        entityMap.put("tosa", new int[] { 0x2929 });
        entityMap.put("tprime", new int[] { 0x2034 });
        entityMap.put("trade", new int[] { 0x2122 });
        entityMap.put("triangle", new int[] { 0x25B5 });
        entityMap.put("triangledown", new int[] { 0x25BF });
        entityMap.put("triangleleft", new int[] { 0x25C3 });
        entityMap.put("trianglelefteq", new int[] { 0x22B4 });
        entityMap.put("triangleq", new int[] { 0x225C });
        entityMap.put("triangleright", new int[] { 0x25B9 });
        entityMap.put("trianglerighteq", new int[] { 0x22B5 });
        entityMap.put("tridot", new int[] { 0x25EC });
        entityMap.put("trie", new int[] { 0x225C });
        entityMap.put("triminus", new int[] { 0x2A3A });
        entityMap.put("triplus", new int[] { 0x2A39 });
        entityMap.put("trisb", new int[] { 0x29CD });
        entityMap.put("tritime", new int[] { 0x2A3B });
        entityMap.put("trpezium", new int[] { 0x23E2 });
        entityMap.put("tscr", new int[] { 0x1D4C9 });
        entityMap.put("tscy", new int[] { 0x0446 });
        entityMap.put("tshcy", new int[] { 0x045B });
        entityMap.put("tstrok", new int[] { 0x0167 });
        entityMap.put("twixt", new int[] { 0x226C });
        entityMap.put("twoheadleftarrow", new int[] { 0x219E });
        entityMap.put("twoheadrightarrow", new int[] { 0x21A0 });
        entityMap.put("uArr", new int[] { 0x21D1 });
        entityMap.put("uHar", new int[] { 0x2963 });
        // Skipping entity missing semicolon: &uacute = 0x00FA
        entityMap.put("uacute", new int[] { 0x00FA });
        entityMap.put("uarr", new int[] { 0x2191 });
        entityMap.put("ubrcy", new int[] { 0x045E });
        entityMap.put("ubreve", new int[] { 0x016D });
        // Skipping entity missing semicolon: &ucirc = 0x00FB
        entityMap.put("ucirc", new int[] { 0x00FB });
        entityMap.put("ucy", new int[] { 0x0443 });
        entityMap.put("udarr", new int[] { 0x21C5 });
        entityMap.put("udblac", new int[] { 0x0171 });
        entityMap.put("udhar", new int[] { 0x296E });
        entityMap.put("ufisht", new int[] { 0x297E });
        entityMap.put("ufr", new int[] { 0x1D532 });
        // Skipping entity missing semicolon: &ugrave = 0x00F9
        entityMap.put("ugrave", new int[] { 0x00F9 });
        entityMap.put("uharl", new int[] { 0x21BF });
        entityMap.put("uharr", new int[] { 0x21BE });
        entityMap.put("uhblk", new int[] { 0x2580 });
        entityMap.put("ulcorn", new int[] { 0x231C });
        entityMap.put("ulcorner", new int[] { 0x231C });
        entityMap.put("ulcrop", new int[] { 0x230F });
        entityMap.put("ultri", new int[] { 0x25F8 });
        entityMap.put("umacr", new int[] { 0x016B });
        // Skipping entity missing semicolon: &uml = 0x00A8
        entityMap.put("uml", new int[] { 0x00A8 });
        entityMap.put("uogon", new int[] { 0x0173 });
        entityMap.put("uopf", new int[] { 0x1D566 });
        entityMap.put("uparrow", new int[] { 0x2191 });
        entityMap.put("updownarrow", new int[] { 0x2195 });
        entityMap.put("upharpoonleft", new int[] { 0x21BF });
        entityMap.put("upharpoonright", new int[] { 0x21BE });
        entityMap.put("uplus", new int[] { 0x228E });
        entityMap.put("upsi", new int[] { 0x03C5 });
        entityMap.put("upsih", new int[] { 0x03D2 });
        entityMap.put("upsilon", new int[] { 0x03C5 });
        entityMap.put("upuparrows", new int[] { 0x21C8 });
        entityMap.put("urcorn", new int[] { 0x231D });
        entityMap.put("urcorner", new int[] { 0x231D });
        entityMap.put("urcrop", new int[] { 0x230E });
        entityMap.put("uring", new int[] { 0x016F });
        entityMap.put("urtri", new int[] { 0x25F9 });
        entityMap.put("uscr", new int[] { 0x1D4CA });
        entityMap.put("utdot", new int[] { 0x22F0 });
        entityMap.put("utilde", new int[] { 0x0169 });
        entityMap.put("utri", new int[] { 0x25B5 });
        entityMap.put("utrif", new int[] { 0x25B4 });
        entityMap.put("uuarr", new int[] { 0x21C8 });
        // Skipping entity missing semicolon: &uuml = 0x00FC
        entityMap.put("uuml", new int[] { 0x00FC });
        entityMap.put("uwangle", new int[] { 0x29A7 });
        entityMap.put("vArr", new int[] { 0x21D5 });
        entityMap.put("vBar", new int[] { 0x2AE8 });
        entityMap.put("vBarv", new int[] { 0x2AE9 });
        entityMap.put("vDash", new int[] { 0x22A8 });
        entityMap.put("vangrt", new int[] { 0x299C });
        entityMap.put("varepsilon", new int[] { 0x03F5 });
        entityMap.put("varkappa", new int[] { 0x03F0 });
        entityMap.put("varnothing", new int[] { 0x2205 });
        entityMap.put("varphi", new int[] { 0x03D5 });
        entityMap.put("varpi", new int[] { 0x03D6 });
        entityMap.put("varpropto", new int[] { 0x221D });
        entityMap.put("varr", new int[] { 0x2195 });
        entityMap.put("varrho", new int[] { 0x03F1 });
        entityMap.put("varsigma", new int[] { 0x03C2 });
        entityMap.put("varsubsetneq", new int[] { 0x228A, 0xFE00 });
        entityMap.put("varsubsetneqq", new int[] { 0x2ACB, 0xFE00 });
        entityMap.put("varsupsetneq", new int[] { 0x228B, 0xFE00 });
        entityMap.put("varsupsetneqq", new int[] { 0x2ACC, 0xFE00 });
        entityMap.put("vartheta", new int[] { 0x03D1 });
        entityMap.put("vartriangleleft", new int[] { 0x22B2 });
        entityMap.put("vartriangleright", new int[] { 0x22B3 });
        entityMap.put("vcy", new int[] { 0x0432 });
        entityMap.put("vdash", new int[] { 0x22A2 });
        entityMap.put("vee", new int[] { 0x2228 });
        entityMap.put("veebar", new int[] { 0x22BB });
        entityMap.put("veeeq", new int[] { 0x225A });
        entityMap.put("vellip", new int[] { 0x22EE });
        entityMap.put("verbar", new int[] { 0x007C });
        entityMap.put("vert", new int[] { 0x007C });
        entityMap.put("vfr", new int[] { 0x1D533 });
        entityMap.put("vltri", new int[] { 0x22B2 });
        entityMap.put("vnsub", new int[] { 0x2282, 0x20D2 });
        entityMap.put("vnsup", new int[] { 0x2283, 0x20D2 });
        entityMap.put("vopf", new int[] { 0x1D567 });
        entityMap.put("vprop", new int[] { 0x221D });
        entityMap.put("vrtri", new int[] { 0x22B3 });
        entityMap.put("vscr", new int[] { 0x1D4CB });
        entityMap.put("vsubnE", new int[] { 0x2ACB, 0xFE00 });
        entityMap.put("vsubne", new int[] { 0x228A, 0xFE00 });
        entityMap.put("vsupnE", new int[] { 0x2ACC, 0xFE00 });
        entityMap.put("vsupne", new int[] { 0x228B, 0xFE00 });
        entityMap.put("vzigzag", new int[] { 0x299A });
        entityMap.put("wcirc", new int[] { 0x0175 });
        entityMap.put("wedbar", new int[] { 0x2A5F });
        entityMap.put("wedge", new int[] { 0x2227 });
        entityMap.put("wedgeq", new int[] { 0x2259 });
        entityMap.put("weierp", new int[] { 0x2118 });
        entityMap.put("wfr", new int[] { 0x1D534 });
        entityMap.put("wopf", new int[] { 0x1D568 });
        entityMap.put("wp", new int[] { 0x2118 });
        entityMap.put("wr", new int[] { 0x2240 });
        entityMap.put("wreath", new int[] { 0x2240 });
        entityMap.put("wscr", new int[] { 0x1D4CC });
        entityMap.put("xcap", new int[] { 0x22C2 });
        entityMap.put("xcirc", new int[] { 0x25EF });
        entityMap.put("xcup", new int[] { 0x22C3 });
        entityMap.put("xdtri", new int[] { 0x25BD });
        entityMap.put("xfr", new int[] { 0x1D535 });
        entityMap.put("xhArr", new int[] { 0x27FA });
        entityMap.put("xharr", new int[] { 0x27F7 });
        entityMap.put("xi", new int[] { 0x03BE });
        entityMap.put("xlArr", new int[] { 0x27F8 });
        entityMap.put("xlarr", new int[] { 0x27F5 });
        entityMap.put("xmap", new int[] { 0x27FC });
        entityMap.put("xnis", new int[] { 0x22FB });
        entityMap.put("xodot", new int[] { 0x2A00 });
        entityMap.put("xopf", new int[] { 0x1D569 });
        entityMap.put("xoplus", new int[] { 0x2A01 });
        entityMap.put("xotime", new int[] { 0x2A02 });
        entityMap.put("xrArr", new int[] { 0x27F9 });
        entityMap.put("xrarr", new int[] { 0x27F6 });
        entityMap.put("xscr", new int[] { 0x1D4CD });
        entityMap.put("xsqcup", new int[] { 0x2A06 });
        entityMap.put("xuplus", new int[] { 0x2A04 });
        entityMap.put("xutri", new int[] { 0x25B3 });
        entityMap.put("xvee", new int[] { 0x22C1 });
        entityMap.put("xwedge", new int[] { 0x22C0 });
        // Skipping entity missing semicolon: &yacute = 0x00FD
        entityMap.put("yacute", new int[] { 0x00FD });
        entityMap.put("yacy", new int[] { 0x044F });
        entityMap.put("ycirc", new int[] { 0x0177 });
        entityMap.put("ycy", new int[] { 0x044B });
        // Skipping entity missing semicolon: &yen = 0x00A5
        entityMap.put("yen", new int[] { 0x00A5 });
        entityMap.put("yfr", new int[] { 0x1D536 });
        entityMap.put("yicy", new int[] { 0x0457 });
        entityMap.put("yopf", new int[] { 0x1D56A });
        entityMap.put("yscr", new int[] { 0x1D4CE });
        entityMap.put("yucy", new int[] { 0x044E });
        // Skipping entity missing semicolon: &yuml = 0x00FF
        entityMap.put("yuml", new int[] { 0x00FF });
        entityMap.put("zacute", new int[] { 0x017A });
        entityMap.put("zcaron", new int[] { 0x017E });
        entityMap.put("zcy", new int[] { 0x0437 });
        entityMap.put("zdot", new int[] { 0x017C });
        entityMap.put("zeetrf", new int[] { 0x2128 });
        entityMap.put("zeta", new int[] { 0x03B6 });
        entityMap.put("zfr", new int[] { 0x1D537 });
        entityMap.put("zhcy", new int[] { 0x0436 });
        entityMap.put("zigrarr", new int[] { 0x21DD });
        entityMap.put("zopf", new int[] { 0x1D56B });
        entityMap.put("zscr", new int[] { 0x1D4CF });
        entityMap.put("zwj", new int[] { 0x200D });
        entityMap.put("zwnj", new int[] { 0x200C });
        
        int maxLength = 0;
        for (final String key : entityMap.keySet()) {
            maxLength = Math.max(maxLength, key.length());
        }
        longestEntityName = maxLength;
    }
    
    private CharacterReferenceData() {
    }
    
//    public static void main(final String[] args) throws IOException {
//        final InputStream in = CharacterReferenceData.class
//                .getResourceAsStream("entities.json");
//        final Reader reader = new InputStreamReader(in);
//        final StringBuilder builder = new StringBuilder();
//        int ch = reader.read();
//        while (ch != -1) {
//            builder.append((char) ch);
//            ch = reader.read();
//        }
//        final JSONObject json = new JSONObject(builder.toString());
//        @SuppressWarnings("unchecked")
//        final Set<String> keySet = json.keySet();
//        for (final String key : new TreeSet<>(keySet)) {
//            final JSONObject value = json.getJSONObject(key);
//            final JSONArray codepoints = value.getJSONArray("codepoints");
//
//            String truncatedKey = key;
//            if (truncatedKey.startsWith("&")) {
//                truncatedKey = truncatedKey.substring(1, truncatedKey.length());
//            }
//            if (truncatedKey.endsWith(";")) {
//                truncatedKey = truncatedKey.substring(0,
//                        truncatedKey.length() - 1);
//            } else {
//                System.out.print("// Skipping entity missing semicolon: ");
//                System.out.print(key);
//                System.out.print(" = ");
//                for (int i = 0; i < codepoints.length(); i++) {
//                    if (i != 0) {
//                        System.out.print(", ");
//                    }
//                    System.out.print(String.format("0x%04X",
//                            codepoints.getInt(i)));
//                }
//                System.out.println();
//                continue;
//            }
//
//            System.out.print("entityMap.put(\"");
//            System.out.print(truncatedKey);
//            System.out.print("\", new int[] {");
//            for (int i = 0; i < codepoints.length(); i++) {
//                if (i != 0) {
//                    System.out.print(", ");
//                }
//                System.out.print(String.format("0x%04X", codepoints.getInt(i)));
//            }
//            System.out.println("});");
//        }
//    }

}
