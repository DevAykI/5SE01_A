/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;

/**
 * File contributors:
 * Burak Kavus (w1726580)
 * Aykut Inalan (w1741621)
 */
public class DB {

    public static Connection getConnection() {
        String urlSQLite = "jdbc:sqlite:trafficapp.db";
        
        //creaat a connection to driver and try connect to the driver
        try {
            Driver driverSQLite = new org.sqlite.JDBC();
            DriverManager.registerDriver(driverSQLite);
            System.out.println("SQLITE Driver has loaded up!");
        } catch (Exception e) { //send error message if cannot connect.
            System.out.println("There was an ERROR with the SQLITE Driver: " + e.getMessage());
        }
        
        //create a connection to database and try connect to the database.
        try {
            Connection connection = DriverManager.getConnection(urlSQLite);
            System.out.println("Connected to the database!");
            return connection;
        } catch (Exception e) { //send error message if cannot connect.
            System.out.println("ERROR connecting to the database: " + e.getMessage());
            return null;
        }
    }
}
