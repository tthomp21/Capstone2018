// Tyler Thompson   
// 1/14/18


package controller;

import business.*;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CaseWorkerController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/views/caseWorker/mainTemplate.jsp";
                
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
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
                break;
            case "sendHome":
                //url = the index of the site
                break;
        }
        
        
        // redirect
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);  
                
    }

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
}
