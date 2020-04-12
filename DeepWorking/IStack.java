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
interface IStack<T> {

    public T pop();

    public T top();

    public void push(T item);

    public boolean full();

    public boolean empty();
}