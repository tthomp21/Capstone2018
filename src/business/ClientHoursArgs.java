/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Ty
 */
public class ClientHoursArgs {
    
    private Double hours;
    private Date date;
    
    public Double getHours()
    {
        return hours;
    }
    
    public Date getDate(){
        return date;
    }
    
    public void setHours(Double hours){
        this.hours = hours;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
}
