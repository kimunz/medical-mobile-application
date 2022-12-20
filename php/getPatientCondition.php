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
	$reg_date = $_GET["reg_date"];

  $query = "SELECT * FROM patientCondition WHERE p_id = '$id' and carte = '$carte' and reg_date = '$reg_date'";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

	if($row){
		do{
			echo("$row[reg_date]\n$row[reg_time]\n$row[carte]\n$row[volume]\n");
	}
		while($row = mysql_fetch_array($result, MYSQL_BOTH));
	}
	mysql_close($connect);
?>
