package main.com.univlr.commons.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import jade.gui.GuiEvent;
import main.com.univlr.commons.WorldLauncherAgent;
import main.com.univlr.commons.utils.world.World;

/**
 * GUI of the {@code WorldLauncherAgent} displaying the {@code World} of a
 * simulation.
 * 
 * @author Antoine Orgerit
 */
public class WorldLauncherAgentGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private WorldLauncherAgent agent;

	public static final int EXIT = 0;

	private World world;

	/**
	 * Constructor of the GUI frame.
	 * 
	 * @param agent the {@code Launcher} agent using the GUI
	 */
	public WorldLauncherAgentGui(WorldLauncherAgent agent) {
		this.agent = agent;
		this.world = this.agent.getWorld();
		this.generateFrame();
	}

	/**
	 * Allows to generate the frame.
	 */
	private void generateFrame() {
		this.setCloseOperation();
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setPreferredSize(new Dimension(this.world.getWidth(), this.world.getHeight()));
		this.add(this.world);
		this.setTitle("Launcher Agent Gui");
		this.setBackground(Color.WHITE);
		this.pack();
	}

	@Override
	public void setVisible(boolean arg0) {
		super.setVisible(arg0);
		this.world.paint(this.world.getGraphics());
	}

	/**
	 * Allows to set a close operation to the frame which is sent to the
	 * {@code Launcher} agent.
	 */
	private void setCloseOperation() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GuiEvent ge = new GuiEvent(this, EXIT);
				agent.postGuiEvent(ge);
			}
		});
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
