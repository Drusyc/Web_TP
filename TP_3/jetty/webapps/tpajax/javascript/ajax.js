/* Fonction de w3school */
function setCookie (cname, cvalue, exdays) {
  var d = new Date();
  d.setTime(d.getTime() + (exdays*24*60*60*1000));
  var expires = "expires="+ d.toUTCString();
  document.cookie = cname + "=" + cvalue + "; " + expires;  
}

/* Fonction de w3school */
function getCookie(cname) {
  var name = cname + "=";
  var ca = document.cookie.split(';');
  for (var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') c = c.substring(1);
    if (c.indexOf(name) == 0) return c.substring(name.length, c.length); 
  }
  return "";
}

function recherche() {

  var url = "http://localhost:8080/tpajax/recherche/";
  var xmlhttp = new XMLHttpRequest();
  
  xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
      $("#resultats").remove();
      
      var msgResultat;
      if (document.getElementsByTagName("h2").length == 1) 
        msgResultat = document.getElementsByTagName("h2")[0];
      else
        msgResultat = document.createElement("h2");

      if (xmlhttp.responseText.length > 0) {
        var resultats = JSON.parse(xmlhttp.responseText);
        msgResultat.innerHTML = resultats.length + " résultat(s) pour \"" + $("#recherche").val() + "\"";
        $("body").append(msgResultat);

        afficheResultat(resultats);
      } else {
        msgResultat.innerHTML = "";
      }
/*
      


      if (xmlhttp.responseText.length > 0) {
        afficheResultat(resultats);
      }*/
    }
  }
  xmlhttp.open("GET", url.concat($("#recherche").val()), true);
  xmlhttp.send();
  
  setCookie("cookieRecherche",$("#recherche").val(), 1);  
}

function afficheResultat(res) {



    var liste = document.createElement("ul");
    liste.setAttribute("id", "resultats");
    liste.setAttribute("style", "list-style-type:none");


    for(var i = 0; i < res.length; i++) {
      var item = document.createElement("li");
      var htmllink = document.createElement("a");

      item.setAttribute("id", "resultats_" + i);
      //item.setAttribute("class", "resultat");

      htmllink.style.color = (i%2 == 0) ? "#006666" : "#009966" ;
  
      htmllink.setAttribute("href", res[i].url);
      htmllink.setAttribute("class", "resultat");
      htmllink.innerHTML = res[i].apogee + " : " + res[i].nom;
    
      item.appendChild(htmllink);
      liste.appendChild(item);
    }
    
    $("body").append(liste);
    
    /* Event pour récupérer les objectifs */
    $("a.resultat").bind("mouseenter", getObjectifs);
    $("a.resultat").bind("mouseout", toggleObjectifs);
}

function getObjectifs() {

  /* La ligne correspondant de la liste */
  var li = this.parentElement;

  /* Si la recherche a déjà été faite, elle a été caché, on la réaffiche.
     Evite une nouvelle requête au serveur */
  if (li.getElementsByClassName("objectifs").length > 0) {
    $("#objectif_" + li.getAttribute("id")).show("fast");
    return;
  } 
  var url = "http://localhost:8080/tpajax/objectifs/";

  /* Récupère l'adresse de la page de présentation de la matière */
  var urlObj = li.getElementsByTagName("a")[0].getAttribute("href");
  
  /* Découpe le lien */
  var values = urlObj.split("/");

  var xmlhttp = new XMLHttpRequest();
  xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
      if (xmlhttp.responseText.length > 0) {
        afficheObjectifs(JSON.parse(xmlhttp.responseText), li.getAttribute("id"));
      }
    }
  }
  xmlhttp.open("GET", url.concat(values[values.length-1]), true);
  xmlhttp.send();
  
}

function toggleObjectifs () {
  if (this.parentElement.getElementsByClassName("objectifs").length > 0) 
    $(document).ready($("#objectif_" + this.parentElement.getAttribute("id")).hide("fast"));   
}

function afficheObjectifs(responseObjectifs, IDListe) {
  
  var item = document.createElement("p");
  item.setAttribute("id", "objectif_" + IDListe);
  item.classList.add("objectifs");

  item.innerHTML = responseObjectifs[0].Objectifs;

  $("#"+IDListe).append(item);

  $(document).ready($("#objectif_" + IDListe).show("fast"));
}

function main() {
  /* Si l'utilisateur a déjà effectué une recherche,
     on récupère cette recherche par le cookie et le met en valeur de l'input */
  $("#recherche").attr("value", getCookie("cookieRecherche"));

  /* Déclenche une requête pour le serveur dès qu'une lettre est tapée */
  $("#recherche").bind("keyup", recherche);
 
}

$(window).bind("load",main);
