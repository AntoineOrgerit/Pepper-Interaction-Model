package main.com.univlr.commons;

import java.awt.Color;
import java.awt.Point;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.com.univlr.commons.utils.world.World;

/**
 * An abstract descriptor that represents a JADE agent existing in a
 * {@code World}. As the agents are using a {@code StateChartBehaviour}, this
 * class is extending {@code StateChartAgent}.
 * 
 * @author Antoine Orgerit
 */
public abstract class WorldAgent extends StateChartAgent {

	private static final long serialVersionUID = 1L;

	private World world;

	private Random random;

	protected Color color;
	private Point position;
	private char[] directions = { 'N', 'E', 'S', 'W' };

	@Override
	public void setup() {
		super.setup();
		this.world = this.getAgentWorld();
		try {
			this.random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			this.logger.config("Couldn't instanciate SecureRandom for " + this.getName() + ". Using Random instead.");
			this.random = new Random();
		}
		this.position = this.world.getInitialPosition(this);
	}

	/**
	 * Retrieves the position of the {@code WorldAgent} in the {@code World}.
	 * 
	 * @return the position of the agent as a {@code Point}
	 */
	public Point getPosition() {
		return this.position;
	}

	/**
	 * Allows to move the {@code WorldAgent} randomly in the {@code World}.
	 */
	public void move() {
		this.moveTo(this.randomDirection());
	}

	/**
	 * Allows to move the {@code WorldAgent} in a specified direction. The position
	 * of the agent in the {@code World} is updated.
	 * 
	 * @param direction the direction on which to move the agent as a character
	 *                  which value has to be 'N', 'E', 'S' or 'W'
	 */
	private void moveTo(char direction) {
		switch (direction) {
		case 'N':
			this.drawAgent(this.position.getX(), this.position.getY(), this.getWorldAgentSize(), this.position.getX(),
					this.position.getY() - 1, this.getWorldAgentSize());
			this.position.setLocation(this.position.getX(), this.position.getY() - 1);
			break;
		case 'E':
			this.drawAgent(this.position.getX(), this.position.getY(), this.getWorldAgentSize(),
					this.position.getX() + 1, this.position.getY(), this.getWorldAgentSize());
			this.position.setLocation(this.position.getX() + 1, this.position.getY());
			break;
		case 'S':
			this.drawAgent(this.position.getX(), this.position.getY(), this.getWorldAgentSize(), this.position.getX(),
					this.position.getY() + 1, this.getWorldAgentSize());
			this.position.setLocation(this.position.getX(), this.position.getY() + 1);
			break;
		case 'W':
			this.drawAgent(this.position.getX(), this.position.getY(), this.getWorldAgentSize(),
					this.position.getX() - 1, this.position.getY(), this.getWorldAgentSize());
			this.position.setLocation(this.position.getX() - 1, this.position.getY());
			break;
		default:
			throw new UnsupportedOperationException("Illegal directon value for LumberjackAgent at move() function.");
		}
	}

	/**
	 * Allows to move the {@code WorldAgent} in a specific direction in order to
	 * reach a particular destination.
	 * 
	 * @param destination the destination to which the agent wants to move as a
	 *                    {@code Point}
	 */
	protected void moveTo(Point destination) {
		this.moveToPossibleDirections(this.destinationPossibleDirections(destination));
	}

	/**
	 * Allows to move the {@code WorldAgent} away from a specific position.
	 * 
	 * @param position the position to avoid as a {@code Point}
	 */
	protected void moveAwayFrom(Point position) {
		this.moveToPossibleDirections(this.possibleDirectionsAwayFrom(position));
	}

	/**
	 * Allows to move the {@code WorldAgent} to one of the potential directions
	 * passed in parameters, according to its current position.
	 * 
	 * @param potentialDirections the potential directions to which the agent can
	 *                            move as a {@code List} of {@code Characters}
	 */
	private void moveToPossibleDirections(List<Character> potentialDirections) {
		List<Character> excludes = this.excludedDirections();
		List<Character> possibleDirections = new ArrayList<>();
		for (int i = 0; i < potentialDirections.size(); i++) {
			if (!excludes.contains(potentialDirections.get(i))) {
				possibleDirections.add(potentialDirections.get(i));
			}
		}
		if (possibleDirections.isEmpty()) {
			this.move();
		} else {
			if (possibleDirections.size() > 1) {
				this.moveTo(possibleDirections.get(this.random.nextInt(possibleDirections.size())));
			} else {
				this.moveTo(possibleDirections.get(0));
			}
		}
	}

