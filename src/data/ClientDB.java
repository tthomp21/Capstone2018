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
import business.Hours;
import business.Sanction;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author
 */
public class ClientDB {
    //TODO: add code to access the clients from the database
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
	    + "AND (DATE <= ? )  "
	    + "ORDER BY DATE asc ";

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

    public static ArrayList<Sanction> getClientSanctions(int clientID) {
        ArrayList<Sanction> clientSanctions = new ArrayList<Sanction>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT *  " //hours, clientID, date
	    + "FROM SCM.TCF_SANCTIONS "
	    + "WHERE clientID = ? "
	    + " ORDER BY sanctionLength desc ";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            rs = ps.executeQuery();

            Sanction sanc = null;
            Hours h = null;
            while (rs.next()) {

                sanc = new Sanction(rs.getInt("sanctionID"), rs.getDate("sanctionDate").toLocalDate(), rs.getInt("sanctionLength"));

                clientSanctions.add(sanc);
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException);

            return null;
        } finally {
            DBUtility.closeResultSet(rs);
            DBUtility.closePreparedStatement(ps);

            return clientSanctions;
        }

    }

    public static ArrayList<Hours> getClientHoursByDates(int clientID, LocalDate fromDate, LocalDate toDate) {
       
         ArrayList<Hours> allHours = new ArrayList<Hours>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT *  " //hours, clientID, date
	    + "FROM SCM.TCF_HOURS "
	    + "WHERE clientID = ? "
	    + "AND (DATE >= ? ) "
	    + "AND (DATE <= ? ) ";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            ps.setDate(2, Date.valueOf(fromDate));
            ps.setDate(3, Date.valueOf(toDate));
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
}

