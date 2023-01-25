/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Panels;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.ciaranlyne.trafficapp.GUIDir.Models.PanelModel;
import net.ciaranlyne.trafficapp.LogItem;
import net.ciaranlyne.trafficapp.User;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 * Tevin
 * 
 * @Admin_Login[UserName/Email:Admin/Admin@email.com | Password: Password ] Testing purposes only!
 */
public class UserLogPanel extends PanelModel implements ActionListener{
    //Attributes
    private User loggedInUser;
    
    private JLabel loginLabel;
    private JLabel codeLabel;
    private JLabel errorLabel;

    private static JTextField txtName;
    private static JPasswordField txtPass;
    
    public JButton btnSubmit;
    public JButton btnRegister;
    
    public JButton hiddenBtnLogin;

    //Constructor
    public UserLogPanel() {
        loginLabel = new JLabel();
        loginLabel.setText("Username: ");
        txtName = new JTextField();

        codeLabel = new JLabel();
        codeLabel.setText("Password: ");
        txtPass = new JPasswordField();
        
        errorLabel = new JLabel();
        errorLabel.setText("Incorrect username or password!");
        errorLabel.setForeground(Color.red);

        this.setLayout(new GridLayout(8, 1));


        btnSubmit = new JButton("Login");
        btnSubmit.setActionCommand("AttemptLogin");
        btnSubmit.addActionListener(this);
        
        hiddenBtnLogin = new JButton();
        hiddenBtnLogin.setActionCommand("SuccessfulLogin");
        
        btnRegister = new JButton("Register for free");
        btnRegister.setActionCommand("GotoRegister");
        
        this.add(loginLabel);
        this.add(txtName);
        this.add(codeLabel);
        this.add(txtPass);
        this.add(errorLabel);
        errorLabel.setVisible(false);
        this.add(btnSubmit);
        this.add(btnRegister);

    }
    
    /*
    * func: actionPerformed
    * use: override for ActionListener. Used in this case when user attempts to login
    * params: ActionEvent e
    * returns: none
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        //If user is attempting to login...
        if("AttemptLogin".equals(e.getActionCommand())) {
            //Get and test username and password if user exists with given combination...
            String username = txtName.getText();
            String password = String.valueOf(txtPass.getPassword());

            User attemptUser = new User(username, password);

            attemptUser = attemptUser.attemptLogin(attemptUser);

            if(attemptUser == null) {
                //if login failed...
                //log activity
                LogItem newLog = new LogItem(-1, "ActionLoginAttempt", (int) (System.currentTimeMillis() / 1000L), 0);
                newLog.submit();
                //display error
                errorLabel.setVisible(true);
            } else {
                //if login success
                //log acitivty
                LogItem newLog = new LogItem(attemptUser.getUserID(), "ActionLoginAttempt", (int) (System.currentTimeMillis() / 1000L), 1);
                newLog.submit();
                loggedInUser = attemptUser;
                //send login action to GUI from hidden button
                hiddenBtnLogin.doClick();
            }
        }
    }
    
    //Getter
    public User getLoggedInUser() {
        return loggedInUser;
    }
	
}
