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
interface ILinkedList<T> {

    public void pushFront(T item);

    public T topFront();

    public T popFront();

    public void pushBack(T item);

    public T topBack();

    public T popBack();

    public Node find(T item);

    public boolean erase(T item);

    public boolean empty();

    public void addBefore(Node node, T item);

    public void addAfter(Node node, T item);

}

public class LinkedList<T> implements ILinkedList<T> {

    int size;
    Node head;
    Node tail;

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.pushFront(5);
        list.pushFront(6);
        list.pushFront(7);
        list.pushFront(8);
        Node n = list.find(8);
        list.pushBack(8);
        list.pushBack(2);
        list.addAfter(n, 100);
        n = list.find(7);
        list.addBefore(n, 50);
        n = list.find(100);
        list.addBefore(n, 500);
        list.addAfter(n, 250);
        while (!list.empty()) {
            System.out.print(list.popFront()+ " ");
        }

    }

    @Override
    public void pushFront(T item) {
        Node<T> newNode = new Node<T>(item);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public T topFront() {
        if (head == null) {
            return null;
        }
        return (T) head.key;
    }

    @Override
    public T popFront() {
        if (empty()) {
            return null;
        } else {
            size--;
            if (head == tail) {
                T data = (T) head.key;
                head = tail = null;
                return data;
            } else {
                Node firstNode = head;
                head.next.previous = null;
                head = head.next;
                firstNode.next = null;
                return (T) firstNode.key;
            }
        }
    }

    @Override
    public void pushBack(T item) {
        size++;
        Node newNode = new Node(item);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
    }

    @Override
    public T topBack() {
        if (tail == null) {
            return null;
        } else {
            return (T) tail.key;
        }
    }

    @Override
    public T popBack() {
        if (empty()) {
            return null;
        } else {
            size--;
            T data = (T) tail.key;
            if (head == tail) {
                head = tail;
                return data;
            }
            tail = tail.previous;
            tail.next.previous = null;
            tail.next = null;
            return data;
        }
    }

    @Override
    public Node find(T item) {
        Node aidToTraverse = head;
        while (aidToTraverse != null && aidToTraverse.key != item) {
            aidToTraverse = aidToTraverse.next;
        }
        return aidToTraverse;
    }

    @Override
    public boolean erase(T item) {
        Node targetNode = find(item);
        if (targetNode == null) {
            return false;
        }
        if (head == targetNode) {
            popFront();
        } else if (tail == targetNode) {
            popBack();
        }
        targetNode.next.previous = targetNode.previous;
        targetNode.previous.next = targetNode.next;
        targetNode.previous = null;
        targetNode.next = null;
        size--;
        return true;
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    @Override
    public void addBefore(Node node, T item) {
        Node newNode = new Node(item);
        newNode.next = node;
        newNode.previous = node.previous;
        if (node.previous != null) {
            node.previous.next = newNode;
        }
        node.previous = newNode;
        if (node == head) {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAfter(Node node, T item) {
        Node newNode = new Node(item);
        newNode.previous = node;
        newNode.next = node.next;
        if (node.next != null) {
            node.next.previous = newNode;
        }
        node.next = newNode;
        if (node == tail) {
            tail = newNode;
        }
        size++;
    }
}

class Node<T> {

    T key;
    Node next;
    Node previous;

    public Node(T key) {
        this.key = key;
    }
}
