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
public class tree_orders {

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

    public class TreeOrders {

        int n;
        int[] key, left, right;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            key = new int[n];
            left = new int[n];
            right = new int[n];
            for (int i = 0; i < n; i++) {
                key[i] = in.nextInt();
                left[i] = in.nextInt();
                right[i] = in.nextInt();
            }
        }

        List<Integer> inOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
            // Finish the implementation
            // You may need to add a new recursive method to do that
            inOrderUtils(0,result);
            return result;
        }

        List<Integer> preOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
            preOrderUtils(0, result);
            return result;
        }

        List<Integer> postOrder() {
            ArrayList<Integer> result = new ArrayList<Integer>();
            postOrderUtils(0, result);
            return result;
        }

        public void inOrderUtils(int root,ArrayList<Integer>res) {
            if (root == -1) {
                return;
            }
            inOrderUtils(left[root],res);
            res.add(key[root]);
            inOrderUtils(right[root],res);
        }
         public void preOrderUtils(int root,ArrayList<Integer>res) {
            if (root == -1) {
                return;
            }
            res.add(key[root]);
            preOrderUtils(left[root],res);
            preOrderUtils(right[root],res);
        }
          public void postOrderUtils(int root,ArrayList<Integer>res) {
            if (root == -1) {
                return;
            }
            postOrderUtils(left[root],res);
            postOrderUtils(right[root],res);
            res.add(key[root]);
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_orders().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void print(List<Integer> x) {
        for (Integer a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        TreeOrders tree = new TreeOrders();
        tree.read();
        print(tree.inOrder());
        print(tree.preOrder());
        print(tree.postOrder());
    }
}
