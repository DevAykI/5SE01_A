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
public class CountData {

    //Attributes
    private final int hour;
    private final String countDate;
    private final int pedalCycles;
    private final int twoWheeledMotorVehicles;
    private final int carsAndTaxis;
    private final int busesAndCoaches;
    private final int lgvs;
    private final int hgvs2RigidAxle;
    private final int hgvs3RigidAxle;
    private final int hgvs4OrMorerigidAxle;
    private final int hgvs3Or4ArticulatedAxle;
    private final int hgvs5ArticulatedAxle;
    private final int hgvs6ArticulatedAxle;
    private final int allHgvs;
    private final int allMotorVehicles;

    //Constructor
    public CountData(int hour, String countDate, int pedalCycles, int twoWheeldedMotorVehicles, int carsAndTaxis, int busesAndCoaches, int lgvs, int hgvs2RigidAxle,
            int hgvs3RigidAxle, int hgvs4OrMorerigidAxle, int hgvs3Or4ArticulatedAxle, int hgvs5ArticulatedAxle, int hgvs6ArticulatedAxle, int allHgvs, int allMotorVehicles) {
        this.hour = hour;
        this.countDate = countDate;
        this.pedalCycles = pedalCycles;
        this.twoWheeledMotorVehicles = twoWheeldedMotorVehicles;
        this.carsAndTaxis = carsAndTaxis;
        this.busesAndCoaches = busesAndCoaches;
        this.lgvs = lgvs;
        this.hgvs2RigidAxle = hgvs2RigidAxle;
        this.hgvs3RigidAxle = hgvs3RigidAxle;
        this.hgvs4OrMorerigidAxle = hgvs4OrMorerigidAxle;
        this.hgvs3Or4ArticulatedAxle = hgvs3Or4ArticulatedAxle;
        this.hgvs5ArticulatedAxle = hgvs5ArticulatedAxle;
        this.hgvs6ArticulatedAxle = hgvs6ArticulatedAxle;
        this.allHgvs = allHgvs;
        this.allMotorVehicles = allMotorVehicles;
    }

    //Getters
    public int getHour() {
        return hour;
    }

    public int getPedalCycles() {
        return pedalCycles;
    }

    public int getTwoWheeldedMotorVehicles() {
        return twoWheeledMotorVehicles;
    }

    public int getCarsAndTaxis() {
        return carsAndTaxis;
    }

    public int getBusesAndCoaches() {
        return busesAndCoaches;
    }

    public int getLgvs() {
        return lgvs;
    }

    public int getHgvs2RigidAxle() {
        return hgvs2RigidAxle;
    }

    public int getHgvs3RigidAxle() {
        return hgvs3RigidAxle;
    }

    public int getHgvs4OrMorerigidAxle() {
        return hgvs4OrMorerigidAxle;
    }

    public int getHgvs3Or4ArticulatedAxle() {
        return hgvs3Or4ArticulatedAxle;
    }

    public int getHgvs5ArticulatedAxle() {
        return hgvs5ArticulatedAxle;
    }

    public int getHgvs6ArticulatedAxle() {
        return hgvs6ArticulatedAxle;
    }

    public int getAllHgvs() {
        return allHgvs;
    }

    public int getAllMotorVehicles() {
        return allMotorVehicles;
    }

    public String getCountDate() {
        return countDate;
    }
}
