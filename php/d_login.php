<?

/***************************************************************************************************/

		$hostname = "127.0.0.1";
   	$username = "u385850038_admin"; 
  	$dbname = "u385850038_hope";

		$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
		$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

	$d_ID = $_POST["d_ID"];
	$d_PW = $_POST["d_PW"];

	if($d_ID == "" || $d_PW == ""){
		die("Fail : element is null");
	}

	if( 0 < preg_match("/[ #\&\+\-%@=\/\\\:;,\.'\"\^`~\_|\!\?\*$#<>()\[\]\{\}]/i", $d_ID ) ){
		die("Fail : special char added");
	}

/***************************************************************************************************/

$sep = "doctor";

	$query = "SELECT id FROM login WHERE id='$d_ID' and pw='$d_PW' and sep='$sep'";
	$result = mysql_query($query, $connect);

	$row = mysql_fetch_array($result, MYSQL_BOTH);

	if($row){
		echo("Success");

		//my Patient count
		$query = "SELECT COUNT(*) count FROM patient WHERE d_id=$row[id]";
		$result = mysql_query($query, $connect);
		$count_myPatient = mysql_fetch_array($result, MYSQL_BOTH);
		if($count_myPatient){
			echo("/$count_myPatient[count]");
		}
		else{
			echo("/0");
		}

		//not read QnA
		$query = "SELECT COUNT(*) count FROM QnA WHERE d_id=$row[id] and is_it_read=0";
		$result = mysql_query($query, $connect);
		$count_notReadQnA_Count = mysql_fetch_array($result, MYSQL_BOTH);
		if($count_notReadQnA_Count){
			echo("/$count_notReadQnA_Count[count]");
		}
		else{
			echo("/0");
		}

		//my Room Number List
		$query = "SELECT * FROM round WHERE d_id=$row[id]";
		$result = mysql_query($query, $connect);
		$round = mysql_fetch_array($result, MYSQL_BOTH);

		if($round) { echo("/$round[location]/$round[auto]/$round[current_move]/$round[order_list]"); }
		else {
				$query = "SELECT DISTINCT p_room FROM patient WHERE d_id=$row[id] ORDER BY p_room";
				$result = mysql_query($query, $connect);
				$list_roomNum = mysql_fetch_array($result, MYSQL_BOTH);
				if($list_roomNum){
				    echo("/0/0/0");
					do{
						echo("/$list_roomNum[p_room]");
					} while($list_roomNum = mysql_fetch_array($result, MYSQL_BOTH));
				}
				else{
					echo("/0/0/0/Null");
				}
		}

	 }
?>
