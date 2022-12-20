<?

/***************************************************************************************************/

  $hostname = "127.0.0.1";
  $username = "u385850038_admin"; 
  $dbname = "u385850038_hope";

  $connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
  $result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $d_ID = $_POST["d_ID"];

  $query = "SELECT * FROM QnA WHERE d_ID='$d_ID' AND is_it_read=0";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

  if($row){
    echo("Success");
    do{
      $query = "SELECT * FROM patient WHERE p_id='$row[p_id]'";
      $result2 = mysql_query($query, $connect);
      $row2 = mysql_fetch_array($result2, MYSQL_BOTH);

      echo("/$row[p_id]/$row2[p_name]/$row2[p_room]/$row[q_date]/$row[body]");
      } while($row = mysql_fetch_array($result, MYSQL_BOTH));
    }
    else{
    echo("Fail/Data is Null");
  }

  mysql_close($connect);
?>
