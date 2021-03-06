// Sayel Rammaha    
// 1/13/18

// get "action" from url that was used to reach this page
var thisUrl = window.location.href.split("/");
var page = thisUrl[thisUrl.length - 1];
var action = page.split("=")[1];

var actDivMinW = "750px";

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
    // if account creation attempted
    else if ($("#createMsg").length > 0 || $("#createMsgSuccess").length > 0)
    {        
        setCssForCreateMsg();
    }
    // if account update attempted
    else if ($("#manageType").length > 0)
    {
        setCssForManageMsg();
    }
    else if ($("#vCodeMsg").length > 0)
    {
        setCssForVCodeMsg();
    }
    else if ($("#vCodeMsgSuccess").length > 0)
    {
        setCssForReset();
    }
    else if ($("#resetMsg").length > 0)
    {
        setCssForResetMsg(false);
    }
    else if ($("#resetMsgSuccess").length > 0)
    {
        setCssForResetMsg(true);
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
            $("#mainCaseWorker").empty();
            $("#mainClient").fadeIn(1000); 
            $("#manageMsgDivCL").css("background-color", "white");
        }
        else if (action === "manageCaseWorker")
        {
            setCssForManageAccount();
            $("nav").load("Includes/caseWorkerNav.html");
            $("#mainClient").empty();
            $("#mainCaseWorker").fadeIn(1000);            
            $("#manageMsgDivCW").css("background-color", "white");
        }
        else // first arrival, or from logging out 
        {
            setCssForArrival();
        }   
    }
    
    // --------------------
    // |*** VALIDATION ***|
    // --------------------
    
    // login forms
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
    
    
    // create account form
    $("#createForm").validate({
        rules: {
            createUserName: {
                required: true,
                minlength: 6
            },
            createPassword1: {
                required: true,
                minlength: 6
            },
            createPassword2: {
                required: true,
                minlength: 6, 
                equalTo: "#createPassword1"
            },
            createClientID: {
                required: true,
                digits: true
            },
            createSSN: {
                required: true,
                digits: true,
                rangelength: [9, 9]
            }
        },
        messages: {
            createUserName: {
                minlength: "User Name must be at least 6 characters."
            },
            createPassword1: {
                minlength: "Password must be at least 6 characters."
            },
            createPassword2: {
                minlength: "Password must be at least 6 characters.",
                equalTo: "Passwords must match."
            },
            createClientID: {
                digits: "Client ID# must be numeric"
            },
            createSSN: {
                rangelength: "Please enter a 9-digit social security number."
            }
        }
    });
    
    
    // manage case worker forms
    $("#phoneFormCW").validate({
        rules: {
            fieldValue: {
                required: true, 
                digits: true,
                rangelength: [10, 10]
            },
            managePasswordCW: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter your phone number.", 
                rangelength: "Please enter a 10-digit phone number."
            },
            managePasswordCW: {
                required: "Please enter your password."
            }
        }
    });
    
    $("#emailFormCW").validate({
        rules: {
            fieldValue: {
                required: true, 
                email: true
            },
            managePasswordCW: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter your email address.", 
                email: "Email address invalid."
            },
            managePasswordCW: {
                required: "Please enter your password."
            }
        }
    });
    
    $("#userNameFormCW").validate({
        rules: {
            fieldValue: {
                required: true, 
                minlength: 6
            },
            managePasswordCW: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter a new user name.", 
                minlength: "User name must be at least 6 characters."
            },
            managePasswordCW: {
                required: "Please enter your password."
            }
        }
    });
    
    $("#passwordFormCW").validate({
        rules: {
            fieldValue: {
                required: true, 
                minlength: 6
            },
            fieldValue2: {
                required: true, 
                minlength: 6,
                equalTo: "#newPwCW"
            },
            managePasswordCW: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter a new password.", 
                minlength: "Password must be at least 6 characters."
            },
            fieldValue2: {
                required: "Please confirm your new password.", 
                minlength: "Password must be at least 6 characters.",
                equalTo: "Passwords must match."
            },
            managePasswordCW: {
                required: "Please enter your current password."
            }
        }
    });
    
    
    // manage client forms
    $("#phoneFormCL").validate({
        rules: {
            fieldValue: {
                required: true, 
                digits: true,
                rangelength: [10, 10]
            },
            managePasswordCL: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter your phone number.", 
                rangelength: "Please enter a 10-digit phone number."
            },
            managePasswordCL: {
                required: "Please enter your password."
            }
        }
    });
    
    $("#emailFormCL").validate({
        rules: {
            fieldValue: {
                required: true, 
                email: true
            },
            managePasswordCL: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter your email address.", 
                email: "Email address invalid."
            },
            managePasswordCL: {
                required: "Please enter your password."
            }
        }
    });
    
    $("#streetFormCL").validate({
        rules: {
            fieldValue: {
                required: true
            },
            managePasswordCL: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter your street address."
            },
            managePasswordCL: {
                required: "Please enter your password."
            }
        }
    });
    
    $("#cityFormCL").validate({
        rules: {
            fieldValue: {
                required: true
            },
            managePasswordCL: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter your city of residence."
            },
            managePasswordCL: {
                required: "Please enter your password."
            }
        }
    });
    
    $("#zipFormCL").validate({
        rules: {
            fieldValue: {
                required: true, 
                digits: true,
                rangelength: [5, 5]
            },
            fieldValue2: {
                required: true,
                digits: true,
                rangelength: [4, 4]
            },
            managePasswordCL: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter your zip code.", 
                rangelength: "Please enter a 5-digit zip code."
            },
            fieldValue2: {
                required: "Please enter your zip code extension.", 
                rangelength: "Please enter a 4-digit zip code extension."
            },
            managePasswordCL: {
                required: "Please enter your password."
            }
        }
    });
    
    $("#userNameFormCL").validate({
        rules: {
            fieldValue: {
                required: true, 
                minlength: 6
            },
            managePasswordCL: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter a new user name.", 
                minlength: "User name must be at least 6 characters."
            },
            managePasswordCL: {
                required: "Please enter your password."
            }
        }
    });
    
    $("#passwordFormCL").validate({
        rules: {
            fieldValue: {
                required: true, 
                minlength: 6
            },
            fieldValue2: {
                required: true, 
                minlength: 6,
                equalTo: "#newPwCL"
            },
            managePasswordCL: {
                required: true
            }
        },
        messages: {
            fieldValue: {
                required: "Please enter a new password.", 
                minlength: "Password must be at least 6 characters."
            },
            fieldValue2: {
                required: "Please confirm your new password.", 
                minlength: "Password must be at least 6 characters.",
                equalTo: "Passwords must match."
            },
            managePasswordCL: {
                required: "Please enter your current password."
            }
        }
    });
    
    
    // reset password forms
    $("#vCodeFormCL").validate({
        rules: {
            vCodeUserNameCL: {
                required: true
            },
            vCodeSSNCL: {
                required: true,                
                digits: true,
                rangelength: [9, 9]
            }
        },
        messages: {
            vCodeUserNameCL: {
                required: "Please enter your user name."
            },
            vCodeSSNCL: {
                required: "Please enter your social security number.",               
                rangelength: "Please enter a 9-digit ssn."
            }
        }
    });

    $("#resetFormCL").validate({
        rules: {
            resetCodeCL: {
                required: true
            },
            resetPwCL: {
                required: true,                
                minlength: 6                
            },
            resetPw2CL: {
                required: true,
                equalTo: "#resetPwCL",
                minlength: 6                
            }
        },
        messages: {
            resetCodeCL: {
                required: "Please enter your verification code."
            },
            resetPwCL: {
                required: "Please choose a new password.", 
                minlength: "Password must be at least 6 characters."
            },
            resetPw2CL: {
                required: "Please enter your password.",
                equalTo: "The passwords do not match.",
                minlength: "Password must be at least 6 characters."
            }
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
        $("#resetForm").css("display", "none");
        $("#vCodeForm").css("display", "none");
        // erase any login error messages
        $("#loginMsgCL").hide();
        $("#loginMsgCW").hide();
        
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
        $("#accountDiv").css("min-width", actDivMinW);
        $("footer").css("min-width", "985px");

        // reset button colors on loginSelect
        $("#btnClient").css("background-color", "white");
        $("#btnCaseWorker").css("background-color", "white");
        
        adjustNav();
        adjustFooter();
        
        // unfold login options        
        $("aside").stop(false, true); 
        $("aside").animate({left: "+150px", right: "150", width: "70%"}, 400, "linear", function() { 
            $("aside").css("width", "auto");
            $("#loginSelect").stop(false, true).fadeIn(500);                        
            
            $("#imgClient").show();
            $("#imgCaseWorker").show();
        });       
        
        // clear create messages
        $("#createMsgDiv").css("background-color", "white");
        $("#createMsgDiv").empty();
    }
    else {  // already unfolded, just show select div
        $("#loginSelect").fadeIn(500);
    }    
}

// shows client login div, highlights selected button
function unfoldClLogin() {    
    $("#caseWorkerLogin").stop(false, true);
    $("#caseWorkerLogin").hide();
    $("#resetForm").css("display", "none");
    $("#vCodeForm").css("display", "none");    
    $("#clientLogin").fadeIn(1000);
    $("#btnClient").css("background-color", "#f0e68c");
    $("#btnCaseWorker").css("background-color", "white");    
    
    // erase any login error messages
    $("#loginMsgCW").hide();   
    // clear login messages
    $("#loginMsgDivCW").css("background-color", "white");
    $("#loginMsgDivCW").empty();
}

// shows case worker login div, highlights selected button
function unfoldCwLogin() {
    $("#clientLogin").stop(false, true);
    $("#clientLogin").hide();   
    $("#resetForm").css("display", "none");
    $("#vCodeForm").css("display", "none");
    $("#caseWorkerLogin").fadeIn(1000);  
    $("#btnClient").css("background-color", "white");
    $("#btnCaseWorker").css("background-color", "#f0e68c");
    
    // erase any login error messages
    $("#loginMsgCL").hide();
    // clear login messages
    $("#loginMsgDivCL").css("background-color", "white");
    $("#loginMsgDivCL").empty();
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
        $("nav").css("min-height", "495px");
    });
}

