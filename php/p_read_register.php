<?

/***************************************************************************************************/

		$hostname = "127.0.0.1";
   	$username = "u385850038_admin";
  	$dbname = "u385850038_hope";

		$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
		$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

		$p_ID = $_POST["p_ID"];
		$carte = $_POST["carte"];

		$query = "SELECT * FROM patientCondition WHERE p_ID='$p_ID' and carte='$carte' ORDER BY reg_date DESC, reg_time DESC";
		$result = mysql_query($query, $connect);
		$row = mysql_fetch_array($result, MYSQL_BOTH);

		if($row){
			echo("Success");
			do{
				echo("/$row[reg_date]/$row[reg_time]/$row[volume]");
			}while($row = mysql_fetch_array($result, MYSQL_BOTH));
		}
		else{
			echo("Fail/Data is Null");
		}

		mysql_close($connect);
?>
