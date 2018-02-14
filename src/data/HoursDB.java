/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.Client;
import business.ClientHoursArgs;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ty
 */
public class HoursDB {
    
    public static ArrayList<ClientHoursArgs> getClientHours(int clientID){
        ArrayList<ClientHoursArgs> args = new ArrayList<ClientHoursArgs>();
        ClientHoursArgs a = new ClientHoursArgs();
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM scm.TCF_hours WHERE ClientID = ?";
        ResultSet rs = null;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);

            rs = ps.executeQuery();
            while (rs.next()) {
                a.setDate(rs.getDate("date"));
                a.setHours(rs.getBigDecimal("hours"));
                
            }
            args.add(a);
        } catch (SQLException e) {
                System.out.println("Error getting client hours with ID\n" + e );
        } finally {
            DBConnection.freeConnection(connection);
        }
        
        return args;
    }
    
    public static void insertClientHours(int mon, int tue, int wed, int thur, int fri, Date monday, Date tuesday, Date wednesday, Date thurday, Date friday, int clientId)
    {
        Connection connection = DBConnection.getConnection();
        String query = "insert into scm.tcf_hours " +
                "(clientid, date, hours) " +
                "values (?, ?, ?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            ps.setDate(2, monday);
            ps.setDouble(2, mon);
        }
        catch(Exception e)
        {
            
        }
    }
    
}

