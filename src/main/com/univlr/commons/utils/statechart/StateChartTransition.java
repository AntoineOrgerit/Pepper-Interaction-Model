package main.com.univlr.commons.utils.statechart;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import main.com.univlr.commons.StateChartAgent;

/**
 * Represents a transition to a next state of a state chart.
 * 
 * @author Antoine Orgerit
 */
@XmlType(name = "TransitionType")
public class StateChartTransition extends StateChartComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlValue
	private String nextState;

	/**
	 * Retrieves the next state of the state chart pointed by the
	 * {@code StateChartTransition}
	 * 
	 * @return the next state of the state chart pointed by the transition as a
	 *         {@code String}
	 */
	public String getNextState() {
		return this.nextState;
	}

	@Override
	protected void setAgent(StateChartAgent agent) {
		// agent not necessary here
	}

}
