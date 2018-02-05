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
 * @author radad
 */
public class Sanction implements Serializable {

    private int sanctionID;
    private int clientID;
    private LocalDate sanctionDate;
    private int sanctionLength;

    private AidType aidType;

    public Sanction() {
    }

    public Sanction(int sanctionID, int clientID, LocalDate sanctionDate, int sanctionLength, AidType aidType) {
        this.sanctionID = sanctionID;
        this.clientID = clientID;
        this.sanctionDate = sanctionDate;
        this.sanctionLength = sanctionLength;
        this.aidType = aidType;
    }

    /**
     * @return the sanctionID
     */
    public int getSanctionID() {
        return sanctionID;
    }

    /**
     * @param sanctionID the sanctionID to set
     */
    public void setSanctionID(int sanctionID) {
        this.sanctionID = sanctionID;
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
     * @return the sanctionDate
     */
    public LocalDate getSanctionDate() {
        return sanctionDate;
    }

    /**
     * @param sanctionDate the sanctionDate to set
     */
    public void setSanctionDate(LocalDate sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    /**
     * @return the sanctionLength
     */
    public int getSanctionLength() {
        return sanctionLength;
    }

    /**
     * @param sanctionLength the sanctionLength to set
     */
    public void setSanctionLength(int sanctionLength) {
        this.sanctionLength = sanctionLength;
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
