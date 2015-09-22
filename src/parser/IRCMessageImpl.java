package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.IRCValues;

public class IRCMessageImpl implements IRCMessage, IRCValues {

	private IRCPrefix prefix;
	private String command;
	private IRCParametersImpl params;

	/**
	 * @param command
	 * @throws IllegalArgumentException
	 *             If command is null.
	 */
	public IRCMessageImpl(String command) {
		this(command, new ArrayList<String>());
	}

	/**
	 * @param command
	 * @param parameters
	 * @throws IllegalArgumentException
	 *             If command or parameters is null.
	 */
	public IRCMessageImpl(String command, String... parameters) {
		this(command);
		setParameters(parameters);
	}

	/**
	 * @param command
	 * @param parameters
	 * @throws IllegalArgumentException
	 *             If command is null.
	 */
	public IRCMessageImpl(String command, List<String> parameters) {
		this.command = command;
		if (parameters != null) {
			this.params = new IRCParametersImpl(parameters);
		}
	}

	IRCMessageImpl() {
	}

	@Override
	public void setParameters(String... parameters) {
		if (parameters == null) {
			throw new IllegalArgumentException();
		}
		this.params = new IRCParametersImpl(Arrays.asList(parameters));
	}

	@Override
	public String getParameter(int index) {
		return params != null ? params.getParameter(index) : null;
	}

	@Override
	public int amountOfParameters() {
		return params != null ? params.amountOfParameters() : 0;
	}

	@Override
	public boolean hasPrefix() {
		return prefix != null;
	}

	@Override
	public IRCPrefix getPrefix() {
		return prefix;
	}

	@Override
	public String getCommand() {
		return command;
	}

	@Override
	public IRCParameters getParameters() {
		return params;
	}
	
	@Override
	public List<String> getParametersAsList() {
		List<String> list = new ArrayList<String>();
		if (params != null) {
			list.addAll(params.getParametersAsList());
		}
		return list;
	}

	@Override
	public String getString() {
		String prefixString = "";
		if (prefix != null) {
			prefixString = PREFIX_INDICATOR + prefix.getString() + EMPTY_SPACE;
		}
		String paramsString = "";
		if (params != null) {
			paramsString = params.getString();
		}
		return prefixString + command + paramsString + MSG_END_SEQ;
	}

	void setPrefix(IRCPrefix prefix) {
		this.prefix = prefix;
	}

	void setCommand(String command) {
		this.command = command;
	}

	void setParams(IRCParametersImpl params) {
		this.params = params;
	}
}
