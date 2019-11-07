/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.helpers;

import aps.scheduler.ALGORITHM;
import aps.scheduler.LogItem;
import aps.scheduler.PCB;
import aps.scheduler.Scheduler;
import aps.data_structures.List;
import aps.data_structures.Queue;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author cmlima
 */
public class Tests {
    
    public static void testPCB() {
    
        System.out.println("\n\n****** TESTANDO PCB ******\n");
        
        int ioRequests[] = { 3, 5, 7 } ;
        PCB pcb = new PCB(2, 10, ioRequests, 2);
        pcb.executeBurst(3);
        pcb.executeBurst(4);
        pcb.executeBurst(5);
        System.out.println("isCompleted: " + pcb.isCompleted());
        System.out.println("remaining: " + pcb.remaining());
        System.out.println("isIORequested in " + pcb.elapsed() + ": " + pcb.isIORequested());
        pcb.executeBurst(6);
        pcb.executeBurst(7);
        System.out.println("isCompleted: " + pcb.isCompleted());
        System.out.println("remaining: " + pcb.remaining());
        System.out.println("isIORequested in " + pcb.elapsed() + ": " + pcb.isIORequested());
        pcb.executeBurst(10);
        pcb.executeBurst(11);
        pcb.executeBurst(12);
        System.out.println("isCompleted: " + pcb.isCompleted());
        System.out.println("remaining: " + pcb.remaining());
        System.out.println("isIORequested in " + pcb.elapsed() + ": " + pcb.isIORequested());
        System.out.println("turnaround: " + pcb.getTurnaroundTime());
        System.out.println("waiting: " + pcb.getWaitingTime());
        pcb.executeBurst(25);
        pcb.executeBurst(26);
        System.out.println("isCompleted: " + pcb.isCompleted());
        System.out.println("remaining: " + pcb.remaining());
        System.out.println("isIORequested: " + pcb.isIORequested());
        System.out.println("turnaround: " + pcb.getTurnaroundTime());
        System.out.println("waiting: " + pcb.getWaitingTime());
    }
    
    public static void testQueue() {

        System.out.println("\n\n****** TESTANDO FILA DINÂMICA ******\n");
        
        int ioRequests1[] = { 3, 5, 7 } ;
        PCB pcb1 = new PCB(2, 10, ioRequests1, 2);
        int ioRequests2[] = { 4, 6, 8 } ;
        PCB pcb2 = new PCB(1, 20, ioRequests2, 2);
        int ioRequests3[] = { 5, 10, 11 } ;
        PCB pcb3 = new PCB(0, 30, ioRequests3, 2);
        
        Queue<PCB> fila = new Queue<>();
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
        fila.enqueue(pcb1);
        System.out.println("front: " + fila.front().toString());
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
        fila.enqueue(pcb2);
        System.out.println("front: " + fila.front().toString());
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
        fila.enqueue(pcb3);
        System.out.println("front: " + fila.front().toString());
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
        System.out.println("dequeue: " + fila.dequeue().getPID());
        System.out.println("dequeue: " + fila.dequeue().getPID());
        System.out.println("dequeue: " + fila.dequeue().getPID());
        System.out.println("size: " + fila.getSize());
        System.out.println("isEmpty: " + fila.isEmpty());
    }
    
    public static void testLog() {

        System.out.println("\n\n****** TESTANDO LOG ******\n");
        
        String queue1 = "";
        LogItem item1 = new LogItem(0, "PID-1", "blue", 4, false, queue1);
        String queue2 = "PID-2 PID-3";
        LogItem item2 = new LogItem(1, "PID-4", "green", 3, true, queue2);
        String queue3 = "PID-3";
        LogItem item3 = new LogItem(2, "PID-2", "red", 4, false, queue3);
        String queue4 = "";
        LogItem item4 = new LogItem(3, "PID-3", "yellow", 2, false, queue4);
        List<LogItem> log = new List<>();
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
        log.add(item1);
        log.add(item2);
        System.out.println("front: " + log.get(0).getPID());
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
        log.add(item3);
        System.out.println("front: " + log.get(0).getPID());
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
        System.out.println("List: " + log.toString());
        System.out.println("Remove: " + log.removeAt(0).getPID());
        log.add(item4);
        System.out.println("List: " + log.toString());
        System.out.println("Remove: " + log.removeAt(0).getPID());
        System.out.println("List: " + log.toString());
        System.out.println("size: " + log.getSize());
        System.out.println("isEmpty: " + log.isEmpty());
    }
    
