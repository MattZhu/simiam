
package simiam.simulator;

import java.util.Timer;
import java.util.TimerTask;

import simiam.app.ControlApp;
import simiam.robot.Robot;
import simiam.ui.Pose2D;
import simiam.ui.SimView;

public class Simulator
{
  private Timer clock; // % Global timer for the simulation

  private long timeStep; // % Time step for the simulation

  private World world; // % A virtual world for the simulator

  private Physics physics;

  private SimView parent;

  private String origin;
  
  private TimerTask task;

  public Simulator(SimView parent, long timeStep, World world, String origin)
  {
    this.world = world;
    this.timeStep = timeStep;
    this.parent = parent;
    this.origin = origin;
    parent.addDrawables(world.getObstacles());
    parent.addDrawables(world.getRobots());
    clock = new Timer();
    physics=new Physics(world);
  }

  public void step()
  {
    /*
     * %% STEP Executes one time step of the simulation. % step(obj, src, event) is the timer callback which is executed
     * % once every time_step seconds.
     * 
     * % if(strcmp(obj.origin, 'launcher')) % split = obj.time_step; % else % split = max(obj.time_step,get(obj.clock,
     * 'InstantPeriod')); % end
     */
    long split = timeStep;
    for (Robot robot_s : world.getRobots())
    {

      Pose2D pose_k_1;

      if ( "hardware".equals(this.origin) )
      {
        pose_k_1 = robot_s.update_state_from_hardware(robot_s.getPose(), split);
      }
      else
      {
        pose_k_1 = robot_s.update_state(robot_s.getPose(), split);

      }
      robot_s.setPose(pose_k_1);

//      System.out.println("current_pose: (%0.3f,%0.3f,%0.3f)\n', x, y, theta");

      robot_s.getSupervisor().execute(split);
    }

    if ( origin.equals("simulink") )
    {

      ControlApp anApp = world.getApps().get(0);
      anApp.run(split);
    }
    //
    boolean bool = false;
//    if ( !origin.equals("launcher") || !origin.equals("testing") )

    bool = physics.apply_physics();

   
    // obj.parent.ui_update(split, bool);
    // drawnow;
    // % fprintf('ui: %0.3fs\n', toc(tstart));
    parent.repaint();
    if(bool){
    	this.stop();
    }
  }

  public void start()
  {

    task=new TimerTask()
    {

      @Override
      public void run()
      {
        step();

      }
    };
    
    clock.scheduleAtFixedRate(task, 0, timeStep);

  }

  public void stop()
  {
     task.cancel();
  }
  public void shutdonw(){
    
  }
  
}
