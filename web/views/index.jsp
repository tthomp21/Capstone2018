<%-- this was called index.jsp inside the accounts folder--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.8.3.min.js"></script>                
        <script src="../jq/jquery.validate.min.js" type="text/javascript"></script>
        <script src="../jq/additional-methods.min.js" type="text/javascript"></script>
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
                            <p id="welcome">Welcome, <br><c:out value="${user.firstName}"/></p>
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
            
            <div id="hiddenDiv">
                 <c:if test="${not empty redirect}">
                     <script id="rScript">
                        var timer = setTimeout(function() {
                            window.location="<c:out value="${redirect}"/>";
                        }, 1400);
                    </script>
                </c:if>
                <c:if test="${not empty loginType}">                    
                    <span id="loginType"><c:out value="${loginType}"/></span>  
                </c:if>                
            </div>
                
            <div id="loginSelect">
                <ul>
                    <a onclick="unfoldClLogin()"><li id="btnClient">Client</li></a>
                    <a onclick="unfoldCwLogin()"><li id="btnCaseWorker">Case Worker</li></a>
                </ul>                
            </div>
                
            <div id="clientLogin">
                <div class="loginDiv">                    
                    <form action="AccountsController" method="post" id="loginFormCL">
                        <fieldset>
                            <legend>Enter your credentials</legend>

                            <label for="loginUserNameCL">User Name:</label>
                            <input type="text" id="loginUserNameCL" name="loginUserNameCL" autofocus><br>

                            <label for="loginPasswordCL">Password:</label>
                            <input type="password" id="loginPasswordCL" name="loginPasswordCL"><br>
                        
                            <input type="hidden" name="action" value="loginAsCL"/>
                            <input type="submit" name="submit" value="Log In" class="button"/>
                            <br>
                            
                            <c:if test="${loginType == 'cl'}">
                                <p id="loginMsgCL" class="loginMsg"><c:out value="${loginMsg}"/></p>
                            </c:if>
                            
                        </fieldset>                        
                    </form>  
                    
                    <form action="AccountsController" method="post" id="testLoginFormCL">
                        <input type="hidden" name="action" value="testLoginAsCL"/>
                        <input type="submit" name="submit" value="Dummy login for testing" class="button"/>                                                       
                    </form>
                </div>      
            </div>
                
            <div id="caseWorkerLogin">
                <div class="loginDiv">
                    <form action="AccountsController" method="post" id="loginFormCW">
                        <fieldset>
                            <legend>Enter your credentials</legend>

                            <label for="loginUserNameCW">User Name:</label>
                            <input type="text" id="loginUserNameCW" name="loginUserNameCW" autofocus><br>

                            <label for="loginPasswordCW">Password:</label>
                            <input type="password" id="loginPasswordCW" name="loginPasswordCW"><br>
                        
                            <input type="hidden" name="action" value="loginAsCW"/>
                            <input type="submit" name="submit" value="Log In" class="button"/>   
                            <br>
                            
                            <c:if test="${loginType == 'cw'}">
                                <p id="loginMsgCW" class="loginMsg"><c:out value="${loginMsg}"/></p>
                            </c:if>
                        </fieldset>                        
                    </form>  
                    
                    <form action="AccountsController" method="post" id="testLoginFormCW">
                        <input type="hidden" name="action" value="testLoginAsCW"/>
                        <input type="submit" name="submit" value="Dummy login for testing" class="button"/>                                                       
                    </form>                    
                </div>      
            </div>
            
            <div id="mainClient">
                Manage Client Account Page                
            </div>
            
            <div id="mainCaseWorker">
                Manage Case Worker Account Page                
            </div>
                
            <div id="frontImages">
                <span><img id="imgClient" src="../Images/client.jpg" width="372" height="372"/></span>
                <span><img id="imgCaseWorker" src="../Images/caseworker.jpg" width="372" height="372"/></span>
            </div>
            
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
