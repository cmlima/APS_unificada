/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.scheduler;

import aps.data_structures.List;

/**
 *
 * @author cmlima
 */
public class Dispatcher {
    
    private final CPU cpu;
    private final List<LogItem> log;

    public Dispatcher() {
        this.cpu = new CPU();
        this.log = new List<>();
    }

    public int getTime() {
        return this.cpu.getTime();
    }
    
    public List getLog() {
        return this.log;
    }
    
    public String getLogAsString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.log.getSize(); i++) {
            LogItem item = this.log.get(i);
            builder.append(item.getTime()).append("\t");
            builder.append(item.getPID()).append(" (priority: ");
            builder.append(item.getPriority()).append(", I/O: ");
            builder.append(item.isIoRequested() ? "YES" : "NO").append(")\tQueue: ");
            builder.append(item.getQueue()).append("\n\n");
        }
        return builder.toString().trim();
    }
    
    public void sleep() {
        this.cpu.tick();
    }

    // O valor booleano informa se o processo conseguiu executar todos os bursts (o quantum)
    public boolean dispatch(PCB pcb, int bursts, String queue) {
        for (int i = 0; i < bursts; i++) {
            pcb.executeBurst(this.getTime());
            this.log.add(new LogItem(this.getTime(), pcb.getPID(), pcb.getColor(), pcb.getPriority(), pcb.isIORequested(), queue));
            this.cpu.tick();
            if (pcb.isCompleted() || pcb.isIORequested()) {
                return (i == bursts - 1);
            }
        }
        return true;
    }
}
