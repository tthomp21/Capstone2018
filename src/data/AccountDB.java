// Sayel Rammaha    
// 1/18/18
// database access methods

package data;

import business.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AccountDB {
        
    // returns true if client already has an account
    public static boolean accountExists(int clientID) throws SQLException
    {
        Connection connection = DBConnection.getConnection();     
        String query = "select username, password from scm.tcf_clients where clientID = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, clientID);
        ResultSet rs = ps.executeQuery();

        if (rs.next())
        {
            String un = rs.getString("username");
            if (un == null || un.isEmpty())
            {
                ps.close();
                rs.close();                
                connection.close();
                return false;
            }
            ps.close();
            rs.close();
            connection.close();
            return true;
        }
        ps.close();
        rs.close();
        connection.close();
        return false;
    }
    
    // returns true if clientID exists in [scm.tcf_clients] table
    public static boolean clientIdExists(int clientID) throws SQLException
    {
        Connection connection = DBConnection.getConnection();     
        String query = "select 1 from scm.tcf_clients where clientID = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, clientID);
        ResultSet rs = ps.executeQuery();

        boolean exists = rs.next(); 
        ps.close();
        rs.close();
        connection.close();

        return exists;
    }
    
    // returns true if ssn matches client record by id
    public static boolean verifySSN(int clientID, String enteredSSN) throws SQLException
    {
        Connection connection = DBConnection.getConnection(); 
        String query = "select ssn from scm.tcf_clients where clientID = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, clientID);
        ResultSet rs = ps.executeQuery();

        if (rs.next())
        {
            String actualSSN = rs.getString("ssn");
            connection.close();
            
            if (actualSSN.equals(enteredSSN))
            {
                ps.close();
                rs.close();
                connection.close();
                return true;
            }
        }
        ps.close();
        rs.close();
        connection.close();
        return false;
    }
    
    // returns true if userName is not already taken
    public static boolean isUserNameUnique(String userName, String user) throws SQLException
    {
        Connection connection = DBConnection.getConnection(); 
        String query = "";
        
        if (user.equals("client"))
            query = "select username from scm.tcf_clients";
        else if (user.equals("caseWorker"))
            query = "select username from scm.tcf_caseworkers";
        
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        String currentUserName;
        
        while (rs.next())
        {
            currentUserName = rs.getString("username");
            
            if (currentUserName.equals(userName))
            {
                ps.close();
                rs.close();
                connection.close();
                return false;
            }
        }
        ps.close();
        rs.close();
        connection.close();
        return true;
    }
  
    // returns true if insert was successful
    // returns false if there were any database errors
    public static boolean setClientCredentials(String userName, String password, int clientID) throws SQLException
    {
        Connection connection = DBConnection.getConnection(); 
        String query = "update scm.tcf_clients " +
                "set username = ?, password = ? where clientid = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.setInt(3, clientID);
        
        int rowsAffected = ps.executeUpdate(); 
        ps.close();
        connection.close();       
        
        return (rowsAffected == 1);
    }
    
    // returns true if entered password matches password for entered user name
    public static boolean verifyPassword(String userName, String enteredPassword, String user) throws SQLException
    {
        Connection connection = DBConnection.getConnection(); 
        String query = "";
        
        if (user.equals("client"))
            query = "select password from scm.tcf_clients where username = ?";
        else if (user.equals("caseWorker"))
            query = "select password from scm.tcf_caseworkers where username = ?";        

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();

        if (rs.next())
        {
            String actualPassword = rs.getString("password");
            ps.close();
            rs.close();
            connection.close();
            
            if (actualPassword.equals(enteredPassword))
            {
                return true;
            }
        }
        return false;
    }
    
    // returns client object
    public static Client getClient(String userName) throws SQLException
    {
        Connection connection = DBConnection.getConnection();     
        String query = "select * from scm.tcf_clients where username = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();

        if (rs.next())
        {
            // retrive fields from client record
            int clientID = rs.getInt("clientid");
            String firstName = rs.getString("firstname");
            String mi = rs.getString("middleinit");
            String lastName = rs.getString("lastName");
            String phone = rs.getString("phone");
            String email = rs.getString("email");
            String ssn = rs.getString("ssn");
            String city = rs.getString("city");
            String state = rs.getString("state");
            String zip = rs.getString("emzip");
            String zipExt = rs.getString("extzip");            
            LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
            int married = rs.getInt("marriageStatus");
            LocalDate enrollDate = rs.getDate("enrollmentdate").toLocalDate();
            int dependents = rs.getInt("dependents");
            int partnerID = rs.getInt("partnerID");
            int caseWorkerID = rs.getInt("caseWorkerID");
            
            ps.close();
            rs.close();
            connection.close();
            
            // create client
            Client client = new Client();
            client.setClientID(clientID);
            client.setFirstName(firstName);
            client.setMiddleInit(mi);
            client.setLastName(lastName);
            client.setPhone(phone);
            client.setEmail(email);
            client.setSSN(ssn);
            client.setCity(city);
            client.setState(state);
            client.setZip(zip + "-" + zipExt);
            client.setBirthDate(birthDate);
            client.setMarried(married == 1);
            client.setEnrollmentDate(enrollDate);
            client.setDependents(dependents);
            client.setPartnerID(partnerID);
            client.setCaseWorkerID(caseWorkerID);
            
            return client;
        }
        ps.close();
        rs.close();
        connection.close();
        return null;
    }    

    // returns case worker object
    public static CaseWorker getCaseWorker(String userName) throws SQLException
    {
        Connection connection = DBConnection.getConnection();     
        String query = "select * from scm.tcf_caseworkers where username = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();

        if (rs.next())
        {
            // retrive fields from client record
            int caseWorkerID = rs.getInt("caseworkerid");
            String firstName = rs.getString("firstname");
            String lastName = rs.getString("lastName");
            String phone = rs.getString("phone");
            String email = rs.getString("email");
            String office = rs.getString("office");            
            
            ps.close();
            rs.close();
            connection.close();
            
            // create caseworker
            CaseWorker cw = new CaseWorker();
            cw.setCaseWorkerID(caseWorkerID);
            cw.setFirstName(firstName);
            cw.setLastName(lastName);
            cw.setPhone(phone);
            cw.setEmail(email);
            cw.setOffice(office);
            
            return cw;
        }
        ps.close();
        rs.close();
        connection.close();
        return null;
    }

}