    public static void testSort() {
        
        System.out.println("\n\n****** TESTANDO SORT ******\n");
        
        List<PCB> lista = new List<>();

        lista.add(new PCB(1, 10, new int[]{2, 6}, 4));
        lista.add(new PCB(1, 15, new int[]{3}, 3));
        lista.add(new PCB(0, 11, new int[]{5}, 2));
        lista.add(new PCB(1, 13, new int[]{7}, 4));
        lista.add(new PCB(5, 13, new int[]{7}, 4));
        lista.add(new PCB(3, 13, new int[]{7}, 4));
        lista.add(new PCB(6, 13, new int[]{7}, 4));
        lista.add(new PCB(4, 13, new int[]{7}, 4));
        lista.add(new PCB(2, 13, new int[]{7}, 4));
        
        lista.sort();
        
        System.out.println(lista.getSize());
        System.out.println(lista.toString());
    }

    public static void testRoundRobin() {
        
        System.out.println("\n\n****** TESTANDO ROUND ROBIN ******\n");
        
        Scheduler scheduler = new Scheduler(ALGORITHM.ROUND_ROBIN);
        scheduler.setQuantum(4);

        scheduler.addProcess(new PCB(1, 10, new int[]{}, 4));
        scheduler.addProcess(new PCB(1, 15, new int[]{3}, 3));
        scheduler.addProcess(new PCB(0, 11, new int[]{5}, 2));
        scheduler.addProcess(new PCB(1, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(5, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(3, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(6, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(4, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(2, 13, new int[]{7}, 4));
        
        scheduler.execute();
        System.out.println("Resultado\n" + scheduler.toString());
        
        System.out.println("avgTurnaround: " + scheduler.getAvgTurnaround());
        System.out.println("avgWaiting: " + scheduler.getAvgWaiting());
        System.out.println("TimeLines: \n" + scheduler.getCompletedAsString());
    }

    public static void testPreemptivePriority() {
        
        System.out.println("\n\n****** TESTANDO PRIORIDADE PREEMPTIVO ******\n");

        Scheduler scheduler = new Scheduler(ALGORITHM.PRIORITY_PREEMPTIVE);
        
        scheduler.addProcess(new PCB(1, 10, new int[]{}, 4));
        scheduler.addProcess(new PCB(1, 15, new int[]{3}, 3));
        scheduler.addProcess(new PCB(0, 11, new int[]{5}, 2));
        scheduler.addProcess(new PCB(1, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(5, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(3, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(6, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(4, 13, new int[]{7}, 4));
        scheduler.addProcess(new PCB(2, 13, new int[]{7}, 4));
        
        scheduler.execute();
        System.out.println("Resultado\n" + scheduler.toString());
        
        System.out.println("avgTurnaround: " + scheduler.getAvgTurnaround());
        System.out.println("avgWaiting: " + scheduler.getAvgWaiting());
        System.out.println("TimeLines: \n" + scheduler.getCompletedAsString());
    }
    
    public static void testHelpers() {
        
        System.out.println("\n\n****** TESTANDO HELPERS ******\n");
        
        try {
            List<PCB> lista = RandomGenerator.generatePCBList(10, 0, 50);
            System.out.println("size: " + lista.getSize());
            System.out.println(lista.toString());
            for (int i = 0; i < 10; i++) {
                System.out.println(lista.get(i).toString());
                System.out.println(lista.get(i).getArrival());
                System.out.println(lista.get(i).getDuration());
                System.out.println(Arrays.toString(lista.get(i).getIoRequests()));        
                System.out.println(lista.get(i).getPriority());
            }
            String json = JSON.stringify(lista, 4);
            //System.out.println(json);
            List<PCB> nova = JSON.parse(json);
            System.out.println(nova.toString());
            for (int i = 0; i < nova.getSize(); i++) {
                System.out.println(nova.get(i).toString());
                System.out.println(nova.get(i).getArrival());
                System.out.println(nova.get(i).getDuration());
                System.out.println(Arrays.toString(nova.get(i).getIoRequests()));
                System.out.println(nova.get(i).getPriority());
            }
            FileHelpers.save(nova, "C:\\Users\\cmlima\\Desenvolvimento\\UAM\\teste.json");
            List<PCB> novissima = FileHelpers.load("C:\\Users\\cmlima\\Desenvolvimento\\UAM\\teste.json");
            for (int i = 0; i < novissima.getSize(); i++) {
                System.out.println(novissima.get(i).toString());
                System.out.println(novissima.get(i).getArrival());
                System.out.println(novissima.get(i).getDuration());
                System.out.println(Arrays.toString(novissima.get(i).getIoRequests()));
                System.out.println(novissima.get(i).getPriority());
            }
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }
}
