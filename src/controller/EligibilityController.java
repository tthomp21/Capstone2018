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
        ArrayList<Hours> clientsHoursList  = (ArrayList<Hours>) session.getAttribute("clientsHoursList");
        ArrayList<Hours> clientsPartnerHoursList  = (ArrayList<Hours>) session.getAttribute("clientsPartnerHoursList");
        ArrayList<Sanction> clientSanctions = (ArrayList<Sanction>)session.getAttribute("clientSanctions");
        Boolean isSanctioned = (Boolean)session.getAttribute("isSanctioned");
        Boolean isMarried  =(Boolean)session.getAttribute("isMarried");
        
        String url = "/";
        String lowHoursMsg ="";
        String warningMsg ="";
        String periodToWaitToB_Eligible ="";
        
       
        double clientMarriedOrSingleTotalHours = 0, clientSingleTotalHours =0;
                
        if(clientsHoursList == null)
        {
            clientsHoursList = new ArrayList<Hours>();
            setClientOrPartnerHoursAndAccumulated(clientsHoursList, aClient, session);
            
        }if(clientsPartnerHoursList == null){
            clientsPartnerHoursList = new ArrayList<Hours>();

        }if(clientSanctions == null){
            clientSanctions = new ArrayList<Sanction>();
        }
        if(isSanctioned == null){
            isSanctioned = false;
        }
        if(isMarried == null){
            isMarried = false;
        }

        
         
        
      //  url ="/views/viewEligibility.jsp";
     // cs.getRequestDispatcher(url).forward(request, response);
                
               
       try{ 
               //get sanction first , if there is any for the client then it is applied to the partner as well
               clientSanctions = ClientDB.getClientSanctions(aClient.getClientID());
               //this method also set the atrribute of session, to periodToWaitToB_Eligible, it calls, !seeIfSanctionPassedRequiredPeriod method == false, then resulted to = true, 
               //then it calls getHowLongClientShouldWait to set the appropriate message in periodToWaitToB_Eligible variable. 
               isSanctioned = getSanctionStatus(clientSanctions, session);
               if(!isSanctioned){ //if client is not sanctioned then do the hours for the clietns , if anyone is sanctioned, it applies to both.
                  isMarried = checkMarriageStatus(aClient);
                  clientMarriedOrSingleTotalHours = (double)session.getAttribute("totalHours");
                  warningMsg = getWarningMessage(aClient, isMarried, session); //this for the three weeks
               }else{ //if sanctioned then the other processing should be on the view side on JSP. whether to display the secondary message and ADC benefits. 
                   
               }
              
               url = "/views/viewEligibility.jsp";
               
               
        //********************if there is no sanction then start count hours *******************************
        /**it would be nice if know how many hours each of client and the partner required.check marriage status
	 * 
	 * lets get the hours from the beginning of this month to today.
	 * LocalDate today = LocalDate.now();
	 * LocalDate firstOfMonth   = today.withDayOfMonth(1);
	 * 
	 */
	
	
        }
        catch(Exception ex){
	System.out.print(ex.equals(ex));
        }finally{
            session.setAttribute("warningMsg", warningMsg);
            session.setAttribute("isSanctioned", isSanctioned);
            cs.getRequestDispatcher(url).forward(request, response);
            
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

    private double getTotalHoursAccumulated(ArrayList<Hours> clientsHours) {
	    double totalHours =0;
	    for(Hours hrs: clientsHours){
	        totalHours += hrs.getNumberOfHours();
	        
	    }
	   return totalHours;
    }
    
    //
    private boolean checkHoursStatus(double totalHours, boolean married) {
            boolean isGoodOnHours = true;
            
            
            if(married){
                if(totalHours < 35){
                    isGoodOnHours = false;
                }
            }
            else{
                if(totalHours < 20){
                    isGoodOnHours = false;
                }
            }
                 return isGoodOnHours;
    }
//            LocalDate today = LocalDate.now();
//            
//            LocalDate firstOfMonth   = today.withDayOfMonth(1);
//            LocalDate firstWeek      = today.withDayOfMonth(7);
//            LocalDate secondWeek     = today.withDayOfMonth(14);
//            LocalDate thirdWeek      = today.withDayOfMonth(21);
//            LocalDate fourthWeek     = today.withDayOfMonth(28);
//            LocalDate endOfTheMonth  = today.withDayOfMonth(today.lengthOfMonth());
            
         //   String dayofWeek = today.getDayOfWeek().FRIDAY +"";
         //  dayofWeek= dayofWeek.toLowerCase();
          
        
   

    private boolean seeIfSanctionPassedRequiredPeriod(LocalDate sanctionDate, int sanctionLength){
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
    private String getWarningMessage(Client aClient, Boolean married, HttpSession session) {
//        double clientsTotalHours        = (double) session.getAttribute("clientTotalHours");
//        double parntersTotalHours       = (double) session.getAttribute("partnerTotalHours");
//        double couplesHours             = (double) session.getAttribute("totalHours");

            String warningMsg = "";
            double clientsTotalHours =0;
            double parntersTotalHours =0;
            double couplesHours  =0;
            
          LocalDate threeWeeksDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).minusDays(7);
          LocalDate firstOfMonth   = LocalDate.now().withDayOfMonth(1);
          LocalDate todayDate      = LocalDate.now();
          
          ArrayList<Hours> clientsPartnerHoursList = new ArrayList<Hours>(); //(ArrayList<Hours>)session.getAttribute("clientsPartnerHoursList");
          ArrayList<Hours> clientsHoursList        = new ArrayList<Hours>();//(ArrayList<Hours>)session.getAttribute("clientsHoursList");
          
          if((todayDate.isAfter(threeWeeksDate) || todayDate.isEqual(threeWeeksDate))){ //dont calculate if it is not three weeks yet.
               
                clientsHoursList        = ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, threeWeeksDate); // hours for the client are needed anyway; but parter's hours are only needed if married

                clientsTotalHours       = getTotalHoursAccumulated(clientsHoursList);

                if(married){
                    clientsPartnerHoursList = ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, threeWeeksDate);
                    parntersTotalHours = getTotalHoursAccumulated(clientsPartnerHoursList);
                    couplesHours = clientsTotalHours + parntersTotalHours;

                    if((todayDate.isAfter(threeWeeksDate) || todayDate.isEqual(threeWeeksDate)) && couplesHours < 105 ){

                          warningMsg = "Our records indicate that your and your partner's hours are low by today " + LocalDate.now().toString() 
                                        + ". But do not worry you still "
                                        + " One week from " + threeWeeksDate.toString() 
                                        + " to " + LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).toString() 
                                        + "to make those hours. Just for your information your partner's hours are: " + parntersTotalHours + " and yours are: " + clientsTotalHours
                                        + " while you both supposed to do 105 hours by today.";

                    }else {
                         warningMsg = "Keep doing the good work! your hours and your partner's too are all set; however make sure you make the rest of hours you are required for the last week ";
                    }
                   

                }else{ // if single, only clientTotalHours are needed
                    if(todayDate.isAfter(threeWeeksDate) || todayDate.isEqual(threeWeeksDate) && clientsTotalHours < 60){
                       warningMsg = "Our records indicate that your hours are low by today " 
                                    + LocalDate.now().toString() + ". But do not worry you still "
                                    + " One week from " + threeWeeksDate.toString() + " to " + LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).toString() 
                                    + "to make those hours. Just for your information your hours are: " + clientsTotalHours
                                    + " while you were supposed to make 60 hours by today.";
                    }else{
                         warningMsg = "Keep doing the good work! your hours are all set; however make sure you the the rest of hours you are required for the last week ";
                    }
                }
          }else{// if it hasn't been three weeks yet, display a regular message based on marriage
              if(married){
                  warningMsg = "Keep doing the good work! your hours and your partner's at this point are fine; however make sure you make whatever hours you are required for the remaining of the month ";
              }else{
                  warningMsg = "Keep doing the good work! your hours are fine; however make sure you make whatever hours you are required for the remaining of the month ";
              }
          }
          session.setAttribute("clinetHoursList3Weeks", clientsHoursList);
          session.setAttribute("clientsPartnerHoursList3Weeks", clientsPartnerHoursList);
          session.setAttribute("coupleHours3Weeks", couplesHours);
          session.setAttribute("clientsTotalHours3Weeks", clientsTotalHours);
          session.setAttribute("parntersTotalHours3Weeks", parntersTotalHours);
          
          
          
          
          return warningMsg;
    }

    private boolean checkMarriageStatus(Client aClient) {
        boolean isMarried = false;

       // if((aClient.getPartnerID() != 0 & aClient.getPartnerID()+"" != " " & aClient.getPartnerID()+"" != null )){
       if(aClient.isMarried()){
           if((aClient.getPartnerID() != 0)){ //this is a double check, extra maybe
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

   

    private void setClientOrPartnerHoursAndAccumulated(ArrayList<Hours> clientsHoursList, Client aClient, HttpSession session) {
        ArrayList<Hours> clientsPartnerHoursList =  new ArrayList<Hours>();
        
        boolean isMarried;
        isMarried = checkMarriageStatus(aClient);
        
        double clientTotalHours =0;
        double partnerTotalHours =0;
        
        double totalHours =0; //todaysDate.withDayOfMonth(15)
        //ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, todaysDate); // hours for the client are needed anyway; but parter's hours are only needed if married
        clientsHoursList = ClientDB.getClientHoursForWholeMonth(aClient.getClientID());
        clientTotalHours = getTotalHoursAccumulated(clientsHoursList);
        
        if(isMarried){
            clientsPartnerHoursList = ClientDB.getClientHoursForWholeMonth(aClient.getClientID());
            partnerTotalHours =  getTotalHoursAccumulated(clientsPartnerHoursList); 
        }
        
        totalHours = clientTotalHours + partnerTotalHours;
        
        session.setAttribute("clientsHoursList", clientsHoursList);
        session.setAttribute("clientsPartnerHoursList", clientsPartnerHoursList);
        session.setAttribute("clientTotalHours", clientTotalHours);
        session.setAttribute("partnerTotalHours", partnerTotalHours);
        session.setAttribute("totalHours", totalHours);
        session.setAttribute("isMarried", isMarried);
       
    }

    

    

}
//
// private double getTotalHoursMarridOrSingle(boolean married, HttpSession session) {
//        
//        LocalDate todaysDate = LocalDate.now();
//	LocalDate firstOfMonth   = todaysDate.withDayOfMonth(1);
//        
//	ArrayList<Hours> clientsHours = new ArrayList<Hours>();
//        ArrayList<Hours> clientsPartnerHours =  new ArrayList<Hours>();
//        Client aClient = (Client)session.getAttribute("user");
//        
//        double clientTotalHours =0;
//        double partnerTotalHours =0;
//        
//        double totalHours =0; //todaysDate.withDayOfMonth(15)
//        // ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, todaysDate); // hours for the client are needed anyway; but parter's hours are only needed if married
//        clientsHours = ClientDB.getClientHoursForWholeMonth(aClient.getClientID());
//        clientTotalHours = getTotalHoursAccumulated(clientsHours);
//        
//        if(married){ //if married get clients partner hours too, otheriwse the hours are accumulated above.
//            clientsPartnerHours = ClientDB.getClientHoursForWholeMonth(aClient.getClientID());
//            partnerTotalHours =  getTotalHoursAccumulated(clientsPartnerHours);            
//            
//        }
//        session.setAttribute("clientTotalHours", clientTotalHours);
//        session.setAttribute("partnerTotalHours", partnerTotalHours);
//        
//        totalHours = clientTotalHours + partnerTotalHours;
//        return totalHours;
//    }