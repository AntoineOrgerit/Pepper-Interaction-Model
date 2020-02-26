package main.com.univlr.commons.utils.statechart;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.com.univlr.commons.StateChartAgent;

/**
 * Represents a state chart content (i.e. a body of a state or transition).
 * 
 * @author Antoine Orgerit
 */
@XmlRootElement(name = "Content")
public class StateChartContent extends StateChartComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Function")
	private List<StateChartFunction> functions;

	@Override
	protected void setAgent(StateChartAgent agent) {
		for (StateChartFunction function : this.functions) {
			function.setAgent(agent);
		}
	}

	/**
	 * Allows to execute the {@code StateChartFunction} functions contained in the
	 * {@code StateChartContent}.
	 * 
	 * @throws StateChartFunctionException if one of the functions can not be
	 *                                     executed or encountered an error during
	 *                                     the execution
	 */
	protected void execute() throws StateChartFunctionException {
		for (StateChartFunction function : this.functions) {
			function.execute();
		}
	}

}
