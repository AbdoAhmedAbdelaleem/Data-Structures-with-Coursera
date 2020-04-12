/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Week1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author Abdo
 */
public class max_sliding_window {

    private static void max_sliding_window_enhanced(int[] arr, int k) {
        int n = arr.length;
        LinkedList<Integer> q = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (q.size() > 0 && arr[i] > arr[q.peekLast()]) {
                q.removeLast();
            }
            q.add(i);
        }
        int j = 0;
        for (int i = k - 1; i < n - 1; i++) {
            System.out.print(arr[q.peek()] + " ");
            while (q.size() > 0 && q.peek() <= i + 1 - k) {
                q.removeFirst();
            }
            while (q.size() > 0 && arr[i + 1] > arr[q.peekLast()]) {
                q.removeLast();
            }
            q.add(i + 1);
        }
        if (q.size() > 0) {
            System.out.print(arr[q.peek()]);
        }
        System.out.println("");
    }

    public void read() throws IOException {
        FastScanner in = new FastScanner();
        n = in.nextInt();
        sequence = new int[n];
        for (int i = 0; i < n; i++) {
            sequence[i] = in.nextInt();
        }
        k = in.nextInt();
    }

    int n;
    int sequence[];
    int k;

    public static void main(String[] args)throws IOException{
        max_sliding_window obj = new max_sliding_window();
        obj.read();
       /* Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.next());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(in.next());
        }
        int k = Integer.parseInt(in.next())*/;
        max_sliding_window_enhanced(obj.sequence,obj.k);
    }
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
