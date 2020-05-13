package Week4;

import java.io.*;
import java.util.*;

public class SetRangeSum {

    BufferedReader br;
    PrintWriter out;
    StringTokenizer st;
    boolean eof;

    // Splay tree implementation
    // Vertex of a splay tree
    class Vertex {

        int key;
        // Sum of all the keys in the subtree - remember to update
        // it after each operation that changes the tree.
        long sum;
        Vertex left;
        Vertex right;
        Vertex parent;

        Vertex(int key, long sum, Vertex left, Vertex right, Vertex parent) {
            this.key = key;
            this.sum = sum;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    void update(Vertex v) {
        if (v == null) {
            return;
        }
        v.sum = v.key + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0);
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
            if(m != null) {
            	parent.left.parent = parent;
            }
            
        } else {
            Vertex m = v.left;			//If this curr Node is right child of its PARENT
            v.left = parent;			//Right(Anti-clockwise) Rotation
            parent.right = m;
//ADDITION 2         
            parent.parent = v;
            if(m != null) {
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
        }
        
//ADDITION 3        //My addition to this:
        else if(grandparent == null) {
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

    void insert(int x) {
        Vertex left = null;
        Vertex right = null;
        Vertex new_vertex = null;
        VertexPair leftRight = split(root, x);
        left = leftRight.left;
        right = leftRight.right;
        if (right == null || right.key != x) {
            new_vertex = new Vertex(x, x, null, null, null);
        }
        root = merge(merge(left, new_vertex), right);
    }
 void eraseOld(int x) {
        // TODO: Implement erase yourself
        //out.println("del:"+x);
        VertexPair pair = find(root, x);
        root = pair.right;
        /**BZ: RETURN THE NEW ROOT OF SUBTREE */
        root = delete(x, root);
        /**BZ: MUST UPDATE PARENT LINKS AFTER DELETION */
        if (root != null) root.parent = null;
    }

    private Vertex delete(int x, Vertex root) {
        if (root == null) return root;
        if (x < root.key)
            root.left  = delete(x, root.left);
        else if (x > root.key)
            /**BZ: DELETE STH DOES NOT EXIST? */
            root.right = delete(x, root.right);
        /**BZ: ELSE IF OR IF? */
        else if (root.left == null)
            return root.right;
        else if (root.right == null)
            return root.left;
        else {
            // Two children case.
            // Find Next() of R in R.Right.
            // Replace R's key by its Next;
            // Promote Next's right subtree to Next.
            // out.println(root.key);
            Vertex next = next(root);
            assert(next.left == null);
            root.key = next.key;
            promote(next.right, next);
        }
        return root;
    }
     private void promote(Vertex child, Vertex node) {
        /**Promote child to replace node */
        assert(node.parent != null);   // Assert that Parent must exist
        // BZ: node may have null child
        if (child != null) child.parent = node.parent;
        if (node == node.parent.left) node.parent.left = child;
        else node.parent.right = child;
        update(node.parent);
        node.parent = null;
    }
    private Vertex next(Vertex node) {
        /**Return the next larger node */
        if (node == null) return null;
        if (node.right != null) {
            node = node.right;
            while (node.left != null) node = node.left;
            return node;
        }
        Vertex parent = node.parent;
        while (parent != null && parent.key <= node.key)
            parent = parent.parent;
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
        if(root!=null)
        root.parent = null;
    }

    boolean findOld(int x) {
        if (root == null) return false;
        Vertex node = root;
        while (node != null) {
            if (node.key == x) return true;
            else if (x < node.key) node = node.left;
            else node = node.right;
        }
        root=splay(node);
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

    public static final int MODULO = 1000000001;

    void solve() throws IOException {
        int n = nextInt();
        int last_sum_result = 0;
        for (int i = 0; i < n; i++) {
            char type = nextChar();
            switch (type) {
                case '+': {
                    int x = nextInt();
                    insert((x + last_sum_result) % MODULO);
                }
                break;
                case '-': {
                    
                    int x = nextInt();
                    erase((x + last_sum_result) % MODULO);
                }
                break;
                case '?': {
                    int x = nextInt();
                    out.println(find((x + last_sum_result) % MODULO) ? "Found" : "Not found");
                }
                break;
                case 's': {
                    int l = nextInt();
                    int r = nextInt();
                    long res = sum((l + last_sum_result) % MODULO, (r + last_sum_result) % MODULO);
                    out.println(res);
                    last_sum_result = (int) (res % MODULO);
                }
            }
        }
    }

    SetRangeSum() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new SetRangeSum();
    }

    String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                eof = true;
                return null;
            }
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    char nextChar() throws IOException {
        return nextToken().charAt(0);
    }
}
