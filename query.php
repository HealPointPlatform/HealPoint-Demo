<?php
 function findhome() {
  $dir = $_SERVER["DOCUMENT_ROOT"];
  while (strlen($dir) > 1 && !file_exists($dir . PATH_SEPARATOR . ".netrc")) {
   $dir = dirname($dir);
  }
  if (strlen($dir) <= 1) $dir = "/home/roiexp";
  return $dir;
 }
 function netrc($hostname) {
  $homedir = getenv("HOME") ? getenv("HOME") : findhome();
  $netrc = file($homedir . DIRECTORY_SEPARATOR . ".netrc");
  foreach ($netrc as $line) {
   $credentials = preg_split("/\s/", trim($line));
   if ($credentials[1] == $GLOBALS["database"]) {
    $login = array("db" => $credentials[1], "login" => $credentials[3],
     "password" => $credentials[5]);
   } elseif (isset($login) && $credentials[3] == $login["login"] &&
    $credentials[5] == $login["password"]) {
    $login["server"] = $credentials[1];
   }
  }
  return $login;
 }
 function query($query) {
  $credentials = netrc($GLOBALS["database"]);
  mysql_connect($credentials["server"], $credentials["login"],
   $credentials["password"]);
  mysql_select_db($credentials["db"]);
  $fields = NULL;
  $result = mysql_query($query);
  error_log("query: ${query}, result: ${result}");
  return $result;
 }
 function extract_post($key) {
  if (array_key_exists($key, $_POST)) {
   $value = $_POST[$key];
   unset($_POST[$key]);
   return $value;
  } else {
   return "";
  }
 }
 function removeslashes($string) {
  return get_magic_quotes_gpc() ? stripslashes($string) : $string;
 }
 function remove_crlf($string) {
  return str_replace(array("\r", "\n"), " ", $string);
 }
 function dispatch_query() {
  $table = extract_post("table");
  $query = extract_post("query");
  $where = extract_post("where");
  error_log("query=$query, table=$table, where=$where");
  if ($query == "insert") {
   $sql = "INSERT INTO $table (" . implode(", ", array_keys($_POST)) .
    ") VALUES ('" . implode("', '", array_values($_POST)) . "')";
  } elseif ($query == "select") {
   $sql = "SELECT " . implode(", ", array_keys($_POST)) .
    " FROM $table";
   if ($where) $sql .= " WHERE " . removeslashes(urldecode($where));
  } else {
   error_log("only 'select' and 'insert' supported");
   return false;
  }
  $result = query($sql);
  if ($query == "insert" && $result) {
   print "Insert successful\r\n";
  } elseif ($query == "select" && $result) {
   while ($row = mysql_fetch_assoc($result)) {
    if (!$fields) {
     $fields = implode("\t", array_keys($row));
     print $fields . "\r\n";
    }
    $values = implode("\t", array_map(remove_crlf, (array_values($row))));
    print $values . "\r\n";
   }
  }
  //print "query was: ${query}";
  return ($result);
 }
 header("Content-type: text/plain");
 $GLOBALS["database"] = "roiexp_med";
 $GLOBALS["sqlserver"] = "mysql4.loosefoot.com";
 if (!dispatch_query()) error_log("no valid query");
?>
