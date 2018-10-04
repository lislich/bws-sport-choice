$(document).ready(function(){
    $("#logo").attr("src" , "../imgs/logo.svg");
    
    $("#menu").click(function(){
        $("#togglebar").toggle(function(){
            $("#togglebar").addClass("hide");
        }, function(){
            $("#togglebar").removeClass("hide");
        });
        
    });
    
    $("#UWFEins").change(function () {
        if ($("#UWFEins").is(" :checked")) {
            $("#UWFZwei").attr("disabled", true);
            $("#UWFDrei").attr("disabled", true);
        }
    });
    
    $("#UWFZwei").change(function () {
        if ($("#UWFZwei").is(" :checked")) {
            $("#UWFEins").attr("disabled", true);
            $("#UWFDrei").attr("disabled", true);
        }
    });
    
    $("#UWFDrei").change(function () {
        if ($("#UWFDrei").is(" :checked")) {
            $("#UWFZwei").attr("disabled", true);
            $("#UWFEins").attr("disabled", true);
        }
    });
    
});