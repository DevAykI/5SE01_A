/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Models;

import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import net.ciaranlyne.trafficapp.Count;
import net.ciaranlyne.trafficapp.CountData;
import net.ciaranlyne.trafficapp.CountPoint;
import net.ciaranlyne.trafficapp.DB;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 */
public abstract class DashboardModel extends PanelModel{
    
    //Attributes
    protected String designer;
    protected HashMap<Integer, CountPoint> trafficCountPoints;
    protected ArrayList<Count> trafficData;
    
    /*
    * func: populate
    * use: populates the trafficCountPoints and trafficData attributes using the database tables; ta_countpoints, ta_count and ta_countdata
    * params: none
    * returns: none
    */
    protected void populate() {
        trafficData = new ArrayList<>();
        trafficCountPoints = new HashMap<>();
        
        Connection con = DB.getConnection();

        String query = "SELECT * FROM ta_countpoints";
        Statement stmt = null;
        try {
            stmt = con.createStatement();;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                CountPoint temp = new CountPoint(rs.getInt("countPointID"), rs.getInt("regionID"), rs.getString("regionName"), rs.getInt("localAuthorityID"), 
                        rs.getString("localAuthorityName"), rs.getString("roadName"), rs.getString("roadType"), rs.getString("startJunctionRoadName"), rs.getString("endJunctionRoadName"));
                trafficCountPoints.put(rs.getInt("countPointID"), temp);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't read the database (Empty Database maybe?)");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        query = "SELECT * FROM ta_count LEFT JOIN ta_countdata ON ta_count.countID = ta_countdata.countID";
        stmt = null;
        try {
            stmt = con.createStatement();;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                CountData tempCountData = new CountData(rs.getInt("hour"), rs.getString("countDate"), rs.getInt("pedalCycles"), rs.getInt("twoWheeledMotorVehicles"), 
                    rs.getInt("carsAndTaxis"), rs.getInt("busesAndCoaches"), rs.getInt("lgvs"), rs.getInt("hgvs2RigidAxle"), rs.getInt("hgvs3RigidAxle"), 
                    rs.getInt("hgvs4OrMorerigidAxle"), rs.getInt("hgvs3Or4ArticulatedAxle"), rs.getInt("hgvs5ArticulatedAxle"), rs.getInt("hgvs6ArticulatedAxle"), 
                    rs.getInt("allHgvs"), rs.getInt("allMotorVehicles"));
                Count tempCount = new Count(rs.getInt("countID"), tempCountData, rs.getInt("countPointID"), rs.getString("dirOfTravel"), rs.getInt("year"));
                
                trafficData.add(tempCount);
                
            }
        } catch (SQLException e) {
            System.out.println("Couldn't read the database (Empty Database maybe?)");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
