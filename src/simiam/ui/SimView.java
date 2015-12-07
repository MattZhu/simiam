package simiam.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class SimView extends JPanel implements MouseListener,
		MouseMotionListener {

	/**
   * 
   */
	private static final long serialVersionUID = 1L;

	private List<Drawable> drawables = new ArrayList<Drawable>();

	private float scale = 0.6f;

	private Stroke drawingStroke = new BasicStroke(1 / scale,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
			new float[] { 2, 4 }, 0);

	private double center_x = -1;

	private double center_y = -1;
	
	private int last_x=-1;
	private int last_y=-1;

	public SimView() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void paint(Graphics g) {
		super.paint(g);

		// paintClipRectangle(g);
		Graphics2D g2 = (Graphics2D) g;
		// g2.scale(0.5, .5);

		if (center_x == -1) {
			center_x = this.getWidth() / 2;
			center_y = this.getHeight() / 2;
		}
		g2.scale(scale, scale);
		double t[][] = { { 800, 0, center_x / scale },
				{ 0, -800, center_y / scale }, { 0, 0, 1 } };
		drawGrid(g);
		for (Drawable d : drawables) {
			d.drawSurfaces(g, t);
		}
	}

	private void paintClipRectangle(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		// g2.scale(0.5, .5);
		g2.scale(2, 2);
		int w = getWidth();
		int h = getHeight() / 2;
		// 设定裁剪区域为当前的椭圆，由于使用的setClip，因此不会和Graphics中
		// 已经设定的clip进行组合。
		Ellipse2D e = new Ellipse2D.Float(w / 4.0f, h / 4.0f, w / 2.0f,
				h / 2.0f);
		g2.setClip(e);
		g2.setColor(Color.yellow);
		g2.fillRect(0, 0, w, h);
		// 打印出当前剪切区域的矩形边界，这里的bounds为椭圆剪切区域的外切矩形
		System.out.println(g2.getClipBounds());
		e = new Ellipse2D.Float(w / 4.0f, h / 4.0f + h, w / 2.0f, h / 2.0f);
		g2.setClip(e);
		g2.setColor(Color.green);
		g2.fillRect(0, h, w, h);
		System.out.println("Before clip: " + g2.getClipBounds());
		// 和Graphics中已经存在的剪切区域组合，取两个剪切区域的交集。
		// 如果没有交集，则无法绘制任何形状。
		Rectangle r = new Rectangle(w / 2, h, w / 2, h);
		g2.clip(r);
		g2.setColor(Color.blue);
		g2.fillRect(0, h, w, h);
		System.out.println("After clip: " + g2.getClipBounds());
	}

	public <T extends Drawable> void addDrawable(T d) {
		this.drawables.add(d);
	}

	public void addDrawables(List<? extends Drawable> ds) {
		this.drawables.addAll(ds);
	}

	public void drawGrid(Graphics g) {
		
	

		int w = (int) (getWidth() / scale);
		int h = (int) (getHeight() / scale);
		this.setBackground(Color.white);
		g.setColor(Color.black);
		Graphics2D g2d = (Graphics2D) g;
		Stroke s=g2d.getStroke();
		g2d.setStroke(drawingStroke);
		int mid_w = w / 2;
		int mid_h = h / 2;
		for (int i = mid_w; i < w; i += 50) {
			g2d.drawLine(i, 0, i, h);
		}
		for (int i = mid_w; i > 0; i -= 50) {
			g2d.drawLine(i, 0, i, h);
		}
		for (int i = mid_h; i < h; i += 50) {
			g2d.drawLine(0, i, w, i);
		}
		for (int i = mid_h; i > 0; i -= 50) {
			g2d.drawLine(0, i, w, i);
		}
		g2d.setStroke(s);
	}

	public void stop() {

	}

	public void pause() {

	}

	public void start() {

	}

	public void resume() {

	}

	public boolean zoom(double d) {
		this.scale += d;
		repaint();
		if (d > 0 && this.scale > 1.79) {
			return false;
		} else if (d < 0 && this.scale < 0.21) {
			return false;
		}
		return true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		center_x+=e.getX()-last_x;
		center_y+=e.getY()-last_y;
		repaint();
		last_x=e.getX();
		last_y=e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		last_x=e.getX();
		last_y=e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
