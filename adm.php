<?php
include('db.php');
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); // Date in the past
header("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT"); // always modified
header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
header("Pragma: no-cache"); // HTTP/1.0
$islogged=0;
if (isset($_POST['f_password']) && $_POST['f_password']=="admin123")
 {
    setcookie("prv_adm",uniqid('',true));
    $islogged=1;
 }
if (isset($_COOKIE['prv_adm']))
 {
  $islogged=1;
 }
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Password management</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-family: Tahoma, Arial, sans-serif;
	font-size: 12px;
}
a.menu:hover, a.menu:active, a.menu:link, a.menu:visited {font-color:003366; font-weight:bold; margin-left:15px;margin-right:15px;}
-->
</style>
</head>

<body>
<?php
require("menu.frm");
?>
<br />
<br />
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td bgcolor="#EEEEEE">
    <?php
if ($islogged==0)
 {
	if(isset($_POST['f_password']))
	 {
	  echo("Wrong password. Access denied. <br><br>");
	 }
  include_once("pw.frm");
 }
 else
 {
  echo("Access granted.");
 }
?>
    </td>
  </tr>
</table>
</body>
</html>