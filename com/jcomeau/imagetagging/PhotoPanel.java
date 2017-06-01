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
class PhotoPanel extends JPanel implements MouseMotionListener, MouseListener {
 static final long serialVersionUID = 0L;
 public static final double DARKER = 0.6;
 public static final double LIGHTER = 1.0 / DARKER;
 public static String BODY_IMAGE = "/front_back.png";
 Ellipse2D.Double selection = null;
 Vector<Ellipse2D.Double> selections = null;
 Point anchor;
 JApplet applet;
 public double scaleFactor = 1.0;
 BufferedImage body;
 Window windows[] = null;
 public PhotoPanel(JApplet containingApplet) {
  super();
  setLayout(null);  // for absolute positioning of ComplaintArea panels
  int appletWidth = 0;
  if (containingApplet != null) {
   applet = containingApplet;
   try {
    body = ImageIO.read(applet.getClass().getResource(BODY_IMAGE));
    appletWidth = applet.getWidth();
    if (appletWidth == 0) appletWidth = applet.getPreferredSize().width;
    double scale = (double)appletWidth / (double)body.getWidth();
    if (Math.abs(1.0d - scale) > 0.01) scaleFactor = scale;
    setSize(appletWidth, (int)(body.getHeight() * scaleFactor));
    setPreferredSize(getSize());  // for container with layout
    Common.debug("PhotoPanel size: " + getSize());
    windows = Window.getWindows();
    Common.debug("windows: " + windows);
   } catch (Exception whatever) {
    Common.debug("Failed loading body image: " + whatever);
   }
   Common.debug("scaleFactor: " + scaleFactor);
   if (containingApplet instanceof TaggingApplet) {
    selections = new Vector<Ellipse2D.Double>();
    addMouseListener(this);
    addMouseMotionListener(this);
    setToolTipText("Click and tag the area that concerns you");
   }
  }
 }
 protected void paintComponent(Graphics graphics) {
  super.paintComponent(graphics);
  if (scaleFactor != 1.0d) graphics.drawImage(
   body, 0, 0, getWidth(), getHeight(), null);
  else graphics.drawImage(body, 0, 0, null);
  if (selection != null) {
   Graphics2D g2d = (Graphics2D)graphics;
   g2d.draw(selection);
  }
 }
 public Ellipse2D.Double scaled(Ellipse2D.Double ellipse) {
  return scaled(ellipse, scaleFactor);
 }
 public Ellipse2D.Double scaled(Ellipse2D.Double ellipse, double scaleFactor) {
  Ellipse2D.Double scaled = null;
  if (scaleFactor == 0d || scaleFactor == 1.0d || scaleFactor > 1000) {
   Common.debug("ignoring scale request to " + scaleFactor);
   return ellipse;
  }
  scaled = new Ellipse2D.Double(ellipse.x * scaleFactor,
   ellipse.y * scaleFactor,
   ellipse.width * scaleFactor,
   ellipse.height * scaleFactor);
  return scaled;
 }
 public void darken(Ellipse2D.Double selection, double amount) {
  // stackoverflow.com/questions/5132015/how-to-convert-image-to-sepia-in-java
  Ellipse2D.Double area = selection;
  for (int i = (int)area.x; i <= area.x + area.width; i++) {
   for (int j = (int)area.y; j <= area.y + area.height; j++) {
    if (area.contains(new Point2D.Double((double)i, (double)j))) {
     body.setRGB(i, j, redden(body.getRGB(i, j), amount));
    }
   }
  }
  repaint();
 }
 int redden(int value, double amount) {
  // make pixel redder by decreasing G and B components
  //System.out.printf("value before darkening: 0x%08x\n", value);
  int bgMask = 0xffff;
  int bgValue = value & bgMask;
  value &= ~bgMask;
  value |= (int)((bgValue & 0xff) * amount);  // blue
  value |= (int)((bgValue & 0xff00) * amount) & 0xff00;  // green
  //System.out.printf("value after darkening: 0x%08x\n", value);
  return value;
 }
 public void mousePressed(MouseEvent event) {
  Common.debug("mousePressed: " + event);
  anchor = event.getPoint();
  selection = new Ellipse2D.Double(anchor.x, anchor.y, 0, 0);
 }
 public void mouseDragged(MouseEvent event) {
  selection.setFrame(
   Math.min(anchor.x,event.getX()),
   Math.min(anchor.y,event.getY()),
   Math.abs(event.getX() - anchor.x),
   Math.abs(event.getY() - anchor.y));
  repaint();
 }
 public void mouseReleased(MouseEvent event) {
  Graphics graphics = getGraphics();
  String symptoms = "";
  Hashtable<String, String> insertSymptoms = new Hashtable<String, String>();
  if (!check(selection)) return;
  selections.add(selection);
  if (selection != null) {
   //darken(selection, DARKER);
   darken(scaled(selection, scaleFactor), DARKER);
   Graphics2D g2d = (Graphics2D)graphics;
   g2d.draw(selection);
   repaint();
  }
  symptoms = getSymptoms();
  if (symptoms != null && symptoms.length() > 0) {
   Common.debug("Symptoms: " + symptoms);
   insertSymptoms.put("complaint", symptoms);
   insertSymptoms.put("left_x", "" + selection.x);
   insertSymptoms.put("top_y", "" + selection.y);
   insertSymptoms.put("width", "" + selection.width);
   insertSymptoms.put("height", "" + selection.height);
   insertSymptoms.put("front_back",
    selection.x + (selection.width / 2) > body.getWidth() / 2 ? "1" : "0");
   try {
    ConnectClient.insert("complaints", insertSymptoms, applet.getCodeBase());
   } catch (Exception whatever) {
    Common.debug("Could not send to database: " + whatever);
   }
  } else {
   selections.removeElementAt(selections.size() - 1);
   //darken(selection, LIGHTER);
   darken(scaled(selection, scaleFactor), LIGHTER);
   selection = null;
   repaint();
  }
 }
 public boolean check(Ellipse2D.Double selection) {
  return !(selection.width < 1 ||
   selection.height < 1 ||
   selection.x + selection.width > body.getWidth() ||
   selection.y + selection.height > body.getHeight());
 }
 public String getSymptoms() {
  ComplaintEntry dialog = new ComplaintEntry(windows[windows.length - 1]);
  String input = dialog.getValue();
  Common.debug("input from dialog: \"" + input + "\"");
  if (input == null || input.trim().equals("")) return null;
  return input;
 }
 public void mouseMoved(MouseEvent event) {}
 public void mouseClicked(MouseEvent event) {}
 public void mouseEntered(MouseEvent event) {}
 public void mouseExited(MouseEvent event) {}
 public static void main(String args[]) {
  try {
   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  } catch (Exception error) {
   Common.debug("Could not set Look and Feel: " + error);
  }
  JFrame container = new JFrame();
  container.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
  Common.init();
  Common.debug("creating applet");
  JApplet applet = new JApplet();
  applet.setPreferredSize(new Dimension(240, 480));
  applet.getContentPane().setLayout(new BorderLayout());
  applet.getContentPane().add(new PhotoPanel(applet), BorderLayout.SOUTH);
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
