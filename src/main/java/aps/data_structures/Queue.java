/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.data_structures;

/**
 *
 * @author pedro
 * @param <T>
 */
public class Queue<T extends IEquatable & IComparable> {
    
    private Node<T> head, tail;
    private int size;

    public Queue() {
        size = 0;
        this.head = this.tail = null;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int getSize() {
        return this.size;
    }

    public int getSize2() {
        if (this.head == null) {
            return 0;
        }
        int counter = 0;
        Node<T> next = this.head;
        while (next != null) {
            counter++;
            next = next.next;
        }
        return counter;
    }

    public void enqueue(T data) {
        Node<T> node = new Node<>(data);
        if (this.isEmpty()) {
            this.head = this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
        this.size++;
    }

    public T dequeue() {
        if (this.isEmpty()) {
            return null;
        }
        T temp = this.head.data;
        if (this.size == 1) {
            this.head = this.tail = null;
        } else {
            this.head = this.head.next;
        }
        this.size--;
        return temp;
    }
    
    public T front() {
        if (this.isEmpty()) {
            return null;
        }
        return this.head.data;
    }
    
    public void clear() {
        while(!this.isEmpty()) {
            this.dequeue();
        }
    }
    
    public Queue copy() {
        if (this.isEmpty()) {
            return null;
        }
        
        Queue<T> copiedQueue = new Queue();
        
        Node<T> next = this.head;
        while (next != null) {
            copiedQueue.enqueue(next.data);
            next = next.next;
        }
        
        return copiedQueue;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<T> node = this.head;
        while (node != null) {
            builder.append(node.data.toString()).append(" ");
            node = node.next;
        }
        return builder.toString().trim();
    }
}