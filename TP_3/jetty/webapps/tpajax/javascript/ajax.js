

function recherche() {
  if (!($("#resultats").length > 0)) {
    /* Première recherche, création d'un élément pour récupérer le résultat */
    var result;
    result = document.createElement("p");
    result.setAttribute("id", "resultats");
    $("body").append(result);
  }

  var url = "http://localhost:8080/tpajax/recherche/";
  var xmlhttp = new XMLHttpRequest();
  
  xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
      alert("ok");
      displayResults(JSON.parse(xmlhttp.responseText));
    }
  }
  xmlhttp.open("GET", url, true);
  xmlhttp.send();

}

function displayResults (res) {
    alert(res);
}


function main() {
  $("#recherche").bind("keyup", recherche);
  
}
$(window).bind("load",main);
