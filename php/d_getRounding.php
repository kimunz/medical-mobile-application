<?

/***************************************************************************************************/

$hostname = "127.0.0.1";
$username = "u385850038_admin"; 
$dbname = "u385850038_hope";

$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

	$d_ID = $_POST["d_ID"];

	$query = "SELECT * FROM round WHERE d_id='$d_ID'";
	$result = mysql_query($query, $connect);
	$row = mysql_fetch_array($result, MYSQL_BOTH);

	if($row[location] != 0) {
		echo("$row[location]/$row[auto]");
	} else {
	    echo("nop");
	}
?>