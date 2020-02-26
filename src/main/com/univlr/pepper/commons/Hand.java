package main.com.univlr.pepper.commons;

/**
 * {@code PepperAction} used for controlling one of the hand of the Pepper
 * robot.
 * 
 * @author Antoine Orgerit
 */
public abstract class Hand extends PepperAction {

	private static final long serialVersionUID = 1L;

	public static final String LEFT_HAND = "LHand";
	public static final String RIGHT_HAND = "RHand";

	/**
	 * Allows to open a {@code Hand} of the Pepper robot.
	 * 
	 * @param hand the hand to open, as one of the {@code String} defined in
	 *             LEFT_HAND and RIGHT_HAND
	 * @throws PepperActionException if an error occurred during the execution of
	 *                               the corresponding script
	 */
	public void open(String hand) throws PepperActionException {
		String[] params = { hand };
		this.executeAction("open.py", params);
	}

	/**
	 * Allows to close a {@code Hand} of the Pepper robot.
	 * 
	 * @param hand the hand to close, as one of the {@code String} defined in
	 *             LEFT_HAND and RIGHT_HAND
	 * @throws PepperActionException if an error occurred during the execution of
	 *                               the corresponding script
	 */
	public void close(String hand) throws PepperActionException {
		String[] params = { hand };
		this.executeAction("close.py", params);
	}

	@Override
	public String getActionPart() {
		return "hand";
	}

}