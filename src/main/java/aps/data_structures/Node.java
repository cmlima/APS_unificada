/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.data_structures;

/**
 *
 * @author cmlima
 * @param <T>
 */
public class Node<T> {

    public T data;
    public Node next;
    
    public Node (T data) {
        this.data = data;
        this.next = null;
    }
    
    @Override
    public String toString() {
        return this.data.toString();
    }

    
}
