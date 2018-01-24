// Sayel Rammaha    
// 1/20/18
// accounts validation and intermediary methods between
// AccountsController.java and AccountDB.java


package business;
import data.*;
import java.sql.SQLException;

public class Accounts {
    private Accounts() {}
    
    // ----------------------------
    // |*** VALIDATION METHODS ***|
    // ----------------------------
       
    // dynamically validate a given account info update field
    public static String isValidUpdateField(String fieldName, String fieldValue, String fieldValue2)
    {
        return "";
    }

    // checks test against minCharLength
    public static boolean isValidLength(String test, int minCharLength)
    {        
        return test.length() >= minCharLength;        
    }
    
    // checks string: test for integer parse
    public static boolean isInteger(String test)
    {
        try 
        {
            Integer.parseInt(test);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }            
    }

    // checks desiredUserName for uniqueness
    public static QueryResult isValidUserName(String desiredUserName)
    {        
        QueryResult result = new QueryResult();
        try {
            result.setResult(AccountDB.isUserNameUnique(desiredUserName, "client"));
        }
        catch (SQLException e) {
            result.setSqlError(true);
        }
        
        return result; 
    }   
    
    // looks up user name in client table 
    public static QueryResult verifyUserName(String userName, String user)
    {   
        QueryResult result = new QueryResult();
        try {
            result.setResult(!AccountDB.isUserNameUnique(userName, user));
        }
        catch (SQLException e) {
            result.setSqlError(true);
        }
        
        return result; 
    }   
    
    // checks clientID for a match in the database
    // result.result(): boolean for valid id
    // result.sqlErrors(): boolean for sql errors during query
    public static QueryResult isValidClientID(int clientID)
    {
        QueryResult result = new QueryResult();
        try {
            result.setResult(AccountDB.clientIdExists(clientID));
        }
        catch (SQLException e) {
            result.setSqlError(true);
        }
        
        return result;     
    }
    
    // checks ssn against matching client record in the database
    public static QueryResult isValidSSN(String ssn, int clientID)
    {
        QueryResult result = new QueryResult();
        try {
            result.setResult(AccountDB.verifySSN(clientID, ssn));
        }
        catch (SQLException e) {
            result.setSqlError(true);
        }
        
        return result;    
    }
    
    // checks if user has already created an account
    public static QueryResult accountCreationAvailable(int clientID)
    {
        QueryResult result = new QueryResult();
        try {
            result.setResult(!AccountDB.accountExists(clientID));
        }
        catch (SQLException e) {
            result.setSqlError(true);
        }
        
        return result;    
    }
    
    // verifies entered password is correct
    public static QueryResult authenticateUser(String userName, String password, String user)
    {
        QueryResult result = new QueryResult();
        try {
            result.setResult(AccountDB.verifyPassword(userName, password, user));
        }
        catch (SQLException e) {
            result.setSqlError(true);
        }
        
        return result;    
    }
    
    
    
    // --------------------------
    // |*** DATABASE ACTIONS ***|
    // --------------------------
    
    // makes call to db layer to add username and password to client record
    public static boolean registerAccount(String userName, String password, int clientID)
    {        
        boolean success;
        try {
            success = AccountDB.setClientCredentials(userName, password, clientID);
        }
        catch (SQLException e) {
            return false;
        }
        return success;
    }
    
    // makes call to db layer to update a field in client or caseworker table
    public static boolean updateField(String userType, String userName, String fieldName, String fieldValue)
    {        
        boolean success;
        try {
            success = AccountDB.updateInfo(userType, userName, fieldName, fieldValue);
        }
        catch (SQLException e) {
            return false;
        }
        return success;
    }
    
    
    // makes call to db layer to retrieve client object from username
    public static Client logInClient(String userName)
    {        
        Client client;
        try {
            client = AccountDB.getClient(userName);
            return client;
        }
        catch (SQLException e) {
            return null;
        } 
    }
    
    // makes call to db layer to retrieve caseworker object from username
    public static CaseWorker logInCaseWorker(String userName)
    {        
        CaseWorker cw;
        try {
            cw = AccountDB.getCaseWorker(userName);
            return cw;
        }
        catch (SQLException e) {
            return null;
        } 
    }
}