// transition before redirect to rest of site
function animateTransition() {
    // disable manage account link, since already there
    $("#btnManageAccount").click(function(e) {
        e.preventDefault();
    });
    
    $("#loginSelect").css("display", "none");
    $("#caseWorkerLogin").css("display", "none");
    
    $("#clientLogin").empty().show();
    var markup = "<p>Authentication Successful</p>";
    $("#clientLogin").html(markup);
    $("#clientLogin").css("background-color", "green");
    $("#clientLogin").css("color", "white");
    $("#clientLogin").css("font-size", "75px");
    $("#clientLogin").css("font-weight", "bold");
    $("#clientLogin").css("text-align", "center");
    $("#clientLogin").toggleClass("glow");
    $("#clientLogin").fadeOut(1000);
        
    $("#accountDiv").css("min-width", "0px");
    
    $("aside").stop(false, true);
    $("aside").css("min-width", "270px");
    $("aside").delay(1000).animate({width: "270px", left: "15px"}, 400, "linear", function() { 
        if ($("#redirect").html() === "/CaseWorkerController")
            $("nav").load("Includes/caseWorkerNav.html");
        else 
            $("nav").load("Includes/clientNav.html");
        adjustNav();
        adjustFooter();
    });   
}

// shows request code for pw reset form
function showVCodeForm() {
    $("#clientLogin").hide();
    $("#vCodeForm").show();
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
    $("#vCodeForm").css("display", "none");
    $("#resetForm").css("display", "none");
}

