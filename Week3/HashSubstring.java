/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Week3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Abdo
 */
class PolynomialHash {

    private int prime = 1000000007;
    private int x = 263;

    public PolynomialHash() {

    }

    public long getPolynomailHash(String pattern) {
        long hash = 0;
        for (int i = pattern.length() - 1; i >= 0; i--) {
            hash = ((hash * x + pattern.charAt(i)) % prime);
        }
        return hash;
    }

    // find p in t
    public ArrayList<Integer> find(String text, String pattern) {
        ArrayList<Integer> returnedIndices = new ArrayList<>();
        long hash = getPolynomailHash(pattern);
        int tLength = text.length();
        int pLength = pattern.length();
        long[] tableHashing = new long[tLength - pLength + 1];
        long h = 1;
        for (int i = 0; i < pLength; i++) {
            h = (h * x) % prime;
        }
        int startIndexLastPart = tLength - pLength;
        String lastPart = text.substring(startIndexLastPart);
        tableHashing[startIndexLastPart] = getPolynomailHash(lastPart);
        if (tableHashing[startIndexLastPart] == hash && isEqual(pattern, text, startIndexLastPart,
                startIndexLastPart + pLength)) {
            returnedIndices.add(startIndexLastPart);
        }
        for (int i = tLength - pLength - 1; i >= 0; i--) {
            tableHashing[i] = ((text.charAt(i) + x * tableHashing[i + 1] 
                    - text.charAt(i + pLength) * h) % prime+prime)%prime;
            if (tableHashing[i] == hash) {
                if (isEqual(pattern,text, i, i + pLength)) {
                    returnedIndices.add(i);
                }
            }
        }
        Collections.reverse(returnedIndices);
        return returnedIndices;
    }

    //check target is same string on text from startIndex to endIndex
    private boolean isEqual(String target, String t, int startIndex, int endIndex) {
        if (endIndex - startIndex  != target.length()) {
            return false;
        }
        int j=0;
        for (int i = startIndex; i < endIndex; i++) {
            if (t.charAt(i) != target.charAt(j++)) {
                return false;
            }
        }
        return true;
    }
}

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        PolynomialHash polynomialHash = new PolynomialHash();
        return polynomialHash.find(input.text, input.pattern);
    }

    static class Data {

        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
        }
    }

    static class FastScanner {

        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
