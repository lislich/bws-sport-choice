$(document).ready(function () {

    // # Menü #############################################################################################

    /**
     * @author Lisa
     * 
     * Lässt die Menü-Auswahl erscheinen/verschwinden wenn man das Label des Menü anklickt.
     */
    $("#menu").click(function () {
        $("#togglebar").toggle(function () {
            $("#togglebar").addClass("hide");
        }, function () {
            $("#togglebar").removeClass("hide");
        });

    });
    
    /**
     * @author Lisa
     * 
     * Methode für Handyansicht.
     * Lässt die Menü-Auswahl erscheinen/verschwinden wenn man das Label des Menü anklickt.
     */
    $("#responsiveMenu").click(function () {
        $("#togglebar").toggle(function () {
            $("#togglebar").addClass("hide");
        }, function () {
            $("#togglebar").removeClass("hide");
        });

    });

     // # Kurs anlegen ####################################################################################
    
   /**
     * @author Lisa
     * 
     * Wenn die Checkbox angehakt ist wird das Feld für die Teilnehmerzahl gesperrt, da
     * die Teilnehmerzahl dann unbegrenzt sein soll. 
     * Der Default-Wert der Teilnehmerzahl wird auf 25 gesetzt.
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
    }); */

/**
     * @author Lisa
     * 
     * Wenn die Checkbox im Header der Tabelle angehakt wird werden alle Checkboxen in der Tabelle angehakt und umgekehrt auch enthakt.
     */
    $('#alleWaehlen').change(function() {   
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
   

    // # Kurs bearbeiten ####################################################################################

    /**
     * @author Lisa
     * 
     * Beim Kurs bearbeiten soll die Stufenauswahl anfangs gesperrt sein. Wenn man auf den Button "neu setzen" klickt,
     * wird die Auswahl freigeschalten.
     */
    $(".disableSelectStufe").addClass("hide");
    $(".disableSelectStufe").attr("disabled", true);
    $("#stufeNeu").click(function () {
       $(".disableStufe").val("");
       $(".disableSelectStufe").removeClass("hide");       
       $(".disableSelectStufe").attr("disabled", false);
       $(".disableStufe").replaceWith($(".disableSelectStufe"));
    });
    
    /**
     * @author Lisa
     * 
     * Beim Kurs bearbeiten soll die Themengleich-Kursauswahl anfangs gesperrt sein. Wenn man auf den Button "neu setzen" klickt,
     * wird die Auswahl freigeschalten.
     */
    $(".disableSelectThemengleich").addClass("hide");
    $(".disableSelectThemengleich").attr("disabled", true);
    $("#themengleichNeu").click(function () {
        $(".disableThemengleich").val("");
        $(".disableSelectThemengleich").removeClass("hide");       
        $(".disableSelectThemengleich").attr("disabled", false);
        $(".disableThemengleich").replaceWith($(".disableSelectThemengleich"));
    });
    
    
//    ## Benutzer ändern ###################################################################################
    $(".disableSelectTutor").addClass("hide");
    $(".disableSelectTutor").attr("disabled", true);
    $("#tutorNeu").click(function () {
        $(".disableTutor").val("");
        $(".disableSelectTutor").removeClass("hide");       
        $(".disableSelectTutor").attr("disabled", false);
        $(".disableTutor").replaceWith($(".disableSelectTutor"));
    });
    
});

