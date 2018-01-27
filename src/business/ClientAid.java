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
public class ClientAid extends AssistanceRequest implements Serializable{
    
    
    private     int	         clientID;
    private     String       clientAidDesc;
    private     LocalDate    clientAidDateDisbursed;
    private     AidType      aidType;
    
    public ClientAid(){}
    public ClientAid(int theclientAidID, String theclientAidDesc, LocalDate theAidDateDisbursed, AidType theAidType)
    {
        this.clientID              = theclientAidID;
        this.clientAidDesc         = theclientAidDesc;
        this.clientAidDateDisbursed = theAidDateDisbursed;
        this.aidType              = theAidType;
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
     * @return the clientAidDesc
     */
    public String getClientAidDesc() {
        return clientAidDesc;
    }

    /**
     * @param clientAidDesc the clientAidDesc to set
     */
    public void setClientAidDesc(String clientAidDesc) {
        this.clientAidDesc = clientAidDesc;
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
    
}
