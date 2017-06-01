package com.jcomeau.imagetagging;
import java.io.*;
import java.util.*;
import java.net.*;
public class Common {
 public static int DEBUGLEVEL = 0;
 public static String buildDate = null;
 public static String host = "localhost";
 public static boolean debug(String message, int level) {
  if (DEBUGLEVEL > 0 && level <= DEBUGLEVEL) {
   System.err.println(message);
   return true;
  }
  return false;
 }
 public static boolean debug(String message) {
  return debug(message, 1);
 }
 public static void init() {
  DEBUGLEVEL = local() ? 1 : 0;
  buildDate = getResourceAsString("/timestamp.txt").trim();
  debug("local: " + local());
  debug("Date of this build: " + buildDate);
 }
 public static boolean local() {
  return (host == null || host.equals("tagging") || host.equals("localhost"));
 }
 public static String getResourceAsString(String path) {
  String line = null;
  try {
   BufferedReader instream = new BufferedReader(new InputStreamReader(
    Common.class.getResourceAsStream(path)));
   line = instream.readLine();
   instream.close();
  } catch (Exception whatever) {
   System.err.println("Failed reading " + path + ": " + whatever);
  }
  return line;
 }
 public static void main(String args[]) {
  init();
  System.err.println("DEBUGLEVEL: " + DEBUGLEVEL);
 }
}
