package simiam.controller;

import java.util.List;

import simiam.robot.Robot;
import simiam.ui.Pose2D;

public class Supervisor
{

  private Controller current_controller; // % Currently selected controller
  private List<Controller> controllers;        // % List of available controllers
  private Robot robot  ;//             % The robot
  private Pose2D state_estimate; 
  
  public void attach_robot(Robot robot)
  {
    this.robot=robot;
    state_estimate= new Pose2D(robot.getPose());
  }
  
  public  void execute(long dt){
    
  }

  public Controller getCurrent_controller()
  {
    return current_controller;
  }

  public void setCurrent_controller(Controller current_controller)
  {
    this.current_controller = current_controller;
  }

  public List<Controller> getControllers()
  {
    return controllers;
  }

  public void setControllers(List<Controller> controllers)
  {
    this.controllers = controllers;
  }

  public Robot getRobot()
  {
    return robot;
  }

  public void setRobot(Robot robot)
  {
    this.robot = robot;
  }

  public Pose2D getState_estimate()
  {
    return state_estimate;
  }

  public void setState_estimate(Pose2D state_estimate)
  {
    this.state_estimate = state_estimate;
  }
  
  
}
