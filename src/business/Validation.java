/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 *
 * @author Tyler Thompson
 */
public class Validation {
    
    public static String isStringPresent(String text, String elementName)
    {
        String message = "";
        if(text.equals("") || text == null)
        {
            message = elementName + " is a required field.";
        }
        return message;
    }
    
    public static String isInteger(String number, String elementName)
    {
        String message = "";
        try{
            Integer.parseInt(number);
        }
        catch(NumberFormatException ex)
        {
            message = elementName + " must be a numeric value.";
        }
        return message;
    }
    
    public static String isDouble(String number, String elementName)
    {
        String message = "";
        try{
            Double.parseDouble(number);
        }
        catch(NumberFormatException ex)
        {
            message = elementName + " must be a numeric value.";
        }
        return message;
    }
    
    public static String isLong(String number, String elementName)
    {
        String message = "";
        try{
            Long.parseLong(number);
        }
        catch(NumberFormatException ex)
        {
            message = elementName + " must be a numeric value";
        }
        return message;
    }
     public static BigDecimal formatRound(double number) {
        BigDecimal decimalRound = new BigDecimal(Double.toString(number));
         decimalRound = decimalRound.setScale(2, RoundingMode.HALF_UP);
         return decimalRound;
    }

    public static String formatRoundDollar(double number) {
        NumberFormat num = NumberFormat.getCurrencyInstance();

        BigDecimal decimalRound = formatRound(number);
        return num.format(decimalRound);
    }
    
}
