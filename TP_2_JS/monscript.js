function color() {

  var table = document.getElementById("T-1");
  var rows = table.getElementsByTagName("tr");

  for (var i = 1; i < rows.length; ++i) {
    if (i % 2 == 0)
      rows[i].classList.add("grayHeader"); 

    var cells = rows[i].getElementsByTagName("td");
// //     al(cells[5]);
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
}//color()

function removeIndex() {

  var table = document.getElementById("T-1");
  var rows = table.getElementsByTagName("tr");
  
  for (var i = 1; i < rows.length; ++i) {
    var cells = rows[i].getElementsByTagName("td");
    nameArbo = cells[1];

    if (nameArbo.getElementsByTagName("span") &&
	nameArbo.getElementsByTagName("span")[0].className.contains("arbo")) {
      nameArbo.getElementsByTagName("span")[0].remove();
    }	
  }//for

}//removeIndex()

function MrHide () {
   
  var table = document.getElementById("T-1");
  table.classList.toggle("hidden");
}

function MrSort(tableRows, id) {  
  //alert(typeof tableRows[1].getElementsByTagName("td")[id].textContent);
  //alert (isNaN(tableRows[1].getElementsByTagName("td")[id].textContent));
  var isStr = isNaN(tableRows[1].getElementsByTagName("td")[id].textContent);

  var isAsc = true;
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

  for (var i = 1; i < tableRows.length - 1; i++) {
    var min = i;

    for (var j = i + 1; j < tableRows.length; j++) {
      v1 = tableRows[j].cells[id].textContent;
      v2 = tableRows[min].cells[id].textContent;

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

function MrTrie () {
  var id = this.cellIndex;
  var table = document.getElementById("T-1");
  var rows = table.rows;
  MrSort(rows, id);
}

function main () {

  color();
  removeIndex();

  //Cacher les notes\\
  document.getElementsByTagName("h1")[0].addEventListener("click", MrHide, false);

  //Trier les notes\\
  var columns = document.getElementById("T-1").getElementsByTagName("th");
  for (var i = 0; i < columns.length; ++i)
    columns[i].addEventListener("click", MrTrie, false);
}

window.addEventListener("load", main, false);

