/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.*;
import business.Hours;
import business.Sanction;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import data.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
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
         * to view the eligibility for ADC: 1- check the marriage status ---> if
         * married get the partnerID for the underlying/signed in client do step
         * 2 otherwise check step 3 for single person against 20 hours a week.
         * 2- check the hours for both and has to be 35 hours for the current
         * week, or total of 35/week * 4 = 140hours/month. ---> another option
         * is to notify the client for the shortage of hours if there is any; 3-
         * if not married, it means the client is a single parent with
         * dependents, his/her hours =20/week * 4 = 80/month
         *
         * 4- once hours are checked check the sanctions as follow, sanctions 1,
         * 2, 3 are types of sanctions 1s, 2nd, 3rd ---> if sanction length is
         * 1, it means that current date - sanction date has to be > 30 days so
         * the client can be eligible for the assistance, mainly ADC ---> if
         * sanction length is 2, it means that current date - sanction date has
         * to be > 90 days so the client can be eligible for the assistance,
         * mainly ADC ---> if sanction length is 3, it means that current date -
         * sanction date has to be > 365 days so the client can be eligible for
         * the assistance, mainly ADC 5- for the secondary assistances follow
         * this steps: ---> clothing: $200 once a year., how this should be
         * handled; is it just something that assumed it is given regularly or
         * just $200. ---> vehicle registeration: one time and only one. --->
         * fuel: once a week, $20 dollars each. ---> tuition: one time and only
         * once. ---> car repairs: depends may be once or once per year. ---> 6-
         * done forget about SCM.TCF_AIDNOTIFY
         *
         * ******************************************************************
         * build a select tag to allow the client check for the hours based on
         * the week, 1,2,3, and 4. call a common method that would get the hours
         * for the week been selected and the one before it, for example 1st
         * week, I need date obj for first of month, and 7th of the month. may
         * be add two input box of type date to select what date want to be
         * selected add two radio buttons to check if the client want to get
         * hours for both
         *
         */
        HttpSession session = request.getSession();
        ServletContext cs = session.getServletContext();

        Client aClient = (Client) session.getAttribute("user"); // get this from successful login.
        CaseWorker clientCaseWorker = (CaseWorker) session.getAttribute("clientCaseWorker"); // get this from successful login.
        ArrayList<Hours> clientsHoursList = (ArrayList<Hours>) session.getAttribute("clientsHoursList");
        ArrayList<Hours> clientsPartnerHoursList = (ArrayList<Hours>) session.getAttribute("clientsPartnerHoursList");
        ArrayList<Sanction> clientSanctions = (ArrayList<Sanction>) session.getAttribute("clientSanctions");
        ArrayList<AidNotify> aidNotifyList = (ArrayList<AidNotify>) session.getAttribute("aidNotifyList");
        Boolean isSanctioned = (Boolean) session.getAttribute("isSanctioned");
        Boolean isMarried = (Boolean) session.getAttribute("isMarried");
        Boolean isHideTable = (Boolean) session.getAttribute("isHideTable");
        //  Boolean isOnlyLoadOnce	            = (Boolean)session.getAttribute("isOnlyLoadOnce");

        String action = (String) request.getParameter("action");

        //String strToDate = (String)request.getParameter("fromDate");
        //String strFromDate = (String)request.getParameter("toDate");
        double clientMarriedOrSingleTotalHours = 0;
        String url = "/";
        String warningMsg = "";
        String periodToWaitToB_Eligible = "";

        try {
            if (clientCaseWorker == null) {
                clientCaseWorker = CaseWorkerDB.getClientCaseWorker(aClient.getCaseWorkerID());
            }
            if (aidNotifyList == null) {
                // aidNotifyList = new ArrayList<AidNotify>();
                aidNotifyList = prepareAidNotifyList(aClient.getClientID());
            }
            if (aClient.isMarried()) {
                Client clientPartner = (Client) session.getAttribute("clientPartner");

                if (clientPartner == null) {
                    clientPartner = ClientDB.getClientWithID(aClient.getPartnerID());
                    session.setAttribute("clientPartner", clientPartner);
                }
            }
            if (clientsHoursList == null) {
                clientsHoursList = new ArrayList<Hours>();
                clientsHoursList = ClientDB.getClientHoursForWholeMonth(aClient.getClientID());

                //setClientOrPartnerHoursAndAccumulated(session);
            }
            if (clientsPartnerHoursList == null) {
                clientsPartnerHoursList = new ArrayList<Hours>();
                if (aClient.isMarried()) {
                    clientsPartnerHoursList = ClientDB.getClientHoursForWholeMonth(aClient.getPartnerID());
                }

            }
            if (clientSanctions == null) {
                clientSanctions = new ArrayList<Sanction>();
                clientSanctions = ClientDB.getClientSanctions(aClient.getClientID());

            }
            if (isSanctioned == null) {
                isSanctioned = false;
            }

            setClientOrPartnerHoursAndAccumulated(clientsHoursList, clientsPartnerHoursList, session);

            if (isHideTable == null) {
                isHideTable = true;
            } else {
                isHideTable = false;
            }
            session.setAttribute("isHideTable", isHideTable);

            if (action == null) {
                action = "dont do anything";
                // String dateMessage ="";

                // session.setAttribute("dateMessage", dateMessage);
                // "filterHours";
            }
            url = "/views/viewEligibility.jsp";
            if (action.equals("filterHours")) {
                provideFilteredHours(request, response, session);
            }

            //get sanction first , if there is any for the client then it is applied to the partner as well
            isSanctioned = getSanctionStatus(clientSanctions, session);
            if (!isSanctioned) { //if client is not sanctioned then do the hours for the clietns , if anyone is sanctioned, it applies to both.
                isMarried = (Boolean) session.getAttribute("isMarried");
                clientMarriedOrSingleTotalHours = (double) session.getAttribute("totalHours");
                warningMsg = getWarningMessage(aClient, isMarried, session); //this for the three weeks
            } else { //if sanctioned then the other processing should be on the view side on JSP. whether to display the secondary message and ADC benefits. 

            }

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        } finally {
            session.setAttribute("warningMsg", warningMsg);
            session.setAttribute("isSanctioned", isSanctioned);
            session.setAttribute("clientsHoursList", clientsHoursList);
            session.setAttribute("clientsPartnerHoursList", clientsPartnerHoursList);
            session.setAttribute("clientSanctions", clientSanctions);
            session.setAttribute("isHideTable", isHideTable);
            session.setAttribute("clientCaseWorker", clientCaseWorker);
            session.setAttribute("aidNotifyList", aidNotifyList);

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
        double totalHours = 0;
        for (Hours hrs : clientsHours) {
            totalHours += hrs.getNumberOfHours();

        }
        return totalHours;
    }

    //
    private boolean checkHoursStatus(double totalHours, boolean married) {
        boolean isGoodOnHours = true;

        if (married) {
            if (totalHours < 35) {
                isGoodOnHours = false;
            }
        } else {
            if (totalHours < 20) {
                isGoodOnHours = false;
            }
        }
        return isGoodOnHours;
    }

    private boolean seeIfSanctionPassedRequiredPeriod(LocalDate sanctionDate, int sanctionLength) {
        LocalDate todaysDate = LocalDate.now();
        boolean passedSanctionPeriod = true;
        boolean withinSanctionPeriod = false; // still not eligible

        switch (sanctionLength) {
            case 3:
                if (todaysDate.isAfter(sanctionDate.plusYears(1)) || todaysDate.isEqual(sanctionDate.plusYears(1))) {
                    return passedSanctionPeriod;
                } else {
                    return withinSanctionPeriod;
                }
            case 2:
                if (todaysDate.isAfter(sanctionDate.plusMonths(3)) || todaysDate.isEqual(sanctionDate.plusMonths(3))) {
                    return passedSanctionPeriod;
                } else {
                    return withinSanctionPeriod;
                }
            case 1:
                if (todaysDate.isAfter(sanctionDate.plusMonths(1)) || todaysDate.isEqual(sanctionDate.plusMonths(1))) {
                    return passedSanctionPeriod;
                } else {
                    return withinSanctionPeriod;
                }

        }
        return false;
    }

    private String getMessageHowLongClientShouldWait(LocalDate sanctionDate, int sanctionLength) {

        String periodLeftToRemoveSanction = "";

        switch (sanctionLength) {
            case 3:
                periodLeftToRemoveSanction += "The sanction that was applied to your case on "
                        + sanctionDate.toString() + " was the third one, that you have to wait until "
                        + sanctionDate.plusYears(1).toString() + " so you can be re-eligible for the benefits-ADC.\n Please be noticed that "
                        + "once you are not eligible for ADC, you are automatically not eligible for: clothing, fuel, tuition, vehicle registration or repair.";
                break;

            case 2:
                periodLeftToRemoveSanction += "The sanction that was applied to your case on "
                        + sanctionDate.toString() + " was the second one, that you have to wait until "
                        + sanctionDate.plusMonths(3).toString() + " so you can be re-eligible for the benefits-ADC.\n Please be noticed that "
                        + "once you are not eligible for ADC, you are automatically not eligible for: clothing, fuel, tuition, vehicle registration or repair.";
                break;

            case 1:
                periodLeftToRemoveSanction += "The sanction that was applied to your case on "
                        + sanctionDate.toString() + " was the first one that you have to wait until "
                        + sanctionDate.plusMonths(1).toString() + " so you can be re-eligible for the benefits-ADC.\n Please be noticed that "
                        + "once you are not eligible for ADC, you are automatically not eligible for: clothing, fuel, tuition, vehicle registration or repair.";
                break;

        }

        return periodLeftToRemoveSanction;
    }

    //we can get the partners name here too so the message make more sense. 
    private String getWarningMessage(Client aClient, Boolean married, HttpSession session) {

        String warningMsg = "";
        double clientsTotalHours = 0;
        double parntersTotalHours = 0;
        double couplesHours = 0;
        boolean isWarning = false;
        
       // String clientName = aClient.getFirstName();
        
        Client partner = null;
       
               
        
        LocalDate threeWeeksDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).minusDays(7);
        LocalDate firstOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate todayDate = LocalDate.now();
        LocalDate endoOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        LocalDate twoWeeks = todayDate.withDayOfMonth(14);
        LocalDate oneWeek = todayDate.withDayOfMonth(7);

        ArrayList<Hours> clientsPartnerHoursList = new ArrayList<Hours>(); //(ArrayList<Hours>)session.getAttribute("clientsPartnerHoursList");
        ArrayList<Hours> clientsHoursList = new ArrayList<Hours>();//(ArrayList<Hours>)session.getAttribute("clientsHoursList");

        clientsHoursList = ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, todayDate); // hours for the client are needed anyway; but parter's hours are only needed if married
        if (married) {
            partner = ClientDB.getClientWithID(aClient.getClientID());
            clientsPartnerHoursList = ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, todayDate);
            parntersTotalHours = getTotalHoursAccumulated(clientsPartnerHoursList);

        }
        
        clientsTotalHours = getTotalHoursAccumulated(clientsHoursList);
        couplesHours = clientsTotalHours + parntersTotalHours;
        //don't calculate if it is not three weeks yet. 
        if ((todayDate.isAfter(todayDate.withDayOfMonth(7)) && todayDate.isBefore(twoWeeks))) {

            

            if (married) {

                if (couplesHours < 70) {

                    warningMsg = "Our records indicate that your and your partner's hours are low by today, " + LocalDate.now().toString()
                            + ". You have " + (todayDate.withDayOfMonth(todayDate.lengthOfMonth()).getDayOfMonth() -  todayDate.getDayOfMonth()) + " days "
                            + "up to " + endoOfMonth.toString()
                            + " to make those hours." // + partner.getFirstName() +  "'s hours are " + parntersTotalHours + " and yours are " + clientsTotalHours
                            + " You both were supposed to make 70 hours by today.";

                    isWarning = true;

                } else {
                    warningMsg = "Keep doing the good work! your hours and your partner's too are all set; however make sure you make the rest of hours you are required for the last week.";
                }

            } else { // if single, only clientTotalHours are needed
                if (clientsTotalHours < 40) { //if (todayDate.isAfter(threeWeeksDate) || todayDate.isEqual(threeWeeksDate) && clientsTotalHours < 60) {
                    warningMsg = "Our records indicate that your hours are low by today, "
                            + LocalDate.now().toString()
                            + ". You have " +(todayDate.withDayOfMonth(todayDate.lengthOfMonth()).getDayOfMonth() -  todayDate.getDayOfMonth()) + " days " // +  todayDate.withDayOfMonth(todayDate.lengthOfMonth()).minus(todayDate.getDayOfMonth())
                            + " up to " + endoOfMonth.toString()
                            + "to make those hours."  //Your hours are: " + clientsTotalHours
                            + " You were supposed to make 40 hours by today.";
                    isWarning = true;
                } else {
                    warningMsg = "Keep doing the good work! your hours are all set; however make sure you make the rest of hours you are required for the last week.";
                }
            }
        } else {// if it hasn't been three weeks yet, display a regular message based on marriage

            if (married) {
                warningMsg = "Your hours and your partner's at this point are fine; however make sure you make whatever hours you are required for the remaining of the month. ";
            } else {
                warningMsg = "Your hours are fine; however make sure you make whatever hours you are required for the remaining of the month.";
            }
        }
        
        
        session.setAttribute("clinetHoursList3Weeks", clientsHoursList);
        session.setAttribute("clientsPartnerHoursList3Weeks", clientsPartnerHoursList);
        session.setAttribute("coupleHours3Weeks", couplesHours);
        session.setAttribute("clientsTotalHours3Weeks", clientsTotalHours);
        session.setAttribute("parntersTotalHours3Weeks", parntersTotalHours);
        session.setAttribute("isWarning", isWarning);

        return warningMsg;
    }

    //this method also set the atrribute of session, to periodToWaitToB_Eligible, it calls, !seeIfSanctionPassedRequiredPeriod method == false, then resulted to = true, 
    //then it calls getHowLongClientShouldWait to set the appropriate message in periodToWaitToB_Eligible variable. 
    private Boolean getSanctionStatus(ArrayList<Sanction> clientSanctions, HttpSession session) {

        String periodToWaitToB_Eligible = "";
        boolean isSanctioned = false;

        if (clientSanctions.size() != 0) {   //if the list is empty, there is no sanctions, but if there sanctions in the list, these might have been waived or passed the required time.
            for (Sanction sanction : clientSanctions) {
                if (sanction.getSanctionLength() == 3 & !seeIfSanctionPassedRequiredPeriod(sanction.getSanctionDate(), sanction.getSanctionLength())) // if sanction from type 3  =1 year period, check if it has been a year since
                {				            // if type3 and hasnont pass the required period, then no need to check for other sanctions
                    periodToWaitToB_Eligible = getMessageHowLongClientShouldWait(sanction.getSanctionDate(), sanction.getSanctionLength());
                    isSanctioned = true;
                    break;

                } else if (sanction.getSanctionLength() == 2 & !seeIfSanctionPassedRequiredPeriod(sanction.getSanctionDate(), sanction.getSanctionLength())) {
                    periodToWaitToB_Eligible = getMessageHowLongClientShouldWait(sanction.getSanctionDate(), sanction.getSanctionLength());
                    isSanctioned = true;
                    break;

                } else if (sanction.getSanctionLength() == 1 & !seeIfSanctionPassedRequiredPeriod(sanction.getSanctionDate(), sanction.getSanctionLength())) {
                    periodToWaitToB_Eligible = getMessageHowLongClientShouldWait(sanction.getSanctionDate(), sanction.getSanctionLength());
                    isSanctioned = true;
                    break;
                }
            }
        } else {//this is ture only if the list has no sanction, in our case/current business policy will never execute, that is why boolean isSanctioned is used. 
                 //but if sanctions was updated in sanction table right away. some code will be not required plus that this was executed. i though of select count(*) but dont make sense
            periodToWaitToB_Eligible += "You are doing awesome by paritcipating hours you have been required&mdash;"
                                     + "which is good for you and keeps you from getting sanctioned.";
        }

        if (isSanctioned == false) {
            periodToWaitToB_Eligible += "You are doing awesome by paritcipating hours you are required&mdash;"
                    + "which is good for you and keeps you from getting sanctioned.";
        }
        session.setAttribute("periodToWaitToB_Eligible", periodToWaitToB_Eligible);
        session.setAttribute("isSanctioned", isSanctioned);
        return isSanctioned;
    }

    private void setClientOrPartnerHoursAndAccumulated(ArrayList<Hours> clientsHoursList, ArrayList<Hours> clientsPartnerHoursList, HttpSession session) {
        Client client = (Client) session.getAttribute("user");
        double clientTotalHours = 0;
        double partnerTotalHours = 0;
        double totalHours = 0;
        try {
            //        ArrayList<Hours> clientsPartnerHoursList =  new ArrayList<Hours>();
            //        ArrayList<Hours> clientsHoursList =  new ArrayList<Hours>();
            //        HttpSession session = request.getSession();

            //todaysDate.withDayOfMonth(15)
            //ClientDB.getClientHoursByDates(aClient.getClientID(), firstOfMonth, todaysDate); // hours for the client are needed anyway; but parter's hours are only needed if married
            //        clientsHoursList = ClientDB.getClientHoursForWholeMonth(client.getClientID());
            //        clientTotalHours = getTotalHoursAccumulated(clientsHoursList);
            if (client.isMarried()) {
                clientsPartnerHoursList = ClientDB.getClientHoursForWholeMonth(client.getPartnerID());
                partnerTotalHours = getTotalHoursAccumulated(clientsPartnerHoursList);
            }

            totalHours = clientTotalHours + partnerTotalHours;

        } catch (Exception ex) {

        } finally {

            session.setAttribute("clientsHoursList", clientsHoursList);
            session.setAttribute("clientsPartnerHoursList", clientsPartnerHoursList);
            session.setAttribute("clientTotalHours", clientTotalHours);
            session.setAttribute("partnerTotalHours", partnerTotalHours);
            session.setAttribute("totalHours", totalHours);
            session.setAttribute("isMarried", client.isMarried());
        }

    }

    private void provideFilteredHours(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

        ServletContext cs = session.getServletContext();
        //these two list will be to hold the hours to be filtered
        ArrayList<Hours> clientHoursFiltered = new ArrayList<Hours>();
        ArrayList<Hours> partnerHoursFiltered = new ArrayList<Hours>();
        String url = "/views/viewEligibility.jsp";
        String dateMessage = "";

        double clientTotalFilteredHours = 0, partnerTotalFilteredHours = 0;
        double totalHourBoth = 0;
        try {
            ArrayList<Hours> clientAllHours = (ArrayList<Hours>) session.getAttribute("clientsHoursList");
            ArrayList<Hours> partnerAllHours = (ArrayList<Hours>) session.getAttribute("clientsPartnerHoursList");

            Boolean isMarried = (Boolean) session.getAttribute("isMarried");

            LocalDate currentDate = LocalDate.now();
            LocalDate firstOfMonth = currentDate.withDayOfMonth(1);

            String strToDate = request.getParameter("toDate");
            String strFromDate = request.getParameter("fromDate");

            if (strToDate.isEmpty() || strFromDate.isEmpty()) {
                dateMessage = "Please make sure you select both dates.";
                throw new NullPointerException();
            }

            //once above message is 
            LocalDate dToDate = LocalDate.parse(strToDate);
            LocalDate dFromDate = LocalDate.parse(strFromDate);

            if (dFromDate.isAfter(dToDate)) {
                dateMessage = "Please make sure the first Date is before the second one.";
                throw new IOException("");

            } else if (dFromDate.isBefore(firstOfMonth) || dToDate.isAfter(currentDate)) {
                dateMessage = "Please make sure you select dates only from the current month and up to today's date (" + currentDate.toString() + ")";
                throw new IOException("");
            } else { //clientWeeklyHours.get(i).getDateHoursEntered().equals(dFromDate) // clientWeeklyHours.get(i).getDateHoursEntered().equals(dToDate))
                //clientWeeklyHours.get(i).getDateHoursEntered().equals(dFromDate)) && 
                dateMessage = "";
                for (int i = 0; i < clientAllHours.size(); i++) {
                    if ((clientAllHours.get(i).getDateHoursEntered().isAfter(dFromDate) || clientAllHours.get(i).getDateHoursEntered().equals(dFromDate))
                            && ((clientAllHours.get(i).getDateHoursEntered().isBefore(dToDate) || clientAllHours.get(i).getDateHoursEntered().equals(dToDate)))) {
                        clientHoursFiltered.add(clientAllHours.get(i));
                    }

                }

                for (Hours h : clientHoursFiltered) {
                    clientTotalFilteredHours += h.getNumberOfHours();
                }

                if (isMarried) {
                    for (int i = 0; i < partnerAllHours.size(); i++) {

                        if ((partnerAllHours.get(i).getDateHoursEntered().isAfter(dFromDate) || partnerAllHours.get(i).getDateHoursEntered().equals(dFromDate))
                                && ((partnerAllHours.get(i).getDateHoursEntered().isBefore(dToDate) || partnerAllHours.get(i).getDateHoursEntered().equals(dToDate)))) {
                            partnerHoursFiltered.add(partnerAllHours.get(i));
                        }
                    }

                    for (Hours h : partnerHoursFiltered) {
                        partnerTotalFilteredHours += h.getNumberOfHours();
                    }
                }
                totalHourBoth = clientTotalFilteredHours + partnerTotalFilteredHours;

            }

        } catch (IOException iox) {
            session.setAttribute("dateMessage", dateMessage);
            session.setAttribute("isHideTable", true);
        } catch (NullPointerException nullExp) {
            session.setAttribute("dateMessage", dateMessage);
            session.setAttribute("isHideTable", true);
        } finally {
            session.setAttribute("clientWeeklyHours", clientHoursFiltered);
            session.setAttribute("partnerWeeklyHours", partnerHoursFiltered);
            session.setAttribute("clientTotalFilteredHours", clientTotalFilteredHours);
            session.setAttribute("partnerTotalFilteredHours", partnerTotalFilteredHours);
            session.setAttribute("dateMessage", dateMessage);
            session.setAttribute("totalHoursFilteredForBoth", totalHourBoth);

            cs.getRequestDispatcher(url).forward(request, response);

        }
    }

    private ArrayList<AidNotify> prepareAidNotifyList(int clientID) {

        ArrayList<AidNotify> aidNotifyList = new ArrayList<AidNotify>();

        try {
            aidNotifyList = AssistanceDB.getClientSanctions(clientID);

        } catch (Exception ex) {

        } finally {
            return aidNotifyList;
        }

    }

}
