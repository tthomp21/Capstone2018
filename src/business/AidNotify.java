/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.time.LocalDate;

/**
 *
 * @author Murad
 */
public class AidNotify {
    
    private int	    clientID;
    private int	    aidTypeID;
    private LocalDate   recertificationDate;
    
    
    public AidNotify(){}

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
     * @return the aidType
     */
    public int getAidTypeID() {
        return aidTypeID;
    }

    /**
     * @param aidType the aidType to set
     */
    public void setAidTypeID(int aidTypeID) {
        this.aidTypeID = aidTypeID;
    }

    /**
     * @return the recertificationDate
     */
    public LocalDate getRecertificationDate() {
        return recertificationDate;
    }

    /**
     * @param recertificationDate the recertificationDate to set
     */
    public void setRecertificationDate(LocalDate recertificationDate) {
        this.recertificationDate = recertificationDate;
    }
    
    
    
    
    
}
