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
	$current_move = $_POST["current_move"];

	$query = "UPDATE round SET location='$location', current_move=$current_move WHERE d_id='$d_ID'";
	$result = mysql_query($query, $connect);
	$row = mysql_affected_rows();//get count how many record is affected

	if($row){
		$check = 1;
	}
	else{
		echo("Fail/Update Error");
	}

/***************************************************************************************************/

	//
	// function send_notification ($tokens, $message)
	// {
	// 	$url = 'https://fcm.googleapis.com/fcm/send';
	// 	$fields = array
	// 		(
	// 		 'registration_ids' => $tokens,
	// 		 'data' => $message
	// 		);
	//
	// 	$headers = array
	// 		(
	// 		'Authorization:key =' . GOOGLE_API_KEY,
	// 		'Content-Type: application/json'
	// 		);
	//
	// 	$ch = curl_init();
	// 	curl_setopt($ch, CURLOPT_URL, $url);
	// 	curl_setopt($ch, CURLOPT_POST, true);
	// 	curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
	// 	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	// 	curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
	// 	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	// 	curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
	// 	$result = curl_exec($ch);
	// 	if ($result === FALSE)
	// 	{
	// 		die('Curl failed: ' . curl_error($ch));
	// 	}
  //     		curl_close($ch);
  //      		return $result;
	// }
	//
	// include_once './setup.php';
	// $query = "Select token From Patient where d_No = $d_No";
  //       $result = mysql_query($query,$connect);
	// $tokens = array();
	//
  //       while($row = mysql_fetch_array($result,MYSQL_BOTH))
	// {
	// 	$tokens[] = $row[token];
	// }
	//
	// $myMessage = $_POST['message'];
	//
  //       $query = "Select location,orderlist From Round where d_No = $d_No";
  //       $result = mysql_query($query,$connect);
  //       $str = mysql_fetch_array($result, MYSQL_BOTH);
	//
	//
	// if ($myMessage == "")
	// {
	// 	$myMessage = $str[location]."/".$str[orderlist];
	// }
	//
	// $message = array("message" => $myMessage);
	// $message_status = send_notification($tokens,$message);
	//
	// if($result && $check==1){
	// 	echo("Success");
	// }
	// else{
	// 	echo("Fail/Insert Error");
	// }
?>
