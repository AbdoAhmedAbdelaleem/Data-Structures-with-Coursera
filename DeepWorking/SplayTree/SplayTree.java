/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeepWorking.SplayTree;

/**
 *
 * @author Abdo
 */
class Node {

    Node parent;
    int data;
    Node left;
    Node right;

    public Node(int data, Node left, Node right, Node parent) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
}

public class SplayTree {

    Node root;

    public void search(int data) {
        Node node = root;
        while (node != null) {
            if (node.data == data) {
                break;
            } else if (data < node.data) {
                node = node.left;
            } else if (data > node.data) {
                node = node.right;
            }
        }
        if (node != null) {
            splay(node);
        }
    }

    public void insert(int data) {
        Node node = root;
        Node temp = null;
        while (node != null) {
            if (node.data == data) {
                break;
            } else if (data < node.data) {
                temp = node;
                node = node.left;
            } else if (data > node.data) {
                temp = node;
                node = node.right;
            }
        }
        Node n = new Node(data, null, null, temp);
        if (temp == null) {
            this.root = root;
        } else {
           if(data<temp.data)
               temp.left=n;
           else
               temp.right=n;
            splay(n);
        }
    }
  public void delete(int data)
  {
       Node node = root;
        while (node != null) {
            if (node.data == data) {
                break;
            } else if (data < node.data) {
                node = node.left;
            } else if (data > node.data) {
                node = node.right;
            }
        }
        if (node != null) {
            splay(node);
        }
  }
    void update(Node v) {
        if (v == null) {
            return;
        }
        if (v.left != null) {
            v.left.parent = v;
        }
        if (v.right != null) {
            v.right.parent = v;
        }
    }

    private Node leftRotate(Node x) {
        Node parent = x.parent;
        Node y = x.right;
        Node t2 = y.left;
        y.left = x;
        x.right = t2;
        x.parent = y;
        update(x);
        update(y);
        y.parent = parent;
        return y;
    }

    private Node rightRotate(Node y) {
        Node parent = y.parent;
        Node x = y.left;
        Node t2 = x.right;
        x.right = y;
        y.left = t2;
        y.parent = x;
        update(y);
        update(x);
        x.parent = parent;
        return x;
    }

    public void splay(Node n) {
        if (n == null || n.parent == null) {
            return;
        }
        Node node = n;
        while (node.parent != null) {
            Node parent = node.parent;
            Node grandParent = parent.parent;
            if (grandParent != null
                    && (node == parent.right && parent == grandParent.right
                    || node == parent.left && parent == grandParent.left)) {
                if (node == parent.left && parent == grandParent.left) {
                    rightRotate(grandParent);
                    rightRotate(parent);

                }
                if (node == parent.right && parent == grandParent.right) {
                    leftRotate(grandParent);
                    leftRotate(parent);

                }
            } else {
                if (node == parent.left) {
                    rightRotate(parent);
                    if (grandParent != null) {
                        leftRotate(grandParent);
                    }
                } else if (node == node.parent.right) {
                    leftRotate(node.parent);
                    if (grandParent != null) {
                        rightRotate(grandParent);
                    }
                }
            }
        }
        root=n;
    }
}
