/*
 * The MIT License
 *
 * Copyright 2015 Thibault Debatty.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package server.resources;

import server.info.debatty.java.stringsimilarity.Cosine;
import server.info.debatty.java.stringsimilarity.Jaccard;
import server.info.debatty.java.stringsimilarity.JaroWinkler;
import server.info.debatty.java.stringsimilarity.NGram;
import server.info.debatty.java.stringsimilarity.NormalizedLevenshtein;
import server.info.debatty.java.stringsimilarity.SorensenDice;

/**
 *
 * @author Thibault Debatty
 */
public class Examples {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Jaccard index
        // =============
        System.out.println("\nJaccard");
        Jaccard j2 = new Jaccard(2);
        // AB BC CD DE DF
        // 1  1  1  1  0
        // 1  1  1  0  1
        // => 3 / 5 = 0.6
        System.out.println(j2.similarity("ABCDE", "ABCDF"));

        // Jaro-Winkler
        // ============
        System.out.println("\nJaro-Winkler");
        JaroWinkler jw = new JaroWinkler();

        // substitution of s and t : 0.9740740656852722
        System.out.println(jw.similarity("My string", "My string"));

        // substitution of s and n : 0.8962963223457336
        System.out.println(jw.similarity("My string", "My ntrisg"));

        // Cosine
        // ======
        System.out.println("\nCosine");
        Cosine cos = new Cosine(3);

        // ABC BCE
        // 1  0
        // 1  1
        // angle = 45°
        // => similarity = .71
        System.out.println(cos.similarity("ABC", "ABCE"));

        cos = new Cosine(2);
        // AB BA
        // 2  1
        // 1  1
        // similarity = .95
        System.out.println(cos.similarity("ABAB", "BAB"));

        // NGram *******************
        // =====
        // produces 0.416666
        System.out.println("\nNGrammmmmm");
        NGram twogram = new NGram(2);
        System.out.println(1-twogram.distance("ABCD", "ABTUIO"));

        // produces 0.97222
        String s1 = "Adobe CreativeSuite 5 Master Collection from cheap 4zp";
        String s2 = "Adobe CreativeSuite 5 Master Collection from cheap d1x";
        NGram ngram = new NGram(4);
        System.out.println(1-ngram.distance(s1, s2));

        // Normalized Levenshtein
        // ======================
        System.out.println("\nNormalized Levenshtein");
        NormalizedLevenshtein l = new NormalizedLevenshtein();

        System.out.println(l.distance("My string", "My $tring"));
        System.out.println(l.distance("My string", "M string2"));
        System.out.println(l.distance("My string", "abcd"));

        // Sorensen-Dice
        // =============
        System.out.println("\nSorensen-Dice");
        SorensenDice sd = new SorensenDice(2);

        // AB BC CD DE DF FG
        // 1  1  1  1  0  0
        // 1  1  1  0  1  1
        // => 2 x 3 / (4 + 5) = 6/9 = 0.6666
        System.out.println(sd.similarity("ABCDFG", "ABCDFG"));
    }

}
