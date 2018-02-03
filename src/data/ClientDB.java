/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.AidType;
import business.Assistance;
import business.ClientAid;
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

        all.add(new AssistanceRequest(1, 1, 1, LocalDate.of(2012, Month.JANUARY, 1), 200 + "", "active", LocalDate.of(2012, Month.FEBRUARY, 10)));
        all.add(new AssistanceRequest(2, 2, 2, LocalDate.of(2013, Month.MARCH, 1), 0 + "", "pending", LocalDate.of(2013, Month.MARCH, 12)));
        all.add(new AssistanceRequest(3, 3, 3, LocalDate.of(2018, Month.JANUARY, 1), 400 + "", "denied", LocalDate.of(2018, Month.JANUARY, 22)));

        all.add(new AssistanceRequest(4, 2, 4, LocalDate.of(2016, Month.APRIL, 1), 600 + "", "active", LocalDate.of(2016, Month.APRIL, 24)));
        all.add(new AssistanceRequest(5, 3, 2, LocalDate.of(2015, Month.JANUARY, 1), 200 + "", "pending", LocalDate.of(2015, Month.JUNE, 2)));
        all.add(new AssistanceRequest(6, 3, 6, LocalDate.of(2017, Month.JANUARY, 1), 200 + "", "active", LocalDate.of(2018, Month.JANUARY, 29)));

        all.add(new AssistanceRequest(7, 2, 31, LocalDate.of(2015, Month.MAY, 1), 200 + "", "active", LocalDate.of(2015, Month.MAY, 31)));
        all.add(new AssistanceRequest(8, 2, 3, LocalDate.of(2018, Month.JANUARY, 1), 200 + "", "Pending", LocalDate.of(2018, Month.JANUARY, 11)));
        all.add(new AssistanceRequest(9, 2, 2, LocalDate.of(2011, Month.JANUARY, 1), 200 + "", "active", LocalDate.of(2011, Month.MARCH, 22)));

        return all;
    }

    /**
     * it might be a good idea to get a client object
     *
     * @return assistance and requestAssistance
     */
    public static ArrayList<AssistanceRequest> getSecondaryAssistances(int clientID) {
        ArrayList<AssistanceRequest> allAssist = new ArrayList<AssistanceRequest>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //insert some data into requestassist table and then try this.
        String query = "SELECT requestID, ra.assistanceID, clientID, dateRequest, status, dateDisbursed, "
	    + "amount, a.assistanceID, description "
	    + "FROM SCM.TCF_REQUESTASSIST ra join SCM.TCF_ASSISTANCE a "
	    + "on ra.assistanceId = a.assistanceID "
	    + "WHERE clientID = ?";

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
                assistRequest.setDateDisbursed(rs.getDate("dateDisbursed").toLocalDate());
                assistRequest.setAmountPaid(rs.getDouble("Amount") + "");

                anAssistance = new Assistance(rs.getInt("assistanceID"), rs.getString("description"));

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
	    + " WHERE clientID = ?";

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

    public static ArrayList<Client> getAllClientsForCaseWorker(CaseWorker caseWorker) {
        ArrayList<Client> allClients = new ArrayList<Client>();

        Connection connection = DBConnection.getConnection();
        String query = "SELECT * FROM scm.TCF_Clients WHERE CaseWorkerID = ?";
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
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                c.setMiddleInit(rs.getString("MiddleInit"));
                c.setEmail(rs.getString("Email"));
                c.setClientID(rs.getInt("ClientID"));
                c.setDependents(rs.getInt("Dependents"));
                c.setPhone(rs.getString("Phone"));
                c.setSSN(rs.getString("SSN"));
                c.setState(rs.getString("State"));
                c.setemZip(rs.getString("emZip"));
                c.setExtZip(rs.getString("extzip"));
                c.setPartnerID(rs.getInt("PartnerID"));
                int married = rs.getInt("MarriageStatus");
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
        String query = "SELECT * FROM scm.TCF_Clients WHERE ClientID = ?";
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
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                c.setMiddleInit(rs.getString("MiddleInit"));
                c.setEmail(rs.getString("Email"));
                c.setClientID(rs.getInt("ClientID"));
                c.setDependents(rs.getInt("Dependents"));
                c.setPhone(rs.getString("Phone"));
                c.setSSN(rs.getString("SSN"));
                c.setState(rs.getString("State"));
                c.setemZip(rs.getString("emZip"));
                c.setExtZip(rs.getString("extzip"));
                c.setPartnerID(rs.getInt("PartnerID"));
                int married = rs.getInt("MarriageStatus");
                if (married == 0) {
	c.setMarried(false);
                } else if (married == 1) {
	c.setMarried(true);
                } else {
	System.out.println("Married not set to 1 or 0.");
                }

            }
        } catch (SQLException e) {
            System.out.println("Error getting client with ID\n" + e);
        } finally {
            DBConnection.freeConnection(connection);
        }
        return c;
    }

}

