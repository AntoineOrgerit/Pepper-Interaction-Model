package main.com.univlr.commons.behaviours;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jade.core.behaviours.CyclicBehaviour;
import jade.util.Logger;
import main.com.univlr.commons.StateChartAgent;
import main.com.univlr.commons.utils.statechart.StateChartState;
import main.com.univlr.commons.utils.statechart.StateChartStateException;
import main.com.univlr.commons.utils.statechart.StateChartTransition;

/**
 * Represents the behaviour of a state chart. This behaviour uses an XML file in
 * input to dynamically load the states and transitions. It uses a
 * {@code StateChartState} to perform the different required actions.
 * 
 * @author Antoine Orgerit
 */
public class StateChartBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 1L;

	private Logger logger;

	private Random random;

	private File file;
	private String name;

	private static final String LOOK_IN_MODEL = "//Model[@name='";
	private static final String BEGIN_EXCEPTION_MESSAGE = "Exception encountered in the ";
	private static final String END_STATE = "EndState";

	private transient XMLInputFactory xmlif;
	private transient Validator validator;
	private transient DocumentBuilder builder;
	private transient XPath path;
	private transient Unmarshaller unmarshaller;

	private StateChartAgent agent;

	private StateChartState currentState;

	/**
	 * Constructor of the {@code StateChartBehaviour}.
	 * 
	 * @param agent    the JADE {@code Agent} using the behaviour
	 * @param name     the name of the model that the behaviour has to keep track in
	 *                 the XML file
	 * @param filename the name of the XML file to use
	 */
	public StateChartBehaviour(StateChartAgent agent, String name, String filename) {
		super(agent);
		try {
			this.random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			this.log(Level.CONFIG, "Couldn't instanciate SecureRandom for the " + this.agent.getName()
					+ " agent StateChartBehaviour. Using Random instead.");
			this.random = new Random();
		}
		this.logger = Logger.getMyLogger(myAgent.getClass().getName());
		this.file = new File(filename);
		this.name = name;
		this.agent = agent;
	}

	@Override
	public void onStart() {
		try {
			this.path = XPathFactory.newInstance().newXPath();
			this.unmarshaller = JAXBContext.newInstance(StateChartState.class).createUnmarshaller();
			this.generateFirstState();
		} catch (JAXBException | DOMException | StateChartBehaviourException e) {
			this.logger.severe(BEGIN_EXCEPTION_MESSAGE + myAgent.getName()
					+ " agent StateChartBehaviour at method onStart(). Stopping the agent.\n" + e);
			myAgent.doDelete();
		}
	}

	@Override
	public void action() {
		// ensuring that the agent hasn't been stopped
		if (this.agent.getState() != 6) {
			try {
				// checking if while loop or unique execution
				if (this.currentState.hasInvariant()) {
					while (this.currentState.evaluateInvariant()) {
						this.currentState.executeContent();
						myAgent.doWait(30);
					}
				} else {
					this.currentState.executeContent();
					myAgent.doWait(30);
				}

				StateChartState nextState = this.getNextState();
				// if no transition performed, we stay here
				if (nextState != null) {
					if (nextState.getName().equals(END_STATE)) {
						myAgent.doDelete();
					} else {
						this.currentState = nextState;
					}
				}
			} catch (StateChartStateException | StateChartBehaviourException e) {
				this.logger.severe(BEGIN_EXCEPTION_MESSAGE + myAgent.getName()
						+ " agent StateChartBehaviour at method action(). Stopping the agent.\n" + e);
				myAgent.doDelete();
			}
		}
	}

	/**
	 * Allows to generate the first state of the {@code StateChartBehaviour}.
	 * 
	 * @throws StateChartBehaviourException if an error occurred during the state
	 *                                      generation from the XML file
	 */
	private void generateFirstState() throws StateChartBehaviourException {
		try {
			Document doc = this.loadDocument();
			String nameFirstState = this.path.evaluate(LOOK_IN_MODEL + this.name + "']/InitialState", doc);
			Node firstState = (Node) this.path.evaluate(
					LOOK_IN_MODEL + this.name + "']/State[@name='" + nameFirstState + "']", doc, XPathConstants.NODE);
			this.convertXMLState(firstState);
		} catch (JAXBException | DOMException | XPathExpressionException | StateChartBehaviourException e) {
			throw new StateChartBehaviourException("Error while generating the first state of the model " + this.name
					+ " used by the agent " + this.agent.getName() + " from the file " + this.file.getName() + ".", e);
		}
	}

	/**
	 * Retrieves the next possible state according to the current
	 * {@code StateChartState} possible transitions. If more than one transition is
	 * possible, then a random one is selected.
	 * 
	 * @return the next state from the current one as a {@code StateChartState}
	 * @throws StateChartBehaviourException if an error occurred while retrieving
	 *                                      the possible next states from the XML
	 *                                      document, or while evaluating the
	 *                                      possible transitions
	 */
	private StateChartState getNextState() throws StateChartBehaviourException {
		List<StateChartState> possibleStates = this.getPossibleStates();
		if (possibleStates.isEmpty()) {
			return null;
		} else {
			if (possibleStates.size() > 1) {
				this.log(Level.FINE, "Multiple possible transitions found for the currentState " + this.name
						+ " of the " + this.agent.getName() + " agent. Executing random transition.");
				int randomTransition = this.random.nextInt(possibleStates.size());
				return possibleStates.get(randomTransition);
			} else {
				return possibleStates.get(0);
			}
		}
	}

	/**
	 * Retrieves the possible next states according to the current
	 * {@code StateChartState} possible transitions. The possibility of a transition
	 * is based on the next possible states invariant evaluation.
	 * 
	 * @return the possible states from the current one as a {@code List} of
	 *         {@code StateChartState}
	 * @throws StateChartBehaviourException if an error occurred while evaluating
	 *                                      the transitions from the current state
	 */
	private List<StateChartState> getPossibleStates() throws StateChartBehaviourException {
		List<StateChartState> possibleStates = new ArrayList<>();
		Node possibleNextStateNode;
		StateChartState possibleNextState;
		try {
			Document doc = this.loadDocument();
			for (StateChartTransition transition : this.currentState.getTransitions()) {
				if (transition.getNextState().equals(END_STATE)) {
					StateChartState endState = new StateChartState();
					endState.setName(END_STATE);
					possibleStates.add(endState);
				} else {
					possibleNextStateNode = (Node) this.path.evaluate(
							LOOK_IN_MODEL + this.name + "']/State[@name='" + transition.getNextState() + "']", doc,
							XPathConstants.NODE);
					possibleNextState = (StateChartState) this.unmarshaller.unmarshal(possibleNextStateNode);
					possibleNextState.setAgent(this.agent);
					if (possibleNextState.hasInvariant()) {
						if (possibleNextState.evaluateInvariant()) {
							possibleStates.add(possibleNextState);
						}
					} else {
						possibleStates.add(possibleNextState);
					}
				}
			}
		} catch (JAXBException | DOMException | XPathExpressionException | StateChartBehaviourException
				| StateChartStateException e) {
			throw new StateChartBehaviourException(
					"Error while trying to evaluate possible transitions from the agent " + myAgent.getName() + ".", e);
		}
		return possibleStates;
	}

	/**
	 * Allows to log a message to the JADE {@code Logger}.
	 * 
	 * @param level   the level of the logged message as a {@code Level}
	 * @param message the message to log as a {@code String}
	 */
	private void log(Level level, String message) {
		if (this.logger != null) {
			this.logger.log(level, message);
		}
	}

	/**
	 * Allows to convert a DOM XML state representation to a
	 * {@code StateChartState}.
	 * 
	 * @param state the DOM XML state to convert as a {@code Node}
	 * @throws JAXBException if an error occurred while unmarshalling the DOM XML
	 *                       state node to the {@code StateChartState}
	 */
	private void convertXMLState(Node state) throws JAXBException {
		this.currentState = (StateChartState) this.unmarshaller.unmarshal(state);
		this.currentState.setAgent(this.agent);
	}

	/**
	 * Allows to validate the XML file in entry of the {@code StateChartBehaviour}
	 * with the StateChartSchema.xsd file.
	 * 
	 * @throws StateChartBehaviourException if an error occurred while setting the
	 *                                      validator, or while validating the XML
	 *                                      file with the schema
	 */
	private void validateXML() throws StateChartBehaviourException {
		if (this.validator == null) {
			File schemaFile = new File("StateChartSchema.xsd");
			if (!file.exists()) {
				throw new StateChartBehaviourException("Unable to find the file StateChartSchema.xsd.");
			}
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			this.xmlif = XMLInputFactory.newInstance();
			this.xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
			this.xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
			this.xmlif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
			this.xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
			Schema schema;
			try {
				schema = schemaFactory.newSchema(schemaFile);
				validator = schema.newValidator();
				validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
				validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
			} catch (SAXException e) {
				throw new StateChartBehaviourException("Error while setting validator.", e);
			}
		}
		XMLEventReader xmlr;
		Source xmlFile;
		try (FileInputStream fis = new FileInputStream(this.file.getName())) {
			xmlr = this.xmlif.createXMLEventReader(this.file.getName(), fis);
			xmlFile = new StAXSource(xmlr);
			this.validator.validate(xmlFile);
		} catch (XMLStreamException | SAXException | IOException e) {
			throw new StateChartBehaviourException("Error while validating the XML file.", e);
		}
	}

	/**
	 * Allows to validate if every {@code receive} function defined in the XML
	 * document is included in a {@code NOT} element.
	 * 
	 * @param doc the DOM {@code Document} on which to validate the property
	 * @throws StateChartBehaviourException if an error occurred while retrieving
	 *                                      the {@code receive} functions in the
	 *                                      document, or if a {@code receive}
	 *                                      function is not declared in a
	 *                                      {@code NOT} element in the XML document
	 */
	private void validateFunctionsReceiveDefinition(Document doc) throws StateChartBehaviourException {
		NodeList receiveFunctions;
		try {
			receiveFunctions = (NodeList) this.path.evaluate(
					LOOK_IN_MODEL + this.name + "']//Function[./FunctionName='receive']", doc, XPathConstants.NODESET);
			for (int i = 0; i < receiveFunctions.getLength(); i++) {
				// receive function must be included in a NOT
				if (!receiveFunctions.item(i).getParentNode().getNodeName().equals("NOT")) {
					throw new StateChartBehaviourException(
							"Error: one of the receive function defined in the XML is not included in a NOT element.");
				}
			}
		} catch (XPathExpressionException e) {
			throw new StateChartBehaviourException("Error while retrieving the receive functions defined in the XML.",
					e);
		}
	}

	/**
	 * Allows to load the DOM document representation of the XML file in entry of
	 * the {@code StateChartBehaviour}.
	 * 
	 * @return the DOM document representation of the XML file in entry of the
	 *         behaviour as a {@code Document}
	 * @throws StateChartBehaviourException if an error occurred while validating
	 *                                      the XML, or while reading the file, or
	 *                                      while parsing the XML file
	 */
	private Document loadDocument() throws StateChartBehaviourException {
		try {
			this.validateXML();
			if (this.builder == null) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
				dbf.setNamespaceAware(true);
				this.builder = dbf.newDocumentBuilder();
			}
			Document doc = this.builder.parse(this.file);
			this.validateFunctionsReceiveDefinition(doc);
			return doc;
		} catch (StateChartBehaviourException | ParserConfigurationException | SAXException | IOException e) {
			throw new StateChartBehaviourException("Error loading the DOM document.", e);
		}
	}

}
