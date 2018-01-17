// Sayel Rammaha    
// 1/13/18

// get "action" from url that was used to reach this page
var thisUrl = window.location.href.split("/");
var page = thisUrl[thisUrl.length - 1];
var action = page.split("=")[1];


// ------------------------------------
// |*** DOM SETUP ON DOCUMENT LOAD ***|
// ------------------------------------

$(document).ready(function() {      
    // set footer to full width
    $("footer").css("left", "15px");
    
    // hide divs until expanded
    hideDivs();
    
    // *** check for contextual events ***
    // presence of redirect script, db error messages, 
    //    redirect actions from other controllers
    
    // if redirect script active, animate transition first 
    if ($("#rScript").length > 0)
    {
        animateTransition();
    } 
    // if error logging in
    else if ($("#loginType").length > 0)
    {        
        setCssForLoginMsg();
    }
    // action checks
    else 
    {
        // load correct nav, show correct divs, 
        // set proper css for each action/view type
        if (action === "manageClient")  
        {
            setCssForManageAccount();
            $("nav").load("Includes/clientNav.html");            
            $("#mainClient").fadeIn(1000);
        }
        else if (action === "manageCaseWorker")
        {
            setCssForManageAccount();
            $("nav").load("Includes/caseWorkerNav.html");
            $("#mainCaseWorker").fadeIn(1000);
        }
        else // first arrival, or from logging out 
        {
            setCssForArrival();
        }   
    }
    
    // --------------------
    // |*** VALIDATION ***|
    // --------------------
    
    $("#loginFormCW").validate({
        rules: {
            loginUserNameCW: {
                required: true
            },
            loginPasswordCW: {
                required: true
            }
        },
        messages: {
            loginUserNameCW: {
                required: "Please enter your user name."
            },
            loginPasswordCW: {
                required: "Please enter your password."
            }
        }, 
        submitHandler: function() {
            (this).submit();
            //return false;
        }
    });
    
    $("#loginFormCL").validate({
        rules: {
            loginUserNameCL: {
                required: true
            },
            loginPasswordCL: {
                required: true
            }
        },
        messages: {
            loginUserNameCL: {
                required: "Please enter your user name."
            },
            loginPasswordCL: {
                required: "Please enter your password."
            }
        }, 
        submitHandler: function() {
            (this).submit();
            //return false;
        }
    });
});


// ---------------------
// |*** TRANSITIONS ***|
// ---------------------

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

        // reset button colors on loginSelect
        $("btnClient").css("background-color", "white");
        $("btnCaseWorker").css("background-color", "white");
        
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
    else {  // already unfolded, just show select div
        $("#loginSelect").fadeIn(500);
    }    
}

// shows client login div, highlights selected button
function unfoldClLogin() {
    $("#caseWorkerLogin").stop(false, true);
    $("#caseWorkerLogin").hide();            
    $("#clientLogin").fadeIn(1000);   
    $("#btnClient").css("background-color", "#f0e68c");
    $("#btnCaseWorker").css("background-color", "white");
}

// shows case worker login div, highlights selected button
function unfoldCwLogin() {
    $("#clientLogin").stop(false, true);
    $("#clientLogin").hide();          
    $("#caseWorkerLogin").fadeIn(1000);  
    $("#btnClient").css("background-color", "white");
    $("#btnCaseWorker").css("background-color", "#f0e68c");
}

// animates footer adjustment between full or partial width
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

// animates nav fold up/down
function adjustNav() {
    $("nav").stop(false, true); 
    $("nav").css("min-height", "0px");        
    $("nav").slideToggle(1000, function() {
        $("nav").css("min-height", "265px");
    });
}

// transition before redirect to rest of site
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


// --------------------------------------------
// |*** CSS ADJUSTMENTS FOR SPECIFIC VIEWS ***|
// --------------------------------------------

// hide unopened divs for arrival 
function hideDivs() {
    $("#mainCreate").css("display", "none");
    $("#mainClient").css("display", "none");
    $("#mainCaseWorker").css("display", "none");
    $("#loginSelect").css("display", "none");    
    $("#clientLogin").css("display", "none");
    $("#caseWorkerLogin").css("display", "none");   
    $("nav").css("display", "none");
}

// set css for initial arrival with no context
function setCssForArrival() {
    // load default accounts nav
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

// sets css for account management
function setCssForManageAccount() {
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

// set css to keep view the same and display error msg
function setCssForLoginMsg() {    
    $("#accountDiv").css("min-width", "750px");
    $("#loginSelect").show();

    if ($("#loginType").html() === "cl")
    {
        $("#clientLogin").fadeIn(1);   
        $("#btnClient").css("background-color", "#f0e68c");
        $("#btnCaseWorker").css("background-color", "white");
    }
    else 
    {
        $("#caseWorkerLogin").fadeIn(1);   
        $("#btnClient").css("background-color", "white");
        $("#btnCaseWorker").css("background-color", "#f0e68c");
    }
}

