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
        <link href="../CSS/assist.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="/JS/assistance.js"></script>
        
        <script src="../JS/modals.js" type="text/javascript"></script>

        <title>Primary Assistances</title>

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

                <section id="primarySection">
                    <%-- <h1 class="primaryTitle" class="priClose">Primary Assistances</h1> --%>
                    <h1 id="priH1" class="priClose">Primary Assistances</h1>
                    <h2 class="fold">Food Stamp or SNAP</h2>
                    <div class="closed">
                        <table border="1">
                            <tr>

                                <th>Amount Paid</th>
                                <th>Date Disbursed</th>
                                <th>Assistance Status</th>
                            </tr>
                            <c:forEach items="${foodList}" var="aBenefit" varStatus="loop">

                                <tr>

                                    <td><c:out value="${aBenefit.aidAmount}" /></td>
                                    <td><c:out value="${aBenefit.aidDateDisbursedFormatted}" /></td>
                                    <td><c:out value="${aBenefit.assistanceStatus}" /></td>
                                </tr>

                            </c:forEach>
                        </table>
                    </div>
                    <h2 class="fold">Cash Benefits or ADC</h2>                            
                    <c:choose>
                        <c:when test="${isSanctioned}">
                            <div class="closed" style="color: red;">
                                <h3>Our records indicate that you were sanctioned; which means that you are no longer eligible
                                    for cash assistance till the date the sanction is terminated.
                                </h3>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="closed">
                                <table border="1">
                                    <tr>
                                        <th>Amount Paid</th>
                                        <th>Date Disbursed</th>
                                        <th>Assistance Status</th>
                                    </tr>
                                    <c:forEach items="${cashList}" var="aBenefit" varStatus="loop">

                                        <tr>
                                            <td><c:out value="${aBenefit.aidAmount}" /></td>
                                            <td><c:out value="${aBenefit.aidDateDisbursedFormatted}" /></td>
                                            <td><c:out value="${aBenefit.assistanceStatus}" /></td>
                                        </tr>

                                    </c:forEach>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>



                    <h2 class="fold">Medicaid Benefits</h2>
                    <div class="closed">
                        <table border="1">
                            <tr>


                                <%--   <th>Approval Code</th> --%>
                                <th>Activation Date</th>
                                <th>Assistance Status</th>
                            </tr>
                            <c:forEach items="${medicaidList}" var="aBenefit" varStatus="loop">

                                <tr>

                                  <%--  <td><c:out value="${aBenefit.aidAmount}" /></td> --%>
                                    <td><c:out value="${aBenefit.aidDateDisbursedFormatted}" /></td>
                                    <td><c:out value="${aBenefit.assistanceStatus}" /></td>
                                </tr>

                            </c:forEach>
                        </table>
                    </div>
                </section>

                <section id="secondarySection">
                    <h1 id="secH1" class="priClose">Secondary Assistances</h1>

                    <c:choose>
                        <c:when test="${isSanctioned}">
                            <div>
                                <h1 style="color: red;">Sanction has applied to your case</h1>
                                <p style="font-size: 1.5em; text-align: justify;">Here are details of the sanction:<br>
                                    ${periodToWaitToB_Eligible}
                                </p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <h2 class="fold">Car Repairs</h2>
                            <div class="closed">
                                <table border="1">
                                    <tr>

                                        <th>Date requested</th>
                                        <th>Amount Paid</th>
                                        <th>Date Approved</th>
                                        <th>Assistance Status</th>
                                    </tr>
                                    <c:forEach items="${carRepairsList}" var="aBenefit" varStatus="loop">

                                        <tr>

                                            <td><c:out value="${aBenefit.requestDate}" /></td>
                                            <td><c:out value="${aBenefit.amountPaid}" /></td>
                                            <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                            <td><c:out value="${aBenefit.status}" /></td>
                                        </tr>

                                    </c:forEach>
                                </table>
                            </div>

                            <h2 class="fold">Clothing Benefits</h2>
                            <div class="closed">
                                <table border="1">
                                    <tr>

                                        <th>Date requested</th>
                                        <th>Amount Paid</th>
                                        <th>Date Approved</th>
                                        <th>Assistance Status</th>
                                    </tr>
                                    <c:forEach items="${clothingList}" var="aBenefit" varStatus="loop">

                                        <tr>

                                            <td><c:out value="${aBenefit.requestDate}" /></td>
                                            <td><c:out value="${aBenefit.amountPaid}" /></td>
                                            <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                            <td><c:out value="${aBenefit.status}" /></td>
                                        </tr>

                                    </c:forEach>
                                </table>
                            </div>

                            <h2 class="fold">Vehicle Register</h2>
                            <div class="closed">
                                <table border="1">
                                    <tr>

                                        <th>Date requested</th>
                                        <th>Amount Paid</th>
                                        <th>Date Approved</th>
                                        <th>Assistance Status</th>
                                    </tr>
                                    <c:forEach items="${vehicleRegisterList}" var="aBenefit" varStatus="loop">

                                        <tr>

                                            <td><c:out value="${aBenefit.requestDate}" /></td>
                                            <td><c:out value="${aBenefit.amountPaid}" /></td>
                                            <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                            <td><c:out value="${aBenefit.status}" /></td>
                                        </tr>

                                    </c:forEach>
                                </table>
                            </div>

                            <h2 class="fold">Gas Fuel</h2>
                            <div class="closed">
                                <table border="1">
                                    <tr>

                                        <th>Date requested</th>
                                        <th>Amount Paid</th>
                                        <th>Date Approved</th>
                                        <th>Assistance Status</th>
                                    </tr>
                                    <c:forEach items="${gasList}" var="aBenefit" varStatus="loop">

                                        <tr>
                                            <td><c:out value="${aBenefit.requestDate}" /></td>
                                            <td><c:out value="${aBenefit.amountPaid}" /></td>
                                            <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                            <td><c:out value="${aBenefit.status}" /></td>
                                        </tr>

                                    </c:forEach>
                                </table>        </div>

                            <h2 class="fold">Tuitions</h2>
                            <div class="closed">
                                <table border="1">
                                    <tr>

                                        <th>Date requested</th>
                                        <th>Amount Paid</th>
                                        <th>Date Approved</th>
                                        <th>Assistance Status</th>
                                    </tr>
                                    <c:forEach items="${tuitionList}" var="aBenefit" varStatus="loop">

                                        <tr>

                                            <td><c:out value="${aBenefit.requestDate}" /></td>
                                            <td><c:out value="${aBenefit.amountPaid}" /></td>
                                            <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                            <td><c:out value="${aBenefit.status}" /></td>
                                        </tr>

                                    </c:forEach>
                                </table>        
                            </div>
                        </c:otherwise>
                    </c:choose>
                </section>



            </div>

            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
