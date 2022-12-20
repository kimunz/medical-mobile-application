<?

/***************************************************************************************************/

	$hostname = "127.0.0.1";
	$username = "u385850038_admin";
	$dbname = "u385850038_hope";

	$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
	$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/
  $p_id = $_GET["p_id"];
  $hour = $_GET["hour"];
  $min = $_GET["min"];
  $med_time = "$hour:$min";
	
  $query = "SELECT * FROM patient WHERE p_id = '$p_id'";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

	if($row){
		$query = "INSERT INTO medication VALUES('$p_id', '$row[p_name]', $row[p_room], '$row[disease]', '$med_time')";
		$result = mysql_query($query, $connect);
        
        $query = "SELECT * FROM medicationTemp WHERE med_time='$med_time'";
        $result = mysql_query($query, $connect);
        $row2 = mysql_fetch_array($result, MYSQL_BOTH);
        
        if($row2) {
            $query = "INSERT INTO medicationTemp VALUES('$row[p_id]', '$row[p_name]', $row[p_room], '$row[disease]', '$med_time')";
				    $result2 = mysql_query($query, $connect);
        }
		echo("Success");
	} else { echo("fail"); }

	mysql_close($connect);
?>
