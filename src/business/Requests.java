/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.RequestDB;
import java.sql.SQLException;


public class Requests {
    // numbers are loose for testing purposes to allow many requests
    // to make testing file upload/re serving to cw easier
    private static final int maxTuitionOccurrences = 2;
    private static final double maxTuitionAmount = 9999;
    private static final int tuitionRange = 12;
    
    private static final int maxUtilitiesOccurrences = 2;
    private static final double maxUtilitiesAmount = 150;
    private static final int utilitiesRange = 12;
    
    private static final int maxRepairOccurrences = 2;
    private static final double maxRepairAmount = 9999;
    private static final int repairRange = 12;
    
    private static final int maxRegistrationOccurrences = 1;
    private static final double maxRegistrationAmount = 200;
    private static final int registrationRange = 12;
    
    private static final int maxFuelOccurrences = 10;
    private static final double maxFuelAmount = 200;
    private static final int fuelRange = 12;
    
    private static final int maxClothingOccurrences = 2;
    private static final double maxClothingAmount = 200;
    private static final int clothingRange = 12;
    
    // validates requests for assistance according to business rules limits on payouts
    public static MessageResult validate(String requestType, int clientID, double amount)
    {
        MessageResult result = new MessageResult();
        boolean validType = true;
        
        int maxOccurrences = 0;
        double maxAmount = 0;
        int range = 0;

        switch (requestType)
        {
            case "tuition":
                maxOccurrences = maxTuitionOccurrences;
                maxAmount = maxTuitionAmount;
                range = tuitionRange;                
                break;
            case "utilities":
                maxOccurrences = maxUtilitiesOccurrences;
                maxAmount = maxUtilitiesAmount;
                range = utilitiesRange;                
                break;
            case "repair":
                maxOccurrences = maxRepairOccurrences;
                maxAmount = maxRepairAmount;
                range = repairRange;                
                break;
            case "fuel":
                maxOccurrences = maxFuelOccurrences;
                maxAmount = maxFuelAmount;
                range = fuelRange;                
                break;
            case "registration":
                maxOccurrences = maxRegistrationOccurrences;
                maxAmount = maxRegistrationAmount;
                range = registrationRange;                
                break;
            case "clothing":
                maxOccurrences = maxClothingOccurrences;
                maxAmount = maxClothingAmount;
                range = clothingRange;                
                break;
            default:
                validType = false;
                break;
        }
        
        if (!validType) // from altering the select option menu box for asst type
        {               // to something other than the options provided
            result.setResult(false);
            result.setMessage("There was an error processing your request.  Please try again.");
            return result;
        }
        else
        {
            try 
            {
                // check other requests of that type, during that range for that client to determine if they have reached the limits
                // amount of times given this assistance
                result.setResult(RequestDB.checkRequestOccurrenceLimit(requestType, clientID, maxOccurrences, range));
                if (!result.successful())
                {
                    result.setMessage("You have exceeded the amount of " + requestType + " payments (" + 
                        maxOccurrences + ") in the last " + range + " months.");
                    return result;
                }
                else 
                {
                    // total dollar amount given for this type of assistance
                    result.setResult(RequestDB.checkRequestAmountLimit(requestType, clientID, maxAmount, range));
                    if (!result.successful())
                    {
                        result.setMessage("You have exceeded the dollar amount limit for " + requestType + " payments (" + 
                            maxAmount + ") in the last " + range + " months.");
                        return result;
                    }
                }
            }
            catch (SQLException e) // db error
            {
                result.setResult(false);
                result.setMessage("There was an error connecting to the database.");
                return result;
            }
        }
        // no errors, valid request
        result.setResult(true);
        result.setMessage("");
        return result;
    }
    
    public static MessageResult checkForSanction(int clientID)
    {
        MessageResult result = new MessageResult();
        try {
            result = RequestDB.checkForSanction(clientID);
        }
        catch (SQLException e)
        {
            result.setResult(false);
            result.setMessage("There was an error connecting to the database.");
        }         
        
        return result;       
    }
}
