<?
/***************************************************************************************************/

$hostname = "127.0.0.1";
$username = "u385850038_admin"; 
$dbname = "u385850038_hope";

$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

$p_ID = $_POST["p_id"];

$query = "SELECT * FROM patient WHERE p_id ='$p_ID'";
$result = mysql_query($query, $connect);
$row = mysql_fetch_array($result, MYSQL_BOTH);

if($row)
	echo = ("$row[p_name]/$row[gen]/$row[birth]/$row[p_room]/$row[disease]");
else
	echo = ("Null");

mysql_close($connect);
?>