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
    
    private int     assistanceID;
    private String  assistDescription;
    
    public Assistance(){}
    
    
    public Assistance(int assistanceID, String assisDescription){
        this.assistanceID = assistanceID;
        this.assistDescription = assisDescription;
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
     * @return the assistDescription
     */
    public String getAssistDescription() {
        return assistDescription;
    }

    /**
     * @param assistDescription the assistDescription to set
     */
    public void setAssistDescription(String assistDescription) {
        this.assistDescription = assistDescription;
    }
}
