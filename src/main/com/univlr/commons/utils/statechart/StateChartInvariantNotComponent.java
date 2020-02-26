package main.com.univlr.commons.utils.statechart;

/**
 * Represents the negation of a {@code StateChartInvariantComponent} of a state
 * chart.
 * 
 * @author Antoine Orgerit
 */
public class StateChartInvariantNotComponent extends StateChartInvariant {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean evaluate() throws StateChartFunctionException {
		return !super.evaluate();
	}

}
