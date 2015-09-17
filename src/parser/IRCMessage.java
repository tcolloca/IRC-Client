package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.IRCValues;

/**
 * Represents a message of IRC according to the BNF representation specified in
 * the RFC 1459.
 * 
 * @author Tomas
 */
public class IRCMessage implements IRCValues {

	private IRCPrefix prefix;
	private String command;
	private IRCParameters params;

	/**
	 * @param command
	 * @throws IllegalArgumentException
	 *             If command is null.
	 */
	public IRCMessage(String command) {
		this(command, new ArrayList<String>());
	}

	/**
	 * @param command
	 * @param parameters
	 * @throws IllegalArgumentException
	 *             If command or parameters is null.
	 */
	public IRCMessage(String command, String... parameters) {
		this(command);
		setParameters(parameters);
	}

	/**
	 * @param command
	 * @param parameters
	 * @throws IllegalArgumentException
	 *             If command is null.
	 */
	public IRCMessage(String command, List<String> parameters) {
		this.command = command;
		if (parameters != null) {
			this.params = new IRCParameters(parameters);
		}
	}

	IRCMessage() {
	}

	/**
	 * @param parameters
	 * @throws IllegalArgumentException
	 *             If parameters is null.
	 */
	public void setParameters(String... parameters) {
		if (parameters == null) {
			throw new IllegalArgumentException();
		}
		this.params = new IRCParameters(Arrays.asList(parameters));
	}

	/**
	 * Returns the parameter in the index position (starting from 0), or null if
	 * there is no parameter in such position.
	 * 
	 * @param index
	 * @return the parameter in the index position (starting from 0), or null if
	 *         there is no parameter in such position.
	 */
	public String getParameter(int index) {
		return params != null ? params.getParameter(index) : null;
	}

	/**
	 * Returns the amount of parameters.
	 * 
	 * @return the amount of parameters.
	 */
	public int amountOfParameters() {
		return params != null ? params.amountOfParameters() : 0;
	}

	public boolean hasPrefix() {
		return prefix != null;
	}

	public IRCPrefix getPrefix() {
		return prefix;
	}

	public String getCommand() {
		return command;
	}

	public IRCParameters getParams() {
		return params;
	}

	void setPrefix(IRCPrefix prefix) {
		this.prefix = prefix;
	}

	void setCommand(String command) {
		this.command = command;
	}

	void setParams(IRCParameters params) {
		this.params = params;
	}

	@Override
	public String toString() {
		String prefixString = "";
		if (prefix != null) {
			prefixString = PREFIX_INDICATOR + prefix.toString() + EMPTY_SPACE;
		}
		String paramsString = "";
		if (params != null) {
			paramsString = params.toString();
		}
		return prefixString + command + paramsString + MSG_END_SEQ;
	}
}
