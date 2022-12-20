<?
/***************************************************************************************************/
   $hostname = "127.0.0.1";
   $username = "u385850038_admin";
   $dbname = "u385850038_hope";

   $connect = @mysql_connect($hostname, $username, $password) or die("Connecting Fail");
   $result = mysql_select_db($dbname, $connect);

/***************************************************************************************************/

   $ID = $_GET["id"];
   $PW = $_GET["pw"];

   if($ID == "" || $PW == ""){
 		die("Fail/element is null");
 	}

  	if( 0 < preg_match("/[ #\&\+\-%@=\/\\\:;,\.'\"\^`~\_|\!\?\*$#<>()\[\]\{\}]/i", $p_ID ) ){
      echo($ID);
  		die("Fail/special char added");
  	}

/***************************************************************************************************/

$sep = "nurse";

   $query = "SELECT id FROM login WHERE id='$ID' and pw='$PW' and sep='$sep'";
   $result = mysql_query($query, $connect);
   $row = mysql_fetch_array($result, MYSQL_BOTH);

if($row){
    echo("Success");
}
  mysql_close($connect);
?>
