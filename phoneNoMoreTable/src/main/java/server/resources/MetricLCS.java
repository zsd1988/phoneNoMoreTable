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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Thibault Debatty
 */
public class MetricLCS {

        public static void main(String[] args) {
        
        server.info.debatty.java.stringsimilarity.MetricLCS lcs = 
                new server.info.debatty.java.stringsimilarity.MetricLCS();
        
        String s1 = "ABCDEFG";   
        String s2 = "ABCDEFHJKL";
        // LCS: ABCDEF => length = 6
        // longest = s2 => length = 10
        // => 1 - 6/10 = 0.4
        System.out.println(lcs.distance(s1, s2));
        
        // LCS: ABDF => length = 4
        // longest = ABDEF => length = 5
        // => 1 - 4 / 5 = 0.2
        System.out.println(lcs.distance("ABDEF", "ABDIF"));
        try {
			System.out.println(readResourceFile("resources/cus_yes.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
      private static String readResourceFile(String file) throws IOException {
        
            InputStream stream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(file);
        
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder string_builder = new StringBuilder();
            String ls = System.getProperty("line.separator");
            String line = null;
        
            while (( line = reader.readLine() ) != null ) {
                string_builder.append(line);
                string_builder.append(ls);
            }
        
            string_builder.deleteCharAt(string_builder.length() - 1);
            return string_builder.toString();
        }
}