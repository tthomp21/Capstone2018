<%-- 
    Document   : caseworkerclientdetails
    Created on : Jan 23, 2018, 9:38:00 PM
    Author     : Ty
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
                    <div class="column">
                    <table>
                        <tr><th>Client Information</th></tr>
                        <tr><td>Name:</td><td>${foundClient.firstName} <c:choose><c:when test="${foundClient.middleInit != null}">${foundClient.middleInit}.</c:when></c:choose> ${foundClient.lastName}<br>
                        <tr><td>Client Id:</td><td>${foundClient.clientID}</td></tr>
                            <tr><td>Client SSN:</td><td>${foundClient.SSN}</td></tr>
                            <tr><td>Address:</td><td>${foundClient.street}</td></tr>
                            <tr><td>City:</td><td>${foundClient.city}</td></tr>
                            <tr><td>State:</td><td>${foundClient.state}</td></tr>
                            <tr><td>Zip:</td><td>${foundClient.emZip}- ${foundClient.extZip}</td></tr>
                            <tr><td>Email:</td><td>${foundClient.email}</td></tr>
                            <tr><td>Phone:</td><td>${foundClient.getPhoneFormatted()}</td></tr>
                            <tr><td>BirthDate:</td><td>${foundClient.birthDate}</td></tr>
                            <tr><td>Enroll Date:</td><td>${foundClient.enrollmentDate}</td></tr>
                            <tr><td>Dependents:</td><td>${foundClient.dependents}</td></tr>
                            <tr><td>Partner Id:</td><td>${foundClient.partnerID}</td></tr>
                            <tr><td>Caseworker Id:</td><td>${foundClient.caseWorkerID}</td></tr>
                            <tr><td>Monthly Hours:</td>
                            <td>
                                <c:forEach var="c" items="${clientHours}" varStatus="status">
                                    ${c.hours}
                                </c:forEach>
                            </td> 
                        </tr>
                    </table>
                </div>
                <div class="column">
                    <table>
                        <tr><th>Hours</th></tr>
                        <tr><td>Monday</td><td> ${monday} </td><td><input type="number" name="mondayHours"></td></tr>
                        <tr><td>Tuesday</td><td> ${tuesday} </td><td><input type="number" name="tuesdayHours"></td></tr>
                        <tr><td>Wednesday</td><td> ${wednesday} </td><td><input type="number" name="wednesdayHours"></td></tr>
                        <tr><td>Thursday</td><td> ${thursday} </td><td><input type="number" name="thursdayHours"></td></tr>
                        <tr><td>Friday</td><td> ${friday} </td><td><input type="number" name="fridayHours"></td></tr>
                        <tr ><td colspan="3">
                        <form action="CaseWorkerController" method="post">
                            <input type="hidden" name="action" value="submitHours">
                            <input type="submit" value="Submit Hours">
                        </form>
                                </td></tr>
                    </table>
                </div>
                    <br>
                <div class="column">
                    <table>
                        <tr><th>Assistance Requests</th></tr>
                    <c:forEach var="r" items="${clientRequests}" varStatus="status">
                            <tr><td><b>Request id:</b></td><td> ${r.requestID}</td></tr>
                            <tr><td><b>Reason/Description:</b></td><td> ${r.getAssistance().assistDescription}</td></tr>
                            <tr><td><b>Amount:</b></td><td> ${r.amountPaid}</td></tr>
                        <tr><td><b>Date Submitted:</b></td><td> ${r.requestDate}</td></tr>
                        <tr><td><b>Date Approved:</b></td><td> ${r.dateDisbursed}</td></tr>
                        
                        
                        <tr><td><b>status:</b></td><td>${r.status}</td></tr>
                        <tr style="border-bottom-style: solid; margin-bottom: 2px;"><td colspan="2">
                        <c:choose>
                            <c:when test="${r.status == 0}">
                                <form action="CaseWorkerController" method="post">
                                <input type="hidden" value="${r.requestID}" name="requestID">
                                <input type="hidden" value="1" name="requestStat">
                                <input type="hidden" name="action" value="approveRequest">
                                <input type="submit" value="Approve">
                                </form>
                                <form action="CaseWorkerController" method="post">
                                <input type="hidden" value="${r.requestID}" name="requestID">
                                <input type="hidden" value="2" name="requestStat">
                                <input type="hidden" name="action" value="declineRequest">
                                <input type="submit" value="Decline">
                                </form>
                            </c:when>
                        </c:choose></td></tr>
                        
                        

                        <!--aid type: ${r.aidType}<br><br> -->
                                </td>
                    </tr>
                    </c:forEach>
                    </table>
                </div>
                
                </div>
            
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
