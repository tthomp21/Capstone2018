/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
/**
 *
 * @author
 */
public class ConnectionPool {
    
    
    private ConnectionPool(){
        
    }
    
    public Connection getConnection(){
        
        
        Connection dbConn = null;
            String jdbcDriver = "com.ibm.db2.jcc.DB2Driver";
                    // Make sure to import db2java.zip, db2jcc.jar, and db2jcc_license_cisuz.jar into WebContent/WEB-INF/lib
            String dbURL = "jdbc:db2://zos7.kctr.marist.edu:5030/DALLASA";
            try
        {
          Class.forName(jdbcDriver);
              //dbConn = DriverManager.getConnection(dbURL, "kc02375", "fall2016");
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
    
    public void freeConnection(Connection c){
        //TODO: free the connection from the DB... again don't know how different
        //this will be for a DB2 database
    }
    
    
    
}
