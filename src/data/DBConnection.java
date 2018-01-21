package data;

import java.sql.*;

public class DBConnection {
    private DBConnection(){ }
    
    // creates a connection to our database 
    public static Connection getConnection() {
        Connection dbConn = null;
        String jdbcDriver = "com.ibm.db2.jcc.DB2Driver";                
        String dbURL = "jdbc:db2://zos7.kctr.marist.edu:5030/DALLASA";
        
        try
        {
            Class.forName(jdbcDriver);            
            dbConn = DriverManager.getConnection(dbURL, "kc02511", "cashflow");
        }
        catch (ClassNotFoundException e)
        {
          //throw new ServletException("JDBC driver not found:" + jdbcDriver);
        }
        catch (SQLException e)
        {
          //throw new ServletException("Unable to connect to: " + dbURL + e);
        }
        catch (Exception e)
        {
          //throw new ServletException("Error: " + e);
        }
            
        return dbConn;
    }
    
    public static void freeConnection(Connection c){
        //TODO: free the connection from the DB... again don't know how different
        //this will be for a DB2 database
    }
    
    
    
}