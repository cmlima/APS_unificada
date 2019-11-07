/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aps.scheduler;

import aps.helpers.Tests;

/**
 *
 * @author pedro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tests.testPCB();
        Tests.testQueue();
        Tests.testLog();
        Tests.testSort();
        Tests.testRoundRobin();
        Tests.testPreemptivePriority();
        Tests.testHelpers();
    }
}
