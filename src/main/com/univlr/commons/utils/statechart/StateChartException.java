package main.com.univlr.commons.utils.statechart;

/**
 * Exception thrown whenever an error occurred on the state chart scope.
 * 
 * @author Antoine Orgerit
 */
public class StateChartException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the {@code StateChartException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 */
	public StateChartException(String message) {
		super(message);
	}

	/**
	 * Constructor of the {@code StateChartException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 * @param err     the error at the origin of the exception as a
	 *                {@code Throwable}
	 */
	public StateChartException(String message, Throwable err) {
		super(message, err);
	}

}
