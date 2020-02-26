package main.com.univlr.pepper.commons;

/**
 * {@code PepperAction} used for controlling one of the elbow of the Pepper
 * robot.
 * 
 * @author Antoine Orgerit
 */
public abstract class Elbow extends PepperAction {

	private static final long serialVersionUID = 1L;

	public static final String LEFT_ELBOW = "LElbow";
	public static final String RIGHT_ELBOW = "RElbow";

	@Override
	public String getActionPart() {
		return "elbow";
	}

	/**
	 * Allows to roll an {@code Elbow} of the Pepper robot.
	 * 
	 * @param elbow the elbow to roll, as one of the {@code String} defined in
	 *              LEFT_ELBOW and RIGHT_ELBOW
	 * @throws PepperActionException if an error occurred during the execution of
	 *                               the corresponding script
	 */
	public void rolling(String elbow) throws PepperActionException {
		String[] params = { elbow + "Roll" };
		this.executeAction("rolling.py", params);
	}

}
