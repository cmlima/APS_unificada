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
public class TimeLineItem implements IEquatable<TimeLineItem>, IComparable<TimeLineItem> {

    private int start;
    private int end;
    private final String PID;
    private final String color;
    private final int priority;

    public TimeLineItem(String PID, String color, int priority, int start, int end) {
        this.start = start;
        this.end = end;
        this.PID = PID;
        this.color = color;
        this.priority = priority;
    }

    public TimeLineItem(String PID, String color, int priority, int start) {
        this.start = start;
        this.PID = PID;
        this.color = color;
        this.priority = priority;
    }
    
    public TimeLineItem(String PID, String color, int priority) {
        this.PID = PID;
        this.color = color;
        this.priority = priority;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
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
    
    public boolean isIdle() {
        return PID.isBlank();
    }

    @Override
    public int compareTo(TimeLineItem other) {
        return this.getStart() - other.getStart();
    }

    @Override
    public int compareToByName(TimeLineItem other) {
        return this.PID.compareTo(other.PID);
    }

    @Override
    public boolean isEqual(TimeLineItem other) {
        return this.getStart() == other.getStart();
    }
    
    @Override
    public String toString() {
        return this.getStart() + "-" + this.getEnd() + ": " + this.getPID();
    }
}
