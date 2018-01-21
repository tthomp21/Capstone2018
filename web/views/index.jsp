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
                <div id="createDiv">
                    <h1>
                        Create a Client Account
                    </h1>
                    <form action="AccountsController" method="post" id="createForm">                        
                        <p>
                            <label for="createClientID">Enter your Client ID#:</label>
                            <input type="text" id="createClientID" name="createClientID" value="${prevCreateClientID}"><br>
                        </p>

                        <p>
                            <label for="createSSN">Verify your SSN:</label>
                            <input type="text" id="createSSN" name="createSSN" value="${prevCreateSSN}"><br>
                        </p>

                        <p>
                            <label for="createUserName">Choose a User Name:</label>
                            <input type="text" id="createUserName" name="createUserName" value="${prevCreateUserName}"><br>
                        </p>

                        <p>
                            <label for="createPassword1">Choose a Password:</label>
                            <input type="password" id="createPassword1" name="createPassword1" value="${prevCreatePassword1}"><br>
                        </p>

                        <p>
                            <label for="createPassword2">Confirm Password:</label>
                            <input type="password" id="createPassword2" name="createPassword2" value="${prevCreatePassword2}"><br>
                        </p>

                        <p>
                            <label></label>
                            <input type="hidden" name="action" value="create"/>
                            <input type="submit" name="submit" value="Create Account" class="button" id="btnCreate"/>                                 
                        </p>
                    </form>
                    
                    <div id="createMsgDiv" class="msgDiv">
                        <c:if test="${not empty createMsg}">
                            <p id="createMsg" class="errorMsg"><c:out value="${createMsg}"/></p>
                        </c:if>
                        <c:if test="${not empty createMsgSuccess}">
                            <p id="createMsgSuccess" class="successMsg"><c:out value="${createMsgSuccess}"/></p>
                        </c:if> 
                    </div>
                    
                </div>                
            </div>
            
            <div id="hiddenDiv">
                <c:if test="${not empty redirect}">
                    <script id="rScript">
                        var timer = setTimeout(function() {
                            window.location="<c:out value="${redirect}"/>";
                        }, 2400);
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
                    <form action="AccountsController" method="post" id="loginFormCL" class="actForm">
                        <fieldset>
                            <legend>Enter your credentials</legend>

                            <label for="loginUserNameCL">User Name:</label>
                            <input type="text" id="loginUserNameCL" name="loginUserNameCL" autofocus="true" value="${prevLoginUserNameCL}"><br>

                            <label for="loginPasswordCL">Password:</label>
                            <input type="password" id="loginPasswordCL" name="loginPasswordCL" value="${prevPasswordNameCL}"><br>
                        
                            <input type="hidden" name="action" value="loginAsCL"/>
                            <input type="submit" name="submit" value="Log In" class="button"/>
                            <br>
                        </fieldset>                        
                    </form>  
                
                    <div id="loginMsgDivCL" class="msgDiv">
                        <c:if test="${loginType == 'cl'}">
                            <p id="loginMsgCL" class="errorMsg"><c:out value="${loginMsg}"/></p>
                        </c:if>
                    </div>
                    <!--        
                    <form action="AccountsController" method="post" id="testLoginFormCL" class="actForm">
                        <input type="hidden" name="action" value="testLoginAsCL"/>
                        <input type="submit" name="submit" value="Dummy login for testing" class="button"/>                                                       
                    </form>
                    -->
                </div>       
            </div>
                
            <div id="caseWorkerLogin">
                <div class="loginDiv">
                    <form action="AccountsController" method="post" id="loginFormCW" class="actForm">
                        <fieldset>
                            <legend>Enter your credentials</legend>

                            <label for="loginUserNameCW">User Name:</label>
                            <input type="text" id="loginUserNameCW" name="loginUserNameCW" autofocus="true" value="${prevLoginUserNameCW}"><br>

                            <label for="loginPasswordCW">Password:</label>
                            <input type="password" id="loginPasswordCW" name="loginPasswordCW" value="${prevLoginPasswordCW}"><br>
                        
                            <input type="hidden" name="action" value="loginAsCW"/>
                            <input type="submit" name="submit" value="Log In" class="button"/>   
                            <br>
                        </fieldset>                        
                    </form>  
                            
                    <div id="loginMsgDivCW" class="msgDiv">
                        <c:if test="${loginType == 'cw'}">
                            <p id="loginMsgCW" class="errorMsg"><c:out value="${loginMsg}"/></p>
                        </c:if>
                    </div>
                       
                    <!--        
                    <form action="AccountsController" method="post" id="testLoginFormCW" class="actForm">
                        <input type="hidden" name="action" value="testLoginAsCW"/>
                        <input type="submit" name="submit" value="Dummy login for testing" class="button"/>                                                       
                    </form>
                    -->
                </div>      
            </div>
                            
            <div id="loginSuccess">
                
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
