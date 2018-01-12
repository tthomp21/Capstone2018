<%-- 
    Document   : index
    Created on : Jan 11, 2018, 11:26:00 PM
    Author     : sayel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:import url="Includes/Header.html"/>
    </head>
    
    <body class="w3-light-grey">

        <!-- Top container -->
        <c:import url="Includes/TitleBar.html"/>

        <!-- Sidebar/menu -->
        <c:import url="Includes/Sidebar.html"/>

        <!-- Overlay effect when opening sidebar on small screens -->
        <div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

        <!-- !PAGE CONTENT! -->
        <div class="w3-main" style="margin-left:300px;margin-top:43px;">

          <!-- Header -->
          <header class="w3-container" style="padding-top:22px">
            <h5><b><i class="fa fa-dashboard"></i> Dashboard</b></h5>
          </header>
          <main>
          <div class="w3-row-padding w3-margin-bottom">
              <p>Some general page info could go here.  The sidebar can hold all the links we may need.  The page scales down well for mobile.  The link images could probably change to fit our needs better.  
                  We could also add colors if we wanted a more colorful site.  If someone needs more seconds on the main page they are easy to add.  Only problem is that this is not our css and have little to no control over it unless we rewrite our own.</p>
          </div>

          <div class="w3-panel">
            <div class="w3-row-padding" style="margin:0 -16px">
              <div class="w3-third">
                <h5>Main section #1</h5>
                <p>This could hold some information.</p>
              </div>
              <div class="w3-twothird">
                <h5>Main section #2</h5>
                <p>This could hold some information or may not be needed.</p>
              </div>
            </div>
          </div>
          <hr>
          <div class="w3-container">
            <h5>Sub section #1</h5>
            <p>This section may also not be needed.</p>
          </div>
          <hr>
          </main>

          <!-- Footer -->
          <c:import url="Includes/Footer.html"/>

          <!-- End page content -->
        </div>

        <script>
            // Get the Sidebar
            var mySidebar = document.getElementById("mySidebar");

            // Get the DIV with overlay effect
            var overlayBg = document.getElementById("myOverlay");
                       
            // Toggle between showing and hiding the sidebar, and add overlay effect
            function open() {
                if (mySidebar.style.display === 'block') {
                    mySidebar.style.display = 'none';
                    overlayBg.style.display = "none";
                } else {
                    mySidebar.style.display = 'block';
                    overlayBg.style.display = "block";
                }
                
            }

            // Close the sidebar with the close button
            function close() {
                mySidebar.style.display = "none";
                overlayBg.style.display = "none";
            }
        </script>
    </body>
</html>
