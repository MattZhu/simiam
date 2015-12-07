
package simiam.robot.dynamics;

import simiam.ui.Pose2D;

public class DifferentialDrive extends Dynamics
{
  private double wheel_radius;

  private double wheel_base_length;

  public DifferentialDrive(double wheel_radius, double wheel_base_length)
  {
    super();
    this.wheel_radius = wheel_radius;
    this.wheel_base_length = wheel_base_length;
  }

  @Override
  public Pose2D apply_dynamics(Pose2D pose_t, double dt, double vel_r, double vel_l)
  {
    double R = this.wheel_radius;
    double L = this.wheel_base_length;

    double v = R / 2 * (vel_r + vel_l);
    double w = R / L * (vel_r - vel_l);

    double x_k_1 = pose_t.getX() + dt * (v * Math.cos(pose_t.getTheta()));
    double y_k_1 = pose_t.getY() + dt * (v * Math.sin(pose_t.getTheta()));
    double theta_k_1 = pose_t.getTheta() + dt * w;    
    
    return new Pose2D(x_k_1,y_k_1,theta_k_1);
  }

}
