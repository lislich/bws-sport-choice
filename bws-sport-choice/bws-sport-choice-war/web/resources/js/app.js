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
            $(".zwei").attr("disabled", false);
            $(".drei").attr("disabled", false);
        }
    });

    $(".zwei").change(function () {
        if ($(".zwei").is(" :checked")) {
            $(".zwei").attr("disabled", true);
            $(".drei").attr("disabled", false);
            $(".eins").attr("disabled", false);
        }
    });

    $(".drei").change(function () {
        if ($(".drei").is(" :checked")) {
            $(".drei").attr("disabled", true);
            $(".eins").attr("disabled", false);
            $(".zwei").attr("disabled", false);
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

    // # Benutzer verwalten ####################################################################################
    
    $("#auswahl").click(function(){
        if ($("#auswahl").is(":checked")){
            $(".loeschen").prop("checked", true);
        } else{
            $(".loeschen").prop("checked", false);
        }
    });
    var dialog;
    dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        buttons: {
            "Create an account": addUser,
            Cancel: function () {
                dialog.dialog("close");
            }
        },
        close: function () {
            form[ 0 ].reset();
            allFields.removeClass("ui-state-error");
        }
    });

    form = dialog.find("form").on("submit", function (event) {
        event.preventDefault();
        addUser();
    });

    $("#anlegen").button().on("click", function () {
        dialog.dialog("open");
    });


});

