package simiam.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

public class Drawable {
	private JPanel parent;

	private Pose2D pose;

	private List<Surface2D> surfaces;
	
	private Surface2D edgeSurface;
	
	private Comparator c=new Comparator<Surface2D>(){

		@Override
		public int compare(Surface2D o1, Surface2D o2) {
			if(o1.getDepth()>o2.getDepth()){
				return 1;
			}else if(o1.getDepth()<o2.getDepth()){
				return -1;
			}
			return 0;
		}};

	public Drawable(JPanel parent, Pose2D pose) {
		super();
		this.parent = parent;
		this.pose = pose;
		this.surfaces = new ArrayList<Surface2D>();
	}

	public Surface2D addSurface(List<Vertex> geometry, Color color) {
		return addSurfaceWithDepth(geometry, color, 1);
	}

	public Surface2D addSurfaceWithDepth(List<Vertex> geometry, Color color,
			double depth) {
		// surface_g = geometry;
		// T = obj.pose.get_transformation_matrix();
		// geometry_t = geometry*T';
		// geometry_t(:,3) = depth;
		// surface_h = patch('Parent', obj.parent, ...
		// 'Vertices', geometry_t, ...
		// 'Faces', 1:size(geometry,1), ...
		// 'FaceColor', 'flat', ...
		// 'FaceVertexCData', color);
		Surface2D surface = new Surface2D(geometry, new Object());
		surface.setDepth(depth);
		surface.setColor(color);
		surface.transform(this.pose.getTransformationMatrix());
		surfaces.add(surface);
		Collections.sort(surfaces, c);
		return surface;
	}

	public void drawSurfaces(Graphics g,double t[][]) {

		for (Surface2D s : surfaces) {
			s.transform(pose.getTransformationMatrix());
			Graphics2D g2 = (Graphics2D) g;
			
			s.draw(g,t);
		}
	}

	public void updatePose(Pose2D npose) {
		pose.setPose(npose);
		// drawSurfaces();
	}

	public Pose2D getPose() {
		return pose;
	}

	public void setPose(Pose2D pose) {
		this.pose = pose;
	}

	public List<Surface2D> getSurfaces() {
		return surfaces;
	}

	public void setSurfaces(List<Surface2D> surfaces) {
		this.surfaces = surfaces;
	}

	@Override
	public String toString() {
		return "Drawable [pose=" + pose + ", surfaces=" + surfaces + "]";
	}

  public Surface2D getEdgeSurface()
  {
    if(edgeSurface==null){
      return this.surfaces.get(0);
    }
    return edgeSurface;
  }

  public void setEdgeSurface(Surface2D edgeSurface)
  {
    this.edgeSurface = edgeSurface;
  }
	
	

}
