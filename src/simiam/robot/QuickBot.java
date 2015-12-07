
package simiam.robot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import simiam.robot.dynamics.DifferentialDrive;
import simiam.robot.dynamics.Dynamics;
import simiam.robot.sensor.ProximitySensor;
import simiam.robot.sensor.WheelEncoder;
import simiam.ui.Pose2D;
import simiam.ui.Surface2D;
import simiam.ui.Vertex;

public class QuickBot extends Robot
{

  private double wheel_radius;

  private double wheel_base_length;

  private int ticks_per_rev;

  private double max_vel;

  private double min_vel;

  private List<WheelEncoder> encoders = new ArrayList<WheelEncoder>();

  private List<ProximitySensor> ir_array = new ArrayList<ProximitySensor>();

  private Dynamics dynamics;

  private int prev_ticks_left;
  private int prev_ticks_right;

  private double right_wheel_speed;

  private double left_wheel_speed;

  public QuickBot(JPanel parent, Pose2D pose)
  {
    super(parent, pose);

    List<Vertex> qb_base_plate = new ArrayList<Vertex>();
    qb_base_plate.add(new Vertex(0.0335, 0.0534, 1));
    qb_base_plate.add(new Vertex(0.0429, 0.0534, 1));
    qb_base_plate.add(new Vertex(0.0639, 0.0334, 1));
    qb_base_plate.add(new Vertex(0.0686, 0.0000, 1));
    qb_base_plate.add(new Vertex(0.0639, -0.0334, 1));
    qb_base_plate.add(new Vertex(0.0429, -0.0534, 1));
    qb_base_plate.add(new Vertex(0.0335, -0.0534, 1));
    qb_base_plate.add(new Vertex(-0.0465, -0.0534, 1));
    qb_base_plate.add(new Vertex(-0.0815, -0.0534, 1));
    qb_base_plate.add(new Vertex(-0.1112, -0.0387, 1));
    qb_base_plate.add(new Vertex(-0.1112, 0.0387, 1));
    qb_base_plate.add(new Vertex(-0.0815, 0.0534, 1));
    qb_base_plate.add(new Vertex(-0.0465, 0.0534, 1));

    List<Vertex> qb_bbb = new ArrayList<Vertex>();
    qb_bbb.add(new Vertex(-0.0914, -0.0406, 1));
    qb_bbb.add(new Vertex(-0.0944, -0.0376, 1));
    qb_bbb.add(new Vertex(-0.0944, 0.0376, 1));
    qb_bbb.add(new Vertex(-0.0914, 0.0406, 1));
    qb_bbb.add(new Vertex(-0.0429, 0.0406, 1));
    qb_bbb.add(new Vertex(-0.0399, 0.0376, 1));
    qb_bbb.add(new Vertex(-0.0399, -0.0376, 1));
    qb_bbb.add(new Vertex(-0.0429, -0.0406, 1));

    List<Vertex> qb_bbb_rail_l = new ArrayList<Vertex>();
    qb_bbb_rail_l.add(new Vertex(-0.0429, -0.0356, 1));
    qb_bbb_rail_l.add(new Vertex(-0.0429, 0.0233, 1));
    qb_bbb_rail_l.add(new Vertex(-0.0479, 0.0233, 1));
    qb_bbb_rail_l.add(new Vertex(-0.0479, -0.0356, 1));

    List<Vertex> qb_bbb_rail_r = new ArrayList<Vertex>();
    qb_bbb_rail_r.add(new Vertex(-0.0914, -0.0356, 1));
    qb_bbb_rail_r.add(new Vertex(-0.0914, 0.0233, 1));
    qb_bbb_rail_r.add(new Vertex(-0.0864, 0.0233, 1));
    qb_bbb_rail_r.add(new Vertex(-0.0864, -0.0356, 1));

    List<Vertex> qb_bbb_eth = new ArrayList<Vertex>();
    qb_bbb_eth.add(new Vertex(-0.0579, 0.0436, 1));
    qb_bbb_eth.add(new Vertex(-0.0579, 0.0226, 1));
    qb_bbb_eth.add(new Vertex(-0.0739, 0.0226, 1));
    qb_bbb_eth.add(new Vertex(-0.0739, 0.0436, 1));

    List<Vertex> qb_left_wheel = new ArrayList<Vertex>();
    qb_left_wheel.add(new Vertex(0.0254, 0.0595, 1));
    qb_left_wheel.add(new Vertex(0.0254, 0.0335, 1));
    qb_left_wheel.add(new Vertex(-0.0384, 0.0335, 1));
    qb_left_wheel.add(new Vertex(-0.0384, 0.0595, 1));

    List<Vertex> qb_left_wheel_ol = new ArrayList<Vertex>();
    qb_left_wheel_ol.add(new Vertex(0.0254, 0.0595, 1));
    qb_left_wheel_ol.add(new Vertex(0.0254, 0.0335, 1));
    qb_left_wheel_ol.add(new Vertex(-0.0384, 0.0335, 1));
    qb_left_wheel_ol.add(new Vertex(-0.0384, 0.0595, 1));

    List<Vertex> qb_right_wheel_ol = new ArrayList<Vertex>();
    qb_right_wheel_ol.add(new Vertex(0.0254, -0.0595, 1));
    qb_right_wheel_ol.add(new Vertex(0.0254, -0.0335, 1));
    qb_right_wheel_ol.add(new Vertex(-0.0384, -0.0335, 1));
    qb_right_wheel_ol.add(new Vertex(-0.0384, -0.0595, 1));

    List<Vertex> qb_right_wheel = new ArrayList<Vertex>();
    qb_right_wheel.add(new Vertex(0.0254, -0.0595, 1));
    qb_right_wheel.add(new Vertex(0.0254, -0.0335, 1));
    qb_right_wheel.add(new Vertex(-0.0384, -0.0335, 1));
    qb_right_wheel.add(new Vertex(-0.0384, -0.0595, 1));

    List<Vertex> qb_ir_1 = new ArrayList<Vertex>();
    qb_ir_1.add(new Vertex(-0.0732, 0.0534, 1));
    qb_ir_1.add(new Vertex(-0.0732, 0.0634, 1));
    qb_ir_1.add(new Vertex(-0.0432, 0.0634, 1));
    qb_ir_1.add(new Vertex(-0.0432, 0.0534, 1));

    List<Vertex> qb_ir_2 = new ArrayList<Vertex>();
    qb_ir_2.add(new Vertex(0.0643, 0.0214, 1));
    qb_ir_2.add(new Vertex(0.0714, 0.0285, 1));
    qb_ir_2.add(new Vertex(0.0502, 0.0497, 1));
    qb_ir_2.add(new Vertex(0.0431, 0.0426, 1));

    List<Vertex> qb_ir_3 = new ArrayList<Vertex>();
    qb_ir_3.add(new Vertex(0.0636, -0.0042, 1));
    qb_ir_3.add(new Vertex(0.0636, 0.0258, 1));
    qb_ir_3.add(new Vertex(0.0736, 0.0258, 1));
    qb_ir_3.add(new Vertex(0.0736, -0.0042, 1));

    List<Vertex> qb_ir_4 = new ArrayList<Vertex>();
    qb_ir_4.add(new Vertex(0.0643, -0.0214, 1));
    qb_ir_4.add(new Vertex(0.0714, -0.0285, 1));
    qb_ir_4.add(new Vertex(0.0502, -0.0497, 1));
    qb_ir_4.add(new Vertex(0.0431, -0.0426, 1));

    List<Vertex> qb_ir_5 = new ArrayList<Vertex>();
    qb_ir_5.add(new Vertex(-0.0732, -0.0534, 1));
    qb_ir_5.add(new Vertex(-0.0732, -0.0634, 1));
    qb_ir_5.add(new Vertex(-0.0432, -0.0634, 1));
    qb_ir_5.add(new Vertex(-0.0432, -0.0534, 1));

    List<Vertex> qb_bbb_usb = new ArrayList<Vertex>();
    qb_bbb_usb.add(new Vertex(-0.0824, -0.0418, 1));
    qb_bbb_usb.add(new Vertex(-0.0694, -0.0418, 1));
    qb_bbb_usb.add(new Vertex(-0.0694, -0.0278, 1));
    qb_bbb_usb.add(new Vertex(-0.0824, -0.0278, 1));

    Surface2D s = addSurfaceWithDepth(qb_base_plate, new Color(226, 0, 2), 1.3);
    this.setEdgeSurface(s);
     addSurfaceWithDepth(qb_base_plate, Color.red, 1.3);
    addSurface(qb_right_wheel, new Color(38, 38, 38));
    s = addSurfaceWithDepth(qb_right_wheel_ol, new Color(38, 38, 38), 1.5);
    Stroke drawingStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
        new float[] { 2, 2 }, 0);
    s.setDrawLine(true);
    s.setDrawingStroke(drawingStroke);

