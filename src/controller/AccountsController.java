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
import javax.servlet.http.HttpSession;


public class AccountsController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String url = "/views/index.jsp";
        
        // Auto fill login forms for easy testing purposes ----- |
        request.setAttribute("prevLoginUserNameCW", "testing");//|
        request.setAttribute("prevLoginPasswordCW", "testing");//|
        request.setAttribute("prevLoginUserNameCL", "testun");// |
        request.setAttribute("prevLoginPasswordCL", "pppppp");// |
        // ------------------------------------------------------|
                
        User user = null;
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "arrival";
        }      
        
        switch(action) {
            case "arrival":                
                break;
            case "logout":                     
                // delete user from session
                session.setAttribute("user", null);
                break;
            case "manageClient":
                break;
            case "manageCaseWorker":
                break;
                
            // account management actions
            case "updateClient":                
                if (updateClientInfo(request))
                {
                    String manageMsgSuccess = 
                        "Your Account has been successfully updated";
                    request.setAttribute("manageMsgSuccess", manageMsgSuccess);
                }
                else 
                    request.setAttribute("manageType", "cl");
                break;
            case "updateCaseWorker":                
                request.setAttribute("manageType", "cw");
                if (updateCaseWorkerInfo(request))
                {
                    String manageMsgSuccess = 
                        "Your Account has been successfully updated";
                    request.setAttribute("manageMsgSuccess", manageMsgSuccess);                
                }
                break;
            // login actions
            case "loginAsCL":              
                if (clientLogIn(request))
                    request.setAttribute("redirect", "/AssistanceController");                
                else
                    request.setAttribute("loginType", "cl");
                break;
            case "loginAsCW":            
                if (caseWorkerLogIn(request))
                    request.setAttribute("redirect", "/CaseWorkerController");
                else
                    request.setAttribute("loginType", "cw");                       
                break;
            // create account action
            case "create":
                if (accountCreation(request))
                {
                    String createMsgSuccess = 
                        "Your Account has been successfully created!  Please log in.";
                    request.setAttribute("createMsgSuccess", createMsgSuccess);
                }                
                break;
        }        
        
        // redirect to 'url'        
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);  
    }

    // validates and creates user account, or creates error messages
    private boolean accountCreation(HttpServletRequest request)
    {
        boolean success = true;
        String createMsg = "";
        
        // retrieve form entries
        String userName =  (String) request.getParameter("createUserName");
        String password1 = (String) request.getParameter("createPassword1");
        String password2 = (String) request.getParameter("createPassword2");        
        String clientID =  (String) request.getParameter("createClientID");
        String ssn =       (String) request.getParameter("createSSN");
        
        // retain entries in form
        request.setAttribute("prevCreateUserName", userName);
        request.setAttribute("prevCreatePassword1", password1);
        request.setAttribute("prevCreatePassword2", password2);
        request.setAttribute("prevCreateClientID", clientID);
        request.setAttribute("prevCreateSSN", ssn);        
                
        // validate username/pw correct format (done on front end as well)
        if (!Accounts.isValidLength(userName, 6))
        {            
            createMsg = "User name must be at least 6 characters";
            request.setAttribute("createMsg", createMsg);
            return false;
        }
        else if (!password1.equals(password2))
        {
            createMsg = "Passwords do not match";
            request.setAttribute("createMsg", createMsg);
            return false;
        }
        else if (!Accounts.isValidLength(password1, 6))
        {
            createMsg = "Password must be at least 6 characters";
            request.setAttribute("createMsg", createMsg);
            return false;
        }
        else if (!Accounts.isInteger(clientID))
        {
            createMsg = "Client ID must be numeric";
            request.setAttribute("createMsg", createMsg);
            return false;
        }
        
        String sqlErrorMsg = "There was an error with the database connection";
        QueryResult results;        
        int id = Integer.parseInt(clientID);
        
        // ensure client id is found in the database
        results = Accounts.isValidClientID(id);
        if (!results.successful()) 
        {            
            if (results.sqlErrors())
                createMsg = sqlErrorMsg;
            else                 
                createMsg = "There was an error finding a client with that ID#";   
            
            request.setAttribute("createMsg", createMsg);
            return false;
        }

        // ensure ssn matches client with that id
        results = Accounts.isValidSSN(ssn, id);
        if (!results.successful()) 
        {            
            if (results.sqlErrors())
                createMsg = sqlErrorMsg;
            else                 
                createMsg = "The SSN# does not match our records for that ID#";     
            
            request.setAttribute("createMsg", createMsg);
            return false;             
        }    
        
        // ensure client hasn't already created an account
        results = Accounts.accountCreationAvailable(id);
        if (!results.successful()) 
        {            
            if (results.sqlErrors())
                createMsg = sqlErrorMsg;
            else                 
                createMsg = "You have already created an account.  Please Log In";     
            
            request.setAttribute("createMsg", createMsg);
            return false;             
        }   
        
        // ensure desired user name isn't taken 
        results = Accounts.isValidUserName(userName);
        if (!results.successful()) 
        {
            if (results.sqlErrors())
                createMsg = sqlErrorMsg;
            else
                createMsg = "This user name is already taken";
            
            request.setAttribute("createMsg", createMsg);
            return false;   
        }            

        // attempt to create account
        boolean creationSuccess = Accounts.registerAccount(userName, password1, id);                

        if (creationSuccess)
            return true;            
        else 
        {
            createMsg = "There was an error adding account to the database";
            request.setAttribute("createMsg", createMsg);
            return false;        
        }  
    }
    
    
    // validates and authenticates client login
    private boolean clientLogIn(HttpServletRequest request)
    {        
        String sqlErrorMsg = "There was an error with the database connection";
        String loginMsg = "";
        
        // retrieve form entries
        String userName =  (String) request.getParameter("loginUserNameCL");
        String password =  (String) request.getParameter("loginPasswordCL");
        
        // retain entries in form
        request.setAttribute("prevLoginUserNameCL", userName);
        request.setAttribute("prevLoginPasswordCL", password);
        
        QueryResult userNameExists = Accounts.verifyUserName(userName, "client");
        if (!userNameExists.successful())
        {
            if (userNameExists.sqlErrors())
                loginMsg = sqlErrorMsg;
            else                 
                loginMsg = "The user name cannot be found";   
            
            request.setAttribute("loginMsg", loginMsg);
            return false;
        }  
        
        QueryResult authentication = Accounts.authenticateUser(userName, password, "client");
        if (!authentication.successful())
        {
            if (authentication.sqlErrors())
                loginMsg = sqlErrorMsg;
            else                 
                loginMsg = "The password is incorrect";   
            
            request.setAttribute("loginMsg", loginMsg);
            return false;
        }   
        
        Client user = Accounts.logInClient(userName);       
        if (user == null)
        {                           
            request.setAttribute("loginMsg", sqlErrorMsg);
            return false;
        }
        else 
        {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return true;
        }
    }
    
    // validates and authenticates case worker login    
    private boolean caseWorkerLogIn(HttpServletRequest request)
    {        
        String sqlErrorMsg = "There was an error with the database connection";
        String loginMsg = "";
        
        // retrieve form entries
        String userName =  (String) request.getParameter("loginUserNameCW");
        String password =  (String) request.getParameter("loginPasswordCW");
        
        // retain entries in form
        request.setAttribute("prevLoginUserNameCW", userName);
        request.setAttribute("prevLoginPasswordCW", password);
        
        QueryResult userNameExists = Accounts.verifyUserName(userName, "caseWorker");
        if (!userNameExists.successful())
        {
            if (userNameExists.sqlErrors())
                loginMsg = sqlErrorMsg;
            else                 
                loginMsg = "The user name cannot be found";   
            
            request.setAttribute("loginMsg", loginMsg);
            return false;
        }  
        
        QueryResult authentication = Accounts.authenticateUser(userName, password, "caseWorker");
        if (!authentication.successful())
        {
            if (authentication.sqlErrors())
                loginMsg = sqlErrorMsg;
            else                 
                loginMsg = "The password is incorrect";   
            
            request.setAttribute("loginMsg", loginMsg);
            return false;
        }   
        
        CaseWorker user = Accounts.logInCaseWorker(userName);       
        if (user == null)
        {                           
            request.setAttribute("loginMsg", sqlErrorMsg);
            return false;
        }
        else 
        {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return true;
        }
    }
    
    
    // handles updating of case worker account info
    private boolean updateCaseWorkerInfo(HttpServletRequest request) {
        String manageMsg;
        String sqlErrorMsg = "There was an error with the database connection";
        CaseWorker user;
        
        HttpSession session = request.getSession();
        String userName = ((CaseWorker)session.getAttribute("user")).getUserName();        
                
        // retrieves name of field being updated
        String fieldName = request.getParameter("fieldName");
            
        if (fieldName == null)
            return false;
        
        // retrieve confirmation password and update field from update form
        String managePasswordCW = (String) request.getParameter("managePasswordCW");        
        String fieldValue = (String) request.getParameter("fieldValue");
        String fieldValue2 = null;
        
        // retain form values
        switch (fieldName) {
            case "password":
                // get second password field if chosen
                fieldValue2 = (String) request.getParameter("fieldValue2");
                request.setAttribute("prevManagePassword1CW", fieldValue);                
                request.setAttribute("prevManagePassword2CW", fieldValue2);
                break;
            case "phone":
                request.setAttribute("prevManagePhoneCW", fieldValue);      
                break;
            case "email":
                request.setAttribute("prevManageEmailCW", fieldValue);      
                break;
            case "username":
                request.setAttribute("prevManageUserNameCW", fieldValue);      
                break;
        }
        
        // validate chosen field
        String validationMsg = Accounts.isValidUpdateField(fieldName, fieldValue, fieldValue2);
        if (!validationMsg.isEmpty())
        {
            manageMsg = validationMsg;
            request.setAttribute("manageMsg", manageMsg);
            return false;
        }
        else
        {
            QueryResult results;
            
            // verify password for update
            results = Accounts.authenticateUser(userName, managePasswordCW, "caseWorker");
            if (!results.successful()) 
            {            
                if (results.sqlErrors())
                    manageMsg = sqlErrorMsg;
                else                 
                    manageMsg = "Your password is incorrect";   

                request.setAttribute("manageMsg", manageMsg);
                return false;
            }
            
            // if updating username, check to make sure it is not taken
            if (fieldName.equals("username"))
            {
                results = Accounts.isValidUserName(fieldValue);
                if (!results.successful()) 
                {            
                    if (results.sqlErrors())
                        manageMsg = sqlErrorMsg;
                    else                 
                        manageMsg = "That User Name is already taken";   

                    request.setAttribute("manageMsg", manageMsg);
                    return false;
                }                
            }
            
            // update chosen field with new value
            if (Accounts.updateField("caseworkers", userName, fieldName, fieldValue))
            {
                if (fieldName.equals("username"))
                    userName = fieldValue;
                
                user = Accounts.logInCaseWorker(userName);
                if (user == null)
                    return false;
                session.setAttribute("user", user);
                return true;            
            }
            else 
            {
                manageMsg = "There was an error updating database"; 
                request.setAttribute("manageMsg", manageMsg);
                return false;
            }
        }        
    }  
    
    
    // ----------------------------------------------------
    
    // handles updating of client account info
    private boolean updateClientInfo(HttpServletRequest request) {
        String manageMsg = "";
        String sqlErrorMsg = "sdfasdfsdfasdsa";
        
        HttpSession session = request.getSession();
        String userName = ((Client)session.getAttribute("user")).getUserName();
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    
    
    
    // ---------------------------------------------------------------------------
    
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
        return "Account Management for TCF - Sayel Rammaha";
    }


}
