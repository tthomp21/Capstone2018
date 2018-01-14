$(document).ready(function() {      
   $("#main").css("display", "none");
   $("#main2").css("display", "none");
   $("nav").css("display", "none");
   $("footer").css("left", "15px");
});

function unfoldCreate() {    
    $("#main2").css("display", "none");
    
    $("#main").fadeIn(1000);
    
    $("nav").slideDown(2000);
    if ($("footer").css("left") === "15px")
    {
        $("footer").animate({
            left: "+=285"
        }, 1500, "linear");
    }
}
function unfoldLogin() {    
    $("#main").css("display", "none");
    
    $("#main2").fadeIn(1000);
    
    $("nav").slideDown(2000);
    if ($("footer").css("left") === "15px")
    {
        $("footer").animate({
            left: "+=285"
        }, 1500, "linear");
    }    
    
}

