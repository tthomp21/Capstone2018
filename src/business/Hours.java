/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author ms610358
 */
public class Hours implements Serializable {
    
    private Client      aClientInHours;
    private LocalDate   dateHoursEntered;
    private double      numberOfHours;
    private double      totalHoursForEach;   
    private String	    dateHoursEnteredFormatted;
    public Hours(){}
    
    
    public Hours(Client aClientInHours, LocalDate  dateHoursEntered, double numberOfHours){
        
        this.aClientInHours     =   aClientInHours;
        this.dateHoursEntered   =   dateHoursEntered;
        this.numberOfHours      =   numberOfHours; 
    }

    /**
     * @return the aClientInHours
     */
    public Client getaClientInHours() {
        return aClientInHours;
    }

    /**
     * @param aClientInHours the aClientInHours to set
     */
    public void setaClientInHours(Client aClientInHours) {
        this.aClientInHours = aClientInHours;
    }

    /**
     * @return the aClients_Partner
     */
//    public Client getaClients_Partner() {
//        return aClients_Partner;
//    }

    /**
     * @return the totalHoursForEach
     */
    public double getTotalHoursForEach() {
        return totalHoursForEach;
    }

    /**
     * @param totalHours the totalHoursForEach to set
     */
    public void setTotalHours(double totalHours) {
        this.totalHoursForEach += this.getNumberOfHours();
    }

    /**
     * @param aClients_Partner the aClients_Partner to set
     */
//    public void setaClients_Partner(Client aClients_Partner) {
//        this.aClients_Partner = aClients_Partner;
//    }

    /**
     * @return the dateHoursEntered
     */
    public LocalDate getDateHoursEntered() {
        return dateHoursEntered;
    }

    /**
     * @param dateHoursEntered the dateHoursEntered to set
     */
    public void setDateHoursEntered(LocalDate dateHoursEntered) {
          DateTimeFormatter formatter	= DateTimeFormatter.ofPattern("MM/dd/yyyy");
         this.dateHoursEnteredFormatted =  dateHoursEntered.format(formatter);
        this.dateHoursEntered = dateHoursEntered;
    }

    /**
     * @return the numberOfHours
     */
    public double getNumberOfHours() {
        return numberOfHours;
    }

    /**
     * @param numberOfHours the numberOfHours to set
     */
    public void setNumberOfHours(double numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    /**
     * @return the dateHoursEnteredFormatted
     */
    public String getDateHoursEnteredFormatted() {
        return dateHoursEnteredFormatted;
    }

    /**
     * @param dateHoursEnteredFormatted the dateHoursEnteredFormatted to set
     */
    public void setDateHoursEnteredFormatted(String dateHoursEnteredFormatted) {
        this.dateHoursEnteredFormatted = dateHoursEnteredFormatted;
    }
    
    
    
    
}
