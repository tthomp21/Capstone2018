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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 *
 * @author Tyler
 */
public class HoursDB {
    
    public static ArrayList<ClientHoursArgs> getClientHours(int clientID, int month, int year){
        ArrayList<ClientHoursArgs> args = new ArrayList<ClientHoursArgs>();
        ClientHoursArgs a = null;
        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM scm.TCF_hours WHERE ClientID = ? AND MONTH(Date) = ? AND YEAR(Date) = ?";
        ResultSet rs = null;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            ps.setInt(2, month);
            ps.setInt(3, year);

            rs = ps.executeQuery();
            while (rs.next()) {
                a = new ClientHoursArgs();
                a.setDate(rs.getDate("date"));
                a.setHours(rs.getDouble("hours"));
                args.add(a);
            }
            
        } catch (SQLException e) {
                System.out.println("Error getting client hours with ID\n" + e );
        } finally {
            DBConnection.freeConnection(connection);
        }
        
        return args;
    }
    
    public static double getClientHoursForSingleDate(int clientID, LocalDate date)
    {
        double hours = 0;
        Connection connection = DBConnection.getConnection();
        String query = "Select hours from scm.tcf_hours where clientID = ? AND date = ?";
        ResultSet rs = null;
        
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            ps.setDate(2, Date.valueOf(date));
            
            rs = ps.executeQuery();
            while(rs.next())
            {
                hours = rs.getDouble("hours");
            }
        }catch (SQLException e){
            
        }finally{
            DBConnection.freeConnection(connection);
        }
        
        return hours;
    }
    
    
    
    public static void insertClientHours(double mon, double tue, double wed, double thur, double fri, LocalDate monday, LocalDate tuesday, LocalDate wednesday, LocalDate thursday, LocalDate friday, int clientId)
    {
        Connection connection = DBConnection.getConnection();
        String query = "insert into scm.tcf_hours " +
                "(clientid, date, hours) " +
                "values (?, ?, ?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            ps.setDate(2, Date.valueOf(monday));
            ps.setDouble(3, mon);
            
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.print("error inserting monday hours");
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            ps.setDate(2, Date.valueOf(tuesday));
            ps.setDouble(3, tue);
            
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.print("error inserting tuesday hours");
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            ps.setDate(2, Date.valueOf(wednesday));
            ps.setDouble(3, wed);
            
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.print("error inserting wednesday hours");
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            ps.setDate(2, Date.valueOf(thursday));
            ps.setDouble(3, thur);
            
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.print("error inserting thursday hours");
        }
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientId);
            ps.setDate(2, Date.valueOf(friday));
            ps.setDouble(3, fri);
            
            ps.executeUpdate();
        }
        catch(Exception e)
        {
            System.out.print("error inserting friday hours");
        }
        
        
        DBConnection.freeConnection(connection);
    }
    
}

