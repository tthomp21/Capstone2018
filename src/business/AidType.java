/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;

/**
 *
 * @author Murad
 */
public class AidType implements Serializable{
    
    private     int	    aidID;
    private     String  aidDescription;
    
    public AidType(){}
    public AidType(int theAidID, String theAidDescription){
        this.aidID        =   theAidID;
        this.aidDescription =   theAidDescription;
    }

    /**
     * @return the aidID
     */
    public int getAidID() {
        return aidID;
    }

    /**
     * @param aidID the aidID to set
     */
    public void setAidID(int aidID) {
        this.aidID = aidID;
    }

    /**
     * @return the aidDescription
     */
    public String getAidDescription() {
        return aidDescription;
    }

    /**
     * @param aidDescription the aidDescription to set
     */
    public void setAidDescription(String aidDescription) {
        this.aidDescription = aidDescription;
    }
    
    
}
