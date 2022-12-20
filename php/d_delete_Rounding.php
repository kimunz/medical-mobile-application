<?
/***************************************************************************************************/

$hostname = "127.0.0.1";
$username = "u385850038_admin";
$dbname = "u385850038_hope";

$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

	$d_ID = $_POST["d_ID"];

	$query = "UPDATE round SET location='0' WHERE d_id='$d_ID'";
	$result = mysql_query($query, $connect);
	$row = mysql_affected_rows();//get count how many record is affected

	if($row){
		echo("Success");
	}
	else{
		echo("Fail/Delete Error");
	}

	mysql_close($connect);
?>
