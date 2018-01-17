// Tyler Thompson   
// 1/14/18


package controller;

import business.*;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
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
                
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        if (action == null) {
            action = "arrival";
        }
        
        CaseWorker caseWorker = (CaseWorker)session.getAttribute("user");
        
        if(caseWorker == null)
        {
            //action = "sendHome";
            
            //dummy in some data for testing until the db gets up
            
            List<Client> workers = new ArrayList<Client>();
            //workers.add(new Client())
        }
        
        
        
        switch(action) {
            case "arrival":
                break;
            case "sendHome":
                url = "/index.jsp";
                break;
            case "viewAllClients":
                url = "/allClients";
        }
        
        
        // redirect
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);  
                
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
