/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Week1;
import java.util.*;
import java.io.*;

/**
 *
 * @author Abdo
 */
public class tree_height {

    class Node
    {
        int data;
        ArrayList<Node>children;
     public Node()
     {
         children=new ArrayList<>();
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

    public class TreeHeight {

        int n;
        int parent[];

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = in.nextInt();
            }
        }

        int computeHeight() {
            Node root=null;
            Node[]nodes=new Node[n];
            for (int i = 0; i < n; i++) {
                nodes[i]=new Node();
                nodes[i].data=i;
            }
            for (int childIndex = 0; childIndex < n; childIndex++) {
                if(parent[childIndex]==-1)
                    root=nodes[childIndex];
                else 
                    nodes[parent[childIndex]].children.add(nodes[childIndex]);
            }
            int maxHeight = getHightRecursion(root);
           
            return maxHeight;
        }
    }
    int getHightRecursion(Node root)
    {
        int height=Integer.MIN_VALUE;
                
        if(root.children==null ||root.children.size()==0)
            return 1;
        int childrenSize=root.children.size();
        for (int i = 0; i < childrenSize ; i++) {
            height= Math.max(height, getHightRecursion(root.children.get(i))+1);
        }  
        return height;
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_height().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}
