package com.jcomeau.imagetagging;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
public class DoctorApplet extends javax.swing.JApplet {
 static final long serialVersionUID = 0L;
 public static final double DARKER = 0.6;
 public static final double LIGHTER = 1.0 / DARKER;
 public static String BODY_IMAGE = "/front_back.png";
 public static JFrame container = null;
 public static String DEFAULT_CODE_BASE = "http://tagging/";
 public static int patientId = 10;  // fixed for this prototype
 public PhotoPanel panel = null;
 Vector<Ellipse2D.Double> selections = null;
 BufferedImage body;
 Complaints complaints = null;
 public void init() {
  try {
   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  } catch (Exception error) {
   Common.debug("Could not set Look and Feel: " + error);
  }
  try {Common.host = getParameter("host").trim();} catch (Exception ignored) {}
  Common.init();
  selections = new Vector<Ellipse2D.Double>();
  try {
   body = ImageIO.read(getClass().getResource(BODY_IMAGE));
   panel = new PhotoPanel(this);
   Complaints.applet = this; Complaints.codebase = getCodeBase();
   complaints = new Complaints(patientId);
   complaints.setPreferredSize(new Dimension(getWidth(),
    getHeight() - panel.getPreferredSize().height));
   complaints.setSize(complaints.getPreferredSize());
   Common.debug("applet size: " + getWidth() + "x" + getHeight());
   Common.debug("complaints size: " + complaints.box);
   getContentPane().setLayout(new BorderLayout());
   getContentPane().add(complaints, BorderLayout.NORTH);
   complaints.init();
   getContentPane().add(panel, BorderLayout.SOUTH);
   ComplaintArea.cover(panel, complaints);
   Rectangle bounds = complaints.box.getComponent(
    complaints.box.getComponentCount() - 1).getBounds(null);
   Common.debug("setting complaint " + 
    (complaints.box.getComponentCount() - 1) + " at " + bounds + " visible");
   bounds.y = 200000;  // works but bad programming
   bounds.width = bounds.width > 0 ? bounds.width : 1;
   complaints.box.scrollRectToVisible(bounds);
  } catch (Exception whatever) {
   Common.debug("Failed initialization: " + whatever);
  }
 }
 public URL getCodeBase() {
  URL codebase = null;
  try {
   codebase = super.getCodeBase();
  } catch (Exception whatever) {
   if (this.container == null)
    Common.debug("Cannot get codebase: " + whatever);
  }
  try {
   if (codebase == null) codebase = new URL(DEFAULT_CODE_BASE);
  } catch (Exception failed) {
   Common.debug("Failed creating codebase: " + failed);
  }
  return codebase;
 }
 public static void main(String args[]) {
  try {
   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  } catch (Exception error) {
   Common.debug("Could not set Look and Feel: " + error);
  }
  container = new JFrame();
  container.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
  Common.debug("creating applet");
  DoctorApplet applet = new DoctorApplet();
  applet.setSize(240, 480);
  container.add(applet);
  Common.debug("initializing applet");
  applet.init();
  Common.debug("showing applet");
  container.pack();
  container.setVisible(true);
  Common.debug("height: " + container.getHeight() + ", width: " +
   container.getWidth());
  Common.debug("applet height: " + applet.getHeight() +
   ", applet width: " + applet.getWidth());
 }
}
