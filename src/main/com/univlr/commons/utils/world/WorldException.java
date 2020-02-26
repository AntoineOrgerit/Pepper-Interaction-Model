package main.com.univlr.commons.utils.world;

/**
 * Exception thrown whenever an error occurred on the {@code World} scope.
 * 
 * @author Antoine Orgerit
 */
public class WorldException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the {@code WorldException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 */
	public WorldException(String message) {
		super(message);
	}

	/**
	 * Constructor of the {@code WorldException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 * @param err     the error at the origin of the exception as a
	 *                {@code Throwable}
	 */
	public WorldException(String message, Throwable err) {
		super(message, err);
	}

}
