/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.AidNotify;
import business.AidType;
import business.Assistance;
import business.AssistanceRequest;
import business.Client;
import business.ClientAid;
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
    
    
     public static ArrayList<AssistanceRequest> getSecondaryAssistances(int clientID) {
        ArrayList<AssistanceRequest> allAssist = new ArrayList<AssistanceRequest>();
        
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
       
        String query = "SELECT requestID, ra.assistanceID, clientID, dateRequest, status, dateDisbursed, "
	    + "amount, a.assistanceID, description "
	    + "FROM SCM.TCF_REQUESTASSIST ra join SCM.TCF_ASSISTANCE a "
	    + "on ra.assistanceId = a.assistanceID "
	    + "WHERE clientID = ? " 
	    +"Order BY dateRequest desc, dateDisbursed desc ";


        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            rs = ps.executeQuery();

            AssistanceRequest assistRequest = null;
            Assistance anAssistance = null;

            while (rs.next()) {
                assistRequest = new AssistanceRequest();

                assistRequest.setRequestID(rs.getInt("requestID"));
                assistRequest.setAssistanceID(rs.getInt("assistanceID"));
                assistRequest.setClientID(rs.getInt("clientID"));
                assistRequest.setRequestDate(rs.getDate("dateRequest").toLocalDate());
                assistRequest.setStatus(rs.getString("status"));
                
                
                assistRequest.setAmountPaid(rs.getDouble("Amount") + "");
                anAssistance = new Assistance(rs.getInt("assistanceID"), rs.getString("description"));
                try{
                    assistRequest.setDateDisbursed(rs.getDate("dateDisbursed").toLocalDate());
                }
                catch(Exception e)
                {
                }
                
                
                
                
                /*if(disDate != null || disDate != "")
                {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    formatter = formatter.withLocale(Locale.US);
                    assistRequest.setDateDisbursed(LocalDate.parse(disDate, formatter));
                }*/
//                anAssistance.setClientID(rs.getInt("assistanceID"));
//                anAssistance.setAidAmount(rs.getDouble("aidAmount"));
                assistRequest.setAssistance(anAssistance);

                allAssist.add(assistRequest);

            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);

            return null;
        } finally {
            DBUtility.closeResultSet(rs);
            DBUtility.closePreparedStatement(ps);

            return allAssist;
        }

    }//end of get secondary assistance

    public static ArrayList<ClientAid> getPrimaryAssistances(int clientID) {
        ArrayList<ClientAid> clientAidList = new ArrayList<ClientAid>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT clientID, dateDisbursed, c.aidtype, aidName, AidAmount "
	    + "FROM SCM.TCF_CLIENTAID c join SCM.TCF_AIDTYPES a "
	    + "ON c.aidtype = a.aidtype "
	    + "WHERE clientID = ? " 
	    + "Order BY  dateDisbursed desc";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            rs = ps.executeQuery();

            ClientAid clientAid = null;
            AidType aidType = null;

            while (rs.next()) {
                clientAid = new ClientAid();

                clientAid.setClientID(rs.getInt("clientID"));
                clientAid.setClientAidDateDisbursed(rs.getDate("dateDisbursed").toLocalDate());
                clientAid.setAidAmount(rs.getDouble("AidAmount") + "");
                aidType = new AidType(rs.getInt("aidType"), rs.getString("aidName"));
                clientAid.setAidType(aidType);

                clientAidList.add(clientAid);

            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);

            return null;
        } finally {
            DBUtility.closeResultSet(rs);
            DBUtility.closePreparedStatement(ps);

            return clientAidList;
        }

    }//end of get primary assistances
    
}
