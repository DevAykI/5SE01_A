/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.ciaranlyne.trafficapp.GUIDir.Panels.AdminLogsPanel;
import net.ciaranlyne.trafficapp.GUIDir.Panels.AdminUsersPanel;
import net.ciaranlyne.trafficapp.GUIDir.Panels.Dashboards.Dashboard1;
import net.ciaranlyne.trafficapp.GUIDir.Panels.Dashboards.Dashboard2;
import net.ciaranlyne.trafficapp.GUIDir.Panels.Dashboards.Dashboard3;
import net.ciaranlyne.trafficapp.GUIDir.Panels.Dashboards.Dashboard4;
import net.ciaranlyne.trafficapp.GUIDir.Panels.Dashboards.Dashboard5;
import net.ciaranlyne.trafficapp.GUIDir.Panels.NavPanel;
import net.ciaranlyne.trafficapp.GUIDir.Panels.UserLogPanel;
import net.ciaranlyne.trafficapp.GUIDir.Panels.UserRegPanel;
import net.ciaranlyne.trafficapp.LogItem;
import net.ciaranlyne.trafficapp.User;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 */
public class GUI extends JFrame implements ActionListener {

    //Attributes
    private User loggedUser;

    protected JPanel mainPanel;
    protected GridLayout mainLayout;

    private UserLogPanel loginPanel;
    private UserRegPanel regPanel;

    private NavPanel navMenu;

    private AdminUsersPanel adminUsersPanel;
    private AdminLogsPanel adminLogsPanel;

    private Dashboard1 db1;
    private Dashboard2 db2;
    private Dashboard3 db3;
    private Dashboard4 db4;
    private Dashboard5 db5;

    //Constructor
    public GUI() {
        init();
    }

    //Getters
    public User getLoggedUser() {
        return loggedUser;
    }

