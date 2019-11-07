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
public interface IComparable<T> {
    public int compareTo(T other);
    public int compareToByName(T other);
}
