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
        
        <link href="../CSS/default.css" rel="stylesheet" type="text/css"/>
        <link href="../CSS/caseWorker.css" rel="stylesheet" type="text/css"/>
        <script src="../JS/caseworker.js" type="text/javascript"></script>
        
        <script src="../JS/modals.js" type="text/javascript"></script>
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
                
                <c:forEach var="c" items="${foundClients}" varStatus="status">
                    <!-- need to make a form here -->
                    <!-- this is going to be similar to the client details page only they will be able to edit some of the clients info -->
                    
                    
                    
                </c:forEach>
                
                
                
                
                
                </div>
                
            </aside>
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
