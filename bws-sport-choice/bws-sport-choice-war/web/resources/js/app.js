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
    * Funktion um Doppel-Wahl eines Kurses nicht zuzulassen.
    * Wenn ein Kurs bereits als 1.Wahl angeklickt wurde ist die 2. + 3.Wahl nurnoch bei anderen Kursen möglich.
    */
    $(".eins").change(function () {
        if ($(".eins").is(" :checked")) {
            $(".eins").attr("disabled", true);
        }
        if (!$(".zwei").is(" :checked")) {
            $(".zwei").attr("disabled", false);
        }
        if (!$(".drei").is(" :checked")) {
            $(".drei").attr("disabled", false);
        }

    });

    $(".zwei").change(function () {
        if ($(".zwei").is(" :checked")) {
            $(".zwei").attr("disabled", true);
        }
        if (!$(".eins").is(" :checked")) {
            $(".eins").attr("disabled", false);
        }
        if (!$(".drei").is(" :checked")) {
            $(".drei").attr("disabled", false);
        }
    });

    $(".drei").change(function () {
        if ($(".drei").is(" :checked")) {
            $(".drei").attr("disabled", true);
        }
        if (!$(".eins").is(" :checked")) {
            $(".eins").attr("disabled", false);
        }
        if (!$(".zwei").is(" :checked")) {
            $(".zwei").attr("disabled", false);
        }
    });
    
    
    /**
     * Setzt die 1., 2. oder 3.Wahl eines Kurses zurück
     */
    $("#resetEins").click(function(){
        $(".eins").attr("disabled", false);
        $(".eins").prop('checked', false);
    });
    $("#resetZwei").click(function(){
        $(".zwei").attr("disabled", false);
        $(".zwei").prop('checked', false);
    });
    $("#resetDrei").click(function(){
        $(".drei").attr("disabled", false);
        $(".drei").prop('checked', false);
    });

    
   /**
     * 
     */
    $(".disable").val("999");
    $("#teilnehmerzahlRequired").change(function(){
        if (this.checked){
            $(".disable").attr('disabled', true);
            $(".disable").val("999");
        }else{
            $(".disable").attr('disabled', false);
            $(".disable").val("0");
        }
    });
    

    // # Benutzer verwalten ####################################################################################
    
    $(".filter").change(function(){
        $("#refreshFilter").click();
        $("#tableBenutzer").load();
    });

    $("#alleWaehlen").click(function(){
        if($("#alleWaehlen").is(":checked")){
            $(".cbWaehlen").attr('checked', true);
        } else {
            $(".cbWaehlen").attr('checked', false);
        }
    });
    
    $(".cbWaehlen").change(function(){
        $("#alleWaehlen").attr('checked', false);
    });
   
   
    // # Kurs bearbeiten ####################################################################################

    $(".disableSelectStufe").attr("disabled", true);
    $("#stufeNeu").click(function () {
        $(".disableStufe").empty();
        $(".disableSelectStufe").attr("disabled", false);
    });
    
    $(".disableSelectThemengleich").attr("disabled", true);
    $("#themengleichNeu").click(function () {
        $(".disableThemengleich").empty();
        $(".disableSelectThemengleich").attr("disabled", false);
    });
});

