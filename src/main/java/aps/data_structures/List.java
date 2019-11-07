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
public class List<T extends IEquatable & IComparable> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public List() {
        this.last = this.first = null;
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public boolean exists(T search) {
        Node<T> node = this.first;
        while (node != null) {
            if (node.data.isEqual(search)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public boolean add(T data, int position) {
        if (position > this.size || position < 0) {
            return false;
        }
        Node<T> newNode = new Node<>(data);
        if (position == 0 && this.isEmpty()) {
            this.first = this.last = newNode;
        } else {
            if (position == 0) {
                newNode.next = this.first;
                this.first = newNode;
            } else {
                if (position == this.size) {
                    this.last.next = newNode;
                    this.last = newNode;
                } else {
                    int counter = 0;
                    Node<T> temp = this.first;
                    while (counter < position - 1) {
                        temp = temp.next;
                        counter++;
                    }
                    newNode.next = temp.next;
                    temp.next = newNode;
                }
            }
        }
        this.size++;
        return true;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (this.isEmpty()) {
            this.first = this.last = newNode;
        } else {
            this.last.next = newNode;
            this.last = newNode;
        }
        this.size++;
    }

    public void addAll(List<T> list) {
        if (list.isEmpty()) { // list is empty
            throw new Error("List is empty");
        }
        Node<T> node = list.first;
        while (node != null) {
            this.add(node.data);
            node = node.next;
        }
    }
    
    public T removeAt(int position) {
        if (this.isEmpty() || position > this.size || position < 0) {
            throw new Error("Invalid position");
        }
        T temp;
        if (position == 0) {
            temp = this.first.data;
            if (this.size == 1) {
                this.first = this.last = null;
            } else {
                this.first = this.first.next;
            }
        } else {
            int counter = 0;
            Node<T> previous = this.first;
            while (counter < position - 1) {
                previous = previous.next;
                counter++;
            }
            Node<T> removed = previous.next;
            temp = removed.data;
            previous.next = removed.next;
        }
        this.size--;
        return temp;
    }

    public boolean remove(T data) {
        int position = this.indexOf(data);
        if (position > 0) {
            this.removeAt(position);
            return true;
        }
        return false;        
    }

    public boolean set(T data, int position) {
        if (!(this.isEmpty() || position < 0 || position >= this.size)) {
            int counter = 0;
            Node<T> node = this.first;
            while (counter < position) {
                node = node.next;
                counter++;
            }
            node.data = data;
            return true;
        }
        return false;        
    }
    
    public T get(int position) {
        if (this.isEmpty() || position < 0 || position >= this.size) {
            throw new Error("Invalid position");
        }
        int counter = 0;
        Node<T> node = this.first;
        while (counter < position) {
            node = node.next;
            counter++;
        }
        return node.data;
    }
        
    public int indexOf(T search) {
        int counter = 0;
        Node<T> node = this.first;
        while (node != null) {
            if (node.data.isEqual(search)) {
                return counter;
            }
            counter++;
            node = node.next;
        }
        return -1;
    }
    
    public void sort() {
        boolean sorted = false;
        T temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < this.getSize() - 1; i++) {
                if (this.get(i).compareTo(this.get(i + 1)) > 0) {
                    temp = this.get(i);
                    this.set(this.get(i + 1), i);
                    this.set(temp, i + 1);
                    sorted = false;
                }
            }
        }
    }

    public void sortByName() {
        boolean sorted = false;
        T temp;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < this.getSize() - 1; i++) {
                if (this.get(i).compareToByName(this.get(i + 1)) > 0) {
                    temp = this.get(i);
                    this.set(this.get(i + 1), i);
                    this.set(temp, i + 1);
                    sorted = false;
                }
            }
        }
    }
    
    public void clear() {
        this.first = this.last = null;
        this.size = 0;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<T> node = this.first;
        while (node != null) {
            builder.append(node.data.toString()).append(" ");
            node = node.next;
        }
        return builder.toString().trim();
    }    

}
