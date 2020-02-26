package main.com.univlr.commons.utils.statechart;

import javax.xml.bind.annotation.XmlTransient;

import main.com.univlr.commons.StateChartAgent;

/**
 * An abstract descriptor that represents a component of a state chart.
 * 
 * @author Antoine Orgerit
 */
@XmlTransient
public abstract class StateChartComponent {

	/**
	 * Allows to set the agent to the {@code StateChartComponent} and/or its
	 * subcomponents. This method must be implemented to properly define the setting
	 * of the agent to the component.
	 * 
	 * @param agent the agent to set to the component as a {@code StateChartAgent}
	 */
	protected abstract void setAgent(StateChartAgent agent);

}
