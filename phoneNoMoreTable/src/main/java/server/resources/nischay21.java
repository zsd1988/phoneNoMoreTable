/*
 * The MIT License
 *
 * Copyright 2017 Thibault Debatty.
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
import server.info.debatty.java.stringsimilarity.Damerau;
import server.info.debatty.java.stringsimilarity.Jaccard;
import server.info.debatty.java.stringsimilarity.JaroWinkler;
import server.info.debatty.java.stringsimilarity.Levenshtein;
import server.info.debatty.java.stringsimilarity.NGram;
import server.info.debatty.java.stringsimilarity.SorensenDice;
import server.info.debatty.java.stringsimilarity.interfaces.StringDistance;
import java.util.LinkedList;

/**
 *
 * @author Thibault Debatty
 */
public class nischay21 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String s1 = "你好，打扰一下，这边是擎谱集团G58的，想问下您近期有想法买商铺吗？";
        String s2 = "喂，你好，我这边是擎谱集团G58的，你有考虑买商铺吗？";
        String s3 = "Milwaukee Video Borescope, Rotating Inspection Scope, Series: M-SPECTOR 360, 2.7 in 640 x 480 pixels High-Resolution LCD, Plastic, Black/Red";

        LinkedList<StringDistance> algos = new LinkedList<StringDistance>();
        algos.add(new JaroWinkler());
        algos.add(new Levenshtein());
        algos.add(new NGram());
        algos.add(new Damerau());
        algos.add(new Jaccard());
        algos.add(new SorensenDice());
        algos.add(new Cosine());


        System.out.println("S1 vs S2");
        for (StringDistance algo : algos) {
            System.out.print(algo.getClass().getSimpleName() + " : ");
            System.out.println(algo.distance(s1, s2));
        }
        System.out.println();

        System.out.println("S1 vs S3");
        for (StringDistance algo : algos) {
            System.out.print(algo.getClass().getSimpleName() + " : ");
            System.out.println(algo.distance(s1, s3));
        }
        System.out.println();

        System.out.println("With .toLower()");
        System.out.println("S1 vs S2");
        for (StringDistance algo : algos) {
            System.out.print(algo.getClass().getSimpleName() + " : ");
            System.out.println(algo.distance(s1.toLowerCase(), s2.toLowerCase()));
        }
        System.out.println();

        System.out.println("S1 vs S3");
        for (StringDistance algo : algos) {
            System.out.print(algo.getClass().getSimpleName() + " : ");
            System.out.println(algo.distance(s1.toLowerCase(), s3.toLowerCase()));
        }
        System.out.println();

    }

}
