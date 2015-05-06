/* Ouvre un nouvel onglet pour afficher le formulaire et les commentaires */
function formComment (matiere) {
  //window.location.assign("formulaire.html");
  window.open("commentaires.html", matiere);
}

/* Fonction de w3school pour la gestion du cookie */
function setCookie (cname, cvalue, exdays) {
  var d = new Date();
  d.setTime(d.getTime() + (exdays*24*60*60*1000));
  var expires = "expires="+ d.toUTCString();
  document.cookie = cname + "=" + cvalue + "; " + expires;  
}

/* Fonction de w3school pour la gestion du cookie */
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


/* Fonction appelée lorsqu'une lettre est tapée dans le champ input 
   Effectue une requête sur le serveur et affiche les résultats */
function recherche() {
  var url = "http://localhost:8080/tpajax/recherche/";
  var xmlhttp = new XMLHttpRequest();
  
  /* Fonction appelée lorqu'on reçoit une réponse du serveur */
  xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
      $("#resultats").remove();
      
    /* Petit header pour afficher le nombre de résultats */
      var msgResultat;
      if (document.getElementsByTagName("h2").length == 1) 
        msgResultat = document.getElementsByTagName("h2")[0];
      else
        msgResultat = document.createElement("h2");

      if (xmlhttp.responseText.length > 0) {
        var resultats = JSON.parse(xmlhttp.responseText);
        msgResultat.innerHTML = resultats.length + " résultat(s) pour \"" + $("#recherche").val() + "\"";
        $("body").append(msgResultat);

        /* Affiche les résultats du JSON*/
        afficheResultat(resultats);
      } else {
        msgResultat.innerHTML = "";
      }
    }
  }
  xmlhttp.open("GET", url.concat($("#recherche").val()), true);
  xmlhttp.send();
  
  setCookie("cookieRecherche",$("#recherche").val(), 1);  
}

/* Récupère la réponse du serveur (JSON) au format text et affiche le contenu de la recherche */
function afficheResultat(res) {
    var liste = document.createElement("ul");
    liste.setAttribute("id", "resultats");
    liste.setAttribute("style", "list-style-type:none");

    for(var i = 0; i < res.length; i++) {
      var item = document.createElement("li");
      var htmllink = document.createElement("a");

      item.setAttribute("id", "resultats_" + i);

      //htmllink.style.color = (i%2 == 0) ? "#006666" : "#009966";
      if (i%2 == 0)
        htmllink.setAttribute("style", "color:#006666");
      else 
        htmllink.setAttribute("style", "color:#009966");


      htmllink.setAttribute("href", res[i].url);
      htmllink.setAttribute("class", "resultat");
      htmllink.innerHTML = res[i].apogee + " : " + res[i].nom;
                    
      var details = document.createElement("details");
      details.setAttribute("id", "details_" + "resultats_" + i);
      details.classList.add("objAndComm");

      
//       Codant sous Firefox (avec les machines virtuelles de l'ensimag), je n'ai pas pu tester le bon fonctionnement 
//       des éléments <details>. J'ai donc opté pour masquer/afficher les informations en passant la souris sur le lien du cours.
//       J'ai retiré le <summary> qui ne se comportait bizarrement avec cette option d'affichage/masquage.

//       var sum = document.createElement("summary");
//       sum.innerHTML = "Détails du cours (Objectifs et Commentaires)";
// 
//       details.appendChild(sum);
      

      item.appendChild(details);

      item.appendChild(htmllink);

      liste.appendChild(item);
    }
    
    $("body").append(liste);
    
    /* Event pour récupérer les objectifs */
    $("a.resultat").bind("mouseenter", getObjectifs);

    /* Cache les objectifs une fois que la souris sort du l'hypertext */
    //$("a.resultat").bind("mouseout", toggleObjectifs);
}

/* Fonction appelé lorsque la souris est passée sur un lien 
   Effectue une requête sur le serveur pour récupérer les objectifs du cours souhaités 
   Affiche les objectifs */
