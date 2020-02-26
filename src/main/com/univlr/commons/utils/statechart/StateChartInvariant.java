package main.com.univlr.commons.utils.statechart;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import main.com.univlr.commons.StateChartAgent;

/**
 * Represents a state chart invariant (i.e. a guard of a state or transition).
 * 
 * @author Antoine Orgerit
 */
@XmlType(name = "InvariantType")
public class StateChartInvariant extends StateChartInvariantComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElements({ @XmlElement(name = "Function", type = StateChartFunction.class),
			@XmlElement(name = "AND", type = StateChartInvariantAndComponent.class),
			@XmlElement(name = "OR", type = StateChartInvariantOrComponent.class),
			@XmlElement(name = "NOT", type = StateChartInvariantNotComponent.class) })
	private StateChartInvariantComponent component;

	@Override
	protected void setAgent(StateChartAgent agent) {
		this.component.setAgent(agent);
	}

	/**
	 * Allows to check if the {@code StateChartInvariant} is empty or not.
	 * 
	 * @return {@code true} if the invariant is empty, {@code false} otherwise
	 */
	protected boolean isEmpty() {
		return this.component == null;
	}

	@Override
	protected boolean evaluate() throws StateChartFunctionException {
		return this.component.evaluate();
	}

}
