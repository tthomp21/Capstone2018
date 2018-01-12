<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.8.3.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.js"></script>
        
        <link rel="stylesheet" href="../CSS/testingCSS.css" type="text/css"/>
        <script src="JS/defaultJS.js" type="text/javascript"></script>
        <title>Team Cash Flow</title>
    </head>
    
    <body>
        <div id="all">
            <c:import url="TestIncludes/header.html"/>
            
            <aside>
                <div id="accountDiv">
                    <c:choose>
                        <c:when test="${empty user}">
                            <p id="welcome">Welcome</p>
                            <a href="HomeController?action=create">
                                <li class="actItem">Create Account</li>
                            </a>
                            <a href="HomeController?action=login">
                                <li class="actItem">Log In</li>
                            </a>
                        </c:when>

                        <c:when test="${not empty user}">
                            <p id="welcome">Welcome, <c:out value="${user.firstName}"/></p>
                            <ul>
                                <a href="HomeController?action=manage">
                                    <li class="actItem">Manage Account</li>
                                </a>
                                <a href="HomeController?action=logout">
                                    <li class="actItem">Log Out</li>
                                </a>
                            </ul>                            
                        </c:when> 
                    </c:choose>
                </div>
                
                <c:import url="TestIncludes/nav.html"/>
            </aside>
            
            <div id="main">
                Main program body                
            </div>
            
            <c:import url="TestIncludes/footer.html"/>            
        </div>
    </body>
</html>
