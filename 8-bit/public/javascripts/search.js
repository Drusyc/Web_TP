
/* Fonction appelée lorsqu'une lettre est tapée dans le champ input 
   Effectue une requête sur le  serveur et affiche les résultats */
$(function recherche () {
	var url = "/search/";

	$("#recherche").bind("input", function() {
		if ($("#recherche").val().length < 4) return;
		var game = $("#recherche").val();
		
		$.getJSON(url.concat(game), function(data){
			$("#game-list").empty();
			$.each(data, function(key, val) {
				var item = document.createElement("li");
				item.setAttribute("id", "item_found_" + val);

				var url = document.createElement("a");
				url.setAttribute("id", "game_url_" + val);
				
				url.setAttribute("href", "/game/" + val.name.replace(/ /g, "+"));
				url.innerHTML = val.name;					

				item.appendChild(url);				

				$("#game-list").append(item);
			});
		});
	});//bind

})
