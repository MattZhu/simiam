package simiam.robot.sensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import simiam.robot.sensor.noise.GaussianNoise;
import simiam.robot.sensor.noise.NoiseModel;
import simiam.ui.Drawable;
import simiam.ui.Pose2D;
import simiam.ui.Surface2D;
import simiam.ui.Vertex;

public class ProximitySensor extends Drawable {

	private String type;
	private Pose2D location;
	private double range;
	private double spread;
	private double max_range;
	private double min_range;
	private Surface2D s;

	private NoiseModel noise_model = new GaussianNoise(0,0);

	public ProximitySensor(JPanel parent, String type, Pose2D r_pose,
			Pose2D pose, double r_min, double r_max, double phi) {
		super(parent, r_pose);
		this.type = type;
		this.location = pose;

		double T[][] = this.location.getTransformationMatrix();
		double r = r_max;
		double r1 = r * Math.tan(phi / 4);
		double r2 = r * Math.tan(phi / 2);
		List<Vertex> sensor_cone = new ArrayList<Vertex>();
		sensor_cone.add(new Vertex(0, 0, 1).transfer(T));
		sensor_cone.add(new Vertex(Math.sqrt(r * r - r2 * r2), r2, 1)
				.transfer(T));
		sensor_cone.add(new Vertex(Math.sqrt(r * r - r1 * r1), r1, 1)
				.transfer(T));
		sensor_cone.add(new Vertex(r, 0, 1).transfer(T));
		sensor_cone.add(new Vertex(Math.sqrt(r * r - r1 * r1), -r1, 1)
				.transfer(T));
		sensor_cone.add(new Vertex(Math.sqrt(r * r - r2 * r2), -r2, 1)
				.transfer(T));

		s = this.addSurface(sensor_cone, new Color(204, 204, 255));
		s.setEdgeColor(Color.black);

		this.range = r;
		this.spread = phi;

		this.max_range = r_max;
		this.min_range = r_min;

	}

	public void update_range(double distance) {
		this.range = limit_to_sensor(noise_model.apply_noise(distance));
		distance = range;

		double r1 = distance * Math.tan(this.spread / 4);
		double r2 = distance * Math.tan(this.spread / 2);

		double T[][] = this.location.getTransformationMatrix();
		List<Vertex> sensor_cone = new ArrayList<Vertex>();
		sensor_cone.add(new Vertex(0, 0, 1).transfer(T));
		sensor_cone.add(new Vertex(Math.sqrt(distance * distance - r2 * r2),
				r2, 1).transfer(T));
		sensor_cone.add(new Vertex(Math.sqrt(distance * distance - r1 * r1),
				r1, 1).transfer(T));
		sensor_cone.add(new Vertex(distance, 0, 1).transfer(T));
		sensor_cone.add(new Vertex(Math.sqrt(distance * distance - r1 * r1),
				-r1, 1).transfer(T));
		sensor_cone.add(new Vertex(Math.sqrt(distance * distance - r2 * r1),
				-r2, 1).transfer(T));

		s.updateGeometry(sensor_cone);

		if (distance < max_range) {
			s.setEdgeColor(Color.red);
			s.setColor(new Color(255, 204, 204));
		} else {
			s.setEdgeColor(Color.black);
			s.setColor(new Color(204, 204, 255));
		}
	}
	
	public double get_range(){
//            s = obj.map;
//            raw = s(obj.range);
		return 0;
	}
        
	public double limit_to_sensor(double distance){
       return Math.min(Math.max(distance, min_range), max_range);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Pose2D getLocation() {
		return location;
	}

	public void setLocation(Pose2D location) {
		this.location = location;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public double getSpread() {
		return spread;
	}

	public void setSpread(double spread) {
		this.spread = spread;
	}

	public double getMax_range() {
		return max_range;
	}

	public void setMax_range(double max_range) {
		this.max_range = max_range;
	}

	public double getMin_range() {
		return min_range;
	}

	public void setMin_range(double min_range) {
		this.min_range = min_range;
	}

	public Surface2D getS() {
		return s;
	}

	public void setS(Surface2D s) {
		this.s = s;
	}

	public NoiseModel getNoise_model() {
		return noise_model;
	}

	public void setNoise_model(NoiseModel noise_model) {
		this.noise_model = noise_model;
	}

	
}
