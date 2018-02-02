$(document).ready(function () {
        
    $("#main h2").click(
            function () {

                if ($(this).hasClass("fold")) {
                    $(this).toggleClass("unfold");
                    $(this).next().toggleClass("open");

                } else {
                    $(this).toggleClass("fold");
                    $(this).next().toggleClass("closed");
                }
            }
    ); // end click
    
    $(".primaryTitle").click(
            function (){
                
                $("#primarySection h2").toggleClass("unfold");
                $("#primarySection div").toggleClass("open"); 
            });
    
    
     
    $(".secondaryTitle").click(
            function (){
                
                $("#secondarySection h2").toggleClass("unfold");
                $("#secondarySection div").toggleClass("open"); 
            });
}); // end ready






//$(document).ready(function () {
//   // var theMain = $("main");
//    var allH2Elements = $("#main").getElementsByTagName("h2");
//
//    var h2Node;
//    for (var i = 0; i < allH2Elements.length; i++) {
//        h2Node = allH2Elements[i];
//
//        h2Node.click = function () {
//            var h2 = this;
//
//            if (h2.getAttribute("class") === "fold") {
//                h2.setAttribute("class", "unfold");
//            } else {
//                h2.setAttribute("class", "fold");
//            }
//
//            if (h2.nextElementSibling.getAttribute("class") === "closed")
//            {
//                h2.nextElementSibling.setAttribute("class", "open");
//            }else{
//                h2.nextElementSibling.setAttribute("class", "closed");
//            }
//        };
//
//
//    }
//
//});

//  $(document).ready(function() {
//	$("#main h2").first().addClass("open");
//	$("#main h2").not($(this)).addClass("close");
//        
//	$("#main div").first().show();
//	$("#main h2").click(
//		function() {
//                    
//                    if($(this).hasClass("open")){
//                        $(this).toggleClass("close");
//                       $("h2").not($(this)).next().hide();
//                        
//                    }                    else if($(this).hasClass("close")){
//                       $(this).toggleClass("open"); 
//                        $(this).next().show();
//                       //$(this).toggleClass("open");
//                    }
//			$(this).toggleClass("open");
//                        $(this).next().show();
//
//			
//			$("h2").not($(this)).addClass("close");
//$("h2").not($(this)).next().hide();
//
//
//
//
//		}
//    ); // end click
//}); // end ready
