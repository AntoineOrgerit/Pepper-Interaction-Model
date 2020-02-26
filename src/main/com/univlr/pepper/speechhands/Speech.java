package main.com.univlr.pepper.speechhands;

import java.util.logging.Level;

import jade.content.AgentAction;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import main.com.univlr.commons.behaviours.StateChartBehaviour;
import main.com.univlr.pepper.commons.Voice;
import main.com.univlr.pepper.speechhands.ontology.HandGestureMessage;
import main.com.univlr.pepper.speechhands.ontology.HandGestureOntology;

/**
 * Represents the speech during the first automate test on the Pepper
 * robot.
 * 
 * @author Antoine Orgerit
 */
public class Speech extends Voice {

	private static final long serialVersionUID = 1L;
	
	protected Ontology handGestureOntology = HandGestureOntology.getInstance();
	
	@Override
	public void setup() {
		this.getContentManager().registerOntology(this.handGestureOntology);
		super.setup();
		Object[] args = this.getArguments();
		this.addBehaviour(new StateChartBehaviour(this, "Speech", (String) args[0]));
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
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("hand-gesture");
		dfd.addServices(sd);
		DFAgentDescription[] result;
		try {
			result = DFService.search(this, dfd);
			if (result.length > 0) {
				for(int r=0; r<result.length; r++) {
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setLanguage(this.codec.getName());
					if (ontologyTemplate.equals("HAND_GESTURE")) {
						msg.setOntology(handGestureOntology.getName());
						this.getContentManager().fillContent(msg,
								new Action(result[r].getName(), (AgentAction) new HandGestureMessage()));
						msg.addReceiver(result[r].getName());
						this.send(msg);
					} else {
						throw new UnsupportedOperationException("This agent does not implement this operation yet.");
					}
				}
			}
		} catch (CodecException | OntologyException | FIPAException e) {
			this.logger.log(Level.WARNING,
					"An error occured while the " + this.getName()
							+ " agent was sending a message with the ontology template " + ontologyTemplate + ": {0}",
					e);
		}
	}

}
