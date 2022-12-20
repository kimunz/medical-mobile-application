<?

/***************************************************************************************************/

$hostname = "127.0.0.1";
$username = "u385850038_admin";
$dbname = "u385850038_hope";

$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

	$p_ID = $_POST["p_ID"];
  $d_ID = $_POST["d_ID"];
	$token = $_POST["token"];

/***************************************************************************************************/

$query = "SELECT * FROM token WHERE p_id = '$p_ID'";
$result = mysql_query($query, $connect);
$row = mysql_fetch_array($result, MYSQL_BOTH);

if($row) {
  $query = "UPDATE token SET d_id = '$d_ID', token = '$token' WHERE p_id ='$p_ID'";
  $result = mysql_query($query, $connect);
} else {
  $query = "INSERT INTO token VALUES('$p_ID', '$d_ID', '$token')";
  $result = mysql_query($query, $connect);
}

mysql_close($connect);
?>
