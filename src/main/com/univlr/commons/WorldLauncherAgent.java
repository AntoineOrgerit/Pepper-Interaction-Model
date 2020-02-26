package main.com.univlr.commons;

import java.util.logging.Level;

import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;
import main.com.univlr.commons.ui.WorldLauncherAgentGui;
import main.com.univlr.commons.utils.world.World;

/**
 * Agent used to launch the {@code World} simulation.
 * 
 * @author Antoine Orgerit
 */
public abstract class WorldLauncherAgent extends GuiAgent {

	private static final long serialVersionUID = 1L;

	protected Logger logger;

	private Codec codec = new SLCodec();
	private Ontology jadeManagementOntology = JADEManagementOntology.getInstance();

	protected World world;

	@Override
	protected void setup() {
		this.logger = Logger.getMyLogger(this.getClass().getName());
		this.getContentManager().registerLanguage(this.codec);
		this.getContentManager().registerOntology(this.jadeManagementOntology);
		this.world = this.getWorld();
		WorldLauncherAgentGui gui = new WorldLauncherAgentGui(this);
		gui.setVisible(true);
		this.generateAgents();
	}

	/**
	 * Retrieves the {@code World} linked to the {@code WorldLauncherAgent}. This
	 * method must be implemented by the subclasses to return the corresponding
	 * world they are using.
	 * 
	 * @return the world linked to the launcher agent as a {@code World}
	 */
	public abstract World getWorld();

	/**
	 * Allows to generate the {@code WorldAgents} agents evolving in the
	 * {@code World}. This method must be implemented by subclasses to generate the
	 * agents existing in the defined world.
	 */
	public abstract void generateAgents();

	/**
	 * Allows to properly shutdown all agents on the JADE platform.
	 */
	private void shutdown() {
		this.logger.info("Stopping the system... It can take some time, please wait.");
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(this.getAMS());
		msg.setLanguage(this.codec.getName());
		msg.setOntology(this.jadeManagementOntology.getName());
		try {
			this.getContentManager().fillContent(msg, new Action(this.getAID(), new ShutdownPlatform()));
			send(msg);
		} catch (CodecException | OntologyException e) {
			this.logger.severe("Exception encountered while stopping the AMS: " + e);
		}
	}

	@Override
	protected void onGuiEvent(GuiEvent event) {
		if (event.getType() == WorldLauncherAgentGui.EXIT) {
			this.shutdown();
		} else {
			this.logger.log(Level.INFO, "The following event from the GUI is not currently supported: {0}", event);
		}
	}

}
