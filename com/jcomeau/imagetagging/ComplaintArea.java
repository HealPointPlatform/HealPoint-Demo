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
public class ComplaintArea extends JPanel implements MouseListener {
 static final long serialVersionUID = 0L;
 public static final int SIZE = 10;
 Rectangle covered = null;
 public Vector<Complaint> attachedComplaints = new Vector<Complaint>();
 Color transparentRed = new Color(255, 0, 0, 25);
 public ComplaintArea(int x, int y) {
  super();
  covered = new Rectangle(x, y, SIZE, SIZE);
  setSize(new Dimension(covered.width, covered.height));
  setPreferredSize(getSize());
  setOpaque(false);
  addMouseListener(this);
 }
 public void mousePressed(MouseEvent event) {}
 public void mouseReleased(MouseEvent event) {}
 public void mouseClicked(MouseEvent event) {}
 public void mouseEntered(MouseEvent event) {
  //Common.debug("entered: " + event.getComponent());
  Complaints.showOnlyIntersecting(covered.getBounds2D());
 }
 public void mouseExited(MouseEvent event) {
  Complaints.setAllVisible();
 }
 public static void add(Container pane, Component item) {
  pane.add(item);
  Insets insets = pane.getInsets();
  try {
   ComplaintArea area = (ComplaintArea)item;
   item.setBounds(area.covered.x + insets.left, area.covered.y + insets.top,
    area.getWidth(), area.getHeight());
  } catch (Exception ignored) {}  // probably just non-ComplaintArea component
 }
 public static void cover(Container panel, Complaints complaints) {
  ComplaintArea area;
  int width = panel.getWidth(), height = panel.getHeight();
  for (int i = 0; i < width; i += SIZE) {
   for (int j = 0; j < height; j += SIZE) {
    add(panel, area = new ComplaintArea(i, j));
    //Common.debug("added: " + area);
   }
  }
 } 
 public static void main(String args[]) {
  Common.DEBUGLEVEL = 1;
  JFrame container;
  container = new JFrame();
  container.setSize(400, 300);
  container.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
  container.setLayout(null);  // allows absolute positioning
  container.setVisible(true);
  cover(container.getContentPane(), null);
 }
}