    s = addSurfaceWithDepth(qb_left_wheel_ol, new Color(38, 38, 38), 1.5);
    s.setDrawLine(true);
    s.setDrawingStroke(drawingStroke);

    addSurface(qb_left_wheel, new Color(38, 38, 38));

    addSurfaceWithDepth(qb_ir_1, new Color(25, 25, 25), 1.2);
    addSurfaceWithDepth(qb_ir_2, new Color(25, 25, 25), 1.2);
    addSurfaceWithDepth(qb_ir_3, new Color(25, 25, 25), 1.2);
    addSurfaceWithDepth(qb_ir_4, new Color(25, 25, 25), 1.2);
    addSurfaceWithDepth(qb_ir_5, new Color(25, 25, 25), 1.2);

    addSurfaceWithDepth(qb_bbb, new Color(51, 51, 51), 1.4);
    addSurfaceWithDepth(qb_bbb_rail_l, new Color(0, 0, 0), 1.5);
    addSurfaceWithDepth(qb_bbb_rail_r, new Color(0, 0, 0), 1.5);
    addSurfaceWithDepth(qb_bbb_eth, new Color(178, 178, 178), 1.5);
    addSurfaceWithDepth(qb_bbb_usb, new Color(178, 178, 178), 1.5);

    wheel_radius = 0.0325; // % 65.0mm in diameter
    wheel_base_length = 0.09925; // % 99.25mm
    ticks_per_rev = 16;

