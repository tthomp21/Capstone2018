// Sayel Rammaha    
// 1/30/18
// handles just the client assistance request processing

package controller;

import business.*;
import data.FileServe;
import data.RequestDB;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@MultipartConfig
public class ClientRequestController extends HttpServlet {
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "/views/clientRequest.jsp";
        
        // set directory for uploaded files
        String fileDir = System.getProperty("user.home") + "/Desktop";
        
        Client user = (Client) session.getAttribute("user");
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "arrival";
        }      
        
        switch(action) {
            case "arrival": 
                // redirect to 'url'        
                ServletContext sc = getServletContext();
                sc.getRequestDispatcher(url).forward(request, response);  
                break;
            case "view":
                FileServe.retrieve(request, response, getServletContext(), 21);
                break;
        }
    }

    // post function handles file upload
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "/views/clientRequest.jsp";
        
        Client user = (Client) session.getAttribute("user");
        
        String amount = request.getParameter("amount");
        int requestID = 0;
        // validate request
        if (isValidRequest(request, amount, user)) 
        {
            // add request to db and retrieve auto inc request id
            try {
                requestID = RequestDB.addRequest("", user.getClientID(), Double.parseDouble(amount));
            }
            catch (SQLException e) {
                
            }
            if (requestID != 0)
            {
                // upload file to server
                uploadFile(request, requestID);
                
                // show msg to user
                
            }            
        }
        
        // redirect to 'url'        
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);  
    }

    
    // retrieves file from upload form, saves to file directory with filename:
    // requestID-filename.ext ||  ex: 5-document.pdf
    private static void uploadFile(HttpServletRequest request, int requestID) throws ServletException, IOException
    {
        // set directory for uploaded files
        String fileDir = System.getProperty("user.home") + "/Desktop";
        // retrieve file from form
        Part filePart = request.getPart("file");
        // retrieve file name of document
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        // load file into stream
        InputStream fileContent = filePart.getInputStream();
        // create file container with save directory 
        File saveFileContainer = new File(fileDir);        
        // add file name to path           
        File file = new File(saveFileContainer, requestID + "-" + fileName);     
        
        // write file to save directory
        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    
    // validates request for assistance
    private static boolean isValidRequest(HttpServletRequest request, String amount, Client client)
            throws ServletException, IOException {
        boolean properAmt = false;        
        try {
            Double.parseDouble(amount);
            properAmt = true;            
        }
        catch (Exception e)
        { }
        return properAmt;
    }
  
    // helper method possibly needed depending on tomcat version
    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) 
        {
            if (cd.trim().startsWith("filename")) 
            {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                // IE fix
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); 
            }
        }
        return null;
    }
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
