/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.AssistanceRequest;
import business.Client;
import business.ClientAid;
import data.AccountDB;
import data.ClientDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

        HttpSession session = request.getSession();
        ServletContext cs = session.getServletContext();

        String url = ""; //= "/view/the default page";
        Client aClient = (Client) session.getAttribute("user"); // get this from successful login.

        ArrayList<AssistanceRequest> allAssitancesList = (ArrayList<AssistanceRequest>) session.getAttribute("allAssitancesList");
        ArrayList<ClientAid> clientAidList = (ArrayList<ClientAid>) session.getAttribute("clientAidList");

        if (allAssitancesList == null) {
            allAssitancesList = new ArrayList<AssistanceRequest>();
          //  allAssitancesList = ClientDB.getSecondaryAssistances(aClient.getClientID());
        } else if(clientAidList == null){
	    clientAidList = new ArrayList<ClientAid>();
	}

        /**
         * documentation : create allAssitancesList to hold all assistances make
         * a call to the data base to get all the assistances create a list for
         * each assistance type loop through allAssistanacesList and
         * filter/insert them to the appropriate list. load all lists into the
         * session
         */
        //Primary assistance
         ArrayList<ClientAid> foodList	    = new ArrayList<ClientAid>();
         ArrayList<ClientAid> cashList	    = new ArrayList<ClientAid>();
         ArrayList<ClientAid> medicaidList  = new ArrayList<ClientAid>();
        
        
        //this list is only seconary list, as the names of the lists indicate
        
        //allAssitancesList = ClientDB.getSecondaryAssistances(aClient.getClientID());
        clientAidList     = ClientDB.getPrimaryAssistances(aClient.getClientID());
        //this is only test data (hard coded)
        allAssitancesList = ClientDB.getTestData();
        
        ArrayList<AssistanceRequest> carRepairsList      = new ArrayList<AssistanceRequest>();
        ArrayList<AssistanceRequest> clothingList        = new ArrayList<AssistanceRequest>();
        ArrayList<AssistanceRequest> vehicleRegisterList = new ArrayList<AssistanceRequest>();
        ArrayList<AssistanceRequest> gasList             = new ArrayList<AssistanceRequest>();
        ArrayList<AssistanceRequest> tuitionList         = new ArrayList<AssistanceRequest>();
        
        
        //get all assitances
        try {
            //loop through assistances
            for (AssistanceRequest assist : allAssitancesList) {
                if (assist.getStatus().equalsIgnoreCase("car repair")) {
	carRepairsList.add(assist);
                }

                if (assist.getStatus().equalsIgnoreCase("clothing")) {
	clothingList.add(assist);

                } else if (assist.getStatus().equalsIgnoreCase("gas")) {
	gasList.add(assist);
                } else if (assist.getStatus().equalsIgnoreCase("vehicle registeration")) {
	vehicleRegisterList.add(assist);
                } else {
	tuitionList.add(assist);
                }

            }
            
            //filter primary benefits
            for(ClientAid clientAssist: clientAidList){
                if(clientAssist.getAidType().getAidDescription().equalsIgnoreCase("SNAP")){
	foodList.add(clientAssist);	
                }
                else if (clientAssist.getAidType().getAidDescription().equalsIgnoreCase("ADC")){
	cashList.add(clientAssist);
                }
                else
                {
	medicaidList.add(clientAssist);
                }
            }
            
            Collections.sort(carRepairsList, AssistanceRequest.sortAssistanceListByDate);
            Collections.sort(clothingList, AssistanceRequest.sortAssistanceListByDate);
            Collections.sort(gasList, AssistanceRequest.sortAssistanceListByDate);
            Collections.sort(vehicleRegisterList, AssistanceRequest.sortAssistanceListByDate);
            Collections.sort(tuitionList, AssistanceRequest.sortAssistanceListByDate);
            
            
            //NOTE: I made the clientAid to extend the AssistanceRequest to take advantage of implementing comparator class
            // so this will be tested once tables are populated later. 
            Collections.sort(foodList, ClientAid.sortAssistanceListByDate);
            Collections.sort(cashList, ClientAid.sortAssistanceListByDate);
            Collections.sort(medicaidList, ClientAid.sortAssistanceListByDate);
           

        } catch (Exception ex) {
            

        } finally {
            // sortOurLists(foodList);
            //this is executed anyway. so load the list here and forward the request.
            session.setAttribute("allAssitancesList", allAssitancesList);
            session.setAttribute("carRepairsList", carRepairsList);
            session.setAttribute("clothingList", clothingList);
            session.setAttribute("vehicleRegisterList", vehicleRegisterList);
            session.setAttribute("gasList", gasList);
            session.setAttribute("tuitionList", tuitionList);

            url = "/views/assistance.jsp";
            cs.getRequestDispatcher(url).forward(request, response);
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

    /**
     * this is sorting the list based on the year for now. ***** come up with
     * something that would sort based on the whole date.
     *
     * this method may need session object so it forwards the sorted list from
     * here.
     *
     * @param foodList
     *
     * private void sortOurLists(ArrayList<AssistanceRequest> foodList) {
     *
     * Collections.sort(foodList, new Comparator<AssistanceRequest>() { public
     * int compare(AssistanceRequest assis1, AssistanceRequest assis2) { return
     * Integer.valueOf(assis1.getDateDisbursed().getYear()).compareTo(assis2.getDateDisbursed().getYear());
     * }
     *
     * }
     * );
     *
     */
}
