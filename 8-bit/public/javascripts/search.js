/**
 * Fonction appelée lorsqu'une lettre est tapée dans le champ input
 * Effectue une requête sur le serveur et affiche les résultats
 */
$(function search () {
    var url = "/search/";
    var search = $("#search");
    var gameList = $("#game-list");


    search.bind("input", function() {
        var game = search.val();
        if (game.length < 4) {
            gameList.empty();
            return;
        }

        $.getJSON(url.concat(game), function(data){
            gameList.empty();
            $.each(data, function(key, val) {
                var item = document.createElement("li");
                var url = document.createElement("a");

                item.setAttribute("class", "search-item");

                url.setAttribute("href", "/game/" + val.replace(/ /g, "+"));
                url.innerHTML = val;

                item.appendChild(url);

                gameList.append(item);
            });
        });
    });//bind

});
