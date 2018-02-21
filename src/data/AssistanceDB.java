/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.AidNotify;
import business.Client;
import business.Hours;
import business.Sanction;
import static data.ClientDB.getClientWithID;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Murad
 */
public class AssistanceDB {
    
    
    
     public static ArrayList<Hours> getClientHoursForWholeMonth(int clientID) {
        
        LocalDate firstOfThisMonthDate = LocalDate.now().withDayOfMonth(1);
        LocalDate lastOfThisMonthDate = LocalDate.now().withDayOfMonth(firstOfThisMonthDate.lengthOfMonth()); // always get end of the month
        ArrayList<Hours> allHours = new ArrayList<Hours>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT *  " //hours, clientID, date
	    + "FROM SCM.TCF_HOURS "
	    + "WHERE clientID = ? "
	    + "AND (DATE >= ? )  "
	    + "AND (DATE <= ? )  ";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            ps.setDate(2, Date.valueOf(firstOfThisMonthDate));
            ps.setDate(3,Date.valueOf(lastOfThisMonthDate) );
            rs = ps.executeQuery();

            Client client = getClientWithID(clientID);
            Hours h = null;
            while (rs.next()) {
                h = new Hours();
                h.setNumberOfHours(rs.getDouble("hours"));
                h.setDateHoursEntered(rs.getDate("date").toLocalDate());
                h.setaClientInHours(client);

                allHours.add(h);

            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException);

            return null;
        } finally {
            DBUtility.closeResultSet(rs);
            DBUtility.closePreparedStatement(ps);

            return allHours;
        }

    }

    public static ArrayList<AidNotify> getClientSanctions(int clientID) {
        ArrayList<AidNotify> aidNotifyList = new ArrayList<AidNotify>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT *  " 
	    + "FROM SCM.TCF_AidNotify "
	    + "WHERE clientID = ? ";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            rs = ps.executeQuery();

           AidNotify  an = null;
            while (rs.next()) {
                an = new AidNotify();
                
	an.setClientID(clientID); // this can be ignored, since it is the client him/her-self
	an.setAidTypeID(rs.getInt("AidType"));
	an.setRecertificationDate(rs.getDate("ExpireDate").toLocalDate());
	
	aidNotifyList.add(an);
              
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException);

            return null;
        } finally {
            DBUtility.closeResultSet(rs);
            DBUtility.closePreparedStatement(ps);

            return aidNotifyList;
        }

    }
    
}
