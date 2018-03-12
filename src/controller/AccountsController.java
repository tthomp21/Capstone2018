// Sayel Rammaha    
// 1/12/18

package controller;

import business.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
        request.setAttribute("prevLoginUserNameCW", "praymond");//|
        request.setAttribute("prevLoginPasswordCW", "praymond");//|
        request.setAttribute("prevLoginUserNameCL", "sayelr");// |
        request.setAttribute("prevLoginPasswordCL", "rsayel");// |
        // ------------------------------------------------------|
                
        User user = null;
        String userName = "";
        String password = "";
        String rememberMeStatus = null;
        boolean rememberMe = false;       
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "arrival";
        }      
        
        switch(action) {            
            case "arrival": 
                // check for cookies
                if (checkForCookies(request, response))
                {
                    // valid login from cookie achieved
                    String redirect = "";
                    Cookie[] cookies = request.getCookies();
                    for (int i = 0; i < cookies.length; i++)
                    {
                        if (cookies[i].getName().equals("user"))
                        {
                            redirect = cookies[i].getValue();
                            break;
                        }
                    }
                    // redirect to appropriate user controller
                    if (redirect.equals("client"))
                        url = "/EligibilityController";
	    
                    else if (redirect.equals("cw"))
                        url = "/CaseWorkerController";
                }
                break;
            case "logout":                     
                // delete user from session
                session.setAttribute("user", null);
                clearCookies(request, response);
                break;
            case "manageClient":
                if (session.getAttribute("user") == null)
                    checkForCookies(request, response);
                break;
            case "manageCaseWorker":
                if (session.getAttribute("user") == null)
                    checkForCookies(request, response);
                break;
                
            // account management actions
            case "updateClient":                
                request.setAttribute("manageType", "cl");
                if (updateClientInfo(request))
                {
                    String manageMsgSuccess = 
                        "Your Account has been successfully updated";                    
                    request.setAttribute("manageMsgSuccess", "");
                    // clear form
                    request.setAttribute("prevManagePassword1CL", "");                
                    request.setAttribute("prevManagePassword2CL", "");                
                    request.setAttribute("prevManagePhoneCL", "");      
                    request.setAttribute("prevManageEmailCL", "");      
                    request.setAttribute("prevManageUserNameCL", "");      
                    request.setAttribute("prevManageStreetCL", "");    
                    request.setAttribute("prevManageCityCL", "");    
                    request.setAttribute("prevManageZipCL", "");                
                    request.setAttribute("prevManageZipExtCL", "");
                }                    
                break;
            case "updateCaseWorker":                
                request.setAttribute("manageType", "cw");
                if (updateCaseWorkerInfo(request))
                {
                    String manageMsgSuccess = 
                        "Your Account has been successfully updated";
                    request.setAttribute("manageMsgSuccess", manageMsgSuccess);
                    
                    // clear form
                    request.setAttribute("prevManagePassword1CW", "");                
                    request.setAttribute("prevManagePassword2CW", "");
                    request.setAttribute("prevManagePhoneCW", ""); 
                    request.setAttribute("prevManageEmailCW", ""); 
                    request.setAttribute("prevManageUserNameCW", "");      
                }
                break;
                
            // login actions
            case "loginAsCL":              
                // retrieve form entries
                userName =  (String) request.getParameter("loginUserNameCL");
                password =  (String) request.getParameter("loginPasswordCL");
                rememberMeStatus = (String)request.getParameter("rememberMe");
                
                rememberMe = rememberMeStatus != null;
                
                if (clientLogIn(request, response, userName, password, rememberMe))
                    request.setAttribute("redirect", "/EligibilityController");                
                else
                    request.setAttribute("loginType", "cl");
                break;
            case "loginAsCW":            
                // retrieve form entries
                userName =  (String) request.getParameter("loginUserNameCW");
                password =  (String) request.getParameter("loginPasswordCW");
                rememberMeStatus = (String)request.getParameter("rememberMe");
                
                rememberMe = rememberMeStatus != null;
                
                if (caseWorkerLogIn(request, response, userName, password, rememberMe))
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
            // reset password verification code sending
            case "sendCode":
                try 
                {                    
                    if (sendCode(request))
                    {
                        request.setAttribute("vCodeMsgSuccess", "test");
                    }                    
                }
                catch (Exception e)
                { 
                    request.setAttribute("vCodeMsg", "There was an error connecting to the SMS service.");
                }
                break;
            // reset password code validation
            case "resetPW":                
                if (resetPassword(request))
                {
                    String resetMsgSuccess = 
                        "Your password has been changed.  Please log in.";
                    request.setAttribute("resetMsgSuccess", resetMsgSuccess);
                }                
                break;
        }        
        
        // redirect to 'url'        
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);  
    }

    
    // check for previous login cookies
    private boolean checkForCookies(HttpServletRequest request, HttpServletResponse response)
    {
        boolean rememberMeOn = false;
        String userName = null;
        String password = null;
        String userType = null;
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (int i = 0; i < cookies.length; i++)
            {
                Cookie c = cookies[i];
                if (c.getName().equals("rememberMe")) 
                {
                    if (c.getValue().equals("no"))
                        break;
                    else if (c.getValue().equals("yes"))
                        rememberMeOn = true;
                }

                if (c.getName().equals("userName"))
                    userName = c.getValue();

                if (c.getName().equals("password"))
                    password = c.getValue();

                if (c.getName().equals("user"))
                    userType = c.getValue();
            }
        }
        
        if (rememberMeOn)
        {
            if (userName == null || password == null || userType == null)
            {
                return false;
            }
            else 
            {
                if (userType.equals("client"))
                    return clientLogIn(request, response, userName, password, false);
                else if (userType.equals("cw"))
                    return caseWorkerLogIn(request, response, userName, password, false);
            }
        }
        return false;
    }

    // deletes all cookies
    private void clearCookies(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (int i = 0; i < cookies.length; i++)
            {
                Cookie c = cookies[i];
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
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
    private boolean clientLogIn(HttpServletRequest request, HttpServletResponse response, String userName, String password, boolean rememberMe)
    {        
        String sqlErrorMsg = "There was an error with the database connection";
        String loginMsg = "";  
        
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
            // create cookies
            if (rememberMe)
            {
                Cookie c = new Cookie("rememberMe", "yes");
                c.setMaxAge(60 * 60);                           
                Cookie c2 = new Cookie("userName", userName);
                c2.setMaxAge(60 * 60);
                Cookie c3 = new Cookie("password", password);
                c3.setMaxAge(60 * 60);
                Cookie c4 = new Cookie("user", "client");
                c4.setMaxAge(60 * 60);
                
                response.addCookie(c); 
                response.addCookie(c);
                response.addCookie(c2);
                response.addCookie(c3);
                response.addCookie(c4);
            }
            return true;
        }
    }
    
    // validates and authenticates case worker login    
    private boolean caseWorkerLogIn(HttpServletRequest request, HttpServletResponse response, String userName, String password, boolean rememberMe)
    {        
        String sqlErrorMsg = "There was an error with the database connection";
        String loginMsg = "";        
        
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
            // create cookies
            if (rememberMe)
            {
                Cookie c = new Cookie("rememberMe", "yes");
                c.setMaxAge(60 * 60);                           
                Cookie c2 = new Cookie("userName", userName);
                c2.setMaxAge(60 * 60);
                Cookie c3 = new Cookie("password", password);
                c3.setMaxAge(60 * 60);
                Cookie c4 = new Cookie("user", "cw");
                c4.setMaxAge(60 * 60);
                
                response.addCookie(c); 
                response.addCookie(c);
                response.addCookie(c2);
                response.addCookie(c3);
                response.addCookie(c4);
            }
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
        
    // handles updating of client account info
    private boolean updateClientInfo(HttpServletRequest request) {
        String manageMsg;
        String sqlErrorMsg = "There was an error with the database connection";
        Client user;
        
        HttpSession session = request.getSession();
        String userName = ((Client)session.getAttribute("user")).getUserName();        
                
        // retrieves name of field being updated
        String fieldName = request.getParameter("fieldName");
            
        if (fieldName == null)
            return false;
        
        // retrieve confirmation password and update field from update form
        String managePasswordCL = (String) request.getParameter("managePasswordCL");        
        String fieldValue = (String) request.getParameter("fieldValue");
        String fieldValue2 = null;
        
        // retain form values
        switch (fieldName) {
            case "password":
                // get second password field if chosen
                fieldValue2 = (String) request.getParameter("fieldValue2");
                request.setAttribute("prevManagePassword1CL", fieldValue);                
                request.setAttribute("prevManagePassword2CL", fieldValue2);
                break;
            case "phone":
                request.setAttribute("prevManagePhoneCL", fieldValue);      
                break;
            case "email":
                request.setAttribute("prevManageEmailCL", fieldValue);      
                break;
            case "username":
                request.setAttribute("prevManageUserNameCL", fieldValue);      
                break;
            case "street":
                request.setAttribute("prevManageStreetCL", fieldValue);    
                break;
            case "city":    
                request.setAttribute("prevManageCityCL", fieldValue);    
                break;
            case "zip":
                fieldValue2 = (String) request.getParameter("fieldValue2");
                request.setAttribute("prevManageZipCL", fieldValue);                
                request.setAttribute("prevManageZipExtCL", fieldValue2);
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
            results = Accounts.authenticateUser(userName, managePasswordCL, "client");
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
            
            // update field in db
            boolean success;            
            if (fieldName.equals("zip"))
            {
                success = Accounts.updateField("clients", userName, "emzip", fieldValue);
                if (success)
                    success = Accounts.updateField("clients", userName, "extzip", fieldValue2);
            }
            else 
            {  
                success = Accounts.updateField("clients", userName, fieldName, fieldValue);
            }           
            
            // if update successful
            if (success)
            {                
                // if user name changed, use the new user name for login attempt
                if (fieldName.equals("username"))
                    userName = fieldValue;
                
                // re create user with updated info
                user = Accounts.logInClient(userName);
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
    
    
    
    // verifies username with ssn, sends verification code to client's phone
    private boolean sendCode(HttpServletRequest request) throws UnsupportedEncodingException, IOException 
    {
        String vCodeMsg = "";
        String sqlErrorMsg = "There was an error with the database connection";
        QueryResult results;
        Client client = null;

        // get user name and ssn from form
        String userName = request.getParameter("vCodeUserNameCL");
        String ssn = request.getParameter("vCodeSSNCL");
        
        // retain form values
        request.setAttribute("prevVCodeUserName", userName);
        request.setAttribute("prevVCodeSSN", ssn);
        
        // verify user name exists        
        results = Accounts.verifyUserName(userName, "client");
        if (!results.successful()) 
        {            
            if (results.sqlErrors())
                vCodeMsg = sqlErrorMsg;
            else                 
                vCodeMsg = "That User Name does not exist";   

            request.setAttribute("vCodeMsg", vCodeMsg);
            return false;
        }      
        else 
        {
            // verify ssn format
            String result = Accounts.isValidUpdateField("ssn", ssn, "");
            if (!result.equals(""))
            {
                vCodeMsg = result;
                request.setAttribute("vCodeMsg", vCodeMsg);
                return false;
            }
            
            // retrieve user based on user name
            client = Accounts.logInClient(userName);
            int id = client.getClientID();
            
            // verify ssn
            results = Accounts.isValidSSN(ssn, id);
            if (!results.successful()) 
            {            
                if (results.sqlErrors())
                    vCodeMsg = sqlErrorMsg;
                else                 
                    vCodeMsg = "The SSN you entered does not match our records.";   

                request.setAttribute("vCodeMsg", vCodeMsg);
                return false;
            }  
        }
        
        // retrieve user phone
        String phone = client.getPhone();
        
        // generate verification code
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++)
        {
            code += random.nextInt(10);
        }
        
        // send text with code        
        boolean success = Accounts.sendSMS(phone, code);
        // store code in session
        if (success)
        {
            HttpSession session = request.getSession();
            session.setAttribute("resetCode", code);
            session.setAttribute("userName", userName);
            return true;
        }
        else 
        {
            vCodeMsg = "There was an error sending text.";   
            request.setAttribute("vCodeMsg", vCodeMsg);
            return false;
        }
    }
    
    // verifies code, validates new password, updates password
    private boolean resetPassword(HttpServletRequest request) 
    {
        String resetMsg = "";
        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("userName");
        String codeSent = (String)session.getAttribute("resetCode");       
        
        // get from form
        String code = request.getParameter("resetCodeCL");
        String password = request.getParameter("resetPwCL");
        String password2 = request.getParameter("resetPw2CL");
        
        // verify code
        if (codeSent == null || !codeSent.equals(code))
        {
            resetMsg = "The code you entered is invalid.";
            request.setAttribute("resetMsg", resetMsg);
            return false;
        }
        
        // verify password length
        if (!Accounts.isValidLength(password, 6))
        {
            resetMsg = "Your password must be at least 6 characters";
            request.setAttribute("resetMsg", resetMsg);
            return false;
        }
        
        // verify passwords match
        String pwMatchMsg = Accounts.isValidUpdateField("password", password, password2);
        if (!pwMatchMsg.isEmpty())
        {
            resetMsg = pwMatchMsg;
            request.setAttribute("resetMsg", resetMsg);
            return false;
        }
        
        // attempt update password
        boolean success = Accounts.updateField("clients", userName, "password", password); 
        // if update successful
        if (!success)         
        {
            resetMsg = "There was an error updating database"; 
            request.setAttribute("resetMsg", resetMsg);
            return false;
        }
        return true;  
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
