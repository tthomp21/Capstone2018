/**
$(document).ready(function() {
	$("#main h2").addClass("close");
	$("#main div").first().show();
	$("#main h2").click(
		function() {
			$(this).addClass("open");
                        $("h2").not($(this)).next("div")
		    //$(this).next().slideToggle(1000);

			
			$("h2").not($(this)).removeClass("minus");
			$("h2").not($(this)).next().slideUp(1000);
			
			
			$(this).addClass("minus");
			$(this).next().slideDown(1000);
			
		}
    ); // end click
    
 //alert("table width" + $("#assistanceAside").css("width"));
    // alert("here is the with of the body: " + $("body").css("width")); .css("width") attaches the units too like 200px, .width() only gives number like 200.
}); // end ready
*/


  $(document).ready(function() {
	$("#main h2").first().addClass("open");
	$("#main h2").not($(this)).addClass("close");
        
	$("#main div").first().show();
	$("#main h2").click(
		function() {
                    
                    if($(this).hasClass("open")){
                        $(this).toggleClass("close");
                       $("h2").not($(this)).next().hide();
                        
                    }
                    else if($(this).hasClass("close")){
                       $(this).toggleClass("open"); 
                        $(this).next().show();
                       //$(this).toggleClass("open");
                    }
//			$(this).toggleClass("open");
//                        $(this).next().show();
//
//			
//			$("h2").not($(this)).addClass("close");
			//$("h2").not($(this)).next().hide();
			
			
			
			
		}
    ); // end click
}); // end ready