	/**
	 * Allows to generate a random direction according to which direction the
	 * {@code WorldAgent} cannot take.
	 * 
	 * @return the random direction generated as a character which value is one of
	 *         the following: 'N', 'E', 'S' or 'W'
	 */
	private char randomDirection() {
		int randomDirection = this.random.nextInt(4);
		List<Character> excludes = this.excludedDirections();
		while (excludes.contains(this.directions[randomDirection])) {
			randomDirection = this.random.nextInt(4);
		}
		return this.directions[randomDirection];
	}

	/**
	 * Allows to evaluate the directions the {@code WorldAgent} cannot take
	 * according to its current position in the {@code World}.
	 * 
	 * @return the directions the agent cannot take as a {@code List} of characters,
	 *         which values can zero, one or two of the following: 'N', 'E', 'S' or
	 *         'W'
	 */
	private List<Character> excludedDirections() {
		List<Character> excludedDirections = new ArrayList<>();
		if (this.position.getX() <= 0) {
			excludedDirections.add('W');
		}
		if (this.position.getX() >= this.world.getWidth()) {
			excludedDirections.add('E');
		}
		if (this.position.getY() <= 0) {
			excludedDirections.add('N');
		}
		if (this.position.getY() >= this.world.getHeight()) {
			excludedDirections.add('S');
		}
		return excludedDirections;
	}

	/**
	 * Allows to determine the possible directions the {@code WorldAgent} can take
	 * in order to reach a particular destination.
	 * 
	 * @param destination the destination the agent wants to reach as a
	 *                    {@code Point}
	 * @return the possible directions the agent can take to reach its destination
	 *         as a {@code List} of characters, which values can be one or two of
	 *         the following: 'N', 'E', 'S' or 'W'
	 */
	private List<Character> destinationPossibleDirections(Point destination) {
		List<Character> possibleDirections = new ArrayList<>();
		if (this.position.getX() > destination.getX()) {
			possibleDirections.add('W');
		}
		if (this.position.getX() < destination.getX()) {
			possibleDirections.add('E');
		}
		if (this.position.getY() < destination.getY()) {
			possibleDirections.add('S');
		}
		if (this.position.getY() > destination.getY()) {
			possibleDirections.add('N');
		}
		return possibleDirections;
	}

	/**
	 * Allows to determine the possible directions the {@code WorldAgent} can take
	 * in order to avoid a particular position.
	 * 
	 * @param position the position to avoid as a {@code Point}
	 * @return the possible directions the agent can take to avoid the position as a
	 *         {@code List} of characters, which values can be one or two of the
	 *         following: 'N', 'E', 'S' or 'W'
	 */
	private List<Character> possibleDirectionsAwayFrom(Point position) {
		List<Character> possibleDirections = new ArrayList<>();
		if (this.position.getX() < position.getX()) {
			possibleDirections.add('W');
		}
		if (this.position.getX() > position.getX()) {
			possibleDirections.add('E');
		}
		if (this.position.getY() > position.getY()) {
			possibleDirections.add('S');
		}
		if (this.position.getY() < position.getY()) {
			possibleDirections.add('N');
		}
		if (this.position.getX() == position.getX() && this.position.getY() != position.getY()) {
			possibleDirections.add('S');
			possibleDirections.add('N');
		}
		if (this.position.getY() == position.getY() && this.position.getX() != position.getX()) {
			possibleDirections.add('W');
			possibleDirections.add('E');
		}
		return possibleDirections;
	}

	/**
	 * Allows to draw the {@code WorldAgent} in the {@code World}.
	 * 
	 * @param oldX    the old X axis position of the agent as a double
	 * @param oldY    the old Y axis position of the agent as a double
	 * @param oldSize the old size of the agent as an integer
	 * @param newX    the new X axis position of the agent as a double
	 * @param newY    the new Y axis position of the agent as a double
	 * @param newSize the new size of the agent as an integer
	 */
	protected void drawAgent(double oldX, double oldY, int oldSize, double newX, double newY, int newSize) {
		this.world.drawAgent(oldX, oldY, oldSize, newX, newY, newSize, this.getAgentColor());
	}

	/**
	 * Retrieves the color of the {@code WorldAgent} in the {@code World}. This
	 * method must be implemented by subclasses to return the desired color of the
	 * agent in the world.
	 * 
	 * @return the color of the agent as a {@code Color}
	 */
	protected abstract Color getAgentColor();

	/**
	 * Retrieves the {@code WorldAgent} size in the {@code World}. This method must
	 * be implemented by subclasses to return the desired size of the agent in the
	 * world.
	 * 
	 * @return the size of the agent as an integer
	 */
	protected abstract int getWorldAgentSize();

	/**
	 * Retrieves the {@code World} in which the {@code WorldAgent} evolves. This
	 * method must be implemented by subclasses to return the corresponding world in
	 * which the agent is evolving.
	 * 
	 * @return the world of the agent as a {@code World}
	 */
	protected abstract World getAgentWorld();

}
