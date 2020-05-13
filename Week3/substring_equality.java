/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week3;

import java.util.*;
import java.io.*;

/**
 *
 * @author Abdo
 */
class PolynomialHash2 {

    private int prime1 = 1000000007;
    private int prime2 = 1000000009;
    private int x;
    String str;

    long[] hashTable1;
    long[] hashTable2;
    long[] multipls1;
    long[] multipls2;

    public PolynomialHash2(String s) {
        Random rnd = new Random();
        x = rnd.nextInt(1000000000) + 1;
        str = s;
        preComputeHash();
    }

    private void preComputeHash() {
        int n = str.length();
        hashTable1 = new long[n + 1];
        hashTable2 = new long[n + 1];
        hashTable1[0] = 0;
        hashTable2[0] = 0;
        multipls1 = new long[n + 1];
        multipls1[0] = 1;
        multipls2 = new long[n + 1];
        multipls2[0] = 1;
        for (int i = 1; i <= n; i++) {
            multipls1[i] = (((multipls1[i - 1] * x) % prime1));
            multipls2[i] = (((multipls2[i - 1] * x) % prime2));

            hashTable1[i] = (((x * hashTable1[i - 1] + str.charAt(i - 1)) % prime1));
            hashTable2[i] = (((x * hashTable2[i - 1] + str.charAt(i - 1)) % prime2));
        }
    }    

    private long getHash(int a, int l, long[] hTable, long prime, long[] multiples) {
        long y = multiples[l];
            return ((hTable[a + l] - y * hTable[a] % prime) + prime) % prime;        
    }

    boolean isSubstrEqual(int s1, int s2, int count) {
        return getHash(s1, count, hashTable1, prime1, multipls1) == getHash(s2, count, hashTable1, prime1, multipls1)
        && getHash(s1, count, hashTable2, prime2, multipls2) == getHash(s2, count, hashTable2, prime2, multipls2);
    }

}

public class substring_equality {

    private PolynomialHash2 PolynomialHash;

    public class Solver {

        private String s;

        public Solver(String s) {
            this.s = s;
        }

        public boolean ask(int a, int b, int l) {
            for (int i = 0; i < l; i++) {
                if (s.charAt(a + i) != s.charAt(b + i)) {
                    return false;
                }
            }
            return true;
        }
    }

    public void run() throws IOException {


        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        String s = in.next();
        PolynomialHash = new PolynomialHash2(s);
        int q = in.nextInt();
        Solver solver = new Solver(s);
        for (int i = 0; i < q; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int l = in.nextInt();
            out.println(PolynomialHash.isSubstrEqual(a, b, l) ? "Yes" : "No");
        }
        out.close();
    }

    static public void main(String[] args) throws IOException {
        new substring_equality().run();
    }

    class FastScanner {

        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements()) {
                tok = new StringTokenizer(in.readLine());
            }
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
