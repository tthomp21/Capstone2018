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
public class AssistanceRequest implements Serializable{
    private Assitance               anAssistance     = null;
    private int                     assistanceID     = 0;
    private Client                  client           = null;
    private LocalDate               requestDate      = null;
    private double                  amountPaid       = 0.0;
    private String                  assistanceStatus = "";
    private LocalDate               dateDisbursed    = null;

    //the number of times a client has recieved an assitance might be taken from the list size for that assistance. we will see!!
   // private int                     occurancesPaid = 0;



    public AssistanceRequest(){}
    public AssistanceRequest(Assitance theReqestID, LocalDate theDate, double theAmountPaid,  String theassistanceStatus)//, int theOccurences)
    {
        this.client = null;
        this.requestDate         =   theDate;
        this.amountPaid             =   theAmountPaid;
        this.assistanceStatus         =   theassistanceStatus;

    }



}
