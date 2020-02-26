package main.com.univlr.commons.utils.statechart;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Represents a component of a {@code StateChartInvariant} of a state chart.
 * 
 * @author Antoine Orgerit
 */
@XmlTransient
public abstract class StateChartInvariantComponent extends StateChartComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Allows to evaluate the {@code StateChartInvariantComponent} depending on what
	 * it contains.
	 * 
	 * @return {@code true} if the invariant component is evaluated to true,
	 *         {@code false} otherwise
	 * @throws StateChartFunctionException if an error occurred while evaluating the
	 *                                     invariant component functions
	 */
	protected abstract boolean evaluate() throws StateChartFunctionException;

}
