/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.scheduler;

import aps.data_structures.IComparable;
import aps.data_structures.IEquatable;

/**
 *
 * @author cmlima
 */
public class LogItem implements IEquatable<LogItem>, IComparable<LogItem> {

    private final int time;
    private final String PID;
    private final String color;
    private final int priority;
    private final boolean ioRequested; 
    private final String queue;

    public LogItem(int time, String PID, String color, int priority, boolean ioRequested, String queue) {
        this.time = time;
        this.PID = PID;
        this.color = color;
        this.priority = priority;
        this.ioRequested = ioRequested;
        this.queue = queue;
    }

    public int getTime() {
        return time;
    }

    public String getPID() {
        return PID;
    }

    public String getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isIoRequested() {
        return ioRequested;
    }
    
    public String getQueue() {
        return queue;
    }

    @Override
    public int compareTo(LogItem other) {
        return this.getTime() - other.getTime();
    }

    @Override
    public int compareToByName(LogItem other) {
        return this.PID.compareTo(other.PID);
    }

    @Override
    public boolean isEqual(LogItem other) {
        return this.getTime() == other.getTime();
    }
    
    @Override
    public String toString() {
        return this.getTime() + " " + this.getPID();
    }
}
