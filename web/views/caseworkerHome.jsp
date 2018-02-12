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
        <script src="../JS/caseworker.js" type="text/javascript"></script>
        
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
                <h3>Your Clients</h3>
                <table>
                    <tr>
                        <th>Client ID |</th>
                        <th>Last Name |</th>
                        <th>Mid |</th>
                        <th>First Name</th>
                        
                    </tr>
                    <c:forEach var="c" items="${foundClients}" varStatus="status">
                        <tr>
                            <td align="center">${c.clientID}</td>
                            <td>${c.lastName}</td>
                            <td>${c.middleInit}</td>
                            <td>${c.firstName}</td>
                            
                            <td>
                                <form action="CaseWorkerController" method="post">
                                <input type="hidden" value="${c.clientID}" name="clientID">
                                <input type="hidden" name="action" value="clientDetails">
                                <input type="submit" value="Details">
                                </form>
                            </td>
                            <!-- <td>
                                <form action="CaseWorkerController" method="post">
                                <input type="hidden" value="${c.clientID}" name="clientID">
                                <input type="hidden" name="action" value="clientHours">
                                <input type="submit" value="Enter Hours">
                                </form>
                            </td> -->
                        </tr>
                    </c:forEach>  
                </table>
            </div>
            
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
    <script type="text/javascript">
        function handleClick(evt){
            var node = evt.target || evt.srcElement;
            if(node.name == "action"){
                node.value = "clientDetails";
            }
        }
    </script>
</html>
