// Sayel Rammaha    
// 1/12/18

package controller;

import business.*;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HomeController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/views/testing.jsp";
        //String url = "/views/index.jsp";
        
        // create test user
        CaseWorker cw = new CaseWorker(1, "firstNamefirstNamefirstName30c", "l", "p", "e", "o");
        request.setAttribute("user", cw);
        
        // redirect
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url)
                .forward(request, response);  
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
