// blue-cyan-green-yellow-orange-red, rectangular and elliptical markers
package com.jcomeau.imagetagging;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
// from timeline table we need start_treatment and current_date
// use logarithmic algorithm to plot dates, start_treatment is black rectangle
// complaint date is round, rightmost point of spectrum is current_date
public class SpectrumIcon implements Icon {
 static final long serialVersionUID = 0L;
 static final String TIMELINE_IMAGE = "/timeline.jpg";
 static final String MARKER_IMAGE = "/complaint.png";
 int height = 32;
 int width = 0;  // filled in during initialization
 Date startTreatment = null;
 Date complaintDate = null;
 Date today = new Date();
 BufferedImage timeline = null;
 BufferedImage marker = null;
 Container parent = null;
 public SpectrumIcon(Container parent, Date startTreatment, Date complaint) {
  super();
  this.startTreatment = startTreatment;
  this.complaintDate = complaint;
  this.parent = parent;
  Common.debug("created SpectrumIcon for " + startTreatment + " and " +
   complaint, 2);
  try {
   timeline = ImageIO.read(getClass().getResource(TIMELINE_IMAGE));
   marker = ImageIO.read(getClass().getResource(MARKER_IMAGE));
   width = timeline.getWidth();
  } catch (Exception whatever) {
   Common.debug("failed loading timeline image: " + whatever);
  }
 }
 double days(Date older, Date newer) {
  double millis = 0.0, days = 0.0;
  double oldTime = older.getTime(), newTime = newer.getTime();
  Common.debug("older: " + older + ", newer: " + newer, 2);
  Common.debug("older: " + oldTime + ", newer: " + newTime, 2);
  millis = newTime - oldTime;
  Common.debug("millis: " + millis, 2);
  if (millis > 0) days = (millis / 1000.0) / (60 * 60 * 24);
  Common.debug("days: " + days, 2);
  return days;
 }
 int dateOffset(Date date) {
  // offset into icon for a certain date
  double fullScale = Math.max(0.0, Math.log(days(startTreatment, today)));
  double dateScale = Math.max(0.0, Math.log(days(date, today)));
  if (dateScale > fullScale) dateScale = fullScale;
  int offset;
  Common.debug("fullScale: " + fullScale + ", dateScale: " + dateScale, 2);
  try {
   offset = width - (int)((dateScale / fullScale) * width);
  } catch (Exception error) {
   offset = 0;
  }
  Common.debug("offset of date " + date + ": " + offset, 2);
  return Math.min(width - marker.getWidth(), offset);
 }
 public void paintIcon(Component ignored, Graphics graphics, int x, int y) {
  Graphics2D g2d = (Graphics2D)graphics.create();
  g2d.drawImage(timeline, 0, 0, width, height, null);
  int markerOffset = complaintDate.getTime() < startTreatment.getTime() ?
   0 : dateOffset(complaintDate);
  g2d.drawImage(marker,
   Math.min(markerOffset, width - marker.getWidth()), 
   (height / 2 - marker.getHeight() / 2), null);
  g2d.dispose();
 }
 public int getIconWidth() {
  return width;
 }
 public int getIconHeight() {
  return height;
 }
 public static void main(String args[]) {
  JFrame container = new JFrame("Timeline Icon");
  container.getContentPane().add(new JLabel(
   "Test of complaint timeline",
   (Icon)new SpectrumIcon(container, new Date(), new Date()),
   JLabel.LEFT
  ));
  container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  container.pack();
  container.setVisible(true);
 }
}
