/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
	$("#faqs h2").first().addClass("minus");
	$("#faqs div").first().show();
	$("#faqs h2").click(
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


