/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.ClientHoursArgs;
import data.ClientDB;
import data.DBConnection;
import data.HoursDB;
import data.InsertDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.jni.Local;

/**
 *
 * @author tt283071
 */
@WebServlet(name = "DBInsertController", urlPatterns = {"/DBInsertController"})
public class DBInsertController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String url = "/views/insertToDB.jsp";
        Connection conn = DBConnection.getConnection();
        HttpSession session = request.getSession();
        PreparedStatement ps;
        String query;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        
        if(action == null)
            action = "";
        
        try{
        switch(action){
                case "":
                break;
                case "institutionDB":
                    query = "insert into scm.tcf_institution " +
                "(institutionid, name, description, city, street, state, zip, extzip) " +
                "values (?, ?, ?, ?, ?, ?, ? ,?)";
                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, Integer.parseInt((String)session.getAttribute("institutionID")));
                    ps.setString(2, (String)session.getAttribute("institutionName"));
                    ps.setString(3, (String)session.getAttribute("institutionDesc"));
                    ps.setString(4, (String)session.getAttribute("institutionCity"));
                    ps.setString(5, (String)session.getAttribute("institutionStreet"));
                    ps.setString(6, (String)session.getAttribute("institutionState"));
                    ps.setString(7, (String)session.getAttribute("institutionZip"));
                    ps.setString(8, (String)session.getAttribute("institutionExtZip"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case "caseworkerDB":
                    query = "insert into scm.tcf_caseworkers " +
                "(caseworkerid, firstname, lastname, phone, email, office, username, password) " +
                "values (?, ?, ?, ?, ?, ?, ? ,?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, Integer.parseInt((String)session.getAttribute("caseworkerID")));
                    ps.setString(2, (String)session.getAttribute("caseworkerFName"));
                    ps.setString(3, (String)session.getAttribute("caseworkerLName"));
                    ps.setString(4, (String)session.getAttribute("caseworkerPhone"));
                    ps.setString(5, (String)session.getAttribute("caseworkerEmail"));
                    ps.setString(6, (String)session.getAttribute("caseworkerOffice"));
                    ps.setString(7, (String)session.getAttribute("caseworkerUserName"));
                    ps.setString(8, (String)session.getAttribute("caseworkerPassword"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case "clientDB":
                    query = "insert into scm.tcf_clients " +
                "(clientid, ssn, firstname, middleinit, lastname, city, street, state, emzip, extzip, phone, email, birthdate, marriagestatus, enrollmentdate, dependents, partnerid, caseworkerid, username, password) " +
                "values (?, ?, ?, ?, ?, ?, ? ,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, Integer.parseInt((String)session.getAttribute("clientID")));
                    ps.setString(2, (String)session.getAttribute("clientSSN"));
                    ps.setString(3, (String)session.getAttribute("clientFName"));
                    ps.setString(4, (String)session.getAttribute("clientMid"));
                    ps.setString(5, (String)session.getAttribute("clientLName"));
                    ps.setString(6, (String)session.getAttribute("clientCity"));
                    ps.setString(7, (String)session.getAttribute("clientStreet"));
                    ps.setString(8, (String)session.getAttribute("clientState"));
                    ps.setString(9, (String)session.getAttribute("clientEmZip"));
                    ps.setString(10, (String)session.getAttribute("clientExtZip"));
                    ps.setString(11, (String)session.getAttribute("clientPhone"));
                    ps.setString(12, (String)session.getAttribute("clientEmail"));
                    ps.setDate(13, java.sql.Date.valueOf((String)session.getAttribute("clientBDay")));
                    ps.setInt(14, (int)session.getAttribute("clientMarried"));
                    ps.setDate(15, java.sql.Date.valueOf((String)session.getAttribute("clientEnrollDate")));
                    ps.setInt(16, (int)session.getAttribute("clientDependents"));
                    ps.setInt(17, (int)session.getAttribute("clientPartner"));
                    ps.setInt(18, (int)session.getAttribute("clientCaseWorker"));
                    ps.setString(19, (String)session.getAttribute("clientUserName"));
                    ps.setString(20, (String)session.getAttribute("clientPass"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case "hoursDB":
                    query = "insert into scm.tcf_hours " +
                "(clientid, date, hours) " +
                "values (?, ?, ?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, Integer.parseInt((String)session.getAttribute("hoursID")));
                    ps.setDate(2, java.sql.Date.valueOf((String)session.getAttribute("hoursDate")));
                    ps.setDouble(3, (double)session.getAttribute("hourshours"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case"sanctionDB":
                    query = "insert into scm.tcf_sanctions " +
                "(sanctionid, clientid, sanctiondate, sanctionlength) " +
                "values (?, ?, ?, ?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, (int)session.getAttribute("sanctionID"));
                    ps.setInt(2, (int)session.getAttribute("sanctionclientID"));
                    ps.setDate(3, java.sql.Date.valueOf((String)session.getAttribute("sanctionDate")));
                    ps.setInt(4, (int)session.getAttribute("sanctionLength"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case"clientInstDB":
                    query = "insert into scm.tcf_clientinstitution " +
                "(clientid, institutionid, programlength, programprogress, contactfname, contactlname, contactphone) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, (int)session.getAttribute("CIClientID"));
                    ps.setInt(2, (int)session.getAttribute("CIInstiID"));
                    ps.setInt(3, (int)session.getAttribute("CIProgLength"));
                    ps.setInt(4, (int)session.getAttribute("CIProgProg"));
                    ps.setString(5, (String)session.getAttribute("CIContactF"));
                    ps.setString(6, (String)session.getAttribute("CIContactL"));
                    ps.setString(7, (String)session.getAttribute("CIContactP"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case "aidDB":
                    query = "insert into scm.tcf_aidtypes " +
                "(aidtype, aidname) " +
                "values (?, ?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, (int)session.getAttribute("aidType"));
                    ps.setString(2, (String)session.getAttribute("aidName"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case "assDB":
                    query = "insert into scm.tcf_assistance " +
                "(assistanceid, description) " +
                "values (?, ?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, (int)session.getAttribute("assID"));
                    ps.setString(2, (String)session.getAttribute("assDesc"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case "empDB":
                    query = "insert into scm.tcf_employer " +
                "(employerid, name, city, street, state, zip, extzip) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, (int)session.getAttribute("empID"));
                    ps.setString(2, (String)session.getAttribute("empName"));
                    ps.setString(3, (String)session.getAttribute("empCity"));
                    ps.setString(4, (String)session.getAttribute("empStreet"));
                    ps.setString(5, (String)session.getAttribute("empState"));
                    ps.setString(6, (String)session.getAttribute("empZip"));
                    ps.setString(7, (String)session.getAttribute("empExtzip"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case "CEDB":
                    query = "insert into scm.tcf_clientemployer " +
                "(clientid, employerid, income, contactfname, contactlname, contactphone) " +
                "values (?, ?, ?, ?, ?, ?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, (int)session.getAttribute("CEEmpId"));
                    ps.setInt(2, (int)session.getAttribute("CEClientID"));
                    ps.setDouble(3, (double)session.getAttribute("CEIncome"));
                    ps.setString(4, (String)session.getAttribute("CEContactFName"));
                    ps.setString(5, (String)session.getAttribute("CEContactLName"));
                    ps.setString(6, (String)session.getAttribute("CEPhone"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
                case "CADB":
                    query = "insert into scm.tcf_clientaid " +
                "(clientid, datedisbursed, aidtype) " +
                "values (?, ?, ?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, (int)session.getAttribute("CAClientID"));
                    ps.setDate(2, java.sql.Date.valueOf((String)session.getAttribute("CADate")));
                    ps.setInt(3, (int)session.getAttribute("CAAidType"));
                break;
                case "RADB":
                    query = "insert into scm.tcf_requestassist " +
                "(requestid, assistanceid, clientid, daterequest, status, datedisbursed, amount) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

                    ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, (int)session.getAttribute("RAID"));
                    ps.setString(2, (String)session.getAttribute("RAAssID"));
                    ps.setString(3, (String)session.getAttribute("RAClientID"));
                    ps.setString(4, (String)session.getAttribute("RADateRequest"));
                    ps.setString(5, (String)session.getAttribute("RAStatus"));
                    ps.setString(6, (String)session.getAttribute("RADate"));
                    ps.setString(7, (String)session.getAttribute("RAAmount"));
                    
                    InsertDB.insertIntoDB(ps);
                break;
            }
        }
        catch(SQLException e)
        {
        }
        finally{
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        
        // redirect
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
