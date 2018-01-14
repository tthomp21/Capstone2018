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
    $("#mainLogin").css("display", "none");
    $("#mainClient").css("display", "none");
    $("#mainCaseWorker").css("display", "none");
    
    // load correct nav, show correct main div
    if (action === "manageClient")
    {
        // arrived from logged in client page, clicked on manage account
        
        // load client nav
        $("nav").load("views/pageImports/clientNav.html");
        
        // set footer partial width
        $("footer").css("left", "300px");
        
        // disable manage account link, since already there
        $("#btnManageAccount").click(function(e) {
            e.preventDefault();
        });
        
        // show manage client main div
        unfoldManageClient();
    }
    else if (action === "manageCaseWorker")
    {
        // arrived from logged in case worker page, clicked on manage account
        
        // load case worker nav
        $("nav").load("views/pageImports/caseWorkerNav.html");
        // set footer partial width
        $("footer").css("left", "300px");
        
        // disable manage account link, since already there
        $("#btnManageAccount").click(function(e) {
            e.preventDefault();
        });
        
        // show manage case worker main div
        unfoldManageCaseWorker();
    }
    else 
    {
        // first arrival, or from logging out 
                
        // load accounts nav
        $("nav").load("views/pageImports/accountsNav.html");
        
        // hide main div
        $("#mainCreate").css("display", "none");
        
        // hide nav until something to show
        $("nav").css("display", "none");
        
        // change welcome banner for no logged in user
        $("#welcome").css("font-size", "2.2em");
        $("#welcome").css("font-style", "normal");
        $("#accountDiv").hide();
        
        $("#welcome").empty(); 
        $("#accountDiv").fadeIn(1200, function() {
            $("#welcome").html("Welcome!");
        });
        
    }   
});

// show create account main div
function unfoldCreate() {    
    $("aside").animate({width: "270px", left: "15px"}, 400, "linear", function() {
        $("#mainLogin").css("display", "none");
        $("#mainClient").css("display", "none");
        $("#mainCaseWorker").css("display", "none");
        $("#mainCreate").fadeIn(1000);

        $("nav").slideDown(2000);
        if ($("footer").css("left") === "15px")
        {
            $("footer").animate({
                left: "+=285"
            }, 1500, "linear");
        }
        
        $("#btnCreateAccount").click(function(e) {
            e.preventDefault();
        });
    });    
}

// show login main div
function unfoldLogin() {    
    $("aside").animate({width: "270px", left: "15px"}, 400, "linear", function() { 
        $("#mainCreate").css("display", "none");    
        $("#mainClient").css("display", "none");
        $("#mainCaseWorker").css("display", "none");

        $("#mainLogin").fadeIn(1000);

        $("nav").slideDown(2000);
        if ($("footer").css("left") === "15px")
        {
            $("footer").animate({
                left: "+=285"
            }, 1500, "linear");
        }   
        
        $("#btnLogin").click(function(e) {
            e.preventDefault();
        });
    });
}

// show manage client main div
function unfoldManageClient() {    
    $("aside").css("width", "270");
    $("aside").css("left", "15px");
    
    $("#mainCreate").css("display", "none");
    $("#mainLogin").css("display", "none");    
    $("#mainCaseWorker").css("display", "none");
    
    $("#mainClient").fadeIn(1000);
    
    $("nav").slideDown(2000);
    if ($("footer").css("left") === "15px")
    {
        $("footer").animate({
            left: "+=285"
        }, 1500, "linear");
    }    
}

// show manage case worker main div
function unfoldManageCaseWorker() {    
    $("aside").css("width", "270");
    $("aside").css("left", "15px");
    
    $("#mainCreate").css("display", "none");
    $("#mainLogin").css("display", "none");
    $("#mainClient").css("display", "none");
    
    $("#mainCaseWorker").fadeIn(1000);
    
    $("nav").slideDown(2000);
    if ($("footer").css("left") === "15px")
    {
        $("footer").animate({
            left: "+=285"
        }, 1500, "linear");
    }    
}

