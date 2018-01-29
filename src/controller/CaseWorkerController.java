// Tyler Thompson   
// 1/14/18


package controller;

import business.*;
import data.ClientDB;
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
            case "clientDetails":
                String selectedClient = (String) request.getParameter("clientID");
                Client foundClient = ClientDB.getClientWithID(Integer.parseInt(selectedClient));
                session.setAttribute("foundClient", foundClient);
                url = "/views/caseworkerclientdetails.jsp";
        }
        
        
        // redirect
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);  
                
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
