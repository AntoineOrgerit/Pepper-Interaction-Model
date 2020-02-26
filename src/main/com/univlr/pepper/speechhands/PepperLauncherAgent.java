package main.com.univlr.pepper.speechhands;

import java.util.logging.Level;

import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import main.com.univlr.commons.LauncherAgent;
import main.com.univlr.pepper.commons.Elbow;
import main.com.univlr.pepper.commons.Hand;
import main.com.univlr.pepper.commons.Wrist;

/**
 * {@code LauncherAgent} used for the first test of automata on the Pepper
 * robot.
 * 
 * @author Antoine Orgerit
 */
public class PepperLauncherAgent extends LauncherAgent {

	private static final long serialVersionUID = 1L;

	@Override
	public void generateAgents() {
		String[] args = null;
		PlatformController container = this.getContainerController();
		try {
			args = new String[] { "PepperSpeechHands.xml" };
			AgentController speech = container.createNewAgent("Speech", "main.com.univlr.pepper.speechhands.Speech",
					args);
			speech.start();
			args = new String[] { "PepperSpeechHands.xml", Hand.LEFT_HAND };
			AgentController handGesture = container.createNewAgent("HandGestureLeft",
					"main.com.univlr.pepper.speechhands.HandGesture", args);
			handGesture.start();
			args = new String[] { "PepperSpeechHands.xml", Hand.RIGHT_HAND };
			handGesture = container.createNewAgent("HandGestureRight", "main.com.univlr.pepper.speechhands.HandGesture",
					args);
			handGesture.start();
			args = new String[] { "PepperSpeechHands.xml", Wrist.LEFT_WRIST };
			AgentController wristGesture = container.createNewAgent("WristGestureLeft",
					"main.com.univlr.pepper.speechhands.WristGesture", args);
			wristGesture.start();
			args = new String[] { "PepperSpeechHands.xml", Wrist.RIGHT_WRIST };
			wristGesture = container.createNewAgent("WristGestureRight",
					"main.com.univlr.pepper.speechhands.WristGesture", args);
			wristGesture.start();
			args = new String[] { "PepperSpeechHands.xml", Elbow.LEFT_ELBOW };
			AgentController elbowGesture = container.createNewAgent("ElbowGestureLeft",
					"main.com.univlr.pepper.speechhands.ElbowGesture", args);
			elbowGesture.start();
			args = new String[] { "PepperSpeechHands.xml", Elbow.RIGHT_ELBOW };
			elbowGesture = container.createNewAgent("ElbowGestureRight",
					"main.com.univlr.pepper.speechhands.ElbowGesture", args);
			elbowGesture.start();
		} catch (ControllerException e) {
			this.logger.log(Level.SEVERE,
					"An error occured while generating the agent of the simulation of " + this.getName() + ": {0}", e);
		}
	}

}
