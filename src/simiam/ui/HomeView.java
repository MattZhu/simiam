package simiam.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeView extends JPanel
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public HomeView(){
    
    this.setBackground(new Color(96, 184, 206));
    JLabel label=new JLabel();
    String html="<html><div style=\"text-align: center\"><img src=\""+ HomeView.class.getResource("simiam_splash.png")+ "\"/>" +
        "<br>Welcome to <b>Sim.I.am</b>, a robot simulator." +
        "<br>This is <em>Sim the Third</em>, your companion for control theory and robotics." +
        "<br>The simulator is maintained by the GRITSLab at" +
        "<br><a href=\"http://gritslab.gatech.edu/projects/robot-simulator\">http://gritslab.gatech.edu/projects/robot-simulator</a>" +
        "</div><br><ol><li>Start by clicking the play button.</li>" +
        "<li>Use the mouse to pan and zoom.</li>" +
        "<li>Select the robot to follow it</li>" +
        "<li>If your robot crashes, press the rewind button.</li></ol>"+
        "</html>";
    label.setText(html);
    label.setForeground(Color.white);
    label.setFont(label.getFont().deriveFont(Font.BOLD));
    this.add(label);
  }

}
