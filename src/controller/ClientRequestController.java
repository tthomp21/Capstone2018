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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@MultipartConfig
public class ClientRequestController extends HttpServlet {
   
    // arrival get method
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                        
        checkForSanctions(request);

        // testing for showing the file back to user        
        /* 
        if(!FileServe.retrieve(request, response, getServletContext(), 25))
            sc.getRequestDispatcher(url).forward(request, response);                    
        */    
        
        String url = "/views/clientRequest.jsp";
        ServletContext sc = getServletContext();        
        sc.getRequestDispatcher(url).forward(request, response);     
    }

    // post function handles file upload
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "/views/clientRequest.jsp";
        
        boolean underSanction = checkForSanctions(request);
        
        if (!underSanction) 
        {
            Client user = (Client) session.getAttribute("user");
            String requestMsg = "";

            String amount = request.getParameter("amount");
            request.setAttribute("prevAmount", amount);
            
            int requestID = 0;
            String requestType = request.getParameter("requestType");
            // validate request
            if (isValidRequest(request, amount, user)) 
            {
                // add request to db and retrieve auto inc request id
                try 
                {   // possible sql exception
                    requestID = RequestDB.addRequest(requestType, user.getClientID(), Double.parseDouble(amount));
                    if (requestID != 0)
                    {
                        // upload file to server
                        uploadFile(request, requestID); //possible IO exception

                        // show success msg to user
                        String msg = "Your request has been submitted.";
                        request.setAttribute("requestSuccessMsg", msg);                    
                    }            
                }
                catch (SQLException e) 
                {   
                    requestMsg = "There was an error connecting to the database.";
                    request.setAttribute("requestMsg", requestMsg);
                }
                catch (IOException e)
                {
                    requestMsg = "There was an error uploading the file.";
                    request.setAttribute("requestMsg", requestMsg);
                }
            }
        }
        
        // redirect to 'url'        
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);  
    }

    
    // checks for sanctions
    private boolean checkForSanctions(HttpServletRequest request) 
    {
        HttpSession session = request.getSession();
        String sanctionMsg;
        String msg = "You are under sanction until ";
        String sanctionEnd;
        
        String status = (String) session.getAttribute("sanctionStatus");
        if (status != null)
            if (status.equals("true"))
            {
                sanctionEnd = (String) session.getAttribute("sanctionEndDate");
                sanctionMsg = msg + sanctionEnd;
                request.setAttribute("sanctionMsg", sanctionMsg);
                return true;
            }
            else 
                return false;
        else 
        {
            // retrieve sanction
            int clientID = ((Client) session.getAttribute("user")).getClientID();
            MessageResult result = Requests.checkForSanction(clientID);
            if (result.successful())
            {
                sanctionEnd = result.getMessage();
                sanctionMsg = msg + sanctionEnd;
                request.setAttribute("sanctionMsg", sanctionMsg);
                session.setAttribute("sanctionStatus", "true");
                session.setAttribute("sanctionEndDate", sanctionEnd);
                return true;
            }
            else 
            {
                session.setAttribute("sanctionStatus", "false");
                return false;
            }
        }
    }
    
    
    // retrieves file from upload form, saves to file directory with filename:
    // requestID-filename.ext ||  ex: 5-document.pdf
    private static void uploadFile(HttpServletRequest request, int requestID) throws ServletException, IOException
    {
        // set directory for uploaded files
        String fileDir = System.getProperty("user.home") + "/My Documents";
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
 
        double amt = 0;        
        // validate amount
        try 
        {
            amt = Double.parseDouble(amount);                       
        }
        catch (NumberFormatException e)
        { 
            String requestMsg = "Please enter a valid dollar amount.";
            request.setAttribute("requestMsg", requestMsg);
            return false;
        }
        
        // validate rules for assistance limits
        String requestType = request.getParameter("requestType");
        HttpSession session = request.getSession();
        int clientID = ((Client)session.getAttribute("user")).getClientID();
        MessageResult result = Requests.validate(requestType, clientID, amt);
        
        if (result.successful())
        {            
            return true;
        }
        else 
        {
            request.setAttribute("requestMsg", result.getMessage());
            return false;
        }        
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
    public String getServletInfo() {
        return "Submit Requests";
    }// </editor-fold>

}
