/* 
 Sayel Rammaha  
 2/3/18
 js for clientRequest.jsp
 */

$(document).ready(function() {   
    jQuery.validator.addMethod(
        "money",
        function(value, element) {
            var isValidMoney = /^\d{0,4}(\.\d{0,2})?$/.test(value);
            return this.optional(element) || isValidMoney;
        },
        "Insert "
    );
    
    $("#requestForm").validate({
        rules: {
            amount: {
                required: true,
                money: true
            },
            file: {
                required: true
            }
        },
        messages: {
            amount: {
                required: "Please enter the amount of your request.",
                money: "Please enter a valid dollar amount."
            },
            file: {
                required: "Please upload documentation."
            }
        },
        errorElement: 'div',
        errorLabelContainer: '.requestErrors'        
    });
});

function hideOldMsg() {
    $(".requestSuccess").hide();
}
