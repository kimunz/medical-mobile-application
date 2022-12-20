<?

/***************************************************************************************************/

	$hostname = "127.0.0.1";
	$username = "u385850038_admin";
	$dbname = "u385850038_hope";

	$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
	$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/
	$id = $_GET["id"];
	$carte = $_GET["carte"];

  $query = "SELECT DISTINCT reg_date FROM patientCondition WHERE p_id = '$id' and carte = '$carte'";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

	if($row){
		do{
			echo("$row[reg_date]\n");
	}
		while($row = mysql_fetch_array($result, MYSQL_BOTH));
	}
	mysql_close($connect);
?>
