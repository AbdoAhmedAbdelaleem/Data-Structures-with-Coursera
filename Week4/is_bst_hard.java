/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Week4;

import java.util.*;
import java.io.*;

/**
 *
 * @author Abdo
 */
public class is_bst_hard {

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

    public class IsBST {

        class Node {

            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        private boolean solve() {
            return isBinarySearchTree();//To change body of generated methods, choose Tools | Templates.
        }

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
            // Implement correct algorithm here
            if (tree.length == 0) {
                return true;
            }
            return isBSTUtils(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        boolean isBSTUtils(int rootIndex, int minBound, int maxBound) {
            if (rootIndex == -1) {
                return true;
            }
            if (!isBSTUtils(tree[rootIndex].left, minBound, tree[rootIndex].key)) {
                return false;
            }
            if (!((tree[rootIndex].left == -1 || tree[tree[rootIndex].left].key < tree[rootIndex].key)
                    && (tree[rootIndex].right == -1 || tree[tree[rootIndex].right].key >= tree[rootIndex].key)
                    && tree[rootIndex].key >= minBound && tree[rootIndex].key <= maxBound)) {
                return false;
            }
            if (!isBSTUtils(tree[rootIndex].right, tree[rootIndex].key, maxBound)) {
                return false;
            }
            return true;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst_hard().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.solve()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
