package main.com.univlr.commons;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.core.Agent;
import jade.util.Logger;

/**
 * Agent used to launch the simulation.
 * 
 * @author Antoine Orgerit
 */
public abstract class LauncherAgent extends Agent {

	private static final long serialVersionUID = 1L;

	protected Logger logger;

	private Codec codec = new SLCodec();
	private Ontology jadeManagementOntology = JADEManagementOntology.getInstance();

	@Override
	protected void setup() {
		this.logger = Logger.getMyLogger(this.getClass().getName());
		this.getContentManager().registerLanguage(this.codec);
		this.getContentManager().registerOntology(this.jadeManagementOntology);
		this.generateAgents();
	}

	/**
	 * Allows to generate the {@code Agent} agents evolving in the
	 * simulation.
	 */
	public abstract void generateAgents();

}
