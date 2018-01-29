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
        <!-- <script type="text/javascript" src="/JS/assistance.js"></script>-->
<!--        <link href="../CSS/assistance.css" rel="stylesheet" type="text/css"/>-->
        
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
                
                <section id="primarySection">
                    <h1 class="sectionTitle">Primary Assistances</h1>
                    <h2>Food Stamp or SNAP</h2>
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
                    <table border="1">
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
                                <td><c:out value="${aBenefit.status}" /></td>
                            </tr>

                        </c:forEach>
                    </table>
                </div>
                    
                    <h2>Medicaid Benefits</h2>
                <div>
                    <table border="1">
                        <tr>
                            
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
                            <th>Assistance Status</th>
                        </tr>
                        <c:forEach items="${mediaaidList}" var="aBenefit" varStatus="loop">

                            <tr>
                               
                                <td><c:out value="${aBenefit.requestDate}" /></td>
                                <td><c:out value="${aBenefit.amountPaid}" /></td>
                                <td><c:out value="${aBenefit.dateDisbursed}" /></td>
                                <td><c:out value="${aBenefit.status}" /></td>
                            </tr>

                        </c:forEach>
                    </table>
                </div>
                </section>
                
                <section id="secondarySection">
                    <h1 class="sectionTitle">Secondary Assistances</h1>
                <h2>Car Repairs</h2>
                <div>
                    <table border="1">
                        <tr>
                            
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
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

                <h2>Clothing Benefits</h2>
                <div>
                 <table border="1">
                        <tr>
                          
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
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

                <h2>Vehicle Register</h2>
                <div>
                  <table border="1">
                        <tr>
                            
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
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

                <h2>Gas Fuel</h2>
                <div>
                    <table border="1">
                        <tr>
                        
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
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
                
                <h2>Tuitions</h2>
                <div>
                   <table border="1">
                        <tr>
                        
                            <th>Date requested</th>
                            <th>Amount Paid</th>
                            <th>Date Disbursed</th>
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
             
        </section>
                
                
                
            </div>
            
            <c:import url="../Includes/footer.html"/>            
        </div>
    </body>
</html>
