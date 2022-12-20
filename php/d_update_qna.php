<?

/***************************************************************************************************/

  $hostname = "127.0.0.1";
  $username = "u385850038_admin"; 
  $dbname = "u385850038_hope";

  $connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
  $result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $p_ID = $_POST["p_ID"];
  $q_date = $_POST["q_date"];

  $query = "UPDATE QnA SET is_it_read=1 WHERE p_ID='$p_ID' AND q_date='$q_date'";
  $result = mysql_query($query, $connect);

  echo("Success");

  mysql_close($connect);
?>
