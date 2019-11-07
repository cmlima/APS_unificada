/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.helpers;

import aps.scheduler.PCB;
import aps.data_structures.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author cmlima
 */
public class FileHelpers {
    
    public static boolean save(List<PCB> list, String filePath) throws IOException {
        String json = JSON.stringify(list, 0);
        try (var writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(json);
        }
        return true;
    }
    
    public static List<PCB> load(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (var reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        try {
            return JSON.parse(contentBuilder.toString());
        } catch (Exception e) {
            throw new IOException("Arquivo inválido");
        }
    }
    
}
