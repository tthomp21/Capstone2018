// Tyler Thompson   
// 1/14/18


package controller;

import business.*;
import data.AssistanceDB;
import data.ClientDB;
import data.FileServe;
import data.HoursDB;
import data.RequestDB;
import java.util.List;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.function.Predicate;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CaseWorkerController extends HttpServlet {
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/views/caseworkerHome.jsp";
        String message = "";
        String action = request.getParameter("action");
        String selectedClient;
        int reqID = 0;
        int reqStat = 0;
        Client foundClient;
        HttpSession session = request.getSession();
        ArrayList<ClientHoursArgs> clients = new ArrayList<ClientHoursArgs>();
        DateFormat datFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        
        
        Date monday = getDateForDay(2);
        session.setAttribute("monday", new SimpleDateFormat("MM/dd/yyyy").format(monday));
        Date tuesday = getDateForDay(3);
        session.setAttribute("tuesday", new SimpleDateFormat("MM/dd/yyyy").format(tuesday));
        Date wednesday = getDateForDay(4);
        session.setAttribute("wednesday", new SimpleDateFormat("MM/dd/yyyy").format(wednesday));
        Date thursday = getDateForDay(5);
        session.setAttribute("thursday", new SimpleDateFormat("MM/dd/yyyy").format(thursday));
        Date friday = getDateForDay(6);
        session.setAttribute("friday", new SimpleDateFormat("MM/dd/yyyy").format(friday));
        
        if (action == null) {
            action = "arrival";
        }
        
        CaseWorker caseWorker = (CaseWorker)session.getAttribute("user");
        
        if(caseWorker == null)
        {
            action = "sendHome";
        }
        
        boolean redirect = true;
        
        switch(action) 
        {
        case "arrival":
            //when the caseworker arrives on this page get all the clients assigned to them
            clients = getClients(caseWorker, session);
            if(clients != null)
                session.setAttribute("foundClients", clients);
            break;
        case "sendHome":
            //if there was an error getting the user send them back home
            url = "/AccountsController";
            break;
        case "clientHours":
            selectedClient = (String) request.getParameter("clientID");
            foundClient = ClientDB.getClientWithID(Integer.parseInt(selectedClient));
            session.setAttribute("foundClient", foundClient);
            url = "/views/caseworkerclienteditor.jsp";
            break;
        case "clientDetails":
            //gets the general information about a client
            selectedClient = (String) request.getParameter("clientID");
            getClientDetails(selectedClient, session, request);
            url = "/views/caseworkerclientdetails.jsp";
            break;
        case "search":
            url = "/views/clientSearch.jsp";
            break;
        case "approveRequest":
            //approves a assistance request from the client
            reqID = Integer.parseInt((String)request.getParameter("requestID"));
            reqStat = Integer.parseInt((String)request.getParameter("requestStat"));
            RequestDB.updateRequest(reqID, reqStat);
            updateRequestApproval(session, reqID);
            url = "/views/caseworkerclientdetails.jsp";
            break;
        case "declineRequest":
            //denies a assistance request from the client
            reqID = Integer.parseInt((String)request.getParameter("requestID"));
            reqStat = Integer.parseInt((String)request.getParameter("requestStat"));
            RequestDB.updateRequest(reqID, reqStat);
            updateRequestDenial(session, reqID);
            url = "/views/caseworkerclientdetails.jsp";
            break;
        case "submitHours":
            //enters the weeks hours into the db
            enterHours(session, request);
            url = "/views/caseworkerclientdetails.jsp";
            break;
        case "viewDoucment":
            //opens up a file that was uploaded if there is one
            reqID = Integer.parseInt((String)request.getParameter("requestID"));
            boolean found = FileServe.retrieve(request, response, getServletContext(), reqID);
            if(!found)
                url = "/views/caseworkerclientdetails.jsp";
            else redirect = false;
        }
        
        
        // redirect
        if (redirect) {
            ServletContext sc = getServletContext();
            sc.getRequestDispatcher(url).forward(request, response);  
        }
        
                
    }
        
    private void updateRequestApproval(HttpSession session, int reqID)
    {
        //updates a assistance request with approval values
        ArrayList<AssistanceRequest> assistReq = new ArrayList<AssistanceRequest>();
        assistReq = (ArrayList<AssistanceRequest>)session.getAttribute("clientRequests");
        for(AssistanceRequest as : assistReq)
        {
            if(as.getRequestID() == reqID)
            {
                as.setStatus("1");
                as.setDateDisbursed(LocalDate.now());
            }
        }
    }
    
    private void updateRequestDenial(HttpSession session, int reqID)
    {
        //updates a assistance request with denial values
        ArrayList<AssistanceRequest> assistReq = new ArrayList<AssistanceRequest>();
        assistReq = (ArrayList<AssistanceRequest>)session.getAttribute("clientRequests");
        for(AssistanceRequest as : assistReq)
        {
            if(as.getRequestID() == reqID)
            {
                as.setStatus("0");
                as.setDateDisbursed(LocalDate.now());
            }
        }
    }
    
    private void enterHours(HttpSession session,HttpServletRequest request)
    {
        //gets the hours from the page and inserts them into the database
        double mondayHours = Double.parseDouble((String)request.getParameter("mondayHours"));
        double tuesdayHours = Double.parseDouble((String)request.getParameter("tuesdayHours"));
        double wednesdayHours = Double.parseDouble((String)request.getParameter("wednesdayHours"));
        double thursdayHours = Double.parseDouble((String)request.getParameter("thursdayHours"));
        double fridayHours = Double.parseDouble((String)request.getParameter("fridayHours"));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        format = format.withLocale(Locale.US);
        LocalDate monday = LocalDate.parse((String)session.getAttribute("monday"),format);
        LocalDate tuesday = LocalDate.parse((String)session.getAttribute("tuesday"),format);
        LocalDate wednesday = LocalDate.parse((String)session.getAttribute("wednesday"),format);
        LocalDate thursday = LocalDate.parse((String)session.getAttribute("thursday"),format);
        LocalDate friday = LocalDate.parse((String)session.getAttribute("friday"),format);
        
        HoursDB.insertClientHours(mondayHours, tuesdayHours, wednesdayHours, thursdayHours, 
                fridayHours, monday, tuesday, wednesday, thursday, friday, 
                Integer.parseInt((String)request.getParameter("clientID")));
        
        session.setAttribute("monHours", mondayHours);
        session.setAttribute("tueHours", tuesdayHours);
        session.setAttribute("wedHours", wednesdayHours);
        session.setAttribute("thurHours", thursdayHours);
        session.setAttribute("friHours", fridayHours);
        
        double hours = (double)session.getAttribute("clientHours");
        session.setAttribute("clientHours", hours + mondayHours + tuesdayHours 
                + wednesdayHours + thursdayHours + fridayHours );
    }
    
    private Date getDateForDay(int day)
    {
        //simple method to get the date for a day of the week
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, day);
        
        return c.getTime();
    }
    
    public void getClientDetails(String clientID, HttpSession session, HttpServletRequest request)
    {
        //gets all the client information from the database for the client details page
        Client foundClient;
        foundClient = ClientDB.getClientWithID(Integer.parseInt(clientID));
        session.setAttribute("foundClient", foundClient);
        
        //date stuff for hours input
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        format = format.withLocale(Locale.US);
        LocalDate monday = LocalDate.parse((String)session.getAttribute("monday"),format);
        LocalDate tuesday = LocalDate.parse((String)session.getAttribute("tuesday"),format);
        LocalDate wednesday = LocalDate.parse((String)session.getAttribute("wednesday"),format);
        LocalDate thursday = LocalDate.parse((String)session.getAttribute("thursday"),format);
        LocalDate friday = LocalDate.parse((String)session.getAttribute("friday"),format);
        
        session.setAttribute("monHours", HoursDB.getClientHoursForSingleDate(Integer.parseInt(clientID), monday));
        session.setAttribute("tueHours", HoursDB.getClientHoursForSingleDate(Integer.parseInt(clientID), tuesday));
        session.setAttribute("wedHours", HoursDB.getClientHoursForSingleDate(Integer.parseInt(clientID), wednesday));
        session.setAttribute("thurHours", HoursDB.getClientHoursForSingleDate(Integer.parseInt(clientID), thursday));
        session.setAttribute("friHours", HoursDB.getClientHoursForSingleDate(Integer.parseInt(clientID), friday));
        
        //get the hours for the current and previous month
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        
        session.setAttribute("clientHours", getHoursForMonthYear(Integer.parseInt(clientID),month,year));
        
        if(month == 1)
        {
            month = 12;
            year--;
            session.setAttribute("lastMonthClientHours", getHoursForMonthYear(Integer.parseInt(clientID),month,year));
        }
        else{
            session.setAttribute("lastMonthClientHours", getHoursForMonthYear(Integer.parseInt(clientID),month,year));
        }
        
        //get all assistance requests
        ArrayList<AssistanceRequest> assistReq = new ArrayList<AssistanceRequest>();
        assistReq = AssistanceDB.getSecondaryAssistances(Integer.parseInt(clientID));
        assistReq.sort(Comparator.comparingInt(AssistanceRequest::getRequestID));
        session.setAttribute("clientRequests", assistReq);
        
        //check if client is sanctioned    
        MessageResult result = Requests.checkForSanction(Integer.parseInt(clientID));
        if (result.successful())
        {
            String sanctionEnd = result.getMessage();
            session.setAttribute("sanctionMessage","This client is under a sanction until " + sanctionEnd + ".");
        }
        else 
        {
            session.setAttribute("sanctionMessage", "");
        }
    }
    
    public double getHoursForMonthYear(int clientID, int month, int year)
    {
        ArrayList<ClientHoursArgs> monthHours = new ArrayList<ClientHoursArgs>();
        monthHours = HoursDB.getClientHours(clientID,month,year);
        
        double totalMonthHours = 0;
        if(monthHours != null && monthHours.size() > 0)
        {
            for(ClientHoursArgs arg : monthHours)
            {
                if(arg.getHours() != null)
                    totalMonthHours += arg.getHours();
            }
        }
        
        return totalMonthHours;
    }
    
    
    public ArrayList<ClientHoursArgs> getClients(CaseWorker caseWorker, HttpSession session)
    {
        //returns a list of clients from the DB
        ArrayList<Client> allClients = new ArrayList<Client>();
        ArrayList<ClientHoursArgs> clientHours = new ArrayList<ClientHoursArgs>();
        allClients = ClientDB.getAllClientsForCaseWorker(caseWorker);
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        
        for(Client c : allClients)
        {
            ClientHoursArgs a = new ClientHoursArgs();
            a.setClient(c);
            a.setHours(getHoursForMonthYear(c.getClientID(),month,year));
            clientHours.add(a);
        }
        return clientHours;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    // </editor-fold>
}
