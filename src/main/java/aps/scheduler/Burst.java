/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.scheduler;

import aps.data_structures.IEquatable;
import aps.data_structures.IComparable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author pedro
 */
public class Burst implements IEquatable<Burst>, IComparable<Burst> {
    
    private final SimpleIntegerProperty time;
    private final SimpleBooleanProperty io;
    private final SimpleBooleanProperty cpu;

    public Burst(int time) {
        this.time = new SimpleIntegerProperty(time);
        this.io = new SimpleBooleanProperty(false);
        this.cpu = new SimpleBooleanProperty(true);
    }

    public int getTime() {
        return this.time.get();
    }

    public SimpleIntegerProperty timeProperty() {
        return this.time;
    }    
    public boolean isIo() {
        return this.io.get();
    }

    public SimpleBooleanProperty ioProperty() {
        return this.io;
    }    

    public void setIo(boolean val) {
        this.io.set(val);
        this.cpu.set(!val);
    }

    public boolean isCpu() {
        return this.cpu.get();
    }

    public SimpleBooleanProperty cpuProperty() {
        return this.cpu;
    }    
    
    public void setCpu(boolean val) {
        this.cpu.set(val);
        this.io.set(!val);
    }

    @Override
    public int compareTo(Burst other) {
        return this.time.get() - other.time.get();
    }

    @Override
    public int compareToByName(Burst other) {
        return this.time.get() - other.time.get();
    }

    @Override
    public boolean isEqual(Burst other) {
        return this.time.get() == other.time.get();
    }
}
