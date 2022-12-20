<?

/***************************************************************************************************/

  $hostname = "127.0.0.1";
  $username = "u385850038_admin"; 
  $dbname = "u385850038_hope";

  $connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
  $result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $p_ID = $_GET["p_id"];
  $d_ID = $_GET["d_id"];

  $query = "DELETE FROM patient WHERE p_id='$p_ID'";
  $result = mysql_query($query, $connect);

  $query = "DELETE FROM rount WHERE d_id='$d_ID'";
  $result = mysql_query($query, $connect);
  
  mysql_close($connect);
?>
