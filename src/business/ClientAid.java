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
    private double aidAmountDouble;
    private String assistanceStatus;
    private String medicAidCode; // it is not tangible amount, 0 = denied, 1 = active.
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
        this.aidAmountDouble = Double.parseDouble(aidAmount);
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

    /**
     * @return the aidAmountDouble
     */
    public double getAidAmountDouble() {
        return aidAmountDouble;
    }

    /**
     * @param aidAmountDouble the aidAmountDouble to set
     */
    public void setAidAmountDouble(double aidAmountDouble) {
        this.aidAmountDouble = aidAmountDouble;
    }

    /**
     * @return the assistanceStatus
     */
    public String getAssistanceStatus() {
        return assistanceStatus;
    }

    /**
     * @param assistanceStatus the assistanceStatus to set
     */
    public void setAssistanceStatus(String assistanceStatus) {
        this.assistanceStatus = assistanceStatus;
    }

    /**
     * @return the medicAidCode
     */
    public String getMedicAidCode() {
        return medicAidCode;
    }

    /**
     * @param medicAidCode the medicAidCode to set
     */
    public void setMedicAidCode(String medicAidCode) {
        this.medicAidCode = medicAidCode;
    }

    public static Comparator<ClientAid> sortAssistanceListByDate = new Comparator<ClientAid>() {

        public int compare(ClientAid o1, ClientAid o2) {

            LocalDate date1 = o1.getClientAidDateDisbursed();
            LocalDate date2 = o2.getClientAidDateDisbursed();
            
//            String date1 = o1.getAidDateDisbursedFormatted();
//            String date2 = o2.getAidDateDisbursedFormatted();

            //descending order, newest date to oldest, the most current one
            //return date2.compareTo(date1);

            //ascending order, oldest date to newest
             return date2.compareTo(date1);
        }

    };

    @Override
    public int compare(ClientAid o1, ClientAid o2) {
        return 0;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
