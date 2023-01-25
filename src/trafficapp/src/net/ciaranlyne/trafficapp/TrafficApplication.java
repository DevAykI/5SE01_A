/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp;

import net.ciaranlyne.trafficapp.GUIDir.GUI;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 * Aykut Inalan (w1741621)
 */
public class TrafficApplication {

    public static void main(String args[]) {
        new csvReader(); // populates database (Can be re-ran won't effect the database)
        
        //init program
        GUI gui = new GUI();
    }
}
