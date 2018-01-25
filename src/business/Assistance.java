/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;

/**
 *
 * @author ms610358
 */
public class Assistance implements Serializable{
    
    
    private     int     assistanceID;
    private     String  assistanceDescription;
    
    public Assistance(){}
    public Assistance(int theAssistanceID, String theAssistanceDescription)
    {
        this.assistanceID           = theAssistanceID;
        this.assistanceDescription  = theAssistanceDescription;
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
     * @return the assistanceDescription
     */
    public String getAssistanceDescription() {
        return assistanceDescription;
    }

    /**
     * @param assistanceDescription the assistanceDescription to set
     */
    public void setAssistanceDescription(String assistanceDescription) {
        this.assistanceDescription = assistanceDescription;
    }
    
}
