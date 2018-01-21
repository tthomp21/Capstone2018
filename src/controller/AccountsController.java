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
                if (clientLogIn(request))
                {
                    // redirect to client controller
                    session.setAttribute("redirect", "/AssistanceController");
                }
                else
                {                    
                    // if login fails
                    session.setAttribute("loginType", "cl");
                    // loginMsg (on error) or user (on success) created and stored
                }                 
                break;
            case "loginAsCW":
                // authenticate user
                if (caseWorkerLogIn(request))
                {
                    // redirect to client controller
                    session.setAttribute("redirect", "/CaseWorkerController");
                }
                else
                {                    
                    // if login fails
                    session.setAttribute("loginType", "cw");
                    // loginMsg (on error) or user (on success) created and stored
                }       
                break;
                
//            // testing
//            case "testLoginAsCL":
//                // redirect to client controller
//                session.setAttribute("redirect", "/AssistanceController");
//                
//                // create test client user
//                user = new Client(1, "clientFirstName", "MI", "lastName", "###phone##", 
//                        "email", "###ssn###", "city", "state", "#zip#", 
//                        LocalDate.of(2009, Month.APRIL, 11), true, LocalDate.of(2016, Month.AUGUST, 16),
//                        2, 2, 1);
//                
//                // store in session
//                session.setAttribute("user", user);
//                break;
//            case "testLoginAsCW":
//                // redirect to case worker controller
//                session.setAttribute("redirect", "/CaseWorkerController"); 
//                
//                // create test caseworker user
//                user = new CaseWorker(1, "caseWorkerFirstName", "lastName", 
//                        "###phone##", "email", "officeD2");
//                
//                // store in session
//                session.setAttribute("user", user);                
//                break;
                
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
