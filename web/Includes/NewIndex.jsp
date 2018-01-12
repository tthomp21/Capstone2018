
<%-- the action pages will contain only the body otherwise, the common lines will go to the other files. --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>  
<c:import url="Includes/newHeader.html" />
<!-- Top container -->
<div class="w3-bar w3-top w3-khaki w3-large" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="open();"><i class="fa fa-bars"></i>  Menu</button>
  <span class="w3-bar-item w3-right"><img src="Images/logoTransparent.png" width="82" height="52"/></span>
</div>
<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
  <div class="w3-container w3-row">
    <div class="w3-col s4">
        <p>An profile image could go here instead of the p tag</p>
    </div>
    <div class="w3-col s8 w3-bar">
      <span>Welcome, <strong>Name</strong></span><br>
    </div>
  </div>
  <hr>
  <div class="w3-container">
    <h5>Dashboard</h5>
  </div>
  <div class="w3-bar-block">
    <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>  Close Menu</a>
    <a href="#" class="w3-bar-item w3-button w3-padding w3-blue"><i class="fa fa-users fa-fw"></i>  Overview</a>
    <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-eye fa-fw"></i>  Link1</a>
    <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-users fa-fw"></i>  Link2</a>
    <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bullseye fa-fw"></i>  Link3</a>
    <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-diamond fa-fw"></i>  Link4</a>
    <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bell fa-fw"></i>  Link5</a>
    <a href="#" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bank fa-fw"></i>  Link6</a><br><br>
  </div>
</nav>


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
  <footer class="w3-container w3-padding-16 w3-light-grey">
    <h4>FOOTER</h4>
  </footer>

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

<c:import url="/Includes/Footer.html" />