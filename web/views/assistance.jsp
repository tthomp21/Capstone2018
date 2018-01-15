<%-- 
    Document   : assistance
    Created on : Jan 10, 2018, 2:55:01 PM
    Author     : Murad Smoqy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                        <%--<c:set var="compType" scope="session" value="${personToEdit.getCompensationType()}"></c:set> --%>
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
                                        <%-- <c:set var="hourlyTot" value="${employee.getRate() * employee.getAvgWeeklyHours() * 50}" />  --%>
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
                                <%-- <td><c:out value="${employee.compensationType == 'none' ? 'None'  : employee.salary}" /></td>  --%>
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
