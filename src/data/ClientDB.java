/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.AssistanceRequest;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 *
 * @author 
 */
public class ClientDB {
    //TODO: add code to access the clients from the database
          
          
           public static ArrayList<AssistanceRequest> getTestData()
           {
	 ArrayList<AssistanceRequest> all = new ArrayList<AssistanceRequest>();
	 
	 all.add(new AssistanceRequest(1, 1, 1, LocalDate.of(2018, Month.JANUARY, 1), 200, "active", LocalDate.of(2018, Month.JANUARY, 22)));
	 all.add(new AssistanceRequest(2, 2, 2, LocalDate.of(2018, Month.JANUARY, 1), 0,   "pending", LocalDate.of(2018, Month.JANUARY, 22)));
	 all.add(new AssistanceRequest(3, 3, 3, LocalDate.of(2018, Month.JANUARY, 1), 400, "denied", LocalDate.of(2018, Month.JANUARY, 22)));
	 
	 all.add(new AssistanceRequest(4, 2, 4, LocalDate.of(2018, Month.JANUARY, 1), 600, "active", LocalDate.of(2018, Month.JANUARY, 22)));
	 all.add(new AssistanceRequest(5, 3, 2, LocalDate.of(2018, Month.JANUARY, 1), 200, "pending", LocalDate.of(2018, Month.JANUARY, 22)));
	 all.add(new AssistanceRequest(6, 3, 6, LocalDate.of(2018, Month.JANUARY, 1), 200, "active", LocalDate.of(2018, Month.JANUARY, 22)));
	 
	 all.add(new AssistanceRequest(7, 2,31, LocalDate.of(2018, Month.JANUARY, 1), 200, "active", LocalDate.of(2018, Month.JANUARY, 22)));
	 all.add(new AssistanceRequest(8, 2, 3, LocalDate.of(2018, Month.JANUARY, 1), 200, "Pending", LocalDate.of(2018, Month.JANUARY, 22)));
	 all.add(new AssistanceRequest(9, 2, 2, LocalDate.of(2018, Month.JANUARY, 1), 200, "active", LocalDate.of(2018, Month.JANUARY, 22)));
	 
	 return all;
           }
    
}
