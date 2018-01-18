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
        
        HttpSession session = request.getSession();
        String url = "/views/index.jsp";
        
        session.setAttribute("loginMsg", null);
        session.setAttribute("loginType", null);         
        session.setAttribute("redirect", null); 
        
        User user = null;
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "arrival";
        }      
        
        switch(action) {
            case "arrival":                
                break;
            case "manageClient":                
                break;
            case "manageCaseWorker":
                break;
            case "logout":                     
                // delete user from session
                session.setAttribute("user", null);
                break;
            case "loginAsCL":
                // authenticate user
                
                // if login fails, set these vars and don't set redirect
                session.setAttribute("loginMsg", "error authenticating client");
                session.setAttribute("loginType", "cl"); 
                break;
            case "loginAsCW":
                // authenticate user
                
                // if login fails, set these vars and don't set redirect
                session.setAttribute("loginMsg", "error authenticating case worker");
                session.setAttribute("loginType", "cw"); 
                break;
                
            // testing
            case "testLoginAsCL":
                // redirect to client controller
                session.setAttribute("redirect", "/ClientController");
                
                // create test client user
                user = new Client(1, "clientFirstName", "MI", "lastName", "###phone##", 
                        "email", "###ssn###", "city", "state", "#zip#", 
                        LocalDate.of(2009, Month.APRIL, 11), true, LocalDate.of(2016, Month.AUGUST, 16),
                        2, 2, 1);
                
                // store in session
                session.setAttribute("user", user);
                break;
            case "testLoginAsCW":
                // redirect to case worker controller
                session.setAttribute("redirect", "/CaseWorkerController"); 
                
                // create test caseworker user
                user = new CaseWorker(1, "caseWorkerFirstName", "lastName", 
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
