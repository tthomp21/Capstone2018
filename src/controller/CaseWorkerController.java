// Tyler Thompson   
// 1/14/18


package controller;

import business.*;
import data.ClientDB;
import data.HoursDB;
import data.RequestDB;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CaseWorkerController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/views/caseworkerHome.jsp";
        String message = "";
        String action = request.getParameter("action");
        String selectedClient;
        Client foundClient;
        HttpSession session = request.getSession();
        ArrayList<Client> clients = new ArrayList<Client>();
        
        if (action == null) {
            action = "arrival";
        }
        
        CaseWorker caseWorker = (CaseWorker)session.getAttribute("user");
        
        if(caseWorker == null)
        {
            action = "sendHome";
        }
        
        
        
        switch(action) {
            case "arrival":
                //when the caseworker arrives on this page get all the clients assigned to them
                clients = getClients(caseWorker, session);
                if(clients != null)
                    session.setAttribute("foundClients", clients);
                break;
            case "sendHome":
                //if there was an error getting the user send them back home
                url = "/AccountsController";
                break;
            case "clientHours":
                selectedClient = (String) request.getParameter("clientID");
                foundClient = ClientDB.getClientWithID(Integer.parseInt(selectedClient));
                session.setAttribute("foundClient", foundClient);
                url = "/views/caseworkerclienteditor.jsp";
                break;
            case "clientDetails":
                selectedClient = (String) request.getParameter("clientID");
                getClientDetails(selectedClient, session);
                url = "/views/caseworkerclientdetails.jsp";
                break;
            case "search":
                url = "/views/clientSearch.jsp";
                break;
            case "approveRequest":
                int reqID = (int)session.getAttribute("requestID");
                int reqStat = (int)session.getAttribute("requestStat");
                RequestDB.updateRequest(reqID, reqStat);
                
                break;
            case "declienRequest":
                break;
        }
        
        
        // redirect
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);  
                
    }
    
    
    public void getClientDetails(String clientID, HttpSession session)
    {
        Client foundClient;
        foundClient = ClientDB.getClientWithID(Integer.parseInt(clientID));
        session.setAttribute("foundClient", foundClient);
        
        ArrayList<ClientHoursArgs> hours = new ArrayList<ClientHoursArgs>();
        hours = HoursDB.getClientHours(Integer.parseInt(clientID));
        session.setAttribute("clientHours", hours);
        
        ArrayList<AssistanceRequest> assistReq = new ArrayList<AssistanceRequest>();
        assistReq = ClientDB.getSecondaryAssistances(Integer.parseInt(clientID));
        session.setAttribute("clientRequests", assistReq);
    }
    
    
    public ArrayList<Client> getClients(CaseWorker caseWorker, HttpSession session)
    {
        //returns a list of clients from the DB
        ArrayList<Client> allClients = new ArrayList<Client>();
        allClients = ClientDB.getAllClientsForCaseWorker(caseWorker);
        return allClients;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    // </editor-fold>
}
