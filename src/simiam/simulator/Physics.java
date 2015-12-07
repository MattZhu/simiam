
package simiam.simulator;

import java.util.List;

import simiam.robot.QuickBot;
import simiam.robot.Robot;
import simiam.robot.sensor.ProximitySensor;
import simiam.ui.Surface2D;
import simiam.ui.Vertex;

public class Physics
{
  private World world;

  public Physics(World world)
  {
    this.world = world;
  }

  public boolean apply_physics()
  {
    boolean bool = body_collision_detection();
    if ( bool )
    {
      return true;
    }
    proximity_sensor_detection();
    return false;
  }

  private void proximity_sensor_detection()
  {

    // % token_k = obj.world.robots.head_();
    for (Robot robot : world.getRobots())
    {

      for (ProximitySensor ir : ((QuickBot) robot).getIr_array())
      {
        Surface2D body_ir_s = ir.getEdgeSurface();
        double d_min = ir.getMax_range();
        ir.update_range(d_min);

        // % check against obstacles
        for (Obstacle obstacle : world.getObstacles())
        {
          Surface2D body_o_s = obstacle.getEdgeSurface();
          ;

          if ( body_ir_s.precheckSurface(body_o_s) )
            d_min = update_proximity_sensor(ir, body_ir_s, body_o_s, d_min);

        }

        // % check against other robots
        // % token_l = obj.world.robots.head_;
        // % while (~isempty(token_l))
        for (Robot robot_o : world.getRobots())
        {
          // % robot_o = token_l.key_.robot;
          if ( robot_o != robot )
          {
            Surface2D body_o_s = robot_o.getEdgeSurface();

            if ( body_ir_s.precheckSurface(body_o_s) )
              d_min = update_proximity_sensor(ir, body_ir_s, body_o_s, d_min);

          }
          // % token_l = token_l.next_;
        }

        if ( d_min < ir.getMax_range() ) ir.update_range(d_min);

      }
      // % token_k = token_k.next_;
    }

  }

  private double update_proximity_sensor(ProximitySensor sensor, Surface2D sensor_surface,
      Surface2D obstacle_surface, double d_min)
  {
    List<Vertex> pts = sensor_surface.intersection(obstacle_surface);

    for (Vertex pt : pts)
    {
      // % d = norm(pt-sensor_surface.geometry_(1,1:2));
      double d = Math.sqrt((pt.getX() - sensor_surface.getGeometry().get(0).getX())
          * (pt.getX() - sensor_surface.getGeometry().get(0).getX())
          + (pt.getY() - sensor_surface.getGeometry().get(0).getY())
          * (pt.getY() - sensor_surface.getGeometry().get(0).getY()));
      d = sensor.limit_to_sensor(d);
      if ( d < d_min ) d_min = d;

    }
    return d_min;
  }

  private boolean body_collision_detection()
  {
    boolean result=false;
    for (Robot robot : world.getRobots())
    {
      Surface2D body_r_s=robot.getEdgeSurface();
      for (Obstacle obstacle : world.getObstacles())
      {
        Surface2D body_o_s = obstacle.getEdgeSurface();

        if ( body_r_s.precheckSurface(body_o_s) )
        {
          result =body_r_s.intersection(body_o_s).size()>0;
          if(result){
            System.out.println("COLLISION!");
            return result;
          }
        }

      }
    }

    return result;
  }
}
