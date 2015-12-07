package simiam.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

public class Surface2D {
	private Vertex centroid;

	private List<Vertex> geometry;

	// handle_
	private double geometricSpan = 0;

	private List<Edge> edges;

	private double depth;

	private boolean drawable;

	private List<Vertex> vertexs;
	
	private Color color;
	
	private Stroke drawingStroke = null;
	
	private boolean drawLine= false;

	private Color edgeColor;

	public Surface2D(List<Vertex> vs) {
		this(vs, null);
	}

	public Surface2D(List<Vertex> vs, Object handle) {
		drawable = (handle == null);
		this.vertexs = vs;
		// add handle later
		init();
	}

	public void transform(double tm[][]) {
		this.geometry = new ArrayList<Vertex>(vertexs.size());
		for (Vertex v : vertexs) {
			geometry.add(v.transfer(tm));
		}
		calCentroid();
		calGeometricSpan();
		updateEdges();
		if (drawable) {
			// draw the surface
		}
	}

	public void draw(Graphics g,double [][]t) {
		int length=geometry.size();
		int px[]=new int[length];
		int py[]=new int[length];
		int i=0;
		for(Vertex v:geometry){
			Vertex v1=v.transfer(t);
			px[i]=(int)Math.round(v1.getX());
			py[i]=(int)Math.round(v1.getY());
//			System.out.println("Vertex["+i+"]=("+px[i]+","+py[i]+")");
			i++;
		}
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(color);
		
		Stroke s=g2d.getStroke();
		if(drawingStroke!=null){
			g2d.setStroke(drawingStroke);
		}

		Color c=g.getColor();
		if(!drawLine){
			g.fillPolygon(px,py,length);
			if(this.edgeColor!=null){
				g.setColor(this.edgeColor);
				g.drawPolygon(px,py,length);
			}
		}else
		{
			g.drawPolygon(px,py,length);
		}

		g.setColor(c);
		if(drawingStroke!=null){
			g2d.setStroke(s);
		}
		
	}

	public void updateGeometry(List<Vertex> v) {
		this.vertexs = v;
	}

	public boolean precheckSurface(Surface2D surface) {
		double d = this.centroid.distance(surface.centroid);
		return d < (this.geometricSpan + surface.geometricSpan)
				/ Math.sqrt(3.0);
	}

	public List<Vertex> intersection(Surface2D surface) {
		List<Vertex> result = new ArrayList<Vertex>();
		for (Edge e : this.edges) {
			for (Edge e1 : surface.edges) {
				Vertex v = e.intersection(e1);
				if (v != null) {
					result.add(v);
				}
			}

		}
		return result;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	private void init() {
		this.depth = 1;
		this.geometry = new ArrayList<Vertex>(vertexs.size());
		copyList(vertexs, geometry);
		calCentroid();
		calGeometricSpan();
		updateEdges();
	}

	private void updateEdges() {
		Vertex pv = null;
		edges = new ArrayList<Edge>();
		for (Vertex v : geometry) {
			if (pv == null) {
				pv = v;
			} else {
				Edge e = new Edge(pv, v);
				edges.add(e);
				pv = v;
			}
		}
		edges.add(new Edge(pv, geometry.get(0)));
	}

	private void calGeometricSpan() {
		double d;
		for (Vertex v : geometry) {
			d = 2 * v.distance(centroid);
			if (d > this.geometricSpan) {
				this.geometricSpan = d;
			}
		}

	}

	private void calCentroid() {
		centroid = new Vertex();
		for (Vertex v : geometry) {
			centroid.add(v);
		}
		centroid.div(geometry.size());
	}

	private void copyList(List<Vertex> src, List<Vertex> desc) {
		for (Vertex v : src) {
			Vertex newV = new Vertex(v);
			desc.add(newV);
		}
	}
	

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Surface2D [centroid=" + centroid + ", geometry=" + geometry
				+ ", depth=" + depth + ", drawable=" + drawable + ", vertexs="
				+ vertexs + ",color="
				+ color + "]";
	}

	public double getDepth() {
		return depth;
	}

	public Stroke getDrawingStroke() {
		return drawingStroke;
	}

	public void setDrawingStroke(Stroke drawingStroke) {
		this.drawingStroke = drawingStroke;
	}

	public boolean isDrawLine() {
		return drawLine;
	}

	public void setDrawLine(boolean drawLine) {
		this.drawLine = drawLine;
	}

	public void setEdgeColor(Color c) {
		this.edgeColor=c;		
	}

	public List<Vertex> getGeometry() {
		return geometry;
	}

	public void setGeometry(List<Vertex> geometry) {
		this.geometry = geometry;
	}

	
}
