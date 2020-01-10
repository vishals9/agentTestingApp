function validate() {
	var name = document.getElementById("name").value;
	if (name == '') {
		alert('Please enter a valid name.');
		return false;
	} else {
		return true;
	}
}

function cloudant() {
	document.getElementById('cloudant').style.display = 'block';
	document.getElementById('db2').style.display = 'none';
	document.getElementById('mssql').style.display = 'none';
	document.getElementById('mysql').style.display = 'none';
	document.getElementById('oracle').style.display = 'none';
	document.getElementById('postgres').style.display = 'none';
	document.getElementById('redis').style.display = 'none';
	document.getElementById('lettuce').style.display = 'none';
}
function db2() {
	document.getElementById('cloudant').style.display = 'none';
	document.getElementById('db2').style.display = 'block';
	document.getElementById('mssql').style.display = 'none';
	document.getElementById('mysql').style.display = 'none';
	document.getElementById('oracle').style.display = 'none';
	document.getElementById('postgres').style.display = 'none';
	document.getElementById('redis').style.display = 'none';
	document.getElementById('lettuce').style.display = 'none';
}
function mssql() {
	document.getElementById('cloudant').style.display = 'none';
	document.getElementById('db2').style.display = 'none';
	document.getElementById('mssql').style.display = 'block';
	document.getElementById('mysql').style.display = 'none';
	document.getElementById('oracle').style.display = 'none';
	document.getElementById('postgres').style.display = 'none';
	document.getElementById('redis').style.display = 'none';
	document.getElementById('lettuce').style.display = 'none';
}
function mysql() {
	document.getElementById('cloudant').style.display = 'none';
	document.getElementById('db2').style.display = 'none';
	document.getElementById('mssql').style.display = 'none';
	document.getElementById('mysql').style.display = 'block';
	document.getElementById('oracle').style.display = 'none';
	document.getElementById('postgres').style.display = 'none';
	document.getElementById('redis').style.display = 'none';
	document.getElementById('lettuce').style.display = 'none';
}
function oracle() {
	document.getElementById('cloudant').style.display = 'none';
	document.getElementById('db2').style.display = 'none';
	document.getElementById('mssql').style.display = 'none';
	document.getElementById('mysql').style.display = 'none';
	document.getElementById('oracle').style.display = 'block';
	document.getElementById('postgres').style.display = 'none';
	document.getElementById('redis').style.display = 'none';
	document.getElementById('lettuce').style.display = 'none';
}
function postgres() {
	document.getElementById('cloudant').style.display = 'none';
	document.getElementById('db2').style.display = 'none';
	document.getElementById('mssql').style.display = 'none';
	document.getElementById('mysql').style.display = 'none';
	document.getElementById('oracle').style.display = 'none';
	document.getElementById('postgres').style.display = 'block';
	document.getElementById('redis').style.display = 'none';
	document.getElementById('lettuce').style.display = 'none';
}
function redis() {
	document.getElementById('cloudant').style.display = 'none';
	document.getElementById('db2').style.display = 'none';
	document.getElementById('mssql').style.display = 'none';
	document.getElementById('mysql').style.display = 'none';
	document.getElementById('oracle').style.display = 'none';
	document.getElementById('postgres').style.display = 'none';
	document.getElementById('redis').style.display = 'block';
	document.getElementById('lettuce').style.display = 'none';
}
function lettuce() {
	document.getElementById('cloudant').style.display = 'none';
	document.getElementById('db2').style.display = 'none';
	document.getElementById('mssql').style.display = 'none';
	document.getElementById('mysql').style.display = 'none';
	document.getElementById('oracle').style.display = 'none';
	document.getElementById('postgres').style.display = 'none';
	document.getElementById('redis').style.display = 'none';
	document.getElementById('lettuce').style.display = 'block';
}