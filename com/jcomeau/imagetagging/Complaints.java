package com.jcomeau.imagetagging;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
public class Complaints extends JScrollPane {
 static final long serialVersionUID = 0L;
 static Complaints instance = null;
 int patientId;
 static DoctorApplet applet = null;
 static URL codebase = null;
 public Box box = null;
 static final boolean setMostRecentVisible = true;
 String fields[] = new String[] {
  "left_x", "top_y", "width", "height", "complaint", "tstamp"
 };
 String timelineFields[] = new String[] {
  "start_treatment", "current_date"
 };
 public Complaints(int patientId) {
  super(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_AS_NEEDED);
  super.add(box = new Box(BoxLayout.Y_AXIS));
  this.patientId = patientId;
  this.instance = this;
  setViewportView(box);
 }
 void init() {
  try {
   Hashtable<String, String>[] complaints =
    ConnectClient.select("complaints", "patient_id = '" + patientId + "'",
    fields, codebase);
   Hashtable<String, String>[] patient =
    ConnectClient.select("timeline", "patient_id = '" + patientId + "'",
    timelineFields, codebase);
   Complaint complaint;
   String beginning = patient[0].get("start_treatment");
   Common.debug("Started treatment: " + beginning);
   for (Hashtable<String, String> hashtable : complaints) {
    Common.debug("Complaint date: " + hashtable.get("tstamp"));
    box.add(complaint = new Complaint(box,
     hashtable.get("complaint"), beginning, hashtable.get("tstamp"),
     Double.parseDouble(hashtable.get("left_x")),
     Double.parseDouble(hashtable.get("top_y")),
     Double.parseDouble(hashtable.get("width")),
     Double.parseDouble(hashtable.get("height")),
     applet));
   }
   revalidate();
  } catch (Exception whatever) {
   Common.debug("Failed loading complaints: " + whatever);
  }
 }
 public Component add(Component component) {
  return box.add(component);
 }
 public static void showOnlyIntersecting(Rectangle2D rectangle) {
  if (instance == null || applet == null || applet.panel == null) return;
  for (Component component: instance.box.getComponents()) {
   Complaint complaint = (Complaint)component;
   if (applet.panel.scaled(complaint.selection).intersects(rectangle)) {
    complaint.setVisible(true);
    if (applet instanceof DoctorApplet) {
     Common.debug("showing complaint " + complaint.selection + " darker");
     applet.panel.darken(complaint.selection, applet.DARKER);
    }
   } else {
    complaint.setVisible(false);
   }
  }
 }
 public static void setAllVisible() {
  if (instance == null) return;
  Component complaintList[] = instance.box.getComponents();
  for (int i = complaintList.length - 1; i >= 0; i--) {
   Complaint complaint = (Complaint)complaintList[i];
   if (!complaint.isVisible()) {
    complaint.setVisible(true);
   } else {
    if (applet instanceof DoctorApplet)
     Common.debug("showing complaint " + complaint.selection + " lighter");
     applet.panel.darken(complaint.selection, applet.LIGHTER);
   }
  }
  instance.box.scrollRectToVisible(new Rectangle(0, 200000, 1, 1));
 }
 public static void main(String args[]) throws Exception {
  Common.init();
  JFrame container = new JFrame("Complaints Test");
  Complaints complaints = null;
  container.setSize(240, 480);
  Complaints.codebase = new URL(TaggingApplet.DEFAULT_CODE_BASE);
  container.getContentPane().add(complaints = new Complaints(10));
  complaints.init();
  container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  container.setVisible(true);
  if (setMostRecentVisible) {
   Common.debug("container: " + container.getBounds() +
    ", box: " + complaints.box.getBounds());
   Rectangle bounds = complaints.box.getComponent(
    complaints.box.getComponentCount() - 1).getBounds(null);
   Common.debug("setting complaint " + 
    (complaints.box.getComponentCount() - 1) + " at " + bounds + " visible");
   complaints.box.scrollRectToVisible(bounds);
  }
  if (false) for (Component complaint: complaints.box.getComponents()) {
   ((Complaint)complaint).setVisible(false);
  }
 }
}
