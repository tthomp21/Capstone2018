/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author ms610358
 */
public class ClientAid implements Serializable{
    
    
    private     int	     clientID;
    private     String       aidAmount;
    private     LocalDate    clientAidDateDisbursed;
    private     AidType      aidType; // this is for food stams ... (primary)
    
    public ClientAid(){}
    public ClientAid(int theclientAidID, LocalDate theAidDateDisbursed, String aidAmount, AidType theAidType)
    {
        this.clientID              = theclientAidID;
        this.clientAidDateDisbursed = theAidDateDisbursed;
        this.aidType              = theAidType;
        this.aidAmount              = aidAmount;
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
     * @return the clientAidDateDisbursed
     */
    public LocalDate getClientAidDateDisbursed() {
        return clientAidDateDisbursed;
    }

    /**
     * @param clientAidDateDisbursed the clientAidDateDisbursed to set
     */
    public void setClientAidDateDisbursed(LocalDate clientAidDateDisbursed) {
        this.clientAidDateDisbursed = clientAidDateDisbursed;
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
     * @param aidAmount the aidAmount to set
     */
    public void setAidAmount(String aidAmount) {
        this.aidAmount = Validation.formatRoundDollar(Double.parseDouble(aidAmount));
    }

    /**
     * @return the aidAmount
     */
    public String getAidAmount() {
        return aidAmount;
    }
    
}
