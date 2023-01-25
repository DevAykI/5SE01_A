/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.ciaranlyne.encryption.PasswordUtils;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 * Aykut Inalan (w1741621)
 */
public class User {

    //Attributes
    private int userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private Boolean admin;

    //Constructors
    public User(int userID, String userName, String userEmail, Boolean admin) {
        //Used in edit users admin panel
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = null;
        this.admin = admin;
    }

    public User(String userName, String userEmail, String userPassword) {
        this.userID = -1;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.admin = false;
    }

    public User(String userName, String userPassword) {
        //Used for login attempts
        this.userID = -1;
        this.userName = userName;
        this.userEmail = null;
        this.userPassword = userPassword;
        this.admin = false;
    }

    //Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Boolean isAdmin() {
        return admin;
    }

    /*
    * func: setAdmin
    * use: sets the users admin status in the program and optionally in the database aswell
    * params: 
        - boolean isAdmin (is the user admin?)
        - boolean updateDB (should the admin status also be updated for this user in the database?)
    * returns: none
    */
    public void setAdmin(boolean isAdmin, boolean updateDB) {
        this.admin = isAdmin;

        if (updateDB) {
            //update the admin value in the database to value of isAdmin
            Connection con = DB.getConnection();
            String query;
            
            int intIsAdmin;
            
            if(isAdmin) {
                intIsAdmin = 1;
            } else {
                intIsAdmin = 0;
            }
            
            query = "UPDATE ta_user SET admin = " + intIsAdmin + " WHERE userID = " + this.userID;
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate(query);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /*
    * func: attemptLogin
    * use: looks for a user matching the given user object and if found, returns all information for that user
    * params: User user (user to be logged in, in most cases will be the user it's being called from)
    * returns: User (logged in user with gathered information from db)
    */
    public User attemptLogin(User user) {
        Connection con = DB.getConnection();

        String query = "SELECT * FROM ta_user WHERE userName = ?";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getUserName());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String userName = rs.getString("userName");
                String Email = rs.getString("userEmail").toLowerCase(); // lowercase because cap doesn't matter	
                String pass = rs.getString("userPassword");
                String key = rs.getString("userKey");
                Boolean admin = rs.getBoolean("admin");
                
                if (userName.equals(user.getUserName()) && PasswordUtils.verifyUserPassword(user.getUserPassword(), pass, key)) {

                    // no need to commit as the connection autocommits!
                    user.setUserEmail(Email);
                    user.setUserID(userID);
                    user.setAdmin(admin, false);

                    return user;

                } else {
                    System.out.println("incorrect username/password combo");

                    return null;
                }

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

        System.out.println("username/email does not exist");
        
        return null; // does not exist in the database/ wrong username or password
    }

    /*
    * func: attemptReg
    * use: sees if a user already exists with parsed information, if not creates new user in db
    * params: User user (user with details of requested new account)
    * returns: User (newly registered user)
    */
    public User attemptReg(User user) {
        if (userName == null || userEmail == null || userPassword == null) {
            return null;
        }

        Connection con = DB.getConnection();
        PreparedStatement stmt = null;
        String query = "SELECT * FROM ta_user WHERE userName = ? OR userEmail = ?";
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getUserEmail());
            ResultSet rs = stmt.executeQuery();

            if (rs.next() != false) {
                //email or username already exists
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Couldn't read the database (Empty Database maybe?)");
            return null;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // If we got this far it means the entered fields OK! 
        // Encrypting password
        String salt = PasswordUtils.getSalt(30);
        String userSecurePassword = PasswordUtils.generateSecurePassword(user.getUserPassword(), salt);

        // Protect user's password. The generated value can be stored in DB.
        query = "INSERT INTO ta_user (userName, userEmail, userPassword, userKey) VALUES (?, ?, ?, ?)";

        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getUserEmail());
            stmt.setString(3, userSecurePassword);
            stmt.setString(4, salt);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        query = "SELECT userID FROM ta_user WHERE userName = ?";
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getUserName());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                user.setUserID(rs.getInt("userID"));
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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
