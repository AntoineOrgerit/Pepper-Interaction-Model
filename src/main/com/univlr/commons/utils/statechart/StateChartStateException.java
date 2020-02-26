package main.com.univlr.commons.utils.statechart;

/**
 * Exception thrown whenever an error occurred on the {@code StateChartState}
 * scope.
 * 
 * @author Antoine Orgerit
 */
public class StateChartStateException extends StateChartException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the {@code StateChartStateException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 */
	public StateChartStateException(String message) {
		super(message);
	}

	/**
	 * Constructor of the {@code StateChartStateException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 * @param err     the error at the origin of the exception as a
	 *                {@code Throwable}
	 */
	public StateChartStateException(String message, Throwable err) {
		super(message, err);
	}

}
