<?

/***************************************************************************************************/

	$hostname = "127.0.0.1";
	$username = "u385850038_admin";
	$dbname = "u385850038_hope";

	$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
	$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/
	$p_id = $_GET["p_id"];
  $query = "SELECT * FROM patient WHERE p_id='$p_id'";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);
	if($row){
		do{
			echo("$row[p_id]\n$row[d_id]\n$row[p_name]\n$row[gen]\n$row[p_room]\n$row[disease]\n$row[age]\n$row[forbid_food]\n$row[birth]\n");
		}
		while($row = mysql_fetch_array($result, MYSQL_BOTH));
	}

	mysql_close($connect);
?>
