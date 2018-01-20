// Sayel Rammaha    
// 1/20/18
// accounts validation methods for AccountsController.java

package business;
import data.*;

public class Accounts {
    private Accounts() {}
    
    
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
        QueryResult result = new QueryResult(false, true);        
        return result;
    }   
    
    // checks clientID for a match in the database
    public static QueryResult isValidClientID(int clientID)
    {
        QueryResult result = AccountDB.clientIdExists(clientID);
        return result;
    }
    
    // checks ssn against matching client record in the database
    public static QueryResult isValidSSN(String ssn, int clientID)
    {
        QueryResult result = new QueryResult(false, true);        
        return result;
    }
    
    // makes call db layer to add username and password to client record
    public static boolean setUserNameAndPassword(String userName, String password, int clientID)
    {        
        boolean success = false;
        return success;
    }
}
