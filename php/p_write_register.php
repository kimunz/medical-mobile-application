<?

/***************************************************************************************************/

		$hostname = "127.0.0.1";
   	$username = "u385850038_admin";
  	$dbname = "u385850038_hope";

		$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
		$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

		$p_ID = $_POST["p_ID"];
    $reg_date = $_POST["reg_date"];
		$reg_time = $_POST["reg_time"];
		$carte = $_POST["carte"];
    $volume = $_POST["volume"];

		$query = "INSERT INTO patientCondition VALUES ('$p_ID','$reg_date','$reg_time','$carte',$volume)";
		$result = mysql_query($query, $connect);

		if($result){
			echo("Success");

			$query = "SELECT * FROM patientCondition WHERE p_ID='$p_ID' and carte='$carte' ORDER BY reg_date DESC, reg_time DESC";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);

			if($row){
				do{
					echo("/$row[reg_date]/$row[reg_time]/$row[volume]");
				}while($row = mysql_fetch_array($result, MYSQL_BOTH));
			}
		}
		else{
			echo("Fail");
		}
		mysql_close($connect);
?>
