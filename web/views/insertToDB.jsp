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
        <div>
            <button onclick="showDiv('clients')">Clients</button>
            <button onclick="showDiv('institution')">Institution</button>
            <button onclick="showDiv('caseworker')">Case Worker</button>
        </div>
        <div id="clients">
            <p>clients</p>
        </div>
        <div id="institution">
            <p>institution</p>
        </div>
        <div id="caseworker">
            <p>caseworker</p>
        </div>
    </body>
    <script>
        function showDiv(name)
{
    if(name == 'clients'){
        document.getElementById('clients').style.display = 'block';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'none';
    }
    else if(name == 'institution'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'block';
        document.getElementById('caseworker').style.display = 'none';
    }
    else if(name == 'caseworker'){
        document.getElementById('clients').style.display = 'none';
        document.getElementById('institution').style.display = 'none';
        document.getElementById('caseworker').style.display = 'block';
    }
}
    </script>
</html>
