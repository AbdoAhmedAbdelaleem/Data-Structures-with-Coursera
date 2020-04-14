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
public interface IPriorityQueue<T> {

    public void siftUp(int i);

    public void siftDown(int i);

    public T findMax();

    public T extractMax();

    public void insert(T item);

    public void remove(int i);

    public void changePriority(int i, T newP);

    public boolean empty();
}
