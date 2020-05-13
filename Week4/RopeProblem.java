/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Week4;

//import Week4.SplayTree.Vertex;
import java.io.*;
import java.util.*;

/**
 *
 * @author Abdo
 */
    class Vertex {

        char key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        long sum;
        long size;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(char key, long sum, Vertex left, Vertex right, Vertex parent, long size) {
            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.size = size;
        }
    }
class SplayTree {

    // Splay tree implementation
    // Vertex of a splay tree

    void update(Vertex v) {
        if (v == null) {
            return;
        }
        v.sum = v.key + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
        v.size = 1 + (v.left != null ? v.left.size : 0) + (v.right != null ? v.right.size : 0);
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    void smallRotation(Vertex v) {
        Vertex parent = v.parent;
        if (parent == null) {
            return;
        }
        Vertex grandparent = v.parent.parent;

        if (parent.left == v) {
            Vertex m = v.right;
            v.right = parent;
            parent.left = m;
//ADDITION 1:            
            parent.parent = v;
            if (m != null) {
                parent.left.parent = parent;
            }

        } else {
            Vertex m = v.left;			//If this curr Node is right child of its PARENT
            v.left = parent;			//Right(Anti-clockwise) Rotation
            parent.right = m;
//ADDITION 2         
            parent.parent = v;
            if (m != null) {
                parent.right.parent = parent;
            }
        }

        update(parent);					//Updating information
        update(v);

        v.parent = grandparent;			//Updating the grandparent

        if (grandparent != null) {
            if (grandparent.left == parent) {
                grandparent.left = v;
            } else {
                grandparent.right = v;
            }
        } //ADDITION 3        //My addition to this:
        else if (grandparent == null) {
            root = v;
        }
    }

    void bigRotation(Vertex v) {
        if (v.parent.left == v && v.parent.parent.left == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else if (v.parent.right == v && v.parent.parent.right == v.parent) {
            // Zig-zig
            smallRotation(v.parent);
            smallRotation(v);
        } else {
            // Zig-zag
            smallRotation(v);
            smallRotation(v);
        }
    }

    // Makes splay of the given vertex and returns the new root.
    Vertex splay(Vertex v) {
        if (v == null) {
            return null;
        }
        while (v.parent != null) {
            if (v.parent.parent == null) {
                smallRotation(v);
                break;
            }
            bigRotation(v);
        }
        return v;
    }

    class VertexPair {

        Vertex left;
        Vertex right;

        VertexPair() {
        }

        VertexPair(Vertex left, Vertex right) {
            this.left = left;
            this.right = right;
        }
    }

    // Searches for the given key in the tree with the given root
    // and calls splay for the deepest visited node after that.
    // Returns pair of the result and the new root.
    // If found, result is a pointer to the node with the given key.
    // Otherwise, result is a pointer to the node with the smallest
    // bigger key (next value in the order).
    // If the key is bigger than all keys in the tree,
    // then result is null.
    VertexPair find(Vertex root, int key) {
        Vertex v = root;
        Vertex last = root;
        Vertex next = null;
        while (v != null) {
            if (v.key >= key && (next == null || v.key < next.key)) {
                next = v;
            }
            last = v;
            if (v.key == key) {
                break;
            }
            if (v.key < key) {
                v = v.right;
            } else {
                v = v.left;
            }
        }
        root = splay(last);
        return new VertexPair(next, root);
    }

    Vertex[] SplitNew(Vertex root, int key) {
        if (key < 1) {
            return new Vertex[]{null, root};
        } else if (key > root.size) {
            return new Vertex[]{root, null};
        }
        Vertex v = OrderStatistic(root, key);
        v = splay(v);
        Vertex left = v.left;
        v.left = null;
        if (left != null) {
            left.parent = null;
        }
        update(left);
        update(v);
        return new Vertex[]{left, v};

    }

    public Vertex move(Vertex root, int from, int to, int k) {
        Vertex[] pair = SplitNew(root, from);
        Vertex left = pair[0];
        Vertex mid = pair[1];
        pair = SplitNew(mid, to - from + 2);
        Vertex right = pair[1];
        mid = pair[0];
        long leftSize = left != null ? left.size : 0;
        Vertex merge = null;
        if (k < leftSize) {
            pair = SplitNew(left, k + 1);
            Vertex left1 = pair[0];
            Vertex left2 = pair[1];
            merge = merge(merge(merge(left1, mid), left2), right);
        } else if (k == leftSize) {
            merge = merge(merge(left, mid), right);
        } else // k>leftsize
        {
            pair = SplitNew(right, k - (int) leftSize + 1);
            Vertex right1 = pair[0];
            Vertex right2 = pair[1];
            merge = merge(merge(merge(left, right1), mid), right2);
        }
        return merge;
    }

    VertexPair split(Vertex root, int key) {
        VertexPair result = new VertexPair();
        VertexPair findAndRoot = find(root, key);
        root = findAndRoot.right;
        result.right = findAndRoot.left;
        if (result.right == null) {
            result.left = root;
            return result;
        }
        result.right = splay(result.right);
        result.left = result.right.left;
        result.right.left = null;
        if (result.left != null) {
            result.left.parent = null;
        }
        update(result.left);
        update(result.right);
        return result;
    }

    Vertex merge(Vertex left, Vertex right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        while (right.left != null) {
            right = right.left;
        }
        right = splay(right);
        right.left = left;
        update(right);
        return right;
    }

    // Code that uses splay tree to solve the problem
    Vertex root = null;

    void insert(char x) {
        Vertex left = null;
        Vertex right = null;
        Vertex new_vertex = null;
        VertexPair leftRight = split(root, x);
        left = leftRight.left;
        right = leftRight.right;
        if (right == null || right.key != x) {
            new_vertex = new Vertex(x, 0, null, null, null,1);
        }
        root = merge(merge(left, new_vertex), right);
    }

    void eraseOld(int x) {
        // TODO: Implement erase yourself
        //out.println("del:"+x);
        VertexPair pair = find(root, x);
        root = pair.right;
        /**
         * BZ: RETURN THE NEW ROOT OF SUBTREE
         */
        root = delete(x, root);
        /**
         * BZ: MUST UPDATE PARENT LINKS AFTER DELETION
         */
        if (root != null) {
            root.parent = null;
        }
    }

    private Vertex delete(int x, Vertex root) {
        if (root == null) {
            return root;
        }
        if (x < root.key) {
            root.left = delete(x, root.left);
        } else if (x > root.key) /**
         * BZ: DELETE STH DOES NOT EXIST?
         */
        {
            root.right = delete(x, root.right);
        } /**
         * BZ: ELSE IF OR IF?
         */
        else if (root.left == null) {
            return root.right;
        } else if (root.right == null) {
            return root.left;
        } else {
            // Two children case.
            // Find Next() of R in R.Right.
            // Replace R's key by its Next;
            // Promote Next's right subtree to Next.
            // out.println(root.key);
            Vertex next = next(root);
            assert (next.left == null);
            root.key = next.key;
            promote(next.right, next);
        }
        return root;
    }

    private void promote(Vertex child, Vertex node) {
        /**
         * Promote child to replace node
         */
        assert (node.parent != null);   // Assert that Parent must exist
        // BZ: node may have null child
        if (child != null) {
            child.parent = node.parent;
        }
        if (node == node.parent.left) {
            node.parent.left = child;
        } else {
            node.parent.right = child;
        }
        update(node.parent);
        node.parent = null;
    }

    private Vertex next(Vertex node) {
        /**
         * Return the next larger node
         */
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        Vertex parent = node.parent;
        while (parent != null && parent.key <= node.key) {
            parent = parent.parent;
        }
        return parent;
    }

    void erase(int x) {
        if (root == null) {
            return;
        }
        if (!find(x)) {
            return;
        }
        root = merge(root.left, root.right);
        if (root != null) {
            root.parent = null;
        }
    }

    boolean findOld(int x) {
        if (root == null) {
            return false;
        }
        Vertex node = root;
        while (node != null) {
            if (node.key == x) {
                return true;
            } else if (x < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        root = splay(node);
        return false;
    }

    boolean find(int x) {
        VertexPair pair = find(root, x);
        if (pair.right == null || pair.right.key != x) {
            return false;
        }
        return true;
    }

    long sum(int from, int to) {
        VertexPair leftMiddle = split(root, from);
        Vertex left = leftMiddle.left;
        Vertex middle = leftMiddle.right;
        VertexPair middleRight = split(middle, to + 1);
        middle = middleRight.left;
        Vertex right = middleRight.right;
        long ans = 0;
        if (middle == null) {
            ans = 0;
            root = merge(left, right);
        } else {
            ans = middle.sum;
            root = merge(merge(left, middle), right);

        }
        return ans;
    }

    public long getSize(Vertex n) {
        return n == null ? 0 : n.size;
    }

    public Vertex OrderStatistic(Vertex n, long k) {
        if (n == null) {
            return null;
        }
        long s = getSize(n.left);
        if (k == s + 1) {
            return n;
        }
        if (k < s + 1) {
            return OrderStatistic(n.left, k);
        } else {
            return OrderStatistic(n.right, k - s - 1);
        }
    }

    public void inOrder(Vertex v) {
        if (v == null) {
            return;
        }
        inOrder(v.left);
        System.out.print(v.key);
        inOrder(v.right);

    }

    public void inOrder(Vertex v, StringBuffer buffer) {
        if (v == null) {
            return;
        }
        inOrder(v.left,buffer);
        buffer.append(v.key);
        inOrder(v.right,buffer);

    }

    public Vertex listToTree(String itemes, int from, int to, Vertex root, Vertex parent) {

        int mid = (from + to) / 2;
        Vertex n = new Vertex(itemes.charAt(mid), 0, null, null, parent, 1);
        if (root == null) {
            root = n;
        }
        if (from == to) {
            return n;
        }
        if (from > to) {
            return null;
        }
        root.left = listToTree(itemes, from, mid - 1, root.left, root);
        update(root.left);
        root.right = listToTree(itemes, mid + 1, to, root.right, root);
        update(root.right);
        update(root);
        return root;
    }
}

class RopeProblem {

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

    class Rope {

        SplayTree tree;
        String s;

        void process(int i, int j, int k) {
            tree.root = tree.move(tree.root, i + 1, j + 1, k);
            // Replace this code with a faster implementation
            //String t = s.substring(0, i) + s.substring(j + 1);
            //s = t.substring(0, k) + s.substring(i, j + 1) + t.substring(k);
        }

        String result() {
            StringBuffer buffer=new StringBuffer();
            tree.inOrder(tree.root,buffer);
            //tree.inOrder(tree.root);
            return buffer.toString();
        }

        Rope(String s) {
            this.s = s;
            tree = new SplayTree();
            Vertex v = tree.listToTree(s, 0, s.length() - 1, null, null);
            tree.root = v;

        }
    }

    public static void main(String[] args) throws IOException {

        new RopeProblem().run();
    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        Rope rope = new Rope(in.next());
        for (int q = in.nextInt(); q > 0; q--) {
            int i = in.nextInt();
            int j = in.nextInt();
            int k = in.nextInt();
            rope.process(i , j , k);
        }
        out.println(rope.result());
        out.close();
    }
}
