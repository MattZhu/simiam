package simiam.controller.quickbot;

import simiam.controller.Supervisor;
import simiam.robot.QuickBot;
import simiam.ui.Pose2D;



public class QBSupervisor extends Supervisor
{

    private int prev_tick_right ;
    private int prev_tick_left ;
  
  @Override
  public void execute(long dt)
  {
    
    super.execute(dt);
    
    update_odometry();
    QuickBot robot =(QuickBot)getRobot();
    robot.setLeft_wheel_speed(0.003);
    robot.setRight_wheel_speed(0.0045);
  }

  private void update_odometry(){
    QuickBot robot =(QuickBot)getRobot();
    int right_ticks =robot.getEncoders().get(0).getTicks();
    int left_ticks = robot.getEncoders().get(1).getTicks();
  //% Recal the previous wheel encoder ticks
    
//    prev_left_ticks = obj.prev_ticks.left;
    this.getState_estimate();
    double x=this.getState_estimate().getX();
    double y=this.getState_estimate().getY();
    
    double theta=this.getState_estimate().getTheta();
   double R = robot.getWheel_radius();
   double L = robot.getWheel_base_length();
   double m_per_tick = (2*Math.PI*R)/robot.getEncoders().get(0).getTicks_per_rev();
    
   double d_right = (right_ticks-prev_tick_right)*m_per_tick;
   double d_left = (left_ticks-prev_tick_left)*m_per_tick;
   
   double d_center = (d_right + d_left)/2;
   double phi = (d_right - d_left)/L;
   
   double x_dt = d_center*Math.cos(theta);
   double y_dt = d_center*Math.sin(theta);
   double theta_dt = phi;
   
   double theta_new = theta + theta_dt;
   double x_new = x + x_dt;
   double y_new = y + y_dt;    
   
   prev_tick_right=right_ticks;
   prev_tick_left= left_ticks;
   
   this.setState_estimate(new Pose2D(x_new,y_new,theta_new));
  }
   
    
    
    
    
    
   
   

}
