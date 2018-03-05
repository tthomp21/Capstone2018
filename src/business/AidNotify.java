/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Murad
 */
public class AidNotify {
    
    private int	    clientID;
    private int	    aidTypeID;
    private LocalDate   recertificationDate;
    private String	    recertDateFormatted;
    
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
     * @return the recertDateFormatted
     */
    public String getRecertDateFormatted() {
        return recertDateFormatted;
    }

    /**
     * @param recertDateFormatted the recertDateFormatted to set
     */
    public void setRecertDateFormatted(String recertDateFormatted) {
        this.recertDateFormatted = recertDateFormatted;
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
        
         DateTimeFormatter formatter	= DateTimeFormatter.ofPattern("MM/dd/yyyy");
         this.recertDateFormatted =  recertificationDate.format(formatter);
         this.recertificationDate = recertificationDate;
    }
    
    
    
    
    
}
