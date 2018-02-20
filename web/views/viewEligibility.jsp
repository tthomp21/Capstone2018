<%-- 
    Document   : viewEligibility
    Created on : Feb 3, 2018, 12:05:08 PM
    Author     : radad
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
        <link href="../CSS/eligibility.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="/JS/assistance.js"></script>


        <title>Team Cash Flow</title>

    </head>

    <body>
        <div id="all">
            <c:import url="../Includes/header.html"/>

            <aside>
                <div id="accountDiv">
                    <p id="welcome">Welcome, <br><c:out value="${user.firstName}"/></p>
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
                <section class="eligibilitySection">

                    <table border="1">
                        <caption>Eligibility Overview</caption>
                        <tr>
                            <th>Assistance type</th>
                            <th>Eligibility Status</th>
                            <th>Recertification Date</th>
                        </tr>
                    </table>

                </section>

                <div id="divider">
                    <%--
                                        <c:if test="${empty '${dateMessage}'}">
                                            <h3>Select the date you want to look at your hours.</h3>
                                        </c:if>
                                        <c:if test="${not empty '${dateMessage}'}">
                                            <h3 class="formatDateMessage">${dateMessage}</h3>
                                        </c:if>
                    --%>
                    <c:choose>
                        <c:when test="${empty dateMessage}">
                            <h3>Select the date you want to look at your hours.</h3>
                        </c:when>
                        <c:otherwise>
                            <h3 class="formatDateMessage">${dateMessage}</h3>
                        </c:otherwise>
                    </c:choose>

                    <form action="EligibilityController" method="post">
                        <input type="hidden" name="action" value="filterHours">
                        <label>From:</label><input type="date" name="fromDate"><br>
                        <label>To:</label><input type="date" name="toDate"><br>
                        <label></label><input type="submit" value="Get Hours">
                    </form>

                    <c:if test="${not isHideTable}"> 

                        <section class="hourSection">
                            <fieldset>
                                <legend style="font-weight: bold; background-color: #f0e68c">${user.getFirstName()} ${user.getLastName()}&apos;s Hours</legend>
                                <table border="1" style="width: 100%; border-collapse: collapse">

                                    <tr>
                                        <th>Date Hours Made</th>
                                        <th>Number of hours</th>
                                    </tr>
                                    <c:forEach items="${clientWeeklyHours}" var="hrs" varStatus="loop">
                                        <tr>
                                            <td><c:out value="${hrs.dateHoursEntered}" /></td>
                                            <td><c:out value="${hrs.numberOfHours}" /></td>
                                        </tr>
                                    </c:forEach>
                                    <tr><td colspan="2" style="font-weight: bold; background-color: #f0e68c">${clientPartner.getFirstName()} ${clientPartner.getLastName()}
                                            &apos;s Hours</td></tr>
                                            <c:forEach items="${partnerWeeklyHours}" var="hrs" varStatus="loop">

                                        <tr>

                                            <td><c:out value="${hrs.dateHoursEntered}" /></td>
                                            <td><c:out value="${hrs.numberOfHours}" /></td>

                                        </tr>

                                    </c:forEach>
                                        <tr>
                                            <td colspan="2" style="background-color: #f0e68c">Total Hours in this period for both of you</td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="font-weight: bold">${user.getFirstName()}&apos;s hours</td>
                                             <td><c:out value="${clientTotalFilteredHours}" /></td>
                                        </tr>
                                        <tr>
                                        <td style="font-weight: bold">${clientPartner.getFirstName()}&apos;s hours</td>
                                             <td><c:out value="${partnerTotalFilteredHours}" /></td>
                                        </tr>
                                        <td style="font-weight: bold">Total Hours for you both</td>
                                             <td><c:out value="${totalHoursFilteredForBoth}" /></td>
                                        </tr>

                            </fieldset>

                            </table>
                        </section>
                    </c:if>

                </div>

                <section class="alertsSection">
                    <c:if test="${isSanctioned == false}">   
                        <div id="hoursAlertDiv">
                            <h2>Hours Notification</h2>
                            <p>${warningMsg}</p>

                        </div>
                    </c:if>

                    <c:if test="${isSanctioned}">   
                        <div id="sanctionMsgDiv">
                            <h2>Sanctions Notification</h2>
                            <p>${periodToWaitToB_Eligible}</p>
                        </div>
                    </c:if>

                </section>


            </div>
            <c:import url="../Includes/footer.html"/> 
    </body>
</html>

