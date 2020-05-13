/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeepWorking;

/**
 *
 * @author Abdo
 */
class AVLNode {

    int data;
    AVLNode left;
    AVLNode right;
    int height;

    @Override
    public String toString() {
        return data + ""; //To change body of generated methods, choose Tools | Templates.
    }

    public AVLNode(int data) {
        this.data = data;
        height = 1;
    }
}

public class AVLTree {

    AVLNode root;

    int getHeight(AVLNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.height;
        }
    }

    private void adjustHeight(AVLNode node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public AVLNode rotateLeft(AVLNode x) {
        if (x == null) {
            return null;
        }
        AVLNode y = x.right;
        AVLNode t2 = y.left;
        if (x != null && getHeight(t2) > getHeight(y.right)) {
            rotateRight(y);
            y = x.right;
            t2 = y.left;
        }
        y.left = x;
        x.right = t2;
        adjustHeight(x);
        adjustHeight(y);
        return y;
    }

    public AVLNode rotateRight(AVLNode y) {
        if (y == null) {
            return null;
        }
        AVLNode x = y.left;
        AVLNode t2 = x.right;
        if (x != null && getHeight(t2) > getHeight(x.left)) {
            y.left = rotateLeft(x);
            x = y.left;
            t2 = x.right;
        }
        x.right = y;
        y.left = t2;
        adjustHeight(x);
        adjustHeight(y);
        return x;
    }

    public void insert(int data) {
        if (root == null) {
            root = insertUtils(root, data);
        } else {
            root = insertUtils(root, data);
        }
    }

    public void deleteNode(int data) {
        root = deleteUtils(root, data);
    }

    private AVLNode getSmallestElemnt(AVLNode node) {
        if (node.left == null) {
            return node;
        }
        return getSmallestElemnt(node.left);
    }

    private AVLNode deleteUtils(AVLNode node, int key) {
        if (node == null) {
            return null;
        } else if (key > node.data) {
            node.right = deleteUtils(node.right, key);
        } else if (key < node.data) {
            node.left = deleteUtils(node.left, key);
        }
        if (key == node.data) {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } //has 2 Child
            else {
                AVLNode largerElment = getSmallestElemnt(node.right);
                int swap = largerElment.data;
                largerElment.data = node.data;
                node.data = swap;
                node.right = deleteUtils(node.right, key);
            }
        }
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        if (Math.abs(getHeight(node.left) - getHeight(node.right)) > 1) {
            if (getHeight(node.left) > getHeight(node.right)) {
                return rotateRight(node);
            }
            if (getHeight(node.left) < getHeight(node.right)) {
                return rotateLeft(node);
            }
        }
        return node;
    }

    private AVLNode insertUtils(AVLNode root, int data) {
        if (root == null) {
            return new AVLNode(data);
        }
        if (data > root.data) {
            root.right = insertUtils(root.right, data);
        } else if (data < root.data) {
            root.left = insertUtils(root.left, data);
        } else {
            return root;
        }
        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));
        if (Math.abs(getHeight(root.left) - getHeight(root.right)) > 1) {
            if (getHeight(root.left) > getHeight(root.right)) {
                return rotateRight(root);
            }
            if (getHeight(root.left) < getHeight(root.right)) {
                return rotateLeft(root);
            }
        }
        return root;
    }

    public static void main(String[] args) {
        //AVLTree tree = new AVLTree();
        BST tree=new BST();
        for (int i = 0; i < 1000000; i++) {
            tree.insert(i);
        }
        /* Constructing tree given in the above figure */
        tree.insert(9);
        tree.insert(5);
        tree.insert(10);
        tree.insert(0);
        tree.insert(6);
        tree.insert(11);
        tree.insert(-1);
        tree.insert(1);
        tree.insert(2);
       // tree.deleteNode(10);
       // tree.deleteNode(11);
    }
}
