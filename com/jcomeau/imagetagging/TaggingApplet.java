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

public class TaggingApplet extends javax.swing.JApplet {
 static final long serialVersionUID = 0L;
 static final double DARKER = 0.8;
 static final double LIGHTER = 1.0 / DARKER;
 public static String BODY_IMAGE = "/front_back.png";
 public static JFrame container = null;
 public static String DEFAULT_CODE_BASE = "http://tagging/";
 Vector<Ellipse2D.Double> selections = null;
 BufferedImage body;
 PhotoPanel panel = null;
 public void init() {
  try {
   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  } catch (Exception error) {
   Common.debug("Could not set Look and Feel: " + error);
  }
  try {Common.host = getParameter("host").trim();} catch (Exception ignored) {}
  Common.init();
  Common.debug("initalized");
  selections = new Vector<Ellipse2D.Double>();
  try {
   body = ImageIO.read(getClass().getResource(BODY_IMAGE));
  } catch (Exception error) {
   Common.debug("Error loading body image: " + error);
  }
  panel = new PhotoPanel(this);
  panel.setPreferredSize(new Dimension(body.getWidth(), body.getHeight()));
  getContentPane().add(panel, BorderLayout.CENTER);
  resize(panel.getPreferredSize());
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
  TaggingApplet applet = new TaggingApplet();
  container.add(applet);
  applet.init();
  container.pack();
  container.setVisible(true);
 }
}
