/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Panels;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import net.ciaranlyne.trafficapp.User;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 */
public class NavPanel extends JMenuBar {
    //Attribute
    public JMenuItem btnAdminUsers;
    public JMenuItem btnAdminLogs;
    public JMenuItem btnDash1;
    public JMenuItem btnDash2;
    public JMenuItem btnDash3;
    public JMenuItem btnDash4;
    public JMenuItem btnDash5;
    public JMenuItem btnLogout;
    
    private JMenu dashMenu;
    private JMenu adminMenu;
    private JMenu appMenu;
    
    //Constructor
    public NavPanel(User user) {
        //Set the text on the app menu to be the users username
        appMenu = new JMenu(user.getUserName());
        
        //if logged in user is an admin...
        if(user.isAdmin()) {
            //create admin menu under app menu
            adminMenu = new JMenu("Admin");
            
            btnAdminUsers = new JMenuItem("Edit Users");
            btnAdminUsers.setActionCommand("AdminUsers");
            btnAdminLogs = new JMenuItem("View Logs");
            btnAdminLogs.setActionCommand("AdminLogs");
            
            adminMenu.add(btnAdminUsers);
            adminMenu.add(btnAdminLogs);
            
            appMenu.add(adminMenu);
            appMenu.addSeparator();
        }
        
        dashMenu = new JMenu("Dashboards");
        
        btnDash1 = new JMenuItem("Dashboard 1", KeyEvent.VK_1);
        btnDash1.setActionCommand("DB1");
        dashMenu.add(btnDash1);
        
        btnDash2 = new JMenuItem("Dashboard 2", KeyEvent.VK_2);
        btnDash2.setActionCommand("DB2");
        dashMenu.add(btnDash2);
        
        btnDash3 = new JMenuItem("Dashboard 3", KeyEvent.VK_3);
        btnDash3.setActionCommand("DB3");
        dashMenu.add(btnDash3);
        
        btnDash4 = new JMenuItem("Dashboard 4", KeyEvent.VK_4);
        btnDash4.setActionCommand("DB4");
        dashMenu.add(btnDash4);
        
        btnDash5 = new JMenuItem("Dashboard 5", KeyEvent.VK_5);
        btnDash5.setActionCommand("DB5");
        dashMenu.add(btnDash5);
        
        
        btnLogout = new JMenuItem("Logout");
        btnLogout.setActionCommand("Logout");
        appMenu.add(btnLogout);
        
        this.add(appMenu);
        this.add(dashMenu);
   
    }
    
}
