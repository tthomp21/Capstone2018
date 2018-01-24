/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.AssistanceRequest;
import business.Client;
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
           * Processes requests for both HTTP <code>GET</code> and
           * <code>POST</code> methods.
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
	//String clientID = session.getAttribute("clientID").toString(); // get this from successful login.
	Client aClient = (Client) session.getAttribute("user"); // get this from successful login.
	
	/*try{
	          
	   int iClientID = clientID.getClientID();
	}
	catch(Exception e)
	{
	         url = "/views/temporaryClientIndex.jsp";
	         cs.getRequestDispatcher(url).forward(request, response);
	}*/
	
	ArrayList<AssistanceRequest> allAssitancesList = (ArrayList<AssistanceRequest>) session.getAttribute("allAssitancesList");

	if (allAssitancesList == null) {
	          allAssitancesList = new ArrayList<AssistanceRequest>();
	}
	else
	{
	          allAssitancesList = ClientDB.getTestData();
		      
	}

	/**
	 * documentation create allAssitancesList to hold all
	 * assistances make a call to the data base to get all the
	 * assistances create a list for each assistance type loop
	 * through allAssistanacesList and filter/insert them to the
	 * appropriate list. load all lists into the session
	 */
	//this supposed to get from the Data base
        allAssitancesList = ClientDB.getAllAssistances(aClient.getClientID());
        
       
        //this is only test data (hard coded)
        //allAssitancesList = ClientDB.getTestData();
        
        ArrayList<AssistanceRequest> foodList               = new ArrayList<AssistanceRequest>();
        ArrayList<AssistanceRequest> cashList               = new ArrayList<AssistanceRequest>();
        ArrayList<AssistanceRequest> medicaidList           = new ArrayList<AssistanceRequest>();
        ArrayList<AssistanceRequest> otherBenefitsList      = new ArrayList<AssistanceRequest>();

	//get all assitances
	try {
	          //loop through assistances
	          for (AssistanceRequest assist : allAssitancesList) {
		if(assist.getStatus().equalsIgnoreCase("active"))
		{
		         foodList.add(assist);
		          
		}
		
		
		
		         
//		if (assist.getAnAssistance().getAssistanceDescription().equalsIgnoreCase("active")) {
//		          foodList.add(assist);
//
//		          /**
//		           * this to test Tree map
//		           */
//		          sortedFoodList.put(assist.getDateDisbursed(), assist);
//
//		} else if (assist.getAnAssistance().getAssistanceDescription().equalsIgnoreCase("ADC")) {
//		          cashList.add(assist);
//		} else if (assist.getAnAssistance().getAssistanceDescription().equalsIgnoreCase("MedicAid")) {
//		          medicaidList.add(assist);
//		} else {
//		          otherBenefitsList.add(assist);
//		}

	          }
	          Collections.sort(foodList,AssistanceRequest.sortAssistanceListByDate );

	} catch (Exception ex) {

	} finally {
	          // sortOurLists(foodList);
	          //this is executed anyway. so load the list here and forward the request.
	          session.setAttribute("allAssitancesList", allAssitancesList);
	          session.setAttribute("foodList", foodList);
	          session.setAttribute("cashList", cashList);
	          session.setAttribute("medicaidList", medicaidList);
	          session.setAttribute("otherBenefitsList", otherBenefitsList);
	          
	          url	=         "/views/assistance.jsp";
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
           * this is sorting the list based on the year for now. ***** come up
           * with something that would sort based on the whole date.
           *
           * this method may need session object so it forwards the sorted list
           * from here.
           *
           * @param foodList
           *
           * private void sortOurLists(ArrayList<AssistanceRequest> foodList) {
           *
           * Collections.sort(foodList, new Comparator<AssistanceRequest>() {
           * public int compare(AssistanceRequest assis1, AssistanceRequest
           * assis2) { return
           * Integer.valueOf(assis1.getDateDisbursed().getYear()).compareTo(assis2.getDateDisbursed().getYear());
           * }
           *
           * }
           * );
           *
           */
}
