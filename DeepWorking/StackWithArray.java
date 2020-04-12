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

//IStack Intereface are exist on same folder of this class
public class StackWithArray<T> implements IStack<T> {

    int size;
    Object[] arr;

    public StackWithArray() {
        int defaultCapacity = 100;
        arr = new Object[defaultCapacity];
    }

    public StackWithArray(int capacity) {
        arr = new Object[capacity];
    }

    @Override
    public T pop() {
        if (empty()) {
            throw new IndexOutOfBoundsException();
        }
        size--;
        return (T) arr[size];

    }

    @Override
    public T top() {
        if (!empty()) {
            return (T) arr[size - 1];
        }
        return null;
    }

    @Override
    public void push(T item) {
         if (full()) {
            throw new StackOverflowError();
        }
            arr[size] = item;
            size++;
    }

    @Override
    public boolean full() {
        return size == arr.length;
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    public static void main(String[] args) {
        StackWithArray<Integer> stack = new StackWithArray(5);
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);
        while (!stack.empty()) {
            System.out.print(stack.pop() + " ");
        }

    }
}
