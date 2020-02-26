package main.com.univlr.commons.utils.statechart;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import main.com.univlr.commons.StateChartAgent;

/**
 * Represents a state chart invariant binary component linking two
 * {@code StateChartInvariantComponent}.
 * 
 * @author Antoine Orgerit
 */
@XmlType(name = "BinaryInvariantOperatorType")
public abstract class StateChartInvariantBinaryComponent extends StateChartInvariantComponent {

	private static final long serialVersionUID = 1L;

	@XmlElements({ @XmlElement(name = "Function", type = StateChartFunction.class),
			@XmlElement(name = "AND", type = StateChartInvariantAndComponent.class),
			@XmlElement(name = "OR", type = StateChartInvariantOrComponent.class),
			@XmlElement(name = "NOT", type = StateChartInvariantNotComponent.class) })
	private StateChartInvariantComponent[] components;

	/**
	 * Private constructor of the {@code StateChartInvariantBinaryComponent} used by
	 * JAXB.
	 */
	protected StateChartInvariantBinaryComponent() {
		this.components = new StateChartInvariantComponent[2];
	}

	/**
	 * Retrieves the left component of the
	 * {@code StateChartInvariantBinaryComponent}.
	 * 
	 * @return the left component of the invariant binary component as a {@code
	 *         StateChartInvariantComponent}
	 */
	protected StateChartInvariantComponent getLeftComponent() {
		return this.components[0];
	}

	/**
	 * Retrieves the right component of the
	 * {@code StateChartInvariantBinaryComponent}.
	 * 
	 * @return the right component of the invariant binary component as a {@code
	 *         StateChartInvariantComponent}
	 */
	protected StateChartInvariantComponent getRightComponent() {
		return this.components[1];
	}

	@Override
	protected void setAgent(StateChartAgent agent) {
		this.getLeftComponent().setAgent(agent);
		this.getRightComponent().setAgent(agent);
	}

}
