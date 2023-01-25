/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.ciaranlyne.trafficapp.GUIDir.Models.PanelModel;
import net.ciaranlyne.trafficapp.User;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 */
public class UserRegPanel extends PanelModel implements ActionListener{
    
    //Attribute
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel passCheckLabel;

    private static JTextField txtName;
    private static JTextField txtEmail;
    private static JPasswordField txtPass;
    private static JPasswordField txtPassCheck;
    
    public JButton btnSubmit;
    public JButton btnSwapToLogin;
    public JButton hiddenBtnReg;
    
    //Constructor
    public UserRegPanel() {
        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        txtName = new JTextField();
        
        emailLabel = new JLabel();
        emailLabel.setText("E-Mail:");
        txtEmail = new JTextField();

        passwordLabel = new JLabel();
        passwordLabel.setText("Password: ");
        txtPass = new JPasswordField();
        
        passCheckLabel = new JLabel();
        passCheckLabel.setText("Repeat Password: ");
        txtPassCheck = new JPasswordField();

        this.setLayout(new GridLayout(10, 1));

        btnSubmit = new JButton("Register");
        btnSubmit.setActionCommand("AttemptReg");
        btnSubmit.addActionListener(this);
        
        hiddenBtnReg = new JButton();
        hiddenBtnReg.setActionCommand("SuccessfulReg");
        
        btnSwapToLogin = new JButton("Already have an account? Login");
        btnSwapToLogin.setActionCommand("GotoLogin");
        
        this.add(usernameLabel);
        this.add(txtName);
        this.add(emailLabel);
        this.add(txtEmail);
        this.add(passwordLabel);
        this.add(txtPass);
        this.add(passCheckLabel);
        this.add(txtPassCheck);
        this.add(btnSubmit);
        this.add(btnSwapToLogin);
    }
    
    /*
    * func: actionPerformed
    * use: override for ActionListener. Used in this case when user attempts to register
    * params: ActionEvent e
    * returns: none
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        //If user attempts to register...
        if("AttemptReg".equals(e.getActionCommand())) {
            
            //Gather info from text fields
            String username = txtName.getText();
            String email = txtEmail.getText();
            String password = String.valueOf(txtPass.getPassword());
            String passwordConf = String.valueOf(txtPassCheck.getPassword());
            
            //Test for empty fields, return and inform if any found
            if(username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all the fields.");
                return;
            }
            
            //Test if password matches password confirmation field
            if(!password.equals(passwordConf)) {
                JOptionPane.showMessageDialog(this, "The given passwords don't match! Please try again.");
                return;
            }
            
            //Regular expression for email
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            Pattern regexPattern = Pattern.compile(regex);
            
            //test email regex against gathered email
            if(!regexPattern.matcher(email).matches()) {
                JOptionPane.showMessageDialog(this, "Invalid email format!");
                return;
            }

            //attempt register
            User attemptUser = new User(username, email, password);

            attemptUser = attemptUser.attemptReg(attemptUser);

            if(attemptUser == null) {
                JOptionPane.showMessageDialog(this, "Unable to register! It's possible a user already exists with that username or email. Please try again.");
            } else {
                JOptionPane.showMessageDialog(this, "Registration successful! You may now login to your new account.");
                hiddenBtnReg.doClick();
            }
        }
    }
}
