<?

/***************************************************************************************************/

$hostname = "127.0.0.1";
$username = "u385850038_admin"; 
$dbname = "u385850038_hope";

$connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
$result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

	$d_ID = $_POST["d_ID"];
	$location = $_POST["location"];
	$order_list = $_POST["order_list"];
	$auto = $_POST["auto"];
	$current_move = $_POST["current_move"];

	$query = "SELECT * FROM round WHERE d_id='$d_ID'";
	$result = mysql_query($query, $connect);
	$row = mysql_fetch_array($result, MYSQL_BOTH);

	if($row) {
		$query = "UPDATE round SET location='$location', order_list='$order_list', auto=$auto, current_move=$current_move WHERE d_id='$d_ID'";
		$result = mysql_query($query, $connect);
	}
	else {
		$query = "INSERT INTO round(d_id, location, order_list, auto, current_move) VALUES ('$d_ID', '$location', '$order_list', $auto, $current_move)";
		$result = mysql_query($query, $connect);
	}

/***************************************************************************************************/

	function send_notification ($tokens, $message) {

			$url = 'https://fcm.googleapis.com/fcm/send';
			$server_key = "AAAAbRpXHr4:APA91bHaWgeFRXx9IEg8aWk6mKY-KW7ZdSv1nHo2BLk8R8DjRv0eR2baNFs7TseN41IwUmrMIvD07evih_d9QUd_0zJxL3MYLRvY6bhdZ0nVNyoZ7NVqx_gTuOTRHaCcN5LYlqoFzYpe";
			$fields = array
				(
				 'registration_ids' => $tokens,
				 'data' => $message
				);

			$headers = array
				(
				'Authorization:key =' . $server_key,
				'Content-Type: application/json'
				);

			$ch = curl_init();
			curl_setopt($ch, CURLOPT_URL, $url);
			curl_setopt($ch, CURLOPT_POST, true);
			curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
			curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
			curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
			$result = curl_exec($ch);
			if ($result === FALSE)
			{
				die('Curl failed: ' . curl_error($ch));
			}
	      		curl_close($ch);
	       		return $result;
	}

	$query = "SELECT token FROM token WHERE d_id = $d_ID";
  $result = mysql_query($query,$connect);
	$tokens = array();

  while($row = mysql_fetch_array($result,MYSQL_BOTH))
	{
		$tokens[] = $row[token];
	}


		$myMessage = "START";

	$message = array("message" => $myMessage);
	$message_status = send_notification($tokens, $message);

	if($result){
		echo("Success");
	}
	else{
		echo("Fail/FCM Error");
	}
?>
