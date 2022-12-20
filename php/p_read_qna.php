<?

/***************************************************************************************************/

		$hostname = "127.0.0.1";
   	$username = "u385850038_admin";
  	$dbname = "u385850038_hope";

		$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
		$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

		$p_ID = $_POST["p_ID"];

		$query = "SELECT q_date,body,is_it_read FROM QnA WHERE p_ID='$p_ID' order by q_date desc";
		$result = mysql_query($query, $connect);
		$row = mysql_fetch_array($result, MYSQL_BOTH);

		if($row){
			echo("Success");
			do{
				echo("/$row[q_date]/$row[body]/$row[is_it_read]");
			}while($row = mysql_fetch_array($result, MYSQL_BOTH));
		}
		else{
			echo("Fail/Data is Null");
		}

		mysql_close($connect);
?>
