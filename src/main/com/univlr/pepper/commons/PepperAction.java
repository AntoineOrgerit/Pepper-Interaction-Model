package main.com.univlr.pepper.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import main.com.univlr.commons.StateChartAgent;

/**
 * Represents an action that can be applied to the Pepper robot, according to
 * certain Python scripts.
 * 
 * @author Antoine Orgerit
 */
public abstract class PepperAction extends StateChartAgent {

	private static final long serialVersionUID = 1L;

	private static final String COMMAND_EXECUTABLE = "python";

	protected Codec codec = new SLCodec();

	@Override
	public void setup() {
		this.getContentManager().registerLanguage(this.codec);
	}

	public abstract String getActionPart();

	/**
	 * Allows to execute a {@code PepperAction} on the Pepper robot without any
	 * result needed.
	 * 
	 * @param script the script to call to execute the action
	 * @param params the parameters to use with the script
	 * @throws PepperActionException if an error occurred during the execution of
	 *                               the corresponding script
	 */
	public void executeAction(String script, String[] params) throws PepperActionException {
		List<String> command = new ArrayList<>();
		command.add(COMMAND_EXECUTABLE);
		command.add("scripts/" + (this.getActionPart().equals("") ? script : this.getActionPart() + "/" + script));
		command.addAll(Arrays.asList(params));
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command.toArray(new String[0]));
			p.waitFor();
			p.destroy();
		} catch (IOException e) {
			throw new PepperActionException("Call to script " + script + " failed.", e);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			throw new PepperActionException("Script " + script + " got interrupted.", ie);
		}
	}

	/**
	 * Allows to execute a {@code PepperAction} on the Pepper robot with a returned
	 * result if needed.
	 * 
	 * @param script the script to call to execute the action
	 * @param params the parameters to use with the script
	 * @return the script result as a {@code String}
	 * @throws PepperActionException if an error occurred during the execution of
	 *                               the corresponding script
	 */
	public String executeActionWithResult(String script, String[] params) throws PepperActionException {
		List<String> command = new ArrayList<>();
		command.add(COMMAND_EXECUTABLE);
		command.add("scripts/" + script);
		command.addAll(Arrays.asList(params));
		Process p = null;
		StringBuilder result = new StringBuilder();
		try {
			p = Runtime.getRuntime().exec(command.toArray(new String[0]));
			BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = bri.readLine()) != null) {
				result.append(line + "\n");
			}
			p.waitFor();
			p.destroy();
			bri.close();
			return result.toString();
		} catch (IOException e) {
			throw new PepperActionException("Call to script " + script + " failed.", e);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			throw new PepperActionException("Script " + script + " got interrupted.", ie);
		}
	}

}
