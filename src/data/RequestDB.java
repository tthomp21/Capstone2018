/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.AssistanceRequest;
import business.MessageResult;
import static data.AccountDB.closeResources;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;


public class RequestDB {
    
    private static HashMap<String, String> dbMap;
    private static HashMap<String, Integer> dbIdMap;
    
    // store assistance descriptions
    // used to convert assistance type to their actual description in the table    
    private static void mapDbNames()
    {        
        if (dbMap == null) {
            dbMap = new HashMap<String, String>();
            dbMap.put("tuition", "tuition");
            dbMap.put("utilities", "utilities");
            dbMap.put("fuel", "gas");
            dbMap.put("registration", "Vehicle Register");
            dbMap.put("repair", "car repair");
            dbMap.put("clothing", "clothing");
            // maps asst types to the way they're currently in the database
            // used in the where clause for where tcf_assistance.description = ?
        }
    }    
    
    // store assistance id's 
    // used to convert assistance type to its id in the table
    private static void mapDbAsstIDs()
    {
        if (dbIdMap == null) {
            dbIdMap = new HashMap<String, Integer>();
            dbIdMap.put("repair", 1);
            dbIdMap.put("fuel", 2);
            dbIdMap.put("tuition", 3);
            dbIdMap.put("clothing", 4);
            dbIdMap.put("registration", 5);
            dbIdMap.put("utilities", 21);
        }
    }
    
    // insert request row 
    public static int addRequest(String asstType, int clientID, double amount) throws SQLException
    {
        Connection connection = DBConnection.getConnection();
        
        Date date = Date.valueOf(LocalDate.now());
        mapDbAsstIDs();
        int asstID = dbIdMap.get(asstType);
        
        String query = "insert into scm.tcf_requestassist " +
                "(assistanceid, clientid, daterequest, status, datedisbursed, amount) " +
                "values (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, asstID);
        ps.setInt(2, clientID);
        ps.setDate(3, date);
        ps.setInt(4, 0);
        ps.setNull(5, java.sql.Types.SMALLINT);
        ps.setDouble(6, amount);
        
        ps.executeUpdate();
        int requestID = 0;
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next())
            requestID = rs.getInt(1);
                
        AccountDB.closeResources(ps, null, connection);

        return requestID;
    }
    
    public static void updateRequest(int requestID, int requestStatus)
    {
        Connection conn = DBConnection.getConnection();
        String query = "UPDATE scm.tcf_requestAssist SET status = ?, Datedisbursed = ? WHERE requestId = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, requestStatus);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setInt(3, requestID);
            ps.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.print(e);
        }
        finally{
            DBConnection.freeConnection(conn);
        }
        
        
        
    }
    
    // retrieve request row
    public static void getRequest(int clientID)
    {
        ArrayList<AssistanceRequest> requests = new ArrayList<AssistanceRequest>();
        AssistanceRequest req = new AssistanceRequest();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        String query = "SELECT * FROM scm.tcf_requestassist WHERE clientid = ?";
        
        try{
        
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, clientID);
            rs = ps.executeQuery();
            while(rs.next())
            {
                req.setRequestID(rs.getInt("requestId"));
                req.setAssistanceID(rs.getInt("assistanceID"));
                req.setClientID(rs.getInt("clientid"));
                req.setRequestDate(rs.getDate("daterequest").toLocalDate());
                req.setStatus(rs.getString("status"));
                req.setDateDisbursed(rs.getDate("datedisbursed").toLocalDate());
                req.setAmountPaid(rs.getString("amount"));
            }
        
        }
        catch(SQLException e)
        {
            
        }
        finally
        {
            DBConnection.freeConnection(conn);
        }
        
    }


    // **********************************************************************
    //                            validation
    // **********************************************************************
    
    // checks to see if client has received a type of assistance less times than the limit
    public static boolean checkRequestOccurrenceLimit(String requestType, int clientID, int maxOccurrences, int range) throws SQLException
    {
        // calculate begginning date of range to check within
        LocalDate searchStart = LocalDate.now().minusMonths(range);
        
        Connection connection = DBConnection.getConnection();
        String query = "select count(*) as occurrences from scm.tcf_requestassist where " +
                "clientid = ? and datedisbursed > ? and assistanceid in " +
                "(select assistanceid from scm.tcf_assistance where description = ?)";
               
        PreparedStatement ps = connection.prepareStatement(query);
        
        ps.setInt(1, clientID);        
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ps.setString(2, searchStart.format(dtf));        
        
        mapDbNames();
        String desc = dbMap.get(requestType);
        ps.setString(3, desc);        
        
        ResultSet rs = ps.executeQuery();

        if (rs.next()) 
        {
            int occurrences = rs.getInt("occurrences");            
            closeResources(ps, rs, connection);
            return occurrences < maxOccurrences;
        }
        else 
        {
            closeResources(ps, rs, connection);
            throw new SQLException();
        }
    }
    
    // checks to see if client has received an amount in excess of the limit for an assistance type
    public static boolean checkRequestAmountLimit(String requestType, int clientID, double maxAmount, int range)  throws SQLException
    {
        // calculate begginning date of range to check within
        LocalDate searchStart = LocalDate.now().minusMonths(range);
        
        Connection connection = DBConnection.getConnection();
        String query = "select sum(amount) as total from scm.tcf_requestassist where " +
                "clientid = ? and datedisbursed > ? and assistanceid in " +
                "(select assistanceid from scm.tcf_assistance where description = ?)";

        
        PreparedStatement ps = connection.prepareStatement(query);        
        
        ps.setInt(1, clientID);        
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ps.setString(2, searchStart.format(dtf));
        
        mapDbNames();        
        String desc = dbMap.get(requestType);
        ps.setString(3, desc);        
        
        ResultSet rs = ps.executeQuery();

        if (rs.next()) 
        {
            double total = rs.getDouble("total");            
            closeResources(ps, rs, connection);
            return total < maxAmount;
        }
        else 
        {
            closeResources(ps, rs, connection);
            throw new SQLException();
        }
    }
    
    // checks for current sanction, returns the end date of the sanction if active
    public static MessageResult checkForSanction(int clientID) throws SQLException
    {       
        Connection connection = DBConnection.getConnection();
        String query = "SELECT SANCTIONDATE, SANCTIONLENGTH, " +
                "CASE SANCTIONLENGTH " +
                    "WHEN 1 THEN SANCTIONDATE + 1 MONTH "  +
                    "WHEN 2 THEN SANCTIONDATE + 3 MONTH "  +
                    "WHEN 3 THEN SANCTIONDATE + 12 MONTH " +
                "END AS SANCTIONEND " +                
                "FROM SCM.TCF_SANCTIONS " +
                "WHERE CLIENTID = ? " +
                "ORDER BY SANCTIONDATE DESC";                            
               
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, clientID);    
        
        ResultSet rs = ps.executeQuery();

        MessageResult result = new MessageResult();
        if (rs.next()) 
        {
            LocalDate sanctionEndDate = rs.getDate("sanctionend").toLocalDate();
            if (sanctionEndDate.isAfter(LocalDate.now()))
            {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                String endDate = sanctionEndDate.format(dtf);
                result.setMessage(endDate);    
                result.setResult(true);                            
            }
            else 
            {
                result.setResult(false);
            }
        }
        else 
        {
            result.setResult(false);            
        }
        
        closeResources(ps, rs, connection);
        return result;
    }
}
