/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 * Aykut Inalan (w1741621)
 */
public class Count {
    
    //Attributes
    private final int countID;
    private CountData countData;
    private final int countPointID;
    private final String dirOfTravel;
    private final int year;

    //Constructor
    public Count(int countID, CountData countData, int countPointID, String dirOfTravel, int year) {
        this.countID = countID;
        this.countData = countData;
        this.countPointID = countPointID;
        this.dirOfTravel = dirOfTravel;
        this.year = year;
    }
    
    //Getters
    public int getCountID() {
        return countID;
    }

    public CountData getCountData() {
        return countData;
    }

    public int getCountPointID() {
        return countPointID;
    }

    public String getDirOfTravel() {
        return dirOfTravel;
    }

    public int getYear() {
        return year;
    }
    
    //Setters
    public void setCountData(CountData countData) {
        this.countData = countData;
    }
}
