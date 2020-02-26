package main.com.univlr.pepper.commons;

/**
 * Exception thrown whenever an error occurred on the
 * {@code PepperAction} scope.
 * 
 * @author Antoine Orgerit
 */
public class PepperActionException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the {@code PepperActionException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 */
	public PepperActionException(String message) {
		super(message);
	}

	/**
	 * Constructor of the {@code PepperActionException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 * @param err     the error at the origin of the exception as a
	 *                {@code Throwable}
	 */
	public PepperActionException(String message, Throwable err) {
		super(message, err);
	}

}
