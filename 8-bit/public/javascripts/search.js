/**
 * Fonction appelée lorsqu'une lettre est tapée dans le champ input
   Effectue une requête sur le serveur et affiche les résultats
 */
$(function recherche () {
	var url = "/search/";

	$("#search").bind("input", function() {
		if ($("#search").val().length < 4) {
            $("#game-list").empty();
            return;
        }
		var game = $("#search").val();
		
		$.getJSON(url.concat(game), function(data){
			$("#game-list").empty();
			$.each(data, function(key, val) {
				var item = document.createElement("li");
				var url = document.createElement("a");

                item.setAttribute("class", "search-item");
				
				url.setAttribute("href", "/game/" + val.name.replace(/ /g, "+"));
				url.innerHTML = val.name;					

				item.appendChild(url);				

				$("#game-list").append(item);
			});
		});
	});//bind

	$("main").append(results);
})
