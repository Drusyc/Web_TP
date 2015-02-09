/* Fonction de coloration des lignes du tableau selon les notes */
function MrColor() {

  var table = document.getElementById("T-1");
  var rows = table.rows;

  for (var i = 1; i < rows.length; ++i) {

    if (i % 2 == 0)
      rows[i].classList.add("grayHeader"); 

    var cells = rows[i].getElementsByTagName("td");

    if (cells[5].textContent < 8) {
	cells[5].className = "badGrade";

    } else if (cells[5].textContent < 10) {
	cells[5].className = "mediocreGrade";

    } else if (cells[5].textContent < 12) {
	cells[5].className = "fairGrade";

    } else {
	cells[5].className = "goodGrade";
    }//fi

  }//for()
}

/* Fonction pour retirer l'arborescence des matières  */
function MrRemoveIndex() {

  var table = document.getElementById("T-1");
  var rows = table.rows;
  
  for (var i = 1; i < rows.length; ++i) {
    var cells = rows[i].getElementsByTagName("td");
    nameArbo = cells[1];

    if (nameArbo.getElementsByTagName("span") &&
	nameArbo.getElementsByTagName("span")[0].className.contains("arbo")) {
      //nameArbo.getElementsByTagName("span")[0].remove();
      nameArbo.getElementsByTagName("span")[0].className = "hidden";
    }	
  }//for

}//removeIndex()


/* Fonction pour cacher le tableau en cliquant dessus */
function MrHide () {
  var table = document.getElementById("T-1");
  table.classList.toggle("hidden");
}

/* Trie par sélection utilisé par MrTrie pour trier le tableau selon les colonnes */
function MrSort(tableRows, id) {  
  //alert(typeof tableRows[1].getElementsByTagName("td")[id].textContent);
  //alert (isNaN(tableRows[1].getElementsByTagName("td")[id].textContent));

  /* On cherche à connaitre le type de données à trier, string ou entier */
  var isStr = isNaN(tableRows[1].getElementsByTagName("td")[id].textContent);

  /* Check si la colonne est déjà triée, pour savoir si on la reverse */
  var isAsc = true; //on suppose qu'elle est triée..
  for (var i = 1; i < tableRows.length-1; i++) {
    var v1 = tableRows[i].cells[id].textContent;
    var v2 = tableRows[i+1].cells[id].textContent;

    if (!isStr) {
      v1 = (tableRows[i].cells[id].textContent == "") ? 0 : parseFloat(tableRows[i].cells[id].textContent); 
      v2 = (tableRows[i+1].cells[id].textContent == "") ? 0 : parseFloat(tableRows[i+1].cells[id].textContent);
    }

    if (v1 > v2) {
      isAsc = false;
      break;
    }
  }

  /* Algo de trie par selection */
  for (var i = 1; i < tableRows.length - 1; i++) {
    var min = i;

    for (var j = i + 1; j < tableRows.length; j++) {
      v1 = tableRows[j].cells[id].textContent;
      v2 = tableRows[min].cells[id].textContent;

      /* Conversion en entier si le type à trier est entier*/
      if (!isStr) {
	v1 = (tableRows[j].cells[id].textContent == "") ? 0 : parseFloat(tableRows[j].cells[id].textContent); 
	v2 = (tableRows[min].cells[id].textContent == "") ? 0 : parseFloat(tableRows[min].cells[id].textContent);
      }

      if (isAsc) {
	if (v1 > v2) {
	  min = j;
	}
      } else {
	if (v1 < v2) {
	  min = j;
	}
      }	
    }//for
    
    if (min != i) {
      var tmp = tableRows[i].outerHTML;
      tableRows[i].outerHTML = tableRows[min].outerHTML;
      tableRows[min].outerHTML = tmp;
    }
  }//for
}


/* Fonction de Trie pour les colonnes utilisant un trie par sélection MrSort */
function MrTrie () {
  var id = this.cellIndex;
  var table = document.getElementById("T-1");
  var rows = table.rows;
  MrSort(rows, id);
}

/* Fonction créant un histogramme selon les notes du tableaux */
function MrHist () {
  /* Tableau pour récupérer les notes */
  noteTable = [];
  for (var i = 0; i < 20; i++) 
    noteTable[i] = 0;

  var rows = document.getElementById("T-1").rows;

  /* Récupèrations des notes du tableau */
  for (var i = 1; i < rows.length; ++i) {
    var cells = rows[i].getElementsByTagName("td");
    var note = Math.floor(parseFloat(cells[5].textContent));
    noteTable[note]++;  
  }

  /* Création d'un objet canvas pour l'histogramme */
  var canvi = document.createElement('canvas');
  canvi.setAttribute("id", "histogramme");

  var context = canvi.getContext("2d");

  /* Taille du canvas */
  var H = 300; //y
  var W = 1000;//x

  canvi.height = H;
  canvi.width  = W;

  //axe des abscisses
  context.moveTo(50, H - 50);
  context.lineTo(W-100, H - 50);
   
  context.fillStyle ="red";
  context.font = "15px Arial";
  context.textAlign = "center";
  context.fillText("Notes S1", W/2, 20);
  
  /* Calcul d'un coefficient pour l'espace entre les barres et les chiffres */
  var coef = (W-100)/21;
  for (var i = 0; i <= 20; i++) {
    context.beginPath();
    context.fillStyle = "black";
    /* Données en abscisses */
    context.fillText(String(i), 50 + coef*i, H - 20);
    if (i != 20) {       
      /* Barres */
      context.fillStyle = "cyan";
      context.rect(60 + coef*i, H - 60, 25, -20 * noteTable[i]);
      context.stroke();
      context.fill();   

      /* Indicateurs du nombre de notes par barres */
      if (noteTable[i] != 0) {
	context.fillStyle = "black";
	context.fillText(String(noteTable[i]), 70 + coef*i, (H-65));
      }
    }
    context.closePath();
  }
  document.body.appendChild(canvi);
}//MrHist()


/* Fonction appelante du .js */
function main () {
  
  /* Coloration des lignes */
  MrColor();

  /* Retrait de l'arborescence */ 
  MrRemoveIndex();

  /* Cache/Affiche le tableau */
  document.getElementsByTagName("h1")[0].addEventListener("click", MrHide, false);

  /* Trie par selection de chaque colonne */
  var columns = document.getElementById("T-1").getElementsByTagName("th");
  for (var i = 0; i < columns.length; ++i)
    columns[i].addEventListener("click", MrTrie, false);

  /* Création de l'histogramme */
  MrHist();
}

/* Lance le .js une fois que la page HTML est entièrement chargée */ 
window.addEventListener("load", main, false);

