<%-- 
    Document   : caseworkerclientdetails
    Created on : Jan 23, 2018, 9:38:00 PM
    Author     : Tyler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.8.3.min.js"></script>        
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.js"></script>
        
        <!-- <link href="../CSS/default.css" rel="stylesheet" type="text/css"/> -->
        <link href="../CSS/caseWorker.css" rel="stylesheet" type="text/css"/>
        
        <title>Team Cash Flow</title>
    </head>
    
    <body>
        <div id="all">
            <c:import url="../Includes/header.html"/>
            
            <aside>
                <div id="accountDiv">
                    <p id="welcome">Welcome, <br><c:out value="${user.firstName}"/></p>
                    <ul>
                        <a href="AccountsController?action=manageCaseWorker" id="btnManageAccount">
                            <li class="actItem">Manage Account</li>
                        </a>
                        <a href="AccountsController?action=logout" id="btnLogout">
                            <li class="actItem">Log Out</li>
                        </a>
                    </ul>       
                </div>
                    
                <nav>
                    <c:import url="/Includes/caseWorkerNav.html"/>
                </nav>
                </aside>
                <div id="main">
                    <div style="width: 25%; float: left;">
                    <table>
                        <tr><th>Client Information</th></tr>
                        <tr><td><b>Name:</b></td><td>${foundClient.firstName} <c:choose><c:when test="${foundClient.middleInit != null}">${foundClient.middleInit}.</c:when></c:choose> ${foundClient.lastName}<br>
                        <tr><td><b>Client Id:</b></td><td>${foundClient.clientID}</td></tr>
                            <tr><td><b>Client SSN:</b></td><td>${foundClient.SSN}</td></tr>
                            <tr><td><b>Address:</b></td><td>${foundClient.street}</td></tr>
                            <tr><td><b>City:</b></td><td>${foundClient.city}</td></tr>
                            <tr><td><b>State:</b></td><td>${foundClient.state}</td></tr>
                            <tr><td><b>Zip:</b></td><td>${foundClient.emZip}- ${foundClient.extZip}</td></tr>
                            <tr><td><b>Email:</b></td><td>${foundClient.email}</td></tr>
                            <tr><td><b>Phone:</b></td><td>${foundClient.getPhoneFormatted()}</td></tr>
                            <tr><td><b>BirthDate:</b></td><td>${foundClient.birthDate}</td></tr>
                            <tr><td><b>Enroll Date:</b></td><td>${foundClient.enrollmentDate}</td></tr>
                            <tr><td><b>Dependents:</b></td><td>${foundClient.dependents}</td></tr>
                            <tr><td><b>Partner Id:</b></td><td>${foundClient.partnerID}</td></tr>
                            <tr><td><b>Caseworker Id:</b></td><td>${foundClient.caseWorkerID}</td></tr>
                            <tr><td><b>Hours for this month:</b></td><td>${clientHours}</td></tr>
                            <tr><td><b>Hours for last month:</b></td><td>${lastMonthClientHours} </td></tr>
                        </tr>
                    </table>
                </div>
                
                <div style="width: 50%; float: left;">
                    <table>
                        <tr><th>Assistance Requests</th></tr>
                    <c:forEach var="r" items="${clientRequests}" varStatus="status">
                        <tr><td><b>Request id:</b></td><td> ${r.requestID}</td></tr>
                        <tr><td><b>Reason/Description:</b></td><td> ${r.getAssistance().assistDescription}</td></tr>
                        <tr><td><b>Amount:</b></td><td> ${r.amountPaid}</td></tr>
                        <tr><td><b>Date Submitted:</b></td><td> ${r.requestDate}</td></tr>
                        <tr><td><b>Date Approved/Denied:</b></td><td> ${r.dateDisbursed}</td></tr>
                        
                        
                        
                        <c:choose>
                            <c:when test="${r.status == 0}">
                                <tr><td>
                                <b>Status:</b></td><td> Pending</td></tr>
                                <tr style="border-bottom-style: solid; margin-bottom: 2px;"><td>
                                <form action="CaseWorkerController" method="post">
                                <input type="hidden" value="${r.requestID}" name="requestID">
                                <input type="hidden" value="1" name="requestStat">
                                <input type="hidden" name="action" value="approveRequest">
                                <input type="submit" value="Approve" id="button">
                                </form></td><td>
                                <form action="CaseWorkerController" method="post">
                                <input type="hidden" value="${r.requestID}" name="requestID">
                                <input type="hidden" value="2" name="requestStat">
                                <input type="hidden" name="action" value="declineRequest">
                                <input type="submit" value="Decline" id="button">
                                </form>
                            </c:when>
                            <c:when test="${r.status == 1}">
                                <tr style="border-bottom-style: solid; margin-bottom: 2px;"><td>
                                <b>Status:</b></td><td> Approved
                            </c:when>
                            <c:when test="${r.status == 2}">
                                <tr style="border-bottom-style: solid; margin-bottom: 2px;"><td>
                                <b>Status:</b></td><td> Denied
                            </c:when>
                        </c:choose></td></tr>
                        
                    </c:forEach>
                    </table>
                </div>
                    <div style="width: 20%; float: left;">
                    <table>
                        <form action="CaseWorkerController" method="post">
                            <!-- need to change the input text to variables.. get them with session instead of request -->
                        <tr><th><b>Hours</b></th></tr>
                        <tr><td><b>Monday</b></td><td> ${monday} </td><td><input type="number" name="mondayHours" value="${monHours}"></td></tr>
                        <tr><td><b>Tuesday</b></td><td> ${tuesday} </td><td><input type="number" name="tuesdayHours" value="${tueHours}"></td></tr>
                        <tr><td><b>Wednesday</b></td><td> ${wednesday} </td><td><input type="number" name="wednesdayHours" value="${wedHours}"></td></tr>
                        <tr><td><b>Thursday</b></td><td> ${thursday} </td><td><input type="number" name="thursdayHours" value="${thurHours}"></td></tr>
                        <tr><td><b>Friday</b></td><td> ${friday} </td><td><input type="number" name="fridayHours" value="${friHours}"></td></tr>
                        <tr ><td colspan="3">
                        
                            <input type="hidden" name="action" value="submitHours">
                            <input type="hidden" name="clientID" value="${foundClient.clientID}">
                            <input type="submit" value="Submit Hours" id="button">
                        </form>
                                </td></tr>
                    </table>
                </div>
                
                </div>
            
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
