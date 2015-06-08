
/*
    Sur la page d'un jeu, permet d'afficher la configuration du joueur selectionné dans la selectBox
 */
$(function selectConfiguration () {
    var configIndex = $("#select-config-gamer option:selected").val();

    var ul = $("#config-"+configIndex);
    $("#config-displayed").empty();
    $("#config-displayed").append(ul.children().clone());

    $("#select-config-gamer").bind("change", function() {
        var configIndex = $("#select-config-gamer option:selected").val();

        var ul = $("#config-"+configIndex);
        $("#config-displayed").empty();
        $("#config-displayed").append(ul.children().clone());
    });
})

function compareConfig(gameName) {
    //create popup ou tableau..
    //requête pour récupérer config jeu + config gamer
    //comparer


    /* Création Tableau */
    var table = document.createElement('table');
    table.setAttribute("border", 1);

    /* Header du tableau */
    var theader = table.createTHead();
    var row = theader.insertRow(0);
    var cell = row.insertCell(0);
    cell.innerHTML = "<b>Comparaison</b>";

    /* Création des lignes / colonnes */
    for (i = 1; i < 10; i++) {
        var row = table.insertRow(i);
        for (j = 0; j < 4; j++) {
            row.insertCell(j);
        }
    }

    /* 1ère ligne : Nom des colonnes */
    var i = 1;
    table.rows[i].cells[1].innerHTML = "Configuration de " + gameName;
    table.rows[i].cells[2].innerHTML = "Votre configuration : " + $("#select-config-gamer option:selected").text();
    table.rows[i].cells[3].innerHTML = "OK/KO";

    /* 2ème ligne : OS*/
    var i = 2;
    table.rows[i].cells[0].innerHTML = "OS";
    table.rows[i].cells[1].innerHTML = "";
    table.rows[i].cells[2].innerHTML = "";
    table.rows[i].cells[3].innerHTML = "OK/KO";

    /* Ajoute le tableau au html */
    $("main").append(table);
}