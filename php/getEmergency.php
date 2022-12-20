<?

/***************************************************************************************************/

	$hostname = "127.0.0.1";
	$username = "u385850038_admin"; 
	$dbname = "u385850038_hope";

	$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
	$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $query = "SELECT * FROM emergency";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

	if($row){
		do{
			echo("$row[p_name]\n$row[p_room]\n$row[disease]\n");
	}
		while($row = mysql_fetch_array($result, MYSQL_BOTH));
	}
  $query = "DELETE FROM emergency";
  mysql_query($query, $connect);

	mysql_close($connect);
?>
