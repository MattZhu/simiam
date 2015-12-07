package simiam.simulator;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import simiam.ui.Drawable;
import simiam.ui.Pose2D;
import simiam.ui.Vertex;

public class Obstacle extends Drawable {

	private String type = "obstacle";

	public Obstacle(JPanel parent, Pose2D pose, List<Vertex> geometry) {
		super(parent, pose);
		this.addSurface(geometry, new Color(255,100,100));
	}
	
	

}
