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

   if($p_ID == "" || $p_PW == ""){
 		die("Fail/element is null");
 	}

  	if( 0 < preg_match("/[ #\&\+\-%@=\/\\\:;,\.'\"\^`~\_|\!\?\*$#<>()\[\]\{\}]/i", $p_ID ) ){
      echo($p_ID);
  		die("Fail/special char added");
  	}

/***************************************************************************************************/

$sep = "patient";

   $query = "SELECT id FROM login WHERE id='$p_ID' and pw='$p_PW' and sep='$sep'";
   $result = mysql_query($query, $connect);
   $row = mysql_fetch_array($result, MYSQL_BOTH);

if($row){
    echo("Success/");
    $query = "SELECT * FROM patient WHERE p_id='$row[id]'";
    $result = mysql_query($query, $connect);
    $row3 = mysql_fetch_array($result, MYSQL_BOTH);

    $query = "SELECT d_name FROM doctor WHERE d_id='$row3[d_id]'";
    $result = mysql_query($query, $connect);
    $row2 = mysql_fetch_array($result, MYSQL_BOTH);
    echo("$row3[p_id]/$row3[p_name]/$row3[d_id]/$row2[d_name]/$row3[gen]/$row3[p_room]/$row3[disease]/$row3[forbid_food]/$row3[birth]");
} else{
      echo("Fail/Login Error");
}

  mysql_close($connect);
?>
