<?

/***************************************************************************************************/

	$hostname = "127.0.0.1";
	$username = "u385850038_admin"; 
	$dbname = "u385850038_hope";

	$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
	$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/
  $p_id = $_GET["p_id"];
  $med_time = $_GET["med_time"];

  $query = "DELETE FROM medication WHERE p_id = '$p_id' AND med_time = '$med_time'";
  $result = mysql_query($query, $connect);

  $query = "SELECT * FROM medicationTemp WHERE med_time = '$med_time'";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

  if($row) {
    $query = "DELETE FROM medicationTemp WHERE p_id = '$p_id'";
    $result = mysql_query($query, $connect);
  }
  
	mysql_close($connect);
?>
