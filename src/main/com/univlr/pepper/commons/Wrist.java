package main.com.univlr.pepper.commons;

/**
 * {@code PepperAction} used for controlling one of the wrist of the Pepper
 * robot.
 * 
 * @author Antoine Orgerit
 */
public abstract class Wrist extends PepperAction {

	private static final long serialVersionUID = 1L;

	public static final String LEFT_WRIST = "LWristYaw";
	public static final String RIGHT_WRIST = "RWristYaw";

	@Override
	public String getActionPart() {
		return "wrist";
	}

	/**
	 * Allows to rotate an {@code Wrist} of the Pepper robot.
	 * 
	 * @param wrist the wrist to rotate, as one of the {@code String} defined in
	 *              LEFT_WRIST and RIGHT_WRIST
	 * @throws PepperActionException if an error occurred during the execution of
	 *                               the corresponding script
	 */
	public void rotate(String wrist) throws PepperActionException {
		String[] params = { wrist };
		this.executeAction("rotate.py", params);
	}

}
