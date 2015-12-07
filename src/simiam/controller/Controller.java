
package simiam.controller;

import simiam.robot.Robot;
import simiam.ui.Pose2D;

public class Controller
{
  private String type;// name of controller;

  public Controller(String type)
  {
    this.type = type;
  }

  public Object[] execute(Robot robot, Pose2D state_estimate, Object ... inputs)
  {
    return null;
  }
  
  public void reset(){
    
  }

}