function getObjectifs() {

  /* La ligne correspondant de la liste */
  var li = this.parentElement;

  /* Si la recherche a déjà été faite, elle a été cachée, on la réaffiche.
     Evite une nouvelle requête au serveur */
  if (li.getElementsByClassName("objectifs").length > 0) {
    if ($("#details_" + li.getAttribute("id")).attr("style") == "display: none;") {
      $("#details_" + li.getAttribute("id")).show("fast");
      
      /* On refait la requête REST (au cas où un commentaire ait été rajouté entre temps) */
      restRequest(this.textContent, li);
    } else {
      $("#details_" + li.getAttribute("id")).hide("fast");
    }
    return;
  } 


  var url = "http://localhost:8080/tpajax/objectifs/";

  /* Récupère l'adresse de la page de présentation de la matière */
  var urlObj = li.getElementsByTagName("a")[0].getAttribute("href");
  
  /* Découpe le lien */
  var values = urlObj.split("/");

   /* Fonction appelée lorqu'on reçoit une réponse du serveur */
  var xmlhttp = new XMLHttpRequest();
  xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
      if (xmlhttp.responseText.length > 0) {
        /* Affiche le résultat */
        afficheObjectifs(JSON.parse(xmlhttp.responseText), li.getAttribute("id"));
      }
    }
  }
  xmlhttp.open("GET", url.concat(values[values.length-1]), true);
  xmlhttp.send();
  

  /* REST requête */
  restRequest(this.textContent, li);
}//getObjectifs()



/* Cache les objectifs (*/
function toggleObjectifs () {
  if (this.parentElement.getElementsByClassName("objectifs").length > 0) 
    $(document).ready($("#objectif_" + this.parentElement.getAttribute("id")).hide("fast"));   
}//toggleObjectifs()




/* Récupère la réponse du serveur (JSON) au format text et affiche les objectifs */
function afficheObjectifs(responseObjectifs, IDListe) {
  
  var item = document.getElementById("details_"+IDListe);
  var para = document.createElement("p");
  item.appendChild(para);
  para.setAttribute("id", "objectif_" + IDListe);
  item.classList.add("objectifs");

  para.innerHTML = "Objectifs du cours : <br>" + responseObjectifs[0].Objectifs;

  $("#"+IDListe).append(item);

  $(document).ready($("#objectif_" + IDListe).show("fast"));
}//afficheObjectifs()



