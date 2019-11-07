/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.scheduler;

/**
 *
 * @author cmlima
 */
public class CPU {

    private int time;

    public CPU() {
        this.time = 0;
    }

    public int getTime() {
        return time;
    }
    
    public void tick() {
        this.time++;
    }
    
}
