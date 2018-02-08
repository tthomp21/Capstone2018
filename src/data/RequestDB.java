/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.AssistanceRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author sayel
 */
public class RequestDB {
    public static int addRequest(String asstType, int clientID, double amount) throws SQLException
    {
        Connection connection = DBConnection.getConnection();
    
        int asstID = 1;
        Date date = Date.valueOf(LocalDate.now());
        
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
            req.setStatus(rs.getInt("status"));
            req.setDateDisbursed(rs.getDate("datedisbursed").toLocalDate());
            req.setAmountPaid(rs.getDouble("amount"));
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
}
