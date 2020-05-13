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
class TreeNode {

    TreeNode left;
    TreeNode right;
    int data;

    public TreeNode(int item) {
        left = right = null;
        data = item;
    }
}

public class BST {

    TreeNode root;

    public TreeNode search(int data) {
        return searchUtils(root, data);
    }

    private TreeNode searchUtils(TreeNode root, int data) {
        if (root == null || data == root.data) {
            return root;
        } else if (data > root.data) {
            return searchUtils(root.right, data);
        } else {
            return searchUtils(root.left, data);
        }
    }

    public void insert(int item) {
        TreeNode node = insertUtils(root, item);
        if (root == null) {
            root = node;
        }
    }

    private TreeNode insertUtils(TreeNode root, int item) {
        if (root == null) {
            return new TreeNode(item);
        } else if (item < root.data) {
            root.left = insertUtils(root.left, item);
        } else {
            root.right = insertUtils(root.right, item);
        }
        return root;
    }

    public void deletedNode(int key) {
        deleteUtils(root, key);
    }

    private TreeNode deleteUtils(TreeNode node, int key) {
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
                TreeNode largerElment = getSmallestElemnt(node.right);
                int swap = largerElment.data;
                largerElment.data = node.data;
                node.data = swap;
                node.right = deleteUtils(node.right, key);
            }
        }
        return node;
    }

    private TreeNode getSmallestElemnt(TreeNode node) {
        if (node.left == null) {
            return node;
        }
        return getSmallestElemnt(node.left);
    }

    public static void main(String[] args) {
        BST tree = new BST();
        tree.insert(5);
        tree.insert(8);
        tree.insert(2);
        tree.insert(1);
        tree.insert(4);
        tree.insert(7);
        tree.insert(10);
        tree.deletedNode(1);
        tree.deletedNode(2);
        tree.deletedNode(5);
    }
}
