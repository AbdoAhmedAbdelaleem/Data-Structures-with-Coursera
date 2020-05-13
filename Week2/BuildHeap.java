/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week2;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Abdo
 */
public class BuildHeap {

    private int[] data;
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
            data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
            out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateSwapsEnhanced() {
        swaps = new ArrayList<Swap>();
        int size = data.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }       
    }

    private void siftDown(int i) {
        int leftChildIndex = 2 * i + 1;
        int righChildIndex = 2 * i + 2;
        int size=data.length;
        int m;
        if(leftChildIndex>=size &&righChildIndex>=size)
            return;
        else if(leftChildIndex>=size)
            m=righChildIndex;
        else if(righChildIndex>=size)
            m=leftChildIndex;
        else
        m=data[leftChildIndex]<data[righChildIndex]?leftChildIndex:righChildIndex;
        if(data[i]>data[m])
        {
             swaps.add(new Swap(i, m));
             swap(i, m);
             i=m;
             siftDown(i);
        }
    }

    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    private void generateSwaps() {
        swaps = new ArrayList<Swap>();
        // The following naive implementation just sorts 
        // the given sequence using selection sort algorithm
        // and saves the resulting sequence of swaps.
        // This turns the given array into a heap, 
        // but in the worst case gives a quadratic number of swaps.
        //
        // TODO: replace by a more efficient implementation
        for (int i = 0; i < data.length; ++i) {
            for (int j = i + 1; j < data.length; ++j) {
                if (data[i] > data[j]) {
                    swaps.add(new Swap(i, j));
                    int tmp = data[i];
                    data[i] = data[j];
                    data[j] = tmp;
                }
            }
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwapsEnhanced();
        writeResponse();
        out.close();
    }

    static class Swap {

        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
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