/* Fonction de vérification pour le champ textarea de commentaires.html */
function checkInputTxt() {
  var values = $("#inputTxt").val();
  var cpt = 0;
  var re = /^[\w\d\s,:!&"'()*$?.%]$/;

  /* Ne sachant pas trop comment interdire un caractère tapé, je remets à jour directement la value de textarea */
  var correctStr = new String("");

  for(var i = 0; i < values.length; i++) {
    if (re.test(values[i])) {
      correctStr = correctStr.concat(values[i]);
      cpt++;
    }
  }//for()

  $("#labelInputTxt").html("Texte (" + cpt + "/500):");
  $("#inputTxt").val(correctStr.toString());
}//checkInputTxt()



/* Fonction qui effectue la requête au serveur pour récupérer le dernier commentaire */
function restRequest (matiere, element) { 
  
  var splitted = matiere.split(" :");
  var code = splitted[0];

  /* Création de l'élément qui va contenir le dernier commentaire + un bouton (lien) vers commentaires.html */
  if (document.getElementById("commentaires_" + code) != null)
    var item = document.getElementById("commentaires_" + code);
  else {
    var item = document.createElement("p");
    item.setAttribute("id", "commentaires_" + code);
    item.classList.add("lastCommentaires");

    /* Rattachement vers l'élement du dessus */
    li = document.getElementById("details_" + element.getAttribute("id"));
    li.appendChild(item);
    
    /* Création du bouton (lien) vers commentaires.html (le bouton envoie comme données le titre du cours et le code) */
    var formu = document.createElement("form");
    //formu.setAttribute("method", "get");
    formu.setAttribute("id", "formOpenComment");
    formu.setAttribute("action", "javascript: formComment(" + "\"" + matiere + "\""  + ")");
  
    var b = document.createElement("button");
    b.setAttribute("type", "submit");
    b.setAttribute("name", "Cours");
    b.setAttribute("value", matiere);
    b.innerHTML = "Commentaires";

    formu.appendChild(b);
    li.appendChild(formu);
  }

  /* Requête REST json */
  $.getJSON("http://localhost:8080/tpajax/commentaires/" + code, function(result){
      var str = "<strong>Nombre de commentaire(s) pour ce cours:  </strong>";
      $.each(result, function(i, field){
          str += field.nbComment + "<br><br>";

          if (field.nbComment != "0") {
            str += "<strong>Dernier commentaire déposé par:</strong> " + field.redacteur  + "<br><br>";
            str += "<em>\"" + field.commentaires + "\"</em><br><br>";
            str += "<small>Commentaire ajouté le " + field.date + "</small>";
          }
          item.innerHTML =  str ;
      });
  });  
}//restRequest()



/* Function appelée par le formulaire et le bouton F5 de commentaires.html 
   Effectue des requêtes au serveurs (SELECT ou un INSERT TO) */
function getPostResult(arg) {
  if (arg == "insert") {
    $.post("http://localhost:8080/tpajax/commentaires/insert", 
          {
              mailRedacteur : $("#inputMail").val(),
              codeMatiere   : $("#inputCode").val(),
              ageRedacteur  : $("#inputAge").val(),
              commentaires  : $("#inputTxt").val()            
            }
            , function(data,status) {
                $("#listComment").html(data);
            });
  } else if (arg == "select"){
    $.post("http://localhost:8080/tpajax/commentaires/select", 
          {
              codeMatiere   : $("#inputCode").val(),        
            }
            , function(data,status) {
                $("#listComment").html(data);
            });
  }
}//getPostResult()




/* Fonction d'initialisation de la page commentaires.html 
   Met à jours les éléments du formulaires et leur évenements */
function setUpForm() {
  /* Récupère l'info de la matière passée dans window.name */
  infoMatiere = window.name.split(" :"); // 0 : code // 1 : nom

  /* Affiche les commentaires de la base */
  //afficheCommentaires(infoMatiere[0]);

  /* Le header h1 récupère l'info du nom de la matière */
  $("h1").html("Commentaires de : " + infoMatiere[1]);


  /* On set l'input "Code" et on le rend inclickable */
  $("#inputCode").val(infoMatiere[0]);
  $("#inputCode").attr("style", "background-color:#008B8B");
  $("#inputCode").focus(function() {
    $(this).blur();
  });

  /* Evenement de l'input textarea (check les caractères ajoutés) */
  $("#labelInputTxt").html("Texte (0/500):");
  $("#inputTxt").bind("keyup", checkInputTxt);

  /* Bouton F5 pour donner les commentaires sans être obligé d'en ajouter un */
  $("#buttonSelect").attr("onclick","javascript:getPostResult('select')");


  /* Action à lancée une fois submit */
  $("#comForm").attr("action", "javascript:getPostResult('insert')");
  $("#comForm").submit(function(event) {
    if ( $("#inputTxt").val() != "") { 
      return;
    }//fi
    alert("Ecrivez au moins un mot ! :)");
    event.preventDefault();
  });//checkForm         
}//setUpForm()

function main() {

  if (document.title == "Ensimaagle") {
    /* Si l'utilisateur a déjà effectué une recherche,
      on récupère cette recherche par le cookie et le met en valeur de l'input */
    $("#recherche").attr("value", getCookie("cookieRecherche"));

    /* Déclenche une requête pour le serveur dès qu'une lettre est tapée */
    $("#recherche").bind("keyup", recherche);
  
  } else if (document.title == "Commentaires") {
    setUpForm();    
  }//fi
}

$(window).bind("load",main);
