<%-- 
    Document   : insertToDB
    Created on : Jan 31, 2018, 3:39:33 PM
    Author     : tt283071
--%>

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
        <title>db insert</title>
    </head>
    <body>
        <h1>Insert into the DB!</h1>
        <h3><b><u>All these inputs assume you have entered valid data!!!!!<br>There is zero error checking on the fields!!!</u></b></h3>
        <p>All dates are to be in the format of yyyy-MM-dd.
        <div>
            <button onclick="showDiv('clients')">Clients</button>
            <button onclick="showDiv('institution')">Institution</button>
            <button onclick="showDiv('caseworker')">Case Worker</button>
            <button onclick="showDiv('hours')">Hours</button>
            <button onclick="showDiv('sanctions')">Sanctions</button><!-- -->
            <button onclick="showDiv('clientinstitution')">Client - Institution</button><br><br>
            <button onclick="showDiv('aidtypes')">Aid Types</button>
            <button onclick="showDiv('assistance')">Assistance</button>
            <button onclick="showDiv('employer')">Employer</button>
            <button onclick="showDiv('clientemployer')">Client - Employer</button>
            <button onclick="showDiv('clientaid')">Client - Aid</button>
            <button onclick="showDiv('requestassist')">Request - Assist</button>
        </div>
        <div id="clients">
            <p>Clients</p>
            <p>
                ClientID(int) <input type="text" name="clientID"><br>
                SSN(char 9) <input type="text" name="clientSSN"><br>
                FirstName(varchar 30) <input type="text" name="clientFName"><br>
                MiddleInit(char 1(null)) <input type="text" name="clientMid"><br>
                LastName(varchar 30) <input type="text" name="clientLName"><br>
                City(varchar 25) <input type="text" name="clientCity"><br>
                Street(varchar 50) <input type="text" name="clientStreet"><br>
                State(char 2) <input type="text" name="clientState"><br>
                Emzip(char 6) <input type="text" name="clientEmZip"><br>
                ExtZip(char 4) <input type="text" name="clientExtZip"><br>
                Phone(char 10) <input type="text" name="clientPhone"><br>
                Email(varchar 50) <input type="text" name="clientEmail"><br>
                Birthdate(date) <input type="text" name="clientBDay"><br>
                MarriageStatus(smallInt(1 or 0) <input type="text" name="clientMarried"><br>
                Enrollmentdate(date) <input type="text" name="clientEnrollDate"><br>
                Dependents(smallInt) <input type="text" name="clientDependents"><br>
                PartnerID(int(null)) <input type="text" name="clientPartner"><br>
                CaseWorkerID(int) <input type="text" name="clientCaseWorker"><br>
                UserName(varchar 20) <input type="text" name="clientUserName"><br>
                Password(varchar 20) <input type="text" name="clientPass"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="clientDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="institution" style="display:none;">
            <p>Institution</p>
            <p>
                InstitutionID(int) <input type="text" name="institutionID"><br>
                Name(varchar 50) <input type="text" name="institutionName"><br>
                Description(varchar 50) <input type="text" name="institutionDesc"><br>
                City(varchar 25) <input type="text" name="institutionCity"><br>
                Street(varchar 50) <input type="text" name="institutionStreet"><br>
                State(char 2) <input type="text" name="institutionState"><br>
                Zip(char 5) <input type="text" name="institutionZip"><br>
                ExtZip(char 4) <input type="text" name="institutionExtZip"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="institutionDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="caseworker" style="display:none;">
            <p>Caseworker</p>
            <p>
                CaseWorkerID(int) <input type="text" name="caseworkerID"><br>
                FirstName(varchar 30) <input type="text" name="caseworkerFName"><br>
                LastName(varchar 30) <input type="text" name="caseworkerLName"><br>
                Phone(char 10) <input type="text" name="caseworkerPhone"><br>
                Email(varchar 50) <input type="text" name="caseworkerEmail"><br>
                Office(char 3) <input type="text" name="caseworkerOffice"><br>
                UserName(varchar 20) <input type="text" name="caseworkerUserName"><br>
                Password(varchar 20) <input type="text" name="caseworkerPassword"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="caseworkerDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="hours" style="display:none;">
            <p>Hours</p>
            <p>
                ClientID(int) <input type="text" name="hoursID"><br>
                Date(date) <input type="text" name="hoursDate"><br>
                Hours(decimal(5,2)) <input type="text" name="hourshours"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="hoursDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="sanctions" style="display:none;">
            <p>Sanctions</p>
            <p>
                SanctionID(int) <input type="text" name="sanctionID"><br>
                ClientID(int) <input type="text" name="sanctionclientID"><br>
                SanctionDate(date) <input type="text" name="sanctionDate"><br>
                SanctionLength(smallint) <input type="text" name="sanctionLength"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="sanctionDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="clientinstitution" style="display:none;">
            <p>clientinstitution</p>
            <p>
                ClientID(int) <input type="text" name="CIClientID"><br>
                InstitutionID(int) <input type="text" name="CIInstiID"><br>
                ProgramLength(smallint) <input type="text" name="CIProgLength"><br>
                ProgramProgress(smallint) <input type="text" name="CIProgProg"><br>
                ContactFName(varchar 30) <input type="text" name="CIContactF"><br>
                ContactLName(varchar 30) <input type="text" name="CIContactL"><br>
                ContactPhone(char 10) <input type="text" name="CIContactP"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="clientInstDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="aidtypes" style="display:none;">
            <p>Aid types</p>
            <p>
                AidType(int) <input type="text" name="aidType"><br>
                AidName(char 4) <input type="text" name="aidName"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="aidDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="assistance" style="display:none;">
            <p>assistance</p>
            <p>
                AssistanceID(int) <input type="text" name="assID"><br>
                Description(varchar 50) <input type="text" name="assDesc"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="assDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="employer" style="display:none;">
            <p>employer</p>
            <p>
                EmployerID(int) <input type="text" name="empID"><br>
                Name(varchar 50) <input type="text" name="empName"><br>
                City(varchar 25) <input type="text" name="empCity"><br>
                Street(varchar 50) <input type="text" name="empStreet"><br>
                State(char 2) <input type="text" name="empState"><br>
                Zip(char 5) <input type="text" name="empZip"><br>
                ExtZip(char 4) <input type="text" name="empExtzip"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="empDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="clientemployer" style="display:none;">
            <p>client employer</p>
            <p>
                EmployerID(int) <input type="text" name="CEEmpId"><br>
                ClientID(int) <input type="text" name="CEClientID"><br>
                Income(decimal(7,2)) <input type="text" name="CEIncome"><br>
                ContactFName(varchar 30) <input type="text" name="CEContactFName"><br>
                ContactLName(varchar 30) <input type="text" name="CEContactLName"><br>
                ContactPhone(char 10) <input type="text" name="CEPhone"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="CEDB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="clientaid" style="display:none;">
            <p>client aid</p>
            <p>
                ClientID(int) <input type="text" name="CAClientID"><br>
                DateDisbursed(date) <input type="text" name="CADate"><br>
                AidType(int) <input type="text" name="CAAidType"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="CADB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
        <div id="requestassist" style="display:none;">
            <p>request assist</p>
            <p>
                RequestID(int) <input type="text" name="RAID"><br>
                AssistanceID(int) <input type="text" name="RAAssID"><br>
                ClientID(int) <input type="text" name="RAClientID"><br>
                DateRequest(date) <input type="text" name="RADateRequest"><br>
                Status(smallint) <input type="text" name="RAStatus"><br>
                DateDisbursed(date) <input type="text" name="RADate"><br>
                Amount(decimal(7,2)) <input type="text" name="RAAmount"><br>
                <br>
                <form action="CaseWorkerController" method="post">
                    <input type="hidden" name="action" value="RADB">
                    <input type="submit" value="Submit">
                </form>
            </p>
        </div>
    </body>
    <script>
        function showDiv(name)
{
    if(name == 'clients'){
        document.getElementById('clients').style.display = 'block';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
        
    }
    else if(name == 'institution'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'block';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'caseworker'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'block';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'hours'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'block';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'sanctions'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'block';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'clientinstitution'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'block';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'aidtypes'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'block';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'assistance'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'block';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'employer'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'block';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'clientemployer'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'block';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'clientaid'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'block';
        document.getElementById('requestassist').style.display = 'none';
    }
    else if(name == 'requestassist'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'block';
    }
    else if(name == 'sanctions'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
        document.getElementById('hours').style.display = 'none';
        document.getElementById('sanctions').style.display = 'none';
        document.getElementById('clientinstitution').style.display = 'none';
        document.getElementById('aidtypes').style.display = 'none';
        document.getElementById('assistance').style.display = 'none';
        document.getElementById('employer').style.display = 'none';
        document.getElementById('clientemployer').style.display = 'none';
        document.getElementById('clientaid').style.display = 'none';
        document.getElementById('requestassist').style.display = 'none';
    }
}
    </script>
</html>
