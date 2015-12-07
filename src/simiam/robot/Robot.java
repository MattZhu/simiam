
package simiam.robot;

import javax.swing.JPanel;

import simiam.controller.Supervisor;
import simiam.ui.Drawable;
import simiam.ui.Pose2D;

public class Robot extends Drawable
{

  private Supervisor supervisor;

  private Object driver;

  private Object optitrack;

  private String hostname;

  private int port;

  private boolean islinked;

  public Robot(JPanel parent, Pose2D pose)
  {
    super(parent, pose);
  }

  public void attach_supervisor(Supervisor supervisor)
  {
    this.supervisor = supervisor;
    supervisor.attach_robot(this);
  }
  
  public void open_hardware_link(){
    islinked=true;
  }

  public void close_hardware_link(){
    islinked=false;
  }

  public Supervisor getSupervisor()
  {
    return supervisor;
  }

  public void setSupervisor(Supervisor supervisor)
  {
    this.supervisor = supervisor;
  }

  public Pose2D update_state_from_hardware(Pose2D pose, long split)
  {
    
    return null;
  }

  public Pose2D update_state(Pose2D pose, long split)
  {
   
    return pose;
  }
  
  
}
