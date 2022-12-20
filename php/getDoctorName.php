<?

/***************************************************************************************************/

	$hostname = "127.0.0.1";
	$username = "u385850038_admin"; 
	$dbname = "u385850038_hope";

	$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
	$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/
	$d_id = $_GET["d_id"];

  $query = "SELECT d_name FROM doctor WHERE d_id = '$d_id'";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

	if($row){
		do{
			echo("$row[d_name]");
		}
		while($row = mysql_fetch_array($result, MYSQL_BOTH));
	}

	mysql_close($connect);
?>
