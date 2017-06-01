package com.jcomeau.imagetagging;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.util.*;
import java.net.*;
class ComplaintEntry extends JDialog {
 static final long serialVersionUID = 0L;
 Window windows[] = Window.getWindows();
 JTextArea textarea = null;
 JButton okayButton = new JButton(new ImageIcon(
  Common.class.getResource("/send.png")));
 int input = 0;
 public ComplaintEntry(Window owner) {
  super(owner, "Please enter symptoms");
  //UIManager.put("activeCaption", new ColorUIResource(Color.BLUE));
  //UIManager.put("activeCaptionText", new ColorUIResource(Color.WHITE));
  setDefaultLookAndFeelDecorated(true);
  textarea = new JTextArea();
  textarea.setEditable(true);
  textarea.setLineWrap(true);
  okayButton.setBorderPainted(false);
  okayButton.setHorizontalAlignment(SwingConstants.LEFT);
  JScrollPane scrollpane = new JScrollPane(textarea);
  scrollpane.setHorizontalScrollBarPolicy(
   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
  scrollpane.setPreferredSize(new Dimension(240, 120));
  add(scrollpane);
  add(okayButton);
  if (true) {
   input = JOptionPane.showConfirmDialog(
    null,
    new Object[] {"Symptoms for this area of the body", scrollpane},
    "Please enter symptoms",
    JOptionPane.DEFAULT_OPTION,
    JOptionPane.PLAIN_MESSAGE);
  } else {
   pack();
   setVisible(true);
  }
 }
 public String getValue() {
  String text = textarea.getText().trim();
  if (input == JOptionPane.OK_OPTION && !text.equals("")) return text;
  else return null;
 }
 public static void main(String args[]) {
  try {
   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  } catch (Exception error) {
   Common.debug("Could not set Look and Feel: " + error);
  }
  JFrame container = new JFrame();
  container.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
  Common.init();
  ComplaintEntry dialog = new ComplaintEntry(container);
  String complaint = dialog.getValue();
  Common.debug("complaint: " + complaint);
 }
}
