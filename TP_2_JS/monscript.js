function color() {

  var table = document.getElementById("T-1");
  var rows = table.getElementsByTagName("tr");

  for (var i = 1; i < rows.length; ++i) {
    if (i % 2 == 0)
      rows[i].className = "grayHeader"; 

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
  if (table.className.contains("hidden")) {
    table.classList.remove("hidden"); 
  } else {
    table.classList.add("hidden");  
  }
}

function isSorted(table, id) {
  var table = document.getElementById("T-1");
  var rows = table.getElementsByTagName("tr");
  var columns = table.getElementsByTagName("th");
  
}

function MrTrie () {
  var id = this.cellIndex;
  var table = document.getElementById("T-1");
  var rows = table.getElementsByTagName("tr");
  var tableData = [];

  for (var i = 1; i < rows.length; ++i) {
    var cells = rows[i].getElementsByTagName("td");
    tableData[i] = cells[id].textContent;
  }

  //isSorted(tableData);
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

