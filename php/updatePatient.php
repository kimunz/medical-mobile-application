<?

/***************************************************************************************************/

  $hostname = "127.0.0.1";
  $username = "u385850038_admin";
  $dbname = "u385850038_hope";

  $connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
  $result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

  $p_ID = $_GET["p_id"];
  $p_room = $_GET["p_room"];
  $d_ID = $_GET["d_id"];
  $forbid_food = $_GET["forbid_food"];

  $query = "SELECT * FROM doctor WHERE d_id='$d_ID'";
  $result = mysql_query($query, $connect);
  $row = mysql_fetch_array($result, MYSQL_BOTH);

  if(!$row) {
    echo("fail");
  }
  else {
    $query = "SELECT * FROM patient WHERE p_id='$p_ID'";
    $result = mysql_query($query, $connect);
    $row = mysql_fetch_array($result, MYSQL_BOTH);

    if($row[p_room] != $p_room || $row[d_id] != $d_ID) {
      //$row[d_id]랑 $d_id 둘 다 삭제!
      $query = "DELETE FROM round WHERE d_id='$row[d_id]'";
      $result = mysql_query($query, $connect);

      $query = "DELETE FROM round WHERE d_id='$d_id'";
      $result = mysql_query($query, $connect);

      $query = "UPDATE patient SET d_id='$d_ID', p_room='$p_room', forbid_food='$forbid_food' WHERE p_id='$p_ID'";
      $result = mysql_query($query, $connect);
    }
  }
  mysql_close($connect);
?>
