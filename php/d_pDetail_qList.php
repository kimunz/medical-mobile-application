<?

/***************************************************************************************************/

  $hostname = "127.0.0.1";
  $username = "u385850038_admin"; 
  $dbname = "u385850038_hope";

  $connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
  $result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $p_ID = $_POST["p_ID"];

  $query = "SELECT * FROM QnA WHERE p_id='$p_ID' ORDER BY q_date DESC";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

  if($row){
    echo("Success");

    $query = "SELECT count(*) count FROM QnA WHERE p_id='$p_ID' ORDER BY q_date DESC";
    $result2 = mysql_query($query, $connect);
    $row2 = mysql_fetch_array($result2, MYSQL_BOTH);

    do{
      echo("/$row[q_date]/$row[body]/$row[is_it_read]/$row2[count]");
      } while($row = mysql_fetch_array($result, MYSQL_BOTH));
    }
    else{
    echo("Fail/Data is Null");
  }

  mysql_close($connect);
?>
