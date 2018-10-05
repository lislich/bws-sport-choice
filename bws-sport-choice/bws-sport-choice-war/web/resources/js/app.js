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



    /**
     * Fügt eine Zeile zur Tabelle der Unterrichtsthemen hinzu für das Anlegen von Kursen.
     */
    $(".add-row").click(function () {
        var markup = "<tr>";
        markup += "<td><input type='number' min='1' max='100' name='anteil' class='input-group-field'/></td>"
        markup += "<td><input type='text' class='input-group-field'/></td>";
        markup += "<td><input type='text' class='input-group-field' /></td>";
        markup += "<td><input type='checkbox' name='loeschen'/></td></tr>";
        $("table tbody").append(markup);
    });



   /**
     * Löscht ausgewählte Zeilen der Tabelle der Unterrichtsthemen für das Anlegen von Kursen.
     */
    $(".delete-row").click(function () {
        $("table tbody").find('input[name="loeschen"]').each(function () {
            if ($(this).is(":checked")) {
                $(this).parents("tr").remove();
            }
        });
    });

});

