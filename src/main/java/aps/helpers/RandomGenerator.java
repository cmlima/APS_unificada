
package aps.helpers;

import aps.scheduler.PCB;
import aps.data_structures.List;
import java.util.Arrays;
import java.util.Random;

public class RandomGenerator {
    
     private static boolean isUnique(int[] array, int num) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == num) {
                return false;
            }
        }
        return true;
    }

    private static int[] toUniqueArray(int[] array) {
        int[] temp = new int[array.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = -1;
        }
        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (isUnique(temp, array[i]))
                temp[counter++] = array[i];
        }
        int[] uniqueArray = new int[counter];
        System.arraycopy(temp, 0, uniqueArray, 0, uniqueArray.length);
        return uniqueArray;
    }
   
    public static int generateInteger(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
    
    public static PCB generatePCB(int minTime, int maxTime) {
        if (maxTime - 1 <= minTime) {
            throw new Error("Parâmetros inválidos");
        }
        int arrival = generateInteger(minTime, maxTime - 1);
        int duration;
        if (maxTime - arrival - 1 <= minTime) {
            duration = maxTime - minTime;
        } else {
            duration = generateInteger(minTime, maxTime - arrival - 1);
        }
        int requests = generateInteger(-2, 5);
        int ioRequests[];
        if (requests <= 0) {
            ioRequests = new int[0];
        } else {
            if (requests > (duration / 2)) {
                requests = duration / 2;
            }
            int temp[] = new int[requests];
            for (int i = 0; i < requests; i++) {
                temp[i] = generateInteger(1, duration);
            }
            ioRequests = toUniqueArray(temp);
            Arrays.sort(ioRequests);
        }
        int priority = generateInteger(0, 4);
        return new PCB(arrival, duration, ioRequests, priority);
    }
    
    public static List<PCB> generatePCBList(int size, int startTime, int endTime) {
        List<PCB> list = new List<>();
        for (int i = 0; i < size; i++) {
            list.add(generatePCB(startTime, endTime));
        }
        return list;
    }
}
