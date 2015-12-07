package simiam.robot.sensor;

public class WheelEncoder {

	private double radius;
	private double length;
	private String type;
	private int ticks_per_rev;
	private int ticks;
	private double total_distance;

	public WheelEncoder(String type, double wheel_radius,
			double wheel_base_length, int ticks_per_rev) {
		this.radius = radius;
		this.length = length;
		this.type = type;
		this.ticks_per_rev = ticks_per_rev;
		this.ticks = 0;
		this.total_distance = 0;
	}

	public void update_ticks(double wheel_velocity, double dt) {
		this.ticks = this.ticks + distance_to_ticks(wheel_velocity * dt);
	}

	public void reset_ticks() {
		this.ticks = 0;
	}

	public int distance_to_ticks(double distance) {
		this.total_distance = this.total_distance + distance;
		ticks = (int) Math.round((this.total_distance * this.ticks_per_rev)
				/ (2 * Math.PI));
		this.total_distance = this.total_distance
				- this.ticks_to_distance(ticks);
		return ticks;
	}

	public double ticks_to_distance(int ticks) {
		return (ticks * 2 * Math.PI) / this.ticks_per_rev;
	}

  public int getTicks()
  {
    return ticks;
  }

  public void setTicks(int ticks)
  {
    this.ticks = ticks;
  }

  public double getRadius()
  {
    return radius;
  }

  public void setRadius(double radius)
  {
    this.radius = radius;
  }

  public double getLength()
  {
    return length;
  }

  public void setLength(double length)
  {
    this.length = length;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public int getTicks_per_rev()
  {
    return ticks_per_rev;
  }

  public void setTicks_per_rev(int ticks_per_rev)
  {
    this.ticks_per_rev = ticks_per_rev;
  }

  public double getTotal_distance()
  {
    return total_distance;
  }

  public void setTotal_distance(double total_distance)
  {
    this.total_distance = total_distance;
  }
  
  
	
	
}
