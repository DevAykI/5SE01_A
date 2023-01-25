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
public class CountPoint {

    //Attributes
    private final int countPointID;

    private final int regionID;
    private final String regionName;
    private final int localAuthorityID;
    private final String localAuthorityName;
    private final String roadName;
    private final String roadType;
    private final String startJunctionRoadName;
    private final String endJunctionRoadName;

    //Constructor
    public CountPoint(int countPointID, int regionID, String regionName, int localAuthorityID, String localAuthorityName, String roadName, String roadType, String startJunctionRoadName, String endJunctionRoadName) {
        this.countPointID = countPointID;
        this.regionID = regionID;
        this.regionName = regionName;
        this.localAuthorityID = localAuthorityID;
        this.localAuthorityName = localAuthorityName;
        this.roadName = roadName;
        this.roadType = roadType;
        this.startJunctionRoadName = startJunctionRoadName;
        this.endJunctionRoadName = endJunctionRoadName;
    }

    //Getters
    public int getCountPointID() {
        return countPointID;
    }

    public int getRegionID() {
        return regionID;
    }

    public String getRegionName() {
        return regionName;
    }

    public int getLocalAuthorityID() {
        return localAuthorityID;
    }

    public String getLocalAuthorityName() {
        return localAuthorityName;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getRoadType() {
        return roadType;
    }

    public String getStartJunctionRoadName() {
        return startJunctionRoadName;
    }

    public String getEndJunctionRoadName() {
        return endJunctionRoadName;
    }
}
