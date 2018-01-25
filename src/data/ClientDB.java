/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.Assistance;
import business.AssistanceRequest;
import business.CaseWorker;
import business.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 *
 * @author
 */
public class ClientDB {
    //TODO: add code to access the clients from the database

    public static ArrayList<AssistanceRequest> getTestData() {
        ArrayList<AssistanceRequest> all = new ArrayList<AssistanceRequest>();

        all.add(new AssistanceRequest(1, 1, 1, LocalDate.of(2012, Month.JANUARY, 1), 200, "active", LocalDate.of(2012, Month.FEBRUARY, 10)));
        all.add(new AssistanceRequest(2, 2, 2, LocalDate.of(2013, Month.MARCH, 1), 0, "pending", LocalDate.of(2013, Month.MARCH, 12)));
        all.add(new AssistanceRequest(3, 3, 3, LocalDate.of(2018, Month.JANUARY, 1), 400, "denied", LocalDate.of(2018, Month.JANUARY, 22)));

        all.add(new AssistanceRequest(4, 2, 4, LocalDate.of(2016, Month.APRIL, 1), 600, "active", LocalDate.of(2016, Month.APRIL, 24)));
        all.add(new AssistanceRequest(5, 3, 2, LocalDate.of(2015, Month.JANUARY, 1), 200, "pending", LocalDate.of(2015, Month.JUNE, 2)));
        all.add(new AssistanceRequest(6, 3, 6, LocalDate.of(2017, Month.JANUARY, 1), 200, "active", LocalDate.of(2018, Month.JANUARY, 29)));

        all.add(new AssistanceRequest(7, 2, 31, LocalDate.of(2015, Month.MAY, 1), 200, "active", LocalDate.of(2015, Month.MAY, 31)));
        all.add(new AssistanceRequest(8, 2, 3, LocalDate.of(2018, Month.JANUARY, 1), 200, "Pending", LocalDate.of(2018, Month.JANUARY, 11)));
        all.add(new AssistanceRequest(9, 2, 2, LocalDate.of(2011, Month.JANUARY, 1), 200, "active", LocalDate.of(2011, Month.MARCH, 22)));

        return all;
    }

    /**
     * it might be a good idea to get a client object
     *
     * @return
     */
    public static ArrayList<AssistanceRequest> getAllAssistances(int clientID) {
        ArrayList<AssistanceRequest> allAssist = new ArrayList<AssistanceRequest>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //insert some data into requestassist table and then try this.
        String query = "SELECT requestID"
	    + ", ra.assistanceID"
	    + ", clientID"
	    + ", dateRequest"
	    + ", status"
	    + ", dateDisbursed"
	    + ", amount"
	    + ", a.assistanceID"
	    + ", description"
	    + "FROM SCM.TCF_REQUESTASSIST ra left join SCM.TCF_ASSIISTANCE a "
	    + " on ra.assistanceId = a.assistanceID "
	    + " WHERE clientID = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            rs = ps.executeQuery();

            AssistanceRequest assistRequest = null;
            Assistance anAssistance = null;            
            
            while (rs.next()) {
                assistRequest = new AssistanceRequest();
                anAssistance  = new Assistance();
                
                assistRequest.setRequestID(rs.getInt("requestID"));
                assistRequest.setAssistanceID(rs.getInt("assistanceID"));
                assistRequest.setClientID(rs.getInt("clientID"));
                assistRequest.setRequestDate(rs.getDate("dateRequest").toLocalDate());
                assistRequest.setStatus(rs.getString("status"));
                assistRequest.setDateDisbursed(rs.getDate("dateDisbursed").toLocalDate());
                assistRequest.setAmountPaid(rs.getDouble("Amount"));
                
                anAssistance.setAssistanceID(rs.getInt("assistanceID"));
                anAssistance.setAssistanceDescription(rs.getString("description"));
                
                assistRequest.setAnAssistance(anAssistance);
                
                

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

    }

    //end of get all assistanc
    public static ArrayList<Client> getAllClientsForCaseWorker(CaseWorker caseWorker) {
        ArrayList<Client> allClients = new ArrayList<Client>();

        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM TCF_Clients WHERE CaseWorkerID = ?";
        ResultSet rs = null;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, caseWorker.getCaseWorkerID());

            rs = ps.executeQuery();
            while (rs.next()) {
                Client c = new Client();
                c.setBirthDate(rs.getDate("BirthDate").toLocalDate());
                c.setEnrollmentDate(rs.getDate("EnrollmentDate").toLocalDate());
                c.setCaseWorkerID(rs.getInt("CaseWorkerID"));
                c.setCity(rs.getString("City"));
                c.setCity(rs.getString("FirstName"));
                c.setCity(rs.getString("LastName"));
                c.setCity(rs.getString("MiddleInit"));
                c.setCity(rs.getString("Email"));
                c.setClientID(rs.getInt("ClientID"));
                c.setDependents(rs.getInt("Dependents"));
                c.setPhone(rs.getString("Phone"));
                c.setSSN(rs.getString("SSN"));
                c.setState(rs.getString("State"));
                c.setemZip(rs.getString("Zip"));
                c.setPartnerID(rs.getInt("PartnerID"));
                int married = rs.getInt("Married");
                if (married == 0) {
	c.setMarried(false);
                } else if (married == 1) {
	c.setMarried(true);
                } else {
	System.out.println("Married not set to 1 or 0.");
                }

                allClients.add(c);
            }
        } catch (SQLException e) {

        } finally {
            DBConnection.freeConnection(connection);
        }

        return allClients;
    }

    public static Client getClientWithID(int clientID) {
        Client c = new Client();

        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM TCF_Clients WHERE ClientID = ?";
        ResultSet rs = null;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);

            rs = ps.executeQuery();
            while (rs.next()) {
                c.setBirthDate(rs.getDate("BirthDate").toLocalDate());
                c.setEnrollmentDate(rs.getDate("EnrollmentDate").toLocalDate());
                c.setCaseWorkerID(rs.getInt("CaseWorkerID"));
                c.setCity(rs.getString("City"));
                c.setCity(rs.getString("FirstName"));
                c.setCity(rs.getString("LastName"));
                c.setCity(rs.getString("MiddleInit"));
                c.setCity(rs.getString("Email"));
                c.setClientID(rs.getInt("ClientID"));
                c.setDependents(rs.getInt("Dependents"));
                c.setPhone(rs.getString("Phone"));
                c.setSSN(rs.getString("SSN"));
                c.setState(rs.getString("State"));
                c.setemZip(rs.getString("Zip"));
                c.setPartnerID(rs.getInt("PartnerID"));
                int married = rs.getInt("Married");
                if (married == 0) {
	c.setMarried(false);
                } else if (married == 1) {
	c.setMarried(true);
                } else {
	System.out.println("Married not set to 1 or 0.");
                }

            }
        } catch (SQLException e) {

        } finally {
            DBConnection.freeConnection(connection);
        }
        return c;
    }

}
