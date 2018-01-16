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
        
        <title>Team Cash Flow</title>
    </head>
    
    <body>
        <div id="all">
            <c:import url="../Includes/header.html"/>
            
            <aside>
                <div id="accountDiv">
                    <p id="welcome">Welcome, <c:out value="${user.firstName}"/></p>
                    <ul>
                        <a href="AccountsController?action=manageClient" id="btnManageAccount">
                            <li class="actItem">Manage Account</li>
                        </a>
                        <a href="AccountsController?action=logout" id="btnLogout">
                            <li class="actItem">Log Out</li>
                        </a>
                    </ul>       
                </div>
                    
                <nav>
                    <c:import url="../Includes/clientNav.html"/>
                </nav>    
                
            </aside>
            
            <div id="main">
                cl Main program body                
            </div>
            
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
