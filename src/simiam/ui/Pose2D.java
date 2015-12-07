
package simiam.ui;

/**
 * 
 * @author Matthew.Zhu
 * 
 */
public class Pose2D
{
  private double x;

  private double y;

  private double theta;

  public Pose2D(Pose2D pose)
  {
   this(pose.x,pose.y,pose.theta);
  }
  
  public Pose2D(double x, double y, double theta)
  {
    this.x = x;
    this.y = y;
    this.theta = theta;
  }

  public void setPose(double x, double y, double theta)
  {
    this.x = x;
    this.y = y;
    this.theta = theta;
  }

  public void setPose(Pose2D pose)
  {
    this.x = pose.getX();
    this.y = pose.getY();
    this.theta = pose.getTheta();
  }

  public void setPose(float[] pose)
  {
    this.x = pose[0];
    this.y = pose[1];
    this.theta = pose[2];
  }

  public double[][] getTransformationMatrix()
  {
    return new double[][] { { Math.cos(theta), -Math.sin(theta), x },
        { Math.sin(theta), Math.cos(theta), y }, { 0, 0, 1 } };
  }

  public static double deg2rad(double deg)
  {
    return deg * Math.PI / 180;
  }
  
  public static double rad2deg(double rad){
    return rad*180/Math.PI;
  }

  /**
   * 
   * @return
   */
  public double[] unpack()
  {
    return new double[] { this.x, this.y, this.theta };
  }

  public double getX()
  {
    return x;
  }

  public void setX(double x)
  {
    this.x = x;
  }

  public double getY()
  {
    return y;
  }

  public void setY(double y)
  {
    this.y = y;
  }

  public double getTheta()
  {
    return theta;
  }

  public void setTheta(double theta)
  {
    this.theta = theta;
  }

@Override
public String toString() {
	return "Pose2D [x=" + x + ", y=" + y + ", theta=" + theta + "]";
}
  
  

}
