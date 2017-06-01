package com.jcomeau.imagetagging;
import org.apache.commons.lang.StringUtils;
import java.util.*;
import java.net.*;
import java.io.*;

public class ConnectClient {
 public static Hashtable<String, String>[] select(String table, String where,
  String fields[], URL codebase) throws Exception {
  String postdata = "query=select&table=" + table +
   "&where=" + URLEncoder.encode(where, "UTF-8");
  for (int i = 0; i < fields.length; i++) {
   postdata += "&" + URLEncoder.encode(fields[i], "UTF-8") + "=";
  }
  Common.debug("postdata: " + postdata);
  String[] result = sendQuery(postdata, codebase);
  @SuppressWarnings("unchecked")
  Hashtable<String, String>[] selectResults =
   (Hashtable<String, String>[])new Hashtable<?, ?>[result.length - 1];
  for (int i = 1; i < result.length; i++) {
   selectResults[i - 1] = new Hashtable<String, String>();
   String[] row = StringUtils.split(result[i], '\t');
   for (int j = 0; j < row.length; j++) {
    selectResults[i - 1].put(fields[j], row[j]);
   }
  }
  return selectResults;
 }
 public static boolean insert(String table,
  Hashtable items, URL codebase) throws Exception {
  boolean successful = false;
  String postdata = "query=insert&table=" + table;
  String fields[] = array(items.keys(), items.size());
  String values[] = array(items.elements(), items.size());
  for (int i = 0; i < fields.length; i++) {
   postdata += "&" + URLEncoder.encode(fields[i], "UTF-8") +
    "=" + URLEncoder.encode(values[i], "UTF-8");
  }
  Common.debug("postdata: " + postdata);
  if (sendQuery(postdata, codebase)[0] == "Insert successful")
   successful = true;
  return successful;
 }
 public static String[] sendQuery(String postdata, URL codebase) {
  String result = "", line = "";
  try {
   URL postURL = new URL(codebase, "query.php");
   URLConnection connection = postURL.openConnection();
   connection.setDoOutput(true);
   OutputStreamWriter output = new OutputStreamWriter(
    connection.getOutputStream());
   output.write(postdata);
   output.flush();
   BufferedReader input = new BufferedReader(
    new InputStreamReader(connection.getInputStream()));
    while ((line = input.readLine()) != null) {
     if (line.length() > 0) result += line + "\n";
     Common.debug("returned: " + line);
    }
    output.close();
    input.close();
  } catch (Exception error) {
   Common.debug("Database error: " + error);
  }
  return StringUtils.split(result.substring(0, result.length()), '\n');
 }
 public static Hashtable fillInDefaults(
  Hashtable<String, String> items, String[][] defaults) {
  String item[] = null;
  for (int i = 0; i < defaults.length; i++) {
   item = defaults[i];
   if (!items.containsKey(item[0])) {
    items.put(item[0], item[1]);
   }
  }
  return items;
 } 
 public static String[] array(Enumeration items, int length) {
  String[] result = new String[length];
  int i = 0;
  String item;
  while (items.hasMoreElements()) {
   item = (String)items.nextElement();
   result[i++] = item;
  }
  return result;
 }
 public static void main (String[] args) {
  try {
   URL codebase = new URL(TaggingApplet.DEFAULT_CODE_BASE);
   Common.debug("testing 'insert'");
   insert("complaints", fillInDefaults(new Hashtable<String, String>(),
    new String[][] {{"left_x", "20"}, {"top_y", "30"},
     {"width", "22"}, {"height", "33"},
     {"complaint", "unspecified problem"}}), codebase);
   Common.debug("testing 'select'");
   String fields[] = new String[] {"patient_id", "complaint"}, line = null;
   Hashtable<String, String>[] complaints =
    select("complaints", "patient_id = '10'", fields, codebase);
   for (int i = 0; i < complaints.length; i++) {
    line = "";
    for (int j = 0; j < fields.length; j++) {
     line += ", " + fields[j] + ": " + complaints[i].get(fields[j]);
    }
    System.out.println(line.substring(2));
   }
  } catch (Exception whatever) {
   Common.debug("database error: " + whatever);
  }
 }
}