    //Setters
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    /*
    * func: init
    * use: initialises the frame
    * params: none
    * returns: none
    */
    private void init() {
        this.setTitle("Traffic Data Viewer");

        loginPanel = new UserLogPanel();
        this.getContentPane().add(loginPanel);

        loginPanel.btnRegister.addActionListener(this);
        loginPanel.hiddenBtnLogin.addActionListener(this);

        this.setResizable(false);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /*
    * func: actionPerformed
    * use: override of the implemented action listener class, used to manage when to send users to other panels
    * params: ActionEvent e (the event that this function was called from)
    * returns: none
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        //If user wishes to go to the register panel
        if ("GotoRegister".equals(e.getActionCommand())) {
            loginPanel.setVisible(false);
            loginPanel.invalidate();

            regPanel = new UserRegPanel();
            regPanel.btnSwapToLogin.addActionListener(this);
            regPanel.hiddenBtnReg.addActionListener(this);
            this.getContentPane().add(regPanel);

            this.setVisible(true);
        }

        //If user wishes to go to the login panel
        if ("GotoLogin".equals(e.getActionCommand())) {
            regPanel.setVisible(false);
            regPanel.invalidate();

            loginPanel = new UserLogPanel();
            loginPanel.btnRegister.addActionListener(this);
            loginPanel.hiddenBtnLogin.addActionListener(this);
            this.getContentPane().add(loginPanel);

            this.setVisible(true);
        }

        //If the user has just succesfully logged into the application
        if ("SuccessfulLogin".equals(e.getActionCommand())) {
            successfulLogin();
        }

        //If the user has just successfully registered an account
        if ("SuccessfulReg".equals(e.getActionCommand())) {
            regPanel.setVisible(false);
            regPanel.invalidate();

            loginPanel = new UserLogPanel();
            loginPanel.btnRegister.addActionListener(this);
            loginPanel.hiddenBtnLogin.addActionListener(this);
            this.getContentPane().add(loginPanel);

            this.setVisible(true);
        }

        //If the user requests to logout from the menu bar
        if ("Logout".equals(e.getActionCommand())) {
            clearLoggedInFrame();
            
            LogItem newLog = new LogItem(loggedUser.getUserID(), "ActionLogout", (int) (System.currentTimeMillis() / 1000L));

            loggedUser = null;

            loginPanel = new UserLogPanel();
            loginPanel.btnRegister.addActionListener(this);
            loginPanel.hiddenBtnLogin.addActionListener(this);
            this.getContentPane().add(loginPanel);
            
            newLog.submit();

            JOptionPane.showMessageDialog(this, "Successfully logged out.");

            this.setVisible(true);
        }

        //If the user requests to go to the admin panel to manage users
        if ("AdminUsers".equals(e.getActionCommand())) {
            clearLoggedInFrame();

            adminUsersPanel = new AdminUsersPanel(loggedUser);
            this.getContentPane().add(adminUsersPanel);

            this.setVisible(true);
        }

        //If the user requests to go to the admin panel to view logs
        if ("AdminLogs".equals(e.getActionCommand())) {
            clearLoggedInFrame();

            adminLogsPanel = new AdminLogsPanel();
            this.getContentPane().add(adminLogsPanel);

            this.setVisible(true);
        }

        //If user requests to view dashboard 1
        if ("DB1".equals(e.getActionCommand())) {
            clearLoggedInFrame();

            db1 = new Dashboard1();
            this.getContentPane().add(db1);

            this.setVisible(true);
        }

        //If user requests to view dashboard 2
        if ("DB2".equals(e.getActionCommand())) {
            clearLoggedInFrame();

            db2 = new Dashboard2();
            this.getContentPane().add(db2);

            this.setVisible(true);
        }

        //If user requests to view dashboard 3
        if ("DB3".equals(e.getActionCommand())) {
            clearLoggedInFrame();

            db3 = new Dashboard3();
            this.getContentPane().add(db3);

            this.setVisible(true);
        }

        //if user requests to view dashboard 4
        if ("DB4".equals(e.getActionCommand())) {
            clearLoggedInFrame();

            db4 = new Dashboard4();
            this.getContentPane().add(db4);

            this.setVisible(true);
        }

        //if user requests to view dashboard 5
        if ("DB5".equals(e.getActionCommand())) {
            clearLoggedInFrame();

            db5 = new Dashboard5();
            this.getContentPane().add(db5);

            this.setVisible(true);
        }
    }

    /*
    * func: successfulLogin
    * use: initialises the program beyond the login and register panels after a successful login
    * params: none
    * returns: none
    */
    private void successfulLogin() {
        loggedUser = loginPanel.getLoggedInUser();

        if (regPanel != null) {
            regPanel.setVisible(false);
        }
        loginPanel.setVisible(false);

        navMenu = new NavPanel(loggedUser);
        this.setJMenuBar(navMenu);

        if (loggedUser.isAdmin()) {
            navMenu.btnAdminLogs.addActionListener(this);
            navMenu.btnAdminUsers.addActionListener(this);
        }

        navMenu.btnDash1.addActionListener(this);
        navMenu.btnDash2.addActionListener(this);
        navMenu.btnDash3.addActionListener(this);
        navMenu.btnDash4.addActionListener(this);
        navMenu.btnDash5.addActionListener(this);

        navMenu.btnLogout.addActionListener(this);

        this.setVisible(true);
    }

    /*
    * func: clearLoggedInFrame
    * use: clear JFrame of any possible existing panels
    * params: none
    * returns: none
    */
    private void clearLoggedInFrame() {
        if (loggedUser.isAdmin()) {
            if(adminUsersPanel != null) {
                adminUsersPanel.setVisible(false);
                adminUsersPanel.invalidate();
            }
            if(adminLogsPanel != null) {
                adminLogsPanel.setVisible(false);
                adminLogsPanel.invalidate();
            }
        }

        if(db1 != null) {
            db1.setVisible(false);
            db1.invalidate();
        }
        
        if(db2 != null) {
            db2.setVisible(false);
            db2.invalidate();
        }
        
        if(db3 != null) {
            db3.setVisible(false);
            db3.invalidate();
        }
        
        if(db4 != null) {
            db4.setVisible(false);
            db4.invalidate();
        }
        
        if(db5 != null) {
            db5.setVisible(false);
            db5.invalidate();
        }
    }
}