// set css for initial arrival with no context
function setCssForArrival() {
    // load default accounts nav
    $("nav").load("Includes/accountsNav.html");

    // change welcome banner for no logged in user
    $("#welcome").css("font-size", "2.2em");
    $("#welcome").css("font-style", "normal");
    $("#accountDiv").css("min-width", actDivMinW);
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

    // hide msg div
    $("#manageMsgDivCW").hide();
    $("#manageMsgDivCL").hide();
    
    // disable manage account link, since already there
    $("#btnManageAccount").click(function(e) {
        e.preventDefault();
    });
}

// set css to keep view the same and display error msg
function setCssForLoginMsg() {    
    $("#accountDiv").css("min-width", actDivMinW);
    $("#loginSelect").show();
    $("#welcome").css("font-size", "2.2em");
    $("#welcome").css("font-style", "normal");
    $("#welcome").html("Welcome!");
    
    if ($("#loginType").html() === "cl")
    {
        $("#clientLogin").fadeIn(1);   
        $("#btnClient").css("background-color", "#f0e68c");
        $("#btnCaseWorker").css("background-color", "white");
        
        $("#loginMsgDivCL").css("background-color", "red");
    }
    else 
    {
        $("#caseWorkerLogin").fadeIn(1);   
        $("#btnClient").css("background-color", "white");
        $("#btnCaseWorker").css("background-color", "#f0e68c");
        
        $("#loginMsgDivCW").css("background-color", "red");
    }    
}

