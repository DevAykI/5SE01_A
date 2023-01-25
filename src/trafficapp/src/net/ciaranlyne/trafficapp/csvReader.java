package net.ciaranlyne.trafficapp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * File contributors:
 * Aykut Inalan (w1741621)
 */
class csvReader {

    public csvReader() {
    	/*[csvReader.java]
    	 * +The purpose of this is to check if tables exist
    	 * +if tables don't exist create them
    	 * +populate the .db via reading the csv File
    	 * 
    	 */
        Connection con = DB.getConnection();
        Statement stmt = null;
        CreateTables(con); // creating tables to make sure they do indeed exist!

        try {
            //Getting CSV to insert from
            FileInputStream fstream = new FileInputStream("src/dft_rawcount_local_authority_id_182.csv"); // the file to read from
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            ArrayList list = new ArrayList();
            ArrayList IdChecker = new ArrayList();
            int count = 0;
            while ((strLine = br.readLine()) != null) {
                list.add(strLine);
                count++;

            }
            list.remove(0);
            Iterator itr;
            int CurrentGeneratedKey = 0;

            // using prepared statements to batch populate the rows
            PreparedStatement ps = con.prepareStatement("INSERT INTO ta_countpoints (countPointID, regionID, regionName, localAuthorityID, localAuthorityName, roadName, roadType, startJunctionRoadName, endJunctionRoadName, easting, northing, latitude, longitude, linkLengthKm, linkLengthMiles) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)");
            PreparedStatement ps2 = con.prepareStatement("INSERT INTO ta_count (countID,countPointID, dirOfTravel, year) VALUES (?,?,?,?)"); // can get generated keys but don't need to
            PreparedStatement ps3 = con.prepareStatement("INSERT INTO ta_countdata (hour, countDate, countID, pedalCycles, twoWheeledMotorVehicles, carsAndTaxis, busesAndCoaches, lgvs, hgvs2RigidAxle, hgvs3RigidAxle, hgvs4OrMoreRigidAxle, hgvs3or4ArticulatedAxle, hgvs5ArticulatedAxle, hgvs6ArticulatedAxle, allHgvs, allMotorVehicles) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            // deleting previous entries (hacky but works) 
            stmt = con.createStatement();
            String sql = "DELETE FROM ta_countpoints; DELETE FROM ta_count; DELETE FROM ta_countdata;";
            stmt.executeUpdate(sql);
            for (itr = list.iterator(); itr.hasNext();) {
                CurrentGeneratedKey++;
                String str = itr.next().toString();
                String[] splitSt = str.split(",");

                // values
                int count_point_id = Integer.parseInt(splitSt[0]);
                String direction_of_travel = splitSt[1];
                int year = Integer.parseInt(splitSt[2]);
                String count_date = splitSt[3];
                int hour = Integer.parseInt(splitSt[4]);
                int region_id = Integer.parseInt(splitSt[5]);
                String region_name = splitSt[6];
                int local_authority_id = Integer.parseInt(splitSt[7]);
                String local_authority_name = splitSt[8];
                String road_name = splitSt[9];
                String road_type = splitSt[10];
                String start_junction_road_name = splitSt[11];
                String end_junction_road_name = splitSt[12];
                int easting = Integer.parseInt(splitSt[13]);
                int northing = Integer.parseInt(splitSt[14]);
                Double latitude = Double.NaN;
                Double longitude = Double.NaN;
                Double link_length_km = Double.NaN;
                Double link_length_miles = Double.NaN;
                try {
                    latitude = Double.parseDouble(splitSt[15]);
                    longitude = Double.parseDouble(splitSt[16]);
                    link_length_km = Double.parseDouble(splitSt[17]);
                    link_length_miles = Double.parseDouble(splitSt[18]);
                } catch (Exception e) {
                    // empty
                }

                int pedal_cycles = Integer.parseInt(splitSt[19]);
                int two_wheeled_motor_vehicles = Integer.parseInt(splitSt[20]);
                int cars_and_taxis = Integer.parseInt(splitSt[21]);
                int buses_and_coaches = Integer.parseInt(splitSt[22]);
                int lgvs = Integer.parseInt(splitSt[23]);
                int hgvs_2_rigid_axle = Integer.parseInt(splitSt[24]);
                int hgvs_3_rigid_axle = Integer.parseInt(splitSt[25]);
                int hgvs_4_or_more_rigid_axle = Integer.parseInt(splitSt[26]);
                int hgvs_3_or_4_articulated_axle = Integer.parseInt(splitSt[27]);
                int hgvs_5_articulated_axle = Integer.parseInt(splitSt[28]);
                int hgvs_6_articulated_axle = Integer.parseInt(splitSt[29]);
                int all_hgvs = Integer.parseInt(splitSt[30]);
                int all_motor_vehicles = Integer.parseInt(splitSt[31]);

                if (!IdChecker.contains(splitSt[0])) {
                    IdChecker.add(splitSt[0]);

                    //CountPoint
                    ps.setInt(1, count_point_id);
                    ps.setInt(2, region_id);
                    ps.setString(3, region_name);
                    ps.setInt(4, local_authority_id);
                    ps.setString(5, local_authority_name);
                    ps.setString(6, road_name);
                    ps.setString(7, road_type);
                    ps.setString(8, start_junction_road_name);
                    ps.setString(9, end_junction_road_name);
                    ps.setInt(10, easting);
                    ps.setInt(11, northing);
                    ps.setDouble(12, latitude);
                    ps.setDouble(13, longitude);
                    ps.setDouble(14, link_length_km);
                    ps.setDouble(15, link_length_miles);

                    // Count
                    ps2.setInt(1, CurrentGeneratedKey);
                    ps2.setInt(2, count_point_id);
                    ps2.setString(3, direction_of_travel);
                    ps2.setInt(4, year);

                    //CountData
                    ps3.setInt(1, hour);
                    ps3.setString(2, count_date);
                    ps3.setInt(3, CurrentGeneratedKey);
                    ps3.setInt(4, pedal_cycles);
                    ps3.setInt(5, two_wheeled_motor_vehicles);
                    ps3.setInt(6, cars_and_taxis);
                    ps3.setInt(7, buses_and_coaches);
                    ps3.setInt(8, lgvs);
                    ps3.setInt(9, hgvs_2_rigid_axle);
                    ps3.setInt(10, hgvs_3_rigid_axle);
                    ps3.setInt(11, hgvs_4_or_more_rigid_axle);
                    ps3.setInt(12, hgvs_3_or_4_articulated_axle);
                    ps3.setInt(13, hgvs_5_articulated_axle);
                    ps3.setInt(14, hgvs_6_articulated_axle);
                    ps3.setInt(15, all_hgvs);
                    ps3.setInt(16, all_motor_vehicles);

                    // adding to batch
                    ps.addBatch();
                    ps2.addBatch();
                    ps3.addBatch();

                    // setting auto-commit to false or it will be on auto-commit mode which could break the code.
                    con.setAutoCommit(false);

                } else {
                    //System.out.println("Duplicate record for countpoint, with pk:"+ splitSt[0]);

                    // only inserting countdata and count if the primary key already exists
                    ps2.setInt(1, CurrentGeneratedKey);
                    ps2.setInt(2, count_point_id);
                    ps2.setString(3, direction_of_travel);
                    ps2.setInt(4, year);

                    ps3.setInt(1, hour);
                    ps3.setString(2, count_date);
                    ps3.setInt(3, CurrentGeneratedKey);
                    ps3.setInt(4, pedal_cycles);
                    ps3.setInt(5, two_wheeled_motor_vehicles);
                    ps3.setInt(6, cars_and_taxis);
                    ps3.setInt(7, buses_and_coaches);
                    ps3.setInt(8, lgvs);
                    ps3.setInt(9, hgvs_2_rigid_axle);
                    ps3.setInt(10, hgvs_3_rigid_axle);
                    ps3.setInt(11, hgvs_4_or_more_rigid_axle);
                    ps3.setInt(12, hgvs_3_or_4_articulated_axle);
                    ps3.setInt(13, hgvs_5_articulated_axle);
                    ps3.setInt(14, hgvs_6_articulated_axle);
                    ps3.setInt(15, all_hgvs);
                    ps3.setInt(16, all_motor_vehicles);
                    ps2.addBatch();
                    ps3.addBatch();
                    con.setAutoCommit(false);
                }

            }

            // executing prepared statements!
            ps.executeBatch();
            System.out.println("[CountPoint] Succesfully inserted");

            ps2.executeBatch();
            System.out.println("[Count] Succesfully inserted");

            ps3.executeBatch();
            System.out.println("[CountData] Succesfully inserted");

            br.close(); // closing the bufferReader
            con.commit(); // commiting connection
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public void CreateTables(Connection con) {
        Statement stmt = null;
        String createString;
        // Creating the tables if they don't exist!

        createString = "CREATE TABLE if not exists ta_count (\r\n"
                + "    countID      INTEGER     PRIMARY KEY AUTOINCREMENT,\r\n"
                + "    countPointID INTEGER     REFERENCES ta_countpoints (countPointID) \r\n"
                + "                             NOT NULL,\r\n"
                + "    dirOfTravel  CHAR (1),\r\n"
                + "    year         INTEGER (4) \r\n"
                + ");\r\n"
                + "CREATE TABLE if not exists ta_countdata (\r\n"
                + "    hour                    INTEGER (2) NOT NULL,\r\n"
                + "    countDate               DATETIME    NOT NULL,\r\n"
                + "    countID                 INTEGER     REFERENCES ta_count (countID),\r\n"
                + "    pedalCycles             INTEGER,\r\n"
                + "    twoWheeledMotorVehicles INTEGER,\r\n"
                + "    carsAndTaxis            INTEGER,\r\n"
                + "    busesAndCoaches         INTEGER,\r\n"
                + "    lgvs                    INTEGER,\r\n"
                + "    hgvs2RigidAxle          INTEGER,\r\n"
                + "    hgvs3RigidAxle          INTEGER,\r\n"
                + "    hgvs4OrMoreRigidAxle    INTEGER,\r\n"
                + "    hgvs3or4ArticulatedAxle INTEGER,\r\n"
                + "    hgvs5ArticulatedAxle    INTEGER,\r\n"
                + "    hgvs6ArticulatedAxle    INTEGER,\r\n"
                + "    allHgvs                 INTEGER,\r\n"
                + "    allMotorVehicles        INTEGER,\r\n"
                + "    PRIMARY KEY (\r\n"
                + "        hour,\r\n"
                + "        countDate,\r\n"
                + "        countID\r\n"
                + "    )\r\n"
                + ");"
                + "CREATE TABLE if not exists ta_countpoints (\r\n"
                + "    countPointID          INTEGER      PRIMARY KEY AUTOINCREMENT,\r\n"
                + "    regionID              INTEGER,\r\n"
                + "    regionName            VARCHAR (32),\r\n"
                + "    localAuthorityID      INTEGER,\r\n"
                + "    localAuthorityName    VARCHAR (32),\r\n"
                + "    roadName              VARCHAR (32),\r\n"
                + "    roadType              VARCHAR (6),\r\n"
                + "    startJunctionRoadName TEXT,\r\n"
                + "    endJunctionRoadName   TEXT,\r\n"
                + "    easting               INTEGER,\r\n"
                + "    northing              INTEGER,\r\n"
                + "    latitude              DOUBLE,\r\n"
                + "    longitude             DOUBLE,\r\n"
                + "    linkLengthKm          DOUBLE,\r\n"
                + "    linkLengthMiles       DOUBLE\r\n"
                + ");\r\n"
                + "CREATE TABLE if not exists ta_user (\r\n"
                + "    userID       INTEGER       PRIMARY KEY AUTOINCREMENT\r\n"
                + "                               NOT NULL,\r\n"
                + "    userName     VARCHAR (32)  NOT NULL,\r\n"
                + "    userEmail    VARCHAR (64)  NOT NULL,\r\n"
                + "    userPassword VARCHAR (255),\r\n"
                + "    userKey      STRING (30),\r\n"
                + "    admin        BOOLEAN       DEFAULT (0) \r\n"
                + ");\r\n"
                + "CREATE TABLE if not exists ta_actionlogout (\r\n"
                + "    logID INTEGER PRIMARY KEY\r\n"
                + "                REFERENCES ta_actionlog (logID) \r\n"
                + ");\r\n"
                + "CREATE TABLE if not exists ta_actionloginattempt (\r\n"
                + "    logID                   PRIMARY KEY\r\n"
                + "                            REFERENCES ta_actionlog (logID),\r\n"
                + "    loginSuccessful BOOLEAN NOT NULL\r\n"
                + ");\r\n"
                + "CREATE TABLE if not exists ta_actionlog (\r\n"
                + "    logID          INTEGER       PRIMARY KEY AUTOINCREMENT,\r\n"
                + "    userID         INTEGER       REFERENCES ta_user (userID),\r\n"
                + "    actionName     VARCHAR (255),\r\n"
                + "    actionDateTime DATETIME\r\n"
                + ");\r\n"
                + "CREATE TABLE if not exists ta_actionedituser (\r\n"
                + "    logID   PRIMARY KEY\r\n"
                + "            REFERENCES ta_actionlog (logID),\r\n"
                + "    userID  REFERENCES ta_user (userID) \r\n"
                + "            NOT NULL\r\n"
                + ");\r\n";
        // catching errors that the SQL may throw at us
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(createString);
            
            // The reason i don't commit connected here is because i still need it!

        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
