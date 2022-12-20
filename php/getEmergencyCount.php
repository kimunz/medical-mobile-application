<?

/***************************************************************************************************/

	$hostname = "127.0.0.1";
	$username = "u385850038_admin"; 
	$dbname = "u385850038_hope";

	$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
	$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $query = "SELECT count(*)count FROM emergency";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

	if($row){
			echo("$row[count]\n");
	}

	mysql_close($connect);
?>
