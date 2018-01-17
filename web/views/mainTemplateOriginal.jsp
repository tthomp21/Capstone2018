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
             
        <title>Team Cash Flow</title>
    </head>
    
    <body>
        <div id="all">
            <c:import url="../Includes/header.html"/>
            
            <aside>
                <div id="accountDiv">
                    <p id="welcome">Welcome, <br><c:out value="${user.firstName}"/></p>
                    <ul>
                        
                        <!-- set action=manageClient or action=manageCaseWorker -->
                        <a href="/AccountsController?action=manage" id="btnManageAccount">
                            <li class="actItem">Manage Account</li>
                        </a>
                       
                        <a href="/AccountsController?action=logout" id="btnLogout">
                            <li class="actItem">Log Out</li>
                        </a>
                        
                    </ul>       
                </div>
                
                <!--choose required nav-->
                <nav>
                    <c:import url="../Includes/nav.html"/>
                </nav>
                
            </aside>
            
            <div id="main">
                Main program body goes here               
            </div>
            
            <c:import url="../pageImports/footer.html"/>            
        </div>
    </body>
</html>
