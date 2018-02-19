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
        
        
        
        switch(action) {
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
                selectedClient = (String) request.getParameter("clientID");
                getClientDetails(selectedClient, session);
                url = "/views/caseworkerclientdetails.jsp";
                break;
            case "search":
                url = "/views/clientSearch.jsp";
                break;
            case "approveRequest":
                int reqID = Integer.parseInt((String)request.getParameter("requestID"));
                int reqStat = Integer.parseInt((String)request.getParameter("requestStat"));
                RequestDB.updateRequest(reqID, reqStat);
                updateRequestApproval(session, reqID);
                
                break;
            case "declienRequest":
                break;
            case "submitHours":
                enterHours(session, request);
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
    
    private void enterHours(HttpSession session,HttpServletRequest request)
    {
        String mon = (String)request.getAttribute("mondayHours");
        int mondayHours = Integer.parseInt((String)request.getParameter("mondayHours"));
        int tuesdayHours = Integer.parseInt((String)request.getParameter("tuesdayHours"));
        int wednesdayHours = Integer.parseInt((String)request.getParameter("wednesdayHours"));
        int thursdayHours = Integer.parseInt((String)request.getParameter("thursdayHours"));
        int fridayHours = Integer.parseInt((String)request.getParameter("fridayHours"));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        format = format.withLocale(Locale.US);
        LocalDate monday = LocalDate.parse((String)session.getAttribute("monday"),format);
        LocalDate tuesday = LocalDate.parse((String)session.getAttribute("tuesday"),format);
        LocalDate wednesday = LocalDate.parse((String)session.getAttribute("wednesday"),format);
        LocalDate thursday = LocalDate.parse((String)session.getAttribute("thursday"),format);
        LocalDate friday = LocalDate.parse((String)session.getAttribute("friday"),format);
        
        HoursDB.insertClientHours(mondayHours, tuesdayHours, wednesdayHours, thursdayHours, fridayHours, monday, tuesday, wednesday, thursday, friday, (int)session.getAttribute("clientID"));
        
    }
    
    private Date getDateForDay(int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, day);
        
        return c.getTime();
    }
    
    
    public void getClientDetails(String clientID, HttpSession session)
    {
        Client foundClient;
        foundClient = ClientDB.getClientWithID(Integer.parseInt(clientID));
        session.setAttribute("foundClient", foundClient);
        
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
        if(thisMonthHours.size() > 0 && thisMonthHours != null)
        {
            for(ClientHoursArgs arg : thisMonthHours)
            {
                if(arg.getHours() != null)
                    totalMonthHours += arg.getHours().doubleValue();
            }
        }
        session.setAttribute("clientHours", totalMonthHours);
        
        totalMonthHours = 0;
        if(lastMonthHours.size() > 0 && lastMonthHours != null)
        {
            for(ClientHoursArgs arg : lastMonthHours)
            {
                if(arg.getHours() != null)
                    totalMonthHours += arg.getHours().doubleValue();
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
