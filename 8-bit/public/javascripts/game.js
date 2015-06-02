
/*
    Sur la page d'un jeu, permet d'afficher la configuration du joueur selectionné dans la selectBox
 */
$(function selectConfiguration () {
    $("#select-config-gamer").bind("change", function() {
        var configIndex = $("#select-config-gamer option:selected").val();

        var ul = $("#config-"+configIndex);
        $("#config-displayed").empty();
        $("#config-displayed").append(ul.children().clone());
    });
})