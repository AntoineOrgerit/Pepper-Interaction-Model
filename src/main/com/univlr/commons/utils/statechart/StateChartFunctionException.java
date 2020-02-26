package main.com.univlr.commons.utils.statechart;

/**
 * Exception thrown whenever an error occurred on the {@code StateChartFunction}
 * scope.
 * 
 * @author Antoine Orgerit
 */
public class StateChartFunctionException extends StateChartException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the {@code StateChartFunctionException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 */
	public StateChartFunctionException(String message) {
		super(message);
	}

	/**
	 * Constructor of the {@code StateChartFunctionException}.
	 * 
	 * @param message the message to include in the exception as a {@code String}
	 * @param err     the error at the origin of the exception as a
	 *                {@code Throwable}
	 */
	public StateChartFunctionException(String message, Throwable err) {
		super(message, err);
	}

}
