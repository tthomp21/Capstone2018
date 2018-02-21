/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.AidType;
import business.CaseWorker;
import business.ClientAid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author
 */
public class CaseWorkerDB {
    //TODO: add code to access the case workers from the database

    /**
     * Murad Smoqy 02/21/2018
     */
    /**
     *
     * @param CaseWorkerID
     * @return
     */
    public static CaseWorker getClientCaseWorker(int CaseWorkerID) {
        CaseWorker caseWorker = new CaseWorker();

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * "
	    + "FROM SCM.TCF_CASEWORKERS "
	    + "WHERE CASEWORKERID = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, CaseWorkerID);
            rs = ps.executeQuery();

            while (rs.next()) {
                caseWorker.setCaseWorkerID(rs.getInt("caseworkerid"));
                caseWorker.setFirstName(rs.getString("firstName"));
                caseWorker.setLastName(rs.getString("lastName"));
                caseWorker.setEmail(rs.getString("Email"));
                caseWorker.setPhone(rs.getString("phone"));
                caseWorker.setOffice(rs.getString("Office"));
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);

            return null;
        } finally {
            DBUtility.closeResultSet(rs);
            DBUtility.closePreparedStatement(ps);

            return caseWorker;
        }
    }
}
/** these from casework table/DB
 * Column          Data                    
CASEWORKERID              1             
FIRSTNAME       Casey<                  
LASTNAME        Workerman<              
PHONE           4025555511              
EMAIL           cworkerman@email.com<   
OFFICE          a1                      
USERNAME        testing<                
PASSWORD        testing<                
 */