/**
 * //checking if it can incude all the tables together public static
 * ArrayList<AssistanceRequest> getAllAssistances(int clientID) {
 * ArrayList<AssistanceRequest> allAssist = new ArrayList<AssistanceRequest>();
 *
 * Connection connection = DBConnection.getConnection(); PreparedStatement ps =
 * null; ResultSet rs = null; //insert some data into requestassist table and
 * then try this. String query = "SELECT requestID, ra.assistanceID, clientID,
 * dateRequest, " + "CASE status " + "WHEN '1' THEN active " + "WHEN '0' THEN
 * denied " + "ELSE Processing " + "END assistStatus, " + "dateDisbursed,
 * amount, a.assistanceID, description, " + "FROM SCM.TCF_REQUESTASSIST ra JOIN
 * SCM.TCF_ASSISTANCE a " + "on ra.assistanceId = a.assistanceID " + "WHERE
 * clientID = ? "; //	+ "UNION " //	+ "SELECT dateDisbursed as
 * clientAidDateDisbursed, c.aidtype, aidName " //	+ "FROM SCM.TCF_CLIENTAID c
 * join SCM.TCF_AIDTYPES at " //	+ "ON c.aidtype = at.aidtype " //	+ " WHERE
 * clientID = ?";
 *
 *
 *
 * try { ps = connection.prepareStatement(query); ps.setInt(1, clientID); rs =
 * ps.executeQuery();
 *
 * AssistanceRequest assistRequest = null; ClientAid clientAid = null; AidType
 * aidType = null; while (rs.next()) { assistRequest = new AssistanceRequest();
 * //clientAid = new ClientAid();
 *
 * assistRequest.setRequestID(rs.getInt("requestID"));
 * assistRequest.setAssistanceID(rs.getInt("assistanceID"));
 * assistRequest.setClientID(rs.getInt("clientID"));
 * assistRequest.setRequestDate(rs.getDate("dateRequest").toLocalDate());
 * assistRequest.setStatus(rs.getString("assistStatus"));
 * assistRequest.setDateDisbursed(rs.getDate("dateDisbursed").toLocalDate());
 * assistRequest.setAmountPaid(rs.getDouble("Amount") + "");
 *
 * //clientAid.setClientID(rs.getInt("clientID")); //this could be set to the
 * clientId recieved as argument
 * //clientAid.setClientAidDesc(rs.getString("description"));
 *
 *
 * aidType = new AidType(rs.getInt("aidtype"), rs.getString("aidName"));
 * //clientAid = new ClientAid(rs.getInt("clientID"),
 * rs.getDate("clientAidDateDisbursed").toLocalDate() , aidType);
 *
 * //assistRequest.setAssistance(clientAid);
 *
 *
 *
 * allAssist.add(assistRequest);
 *
 * }
 * } catch (SQLException sqlException) { System.out.println(sqlException);
 *
 * return null; } finally { DBUtility.closeResultSet(rs);
 * DBUtility.closePreparedStatement(ps);
 *
 * return allAssist; }
 *
 * }//end of get secondary assistance  *
 * }
 *
 */
