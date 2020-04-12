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
public class QueueUsingArray<T> implements IQueue<T> {

    Object arr[];
    int size;
    int r, w;

    public QueueUsingArray() {
        int defaultCapacity = 100;
        arr = new Object[defaultCapacity];
        r = w = -1;
    }

    public QueueUsingArray(int capacity) {
        arr = new Object[capacity];
        r = w = -1;
    }

    @Override
    public void enqueue(T item) {
        if (full()) {
            throw new StackOverflowError("size");
        }
        size++;
        w = (w + 1) % arr.length;
        arr[w] = item;
    }

    @Override
    public T dequeue() {
        if (empty()) {
            throw new IndexOutOfBoundsException(-1);
        }
        size--;
        r = (r + 1) % arr.length;
        return (T) arr[r];
    }

    @Override
    public Boolean empty() {
        return size == 0;
    }

    @Override
    public boolean full() {
        return size == arr.length - 1;
    }

    public static void main(String[] args) {
        QueueUsingArray<Integer> q = new QueueUsingArray<>(5);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        q.enqueue(5);
        q.enqueue(6);
        q.enqueue(7);
        q.enqueue(8);
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());

        //q.enqueue(5);
    }

}
