/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ty
 */
public class InsertDB {

    private ResultSet _rs = null;
    private String _query = "";
    
    public static void insertIntoDB(PreparedStatement ps) throws SQLException
    {
        ps.executeUpdate();
    }
    
}
