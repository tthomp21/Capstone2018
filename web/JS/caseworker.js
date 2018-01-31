/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function showDiv()
{
    if(document.getElementById('rbclients').checked)
        document.getElementById('clients').style.display = 'block';
    else
        $("#clients").css('display','none');
    
    if(document.getElementById('rbinstitution').checked)
        $("#institution").show();
    else
        $("#institution").hide();
}
