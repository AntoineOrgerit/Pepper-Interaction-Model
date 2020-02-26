package main.com.univlr.commons.behaviours;

/**
 * Exception thrown whenever an error occurred on the
 * {@code StateChartBehaviour} scope.
 * 
 * @author Antoine Orgerit
 */
public class StateChartBehaviourException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the {@code StateChartBehaviourException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 */
	public StateChartBehaviourException(String message) {
		super(message);
	}

	/**
	 * Constructor of the {@code StateChartBehaviourException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 * @param err     the error at the origin of the exception as a
	 *                {@code Throwable}
	 */
	public StateChartBehaviourException(String message, Throwable err) {
		super(message, err);
	}

}