    int max_rpm = 130;
    max_vel = max_rpm * 2 * Math.PI / 60;

    int min_rpm = 30;
    min_vel = min_rpm * 2 * Math.PI / 60;

    encoders.add(new WheelEncoder("right_wheel", wheel_radius, wheel_base_length, ticks_per_rev));
    encoders.add(new WheelEncoder("left_wheel", wheel_radius, wheel_base_length, ticks_per_rev));

    Pose2D ir_pose = new Pose2D(-0.0474, 0.0534, Pose2D.deg2rad(90));
    ir_array.add(new ProximitySensor(parent, "IR", pose, ir_pose, 0.04, 0.3, Pose2D.deg2rad(6)));// ,
                                                                                                 // 'simiam.robot.QuickBot.ir_distance_to_raw');

    ir_pose = new Pose2D(0.0613, 0.0244, Pose2D.deg2rad(45));
    ir_array.add(new ProximitySensor(parent, "IR", pose, ir_pose, 0.04, 0.3, Pose2D.deg2rad(6))); // 'simiam.robot.QuickBot.ir_distance_to_raw');

    ir_pose = new Pose2D(0.0636, 0.0, Pose2D.deg2rad(0));
    ir_array.add(new ProximitySensor(parent, "IR", pose, ir_pose, 0.04, 0.3, Pose2D.deg2rad(6))); // 'simiam.robot.QuickBot.ir_distance_to_raw');

