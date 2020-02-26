package main.com.univlr.commons.utils.statechart;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a state chart invariant binary OR component linking two
 * {@code StateChartInvariantComponent}.
 * 
 * @author Antoine Orgerit
 */
@XmlRootElement(name = "OR")
public class StateChartInvariantOrComponent extends StateChartInvariantBinaryComponent {

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean evaluate() throws StateChartFunctionException {
		return (this.getLeftComponent().evaluate() || this.getRightComponent().evaluate());
	}

}
