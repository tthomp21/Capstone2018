// Sayel Rammaha    
// 1/12/18

package controller;

import business.*;
import java.time.LocalDate;
import java.io.IOException;
import java.time.Month;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AccountsController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/views/accountIndex.jsp";
               
        String action = request.getParameter("action");
        if (action == null) {
            action = "arrival";
        }
        
        HttpSession session = request.getSession();
        User user = null;
        
        switch(action) {
            case "arrival":                
                break;
            case "manageClient":
                url = "/views/accountIndex.jsp";
                
                break;
            case "manageCaseWorker":
                url = "/views/accountIndex.jsp";
                break;
            case "logout":
                url = "/views/accountIndex.jsp";               
                
                // delete user from session
                session.setAttribute("user", null);
                break;
                
            // testing
            case "testingCL":
                // redirect to client controller
                url = "/ClientController";  
                
                // create test client user
                user = new Client(1, "firstName", "MI", "lastName", "###phone##", 
                        "email", "###ssn###", "city", "state", "#zip#", 
                        LocalDate.of(2009, Month.APRIL, 11), true, LocalDate.of(2016, Month.AUGUST, 16),
                        2, 2, 1);
                // store in session
                session.setAttribute("user", user);
                break;
            case "testingCW":
                // redirect to case worker controller
                url = "/CaseWorkerController";    
                
                // create test caseworker user
                user = new CaseWorker(1, "firstName", "lastName", 
                        "###phone##", "email", "officeD2");
                // store in session
                session.setAttribute("user", user);
                break;
        }        
        
        // redirect to 'url'
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
