
$(document).ready(function() { 
    $("#closeAbout").on("click", hideAboutUs);    
    $("#closeContact").on("click", hideContactUs);    
});

$(document).click(function (e) {
   if ($(e.target).is("#aboutUs")) {
       hideAboutUs();
   }
   if ($(e.target).is("#contactUs")) {
       hideContactUs();
   }
});

function showAboutUs() {
    $("#aboutUs").css("display", "block");
}

function hideAboutUs() {
    $("#aboutUs").css("display", "none");
}

function showContactUs() {
    $("#contactUs").css("display", "block");
}

function hideContactUs() {
    $("#contactUs").css("display", "none");
}