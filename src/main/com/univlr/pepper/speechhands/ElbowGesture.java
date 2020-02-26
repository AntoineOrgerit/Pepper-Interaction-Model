package main.com.univlr.pepper.speechhands;

import java.util.logging.Level;

import jade.content.onto.Ontology;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import main.com.univlr.commons.behaviours.StateChartBehaviour;
import main.com.univlr.pepper.commons.Elbow;
import main.com.univlr.pepper.commons.PepperActionException;
import main.com.univlr.pepper.speechhands.ontology.HandGestureOntology;

/**
 * Represents an elbow gesture during the first automate test on the Pepper
 * robot.
 * 
 * @author Antoine Orgerit
 */
public class ElbowGesture extends Elbow {

	private static final long serialVersionUID = 1L;

	protected Ontology handGestureOntology = HandGestureOntology.getInstance();

	private String side;

	@Override
	public void setup() {
		this.getContentManager().registerOntology(this.handGestureOntology);
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("hand-gesture");
		sd.setName(this.getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			this.logger.log(Level.SEVERE, "Error while registering the service for {0}. Stopping the agent.",
					this.getLocalName());
			this.doDelete();
		}
		super.setup();
		Object[] args = this.getArguments();
		this.addBehaviour(new StateChartBehaviour(this, "ElbowGesture", (String) args[0]));
		this.side = (String) args[1];
	}

	/**
	 * Allows to roll the elbow during the {@code ElbowGesture}.
	 * 
	 * @throws PepperActionException if an error occurred during the execution of
	 *                               the gesture.
	 */
	public void rolling() throws PepperActionException {
		super.rolling(this.side);
	}

	@Override
	public Ontology getOntology(String messageOntologyTemplate) {
		if (messageOntologyTemplate.equals("HAND_GESTURE")) {
			return this.handGestureOntology;
		} else {
			throw new UnsupportedOperationException(
					"This ontology template does not exist or is not supported yet: " + messageOntologyTemplate);
		}
	}

	@Override
	public void send(String ontologyTemplate) {
		throw new UnsupportedOperationException("This method should not be called for agent " + this.getName() + ".");
	}

}
