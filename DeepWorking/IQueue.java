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
public interface IQueue<T> 
{
    public void enqueue(T item);
    public T dequeue();
    public Boolean empty();
    public boolean full();
}
