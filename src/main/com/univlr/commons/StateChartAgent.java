package main.com.univlr.commons;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.util.Logger;

/**
 * An abstract descriptor that represents a JADE agent using a
 * {@code StateChartBehaviour}.
 * 
 * @author Antoine Orgerit
 */
public abstract class StateChartAgent extends Agent {

	private static final long serialVersionUID = 1L;

	protected Logger logger;

	private Codec codec = new SLCodec();

	@Override
	public void setup() {
		this.logger = Logger.getMyLogger(this.getClass().getName());
		this.getContentManager().registerLanguage(codec);
	}

	/**
	 * Retrieves the {@code Codec} used by the {@code WorldAgent}.
	 * 
	 * @return the codec used by the agent as a {@code Codec}
	 */
	public Codec getCodec() {
		return this.codec;
	}

	/**
	 * Retrieves the {@code Ontology} corresponding to a particular message
	 * template.
	 * 
	 * @param messageOntologyTemplate the message ontology template to analyze as a
	 *                                {@code String}
	 * @return the corresponding ontology of the template as an {@code Ontology}
	 */
	public abstract Ontology getOntology(String messageOntologyTemplate);

	/**
	 * Allows to send a {@code ACLMessage} to one or more {@code WorldAgent}
	 * depending on the ontology template passed in argument.
	 * 
	 * @param ontologyTemplate the ontology template to use as a {@code String}
	 */
	public abstract void send(String ontologyTemplate);

}
