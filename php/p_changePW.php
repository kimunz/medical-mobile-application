<?
/***************************************************************************************************/
   $hostname = "127.0.0.1";
   $username = "u385850038_admin";
   $dbname = "u385850038_hope";

   $connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
   $result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

   $p_ID = $_POST["p_ID"];
   $p_PW = $_POST["p_PW"];
   $p_newPW = $_POST["p_newPW"];


$sep = "patient";

   $query = "SELECT * FROM login WHERE id='$p_ID' and pw='$p_PW' and sep='$sep'";
   $result = mysql_query($query, $connect);
   $row = mysql_fetch_array($result, MYSQL_BOTH);

if($row){
    echo("Success/");
    $query = "UPDATE login set pw = '$p_newPW' WHERE id='$row[id]' and sep='$sep'";
    $result = mysql_query($query, $connect);
} else{
      echo("Fail/pw Error");
}

  mysql_close($connect);
?>
