/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.ciaranlyne.trafficapp.LogItem;
import net.ciaranlyne.trafficapp.User;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 */
public class UsersTableModel extends AbstractTableModel{
    //Attributes
    private final String[] colHeaders = {"User ID", "Username", "Email", "Set Admin"};
    private ArrayList<User> users;
    private User admin;
    
    //Constructor
    public UsersTableModel(ArrayList<User> users, User admin) {
        this.users = users;
        this.admin = admin;
    }
    
    /*
    * func: getRowCount
    * use: returns number of rows table should have by getting the size of the users array list
    * params: none
    * returns: int (row index)
    */
    @Override
    public int getRowCount() {
        if(users.isEmpty()) {
            return 0;
        } else {
            return users.size();
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
            case 3:
                return Boolean.class;
            default:
                return String.class;
        }
    }
    
    /*
    * func: isCellEditable
    * use: gets class of specified column from its index
    * params:
        - int row (index of row of cell to be queried)
        - int column (index of column of cell to be queried)
    * returns: boolean (answer to question in name of function)
    */
    @Override
    public boolean isCellEditable(int row, int column) {
        return (column == 3);
    }
    
    /*
    * func: setValueAt
    * use: sets value at requested cell. Used in this case to change the value of a given users admin status if the admin checkbox is changed
    * params: 
        - Object aValue (the new value of the cell)
        - int rowIndex (index of requested cells row)
        - int columnIndex (index of requested cells column)
    * returns: none
    */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int colIndex) {
        if(colIndex == 3) {
            Boolean isAdmin = (Boolean) aValue;
            users.get(rowIndex).setAdmin(isAdmin, true);
            LogItem newLog = new LogItem(admin.getUserID(), "ActionEditUser", (int) (System.currentTimeMillis() / 1000L), users.get(rowIndex).getUserID());
            newLog.submit();
        }
    }
    
    /*
    * func: getValueAt
    * use: gets value for any given cell by querying the row index as an index of users and returns a different attribute of the User based of columnIndex
    * params: 
        - int rowIndex (index of requested cells row)
        - int columnIndex (index of requested cells column)
    * returns: Object (any possible data type or value that is stored in given cell in table
    */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object retValue = null;
      
        if(columnIndex == 0) {
            retValue = users.get(rowIndex).getUserID();
        } else if (columnIndex == 1) {
            retValue = users.get(rowIndex).getUserName();
        } else if (columnIndex == 2) {
            retValue = users.get(rowIndex).getUserEmail();
        } else if (columnIndex == 3) {
            retValue = users.get(rowIndex).isAdmin();  
        }
        
        return retValue;
    }
}
