<?

/***************************************************************************************************/

		$hostname = "127.0.0.1";
   	$username = "u385850038_admin";
  	$dbname = "u385850038_hope";

		$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
		$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

		$p_ID = $_POST["p_ID"];
    $p_name = $_POST["p_name"];
		$p_room = $_POST["p_room"];
		$disease = $_POST["disease"];

    $query = "INSERT INTO emergency VALUES ('$p_ID','$p_name',$p_room,'$disease')";
    $result = mysql_query($query, $connect);

    if ($result) {
      echo("Success/");
    }
    else {
      echo ("Fail/");
    }

		mysql_close($connect);
?>