// set css to keep view the same and display error msg
function setCssForCreateMsg() {
    // reset aside
    $("aside").css("width", "270px");
    $("aside").css("left", "15px");

    // load client nav            
    $("nav").fadeIn(1);

    // set footer partial width
    $("footer").css("left", "300px");

    // show create panel
    $("#mainCreate").show();
    
    if ($("#createMsg").length > 0)
    {
        $("#createMsgDiv").css("background-color", "red");
    }
    else 
    {
        $("#createMsgDiv").css("background-color", "green");
    }
    
    // disable create account link, since already there
    $("#btnCreateAccount").click(function(e) {
        e.preventDefault();
    });
}

// set css to keep view the same and display error msg
function setCssForManageMsg() {
    setCssForManageAccount();
    // unhide msg div
    $("#manageMsgDivCW").show();
    $("#manageMsgDivCL").show();
    
    if ($("#manageType").html() === "cl")
    {
        $("nav").load("Includes/clientNav.html");
        $("#mainClient").fadeIn(1);   
    }
    else 
    {
        $("nav").load("Includes/caseWorkerNav.html");
        $("#mainCaseWorker").fadeIn(1);   
    }
    
    if ($("#manageMsg").length > 0)
    {
        $("#manageMsgDivCW").css("background-color", "red");
        $("#manageMsgDivCL").css("background-color", "red");
    }
    else 
    {
        $("#manageMsgDivCW").css("background-color", "green");
        $("#manageMsgDivCL").css("background-color", "green");
    }
}

// set css to keep view the same and display error msg
function setCssForVCodeMsg() {
    $("#accountDiv").css("min-width", actDivMinW);
    $("#loginSelect").show();
    $("#welcome").css("font-size", "2.2em");
    $("#welcome").css("font-style", "normal");
    $("#welcome").html("Welcome!");
    
    $("#vCodeForm").fadeIn(1);   
    $("#btnClient").css("background-color", "#f0e68c");
    $("#btnCaseWorker").css("background-color", "white");
    $("#vCodeMsgDivCL").show();
    $("#vCodeMsgDivCL").css("background-color", "red");
}

// set css to keep view the same and display error msg
function setCssForReset() {
    $("#accountDiv").css("min-width", actDivMinW);
    $("#loginSelect").show();
    $("#welcome").css("font-size", "2.2em");
    $("#welcome").css("font-style", "normal");
    $("#welcome").html("Welcome!");
    
    $("#resetForm").fadeIn(1);   
    $("#btnClient").css("background-color", "#f0e68c");
    $("#btnCaseWorker").css("background-color", "white");    
}

// set css to keep view the same and display error msg
function setCssForResetMsg(successful) {
    $("#accountDiv").css("min-width", actDivMinW);
    $("#loginSelect").show();
    $("#welcome").css("font-size", "2.2em");
    $("#welcome").css("font-style", "normal");
    $("#welcome").html("Welcome!");
    
    $("#resetForm").fadeIn(1);   
    $("#btnClient").css("background-color", "#f0e68c");
    $("#btnCaseWorker").css("background-color", "white");
    $("#resetMsgDivCL").show();
    
    if (successful)
    {
        $("#resetMsgDivCL").css("background-color", "green");
        $("#btnResetPW").hide();              
    }
    else 
    {
        $("#resetMsgDivCL").css("background-color", "red");
    }
}