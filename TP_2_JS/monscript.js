function color() {

  var table = document.getElementById("T-1");
  var rows = table.getElementsByTagName("tr");

  for (var i = 1; i < rows.length; ++i) {
    if (i % 2 == 0)
      rows[i].className = "gray"; 

    var cells = rows[i].getElementsByTagName("td");
// //     al(cells[5]);
    if (cells[5].textContent < 8) {
	cells[5].className = "badGrade";

    } else if (cells[5].textContent < 10) {
	cells[5].className = "bofGrade";

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

function main () {
  color();
  removeIndex();
}

window.addEventListener("load", main, false);