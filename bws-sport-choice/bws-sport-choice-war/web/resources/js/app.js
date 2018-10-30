$(document).ready(function () {

    // # Menü #############################################################################################

    /**
     * Lässt die Menü-Auswahl erscheinen/verschwinden wenn man das Label des Menü anklickt.
     */
    $("#menu").click(function () {
        $("#togglebar").toggle(function () {
            $("#togglebar").addClass("hide");
        }, function () {
            $("#togglebar").removeClass("hide");
        });

    });

     // # Kurs anlegen ####################################################################################
    
   /**
     * 
     */
    if($(".teilnehmerzahlRequired").is(" :checked")){
        $(".disable").attr('disabled', true);
    }else{
        $(".disable").val("25");
    }
    $(".teilnehmerzahlRequired").change(function(){
        if (this.checked){
            $(".disable").attr('disabled', true);
        }else{
            $(".disable").attr('disabled', false);
        }
    });
    
    

    // # Benutzer verwalten ####################################################################################
/*    
    $(".filter").change(function(){
        $("#refreshFilter").click();
        $("#tableBenutzer").load();
    });

    $('#alleWaehlen').click(function(event) {   
       if(this.checked) {
           // Iterate each checkbox
           $(".cbWaehlen").each(function() {
               this.checked = true;                        
           });
       } else {
           $(".cbWaehlen").each(function() {
               this.checked = false;                       
           });
       }
   });
    
    $(".cbWaehlen").change(function(){
        $("#alleWaehlen").attr('checked', false);
    });
   
*/   
    // # Kurs bearbeiten ####################################################################################

    $(".disableSelectStufe").addClass("hide");
    $(".disableSelectStufe").attr("disabled", true);
    $("#stufeNeu").click(function () {
       $(".disableStufe").val("");
       $(".disableSelectStufe").removeClass("hide");       
       $(".disableSelectStufe").attr("disabled", false);
       $(".disableStufe").replaceWith($(".disableSelectStufe"));
    });
    
    $(".disableSelectThemengleich").addClass("hide");
    $(".disableSelectThemengleich").attr("disabled", true);
    $("#themengleichNeu").click(function () {
        $(".disableThemengleich").val("");
        $(".disableSelectThemengleich").removeClass("hide");       
        $(".disableSelectThemengleich").attr("disabled", false);
        $(".disableThemengleich").replaceWith($(".disableSelectThemengleich"));
    });
});

