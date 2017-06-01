package com.jcomeau.imagetagging;
import java.awt.*;
import java.text.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
/* from complaints table we need:
 patient_id, left_x, top_y, width, height, front_back, complaint, tstamp
*/
public class Complaint extends Box implements MouseListener {
 static final long serialVersionUID = 0L;
 Date startTreatment = null;
 Date complaintDate = null;
 public static SimpleDateFormat dateFormat =
  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 public static String sampleDate = "2011-12-31 12:00:00";
 public static String timeDefault = " 00:00:00";
 public Ellipse2D.Double selection = null;
 Timeline timeline = null;
 JTextArea textarea = null;
 DoctorApplet applet = null;
 static final int MAX_DEPTH = 10;  // nested containers
 public Complaint(Container parent,
  String text, String beginning, String when,
  Double x, Double y, Double width, Double height, DoctorApplet applet) {
  super(BoxLayout.Y_AXIS);
  if (parent != null) setMinimumSize(new Dimension(parent.getWidth(), 1));
  selection = new Ellipse2D.Double(x, y, width, height);
  (textarea = new JTextArea(text)).setLineWrap(true);
  setAlignmentX(LEFT_ALIGNMENT);  // Complaint's alignment within applet
  try {
   startTreatment = parseDate(beginning);
   complaintDate = parseDate(when);
  } catch (Exception badDate) {
   Common.debug("Cannot parse date: " + badDate);
  }
  add(timeline = new Timeline(startTreatment, complaintDate));
  textarea.setAlignmentX(LEFT_ALIGNMENT);
  textarea.setEditable(false);
  textarea.setOpaque(false);
  add(textarea);
  this.applet = applet;
  addMouseListener(this);
  timeline.addMouseListener(this);
  textarea.addMouseListener(this);
 }
 public Date parseDate(String dateString) throws Exception {
  if (dateString.length() == sampleDate.length() - timeDefault.length())
   dateString += timeDefault;
  Date date = dateFormat.parse(dateString, new ParsePosition(0));
  if (date == null) throw new Exception("Bad date string: " + dateString);
  return date;
 }
 public Complaint(String text) {
  this(null, text, sampleDate, now(), 10.0, 10.0, 10.0, 10.0, null);
 }
 public Complaint() {
  this("(Unspecified complaint)");
 }
 public static String now() {
  return dateString(new Date());
 }
 public static String dateString(Date date) {
  return dateFormat.format(date, new StringBuffer(),
   new FieldPosition(0)).toString();
 }
 public void mouseClicked(MouseEvent event) {}
 public void mousePressed(MouseEvent event) {}
 public void mouseReleased(MouseEvent event) {}
 public void mouseEntered(MouseEvent event) {
  //Common.debug("mouse entered " + this);
  if (applet instanceof DoctorApplet) {
   applet.panel.darken(selection, applet.DARKER);
  } else {
   Common.debug("not an applet: " + applet);
  }
 }
 public void mouseExited(MouseEvent event) {
  //Common.debug("mouse exited " + this);
  if (applet instanceof DoctorApplet) {
   applet.panel.darken(selection, applet.LIGHTER);
  }
 }
 public static void main(String args[]) {
  Common.init();
  JFrame container = new JFrame("Complaint Test");
  container.setSize(240, 480);
  container.setLayout(new BoxLayout(container.getContentPane(),
   BoxLayout.Y_AXIS));
  container.getContentPane().add(new JLabel("Test of complaint timeline"));
  container.getContentPane().add(new Complaint());
  container.getContentPane().add(new Complaint("some really," +
   " really, really, really," +
   " really, really, really," +
   " really, really, really," +
   " really, really, really," +
   " undoubtedly long text"));
  container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  container.setVisible(true);
  Common.debug("Dimensions: " + container.getSize());
 }
}
