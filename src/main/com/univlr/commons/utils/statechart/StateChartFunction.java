package main.com.univlr.commons.utils.statechart;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import jade.lang.acl.MessageTemplate;
import main.com.univlr.commons.StateChartAgent;

/**
 * Represents a function contained in a {@code StateChartComponent}.
 * 
 * @author Antoine Orgerit
 */
@XmlType(name = "FunctionType")
public class StateChartFunction extends StateChartInvariantComponent {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "FunctionName")
	private String functionName;

	@XmlElementWrapper(name = "FunctionParameters")
	@XmlElement(name = "FunctionParameter")
	private List<String> parameters;

	private StateChartAgent agent;
	private boolean triggered = false;

	@Override
	public void setAgent(StateChartAgent agent) {
		this.agent = agent;
	}

	@Override
	protected boolean evaluate() throws StateChartFunctionException {
		if (this.agent == null) {
			throw new StateChartFunctionException("Error: no agent sets for the function " + this.functionName + ".");
		} else {
			if (this.functionName.equals("receive")) {
				MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchLanguage(agent.getCodec().getName()),
						MessageTemplate.MatchOntology(agent.getOntology(this.parameters.get(0)).getName()));
				if (agent.receive(mt) != null) {
					this.triggered = true;
				}
				return this.triggered;
			} else {
				Method method;
				try {
					if (this.parameters == null) {
						method = this.agent.getClass().getMethod(this.functionName, (Class<?>[]) null);
						return (boolean) method.invoke(this.agent, (Object[]) null);
					} else {
						method = this.agent.getClass().getMethod(this.functionName, String.class);
						return (boolean) method.invoke(this.agent, this.parameters.toArray());
					}
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new StateChartFunctionException("Error while invoking method " + this.functionName
							+ " from the StateChartAgent " + this.agent.getName() + ".", e);
				}
			}
		}
	}

	/**
	 * Allows to execute the {@code StateChartFunction} according to the
	 * {@code StateChartAgent} on which to invoke the function.
	 * 
	 * @throws StateChartFunctionException if no agent is set for the function, or
	 *                                     if an error occurred while invoking the
	 *                                     function
	 */
	public void execute() throws StateChartFunctionException {
		if (this.agent == null) {
			throw new StateChartFunctionException("Error: no agent sets for the function " + this.functionName + ".");
		} else {
			Method method;
			try {
				if (this.parameters == null) {
					method = this.agent.getClass().getMethod(this.functionName, (Class<?>[]) null);
					method.invoke(this.agent, (Object[]) null);
				} else {
					method = this.agent.getClass().getMethod(this.functionName, String.class);
					method.invoke(this.agent, this.parameters.toArray());
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new StateChartFunctionException("Error while invoking method " + this.functionName
						+ " from the StateChartAgent " + this.agent.getName() + ".", e);
			}
		}
	}

}
