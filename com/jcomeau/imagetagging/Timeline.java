package com.jcomeau.imagetagging;
import javax.swing.*;
import java.util.*;
public class Timeline extends JLabel {
 static final long serialVersionUID = 0L;
 public Timeline(Date startTreatment, Date complaintDate) {
  super();
  setHorizontalAlignment(LEFT);
  setIcon(new SpectrumIcon(this, startTreatment, complaintDate));
  setOpaque(false);
  setAlignmentX(LEFT_ALIGNMENT);  // Timeline's alignment within applet
  setToolTipText(Complaint.dateString(complaintDate));
 }
 public static void main(String args[]) {
  JFrame container = new JFrame("Timeline Test");
  container.setSize(400, 400);
  container.add(new Timeline(new Date(), new Date()));
  container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  container.setVisible(true);
 }
}
