// Tyler Thompson   
// 1/14/18


package controller;

import business.*;
import data.ClientDB;
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
        ArrayList<Client> clients = new ArrayList<Client>();
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
        }
        
        
        // redirect
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher(url).forward(request, response);  
                
    }
        
    private void updateRequestApproval(HttpSession session, int reqID)
    {
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
        ArrayList<AssistanceRequest> assistReq = new ArrayList<AssistanceRequest>();
        assistReq = (ArrayList<AssistanceRequest>)session.getAttribute("clientRequests");
        for(AssistanceRequest as : assistReq)
        {
            if(as.getRequestID() == reqID)
            {
                as.setStatus("2");
                as.setDateDisbursed(LocalDate.now());
            }
        }
    }
    
    private void enterHours(HttpSession session,HttpServletRequest request)
    {
        //change request to session
        double mondayHours = (double)session.getAttribute("monHours");
        double tuesdayHours = (double)session.getAttribute("tueHours");
        double wednesdayHours = (double)session.getAttribute("wedHours");
        double thursdayHours = (double)session.getAttribute("thurHours");
        double fridayHours = (double)session.getAttribute("friHours");
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        format = format.withLocale(Locale.US);
        LocalDate monday = LocalDate.parse((String)session.getAttribute("monday"),format);
        LocalDate tuesday = LocalDate.parse((String)session.getAttribute("tuesday"),format);
        LocalDate wednesday = LocalDate.parse((String)session.getAttribute("wednesday"),format);
        LocalDate thursday = LocalDate.parse((String)session.getAttribute("thursday"),format);
        LocalDate friday = LocalDate.parse((String)session.getAttribute("friday"),format);
        
        HoursDB.insertClientHours(mondayHours, tuesdayHours, wednesdayHours, thursdayHours, fridayHours, monday, tuesday, wednesday, thursday, friday, Integer.parseInt((String)request.getParameter("clientID")));
        
    }
    
    private Date getDateForDay(int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, day);
        
        return c.getTime();
    }
    
    
    
    
    public void getClientDetails(String clientID, HttpSession session, HttpServletRequest request)
    {
        Client foundClient;
        foundClient = ClientDB.getClientWithID(Integer.parseInt(clientID));
        session.setAttribute("foundClient", foundClient);
        
        
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
        
        ArrayList<ClientHoursArgs> thisMonthHours = new ArrayList<ClientHoursArgs>();
        ArrayList<ClientHoursArgs> lastMonthHours = new ArrayList<ClientHoursArgs>();
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        thisMonthHours = HoursDB.getClientHours(Integer.parseInt(clientID),month,year);
        if(month == 1)
        {
            month = 12;
            year--;
            lastMonthHours = HoursDB.getClientHours(Integer.parseInt(clientID), month, year);
        }
        else{
            lastMonthHours = HoursDB.getClientHours(Integer.parseInt(clientID), month - 1, year);
        }
        
        double totalMonthHours = 0;
        if(thisMonthHours != null && thisMonthHours.size() > 0)
        {
            for(ClientHoursArgs arg : thisMonthHours)
            {
                if(arg.getHours() != null)
                    totalMonthHours += arg.getHours();
                
                //if(LocalDate.parse(arg.getDate().toString(), format) == monday)
                //    request.s
            }
        }
        session.setAttribute("clientHours", totalMonthHours);
        
        totalMonthHours = 0;
        if(lastMonthHours != null && lastMonthHours.size() > 0)
        {
            for(ClientHoursArgs arg : lastMonthHours)
            {
                if(arg.getHours() != null)
                    totalMonthHours += arg.getHours();
            }
        }
        session.setAttribute("lastMonthClientHours", totalMonthHours);
        
        
        ArrayList<AssistanceRequest> assistReq = new ArrayList<AssistanceRequest>();
        assistReq = ClientDB.getSecondaryAssistances(Integer.parseInt(clientID));
        session.setAttribute("clientRequests", assistReq);
        
    }
    
    
    public ArrayList<Client> getClients(CaseWorker caseWorker, HttpSession session)
    {
        //returns a list of clients from the DB
        ArrayList<Client> allClients = new ArrayList<Client>();
        allClients = ClientDB.getAllClientsForCaseWorker(caseWorker);
        return allClients;
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
