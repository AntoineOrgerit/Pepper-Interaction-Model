package main.com.univlr.commons.utils.world;

import java.awt.Point;
import java.io.Serializable;

/**
 * Represents a World entity existing inside the {@code World}.
 * 
 * @author Antoine Orgerit
 */
public abstract class WorldEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Point position;

	/**
	 * Constructor of the {@code WorldEntity}.
	 * 
	 * @param position the position of the entity in the {@code World} as a
	 *                 {@code Point} object
	 */
	public WorldEntity(Point position) {
		this.position = position;
	}

	/**
	 * Retrieves the position of the {@code WorldEntity}.
	 * 
	 * @return the position of the entity in the {@code World} as a {@code Point}
	 *         object
	 */
	public Point getPosition() {
		return this.position;
	}

	/**
	 * Retrieves the {@code WorldEntity} previous size in the {@code World}.
	 * 
	 * @return the previous size of the entity as an integer
	 */
	public abstract int getEntityPreviousSize();

	/**
	 * Retrieves the {@code WorldEntity} size in the {@code World}.
	 * 
	 * @return the size of the entity as an integer
	 */
	public abstract int getEntitySize();

}
