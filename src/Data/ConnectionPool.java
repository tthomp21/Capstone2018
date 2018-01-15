/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author
 */
public class ConnectionPool {
    
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;
    
    private ConnectionPool(){
        
    }
    
    public static synchronized ConnectionPool getInstance(){
        
        if(pool == null)
            pool = new ConnectionPool();
        
        return pool;
    }
    
    public Connection getConnection(){
        //TODO: add code to get the connection... don't know if it will be the same
        //when connecting to DB2 tables
        
        return null;
    }
    
    public void freeConnection(Connection c){
        //TODO: free the connection from the DB... again don't know how different
        //this will be for a DB2 database
    }
    
    
    
}
