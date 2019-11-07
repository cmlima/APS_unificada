/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.helpers;

import aps.scheduler.PCB;
import aps.data_structures.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cmlima
 */
public class JSON {
    
    public static List<PCB> parse(String json) {
        JSONArray arr = new JSONArray(json);
        List<PCB> list = new List<>();
        for (int i = 0; i < arr.length(); i ++) {
            String PID = arr.getJSONObject(i).getString("PID");
            int arrival = arr.getJSONObject(i).getInt("arrival");
            int duration = arr.getJSONObject(i).getInt("duration");
            int priority = arr.getJSONObject(i).getInt("priority");
            JSONArray requests = arr.getJSONObject(i).getJSONArray("ioRequests");
            int ioRequests[] = new int[requests.length()];
            for (int j = 0; j < requests.length(); j++) {
                ioRequests[j] = requests.getInt(j);
            }
            list.add(new PCB(PID, arrival, duration, ioRequests, priority));
        }
        return list;
    }
    
    public static String stringify(List<PCB> jobs, int indentFactor) {
        JSONArray arr = new JSONArray();
        for (int i = 0; i < jobs.getSize(); i++) {
            JSONObject obj = new JSONObject();
            PCB pcb = jobs.get(i); 
            obj.put("PID", pcb.getPID());
            obj.put("arrival", pcb.getArrival());
            obj.put("duration", pcb.getDuration());
            obj.put("priority", pcb.getPriority());
            JSONArray requests = new JSONArray();
            for (int j = 0; j < pcb.getIoRequests().length; j++) {
                requests.put(pcb.getIoRequests()[j]);
            }
            obj.put("ioRequests", requests);
            arr.put(obj);
        }
        return arr.toString(indentFactor);
    }
    
    
}
