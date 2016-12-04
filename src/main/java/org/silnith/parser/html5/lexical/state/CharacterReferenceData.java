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
     * <div id="named-character-references-table">
     *   <table>
     *     <thead>
     *       <tr><th> Name </th> <th> Character(s) </th> <th> Glyph </th> </tr>
     *     </thead>
     *     <tbody>
     *       <tr id="entity-Aacute"><td> <code title="">Aacute;</code> </td> <td> U+000C1 </td> <td> <span class="glyph" title="">Á</span> </td> </tr>
     *       <tr class="impl" id="entity-Aacute-legacy"><td> <code title="">Aacute</code> </td> <td> U+000C1 </td> <td> <span title="">Á</span> </td> </tr>
     *       <tr id="entity-aacute"><td> <code title="">aacute;</code> </td> <td> U+000E1 </td> <td> <span class="glyph" title="">á</span> </td> </tr>
     *       <tr class="impl" id="entity-aacute-legacy"><td> <code title="">aacute</code> </td> <td> U+000E1 </td> <td> <span title="">á</span> </td> </tr>
     *       <tr id="entity-Abreve"><td> <code title="">Abreve;</code> </td> <td> U+00102 </td> <td> <span class="glyph" title="">Ă</span> </td> </tr>
     *       <tr id="entity-abreve"><td> <code title="">abreve;</code> </td> <td> U+00103 </td> <td> <span class="glyph" title="">ă</span> </td> </tr>
     *       <tr id="entity-ac"><td> <code title="">ac;</code> </td> <td> U+0223E </td> <td> <span class="glyph" title="">∾</span> </td> </tr>
     *       <tr id="entity-acd"><td> <code title="">acd;</code> </td> <td> U+0223F </td> <td> <span class="glyph" title="">∿</span> </td> </tr>
     *       <tr id="entity-acE"><td> <code title="">acE;</code> </td> <td> U+0223E U+00333 </td> <td> <span class="glyph compound" title="">∾̳</span> </td> </tr>
     *       <tr id="entity-Acirc"><td> <code title="">Acirc;</code> </td> <td> U+000C2 </td> <td> <span class="glyph" title="">Â</span> </td> </tr>
     *       <tr class="impl" id="entity-Acirc-legacy"><td> <code title="">Acirc</code> </td> <td> U+000C2 </td> <td> <span title="">Â</span> </td> </tr>
     *       <tr id="entity-acirc"><td> <code title="">acirc;</code> </td> <td> U+000E2 </td> <td> <span class="glyph" title="">â</span> </td> </tr>
     *       <tr class="impl" id="entity-acirc-legacy"><td> <code title="">acirc</code> </td> <td> U+000E2 </td> <td> <span title="">â</span> </td> </tr>
     *       <tr id="entity-acute"><td> <code title="">acute;</code> </td> <td> U+000B4 </td> <td> <span class="glyph" title="">´</span> </td> </tr>
     *       <tr class="impl" id="entity-acute-legacy"><td> <code title="">acute</code> </td> <td> U+000B4 </td> <td> <span title="">´</span> </td> </tr>
     *       <tr id="entity-Acy"><td> <code title="">Acy;</code> </td> <td> U+00410 </td> <td> <span class="glyph" title="">А</span> </td> </tr>
     *       <tr id="entity-acy"><td> <code title="">acy;</code> </td> <td> U+00430 </td> <td> <span class="glyph" title="">а</span> </td> </tr>
     *       <tr id="entity-AElig"><td> <code title="">AElig;</code> </td> <td> U+000C6 </td> <td> <span class="glyph" title="">Æ</span> </td> </tr>
     *       <tr class="impl" id="entity-AElig-legacy"><td> <code title="">AElig</code> </td> <td> U+000C6 </td> <td> <span title="">Æ</span> </td> </tr>
     *       <tr id="entity-aelig"><td> <code title="">aelig;</code> </td> <td> U+000E6 </td> <td> <span class="glyph" title="">æ</span> </td> </tr>
     *       <tr class="impl" id="entity-aelig-legacy"><td> <code title="">aelig</code> </td> <td> U+000E6 </td> <td> <span title="">æ</span> </td> </tr>
     *       <tr id="entity-af"><td> <code title="">af;</code> </td> <td> U+02061 </td> <td> <span class="glyph" title="">⁡</span> </td> </tr>
     *       <tr id="entity-Afr"><td> <code title="">Afr;</code> </td> <td> U+1D504 </td> <td> <span class="glyph" title="">𝔄</span> </td> </tr>
     *       <tr id="entity-afr"><td> <code title="">afr;</code> </td> <td> U+1D51E </td> <td> <span class="glyph" title="">𝔞</span> </td> </tr>
     *       <tr id="entity-Agrave"><td> <code title="">Agrave;</code> </td> <td> U+000C0 </td> <td> <span class="glyph" title="">À</span> </td> </tr>
     *       <tr class="impl" id="entity-Agrave-legacy"><td> <code title="">Agrave</code> </td> <td> U+000C0 </td> <td> <span title="">À</span> </td> </tr>
     *       <tr id="entity-agrave"><td> <code title="">agrave;</code> </td> <td> U+000E0 </td> <td> <span class="glyph" title="">à</span> </td> </tr>
     *       <tr class="impl" id="entity-agrave-legacy"><td> <code title="">agrave</code> </td> <td> U+000E0 </td> <td> <span title="">à</span> </td> </tr>
     *       <tr id="entity-alefsym"><td> <code title="">alefsym;</code> </td> <td> U+02135 </td> <td> <span class="glyph" title="">ℵ</span> </td> </tr>
     *       <tr id="entity-aleph"><td> <code title="">aleph;</code> </td> <td> U+02135 </td> <td> <span class="glyph" title="">ℵ</span> </td> </tr>
     *       <tr id="entity-Alpha"><td> <code title="">Alpha;</code> </td> <td> U+00391 </td> <td> <span class="glyph" title="">Α</span> </td> </tr>
     *       <tr id="entity-alpha"><td> <code title="">alpha;</code> </td> <td> U+003B1 </td> <td> <span class="glyph" title="">α</span> </td> </tr>
     *       <tr id="entity-Amacr"><td> <code title="">Amacr;</code> </td> <td> U+00100 </td> <td> <span class="glyph" title="">Ā</span> </td> </tr>
     *       <tr id="entity-amacr"><td> <code title="">amacr;</code> </td> <td> U+00101 </td> <td> <span class="glyph" title="">ā</span> </td> </tr>
     *       <tr id="entity-amalg"><td> <code title="">amalg;</code> </td> <td> U+02A3F </td> <td> <span class="glyph" title="">⨿</span> </td> </tr>
     *       <tr id="entity-AMP"><td> <code title="">AMP;</code> </td> <td> U+00026 </td> <td> <span class="glyph" title="">&amp;</span> </td> </tr>
     *       <tr class="impl" id="entity-AMP-legacy"><td> <code title="">AMP</code> </td> <td> U+00026 </td> <td> <span title="">&amp;</span> </td> </tr>
     *       <tr id="entity-amp"><td> <code title="">amp;</code> </td> <td> U+00026 </td> <td> <span class="glyph" title="">&amp;</span> </td> </tr>
     *       <tr class="impl" id="entity-amp-legacy"><td> <code title="">amp</code> </td> <td> U+00026 </td> <td> <span title="">&amp;</span> </td> </tr>
     *       <tr id="entity-And"><td> <code title="">And;</code> </td> <td> U+02A53 </td> <td> <span class="glyph" title="">⩓</span> </td> </tr>
     *       <tr id="entity-and"><td> <code title="">and;</code> </td> <td> U+02227 </td> <td> <span class="glyph" title="">∧</span> </td> </tr>
     *       <tr id="entity-andand"><td> <code title="">andand;</code> </td> <td> U+02A55 </td> <td> <span class="glyph" title="">⩕</span> </td> </tr>
     *       <tr id="entity-andd"><td> <code title="">andd;</code> </td> <td> U+02A5C </td> <td> <span class="glyph" title="">⩜</span> </td> </tr>
     *       <tr id="entity-andslope"><td> <code title="">andslope;</code> </td> <td> U+02A58 </td> <td> <span class="glyph" title="">⩘</span> </td> </tr>
     *       <tr id="entity-andv"><td> <code title="">andv;</code> </td> <td> U+02A5A </td> <td> <span class="glyph" title="">⩚</span> </td> </tr>
     *       <tr id="entity-ang"><td> <code title="">ang;</code> </td> <td> U+02220 </td> <td> <span class="glyph" title="">∠</span> </td> </tr>
     *       <tr id="entity-ange"><td> <code title="">ange;</code> </td> <td> U+029A4 </td> <td> <span class="glyph" title="">⦤</span> </td> </tr>
     *       <tr id="entity-angle"><td> <code title="">angle;</code> </td> <td> U+02220 </td> <td> <span class="glyph" title="">∠</span> </td> </tr>
     *       <tr id="entity-angmsd"><td> <code title="">angmsd;</code> </td> <td> U+02221 </td> <td> <span class="glyph" title="">∡</span> </td> </tr>
     *       <tr id="entity-angmsdaa"><td> <code title="">angmsdaa;</code> </td> <td> U+029A8 </td> <td> <span class="glyph" title="">⦨</span> </td> </tr>
     *       <tr id="entity-angmsdab"><td> <code title="">angmsdab;</code> </td> <td> U+029A9 </td> <td> <span class="glyph" title="">⦩</span> </td> </tr>
     *       <tr id="entity-angmsdac"><td> <code title="">angmsdac;</code> </td> <td> U+029AA </td> <td> <span class="glyph" title="">⦪</span> </td> </tr>
     *       <tr id="entity-angmsdad"><td> <code title="">angmsdad;</code> </td> <td> U+029AB </td> <td> <span class="glyph" title="">⦫</span> </td> </tr>
     *       <tr id="entity-angmsdae"><td> <code title="">angmsdae;</code> </td> <td> U+029AC </td> <td> <span class="glyph" title="">⦬</span> </td> </tr>
     *       <tr id="entity-angmsdaf"><td> <code title="">angmsdaf;</code> </td> <td> U+029AD </td> <td> <span class="glyph" title="">⦭</span> </td> </tr>
     *       <tr id="entity-angmsdag"><td> <code title="">angmsdag;</code> </td> <td> U+029AE </td> <td> <span class="glyph" title="">⦮</span> </td> </tr>
     *       <tr id="entity-angmsdah"><td> <code title="">angmsdah;</code> </td> <td> U+029AF </td> <td> <span class="glyph" title="">⦯</span> </td> </tr>
     *       <tr id="entity-angrt"><td> <code title="">angrt;</code> </td> <td> U+0221F </td> <td> <span class="glyph" title="">∟</span> </td> </tr>
     *       <tr id="entity-angrtvb"><td> <code title="">angrtvb;</code> </td> <td> U+022BE </td> <td> <span class="glyph" title="">⊾</span> </td> </tr>
     *       <tr id="entity-angrtvbd"><td> <code title="">angrtvbd;</code> </td> <td> U+0299D </td> <td> <span class="glyph" title="">⦝</span> </td> </tr>
     *       <tr id="entity-angsph"><td> <code title="">angsph;</code> </td> <td> U+02222 </td> <td> <span class="glyph" title="">∢</span> </td> </tr>
     *       <tr id="entity-angst"><td> <code title="">angst;</code> </td> <td> U+000C5 </td> <td> <span class="glyph" title="">Å</span> </td> </tr>
     *       <tr id="entity-angzarr"><td> <code title="">angzarr;</code> </td> <td> U+0237C </td> <td> <span class="glyph" title="">⍼</span> </td> </tr>
     *       <tr id="entity-Aogon"><td> <code title="">Aogon;</code> </td> <td> U+00104 </td> <td> <span class="glyph" title="">Ą</span> </td> </tr>
     *       <tr id="entity-aogon"><td> <code title="">aogon;</code> </td> <td> U+00105 </td> <td> <span class="glyph" title="">ą</span> </td> </tr>
     *       <tr id="entity-Aopf"><td> <code title="">Aopf;</code> </td> <td> U+1D538 </td> <td> <span class="glyph" title="">𝔸</span> </td> </tr>
     *       <tr id="entity-aopf"><td> <code title="">aopf;</code> </td> <td> U+1D552 </td> <td> <span class="glyph" title="">𝕒</span> </td> </tr>
     *       <tr id="entity-ap"><td> <code title="">ap;</code> </td> <td> U+02248 </td> <td> <span class="glyph" title="">≈</span> </td> </tr>
     *       <tr id="entity-apacir"><td> <code title="">apacir;</code> </td> <td> U+02A6F </td> <td> <span class="glyph" title="">⩯</span> </td> </tr>
     *       <tr id="entity-apE"><td> <code title="">apE;</code> </td> <td> U+02A70 </td> <td> <span class="glyph" title="">⩰</span> </td> </tr>
     *       <tr id="entity-ape"><td> <code title="">ape;</code> </td> <td> U+0224A </td> <td> <span class="glyph" title="">≊</span> </td> </tr>
     *       <tr id="entity-apid"><td> <code title="">apid;</code> </td> <td> U+0224B </td> <td> <span class="glyph" title="">≋</span> </td> </tr>
     *       <tr id="entity-apos"><td> <code title="">apos;</code> </td> <td> U+00027 </td> <td> <span class="glyph" title="">'</span> </td> </tr>
     *       <tr id="entity-ApplyFunction"><td> <code title="">ApplyFunction;</code> </td> <td> U+02061 </td> <td> <span class="glyph" title="">⁡</span> </td> </tr>
     *       <tr id="entity-approx"><td> <code title="">approx;</code> </td> <td> U+02248 </td> <td> <span class="glyph" title="">≈</span> </td> </tr>
     *       <tr id="entity-approxeq"><td> <code title="">approxeq;</code> </td> <td> U+0224A </td> <td> <span class="glyph" title="">≊</span> </td> </tr>
     *       <tr id="entity-Aring"><td> <code title="">Aring;</code> </td> <td> U+000C5 </td> <td> <span class="glyph" title="">Å</span> </td> </tr>
     *       <tr class="impl" id="entity-Aring-legacy"><td> <code title="">Aring</code> </td> <td> U+000C5 </td> <td> <span title="">Å</span> </td> </tr>
     *       <tr id="entity-aring"><td> <code title="">aring;</code> </td> <td> U+000E5 </td> <td> <span class="glyph" title="">å</span> </td> </tr>
     *       <tr class="impl" id="entity-aring-legacy"><td> <code title="">aring</code> </td> <td> U+000E5 </td> <td> <span title="">å</span> </td> </tr>
     *       <tr id="entity-Ascr"><td> <code title="">Ascr;</code> </td> <td> U+1D49C </td> <td> <span class="glyph" title="">𝒜</span> </td> </tr>
     *       <tr id="entity-ascr"><td> <code title="">ascr;</code> </td> <td> U+1D4B6 </td> <td> <span class="glyph" title="">𝒶</span> </td> </tr>
     *       <tr id="entity-Assign"><td> <code title="">Assign;</code> </td> <td> U+02254 </td> <td> <span class="glyph" title="">≔</span> </td> </tr>
     *       <tr id="entity-ast"><td> <code title="">ast;</code> </td> <td> U+0002A </td> <td> <span class="glyph" title="">*</span> </td> </tr>
     *       <tr id="entity-asymp"><td> <code title="">asymp;</code> </td> <td> U+02248 </td> <td> <span class="glyph" title="">≈</span> </td> </tr>
     *       <tr id="entity-asympeq"><td> <code title="">asympeq;</code> </td> <td> U+0224D </td> <td> <span class="glyph" title="">≍</span> </td> </tr>
     *       <tr id="entity-Atilde"><td> <code title="">Atilde;</code> </td> <td> U+000C3 </td> <td> <span class="glyph" title="">Ã</span> </td> </tr>
     *       <tr class="impl" id="entity-Atilde-legacy"><td> <code title="">Atilde</code> </td> <td> U+000C3 </td> <td> <span title="">Ã</span> </td> </tr>
     *       <tr id="entity-atilde"><td> <code title="">atilde;</code> </td> <td> U+000E3 </td> <td> <span class="glyph" title="">ã</span> </td> </tr>
     *       <tr class="impl" id="entity-atilde-legacy"><td> <code title="">atilde</code> </td> <td> U+000E3 </td> <td> <span title="">ã</span> </td> </tr>
     *       <tr id="entity-Auml"><td> <code title="">Auml;</code> </td> <td> U+000C4 </td> <td> <span class="glyph" title="">Ä</span> </td> </tr>
     *       <tr class="impl" id="entity-Auml-legacy"><td> <code title="">Auml</code> </td> <td> U+000C4 </td> <td> <span title="">Ä</span> </td> </tr>
     *       <tr id="entity-auml"><td> <code title="">auml;</code> </td> <td> U+000E4 </td> <td> <span class="glyph" title="">ä</span> </td> </tr>
     *       <tr class="impl" id="entity-auml-legacy"><td> <code title="">auml</code> </td> <td> U+000E4 </td> <td> <span title="">ä</span> </td> </tr>
     *       <tr id="entity-awconint"><td> <code title="">awconint;</code> </td> <td> U+02233 </td> <td> <span class="glyph" title="">∳</span> </td> </tr>
     *       <tr id="entity-awint"><td> <code title="">awint;</code> </td> <td> U+02A11 </td> <td> <span class="glyph" title="">⨑</span> </td> </tr>
     *       <tr id="entity-backcong"><td> <code title="">backcong;</code> </td> <td> U+0224C </td> <td> <span class="glyph" title="">≌</span> </td> </tr>
     *       <tr id="entity-backepsilon"><td> <code title="">backepsilon;</code> </td> <td> U+003F6 </td> <td> <span class="glyph" title="">϶</span> </td> </tr>
     *       <tr id="entity-backprime"><td> <code title="">backprime;</code> </td> <td> U+02035 </td> <td> <span class="glyph" title="">‵</span> </td> </tr>
     *       <tr id="entity-backsim"><td> <code title="">backsim;</code> </td> <td> U+0223D </td> <td> <span class="glyph" title="">∽</span> </td> </tr>
     *       <tr id="entity-backsimeq"><td> <code title="">backsimeq;</code> </td> <td> U+022CD </td> <td> <span class="glyph" title="">⋍</span> </td> </tr>
     *       <tr id="entity-Backslash"><td> <code title="">Backslash;</code> </td> <td> U+02216 </td> <td> <span class="glyph" title="">∖</span> </td> </tr>
     *       <tr id="entity-Barv"><td> <code title="">Barv;</code> </td> <td> U+02AE7 </td> <td> <span class="glyph" title="">⫧</span> </td> </tr>
     *       <tr id="entity-barvee"><td> <code title="">barvee;</code> </td> <td> U+022BD </td> <td> <span class="glyph" title="">⊽</span> </td> </tr>
     *       <tr id="entity-Barwed"><td> <code title="">Barwed;</code> </td> <td> U+02306 </td> <td> <span class="glyph" title="">⌆</span> </td> </tr>
     *       <tr id="entity-barwed"><td> <code title="">barwed;</code> </td> <td> U+02305 </td> <td> <span class="glyph" title="">⌅</span> </td> </tr>
     *       <tr id="entity-barwedge"><td> <code title="">barwedge;</code> </td> <td> U+02305 </td> <td> <span class="glyph" title="">⌅</span> </td> </tr>
     *       <tr id="entity-bbrk"><td> <code title="">bbrk;</code> </td> <td> U+023B5 </td> <td> <span class="glyph" title="">⎵</span> </td> </tr>
     *       <tr id="entity-bbrktbrk"><td> <code title="">bbrktbrk;</code> </td> <td> U+023B6 </td> <td> <span class="glyph" title="">⎶</span> </td> </tr>
     *       <tr id="entity-bcong"><td> <code title="">bcong;</code> </td> <td> U+0224C </td> <td> <span class="glyph" title="">≌</span> </td> </tr>
     *       <tr id="entity-Bcy"><td> <code title="">Bcy;</code> </td> <td> U+00411 </td> <td> <span class="glyph" title="">Б</span> </td> </tr>
     *       <tr id="entity-bcy"><td> <code title="">bcy;</code> </td> <td> U+00431 </td> <td> <span class="glyph" title="">б</span> </td> </tr>
     *       <tr id="entity-bdquo"><td> <code title="">bdquo;</code> </td> <td> U+0201E </td> <td> <span class="glyph" title="">„</span> </td> </tr>
     *       <tr id="entity-becaus"><td> <code title="">becaus;</code> </td> <td> U+02235 </td> <td> <span class="glyph" title="">∵</span> </td> </tr>
     *       <tr id="entity-Because"><td> <code title="">Because;</code> </td> <td> U+02235 </td> <td> <span class="glyph" title="">∵</span> </td> </tr>
     *       <tr id="entity-because"><td> <code title="">because;</code> </td> <td> U+02235 </td> <td> <span class="glyph" title="">∵</span> </td> </tr>
     *       <tr id="entity-bemptyv"><td> <code title="">bemptyv;</code> </td> <td> U+029B0 </td> <td> <span class="glyph" title="">⦰</span> </td> </tr>
     *       <tr id="entity-bepsi"><td> <code title="">bepsi;</code> </td> <td> U+003F6 </td> <td> <span class="glyph" title="">϶</span> </td> </tr>
     *       <tr id="entity-bernou"><td> <code title="">bernou;</code> </td> <td> U+0212C </td> <td> <span class="glyph" title="">ℬ</span> </td> </tr>
     *       <tr id="entity-Bernoullis"><td> <code title="">Bernoullis;</code> </td> <td> U+0212C </td> <td> <span class="glyph" title="">ℬ</span> </td> </tr>
     *       <tr id="entity-Beta"><td> <code title="">Beta;</code> </td> <td> U+00392 </td> <td> <span class="glyph" title="">Β</span> </td> </tr>
     *       <tr id="entity-beta"><td> <code title="">beta;</code> </td> <td> U+003B2 </td> <td> <span class="glyph" title="">β</span> </td> </tr>
     *       <tr id="entity-beth"><td> <code title="">beth;</code> </td> <td> U+02136 </td> <td> <span class="glyph" title="">ℶ</span> </td> </tr>
     *       <tr id="entity-between"><td> <code title="">between;</code> </td> <td> U+0226C </td> <td> <span class="glyph" title="">≬</span> </td> </tr>
     *       <tr id="entity-Bfr"><td> <code title="">Bfr;</code> </td> <td> U+1D505 </td> <td> <span class="glyph" title="">𝔅</span> </td> </tr>
     *       <tr id="entity-bfr"><td> <code title="">bfr;</code> </td> <td> U+1D51F </td> <td> <span class="glyph" title="">𝔟</span> </td> </tr>
     *       <tr id="entity-bigcap"><td> <code title="">bigcap;</code> </td> <td> U+022C2 </td> <td> <span class="glyph" title="">⋂</span> </td> </tr>
     *       <tr id="entity-bigcirc"><td> <code title="">bigcirc;</code> </td> <td> U+025EF </td> <td> <span class="glyph" title="">◯</span> </td> </tr>
     *       <tr id="entity-bigcup"><td> <code title="">bigcup;</code> </td> <td> U+022C3 </td> <td> <span class="glyph" title="">⋃</span> </td> </tr>
     *       <tr id="entity-bigodot"><td> <code title="">bigodot;</code> </td> <td> U+02A00 </td> <td> <span class="glyph" title="">⨀</span> </td> </tr>
     *       <tr id="entity-bigoplus"><td> <code title="">bigoplus;</code> </td> <td> U+02A01 </td> <td> <span class="glyph" title="">⨁</span> </td> </tr>
     *       <tr id="entity-bigotimes"><td> <code title="">bigotimes;</code> </td> <td> U+02A02 </td> <td> <span class="glyph" title="">⨂</span> </td> </tr>
     *       <tr id="entity-bigsqcup"><td> <code title="">bigsqcup;</code> </td> <td> U+02A06 </td> <td> <span class="glyph" title="">⨆</span> </td> </tr>
     *       <tr id="entity-bigstar"><td> <code title="">bigstar;</code> </td> <td> U+02605 </td> <td> <span class="glyph" title="">★</span> </td> </tr>
     *       <tr id="entity-bigtriangledown"><td> <code title="">bigtriangledown;</code> </td> <td> U+025BD </td> <td> <span class="glyph" title="">▽</span> </td> </tr>
     *       <tr id="entity-bigtriangleup"><td> <code title="">bigtriangleup;</code> </td> <td> U+025B3 </td> <td> <span class="glyph" title="">△</span> </td> </tr>
     *       <tr id="entity-biguplus"><td> <code title="">biguplus;</code> </td> <td> U+02A04 </td> <td> <span class="glyph" title="">⨄</span> </td> </tr>
     *       <tr id="entity-bigvee"><td> <code title="">bigvee;</code> </td> <td> U+022C1 </td> <td> <span class="glyph" title="">⋁</span> </td> </tr>
     *       <tr id="entity-bigwedge"><td> <code title="">bigwedge;</code> </td> <td> U+022C0 </td> <td> <span class="glyph" title="">⋀</span> </td> </tr>
     *       <tr id="entity-bkarow"><td> <code title="">bkarow;</code> </td> <td> U+0290D </td> <td> <span class="glyph" title="">⤍</span> </td> </tr>
     *       <tr id="entity-blacklozenge"><td> <code title="">blacklozenge;</code> </td> <td> U+029EB </td> <td> <span class="glyph" title="">⧫</span> </td> </tr>
     *       <tr id="entity-blacksquare"><td> <code title="">blacksquare;</code> </td> <td> U+025AA </td> <td> <span class="glyph" title="">▪</span> </td> </tr>
     *       <tr id="entity-blacktriangle"><td> <code title="">blacktriangle;</code> </td> <td> U+025B4 </td> <td> <span class="glyph" title="">▴</span> </td> </tr>
     *       <tr id="entity-blacktriangledown"><td> <code title="">blacktriangledown;</code> </td> <td> U+025BE </td> <td> <span class="glyph" title="">▾</span> </td> </tr>
     *       <tr id="entity-blacktriangleleft"><td> <code title="">blacktriangleleft;</code> </td> <td> U+025C2 </td> <td> <span class="glyph" title="">◂</span> </td> </tr>
     *       <tr id="entity-blacktriangleright"><td> <code title="">blacktriangleright;</code> </td> <td> U+025B8 </td> <td> <span class="glyph" title="">▸</span> </td> </tr>
     *       <tr id="entity-blank"><td> <code title="">blank;</code> </td> <td> U+02423 </td> <td> <span class="glyph" title="">␣</span> </td> </tr>
     *       <tr id="entity-blk12"><td> <code title="">blk12;</code> </td> <td> U+02592 </td> <td> <span class="glyph" title="">▒</span> </td> </tr>
     *       <tr id="entity-blk14"><td> <code title="">blk14;</code> </td> <td> U+02591 </td> <td> <span class="glyph" title="">░</span> </td> </tr>
     *       <tr id="entity-blk34"><td> <code title="">blk34;</code> </td> <td> U+02593 </td> <td> <span class="glyph" title="">▓</span> </td> </tr>
     *       <tr id="entity-block"><td> <code title="">block;</code> </td> <td> U+02588 </td> <td> <span class="glyph" title="">█</span> </td> </tr>
     *       <tr id="entity-bne"><td> <code title="">bne;</code> </td> <td> U+0003D U+020E5 </td> <td> <span class="glyph compound" title="">=⃥</span> </td> </tr>
     *       <tr id="entity-bnequiv"><td> <code title="">bnequiv;</code> </td> <td> U+02261 U+020E5 </td> <td> <span class="glyph compound" title="">≡⃥</span> </td> </tr>
     *       <tr id="entity-bNot"><td> <code title="">bNot;</code> </td> <td> U+02AED </td> <td> <span class="glyph" title="">⫭</span> </td> </tr>
     *       <tr id="entity-bnot"><td> <code title="">bnot;</code> </td> <td> U+02310 </td> <td> <span class="glyph" title="">⌐</span> </td> </tr>
     *       <tr id="entity-Bopf"><td> <code title="">Bopf;</code> </td> <td> U+1D539 </td> <td> <span class="glyph" title="">𝔹</span> </td> </tr>
     *       <tr id="entity-bopf"><td> <code title="">bopf;</code> </td> <td> U+1D553 </td> <td> <span class="glyph" title="">𝕓</span> </td> </tr>
     *       <tr id="entity-bot"><td> <code title="">bot;</code> </td> <td> U+022A5 </td> <td> <span class="glyph" title="">⊥</span> </td> </tr>
     *       <tr id="entity-bottom"><td> <code title="">bottom;</code> </td> <td> U+022A5 </td> <td> <span class="glyph" title="">⊥</span> </td> </tr>
     *       <tr id="entity-bowtie"><td> <code title="">bowtie;</code> </td> <td> U+022C8 </td> <td> <span class="glyph" title="">⋈</span> </td> </tr>
     *       <tr id="entity-boxbox"><td> <code title="">boxbox;</code> </td> <td> U+029C9 </td> <td> <span class="glyph" title="">⧉</span> </td> </tr>
     *       <tr id="entity-boxDL"><td> <code title="">boxDL;</code> </td> <td> U+02557 </td> <td> <span class="glyph" title="">╗</span> </td> </tr>
     *       <tr id="entity-boxDl"><td> <code title="">boxDl;</code> </td> <td> U+02556 </td> <td> <span class="glyph" title="">╖</span> </td> </tr>
     *       <tr id="entity-boxdL"><td> <code title="">boxdL;</code> </td> <td> U+02555 </td> <td> <span class="glyph" title="">╕</span> </td> </tr>
     *       <tr id="entity-boxdl"><td> <code title="">boxdl;</code> </td> <td> U+02510 </td> <td> <span class="glyph" title="">┐</span> </td> </tr>
     *       <tr id="entity-boxDR"><td> <code title="">boxDR;</code> </td> <td> U+02554 </td> <td> <span class="glyph" title="">╔</span> </td> </tr>
     *       <tr id="entity-boxDr"><td> <code title="">boxDr;</code> </td> <td> U+02553 </td> <td> <span class="glyph" title="">╓</span> </td> </tr>
     *       <tr id="entity-boxdR"><td> <code title="">boxdR;</code> </td> <td> U+02552 </td> <td> <span class="glyph" title="">╒</span> </td> </tr>
     *       <tr id="entity-boxdr"><td> <code title="">boxdr;</code> </td> <td> U+0250C </td> <td> <span class="glyph" title="">┌</span> </td> </tr>
     *       <tr id="entity-boxH"><td> <code title="">boxH;</code> </td> <td> U+02550 </td> <td> <span class="glyph" title="">═</span> </td> </tr>
     *       <tr id="entity-boxh"><td> <code title="">boxh;</code> </td> <td> U+02500 </td> <td> <span class="glyph" title="">─</span> </td> </tr>
     *       <tr id="entity-boxHD"><td> <code title="">boxHD;</code> </td> <td> U+02566 </td> <td> <span class="glyph" title="">╦</span> </td> </tr>
     *       <tr id="entity-boxHd"><td> <code title="">boxHd;</code> </td> <td> U+02564 </td> <td> <span class="glyph" title="">╤</span> </td> </tr>
     *       <tr id="entity-boxhD"><td> <code title="">boxhD;</code> </td> <td> U+02565 </td> <td> <span class="glyph" title="">╥</span> </td> </tr>
     *       <tr id="entity-boxhd"><td> <code title="">boxhd;</code> </td> <td> U+0252C </td> <td> <span class="glyph" title="">┬</span> </td> </tr>
     *       <tr id="entity-boxHU"><td> <code title="">boxHU;</code> </td> <td> U+02569 </td> <td> <span class="glyph" title="">╩</span> </td> </tr>
     *       <tr id="entity-boxHu"><td> <code title="">boxHu;</code> </td> <td> U+02567 </td> <td> <span class="glyph" title="">╧</span> </td> </tr>
     *       <tr id="entity-boxhU"><td> <code title="">boxhU;</code> </td> <td> U+02568 </td> <td> <span class="glyph" title="">╨</span> </td> </tr>
     *       <tr id="entity-boxhu"><td> <code title="">boxhu;</code> </td> <td> U+02534 </td> <td> <span class="glyph" title="">┴</span> </td> </tr>
     *       <tr id="entity-boxminus"><td> <code title="">boxminus;</code> </td> <td> U+0229F </td> <td> <span class="glyph" title="">⊟</span> </td> </tr>
     *       <tr id="entity-boxplus"><td> <code title="">boxplus;</code> </td> <td> U+0229E </td> <td> <span class="glyph" title="">⊞</span> </td> </tr>
     *       <tr id="entity-boxtimes"><td> <code title="">boxtimes;</code> </td> <td> U+022A0 </td> <td> <span class="glyph" title="">⊠</span> </td> </tr>
     *       <tr id="entity-boxUL"><td> <code title="">boxUL;</code> </td> <td> U+0255D </td> <td> <span class="glyph" title="">╝</span> </td> </tr>
     *       <tr id="entity-boxUl"><td> <code title="">boxUl;</code> </td> <td> U+0255C </td> <td> <span class="glyph" title="">╜</span> </td> </tr>
     *       <tr id="entity-boxuL"><td> <code title="">boxuL;</code> </td> <td> U+0255B </td> <td> <span class="glyph" title="">╛</span> </td> </tr>
     *       <tr id="entity-boxul"><td> <code title="">boxul;</code> </td> <td> U+02518 </td> <td> <span class="glyph" title="">┘</span> </td> </tr>
     *       <tr id="entity-boxUR"><td> <code title="">boxUR;</code> </td> <td> U+0255A </td> <td> <span class="glyph" title="">╚</span> </td> </tr>
     *       <tr id="entity-boxUr"><td> <code title="">boxUr;</code> </td> <td> U+02559 </td> <td> <span class="glyph" title="">╙</span> </td> </tr>
     *       <tr id="entity-boxuR"><td> <code title="">boxuR;</code> </td> <td> U+02558 </td> <td> <span class="glyph" title="">╘</span> </td> </tr>
     *       <tr id="entity-boxur"><td> <code title="">boxur;</code> </td> <td> U+02514 </td> <td> <span class="glyph" title="">└</span> </td> </tr>
     *       <tr id="entity-boxV"><td> <code title="">boxV;</code> </td> <td> U+02551 </td> <td> <span class="glyph" title="">║</span> </td> </tr>
     *       <tr id="entity-boxv"><td> <code title="">boxv;</code> </td> <td> U+02502 </td> <td> <span class="glyph" title="">│</span> </td> </tr>
     *       <tr id="entity-boxVH"><td> <code title="">boxVH;</code> </td> <td> U+0256C </td> <td> <span class="glyph" title="">╬</span> </td> </tr>
     *       <tr id="entity-boxVh"><td> <code title="">boxVh;</code> </td> <td> U+0256B </td> <td> <span class="glyph" title="">╫</span> </td> </tr>
     *       <tr id="entity-boxvH"><td> <code title="">boxvH;</code> </td> <td> U+0256A </td> <td> <span class="glyph" title="">╪</span> </td> </tr>
     *       <tr id="entity-boxvh"><td> <code title="">boxvh;</code> </td> <td> U+0253C </td> <td> <span class="glyph" title="">┼</span> </td> </tr>
     *       <tr id="entity-boxVL"><td> <code title="">boxVL;</code> </td> <td> U+02563 </td> <td> <span class="glyph" title="">╣</span> </td> </tr>
     *       <tr id="entity-boxVl"><td> <code title="">boxVl;</code> </td> <td> U+02562 </td> <td> <span class="glyph" title="">╢</span> </td> </tr>
     *       <tr id="entity-boxvL"><td> <code title="">boxvL;</code> </td> <td> U+02561 </td> <td> <span class="glyph" title="">╡</span> </td> </tr>
     *       <tr id="entity-boxvl"><td> <code title="">boxvl;</code> </td> <td> U+02524 </td> <td> <span class="glyph" title="">┤</span> </td> </tr>
     *       <tr id="entity-boxVR"><td> <code title="">boxVR;</code> </td> <td> U+02560 </td> <td> <span class="glyph" title="">╠</span> </td> </tr>
     *       <tr id="entity-boxVr"><td> <code title="">boxVr;</code> </td> <td> U+0255F </td> <td> <span class="glyph" title="">╟</span> </td> </tr>
     *       <tr id="entity-boxvR"><td> <code title="">boxvR;</code> </td> <td> U+0255E </td> <td> <span class="glyph" title="">╞</span> </td> </tr>
     *       <tr id="entity-boxvr"><td> <code title="">boxvr;</code> </td> <td> U+0251C </td> <td> <span class="glyph" title="">├</span> </td> </tr>
     *       <tr id="entity-bprime"><td> <code title="">bprime;</code> </td> <td> U+02035 </td> <td> <span class="glyph" title="">‵</span> </td> </tr>
     *       <tr id="entity-Breve"><td> <code title="">Breve;</code> </td> <td> U+002D8 </td> <td> <span class="glyph" title="">˘</span> </td> </tr>
     *       <tr id="entity-breve"><td> <code title="">breve;</code> </td> <td> U+002D8 </td> <td> <span class="glyph" title="">˘</span> </td> </tr>
     *       <tr id="entity-brvbar"><td> <code title="">brvbar;</code> </td> <td> U+000A6 </td> <td> <span class="glyph" title="">¦</span> </td> </tr>
     *       <tr class="impl" id="entity-brvbar-legacy"><td> <code title="">brvbar</code> </td> <td> U+000A6 </td> <td> <span title="">¦</span> </td> </tr>
     *       <tr id="entity-Bscr"><td> <code title="">Bscr;</code> </td> <td> U+0212C </td> <td> <span class="glyph" title="">ℬ</span> </td> </tr>
     *       <tr id="entity-bscr"><td> <code title="">bscr;</code> </td> <td> U+1D4B7 </td> <td> <span class="glyph" title="">𝒷</span> </td> </tr>
     *       <tr id="entity-bsemi"><td> <code title="">bsemi;</code> </td> <td> U+0204F </td> <td> <span class="glyph" title="">⁏</span> </td> </tr>
     *       <tr id="entity-bsim"><td> <code title="">bsim;</code> </td> <td> U+0223D </td> <td> <span class="glyph" title="">∽</span> </td> </tr>
     *       <tr id="entity-bsime"><td> <code title="">bsime;</code> </td> <td> U+022CD </td> <td> <span class="glyph" title="">⋍</span> </td> </tr>
     *       <tr id="entity-bsol"><td> <code title="">bsol;</code> </td> <td> U+0005C </td> <td> <span class="glyph" title="">\</span> </td> </tr>
     *       <tr id="entity-bsolb"><td> <code title="">bsolb;</code> </td> <td> U+029C5 </td> <td> <span class="glyph" title="">⧅</span> </td> </tr>
     *       <tr id="entity-bsolhsub"><td> <code title="">bsolhsub;</code> </td> <td> U+027C8 </td> <td> <span class="glyph" title="">⟈</span> </td> </tr>
     *       <tr id="entity-bull"><td> <code title="">bull;</code> </td> <td> U+02022 </td> <td> <span class="glyph" title="">•</span> </td> </tr>
     *       <tr id="entity-bullet"><td> <code title="">bullet;</code> </td> <td> U+02022 </td> <td> <span class="glyph" title="">•</span> </td> </tr>
     *       <tr id="entity-bump"><td> <code title="">bump;</code> </td> <td> U+0224E </td> <td> <span class="glyph" title="">≎</span> </td> </tr>
     *       <tr id="entity-bumpE"><td> <code title="">bumpE;</code> </td> <td> U+02AAE </td> <td> <span class="glyph" title="">⪮</span> </td> </tr>
     *       <tr id="entity-bumpe"><td> <code title="">bumpe;</code> </td> <td> U+0224F </td> <td> <span class="glyph" title="">≏</span> </td> </tr>
     *       <tr id="entity-Bumpeq"><td> <code title="">Bumpeq;</code> </td> <td> U+0224E </td> <td> <span class="glyph" title="">≎</span> </td> </tr>
     *       <tr id="entity-bumpeq"><td> <code title="">bumpeq;</code> </td> <td> U+0224F </td> <td> <span class="glyph" title="">≏</span> </td> </tr>
     *       <tr id="entity-Cacute"><td> <code title="">Cacute;</code> </td> <td> U+00106 </td> <td> <span class="glyph" title="">Ć</span> </td> </tr>
     *       <tr id="entity-cacute"><td> <code title="">cacute;</code> </td> <td> U+00107 </td> <td> <span class="glyph" title="">ć</span> </td> </tr>
     *       <tr id="entity-Cap"><td> <code title="">Cap;</code> </td> <td> U+022D2 </td> <td> <span class="glyph" title="">⋒</span> </td> </tr>
     *       <tr id="entity-cap"><td> <code title="">cap;</code> </td> <td> U+02229 </td> <td> <span class="glyph" title="">∩</span> </td> </tr>
     *       <tr id="entity-capand"><td> <code title="">capand;</code> </td> <td> U+02A44 </td> <td> <span class="glyph" title="">⩄</span> </td> </tr>
     *       <tr id="entity-capbrcup"><td> <code title="">capbrcup;</code> </td> <td> U+02A49 </td> <td> <span class="glyph" title="">⩉</span> </td> </tr>
     *       <tr id="entity-capcap"><td> <code title="">capcap;</code> </td> <td> U+02A4B </td> <td> <span class="glyph" title="">⩋</span> </td> </tr>
     *       <tr id="entity-capcup"><td> <code title="">capcup;</code> </td> <td> U+02A47 </td> <td> <span class="glyph" title="">⩇</span> </td> </tr>
     *       <tr id="entity-capdot"><td> <code title="">capdot;</code> </td> <td> U+02A40 </td> <td> <span class="glyph" title="">⩀</span> </td> </tr>
     *       <tr id="entity-CapitalDifferentialD"><td> <code title="">CapitalDifferentialD;</code> </td> <td> U+02145 </td> <td> <span class="glyph" title="">ⅅ</span> </td> </tr>
     *       <tr id="entity-caps"><td> <code title="">caps;</code> </td> <td> U+02229 U+0FE00 </td> <td> <span class="glyph compound" title="">∩︀</span> </td> </tr>
     *       <tr id="entity-caret"><td> <code title="">caret;</code> </td> <td> U+02041 </td> <td> <span class="glyph" title="">⁁</span> </td> </tr>
     *       <tr id="entity-caron"><td> <code title="">caron;</code> </td> <td> U+002C7 </td> <td> <span class="glyph" title="">ˇ</span> </td> </tr>
     *       <tr id="entity-Cayleys"><td> <code title="">Cayleys;</code> </td> <td> U+0212D </td> <td> <span class="glyph" title="">ℭ</span> </td> </tr>
     *       <tr id="entity-ccaps"><td> <code title="">ccaps;</code> </td> <td> U+02A4D </td> <td> <span class="glyph" title="">⩍</span> </td> </tr>
     *       <tr id="entity-Ccaron"><td> <code title="">Ccaron;</code> </td> <td> U+0010C </td> <td> <span class="glyph" title="">Č</span> </td> </tr>
     *       <tr id="entity-ccaron"><td> <code title="">ccaron;</code> </td> <td> U+0010D </td> <td> <span class="glyph" title="">č</span> </td> </tr>
     *       <tr id="entity-Ccedil"><td> <code title="">Ccedil;</code> </td> <td> U+000C7 </td> <td> <span class="glyph" title="">Ç</span> </td> </tr>
     *       <tr class="impl" id="entity-Ccedil-legacy"><td> <code title="">Ccedil</code> </td> <td> U+000C7 </td> <td> <span title="">Ç</span> </td> </tr>
     *       <tr id="entity-ccedil"><td> <code title="">ccedil;</code> </td> <td> U+000E7 </td> <td> <span class="glyph" title="">ç</span> </td> </tr>
     *       <tr class="impl" id="entity-ccedil-legacy"><td> <code title="">ccedil</code> </td> <td> U+000E7 </td> <td> <span title="">ç</span> </td> </tr>
     *       <tr id="entity-Ccirc"><td> <code title="">Ccirc;</code> </td> <td> U+00108 </td> <td> <span class="glyph" title="">Ĉ</span> </td> </tr>
     *       <tr id="entity-ccirc"><td> <code title="">ccirc;</code> </td> <td> U+00109 </td> <td> <span class="glyph" title="">ĉ</span> </td> </tr>
     *       <tr id="entity-Cconint"><td> <code title="">Cconint;</code> </td> <td> U+02230 </td> <td> <span class="glyph" title="">∰</span> </td> </tr>
     *       <tr id="entity-ccups"><td> <code title="">ccups;</code> </td> <td> U+02A4C </td> <td> <span class="glyph" title="">⩌</span> </td> </tr>
     *       <tr id="entity-ccupssm"><td> <code title="">ccupssm;</code> </td> <td> U+02A50 </td> <td> <span class="glyph" title="">⩐</span> </td> </tr>
     *       <tr id="entity-Cdot"><td> <code title="">Cdot;</code> </td> <td> U+0010A </td> <td> <span class="glyph" title="">Ċ</span> </td> </tr>
     *       <tr id="entity-cdot"><td> <code title="">cdot;</code> </td> <td> U+0010B </td> <td> <span class="glyph" title="">ċ</span> </td> </tr>
     *       <tr id="entity-cedil"><td> <code title="">cedil;</code> </td> <td> U+000B8 </td> <td> <span class="glyph" title="">¸</span> </td> </tr>
     *       <tr class="impl" id="entity-cedil-legacy"><td> <code title="">cedil</code> </td> <td> U+000B8 </td> <td> <span title="">¸</span> </td> </tr>
     *       <tr id="entity-Cedilla"><td> <code title="">Cedilla;</code> </td> <td> U+000B8 </td> <td> <span class="glyph" title="">¸</span> </td> </tr>
     *       <tr id="entity-cemptyv"><td> <code title="">cemptyv;</code> </td> <td> U+029B2 </td> <td> <span class="glyph" title="">⦲</span> </td> </tr>
     *       <tr id="entity-cent"><td> <code title="">cent;</code> </td> <td> U+000A2 </td> <td> <span class="glyph" title="">¢</span> </td> </tr>
     *       <tr class="impl" id="entity-cent-legacy"><td> <code title="">cent</code> </td> <td> U+000A2 </td> <td> <span title="">¢</span> </td> </tr>
     *       <tr id="entity-CenterDot"><td> <code title="">CenterDot;</code> </td> <td> U+000B7 </td> <td> <span class="glyph" title="">·</span> </td> </tr>
     *       <tr id="entity-centerdot"><td> <code title="">centerdot;</code> </td> <td> U+000B7 </td> <td> <span class="glyph" title="">·</span> </td> </tr>
     *       <tr id="entity-Cfr"><td> <code title="">Cfr;</code> </td> <td> U+0212D </td> <td> <span class="glyph" title="">ℭ</span> </td> </tr>
     *       <tr id="entity-cfr"><td> <code title="">cfr;</code> </td> <td> U+1D520 </td> <td> <span class="glyph" title="">𝔠</span> </td> </tr>
     *       <tr id="entity-CHcy"><td> <code title="">CHcy;</code> </td> <td> U+00427 </td> <td> <span class="glyph" title="">Ч</span> </td> </tr>
     *       <tr id="entity-chcy"><td> <code title="">chcy;</code> </td> <td> U+00447 </td> <td> <span class="glyph" title="">ч</span> </td> </tr>
     *       <tr id="entity-check"><td> <code title="">check;</code> </td> <td> U+02713 </td> <td> <span class="glyph" title="">✓</span> </td> </tr>
     *       <tr id="entity-checkmark"><td> <code title="">checkmark;</code> </td> <td> U+02713 </td> <td> <span class="glyph" title="">✓</span> </td> </tr>
     *       <tr id="entity-Chi"><td> <code title="">Chi;</code> </td> <td> U+003A7 </td> <td> <span class="glyph" title="">Χ</span> </td> </tr>
     *       <tr id="entity-chi"><td> <code title="">chi;</code> </td> <td> U+003C7 </td> <td> <span class="glyph" title="">χ</span> </td> </tr>
     *       <tr id="entity-cir"><td> <code title="">cir;</code> </td> <td> U+025CB </td> <td> <span class="glyph" title="">○</span> </td> </tr>
     *       <tr id="entity-circ"><td> <code title="">circ;</code> </td> <td> U+002C6 </td> <td> <span class="glyph" title="">ˆ</span> </td> </tr>
     *       <tr id="entity-circeq"><td> <code title="">circeq;</code> </td> <td> U+02257 </td> <td> <span class="glyph" title="">≗</span> </td> </tr>
     *       <tr id="entity-circlearrowleft"><td> <code title="">circlearrowleft;</code> </td> <td> U+021BA </td> <td> <span class="glyph" title="">↺</span> </td> </tr>
     *       <tr id="entity-circlearrowright"><td> <code title="">circlearrowright;</code> </td> <td> U+021BB </td> <td> <span class="glyph" title="">↻</span> </td> </tr>
     *       <tr id="entity-circledast"><td> <code title="">circledast;</code> </td> <td> U+0229B </td> <td> <span class="glyph" title="">⊛</span> </td> </tr>
     *       <tr id="entity-circledcirc"><td> <code title="">circledcirc;</code> </td> <td> U+0229A </td> <td> <span class="glyph" title="">⊚</span> </td> </tr>
     *       <tr id="entity-circleddash"><td> <code title="">circleddash;</code> </td> <td> U+0229D </td> <td> <span class="glyph" title="">⊝</span> </td> </tr>
     *       <tr id="entity-CircleDot"><td> <code title="">CircleDot;</code> </td> <td> U+02299 </td> <td> <span class="glyph" title="">⊙</span> </td> </tr>
     *       <tr id="entity-circledR"><td> <code title="">circledR;</code> </td> <td> U+000AE </td> <td> <span class="glyph" title="">®</span> </td> </tr>
     *       <tr id="entity-circledS"><td> <code title="">circledS;</code> </td> <td> U+024C8 </td> <td> <span class="glyph" title="">Ⓢ</span> </td> </tr>
     *       <tr id="entity-CircleMinus"><td> <code title="">CircleMinus;</code> </td> <td> U+02296 </td> <td> <span class="glyph" title="">⊖</span> </td> </tr>
     *       <tr id="entity-CirclePlus"><td> <code title="">CirclePlus;</code> </td> <td> U+02295 </td> <td> <span class="glyph" title="">⊕</span> </td> </tr>
     *       <tr id="entity-CircleTimes"><td> <code title="">CircleTimes;</code> </td> <td> U+02297 </td> <td> <span class="glyph" title="">⊗</span> </td> </tr>
     *       <tr id="entity-cirE"><td> <code title="">cirE;</code> </td> <td> U+029C3 </td> <td> <span class="glyph" title="">⧃</span> </td> </tr>
     *       <tr id="entity-cire"><td> <code title="">cire;</code> </td> <td> U+02257 </td> <td> <span class="glyph" title="">≗</span> </td> </tr>
     *       <tr id="entity-cirfnint"><td> <code title="">cirfnint;</code> </td> <td> U+02A10 </td> <td> <span class="glyph" title="">⨐</span> </td> </tr>
     *       <tr id="entity-cirmid"><td> <code title="">cirmid;</code> </td> <td> U+02AEF </td> <td> <span class="glyph" title="">⫯</span> </td> </tr>
     *       <tr id="entity-cirscir"><td> <code title="">cirscir;</code> </td> <td> U+029C2 </td> <td> <span class="glyph" title="">⧂</span> </td> </tr>
     *       <tr id="entity-ClockwiseContourIntegral"><td> <code title="">ClockwiseContourIntegral;</code> </td> <td> U+02232 </td> <td> <span class="glyph" title="">∲</span> </td> </tr>
     *       <tr id="entity-CloseCurlyDoubleQuote"><td> <code title="">CloseCurlyDoubleQuote;</code> </td> <td> U+0201D </td> <td> <span class="glyph" title="">”</span> </td> </tr>
     *       <tr id="entity-CloseCurlyQuote"><td> <code title="">CloseCurlyQuote;</code> </td> <td> U+02019 </td> <td> <span class="glyph" title="">’</span> </td> </tr>
     *       <tr id="entity-clubs"><td> <code title="">clubs;</code> </td> <td> U+02663 </td> <td> <span class="glyph" title="">♣</span> </td> </tr>
     *       <tr id="entity-clubsuit"><td> <code title="">clubsuit;</code> </td> <td> U+02663 </td> <td> <span class="glyph" title="">♣</span> </td> </tr>
     *       <tr id="entity-Colon"><td> <code title="">Colon;</code> </td> <td> U+02237 </td> <td> <span class="glyph" title="">∷</span> </td> </tr>
     *       <tr id="entity-colon"><td> <code title="">colon;</code> </td> <td> U+0003A </td> <td> <span class="glyph" title="">:</span> </td> </tr>
     *       <tr id="entity-Colone"><td> <code title="">Colone;</code> </td> <td> U+02A74 </td> <td> <span class="glyph" title="">⩴</span> </td> </tr>
     *       <tr id="entity-colone"><td> <code title="">colone;</code> </td> <td> U+02254 </td> <td> <span class="glyph" title="">≔</span> </td> </tr>
     *       <tr id="entity-coloneq"><td> <code title="">coloneq;</code> </td> <td> U+02254 </td> <td> <span class="glyph" title="">≔</span> </td> </tr>
     *       <tr id="entity-comma"><td> <code title="">comma;</code> </td> <td> U+0002C </td> <td> <span class="glyph" title="">,</span> </td> </tr>
     *       <tr id="entity-commat"><td> <code title="">commat;</code> </td> <td> U+00040 </td> <td> <span class="glyph" title="">@</span> </td> </tr>
     *       <tr id="entity-comp"><td> <code title="">comp;</code> </td> <td> U+02201 </td> <td> <span class="glyph" title="">∁</span> </td> </tr>
     *       <tr id="entity-compfn"><td> <code title="">compfn;</code> </td> <td> U+02218 </td> <td> <span class="glyph" title="">∘</span> </td> </tr>
     *       <tr id="entity-complement"><td> <code title="">complement;</code> </td> <td> U+02201 </td> <td> <span class="glyph" title="">∁</span> </td> </tr>
     *       <tr id="entity-complexes"><td> <code title="">complexes;</code> </td> <td> U+02102 </td> <td> <span class="glyph" title="">ℂ</span> </td> </tr>
     *       <tr id="entity-cong"><td> <code title="">cong;</code> </td> <td> U+02245 </td> <td> <span class="glyph" title="">≅</span> </td> </tr>
     *       <tr id="entity-congdot"><td> <code title="">congdot;</code> </td> <td> U+02A6D </td> <td> <span class="glyph" title="">⩭</span> </td> </tr>
     *       <tr id="entity-Congruent"><td> <code title="">Congruent;</code> </td> <td> U+02261 </td> <td> <span class="glyph" title="">≡</span> </td> </tr>
     *       <tr id="entity-Conint"><td> <code title="">Conint;</code> </td> <td> U+0222F </td> <td> <span class="glyph" title="">∯</span> </td> </tr>
     *       <tr id="entity-conint"><td> <code title="">conint;</code> </td> <td> U+0222E </td> <td> <span class="glyph" title="">∮</span> </td> </tr>
     *       <tr id="entity-ContourIntegral"><td> <code title="">ContourIntegral;</code> </td> <td> U+0222E </td> <td> <span class="glyph" title="">∮</span> </td> </tr>
     *       <tr id="entity-Copf"><td> <code title="">Copf;</code> </td> <td> U+02102 </td> <td> <span class="glyph" title="">ℂ</span> </td> </tr>
     *       <tr id="entity-copf"><td> <code title="">copf;</code> </td> <td> U+1D554 </td> <td> <span class="glyph" title="">𝕔</span> </td> </tr>
     *       <tr id="entity-coprod"><td> <code title="">coprod;</code> </td> <td> U+02210 </td> <td> <span class="glyph" title="">∐</span> </td> </tr>
     *       <tr id="entity-Coproduct"><td> <code title="">Coproduct;</code> </td> <td> U+02210 </td> <td> <span class="glyph" title="">∐</span> </td> </tr>
     *       <tr id="entity-COPY"><td> <code title="">COPY;</code> </td> <td> U+000A9 </td> <td> <span class="glyph" title="">©</span> </td> </tr>
     *       <tr class="impl" id="entity-COPY-legacy"><td> <code title="">COPY</code> </td> <td> U+000A9 </td> <td> <span title="">©</span> </td> </tr>
     *       <tr id="entity-copy"><td> <code title="">copy;</code> </td> <td> U+000A9 </td> <td> <span class="glyph" title="">©</span> </td> </tr>
     *       <tr class="impl" id="entity-copy-legacy"><td> <code title="">copy</code> </td> <td> U+000A9 </td> <td> <span title="">©</span> </td> </tr>
     *       <tr id="entity-copysr"><td> <code title="">copysr;</code> </td> <td> U+02117 </td> <td> <span class="glyph" title="">℗</span> </td> </tr>
     *       <tr id="entity-CounterClockwiseContourIntegral"><td> <code title="">CounterClockwiseContourIntegral;</code> </td> <td> U+02233 </td> <td> <span class="glyph" title="">∳</span> </td> </tr>
     *       <tr id="entity-crarr"><td> <code title="">crarr;</code> </td> <td> U+021B5 </td> <td> <span class="glyph" title="">↵</span> </td> </tr>
     *       <tr id="entity-Cross"><td> <code title="">Cross;</code> </td> <td> U+02A2F </td> <td> <span class="glyph" title="">⨯</span> </td> </tr>
     *       <tr id="entity-cross"><td> <code title="">cross;</code> </td> <td> U+02717 </td> <td> <span class="glyph" title="">✗</span> </td> </tr>
     *       <tr id="entity-Cscr"><td> <code title="">Cscr;</code> </td> <td> U+1D49E </td> <td> <span class="glyph" title="">𝒞</span> </td> </tr>
     *       <tr id="entity-cscr"><td> <code title="">cscr;</code> </td> <td> U+1D4B8 </td> <td> <span class="glyph" title="">𝒸</span> </td> </tr>
     *       <tr id="entity-csub"><td> <code title="">csub;</code> </td> <td> U+02ACF </td> <td> <span class="glyph" title="">⫏</span> </td> </tr>
     *       <tr id="entity-csube"><td> <code title="">csube;</code> </td> <td> U+02AD1 </td> <td> <span class="glyph" title="">⫑</span> </td> </tr>
     *       <tr id="entity-csup"><td> <code title="">csup;</code> </td> <td> U+02AD0 </td> <td> <span class="glyph" title="">⫐</span> </td> </tr>
     *       <tr id="entity-csupe"><td> <code title="">csupe;</code> </td> <td> U+02AD2 </td> <td> <span class="glyph" title="">⫒</span> </td> </tr>
     *       <tr id="entity-ctdot"><td> <code title="">ctdot;</code> </td> <td> U+022EF </td> <td> <span class="glyph" title="">⋯</span> </td> </tr>
     *       <tr id="entity-cudarrl"><td> <code title="">cudarrl;</code> </td> <td> U+02938 </td> <td> <span class="glyph" title="">⤸</span> </td> </tr>
     *       <tr id="entity-cudarrr"><td> <code title="">cudarrr;</code> </td> <td> U+02935 </td> <td> <span class="glyph" title="">⤵</span> </td> </tr>
     *       <tr id="entity-cuepr"><td> <code title="">cuepr;</code> </td> <td> U+022DE </td> <td> <span class="glyph" title="">⋞</span> </td> </tr>
     *       <tr id="entity-cuesc"><td> <code title="">cuesc;</code> </td> <td> U+022DF </td> <td> <span class="glyph" title="">⋟</span> </td> </tr>
     *       <tr id="entity-cularr"><td> <code title="">cularr;</code> </td> <td> U+021B6 </td> <td> <span class="glyph" title="">↶</span> </td> </tr>
     *       <tr id="entity-cularrp"><td> <code title="">cularrp;</code> </td> <td> U+0293D </td> <td> <span class="glyph" title="">⤽</span> </td> </tr>
     *       <tr id="entity-Cup"><td> <code title="">Cup;</code> </td> <td> U+022D3 </td> <td> <span class="glyph" title="">⋓</span> </td> </tr>
     *       <tr id="entity-cup"><td> <code title="">cup;</code> </td> <td> U+0222A </td> <td> <span class="glyph" title="">∪</span> </td> </tr>
     *       <tr id="entity-cupbrcap"><td> <code title="">cupbrcap;</code> </td> <td> U+02A48 </td> <td> <span class="glyph" title="">⩈</span> </td> </tr>
     *       <tr id="entity-CupCap"><td> <code title="">CupCap;</code> </td> <td> U+0224D </td> <td> <span class="glyph" title="">≍</span> </td> </tr>
     *       <tr id="entity-cupcap"><td> <code title="">cupcap;</code> </td> <td> U+02A46 </td> <td> <span class="glyph" title="">⩆</span> </td> </tr>
     *       <tr id="entity-cupcup"><td> <code title="">cupcup;</code> </td> <td> U+02A4A </td> <td> <span class="glyph" title="">⩊</span> </td> </tr>
     *       <tr id="entity-cupdot"><td> <code title="">cupdot;</code> </td> <td> U+0228D </td> <td> <span class="glyph" title="">⊍</span> </td> </tr>
     *       <tr id="entity-cupor"><td> <code title="">cupor;</code> </td> <td> U+02A45 </td> <td> <span class="glyph" title="">⩅</span> </td> </tr>
     *       <tr id="entity-cups"><td> <code title="">cups;</code> </td> <td> U+0222A U+0FE00 </td> <td> <span class="glyph compound" title="">∪︀</span> </td> </tr>
     *       <tr id="entity-curarr"><td> <code title="">curarr;</code> </td> <td> U+021B7 </td> <td> <span class="glyph" title="">↷</span> </td> </tr>
     *       <tr id="entity-curarrm"><td> <code title="">curarrm;</code> </td> <td> U+0293C </td> <td> <span class="glyph" title="">⤼</span> </td> </tr>
     *       <tr id="entity-curlyeqprec"><td> <code title="">curlyeqprec;</code> </td> <td> U+022DE </td> <td> <span class="glyph" title="">⋞</span> </td> </tr>
     *       <tr id="entity-curlyeqsucc"><td> <code title="">curlyeqsucc;</code> </td> <td> U+022DF </td> <td> <span class="glyph" title="">⋟</span> </td> </tr>
     *       <tr id="entity-curlyvee"><td> <code title="">curlyvee;</code> </td> <td> U+022CE </td> <td> <span class="glyph" title="">⋎</span> </td> </tr>
     *       <tr id="entity-curlywedge"><td> <code title="">curlywedge;</code> </td> <td> U+022CF </td> <td> <span class="glyph" title="">⋏</span> </td> </tr>
     *       <tr id="entity-curren"><td> <code title="">curren;</code> </td> <td> U+000A4 </td> <td> <span class="glyph" title="">¤</span> </td> </tr>
     *       <tr class="impl" id="entity-curren-legacy"><td> <code title="">curren</code> </td> <td> U+000A4 </td> <td> <span title="">¤</span> </td> </tr>
     *       <tr id="entity-curvearrowleft"><td> <code title="">curvearrowleft;</code> </td> <td> U+021B6 </td> <td> <span class="glyph" title="">↶</span> </td> </tr>
     *       <tr id="entity-curvearrowright"><td> <code title="">curvearrowright;</code> </td> <td> U+021B7 </td> <td> <span class="glyph" title="">↷</span> </td> </tr>
     *       <tr id="entity-cuvee"><td> <code title="">cuvee;</code> </td> <td> U+022CE </td> <td> <span class="glyph" title="">⋎</span> </td> </tr>
     *       <tr id="entity-cuwed"><td> <code title="">cuwed;</code> </td> <td> U+022CF </td> <td> <span class="glyph" title="">⋏</span> </td> </tr>
     *       <tr id="entity-cwconint"><td> <code title="">cwconint;</code> </td> <td> U+02232 </td> <td> <span class="glyph" title="">∲</span> </td> </tr>
     *       <tr id="entity-cwint"><td> <code title="">cwint;</code> </td> <td> U+02231 </td> <td> <span class="glyph" title="">∱</span> </td> </tr>
     *       <tr id="entity-cylcty"><td> <code title="">cylcty;</code> </td> <td> U+0232D </td> <td> <span class="glyph" title="">⌭</span> </td> </tr>
     *       <tr id="entity-Dagger"><td> <code title="">Dagger;</code> </td> <td> U+02021 </td> <td> <span class="glyph" title="">‡</span> </td> </tr>
     *       <tr id="entity-dagger"><td> <code title="">dagger;</code> </td> <td> U+02020 </td> <td> <span class="glyph" title="">†</span> </td> </tr>
     *       <tr id="entity-daleth"><td> <code title="">daleth;</code> </td> <td> U+02138 </td> <td> <span class="glyph" title="">ℸ</span> </td> </tr>
     *       <tr id="entity-Darr"><td> <code title="">Darr;</code> </td> <td> U+021A1 </td> <td> <span class="glyph" title="">↡</span> </td> </tr>
     *       <tr id="entity-dArr"><td> <code title="">dArr;</code> </td> <td> U+021D3 </td> <td> <span class="glyph" title="">⇓</span> </td> </tr>
     *       <tr id="entity-darr"><td> <code title="">darr;</code> </td> <td> U+02193 </td> <td> <span class="glyph" title="">↓</span> </td> </tr>
     *       <tr id="entity-dash"><td> <code title="">dash;</code> </td> <td> U+02010 </td> <td> <span class="glyph" title="">‐</span> </td> </tr>
     *       <tr id="entity-Dashv"><td> <code title="">Dashv;</code> </td> <td> U+02AE4 </td> <td> <span class="glyph" title="">⫤</span> </td> </tr>
     *       <tr id="entity-dashv"><td> <code title="">dashv;</code> </td> <td> U+022A3 </td> <td> <span class="glyph" title="">⊣</span> </td> </tr>
     *       <tr id="entity-dbkarow"><td> <code title="">dbkarow;</code> </td> <td> U+0290F </td> <td> <span class="glyph" title="">⤏</span> </td> </tr>
     *       <tr id="entity-dblac"><td> <code title="">dblac;</code> </td> <td> U+002DD </td> <td> <span class="glyph" title="">˝</span> </td> </tr>
     *       <tr id="entity-Dcaron"><td> <code title="">Dcaron;</code> </td> <td> U+0010E </td> <td> <span class="glyph" title="">Ď</span> </td> </tr>
     *       <tr id="entity-dcaron"><td> <code title="">dcaron;</code> </td> <td> U+0010F </td> <td> <span class="glyph" title="">ď</span> </td> </tr>
     *       <tr id="entity-Dcy"><td> <code title="">Dcy;</code> </td> <td> U+00414 </td> <td> <span class="glyph" title="">Д</span> </td> </tr>
     *       <tr id="entity-dcy"><td> <code title="">dcy;</code> </td> <td> U+00434 </td> <td> <span class="glyph" title="">д</span> </td> </tr>
     *       <tr id="entity-DD"><td> <code title="">DD;</code> </td> <td> U+02145 </td> <td> <span class="glyph" title="">ⅅ</span> </td> </tr>
     *       <tr id="entity-dd"><td> <code title="">dd;</code> </td> <td> U+02146 </td> <td> <span class="glyph" title="">ⅆ</span> </td> </tr>
     *       <tr id="entity-ddagger"><td> <code title="">ddagger;</code> </td> <td> U+02021 </td> <td> <span class="glyph" title="">‡</span> </td> </tr>
     *       <tr id="entity-ddarr"><td> <code title="">ddarr;</code> </td> <td> U+021CA </td> <td> <span class="glyph" title="">⇊</span> </td> </tr>
     *       <tr id="entity-DDotrahd"><td> <code title="">DDotrahd;</code> </td> <td> U+02911 </td> <td> <span class="glyph" title="">⤑</span> </td> </tr>
     *       <tr id="entity-ddotseq"><td> <code title="">ddotseq;</code> </td> <td> U+02A77 </td> <td> <span class="glyph" title="">⩷</span> </td> </tr>
     *       <tr id="entity-deg"><td> <code title="">deg;</code> </td> <td> U+000B0 </td> <td> <span class="glyph" title="">°</span> </td> </tr>
     *       <tr class="impl" id="entity-deg-legacy"><td> <code title="">deg</code> </td> <td> U+000B0 </td> <td> <span title="">°</span> </td> </tr>
     *       <tr id="entity-Del"><td> <code title="">Del;</code> </td> <td> U+02207 </td> <td> <span class="glyph" title="">∇</span> </td> </tr>
     *       <tr id="entity-Delta"><td> <code title="">Delta;</code> </td> <td> U+00394 </td> <td> <span class="glyph" title="">Δ</span> </td> </tr>
     *       <tr id="entity-delta"><td> <code title="">delta;</code> </td> <td> U+003B4 </td> <td> <span class="glyph" title="">δ</span> </td> </tr>
     *       <tr id="entity-demptyv"><td> <code title="">demptyv;</code> </td> <td> U+029B1 </td> <td> <span class="glyph" title="">⦱</span> </td> </tr>
     *       <tr id="entity-dfisht"><td> <code title="">dfisht;</code> </td> <td> U+0297F </td> <td> <span class="glyph" title="">⥿</span> </td> </tr>
     *       <tr id="entity-Dfr"><td> <code title="">Dfr;</code> </td> <td> U+1D507 </td> <td> <span class="glyph" title="">𝔇</span> </td> </tr>
     *       <tr id="entity-dfr"><td> <code title="">dfr;</code> </td> <td> U+1D521 </td> <td> <span class="glyph" title="">𝔡</span> </td> </tr>
     *       <tr id="entity-dHar"><td> <code title="">dHar;</code> </td> <td> U+02965 </td> <td> <span class="glyph" title="">⥥</span> </td> </tr>
     *       <tr id="entity-dharl"><td> <code title="">dharl;</code> </td> <td> U+021C3 </td> <td> <span class="glyph" title="">⇃</span> </td> </tr>
     *       <tr id="entity-dharr"><td> <code title="">dharr;</code> </td> <td> U+021C2 </td> <td> <span class="glyph" title="">⇂</span> </td> </tr>
     *       <tr id="entity-DiacriticalAcute"><td> <code title="">DiacriticalAcute;</code> </td> <td> U+000B4 </td> <td> <span class="glyph" title="">´</span> </td> </tr>
     *       <tr id="entity-DiacriticalDot"><td> <code title="">DiacriticalDot;</code> </td> <td> U+002D9 </td> <td> <span class="glyph" title="">˙</span> </td> </tr>
     *       <tr id="entity-DiacriticalDoubleAcute"><td> <code title="">DiacriticalDoubleAcute;</code> </td> <td> U+002DD </td> <td> <span class="glyph" title="">˝</span> </td> </tr>
     *       <tr id="entity-DiacriticalGrave"><td> <code title="">DiacriticalGrave;</code> </td> <td> U+00060 </td> <td> <span class="glyph" title="">`</span> </td> </tr>
     *       <tr id="entity-DiacriticalTilde"><td> <code title="">DiacriticalTilde;</code> </td> <td> U+002DC </td> <td> <span class="glyph" title="">˜</span> </td> </tr>
     *       <tr id="entity-diam"><td> <code title="">diam;</code> </td> <td> U+022C4 </td> <td> <span class="glyph" title="">⋄</span> </td> </tr>
     *       <tr id="entity-Diamond"><td> <code title="">Diamond;</code> </td> <td> U+022C4 </td> <td> <span class="glyph" title="">⋄</span> </td> </tr>
     *       <tr id="entity-diamond"><td> <code title="">diamond;</code> </td> <td> U+022C4 </td> <td> <span class="glyph" title="">⋄</span> </td> </tr>
     *       <tr id="entity-diamondsuit"><td> <code title="">diamondsuit;</code> </td> <td> U+02666 </td> <td> <span class="glyph" title="">♦</span> </td> </tr>
     *       <tr id="entity-diams"><td> <code title="">diams;</code> </td> <td> U+02666 </td> <td> <span class="glyph" title="">♦</span> </td> </tr>
     *       <tr id="entity-die"><td> <code title="">die;</code> </td> <td> U+000A8 </td> <td> <span class="glyph" title="">¨</span> </td> </tr>
     *       <tr id="entity-DifferentialD"><td> <code title="">DifferentialD;</code> </td> <td> U+02146 </td> <td> <span class="glyph" title="">ⅆ</span> </td> </tr>
     *       <tr id="entity-digamma"><td> <code title="">digamma;</code> </td> <td> U+003DD </td> <td> <span class="glyph" title="">ϝ</span> </td> </tr>
     *       <tr id="entity-disin"><td> <code title="">disin;</code> </td> <td> U+022F2 </td> <td> <span class="glyph" title="">⋲</span> </td> </tr>
     *       <tr id="entity-div"><td> <code title="">div;</code> </td> <td> U+000F7 </td> <td> <span class="glyph" title="">÷</span> </td> </tr>
     *       <tr id="entity-divide"><td> <code title="">divide;</code> </td> <td> U+000F7 </td> <td> <span class="glyph" title="">÷</span> </td> </tr>
     *       <tr class="impl" id="entity-divide-legacy"><td> <code title="">divide</code> </td> <td> U+000F7 </td> <td> <span title="">÷</span> </td> </tr>
     *       <tr id="entity-divideontimes"><td> <code title="">divideontimes;</code> </td> <td> U+022C7 </td> <td> <span class="glyph" title="">⋇</span> </td> </tr>
     *       <tr id="entity-divonx"><td> <code title="">divonx;</code> </td> <td> U+022C7 </td> <td> <span class="glyph" title="">⋇</span> </td> </tr>
     *       <tr id="entity-DJcy"><td> <code title="">DJcy;</code> </td> <td> U+00402 </td> <td> <span class="glyph" title="">Ђ</span> </td> </tr>
     *       <tr id="entity-djcy"><td> <code title="">djcy;</code> </td> <td> U+00452 </td> <td> <span class="glyph" title="">ђ</span> </td> </tr>
     *       <tr id="entity-dlcorn"><td> <code title="">dlcorn;</code> </td> <td> U+0231E </td> <td> <span class="glyph" title="">⌞</span> </td> </tr>
     *       <tr id="entity-dlcrop"><td> <code title="">dlcrop;</code> </td> <td> U+0230D </td> <td> <span class="glyph" title="">⌍</span> </td> </tr>
     *       <tr id="entity-dollar"><td> <code title="">dollar;</code> </td> <td> U+00024 </td> <td> <span class="glyph" title="">$</span> </td> </tr>
     *       <tr id="entity-Dopf"><td> <code title="">Dopf;</code> </td> <td> U+1D53B </td> <td> <span class="glyph" title="">𝔻</span> </td> </tr>
     *       <tr id="entity-dopf"><td> <code title="">dopf;</code> </td> <td> U+1D555 </td> <td> <span class="glyph" title="">𝕕</span> </td> </tr>
     *       <tr id="entity-Dot"><td> <code title="">Dot;</code> </td> <td> U+000A8 </td> <td> <span class="glyph" title="">¨</span> </td> </tr>
     *       <tr id="entity-dot"><td> <code title="">dot;</code> </td> <td> U+002D9 </td> <td> <span class="glyph" title="">˙</span> </td> </tr>
     *       <tr id="entity-DotDot"><td> <code title="">DotDot;</code> </td> <td> U+020DC </td> <td> <span class="glyph composition" title="">◌⃜</span> </td> </tr>
     *       <tr id="entity-doteq"><td> <code title="">doteq;</code> </td> <td> U+02250 </td> <td> <span class="glyph" title="">≐</span> </td> </tr>
     *       <tr id="entity-doteqdot"><td> <code title="">doteqdot;</code> </td> <td> U+02251 </td> <td> <span class="glyph" title="">≑</span> </td> </tr>
     *       <tr id="entity-DotEqual"><td> <code title="">DotEqual;</code> </td> <td> U+02250 </td> <td> <span class="glyph" title="">≐</span> </td> </tr>
     *       <tr id="entity-dotminus"><td> <code title="">dotminus;</code> </td> <td> U+02238 </td> <td> <span class="glyph" title="">∸</span> </td> </tr>
     *       <tr id="entity-dotplus"><td> <code title="">dotplus;</code> </td> <td> U+02214 </td> <td> <span class="glyph" title="">∔</span> </td> </tr>
     *       <tr id="entity-dotsquare"><td> <code title="">dotsquare;</code> </td> <td> U+022A1 </td> <td> <span class="glyph" title="">⊡</span> </td> </tr>
     *       <tr id="entity-doublebarwedge"><td> <code title="">doublebarwedge;</code> </td> <td> U+02306 </td> <td> <span class="glyph" title="">⌆</span> </td> </tr>
     *       <tr id="entity-DoubleContourIntegral"><td> <code title="">DoubleContourIntegral;</code> </td> <td> U+0222F </td> <td> <span class="glyph" title="">∯</span> </td> </tr>
     *       <tr id="entity-DoubleDot"><td> <code title="">DoubleDot;</code> </td> <td> U+000A8 </td> <td> <span class="glyph" title="">¨</span> </td> </tr>
     *       <tr id="entity-DoubleDownArrow"><td> <code title="">DoubleDownArrow;</code> </td> <td> U+021D3 </td> <td> <span class="glyph" title="">⇓</span> </td> </tr>
     *       <tr id="entity-DoubleLeftArrow"><td> <code title="">DoubleLeftArrow;</code> </td> <td> U+021D0 </td> <td> <span class="glyph" title="">⇐</span> </td> </tr>
     *       <tr id="entity-DoubleLeftRightArrow"><td> <code title="">DoubleLeftRightArrow;</code> </td> <td> U+021D4 </td> <td> <span class="glyph" title="">⇔</span> </td> </tr>
     *       <tr id="entity-DoubleLeftTee"><td> <code title="">DoubleLeftTee;</code> </td> <td> U+02AE4 </td> <td> <span class="glyph" title="">⫤</span> </td> </tr>
     *       <tr id="entity-DoubleLongLeftArrow"><td> <code title="">DoubleLongLeftArrow;</code> </td> <td> U+027F8 </td> <td> <span class="glyph" title="">⟸</span> </td> </tr>
     *       <tr id="entity-DoubleLongLeftRightArrow"><td> <code title="">DoubleLongLeftRightArrow;</code> </td> <td> U+027FA </td> <td> <span class="glyph" title="">⟺</span> </td> </tr>
     *       <tr id="entity-DoubleLongRightArrow"><td> <code title="">DoubleLongRightArrow;</code> </td> <td> U+027F9 </td> <td> <span class="glyph" title="">⟹</span> </td> </tr>
     *       <tr id="entity-DoubleRightArrow"><td> <code title="">DoubleRightArrow;</code> </td> <td> U+021D2 </td> <td> <span class="glyph" title="">⇒</span> </td> </tr>
     *       <tr id="entity-DoubleRightTee"><td> <code title="">DoubleRightTee;</code> </td> <td> U+022A8 </td> <td> <span class="glyph" title="">⊨</span> </td> </tr>
     *       <tr id="entity-DoubleUpArrow"><td> <code title="">DoubleUpArrow;</code> </td> <td> U+021D1 </td> <td> <span class="glyph" title="">⇑</span> </td> </tr>
     *       <tr id="entity-DoubleUpDownArrow"><td> <code title="">DoubleUpDownArrow;</code> </td> <td> U+021D5 </td> <td> <span class="glyph" title="">⇕</span> </td> </tr>
     *       <tr id="entity-DoubleVerticalBar"><td> <code title="">DoubleVerticalBar;</code> </td> <td> U+02225 </td> <td> <span class="glyph" title="">∥</span> </td> </tr>
     *       <tr id="entity-DownArrow"><td> <code title="">DownArrow;</code> </td> <td> U+02193 </td> <td> <span class="glyph" title="">↓</span> </td> </tr>
     *       <tr id="entity-Downarrow"><td> <code title="">Downarrow;</code> </td> <td> U+021D3 </td> <td> <span class="glyph" title="">⇓</span> </td> </tr>
     *       <tr id="entity-downarrow"><td> <code title="">downarrow;</code> </td> <td> U+02193 </td> <td> <span class="glyph" title="">↓</span> </td> </tr>
     *       <tr id="entity-DownArrowBar"><td> <code title="">DownArrowBar;</code> </td> <td> U+02913 </td> <td> <span class="glyph" title="">⤓</span> </td> </tr>
     *       <tr id="entity-DownArrowUpArrow"><td> <code title="">DownArrowUpArrow;</code> </td> <td> U+021F5 </td> <td> <span class="glyph" title="">⇵</span> </td> </tr>
     *       <tr id="entity-DownBreve"><td> <code title="">DownBreve;</code> </td> <td> U+00311 </td> <td> <span class="glyph composition" title="">◌̑</span> </td> </tr>
     *       <tr id="entity-downdownarrows"><td> <code title="">downdownarrows;</code> </td> <td> U+021CA </td> <td> <span class="glyph" title="">⇊</span> </td> </tr>
     *       <tr id="entity-downharpoonleft"><td> <code title="">downharpoonleft;</code> </td> <td> U+021C3 </td> <td> <span class="glyph" title="">⇃</span> </td> </tr>
     *       <tr id="entity-downharpoonright"><td> <code title="">downharpoonright;</code> </td> <td> U+021C2 </td> <td> <span class="glyph" title="">⇂</span> </td> </tr>
     *       <tr id="entity-DownLeftRightVector"><td> <code title="">DownLeftRightVector;</code> </td> <td> U+02950 </td> <td> <span class="glyph" title="">⥐</span> </td> </tr>
     *       <tr id="entity-DownLeftTeeVector"><td> <code title="">DownLeftTeeVector;</code> </td> <td> U+0295E </td> <td> <span class="glyph" title="">⥞</span> </td> </tr>
     *       <tr id="entity-DownLeftVector"><td> <code title="">DownLeftVector;</code> </td> <td> U+021BD </td> <td> <span class="glyph" title="">↽</span> </td> </tr>
     *       <tr id="entity-DownLeftVectorBar"><td> <code title="">DownLeftVectorBar;</code> </td> <td> U+02956 </td> <td> <span class="glyph" title="">⥖</span> </td> </tr>
     *       <tr id="entity-DownRightTeeVector"><td> <code title="">DownRightTeeVector;</code> </td> <td> U+0295F </td> <td> <span class="glyph" title="">⥟</span> </td> </tr>
     *       <tr id="entity-DownRightVector"><td> <code title="">DownRightVector;</code> </td> <td> U+021C1 </td> <td> <span class="glyph" title="">⇁</span> </td> </tr>
     *       <tr id="entity-DownRightVectorBar"><td> <code title="">DownRightVectorBar;</code> </td> <td> U+02957 </td> <td> <span class="glyph" title="">⥗</span> </td> </tr>
     *       <tr id="entity-DownTee"><td> <code title="">DownTee;</code> </td> <td> U+022A4 </td> <td> <span class="glyph" title="">⊤</span> </td> </tr>
     *       <tr id="entity-DownTeeArrow"><td> <code title="">DownTeeArrow;</code> </td> <td> U+021A7 </td> <td> <span class="glyph" title="">↧</span> </td> </tr>
     *       <tr id="entity-drbkarow"><td> <code title="">drbkarow;</code> </td> <td> U+02910 </td> <td> <span class="glyph" title="">⤐</span> </td> </tr>
     *       <tr id="entity-drcorn"><td> <code title="">drcorn;</code> </td> <td> U+0231F </td> <td> <span class="glyph" title="">⌟</span> </td> </tr>
     *       <tr id="entity-drcrop"><td> <code title="">drcrop;</code> </td> <td> U+0230C </td> <td> <span class="glyph" title="">⌌</span> </td> </tr>
     *       <tr id="entity-Dscr"><td> <code title="">Dscr;</code> </td> <td> U+1D49F </td> <td> <span class="glyph" title="">𝒟</span> </td> </tr>
     *       <tr id="entity-dscr"><td> <code title="">dscr;</code> </td> <td> U+1D4B9 </td> <td> <span class="glyph" title="">𝒹</span> </td> </tr>
     *       <tr id="entity-DScy"><td> <code title="">DScy;</code> </td> <td> U+00405 </td> <td> <span class="glyph" title="">Ѕ</span> </td> </tr>
     *       <tr id="entity-dscy"><td> <code title="">dscy;</code> </td> <td> U+00455 </td> <td> <span class="glyph" title="">ѕ</span> </td> </tr>
     *       <tr id="entity-dsol"><td> <code title="">dsol;</code> </td> <td> U+029F6 </td> <td> <span class="glyph" title="">⧶</span> </td> </tr>
     *       <tr id="entity-Dstrok"><td> <code title="">Dstrok;</code> </td> <td> U+00110 </td> <td> <span class="glyph" title="">Đ</span> </td> </tr>
     *       <tr id="entity-dstrok"><td> <code title="">dstrok;</code> </td> <td> U+00111 </td> <td> <span class="glyph" title="">đ</span> </td> </tr>
     *       <tr id="entity-dtdot"><td> <code title="">dtdot;</code> </td> <td> U+022F1 </td> <td> <span class="glyph" title="">⋱</span> </td> </tr>
     *       <tr id="entity-dtri"><td> <code title="">dtri;</code> </td> <td> U+025BF </td> <td> <span class="glyph" title="">▿</span> </td> </tr>
     *       <tr id="entity-dtrif"><td> <code title="">dtrif;</code> </td> <td> U+025BE </td> <td> <span class="glyph" title="">▾</span> </td> </tr>
     *       <tr id="entity-duarr"><td> <code title="">duarr;</code> </td> <td> U+021F5 </td> <td> <span class="glyph" title="">⇵</span> </td> </tr>
     *       <tr id="entity-duhar"><td> <code title="">duhar;</code> </td> <td> U+0296F </td> <td> <span class="glyph" title="">⥯</span> </td> </tr>
     *       <tr id="entity-dwangle"><td> <code title="">dwangle;</code> </td> <td> U+029A6 </td> <td> <span class="glyph" title="">⦦</span> </td> </tr>
     *       <tr id="entity-DZcy"><td> <code title="">DZcy;</code> </td> <td> U+0040F </td> <td> <span class="glyph" title="">Џ</span> </td> </tr>
     *       <tr id="entity-dzcy"><td> <code title="">dzcy;</code> </td> <td> U+0045F </td> <td> <span class="glyph" title="">џ</span> </td> </tr>
     *       <tr id="entity-dzigrarr"><td> <code title="">dzigrarr;</code> </td> <td> U+027FF </td> <td> <span class="glyph" title="">⟿</span> </td> </tr>
     *       <tr id="entity-Eacute"><td> <code title="">Eacute;</code> </td> <td> U+000C9 </td> <td> <span class="glyph" title="">É</span> </td> </tr>
     *       <tr class="impl" id="entity-Eacute-legacy"><td> <code title="">Eacute</code> </td> <td> U+000C9 </td> <td> <span title="">É</span> </td> </tr>
     *       <tr id="entity-eacute"><td> <code title="">eacute;</code> </td> <td> U+000E9 </td> <td> <span class="glyph" title="">é</span> </td> </tr>
     *       <tr class="impl" id="entity-eacute-legacy"><td> <code title="">eacute</code> </td> <td> U+000E9 </td> <td> <span title="">é</span> </td> </tr>
     *       <tr id="entity-easter"><td> <code title="">easter;</code> </td> <td> U+02A6E </td> <td> <span class="glyph" title="">⩮</span> </td> </tr>
     *       <tr id="entity-Ecaron"><td> <code title="">Ecaron;</code> </td> <td> U+0011A </td> <td> <span class="glyph" title="">Ě</span> </td> </tr>
     *       <tr id="entity-ecaron"><td> <code title="">ecaron;</code> </td> <td> U+0011B </td> <td> <span class="glyph" title="">ě</span> </td> </tr>
     *       <tr id="entity-ecir"><td> <code title="">ecir;</code> </td> <td> U+02256 </td> <td> <span class="glyph" title="">≖</span> </td> </tr>
     *       <tr id="entity-Ecirc"><td> <code title="">Ecirc;</code> </td> <td> U+000CA </td> <td> <span class="glyph" title="">Ê</span> </td> </tr>
     *       <tr class="impl" id="entity-Ecirc-legacy"><td> <code title="">Ecirc</code> </td> <td> U+000CA </td> <td> <span title="">Ê</span> </td> </tr>
     *       <tr id="entity-ecirc"><td> <code title="">ecirc;</code> </td> <td> U+000EA </td> <td> <span class="glyph" title="">ê</span> </td> </tr>
     *       <tr class="impl" id="entity-ecirc-legacy"><td> <code title="">ecirc</code> </td> <td> U+000EA </td> <td> <span title="">ê</span> </td> </tr>
     *       <tr id="entity-ecolon"><td> <code title="">ecolon;</code> </td> <td> U+02255 </td> <td> <span class="glyph" title="">≕</span> </td> </tr>
     *       <tr id="entity-Ecy"><td> <code title="">Ecy;</code> </td> <td> U+0042D </td> <td> <span class="glyph" title="">Э</span> </td> </tr>
     *       <tr id="entity-ecy"><td> <code title="">ecy;</code> </td> <td> U+0044D </td> <td> <span class="glyph" title="">э</span> </td> </tr>
     *       <tr id="entity-eDDot"><td> <code title="">eDDot;</code> </td> <td> U+02A77 </td> <td> <span class="glyph" title="">⩷</span> </td> </tr>
     *       <tr id="entity-Edot"><td> <code title="">Edot;</code> </td> <td> U+00116 </td> <td> <span class="glyph" title="">Ė</span> </td> </tr>
     *       <tr id="entity-eDot"><td> <code title="">eDot;</code> </td> <td> U+02251 </td> <td> <span class="glyph" title="">≑</span> </td> </tr>
     *       <tr id="entity-edot"><td> <code title="">edot;</code> </td> <td> U+00117 </td> <td> <span class="glyph" title="">ė</span> </td> </tr>
     *       <tr id="entity-ee"><td> <code title="">ee;</code> </td> <td> U+02147 </td> <td> <span class="glyph" title="">ⅇ</span> </td> </tr>
     *       <tr id="entity-efDot"><td> <code title="">efDot;</code> </td> <td> U+02252 </td> <td> <span class="glyph" title="">≒</span> </td> </tr>
     *       <tr id="entity-Efr"><td> <code title="">Efr;</code> </td> <td> U+1D508 </td> <td> <span class="glyph" title="">𝔈</span> </td> </tr>
     *       <tr id="entity-efr"><td> <code title="">efr;</code> </td> <td> U+1D522 </td> <td> <span class="glyph" title="">𝔢</span> </td> </tr>
     *       <tr id="entity-eg"><td> <code title="">eg;</code> </td> <td> U+02A9A </td> <td> <span class="glyph" title="">⪚</span> </td> </tr>
     *       <tr id="entity-Egrave"><td> <code title="">Egrave;</code> </td> <td> U+000C8 </td> <td> <span class="glyph" title="">È</span> </td> </tr>
     *       <tr class="impl" id="entity-Egrave-legacy"><td> <code title="">Egrave</code> </td> <td> U+000C8 </td> <td> <span title="">È</span> </td> </tr>
     *       <tr id="entity-egrave"><td> <code title="">egrave;</code> </td> <td> U+000E8 </td> <td> <span class="glyph" title="">è</span> </td> </tr>
     *       <tr class="impl" id="entity-egrave-legacy"><td> <code title="">egrave</code> </td> <td> U+000E8 </td> <td> <span title="">è</span> </td> </tr>
     *       <tr id="entity-egs"><td> <code title="">egs;</code> </td> <td> U+02A96 </td> <td> <span class="glyph" title="">⪖</span> </td> </tr>
     *       <tr id="entity-egsdot"><td> <code title="">egsdot;</code> </td> <td> U+02A98 </td> <td> <span class="glyph" title="">⪘</span> </td> </tr>
     *       <tr id="entity-el"><td> <code title="">el;</code> </td> <td> U+02A99 </td> <td> <span class="glyph" title="">⪙</span> </td> </tr>
     *       <tr id="entity-Element"><td> <code title="">Element;</code> </td> <td> U+02208 </td> <td> <span class="glyph" title="">∈</span> </td> </tr>
     *       <tr id="entity-elinters"><td> <code title="">elinters;</code> </td> <td> U+023E7 </td> <td> <span class="glyph" title="">⏧</span> </td> </tr>
     *       <tr id="entity-ell"><td> <code title="">ell;</code> </td> <td> U+02113 </td> <td> <span class="glyph" title="">ℓ</span> </td> </tr>
     *       <tr id="entity-els"><td> <code title="">els;</code> </td> <td> U+02A95 </td> <td> <span class="glyph" title="">⪕</span> </td> </tr>
     *       <tr id="entity-elsdot"><td> <code title="">elsdot;</code> </td> <td> U+02A97 </td> <td> <span class="glyph" title="">⪗</span> </td> </tr>
     *       <tr id="entity-Emacr"><td> <code title="">Emacr;</code> </td> <td> U+00112 </td> <td> <span class="glyph" title="">Ē</span> </td> </tr>
     *       <tr id="entity-emacr"><td> <code title="">emacr;</code> </td> <td> U+00113 </td> <td> <span class="glyph" title="">ē</span> </td> </tr>
     *       <tr id="entity-empty"><td> <code title="">empty;</code> </td> <td> U+02205 </td> <td> <span class="glyph" title="">∅</span> </td> </tr>
     *       <tr id="entity-emptyset"><td> <code title="">emptyset;</code> </td> <td> U+02205 </td> <td> <span class="glyph" title="">∅</span> </td> </tr>
     *       <tr id="entity-EmptySmallSquare"><td> <code title="">EmptySmallSquare;</code> </td> <td> U+025FB </td> <td> <span class="glyph" title="">◻</span> </td> </tr>
     *       <tr id="entity-emptyv"><td> <code title="">emptyv;</code> </td> <td> U+02205 </td> <td> <span class="glyph" title="">∅</span> </td> </tr>
     *       <tr id="entity-EmptyVerySmallSquare"><td> <code title="">EmptyVerySmallSquare;</code> </td> <td> U+025AB </td> <td> <span class="glyph" title="">▫</span> </td> </tr>
     *       <tr id="entity-emsp"><td> <code title="">emsp;</code> </td> <td> U+02003 </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-emsp13"><td> <code title="">emsp13;</code> </td> <td> U+02004 </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-emsp14"><td> <code title="">emsp14;</code> </td> <td> U+02005 </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-ENG"><td> <code title="">ENG;</code> </td> <td> U+0014A </td> <td> <span class="glyph" title="">Ŋ</span> </td> </tr>
     *       <tr id="entity-eng"><td> <code title="">eng;</code> </td> <td> U+0014B </td> <td> <span class="glyph" title="">ŋ</span> </td> </tr>
     *       <tr id="entity-ensp"><td> <code title="">ensp;</code> </td> <td> U+02002 </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-Eogon"><td> <code title="">Eogon;</code> </td> <td> U+00118 </td> <td> <span class="glyph" title="">Ę</span> </td> </tr>
     *       <tr id="entity-eogon"><td> <code title="">eogon;</code> </td> <td> U+00119 </td> <td> <span class="glyph" title="">ę</span> </td> </tr>
     *       <tr id="entity-Eopf"><td> <code title="">Eopf;</code> </td> <td> U+1D53C </td> <td> <span class="glyph" title="">𝔼</span> </td> </tr>
     *       <tr id="entity-eopf"><td> <code title="">eopf;</code> </td> <td> U+1D556 </td> <td> <span class="glyph" title="">𝕖</span> </td> </tr>
     *       <tr id="entity-epar"><td> <code title="">epar;</code> </td> <td> U+022D5 </td> <td> <span class="glyph" title="">⋕</span> </td> </tr>
     *       <tr id="entity-eparsl"><td> <code title="">eparsl;</code> </td> <td> U+029E3 </td> <td> <span class="glyph" title="">⧣</span> </td> </tr>
     *       <tr id="entity-eplus"><td> <code title="">eplus;</code> </td> <td> U+02A71 </td> <td> <span class="glyph" title="">⩱</span> </td> </tr>
     *       <tr id="entity-epsi"><td> <code title="">epsi;</code> </td> <td> U+003B5 </td> <td> <span class="glyph" title="">ε</span> </td> </tr>
     *       <tr id="entity-Epsilon"><td> <code title="">Epsilon;</code> </td> <td> U+00395 </td> <td> <span class="glyph" title="">Ε</span> </td> </tr>
     *       <tr id="entity-epsilon"><td> <code title="">epsilon;</code> </td> <td> U+003B5 </td> <td> <span class="glyph" title="">ε</span> </td> </tr>
     *       <tr id="entity-epsiv"><td> <code title="">epsiv;</code> </td> <td> U+003F5 </td> <td> <span class="glyph" title="">ϵ</span> </td> </tr>
     *       <tr id="entity-eqcirc"><td> <code title="">eqcirc;</code> </td> <td> U+02256 </td> <td> <span class="glyph" title="">≖</span> </td> </tr>
     *       <tr id="entity-eqcolon"><td> <code title="">eqcolon;</code> </td> <td> U+02255 </td> <td> <span class="glyph" title="">≕</span> </td> </tr>
     *       <tr id="entity-eqsim"><td> <code title="">eqsim;</code> </td> <td> U+02242 </td> <td> <span class="glyph" title="">≂</span> </td> </tr>
     *       <tr id="entity-eqslantgtr"><td> <code title="">eqslantgtr;</code> </td> <td> U+02A96 </td> <td> <span class="glyph" title="">⪖</span> </td> </tr>
     *       <tr id="entity-eqslantless"><td> <code title="">eqslantless;</code> </td> <td> U+02A95 </td> <td> <span class="glyph" title="">⪕</span> </td> </tr>
     *       <tr id="entity-Equal"><td> <code title="">Equal;</code> </td> <td> U+02A75 </td> <td> <span class="glyph" title="">⩵</span> </td> </tr>
     *       <tr id="entity-equals"><td> <code title="">equals;</code> </td> <td> U+0003D </td> <td> <span class="glyph" title="">=</span> </td> </tr>
     *       <tr id="entity-EqualTilde"><td> <code title="">EqualTilde;</code> </td> <td> U+02242 </td> <td> <span class="glyph" title="">≂</span> </td> </tr>
     *       <tr id="entity-equest"><td> <code title="">equest;</code> </td> <td> U+0225F </td> <td> <span class="glyph" title="">≟</span> </td> </tr>
     *       <tr id="entity-Equilibrium"><td> <code title="">Equilibrium;</code> </td> <td> U+021CC </td> <td> <span class="glyph" title="">⇌</span> </td> </tr>
     *       <tr id="entity-equiv"><td> <code title="">equiv;</code> </td> <td> U+02261 </td> <td> <span class="glyph" title="">≡</span> </td> </tr>
     *       <tr id="entity-equivDD"><td> <code title="">equivDD;</code> </td> <td> U+02A78 </td> <td> <span class="glyph" title="">⩸</span> </td> </tr>
     *       <tr id="entity-eqvparsl"><td> <code title="">eqvparsl;</code> </td> <td> U+029E5 </td> <td> <span class="glyph" title="">⧥</span> </td> </tr>
     *       <tr id="entity-erarr"><td> <code title="">erarr;</code> </td> <td> U+02971 </td> <td> <span class="glyph" title="">⥱</span> </td> </tr>
     *       <tr id="entity-erDot"><td> <code title="">erDot;</code> </td> <td> U+02253 </td> <td> <span class="glyph" title="">≓</span> </td> </tr>
     *       <tr id="entity-Escr"><td> <code title="">Escr;</code> </td> <td> U+02130 </td> <td> <span class="glyph" title="">ℰ</span> </td> </tr>
     *       <tr id="entity-escr"><td> <code title="">escr;</code> </td> <td> U+0212F </td> <td> <span class="glyph" title="">ℯ</span> </td> </tr>
     *       <tr id="entity-esdot"><td> <code title="">esdot;</code> </td> <td> U+02250 </td> <td> <span class="glyph" title="">≐</span> </td> </tr>
     *       <tr id="entity-Esim"><td> <code title="">Esim;</code> </td> <td> U+02A73 </td> <td> <span class="glyph" title="">⩳</span> </td> </tr>
     *       <tr id="entity-esim"><td> <code title="">esim;</code> </td> <td> U+02242 </td> <td> <span class="glyph" title="">≂</span> </td> </tr>
     *       <tr id="entity-Eta"><td> <code title="">Eta;</code> </td> <td> U+00397 </td> <td> <span class="glyph" title="">Η</span> </td> </tr>
     *       <tr id="entity-eta"><td> <code title="">eta;</code> </td> <td> U+003B7 </td> <td> <span class="glyph" title="">η</span> </td> </tr>
     *       <tr id="entity-ETH"><td> <code title="">ETH;</code> </td> <td> U+000D0 </td> <td> <span class="glyph" title="">Ð</span> </td> </tr>
     *       <tr class="impl" id="entity-ETH-legacy"><td> <code title="">ETH</code> </td> <td> U+000D0 </td> <td> <span title="">Ð</span> </td> </tr>
     *       <tr id="entity-eth"><td> <code title="">eth;</code> </td> <td> U+000F0 </td> <td> <span class="glyph" title="">ð</span> </td> </tr>
     *       <tr class="impl" id="entity-eth-legacy"><td> <code title="">eth</code> </td> <td> U+000F0 </td> <td> <span title="">ð</span> </td> </tr>
     *       <tr id="entity-Euml"><td> <code title="">Euml;</code> </td> <td> U+000CB </td> <td> <span class="glyph" title="">Ë</span> </td> </tr>
     *       <tr class="impl" id="entity-Euml-legacy"><td> <code title="">Euml</code> </td> <td> U+000CB </td> <td> <span title="">Ë</span> </td> </tr>
     *       <tr id="entity-euml"><td> <code title="">euml;</code> </td> <td> U+000EB </td> <td> <span class="glyph" title="">ë</span> </td> </tr>
     *       <tr class="impl" id="entity-euml-legacy"><td> <code title="">euml</code> </td> <td> U+000EB </td> <td> <span title="">ë</span> </td> </tr>
     *       <tr id="entity-euro"><td> <code title="">euro;</code> </td> <td> U+020AC </td> <td> <span class="glyph" title="">€</span> </td> </tr>
     *       <tr id="entity-excl"><td> <code title="">excl;</code> </td> <td> U+00021 </td> <td> <span class="glyph" title="">!</span> </td> </tr>
     *       <tr id="entity-exist"><td> <code title="">exist;</code> </td> <td> U+02203 </td> <td> <span class="glyph" title="">∃</span> </td> </tr>
     *       <tr id="entity-Exists"><td> <code title="">Exists;</code> </td> <td> U+02203 </td> <td> <span class="glyph" title="">∃</span> </td> </tr>
     *       <tr id="entity-expectation"><td> <code title="">expectation;</code> </td> <td> U+02130 </td> <td> <span class="glyph" title="">ℰ</span> </td> </tr>
     *       <tr id="entity-ExponentialE"><td> <code title="">ExponentialE;</code> </td> <td> U+02147 </td> <td> <span class="glyph" title="">ⅇ</span> </td> </tr>
     *       <tr id="entity-exponentiale"><td> <code title="">exponentiale;</code> </td> <td> U+02147 </td> <td> <span class="glyph" title="">ⅇ</span> </td> </tr>
     *       <tr id="entity-fallingdotseq"><td> <code title="">fallingdotseq;</code> </td> <td> U+02252 </td> <td> <span class="glyph" title="">≒</span> </td> </tr>
     *       <tr id="entity-Fcy"><td> <code title="">Fcy;</code> </td> <td> U+00424 </td> <td> <span class="glyph" title="">Ф</span> </td> </tr>
     *       <tr id="entity-fcy"><td> <code title="">fcy;</code> </td> <td> U+00444 </td> <td> <span class="glyph" title="">ф</span> </td> </tr>
     *       <tr id="entity-female"><td> <code title="">female;</code> </td> <td> U+02640 </td> <td> <span class="glyph" title="">♀</span> </td> </tr>
     *       <tr id="entity-ffilig"><td> <code title="">ffilig;</code> </td> <td> U+0FB03 </td> <td> <span class="glyph" title="">ﬃ</span> </td> </tr>
     *       <tr id="entity-fflig"><td> <code title="">fflig;</code> </td> <td> U+0FB00 </td> <td> <span class="glyph" title="">ﬀ</span> </td> </tr>
     *       <tr id="entity-ffllig"><td> <code title="">ffllig;</code> </td> <td> U+0FB04 </td> <td> <span class="glyph" title="">ﬄ</span> </td> </tr>
     *       <tr id="entity-Ffr"><td> <code title="">Ffr;</code> </td> <td> U+1D509 </td> <td> <span class="glyph" title="">𝔉</span> </td> </tr>
     *       <tr id="entity-ffr"><td> <code title="">ffr;</code> </td> <td> U+1D523 </td> <td> <span class="glyph" title="">𝔣</span> </td> </tr>
     *       <tr id="entity-filig"><td> <code title="">filig;</code> </td> <td> U+0FB01 </td> <td> <span class="glyph" title="">ﬁ</span> </td> </tr>
     *       <tr id="entity-FilledSmallSquare"><td> <code title="">FilledSmallSquare;</code> </td> <td> U+025FC </td> <td> <span class="glyph" title="">◼</span> </td> </tr>
     *       <tr id="entity-FilledVerySmallSquare"><td> <code title="">FilledVerySmallSquare;</code> </td> <td> U+025AA </td> <td> <span class="glyph" title="">▪</span> </td> </tr>
     *       <tr id="entity-fjlig"><td> <code title="">fjlig;</code> </td> <td> U+00066 U+0006A </td> <td> <span class="glyph compound" title="">fj</span> </td> </tr>
     *       <tr id="entity-flat"><td> <code title="">flat;</code> </td> <td> U+0266D </td> <td> <span class="glyph" title="">♭</span> </td> </tr>
     *       <tr id="entity-fllig"><td> <code title="">fllig;</code> </td> <td> U+0FB02 </td> <td> <span class="glyph" title="">ﬂ</span> </td> </tr>
     *       <tr id="entity-fltns"><td> <code title="">fltns;</code> </td> <td> U+025B1 </td> <td> <span class="glyph" title="">▱</span> </td> </tr>
     *       <tr id="entity-fnof"><td> <code title="">fnof;</code> </td> <td> U+00192 </td> <td> <span class="glyph" title="">ƒ</span> </td> </tr>
     *       <tr id="entity-Fopf"><td> <code title="">Fopf;</code> </td> <td> U+1D53D </td> <td> <span class="glyph" title="">𝔽</span> </td> </tr>
     *       <tr id="entity-fopf"><td> <code title="">fopf;</code> </td> <td> U+1D557 </td> <td> <span class="glyph" title="">𝕗</span> </td> </tr>
     *       <tr id="entity-ForAll"><td> <code title="">ForAll;</code> </td> <td> U+02200 </td> <td> <span class="glyph" title="">∀</span> </td> </tr>
     *       <tr id="entity-forall"><td> <code title="">forall;</code> </td> <td> U+02200 </td> <td> <span class="glyph" title="">∀</span> </td> </tr>
     *       <tr id="entity-fork"><td> <code title="">fork;</code> </td> <td> U+022D4 </td> <td> <span class="glyph" title="">⋔</span> </td> </tr>
     *       <tr id="entity-forkv"><td> <code title="">forkv;</code> </td> <td> U+02AD9 </td> <td> <span class="glyph" title="">⫙</span> </td> </tr>
     *       <tr id="entity-Fouriertrf"><td> <code title="">Fouriertrf;</code> </td> <td> U+02131 </td> <td> <span class="glyph" title="">ℱ</span> </td> </tr>
     *       <tr id="entity-fpartint"><td> <code title="">fpartint;</code> </td> <td> U+02A0D </td> <td> <span class="glyph" title="">⨍</span> </td> </tr>
     *       <tr id="entity-frac12"><td> <code title="">frac12;</code> </td> <td> U+000BD </td> <td> <span class="glyph" title="">½</span> </td> </tr>
     *       <tr class="impl" id="entity-frac12-legacy"><td> <code title="">frac12</code> </td> <td> U+000BD </td> <td> <span title="">½</span> </td> </tr>
     *       <tr id="entity-frac13"><td> <code title="">frac13;</code> </td> <td> U+02153 </td> <td> <span class="glyph" title="">⅓</span> </td> </tr>
     *       <tr id="entity-frac14"><td> <code title="">frac14;</code> </td> <td> U+000BC </td> <td> <span class="glyph" title="">¼</span> </td> </tr>
     *       <tr class="impl" id="entity-frac14-legacy"><td> <code title="">frac14</code> </td> <td> U+000BC </td> <td> <span title="">¼</span> </td> </tr>
     *       <tr id="entity-frac15"><td> <code title="">frac15;</code> </td> <td> U+02155 </td> <td> <span class="glyph" title="">⅕</span> </td> </tr>
     *       <tr id="entity-frac16"><td> <code title="">frac16;</code> </td> <td> U+02159 </td> <td> <span class="glyph" title="">⅙</span> </td> </tr>
     *       <tr id="entity-frac18"><td> <code title="">frac18;</code> </td> <td> U+0215B </td> <td> <span class="glyph" title="">⅛</span> </td> </tr>
     *       <tr id="entity-frac23"><td> <code title="">frac23;</code> </td> <td> U+02154 </td> <td> <span class="glyph" title="">⅔</span> </td> </tr>
     *       <tr id="entity-frac25"><td> <code title="">frac25;</code> </td> <td> U+02156 </td> <td> <span class="glyph" title="">⅖</span> </td> </tr>
     *       <tr id="entity-frac34"><td> <code title="">frac34;</code> </td> <td> U+000BE </td> <td> <span class="glyph" title="">¾</span> </td> </tr>
     *       <tr class="impl" id="entity-frac34-legacy"><td> <code title="">frac34</code> </td> <td> U+000BE </td> <td> <span title="">¾</span> </td> </tr>
     *       <tr id="entity-frac35"><td> <code title="">frac35;</code> </td> <td> U+02157 </td> <td> <span class="glyph" title="">⅗</span> </td> </tr>
     *       <tr id="entity-frac38"><td> <code title="">frac38;</code> </td> <td> U+0215C </td> <td> <span class="glyph" title="">⅜</span> </td> </tr>
     *       <tr id="entity-frac45"><td> <code title="">frac45;</code> </td> <td> U+02158 </td> <td> <span class="glyph" title="">⅘</span> </td> </tr>
     *       <tr id="entity-frac56"><td> <code title="">frac56;</code> </td> <td> U+0215A </td> <td> <span class="glyph" title="">⅚</span> </td> </tr>
     *       <tr id="entity-frac58"><td> <code title="">frac58;</code> </td> <td> U+0215D </td> <td> <span class="glyph" title="">⅝</span> </td> </tr>
     *       <tr id="entity-frac78"><td> <code title="">frac78;</code> </td> <td> U+0215E </td> <td> <span class="glyph" title="">⅞</span> </td> </tr>
     *       <tr id="entity-frasl"><td> <code title="">frasl;</code> </td> <td> U+02044 </td> <td> <span class="glyph" title="">⁄</span> </td> </tr>
     *       <tr id="entity-frown"><td> <code title="">frown;</code> </td> <td> U+02322 </td> <td> <span class="glyph" title="">⌢</span> </td> </tr>
     *       <tr id="entity-Fscr"><td> <code title="">Fscr;</code> </td> <td> U+02131 </td> <td> <span class="glyph" title="">ℱ</span> </td> </tr>
     *       <tr id="entity-fscr"><td> <code title="">fscr;</code> </td> <td> U+1D4BB </td> <td> <span class="glyph" title="">𝒻</span> </td> </tr>
     *       <tr id="entity-gacute"><td> <code title="">gacute;</code> </td> <td> U+001F5 </td> <td> <span class="glyph" title="">ǵ</span> </td> </tr>
     *       <tr id="entity-Gamma"><td> <code title="">Gamma;</code> </td> <td> U+00393 </td> <td> <span class="glyph" title="">Γ</span> </td> </tr>
     *       <tr id="entity-gamma"><td> <code title="">gamma;</code> </td> <td> U+003B3 </td> <td> <span class="glyph" title="">γ</span> </td> </tr>
     *       <tr id="entity-Gammad"><td> <code title="">Gammad;</code> </td> <td> U+003DC </td> <td> <span class="glyph" title="">Ϝ</span> </td> </tr>
     *       <tr id="entity-gammad"><td> <code title="">gammad;</code> </td> <td> U+003DD </td> <td> <span class="glyph" title="">ϝ</span> </td> </tr>
     *       <tr id="entity-gap"><td> <code title="">gap;</code> </td> <td> U+02A86 </td> <td> <span class="glyph" title="">⪆</span> </td> </tr>
     *       <tr id="entity-Gbreve"><td> <code title="">Gbreve;</code> </td> <td> U+0011E </td> <td> <span class="glyph" title="">Ğ</span> </td> </tr>
     *       <tr id="entity-gbreve"><td> <code title="">gbreve;</code> </td> <td> U+0011F </td> <td> <span class="glyph" title="">ğ</span> </td> </tr>
     *       <tr id="entity-Gcedil"><td> <code title="">Gcedil;</code> </td> <td> U+00122 </td> <td> <span class="glyph" title="">Ģ</span> </td> </tr>
     *       <tr id="entity-Gcirc"><td> <code title="">Gcirc;</code> </td> <td> U+0011C </td> <td> <span class="glyph" title="">Ĝ</span> </td> </tr>
     *       <tr id="entity-gcirc"><td> <code title="">gcirc;</code> </td> <td> U+0011D </td> <td> <span class="glyph" title="">ĝ</span> </td> </tr>
     *       <tr id="entity-Gcy"><td> <code title="">Gcy;</code> </td> <td> U+00413 </td> <td> <span class="glyph" title="">Г</span> </td> </tr>
     *       <tr id="entity-gcy"><td> <code title="">gcy;</code> </td> <td> U+00433 </td> <td> <span class="glyph" title="">г</span> </td> </tr>
     *       <tr id="entity-Gdot"><td> <code title="">Gdot;</code> </td> <td> U+00120 </td> <td> <span class="glyph" title="">Ġ</span> </td> </tr>
     *       <tr id="entity-gdot"><td> <code title="">gdot;</code> </td> <td> U+00121 </td> <td> <span class="glyph" title="">ġ</span> </td> </tr>
     *       <tr id="entity-gE"><td> <code title="">gE;</code> </td> <td> U+02267 </td> <td> <span class="glyph" title="">≧</span> </td> </tr>
     *       <tr id="entity-ge"><td> <code title="">ge;</code> </td> <td> U+02265 </td> <td> <span class="glyph" title="">≥</span> </td> </tr>
     *       <tr id="entity-gEl"><td> <code title="">gEl;</code> </td> <td> U+02A8C </td> <td> <span class="glyph" title="">⪌</span> </td> </tr>
     *       <tr id="entity-gel"><td> <code title="">gel;</code> </td> <td> U+022DB </td> <td> <span class="glyph" title="">⋛</span> </td> </tr>
     *       <tr id="entity-geq"><td> <code title="">geq;</code> </td> <td> U+02265 </td> <td> <span class="glyph" title="">≥</span> </td> </tr>
     *       <tr id="entity-geqq"><td> <code title="">geqq;</code> </td> <td> U+02267 </td> <td> <span class="glyph" title="">≧</span> </td> </tr>
     *       <tr id="entity-geqslant"><td> <code title="">geqslant;</code> </td> <td> U+02A7E </td> <td> <span class="glyph" title="">⩾</span> </td> </tr>
     *       <tr id="entity-ges"><td> <code title="">ges;</code> </td> <td> U+02A7E </td> <td> <span class="glyph" title="">⩾</span> </td> </tr>
     *       <tr id="entity-gescc"><td> <code title="">gescc;</code> </td> <td> U+02AA9 </td> <td> <span class="glyph" title="">⪩</span> </td> </tr>
     *       <tr id="entity-gesdot"><td> <code title="">gesdot;</code> </td> <td> U+02A80 </td> <td> <span class="glyph" title="">⪀</span> </td> </tr>
     *       <tr id="entity-gesdoto"><td> <code title="">gesdoto;</code> </td> <td> U+02A82 </td> <td> <span class="glyph" title="">⪂</span> </td> </tr>
     *       <tr id="entity-gesdotol"><td> <code title="">gesdotol;</code> </td> <td> U+02A84 </td> <td> <span class="glyph" title="">⪄</span> </td> </tr>
     *       <tr id="entity-gesl"><td> <code title="">gesl;</code> </td> <td> U+022DB U+0FE00 </td> <td> <span class="glyph compound" title="">⋛︀</span> </td> </tr>
     *       <tr id="entity-gesles"><td> <code title="">gesles;</code> </td> <td> U+02A94 </td> <td> <span class="glyph" title="">⪔</span> </td> </tr>
     *       <tr id="entity-Gfr"><td> <code title="">Gfr;</code> </td> <td> U+1D50A </td> <td> <span class="glyph" title="">𝔊</span> </td> </tr>
     *       <tr id="entity-gfr"><td> <code title="">gfr;</code> </td> <td> U+1D524 </td> <td> <span class="glyph" title="">𝔤</span> </td> </tr>
     *       <tr id="entity-Gg"><td> <code title="">Gg;</code> </td> <td> U+022D9 </td> <td> <span class="glyph" title="">⋙</span> </td> </tr>
     *       <tr id="entity-gg"><td> <code title="">gg;</code> </td> <td> U+0226B </td> <td> <span class="glyph" title="">≫</span> </td> </tr>
     *       <tr id="entity-ggg"><td> <code title="">ggg;</code> </td> <td> U+022D9 </td> <td> <span class="glyph" title="">⋙</span> </td> </tr>
     *       <tr id="entity-gimel"><td> <code title="">gimel;</code> </td> <td> U+02137 </td> <td> <span class="glyph" title="">ℷ</span> </td> </tr>
     *       <tr id="entity-GJcy"><td> <code title="">GJcy;</code> </td> <td> U+00403 </td> <td> <span class="glyph" title="">Ѓ</span> </td> </tr>
     *       <tr id="entity-gjcy"><td> <code title="">gjcy;</code> </td> <td> U+00453 </td> <td> <span class="glyph" title="">ѓ</span> </td> </tr>
     *       <tr id="entity-gl"><td> <code title="">gl;</code> </td> <td> U+02277 </td> <td> <span class="glyph" title="">≷</span> </td> </tr>
     *       <tr id="entity-gla"><td> <code title="">gla;</code> </td> <td> U+02AA5 </td> <td> <span class="glyph" title="">⪥</span> </td> </tr>
     *       <tr id="entity-glE"><td> <code title="">glE;</code> </td> <td> U+02A92 </td> <td> <span class="glyph" title="">⪒</span> </td> </tr>
     *       <tr id="entity-glj"><td> <code title="">glj;</code> </td> <td> U+02AA4 </td> <td> <span class="glyph" title="">⪤</span> </td> </tr>
     *       <tr id="entity-gnap"><td> <code title="">gnap;</code> </td> <td> U+02A8A </td> <td> <span class="glyph" title="">⪊</span> </td> </tr>
     *       <tr id="entity-gnapprox"><td> <code title="">gnapprox;</code> </td> <td> U+02A8A </td> <td> <span class="glyph" title="">⪊</span> </td> </tr>
     *       <tr id="entity-gnE"><td> <code title="">gnE;</code> </td> <td> U+02269 </td> <td> <span class="glyph" title="">≩</span> </td> </tr>
     *       <tr id="entity-gne"><td> <code title="">gne;</code> </td> <td> U+02A88 </td> <td> <span class="glyph" title="">⪈</span> </td> </tr>
     *       <tr id="entity-gneq"><td> <code title="">gneq;</code> </td> <td> U+02A88 </td> <td> <span class="glyph" title="">⪈</span> </td> </tr>
     *       <tr id="entity-gneqq"><td> <code title="">gneqq;</code> </td> <td> U+02269 </td> <td> <span class="glyph" title="">≩</span> </td> </tr>
     *       <tr id="entity-gnsim"><td> <code title="">gnsim;</code> </td> <td> U+022E7 </td> <td> <span class="glyph" title="">⋧</span> </td> </tr>
     *       <tr id="entity-Gopf"><td> <code title="">Gopf;</code> </td> <td> U+1D53E </td> <td> <span class="glyph" title="">𝔾</span> </td> </tr>
     *       <tr id="entity-gopf"><td> <code title="">gopf;</code> </td> <td> U+1D558 </td> <td> <span class="glyph" title="">𝕘</span> </td> </tr>
     *       <tr id="entity-grave"><td> <code title="">grave;</code> </td> <td> U+00060 </td> <td> <span class="glyph" title="">`</span> </td> </tr>
     *       <tr id="entity-GreaterEqual"><td> <code title="">GreaterEqual;</code> </td> <td> U+02265 </td> <td> <span class="glyph" title="">≥</span> </td> </tr>
     *       <tr id="entity-GreaterEqualLess"><td> <code title="">GreaterEqualLess;</code> </td> <td> U+022DB </td> <td> <span class="glyph" title="">⋛</span> </td> </tr>
     *       <tr id="entity-GreaterFullEqual"><td> <code title="">GreaterFullEqual;</code> </td> <td> U+02267 </td> <td> <span class="glyph" title="">≧</span> </td> </tr>
     *       <tr id="entity-GreaterGreater"><td> <code title="">GreaterGreater;</code> </td> <td> U+02AA2 </td> <td> <span class="glyph" title="">⪢</span> </td> </tr>
     *       <tr id="entity-GreaterLess"><td> <code title="">GreaterLess;</code> </td> <td> U+02277 </td> <td> <span class="glyph" title="">≷</span> </td> </tr>
     *       <tr id="entity-GreaterSlantEqual"><td> <code title="">GreaterSlantEqual;</code> </td> <td> U+02A7E </td> <td> <span class="glyph" title="">⩾</span> </td> </tr>
     *       <tr id="entity-GreaterTilde"><td> <code title="">GreaterTilde;</code> </td> <td> U+02273 </td> <td> <span class="glyph" title="">≳</span> </td> </tr>
     *       <tr id="entity-Gscr"><td> <code title="">Gscr;</code> </td> <td> U+1D4A2 </td> <td> <span class="glyph" title="">𝒢</span> </td> </tr>
     *       <tr id="entity-gscr"><td> <code title="">gscr;</code> </td> <td> U+0210A </td> <td> <span class="glyph" title="">ℊ</span> </td> </tr>
     *       <tr id="entity-gsim"><td> <code title="">gsim;</code> </td> <td> U+02273 </td> <td> <span class="glyph" title="">≳</span> </td> </tr>
     *       <tr id="entity-gsime"><td> <code title="">gsime;</code> </td> <td> U+02A8E </td> <td> <span class="glyph" title="">⪎</span> </td> </tr>
     *       <tr id="entity-gsiml"><td> <code title="">gsiml;</code> </td> <td> U+02A90 </td> <td> <span class="glyph" title="">⪐</span> </td> </tr>
     *       <tr id="entity-GT"><td> <code title="">GT;</code> </td> <td> U+0003E </td> <td> <span class="glyph" title="">&gt;</span> </td> </tr>
     *       <tr class="impl" id="entity-GT-legacy"><td> <code title="">GT</code> </td> <td> U+0003E </td> <td> <span title="">&gt;</span> </td> </tr>
     *       <tr id="entity-Gt"><td> <code title="">Gt;</code> </td> <td> U+0226B </td> <td> <span class="glyph" title="">≫</span> </td> </tr>
     *       <tr id="entity-gt"><td> <code title="">gt;</code> </td> <td> U+0003E </td> <td> <span class="glyph" title="">&gt;</span> </td> </tr>
     *       <tr class="impl" id="entity-gt-legacy"><td> <code title="">gt</code> </td> <td> U+0003E </td> <td> <span title="">&gt;</span> </td> </tr>
     *       <tr id="entity-gtcc"><td> <code title="">gtcc;</code> </td> <td> U+02AA7 </td> <td> <span class="glyph" title="">⪧</span> </td> </tr>
     *       <tr id="entity-gtcir"><td> <code title="">gtcir;</code> </td> <td> U+02A7A </td> <td> <span class="glyph" title="">⩺</span> </td> </tr>
     *       <tr id="entity-gtdot"><td> <code title="">gtdot;</code> </td> <td> U+022D7 </td> <td> <span class="glyph" title="">⋗</span> </td> </tr>
     *       <tr id="entity-gtlPar"><td> <code title="">gtlPar;</code> </td> <td> U+02995 </td> <td> <span class="glyph" title="">⦕</span> </td> </tr>
     *       <tr id="entity-gtquest"><td> <code title="">gtquest;</code> </td> <td> U+02A7C </td> <td> <span class="glyph" title="">⩼</span> </td> </tr>
     *       <tr id="entity-gtrapprox"><td> <code title="">gtrapprox;</code> </td> <td> U+02A86 </td> <td> <span class="glyph" title="">⪆</span> </td> </tr>
     *       <tr id="entity-gtrarr"><td> <code title="">gtrarr;</code> </td> <td> U+02978 </td> <td> <span class="glyph" title="">⥸</span> </td> </tr>
     *       <tr id="entity-gtrdot"><td> <code title="">gtrdot;</code> </td> <td> U+022D7 </td> <td> <span class="glyph" title="">⋗</span> </td> </tr>
     *       <tr id="entity-gtreqless"><td> <code title="">gtreqless;</code> </td> <td> U+022DB </td> <td> <span class="glyph" title="">⋛</span> </td> </tr>
     *       <tr id="entity-gtreqqless"><td> <code title="">gtreqqless;</code> </td> <td> U+02A8C </td> <td> <span class="glyph" title="">⪌</span> </td> </tr>
     *       <tr id="entity-gtrless"><td> <code title="">gtrless;</code> </td> <td> U+02277 </td> <td> <span class="glyph" title="">≷</span> </td> </tr>
     *       <tr id="entity-gtrsim"><td> <code title="">gtrsim;</code> </td> <td> U+02273 </td> <td> <span class="glyph" title="">≳</span> </td> </tr>
     *       <tr id="entity-gvertneqq"><td> <code title="">gvertneqq;</code> </td> <td> U+02269 U+0FE00 </td> <td> <span class="glyph compound" title="">≩︀</span> </td> </tr>
     *       <tr id="entity-gvnE"><td> <code title="">gvnE;</code> </td> <td> U+02269 U+0FE00 </td> <td> <span class="glyph compound" title="">≩︀</span> </td> </tr>
     *       <tr id="entity-Hacek"><td> <code title="">Hacek;</code> </td> <td> U+002C7 </td> <td> <span class="glyph" title="">ˇ</span> </td> </tr>
     *       <tr id="entity-hairsp"><td> <code title="">hairsp;</code> </td> <td> U+0200A </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-half"><td> <code title="">half;</code> </td> <td> U+000BD </td> <td> <span class="glyph" title="">½</span> </td> </tr>
     *       <tr id="entity-hamilt"><td> <code title="">hamilt;</code> </td> <td> U+0210B </td> <td> <span class="glyph" title="">ℋ</span> </td> </tr>
     *       <tr id="entity-HARDcy"><td> <code title="">HARDcy;</code> </td> <td> U+0042A </td> <td> <span class="glyph" title="">Ъ</span> </td> </tr>
     *       <tr id="entity-hardcy"><td> <code title="">hardcy;</code> </td> <td> U+0044A </td> <td> <span class="glyph" title="">ъ</span> </td> </tr>
     *       <tr id="entity-hArr"><td> <code title="">hArr;</code> </td> <td> U+021D4 </td> <td> <span class="glyph" title="">⇔</span> </td> </tr>
     *       <tr id="entity-harr"><td> <code title="">harr;</code> </td> <td> U+02194 </td> <td> <span class="glyph" title="">↔</span> </td> </tr>
     *       <tr id="entity-harrcir"><td> <code title="">harrcir;</code> </td> <td> U+02948 </td> <td> <span class="glyph" title="">⥈</span> </td> </tr>
     *       <tr id="entity-harrw"><td> <code title="">harrw;</code> </td> <td> U+021AD </td> <td> <span class="glyph" title="">↭</span> </td> </tr>
     *       <tr id="entity-Hat"><td> <code title="">Hat;</code> </td> <td> U+0005E </td> <td> <span class="glyph" title="">^</span> </td> </tr>
     *       <tr id="entity-hbar"><td> <code title="">hbar;</code> </td> <td> U+0210F </td> <td> <span class="glyph" title="">ℏ</span> </td> </tr>
     *       <tr id="entity-Hcirc"><td> <code title="">Hcirc;</code> </td> <td> U+00124 </td> <td> <span class="glyph" title="">Ĥ</span> </td> </tr>
     *       <tr id="entity-hcirc"><td> <code title="">hcirc;</code> </td> <td> U+00125 </td> <td> <span class="glyph" title="">ĥ</span> </td> </tr>
     *       <tr id="entity-hearts"><td> <code title="">hearts;</code> </td> <td> U+02665 </td> <td> <span class="glyph" title="">♥</span> </td> </tr>
     *       <tr id="entity-heartsuit"><td> <code title="">heartsuit;</code> </td> <td> U+02665 </td> <td> <span class="glyph" title="">♥</span> </td> </tr>
     *       <tr id="entity-hellip"><td> <code title="">hellip;</code> </td> <td> U+02026 </td> <td> <span class="glyph" title="">…</span> </td> </tr>
     *       <tr id="entity-hercon"><td> <code title="">hercon;</code> </td> <td> U+022B9 </td> <td> <span class="glyph" title="">⊹</span> </td> </tr>
     *       <tr id="entity-Hfr"><td> <code title="">Hfr;</code> </td> <td> U+0210C </td> <td> <span class="glyph" title="">ℌ</span> </td> </tr>
     *       <tr id="entity-hfr"><td> <code title="">hfr;</code> </td> <td> U+1D525 </td> <td> <span class="glyph" title="">𝔥</span> </td> </tr>
     *       <tr id="entity-HilbertSpace"><td> <code title="">HilbertSpace;</code> </td> <td> U+0210B </td> <td> <span class="glyph" title="">ℋ</span> </td> </tr>
     *       <tr id="entity-hksearow"><td> <code title="">hksearow;</code> </td> <td> U+02925 </td> <td> <span class="glyph" title="">⤥</span> </td> </tr>
     *       <tr id="entity-hkswarow"><td> <code title="">hkswarow;</code> </td> <td> U+02926 </td> <td> <span class="glyph" title="">⤦</span> </td> </tr>
     *       <tr id="entity-hoarr"><td> <code title="">hoarr;</code> </td> <td> U+021FF </td> <td> <span class="glyph" title="">⇿</span> </td> </tr>
     *       <tr id="entity-homtht"><td> <code title="">homtht;</code> </td> <td> U+0223B </td> <td> <span class="glyph" title="">∻</span> </td> </tr>
     *       <tr id="entity-hookleftarrow"><td> <code title="">hookleftarrow;</code> </td> <td> U+021A9 </td> <td> <span class="glyph" title="">↩</span> </td> </tr>
     *       <tr id="entity-hookrightarrow"><td> <code title="">hookrightarrow;</code> </td> <td> U+021AA </td> <td> <span class="glyph" title="">↪</span> </td> </tr>
     *       <tr id="entity-Hopf"><td> <code title="">Hopf;</code> </td> <td> U+0210D </td> <td> <span class="glyph" title="">ℍ</span> </td> </tr>
     *       <tr id="entity-hopf"><td> <code title="">hopf;</code> </td> <td> U+1D559 </td> <td> <span class="glyph" title="">𝕙</span> </td> </tr>
     *       <tr id="entity-horbar"><td> <code title="">horbar;</code> </td> <td> U+02015 </td> <td> <span class="glyph" title="">―</span> </td> </tr>
     *       <tr id="entity-HorizontalLine"><td> <code title="">HorizontalLine;</code> </td> <td> U+02500 </td> <td> <span class="glyph" title="">─</span> </td> </tr>
     *       <tr id="entity-Hscr"><td> <code title="">Hscr;</code> </td> <td> U+0210B </td> <td> <span class="glyph" title="">ℋ</span> </td> </tr>
     *       <tr id="entity-hscr"><td> <code title="">hscr;</code> </td> <td> U+1D4BD </td> <td> <span class="glyph" title="">𝒽</span> </td> </tr>
     *       <tr id="entity-hslash"><td> <code title="">hslash;</code> </td> <td> U+0210F </td> <td> <span class="glyph" title="">ℏ</span> </td> </tr>
     *       <tr id="entity-Hstrok"><td> <code title="">Hstrok;</code> </td> <td> U+00126 </td> <td> <span class="glyph" title="">Ħ</span> </td> </tr>
     *       <tr id="entity-hstrok"><td> <code title="">hstrok;</code> </td> <td> U+00127 </td> <td> <span class="glyph" title="">ħ</span> </td> </tr>
     *       <tr id="entity-HumpDownHump"><td> <code title="">HumpDownHump;</code> </td> <td> U+0224E </td> <td> <span class="glyph" title="">≎</span> </td> </tr>
     *       <tr id="entity-HumpEqual"><td> <code title="">HumpEqual;</code> </td> <td> U+0224F </td> <td> <span class="glyph" title="">≏</span> </td> </tr>
     *       <tr id="entity-hybull"><td> <code title="">hybull;</code> </td> <td> U+02043 </td> <td> <span class="glyph" title="">⁃</span> </td> </tr>
     *       <tr id="entity-hyphen"><td> <code title="">hyphen;</code> </td> <td> U+02010 </td> <td> <span class="glyph" title="">‐</span> </td> </tr>
     *       <tr id="entity-Iacute"><td> <code title="">Iacute;</code> </td> <td> U+000CD </td> <td> <span class="glyph" title="">Í</span> </td> </tr>
     *       <tr class="impl" id="entity-Iacute-legacy"><td> <code title="">Iacute</code> </td> <td> U+000CD </td> <td> <span title="">Í</span> </td> </tr>
     *       <tr id="entity-iacute"><td> <code title="">iacute;</code> </td> <td> U+000ED </td> <td> <span class="glyph" title="">í</span> </td> </tr>
     *       <tr class="impl" id="entity-iacute-legacy"><td> <code title="">iacute</code> </td> <td> U+000ED </td> <td> <span title="">í</span> </td> </tr>
     *       <tr id="entity-ic"><td> <code title="">ic;</code> </td> <td> U+02063 </td> <td> <span class="glyph" title="">⁣</span> </td> </tr>
     *       <tr id="entity-Icirc"><td> <code title="">Icirc;</code> </td> <td> U+000CE </td> <td> <span class="glyph" title="">Î</span> </td> </tr>
     *       <tr class="impl" id="entity-Icirc-legacy"><td> <code title="">Icirc</code> </td> <td> U+000CE </td> <td> <span title="">Î</span> </td> </tr>
     *       <tr id="entity-icirc"><td> <code title="">icirc;</code> </td> <td> U+000EE </td> <td> <span class="glyph" title="">î</span> </td> </tr>
     *       <tr class="impl" id="entity-icirc-legacy"><td> <code title="">icirc</code> </td> <td> U+000EE </td> <td> <span title="">î</span> </td> </tr>
     *       <tr id="entity-Icy"><td> <code title="">Icy;</code> </td> <td> U+00418 </td> <td> <span class="glyph" title="">И</span> </td> </tr>
     *       <tr id="entity-icy"><td> <code title="">icy;</code> </td> <td> U+00438 </td> <td> <span class="glyph" title="">и</span> </td> </tr>
     *       <tr id="entity-Idot"><td> <code title="">Idot;</code> </td> <td> U+00130 </td> <td> <span class="glyph" title="">İ</span> </td> </tr>
     *       <tr id="entity-IEcy"><td> <code title="">IEcy;</code> </td> <td> U+00415 </td> <td> <span class="glyph" title="">Е</span> </td> </tr>
     *       <tr id="entity-iecy"><td> <code title="">iecy;</code> </td> <td> U+00435 </td> <td> <span class="glyph" title="">е</span> </td> </tr>
     *       <tr id="entity-iexcl"><td> <code title="">iexcl;</code> </td> <td> U+000A1 </td> <td> <span class="glyph" title="">¡</span> </td> </tr>
     *       <tr class="impl" id="entity-iexcl-legacy"><td> <code title="">iexcl</code> </td> <td> U+000A1 </td> <td> <span title="">¡</span> </td> </tr>
     *       <tr id="entity-iff"><td> <code title="">iff;</code> </td> <td> U+021D4 </td> <td> <span class="glyph" title="">⇔</span> </td> </tr>
     *       <tr id="entity-Ifr"><td> <code title="">Ifr;</code> </td> <td> U+02111 </td> <td> <span class="glyph" title="">ℑ</span> </td> </tr>
     *       <tr id="entity-ifr"><td> <code title="">ifr;</code> </td> <td> U+1D526 </td> <td> <span class="glyph" title="">𝔦</span> </td> </tr>
     *       <tr id="entity-Igrave"><td> <code title="">Igrave;</code> </td> <td> U+000CC </td> <td> <span class="glyph" title="">Ì</span> </td> </tr>
     *       <tr class="impl" id="entity-Igrave-legacy"><td> <code title="">Igrave</code> </td> <td> U+000CC </td> <td> <span title="">Ì</span> </td> </tr>
     *       <tr id="entity-igrave"><td> <code title="">igrave;</code> </td> <td> U+000EC </td> <td> <span class="glyph" title="">ì</span> </td> </tr>
     *       <tr class="impl" id="entity-igrave-legacy"><td> <code title="">igrave</code> </td> <td> U+000EC </td> <td> <span title="">ì</span> </td> </tr>
     *       <tr id="entity-ii"><td> <code title="">ii;</code> </td> <td> U+02148 </td> <td> <span class="glyph" title="">ⅈ</span> </td> </tr>
     *       <tr id="entity-iiiint"><td> <code title="">iiiint;</code> </td> <td> U+02A0C </td> <td> <span class="glyph" title="">⨌</span> </td> </tr>
     *       <tr id="entity-iiint"><td> <code title="">iiint;</code> </td> <td> U+0222D </td> <td> <span class="glyph" title="">∭</span> </td> </tr>
     *       <tr id="entity-iinfin"><td> <code title="">iinfin;</code> </td> <td> U+029DC </td> <td> <span class="glyph" title="">⧜</span> </td> </tr>
     *       <tr id="entity-iiota"><td> <code title="">iiota;</code> </td> <td> U+02129 </td> <td> <span class="glyph" title="">℩</span> </td> </tr>
     *       <tr id="entity-IJlig"><td> <code title="">IJlig;</code> </td> <td> U+00132 </td> <td> <span class="glyph" title="">Ĳ</span> </td> </tr>
     *       <tr id="entity-ijlig"><td> <code title="">ijlig;</code> </td> <td> U+00133 </td> <td> <span class="glyph" title="">ĳ</span> </td> </tr>
     *       <tr id="entity-Im"><td> <code title="">Im;</code> </td> <td> U+02111 </td> <td> <span class="glyph" title="">ℑ</span> </td> </tr>
     *       <tr id="entity-Imacr"><td> <code title="">Imacr;</code> </td> <td> U+0012A </td> <td> <span class="glyph" title="">Ī</span> </td> </tr>
     *       <tr id="entity-imacr"><td> <code title="">imacr;</code> </td> <td> U+0012B </td> <td> <span class="glyph" title="">ī</span> </td> </tr>
     *       <tr id="entity-image"><td> <code title="">image;</code> </td> <td> U+02111 </td> <td> <span class="glyph" title="">ℑ</span> </td> </tr>
     *       <tr id="entity-ImaginaryI"><td> <code title="">ImaginaryI;</code> </td> <td> U+02148 </td> <td> <span class="glyph" title="">ⅈ</span> </td> </tr>
     *       <tr id="entity-imagline"><td> <code title="">imagline;</code> </td> <td> U+02110 </td> <td> <span class="glyph" title="">ℐ</span> </td> </tr>
     *       <tr id="entity-imagpart"><td> <code title="">imagpart;</code> </td> <td> U+02111 </td> <td> <span class="glyph" title="">ℑ</span> </td> </tr>
     *       <tr id="entity-imath"><td> <code title="">imath;</code> </td> <td> U+00131 </td> <td> <span class="glyph" title="">ı</span> </td> </tr>
     *       <tr id="entity-imof"><td> <code title="">imof;</code> </td> <td> U+022B7 </td> <td> <span class="glyph" title="">⊷</span> </td> </tr>
     *       <tr id="entity-imped"><td> <code title="">imped;</code> </td> <td> U+001B5 </td> <td> <span class="glyph" title="">Ƶ</span> </td> </tr>
     *       <tr id="entity-Implies"><td> <code title="">Implies;</code> </td> <td> U+021D2 </td> <td> <span class="glyph" title="">⇒</span> </td> </tr>
     *       <tr id="entity-in"><td> <code title="">in;</code> </td> <td> U+02208 </td> <td> <span class="glyph" title="">∈</span> </td> </tr>
     *       <tr id="entity-incare"><td> <code title="">incare;</code> </td> <td> U+02105 </td> <td> <span class="glyph" title="">℅</span> </td> </tr>
     *       <tr id="entity-infin"><td> <code title="">infin;</code> </td> <td> U+0221E </td> <td> <span class="glyph" title="">∞</span> </td> </tr>
     *       <tr id="entity-infintie"><td> <code title="">infintie;</code> </td> <td> U+029DD </td> <td> <span class="glyph" title="">⧝</span> </td> </tr>
     *       <tr id="entity-inodot"><td> <code title="">inodot;</code> </td> <td> U+00131 </td> <td> <span class="glyph" title="">ı</span> </td> </tr>
     *       <tr id="entity-Int"><td> <code title="">Int;</code> </td> <td> U+0222C </td> <td> <span class="glyph" title="">∬</span> </td> </tr>
     *       <tr id="entity-int"><td> <code title="">int;</code> </td> <td> U+0222B </td> <td> <span class="glyph" title="">∫</span> </td> </tr>
     *       <tr id="entity-intcal"><td> <code title="">intcal;</code> </td> <td> U+022BA </td> <td> <span class="glyph" title="">⊺</span> </td> </tr>
     *       <tr id="entity-integers"><td> <code title="">integers;</code> </td> <td> U+02124 </td> <td> <span class="glyph" title="">ℤ</span> </td> </tr>
     *       <tr id="entity-Integral"><td> <code title="">Integral;</code> </td> <td> U+0222B </td> <td> <span class="glyph" title="">∫</span> </td> </tr>
     *       <tr id="entity-intercal"><td> <code title="">intercal;</code> </td> <td> U+022BA </td> <td> <span class="glyph" title="">⊺</span> </td> </tr>
     *       <tr id="entity-Intersection"><td> <code title="">Intersection;</code> </td> <td> U+022C2 </td> <td> <span class="glyph" title="">⋂</span> </td> </tr>
     *       <tr id="entity-intlarhk"><td> <code title="">intlarhk;</code> </td> <td> U+02A17 </td> <td> <span class="glyph" title="">⨗</span> </td> </tr>
     *       <tr id="entity-intprod"><td> <code title="">intprod;</code> </td> <td> U+02A3C </td> <td> <span class="glyph" title="">⨼</span> </td> </tr>
     *       <tr id="entity-InvisibleComma"><td> <code title="">InvisibleComma;</code> </td> <td> U+02063 </td> <td> <span class="glyph" title="">⁣</span> </td> </tr>
     *       <tr id="entity-InvisibleTimes"><td> <code title="">InvisibleTimes;</code> </td> <td> U+02062 </td> <td> <span class="glyph" title="">⁢</span> </td> </tr>
     *       <tr id="entity-IOcy"><td> <code title="">IOcy;</code> </td> <td> U+00401 </td> <td> <span class="glyph" title="">Ё</span> </td> </tr>
     *       <tr id="entity-iocy"><td> <code title="">iocy;</code> </td> <td> U+00451 </td> <td> <span class="glyph" title="">ё</span> </td> </tr>
     *       <tr id="entity-Iogon"><td> <code title="">Iogon;</code> </td> <td> U+0012E </td> <td> <span class="glyph" title="">Į</span> </td> </tr>
     *       <tr id="entity-iogon"><td> <code title="">iogon;</code> </td> <td> U+0012F </td> <td> <span class="glyph" title="">į</span> </td> </tr>
     *       <tr id="entity-Iopf"><td> <code title="">Iopf;</code> </td> <td> U+1D540 </td> <td> <span class="glyph" title="">𝕀</span> </td> </tr>
     *       <tr id="entity-iopf"><td> <code title="">iopf;</code> </td> <td> U+1D55A </td> <td> <span class="glyph" title="">𝕚</span> </td> </tr>
     *       <tr id="entity-Iota"><td> <code title="">Iota;</code> </td> <td> U+00399 </td> <td> <span class="glyph" title="">Ι</span> </td> </tr>
     *       <tr id="entity-iota"><td> <code title="">iota;</code> </td> <td> U+003B9 </td> <td> <span class="glyph" title="">ι</span> </td> </tr>
     *       <tr id="entity-iprod"><td> <code title="">iprod;</code> </td> <td> U+02A3C </td> <td> <span class="glyph" title="">⨼</span> </td> </tr>
     *       <tr id="entity-iquest"><td> <code title="">iquest;</code> </td> <td> U+000BF </td> <td> <span class="glyph" title="">¿</span> </td> </tr>
     *       <tr class="impl" id="entity-iquest-legacy"><td> <code title="">iquest</code> </td> <td> U+000BF </td> <td> <span title="">¿</span> </td> </tr>
     *       <tr id="entity-Iscr"><td> <code title="">Iscr;</code> </td> <td> U+02110 </td> <td> <span class="glyph" title="">ℐ</span> </td> </tr>
     *       <tr id="entity-iscr"><td> <code title="">iscr;</code> </td> <td> U+1D4BE </td> <td> <span class="glyph" title="">𝒾</span> </td> </tr>
     *       <tr id="entity-isin"><td> <code title="">isin;</code> </td> <td> U+02208 </td> <td> <span class="glyph" title="">∈</span> </td> </tr>
     *       <tr id="entity-isindot"><td> <code title="">isindot;</code> </td> <td> U+022F5 </td> <td> <span class="glyph" title="">⋵</span> </td> </tr>
     *       <tr id="entity-isinE"><td> <code title="">isinE;</code> </td> <td> U+022F9 </td> <td> <span class="glyph" title="">⋹</span> </td> </tr>
     *       <tr id="entity-isins"><td> <code title="">isins;</code> </td> <td> U+022F4 </td> <td> <span class="glyph" title="">⋴</span> </td> </tr>
     *       <tr id="entity-isinsv"><td> <code title="">isinsv;</code> </td> <td> U+022F3 </td> <td> <span class="glyph" title="">⋳</span> </td> </tr>
     *       <tr id="entity-isinv"><td> <code title="">isinv;</code> </td> <td> U+02208 </td> <td> <span class="glyph" title="">∈</span> </td> </tr>
     *       <tr id="entity-it"><td> <code title="">it;</code> </td> <td> U+02062 </td> <td> <span class="glyph" title="">⁢</span> </td> </tr>
     *       <tr id="entity-Itilde"><td> <code title="">Itilde;</code> </td> <td> U+00128 </td> <td> <span class="glyph" title="">Ĩ</span> </td> </tr>
     *       <tr id="entity-itilde"><td> <code title="">itilde;</code> </td> <td> U+00129 </td> <td> <span class="glyph" title="">ĩ</span> </td> </tr>
     *       <tr id="entity-Iukcy"><td> <code title="">Iukcy;</code> </td> <td> U+00406 </td> <td> <span class="glyph" title="">І</span> </td> </tr>
     *       <tr id="entity-iukcy"><td> <code title="">iukcy;</code> </td> <td> U+00456 </td> <td> <span class="glyph" title="">і</span> </td> </tr>
     *       <tr id="entity-Iuml"><td> <code title="">Iuml;</code> </td> <td> U+000CF </td> <td> <span class="glyph" title="">Ï</span> </td> </tr>
     *       <tr class="impl" id="entity-Iuml-legacy"><td> <code title="">Iuml</code> </td> <td> U+000CF </td> <td> <span title="">Ï</span> </td> </tr>
     *       <tr id="entity-iuml"><td> <code title="">iuml;</code> </td> <td> U+000EF </td> <td> <span class="glyph" title="">ï</span> </td> </tr>
     *       <tr class="impl" id="entity-iuml-legacy"><td> <code title="">iuml</code> </td> <td> U+000EF </td> <td> <span title="">ï</span> </td> </tr>
     *       <tr id="entity-Jcirc"><td> <code title="">Jcirc;</code> </td> <td> U+00134 </td> <td> <span class="glyph" title="">Ĵ</span> </td> </tr>
     *       <tr id="entity-jcirc"><td> <code title="">jcirc;</code> </td> <td> U+00135 </td> <td> <span class="glyph" title="">ĵ</span> </td> </tr>
     *       <tr id="entity-Jcy"><td> <code title="">Jcy;</code> </td> <td> U+00419 </td> <td> <span class="glyph" title="">Й</span> </td> </tr>
     *       <tr id="entity-jcy"><td> <code title="">jcy;</code> </td> <td> U+00439 </td> <td> <span class="glyph" title="">й</span> </td> </tr>
     *       <tr id="entity-Jfr"><td> <code title="">Jfr;</code> </td> <td> U+1D50D </td> <td> <span class="glyph" title="">𝔍</span> </td> </tr>
     *       <tr id="entity-jfr"><td> <code title="">jfr;</code> </td> <td> U+1D527 </td> <td> <span class="glyph" title="">𝔧</span> </td> </tr>
     *       <tr id="entity-jmath"><td> <code title="">jmath;</code> </td> <td> U+00237 </td> <td> <span class="glyph" title="">ȷ</span> </td> </tr>
     *       <tr id="entity-Jopf"><td> <code title="">Jopf;</code> </td> <td> U+1D541 </td> <td> <span class="glyph" title="">𝕁</span> </td> </tr>
     *       <tr id="entity-jopf"><td> <code title="">jopf;</code> </td> <td> U+1D55B </td> <td> <span class="glyph" title="">𝕛</span> </td> </tr>
     *       <tr id="entity-Jscr"><td> <code title="">Jscr;</code> </td> <td> U+1D4A5 </td> <td> <span class="glyph" title="">𝒥</span> </td> </tr>
     *       <tr id="entity-jscr"><td> <code title="">jscr;</code> </td> <td> U+1D4BF </td> <td> <span class="glyph" title="">𝒿</span> </td> </tr>
     *       <tr id="entity-Jsercy"><td> <code title="">Jsercy;</code> </td> <td> U+00408 </td> <td> <span class="glyph" title="">Ј</span> </td> </tr>
     *       <tr id="entity-jsercy"><td> <code title="">jsercy;</code> </td> <td> U+00458 </td> <td> <span class="glyph" title="">ј</span> </td> </tr>
     *       <tr id="entity-Jukcy"><td> <code title="">Jukcy;</code> </td> <td> U+00404 </td> <td> <span class="glyph" title="">Є</span> </td> </tr>
     *       <tr id="entity-jukcy"><td> <code title="">jukcy;</code> </td> <td> U+00454 </td> <td> <span class="glyph" title="">є</span> </td> </tr>
     *       <tr id="entity-Kappa"><td> <code title="">Kappa;</code> </td> <td> U+0039A </td> <td> <span class="glyph" title="">Κ</span> </td> </tr>
     *       <tr id="entity-kappa"><td> <code title="">kappa;</code> </td> <td> U+003BA </td> <td> <span class="glyph" title="">κ</span> </td> </tr>
     *       <tr id="entity-kappav"><td> <code title="">kappav;</code> </td> <td> U+003F0 </td> <td> <span class="glyph" title="">ϰ</span> </td> </tr>
     *       <tr id="entity-Kcedil"><td> <code title="">Kcedil;</code> </td> <td> U+00136 </td> <td> <span class="glyph" title="">Ķ</span> </td> </tr>
     *       <tr id="entity-kcedil"><td> <code title="">kcedil;</code> </td> <td> U+00137 </td> <td> <span class="glyph" title="">ķ</span> </td> </tr>
     *       <tr id="entity-Kcy"><td> <code title="">Kcy;</code> </td> <td> U+0041A </td> <td> <span class="glyph" title="">К</span> </td> </tr>
     *       <tr id="entity-kcy"><td> <code title="">kcy;</code> </td> <td> U+0043A </td> <td> <span class="glyph" title="">к</span> </td> </tr>
     *       <tr id="entity-Kfr"><td> <code title="">Kfr;</code> </td> <td> U+1D50E </td> <td> <span class="glyph" title="">𝔎</span> </td> </tr>
     *       <tr id="entity-kfr"><td> <code title="">kfr;</code> </td> <td> U+1D528 </td> <td> <span class="glyph" title="">𝔨</span> </td> </tr>
     *       <tr id="entity-kgreen"><td> <code title="">kgreen;</code> </td> <td> U+00138 </td> <td> <span class="glyph" title="">ĸ</span> </td> </tr>
     *       <tr id="entity-KHcy"><td> <code title="">KHcy;</code> </td> <td> U+00425 </td> <td> <span class="glyph" title="">Х</span> </td> </tr>
     *       <tr id="entity-khcy"><td> <code title="">khcy;</code> </td> <td> U+00445 </td> <td> <span class="glyph" title="">х</span> </td> </tr>
     *       <tr id="entity-KJcy"><td> <code title="">KJcy;</code> </td> <td> U+0040C </td> <td> <span class="glyph" title="">Ќ</span> </td> </tr>
     *       <tr id="entity-kjcy"><td> <code title="">kjcy;</code> </td> <td> U+0045C </td> <td> <span class="glyph" title="">ќ</span> </td> </tr>
     *       <tr id="entity-Kopf"><td> <code title="">Kopf;</code> </td> <td> U+1D542 </td> <td> <span class="glyph" title="">𝕂</span> </td> </tr>
     *       <tr id="entity-kopf"><td> <code title="">kopf;</code> </td> <td> U+1D55C </td> <td> <span class="glyph" title="">𝕜</span> </td> </tr>
     *       <tr id="entity-Kscr"><td> <code title="">Kscr;</code> </td> <td> U+1D4A6 </td> <td> <span class="glyph" title="">𝒦</span> </td> </tr>
     *       <tr id="entity-kscr"><td> <code title="">kscr;</code> </td> <td> U+1D4C0 </td> <td> <span class="glyph" title="">𝓀</span> </td> </tr>
     *       <tr id="entity-lAarr"><td> <code title="">lAarr;</code> </td> <td> U+021DA </td> <td> <span class="glyph" title="">⇚</span> </td> </tr>
     *       <tr id="entity-Lacute"><td> <code title="">Lacute;</code> </td> <td> U+00139 </td> <td> <span class="glyph" title="">Ĺ</span> </td> </tr>
     *       <tr id="entity-lacute"><td> <code title="">lacute;</code> </td> <td> U+0013A </td> <td> <span class="glyph" title="">ĺ</span> </td> </tr>
     *       <tr id="entity-laemptyv"><td> <code title="">laemptyv;</code> </td> <td> U+029B4 </td> <td> <span class="glyph" title="">⦴</span> </td> </tr>
     *       <tr id="entity-lagran"><td> <code title="">lagran;</code> </td> <td> U+02112 </td> <td> <span class="glyph" title="">ℒ</span> </td> </tr>
     *       <tr id="entity-Lambda"><td> <code title="">Lambda;</code> </td> <td> U+0039B </td> <td> <span class="glyph" title="">Λ</span> </td> </tr>
     *       <tr id="entity-lambda"><td> <code title="">lambda;</code> </td> <td> U+003BB </td> <td> <span class="glyph" title="">λ</span> </td> </tr>
     *       <tr id="entity-Lang"><td> <code title="">Lang;</code> </td> <td> U+027EA </td> <td> <span class="glyph" title="">⟪</span> </td> </tr>
     *       <tr id="entity-lang"><td> <code title="">lang;</code> </td> <td> U+027E8 </td> <td> <span class="glyph" title="">⟨</span> </td> </tr>
     *       <tr id="entity-langd"><td> <code title="">langd;</code> </td> <td> U+02991 </td> <td> <span class="glyph" title="">⦑</span> </td> </tr>
     *       <tr id="entity-langle"><td> <code title="">langle;</code> </td> <td> U+027E8 </td> <td> <span class="glyph" title="">〈</span> </td> </tr>
     *       <tr id="entity-lap"><td> <code title="">lap;</code> </td> <td> U+02A85 </td> <td> <span class="glyph" title="">⪅</span> </td> </tr>
     *       <tr id="entity-Laplacetrf"><td> <code title="">Laplacetrf;</code> </td> <td> U+02112 </td> <td> <span class="glyph" title="">ℒ</span> </td> </tr>
     *       <tr id="entity-laquo"><td> <code title="">laquo;</code> </td> <td> U+000AB </td> <td> <span class="glyph" title="">«</span> </td> </tr>
     *       <tr class="impl" id="entity-laquo-legacy"><td> <code title="">laquo</code> </td> <td> U+000AB </td> <td> <span title="">«</span> </td> </tr>
     *       <tr id="entity-Larr"><td> <code title="">Larr;</code> </td> <td> U+0219E </td> <td> <span class="glyph" title="">↞</span> </td> </tr>
     *       <tr id="entity-lArr"><td> <code title="">lArr;</code> </td> <td> U+021D0 </td> <td> <span class="glyph" title="">⇐</span> </td> </tr>
     *       <tr id="entity-larr"><td> <code title="">larr;</code> </td> <td> U+02190 </td> <td> <span class="glyph" title="">←</span> </td> </tr>
     *       <tr id="entity-larrb"><td> <code title="">larrb;</code> </td> <td> U+021E4 </td> <td> <span class="glyph" title="">⇤</span> </td> </tr>
     *       <tr id="entity-larrbfs"><td> <code title="">larrbfs;</code> </td> <td> U+0291F </td> <td> <span class="glyph" title="">⤟</span> </td> </tr>
     *       <tr id="entity-larrfs"><td> <code title="">larrfs;</code> </td> <td> U+0291D </td> <td> <span class="glyph" title="">⤝</span> </td> </tr>
     *       <tr id="entity-larrhk"><td> <code title="">larrhk;</code> </td> <td> U+021A9 </td> <td> <span class="glyph" title="">↩</span> </td> </tr>
     *       <tr id="entity-larrlp"><td> <code title="">larrlp;</code> </td> <td> U+021AB </td> <td> <span class="glyph" title="">↫</span> </td> </tr>
     *       <tr id="entity-larrpl"><td> <code title="">larrpl;</code> </td> <td> U+02939 </td> <td> <span class="glyph" title="">⤹</span> </td> </tr>
     *       <tr id="entity-larrsim"><td> <code title="">larrsim;</code> </td> <td> U+02973 </td> <td> <span class="glyph" title="">⥳</span> </td> </tr>
     *       <tr id="entity-larrtl"><td> <code title="">larrtl;</code> </td> <td> U+021A2 </td> <td> <span class="glyph" title="">↢</span> </td> </tr>
     *       <tr id="entity-lat"><td> <code title="">lat;</code> </td> <td> U+02AAB </td> <td> <span class="glyph" title="">⪫</span> </td> </tr>
     *       <tr id="entity-lAtail"><td> <code title="">lAtail;</code> </td> <td> U+0291B </td> <td> <span class="glyph" title="">⤛</span> </td> </tr>
     *       <tr id="entity-latail"><td> <code title="">latail;</code> </td> <td> U+02919 </td> <td> <span class="glyph" title="">⤙</span> </td> </tr>
     *       <tr id="entity-late"><td> <code title="">late;</code> </td> <td> U+02AAD </td> <td> <span class="glyph" title="">⪭</span> </td> </tr>
     *       <tr id="entity-lates"><td> <code title="">lates;</code> </td> <td> U+02AAD U+0FE00 </td> <td> <span class="glyph compound" title="">⪭︀</span> </td> </tr>
     *       <tr id="entity-lBarr"><td> <code title="">lBarr;</code> </td> <td> U+0290E </td> <td> <span class="glyph" title="">⤎</span> </td> </tr>
     *       <tr id="entity-lbarr"><td> <code title="">lbarr;</code> </td> <td> U+0290C </td> <td> <span class="glyph" title="">⤌</span> </td> </tr>
     *       <tr id="entity-lbbrk"><td> <code title="">lbbrk;</code> </td> <td> U+02772 </td> <td> <span class="glyph" title="">❲</span> </td> </tr>
     *       <tr id="entity-lbrace"><td> <code title="">lbrace;</code> </td> <td> U+0007B </td> <td> <span class="glyph" title="">{</span> </td> </tr>
     *       <tr id="entity-lbrack"><td> <code title="">lbrack;</code> </td> <td> U+0005B </td> <td> <span class="glyph" title="">[</span> </td> </tr>
     *       <tr id="entity-lbrke"><td> <code title="">lbrke;</code> </td> <td> U+0298B </td> <td> <span class="glyph" title="">⦋</span> </td> </tr>
     *       <tr id="entity-lbrksld"><td> <code title="">lbrksld;</code> </td> <td> U+0298F </td> <td> <span class="glyph" title="">⦏</span> </td> </tr>
     *       <tr id="entity-lbrkslu"><td> <code title="">lbrkslu;</code> </td> <td> U+0298D </td> <td> <span class="glyph" title="">⦍</span> </td> </tr>
     *       <tr id="entity-Lcaron"><td> <code title="">Lcaron;</code> </td> <td> U+0013D </td> <td> <span class="glyph" title="">Ľ</span> </td> </tr>
     *       <tr id="entity-lcaron"><td> <code title="">lcaron;</code> </td> <td> U+0013E </td> <td> <span class="glyph" title="">ľ</span> </td> </tr>
     *       <tr id="entity-Lcedil"><td> <code title="">Lcedil;</code> </td> <td> U+0013B </td> <td> <span class="glyph" title="">Ļ</span> </td> </tr>
     *       <tr id="entity-lcedil"><td> <code title="">lcedil;</code> </td> <td> U+0013C </td> <td> <span class="glyph" title="">ļ</span> </td> </tr>
     *       <tr id="entity-lceil"><td> <code title="">lceil;</code> </td> <td> U+02308 </td> <td> <span class="glyph" title="">⌈</span> </td> </tr>
     *       <tr id="entity-lcub"><td> <code title="">lcub;</code> </td> <td> U+0007B </td> <td> <span class="glyph" title="">{</span> </td> </tr>
     *       <tr id="entity-Lcy"><td> <code title="">Lcy;</code> </td> <td> U+0041B </td> <td> <span class="glyph" title="">Л</span> </td> </tr>
     *       <tr id="entity-lcy"><td> <code title="">lcy;</code> </td> <td> U+0043B </td> <td> <span class="glyph" title="">л</span> </td> </tr>
     *       <tr id="entity-ldca"><td> <code title="">ldca;</code> </td> <td> U+02936 </td> <td> <span class="glyph" title="">⤶</span> </td> </tr>
     *       <tr id="entity-ldquo"><td> <code title="">ldquo;</code> </td> <td> U+0201C </td> <td> <span class="glyph" title="">“</span> </td> </tr>
     *       <tr id="entity-ldquor"><td> <code title="">ldquor;</code> </td> <td> U+0201E </td> <td> <span class="glyph" title="">„</span> </td> </tr>
     *       <tr id="entity-ldrdhar"><td> <code title="">ldrdhar;</code> </td> <td> U+02967 </td> <td> <span class="glyph" title="">⥧</span> </td> </tr>
     *       <tr id="entity-ldrushar"><td> <code title="">ldrushar;</code> </td> <td> U+0294B </td> <td> <span class="glyph" title="">⥋</span> </td> </tr>
     *       <tr id="entity-ldsh"><td> <code title="">ldsh;</code> </td> <td> U+021B2 </td> <td> <span class="glyph" title="">↲</span> </td> </tr>
     *       <tr id="entity-lE"><td> <code title="">lE;</code> </td> <td> U+02266 </td> <td> <span class="glyph" title="">≦</span> </td> </tr>
     *       <tr id="entity-le"><td> <code title="">le;</code> </td> <td> U+02264 </td> <td> <span class="glyph" title="">≤</span> </td> </tr>
     *       <tr id="entity-LeftAngleBracket"><td> <code title="">LeftAngleBracket;</code> </td> <td> U+027E8 </td> <td> <span class="glyph" title="">〈</span> </td> </tr>
     *       <tr id="entity-LeftArrow"><td> <code title="">LeftArrow;</code> </td> <td> U+02190 </td> <td> <span class="glyph" title="">←</span> </td> </tr>
     *       <tr id="entity-Leftarrow"><td> <code title="">Leftarrow;</code> </td> <td> U+021D0 </td> <td> <span class="glyph" title="">⇐</span> </td> </tr>
     *       <tr id="entity-leftarrow"><td> <code title="">leftarrow;</code> </td> <td> U+02190 </td> <td> <span class="glyph" title="">←</span> </td> </tr>
     *       <tr id="entity-LeftArrowBar"><td> <code title="">LeftArrowBar;</code> </td> <td> U+021E4 </td> <td> <span class="glyph" title="">⇤</span> </td> </tr>
     *       <tr id="entity-LeftArrowRightArrow"><td> <code title="">LeftArrowRightArrow;</code> </td> <td> U+021C6 </td> <td> <span class="glyph" title="">⇆</span> </td> </tr>
     *       <tr id="entity-leftarrowtail"><td> <code title="">leftarrowtail;</code> </td> <td> U+021A2 </td> <td> <span class="glyph" title="">↢</span> </td> </tr>
     *       <tr id="entity-LeftCeiling"><td> <code title="">LeftCeiling;</code> </td> <td> U+02308 </td> <td> <span class="glyph" title="">⌈</span> </td> </tr>
     *       <tr id="entity-LeftDoubleBracket"><td> <code title="">LeftDoubleBracket;</code> </td> <td> U+027E6 </td> <td> <span class="glyph" title="">⟦</span> </td> </tr>
     *       <tr id="entity-LeftDownTeeVector"><td> <code title="">LeftDownTeeVector;</code> </td> <td> U+02961 </td> <td> <span class="glyph" title="">⥡</span> </td> </tr>
     *       <tr id="entity-LeftDownVector"><td> <code title="">LeftDownVector;</code> </td> <td> U+021C3 </td> <td> <span class="glyph" title="">⇃</span> </td> </tr>
     *       <tr id="entity-LeftDownVectorBar"><td> <code title="">LeftDownVectorBar;</code> </td> <td> U+02959 </td> <td> <span class="glyph" title="">⥙</span> </td> </tr>
     *       <tr id="entity-LeftFloor"><td> <code title="">LeftFloor;</code> </td> <td> U+0230A </td> <td> <span class="glyph" title="">⌊</span> </td> </tr>
     *       <tr id="entity-leftharpoondown"><td> <code title="">leftharpoondown;</code> </td> <td> U+021BD </td> <td> <span class="glyph" title="">↽</span> </td> </tr>
     *       <tr id="entity-leftharpoonup"><td> <code title="">leftharpoonup;</code> </td> <td> U+021BC </td> <td> <span class="glyph" title="">↼</span> </td> </tr>
     *       <tr id="entity-leftleftarrows"><td> <code title="">leftleftarrows;</code> </td> <td> U+021C7 </td> <td> <span class="glyph" title="">⇇</span> </td> </tr>
     *       <tr id="entity-LeftRightArrow"><td> <code title="">LeftRightArrow;</code> </td> <td> U+02194 </td> <td> <span class="glyph" title="">↔</span> </td> </tr>
     *       <tr id="entity-Leftrightarrow"><td> <code title="">Leftrightarrow;</code> </td> <td> U+021D4 </td> <td> <span class="glyph" title="">⇔</span> </td> </tr>
     *       <tr id="entity-leftrightarrow"><td> <code title="">leftrightarrow;</code> </td> <td> U+02194 </td> <td> <span class="glyph" title="">↔</span> </td> </tr>
     *       <tr id="entity-leftrightarrows"><td> <code title="">leftrightarrows;</code> </td> <td> U+021C6 </td> <td> <span class="glyph" title="">⇆</span> </td> </tr>
     *       <tr id="entity-leftrightharpoons"><td> <code title="">leftrightharpoons;</code> </td> <td> U+021CB </td> <td> <span class="glyph" title="">⇋</span> </td> </tr>
     *       <tr id="entity-leftrightsquigarrow"><td> <code title="">leftrightsquigarrow;</code> </td> <td> U+021AD </td> <td> <span class="glyph" title="">↭</span> </td> </tr>
     *       <tr id="entity-LeftRightVector"><td> <code title="">LeftRightVector;</code> </td> <td> U+0294E </td> <td> <span class="glyph" title="">⥎</span> </td> </tr>
     *       <tr id="entity-LeftTee"><td> <code title="">LeftTee;</code> </td> <td> U+022A3 </td> <td> <span class="glyph" title="">⊣</span> </td> </tr>
     *       <tr id="entity-LeftTeeArrow"><td> <code title="">LeftTeeArrow;</code> </td> <td> U+021A4 </td> <td> <span class="glyph" title="">↤</span> </td> </tr>
     *       <tr id="entity-LeftTeeVector"><td> <code title="">LeftTeeVector;</code> </td> <td> U+0295A </td> <td> <span class="glyph" title="">⥚</span> </td> </tr>
     *       <tr id="entity-leftthreetimes"><td> <code title="">leftthreetimes;</code> </td> <td> U+022CB </td> <td> <span class="glyph" title="">⋋</span> </td> </tr>
     *       <tr id="entity-LeftTriangle"><td> <code title="">LeftTriangle;</code> </td> <td> U+022B2 </td> <td> <span class="glyph" title="">⊲</span> </td> </tr>
     *       <tr id="entity-LeftTriangleBar"><td> <code title="">LeftTriangleBar;</code> </td> <td> U+029CF </td> <td> <span class="glyph" title="">⧏</span> </td> </tr>
     *       <tr id="entity-LeftTriangleEqual"><td> <code title="">LeftTriangleEqual;</code> </td> <td> U+022B4 </td> <td> <span class="glyph" title="">⊴</span> </td> </tr>
     *       <tr id="entity-LeftUpDownVector"><td> <code title="">LeftUpDownVector;</code> </td> <td> U+02951 </td> <td> <span class="glyph" title="">⥑</span> </td> </tr>
     *       <tr id="entity-LeftUpTeeVector"><td> <code title="">LeftUpTeeVector;</code> </td> <td> U+02960 </td> <td> <span class="glyph" title="">⥠</span> </td> </tr>
     *       <tr id="entity-LeftUpVector"><td> <code title="">LeftUpVector;</code> </td> <td> U+021BF </td> <td> <span class="glyph" title="">↿</span> </td> </tr>
     *       <tr id="entity-LeftUpVectorBar"><td> <code title="">LeftUpVectorBar;</code> </td> <td> U+02958 </td> <td> <span class="glyph" title="">⥘</span> </td> </tr>
     *       <tr id="entity-LeftVector"><td> <code title="">LeftVector;</code> </td> <td> U+021BC </td> <td> <span class="glyph" title="">↼</span> </td> </tr>
     *       <tr id="entity-LeftVectorBar"><td> <code title="">LeftVectorBar;</code> </td> <td> U+02952 </td> <td> <span class="glyph" title="">⥒</span> </td> </tr>
     *       <tr id="entity-lEg"><td> <code title="">lEg;</code> </td> <td> U+02A8B </td> <td> <span class="glyph" title="">⪋</span> </td> </tr>
     *       <tr id="entity-leg"><td> <code title="">leg;</code> </td> <td> U+022DA </td> <td> <span class="glyph" title="">⋚</span> </td> </tr>
     *       <tr id="entity-leq"><td> <code title="">leq;</code> </td> <td> U+02264 </td> <td> <span class="glyph" title="">≤</span> </td> </tr>
     *       <tr id="entity-leqq"><td> <code title="">leqq;</code> </td> <td> U+02266 </td> <td> <span class="glyph" title="">≦</span> </td> </tr>
     *       <tr id="entity-leqslant"><td> <code title="">leqslant;</code> </td> <td> U+02A7D </td> <td> <span class="glyph" title="">⩽</span> </td> </tr>
     *       <tr id="entity-les"><td> <code title="">les;</code> </td> <td> U+02A7D </td> <td> <span class="glyph" title="">⩽</span> </td> </tr>
     *       <tr id="entity-lescc"><td> <code title="">lescc;</code> </td> <td> U+02AA8 </td> <td> <span class="glyph" title="">⪨</span> </td> </tr>
     *       <tr id="entity-lesdot"><td> <code title="">lesdot;</code> </td> <td> U+02A7F </td> <td> <span class="glyph" title="">⩿</span> </td> </tr>
     *       <tr id="entity-lesdoto"><td> <code title="">lesdoto;</code> </td> <td> U+02A81 </td> <td> <span class="glyph" title="">⪁</span> </td> </tr>
     *       <tr id="entity-lesdotor"><td> <code title="">lesdotor;</code> </td> <td> U+02A83 </td> <td> <span class="glyph" title="">⪃</span> </td> </tr>
     *       <tr id="entity-lesg"><td> <code title="">lesg;</code> </td> <td> U+022DA U+0FE00 </td> <td> <span class="glyph compound" title="">⋚︀</span> </td> </tr>
     *       <tr id="entity-lesges"><td> <code title="">lesges;</code> </td> <td> U+02A93 </td> <td> <span class="glyph" title="">⪓</span> </td> </tr>
     *       <tr id="entity-lessapprox"><td> <code title="">lessapprox;</code> </td> <td> U+02A85 </td> <td> <span class="glyph" title="">⪅</span> </td> </tr>
     *       <tr id="entity-lessdot"><td> <code title="">lessdot;</code> </td> <td> U+022D6 </td> <td> <span class="glyph" title="">⋖</span> </td> </tr>
     *       <tr id="entity-lesseqgtr"><td> <code title="">lesseqgtr;</code> </td> <td> U+022DA </td> <td> <span class="glyph" title="">⋚</span> </td> </tr>
     *       <tr id="entity-lesseqqgtr"><td> <code title="">lesseqqgtr;</code> </td> <td> U+02A8B </td> <td> <span class="glyph" title="">⪋</span> </td> </tr>
     *       <tr id="entity-LessEqualGreater"><td> <code title="">LessEqualGreater;</code> </td> <td> U+022DA </td> <td> <span class="glyph" title="">⋚</span> </td> </tr>
     *       <tr id="entity-LessFullEqual"><td> <code title="">LessFullEqual;</code> </td> <td> U+02266 </td> <td> <span class="glyph" title="">≦</span> </td> </tr>
     *       <tr id="entity-LessGreater"><td> <code title="">LessGreater;</code> </td> <td> U+02276 </td> <td> <span class="glyph" title="">≶</span> </td> </tr>
     *       <tr id="entity-lessgtr"><td> <code title="">lessgtr;</code> </td> <td> U+02276 </td> <td> <span class="glyph" title="">≶</span> </td> </tr>
     *       <tr id="entity-LessLess"><td> <code title="">LessLess;</code> </td> <td> U+02AA1 </td> <td> <span class="glyph" title="">⪡</span> </td> </tr>
     *       <tr id="entity-lesssim"><td> <code title="">lesssim;</code> </td> <td> U+02272 </td> <td> <span class="glyph" title="">≲</span> </td> </tr>
     *       <tr id="entity-LessSlantEqual"><td> <code title="">LessSlantEqual;</code> </td> <td> U+02A7D </td> <td> <span class="glyph" title="">⩽</span> </td> </tr>
     *       <tr id="entity-LessTilde"><td> <code title="">LessTilde;</code> </td> <td> U+02272 </td> <td> <span class="glyph" title="">≲</span> </td> </tr>
     *       <tr id="entity-lfisht"><td> <code title="">lfisht;</code> </td> <td> U+0297C </td> <td> <span class="glyph" title="">⥼</span> </td> </tr>
     *       <tr id="entity-lfloor"><td> <code title="">lfloor;</code> </td> <td> U+0230A </td> <td> <span class="glyph" title="">⌊</span> </td> </tr>
     *       <tr id="entity-Lfr"><td> <code title="">Lfr;</code> </td> <td> U+1D50F </td> <td> <span class="glyph" title="">𝔏</span> </td> </tr>
     *       <tr id="entity-lfr"><td> <code title="">lfr;</code> </td> <td> U+1D529 </td> <td> <span class="glyph" title="">𝔩</span> </td> </tr>
     *       <tr id="entity-lg"><td> <code title="">lg;</code> </td> <td> U+02276 </td> <td> <span class="glyph" title="">≶</span> </td> </tr>
     *       <tr id="entity-lgE"><td> <code title="">lgE;</code> </td> <td> U+02A91 </td> <td> <span class="glyph" title="">⪑</span> </td> </tr>
     *       <tr id="entity-lHar"><td> <code title="">lHar;</code> </td> <td> U+02962 </td> <td> <span class="glyph" title="">⥢</span> </td> </tr>
     *       <tr id="entity-lhard"><td> <code title="">lhard;</code> </td> <td> U+021BD </td> <td> <span class="glyph" title="">↽</span> </td> </tr>
     *       <tr id="entity-lharu"><td> <code title="">lharu;</code> </td> <td> U+021BC </td> <td> <span class="glyph" title="">↼</span> </td> </tr>
     *       <tr id="entity-lharul"><td> <code title="">lharul;</code> </td> <td> U+0296A </td> <td> <span class="glyph" title="">⥪</span> </td> </tr>
     *       <tr id="entity-lhblk"><td> <code title="">lhblk;</code> </td> <td> U+02584 </td> <td> <span class="glyph" title="">▄</span> </td> </tr>
     *       <tr id="entity-LJcy"><td> <code title="">LJcy;</code> </td> <td> U+00409 </td> <td> <span class="glyph" title="">Љ</span> </td> </tr>
     *       <tr id="entity-ljcy"><td> <code title="">ljcy;</code> </td> <td> U+00459 </td> <td> <span class="glyph" title="">љ</span> </td> </tr>
     *       <tr id="entity-Ll"><td> <code title="">Ll;</code> </td> <td> U+022D8 </td> <td> <span class="glyph" title="">⋘</span> </td> </tr>
     *       <tr id="entity-ll"><td> <code title="">ll;</code> </td> <td> U+0226A </td> <td> <span class="glyph" title="">≪</span> </td> </tr>
     *       <tr id="entity-llarr"><td> <code title="">llarr;</code> </td> <td> U+021C7 </td> <td> <span class="glyph" title="">⇇</span> </td> </tr>
     *       <tr id="entity-llcorner"><td> <code title="">llcorner;</code> </td> <td> U+0231E </td> <td> <span class="glyph" title="">⌞</span> </td> </tr>
     *       <tr id="entity-Lleftarrow"><td> <code title="">Lleftarrow;</code> </td> <td> U+021DA </td> <td> <span class="glyph" title="">⇚</span> </td> </tr>
     *       <tr id="entity-llhard"><td> <code title="">llhard;</code> </td> <td> U+0296B </td> <td> <span class="glyph" title="">⥫</span> </td> </tr>
     *       <tr id="entity-lltri"><td> <code title="">lltri;</code> </td> <td> U+025FA </td> <td> <span class="glyph" title="">◺</span> </td> </tr>
     *       <tr id="entity-Lmidot"><td> <code title="">Lmidot;</code> </td> <td> U+0013F </td> <td> <span class="glyph" title="">Ŀ</span> </td> </tr>
     *       <tr id="entity-lmidot"><td> <code title="">lmidot;</code> </td> <td> U+00140 </td> <td> <span class="glyph" title="">ŀ</span> </td> </tr>
     *       <tr id="entity-lmoust"><td> <code title="">lmoust;</code> </td> <td> U+023B0 </td> <td> <span class="glyph" title="">⎰</span> </td> </tr>
     *       <tr id="entity-lmoustache"><td> <code title="">lmoustache;</code> </td> <td> U+023B0 </td> <td> <span class="glyph" title="">⎰</span> </td> </tr>
     *       <tr id="entity-lnap"><td> <code title="">lnap;</code> </td> <td> U+02A89 </td> <td> <span class="glyph" title="">⪉</span> </td> </tr>
     *       <tr id="entity-lnapprox"><td> <code title="">lnapprox;</code> </td> <td> U+02A89 </td> <td> <span class="glyph" title="">⪉</span> </td> </tr>
     *       <tr id="entity-lnE"><td> <code title="">lnE;</code> </td> <td> U+02268 </td> <td> <span class="glyph" title="">≨</span> </td> </tr>
     *       <tr id="entity-lne"><td> <code title="">lne;</code> </td> <td> U+02A87 </td> <td> <span class="glyph" title="">⪇</span> </td> </tr>
     *       <tr id="entity-lneq"><td> <code title="">lneq;</code> </td> <td> U+02A87 </td> <td> <span class="glyph" title="">⪇</span> </td> </tr>
     *       <tr id="entity-lneqq"><td> <code title="">lneqq;</code> </td> <td> U+02268 </td> <td> <span class="glyph" title="">≨</span> </td> </tr>
     *       <tr id="entity-lnsim"><td> <code title="">lnsim;</code> </td> <td> U+022E6 </td> <td> <span class="glyph" title="">⋦</span> </td> </tr>
     *       <tr id="entity-loang"><td> <code title="">loang;</code> </td> <td> U+027EC </td> <td> <span class="glyph" title="">⟬</span> </td> </tr>
     *       <tr id="entity-loarr"><td> <code title="">loarr;</code> </td> <td> U+021FD </td> <td> <span class="glyph" title="">⇽</span> </td> </tr>
     *       <tr id="entity-lobrk"><td> <code title="">lobrk;</code> </td> <td> U+027E6 </td> <td> <span class="glyph" title="">⟦</span> </td> </tr>
     *       <tr id="entity-LongLeftArrow"><td> <code title="">LongLeftArrow;</code> </td> <td> U+027F5 </td> <td> <span class="glyph" title="">⟵</span> </td> </tr>
     *       <tr id="entity-Longleftarrow"><td> <code title="">Longleftarrow;</code> </td> <td> U+027F8 </td> <td> <span class="glyph" title="">⟸</span> </td> </tr>
     *       <tr id="entity-longleftarrow"><td> <code title="">longleftarrow;</code> </td> <td> U+027F5 </td> <td> <span class="glyph" title="">⟵</span> </td> </tr>
     *       <tr id="entity-LongLeftRightArrow"><td> <code title="">LongLeftRightArrow;</code> </td> <td> U+027F7 </td> <td> <span class="glyph" title="">⟷</span> </td> </tr>
     *       <tr id="entity-Longleftrightarrow"><td> <code title="">Longleftrightarrow;</code> </td> <td> U+027FA </td> <td> <span class="glyph" title="">⟺</span> </td> </tr>
     *       <tr id="entity-longleftrightarrow"><td> <code title="">longleftrightarrow;</code> </td> <td> U+027F7 </td> <td> <span class="glyph" title="">⟷</span> </td> </tr>
     *       <tr id="entity-longmapsto"><td> <code title="">longmapsto;</code> </td> <td> U+027FC </td> <td> <span class="glyph" title="">⟼</span> </td> </tr>
     *       <tr id="entity-LongRightArrow"><td> <code title="">LongRightArrow;</code> </td> <td> U+027F6 </td> <td> <span class="glyph" title="">⟶</span> </td> </tr>
     *       <tr id="entity-Longrightarrow"><td> <code title="">Longrightarrow;</code> </td> <td> U+027F9 </td> <td> <span class="glyph" title="">⟹</span> </td> </tr>
     *       <tr id="entity-longrightarrow"><td> <code title="">longrightarrow;</code> </td> <td> U+027F6 </td> <td> <span class="glyph" title="">⟶</span> </td> </tr>
     *       <tr id="entity-looparrowleft"><td> <code title="">looparrowleft;</code> </td> <td> U+021AB </td> <td> <span class="glyph" title="">↫</span> </td> </tr>
     *       <tr id="entity-looparrowright"><td> <code title="">looparrowright;</code> </td> <td> U+021AC </td> <td> <span class="glyph" title="">↬</span> </td> </tr>
     *       <tr id="entity-lopar"><td> <code title="">lopar;</code> </td> <td> U+02985 </td> <td> <span class="glyph" title="">⦅</span> </td> </tr>
     *       <tr id="entity-Lopf"><td> <code title="">Lopf;</code> </td> <td> U+1D543 </td> <td> <span class="glyph" title="">𝕃</span> </td> </tr>
     *       <tr id="entity-lopf"><td> <code title="">lopf;</code> </td> <td> U+1D55D </td> <td> <span class="glyph" title="">𝕝</span> </td> </tr>
     *       <tr id="entity-loplus"><td> <code title="">loplus;</code> </td> <td> U+02A2D </td> <td> <span class="glyph" title="">⨭</span> </td> </tr>
     *       <tr id="entity-lotimes"><td> <code title="">lotimes;</code> </td> <td> U+02A34 </td> <td> <span class="glyph" title="">⨴</span> </td> </tr>
     *       <tr id="entity-lowast"><td> <code title="">lowast;</code> </td> <td> U+02217 </td> <td> <span class="glyph" title="">∗</span> </td> </tr>
     *       <tr id="entity-lowbar"><td> <code title="">lowbar;</code> </td> <td> U+0005F </td> <td> <span class="glyph" title="">_</span> </td> </tr>
     *       <tr id="entity-LowerLeftArrow"><td> <code title="">LowerLeftArrow;</code> </td> <td> U+02199 </td> <td> <span class="glyph" title="">↙</span> </td> </tr>
     *       <tr id="entity-LowerRightArrow"><td> <code title="">LowerRightArrow;</code> </td> <td> U+02198 </td> <td> <span class="glyph" title="">↘</span> </td> </tr>
     *       <tr id="entity-loz"><td> <code title="">loz;</code> </td> <td> U+025CA </td> <td> <span class="glyph" title="">◊</span> </td> </tr>
     *       <tr id="entity-lozenge"><td> <code title="">lozenge;</code> </td> <td> U+025CA </td> <td> <span class="glyph" title="">◊</span> </td> </tr>
     *       <tr id="entity-lozf"><td> <code title="">lozf;</code> </td> <td> U+029EB </td> <td> <span class="glyph" title="">⧫</span> </td> </tr>
     *       <tr id="entity-lpar"><td> <code title="">lpar;</code> </td> <td> U+00028 </td> <td> <span class="glyph" title="">(</span> </td> </tr>
     *       <tr id="entity-lparlt"><td> <code title="">lparlt;</code> </td> <td> U+02993 </td> <td> <span class="glyph" title="">⦓</span> </td> </tr>
     *       <tr id="entity-lrarr"><td> <code title="">lrarr;</code> </td> <td> U+021C6 </td> <td> <span class="glyph" title="">⇆</span> </td> </tr>
     *       <tr id="entity-lrcorner"><td> <code title="">lrcorner;</code> </td> <td> U+0231F </td> <td> <span class="glyph" title="">⌟</span> </td> </tr>
     *       <tr id="entity-lrhar"><td> <code title="">lrhar;</code> </td> <td> U+021CB </td> <td> <span class="glyph" title="">⇋</span> </td> </tr>
     *       <tr id="entity-lrhard"><td> <code title="">lrhard;</code> </td> <td> U+0296D </td> <td> <span class="glyph" title="">⥭</span> </td> </tr>
     *       <tr id="entity-lrm"><td> <code title="">lrm;</code> </td> <td> U+0200E </td> <td> <span class="glyph" title="">‎</span> </td> </tr>
     *       <tr id="entity-lrtri"><td> <code title="">lrtri;</code> </td> <td> U+022BF </td> <td> <span class="glyph" title="">⊿</span> </td> </tr>
     *       <tr id="entity-lsaquo"><td> <code title="">lsaquo;</code> </td> <td> U+02039 </td> <td> <span class="glyph" title="">‹</span> </td> </tr>
     *       <tr id="entity-Lscr"><td> <code title="">Lscr;</code> </td> <td> U+02112 </td> <td> <span class="glyph" title="">ℒ</span> </td> </tr>
     *       <tr id="entity-lscr"><td> <code title="">lscr;</code> </td> <td> U+1D4C1 </td> <td> <span class="glyph" title="">𝓁</span> </td> </tr>
     *       <tr id="entity-Lsh"><td> <code title="">Lsh;</code> </td> <td> U+021B0 </td> <td> <span class="glyph" title="">↰</span> </td> </tr>
     *       <tr id="entity-lsh"><td> <code title="">lsh;</code> </td> <td> U+021B0 </td> <td> <span class="glyph" title="">↰</span> </td> </tr>
     *       <tr id="entity-lsim"><td> <code title="">lsim;</code> </td> <td> U+02272 </td> <td> <span class="glyph" title="">≲</span> </td> </tr>
     *       <tr id="entity-lsime"><td> <code title="">lsime;</code> </td> <td> U+02A8D </td> <td> <span class="glyph" title="">⪍</span> </td> </tr>
     *       <tr id="entity-lsimg"><td> <code title="">lsimg;</code> </td> <td> U+02A8F </td> <td> <span class="glyph" title="">⪏</span> </td> </tr>
     *       <tr id="entity-lsqb"><td> <code title="">lsqb;</code> </td> <td> U+0005B </td> <td> <span class="glyph" title="">[</span> </td> </tr>
     *       <tr id="entity-lsquo"><td> <code title="">lsquo;</code> </td> <td> U+02018 </td> <td> <span class="glyph" title="">‘</span> </td> </tr>
     *       <tr id="entity-lsquor"><td> <code title="">lsquor;</code> </td> <td> U+0201A </td> <td> <span class="glyph" title="">‚</span> </td> </tr>
     *       <tr id="entity-Lstrok"><td> <code title="">Lstrok;</code> </td> <td> U+00141 </td> <td> <span class="glyph" title="">Ł</span> </td> </tr>
     *       <tr id="entity-lstrok"><td> <code title="">lstrok;</code> </td> <td> U+00142 </td> <td> <span class="glyph" title="">ł</span> </td> </tr>
     *       <tr id="entity-LT"><td> <code title="">LT;</code> </td> <td> U+0003C </td> <td> <span class="glyph" title="">&lt;</span> </td> </tr>
     *       <tr class="impl" id="entity-LT-legacy"><td> <code title="">LT</code> </td> <td> U+0003C </td> <td> <span title="">&lt;</span> </td> </tr>
     *       <tr id="entity-Lt"><td> <code title="">Lt;</code> </td> <td> U+0226A </td> <td> <span class="glyph" title="">≪</span> </td> </tr>
     *       <tr id="entity-lt"><td> <code title="">lt;</code> </td> <td> U+0003C </td> <td> <span class="glyph" title="">&lt;</span> </td> </tr>
     *       <tr class="impl" id="entity-lt-legacy"><td> <code title="">lt</code> </td> <td> U+0003C </td> <td> <span title="">&lt;</span> </td> </tr>
     *       <tr id="entity-ltcc"><td> <code title="">ltcc;</code> </td> <td> U+02AA6 </td> <td> <span class="glyph" title="">⪦</span> </td> </tr>
     *       <tr id="entity-ltcir"><td> <code title="">ltcir;</code> </td> <td> U+02A79 </td> <td> <span class="glyph" title="">⩹</span> </td> </tr>
     *       <tr id="entity-ltdot"><td> <code title="">ltdot;</code> </td> <td> U+022D6 </td> <td> <span class="glyph" title="">⋖</span> </td> </tr>
     *       <tr id="entity-lthree"><td> <code title="">lthree;</code> </td> <td> U+022CB </td> <td> <span class="glyph" title="">⋋</span> </td> </tr>
     *       <tr id="entity-ltimes"><td> <code title="">ltimes;</code> </td> <td> U+022C9 </td> <td> <span class="glyph" title="">⋉</span> </td> </tr>
     *       <tr id="entity-ltlarr"><td> <code title="">ltlarr;</code> </td> <td> U+02976 </td> <td> <span class="glyph" title="">⥶</span> </td> </tr>
     *       <tr id="entity-ltquest"><td> <code title="">ltquest;</code> </td> <td> U+02A7B </td> <td> <span class="glyph" title="">⩻</span> </td> </tr>
     *       <tr id="entity-ltri"><td> <code title="">ltri;</code> </td> <td> U+025C3 </td> <td> <span class="glyph" title="">◃</span> </td> </tr>
     *       <tr id="entity-ltrie"><td> <code title="">ltrie;</code> </td> <td> U+022B4 </td> <td> <span class="glyph" title="">⊴</span> </td> </tr>
     *       <tr id="entity-ltrif"><td> <code title="">ltrif;</code> </td> <td> U+025C2 </td> <td> <span class="glyph" title="">◂</span> </td> </tr>
     *       <tr id="entity-ltrPar"><td> <code title="">ltrPar;</code> </td> <td> U+02996 </td> <td> <span class="glyph" title="">⦖</span> </td> </tr>
     *       <tr id="entity-lurdshar"><td> <code title="">lurdshar;</code> </td> <td> U+0294A </td> <td> <span class="glyph" title="">⥊</span> </td> </tr>
     *       <tr id="entity-luruhar"><td> <code title="">luruhar;</code> </td> <td> U+02966 </td> <td> <span class="glyph" title="">⥦</span> </td> </tr>
     *       <tr id="entity-lvertneqq"><td> <code title="">lvertneqq;</code> </td> <td> U+02268 U+0FE00 </td> <td> <span class="glyph compound" title="">≨︀</span> </td> </tr>
     *       <tr id="entity-lvnE"><td> <code title="">lvnE;</code> </td> <td> U+02268 U+0FE00 </td> <td> <span class="glyph compound" title="">≨︀</span> </td> </tr>
     *       <tr id="entity-macr"><td> <code title="">macr;</code> </td> <td> U+000AF </td> <td> <span class="glyph" title="">¯</span> </td> </tr>
     *       <tr class="impl" id="entity-macr-legacy"><td> <code title="">macr</code> </td> <td> U+000AF </td> <td> <span title="">¯</span> </td> </tr>
     *       <tr id="entity-male"><td> <code title="">male;</code> </td> <td> U+02642 </td> <td> <span class="glyph" title="">♂</span> </td> </tr>
     *       <tr id="entity-malt"><td> <code title="">malt;</code> </td> <td> U+02720 </td> <td> <span class="glyph" title="">✠</span> </td> </tr>
     *       <tr id="entity-maltese"><td> <code title="">maltese;</code> </td> <td> U+02720 </td> <td> <span class="glyph" title="">✠</span> </td> </tr>
     *       <tr id="entity-Map"><td> <code title="">Map;</code> </td> <td> U+02905 </td> <td> <span class="glyph" title="">⤅</span> </td> </tr>
     *       <tr id="entity-map"><td> <code title="">map;</code> </td> <td> U+021A6 </td> <td> <span class="glyph" title="">↦</span> </td> </tr>
     *       <tr id="entity-mapsto"><td> <code title="">mapsto;</code> </td> <td> U+021A6 </td> <td> <span class="glyph" title="">↦</span> </td> </tr>
     *       <tr id="entity-mapstodown"><td> <code title="">mapstodown;</code> </td> <td> U+021A7 </td> <td> <span class="glyph" title="">↧</span> </td> </tr>
     *       <tr id="entity-mapstoleft"><td> <code title="">mapstoleft;</code> </td> <td> U+021A4 </td> <td> <span class="glyph" title="">↤</span> </td> </tr>
     *       <tr id="entity-mapstoup"><td> <code title="">mapstoup;</code> </td> <td> U+021A5 </td> <td> <span class="glyph" title="">↥</span> </td> </tr>
     *       <tr id="entity-marker"><td> <code title="">marker;</code> </td> <td> U+025AE </td> <td> <span class="glyph" title="">▮</span> </td> </tr>
     *       <tr id="entity-mcomma"><td> <code title="">mcomma;</code> </td> <td> U+02A29 </td> <td> <span class="glyph" title="">⨩</span> </td> </tr>
     *       <tr id="entity-Mcy"><td> <code title="">Mcy;</code> </td> <td> U+0041C </td> <td> <span class="glyph" title="">М</span> </td> </tr>
     *       <tr id="entity-mcy"><td> <code title="">mcy;</code> </td> <td> U+0043C </td> <td> <span class="glyph" title="">м</span> </td> </tr>
     *       <tr id="entity-mdash"><td> <code title="">mdash;</code> </td> <td> U+02014 </td> <td> <span class="glyph" title="">—</span> </td> </tr>
     *       <tr id="entity-mDDot"><td> <code title="">mDDot;</code> </td> <td> U+0223A </td> <td> <span class="glyph" title="">∺</span> </td> </tr>
     *       <tr id="entity-measuredangle"><td> <code title="">measuredangle;</code> </td> <td> U+02221 </td> <td> <span class="glyph" title="">∡</span> </td> </tr>
     *       <tr id="entity-MediumSpace"><td> <code title="">MediumSpace;</code> </td> <td> U+0205F </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-Mellintrf"><td> <code title="">Mellintrf;</code> </td> <td> U+02133 </td> <td> <span class="glyph" title="">ℳ</span> </td> </tr>
     *       <tr id="entity-Mfr"><td> <code title="">Mfr;</code> </td> <td> U+1D510 </td> <td> <span class="glyph" title="">𝔐</span> </td> </tr>
     *       <tr id="entity-mfr"><td> <code title="">mfr;</code> </td> <td> U+1D52A </td> <td> <span class="glyph" title="">𝔪</span> </td> </tr>
     *       <tr id="entity-mho"><td> <code title="">mho;</code> </td> <td> U+02127 </td> <td> <span class="glyph" title="">℧</span> </td> </tr>
     *       <tr id="entity-micro"><td> <code title="">micro;</code> </td> <td> U+000B5 </td> <td> <span class="glyph" title="">µ</span> </td> </tr>
     *       <tr class="impl" id="entity-micro-legacy"><td> <code title="">micro</code> </td> <td> U+000B5 </td> <td> <span title="">µ</span> </td> </tr>
     *       <tr id="entity-mid"><td> <code title="">mid;</code> </td> <td> U+02223 </td> <td> <span class="glyph" title="">∣</span> </td> </tr>
     *       <tr id="entity-midast"><td> <code title="">midast;</code> </td> <td> U+0002A </td> <td> <span class="glyph" title="">*</span> </td> </tr>
     *       <tr id="entity-midcir"><td> <code title="">midcir;</code> </td> <td> U+02AF0 </td> <td> <span class="glyph" title="">⫰</span> </td> </tr>
     *       <tr id="entity-middot"><td> <code title="">middot;</code> </td> <td> U+000B7 </td> <td> <span class="glyph" title="">·</span> </td> </tr>
     *       <tr class="impl" id="entity-middot-legacy"><td> <code title="">middot</code> </td> <td> U+000B7 </td> <td> <span title="">·</span> </td> </tr>
     *       <tr id="entity-minus"><td> <code title="">minus;</code> </td> <td> U+02212 </td> <td> <span class="glyph" title="">−</span> </td> </tr>
     *       <tr id="entity-minusb"><td> <code title="">minusb;</code> </td> <td> U+0229F </td> <td> <span class="glyph" title="">⊟</span> </td> </tr>
     *       <tr id="entity-minusd"><td> <code title="">minusd;</code> </td> <td> U+02238 </td> <td> <span class="glyph" title="">∸</span> </td> </tr>
     *       <tr id="entity-minusdu"><td> <code title="">minusdu;</code> </td> <td> U+02A2A </td> <td> <span class="glyph" title="">⨪</span> </td> </tr>
     *       <tr id="entity-MinusPlus"><td> <code title="">MinusPlus;</code> </td> <td> U+02213 </td> <td> <span class="glyph" title="">∓</span> </td> </tr>
     *       <tr id="entity-mlcp"><td> <code title="">mlcp;</code> </td> <td> U+02ADB </td> <td> <span class="glyph" title="">⫛</span> </td> </tr>
     *       <tr id="entity-mldr"><td> <code title="">mldr;</code> </td> <td> U+02026 </td> <td> <span class="glyph" title="">…</span> </td> </tr>
     *       <tr id="entity-mnplus"><td> <code title="">mnplus;</code> </td> <td> U+02213 </td> <td> <span class="glyph" title="">∓</span> </td> </tr>
     *       <tr id="entity-models"><td> <code title="">models;</code> </td> <td> U+022A7 </td> <td> <span class="glyph" title="">⊧</span> </td> </tr>
     *       <tr id="entity-Mopf"><td> <code title="">Mopf;</code> </td> <td> U+1D544 </td> <td> <span class="glyph" title="">𝕄</span> </td> </tr>
     *       <tr id="entity-mopf"><td> <code title="">mopf;</code> </td> <td> U+1D55E </td> <td> <span class="glyph" title="">𝕞</span> </td> </tr>
     *       <tr id="entity-mp"><td> <code title="">mp;</code> </td> <td> U+02213 </td> <td> <span class="glyph" title="">∓</span> </td> </tr>
     *       <tr id="entity-Mscr"><td> <code title="">Mscr;</code> </td> <td> U+02133 </td> <td> <span class="glyph" title="">ℳ</span> </td> </tr>
     *       <tr id="entity-mscr"><td> <code title="">mscr;</code> </td> <td> U+1D4C2 </td> <td> <span class="glyph" title="">𝓂</span> </td> </tr>
     *       <tr id="entity-mstpos"><td> <code title="">mstpos;</code> </td> <td> U+0223E </td> <td> <span class="glyph" title="">∾</span> </td> </tr>
     *       <tr id="entity-Mu"><td> <code title="">Mu;</code> </td> <td> U+0039C </td> <td> <span class="glyph" title="">Μ</span> </td> </tr>
     *       <tr id="entity-mu"><td> <code title="">mu;</code> </td> <td> U+003BC </td> <td> <span class="glyph" title="">μ</span> </td> </tr>
     *       <tr id="entity-multimap"><td> <code title="">multimap;</code> </td> <td> U+022B8 </td> <td> <span class="glyph" title="">⊸</span> </td> </tr>
     *       <tr id="entity-mumap"><td> <code title="">mumap;</code> </td> <td> U+022B8 </td> <td> <span class="glyph" title="">⊸</span> </td> </tr>
     *       <tr id="entity-nabla"><td> <code title="">nabla;</code> </td> <td> U+02207 </td> <td> <span class="glyph" title="">∇</span> </td> </tr>
     *       <tr id="entity-Nacute"><td> <code title="">Nacute;</code> </td> <td> U+00143 </td> <td> <span class="glyph" title="">Ń</span> </td> </tr>
     *       <tr id="entity-nacute"><td> <code title="">nacute;</code> </td> <td> U+00144 </td> <td> <span class="glyph" title="">ń</span> </td> </tr>
     *       <tr id="entity-nang"><td> <code title="">nang;</code> </td> <td> U+02220 U+020D2 </td> <td> <span class="glyph compound" title="">∠⃒</span> </td> </tr>
     *       <tr id="entity-nap"><td> <code title="">nap;</code> </td> <td> U+02249 </td> <td> <span class="glyph" title="">≉</span> </td> </tr>
     *       <tr id="entity-napE"><td> <code title="">napE;</code> </td> <td> U+02A70 U+00338 </td> <td> <span class="glyph compound" title="">⩰̸</span> </td> </tr>
     *       <tr id="entity-napid"><td> <code title="">napid;</code> </td> <td> U+0224B U+00338 </td> <td> <span class="glyph compound" title="">≋̸</span> </td> </tr>
     *       <tr id="entity-napos"><td> <code title="">napos;</code> </td> <td> U+00149 </td> <td> <span class="glyph" title="">ŉ</span> </td> </tr>
     *       <tr id="entity-napprox"><td> <code title="">napprox;</code> </td> <td> U+02249 </td> <td> <span class="glyph" title="">≉</span> </td> </tr>
     *       <tr id="entity-natur"><td> <code title="">natur;</code> </td> <td> U+0266E </td> <td> <span class="glyph" title="">♮</span> </td> </tr>
     *       <tr id="entity-natural"><td> <code title="">natural;</code> </td> <td> U+0266E </td> <td> <span class="glyph" title="">♮</span> </td> </tr>
     *       <tr id="entity-naturals"><td> <code title="">naturals;</code> </td> <td> U+02115 </td> <td> <span class="glyph" title="">ℕ</span> </td> </tr>
     *       <tr id="entity-nbsp"><td> <code title="">nbsp;</code> </td> <td> U+000A0 </td> <td> <span class="glyph" title="">&nbsp;</span> </td> </tr>
     *       <tr class="impl" id="entity-nbsp-legacy"><td> <code title="">nbsp</code> </td> <td> U+000A0 </td> <td> <span title="">&nbsp;</span> </td> </tr>
     *       <tr id="entity-nbump"><td> <code title="">nbump;</code> </td> <td> U+0224E U+00338 </td> <td> <span class="glyph compound" title="">≎̸</span> </td> </tr>
     *       <tr id="entity-nbumpe"><td> <code title="">nbumpe;</code> </td> <td> U+0224F U+00338 </td> <td> <span class="glyph compound" title="">≏̸</span> </td> </tr>
     *       <tr id="entity-ncap"><td> <code title="">ncap;</code> </td> <td> U+02A43 </td> <td> <span class="glyph" title="">⩃</span> </td> </tr>
     *       <tr id="entity-Ncaron"><td> <code title="">Ncaron;</code> </td> <td> U+00147 </td> <td> <span class="glyph" title="">Ň</span> </td> </tr>
     *       <tr id="entity-ncaron"><td> <code title="">ncaron;</code> </td> <td> U+00148 </td> <td> <span class="glyph" title="">ň</span> </td> </tr>
     *       <tr id="entity-Ncedil"><td> <code title="">Ncedil;</code> </td> <td> U+00145 </td> <td> <span class="glyph" title="">Ņ</span> </td> </tr>
     *       <tr id="entity-ncedil"><td> <code title="">ncedil;</code> </td> <td> U+00146 </td> <td> <span class="glyph" title="">ņ</span> </td> </tr>
     *       <tr id="entity-ncong"><td> <code title="">ncong;</code> </td> <td> U+02247 </td> <td> <span class="glyph" title="">≇</span> </td> </tr>
     *       <tr id="entity-ncongdot"><td> <code title="">ncongdot;</code> </td> <td> U+02A6D U+00338 </td> <td> <span class="glyph compound" title="">⩭̸</span> </td> </tr>
     *       <tr id="entity-ncup"><td> <code title="">ncup;</code> </td> <td> U+02A42 </td> <td> <span class="glyph" title="">⩂</span> </td> </tr>
     *       <tr id="entity-Ncy"><td> <code title="">Ncy;</code> </td> <td> U+0041D </td> <td> <span class="glyph" title="">Н</span> </td> </tr>
     *       <tr id="entity-ncy"><td> <code title="">ncy;</code> </td> <td> U+0043D </td> <td> <span class="glyph" title="">н</span> </td> </tr>
     *       <tr id="entity-ndash"><td> <code title="">ndash;</code> </td> <td> U+02013 </td> <td> <span class="glyph" title="">–</span> </td> </tr>
     *       <tr id="entity-ne"><td> <code title="">ne;</code> </td> <td> U+02260 </td> <td> <span class="glyph" title="">≠</span> </td> </tr>
     *       <tr id="entity-nearhk"><td> <code title="">nearhk;</code> </td> <td> U+02924 </td> <td> <span class="glyph" title="">⤤</span> </td> </tr>
     *       <tr id="entity-neArr"><td> <code title="">neArr;</code> </td> <td> U+021D7 </td> <td> <span class="glyph" title="">⇗</span> </td> </tr>
     *       <tr id="entity-nearr"><td> <code title="">nearr;</code> </td> <td> U+02197 </td> <td> <span class="glyph" title="">↗</span> </td> </tr>
     *       <tr id="entity-nearrow"><td> <code title="">nearrow;</code> </td> <td> U+02197 </td> <td> <span class="glyph" title="">↗</span> </td> </tr>
     *       <tr id="entity-nedot"><td> <code title="">nedot;</code> </td> <td> U+02250 U+00338 </td> <td> <span class="glyph compound" title="">≐̸</span> </td> </tr>
     *       <tr id="entity-NegativeMediumSpace"><td> <code title="">NegativeMediumSpace;</code> </td> <td> U+0200B </td> <td> <span class="glyph" title="">​</span> </td> </tr>
     *       <tr id="entity-NegativeThickSpace"><td> <code title="">NegativeThickSpace;</code> </td> <td> U+0200B </td> <td> <span class="glyph" title="">​</span> </td> </tr>
     *       <tr id="entity-NegativeThinSpace"><td> <code title="">NegativeThinSpace;</code> </td> <td> U+0200B </td> <td> <span class="glyph" title="">​</span> </td> </tr>
     *       <tr id="entity-NegativeVeryThinSpace"><td> <code title="">NegativeVeryThinSpace;</code> </td> <td> U+0200B </td> <td> <span class="glyph" title="">​</span> </td> </tr>
     *       <tr id="entity-nequiv"><td> <code title="">nequiv;</code> </td> <td> U+02262 </td> <td> <span class="glyph" title="">≢</span> </td> </tr>
     *       <tr id="entity-nesear"><td> <code title="">nesear;</code> </td> <td> U+02928 </td> <td> <span class="glyph" title="">⤨</span> </td> </tr>
     *       <tr id="entity-nesim"><td> <code title="">nesim;</code> </td> <td> U+02242 U+00338 </td> <td> <span class="glyph compound" title="">≂̸</span> </td> </tr>
     *       <tr id="entity-NestedGreaterGreater"><td> <code title="">NestedGreaterGreater;</code> </td> <td> U+0226B </td> <td> <span class="glyph" title="">≫</span> </td> </tr>
     *       <tr id="entity-NestedLessLess"><td> <code title="">NestedLessLess;</code> </td> <td> U+0226A </td> <td> <span class="glyph" title="">≪</span> </td> </tr>
     *       <tr id="entity-NewLine"><td> <code title="">NewLine;</code> </td> <td> U+0000A </td> <td> <span class="glyph control" title="">␊</span> </td> </tr>
     *       <tr id="entity-nexist"><td> <code title="">nexist;</code> </td> <td> U+02204 </td> <td> <span class="glyph" title="">∄</span> </td> </tr>
     *       <tr id="entity-nexists"><td> <code title="">nexists;</code> </td> <td> U+02204 </td> <td> <span class="glyph" title="">∄</span> </td> </tr>
     *       <tr id="entity-Nfr"><td> <code title="">Nfr;</code> </td> <td> U+1D511 </td> <td> <span class="glyph" title="">𝔑</span> </td> </tr>
     *       <tr id="entity-nfr"><td> <code title="">nfr;</code> </td> <td> U+1D52B </td> <td> <span class="glyph" title="">𝔫</span> </td> </tr>
     *       <tr id="entity-ngE"><td> <code title="">ngE;</code> </td> <td> U+02267 U+00338 </td> <td> <span class="glyph compound" title="">≧̸</span> </td> </tr>
     *       <tr id="entity-nge"><td> <code title="">nge;</code> </td> <td> U+02271 </td> <td> <span class="glyph" title="">≱</span> </td> </tr>
     *       <tr id="entity-ngeq"><td> <code title="">ngeq;</code> </td> <td> U+02271 </td> <td> <span class="glyph" title="">≱</span> </td> </tr>
     *       <tr id="entity-ngeqq"><td> <code title="">ngeqq;</code> </td> <td> U+02267 U+00338 </td> <td> <span class="glyph compound" title="">≧̸</span> </td> </tr>
     *       <tr id="entity-ngeqslant"><td> <code title="">ngeqslant;</code> </td> <td> U+02A7E U+00338 </td> <td> <span class="glyph compound" title="">⩾̸</span> </td> </tr>
     *       <tr id="entity-nges"><td> <code title="">nges;</code> </td> <td> U+02A7E U+00338 </td> <td> <span class="glyph compound" title="">⩾̸</span> </td> </tr>
     *       <tr id="entity-nGg"><td> <code title="">nGg;</code> </td> <td> U+022D9 U+00338 </td> <td> <span class="glyph compound" title="">⋙̸</span> </td> </tr>
     *       <tr id="entity-ngsim"><td> <code title="">ngsim;</code> </td> <td> U+02275 </td> <td> <span class="glyph" title="">≵</span> </td> </tr>
     *       <tr id="entity-nGt"><td> <code title="">nGt;</code> </td> <td> U+0226B U+020D2 </td> <td> <span class="glyph compound" title="">≫⃒</span> </td> </tr>
     *       <tr id="entity-ngt"><td> <code title="">ngt;</code> </td> <td> U+0226F </td> <td> <span class="glyph" title="">≯</span> </td> </tr>
     *       <tr id="entity-ngtr"><td> <code title="">ngtr;</code> </td> <td> U+0226F </td> <td> <span class="glyph" title="">≯</span> </td> </tr>
     *       <tr id="entity-nGtv"><td> <code title="">nGtv;</code> </td> <td> U+0226B U+00338 </td> <td> <span class="glyph compound" title="">≫̸</span> </td> </tr>
     *       <tr id="entity-nhArr"><td> <code title="">nhArr;</code> </td> <td> U+021CE </td> <td> <span class="glyph" title="">⇎</span> </td> </tr>
     *       <tr id="entity-nharr"><td> <code title="">nharr;</code> </td> <td> U+021AE </td> <td> <span class="glyph" title="">↮</span> </td> </tr>
     *       <tr id="entity-nhpar"><td> <code title="">nhpar;</code> </td> <td> U+02AF2 </td> <td> <span class="glyph" title="">⫲</span> </td> </tr>
     *       <tr id="entity-ni"><td> <code title="">ni;</code> </td> <td> U+0220B </td> <td> <span class="glyph" title="">∋</span> </td> </tr>
     *       <tr id="entity-nis"><td> <code title="">nis;</code> </td> <td> U+022FC </td> <td> <span class="glyph" title="">⋼</span> </td> </tr>
     *       <tr id="entity-nisd"><td> <code title="">nisd;</code> </td> <td> U+022FA </td> <td> <span class="glyph" title="">⋺</span> </td> </tr>
     *       <tr id="entity-niv"><td> <code title="">niv;</code> </td> <td> U+0220B </td> <td> <span class="glyph" title="">∋</span> </td> </tr>
     *       <tr id="entity-NJcy"><td> <code title="">NJcy;</code> </td> <td> U+0040A </td> <td> <span class="glyph" title="">Њ</span> </td> </tr>
     *       <tr id="entity-njcy"><td> <code title="">njcy;</code> </td> <td> U+0045A </td> <td> <span class="glyph" title="">њ</span> </td> </tr>
     *       <tr id="entity-nlArr"><td> <code title="">nlArr;</code> </td> <td> U+021CD </td> <td> <span class="glyph" title="">⇍</span> </td> </tr>
     *       <tr id="entity-nlarr"><td> <code title="">nlarr;</code> </td> <td> U+0219A </td> <td> <span class="glyph" title="">↚</span> </td> </tr>
     *       <tr id="entity-nldr"><td> <code title="">nldr;</code> </td> <td> U+02025 </td> <td> <span class="glyph" title="">‥</span> </td> </tr>
     *       <tr id="entity-nlE"><td> <code title="">nlE;</code> </td> <td> U+02266 U+00338 </td> <td> <span class="glyph compound" title="">≦̸</span> </td> </tr>
     *       <tr id="entity-nle"><td> <code title="">nle;</code> </td> <td> U+02270 </td> <td> <span class="glyph" title="">≰</span> </td> </tr>
     *       <tr id="entity-nLeftarrow"><td> <code title="">nLeftarrow;</code> </td> <td> U+021CD </td> <td> <span class="glyph" title="">⇍</span> </td> </tr>
     *       <tr id="entity-nleftarrow"><td> <code title="">nleftarrow;</code> </td> <td> U+0219A </td> <td> <span class="glyph" title="">↚</span> </td> </tr>
     *       <tr id="entity-nLeftrightarrow"><td> <code title="">nLeftrightarrow;</code> </td> <td> U+021CE </td> <td> <span class="glyph" title="">⇎</span> </td> </tr>
     *       <tr id="entity-nleftrightarrow"><td> <code title="">nleftrightarrow;</code> </td> <td> U+021AE </td> <td> <span class="glyph" title="">↮</span> </td> </tr>
     *       <tr id="entity-nleq"><td> <code title="">nleq;</code> </td> <td> U+02270 </td> <td> <span class="glyph" title="">≰</span> </td> </tr>
     *       <tr id="entity-nleqq"><td> <code title="">nleqq;</code> </td> <td> U+02266 U+00338 </td> <td> <span class="glyph compound" title="">≦̸</span> </td> </tr>
     *       <tr id="entity-nleqslant"><td> <code title="">nleqslant;</code> </td> <td> U+02A7D U+00338 </td> <td> <span class="glyph compound" title="">⩽̸</span> </td> </tr>
     *       <tr id="entity-nles"><td> <code title="">nles;</code> </td> <td> U+02A7D U+00338 </td> <td> <span class="glyph compound" title="">⩽̸</span> </td> </tr>
     *       <tr id="entity-nless"><td> <code title="">nless;</code> </td> <td> U+0226E </td> <td> <span class="glyph" title="">≮</span> </td> </tr>
     *       <tr id="entity-nLl"><td> <code title="">nLl;</code> </td> <td> U+022D8 U+00338 </td> <td> <span class="glyph compound" title="">⋘̸</span> </td> </tr>
     *       <tr id="entity-nlsim"><td> <code title="">nlsim;</code> </td> <td> U+02274 </td> <td> <span class="glyph" title="">≴</span> </td> </tr>
     *       <tr id="entity-nLt"><td> <code title="">nLt;</code> </td> <td> U+0226A U+020D2 </td> <td> <span class="glyph compound" title="">≪⃒</span> </td> </tr>
     *       <tr id="entity-nlt"><td> <code title="">nlt;</code> </td> <td> U+0226E </td> <td> <span class="glyph" title="">≮</span> </td> </tr>
     *       <tr id="entity-nltri"><td> <code title="">nltri;</code> </td> <td> U+022EA </td> <td> <span class="glyph" title="">⋪</span> </td> </tr>
     *       <tr id="entity-nltrie"><td> <code title="">nltrie;</code> </td> <td> U+022EC </td> <td> <span class="glyph" title="">⋬</span> </td> </tr>
     *       <tr id="entity-nLtv"><td> <code title="">nLtv;</code> </td> <td> U+0226A U+00338 </td> <td> <span class="glyph compound" title="">≪̸</span> </td> </tr>
     *       <tr id="entity-nmid"><td> <code title="">nmid;</code> </td> <td> U+02224 </td> <td> <span class="glyph" title="">∤</span> </td> </tr>
     *       <tr id="entity-NoBreak"><td> <code title="">NoBreak;</code> </td> <td> U+02060 </td> <td> <span class="glyph" title="">⁠</span> </td> </tr>
     *       <tr id="entity-NonBreakingSpace"><td> <code title="">NonBreakingSpace;</code> </td> <td> U+000A0 </td> <td> <span class="glyph" title="">&nbsp;</span> </td> </tr>
     *       <tr id="entity-Nopf"><td> <code title="">Nopf;</code> </td> <td> U+02115 </td> <td> <span class="glyph" title="">ℕ</span> </td> </tr>
     *       <tr id="entity-nopf"><td> <code title="">nopf;</code> </td> <td> U+1D55F </td> <td> <span class="glyph" title="">𝕟</span> </td> </tr>
     *       <tr id="entity-Not"><td> <code title="">Not;</code> </td> <td> U+02AEC </td> <td> <span class="glyph" title="">⫬</span> </td> </tr>
     *       <tr id="entity-not"><td> <code title="">not;</code> </td> <td> U+000AC </td> <td> <span class="glyph" title="">¬</span> </td> </tr>
     *       <tr class="impl" id="entity-not-legacy"><td> <code title="">not</code> </td> <td> U+000AC </td> <td> <span title="">¬</span> </td> </tr>
     *       <tr id="entity-NotCongruent"><td> <code title="">NotCongruent;</code> </td> <td> U+02262 </td> <td> <span class="glyph" title="">≢</span> </td> </tr>
     *       <tr id="entity-NotCupCap"><td> <code title="">NotCupCap;</code> </td> <td> U+0226D </td> <td> <span class="glyph" title="">≭</span> </td> </tr>
     *       <tr id="entity-NotDoubleVerticalBar"><td> <code title="">NotDoubleVerticalBar;</code> </td> <td> U+02226 </td> <td> <span class="glyph" title="">∦</span> </td> </tr>
     *       <tr id="entity-NotElement"><td> <code title="">NotElement;</code> </td> <td> U+02209 </td> <td> <span class="glyph" title="">∉</span> </td> </tr>
     *       <tr id="entity-NotEqual"><td> <code title="">NotEqual;</code> </td> <td> U+02260 </td> <td> <span class="glyph" title="">≠</span> </td> </tr>
     *       <tr id="entity-NotEqualTilde"><td> <code title="">NotEqualTilde;</code> </td> <td> U+02242 U+00338 </td> <td> <span class="glyph compound" title="">≂̸</span> </td> </tr>
     *       <tr id="entity-NotExists"><td> <code title="">NotExists;</code> </td> <td> U+02204 </td> <td> <span class="glyph" title="">∄</span> </td> </tr>
     *       <tr id="entity-NotGreater"><td> <code title="">NotGreater;</code> </td> <td> U+0226F </td> <td> <span class="glyph" title="">≯</span> </td> </tr>
     *       <tr id="entity-NotGreaterEqual"><td> <code title="">NotGreaterEqual;</code> </td> <td> U+02271 </td> <td> <span class="glyph" title="">≱</span> </td> </tr>
     *       <tr id="entity-NotGreaterFullEqual"><td> <code title="">NotGreaterFullEqual;</code> </td> <td> U+02267 U+00338 </td> <td> <span class="glyph compound" title="">≧̸</span> </td> </tr>
     *       <tr id="entity-NotGreaterGreater"><td> <code title="">NotGreaterGreater;</code> </td> <td> U+0226B U+00338 </td> <td> <span class="glyph compound" title="">≫̸</span> </td> </tr>
     *       <tr id="entity-NotGreaterLess"><td> <code title="">NotGreaterLess;</code> </td> <td> U+02279 </td> <td> <span class="glyph" title="">≹</span> </td> </tr>
     *       <tr id="entity-NotGreaterSlantEqual"><td> <code title="">NotGreaterSlantEqual;</code> </td> <td> U+02A7E U+00338 </td> <td> <span class="glyph compound" title="">⩾̸</span> </td> </tr>
     *       <tr id="entity-NotGreaterTilde"><td> <code title="">NotGreaterTilde;</code> </td> <td> U+02275 </td> <td> <span class="glyph" title="">≵</span> </td> </tr>
     *       <tr id="entity-NotHumpDownHump"><td> <code title="">NotHumpDownHump;</code> </td> <td> U+0224E U+00338 </td> <td> <span class="glyph compound" title="">≎̸</span> </td> </tr>
     *       <tr id="entity-NotHumpEqual"><td> <code title="">NotHumpEqual;</code> </td> <td> U+0224F U+00338 </td> <td> <span class="glyph compound" title="">≏̸</span> </td> </tr>
     *       <tr id="entity-notin"><td> <code title="">notin;</code> </td> <td> U+02209 </td> <td> <span class="glyph" title="">∉</span> </td> </tr>
     *       <tr id="entity-notindot"><td> <code title="">notindot;</code> </td> <td> U+022F5 U+00338 </td> <td> <span class="glyph compound" title="">⋵̸</span> </td> </tr>
     *       <tr id="entity-notinE"><td> <code title="">notinE;</code> </td> <td> U+022F9 U+00338 </td> <td> <span class="glyph compound" title="">⋹̸</span> </td> </tr>
     *       <tr id="entity-notinva"><td> <code title="">notinva;</code> </td> <td> U+02209 </td> <td> <span class="glyph" title="">∉</span> </td> </tr>
     *       <tr id="entity-notinvb"><td> <code title="">notinvb;</code> </td> <td> U+022F7 </td> <td> <span class="glyph" title="">⋷</span> </td> </tr>
     *       <tr id="entity-notinvc"><td> <code title="">notinvc;</code> </td> <td> U+022F6 </td> <td> <span class="glyph" title="">⋶</span> </td> </tr>
     *       <tr id="entity-NotLeftTriangle"><td> <code title="">NotLeftTriangle;</code> </td> <td> U+022EA </td> <td> <span class="glyph" title="">⋪</span> </td> </tr>
     *       <tr id="entity-NotLeftTriangleBar"><td> <code title="">NotLeftTriangleBar;</code> </td> <td> U+029CF U+00338 </td> <td> <span class="glyph compound" title="">⧏̸</span> </td> </tr>
     *       <tr id="entity-NotLeftTriangleEqual"><td> <code title="">NotLeftTriangleEqual;</code> </td> <td> U+022EC </td> <td> <span class="glyph" title="">⋬</span> </td> </tr>
     *       <tr id="entity-NotLess"><td> <code title="">NotLess;</code> </td> <td> U+0226E </td> <td> <span class="glyph" title="">≮</span> </td> </tr>
     *       <tr id="entity-NotLessEqual"><td> <code title="">NotLessEqual;</code> </td> <td> U+02270 </td> <td> <span class="glyph" title="">≰</span> </td> </tr>
     *       <tr id="entity-NotLessGreater"><td> <code title="">NotLessGreater;</code> </td> <td> U+02278 </td> <td> <span class="glyph" title="">≸</span> </td> </tr>
     *       <tr id="entity-NotLessLess"><td> <code title="">NotLessLess;</code> </td> <td> U+0226A U+00338 </td> <td> <span class="glyph compound" title="">≪̸</span> </td> </tr>
     *       <tr id="entity-NotLessSlantEqual"><td> <code title="">NotLessSlantEqual;</code> </td> <td> U+02A7D U+00338 </td> <td> <span class="glyph compound" title="">⩽̸</span> </td> </tr>
     *       <tr id="entity-NotLessTilde"><td> <code title="">NotLessTilde;</code> </td> <td> U+02274 </td> <td> <span class="glyph" title="">≴</span> </td> </tr>
     *       <tr id="entity-NotNestedGreaterGreater"><td> <code title="">NotNestedGreaterGreater;</code> </td> <td> U+02AA2 U+00338 </td> <td> <span class="glyph compound" title="">⪢̸</span> </td> </tr>
     *       <tr id="entity-NotNestedLessLess"><td> <code title="">NotNestedLessLess;</code> </td> <td> U+02AA1 U+00338 </td> <td> <span class="glyph compound" title="">⪡̸</span> </td> </tr>
     *       <tr id="entity-notni"><td> <code title="">notni;</code> </td> <td> U+0220C </td> <td> <span class="glyph" title="">∌</span> </td> </tr>
     *       <tr id="entity-notniva"><td> <code title="">notniva;</code> </td> <td> U+0220C </td> <td> <span class="glyph" title="">∌</span> </td> </tr>
     *       <tr id="entity-notnivb"><td> <code title="">notnivb;</code> </td> <td> U+022FE </td> <td> <span class="glyph" title="">⋾</span> </td> </tr>
     *       <tr id="entity-notnivc"><td> <code title="">notnivc;</code> </td> <td> U+022FD </td> <td> <span class="glyph" title="">⋽</span> </td> </tr>
     *       <tr id="entity-NotPrecedes"><td> <code title="">NotPrecedes;</code> </td> <td> U+02280 </td> <td> <span class="glyph" title="">⊀</span> </td> </tr>
     *       <tr id="entity-NotPrecedesEqual"><td> <code title="">NotPrecedesEqual;</code> </td> <td> U+02AAF U+00338 </td> <td> <span class="glyph compound" title="">⪯̸</span> </td> </tr>
     *       <tr id="entity-NotPrecedesSlantEqual"><td> <code title="">NotPrecedesSlantEqual;</code> </td> <td> U+022E0 </td> <td> <span class="glyph" title="">⋠</span> </td> </tr>
     *       <tr id="entity-NotReverseElement"><td> <code title="">NotReverseElement;</code> </td> <td> U+0220C </td> <td> <span class="glyph" title="">∌</span> </td> </tr>
     *       <tr id="entity-NotRightTriangle"><td> <code title="">NotRightTriangle;</code> </td> <td> U+022EB </td> <td> <span class="glyph" title="">⋫</span> </td> </tr>
     *       <tr id="entity-NotRightTriangleBar"><td> <code title="">NotRightTriangleBar;</code> </td> <td> U+029D0 U+00338 </td> <td> <span class="glyph compound" title="">⧐̸</span> </td> </tr>
     *       <tr id="entity-NotRightTriangleEqual"><td> <code title="">NotRightTriangleEqual;</code> </td> <td> U+022ED </td> <td> <span class="glyph" title="">⋭</span> </td> </tr>
     *       <tr id="entity-NotSquareSubset"><td> <code title="">NotSquareSubset;</code> </td> <td> U+0228F U+00338 </td> <td> <span class="glyph compound" title="">⊏̸</span> </td> </tr>
     *       <tr id="entity-NotSquareSubsetEqual"><td> <code title="">NotSquareSubsetEqual;</code> </td> <td> U+022E2 </td> <td> <span class="glyph" title="">⋢</span> </td> </tr>
     *       <tr id="entity-NotSquareSuperset"><td> <code title="">NotSquareSuperset;</code> </td> <td> U+02290 U+00338 </td> <td> <span class="glyph compound" title="">⊐̸</span> </td> </tr>
     *       <tr id="entity-NotSquareSupersetEqual"><td> <code title="">NotSquareSupersetEqual;</code> </td> <td> U+022E3 </td> <td> <span class="glyph" title="">⋣</span> </td> </tr>
     *       <tr id="entity-NotSubset"><td> <code title="">NotSubset;</code> </td> <td> U+02282 U+020D2 </td> <td> <span class="glyph compound" title="">⊂⃒</span> </td> </tr>
     *       <tr id="entity-NotSubsetEqual"><td> <code title="">NotSubsetEqual;</code> </td> <td> U+02288 </td> <td> <span class="glyph" title="">⊈</span> </td> </tr>
     *       <tr id="entity-NotSucceeds"><td> <code title="">NotSucceeds;</code> </td> <td> U+02281 </td> <td> <span class="glyph" title="">⊁</span> </td> </tr>
     *       <tr id="entity-NotSucceedsEqual"><td> <code title="">NotSucceedsEqual;</code> </td> <td> U+02AB0 U+00338 </td> <td> <span class="glyph compound" title="">⪰̸</span> </td> </tr>
     *       <tr id="entity-NotSucceedsSlantEqual"><td> <code title="">NotSucceedsSlantEqual;</code> </td> <td> U+022E1 </td> <td> <span class="glyph" title="">⋡</span> </td> </tr>
     *       <tr id="entity-NotSucceedsTilde"><td> <code title="">NotSucceedsTilde;</code> </td> <td> U+0227F U+00338 </td> <td> <span class="glyph compound" title="">≿̸</span> </td> </tr>
     *       <tr id="entity-NotSuperset"><td> <code title="">NotSuperset;</code> </td> <td> U+02283 U+020D2 </td> <td> <span class="glyph compound" title="">⊃⃒</span> </td> </tr>
     *       <tr id="entity-NotSupersetEqual"><td> <code title="">NotSupersetEqual;</code> </td> <td> U+02289 </td> <td> <span class="glyph" title="">⊉</span> </td> </tr>
     *       <tr id="entity-NotTilde"><td> <code title="">NotTilde;</code> </td> <td> U+02241 </td> <td> <span class="glyph" title="">≁</span> </td> </tr>
     *       <tr id="entity-NotTildeEqual"><td> <code title="">NotTildeEqual;</code> </td> <td> U+02244 </td> <td> <span class="glyph" title="">≄</span> </td> </tr>
     *       <tr id="entity-NotTildeFullEqual"><td> <code title="">NotTildeFullEqual;</code> </td> <td> U+02247 </td> <td> <span class="glyph" title="">≇</span> </td> </tr>
     *       <tr id="entity-NotTildeTilde"><td> <code title="">NotTildeTilde;</code> </td> <td> U+02249 </td> <td> <span class="glyph" title="">≉</span> </td> </tr>
     *       <tr id="entity-NotVerticalBar"><td> <code title="">NotVerticalBar;</code> </td> <td> U+02224 </td> <td> <span class="glyph" title="">∤</span> </td> </tr>
     *       <tr id="entity-npar"><td> <code title="">npar;</code> </td> <td> U+02226 </td> <td> <span class="glyph" title="">∦</span> </td> </tr>
     *       <tr id="entity-nparallel"><td> <code title="">nparallel;</code> </td> <td> U+02226 </td> <td> <span class="glyph" title="">∦</span> </td> </tr>
     *       <tr id="entity-nparsl"><td> <code title="">nparsl;</code> </td> <td> U+02AFD U+020E5 </td> <td> <span class="glyph compound" title="">⫽⃥</span> </td> </tr>
     *       <tr id="entity-npart"><td> <code title="">npart;</code> </td> <td> U+02202 U+00338 </td> <td> <span class="glyph compound" title="">∂̸</span> </td> </tr>
     *       <tr id="entity-npolint"><td> <code title="">npolint;</code> </td> <td> U+02A14 </td> <td> <span class="glyph" title="">⨔</span> </td> </tr>
     *       <tr id="entity-npr"><td> <code title="">npr;</code> </td> <td> U+02280 </td> <td> <span class="glyph" title="">⊀</span> </td> </tr>
     *       <tr id="entity-nprcue"><td> <code title="">nprcue;</code> </td> <td> U+022E0 </td> <td> <span class="glyph" title="">⋠</span> </td> </tr>
     *       <tr id="entity-npre"><td> <code title="">npre;</code> </td> <td> U+02AAF U+00338 </td> <td> <span class="glyph compound" title="">⪯̸</span> </td> </tr>
     *       <tr id="entity-nprec"><td> <code title="">nprec;</code> </td> <td> U+02280 </td> <td> <span class="glyph" title="">⊀</span> </td> </tr>
     *       <tr id="entity-npreceq"><td> <code title="">npreceq;</code> </td> <td> U+02AAF U+00338 </td> <td> <span class="glyph compound" title="">⪯̸</span> </td> </tr>
     *       <tr id="entity-nrArr"><td> <code title="">nrArr;</code> </td> <td> U+021CF </td> <td> <span class="glyph" title="">⇏</span> </td> </tr>
     *       <tr id="entity-nrarr"><td> <code title="">nrarr;</code> </td> <td> U+0219B </td> <td> <span class="glyph" title="">↛</span> </td> </tr>
     *       <tr id="entity-nrarrc"><td> <code title="">nrarrc;</code> </td> <td> U+02933 U+00338 </td> <td> <span class="glyph compound" title="">⤳̸</span> </td> </tr>
     *       <tr id="entity-nrarrw"><td> <code title="">nrarrw;</code> </td> <td> U+0219D U+00338 </td> <td> <span class="glyph compound" title="">↝̸</span> </td> </tr>
     *       <tr id="entity-nRightarrow"><td> <code title="">nRightarrow;</code> </td> <td> U+021CF </td> <td> <span class="glyph" title="">⇏</span> </td> </tr>
     *       <tr id="entity-nrightarrow"><td> <code title="">nrightarrow;</code> </td> <td> U+0219B </td> <td> <span class="glyph" title="">↛</span> </td> </tr>
     *       <tr id="entity-nrtri"><td> <code title="">nrtri;</code> </td> <td> U+022EB </td> <td> <span class="glyph" title="">⋫</span> </td> </tr>
     *       <tr id="entity-nrtrie"><td> <code title="">nrtrie;</code> </td> <td> U+022ED </td> <td> <span class="glyph" title="">⋭</span> </td> </tr>
     *       <tr id="entity-nsc"><td> <code title="">nsc;</code> </td> <td> U+02281 </td> <td> <span class="glyph" title="">⊁</span> </td> </tr>
     *       <tr id="entity-nsccue"><td> <code title="">nsccue;</code> </td> <td> U+022E1 </td> <td> <span class="glyph" title="">⋡</span> </td> </tr>
     *       <tr id="entity-nsce"><td> <code title="">nsce;</code> </td> <td> U+02AB0 U+00338 </td> <td> <span class="glyph compound" title="">⪰̸</span> </td> </tr>
     *       <tr id="entity-Nscr"><td> <code title="">Nscr;</code> </td> <td> U+1D4A9 </td> <td> <span class="glyph" title="">𝒩</span> </td> </tr>
     *       <tr id="entity-nscr"><td> <code title="">nscr;</code> </td> <td> U+1D4C3 </td> <td> <span class="glyph" title="">𝓃</span> </td> </tr>
     *       <tr id="entity-nshortmid"><td> <code title="">nshortmid;</code> </td> <td> U+02224 </td> <td> <span class="glyph" title="">∤</span> </td> </tr>
     *       <tr id="entity-nshortparallel"><td> <code title="">nshortparallel;</code> </td> <td> U+02226 </td> <td> <span class="glyph" title="">∦</span> </td> </tr>
     *       <tr id="entity-nsim"><td> <code title="">nsim;</code> </td> <td> U+02241 </td> <td> <span class="glyph" title="">≁</span> </td> </tr>
     *       <tr id="entity-nsime"><td> <code title="">nsime;</code> </td> <td> U+02244 </td> <td> <span class="glyph" title="">≄</span> </td> </tr>
     *       <tr id="entity-nsimeq"><td> <code title="">nsimeq;</code> </td> <td> U+02244 </td> <td> <span class="glyph" title="">≄</span> </td> </tr>
     *       <tr id="entity-nsmid"><td> <code title="">nsmid;</code> </td> <td> U+02224 </td> <td> <span class="glyph" title="">∤</span> </td> </tr>
     *       <tr id="entity-nspar"><td> <code title="">nspar;</code> </td> <td> U+02226 </td> <td> <span class="glyph" title="">∦</span> </td> </tr>
     *       <tr id="entity-nsqsube"><td> <code title="">nsqsube;</code> </td> <td> U+022E2 </td> <td> <span class="glyph" title="">⋢</span> </td> </tr>
     *       <tr id="entity-nsqsupe"><td> <code title="">nsqsupe;</code> </td> <td> U+022E3 </td> <td> <span class="glyph" title="">⋣</span> </td> </tr>
     *       <tr id="entity-nsub"><td> <code title="">nsub;</code> </td> <td> U+02284 </td> <td> <span class="glyph" title="">⊄</span> </td> </tr>
     *       <tr id="entity-nsubE"><td> <code title="">nsubE;</code> </td> <td> U+02AC5 U+00338 </td> <td> <span class="glyph compound" title="">⫅̸</span> </td> </tr>
     *       <tr id="entity-nsube"><td> <code title="">nsube;</code> </td> <td> U+02288 </td> <td> <span class="glyph" title="">⊈</span> </td> </tr>
     *       <tr id="entity-nsubset"><td> <code title="">nsubset;</code> </td> <td> U+02282 U+020D2 </td> <td> <span class="glyph compound" title="">⊂⃒</span> </td> </tr>
     *       <tr id="entity-nsubseteq"><td> <code title="">nsubseteq;</code> </td> <td> U+02288 </td> <td> <span class="glyph" title="">⊈</span> </td> </tr>
     *       <tr id="entity-nsubseteqq"><td> <code title="">nsubseteqq;</code> </td> <td> U+02AC5 U+00338 </td> <td> <span class="glyph compound" title="">⫅̸</span> </td> </tr>
     *       <tr id="entity-nsucc"><td> <code title="">nsucc;</code> </td> <td> U+02281 </td> <td> <span class="glyph" title="">⊁</span> </td> </tr>
     *       <tr id="entity-nsucceq"><td> <code title="">nsucceq;</code> </td> <td> U+02AB0 U+00338 </td> <td> <span class="glyph compound" title="">⪰̸</span> </td> </tr>
     *       <tr id="entity-nsup"><td> <code title="">nsup;</code> </td> <td> U+02285 </td> <td> <span class="glyph" title="">⊅</span> </td> </tr>
     *       <tr id="entity-nsupE"><td> <code title="">nsupE;</code> </td> <td> U+02AC6 U+00338 </td> <td> <span class="glyph compound" title="">⫆̸</span> </td> </tr>
     *       <tr id="entity-nsupe"><td> <code title="">nsupe;</code> </td> <td> U+02289 </td> <td> <span class="glyph" title="">⊉</span> </td> </tr>
     *       <tr id="entity-nsupset"><td> <code title="">nsupset;</code> </td> <td> U+02283 U+020D2 </td> <td> <span class="glyph compound" title="">⊃⃒</span> </td> </tr>
     *       <tr id="entity-nsupseteq"><td> <code title="">nsupseteq;</code> </td> <td> U+02289 </td> <td> <span class="glyph" title="">⊉</span> </td> </tr>
     *       <tr id="entity-nsupseteqq"><td> <code title="">nsupseteqq;</code> </td> <td> U+02AC6 U+00338 </td> <td> <span class="glyph compound" title="">⫆̸</span> </td> </tr>
     *       <tr id="entity-ntgl"><td> <code title="">ntgl;</code> </td> <td> U+02279 </td> <td> <span class="glyph" title="">≹</span> </td> </tr>
     *       <tr id="entity-Ntilde"><td> <code title="">Ntilde;</code> </td> <td> U+000D1 </td> <td> <span class="glyph" title="">Ñ</span> </td> </tr>
     *       <tr class="impl" id="entity-Ntilde-legacy"><td> <code title="">Ntilde</code> </td> <td> U+000D1 </td> <td> <span title="">Ñ</span> </td> </tr>
     *       <tr id="entity-ntilde"><td> <code title="">ntilde;</code> </td> <td> U+000F1 </td> <td> <span class="glyph" title="">ñ</span> </td> </tr>
     *       <tr class="impl" id="entity-ntilde-legacy"><td> <code title="">ntilde</code> </td> <td> U+000F1 </td> <td> <span title="">ñ</span> </td> </tr>
     *       <tr id="entity-ntlg"><td> <code title="">ntlg;</code> </td> <td> U+02278 </td> <td> <span class="glyph" title="">≸</span> </td> </tr>
     *       <tr id="entity-ntriangleleft"><td> <code title="">ntriangleleft;</code> </td> <td> U+022EA </td> <td> <span class="glyph" title="">⋪</span> </td> </tr>
     *       <tr id="entity-ntrianglelefteq"><td> <code title="">ntrianglelefteq;</code> </td> <td> U+022EC </td> <td> <span class="glyph" title="">⋬</span> </td> </tr>
     *       <tr id="entity-ntriangleright"><td> <code title="">ntriangleright;</code> </td> <td> U+022EB </td> <td> <span class="glyph" title="">⋫</span> </td> </tr>
     *       <tr id="entity-ntrianglerighteq"><td> <code title="">ntrianglerighteq;</code> </td> <td> U+022ED </td> <td> <span class="glyph" title="">⋭</span> </td> </tr>
     *       <tr id="entity-Nu"><td> <code title="">Nu;</code> </td> <td> U+0039D </td> <td> <span class="glyph" title="">Ν</span> </td> </tr>
     *       <tr id="entity-nu"><td> <code title="">nu;</code> </td> <td> U+003BD </td> <td> <span class="glyph" title="">ν</span> </td> </tr>
     *       <tr id="entity-num"><td> <code title="">num;</code> </td> <td> U+00023 </td> <td> <span class="glyph" title="">#</span> </td> </tr>
     *       <tr id="entity-numero"><td> <code title="">numero;</code> </td> <td> U+02116 </td> <td> <span class="glyph" title="">№</span> </td> </tr>
     *       <tr id="entity-numsp"><td> <code title="">numsp;</code> </td> <td> U+02007 </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-nvap"><td> <code title="">nvap;</code> </td> <td> U+0224D U+020D2 </td> <td> <span class="glyph compound" title="">≍⃒</span> </td> </tr>
     *       <tr id="entity-nVDash"><td> <code title="">nVDash;</code> </td> <td> U+022AF </td> <td> <span class="glyph" title="">⊯</span> </td> </tr>
     *       <tr id="entity-nVdash"><td> <code title="">nVdash;</code> </td> <td> U+022AE </td> <td> <span class="glyph" title="">⊮</span> </td> </tr>
     *       <tr id="entity-nvDash"><td> <code title="">nvDash;</code> </td> <td> U+022AD </td> <td> <span class="glyph" title="">⊭</span> </td> </tr>
     *       <tr id="entity-nvdash"><td> <code title="">nvdash;</code> </td> <td> U+022AC </td> <td> <span class="glyph" title="">⊬</span> </td> </tr>
     *       <tr id="entity-nvge"><td> <code title="">nvge;</code> </td> <td> U+02265 U+020D2 </td> <td> <span class="glyph compound" title="">≥⃒</span> </td> </tr>
     *       <tr id="entity-nvgt"><td> <code title="">nvgt;</code> </td> <td> U+0003E U+020D2 </td> <td> <span class="glyph compound" title="">&gt;⃒</span> </td> </tr>
     *       <tr id="entity-nvHarr"><td> <code title="">nvHarr;</code> </td> <td> U+02904 </td> <td> <span class="glyph" title="">⤄</span> </td> </tr>
     *       <tr id="entity-nvinfin"><td> <code title="">nvinfin;</code> </td> <td> U+029DE </td> <td> <span class="glyph" title="">⧞</span> </td> </tr>
     *       <tr id="entity-nvlArr"><td> <code title="">nvlArr;</code> </td> <td> U+02902 </td> <td> <span class="glyph" title="">⤂</span> </td> </tr>
     *       <tr id="entity-nvle"><td> <code title="">nvle;</code> </td> <td> U+02264 U+020D2 </td> <td> <span class="glyph compound" title="">≤⃒</span> </td> </tr>
     *       <tr id="entity-nvlt"><td> <code title="">nvlt;</code> </td> <td> U+0003C U+020D2 </td> <td> <span class="glyph compound" title="">&lt;⃒</span> </td> </tr>
     *       <tr id="entity-nvltrie"><td> <code title="">nvltrie;</code> </td> <td> U+022B4 U+020D2 </td> <td> <span class="glyph compound" title="">⊴⃒</span> </td> </tr>
     *       <tr id="entity-nvrArr"><td> <code title="">nvrArr;</code> </td> <td> U+02903 </td> <td> <span class="glyph" title="">⤃</span> </td> </tr>
     *       <tr id="entity-nvrtrie"><td> <code title="">nvrtrie;</code> </td> <td> U+022B5 U+020D2 </td> <td> <span class="glyph compound" title="">⊵⃒</span> </td> </tr>
     *       <tr id="entity-nvsim"><td> <code title="">nvsim;</code> </td> <td> U+0223C U+020D2 </td> <td> <span class="glyph compound" title="">∼⃒</span> </td> </tr>
     *       <tr id="entity-nwarhk"><td> <code title="">nwarhk;</code> </td> <td> U+02923 </td> <td> <span class="glyph" title="">⤣</span> </td> </tr>
     *       <tr id="entity-nwArr"><td> <code title="">nwArr;</code> </td> <td> U+021D6 </td> <td> <span class="glyph" title="">⇖</span> </td> </tr>
     *       <tr id="entity-nwarr"><td> <code title="">nwarr;</code> </td> <td> U+02196 </td> <td> <span class="glyph" title="">↖</span> </td> </tr>
     *       <tr id="entity-nwarrow"><td> <code title="">nwarrow;</code> </td> <td> U+02196 </td> <td> <span class="glyph" title="">↖</span> </td> </tr>
     *       <tr id="entity-nwnear"><td> <code title="">nwnear;</code> </td> <td> U+02927 </td> <td> <span class="glyph" title="">⤧</span> </td> </tr>
     *       <tr id="entity-Oacute"><td> <code title="">Oacute;</code> </td> <td> U+000D3 </td> <td> <span class="glyph" title="">Ó</span> </td> </tr>
     *       <tr class="impl" id="entity-Oacute-legacy"><td> <code title="">Oacute</code> </td> <td> U+000D3 </td> <td> <span title="">Ó</span> </td> </tr>
     *       <tr id="entity-oacute"><td> <code title="">oacute;</code> </td> <td> U+000F3 </td> <td> <span class="glyph" title="">ó</span> </td> </tr>
     *       <tr class="impl" id="entity-oacute-legacy"><td> <code title="">oacute</code> </td> <td> U+000F3 </td> <td> <span title="">ó</span> </td> </tr>
     *       <tr id="entity-oast"><td> <code title="">oast;</code> </td> <td> U+0229B </td> <td> <span class="glyph" title="">⊛</span> </td> </tr>
     *       <tr id="entity-ocir"><td> <code title="">ocir;</code> </td> <td> U+0229A </td> <td> <span class="glyph" title="">⊚</span> </td> </tr>
     *       <tr id="entity-Ocirc"><td> <code title="">Ocirc;</code> </td> <td> U+000D4 </td> <td> <span class="glyph" title="">Ô</span> </td> </tr>
     *       <tr class="impl" id="entity-Ocirc-legacy"><td> <code title="">Ocirc</code> </td> <td> U+000D4 </td> <td> <span title="">Ô</span> </td> </tr>
     *       <tr id="entity-ocirc"><td> <code title="">ocirc;</code> </td> <td> U+000F4 </td> <td> <span class="glyph" title="">ô</span> </td> </tr>
     *       <tr class="impl" id="entity-ocirc-legacy"><td> <code title="">ocirc</code> </td> <td> U+000F4 </td> <td> <span title="">ô</span> </td> </tr>
     *       <tr id="entity-Ocy"><td> <code title="">Ocy;</code> </td> <td> U+0041E </td> <td> <span class="glyph" title="">О</span> </td> </tr>
     *       <tr id="entity-ocy"><td> <code title="">ocy;</code> </td> <td> U+0043E </td> <td> <span class="glyph" title="">о</span> </td> </tr>
     *       <tr id="entity-odash"><td> <code title="">odash;</code> </td> <td> U+0229D </td> <td> <span class="glyph" title="">⊝</span> </td> </tr>
     *       <tr id="entity-Odblac"><td> <code title="">Odblac;</code> </td> <td> U+00150 </td> <td> <span class="glyph" title="">Ő</span> </td> </tr>
     *       <tr id="entity-odblac"><td> <code title="">odblac;</code> </td> <td> U+00151 </td> <td> <span class="glyph" title="">ő</span> </td> </tr>
     *       <tr id="entity-odiv"><td> <code title="">odiv;</code> </td> <td> U+02A38 </td> <td> <span class="glyph" title="">⨸</span> </td> </tr>
     *       <tr id="entity-odot"><td> <code title="">odot;</code> </td> <td> U+02299 </td> <td> <span class="glyph" title="">⊙</span> </td> </tr>
     *       <tr id="entity-odsold"><td> <code title="">odsold;</code> </td> <td> U+029BC </td> <td> <span class="glyph" title="">⦼</span> </td> </tr>
     *       <tr id="entity-OElig"><td> <code title="">OElig;</code> </td> <td> U+00152 </td> <td> <span class="glyph" title="">Œ</span> </td> </tr>
     *       <tr id="entity-oelig"><td> <code title="">oelig;</code> </td> <td> U+00153 </td> <td> <span class="glyph" title="">œ</span> </td> </tr>
     *       <tr id="entity-ofcir"><td> <code title="">ofcir;</code> </td> <td> U+029BF </td> <td> <span class="glyph" title="">⦿</span> </td> </tr>
     *       <tr id="entity-Ofr"><td> <code title="">Ofr;</code> </td> <td> U+1D512 </td> <td> <span class="glyph" title="">𝔒</span> </td> </tr>
     *       <tr id="entity-ofr"><td> <code title="">ofr;</code> </td> <td> U+1D52C </td> <td> <span class="glyph" title="">𝔬</span> </td> </tr>
     *       <tr id="entity-ogon"><td> <code title="">ogon;</code> </td> <td> U+002DB </td> <td> <span class="glyph" title="">˛</span> </td> </tr>
     *       <tr id="entity-Ograve"><td> <code title="">Ograve;</code> </td> <td> U+000D2 </td> <td> <span class="glyph" title="">Ò</span> </td> </tr>
     *       <tr class="impl" id="entity-Ograve-legacy"><td> <code title="">Ograve</code> </td> <td> U+000D2 </td> <td> <span title="">Ò</span> </td> </tr>
     *       <tr id="entity-ograve"><td> <code title="">ograve;</code> </td> <td> U+000F2 </td> <td> <span class="glyph" title="">ò</span> </td> </tr>
     *       <tr class="impl" id="entity-ograve-legacy"><td> <code title="">ograve</code> </td> <td> U+000F2 </td> <td> <span title="">ò</span> </td> </tr>
     *       <tr id="entity-ogt"><td> <code title="">ogt;</code> </td> <td> U+029C1 </td> <td> <span class="glyph" title="">⧁</span> </td> </tr>
     *       <tr id="entity-ohbar"><td> <code title="">ohbar;</code> </td> <td> U+029B5 </td> <td> <span class="glyph" title="">⦵</span> </td> </tr>
     *       <tr id="entity-ohm"><td> <code title="">ohm;</code> </td> <td> U+003A9 </td> <td> <span class="glyph" title="">Ω</span> </td> </tr>
     *       <tr id="entity-oint"><td> <code title="">oint;</code> </td> <td> U+0222E </td> <td> <span class="glyph" title="">∮</span> </td> </tr>
     *       <tr id="entity-olarr"><td> <code title="">olarr;</code> </td> <td> U+021BA </td> <td> <span class="glyph" title="">↺</span> </td> </tr>
     *       <tr id="entity-olcir"><td> <code title="">olcir;</code> </td> <td> U+029BE </td> <td> <span class="glyph" title="">⦾</span> </td> </tr>
     *       <tr id="entity-olcross"><td> <code title="">olcross;</code> </td> <td> U+029BB </td> <td> <span class="glyph" title="">⦻</span> </td> </tr>
     *       <tr id="entity-oline"><td> <code title="">oline;</code> </td> <td> U+0203E </td> <td> <span class="glyph" title="">‾</span> </td> </tr>
     *       <tr id="entity-olt"><td> <code title="">olt;</code> </td> <td> U+029C0 </td> <td> <span class="glyph" title="">⧀</span> </td> </tr>
     *       <tr id="entity-Omacr"><td> <code title="">Omacr;</code> </td> <td> U+0014C </td> <td> <span class="glyph" title="">Ō</span> </td> </tr>
     *       <tr id="entity-omacr"><td> <code title="">omacr;</code> </td> <td> U+0014D </td> <td> <span class="glyph" title="">ō</span> </td> </tr>
     *       <tr id="entity-Omega"><td> <code title="">Omega;</code> </td> <td> U+003A9 </td> <td> <span class="glyph" title="">Ω</span> </td> </tr>
     *       <tr id="entity-omega"><td> <code title="">omega;</code> </td> <td> U+003C9 </td> <td> <span class="glyph" title="">ω</span> </td> </tr>
     *       <tr id="entity-Omicron"><td> <code title="">Omicron;</code> </td> <td> U+0039F </td> <td> <span class="glyph" title="">Ο</span> </td> </tr>
     *       <tr id="entity-omicron"><td> <code title="">omicron;</code> </td> <td> U+003BF </td> <td> <span class="glyph" title="">ο</span> </td> </tr>
     *       <tr id="entity-omid"><td> <code title="">omid;</code> </td> <td> U+029B6 </td> <td> <span class="glyph" title="">⦶</span> </td> </tr>
     *       <tr id="entity-ominus"><td> <code title="">ominus;</code> </td> <td> U+02296 </td> <td> <span class="glyph" title="">⊖</span> </td> </tr>
     *       <tr id="entity-Oopf"><td> <code title="">Oopf;</code> </td> <td> U+1D546 </td> <td> <span class="glyph" title="">𝕆</span> </td> </tr>
     *       <tr id="entity-oopf"><td> <code title="">oopf;</code> </td> <td> U+1D560 </td> <td> <span class="glyph" title="">𝕠</span> </td> </tr>
     *       <tr id="entity-opar"><td> <code title="">opar;</code> </td> <td> U+029B7 </td> <td> <span class="glyph" title="">⦷</span> </td> </tr>
     *       <tr id="entity-OpenCurlyDoubleQuote"><td> <code title="">OpenCurlyDoubleQuote;</code> </td> <td> U+0201C </td> <td> <span class="glyph" title="">“</span> </td> </tr>
     *       <tr id="entity-OpenCurlyQuote"><td> <code title="">OpenCurlyQuote;</code> </td> <td> U+02018 </td> <td> <span class="glyph" title="">‘</span> </td> </tr>
     *       <tr id="entity-operp"><td> <code title="">operp;</code> </td> <td> U+029B9 </td> <td> <span class="glyph" title="">⦹</span> </td> </tr>
     *       <tr id="entity-oplus"><td> <code title="">oplus;</code> </td> <td> U+02295 </td> <td> <span class="glyph" title="">⊕</span> </td> </tr>
     *       <tr id="entity-Or"><td> <code title="">Or;</code> </td> <td> U+02A54 </td> <td> <span class="glyph" title="">⩔</span> </td> </tr>
     *       <tr id="entity-or"><td> <code title="">or;</code> </td> <td> U+02228 </td> <td> <span class="glyph" title="">∨</span> </td> </tr>
     *       <tr id="entity-orarr"><td> <code title="">orarr;</code> </td> <td> U+021BB </td> <td> <span class="glyph" title="">↻</span> </td> </tr>
     *       <tr id="entity-ord"><td> <code title="">ord;</code> </td> <td> U+02A5D </td> <td> <span class="glyph" title="">⩝</span> </td> </tr>
     *       <tr id="entity-order"><td> <code title="">order;</code> </td> <td> U+02134 </td> <td> <span class="glyph" title="">ℴ</span> </td> </tr>
     *       <tr id="entity-orderof"><td> <code title="">orderof;</code> </td> <td> U+02134 </td> <td> <span class="glyph" title="">ℴ</span> </td> </tr>
     *       <tr id="entity-ordf"><td> <code title="">ordf;</code> </td> <td> U+000AA </td> <td> <span class="glyph" title="">ª</span> </td> </tr>
     *       <tr class="impl" id="entity-ordf-legacy"><td> <code title="">ordf</code> </td> <td> U+000AA </td> <td> <span title="">ª</span> </td> </tr>
     *       <tr id="entity-ordm"><td> <code title="">ordm;</code> </td> <td> U+000BA </td> <td> <span class="glyph" title="">º</span> </td> </tr>
     *       <tr class="impl" id="entity-ordm-legacy"><td> <code title="">ordm</code> </td> <td> U+000BA </td> <td> <span title="">º</span> </td> </tr>
     *       <tr id="entity-origof"><td> <code title="">origof;</code> </td> <td> U+022B6 </td> <td> <span class="glyph" title="">⊶</span> </td> </tr>
     *       <tr id="entity-oror"><td> <code title="">oror;</code> </td> <td> U+02A56 </td> <td> <span class="glyph" title="">⩖</span> </td> </tr>
     *       <tr id="entity-orslope"><td> <code title="">orslope;</code> </td> <td> U+02A57 </td> <td> <span class="glyph" title="">⩗</span> </td> </tr>
     *       <tr id="entity-orv"><td> <code title="">orv;</code> </td> <td> U+02A5B </td> <td> <span class="glyph" title="">⩛</span> </td> </tr>
     *       <tr id="entity-oS"><td> <code title="">oS;</code> </td> <td> U+024C8 </td> <td> <span class="glyph" title="">Ⓢ</span> </td> </tr>
     *       <tr id="entity-Oscr"><td> <code title="">Oscr;</code> </td> <td> U+1D4AA </td> <td> <span class="glyph" title="">𝒪</span> </td> </tr>
     *       <tr id="entity-oscr"><td> <code title="">oscr;</code> </td> <td> U+02134 </td> <td> <span class="glyph" title="">ℴ</span> </td> </tr>
     *       <tr id="entity-Oslash"><td> <code title="">Oslash;</code> </td> <td> U+000D8 </td> <td> <span class="glyph" title="">Ø</span> </td> </tr>
     *       <tr class="impl" id="entity-Oslash-legacy"><td> <code title="">Oslash</code> </td> <td> U+000D8 </td> <td> <span title="">Ø</span> </td> </tr>
     *       <tr id="entity-oslash"><td> <code title="">oslash;</code> </td> <td> U+000F8 </td> <td> <span class="glyph" title="">ø</span> </td> </tr>
     *       <tr class="impl" id="entity-oslash-legacy"><td> <code title="">oslash</code> </td> <td> U+000F8 </td> <td> <span title="">ø</span> </td> </tr>
     *       <tr id="entity-osol"><td> <code title="">osol;</code> </td> <td> U+02298 </td> <td> <span class="glyph" title="">⊘</span> </td> </tr>
     *       <tr id="entity-Otilde"><td> <code title="">Otilde;</code> </td> <td> U+000D5 </td> <td> <span class="glyph" title="">Õ</span> </td> </tr>
     *       <tr class="impl" id="entity-Otilde-legacy"><td> <code title="">Otilde</code> </td> <td> U+000D5 </td> <td> <span title="">Õ</span> </td> </tr>
     *       <tr id="entity-otilde"><td> <code title="">otilde;</code> </td> <td> U+000F5 </td> <td> <span class="glyph" title="">õ</span> </td> </tr>
     *       <tr class="impl" id="entity-otilde-legacy"><td> <code title="">otilde</code> </td> <td> U+000F5 </td> <td> <span title="">õ</span> </td> </tr>
     *       <tr id="entity-Otimes"><td> <code title="">Otimes;</code> </td> <td> U+02A37 </td> <td> <span class="glyph" title="">⨷</span> </td> </tr>
     *       <tr id="entity-otimes"><td> <code title="">otimes;</code> </td> <td> U+02297 </td> <td> <span class="glyph" title="">⊗</span> </td> </tr>
     *       <tr id="entity-otimesas"><td> <code title="">otimesas;</code> </td> <td> U+02A36 </td> <td> <span class="glyph" title="">⨶</span> </td> </tr>
     *       <tr id="entity-Ouml"><td> <code title="">Ouml;</code> </td> <td> U+000D6 </td> <td> <span class="glyph" title="">Ö</span> </td> </tr>
     *       <tr class="impl" id="entity-Ouml-legacy"><td> <code title="">Ouml</code> </td> <td> U+000D6 </td> <td> <span title="">Ö</span> </td> </tr>
     *       <tr id="entity-ouml"><td> <code title="">ouml;</code> </td> <td> U+000F6 </td> <td> <span class="glyph" title="">ö</span> </td> </tr>
     *       <tr class="impl" id="entity-ouml-legacy"><td> <code title="">ouml</code> </td> <td> U+000F6 </td> <td> <span title="">ö</span> </td> </tr>
     *       <tr id="entity-ovbar"><td> <code title="">ovbar;</code> </td> <td> U+0233D </td> <td> <span class="glyph" title="">⌽</span> </td> </tr>
     *       <tr id="entity-OverBar"><td> <code title="">OverBar;</code> </td> <td> U+0203E </td> <td> <span class="glyph" title="">‾</span> </td> </tr>
     *       <tr id="entity-OverBrace"><td> <code title="">OverBrace;</code> </td> <td> U+023DE </td> <td> <span class="glyph" title="">⏞</span> </td> </tr>
     *       <tr id="entity-OverBracket"><td> <code title="">OverBracket;</code> </td> <td> U+023B4 </td> <td> <span class="glyph" title="">⎴</span> </td> </tr>
     *       <tr id="entity-OverParenthesis"><td> <code title="">OverParenthesis;</code> </td> <td> U+023DC </td> <td> <span class="glyph" title="">⏜</span> </td> </tr>
     *       <tr id="entity-par"><td> <code title="">par;</code> </td> <td> U+02225 </td> <td> <span class="glyph" title="">∥</span> </td> </tr>
     *       <tr id="entity-para"><td> <code title="">para;</code> </td> <td> U+000B6 </td> <td> <span class="glyph" title="">¶</span> </td> </tr>
     *       <tr class="impl" id="entity-para-legacy"><td> <code title="">para</code> </td> <td> U+000B6 </td> <td> <span title="">¶</span> </td> </tr>
     *       <tr id="entity-parallel"><td> <code title="">parallel;</code> </td> <td> U+02225 </td> <td> <span class="glyph" title="">∥</span> </td> </tr>
     *       <tr id="entity-parsim"><td> <code title="">parsim;</code> </td> <td> U+02AF3 </td> <td> <span class="glyph" title="">⫳</span> </td> </tr>
     *       <tr id="entity-parsl"><td> <code title="">parsl;</code> </td> <td> U+02AFD </td> <td> <span class="glyph" title="">⫽</span> </td> </tr>
     *       <tr id="entity-part"><td> <code title="">part;</code> </td> <td> U+02202 </td> <td> <span class="glyph" title="">∂</span> </td> </tr>
     *       <tr id="entity-PartialD"><td> <code title="">PartialD;</code> </td> <td> U+02202 </td> <td> <span class="glyph" title="">∂</span> </td> </tr>
     *       <tr id="entity-Pcy"><td> <code title="">Pcy;</code> </td> <td> U+0041F </td> <td> <span class="glyph" title="">П</span> </td> </tr>
     *       <tr id="entity-pcy"><td> <code title="">pcy;</code> </td> <td> U+0043F </td> <td> <span class="glyph" title="">п</span> </td> </tr>
     *       <tr id="entity-percnt"><td> <code title="">percnt;</code> </td> <td> U+00025 </td> <td> <span class="glyph" title="">%</span> </td> </tr>
     *       <tr id="entity-period"><td> <code title="">period;</code> </td> <td> U+0002E </td> <td> <span class="glyph" title="">.</span> </td> </tr>
     *       <tr id="entity-permil"><td> <code title="">permil;</code> </td> <td> U+02030 </td> <td> <span class="glyph" title="">‰</span> </td> </tr>
     *       <tr id="entity-perp"><td> <code title="">perp;</code> </td> <td> U+022A5 </td> <td> <span class="glyph" title="">⊥</span> </td> </tr>
     *       <tr id="entity-pertenk"><td> <code title="">pertenk;</code> </td> <td> U+02031 </td> <td> <span class="glyph" title="">‱</span> </td> </tr>
     *       <tr id="entity-Pfr"><td> <code title="">Pfr;</code> </td> <td> U+1D513 </td> <td> <span class="glyph" title="">𝔓</span> </td> </tr>
     *       <tr id="entity-pfr"><td> <code title="">pfr;</code> </td> <td> U+1D52D </td> <td> <span class="glyph" title="">𝔭</span> </td> </tr>
     *       <tr id="entity-Phi"><td> <code title="">Phi;</code> </td> <td> U+003A6 </td> <td> <span class="glyph" title="">Φ</span> </td> </tr>
     *       <tr id="entity-phi"><td> <code title="">phi;</code> </td> <td> U+003C6 </td> <td> <span class="glyph" title="">φ</span> </td> </tr>
     *       <tr id="entity-phiv"><td> <code title="">phiv;</code> </td> <td> U+003D5 </td> <td> <span class="glyph" title="">ϕ</span> </td> </tr>
     *       <tr id="entity-phmmat"><td> <code title="">phmmat;</code> </td> <td> U+02133 </td> <td> <span class="glyph" title="">ℳ</span> </td> </tr>
     *       <tr id="entity-phone"><td> <code title="">phone;</code> </td> <td> U+0260E </td> <td> <span class="glyph" title="">☎</span> </td> </tr>
     *       <tr id="entity-Pi"><td> <code title="">Pi;</code> </td> <td> U+003A0 </td> <td> <span class="glyph" title="">Π</span> </td> </tr>
     *       <tr id="entity-pi"><td> <code title="">pi;</code> </td> <td> U+003C0 </td> <td> <span class="glyph" title="">π</span> </td> </tr>
     *       <tr id="entity-pitchfork"><td> <code title="">pitchfork;</code> </td> <td> U+022D4 </td> <td> <span class="glyph" title="">⋔</span> </td> </tr>
     *       <tr id="entity-piv"><td> <code title="">piv;</code> </td> <td> U+003D6 </td> <td> <span class="glyph" title="">ϖ</span> </td> </tr>
     *       <tr id="entity-planck"><td> <code title="">planck;</code> </td> <td> U+0210F </td> <td> <span class="glyph" title="">ℏ</span> </td> </tr>
     *       <tr id="entity-planckh"><td> <code title="">planckh;</code> </td> <td> U+0210E </td> <td> <span class="glyph" title="">ℎ</span> </td> </tr>
     *       <tr id="entity-plankv"><td> <code title="">plankv;</code> </td> <td> U+0210F </td> <td> <span class="glyph" title="">ℏ</span> </td> </tr>
     *       <tr id="entity-plus"><td> <code title="">plus;</code> </td> <td> U+0002B </td> <td> <span class="glyph" title="">+</span> </td> </tr>
     *       <tr id="entity-plusacir"><td> <code title="">plusacir;</code> </td> <td> U+02A23 </td> <td> <span class="glyph" title="">⨣</span> </td> </tr>
     *       <tr id="entity-plusb"><td> <code title="">plusb;</code> </td> <td> U+0229E </td> <td> <span class="glyph" title="">⊞</span> </td> </tr>
     *       <tr id="entity-pluscir"><td> <code title="">pluscir;</code> </td> <td> U+02A22 </td> <td> <span class="glyph" title="">⨢</span> </td> </tr>
     *       <tr id="entity-plusdo"><td> <code title="">plusdo;</code> </td> <td> U+02214 </td> <td> <span class="glyph" title="">∔</span> </td> </tr>
     *       <tr id="entity-plusdu"><td> <code title="">plusdu;</code> </td> <td> U+02A25 </td> <td> <span class="glyph" title="">⨥</span> </td> </tr>
     *       <tr id="entity-pluse"><td> <code title="">pluse;</code> </td> <td> U+02A72 </td> <td> <span class="glyph" title="">⩲</span> </td> </tr>
     *       <tr id="entity-PlusMinus"><td> <code title="">PlusMinus;</code> </td> <td> U+000B1 </td> <td> <span class="glyph" title="">±</span> </td> </tr>
     *       <tr id="entity-plusmn"><td> <code title="">plusmn;</code> </td> <td> U+000B1 </td> <td> <span class="glyph" title="">±</span> </td> </tr>
     *       <tr class="impl" id="entity-plusmn-legacy"><td> <code title="">plusmn</code> </td> <td> U+000B1 </td> <td> <span title="">±</span> </td> </tr>
     *       <tr id="entity-plussim"><td> <code title="">plussim;</code> </td> <td> U+02A26 </td> <td> <span class="glyph" title="">⨦</span> </td> </tr>
     *       <tr id="entity-plustwo"><td> <code title="">plustwo;</code> </td> <td> U+02A27 </td> <td> <span class="glyph" title="">⨧</span> </td> </tr>
     *       <tr id="entity-pm"><td> <code title="">pm;</code> </td> <td> U+000B1 </td> <td> <span class="glyph" title="">±</span> </td> </tr>
     *       <tr id="entity-Poincareplane"><td> <code title="">Poincareplane;</code> </td> <td> U+0210C </td> <td> <span class="glyph" title="">ℌ</span> </td> </tr>
     *       <tr id="entity-pointint"><td> <code title="">pointint;</code> </td> <td> U+02A15 </td> <td> <span class="glyph" title="">⨕</span> </td> </tr>
     *       <tr id="entity-Popf"><td> <code title="">Popf;</code> </td> <td> U+02119 </td> <td> <span class="glyph" title="">ℙ</span> </td> </tr>
     *       <tr id="entity-popf"><td> <code title="">popf;</code> </td> <td> U+1D561 </td> <td> <span class="glyph" title="">𝕡</span> </td> </tr>
     *       <tr id="entity-pound"><td> <code title="">pound;</code> </td> <td> U+000A3 </td> <td> <span class="glyph" title="">£</span> </td> </tr>
     *       <tr class="impl" id="entity-pound-legacy"><td> <code title="">pound</code> </td> <td> U+000A3 </td> <td> <span title="">£</span> </td> </tr>
     *       <tr id="entity-Pr"><td> <code title="">Pr;</code> </td> <td> U+02ABB </td> <td> <span class="glyph" title="">⪻</span> </td> </tr>
     *       <tr id="entity-pr"><td> <code title="">pr;</code> </td> <td> U+0227A </td> <td> <span class="glyph" title="">≺</span> </td> </tr>
     *       <tr id="entity-prap"><td> <code title="">prap;</code> </td> <td> U+02AB7 </td> <td> <span class="glyph" title="">⪷</span> </td> </tr>
     *       <tr id="entity-prcue"><td> <code title="">prcue;</code> </td> <td> U+0227C </td> <td> <span class="glyph" title="">≼</span> </td> </tr>
     *       <tr id="entity-prE"><td> <code title="">prE;</code> </td> <td> U+02AB3 </td> <td> <span class="glyph" title="">⪳</span> </td> </tr>
     *       <tr id="entity-pre"><td> <code title="">pre;</code> </td> <td> U+02AAF </td> <td> <span class="glyph" title="">⪯</span> </td> </tr>
     *       <tr id="entity-prec"><td> <code title="">prec;</code> </td> <td> U+0227A </td> <td> <span class="glyph" title="">≺</span> </td> </tr>
     *       <tr id="entity-precapprox"><td> <code title="">precapprox;</code> </td> <td> U+02AB7 </td> <td> <span class="glyph" title="">⪷</span> </td> </tr>
     *       <tr id="entity-preccurlyeq"><td> <code title="">preccurlyeq;</code> </td> <td> U+0227C </td> <td> <span class="glyph" title="">≼</span> </td> </tr>
     *       <tr id="entity-Precedes"><td> <code title="">Precedes;</code> </td> <td> U+0227A </td> <td> <span class="glyph" title="">≺</span> </td> </tr>
     *       <tr id="entity-PrecedesEqual"><td> <code title="">PrecedesEqual;</code> </td> <td> U+02AAF </td> <td> <span class="glyph" title="">⪯</span> </td> </tr>
     *       <tr id="entity-PrecedesSlantEqual"><td> <code title="">PrecedesSlantEqual;</code> </td> <td> U+0227C </td> <td> <span class="glyph" title="">≼</span> </td> </tr>
     *       <tr id="entity-PrecedesTilde"><td> <code title="">PrecedesTilde;</code> </td> <td> U+0227E </td> <td> <span class="glyph" title="">≾</span> </td> </tr>
     *       <tr id="entity-preceq"><td> <code title="">preceq;</code> </td> <td> U+02AAF </td> <td> <span class="glyph" title="">⪯</span> </td> </tr>
     *       <tr id="entity-precnapprox"><td> <code title="">precnapprox;</code> </td> <td> U+02AB9 </td> <td> <span class="glyph" title="">⪹</span> </td> </tr>
     *       <tr id="entity-precneqq"><td> <code title="">precneqq;</code> </td> <td> U+02AB5 </td> <td> <span class="glyph" title="">⪵</span> </td> </tr>
     *       <tr id="entity-precnsim"><td> <code title="">precnsim;</code> </td> <td> U+022E8 </td> <td> <span class="glyph" title="">⋨</span> </td> </tr>
     *       <tr id="entity-precsim"><td> <code title="">precsim;</code> </td> <td> U+0227E </td> <td> <span class="glyph" title="">≾</span> </td> </tr>
     *       <tr id="entity-Prime"><td> <code title="">Prime;</code> </td> <td> U+02033 </td> <td> <span class="glyph" title="">″</span> </td> </tr>
     *       <tr id="entity-prime"><td> <code title="">prime;</code> </td> <td> U+02032 </td> <td> <span class="glyph" title="">′</span> </td> </tr>
     *       <tr id="entity-primes"><td> <code title="">primes;</code> </td> <td> U+02119 </td> <td> <span class="glyph" title="">ℙ</span> </td> </tr>
     *       <tr id="entity-prnap"><td> <code title="">prnap;</code> </td> <td> U+02AB9 </td> <td> <span class="glyph" title="">⪹</span> </td> </tr>
     *       <tr id="entity-prnE"><td> <code title="">prnE;</code> </td> <td> U+02AB5 </td> <td> <span class="glyph" title="">⪵</span> </td> </tr>
     *       <tr id="entity-prnsim"><td> <code title="">prnsim;</code> </td> <td> U+022E8 </td> <td> <span class="glyph" title="">⋨</span> </td> </tr>
     *       <tr id="entity-prod"><td> <code title="">prod;</code> </td> <td> U+0220F </td> <td> <span class="glyph" title="">∏</span> </td> </tr>
     *       <tr id="entity-Product"><td> <code title="">Product;</code> </td> <td> U+0220F </td> <td> <span class="glyph" title="">∏</span> </td> </tr>
     *       <tr id="entity-profalar"><td> <code title="">profalar;</code> </td> <td> U+0232E </td> <td> <span class="glyph" title="">⌮</span> </td> </tr>
     *       <tr id="entity-profline"><td> <code title="">profline;</code> </td> <td> U+02312 </td> <td> <span class="glyph" title="">⌒</span> </td> </tr>
     *       <tr id="entity-profsurf"><td> <code title="">profsurf;</code> </td> <td> U+02313 </td> <td> <span class="glyph" title="">⌓</span> </td> </tr>
     *       <tr id="entity-prop"><td> <code title="">prop;</code> </td> <td> U+0221D </td> <td> <span class="glyph" title="">∝</span> </td> </tr>
     *       <tr id="entity-Proportion"><td> <code title="">Proportion;</code> </td> <td> U+02237 </td> <td> <span class="glyph" title="">∷</span> </td> </tr>
     *       <tr id="entity-Proportional"><td> <code title="">Proportional;</code> </td> <td> U+0221D </td> <td> <span class="glyph" title="">∝</span> </td> </tr>
     *       <tr id="entity-propto"><td> <code title="">propto;</code> </td> <td> U+0221D </td> <td> <span class="glyph" title="">∝</span> </td> </tr>
     *       <tr id="entity-prsim"><td> <code title="">prsim;</code> </td> <td> U+0227E </td> <td> <span class="glyph" title="">≾</span> </td> </tr>
     *       <tr id="entity-prurel"><td> <code title="">prurel;</code> </td> <td> U+022B0 </td> <td> <span class="glyph" title="">⊰</span> </td> </tr>
     *       <tr id="entity-Pscr"><td> <code title="">Pscr;</code> </td> <td> U+1D4AB </td> <td> <span class="glyph" title="">𝒫</span> </td> </tr>
     *       <tr id="entity-pscr"><td> <code title="">pscr;</code> </td> <td> U+1D4C5 </td> <td> <span class="glyph" title="">𝓅</span> </td> </tr>
     *       <tr id="entity-Psi"><td> <code title="">Psi;</code> </td> <td> U+003A8 </td> <td> <span class="glyph" title="">Ψ</span> </td> </tr>
     *       <tr id="entity-psi"><td> <code title="">psi;</code> </td> <td> U+003C8 </td> <td> <span class="glyph" title="">ψ</span> </td> </tr>
     *       <tr id="entity-puncsp"><td> <code title="">puncsp;</code> </td> <td> U+02008 </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-Qfr"><td> <code title="">Qfr;</code> </td> <td> U+1D514 </td> <td> <span class="glyph" title="">𝔔</span> </td> </tr>
     *       <tr id="entity-qfr"><td> <code title="">qfr;</code> </td> <td> U+1D52E </td> <td> <span class="glyph" title="">𝔮</span> </td> </tr>
     *       <tr id="entity-qint"><td> <code title="">qint;</code> </td> <td> U+02A0C </td> <td> <span class="glyph" title="">⨌</span> </td> </tr>
     *       <tr id="entity-Qopf"><td> <code title="">Qopf;</code> </td> <td> U+0211A </td> <td> <span class="glyph" title="">ℚ</span> </td> </tr>
     *       <tr id="entity-qopf"><td> <code title="">qopf;</code> </td> <td> U+1D562 </td> <td> <span class="glyph" title="">𝕢</span> </td> </tr>
     *       <tr id="entity-qprime"><td> <code title="">qprime;</code> </td> <td> U+02057 </td> <td> <span class="glyph" title="">⁗</span> </td> </tr>
     *       <tr id="entity-Qscr"><td> <code title="">Qscr;</code> </td> <td> U+1D4AC </td> <td> <span class="glyph" title="">𝒬</span> </td> </tr>
     *       <tr id="entity-qscr"><td> <code title="">qscr;</code> </td> <td> U+1D4C6 </td> <td> <span class="glyph" title="">𝓆</span> </td> </tr>
     *       <tr id="entity-quaternions"><td> <code title="">quaternions;</code> </td> <td> U+0210D </td> <td> <span class="glyph" title="">ℍ</span> </td> </tr>
     *       <tr id="entity-quatint"><td> <code title="">quatint;</code> </td> <td> U+02A16 </td> <td> <span class="glyph" title="">⨖</span> </td> </tr>
     *       <tr id="entity-quest"><td> <code title="">quest;</code> </td> <td> U+0003F </td> <td> <span class="glyph" title="">?</span> </td> </tr>
     *       <tr id="entity-questeq"><td> <code title="">questeq;</code> </td> <td> U+0225F </td> <td> <span class="glyph" title="">≟</span> </td> </tr>
     *       <tr id="entity-QUOT"><td> <code title="">QUOT;</code> </td> <td> U+00022 </td> <td> <span class="glyph" title="">"</span> </td> </tr>
     *       <tr class="impl" id="entity-QUOT-legacy"><td> <code title="">QUOT</code> </td> <td> U+00022 </td> <td> <span title="">"</span> </td> </tr>
     *       <tr id="entity-quot"><td> <code title="">quot;</code> </td> <td> U+00022 </td> <td> <span class="glyph" title="">"</span> </td> </tr>
     *       <tr class="impl" id="entity-quot-legacy"><td> <code title="">quot</code> </td> <td> U+00022 </td> <td> <span title="">"</span> </td> </tr>
     *       <tr id="entity-rAarr"><td> <code title="">rAarr;</code> </td> <td> U+021DB </td> <td> <span class="glyph" title="">⇛</span> </td> </tr>
     *       <tr id="entity-race"><td> <code title="">race;</code> </td> <td> U+0223D U+00331 </td> <td> <span class="glyph compound" title="">∽̱</span> </td> </tr>
     *       <tr id="entity-Racute"><td> <code title="">Racute;</code> </td> <td> U+00154 </td> <td> <span class="glyph" title="">Ŕ</span> </td> </tr>
     *       <tr id="entity-racute"><td> <code title="">racute;</code> </td> <td> U+00155 </td> <td> <span class="glyph" title="">ŕ</span> </td> </tr>
     *       <tr id="entity-radic"><td> <code title="">radic;</code> </td> <td> U+0221A </td> <td> <span class="glyph" title="">√</span> </td> </tr>
     *       <tr id="entity-raemptyv"><td> <code title="">raemptyv;</code> </td> <td> U+029B3 </td> <td> <span class="glyph" title="">⦳</span> </td> </tr>
     *       <tr id="entity-Rang"><td> <code title="">Rang;</code> </td> <td> U+027EB </td> <td> <span class="glyph" title="">⟫</span> </td> </tr>
     *       <tr id="entity-rang"><td> <code title="">rang;</code> </td> <td> U+027E9 </td> <td> <span class="glyph" title="">⟩</span> </td> </tr>
     *       <tr id="entity-rangd"><td> <code title="">rangd;</code> </td> <td> U+02992 </td> <td> <span class="glyph" title="">⦒</span> </td> </tr>
     *       <tr id="entity-range"><td> <code title="">range;</code> </td> <td> U+029A5 </td> <td> <span class="glyph" title="">⦥</span> </td> </tr>
     *       <tr id="entity-rangle"><td> <code title="">rangle;</code> </td> <td> U+027E9 </td> <td> <span class="glyph" title="">〉</span> </td> </tr>
     *       <tr id="entity-raquo"><td> <code title="">raquo;</code> </td> <td> U+000BB </td> <td> <span class="glyph" title="">»</span> </td> </tr>
     *       <tr class="impl" id="entity-raquo-legacy"><td> <code title="">raquo</code> </td> <td> U+000BB </td> <td> <span title="">»</span> </td> </tr>
     *       <tr id="entity-Rarr"><td> <code title="">Rarr;</code> </td> <td> U+021A0 </td> <td> <span class="glyph" title="">↠</span> </td> </tr>
     *       <tr id="entity-rArr"><td> <code title="">rArr;</code> </td> <td> U+021D2 </td> <td> <span class="glyph" title="">⇒</span> </td> </tr>
     *       <tr id="entity-rarr"><td> <code title="">rarr;</code> </td> <td> U+02192 </td> <td> <span class="glyph" title="">→</span> </td> </tr>
     *       <tr id="entity-rarrap"><td> <code title="">rarrap;</code> </td> <td> U+02975 </td> <td> <span class="glyph" title="">⥵</span> </td> </tr>
     *       <tr id="entity-rarrb"><td> <code title="">rarrb;</code> </td> <td> U+021E5 </td> <td> <span class="glyph" title="">⇥</span> </td> </tr>
     *       <tr id="entity-rarrbfs"><td> <code title="">rarrbfs;</code> </td> <td> U+02920 </td> <td> <span class="glyph" title="">⤠</span> </td> </tr>
     *       <tr id="entity-rarrc"><td> <code title="">rarrc;</code> </td> <td> U+02933 </td> <td> <span class="glyph" title="">⤳</span> </td> </tr>
     *       <tr id="entity-rarrfs"><td> <code title="">rarrfs;</code> </td> <td> U+0291E </td> <td> <span class="glyph" title="">⤞</span> </td> </tr>
     *       <tr id="entity-rarrhk"><td> <code title="">rarrhk;</code> </td> <td> U+021AA </td> <td> <span class="glyph" title="">↪</span> </td> </tr>
     *       <tr id="entity-rarrlp"><td> <code title="">rarrlp;</code> </td> <td> U+021AC </td> <td> <span class="glyph" title="">↬</span> </td> </tr>
     *       <tr id="entity-rarrpl"><td> <code title="">rarrpl;</code> </td> <td> U+02945 </td> <td> <span class="glyph" title="">⥅</span> </td> </tr>
     *       <tr id="entity-rarrsim"><td> <code title="">rarrsim;</code> </td> <td> U+02974 </td> <td> <span class="glyph" title="">⥴</span> </td> </tr>
     *       <tr id="entity-Rarrtl"><td> <code title="">Rarrtl;</code> </td> <td> U+02916 </td> <td> <span class="glyph" title="">⤖</span> </td> </tr>
     *       <tr id="entity-rarrtl"><td> <code title="">rarrtl;</code> </td> <td> U+021A3 </td> <td> <span class="glyph" title="">↣</span> </td> </tr>
     *       <tr id="entity-rarrw"><td> <code title="">rarrw;</code> </td> <td> U+0219D </td> <td> <span class="glyph" title="">↝</span> </td> </tr>
     *       <tr id="entity-rAtail"><td> <code title="">rAtail;</code> </td> <td> U+0291C </td> <td> <span class="glyph" title="">⤜</span> </td> </tr>
     *       <tr id="entity-ratail"><td> <code title="">ratail;</code> </td> <td> U+0291A </td> <td> <span class="glyph" title="">⤚</span> </td> </tr>
     *       <tr id="entity-ratio"><td> <code title="">ratio;</code> </td> <td> U+02236 </td> <td> <span class="glyph" title="">∶</span> </td> </tr>
     *       <tr id="entity-rationals"><td> <code title="">rationals;</code> </td> <td> U+0211A </td> <td> <span class="glyph" title="">ℚ</span> </td> </tr>
     *       <tr id="entity-RBarr"><td> <code title="">RBarr;</code> </td> <td> U+02910 </td> <td> <span class="glyph" title="">⤐</span> </td> </tr>
     *       <tr id="entity-rBarr"><td> <code title="">rBarr;</code> </td> <td> U+0290F </td> <td> <span class="glyph" title="">⤏</span> </td> </tr>
     *       <tr id="entity-rbarr"><td> <code title="">rbarr;</code> </td> <td> U+0290D </td> <td> <span class="glyph" title="">⤍</span> </td> </tr>
     *       <tr id="entity-rbbrk"><td> <code title="">rbbrk;</code> </td> <td> U+02773 </td> <td> <span class="glyph" title="">❳</span> </td> </tr>
     *       <tr id="entity-rbrace"><td> <code title="">rbrace;</code> </td> <td> U+0007D </td> <td> <span class="glyph" title="">}</span> </td> </tr>
     *       <tr id="entity-rbrack"><td> <code title="">rbrack;</code> </td> <td> U+0005D </td> <td> <span class="glyph" title="">]</span> </td> </tr>
     *       <tr id="entity-rbrke"><td> <code title="">rbrke;</code> </td> <td> U+0298C </td> <td> <span class="glyph" title="">⦌</span> </td> </tr>
     *       <tr id="entity-rbrksld"><td> <code title="">rbrksld;</code> </td> <td> U+0298E </td> <td> <span class="glyph" title="">⦎</span> </td> </tr>
     *       <tr id="entity-rbrkslu"><td> <code title="">rbrkslu;</code> </td> <td> U+02990 </td> <td> <span class="glyph" title="">⦐</span> </td> </tr>
     *       <tr id="entity-Rcaron"><td> <code title="">Rcaron;</code> </td> <td> U+00158 </td> <td> <span class="glyph" title="">Ř</span> </td> </tr>
     *       <tr id="entity-rcaron"><td> <code title="">rcaron;</code> </td> <td> U+00159 </td> <td> <span class="glyph" title="">ř</span> </td> </tr>
     *       <tr id="entity-Rcedil"><td> <code title="">Rcedil;</code> </td> <td> U+00156 </td> <td> <span class="glyph" title="">Ŗ</span> </td> </tr>
     *       <tr id="entity-rcedil"><td> <code title="">rcedil;</code> </td> <td> U+00157 </td> <td> <span class="glyph" title="">ŗ</span> </td> </tr>
     *       <tr id="entity-rceil"><td> <code title="">rceil;</code> </td> <td> U+02309 </td> <td> <span class="glyph" title="">⌉</span> </td> </tr>
     *       <tr id="entity-rcub"><td> <code title="">rcub;</code> </td> <td> U+0007D </td> <td> <span class="glyph" title="">}</span> </td> </tr>
     *       <tr id="entity-Rcy"><td> <code title="">Rcy;</code> </td> <td> U+00420 </td> <td> <span class="glyph" title="">Р</span> </td> </tr>
     *       <tr id="entity-rcy"><td> <code title="">rcy;</code> </td> <td> U+00440 </td> <td> <span class="glyph" title="">р</span> </td> </tr>
     *       <tr id="entity-rdca"><td> <code title="">rdca;</code> </td> <td> U+02937 </td> <td> <span class="glyph" title="">⤷</span> </td> </tr>
     *       <tr id="entity-rdldhar"><td> <code title="">rdldhar;</code> </td> <td> U+02969 </td> <td> <span class="glyph" title="">⥩</span> </td> </tr>
     *       <tr id="entity-rdquo"><td> <code title="">rdquo;</code> </td> <td> U+0201D </td> <td> <span class="glyph" title="">”</span> </td> </tr>
     *       <tr id="entity-rdquor"><td> <code title="">rdquor;</code> </td> <td> U+0201D </td> <td> <span class="glyph" title="">”</span> </td> </tr>
     *       <tr id="entity-rdsh"><td> <code title="">rdsh;</code> </td> <td> U+021B3 </td> <td> <span class="glyph" title="">↳</span> </td> </tr>
     *       <tr id="entity-Re"><td> <code title="">Re;</code> </td> <td> U+0211C </td> <td> <span class="glyph" title="">ℜ</span> </td> </tr>
     *       <tr id="entity-real"><td> <code title="">real;</code> </td> <td> U+0211C </td> <td> <span class="glyph" title="">ℜ</span> </td> </tr>
     *       <tr id="entity-realine"><td> <code title="">realine;</code> </td> <td> U+0211B </td> <td> <span class="glyph" title="">ℛ</span> </td> </tr>
     *       <tr id="entity-realpart"><td> <code title="">realpart;</code> </td> <td> U+0211C </td> <td> <span class="glyph" title="">ℜ</span> </td> </tr>
     *       <tr id="entity-reals"><td> <code title="">reals;</code> </td> <td> U+0211D </td> <td> <span class="glyph" title="">ℝ</span> </td> </tr>
     *       <tr id="entity-rect"><td> <code title="">rect;</code> </td> <td> U+025AD </td> <td> <span class="glyph" title="">▭</span> </td> </tr>
     *       <tr id="entity-REG"><td> <code title="">REG;</code> </td> <td> U+000AE </td> <td> <span class="glyph" title="">®</span> </td> </tr>
     *       <tr class="impl" id="entity-REG-legacy"><td> <code title="">REG</code> </td> <td> U+000AE </td> <td> <span title="">®</span> </td> </tr>
     *       <tr id="entity-reg"><td> <code title="">reg;</code> </td> <td> U+000AE </td> <td> <span class="glyph" title="">®</span> </td> </tr>
     *       <tr class="impl" id="entity-reg-legacy"><td> <code title="">reg</code> </td> <td> U+000AE </td> <td> <span title="">®</span> </td> </tr>
     *       <tr id="entity-ReverseElement"><td> <code title="">ReverseElement;</code> </td> <td> U+0220B </td> <td> <span class="glyph" title="">∋</span> </td> </tr>
     *       <tr id="entity-ReverseEquilibrium"><td> <code title="">ReverseEquilibrium;</code> </td> <td> U+021CB </td> <td> <span class="glyph" title="">⇋</span> </td> </tr>
     *       <tr id="entity-ReverseUpEquilibrium"><td> <code title="">ReverseUpEquilibrium;</code> </td> <td> U+0296F </td> <td> <span class="glyph" title="">⥯</span> </td> </tr>
     *       <tr id="entity-rfisht"><td> <code title="">rfisht;</code> </td> <td> U+0297D </td> <td> <span class="glyph" title="">⥽</span> </td> </tr>
     *       <tr id="entity-rfloor"><td> <code title="">rfloor;</code> </td> <td> U+0230B </td> <td> <span class="glyph" title="">⌋</span> </td> </tr>
     *       <tr id="entity-Rfr"><td> <code title="">Rfr;</code> </td> <td> U+0211C </td> <td> <span class="glyph" title="">ℜ</span> </td> </tr>
     *       <tr id="entity-rfr"><td> <code title="">rfr;</code> </td> <td> U+1D52F </td> <td> <span class="glyph" title="">𝔯</span> </td> </tr>
     *       <tr id="entity-rHar"><td> <code title="">rHar;</code> </td> <td> U+02964 </td> <td> <span class="glyph" title="">⥤</span> </td> </tr>
     *       <tr id="entity-rhard"><td> <code title="">rhard;</code> </td> <td> U+021C1 </td> <td> <span class="glyph" title="">⇁</span> </td> </tr>
     *       <tr id="entity-rharu"><td> <code title="">rharu;</code> </td> <td> U+021C0 </td> <td> <span class="glyph" title="">⇀</span> </td> </tr>
     *       <tr id="entity-rharul"><td> <code title="">rharul;</code> </td> <td> U+0296C </td> <td> <span class="glyph" title="">⥬</span> </td> </tr>
     *       <tr id="entity-Rho"><td> <code title="">Rho;</code> </td> <td> U+003A1 </td> <td> <span class="glyph" title="">Ρ</span> </td> </tr>
     *       <tr id="entity-rho"><td> <code title="">rho;</code> </td> <td> U+003C1 </td> <td> <span class="glyph" title="">ρ</span> </td> </tr>
     *       <tr id="entity-rhov"><td> <code title="">rhov;</code> </td> <td> U+003F1 </td> <td> <span class="glyph" title="">ϱ</span> </td> </tr>
     *       <tr id="entity-RightAngleBracket"><td> <code title="">RightAngleBracket;</code> </td> <td> U+027E9 </td> <td> <span class="glyph" title="">〉</span> </td> </tr>
     *       <tr id="entity-RightArrow"><td> <code title="">RightArrow;</code> </td> <td> U+02192 </td> <td> <span class="glyph" title="">→</span> </td> </tr>
     *       <tr id="entity-Rightarrow"><td> <code title="">Rightarrow;</code> </td> <td> U+021D2 </td> <td> <span class="glyph" title="">⇒</span> </td> </tr>
     *       <tr id="entity-rightarrow"><td> <code title="">rightarrow;</code> </td> <td> U+02192 </td> <td> <span class="glyph" title="">→</span> </td> </tr>
     *       <tr id="entity-RightArrowBar"><td> <code title="">RightArrowBar;</code> </td> <td> U+021E5 </td> <td> <span class="glyph" title="">⇥</span> </td> </tr>
     *       <tr id="entity-RightArrowLeftArrow"><td> <code title="">RightArrowLeftArrow;</code> </td> <td> U+021C4 </td> <td> <span class="glyph" title="">⇄</span> </td> </tr>
     *       <tr id="entity-rightarrowtail"><td> <code title="">rightarrowtail;</code> </td> <td> U+021A3 </td> <td> <span class="glyph" title="">↣</span> </td> </tr>
     *       <tr id="entity-RightCeiling"><td> <code title="">RightCeiling;</code> </td> <td> U+02309 </td> <td> <span class="glyph" title="">⌉</span> </td> </tr>
     *       <tr id="entity-RightDoubleBracket"><td> <code title="">RightDoubleBracket;</code> </td> <td> U+027E7 </td> <td> <span class="glyph" title="">⟧</span> </td> </tr>
     *       <tr id="entity-RightDownTeeVector"><td> <code title="">RightDownTeeVector;</code> </td> <td> U+0295D </td> <td> <span class="glyph" title="">⥝</span> </td> </tr>
     *       <tr id="entity-RightDownVector"><td> <code title="">RightDownVector;</code> </td> <td> U+021C2 </td> <td> <span class="glyph" title="">⇂</span> </td> </tr>
     *       <tr id="entity-RightDownVectorBar"><td> <code title="">RightDownVectorBar;</code> </td> <td> U+02955 </td> <td> <span class="glyph" title="">⥕</span> </td> </tr>
     *       <tr id="entity-RightFloor"><td> <code title="">RightFloor;</code> </td> <td> U+0230B </td> <td> <span class="glyph" title="">⌋</span> </td> </tr>
     *       <tr id="entity-rightharpoondown"><td> <code title="">rightharpoondown;</code> </td> <td> U+021C1 </td> <td> <span class="glyph" title="">⇁</span> </td> </tr>
     *       <tr id="entity-rightharpoonup"><td> <code title="">rightharpoonup;</code> </td> <td> U+021C0 </td> <td> <span class="glyph" title="">⇀</span> </td> </tr>
     *       <tr id="entity-rightleftarrows"><td> <code title="">rightleftarrows;</code> </td> <td> U+021C4 </td> <td> <span class="glyph" title="">⇄</span> </td> </tr>
     *       <tr id="entity-rightleftharpoons"><td> <code title="">rightleftharpoons;</code> </td> <td> U+021CC </td> <td> <span class="glyph" title="">⇌</span> </td> </tr>
     *       <tr id="entity-rightrightarrows"><td> <code title="">rightrightarrows;</code> </td> <td> U+021C9 </td> <td> <span class="glyph" title="">⇉</span> </td> </tr>
     *       <tr id="entity-rightsquigarrow"><td> <code title="">rightsquigarrow;</code> </td> <td> U+0219D </td> <td> <span class="glyph" title="">↝</span> </td> </tr>
     *       <tr id="entity-RightTee"><td> <code title="">RightTee;</code> </td> <td> U+022A2 </td> <td> <span class="glyph" title="">⊢</span> </td> </tr>
     *       <tr id="entity-RightTeeArrow"><td> <code title="">RightTeeArrow;</code> </td> <td> U+021A6 </td> <td> <span class="glyph" title="">↦</span> </td> </tr>
     *       <tr id="entity-RightTeeVector"><td> <code title="">RightTeeVector;</code> </td> <td> U+0295B </td> <td> <span class="glyph" title="">⥛</span> </td> </tr>
     *       <tr id="entity-rightthreetimes"><td> <code title="">rightthreetimes;</code> </td> <td> U+022CC </td> <td> <span class="glyph" title="">⋌</span> </td> </tr>
     *       <tr id="entity-RightTriangle"><td> <code title="">RightTriangle;</code> </td> <td> U+022B3 </td> <td> <span class="glyph" title="">⊳</span> </td> </tr>
     *       <tr id="entity-RightTriangleBar"><td> <code title="">RightTriangleBar;</code> </td> <td> U+029D0 </td> <td> <span class="glyph" title="">⧐</span> </td> </tr>
     *       <tr id="entity-RightTriangleEqual"><td> <code title="">RightTriangleEqual;</code> </td> <td> U+022B5 </td> <td> <span class="glyph" title="">⊵</span> </td> </tr>
     *       <tr id="entity-RightUpDownVector"><td> <code title="">RightUpDownVector;</code> </td> <td> U+0294F </td> <td> <span class="glyph" title="">⥏</span> </td> </tr>
     *       <tr id="entity-RightUpTeeVector"><td> <code title="">RightUpTeeVector;</code> </td> <td> U+0295C </td> <td> <span class="glyph" title="">⥜</span> </td> </tr>
     *       <tr id="entity-RightUpVector"><td> <code title="">RightUpVector;</code> </td> <td> U+021BE </td> <td> <span class="glyph" title="">↾</span> </td> </tr>
     *       <tr id="entity-RightUpVectorBar"><td> <code title="">RightUpVectorBar;</code> </td> <td> U+02954 </td> <td> <span class="glyph" title="">⥔</span> </td> </tr>
     *       <tr id="entity-RightVector"><td> <code title="">RightVector;</code> </td> <td> U+021C0 </td> <td> <span class="glyph" title="">⇀</span> </td> </tr>
     *       <tr id="entity-RightVectorBar"><td> <code title="">RightVectorBar;</code> </td> <td> U+02953 </td> <td> <span class="glyph" title="">⥓</span> </td> </tr>
     *       <tr id="entity-ring"><td> <code title="">ring;</code> </td> <td> U+002DA </td> <td> <span class="glyph" title="">˚</span> </td> </tr>
     *       <tr id="entity-risingdotseq"><td> <code title="">risingdotseq;</code> </td> <td> U+02253 </td> <td> <span class="glyph" title="">≓</span> </td> </tr>
     *       <tr id="entity-rlarr"><td> <code title="">rlarr;</code> </td> <td> U+021C4 </td> <td> <span class="glyph" title="">⇄</span> </td> </tr>
     *       <tr id="entity-rlhar"><td> <code title="">rlhar;</code> </td> <td> U+021CC </td> <td> <span class="glyph" title="">⇌</span> </td> </tr>
     *       <tr id="entity-rlm"><td> <code title="">rlm;</code> </td> <td> U+0200F </td> <td> <span class="glyph" title="">‏</span> </td> </tr>
     *       <tr id="entity-rmoust"><td> <code title="">rmoust;</code> </td> <td> U+023B1 </td> <td> <span class="glyph" title="">⎱</span> </td> </tr>
     *       <tr id="entity-rmoustache"><td> <code title="">rmoustache;</code> </td> <td> U+023B1 </td> <td> <span class="glyph" title="">⎱</span> </td> </tr>
     *       <tr id="entity-rnmid"><td> <code title="">rnmid;</code> </td> <td> U+02AEE </td> <td> <span class="glyph" title="">⫮</span> </td> </tr>
     *       <tr id="entity-roang"><td> <code title="">roang;</code> </td> <td> U+027ED </td> <td> <span class="glyph" title="">⟭</span> </td> </tr>
     *       <tr id="entity-roarr"><td> <code title="">roarr;</code> </td> <td> U+021FE </td> <td> <span class="glyph" title="">⇾</span> </td> </tr>
     *       <tr id="entity-robrk"><td> <code title="">robrk;</code> </td> <td> U+027E7 </td> <td> <span class="glyph" title="">⟧</span> </td> </tr>
     *       <tr id="entity-ropar"><td> <code title="">ropar;</code> </td> <td> U+02986 </td> <td> <span class="glyph" title="">⦆</span> </td> </tr>
     *       <tr id="entity-Ropf"><td> <code title="">Ropf;</code> </td> <td> U+0211D </td> <td> <span class="glyph" title="">ℝ</span> </td> </tr>
     *       <tr id="entity-ropf"><td> <code title="">ropf;</code> </td> <td> U+1D563 </td> <td> <span class="glyph" title="">𝕣</span> </td> </tr>
     *       <tr id="entity-roplus"><td> <code title="">roplus;</code> </td> <td> U+02A2E </td> <td> <span class="glyph" title="">⨮</span> </td> </tr>
     *       <tr id="entity-rotimes"><td> <code title="">rotimes;</code> </td> <td> U+02A35 </td> <td> <span class="glyph" title="">⨵</span> </td> </tr>
     *       <tr id="entity-RoundImplies"><td> <code title="">RoundImplies;</code> </td> <td> U+02970 </td> <td> <span class="glyph" title="">⥰</span> </td> </tr>
     *       <tr id="entity-rpar"><td> <code title="">rpar;</code> </td> <td> U+00029 </td> <td> <span class="glyph" title="">)</span> </td> </tr>
     *       <tr id="entity-rpargt"><td> <code title="">rpargt;</code> </td> <td> U+02994 </td> <td> <span class="glyph" title="">⦔</span> </td> </tr>
     *       <tr id="entity-rppolint"><td> <code title="">rppolint;</code> </td> <td> U+02A12 </td> <td> <span class="glyph" title="">⨒</span> </td> </tr>
     *       <tr id="entity-rrarr"><td> <code title="">rrarr;</code> </td> <td> U+021C9 </td> <td> <span class="glyph" title="">⇉</span> </td> </tr>
     *       <tr id="entity-Rrightarrow"><td> <code title="">Rrightarrow;</code> </td> <td> U+021DB </td> <td> <span class="glyph" title="">⇛</span> </td> </tr>
     *       <tr id="entity-rsaquo"><td> <code title="">rsaquo;</code> </td> <td> U+0203A </td> <td> <span class="glyph" title="">›</span> </td> </tr>
     *       <tr id="entity-Rscr"><td> <code title="">Rscr;</code> </td> <td> U+0211B </td> <td> <span class="glyph" title="">ℛ</span> </td> </tr>
     *       <tr id="entity-rscr"><td> <code title="">rscr;</code> </td> <td> U+1D4C7 </td> <td> <span class="glyph" title="">𝓇</span> </td> </tr>
     *       <tr id="entity-Rsh"><td> <code title="">Rsh;</code> </td> <td> U+021B1 </td> <td> <span class="glyph" title="">↱</span> </td> </tr>
     *       <tr id="entity-rsh"><td> <code title="">rsh;</code> </td> <td> U+021B1 </td> <td> <span class="glyph" title="">↱</span> </td> </tr>
     *       <tr id="entity-rsqb"><td> <code title="">rsqb;</code> </td> <td> U+0005D </td> <td> <span class="glyph" title="">]</span> </td> </tr>
     *       <tr id="entity-rsquo"><td> <code title="">rsquo;</code> </td> <td> U+02019 </td> <td> <span class="glyph" title="">’</span> </td> </tr>
     *       <tr id="entity-rsquor"><td> <code title="">rsquor;</code> </td> <td> U+02019 </td> <td> <span class="glyph" title="">’</span> </td> </tr>
     *       <tr id="entity-rthree"><td> <code title="">rthree;</code> </td> <td> U+022CC </td> <td> <span class="glyph" title="">⋌</span> </td> </tr>
     *       <tr id="entity-rtimes"><td> <code title="">rtimes;</code> </td> <td> U+022CA </td> <td> <span class="glyph" title="">⋊</span> </td> </tr>
     *       <tr id="entity-rtri"><td> <code title="">rtri;</code> </td> <td> U+025B9 </td> <td> <span class="glyph" title="">▹</span> </td> </tr>
     *       <tr id="entity-rtrie"><td> <code title="">rtrie;</code> </td> <td> U+022B5 </td> <td> <span class="glyph" title="">⊵</span> </td> </tr>
     *       <tr id="entity-rtrif"><td> <code title="">rtrif;</code> </td> <td> U+025B8 </td> <td> <span class="glyph" title="">▸</span> </td> </tr>
     *       <tr id="entity-rtriltri"><td> <code title="">rtriltri;</code> </td> <td> U+029CE </td> <td> <span class="glyph" title="">⧎</span> </td> </tr>
     *       <tr id="entity-RuleDelayed"><td> <code title="">RuleDelayed;</code> </td> <td> U+029F4 </td> <td> <span class="glyph" title="">⧴</span> </td> </tr>
     *       <tr id="entity-ruluhar"><td> <code title="">ruluhar;</code> </td> <td> U+02968 </td> <td> <span class="glyph" title="">⥨</span> </td> </tr>
     *       <tr id="entity-rx"><td> <code title="">rx;</code> </td> <td> U+0211E </td> <td> <span class="glyph" title="">℞</span> </td> </tr>
     *       <tr id="entity-Sacute"><td> <code title="">Sacute;</code> </td> <td> U+0015A </td> <td> <span class="glyph" title="">Ś</span> </td> </tr>
     *       <tr id="entity-sacute"><td> <code title="">sacute;</code> </td> <td> U+0015B </td> <td> <span class="glyph" title="">ś</span> </td> </tr>
     *       <tr id="entity-sbquo"><td> <code title="">sbquo;</code> </td> <td> U+0201A </td> <td> <span class="glyph" title="">‚</span> </td> </tr>
     *       <tr id="entity-Sc"><td> <code title="">Sc;</code> </td> <td> U+02ABC </td> <td> <span class="glyph" title="">⪼</span> </td> </tr>
     *       <tr id="entity-sc"><td> <code title="">sc;</code> </td> <td> U+0227B </td> <td> <span class="glyph" title="">≻</span> </td> </tr>
     *       <tr id="entity-scap"><td> <code title="">scap;</code> </td> <td> U+02AB8 </td> <td> <span class="glyph" title="">⪸</span> </td> </tr>
     *       <tr id="entity-Scaron"><td> <code title="">Scaron;</code> </td> <td> U+00160 </td> <td> <span class="glyph" title="">Š</span> </td> </tr>
     *       <tr id="entity-scaron"><td> <code title="">scaron;</code> </td> <td> U+00161 </td> <td> <span class="glyph" title="">š</span> </td> </tr>
     *       <tr id="entity-sccue"><td> <code title="">sccue;</code> </td> <td> U+0227D </td> <td> <span class="glyph" title="">≽</span> </td> </tr>
     *       <tr id="entity-scE"><td> <code title="">scE;</code> </td> <td> U+02AB4 </td> <td> <span class="glyph" title="">⪴</span> </td> </tr>
     *       <tr id="entity-sce"><td> <code title="">sce;</code> </td> <td> U+02AB0 </td> <td> <span class="glyph" title="">⪰</span> </td> </tr>
     *       <tr id="entity-Scedil"><td> <code title="">Scedil;</code> </td> <td> U+0015E </td> <td> <span class="glyph" title="">Ş</span> </td> </tr>
     *       <tr id="entity-scedil"><td> <code title="">scedil;</code> </td> <td> U+0015F </td> <td> <span class="glyph" title="">ş</span> </td> </tr>
     *       <tr id="entity-Scirc"><td> <code title="">Scirc;</code> </td> <td> U+0015C </td> <td> <span class="glyph" title="">Ŝ</span> </td> </tr>
     *       <tr id="entity-scirc"><td> <code title="">scirc;</code> </td> <td> U+0015D </td> <td> <span class="glyph" title="">ŝ</span> </td> </tr>
     *       <tr id="entity-scnap"><td> <code title="">scnap;</code> </td> <td> U+02ABA </td> <td> <span class="glyph" title="">⪺</span> </td> </tr>
     *       <tr id="entity-scnE"><td> <code title="">scnE;</code> </td> <td> U+02AB6 </td> <td> <span class="glyph" title="">⪶</span> </td> </tr>
     *       <tr id="entity-scnsim"><td> <code title="">scnsim;</code> </td> <td> U+022E9 </td> <td> <span class="glyph" title="">⋩</span> </td> </tr>
     *       <tr id="entity-scpolint"><td> <code title="">scpolint;</code> </td> <td> U+02A13 </td> <td> <span class="glyph" title="">⨓</span> </td> </tr>
     *       <tr id="entity-scsim"><td> <code title="">scsim;</code> </td> <td> U+0227F </td> <td> <span class="glyph" title="">≿</span> </td> </tr>
     *       <tr id="entity-Scy"><td> <code title="">Scy;</code> </td> <td> U+00421 </td> <td> <span class="glyph" title="">С</span> </td> </tr>
     *       <tr id="entity-scy"><td> <code title="">scy;</code> </td> <td> U+00441 </td> <td> <span class="glyph" title="">с</span> </td> </tr>
     *       <tr id="entity-sdot"><td> <code title="">sdot;</code> </td> <td> U+022C5 </td> <td> <span class="glyph" title="">⋅</span> </td> </tr>
     *       <tr id="entity-sdotb"><td> <code title="">sdotb;</code> </td> <td> U+022A1 </td> <td> <span class="glyph" title="">⊡</span> </td> </tr>
     *       <tr id="entity-sdote"><td> <code title="">sdote;</code> </td> <td> U+02A66 </td> <td> <span class="glyph" title="">⩦</span> </td> </tr>
     *       <tr id="entity-searhk"><td> <code title="">searhk;</code> </td> <td> U+02925 </td> <td> <span class="glyph" title="">⤥</span> </td> </tr>
     *       <tr id="entity-seArr"><td> <code title="">seArr;</code> </td> <td> U+021D8 </td> <td> <span class="glyph" title="">⇘</span> </td> </tr>
     *       <tr id="entity-searr"><td> <code title="">searr;</code> </td> <td> U+02198 </td> <td> <span class="glyph" title="">↘</span> </td> </tr>
     *       <tr id="entity-searrow"><td> <code title="">searrow;</code> </td> <td> U+02198 </td> <td> <span class="glyph" title="">↘</span> </td> </tr>
     *       <tr id="entity-sect"><td> <code title="">sect;</code> </td> <td> U+000A7 </td> <td> <span class="glyph" title="">§</span> </td> </tr>
     *       <tr class="impl" id="entity-sect-legacy"><td> <code title="">sect</code> </td> <td> U+000A7 </td> <td> <span title="">§</span> </td> </tr>
     *       <tr id="entity-semi"><td> <code title="">semi;</code> </td> <td> U+0003B </td> <td> <span class="glyph" title="">;</span> </td> </tr>
     *       <tr id="entity-seswar"><td> <code title="">seswar;</code> </td> <td> U+02929 </td> <td> <span class="glyph" title="">⤩</span> </td> </tr>
     *       <tr id="entity-setminus"><td> <code title="">setminus;</code> </td> <td> U+02216 </td> <td> <span class="glyph" title="">∖</span> </td> </tr>
     *       <tr id="entity-setmn"><td> <code title="">setmn;</code> </td> <td> U+02216 </td> <td> <span class="glyph" title="">∖</span> </td> </tr>
     *       <tr id="entity-sext"><td> <code title="">sext;</code> </td> <td> U+02736 </td> <td> <span class="glyph" title="">✶</span> </td> </tr>
     *       <tr id="entity-Sfr"><td> <code title="">Sfr;</code> </td> <td> U+1D516 </td> <td> <span class="glyph" title="">𝔖</span> </td> </tr>
     *       <tr id="entity-sfr"><td> <code title="">sfr;</code> </td> <td> U+1D530 </td> <td> <span class="glyph" title="">𝔰</span> </td> </tr>
     *       <tr id="entity-sfrown"><td> <code title="">sfrown;</code> </td> <td> U+02322 </td> <td> <span class="glyph" title="">⌢</span> </td> </tr>
     *       <tr id="entity-sharp"><td> <code title="">sharp;</code> </td> <td> U+0266F </td> <td> <span class="glyph" title="">♯</span> </td> </tr>
     *       <tr id="entity-SHCHcy"><td> <code title="">SHCHcy;</code> </td> <td> U+00429 </td> <td> <span class="glyph" title="">Щ</span> </td> </tr>
     *       <tr id="entity-shchcy"><td> <code title="">shchcy;</code> </td> <td> U+00449 </td> <td> <span class="glyph" title="">щ</span> </td> </tr>
     *       <tr id="entity-SHcy"><td> <code title="">SHcy;</code> </td> <td> U+00428 </td> <td> <span class="glyph" title="">Ш</span> </td> </tr>
     *       <tr id="entity-shcy"><td> <code title="">shcy;</code> </td> <td> U+00448 </td> <td> <span class="glyph" title="">ш</span> </td> </tr>
     *       <tr id="entity-ShortDownArrow"><td> <code title="">ShortDownArrow;</code> </td> <td> U+02193 </td> <td> <span class="glyph" title="">↓</span> </td> </tr>
     *       <tr id="entity-ShortLeftArrow"><td> <code title="">ShortLeftArrow;</code> </td> <td> U+02190 </td> <td> <span class="glyph" title="">←</span> </td> </tr>
     *       <tr id="entity-shortmid"><td> <code title="">shortmid;</code> </td> <td> U+02223 </td> <td> <span class="glyph" title="">∣</span> </td> </tr>
     *       <tr id="entity-shortparallel"><td> <code title="">shortparallel;</code> </td> <td> U+02225 </td> <td> <span class="glyph" title="">∥</span> </td> </tr>
     *       <tr id="entity-ShortRightArrow"><td> <code title="">ShortRightArrow;</code> </td> <td> U+02192 </td> <td> <span class="glyph" title="">→</span> </td> </tr>
     *       <tr id="entity-ShortUpArrow"><td> <code title="">ShortUpArrow;</code> </td> <td> U+02191 </td> <td> <span class="glyph" title="">↑</span> </td> </tr>
     *       <tr id="entity-shy"><td> <code title="">shy;</code> </td> <td> U+000AD </td> <td> <span class="glyph" title="">­</span> </td> </tr>
     *       <tr class="impl" id="entity-shy-legacy"><td> <code title="">shy</code> </td> <td> U+000AD </td> <td> <span title="">­</span> </td> </tr>
     *       <tr id="entity-Sigma"><td> <code title="">Sigma;</code> </td> <td> U+003A3 </td> <td> <span class="glyph" title="">Σ</span> </td> </tr>
     *       <tr id="entity-sigma"><td> <code title="">sigma;</code> </td> <td> U+003C3 </td> <td> <span class="glyph" title="">σ</span> </td> </tr>
     *       <tr id="entity-sigmaf"><td> <code title="">sigmaf;</code> </td> <td> U+003C2 </td> <td> <span class="glyph" title="">ς</span> </td> </tr>
     *       <tr id="entity-sigmav"><td> <code title="">sigmav;</code> </td> <td> U+003C2 </td> <td> <span class="glyph" title="">ς</span> </td> </tr>
     *       <tr id="entity-sim"><td> <code title="">sim;</code> </td> <td> U+0223C </td> <td> <span class="glyph" title="">∼</span> </td> </tr>
     *       <tr id="entity-simdot"><td> <code title="">simdot;</code> </td> <td> U+02A6A </td> <td> <span class="glyph" title="">⩪</span> </td> </tr>
     *       <tr id="entity-sime"><td> <code title="">sime;</code> </td> <td> U+02243 </td> <td> <span class="glyph" title="">≃</span> </td> </tr>
     *       <tr id="entity-simeq"><td> <code title="">simeq;</code> </td> <td> U+02243 </td> <td> <span class="glyph" title="">≃</span> </td> </tr>
     *       <tr id="entity-simg"><td> <code title="">simg;</code> </td> <td> U+02A9E </td> <td> <span class="glyph" title="">⪞</span> </td> </tr>
     *       <tr id="entity-simgE"><td> <code title="">simgE;</code> </td> <td> U+02AA0 </td> <td> <span class="glyph" title="">⪠</span> </td> </tr>
     *       <tr id="entity-siml"><td> <code title="">siml;</code> </td> <td> U+02A9D </td> <td> <span class="glyph" title="">⪝</span> </td> </tr>
     *       <tr id="entity-simlE"><td> <code title="">simlE;</code> </td> <td> U+02A9F </td> <td> <span class="glyph" title="">⪟</span> </td> </tr>
     *       <tr id="entity-simne"><td> <code title="">simne;</code> </td> <td> U+02246 </td> <td> <span class="glyph" title="">≆</span> </td> </tr>
     *       <tr id="entity-simplus"><td> <code title="">simplus;</code> </td> <td> U+02A24 </td> <td> <span class="glyph" title="">⨤</span> </td> </tr>
     *       <tr id="entity-simrarr"><td> <code title="">simrarr;</code> </td> <td> U+02972 </td> <td> <span class="glyph" title="">⥲</span> </td> </tr>
     *       <tr id="entity-slarr"><td> <code title="">slarr;</code> </td> <td> U+02190 </td> <td> <span class="glyph" title="">←</span> </td> </tr>
     *       <tr id="entity-SmallCircle"><td> <code title="">SmallCircle;</code> </td> <td> U+02218 </td> <td> <span class="glyph" title="">∘</span> </td> </tr>
     *       <tr id="entity-smallsetminus"><td> <code title="">smallsetminus;</code> </td> <td> U+02216 </td> <td> <span class="glyph" title="">∖</span> </td> </tr>
     *       <tr id="entity-smashp"><td> <code title="">smashp;</code> </td> <td> U+02A33 </td> <td> <span class="glyph" title="">⨳</span> </td> </tr>
     *       <tr id="entity-smeparsl"><td> <code title="">smeparsl;</code> </td> <td> U+029E4 </td> <td> <span class="glyph" title="">⧤</span> </td> </tr>
     *       <tr id="entity-smid"><td> <code title="">smid;</code> </td> <td> U+02223 </td> <td> <span class="glyph" title="">∣</span> </td> </tr>
     *       <tr id="entity-smile"><td> <code title="">smile;</code> </td> <td> U+02323 </td> <td> <span class="glyph" title="">⌣</span> </td> </tr>
     *       <tr id="entity-smt"><td> <code title="">smt;</code> </td> <td> U+02AAA </td> <td> <span class="glyph" title="">⪪</span> </td> </tr>
     *       <tr id="entity-smte"><td> <code title="">smte;</code> </td> <td> U+02AAC </td> <td> <span class="glyph" title="">⪬</span> </td> </tr>
     *       <tr id="entity-smtes"><td> <code title="">smtes;</code> </td> <td> U+02AAC U+0FE00 </td> <td> <span class="glyph compound" title="">⪬︀</span> </td> </tr>
     *       <tr id="entity-SOFTcy"><td> <code title="">SOFTcy;</code> </td> <td> U+0042C </td> <td> <span class="glyph" title="">Ь</span> </td> </tr>
     *       <tr id="entity-softcy"><td> <code title="">softcy;</code> </td> <td> U+0044C </td> <td> <span class="glyph" title="">ь</span> </td> </tr>
     *       <tr id="entity-sol"><td> <code title="">sol;</code> </td> <td> U+0002F </td> <td> <span class="glyph" title="">/</span> </td> </tr>
     *       <tr id="entity-solb"><td> <code title="">solb;</code> </td> <td> U+029C4 </td> <td> <span class="glyph" title="">⧄</span> </td> </tr>
     *       <tr id="entity-solbar"><td> <code title="">solbar;</code> </td> <td> U+0233F </td> <td> <span class="glyph" title="">⌿</span> </td> </tr>
     *       <tr id="entity-Sopf"><td> <code title="">Sopf;</code> </td> <td> U+1D54A </td> <td> <span class="glyph" title="">𝕊</span> </td> </tr>
     *       <tr id="entity-sopf"><td> <code title="">sopf;</code> </td> <td> U+1D564 </td> <td> <span class="glyph" title="">𝕤</span> </td> </tr>
     *       <tr id="entity-spades"><td> <code title="">spades;</code> </td> <td> U+02660 </td> <td> <span class="glyph" title="">♠</span> </td> </tr>
     *       <tr id="entity-spadesuit"><td> <code title="">spadesuit;</code> </td> <td> U+02660 </td> <td> <span class="glyph" title="">♠</span> </td> </tr>
     *       <tr id="entity-spar"><td> <code title="">spar;</code> </td> <td> U+02225 </td> <td> <span class="glyph" title="">∥</span> </td> </tr>
     *       <tr id="entity-sqcap"><td> <code title="">sqcap;</code> </td> <td> U+02293 </td> <td> <span class="glyph" title="">⊓</span> </td> </tr>
     *       <tr id="entity-sqcaps"><td> <code title="">sqcaps;</code> </td> <td> U+02293 U+0FE00 </td> <td> <span class="glyph compound" title="">⊓︀</span> </td> </tr>
     *       <tr id="entity-sqcup"><td> <code title="">sqcup;</code> </td> <td> U+02294 </td> <td> <span class="glyph" title="">⊔</span> </td> </tr>
     *       <tr id="entity-sqcups"><td> <code title="">sqcups;</code> </td> <td> U+02294 U+0FE00 </td> <td> <span class="glyph compound" title="">⊔︀</span> </td> </tr>
     *       <tr id="entity-Sqrt"><td> <code title="">Sqrt;</code> </td> <td> U+0221A </td> <td> <span class="glyph" title="">√</span> </td> </tr>
     *       <tr id="entity-sqsub"><td> <code title="">sqsub;</code> </td> <td> U+0228F </td> <td> <span class="glyph" title="">⊏</span> </td> </tr>
     *       <tr id="entity-sqsube"><td> <code title="">sqsube;</code> </td> <td> U+02291 </td> <td> <span class="glyph" title="">⊑</span> </td> </tr>
     *       <tr id="entity-sqsubset"><td> <code title="">sqsubset;</code> </td> <td> U+0228F </td> <td> <span class="glyph" title="">⊏</span> </td> </tr>
     *       <tr id="entity-sqsubseteq"><td> <code title="">sqsubseteq;</code> </td> <td> U+02291 </td> <td> <span class="glyph" title="">⊑</span> </td> </tr>
     *       <tr id="entity-sqsup"><td> <code title="">sqsup;</code> </td> <td> U+02290 </td> <td> <span class="glyph" title="">⊐</span> </td> </tr>
     *       <tr id="entity-sqsupe"><td> <code title="">sqsupe;</code> </td> <td> U+02292 </td> <td> <span class="glyph" title="">⊒</span> </td> </tr>
     *       <tr id="entity-sqsupset"><td> <code title="">sqsupset;</code> </td> <td> U+02290 </td> <td> <span class="glyph" title="">⊐</span> </td> </tr>
     *       <tr id="entity-sqsupseteq"><td> <code title="">sqsupseteq;</code> </td> <td> U+02292 </td> <td> <span class="glyph" title="">⊒</span> </td> </tr>
     *       <tr id="entity-squ"><td> <code title="">squ;</code> </td> <td> U+025A1 </td> <td> <span class="glyph" title="">□</span> </td> </tr>
     *       <tr id="entity-Square"><td> <code title="">Square;</code> </td> <td> U+025A1 </td> <td> <span class="glyph" title="">□</span> </td> </tr>
     *       <tr id="entity-square"><td> <code title="">square;</code> </td> <td> U+025A1 </td> <td> <span class="glyph" title="">□</span> </td> </tr>
     *       <tr id="entity-SquareIntersection"><td> <code title="">SquareIntersection;</code> </td> <td> U+02293 </td> <td> <span class="glyph" title="">⊓</span> </td> </tr>
     *       <tr id="entity-SquareSubset"><td> <code title="">SquareSubset;</code> </td> <td> U+0228F </td> <td> <span class="glyph" title="">⊏</span> </td> </tr>
     *       <tr id="entity-SquareSubsetEqual"><td> <code title="">SquareSubsetEqual;</code> </td> <td> U+02291 </td> <td> <span class="glyph" title="">⊑</span> </td> </tr>
     *       <tr id="entity-SquareSuperset"><td> <code title="">SquareSuperset;</code> </td> <td> U+02290 </td> <td> <span class="glyph" title="">⊐</span> </td> </tr>
     *       <tr id="entity-SquareSupersetEqual"><td> <code title="">SquareSupersetEqual;</code> </td> <td> U+02292 </td> <td> <span class="glyph" title="">⊒</span> </td> </tr>
     *       <tr id="entity-SquareUnion"><td> <code title="">SquareUnion;</code> </td> <td> U+02294 </td> <td> <span class="glyph" title="">⊔</span> </td> </tr>
     *       <tr id="entity-squarf"><td> <code title="">squarf;</code> </td> <td> U+025AA </td> <td> <span class="glyph" title="">▪</span> </td> </tr>
     *       <tr id="entity-squf"><td> <code title="">squf;</code> </td> <td> U+025AA </td> <td> <span class="glyph" title="">▪</span> </td> </tr>
     *       <tr id="entity-srarr"><td> <code title="">srarr;</code> </td> <td> U+02192 </td> <td> <span class="glyph" title="">→</span> </td> </tr>
     *       <tr id="entity-Sscr"><td> <code title="">Sscr;</code> </td> <td> U+1D4AE </td> <td> <span class="glyph" title="">𝒮</span> </td> </tr>
     *       <tr id="entity-sscr"><td> <code title="">sscr;</code> </td> <td> U+1D4C8 </td> <td> <span class="glyph" title="">𝓈</span> </td> </tr>
     *       <tr id="entity-ssetmn"><td> <code title="">ssetmn;</code> </td> <td> U+02216 </td> <td> <span class="glyph" title="">∖</span> </td> </tr>
     *       <tr id="entity-ssmile"><td> <code title="">ssmile;</code> </td> <td> U+02323 </td> <td> <span class="glyph" title="">⌣</span> </td> </tr>
     *       <tr id="entity-sstarf"><td> <code title="">sstarf;</code> </td> <td> U+022C6 </td> <td> <span class="glyph" title="">⋆</span> </td> </tr>
     *       <tr id="entity-Star"><td> <code title="">Star;</code> </td> <td> U+022C6 </td> <td> <span class="glyph" title="">⋆</span> </td> </tr>
     *       <tr id="entity-star"><td> <code title="">star;</code> </td> <td> U+02606 </td> <td> <span class="glyph" title="">☆</span> </td> </tr>
     *       <tr id="entity-starf"><td> <code title="">starf;</code> </td> <td> U+02605 </td> <td> <span class="glyph" title="">★</span> </td> </tr>
     *       <tr id="entity-straightepsilon"><td> <code title="">straightepsilon;</code> </td> <td> U+003F5 </td> <td> <span class="glyph" title="">ϵ</span> </td> </tr>
     *       <tr id="entity-straightphi"><td> <code title="">straightphi;</code> </td> <td> U+003D5 </td> <td> <span class="glyph" title="">ϕ</span> </td> </tr>
     *       <tr id="entity-strns"><td> <code title="">strns;</code> </td> <td> U+000AF </td> <td> <span class="glyph" title="">¯</span> </td> </tr>
     *       <tr id="entity-Sub"><td> <code title="">Sub;</code> </td> <td> U+022D0 </td> <td> <span class="glyph" title="">⋐</span> </td> </tr>
     *       <tr id="entity-sub"><td> <code title="">sub;</code> </td> <td> U+02282 </td> <td> <span class="glyph" title="">⊂</span> </td> </tr>
     *       <tr id="entity-subdot"><td> <code title="">subdot;</code> </td> <td> U+02ABD </td> <td> <span class="glyph" title="">⪽</span> </td> </tr>
     *       <tr id="entity-subE"><td> <code title="">subE;</code> </td> <td> U+02AC5 </td> <td> <span class="glyph" title="">⫅</span> </td> </tr>
     *       <tr id="entity-sube"><td> <code title="">sube;</code> </td> <td> U+02286 </td> <td> <span class="glyph" title="">⊆</span> </td> </tr>
     *       <tr id="entity-subedot"><td> <code title="">subedot;</code> </td> <td> U+02AC3 </td> <td> <span class="glyph" title="">⫃</span> </td> </tr>
     *       <tr id="entity-submult"><td> <code title="">submult;</code> </td> <td> U+02AC1 </td> <td> <span class="glyph" title="">⫁</span> </td> </tr>
     *       <tr id="entity-subnE"><td> <code title="">subnE;</code> </td> <td> U+02ACB </td> <td> <span class="glyph" title="">⫋</span> </td> </tr>
     *       <tr id="entity-subne"><td> <code title="">subne;</code> </td> <td> U+0228A </td> <td> <span class="glyph" title="">⊊</span> </td> </tr>
     *       <tr id="entity-subplus"><td> <code title="">subplus;</code> </td> <td> U+02ABF </td> <td> <span class="glyph" title="">⪿</span> </td> </tr>
     *       <tr id="entity-subrarr"><td> <code title="">subrarr;</code> </td> <td> U+02979 </td> <td> <span class="glyph" title="">⥹</span> </td> </tr>
     *       <tr id="entity-Subset"><td> <code title="">Subset;</code> </td> <td> U+022D0 </td> <td> <span class="glyph" title="">⋐</span> </td> </tr>
     *       <tr id="entity-subset"><td> <code title="">subset;</code> </td> <td> U+02282 </td> <td> <span class="glyph" title="">⊂</span> </td> </tr>
     *       <tr id="entity-subseteq"><td> <code title="">subseteq;</code> </td> <td> U+02286 </td> <td> <span class="glyph" title="">⊆</span> </td> </tr>
     *       <tr id="entity-subseteqq"><td> <code title="">subseteqq;</code> </td> <td> U+02AC5 </td> <td> <span class="glyph" title="">⫅</span> </td> </tr>
     *       <tr id="entity-SubsetEqual"><td> <code title="">SubsetEqual;</code> </td> <td> U+02286 </td> <td> <span class="glyph" title="">⊆</span> </td> </tr>
     *       <tr id="entity-subsetneq"><td> <code title="">subsetneq;</code> </td> <td> U+0228A </td> <td> <span class="glyph" title="">⊊</span> </td> </tr>
     *       <tr id="entity-subsetneqq"><td> <code title="">subsetneqq;</code> </td> <td> U+02ACB </td> <td> <span class="glyph" title="">⫋</span> </td> </tr>
     *       <tr id="entity-subsim"><td> <code title="">subsim;</code> </td> <td> U+02AC7 </td> <td> <span class="glyph" title="">⫇</span> </td> </tr>
     *       <tr id="entity-subsub"><td> <code title="">subsub;</code> </td> <td> U+02AD5 </td> <td> <span class="glyph" title="">⫕</span> </td> </tr>
     *       <tr id="entity-subsup"><td> <code title="">subsup;</code> </td> <td> U+02AD3 </td> <td> <span class="glyph" title="">⫓</span> </td> </tr>
     *       <tr id="entity-succ"><td> <code title="">succ;</code> </td> <td> U+0227B </td> <td> <span class="glyph" title="">≻</span> </td> </tr>
     *       <tr id="entity-succapprox"><td> <code title="">succapprox;</code> </td> <td> U+02AB8 </td> <td> <span class="glyph" title="">⪸</span> </td> </tr>
     *       <tr id="entity-succcurlyeq"><td> <code title="">succcurlyeq;</code> </td> <td> U+0227D </td> <td> <span class="glyph" title="">≽</span> </td> </tr>
     *       <tr id="entity-Succeeds"><td> <code title="">Succeeds;</code> </td> <td> U+0227B </td> <td> <span class="glyph" title="">≻</span> </td> </tr>
     *       <tr id="entity-SucceedsEqual"><td> <code title="">SucceedsEqual;</code> </td> <td> U+02AB0 </td> <td> <span class="glyph" title="">⪰</span> </td> </tr>
     *       <tr id="entity-SucceedsSlantEqual"><td> <code title="">SucceedsSlantEqual;</code> </td> <td> U+0227D </td> <td> <span class="glyph" title="">≽</span> </td> </tr>
     *       <tr id="entity-SucceedsTilde"><td> <code title="">SucceedsTilde;</code> </td> <td> U+0227F </td> <td> <span class="glyph" title="">≿</span> </td> </tr>
     *       <tr id="entity-succeq"><td> <code title="">succeq;</code> </td> <td> U+02AB0 </td> <td> <span class="glyph" title="">⪰</span> </td> </tr>
     *       <tr id="entity-succnapprox"><td> <code title="">succnapprox;</code> </td> <td> U+02ABA </td> <td> <span class="glyph" title="">⪺</span> </td> </tr>
     *       <tr id="entity-succneqq"><td> <code title="">succneqq;</code> </td> <td> U+02AB6 </td> <td> <span class="glyph" title="">⪶</span> </td> </tr>
     *       <tr id="entity-succnsim"><td> <code title="">succnsim;</code> </td> <td> U+022E9 </td> <td> <span class="glyph" title="">⋩</span> </td> </tr>
     *       <tr id="entity-succsim"><td> <code title="">succsim;</code> </td> <td> U+0227F </td> <td> <span class="glyph" title="">≿</span> </td> </tr>
     *       <tr id="entity-SuchThat"><td> <code title="">SuchThat;</code> </td> <td> U+0220B </td> <td> <span class="glyph" title="">∋</span> </td> </tr>
     *       <tr id="entity-Sum"><td> <code title="">Sum;</code> </td> <td> U+02211 </td> <td> <span class="glyph" title="">∑</span> </td> </tr>
     *       <tr id="entity-sum"><td> <code title="">sum;</code> </td> <td> U+02211 </td> <td> <span class="glyph" title="">∑</span> </td> </tr>
     *       <tr id="entity-sung"><td> <code title="">sung;</code> </td> <td> U+0266A </td> <td> <span class="glyph" title="">♪</span> </td> </tr>
     *       <tr id="entity-Sup"><td> <code title="">Sup;</code> </td> <td> U+022D1 </td> <td> <span class="glyph" title="">⋑</span> </td> </tr>
     *       <tr id="entity-sup"><td> <code title="">sup;</code> </td> <td> U+02283 </td> <td> <span class="glyph" title="">⊃</span> </td> </tr>
     *       <tr id="entity-sup1"><td> <code title="">sup1;</code> </td> <td> U+000B9 </td> <td> <span class="glyph" title="">¹</span> </td> </tr>
     *       <tr class="impl" id="entity-sup1-legacy"><td> <code title="">sup1</code> </td> <td> U+000B9 </td> <td> <span title="">¹</span> </td> </tr>
     *       <tr id="entity-sup2"><td> <code title="">sup2;</code> </td> <td> U+000B2 </td> <td> <span class="glyph" title="">²</span> </td> </tr>
     *       <tr class="impl" id="entity-sup2-legacy"><td> <code title="">sup2</code> </td> <td> U+000B2 </td> <td> <span title="">²</span> </td> </tr>
     *       <tr id="entity-sup3"><td> <code title="">sup3;</code> </td> <td> U+000B3 </td> <td> <span class="glyph" title="">³</span> </td> </tr>
     *       <tr class="impl" id="entity-sup3-legacy"><td> <code title="">sup3</code> </td> <td> U+000B3 </td> <td> <span title="">³</span> </td> </tr>
     *       <tr id="entity-supdot"><td> <code title="">supdot;</code> </td> <td> U+02ABE </td> <td> <span class="glyph" title="">⪾</span> </td> </tr>
     *       <tr id="entity-supdsub"><td> <code title="">supdsub;</code> </td> <td> U+02AD8 </td> <td> <span class="glyph" title="">⫘</span> </td> </tr>
     *       <tr id="entity-supE"><td> <code title="">supE;</code> </td> <td> U+02AC6 </td> <td> <span class="glyph" title="">⫆</span> </td> </tr>
     *       <tr id="entity-supe"><td> <code title="">supe;</code> </td> <td> U+02287 </td> <td> <span class="glyph" title="">⊇</span> </td> </tr>
     *       <tr id="entity-supedot"><td> <code title="">supedot;</code> </td> <td> U+02AC4 </td> <td> <span class="glyph" title="">⫄</span> </td> </tr>
     *       <tr id="entity-Superset"><td> <code title="">Superset;</code> </td> <td> U+02283 </td> <td> <span class="glyph" title="">⊃</span> </td> </tr>
     *       <tr id="entity-SupersetEqual"><td> <code title="">SupersetEqual;</code> </td> <td> U+02287 </td> <td> <span class="glyph" title="">⊇</span> </td> </tr>
     *       <tr id="entity-suphsol"><td> <code title="">suphsol;</code> </td> <td> U+027C9 </td> <td> <span class="glyph" title="">⟉</span> </td> </tr>
     *       <tr id="entity-suphsub"><td> <code title="">suphsub;</code> </td> <td> U+02AD7 </td> <td> <span class="glyph" title="">⫗</span> </td> </tr>
     *       <tr id="entity-suplarr"><td> <code title="">suplarr;</code> </td> <td> U+0297B </td> <td> <span class="glyph" title="">⥻</span> </td> </tr>
     *       <tr id="entity-supmult"><td> <code title="">supmult;</code> </td> <td> U+02AC2 </td> <td> <span class="glyph" title="">⫂</span> </td> </tr>
     *       <tr id="entity-supnE"><td> <code title="">supnE;</code> </td> <td> U+02ACC </td> <td> <span class="glyph" title="">⫌</span> </td> </tr>
     *       <tr id="entity-supne"><td> <code title="">supne;</code> </td> <td> U+0228B </td> <td> <span class="glyph" title="">⊋</span> </td> </tr>
     *       <tr id="entity-supplus"><td> <code title="">supplus;</code> </td> <td> U+02AC0 </td> <td> <span class="glyph" title="">⫀</span> </td> </tr>
     *       <tr id="entity-Supset"><td> <code title="">Supset;</code> </td> <td> U+022D1 </td> <td> <span class="glyph" title="">⋑</span> </td> </tr>
     *       <tr id="entity-supset"><td> <code title="">supset;</code> </td> <td> U+02283 </td> <td> <span class="glyph" title="">⊃</span> </td> </tr>
     *       <tr id="entity-supseteq"><td> <code title="">supseteq;</code> </td> <td> U+02287 </td> <td> <span class="glyph" title="">⊇</span> </td> </tr>
     *       <tr id="entity-supseteqq"><td> <code title="">supseteqq;</code> </td> <td> U+02AC6 </td> <td> <span class="glyph" title="">⫆</span> </td> </tr>
     *       <tr id="entity-supsetneq"><td> <code title="">supsetneq;</code> </td> <td> U+0228B </td> <td> <span class="glyph" title="">⊋</span> </td> </tr>
     *       <tr id="entity-supsetneqq"><td> <code title="">supsetneqq;</code> </td> <td> U+02ACC </td> <td> <span class="glyph" title="">⫌</span> </td> </tr>
     *       <tr id="entity-supsim"><td> <code title="">supsim;</code> </td> <td> U+02AC8 </td> <td> <span class="glyph" title="">⫈</span> </td> </tr>
     *       <tr id="entity-supsub"><td> <code title="">supsub;</code> </td> <td> U+02AD4 </td> <td> <span class="glyph" title="">⫔</span> </td> </tr>
     *       <tr id="entity-supsup"><td> <code title="">supsup;</code> </td> <td> U+02AD6 </td> <td> <span class="glyph" title="">⫖</span> </td> </tr>
     *       <tr id="entity-swarhk"><td> <code title="">swarhk;</code> </td> <td> U+02926 </td> <td> <span class="glyph" title="">⤦</span> </td> </tr>
     *       <tr id="entity-swArr"><td> <code title="">swArr;</code> </td> <td> U+021D9 </td> <td> <span class="glyph" title="">⇙</span> </td> </tr>
     *       <tr id="entity-swarr"><td> <code title="">swarr;</code> </td> <td> U+02199 </td> <td> <span class="glyph" title="">↙</span> </td> </tr>
     *       <tr id="entity-swarrow"><td> <code title="">swarrow;</code> </td> <td> U+02199 </td> <td> <span class="glyph" title="">↙</span> </td> </tr>
     *       <tr id="entity-swnwar"><td> <code title="">swnwar;</code> </td> <td> U+0292A </td> <td> <span class="glyph" title="">⤪</span> </td> </tr>
     *       <tr id="entity-szlig"><td> <code title="">szlig;</code> </td> <td> U+000DF </td> <td> <span class="glyph" title="">ß</span> </td> </tr>
     *       <tr class="impl" id="entity-szlig-legacy"><td> <code title="">szlig</code> </td> <td> U+000DF </td> <td> <span title="">ß</span> </td> </tr>
     *       <tr id="entity-Tab"><td> <code title="">Tab;</code> </td> <td> U+00009 </td> <td> <span class="glyph control" title="">␉</span> </td> </tr>
     *       <tr id="entity-target"><td> <code title="">target;</code> </td> <td> U+02316 </td> <td> <span class="glyph" title="">⌖</span> </td> </tr>
     *       <tr id="entity-Tau"><td> <code title="">Tau;</code> </td> <td> U+003A4 </td> <td> <span class="glyph" title="">Τ</span> </td> </tr>
     *       <tr id="entity-tau"><td> <code title="">tau;</code> </td> <td> U+003C4 </td> <td> <span class="glyph" title="">τ</span> </td> </tr>
     *       <tr id="entity-tbrk"><td> <code title="">tbrk;</code> </td> <td> U+023B4 </td> <td> <span class="glyph" title="">⎴</span> </td> </tr>
     *       <tr id="entity-Tcaron"><td> <code title="">Tcaron;</code> </td> <td> U+00164 </td> <td> <span class="glyph" title="">Ť</span> </td> </tr>
     *       <tr id="entity-tcaron"><td> <code title="">tcaron;</code> </td> <td> U+00165 </td> <td> <span class="glyph" title="">ť</span> </td> </tr>
     *       <tr id="entity-Tcedil"><td> <code title="">Tcedil;</code> </td> <td> U+00162 </td> <td> <span class="glyph" title="">Ţ</span> </td> </tr>
     *       <tr id="entity-tcedil"><td> <code title="">tcedil;</code> </td> <td> U+00163 </td> <td> <span class="glyph" title="">ţ</span> </td> </tr>
     *       <tr id="entity-Tcy"><td> <code title="">Tcy;</code> </td> <td> U+00422 </td> <td> <span class="glyph" title="">Т</span> </td> </tr>
     *       <tr id="entity-tcy"><td> <code title="">tcy;</code> </td> <td> U+00442 </td> <td> <span class="glyph" title="">т</span> </td> </tr>
     *       <tr id="entity-tdot"><td> <code title="">tdot;</code> </td> <td> U+020DB </td> <td> <span class="glyph composition" title="">◌⃛</span> </td> </tr>
     *       <tr id="entity-telrec"><td> <code title="">telrec;</code> </td> <td> U+02315 </td> <td> <span class="glyph" title="">⌕</span> </td> </tr>
     *       <tr id="entity-Tfr"><td> <code title="">Tfr;</code> </td> <td> U+1D517 </td> <td> <span class="glyph" title="">𝔗</span> </td> </tr>
     *       <tr id="entity-tfr"><td> <code title="">tfr;</code> </td> <td> U+1D531 </td> <td> <span class="glyph" title="">𝔱</span> </td> </tr>
     *       <tr id="entity-there4"><td> <code title="">there4;</code> </td> <td> U+02234 </td> <td> <span class="glyph" title="">∴</span> </td> </tr>
     *       <tr id="entity-Therefore"><td> <code title="">Therefore;</code> </td> <td> U+02234 </td> <td> <span class="glyph" title="">∴</span> </td> </tr>
     *       <tr id="entity-therefore"><td> <code title="">therefore;</code> </td> <td> U+02234 </td> <td> <span class="glyph" title="">∴</span> </td> </tr>
     *       <tr id="entity-Theta"><td> <code title="">Theta;</code> </td> <td> U+00398 </td> <td> <span class="glyph" title="">Θ</span> </td> </tr>
     *       <tr id="entity-theta"><td> <code title="">theta;</code> </td> <td> U+003B8 </td> <td> <span class="glyph" title="">θ</span> </td> </tr>
     *       <tr id="entity-thetasym"><td> <code title="">thetasym;</code> </td> <td> U+003D1 </td> <td> <span class="glyph" title="">ϑ</span> </td> </tr>
     *       <tr id="entity-thetav"><td> <code title="">thetav;</code> </td> <td> U+003D1 </td> <td> <span class="glyph" title="">ϑ</span> </td> </tr>
     *       <tr id="entity-thickapprox"><td> <code title="">thickapprox;</code> </td> <td> U+02248 </td> <td> <span class="glyph" title="">≈</span> </td> </tr>
     *       <tr id="entity-thicksim"><td> <code title="">thicksim;</code> </td> <td> U+0223C </td> <td> <span class="glyph" title="">∼</span> </td> </tr>
     *       <tr id="entity-ThickSpace"><td> <code title="">ThickSpace;</code> </td> <td> U+0205F U+0200A </td> <td> <span class="glyph compound" title="">  </span> </td> </tr>
     *       <tr id="entity-thinsp"><td> <code title="">thinsp;</code> </td> <td> U+02009 </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-ThinSpace"><td> <code title="">ThinSpace;</code> </td> <td> U+02009 </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-thkap"><td> <code title="">thkap;</code> </td> <td> U+02248 </td> <td> <span class="glyph" title="">≈</span> </td> </tr>
     *       <tr id="entity-thksim"><td> <code title="">thksim;</code> </td> <td> U+0223C </td> <td> <span class="glyph" title="">∼</span> </td> </tr>
     *       <tr id="entity-THORN"><td> <code title="">THORN;</code> </td> <td> U+000DE </td> <td> <span class="glyph" title="">Þ</span> </td> </tr>
     *       <tr class="impl" id="entity-THORN-legacy"><td> <code title="">THORN</code> </td> <td> U+000DE </td> <td> <span title="">Þ</span> </td> </tr>
     *       <tr id="entity-thorn"><td> <code title="">thorn;</code> </td> <td> U+000FE </td> <td> <span class="glyph" title="">þ</span> </td> </tr>
     *       <tr class="impl" id="entity-thorn-legacy"><td> <code title="">thorn</code> </td> <td> U+000FE </td> <td> <span title="">þ</span> </td> </tr>
     *       <tr id="entity-Tilde"><td> <code title="">Tilde;</code> </td> <td> U+0223C </td> <td> <span class="glyph" title="">∼</span> </td> </tr>
     *       <tr id="entity-tilde"><td> <code title="">tilde;</code> </td> <td> U+002DC </td> <td> <span class="glyph" title="">˜</span> </td> </tr>
     *       <tr id="entity-TildeEqual"><td> <code title="">TildeEqual;</code> </td> <td> U+02243 </td> <td> <span class="glyph" title="">≃</span> </td> </tr>
     *       <tr id="entity-TildeFullEqual"><td> <code title="">TildeFullEqual;</code> </td> <td> U+02245 </td> <td> <span class="glyph" title="">≅</span> </td> </tr>
     *       <tr id="entity-TildeTilde"><td> <code title="">TildeTilde;</code> </td> <td> U+02248 </td> <td> <span class="glyph" title="">≈</span> </td> </tr>
     *       <tr id="entity-times"><td> <code title="">times;</code> </td> <td> U+000D7 </td> <td> <span class="glyph" title="">×</span> </td> </tr>
     *       <tr class="impl" id="entity-times-legacy"><td> <code title="">times</code> </td> <td> U+000D7 </td> <td> <span title="">×</span> </td> </tr>
     *       <tr id="entity-timesb"><td> <code title="">timesb;</code> </td> <td> U+022A0 </td> <td> <span class="glyph" title="">⊠</span> </td> </tr>
     *       <tr id="entity-timesbar"><td> <code title="">timesbar;</code> </td> <td> U+02A31 </td> <td> <span class="glyph" title="">⨱</span> </td> </tr>
     *       <tr id="entity-timesd"><td> <code title="">timesd;</code> </td> <td> U+02A30 </td> <td> <span class="glyph" title="">⨰</span> </td> </tr>
     *       <tr id="entity-tint"><td> <code title="">tint;</code> </td> <td> U+0222D </td> <td> <span class="glyph" title="">∭</span> </td> </tr>
     *       <tr id="entity-toea"><td> <code title="">toea;</code> </td> <td> U+02928 </td> <td> <span class="glyph" title="">⤨</span> </td> </tr>
     *       <tr id="entity-top"><td> <code title="">top;</code> </td> <td> U+022A4 </td> <td> <span class="glyph" title="">⊤</span> </td> </tr>
     *       <tr id="entity-topbot"><td> <code title="">topbot;</code> </td> <td> U+02336 </td> <td> <span class="glyph" title="">⌶</span> </td> </tr>
     *       <tr id="entity-topcir"><td> <code title="">topcir;</code> </td> <td> U+02AF1 </td> <td> <span class="glyph" title="">⫱</span> </td> </tr>
     *       <tr id="entity-Topf"><td> <code title="">Topf;</code> </td> <td> U+1D54B </td> <td> <span class="glyph" title="">𝕋</span> </td> </tr>
     *       <tr id="entity-topf"><td> <code title="">topf;</code> </td> <td> U+1D565 </td> <td> <span class="glyph" title="">𝕥</span> </td> </tr>
     *       <tr id="entity-topfork"><td> <code title="">topfork;</code> </td> <td> U+02ADA </td> <td> <span class="glyph" title="">⫚</span> </td> </tr>
     *       <tr id="entity-tosa"><td> <code title="">tosa;</code> </td> <td> U+02929 </td> <td> <span class="glyph" title="">⤩</span> </td> </tr>
     *       <tr id="entity-tprime"><td> <code title="">tprime;</code> </td> <td> U+02034 </td> <td> <span class="glyph" title="">‴</span> </td> </tr>
     *       <tr id="entity-TRADE"><td> <code title="">TRADE;</code> </td> <td> U+02122 </td> <td> <span class="glyph" title="">™</span> </td> </tr>
     *       <tr id="entity-trade"><td> <code title="">trade;</code> </td> <td> U+02122 </td> <td> <span class="glyph" title="">™</span> </td> </tr>
     *       <tr id="entity-triangle"><td> <code title="">triangle;</code> </td> <td> U+025B5 </td> <td> <span class="glyph" title="">▵</span> </td> </tr>
     *       <tr id="entity-triangledown"><td> <code title="">triangledown;</code> </td> <td> U+025BF </td> <td> <span class="glyph" title="">▿</span> </td> </tr>
     *       <tr id="entity-triangleleft"><td> <code title="">triangleleft;</code> </td> <td> U+025C3 </td> <td> <span class="glyph" title="">◃</span> </td> </tr>
     *       <tr id="entity-trianglelefteq"><td> <code title="">trianglelefteq;</code> </td> <td> U+022B4 </td> <td> <span class="glyph" title="">⊴</span> </td> </tr>
     *       <tr id="entity-triangleq"><td> <code title="">triangleq;</code> </td> <td> U+0225C </td> <td> <span class="glyph" title="">≜</span> </td> </tr>
     *       <tr id="entity-triangleright"><td> <code title="">triangleright;</code> </td> <td> U+025B9 </td> <td> <span class="glyph" title="">▹</span> </td> </tr>
     *       <tr id="entity-trianglerighteq"><td> <code title="">trianglerighteq;</code> </td> <td> U+022B5 </td> <td> <span class="glyph" title="">⊵</span> </td> </tr>
     *       <tr id="entity-tridot"><td> <code title="">tridot;</code> </td> <td> U+025EC </td> <td> <span class="glyph" title="">◬</span> </td> </tr>
     *       <tr id="entity-trie"><td> <code title="">trie;</code> </td> <td> U+0225C </td> <td> <span class="glyph" title="">≜</span> </td> </tr>
     *       <tr id="entity-triminus"><td> <code title="">triminus;</code> </td> <td> U+02A3A </td> <td> <span class="glyph" title="">⨺</span> </td> </tr>
     *       <tr id="entity-TripleDot"><td> <code title="">TripleDot;</code> </td> <td> U+020DB </td> <td> <span class="glyph composition" title="">◌⃛</span> </td> </tr>
     *       <tr id="entity-triplus"><td> <code title="">triplus;</code> </td> <td> U+02A39 </td> <td> <span class="glyph" title="">⨹</span> </td> </tr>
     *       <tr id="entity-trisb"><td> <code title="">trisb;</code> </td> <td> U+029CD </td> <td> <span class="glyph" title="">⧍</span> </td> </tr>
     *       <tr id="entity-tritime"><td> <code title="">tritime;</code> </td> <td> U+02A3B </td> <td> <span class="glyph" title="">⨻</span> </td> </tr>
     *       <tr id="entity-trpezium"><td> <code title="">trpezium;</code> </td> <td> U+023E2 </td> <td> <span class="glyph" title="">⏢</span> </td> </tr>
     *       <tr id="entity-Tscr"><td> <code title="">Tscr;</code> </td> <td> U+1D4AF </td> <td> <span class="glyph" title="">𝒯</span> </td> </tr>
     *       <tr id="entity-tscr"><td> <code title="">tscr;</code> </td> <td> U+1D4C9 </td> <td> <span class="glyph" title="">𝓉</span> </td> </tr>
     *       <tr id="entity-TScy"><td> <code title="">TScy;</code> </td> <td> U+00426 </td> <td> <span class="glyph" title="">Ц</span> </td> </tr>
     *       <tr id="entity-tscy"><td> <code title="">tscy;</code> </td> <td> U+00446 </td> <td> <span class="glyph" title="">ц</span> </td> </tr>
     *       <tr id="entity-TSHcy"><td> <code title="">TSHcy;</code> </td> <td> U+0040B </td> <td> <span class="glyph" title="">Ћ</span> </td> </tr>
     *       <tr id="entity-tshcy"><td> <code title="">tshcy;</code> </td> <td> U+0045B </td> <td> <span class="glyph" title="">ћ</span> </td> </tr>
     *       <tr id="entity-Tstrok"><td> <code title="">Tstrok;</code> </td> <td> U+00166 </td> <td> <span class="glyph" title="">Ŧ</span> </td> </tr>
     *       <tr id="entity-tstrok"><td> <code title="">tstrok;</code> </td> <td> U+00167 </td> <td> <span class="glyph" title="">ŧ</span> </td> </tr>
     *       <tr id="entity-twixt"><td> <code title="">twixt;</code> </td> <td> U+0226C </td> <td> <span class="glyph" title="">≬</span> </td> </tr>
     *       <tr id="entity-twoheadleftarrow"><td> <code title="">twoheadleftarrow;</code> </td> <td> U+0219E </td> <td> <span class="glyph" title="">↞</span> </td> </tr>
     *       <tr id="entity-twoheadrightarrow"><td> <code title="">twoheadrightarrow;</code> </td> <td> U+021A0 </td> <td> <span class="glyph" title="">↠</span> </td> </tr>
     *       <tr id="entity-Uacute"><td> <code title="">Uacute;</code> </td> <td> U+000DA </td> <td> <span class="glyph" title="">Ú</span> </td> </tr>
     *       <tr class="impl" id="entity-Uacute-legacy"><td> <code title="">Uacute</code> </td> <td> U+000DA </td> <td> <span title="">Ú</span> </td> </tr>
     *       <tr id="entity-uacute"><td> <code title="">uacute;</code> </td> <td> U+000FA </td> <td> <span class="glyph" title="">ú</span> </td> </tr>
     *       <tr class="impl" id="entity-uacute-legacy"><td> <code title="">uacute</code> </td> <td> U+000FA </td> <td> <span title="">ú</span> </td> </tr>
     *       <tr id="entity-Uarr"><td> <code title="">Uarr;</code> </td> <td> U+0219F </td> <td> <span class="glyph" title="">↟</span> </td> </tr>
     *       <tr id="entity-uArr"><td> <code title="">uArr;</code> </td> <td> U+021D1 </td> <td> <span class="glyph" title="">⇑</span> </td> </tr>
     *       <tr id="entity-uarr"><td> <code title="">uarr;</code> </td> <td> U+02191 </td> <td> <span class="glyph" title="">↑</span> </td> </tr>
     *       <tr id="entity-Uarrocir"><td> <code title="">Uarrocir;</code> </td> <td> U+02949 </td> <td> <span class="glyph" title="">⥉</span> </td> </tr>
     *       <tr id="entity-Ubrcy"><td> <code title="">Ubrcy;</code> </td> <td> U+0040E </td> <td> <span class="glyph" title="">Ў</span> </td> </tr>
     *       <tr id="entity-ubrcy"><td> <code title="">ubrcy;</code> </td> <td> U+0045E </td> <td> <span class="glyph" title="">ў</span> </td> </tr>
     *       <tr id="entity-Ubreve"><td> <code title="">Ubreve;</code> </td> <td> U+0016C </td> <td> <span class="glyph" title="">Ŭ</span> </td> </tr>
     *       <tr id="entity-ubreve"><td> <code title="">ubreve;</code> </td> <td> U+0016D </td> <td> <span class="glyph" title="">ŭ</span> </td> </tr>
     *       <tr id="entity-Ucirc"><td> <code title="">Ucirc;</code> </td> <td> U+000DB </td> <td> <span class="glyph" title="">Û</span> </td> </tr>
     *       <tr class="impl" id="entity-Ucirc-legacy"><td> <code title="">Ucirc</code> </td> <td> U+000DB </td> <td> <span title="">Û</span> </td> </tr>
     *       <tr id="entity-ucirc"><td> <code title="">ucirc;</code> </td> <td> U+000FB </td> <td> <span class="glyph" title="">û</span> </td> </tr>
     *       <tr class="impl" id="entity-ucirc-legacy"><td> <code title="">ucirc</code> </td> <td> U+000FB </td> <td> <span title="">û</span> </td> </tr>
     *       <tr id="entity-Ucy"><td> <code title="">Ucy;</code> </td> <td> U+00423 </td> <td> <span class="glyph" title="">У</span> </td> </tr>
     *       <tr id="entity-ucy"><td> <code title="">ucy;</code> </td> <td> U+00443 </td> <td> <span class="glyph" title="">у</span> </td> </tr>
     *       <tr id="entity-udarr"><td> <code title="">udarr;</code> </td> <td> U+021C5 </td> <td> <span class="glyph" title="">⇅</span> </td> </tr>
     *       <tr id="entity-Udblac"><td> <code title="">Udblac;</code> </td> <td> U+00170 </td> <td> <span class="glyph" title="">Ű</span> </td> </tr>
     *       <tr id="entity-udblac"><td> <code title="">udblac;</code> </td> <td> U+00171 </td> <td> <span class="glyph" title="">ű</span> </td> </tr>
     *       <tr id="entity-udhar"><td> <code title="">udhar;</code> </td> <td> U+0296E </td> <td> <span class="glyph" title="">⥮</span> </td> </tr>
     *       <tr id="entity-ufisht"><td> <code title="">ufisht;</code> </td> <td> U+0297E </td> <td> <span class="glyph" title="">⥾</span> </td> </tr>
     *       <tr id="entity-Ufr"><td> <code title="">Ufr;</code> </td> <td> U+1D518 </td> <td> <span class="glyph" title="">𝔘</span> </td> </tr>
     *       <tr id="entity-ufr"><td> <code title="">ufr;</code> </td> <td> U+1D532 </td> <td> <span class="glyph" title="">𝔲</span> </td> </tr>
     *       <tr id="entity-Ugrave"><td> <code title="">Ugrave;</code> </td> <td> U+000D9 </td> <td> <span class="glyph" title="">Ù</span> </td> </tr>
     *       <tr class="impl" id="entity-Ugrave-legacy"><td> <code title="">Ugrave</code> </td> <td> U+000D9 </td> <td> <span title="">Ù</span> </td> </tr>
     *       <tr id="entity-ugrave"><td> <code title="">ugrave;</code> </td> <td> U+000F9 </td> <td> <span class="glyph" title="">ù</span> </td> </tr>
     *       <tr class="impl" id="entity-ugrave-legacy"><td> <code title="">ugrave</code> </td> <td> U+000F9 </td> <td> <span title="">ù</span> </td> </tr>
     *       <tr id="entity-uHar"><td> <code title="">uHar;</code> </td> <td> U+02963 </td> <td> <span class="glyph" title="">⥣</span> </td> </tr>
     *       <tr id="entity-uharl"><td> <code title="">uharl;</code> </td> <td> U+021BF </td> <td> <span class="glyph" title="">↿</span> </td> </tr>
     *       <tr id="entity-uharr"><td> <code title="">uharr;</code> </td> <td> U+021BE </td> <td> <span class="glyph" title="">↾</span> </td> </tr>
     *       <tr id="entity-uhblk"><td> <code title="">uhblk;</code> </td> <td> U+02580 </td> <td> <span class="glyph" title="">▀</span> </td> </tr>
     *       <tr id="entity-ulcorn"><td> <code title="">ulcorn;</code> </td> <td> U+0231C </td> <td> <span class="glyph" title="">⌜</span> </td> </tr>
     *       <tr id="entity-ulcorner"><td> <code title="">ulcorner;</code> </td> <td> U+0231C </td> <td> <span class="glyph" title="">⌜</span> </td> </tr>
     *       <tr id="entity-ulcrop"><td> <code title="">ulcrop;</code> </td> <td> U+0230F </td> <td> <span class="glyph" title="">⌏</span> </td> </tr>
     *       <tr id="entity-ultri"><td> <code title="">ultri;</code> </td> <td> U+025F8 </td> <td> <span class="glyph" title="">◸</span> </td> </tr>
     *       <tr id="entity-Umacr"><td> <code title="">Umacr;</code> </td> <td> U+0016A </td> <td> <span class="glyph" title="">Ū</span> </td> </tr>
     *       <tr id="entity-umacr"><td> <code title="">umacr;</code> </td> <td> U+0016B </td> <td> <span class="glyph" title="">ū</span> </td> </tr>
     *       <tr id="entity-uml"><td> <code title="">uml;</code> </td> <td> U+000A8 </td> <td> <span class="glyph" title="">¨</span> </td> </tr>
     *       <tr class="impl" id="entity-uml-legacy"><td> <code title="">uml</code> </td> <td> U+000A8 </td> <td> <span title="">¨</span> </td> </tr>
     *       <tr id="entity-UnderBar"><td> <code title="">UnderBar;</code> </td> <td> U+0005F </td> <td> <span class="glyph" title="">_</span> </td> </tr>
     *       <tr id="entity-UnderBrace"><td> <code title="">UnderBrace;</code> </td> <td> U+023DF </td> <td> <span class="glyph" title="">⏟</span> </td> </tr>
     *       <tr id="entity-UnderBracket"><td> <code title="">UnderBracket;</code> </td> <td> U+023B5 </td> <td> <span class="glyph" title="">⎵</span> </td> </tr>
     *       <tr id="entity-UnderParenthesis"><td> <code title="">UnderParenthesis;</code> </td> <td> U+023DD </td> <td> <span class="glyph" title="">⏝</span> </td> </tr>
     *       <tr id="entity-Union"><td> <code title="">Union;</code> </td> <td> U+022C3 </td> <td> <span class="glyph" title="">⋃</span> </td> </tr>
     *       <tr id="entity-UnionPlus"><td> <code title="">UnionPlus;</code> </td> <td> U+0228E </td> <td> <span class="glyph" title="">⊎</span> </td> </tr>
     *       <tr id="entity-Uogon"><td> <code title="">Uogon;</code> </td> <td> U+00172 </td> <td> <span class="glyph" title="">Ų</span> </td> </tr>
     *       <tr id="entity-uogon"><td> <code title="">uogon;</code> </td> <td> U+00173 </td> <td> <span class="glyph" title="">ų</span> </td> </tr>
     *       <tr id="entity-Uopf"><td> <code title="">Uopf;</code> </td> <td> U+1D54C </td> <td> <span class="glyph" title="">𝕌</span> </td> </tr>
     *       <tr id="entity-uopf"><td> <code title="">uopf;</code> </td> <td> U+1D566 </td> <td> <span class="glyph" title="">𝕦</span> </td> </tr>
     *       <tr id="entity-UpArrow"><td> <code title="">UpArrow;</code> </td> <td> U+02191 </td> <td> <span class="glyph" title="">↑</span> </td> </tr>
     *       <tr id="entity-Uparrow"><td> <code title="">Uparrow;</code> </td> <td> U+021D1 </td> <td> <span class="glyph" title="">⇑</span> </td> </tr>
     *       <tr id="entity-uparrow"><td> <code title="">uparrow;</code> </td> <td> U+02191 </td> <td> <span class="glyph" title="">↑</span> </td> </tr>
     *       <tr id="entity-UpArrowBar"><td> <code title="">UpArrowBar;</code> </td> <td> U+02912 </td> <td> <span class="glyph" title="">⤒</span> </td> </tr>
     *       <tr id="entity-UpArrowDownArrow"><td> <code title="">UpArrowDownArrow;</code> </td> <td> U+021C5 </td> <td> <span class="glyph" title="">⇅</span> </td> </tr>
     *       <tr id="entity-UpDownArrow"><td> <code title="">UpDownArrow;</code> </td> <td> U+02195 </td> <td> <span class="glyph" title="">↕</span> </td> </tr>
     *       <tr id="entity-Updownarrow"><td> <code title="">Updownarrow;</code> </td> <td> U+021D5 </td> <td> <span class="glyph" title="">⇕</span> </td> </tr>
     *       <tr id="entity-updownarrow"><td> <code title="">updownarrow;</code> </td> <td> U+02195 </td> <td> <span class="glyph" title="">↕</span> </td> </tr>
     *       <tr id="entity-UpEquilibrium"><td> <code title="">UpEquilibrium;</code> </td> <td> U+0296E </td> <td> <span class="glyph" title="">⥮</span> </td> </tr>
     *       <tr id="entity-upharpoonleft"><td> <code title="">upharpoonleft;</code> </td> <td> U+021BF </td> <td> <span class="glyph" title="">↿</span> </td> </tr>
     *       <tr id="entity-upharpoonright"><td> <code title="">upharpoonright;</code> </td> <td> U+021BE </td> <td> <span class="glyph" title="">↾</span> </td> </tr>
     *       <tr id="entity-uplus"><td> <code title="">uplus;</code> </td> <td> U+0228E </td> <td> <span class="glyph" title="">⊎</span> </td> </tr>
     *       <tr id="entity-UpperLeftArrow"><td> <code title="">UpperLeftArrow;</code> </td> <td> U+02196 </td> <td> <span class="glyph" title="">↖</span> </td> </tr>
     *       <tr id="entity-UpperRightArrow"><td> <code title="">UpperRightArrow;</code> </td> <td> U+02197 </td> <td> <span class="glyph" title="">↗</span> </td> </tr>
     *       <tr id="entity-Upsi"><td> <code title="">Upsi;</code> </td> <td> U+003D2 </td> <td> <span class="glyph" title="">ϒ</span> </td> </tr>
     *       <tr id="entity-upsi"><td> <code title="">upsi;</code> </td> <td> U+003C5 </td> <td> <span class="glyph" title="">υ</span> </td> </tr>
     *       <tr id="entity-upsih"><td> <code title="">upsih;</code> </td> <td> U+003D2 </td> <td> <span class="glyph" title="">ϒ</span> </td> </tr>
     *       <tr id="entity-Upsilon"><td> <code title="">Upsilon;</code> </td> <td> U+003A5 </td> <td> <span class="glyph" title="">Υ</span> </td> </tr>
     *       <tr id="entity-upsilon"><td> <code title="">upsilon;</code> </td> <td> U+003C5 </td> <td> <span class="glyph" title="">υ</span> </td> </tr>
     *       <tr id="entity-UpTee"><td> <code title="">UpTee;</code> </td> <td> U+022A5 </td> <td> <span class="glyph" title="">⊥</span> </td> </tr>
     *       <tr id="entity-UpTeeArrow"><td> <code title="">UpTeeArrow;</code> </td> <td> U+021A5 </td> <td> <span class="glyph" title="">↥</span> </td> </tr>
     *       <tr id="entity-upuparrows"><td> <code title="">upuparrows;</code> </td> <td> U+021C8 </td> <td> <span class="glyph" title="">⇈</span> </td> </tr>
     *       <tr id="entity-urcorn"><td> <code title="">urcorn;</code> </td> <td> U+0231D </td> <td> <span class="glyph" title="">⌝</span> </td> </tr>
     *       <tr id="entity-urcorner"><td> <code title="">urcorner;</code> </td> <td> U+0231D </td> <td> <span class="glyph" title="">⌝</span> </td> </tr>
     *       <tr id="entity-urcrop"><td> <code title="">urcrop;</code> </td> <td> U+0230E </td> <td> <span class="glyph" title="">⌎</span> </td> </tr>
     *       <tr id="entity-Uring"><td> <code title="">Uring;</code> </td> <td> U+0016E </td> <td> <span class="glyph" title="">Ů</span> </td> </tr>
     *       <tr id="entity-uring"><td> <code title="">uring;</code> </td> <td> U+0016F </td> <td> <span class="glyph" title="">ů</span> </td> </tr>
     *       <tr id="entity-urtri"><td> <code title="">urtri;</code> </td> <td> U+025F9 </td> <td> <span class="glyph" title="">◹</span> </td> </tr>
     *       <tr id="entity-Uscr"><td> <code title="">Uscr;</code> </td> <td> U+1D4B0 </td> <td> <span class="glyph" title="">𝒰</span> </td> </tr>
     *       <tr id="entity-uscr"><td> <code title="">uscr;</code> </td> <td> U+1D4CA </td> <td> <span class="glyph" title="">𝓊</span> </td> </tr>
     *       <tr id="entity-utdot"><td> <code title="">utdot;</code> </td> <td> U+022F0 </td> <td> <span class="glyph" title="">⋰</span> </td> </tr>
     *       <tr id="entity-Utilde"><td> <code title="">Utilde;</code> </td> <td> U+00168 </td> <td> <span class="glyph" title="">Ũ</span> </td> </tr>
     *       <tr id="entity-utilde"><td> <code title="">utilde;</code> </td> <td> U+00169 </td> <td> <span class="glyph" title="">ũ</span> </td> </tr>
     *       <tr id="entity-utri"><td> <code title="">utri;</code> </td> <td> U+025B5 </td> <td> <span class="glyph" title="">▵</span> </td> </tr>
     *       <tr id="entity-utrif"><td> <code title="">utrif;</code> </td> <td> U+025B4 </td> <td> <span class="glyph" title="">▴</span> </td> </tr>
     *       <tr id="entity-uuarr"><td> <code title="">uuarr;</code> </td> <td> U+021C8 </td> <td> <span class="glyph" title="">⇈</span> </td> </tr>
     *       <tr id="entity-Uuml"><td> <code title="">Uuml;</code> </td> <td> U+000DC </td> <td> <span class="glyph" title="">Ü</span> </td> </tr>
     *       <tr class="impl" id="entity-Uuml-legacy"><td> <code title="">Uuml</code> </td> <td> U+000DC </td> <td> <span title="">Ü</span> </td> </tr>
     *       <tr id="entity-uuml"><td> <code title="">uuml;</code> </td> <td> U+000FC </td> <td> <span class="glyph" title="">ü</span> </td> </tr>
     *       <tr class="impl" id="entity-uuml-legacy"><td> <code title="">uuml</code> </td> <td> U+000FC </td> <td> <span title="">ü</span> </td> </tr>
     *       <tr id="entity-uwangle"><td> <code title="">uwangle;</code> </td> <td> U+029A7 </td> <td> <span class="glyph" title="">⦧</span> </td> </tr>
     *       <tr id="entity-vangrt"><td> <code title="">vangrt;</code> </td> <td> U+0299C </td> <td> <span class="glyph" title="">⦜</span> </td> </tr>
     *       <tr id="entity-varepsilon"><td> <code title="">varepsilon;</code> </td> <td> U+003F5 </td> <td> <span class="glyph" title="">ϵ</span> </td> </tr>
     *       <tr id="entity-varkappa"><td> <code title="">varkappa;</code> </td> <td> U+003F0 </td> <td> <span class="glyph" title="">ϰ</span> </td> </tr>
     *       <tr id="entity-varnothing"><td> <code title="">varnothing;</code> </td> <td> U+02205 </td> <td> <span class="glyph" title="">∅</span> </td> </tr>
     *       <tr id="entity-varphi"><td> <code title="">varphi;</code> </td> <td> U+003D5 </td> <td> <span class="glyph" title="">ϕ</span> </td> </tr>
     *       <tr id="entity-varpi"><td> <code title="">varpi;</code> </td> <td> U+003D6 </td> <td> <span class="glyph" title="">ϖ</span> </td> </tr>
     *       <tr id="entity-varpropto"><td> <code title="">varpropto;</code> </td> <td> U+0221D </td> <td> <span class="glyph" title="">∝</span> </td> </tr>
     *       <tr id="entity-vArr"><td> <code title="">vArr;</code> </td> <td> U+021D5 </td> <td> <span class="glyph" title="">⇕</span> </td> </tr>
     *       <tr id="entity-varr"><td> <code title="">varr;</code> </td> <td> U+02195 </td> <td> <span class="glyph" title="">↕</span> </td> </tr>
     *       <tr id="entity-varrho"><td> <code title="">varrho;</code> </td> <td> U+003F1 </td> <td> <span class="glyph" title="">ϱ</span> </td> </tr>
     *       <tr id="entity-varsigma"><td> <code title="">varsigma;</code> </td> <td> U+003C2 </td> <td> <span class="glyph" title="">ς</span> </td> </tr>
     *       <tr id="entity-varsubsetneq"><td> <code title="">varsubsetneq;</code> </td> <td> U+0228A U+0FE00 </td> <td> <span class="glyph compound" title="">⊊︀</span> </td> </tr>
     *       <tr id="entity-varsubsetneqq"><td> <code title="">varsubsetneqq;</code> </td> <td> U+02ACB U+0FE00 </td> <td> <span class="glyph compound" title="">⫋︀</span> </td> </tr>
     *       <tr id="entity-varsupsetneq"><td> <code title="">varsupsetneq;</code> </td> <td> U+0228B U+0FE00 </td> <td> <span class="glyph compound" title="">⊋︀</span> </td> </tr>
     *       <tr id="entity-varsupsetneqq"><td> <code title="">varsupsetneqq;</code> </td> <td> U+02ACC U+0FE00 </td> <td> <span class="glyph compound" title="">⫌︀</span> </td> </tr>
     *       <tr id="entity-vartheta"><td> <code title="">vartheta;</code> </td> <td> U+003D1 </td> <td> <span class="glyph" title="">ϑ</span> </td> </tr>
     *       <tr id="entity-vartriangleleft"><td> <code title="">vartriangleleft;</code> </td> <td> U+022B2 </td> <td> <span class="glyph" title="">⊲</span> </td> </tr>
     *       <tr id="entity-vartriangleright"><td> <code title="">vartriangleright;</code> </td> <td> U+022B3 </td> <td> <span class="glyph" title="">⊳</span> </td> </tr>
     *       <tr id="entity-Vbar"><td> <code title="">Vbar;</code> </td> <td> U+02AEB </td> <td> <span class="glyph" title="">⫫</span> </td> </tr>
     *       <tr id="entity-vBar"><td> <code title="">vBar;</code> </td> <td> U+02AE8 </td> <td> <span class="glyph" title="">⫨</span> </td> </tr>
     *       <tr id="entity-vBarv"><td> <code title="">vBarv;</code> </td> <td> U+02AE9 </td> <td> <span class="glyph" title="">⫩</span> </td> </tr>
     *       <tr id="entity-Vcy"><td> <code title="">Vcy;</code> </td> <td> U+00412 </td> <td> <span class="glyph" title="">В</span> </td> </tr>
     *       <tr id="entity-vcy"><td> <code title="">vcy;</code> </td> <td> U+00432 </td> <td> <span class="glyph" title="">в</span> </td> </tr>
     *       <tr id="entity-VDash"><td> <code title="">VDash;</code> </td> <td> U+022AB </td> <td> <span class="glyph" title="">⊫</span> </td> </tr>
     *       <tr id="entity-Vdash"><td> <code title="">Vdash;</code> </td> <td> U+022A9 </td> <td> <span class="glyph" title="">⊩</span> </td> </tr>
     *       <tr id="entity-vDash"><td> <code title="">vDash;</code> </td> <td> U+022A8 </td> <td> <span class="glyph" title="">⊨</span> </td> </tr>
     *       <tr id="entity-vdash"><td> <code title="">vdash;</code> </td> <td> U+022A2 </td> <td> <span class="glyph" title="">⊢</span> </td> </tr>
     *       <tr id="entity-Vdashl"><td> <code title="">Vdashl;</code> </td> <td> U+02AE6 </td> <td> <span class="glyph" title="">⫦</span> </td> </tr>
     *       <tr id="entity-Vee"><td> <code title="">Vee;</code> </td> <td> U+022C1 </td> <td> <span class="glyph" title="">⋁</span> </td> </tr>
     *       <tr id="entity-vee"><td> <code title="">vee;</code> </td> <td> U+02228 </td> <td> <span class="glyph" title="">∨</span> </td> </tr>
     *       <tr id="entity-veebar"><td> <code title="">veebar;</code> </td> <td> U+022BB </td> <td> <span class="glyph" title="">⊻</span> </td> </tr>
     *       <tr id="entity-veeeq"><td> <code title="">veeeq;</code> </td> <td> U+0225A </td> <td> <span class="glyph" title="">≚</span> </td> </tr>
     *       <tr id="entity-vellip"><td> <code title="">vellip;</code> </td> <td> U+022EE </td> <td> <span class="glyph" title="">⋮</span> </td> </tr>
     *       <tr id="entity-Verbar"><td> <code title="">Verbar;</code> </td> <td> U+02016 </td> <td> <span class="glyph" title="">‖</span> </td> </tr>
     *       <tr id="entity-verbar"><td> <code title="">verbar;</code> </td> <td> U+0007C </td> <td> <span class="glyph" title="">|</span> </td> </tr>
     *       <tr id="entity-Vert"><td> <code title="">Vert;</code> </td> <td> U+02016 </td> <td> <span class="glyph" title="">‖</span> </td> </tr>
     *       <tr id="entity-vert"><td> <code title="">vert;</code> </td> <td> U+0007C </td> <td> <span class="glyph" title="">|</span> </td> </tr>
     *       <tr id="entity-VerticalBar"><td> <code title="">VerticalBar;</code> </td> <td> U+02223 </td> <td> <span class="glyph" title="">∣</span> </td> </tr>
     *       <tr id="entity-VerticalLine"><td> <code title="">VerticalLine;</code> </td> <td> U+0007C </td> <td> <span class="glyph" title="">|</span> </td> </tr>
     *       <tr id="entity-VerticalSeparator"><td> <code title="">VerticalSeparator;</code> </td> <td> U+02758 </td> <td> <span class="glyph" title="">❘</span> </td> </tr>
     *       <tr id="entity-VerticalTilde"><td> <code title="">VerticalTilde;</code> </td> <td> U+02240 </td> <td> <span class="glyph" title="">≀</span> </td> </tr>
     *       <tr id="entity-VeryThinSpace"><td> <code title="">VeryThinSpace;</code> </td> <td> U+0200A </td> <td> <span class="glyph" title=""> </span> </td> </tr>
     *       <tr id="entity-Vfr"><td> <code title="">Vfr;</code> </td> <td> U+1D519 </td> <td> <span class="glyph" title="">𝔙</span> </td> </tr>
     *       <tr id="entity-vfr"><td> <code title="">vfr;</code> </td> <td> U+1D533 </td> <td> <span class="glyph" title="">𝔳</span> </td> </tr>
     *       <tr id="entity-vltri"><td> <code title="">vltri;</code> </td> <td> U+022B2 </td> <td> <span class="glyph" title="">⊲</span> </td> </tr>
     *       <tr id="entity-vnsub"><td> <code title="">vnsub;</code> </td> <td> U+02282 U+020D2 </td> <td> <span class="glyph compound" title="">⊂⃒</span> </td> </tr>
     *       <tr id="entity-vnsup"><td> <code title="">vnsup;</code> </td> <td> U+02283 U+020D2 </td> <td> <span class="glyph compound" title="">⊃⃒</span> </td> </tr>
     *       <tr id="entity-Vopf"><td> <code title="">Vopf;</code> </td> <td> U+1D54D </td> <td> <span class="glyph" title="">𝕍</span> </td> </tr>
     *       <tr id="entity-vopf"><td> <code title="">vopf;</code> </td> <td> U+1D567 </td> <td> <span class="glyph" title="">𝕧</span> </td> </tr>
     *       <tr id="entity-vprop"><td> <code title="">vprop;</code> </td> <td> U+0221D </td> <td> <span class="glyph" title="">∝</span> </td> </tr>
     *       <tr id="entity-vrtri"><td> <code title="">vrtri;</code> </td> <td> U+022B3 </td> <td> <span class="glyph" title="">⊳</span> </td> </tr>
     *       <tr id="entity-Vscr"><td> <code title="">Vscr;</code> </td> <td> U+1D4B1 </td> <td> <span class="glyph" title="">𝒱</span> </td> </tr>
     *       <tr id="entity-vscr"><td> <code title="">vscr;</code> </td> <td> U+1D4CB </td> <td> <span class="glyph" title="">𝓋</span> </td> </tr>
     *       <tr id="entity-vsubnE"><td> <code title="">vsubnE;</code> </td> <td> U+02ACB U+0FE00 </td> <td> <span class="glyph compound" title="">⫋︀</span> </td> </tr>
     *       <tr id="entity-vsubne"><td> <code title="">vsubne;</code> </td> <td> U+0228A U+0FE00 </td> <td> <span class="glyph compound" title="">⊊︀</span> </td> </tr>
     *       <tr id="entity-vsupnE"><td> <code title="">vsupnE;</code> </td> <td> U+02ACC U+0FE00 </td> <td> <span class="glyph compound" title="">⫌︀</span> </td> </tr>
     *       <tr id="entity-vsupne"><td> <code title="">vsupne;</code> </td> <td> U+0228B U+0FE00 </td> <td> <span class="glyph compound" title="">⊋︀</span> </td> </tr>
     *       <tr id="entity-Vvdash"><td> <code title="">Vvdash;</code> </td> <td> U+022AA </td> <td> <span class="glyph" title="">⊪</span> </td> </tr>
     *       <tr id="entity-vzigzag"><td> <code title="">vzigzag;</code> </td> <td> U+0299A </td> <td> <span class="glyph" title="">⦚</span> </td> </tr>
     *       <tr id="entity-Wcirc"><td> <code title="">Wcirc;</code> </td> <td> U+00174 </td> <td> <span class="glyph" title="">Ŵ</span> </td> </tr>
     *       <tr id="entity-wcirc"><td> <code title="">wcirc;</code> </td> <td> U+00175 </td> <td> <span class="glyph" title="">ŵ</span> </td> </tr>
     *       <tr id="entity-wedbar"><td> <code title="">wedbar;</code> </td> <td> U+02A5F </td> <td> <span class="glyph" title="">⩟</span> </td> </tr>
     *       <tr id="entity-Wedge"><td> <code title="">Wedge;</code> </td> <td> U+022C0 </td> <td> <span class="glyph" title="">⋀</span> </td> </tr>
     *       <tr id="entity-wedge"><td> <code title="">wedge;</code> </td> <td> U+02227 </td> <td> <span class="glyph" title="">∧</span> </td> </tr>
     *       <tr id="entity-wedgeq"><td> <code title="">wedgeq;</code> </td> <td> U+02259 </td> <td> <span class="glyph" title="">≙</span> </td> </tr>
     *       <tr id="entity-weierp"><td> <code title="">weierp;</code> </td> <td> U+02118 </td> <td> <span class="glyph" title="">℘</span> </td> </tr>
     *       <tr id="entity-Wfr"><td> <code title="">Wfr;</code> </td> <td> U+1D51A </td> <td> <span class="glyph" title="">𝔚</span> </td> </tr>
     *       <tr id="entity-wfr"><td> <code title="">wfr;</code> </td> <td> U+1D534 </td> <td> <span class="glyph" title="">𝔴</span> </td> </tr>
     *       <tr id="entity-Wopf"><td> <code title="">Wopf;</code> </td> <td> U+1D54E </td> <td> <span class="glyph" title="">𝕎</span> </td> </tr>
     *       <tr id="entity-wopf"><td> <code title="">wopf;</code> </td> <td> U+1D568 </td> <td> <span class="glyph" title="">𝕨</span> </td> </tr>
     *       <tr id="entity-wp"><td> <code title="">wp;</code> </td> <td> U+02118 </td> <td> <span class="glyph" title="">℘</span> </td> </tr>
     *       <tr id="entity-wr"><td> <code title="">wr;</code> </td> <td> U+02240 </td> <td> <span class="glyph" title="">≀</span> </td> </tr>
     *       <tr id="entity-wreath"><td> <code title="">wreath;</code> </td> <td> U+02240 </td> <td> <span class="glyph" title="">≀</span> </td> </tr>
     *       <tr id="entity-Wscr"><td> <code title="">Wscr;</code> </td> <td> U+1D4B2 </td> <td> <span class="glyph" title="">𝒲</span> </td> </tr>
     *       <tr id="entity-wscr"><td> <code title="">wscr;</code> </td> <td> U+1D4CC </td> <td> <span class="glyph" title="">𝓌</span> </td> </tr>
     *       <tr id="entity-xcap"><td> <code title="">xcap;</code> </td> <td> U+022C2 </td> <td> <span class="glyph" title="">⋂</span> </td> </tr>
     *       <tr id="entity-xcirc"><td> <code title="">xcirc;</code> </td> <td> U+025EF </td> <td> <span class="glyph" title="">◯</span> </td> </tr>
     *       <tr id="entity-xcup"><td> <code title="">xcup;</code> </td> <td> U+022C3 </td> <td> <span class="glyph" title="">⋃</span> </td> </tr>
     *       <tr id="entity-xdtri"><td> <code title="">xdtri;</code> </td> <td> U+025BD </td> <td> <span class="glyph" title="">▽</span> </td> </tr>
     *       <tr id="entity-Xfr"><td> <code title="">Xfr;</code> </td> <td> U+1D51B </td> <td> <span class="glyph" title="">𝔛</span> </td> </tr>
     *       <tr id="entity-xfr"><td> <code title="">xfr;</code> </td> <td> U+1D535 </td> <td> <span class="glyph" title="">𝔵</span> </td> </tr>
     *       <tr id="entity-xhArr"><td> <code title="">xhArr;</code> </td> <td> U+027FA </td> <td> <span class="glyph" title="">⟺</span> </td> </tr>
     *       <tr id="entity-xharr"><td> <code title="">xharr;</code> </td> <td> U+027F7 </td> <td> <span class="glyph" title="">⟷</span> </td> </tr>
     *       <tr id="entity-Xi"><td> <code title="">Xi;</code> </td> <td> U+0039E </td> <td> <span class="glyph" title="">Ξ</span> </td> </tr>
     *       <tr id="entity-xi"><td> <code title="">xi;</code> </td> <td> U+003BE </td> <td> <span class="glyph" title="">ξ</span> </td> </tr>
     *       <tr id="entity-xlArr"><td> <code title="">xlArr;</code> </td> <td> U+027F8 </td> <td> <span class="glyph" title="">⟸</span> </td> </tr>
     *       <tr id="entity-xlarr"><td> <code title="">xlarr;</code> </td> <td> U+027F5 </td> <td> <span class="glyph" title="">⟵</span> </td> </tr>
     *       <tr id="entity-xmap"><td> <code title="">xmap;</code> </td> <td> U+027FC </td> <td> <span class="glyph" title="">⟼</span> </td> </tr>
     *       <tr id="entity-xnis"><td> <code title="">xnis;</code> </td> <td> U+022FB </td> <td> <span class="glyph" title="">⋻</span> </td> </tr>
     *       <tr id="entity-xodot"><td> <code title="">xodot;</code> </td> <td> U+02A00 </td> <td> <span class="glyph" title="">⨀</span> </td> </tr>
     *       <tr id="entity-Xopf"><td> <code title="">Xopf;</code> </td> <td> U+1D54F </td> <td> <span class="glyph" title="">𝕏</span> </td> </tr>
     *       <tr id="entity-xopf"><td> <code title="">xopf;</code> </td> <td> U+1D569 </td> <td> <span class="glyph" title="">𝕩</span> </td> </tr>
     *       <tr id="entity-xoplus"><td> <code title="">xoplus;</code> </td> <td> U+02A01 </td> <td> <span class="glyph" title="">⨁</span> </td> </tr>
     *       <tr id="entity-xotime"><td> <code title="">xotime;</code> </td> <td> U+02A02 </td> <td> <span class="glyph" title="">⨂</span> </td> </tr>
     *       <tr id="entity-xrArr"><td> <code title="">xrArr;</code> </td> <td> U+027F9 </td> <td> <span class="glyph" title="">⟹</span> </td> </tr>
     *       <tr id="entity-xrarr"><td> <code title="">xrarr;</code> </td> <td> U+027F6 </td> <td> <span class="glyph" title="">⟶</span> </td> </tr>
     *       <tr id="entity-Xscr"><td> <code title="">Xscr;</code> </td> <td> U+1D4B3 </td> <td> <span class="glyph" title="">𝒳</span> </td> </tr>
     *       <tr id="entity-xscr"><td> <code title="">xscr;</code> </td> <td> U+1D4CD </td> <td> <span class="glyph" title="">𝓍</span> </td> </tr>
     *       <tr id="entity-xsqcup"><td> <code title="">xsqcup;</code> </td> <td> U+02A06 </td> <td> <span class="glyph" title="">⨆</span> </td> </tr>
     *       <tr id="entity-xuplus"><td> <code title="">xuplus;</code> </td> <td> U+02A04 </td> <td> <span class="glyph" title="">⨄</span> </td> </tr>
     *       <tr id="entity-xutri"><td> <code title="">xutri;</code> </td> <td> U+025B3 </td> <td> <span class="glyph" title="">△</span> </td> </tr>
     *       <tr id="entity-xvee"><td> <code title="">xvee;</code> </td> <td> U+022C1 </td> <td> <span class="glyph" title="">⋁</span> </td> </tr>
     *       <tr id="entity-xwedge"><td> <code title="">xwedge;</code> </td> <td> U+022C0 </td> <td> <span class="glyph" title="">⋀</span> </td> </tr>
     *       <tr id="entity-Yacute"><td> <code title="">Yacute;</code> </td> <td> U+000DD </td> <td> <span class="glyph" title="">Ý</span> </td> </tr>
     *       <tr class="impl" id="entity-Yacute-legacy"><td> <code title="">Yacute</code> </td> <td> U+000DD </td> <td> <span title="">Ý</span> </td> </tr>
     *       <tr id="entity-yacute"><td> <code title="">yacute;</code> </td> <td> U+000FD </td> <td> <span class="glyph" title="">ý</span> </td> </tr>
     *       <tr class="impl" id="entity-yacute-legacy"><td> <code title="">yacute</code> </td> <td> U+000FD </td> <td> <span title="">ý</span> </td> </tr>
     *       <tr id="entity-YAcy"><td> <code title="">YAcy;</code> </td> <td> U+0042F </td> <td> <span class="glyph" title="">Я</span> </td> </tr>
     *       <tr id="entity-yacy"><td> <code title="">yacy;</code> </td> <td> U+0044F </td> <td> <span class="glyph" title="">я</span> </td> </tr>
     *       <tr id="entity-Ycirc"><td> <code title="">Ycirc;</code> </td> <td> U+00176 </td> <td> <span class="glyph" title="">Ŷ</span> </td> </tr>
     *       <tr id="entity-ycirc"><td> <code title="">ycirc;</code> </td> <td> U+00177 </td> <td> <span class="glyph" title="">ŷ</span> </td> </tr>
     *       <tr id="entity-Ycy"><td> <code title="">Ycy;</code> </td> <td> U+0042B </td> <td> <span class="glyph" title="">Ы</span> </td> </tr>
     *       <tr id="entity-ycy"><td> <code title="">ycy;</code> </td> <td> U+0044B </td> <td> <span class="glyph" title="">ы</span> </td> </tr>
     *       <tr id="entity-yen"><td> <code title="">yen;</code> </td> <td> U+000A5 </td> <td> <span class="glyph" title="">¥</span> </td> </tr>
     *       <tr class="impl" id="entity-yen-legacy"><td> <code title="">yen</code> </td> <td> U+000A5 </td> <td> <span title="">¥</span> </td> </tr>
     *       <tr id="entity-Yfr"><td> <code title="">Yfr;</code> </td> <td> U+1D51C </td> <td> <span class="glyph" title="">𝔜</span> </td> </tr>
     *       <tr id="entity-yfr"><td> <code title="">yfr;</code> </td> <td> U+1D536 </td> <td> <span class="glyph" title="">𝔶</span> </td> </tr>
     *       <tr id="entity-YIcy"><td> <code title="">YIcy;</code> </td> <td> U+00407 </td> <td> <span class="glyph" title="">Ї</span> </td> </tr>
     *       <tr id="entity-yicy"><td> <code title="">yicy;</code> </td> <td> U+00457 </td> <td> <span class="glyph" title="">ї</span> </td> </tr>
     *       <tr id="entity-Yopf"><td> <code title="">Yopf;</code> </td> <td> U+1D550 </td> <td> <span class="glyph" title="">𝕐</span> </td> </tr>
     *       <tr id="entity-yopf"><td> <code title="">yopf;</code> </td> <td> U+1D56A </td> <td> <span class="glyph" title="">𝕪</span> </td> </tr>
     *       <tr id="entity-Yscr"><td> <code title="">Yscr;</code> </td> <td> U+1D4B4 </td> <td> <span class="glyph" title="">𝒴</span> </td> </tr>
     *       <tr id="entity-yscr"><td> <code title="">yscr;</code> </td> <td> U+1D4CE </td> <td> <span class="glyph" title="">𝓎</span> </td> </tr>
     *       <tr id="entity-YUcy"><td> <code title="">YUcy;</code> </td> <td> U+0042E </td> <td> <span class="glyph" title="">Ю</span> </td> </tr>
     *       <tr id="entity-yucy"><td> <code title="">yucy;</code> </td> <td> U+0044E </td> <td> <span class="glyph" title="">ю</span> </td> </tr>
     *       <tr id="entity-Yuml"><td> <code title="">Yuml;</code> </td> <td> U+00178 </td> <td> <span class="glyph" title="">Ÿ</span> </td> </tr>
     *       <tr id="entity-yuml"><td> <code title="">yuml;</code> </td> <td> U+000FF </td> <td> <span class="glyph" title="">ÿ</span> </td> </tr>
     *       <tr class="impl" id="entity-yuml-legacy"><td> <code title="">yuml</code> </td> <td> U+000FF </td> <td> <span title="">ÿ</span> </td> </tr>
     *       <tr id="entity-Zacute"><td> <code title="">Zacute;</code> </td> <td> U+00179 </td> <td> <span class="glyph" title="">Ź</span> </td> </tr>
     *       <tr id="entity-zacute"><td> <code title="">zacute;</code> </td> <td> U+0017A </td> <td> <span class="glyph" title="">ź</span> </td> </tr>
     *       <tr id="entity-Zcaron"><td> <code title="">Zcaron;</code> </td> <td> U+0017D </td> <td> <span class="glyph" title="">Ž</span> </td> </tr>
     *       <tr id="entity-zcaron"><td> <code title="">zcaron;</code> </td> <td> U+0017E </td> <td> <span class="glyph" title="">ž</span> </td> </tr>
     *       <tr id="entity-Zcy"><td> <code title="">Zcy;</code> </td> <td> U+00417 </td> <td> <span class="glyph" title="">З</span> </td> </tr>
     *       <tr id="entity-zcy"><td> <code title="">zcy;</code> </td> <td> U+00437 </td> <td> <span class="glyph" title="">з</span> </td> </tr>
     *       <tr id="entity-Zdot"><td> <code title="">Zdot;</code> </td> <td> U+0017B </td> <td> <span class="glyph" title="">Ż</span> </td> </tr>
     *       <tr id="entity-zdot"><td> <code title="">zdot;</code> </td> <td> U+0017C </td> <td> <span class="glyph" title="">ż</span> </td> </tr>
     *       <tr id="entity-zeetrf"><td> <code title="">zeetrf;</code> </td> <td> U+02128 </td> <td> <span class="glyph" title="">ℨ</span> </td> </tr>
     *       <tr id="entity-ZeroWidthSpace"><td> <code title="">ZeroWidthSpace;</code> </td> <td> U+0200B </td> <td> <span class="glyph" title="">​</span> </td> </tr>
     *       <tr id="entity-Zeta"><td> <code title="">Zeta;</code> </td> <td> U+00396 </td> <td> <span class="glyph" title="">Ζ</span> </td> </tr>
     *       <tr id="entity-zeta"><td> <code title="">zeta;</code> </td> <td> U+003B6 </td> <td> <span class="glyph" title="">ζ</span> </td> </tr>
     *       <tr id="entity-Zfr"><td> <code title="">Zfr;</code> </td> <td> U+02128 </td> <td> <span class="glyph" title="">ℨ</span> </td> </tr>
     *       <tr id="entity-zfr"><td> <code title="">zfr;</code> </td> <td> U+1D537 </td> <td> <span class="glyph" title="">𝔷</span> </td> </tr>
     *       <tr id="entity-ZHcy"><td> <code title="">ZHcy;</code> </td> <td> U+00416 </td> <td> <span class="glyph" title="">Ж</span> </td> </tr>
     *       <tr id="entity-zhcy"><td> <code title="">zhcy;</code> </td> <td> U+00436 </td> <td> <span class="glyph" title="">ж</span> </td> </tr>
     *       <tr id="entity-zigrarr"><td> <code title="">zigrarr;</code> </td> <td> U+021DD </td> <td> <span class="glyph" title="">⇝</span> </td> </tr>
     *       <tr id="entity-Zopf"><td> <code title="">Zopf;</code> </td> <td> U+02124 </td> <td> <span class="glyph" title="">ℤ</span> </td> </tr>
     *       <tr id="entity-zopf"><td> <code title="">zopf;</code> </td> <td> U+1D56B </td> <td> <span class="glyph" title="">𝕫</span> </td> </tr>
     *       <tr id="entity-Zscr"><td> <code title="">Zscr;</code> </td> <td> U+1D4B5 </td> <td> <span class="glyph" title="">𝒵</span> </td> </tr>
     *       <tr id="entity-zscr"><td> <code title="">zscr;</code> </td> <td> U+1D4CF </td> <td> <span class="glyph" title="">𝓏</span> </td> </tr>
     *       <tr id="entity-zwj"><td> <code title="">zwj;</code> </td> <td> U+0200D </td> <td> <span class="glyph" title="">‍</span> </td> </tr>
     *       <tr id="entity-zwnj"><td> <code title="">zwnj;</code> </td> <td> U+0200C </td> <td> <span class="glyph" title="">‌</span> </td> </tr>
     *     </tbody>
     *   </table>
     * </div>
     * <p>This data is also available <a href="entities.json">as a JSON file</a>.</p>
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
