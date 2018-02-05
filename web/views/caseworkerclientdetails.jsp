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
                <div id="main">
                    <table>
                        <tr>
                            <td>Name:</td>
                            <td>${foundClient.firstName} <c:choose><c:when test="${foundClient.middleInit != null}">${foundClient.middleInit}.</c:when></c:choose> ${foundClient.lastName}</td>
                        </tr>
                        <tr>
                            <td>Client Id:</td>
                            <td>${foundClient.clientID}</td>
                        </tr>
                        <tr>
                            <td>Client SSN:</td>
                            <td>${foundClient.SSN}</td>
                        </tr>
                        <tr>
                            <td>Address:</td>
                            <td>${foundClient.street}</td>
                        </tr>
                        <tr>
                            <td>City:</td>
                            <td>${foundClient.city}</td>
                        </tr>
                        <tr>
                            <td>State:</td>
                            <td>${foundClient.state}</td>
                        </tr>
                        <tr>
                            <td>Zip:</td>
                            <td>${foundClient.emZip}- ${foundClient.extZip}</td>
                        </tr>
                        <tr>
                            <td>Email:</td>
                            <td>${foundClient.email}</td>
                        </tr>
                        <tr>
                            <td>Phone:</td>
                            <td>${foundClient.getPhoneFormatted()}</td>
                        </tr>
                        <tr>
                            <td>BirthDate:</td>
                            <td>${foundClient.birthDate}</td>
                        </tr>
                        <tr>
                            <td>Enroll Date:</td>
                            <td>${foundClient.enrollmentDate}</td>
                        </tr>
                        <tr>
                            <td>Dependents:</td>
                            <td>${foundClient.dependents}</td>
                        </tr>
                        <tr>
                            <td>Partner Id:</td>
                            <td>${foundClient.partnerID}</td>
                        </tr>
                        <tr>
                            <td>Caseworker Id:</td>
                            <td>${foundClient.caseWorkerID}</td>
                        </tr>
                        <tr>
                            <td>Hours:</td>
                            <c:forEach var="c" items="${clientHours}" varStatus="status">
                                <td>${c.hours}</td>
                            </c:forEach>
                            
                        </tr>
                    </table>
                </div>
                
            </aside>
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
