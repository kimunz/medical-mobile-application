<?

/***************************************************************************************************/

		$hostname = "127.0.0.1";
   	$username = "u385850038_admin";
  	$dbname = "u385850038_hope";

		$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
		$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

    $p_id = $_GET["p_id"];
		$p_name = $_GET["p_name"];
    $gen = $_GET["gen"];
    $birth = $_GET["birth"];
    $disease = $_GET["disease"];
    $p_room = $_GET["p_room"];
		$d_id = $_GET["d_id"];
    $forbid_food = $_GET["forbid_food"];

    $query = "SELECT * FROM patient WHERE p_id='$p_id'";
    $result = mysql_query($query, $connect);
    $row = mysql_fetch_array($result, MYSQL_BOTH);

    if(!$row) {
      echo("Success");
      $query = "INSERT INTO login VALUES ('$p_id', '$birth')";
      $result = mysql_query($query, $connect);

  		$query = "INSERT INTO patient VALUES ('$p_id','$d_id','$p_name','$gen', $p_room, '$disease', '$forbid_food', '$birth')";
  		$result = mysql_query($query, $connect);

      $query = "DELETE FROM round WHERE d_id='$row[d_id]'";
      $result = mysql_query($query, $connect);
    }
		else{
			echo("Fail");
		}

		mysql_close($connect);
?>
