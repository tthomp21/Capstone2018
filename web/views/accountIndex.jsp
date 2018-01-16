<%-- this was called index.jsp inside the accounts folder--%>
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
        <link href="../CSS/accounts.css" rel="stylesheet" type="text/css"/>              
        <script src="../JS/accounts.js" type="text/javascript"></script>
        <title>Team Cash Flow</title>
    </head>
    
    <body>
        <div id="all">
            <c:import url="../Includes/header.html"/>
            <aside>
                <div id="accountDiv">
                    <c:choose>
                        <c:when test="${empty user}">
                            <p id="welcome">Welcome</p>
                            <a onclick="unfoldCreate()"  id="btnCreateAccount">
                                <li class="actItem">Create Account</li>
                            </a>
                            <a onclick="unfoldLoginSelect()" id="btnLogin">
                                <li class="actItem">Log In</li>
                            </a>
                        </c:when>

                        <c:when test="${not empty user}">
                            <p id="welcome">Welcome, <c:out value="${user.firstName}"/></p>
                            <ul>
                                <a href="AccountsController?action=" id="btnManageAccount">
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
                    <c:import url="../Includes/accountsNav.html"/>
                </nav>
                
            </aside>
            
            <div id="mainCreate">
                Create Account Page                
            </div>
            
            <div id="mainLogin">
                 <c:if test="${not empty redirect}">
                     <script id="rScript">
                        var timer = setTimeout(function() {
                            window.location="<c:out value="${redirect}"/>";
                        }, 1400);
                    </script>
                </c:if>
            </div>
                
            <div id="loginSelect">
                <ul>
                    <a onclick="unfoldClLogin()"><li id="btnClient" class="test">Client</li></a>
                    <a onclick="unfoldCwLogin()"><li id="btnCaseWorker">Case Worker</li></a>
                </ul>                
            </div>
                
            <div id="clientLogin">
                <div class="loginDiv">
                    <form action="AccountsController" method="post" id="testingCL">
                        <input type="hidden" name="action" value="testingCL"/>
                        <input type="submit" name="submit" value="login as dummy client"/>
                    </form>
                </div>      
            </div>
                
            <div id="caseWorkerLogin">
                <div class="loginDiv">
                    <form action="AccountsController" method="post" id="testingCW">
                        <input type="hidden" name="action" value="testingCW"/>
                        <input type="submit" name="submit" value="login as dummy case worker"/>
                    </form>                                      
                </div>      
            </div>
            
            <div id="mainClient">
                Manage Client Account Page                
            </div>
            
            <div id="mainCaseWorker">
                Manage Case Worker Account Page                
            </div>
            
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
