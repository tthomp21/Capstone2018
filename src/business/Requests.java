/*
 * Author: Murad
 * Date: 01/15/2018
 * Description: base class for all assistances that a client will get
*/
package business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Murad
 */
public class Requests implements Serializable{
    private Assitance                 request          = null;
    private int                     assistanceID     = 0;
    private Client                  client           = null;
    private LocalDate               requestDate      = null;
    private double                  amountPaid       = 0.0;
    private String                  assistanceStatus = "";
    private LocalDate               dateDisbursed    = null;
    
    //the number of times a client has recieved an assitance might be taken from the list size for that assistance. we will see!!
   // private int                     occurancesPaid = 0;
    
    
    
    public Requests(){}
    public Requests(Assitance theReqestID, LocalDate theDate, double theAmountPaid,  String theassistanceStatus)//, int theOccurences)
    {
        this.requestDate         =   theDate;
        this.amountPaid             =   theAmountPaid;
        this.assistanceStatus         =   theassistanceStatus;
     
    }

 
    
}
