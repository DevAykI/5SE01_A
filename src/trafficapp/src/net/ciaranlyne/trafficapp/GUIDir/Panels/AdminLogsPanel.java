/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Panels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.ciaranlyne.trafficapp.DB;
import net.ciaranlyne.trafficapp.GUIDir.Models.LogsTableModel;
import net.ciaranlyne.trafficapp.GUIDir.Models.PanelModel;
import net.ciaranlyne.trafficapp.LogItem;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 */
public class AdminLogsPanel extends PanelModel{
    
    //Attributes
    private ArrayList<LogItem> logs;
    private LogsTableModel tableModel;
    private JTable logsTable;
    private JScrollPane logsScrollPane;
    
    //Constructor
    public AdminLogsPanel() {
        logs = new ArrayList<>();
        populate();
        
        tableModel = new LogsTableModel(logs);
        
        logsTable = new JTable(tableModel);
        logsTable.setAutoCreateRowSorter(true);
        logsTable.getRowSorter().toggleSortOrder(0);
        logsTable.getRowSorter().toggleSortOrder(0);
        
        logsScrollPane = new JScrollPane(logsTable);
        
        this.add(logsScrollPane);
        
    }
    
    /*
    * func: populate
    * use: populates the logs ArrayList with logItem's gathered from database
    * params: none
    * returns: none
    */
    private void populate(){
    	Connection con = DB.getConnection();
        String query = 
                "SELECT ta_actionlog.logID, ta_actionlog.userID, ta_actionlog.actionName, ta_actionlog.actionDateTime, ta_user.userName "
                + "FROM ta_actionlog "
                + "LEFT JOIN ta_user "
                + "ON ta_actionlog.userID = ta_user.userID"; 
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                logs.add(new LogItem(rs.getInt("logID"), rs.getInt("userID"), rs.getString("actionName"), rs.getInt("actionDateTime"), rs.getString("userName")));
            }
            
        } catch (SQLException e) {
            System.out.println("Couldn't read the database (Empty Database maybe?)");
        } finally {
            try {
                stmt.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        for(int i = 0; i < logs.size(); i++) {
            
            if(logs.get(i).getActionName().equals("ActionLoginAttempt")) {
                query = "SELECT loginSuccessful FROM ta_actionloginattempt WHERE logID = " + logs.get(i).getLogID(); 
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if(rs.next()) {
                        if(rs.getBoolean("loginSuccessful")) {
                            logs.get(i).setParam(1);
                        } else {
                            logs.get(i).setParam(0);
                        }
                    }
            
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        stmt.close();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                
            } else if(logs.get(i).getActionName().equals("ActionEditUser")) {
                query = "SELECT userID FROM ta_actionedituser WHERE logID = " + logs.get(i).getLogID() ; 
                
                try {
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if(rs.next()) {
                        logs.get(i).setParam(rs.getInt("userID"));
                    }
            
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        stmt.close();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
        }
        try {
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
