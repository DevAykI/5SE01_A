/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.ciaranlyne.trafficapp.LogItem;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 */
public class LogsTableModel extends AbstractTableModel{
    
    //Attributes
    private final String[] colHeaders = {"LogID", "User", "User ID", "Action", "Action Time", "Param"};
    private ArrayList<LogItem> logs;
    
    //Constructor
    public LogsTableModel(ArrayList<LogItem> logs) {
        this.logs = logs;
    }
    
    /*
    * func: getRowCount
    * use: returns number of rows table should have by getting the size of the logs array list
    * params: none
    * returns: int (row index)
    */
    @Override
    public int getRowCount() {
        if(logs.isEmpty()) {
            return 0;
        } else {
            return logs.size();
        }
    }

    /*
    * func: getColumnName
    * use: gets column name from colHeaders array earlier in program using col as the array index
    * params: int col
    * returns: String (column name)
    */
    @Override
    public String getColumnName(int col) {
        return colHeaders[col];
    }
    
    /*
    * func: getColumnCount
    * use: gets number of columns by getting the length of the headers array
    * params: none
    * returns: int (number of colunns)
    */
    @Override
    public int getColumnCount() {
        return colHeaders.length;
    }
    
    /*
    * func: getColumnClass
    * use: gets class of specified column from its index
    * params: int columnIndex (self explanatory)
    * returns: Class (class of requested column)
    */
    @Override
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 2:
                return Integer.class;
            default:
                return String.class;
        }
    }
    
    /*
    * func: getValueAt
    * use: gets value for any given cell by querying the row index as an index of logs and returns a different attribute of the logItem based of columnIndex
    * params: 
        - int rowIndex (index of requested cells row)
        - int columnIndex (index of requested cells column)
    * returns: Object (any possible data type or value that is stored in given cell in table
    */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retValue = null;
      
        if(columnIndex == 0) {
            retValue = logs.get(rowIndex).getLogID();
        } else if (columnIndex == 1) {
            retValue = logs.get(rowIndex).getUsername();
        } else if (columnIndex == 2) {
            retValue = logs.get(rowIndex).getUserID();
        } else if (columnIndex == 3) {
            if("ActionLoginAttempt".equals(logs.get(rowIndex).getActionName())) {
                retValue = "Login";
            } else if("ActionEditUser".equals(logs.get(rowIndex).getActionName())) {
                retValue = "Edit User";
            } else {
                retValue = "Logout";
            }   
        } else if (columnIndex == 4) {
            retValue = logs.get(rowIndex).getActionTimeToString();
        } else if (columnIndex == 5) {
            if(logs.get(rowIndex).getActionName().equals("ActionEditUser")) {
                retValue = logs.get(rowIndex).getParam();
            } else if(logs.get(rowIndex).getActionName().equals("ActionLoginAttempt")) {
                if(logs.get(rowIndex).getParam() == 1) {
                    retValue = "SUCCESS";
                } else {
                    retValue = "FAIL";
                }
            } else {
                retValue = "";
            }
        }
        
        return retValue;
    }
}
