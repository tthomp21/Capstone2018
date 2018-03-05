$(document).ready(function () {

    $("#primarySection h2, #secondarySection h2").click(
            function () {

                if ($(this).hasClass("fold")) {
                   // $(this).toggleClass("unfold");
                   // $(this).next().toggleClass("open");
                   
                    $(this).removeClass("fold");
                    $(this).addClass("unfold");
                    
                    $(this).next().removeClass("closed");
                    $(this).next().addClass("open");
                    
                    

                } else {
                   // $(this).toggleClass("fold");
                   // $(this).next().toggleClass("closed");
                   
                   $(this).removeClass("unfold");
                    $(this).addClass("fold");
                    
                    $(this).next().removeClass("open");
                    $(this).next().addClass("closed");
                }
                
            }
    ); // end h2 click

    $("#priH1").click(
            function () {
                if ($(this).hasClass("priClose"))
                {
                    $(this).attr("class", "priOpen");
                    
                    $("#primarySection h2").removeClass("fold");
                    $("#primarySection h2").addClass("unfold");
                    
                    $("#primarySection div").removeClass("closed");
                    $("#primarySection div").addClass("open");
                   
              
                  
                    
                }else
                {
                    $(this).attr("class", "priClose");
                    
                    $("#primarySection h2").removeClass("unfold");
                    $("#primarySection h2").addClass("fold");
                    
                    $("#primarySection div").removeClass("open");
                    $("#primarySection div").addClass("closed");
                }


            });



    $("#secH1").click(
            function () {
                
                if ($(this).hasClass("priClose"))
                {
                    $(this).attr("class", "priOpen");
                    
                    $("#secondarySection h2").removeClass("fold");
                    $("#secondarySection h2").addClass("unfold");
                    
                    $("#secondarySection div").removeClass("closed");
                    $("#secondarySection div").addClass("open");
                   
              
                  
                    
                }else
                {
                    $(this).attr("class", "priClose");
                    
                    $("#secondarySection h2").removeClass("unfold");
                    $("#secondarySection h2").addClass("fold");
                    
                    $("#secondarySection div").removeClass("open");
                    $("#secondarySection div").addClass("closed");
                }

                //$("#secondarySection h2").toggleClass("unfold");
                //$("#secondarySection div").toggleClass("open");
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
