<?

/***************************************************************************************************/

	$hostname = "127.0.0.1";
	$username = "u385850038_admin";
	$dbname = "u385850038_hope";

	$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
	$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $med_time = $_GET["med_time"];

  $query = "SELECT * FROM medicationTemp WHERE med_time = '$med_time'";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

  if(!$row) {
    echo("존재 X");

    $query = "DELETE FROM medicationTemp";
    mysql_query($query, $connect);

    $query = "SELECT * FROM medication WHERE med_time = '$med_time'";
    $result = mysql_query($query, $connect);
    $row = mysql_fetch_array($result, MYSQL_BOTH);

    if($row){
      echo("row잇슴");
        do{
				    $query = "INSERT INTO medicationTemp VALUES('$row[p_id]', '$row[p_name]', $row[p_room], '$row[disease]', '$row[med_time]')";
				    $result2 = mysql_query($query, $connect);
  	   } while($row = mysql_fetch_array($result, MYSQL_BOTH));
  	 }
	 } else echo("존재 O");
	mysql_close($connect);

?>
