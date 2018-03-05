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
        <link href="../CSS/requests.css" rel="stylesheet" type="text/css"/>
        <script src="../JS/request.js" type="text/javascript"></script>
        
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
                        <a href="/AccountsController?action=manageClient" id="btnManageAccount">
                            <li class="actItem">Manage Account</li>
                        </a>
                       
                        <a href="/AccountsController?action=logout" id="btnLogout">
                            <li class="actItem">Log Out</li>
                        </a>                        
                    </ul>       
                </div>
                                
                <nav>
                    <c:import url="../Includes/clientNav.html"/>
                </nav>
                
            </aside>
            
            <div id="main">
                <div id="container">                    
                    <div id="requestDiv">
                        <form action="ClientRequestController" method="post" id="requestForm" enctype="multipart/form-data">
                            <h1>Submit a request for assistance</h1>
                            <fieldset>
                                <p>
                                    <label for="requestType">Choose a type of assistance: </label>
                                    <select name="requestType">
                                        <option value="tuition">Tuition</option>
                                        <option value="utilities">Utilities</option>
                                        <option value="repair">Vehicle Repair</option>
                                        <option value="registration">Vehicle Registration</option>
                                        <option value="fuel">Fuel</option>
                                        <option value="clothing">Clothing</option>
                                    </select>
                                </p>
                                <p>
                                    <label for="amount">Enter the amount you are requesting: </label>
                                    <input type="text" id="amount" name="amount" value="${prevAmount}"><br>
                                </p>                                
                                                               
                                <label id="uploadLbl">Upload documentation for review:</label><br>
                                <input type="file" id="file" name="file">
                                
                                <input type="hidden" name="action" value="processRequest"/>
                                <input type="submit" name="submit" value="Submit Request" class="button" id="btnUpload" onclick="hideOldMsg()"/>
                                
                            </fieldset>     
                            
                            <div class="requestErrors"> 
                                <c:if test="${not empty requestMsg}">
                                    <c:out value="${requestMsg}"/>
                                </c:if>
                            </div>
                            <div class="sanctionMsg"> 
                                <c:if test="${not empty sanctionMsg}">
                                    <c:out value="${sanctionMsg}"/>
                                </c:if>
                            </div>
                            <div class="requestSuccess"> 
                                <c:if test="${not empty requestSuccessMsg}">
                                    <c:out value="${requestSuccessMsg}"/>
                                </c:if>
                            </div>
                        </form>  
                    </div>
                    
                                
                    <div id="infoDiv">
                        <h1>Available Assistance</h1>
                        <ul>                            
                            <li>Help paying tuition</li>
                            <li>Money for utility bills </li>
                            <li>Vehicle repair </li>
                            <li>Vehicle registration </li>
                            <li>Fuel for necessary transportation</li>
                            <li>Professional clothing </li>
                        </ul>
                    </div>                    
                </div>
            </div>
            
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
