package main.com.univlr.pepper.commons;

/**
 * {@code PepperAction} used for controlling the voice of the Pepper robot.
 * 
 * @author Antoine Orgerit
 */
public abstract class Voice extends PepperAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Allows to make the Pepper robot talk.
	 * 
	 * @param text the text to talk about
	 * @throws PepperActionException if an error occurred during the execution of
	 *                               the corresponding script
	 */
	public void speak(String text) throws PepperActionException {
		String[] params = { text };
		this.executeAction("speak.py", params);
	}

	@Override
	public String getActionPart() {
		return "voice";
	}

}
