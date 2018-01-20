/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.AssistanceRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author radad
 */
public class AssistanceController extends HttpServlet {

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
      * create allAssitancesList to hold all assistances
      * make a call to the data base to get all the assistances
      * create a list for each assistance type
      * loop through allAssistanacesList and filter/insert them to the appropriate list.
      * load all lists into the session
      */
     
     
     
     ArrayList<AssistanceRequest> allAssitancesList    = new ArrayList<AssistanceRequest>();
     
     ArrayList<AssistanceRequest> foodList             = new ArrayList<AssistanceRequest>();
     ArrayList<AssistanceRequest> cashList             = new ArrayList<AssistanceRequest>();
     ArrayList<AssistanceRequest> medicaidList         = new ArrayList<AssistanceRequest>();
     ArrayList<AssistanceRequest> otherBenefitsList    = new ArrayList<AssistanceRequest>();
     
     
     //get all assitances
     
     try
     {
     //loop through assistances
     for(AssistanceRequest assist : allAssitancesList)
     {
         /*
         if(assist.getassistanceStatus().equalsIgnoreCase("SNAP"))
         {
             foodList.add(assist);
         }
         else if(assist.getassistanceStatus().equalsIgnoreCase("ADC"))
         {
             cashList.add(assist);
         }
         else if(assist.getassistanceStatus().equalsIgnoreCase("MedicAid"))
         {
             medicAidList.add(assist);
         }
         else 
         {
             otherBenefitsList.add(assist);
         }
         */
     }
     
     }
     catch(Exception ex)
     {
         
     }
     finally
     {
         //this is executed anyway. so load the list here and forward the request.
     }
     
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
