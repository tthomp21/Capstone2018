/*
 * Author: Murad
 * Date: 01/15/2018
 * Description: base class for all assistances that a client will get
 */
package business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import javax.swing.text.DateFormatter;

/**
 * 1/23/2018
 *
 * @author Murad
 *
 * implement comparator so you get he advantage of sorting the list based on any
 * property/field you want.
 */
public class AssistanceRequest implements Serializable, Comparator<AssistanceRequest> {

    private int requestID;
    private Assistance anAssistance;
    private int assistanceID;
    private int clientID;
    private LocalDate requestDate;
    private String amountPaid;
    //private double amountPaid;
    private String status;
    private LocalDate dateDisbursed;
    
    //private String    assistanceDescription;
    private AidType aidType;

    //the number of times a client has recieved an assitance might be taken from the list size for that assistance. we will see!!
    // private int                     occurancesPaid = 0;
    public AssistanceRequest() {
    }

    public AssistanceRequest(int requestID, int assistanceID, int clientID, LocalDate requestDate, String amountPaid, String status, LocalDate dateDisbursed) {
        this.requestID = requestID;
        this.assistanceID = assistanceID;
        this.clientID = clientID;
        this.requestDate = requestDate;
        this.amountPaid = amountPaid;
        this.status = status;
        this.dateDisbursed = dateDisbursed;

    }

    /**
     * @return the anAssistance
     */
    public Assistance getAssistance() {
        return anAssistance;
    }

    /**
     * @param anAssistance the anAssistance to set
     */
    public void setAssistance(Assistance anAssistance) {
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
     * @return the clientID
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * @param clientID the clientID to set
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
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
     
    public double getAmountPaid() {
        return amountPaid;
    }
    * */
    /**
     * @param amountPaid the amountPaid to set
     
    public void setAmountPaid(double amountPaid) {
        this.setAmountPaid(amountPaid);
    }
*/
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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

    /**
     * @return the requestID
     */
    public int getRequestID() {
        return requestID;
    }

    /**
     * @param requestID the requestID to set
     */
    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    
    /**
     * @return the aidType
     */
    public AidType getAidType() {
        return aidType;
    }

    /**
     * @param aidType the aidType to set
     */
    public void setAidType(AidType aidType) {
        this.aidType = aidType;
    }

    /**
     * @param amountPaid the amountPaid to set
     */
    public void setAmountPaid(String amountPaid) {
        this.amountPaid = Validation.formatRoundDollar(Double.parseDouble(amountPaid));
    }

    /**
     * @return the amountPaid
     */
    public String getAmountPaid() {
        return amountPaid;
    }

 
    
    /**
     * Sorting the list. based on the field chosen. for this example by
     * disbursedDate
     */
    public static Comparator<AssistanceRequest> sortAssistanceListByDate = new Comparator<AssistanceRequest>() {

        public int compare(AssistanceRequest o1, AssistanceRequest o2) {

            LocalDate date1 = o1.getDateDisbursed();
            LocalDate date2 = o2.getDateDisbursed();

            //descending order, newest date to oldest, the most current one
            return date2.compareTo(date1);

            //ascending order, oldest date to newest
            // return date1.compareTo(date2);
        }

    };

    @Override
    public int compare(AssistanceRequest o1, AssistanceRequest o2) {
        return 0; // just to make the implimentation happy. 
    }

}
