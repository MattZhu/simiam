package simiam.robot.dynamics;

import simiam.ui.Pose2D;

public abstract class Dynamics
{
  public abstract Pose2D apply_dynamics(Pose2D pose_t, double dt, double vel_r, double vel_l);
}
