
$(document).ready(function() {
	$("#assistanceMain h2").first().addClass("minus");
	$("#assistanceMain div").first().show();
	$("#assistanceMain h2").click(
		function() {
			$(this).toggleClass("minus");
		    $(this).next().slideToggle(1000);

			
			$("h2").not($(this)).removeClass("minus");
			$("h2").not($(this)).next().slideUp(1000);
			
			
			$(this).addClass("minus");
			$(this).next().slideDown(1000);
			
		}
    ); // end click
    
 //alert("table width" + $("#assistanceAside").css("width"));
    // alert("here is the with of the body: " + $("body").css("width")); .css("width") attaches the units too like 200px, .width() only gives number like 200.
}); // end ready


/**
 * $(document).ready(function() {
	$("#assistanceMain h2").first().addClass("minus");
	$("#assistanceMain div").first().show();
	$("#assistanceMain h2").click(
		function() {
			$(this).toggleClass("minus");
		    $(this).next().slideToggle(1000);

			
			$("h2").not($(this)).removeClass("minus");
			$("h2").not($(this)).next().slideUp(1000);
			
			
			$(this).addClass("minus");
			$(this).next().slideDown(1000);
			
		}
    ); // end click
}); // end ready
 */