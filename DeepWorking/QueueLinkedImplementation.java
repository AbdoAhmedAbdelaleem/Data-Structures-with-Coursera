/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeepWorking;

import java.util.Random;

/**
 *
 * @author Abdo
 */
public class QueueLinkedImplementation<T> implements IQueue<T> {

    LinkedList<T> list;

    public QueueLinkedImplementation() {
        list = new LinkedList<>();
    }

    @Override
    public void enqueue(T item) {
        list.pushBack(item);
    }

    @Override
    public T dequeue() {
        return list.popFront();
    }

    @Override
    public Boolean empty() {
        return list.empty();
    }

    @Override
    public boolean full() {
        return false;
    }

   public static void main(String[] args) {
        QueueLinkedImplementation q = new QueueLinkedImplementation();
        q.enqueue(1);
        q.dequeue();
    }
}
