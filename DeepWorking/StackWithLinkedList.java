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
public class StackWithLinkedList<T> implements IStack<T>{

    // linkedList implemented on same folder
    LinkedList<T>list;
    public StackWithLinkedList()
    {
        list=new LinkedList<>();
    }
    @Override
    public T pop() {
         if (empty()) {
            throw new IndexOutOfBoundsException(-1);
        }
         T first= list.popFront();
         return first;
    }

    @Override
    public T top() {
         if (empty()) {
            throw new IndexOutOfBoundsException(-1);
        }
         T first= list.topFront();
         return first;
    }

    @Override
    public void push(T item) {
       list.pushFront(item);
    }

    @Override
    public boolean full() {
        return false;
    }

    @Override
    public boolean empty() {
        return list.empty();
    }
    public static void main(String[] args) {
         StackWithLinkedList<Integer> stack = new StackWithLinkedList<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.pop();
        stack.pop();
        stack.push(40);
        stack.push(50);
        stack.pop();
        while (!stack.empty()) {
            System.out.print(stack.pop() + " ");
        }
    }
}
