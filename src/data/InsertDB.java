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

/**
 *
 * @author Ty
 */
public class InsertDB {
    
    private Connection _connection = DBConnection.getConnection();
    private PreparedStatement _ps = null;
    private ResultSet _rs = null;
    private String _query = "";
    
    public static void insertClient(Client client)
    {
        
    }
    
    public static void insertInstitution()
    {
        
    }
    
    public static void insertCaseWorker(CaseWorker worker)
    {
        
    }
    
    public static void insertHours(ClientHoursArgs hours)
    {
        
    }
    
    public static void insertSanction()
    {
        
    }
    
    public static void insertClientInst()
    {
        
    }
    
    public static void insertAid()
    {
        
    }
    
    public static void insertAssistance()
    {
        
    }
    
    public static void insertEmployer()
    {
        
    }
    
    public static void insertClientEmployer()
    {
        
    }
    
    public static void insertClientAssistance()
    {
        
    }
    
    public static void insertRequestAssist()
    {
        
    }
}
