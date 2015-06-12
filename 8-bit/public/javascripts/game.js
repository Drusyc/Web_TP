/*
 * Sur la page d'un jeu, permet d'afficher la configuration du joueur sélectionnée dans la selectBox
 */
$(function selectConfiguration () {
    var configIndex = $("#select-config-gamer option:selected").val();

    var table = $("#config-"+configIndex);
    $("#config-displayed").empty();
    $("#config-displayed").append(table.children().clone());

    $("#select-config-gamer").bind("change", function() {
        var configIndex = $("#select-config-gamer option:selected").val();

        var table = $("#config-"+configIndex);
        $("#config-displayed").empty();
        $("#config-displayed").append(table.children().clone());
    });
});