    ir_pose = new Pose2D(0.0461, -0.0396, Pose2D.deg2rad(-45));
    ir_array.add(new ProximitySensor(parent, "IR", pose, ir_pose, 0.04, 0.3, Pose2D.deg2rad(6))); // 'simiam.robot.QuickBot.ir_distance_to_raw');

    ir_pose = new Pose2D(-0.0690, -0.0534, Pose2D.deg2rad(-90));
    ir_array.add(new ProximitySensor(parent, "IR", pose, ir_pose, 0.04, 0.3, Pose2D.deg2rad(6))); // 'simiam.robot.QuickBot.ir_distance_to_raw');
    dynamics = new DifferentialDrive(this.wheel_radius, this.wheel_base_length);
    
    right_wheel_speed = 0;
    left_wheel_speed = 0;
    
    
  }

  @Override
  public void drawSurfaces(Graphics g, double[][] t)
  {
    super.drawSurfaces(g, t);
    for (ProximitySensor sensor : ir_array)
    {
    	
      sensor.drawSurfaces(g, t);
    }
  }

  public List<ProximitySensor> getIr_array()
  {
    return ir_array;
  }

  public void setIr_array(List<ProximitySensor> ir_array)
  {
    this.ir_array = ir_array;
  }

  public int getPrev_ticks_left()
  {
    return prev_ticks_left;
  }

  public void setPrev_ticks_left(int prev_ticks_left)
  {
    this.prev_ticks_left = prev_ticks_left;
  }

  public int getPrev_ticks_right()
  {
    return prev_ticks_right;
  }

  public void setPrev_ticks_right(int prev_ticks_right)
  {
    this.prev_ticks_right = prev_ticks_right;
  }
  
  

  public List<WheelEncoder> getEncoders()
  {
    return encoders;
  }

  public void setEncoders(List<WheelEncoder> encoders)
  {
    this.encoders = encoders;
  }

  @Override
  public Pose2D update_state_from_hardware(Pose2D pose, long split)
  {
    // TODO Auto-generated method stub
    return super.update_state_from_hardware(pose, split);
  }

  @Override
  public Pose2D update_state(Pose2D pose, long split)
  {
    Pose2D npose = dynamics.apply_dynamics(pose, split, this.right_wheel_speed, this.left_wheel_speed);
    updatePose(npose);
    for(ProximitySensor s:ir_array){
      s.updatePose(npose);
//      s.setLocation(npose);
    }
    encoders.get(0).update_ticks(right_wheel_speed, split);
    encoders.get(1).update_ticks(left_wheel_speed, split);
    return npose;
  }

  public double getWheel_radius()
  {
    return wheel_radius;
  }

  public void setWheel_radius(double wheel_radius)
  {
    this.wheel_radius = wheel_radius;
  }

  public double getWheel_base_length()
  {
    return wheel_base_length;
  }

  public void setWheel_base_length(double wheel_base_length)
  {
    this.wheel_base_length = wheel_base_length;
  }

  public int getTicks_per_rev()
  {
    return ticks_per_rev;
  }

  public void setTicks_per_rev(int ticks_per_rev)
  {
    this.ticks_per_rev = ticks_per_rev;
  }

  public double getMax_vel()
  {
    return max_vel;
  }

  public void setMax_vel(double max_vel)
  {
    this.max_vel = max_vel;
  }

  public double getMin_vel()
  {
    return min_vel;
  }

  public void setMin_vel(double min_vel)
  {
    this.min_vel = min_vel;
  }

  public double getRight_wheel_speed()
  {
    return right_wheel_speed;
  }

  public void setRight_wheel_speed(double right_wheel_speed)
  {
    this.right_wheel_speed = right_wheel_speed;
  }

  public double getLeft_wheel_speed()
  {
    return left_wheel_speed;
  }

  public void setLeft_wheel_speed(double left_wheel_speed)
  {
    this.left_wheel_speed = left_wheel_speed;
  }
  
  

}
