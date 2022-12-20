<?

/***************************************************************************************************/

  $hostname = "127.0.0.1";
  $username = "u385850038_admin";
  $dbname = "u385850038_hope";

  $connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
  $result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $d_ID = $_POST["d_ID"];

  $query = "SELECT count(*) count FROM QnA WHERE d_ID='$d_ID' AND is_it_read=0";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

  if($row){
    echo("count/$row[count]");
  } else{
    echo("Fail/Data is Null");
  }

  mysql_close($connect);
?>
