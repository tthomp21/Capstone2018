// Sayel Rammaha    
// 1/20/18
// accounts validation and intermediary methods between
// AccountsController.java and AccountDB.java


package business;
import data.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Accounts {
    private Accounts() {}
    
    // ----------------------------
    // |*** VALIDATION METHODS ***|
    // ----------------------------
       
    // dynamically validate a given account info update field
    public static String isValidUpdateField(String fieldName, String fieldValue, String fieldValue2)
    {
        switch (fieldName)
        {
            case "phone":
                String phonePattern = "\\d{10}";
                if (!fieldValue.matches(phonePattern))
                    return "Please enter a 10-digit phone number.";
                break;
            case "email":
                String emailPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";                              
                if (!fieldValue.matches(emailPattern))
                    return "Please enter a valid email address.";
                break;
            case "street":                
                if (!isValidLength(fieldValue, 8))
                    return "Please enter a valid street address.";
                break;
            case "city":                
                if (!isValidLength(fieldValue, 3))
                    return "Please enter a valid city.";
                break;
            case "zip":                
                String zipPattern = "\\d{5}";
                String zipExtPattern = "\\d{4}";
                if (!fieldValue.matches(zipPattern))
                    return "Please enter a 5-digit zip code.";
                if (!fieldValue2.matches(zipExtPattern))
                    return "Please enter a 4-digit zip code extension.";
                break;
            case "password":
                if (!fieldValue.equals(fieldValue2))
                    return "Passwords must match.";
                if (!isValidLength(fieldValue, 6))
                    return "Password must be at least 6 characters.";
                break;
            case "userName":
                if (!isValidLength(fieldValue, 6))
                    return "User Name must be at least 6 characters.";
                break;
        }
        
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
    
    
    
    
    // sends sms to given phone number with given code to verify password reset
    public static boolean sendSMS(String phone, String code) throws UnsupportedEncodingException, IOException
    {
        final String msg = "Someone has requested to reset your Team Cash Flow " +
                "password.  Your verification number is:  "; 
        final String msg2 = "If this was a mistake, you can disregard this message.";
        
        final NameValuePair[] data = {
            new BasicNameValuePair("phone", phone),
            new BasicNameValuePair("message", msg + code + " - " + msg2),
            new BasicNameValuePair("key", "9f70ff94db13cf920ae4dd3d9b9ebf9ea62b1daezhhICAVvELkpmwJ8PLuO0LsnS_test")
        };
        HttpClient httpClient = HttpClients.createMinimal();
        HttpPost httpPost = new HttpPost("https://textbelt.com/text");
        httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(data)));
        HttpResponse httpResponse = httpClient.execute(httpPost);

        String responseString = EntityUtils.toString(httpResponse.getEntity());        
        
        return !responseString.substring(11, 16).equals("false");
    }
}
