package main.com.univlr.commons.utils.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.swing.JPanel;

import jade.util.Logger;
import main.com.univlr.commons.WorldAgent;

/**
 * Represents the world of the simulation.
 * 
 * @author Antoine Orgerit
 */
public abstract class World extends JPanel implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Logger logger;

	protected Random random;

	/**
	 * Allows to generate the {@code World}.
	 * 
	 * @param width  the width of the world as an integer
	 * @param height the height of the world as an integer
	 */
	protected void generateWorld(int width, int height) {
		this.logger = Logger.getMyLogger(this.getClass().getName());
		try {
			this.random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			this.logger.config(
					"Couldn't instanciate SecureRandom for the " + this.getName() + " world. Using Random instead.");
			this.random = new Random();
		}
		this.setLayout(null);
		this.setBackground(Color.white);
		this.setBounds(0, 0, width, height);
		this.setSize(width, height);
		this.setBackground(Color.WHITE);
		this.setVisible(true);
	}

	/**
	 * Retrieves an initial position for a {@code WorldAgent} of the {@code World}.
	 * This method must be implemented by subclasses to give a custom initial
	 * position and to register the agent if needed.
	 * 
	 * @param agent the agent calling the method as an {@code WorldAgent}
	 * @return an initial position as a {@code Point}
	 */
	public abstract Point getInitialPosition(WorldAgent agent);

	/**
	 * Allows to update a {@code WorldAgent} position in the {@code World}
	 * representation.
	 * 
	 * @param oldX    the old position on the X axis of the agent as a double
	 * @param oldY    the old position on the Y axis of the agent as a double
	 * @param oldSize the old size of the agent as an integer
	 * @param newX    the new position on the X axis of the agent as a double
	 * @param newY    the new position on the Y axis of the agent as a double
	 * @param newSize the new size of the agent as an integer
	 * @param color   the color of the agent as a {@code Color}
	 */
	public void drawAgent(double oldX, double oldY, int oldSize, double newX, double newY, int newSize, Color color) {
		Graphics g = this.getGraphics();
		g.clearRect((int) oldX, (int) oldY, (int) oldSize, (int) oldSize);
		this.paint(g);
		g.setColor(color);
		g.fillRect((int) newX, (int) newY, (int) newSize, (int) newSize);
	}

	/**
	 * Allows to remove a {@code WorldAgent} from the {@code World}.
	 * 
	 * @param agent the agent to remove of the world as a {@code WorldAgent}
	 * @param size  the last size of the agent as an integer
	 */
	public void removeAgent(WorldAgent agent, int size) {
		Graphics g = this.getGraphics();
		g.clearRect((int) agent.getPosition().getX(), (int) agent.getPosition().getY(), size, size);
	}

}
