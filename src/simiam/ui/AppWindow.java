package simiam.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simiam.simulator.Simulator;
import simiam.simulator.World;

public class AppWindow implements ActionListener {

	private Component home;

	private SimView simView;

	private JLabel status;

	private JLabel clock;

	private JFrame frame;

	private JButton btnHome;

	private JButton btnPlay;
	
	private JButton btnZoomin;
	private JButton btnZoomout;
	
	private String origin = "launcher";

	private Simulator simulator;

	private Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

	public AppWindow() {
	}

	public AppWindow(String origin) {
		this.origin = origin;
	}

	public void createWindow() {

		frame = new JFrame("Sim I am");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int ui_w = 800;
		int ui_h = 640;
		frame.setSize(ui_w, ui_h);
		frame.setLocationRelativeTo(null);
		JPanel jp = new JPanel(new BorderLayout());
		addButtons(jp);
		home = new HomeView();
		
		frame.getContentPane().add(jp, BorderLayout.SOUTH);
		frame.getContentPane().add(home, BorderLayout.CENTER);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		JPanel northEast = new JPanel();
		clock = new JLabel();
		clock.setIcon(readImage("ui_status_clock.png"));
		status = new JLabel();
		status.setIcon(readImage("ui_status_ok.png"));
		northEast.add(status);
		northEast.add(clock);
		northPanel.add(northEast, BorderLayout.EAST);

		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		frame.setVisible(true);
	}

	private void addButtons(JPanel panel) {

		btnHome = createIconButton("ui_control_home.png");
		panel.add(btnHome, BorderLayout.WEST);
		JPanel centerP = new JPanel();
		JButton btnReset = createIconButton("ui_control_reset.png");
		centerP.add(btnReset);
		btnPlay = createIconButton("ui_control_play.png", true);
		centerP.add(btnPlay);
		JButton btnHardware = createIconButton("ui_control_hardware.png");
		centerP.add(btnHardware);
		panel.add(centerP);

		JPanel eastP = new JPanel();
		btnZoomin = createIconButton("ui_control_zoom_in.png");
		btnZoomout = createIconButton("ui_control_zoom_out.png");
		eastP.add(btnZoomout);
		eastP.add(btnZoomin);
		panel.add(eastP, BorderLayout.EAST);

	}

	private JButton createIconButton(String iconName) {
		return createIconButton(iconName, false);
	}

	private JButton createIconButton(String iconName, boolean enabled) {
		JButton btn = new JButton();

		btn.setIcon(readImage(iconName));
		// btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setEnabled(enabled);
		btn.addActionListener(this);
		btn.setActionCommand(iconName.replace("ui_control_", "").replace(
				".png", ""));
		return btn;
	}

	private ImageIcon readImage(String path) {
		ImageIcon result = icons.get(path);
		if (result != null) {
			return result;
		}
		try {
			BufferedImage buttonIcon = ImageIO.read(AppWindow.class
					.getResourceAsStream(path));
			result = new ImageIcon(buttonIcon);
			icons.put(path, result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
   * 
   */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {

		AppWindow window = new AppWindow();
		window.createWindow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		JButton btn = null;
		if (e.getSource() instanceof JButton) {
			btn = (JButton) e.getSource();
		}
		if (command.equals("play")) {
			play(btn);
		} else if (command.equals("pause")) {

			pause(btn);
		} else if (command.equals("home")) {

			home(btn);
		} else if (command.equals("resume")) {
			resume(btn);
		} else if (command.equals("zoom_in")) {
			zoom(btn,0.2);
			btnZoomout.setEnabled(true);
		} else if (command.equals("zoom_out")) {
			zoom(btn,-0.2);
			btnZoomin.setEnabled(true);
		}
	}

	private void zoom(JButton btn,double s) {
		// TODO Auto-generated method stub
		boolean result = simView.zoom(s);
		btn.setEnabled(result);
	}

	private void resume(JButton btn) {
		btn.setIcon(readImage("ui_control_pause.png"));
		btn.setActionCommand("pause");
		simView.resume();
		simulator.start();
	}

	private void pause(JButton btn) {
		btn.setIcon(readImage("ui_control_play.png"));
		btn.setActionCommand("resume");
		simView.pause();
		simulator.stop();
	}

	private void home(JButton btn) {
	  frame.getContentPane().remove(simView);
		frame.getContentPane().add(home, BorderLayout.CENTER);
		frame.getContentPane().repaint();
		btn.setEnabled(false);

		btnPlay.setIcon(readImage("ui_control_play.png"));
		btnPlay.setActionCommand("play");

		simView.stop();
	}

	private void play(JButton btn) {
		

		frame.getContentPane().remove(home);
		simView = new SimView();
		frame.getContentPane().add(simView, BorderLayout.CENTER);
		
		simView.setBounds(home.getBounds());
		frame.getContentPane().repaint();
		btnHome.setEnabled(true);
		btnZoomin.setEnabled(true);
		btnZoomout.setEnabled(true);
		btn.setIcon(readImage("ui_control_pause.png"));
		btn.setActionCommand("pause");
		simView.start();
		simView.invalidate();
		simView.validate();
		simulator = create_simulator();
		simulator.start();
	}

	private Simulator create_simulator() {
		World world = new World();
		world.buildFromFile();
		Simulator simulator = new Simulator(simView, 50, world, origin);
		return simulator;
	}
}
