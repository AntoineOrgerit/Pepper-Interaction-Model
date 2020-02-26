package main.com.univlr.pepper.speechhands.ontology;

import java.util.logging.Level;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.util.Logger;

/**
 * {@code Ontology} use when informing a gesture agent about the start of the
 * {@code HandGesture}.
 * 
 * @author Antoine Orgerit
 */
public class HandGestureOntology extends Ontology {

	private static final long serialVersionUID = 1L;

	public static final String ONTOLOGY_NAME = "Hand-gesture-ontology";

	public static final String HAND_GESTURE = "HandGesture";

	private static Ontology instance = new HandGestureOntology();

	/**
	 * Allows to get an instance of the {@code HandGestureOntology}.
	 * 
	 * @return the instance of the ontology as an {@code Ontology}
	 */
	public static Ontology getInstance() {
		return instance;
	}

	/**
	 * Constructor of the {@code HandGestureOntology}.
	 */
	private HandGestureOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {
			this.add(new AgentActionSchema(HAND_GESTURE), HandGestureMessage.class);
		} catch (OntologyException e) {
			Logger.getMyLogger(this.getClass().getName()).log(Level.SEVERE,
					"Error encountered while creating the HandGestureOntology: {0}", e);
		}
	}

}
