<?

/***************************************************************************************************/

	$hostname = "127.0.0.1";
	$username = "u385850038_admin";
	$dbname = "u385850038_hope";

	$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
	$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/
	$value = $_GET["value"];

	switch ($value) {
		case "up_1":
			$query = "SELECT * FROM patient ORDER BY p_id desc";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "down_1":
			$query = "SELECT * FROM patient ORDER BY p_id";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "up_2":
			$query = "SELECT * FROM patient ORDER BY p_name desc";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "down_2":
			$query = "SELECT * FROM patient ORDER BY p_name";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "up_3":
			$query = "SELECT * FROM patient ORDER BY gen desc";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "down_3":
			$query = "SELECT * FROM patient ORDER BY gen";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "up_4":
			$query = "SELECT * FROM patient ORDER BY p_room desc";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "down_4":
			$query = "SELECT * FROM patient ORDER BY p_room";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "up_5":
			$query = "SELECT * FROM patient ORDER BY disease desc";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "down_5":
			$query = "SELECT * FROM patient ORDER BY disease";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "up_6":
			$query = "SELECT * FROM patient ORDER BY age desc";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "down_6":
			$query = "SELECT * FROM patient ORDER BY age";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "room" :
			$room = $_GET["room"];
			$query = "SELECT * FROM patient WHERE p_room='$room'";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "name" :
			$name = $_GET["name"];
			$query = "SELECT * FROM patient WHERE p_name='$name'";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;

		case "search" :
			$room = $_GET["room"];
			$name = $_GET["name"];
			$query = "SELECT * FROM patient WHERE p_name='$name' and p_room='$room'";
			$result = mysql_query($query, $connect);
			$row = mysql_fetch_array($result, MYSQL_BOTH);
			break;
	}

	if($row){
		do{
			$getYear = date("Y");
			$age = $getYear - (int)($row[birth] / 10000) + 1;
			echo("$row[p_id]\n$row[d_id]\n$row[p_name]\n$row[gen]\n$row[p_room]\n$row[disease]\n$age\n$row[forbid_food]\n$row[birth]\n");
		}
		while($row = mysql_fetch_array($result, MYSQL_BOTH));
	}

	mysql_close($connect);
?>
