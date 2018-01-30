<%-- 
    Document   : assistance
    Created on : Jan 10, 2018, 2:55:01 PM
    Author     : Murad Smoqy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" href="/CSS/assistance.css" type="text/css">
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="/JS/assistance.js"></script>
<%-- <%@ page import="java.util.List, business.*"%> --%>
<%--<%@ page import = "jared.simpledatabase.*" %> --%>
<%-- <jsp:useBean id="Person" class="business.Person" scope="request"></jsp:useBean> --%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%-- <link type="text/css" rel="stylesheet" href="style/main.css" /> --%>
        <title>Benefits for ${client.getFirstName()}</title>
    </head>


    <body>
        <section id="assistanceWrapper">
           
            <main id="assistanceMain">
                 <c:import url="../Includes/header.html"/>
                <h2>Food Stamps or SNAP</h2>
                <div>
                    <table border="1">
                        <tr>
                            
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
                            <th>Assistance Status</th>
                        </tr>
                        <c:forEach items="${foodList}" var="aBenefit" varStatus="loop">

                            <tr>
                               
                                <td><c:out value="${aBenefit.requestDate}" /></td>
                                <td><c:out value="${aBenefit.amountPaid}" /></td>
                                <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                <td><c:out value="${aBenefit.status}" /></td>
                            </tr>

                        </c:forEach>
                    </table>
                </div>

                <h2>Cash Benefits or ADC</h2>
                <div>
                    <table>
                        <tr>
                          
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
                            <th>Assistance Status</th>
                        </tr>
                        <c:forEach items="${cashList}" var="aBenefit" varStatus="loop">

                            <tr>
                                
                                <td><c:out value="${aBenefit.requestDate}" /></td>
                                <td><c:out value="${aBenefit.amountPaid}" /></td>
                                <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                <td><c:out value="${aBenefit.assistanceStatus}" /></td>
                            </tr>

                        </c:forEach>
                    </table>
                </div>

                <h2>Medicaid Benefits</h2>
                <div>
                    <table>
                        <tr>
                            
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
                            <th>Assistance Status</th>
                        </tr>
                        <c:forEach items="${medicaidList}" var="aBenefit" varStatus="loop">

                            <tr>
                                
                                <td><c:out value="${aBenefit.requestDate}" /></td>
                                <td><c:out value="${aBenefit.amountPaid}" /></td>
                                <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                <td><c:out value="${aBenefit.assistanceStatus}" /></td>
                            </tr>

                        </c:forEach>
                    </table>
                </div>

                <h2>electricity or Gas benefits </h2>
                <div>
                    <table>
                        <tr>
                        
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
                            <th>Assistance Status</th>
                        </tr>
                        <c:forEach items="${otherBenefitsList}" var="aBenefit" varStatus="loop">

                            <tr>
                                
                                <td><c:out value="${aBenefit.requestDate}" /></td>
                                <td><c:out value="${aBenefit.amountPaid}" /></td>
                                <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                <td><c:out value="${aBenefit.assistanceStatus}" /></td>
                            </tr>

                        </c:forEach>
                    </table>        </div>
                
                <c:import url="../Includes/footer.html"/>    
            </main>

            <aside id="assistanceAside">
                <p>This is the Aside element</p>
            </aside>
        </section>
        <%--
            <div class="tableData">
                <h2 class="allh2">Here all the employees we have (<span class="bigFont"> ${allEmployeesList.size()}</span> employees)</h2>
                <table border="1">

                    <tr class="blueColor">
                        <th>Employee ID</th>
                        <th>Full Name</th>
                        <th>Hire Date</th>
                        <th>Employee Type</th>
                        <th>Yearly Cost</th>                        
                        <th>Edit Employee</th>
                    </tr>
                    <c:forEach items="${allEmployeesList}" var ="employee" varStatus="loop">
      <c:set var="compType" scope="session" value="${personToEdit.getCompensationType()}"></c:set> 
        --%>
        <%--
              <c:if test="${loop.index % 2 == 0}">
                  <tr class="yellowColor">
                      <td><c:out value="${employee.getEmployeeID()}" /></td>
                      <td><c:out value="${employee.fullName}"/></td>                        
                      <td><c:out value="${employee.getHireDate()}" /></td> 
                      <td><c:out value="${employee.getCompensationType()}" /></td>    

                                <c:choose>
                                    <c:when test="${fn:containsIgnoreCase(employee.getCompensationType(), 'hourly')}">
        <c:set var="hourlyTot" value="${employee.getRate() * employee.getAvgWeeklyHours() * 50}" />  
        --%>
        <%--
                             <td><c:out value="${employee.getYearlyCompensation()}" /></td> 
                        </c:when>
                             
                         <c:when test="${fn:containsIgnoreCase(employee.getCompensationType(), 'salary')}">
                            
                             <td><c:out value="${employee.getYearlyCompensation()}" /></td> 
                         </c:when>
                             
                         <c:otherwise>
                             <td><c:out value="${employee.getCompensationType()}" /></td> 
                         </c:otherwise>
                     </c:choose>


                                <td>
                                    <form action="EditEmployee" method="POST">
                                        <input type="hidden" name="employeeIdToEdit" value="${employee.getEmployeeID()}">
                                        <input type="hidden" name="action" value="editEmployee">
                                        <input type="submit" value="edit" class="redColor">
                                    </form>
                                </td> 
                            </tr>
                        </c:if>

                        <c:if test="${loop.index % 2 == 1}">
                            <tr class="redColor">
                                <td><c:out value="${employee.getEmployeeID()}" /></td>
                                <td><c:out value="${employee.fullName}"/></td>                         
                                <td><c:out value="${employee.getHireDate()}" /></td> 
                                <td><c:out value="${employee.getCompensationType()}" /></td> 
       <td><c:out value="${employee.compensationType == 'none' ? 'None'  : employee.salary}" /></td> 
        --%>
        <%--
                    <c:choose>
                        <c:when test="${fn:containsIgnoreCase(employee.getCompensationType(), 'hourly')}">
                            <c:set var="hourlyTot" value="${employee.getRate() * employee.getAvgWeeklyHours() * 50}" />
                            <td><c:out value="${employee.formatRoundDollar(hourlyTot)}" /></td> 
                        </c:when>
                
                        <c:when test="${fn:containsIgnoreCase(employee.getCompensationType(), 'salary')}">
                            <c:set var="salaryTot" value="${employee.getSalary()}" />
                            <td><c:out value="${employee.formatRoundDollar(salaryTot)}" /></td> 
                        </c:when>
                
                        <c:otherwise>
                            <td><c:out value="${employee.getCompensationType()}" /></td> 
                        </c:otherwise>
                    </c:choose>

                                <td>
                                    <form action="EditEmployee" method="POST">
                                        <input type="hidden" name="employeeIdToEdit" value="${employee.getEmployeeID()}">
                                        <input type="hidden" name="action" value="editEmployee">
                                        <input type="submit" value="edit" class="yellowColor">
                                    </form>
                                </td> 
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
              
        <div class="goHome">
            <form action="testing" method="POST">
                <input type="hidden" name="action" value="home">
                <input type="submit" value="Go Home">
            </form>
        </div>
        <c:import url="/Includes/Footer.html"/>
        
        --%>



    </body>
</html>