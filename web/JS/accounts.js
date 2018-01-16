// Sayel Rammaha    
// 1/13/18

// get "action" from url that was used to reach this page
var thisUrl = window.location.href.split("/");
var page = thisUrl[thisUrl.length - 1];
var action = page.split("=")[1];


$(document).ready(function() {      
    // set footer to full width
    $("footer").css("left", "15px");
    
    // hide main divs
    $("#mainCreate").css("display", "none");
    $("#mainClient").css("display", "none");
    $("#mainCaseWorker").css("display", "none");
    $("#loginSelect").css("display", "none");    
    $("#clientLogin").css("display", "none");
    $("#caseWorkerLogin").css("display", "none");   
    $("nav").css("display", "none");
    
    // if redirect script active, animate transition first
    if ($("#rScript").length > 0)
    {
        animateTransition();
    } 
    else // no redirect script
    {
        // load correct nav, show correct divs for each action
        if (action === "manageClient")
        {
            manageAccount();
            $("nav").load("Includes/clientNav.html");            
            $("#mainClient").fadeIn(1000);
        }
        else if (action === "manageCaseWorker")
        {
            manageAccount();
            $("nav").load("Includes/caseWorkerNav.html");
            $("#mainCaseWorker").fadeIn(1000);
        }
        else // first arrival, or from logging out 
        {
            // load accounts nav
            $("nav").load("Includes/accountsNav.html");

            // change welcome banner for no logged in user
            $("#welcome").css("font-size", "2.2em");
            $("#welcome").css("font-style", "normal");
            $("#accountDiv").css("min-width", "750px");
            $("#accountDiv").hide();

            $("#welcome").empty(); 
            $("#accountDiv").fadeIn(500, function() {
                $("#welcome").html("Welcome!");
            });
            
            // resizing minimum
            $("footer").css("min-width", "985px");
        }   
    }
});


// show create account main div
function unfoldCreate() { 
    // if login needs to be folded left
    if ($("aside").css("left") === "150px")
    {
        // hide divs        
        $("#loginSelect").css("display", "none");
        $("#clientLogin").css("display", "none");
        $("#caseWorkerLogin").css("display", "none");
        
        // remove resizing minimum before folding left
        $("#accountDiv").css("min-width", "0px");

        // fold in aside left
        $("aside").stop(false, true);
        $("aside").css("min-width", "270px");
        $("aside").animate({width: "270px", left: "15px"}, 400, "linear", function() {        
            // show create account div
            $("#mainCreate").stop(false, true);
            $("#mainCreate").fadeIn(1000);
            adjustNav();
            adjustFooter();
            $("footer").css("min-width", "700px");
        });
    }
    else 
    {
        $("#mainCreate").stop(false, true);
        $("#mainCreate").fadeIn(1000);
    }
}

// show login main div
function unfoldLoginSelect() {  
    // if login needs to be unfolded
    if ($("aside").css("left") === "15px")
    {
        // hide divs
        $("#mainCreate").css("display", "none");   
        $("#clientLogin").css("display", "none");
        $("#caseWorkerLogin").css("display", "none");
        
        // resizing minimums
        $("#accountDiv").css("min-width", "750px");
        $("footer").css("min-width", "985px");

        adjustNav();
        adjustFooter();

        // unfold login options
        $("aside").stop(false, true); 
        $("aside").animate({left: "+150px", right: "150", width: "70%"}, 400, "linear", function() { 
            $("aside").css("width", "auto");
            $("#loginSelect").stop(false, true); 
            $("#loginSelect").fadeIn(500);    
        });       
    }
    else {
        $("#loginSelect").fadeIn(500);
    }    
}

// shows client login div
function unfoldClLogin() {
    $("#caseWorkerLogin").hide();            
    $("#clientLogin").fadeIn(1000);   
    $("#btnClient").css("background-color", "#f0e68c");
    $("#btnCaseWorker").css("background-color", "white");
}

// shows case worker login div
function unfoldCwLogin() {
    $("#clientLogin").hide();          
    $("#caseWorkerLogin").fadeIn(1000);  
    $("#btnClient").css("background-color", "white");
    $("#btnCaseWorker").css("background-color", "#f0e68c");
}

// animates footer adjustment
function adjustFooter() {
    $("footer").stop(false, true);
    if ($("footer").css("left") === "15px")
    {
        $("footer").animate({
            left: "+=285"
        }, 750, "linear");
    }
    else if ($("footer").css("left") === "300px")
    {
        $("footer").animate({
            left: "-=285"
        }, 750, "linear");
    }
}

// animates nav slide up/down
function adjustNav() {
    $("nav").stop(false, true); 
    $("nav").css("min-height", "0px");        
    $("nav").slideToggle(1000, function() {
        $("nav").css("min-height", "265px");
    });
}

// sets layout for account management
function manageAccount() {
    // arrived from logged in client page, clicked on manage account
    // reset aside
    $("aside").css("width", "270px");
    $("aside").css("left", "15px");

    // load client nav            
    $("nav").fadeIn(1);

    // set footer partial width
    $("footer").css("left", "300px");

    // disable manage account link, since already there
    $("#btnManageAccount").click(function(e) {
        e.preventDefault();
    });
}

// transition to rest of site
function animateTransition() {
    // disable manage account link, since already there
    $("#btnManageAccount").click(function(e) {
        e.preventDefault();
    });

    $("#loginSelect").css("display", "none");
    $("#clientLogin").css("display", "none");
    $("#caseWorkerLogin").css("display", "none");
    $("#accountDiv").css("min-width", "0px");

    $("aside").stop(false, true);
    $("aside").css("min-width", "270px");
    $("aside").animate({width: "270px", left: "15px"}, 400, "linear", function() { 
        adjustNav();
        adjustFooter();
    });
}