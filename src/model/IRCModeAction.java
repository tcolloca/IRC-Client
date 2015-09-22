package model;

import util.IRCValues;

public class IRCModeAction implements IRCValues {

	private IRCMode mode;
	private char action;
	private String[] parameters;

	/**
	 * @param mode
	 *            Mode that is changing.
	 * @param action
	 *            '+' if adding the mode, '-' if removing the mode.
	 * @param parameters
	 *            Parameters if the mode has any.
	 * @throw {@link IllegalArgumentException} If mode is null, action is
	 *        neither '+' nor '-', or any parameter is null.
	 */
	public IRCModeAction(IRCMode mode, char action, String... parameters) {
		if (mode == null || (action != DEL && action != ADD)) {
			throw new IllegalArgumentException();
		}
		for (String parameter : parameters) {
			if (parameter == null) {
				throw new IllegalArgumentException();
			}
		}
		this.mode = mode;
		this.action = action;
		this.parameters = parameters;
	}

	public IRCMode getMode() {
		return mode;
	}

	public char getAction() {
		return action;
	}

	public String[] getParameters() {
		return parameters;
	}

	public String getString() {
		return "" + action + mode.getLetter() + getParamsString();
	}

	private String getParamsString() {
		String ans = "" + EMPTY_SPACE;
		for (int i = 0; i < parameters.length - 1; i++) {
			ans += parameters[i] + " ";
		}
		ans += TRAILING_INDICATOR + parameters[parameters.length - 1];
		return ans;
	}
}
