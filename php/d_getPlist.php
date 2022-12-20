<?

/***************************************************************************************************/

  $hostname = "127.0.0.1";
  $username = "u385850038_admin";
  $dbname = "u385850038_hope";

  $connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
  $result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $d_ID = $_POST["d_ID"];

  $query = "SELECT * FROM patient WHERE d_ID='$d_ID'";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

  if($row){
    echo("Success");
    do{
      echo("/$row[p_id]/$row[p_name]/$row[p_room]/$row[birth]/$row[gen]/$row[disease]/$row[forbid_food]");
      } while($row = mysql_fetch_array($result, MYSQL_BOTH));
    }
    else{
    echo("Fail/Data is Null");
  }

  mysql_close($connect);
?>
