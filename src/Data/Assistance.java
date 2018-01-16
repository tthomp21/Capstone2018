/*
 * Author: Murad
 * Date: 01/15/2018
 * Description: base class for all assistances that a client will get
*/
package Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Murad
 */
public class Assistance implements Serializable{
    
    private LocalDate               assistanceDate = null;
    private double                  amountPaid =0.0;
    private String                  status ="";
    private String                  comments ="";
    private String                  assistanceType="";
    
    //the number of times a client has recieved an assitance might be taken from the list size for that assistance. we will see!!
   // private int                     occurancesPaid = 0;
    
    
    
    public Assistance(){}
    public Assistance(LocalDate theDate, double theAmountPaid, String theStatus, String theComments, String theAssistanceType)//, int theOccurences)
    {
        this.assistanceDate         =   theDate;
        this.amountPaid             =   theAmountPaid;
        this.status                 =   theStatus;
        this.comments               =   theComments;
        this.assistanceType         =   theAssistanceType;
      //  this.occurancesPaid         = theOccurences;
    }

    /**
     * @return the assistanceDate
     */
    public LocalDate getAssistanceDate() {
        return assistanceDate;
    }

    /**
     * @param assistanceDate the assistanceDate to set
     */
    public void setAssistanceDate(LocalDate assistanceDate) {
        this.assistanceDate = assistanceDate;
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
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the assistanceType
     */
    public String getAssistanceType() {
        return assistanceType;
    }

    /**
     * @param assistanceType the assistanceType to set
     */
    public void setAssistanceType(String assistanceType) {
        this.assistanceType = assistanceType;
    }

    /**
     * @return the occurancesPaid
     
    public int getOccurancesPaid() {
        return occurancesPaid;
    }
    */
    /**
     * @param occurancesPaid the occurancesPaid to set
    
    public void setOccurancesPaid(int occurancesPaid) {
        this.occurancesPaid = occurancesPaid;
    }
     */
}
