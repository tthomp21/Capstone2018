/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 *
 * @author ms610358
 */
public class ClientAid implements Serializable, Comparator<ClientAid> {

    private int clientID;
    private String aidAmount;
    private LocalDate clientAidDateDisbursed;
    private AidType aidType; // this is for food stams ... (primary)
    private String aidDateDisbursedFormatted;

    public ClientAid() {
    }

    public ClientAid(int theclientAidID, LocalDate theAidDateDisbursed, String aidAmount, AidType theAidType) {
        this.clientID = theclientAidID;
        this.clientAidDateDisbursed = theAidDateDisbursed;
        this.aidType = theAidType;
        this.aidAmount = aidAmount;
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
        
        DateTimeFormatter formatter	= DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.aidDateDisbursedFormatted  = clientAidDateDisbursed.format(formatter);
        this.clientAidDateDisbursed	= clientAidDateDisbursed;
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

    /**
     * @return the aidDateDisbursedFormatted
     */
    public String getAidDateDisbursedFormatted() {
        return aidDateDisbursedFormatted;
    }

    /**
     * @param aidDateDisbursedFormatted the aidDateDisbursedFormatted to set
     */
    public void setAidDateDisbursedFormatted(String aidDateDisbursedFormatted) {
        this.aidDateDisbursedFormatted = aidDateDisbursedFormatted;
    }

    public static Comparator<ClientAid> sortAssistanceListByDate = new Comparator<ClientAid>() {

        public int compare(ClientAid o1, ClientAid o2) {

            //LocalDate date1 = o1.getClientAidDateDisbursed();
            //LocalDate date2 = o2.getClientAidDateDisbursed();
            
            String date1 = o1.getAidDateDisbursedFormatted();
            String date2 = o2.getAidDateDisbursedFormatted();

            //descending order, newest date to oldest, the most current one
            //return date2.compareTo(date1);

            //ascending order, oldest date to newest
             return date1.compareTo(date2);
        }

    };

    @Override
    public int compare(ClientAid o1, ClientAid o2) {
        return 0;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
