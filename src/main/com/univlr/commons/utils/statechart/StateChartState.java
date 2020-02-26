package main.com.univlr.commons.utils.statechart;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import main.com.univlr.commons.StateChartAgent;

/**
 * Represents a state of a state chart.
 * 
 * @author Antoine Orgerit
 */
@XmlRootElement(name = "State")
@XmlAccessorType(XmlAccessType.FIELD)
public class StateChartState extends StateChartComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "Invariant")
	private StateChartInvariant invariant;

	@XmlElement(name = "Content")
	private StateChartContent content;

	@XmlElementWrapper(name = "Transitions")
	@XmlElement(name = "Transition")
	private List<StateChartTransition> transitions;

	private StateChartAgent agent;

	@Override
	public void setAgent(StateChartAgent agent) {
		this.agent = agent;
		if (this.invariant != null) {
			this.invariant.setAgent(agent);
		}
		if (this.content != null) {
			this.content.setAgent(agent);
		}
	}

	/**
	 * Retrieves the name of the {@code StateChartState}.
	 * 
	 * @return the name of the state chart state as a {@code String}
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Allows to set the name of the {@code StateChartState}.
	 * 
	 * @param name the name of the state chart state as a {@code String}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Allows to check if the {@code StateChartState} has a
	 * {@code StateChartInvariant} or not.
	 * 
	 * @return {@code true} if the state has an invariant, {@code false} otherwise
	 */
	public boolean hasInvariant() {
		return this.invariant != null;
	}

	/**
	 * Allows to evaluate the {@code StateChartInvariant} of the
	 * {@code StateChartState}.
	 * 
	 * @return {@code true} if the invariant is evaluated to true, {@code false}
	 *         otherwise
	 * @throws StateChartStateException if an error occurred while evaluating the
	 *                                  invariant at the execution of the invariant
	 *                                  functions
	 */
	public boolean evaluateInvariant() throws StateChartStateException {
		if (this.invariant == null) {
			// preventing possible loop
			return false;
		} else {
			try {
				return this.invariant.evaluate();
			} catch (StateChartFunctionException e) {
				throw new StateChartStateException("Error while evaluating the invariant for the current state "
						+ this.name + " of the agent " + agent.getName() + ".", e);
			}
		}
	}

	/**
	 * Allows to execute the {@code StateChartContent} of the
	 * {@code StateChartState}.
	 * 
	 * @throws StateChartStateException if an error occurred during the execution of
	 *                                  the content functions
	 */
	public void executeContent() throws StateChartStateException {
		if (this.content != null) {
			try {
				this.content.execute();
			} catch (StateChartFunctionException e) {
				throw new StateChartStateException("Error while executing the content for the current state "
						+ this.name + " of the agent " + agent.getName() + ".", e);
			}
		}
	}

	/**
	 * Retrieves the transitions from the {@code StateChartState}.
	 * 
	 * @return the transitions from the state as a {@code List} of
	 *         {@code StateChartTransition}
	 */
	public List<StateChartTransition> getTransitions() {
		return this.transitions;
	}

}
