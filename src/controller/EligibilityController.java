/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author radad
 */
public class EligibilityController extends HttpServlet {

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
        
        
        /**
         * to view the eligibility for ADC:
         * 1- check the marriage status
         *      ---> if married get the partnerID for the underlying/signed in client do step 2 otherwise check step 3 for single person against 20 hours a week.
         * 2- check the hours for both and has to be 35 hours for the current week, or total of 35/week * 4 = 140hours/month. 
         *      ---> another option is to notify the client for the shortage of hours if there is any; 
         * 3- if not married, it means the client is a single parent with dependents, his/her hours =20/week * 4 = 80/month
         * 
         * 4- once hours are checked check the sanctions as follow, sanctions 1, 2, 3 are types of sanctions 1s, 2nd, 3rd
         *      ---> if sanction length is 1, it means that current date - sanction date has to be > 30 days so the client can be eligible for the assistance, mainly ADC
         *      ---> if sanction length is 2, it means that current date - sanction date has to be > 90 days so the client can be eligible for the assistance, mainly ADC
         *      ---> if sanction length is 3, it means that current date - sanction date has to be > 365 days so the client can be eligible for the assistance, mainly ADC
         * 5- for the secondary assistances follow this steps:
         *      ---> clothing: $200 once a year., how this should be handled; is it just something that assumed it is given regularly or just $200.
         *      ---> vehicle registeration: one time and only one. 
         *      ---> fuel: once a week, $20 dollars each.
         *      ---> tuition: one time and only once.
         *      ---> car repairs: depends may be once or once per year.
         *      --->
         */
       
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
