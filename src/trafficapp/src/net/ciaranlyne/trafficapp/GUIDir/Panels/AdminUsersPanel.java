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
import net.ciaranlyne.trafficapp.GUIDir.Models.UsersTableModel;
import net.ciaranlyne.trafficapp.GUIDir.Models.PanelModel;
import net.ciaranlyne.trafficapp.User;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 */
public class AdminUsersPanel extends PanelModel{
    
    //Attributes
    private ArrayList<User> users;
    private UsersTableModel tableModel;
    private JTable usersTable;
    private JScrollPane usersScrollPane;
    
    //Constructor
    public AdminUsersPanel(User admin) {
        users = new ArrayList<>();
        populate();
        
        tableModel = new UsersTableModel(users, admin);
        usersTable = new JTable(tableModel);
        usersTable.setAutoCreateRowSorter(true);
        usersTable.getRowSorter().toggleSortOrder(3);
        usersTable.getRowSorter().toggleSortOrder(3);
        usersScrollPane = new JScrollPane(usersTable);
        
        this.add(usersScrollPane);
    }
    
    /*
    * func: populate
    * use: populates the users ArrayList with User's gathered from database
    * params: none
    * returns: none
    */
    public void populate(){
    	Connection con = DB.getConnection();
        String query = "SELECT * FROM ta_user"; 
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                users.add(new User(rs.getInt("userID"), rs.getString("userName"), rs.getString("userEmail"), rs.getBoolean("admin")));
            }
            
        } catch (SQLException e) {
            System.out.println("Couldn't read the database (Empty Database maybe?)");
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    

}
