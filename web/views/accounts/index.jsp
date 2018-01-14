<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.8.3.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.js"></script>
        
        <link href="../../CSS/default.css" rel="stylesheet" type="text/css"/>
        <link href="../../CSS/accounts.css" rel="stylesheet" type="text/css"/>              
        <script src="../../JS/accounts.js" type="text/javascript"></script>
        <title>Team Cash Flow</title>
    </head>
    
    <body>
        <div id="all">
            <c:import url="../pageImports/header.html"/>
            <aside>
                <div id="accountDiv">
                    <c:choose>
                        <c:when test="${empty user}">
                            <p id="welcome">Welcome</p>
                            <a onclick="unfoldCreate()"  id="btnCreateAccount">
                                <li class="actItem">Create Account</li>
                            </a>
                            <a onclick="unfoldLogin()" id="btnLogin">
                                <li class="actItem">Log In</li>
                            </a>
                        </c:when>

                        <c:when test="${not empty user}">
                            <p id="welcome">Welcome, <c:out value="${user.firstName}"/></p>
                            <ul>
                                <a href="AccountsController?action=manage" id="btnManageAccount">
                                    <li class="actItem">Manage Account</li>
                                </a>
                                <a href="AccountsController?action=logout" id="btnLogout">
                                    <li class="actItem">Log Out</li>
                                </a>
                            </ul>                            
                        </c:when> 
                    </c:choose>
                </div>
                
                <nav>
                    
                </nav>
                
            </aside>
            
            <div id="mainCreate">
                Create Account Page                
            </div>
            
            <div id="mainLogin">
                Login Page <br><br><br><br>
                <a href="/AccountsController?action=testingCL">Login as client, redirect to client controller</a><br><br>
                <a href="/AccountsController?action=testingCW">Login as case worker, redirect to case worker controller</a><br>
                you can use these to test your programs while I write the accounts login part, the controller will
                create a client or case worker and store the object in the session as a variable called "user".
            </div>
            
            <div id="mainClient">
                Manage Client Account Page                
            </div>
            
            <div id="mainCaseWorker">
                Manage Case Worker Account Page                
            </div>
            
            <c:import url="../pageImports/footer.html"/>            
        </div>
    </body>
</html>
