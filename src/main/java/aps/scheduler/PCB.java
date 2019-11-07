/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.scheduler;

import aps.main.helpers.UIHelpers;
import aps.data_structures.IEquatable;
import aps.data_structures.IComparable;
import aps.data_structures.List;
import java.util.Arrays;

/**
 *
 * @author pedro
 */
public class PCB implements IEquatable<PCB>, IComparable<PCB> {
    private static int counter = 0;
    private final String PID;
    private final String color;
    private final int arrival; // momento no tempo absoluto em que entra na fila ready
    private final int duration; // número total de bursts necessários para completar a execução
    private int ioRequests[]; // bursts em que haverá chamadas de i/o 
    private String ioRequestsString;
    private final int priority;
    private final List<Burst> bursts;

    public PCB(int arrival, int duration, int[] ioRequests, int priority) {
        if (arrival < 0) {
            throw new Error("O parâmetro arrival deve ser positivo");
        }
        if (priority < 0 || priority > 4) {
            throw new Error("O parâmetro priority deve ser um inteiro de 0 a 4");
        }
        this.PID = "PID-" + String.format("%03d", ++counter);
        this.color = UIHelpers.getColor();
        this.arrival = arrival;
        this.duration = duration;
        this.ioRequests = ioRequests;
        this.ioRequestsString = this.intArrayToString(ioRequests);
        this.priority = priority;
        this.bursts = new List<>();
    }

    public PCB(int arrival, int duration, String ioRequests, int priority) {
        if (arrival < 0) {
            throw new Error("O parâmetro arrival deve ser positivo");
        }
        if (priority < 0 || priority > 4) {
            throw new Error("O parâmetro priority deve ser um inteiro de 0 a 4");
        }
        this.PID = "PID-" + String.format("%03d", ++counter);
        this.color = UIHelpers.getColor();
        this.arrival = arrival;
        this.duration = duration;
        this.ioRequests = this.stringToIntArray(ioRequests);
        this.ioRequestsString = ioRequests;
        this.priority = priority;
        this.bursts = new List<>();
    }
    
    public PCB(String PID, int arrival, int duration, int[] ioRequests, int priority) {
        if (arrival < 0) {
            throw new Error("O parâmetro arrival deve ser positivo");
        }
        if (priority < 0 || priority > 4) {
            throw new Error("O parâmetro priority deve ser um inteiro de 0 a 4");
        }
        this.PID = PID;
        this.color = UIHelpers.getColor();
        this.arrival = arrival;
        this.duration = duration;
        this.ioRequests = ioRequests;
        this.ioRequestsString = this.intArrayToString(ioRequests);
        this.priority = priority;
        this.bursts = new List<>();
    }

    public PCB(String PID, int arrival, int duration, String ioRequests, int priority) {
        if (arrival < 0) {
            throw new Error("O parâmetro arrival deve ser positivo");
        }
        if (priority < 0 || priority > 4) {
            throw new Error("O parâmetro priority deve ser um inteiro de 0 a 4");
        }
        this.PID = PID;
        this.color = UIHelpers.getColor();
        this.arrival = arrival;
        this.duration = duration;
        this.ioRequests = this.stringToIntArray(ioRequests);
        this.ioRequestsString = ioRequests;
        this.priority = priority;
        this.bursts = new List<>();
    }
    
    public static void clearCounter() {
        counter = 0;
    }
    
    private String intArrayToString(int arr[]) {
        return Arrays.toString(arr).replaceAll("\\[|\\]", "");
    }
    
    private int[] stringToIntArray(String s) {
        String arr[] = s.split(",");
        int intArr[] = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }

    public String getPID() {
        return PID;
    }

    public String getColor() {
        return color;
    }

    public int getArrival() {
        return arrival;
    }
    
    public int getDuration() {
        return this.duration;
    }

    public int[] getIoRequests() {
        if (this.ioRequestsString.length() > 0 && this.ioRequests.length == 0) {
            this.ioRequests = this.stringToIntArray(this.ioRequestsString);
        }
        return this.ioRequests;
    }

    public String getIoRequestsString() {
        if (this.ioRequestsString.length() == 0 && this.ioRequests.length > 0) {
            this.ioRequestsString = this.intArrayToString(this.ioRequests);
        }
        return ioRequestsString;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public int elapsed() {
        return this.bursts.getSize();
    }
    
    public int remaining() {
        return this.duration - this.elapsed();
    }

    public boolean isStarted() {
        return !this.bursts.isEmpty();
    }

    public boolean isCompleted() {
        return this.bursts.getSize() == this.duration;
    }

    public void executeBurst(int time) {
        if (!this.isCompleted()) {
            this.bursts.add(new Burst(time));
        }
    }
    
    public boolean isIORequested() {
        if (this.ioRequestsString.length() > 0 && this.ioRequests.length == 0) {
            this.ioRequests = this.stringToIntArray(this.ioRequestsString);
        }
        if (!this.isCompleted()) {
            for (int burst: this.ioRequests) {
                if (burst == this.bursts.getSize()) {
                    return true;
                }
            }            
        }
        return false;
    }

    public int startTime() {
        if (this.isCompleted() && this.bursts.getSize() == 0) {
            return this.arrival;
        }
        if (this.isStarted()) {
            return this.bursts.get(0).getTime();            
        }
        return -1;
    }

    public int endTime() {
        if (this.isCompleted()) {
            if (this.bursts.getSize() == 0) {
                return this.arrival;
            }
            return this.bursts.get(this.bursts.getSize() - 1).getTime() + 1;        
        }
        return -1;
    }

    public int getTurnaroundTime() {
        if (this.isCompleted()) {
            return this.endTime() - this.arrival;
        }
        return -1;
    }

    public int getWaitingTime() {
        if (this.isCompleted()) {
            int waitToStart = this.startTime() - this.arrival;
            int waitDuringBursts = (this.endTime() - this.startTime()) - this.duration;
            return waitToStart + waitDuringBursts;
        }
        return -1;
    }
    
    public int[] getTimeLine() {
        if (!this.isCompleted()) {return null;}
        
        int burstsArr[] = new int[this.bursts.getSize()];
        
        for (int i = 0; i < this.bursts.getSize(); i++) {
            burstsArr[i] = this.bursts.get(i).getTime();
        }
        
        return burstsArr;
    }

    public String getTimeLineAsString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.PID).append(": ");
        for (int i = 0; i < this.bursts.getSize(); i++) {
            builder.append(this.bursts.get(i).getTime()).append(" ");
        }
        builder.append("(arrival: ").append(this.arrival).append(", ");
        builder.append("duration: ").append(this.duration).append(", ");
        builder.append("turnaround: ").append(this.getTurnaroundTime()).append(", ");
        builder.append("waiting: ").append(this.getWaitingTime()).append(")");
        return builder.toString().trim();
    }
    
    public void reset() {
        this.bursts.clear();
    }
    
    @Override
    public int compareTo(PCB other) {
        if (this.getArrival() > other.getArrival()) {
            return 1;
        }
        if (this.getArrival() < other.getArrival()) {
            return -1;
        }
        return this.getPID().compareTo(other.getPID());
    }

    @Override
    public int compareToByName(PCB other) {
        return this.PID.compareTo(other.PID);
    }
    
    @Override
    public boolean isEqual(PCB other) {
        return this.getPID().equals(other.getPID());
    }

    @Override
    public String toString() {
        return this.getPID();
    }
}
