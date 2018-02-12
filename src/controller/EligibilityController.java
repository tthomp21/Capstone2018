/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Client;
import business.Hours;
import business.Sanction;
import data.ClientDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author radad
 */
public class EligibilityController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     * 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
        
        
        /**
         * to view the eligibility for ADC:
         * 1- check the marriage status
         *      ---> if married get the partnerID for the underlying/signed in client do step 2 otherwise check step 3 for single person against 20 hours a week.
         * 2- check the hours for both and has to be 35 hours for the current week, or total of 35/week * 4 = 140hours/month. 
         *      ---> another option is to notify the client for the shortage of hours if there is any; 
         * 3- if not married, it means the client is a single parent with dependents, his/her hours =20/week * 4 = 80/month
         * 
         * 4- once hours are checked check the sanctions as follow, sanctions 1, 2, 3 are types of sanctions 1s, 2nd, 3rd
         *      ---> if sanction length is 1, it means that current date - sanction date has to be > 30 days so the client can be eligible for the assistance, mainly ADC
         *      ---> if sanction length is 2, it means that current date - sanction date has to be > 90 days so the client can be eligible for the assistance, mainly ADC
         *      ---> if sanction length is 3, it means that current date - sanction date has to be > 365 days so the client can be eligible for the assistance, mainly ADC
         * 5- for the secondary assistances follow this steps:
         *      ---> clothing: $200 once a year., how this should be handled; is it just something that assumed it is given regularly or just $200.
         *      ---> vehicle registeration: one time and only one. 
         *      ---> fuel: once a week, $20 dollars each.
         *      ---> tuition: one time and only once.
         *      ---> car repairs: depends may be once or once per year.
         *      --->
         *  6- done forget about SCM.TCF_AIDNOTIFY
         * 
         * ******************************************************************
         * build a select tag to allow the client check for the hours based on the week, 1,2,3, and 4.
         * call  a common method that would get the hours for the week been selected and the one before it, for example 1st week, I need date obj for first of month, and 7th of the month.
         * may be add two input box of type date to select what date want to be selected
         * add two radio buttons to check if the client want to get hours for both
         * 
         */ 
        
        
        HttpSession session = request.getSession();
        ServletContext cs = session.getServletContext();

       
        Client aClient = (Client) session.getAttribute("user"); // get this from successful login.
        ArrayList<Hours> clientsHours  = (ArrayList<Hours>) session.getAttribute("clientsHours");
        ArrayList<Hours> clientsPartnerHours  = (ArrayList<Hours>) session.getAttribute("clientsPartnerHours");
        ArrayList<Sanction> clientSanctions = (ArrayList<Sanction>)session.getAttribute("clientSanctions");
        Boolean isSanctioned = (Boolean)session.getAttribute("isSanctioned");
        String url = "/";
        String lowHoursMsg ="";
        String warningMsg ="";
        String periodToWaitToB_Eligible ="";
        boolean isMarried;
       
        double clientTotalHours = 0, partnerTotalHours =0;
                
        if(clientsHours == null)
        {
            clientsHours = new ArrayList<Hours>();
        }if(clientsPartnerHours == null){
            clientsPartnerHours = new ArrayList<Hours>();

        }if(clientSanctions == null){
            clientSanctions = new ArrayList<Sanction>();
        }
        if(isSanctioned == null){
        isSanctioned = false;
        }

        isMarried = checkMarriageStatus(aClient);
        warningMsg = getWarningMessage(aClient, isMarried);
        session.setAttribute("warningMsg", warningMsg);
        url ="/views/viewEligibility.jsp";
     //   cs.getRequestDispatcher(url).forward(request, response);
                
               
       try{ 
               //get sanction first , if there is any for the client then it is applied to the partner as well
               
               clientSanctions = ClientDB.getClientSanctions(aClient.getClientID());
               //this method also set the atrribute of session, to periodToWaitToB_Eligible
               isSanctioned = getSanctionStatus(clientSanctions, session);
               
              
               
               
               
        //********************if there is no sanction then start count hours *******************************
        /**it would be nice if know how many hours each of client and the partner required.check marriage status
	 * 
	 * lets get the hours from the begining of this month to today.
	 * LocalDate today = LocalDate.now();
	 * LocalDate firstOfMonth   = today.withDayOfMonth(1);
	 * 
	 */
	
	LocalDate todaysDate = LocalDate.now();
	LocalDate firstOfMonth   = todaysDate.withDayOfMonth(1);
	 
               clientsHours =   ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, todaysDate.withDayOfMonth(15)); // hours for the client are needed anyway; but parter's hours are only needed if married
                 if(aClient.getPartnerID() != 0 & aClient.getPartnerID()+"" != " " & aClient.getPartnerID()+"" != null ){
	    //get hours for the couple from the db
	    clientsPartnerHours = ClientDB.getClientHoursForWholeMonth(aClient.getClientID());
	    //get total hours for couples
	    partnerTotalHours = getTotalHours(clientsPartnerHours);
	    boolean isGoodOnHours = checkHoursStatus(clientTotalHours, partnerTotalHours);
	    
	    //getHoursForSpecificWeek(request, response, session, cs);
	}
               
              //  int time = session.getMaxInactiveInterval();
                //these will serve as argument the query will be based on.
                 LocalDate today = LocalDate.now();
            
               // LocalDate firstOfMonth   = today.withDayOfMonth(1);
                LocalDate firstWeek      = today.withDayOfMonth(7);
                LocalDate secondWeek     = today.withDayOfMonth(14);
                LocalDate thirdWeek      = today.withDayOfMonth(21);
                LocalDate fourthWeek     = today.withDayOfMonth(28);
                LocalDate endOfTheMonth  = today.withDayOfMonth(today.lengthOfMonth());
                
                String weekOrMonth = (String)session.getAttribute("weekHours"); 
                switch(weekOrMonth){
	case "first":  clientTotalHours = getTotalHours(clientsHours);
	    break;
	case "second":
	// more case based on the whether single or couple    
	    
                }
        }
        catch(Exception ex){
	System.out.print(ex.equals(ex));
        }finally{

        }
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private double getTotalHours(ArrayList<Hours> clientsHours) {
	    double clientTotalHours =0;
	     for(Hours hrs: clientsHours){
	        clientTotalHours += hrs.getNumberOfHours();
	        
	    }
	   return clientTotalHours;
    }
    
    //this method may be deleted later it was just for testing.
    private boolean checkHoursStatus(double clientTotalHours, double partnerTotalHours) {
            boolean isGoodOnHours = true;
            
            LocalDate today = LocalDate.now();
            
            LocalDate firstOfMonth   = today.withDayOfMonth(1);
            LocalDate firstWeek      = today.withDayOfMonth(7);
            LocalDate secondWeek     = today.withDayOfMonth(14);
            LocalDate thirdWeek      = today.withDayOfMonth(21);
            LocalDate fourthWeek     = today.withDayOfMonth(28);
            LocalDate endOfTheMonth  = today.withDayOfMonth(today.lengthOfMonth());
            
         //   String dayofWeek = today.getDayOfWeek().FRIDAY +"";
         //  dayofWeek= dayofWeek.toLowerCase();
          
        
        return isGoodOnHours;
    }

    private boolean seeIfSanctionPassedRequiredPeriod(LocalDate sanctionDate, int sanctionLength) {
       LocalDate todaysDate = LocalDate.now();
       boolean passedSanctionPeriod = true;
       boolean withinSanctionPeriod = false; // still not eligible
       
       switch(sanctionLength){
           case 3: 
                if(todaysDate.isAfter(sanctionDate.plusYears(1)) || todaysDate.isEqual(sanctionDate.plusYears(1)) ){
                     return passedSanctionPeriod;
                }else{
                        return withinSanctionPeriod;
                }
           case 2: 
                if(todaysDate.isAfter(sanctionDate.plusMonths(3)) || todaysDate.isEqual(sanctionDate.plusMonths(3)) ){
                        return passedSanctionPeriod;
                }else{
                        return withinSanctionPeriod;
                }
            case 1: 
                if(todaysDate.isAfter(sanctionDate.plusMonths(1)) || todaysDate.isEqual(sanctionDate.plusMonths(1)) ){
                        return passedSanctionPeriod;
                }else{
                        return withinSanctionPeriod;
                }   
            
       }
       return false;
    }
    
    private String getHowLongClientShouldWait(LocalDate sanctionDate, int sanctionLength) {
       
        String periodLeftToRemoveSanction = "";
                
         switch(sanctionLength){
           case 3: 
                 periodLeftToRemoveSanction += "The sanction that was applied to your case on "
		+ sanctionDate.toString() + " was the third one, that you have to wait till " 
		+ sanctionDate.plusYears(1).toString() + " so you can be re-eligible for the benefits-ADC\n Please be noticed that "
		+ "once you are not eligible for ADC, you are automatically not eligible for: clothing, fuel, tuition, vehicle registeration or repair.";
               break;
               
               
           case 2:
                periodLeftToRemoveSanction += "The sanction that was applied to your case on "
		+ sanctionDate.toString() + " was the second one, that you have to wait till " 
		+ sanctionDate.plusMonths(3).toString() + " so you can be re-eligible for the benefits-ADC\n Please be noticed that "
		+ "once you are not eligible for ADC, you are automatically not eligible for: clothing, fuel, tuition, vehicle registeration or repair.";
                break;
                
                
           case 1:
               periodLeftToRemoveSanction += "The sanction that was applied to your case on "
		+ sanctionDate.toString() + " was the first one that you have to wait till " 
		+ sanctionDate.plusMonths(1).toString() + " so you can be re-eligible for the benefits-ADC\n Please be noticed that "
		+ "once you are not eligible for ADC, you are automatically not eligible for: clothing, fuel, tuition, vehicle registeration or repair.";
                break; 
            
       }
        
       
        
        return periodLeftToRemoveSanction;
    }
    
    //we can get the partners name here too so the message make more sense. 
    private String getWarningMessage(Client aClient, boolean married) {
        String warningMsg = "";
        
          double parntersTotalHours =0;
          double clientsTotalHours =0;
          
          double couplesHours = 0;
          LocalDate threeWeeksDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).minusDays(7);
          LocalDate firstOfMonth   = LocalDate.now().withDayOfMonth(1);
          LocalDate todayDate = LocalDate.now();
          ArrayList<Hours> clientsPartnerHours = new ArrayList<Hours>();
          ArrayList<Hours> clientsHours = new ArrayList<Hours>();
          
          clientsPartnerHours = ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, threeWeeksDate);
          clientsHours        = ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, threeWeeksDate); // hours for the client are needed anyway; but parter's hours are only needed if married
            
          clientsTotalHours = getTotalHours(clientsHours);
          parntersTotalHours = getTotalHours(clientsPartnerHours);
          
          couplesHours = clientsTotalHours + parntersTotalHours;
          if(married){
              if(todayDate.isAfter(threeWeeksDate) || todayDate.isEqual(threeWeeksDate) && couplesHours < 105 ){
               
                    warningMsg = "Our records indicate that your and your partner's hours are low by today " + LocalDate.now().toString() + ". But do not worry you still "
                                + " One week from " + threeWeeksDate.toString() + " to " + LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).toString() + 
                                  "to make those hours. Just for your information your partner's hours are: " + parntersTotalHours + " and yours are: " + clientsTotalHours
                            + " while you both supposed to do 105 hours by tody.";
                
              }
              else if(todayDate.isAfter(threeWeeksDate.minusDays(7)) || todayDate.isEqual(threeWeeksDate.minusDays(7)) && couplesHours < 70){
                  
              }
                
          }else{ // if single
              warningMsg = "Keep doing the good work! your hours all set; however make sure you the the rest of hours you are required for the last week ";
          }
          return warningMsg;
    }

    private boolean checkMarriageStatus(Client aClient) {
        boolean isMarried = false;

       // if((aClient.getPartnerID() != 0 & aClient.getPartnerID()+"" != " " & aClient.getPartnerID()+"" != null )){
       if(aClient.isMarried()){
           if((aClient.getPartnerID() != 0)){
                isMarried =true;
           }
        }
        
        return isMarried;
    }

    private Boolean getSanctionStatus(ArrayList<Sanction> clientSanctions, HttpSession session) {
        
        
        String periodToWaitToB_Eligible ="";
        boolean isSanctioned = false;
        
        
            if(clientSanctions != null){   //if the list is empty, there is no sanctions, but if there sanctions in the list, these might have been waived or passed the required time.
                   for(Sanction sanction: clientSanctions){
                        if(sanction.getSanctionLength() == 3 & !seeIfSanctionPassedRequiredPeriod(sanction.getSanctionDate(), sanction.getSanctionLength())) // if sanction from type 3  =1 year period, check if it has been a year since
                        {				            // if type3 and hasnont pass the required period, then no need to check for other sanctions
                              periodToWaitToB_Eligible = getHowLongClientShouldWait(sanction.getSanctionDate(), sanction.getSanctionLength());
                              isSanctioned = true;
                              break;

                        }else if(sanction.getSanctionLength() == 2 & !seeIfSanctionPassedRequiredPeriod(sanction.getSanctionDate(), sanction.getSanctionLength())){
                                 periodToWaitToB_Eligible = getHowLongClientShouldWait(sanction.getSanctionDate(), sanction.getSanctionLength());
                                 isSanctioned = true;
                                  break;

                        }else if(sanction.getSanctionLength() == 1 & !seeIfSanctionPassedRequiredPeriod(sanction.getSanctionDate(), sanction.getSanctionLength())){
                                 periodToWaitToB_Eligible = getHowLongClientShouldWait(sanction.getSanctionDate(), sanction.getSanctionLength());
                                 isSanctioned = true;
                                  break;
                        }
                    }
               }else{//this is ture only if the list has no sanction, in our case/current business policy will never execute, that is why boolean isSanctioned is used. 
	    //but if sanctions was updated in sanction table right away. some code will be not required plus that this was executed. i though of select count(*) but dont make sense
                   periodToWaitToB_Eligible += "You are doing awesome by paritcipating hours you have been required&mdash;"
		   + "which is good for you which keeps you from getting sanctioned. Keep the good work.";
               }
            
                if(isSanctioned == false){
                    periodToWaitToB_Eligible += "You are doing awesome by paritcipating hours you are required&mdash;"
		    + "which is good for you which keeps you from getting sanctioned. Keep the good work.";
                }
                session.setAttribute("periodToWaitToB_Eligible", periodToWaitToB_Eligible);
           
           return isSanctioned;
    }

}
