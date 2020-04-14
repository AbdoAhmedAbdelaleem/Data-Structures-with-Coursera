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
public class PriorityQueue<T extends Comparable<T>> implements IPriorityQueue<T> {

    int size;
    public final int defaultCapacity = 11;
    Object[] q;

    public PriorityQueue() {
        initPriorityQ(defaultCapacity);
    }

    public void buildHeap(T[] arr) {
        q = arr;
        size = arr.length;
        for (int i = (arr.length / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    public void heapSortInPlace(T[] arr) {
        buildHeap(arr);
        int i=size;
        while(i>0) {
            swap(size - 1, 0);
            size--;
            siftDown(0);
            i--;
        }
    }

    public PriorityQueue(int capacity) {
        q = new Object[capacity];
    }

    private void initPriorityQ(int capacity) {
        q = new Object[capacity];
    }

    @Override
    public void siftUp(int i) {
        if (i == 0) {
            return;
        }
        int parent = (i % 2 == 0) ? (i - 2) / 2 : (i - 1) / 2;
        if (((T) q[i]).compareTo(((T) q[parent])) > 0) {
            swap(i, parent);
            siftUp(parent);
        }
    }

    @Override
    public void siftDown(int i) {
        int index = i;
        int leftIndex = (2 * i) + 1;
        int righIndex = (2 * i) + 2;
        if (leftIndex < size && ((T) q[leftIndex]).compareTo((T) q[index]) > 0) {
            index = leftIndex;
        }
        if (righIndex < size && ((T) q[righIndex]).compareTo((T) q[index]) > 0) {
            index = righIndex;
        }
        if (index != i) {
            swap(index, i);
            siftDown(index);
        }
    }

    @Override
    public T findMax() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
        return (T) q[0];
    }

    @Override
    public T extractMax() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
        T max = (T) q[0];
        size--;
        q[0] = q[size];
        siftDown(0);
        return max;
    }

    private void swap(int i, int j) {
        Object temp = q[i];
        q[i] = q[j];
        q[j] = temp;
    }

    @Override
    public void insert(T item) {
        if (size == q.length) {
            doubleCapacity();
        }
        q[size] = item;
        siftUp(size);
        size++;
    }

    private void doubleCapacity() {
        int oldCapacity = q.length;
        int newCapacity = oldCapacity * 2;
        Object[] newArr = new Object[newCapacity];
        for (int i = 0; i < oldCapacity; i++) {
            newArr[i] = q[i];
        }
        q = newArr;
    }

    @Override
    public void remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException(i);
        }
        q[i] = Integer.MAX_VALUE;
        siftUp(i);
        extractMax();
    }

    @Override
    public void changePriority(int i, T newP) {
        T oldP = (T) q[i];
        q[i] = newP;
        if (newP.compareTo(oldP) > 0) {
            siftUp(i);
        } else if (newP.compareTo(oldP) < 0) {
            siftDown(i);
        }
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> p = new PriorityQueue<>();
        p.insert(5);
        p.insert(1);
        p.insert(6);
        p.insert(2);
        p.insert(3);
        p.insert(8);
        p.insert(4);
        p.insert(0);
        p.remove(5);
        while (!p.empty()) {
            System.out.println(p.extractMax());
        }
    }
}
