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

    /**
     * @return the anAssistance
     */
    public Assitance getAnAssistance() {
        return anAssistance;
    }

    /**
     * @param anAssistance the anAssistance to set
     */
    public void setAnAssistance(Assitance anAssistance) {
        this.anAssistance = anAssistance;
    }

    /**
     * @return the assistanceID
     */
    public int getAssistanceID() {
        return assistanceID;
    }

    /**
     * @param assistanceID the assistanceID to set
     */
    public void setAssistanceID(int assistanceID) {
        this.assistanceID = assistanceID;
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return the requestDate
     */
    public LocalDate getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the amountPaid
     */
    public double getAmountPaid() {
        return amountPaid;
    }

    /**
     * @param amountPaid the amountPaid to set
     */
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    /**
     * @return the assistanceStatus
     */
    public String getAssistanceStatus() {
        return assistanceStatus;
    }

    /**
     * @param assistanceStatus the assistanceStatus to set
     */
    public void setAssistanceStatus(String assistanceStatus) {
        this.assistanceStatus = assistanceStatus;
    }

    /**
     * @return the dateDisbursed
     */
    public LocalDate getDateDisbursed() {
        return dateDisbursed;
    }

    /**
     * @param dateDisbursed the dateDisbursed to set
     */
    public void setDateDisbursed(LocalDate dateDisbursed) {
        this.dateDisbursed = dateDisbursed;
    }



}
