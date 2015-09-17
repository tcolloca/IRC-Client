package parser;

import java.util.List;

import util.IRCValues;

/**
 * Represents a message's parameters of IRC according to the BNF representation
 * specified in the RFC 1459.
 * 
 * @author Tomas
 */
public class IRCParameters implements IRCValues {

	public String trailing;
	public String middle;
	public IRCParameters params;

	IRCParameters() {
	}

	IRCParameters(List<String> parameters) {
		if (parameters.isEmpty()) {
			return;
		}
		if (parameters.size() == 1) {
			this.trailing = parameters.get(0);
		} else {
			this.middle = parameters.get(0);
		}
		params = new IRCParameters(parameters.subList(1, parameters.size()));
	}

	public String getTrailing() {
		return trailing;
	}

	public String getMiddle() {
		return middle;
	}

	public IRCParameters getParams() {
		return params;
	}

	String getParameter(int index) {
		if (index == 0) {
			return middle != null ? middle : trailing;
		}
		return params != null ? params.getParameter(index - 1) : null;
	}

	int amountOfParameters() {
		int aux = 0;
		if (middle != null || trailing != null) {
			aux++;
		}
		return params != null ? params.amountOfParameters() + aux : aux;
	}

	void setTrailing(String trailing) {
		this.trailing = trailing;
	}

	void setMiddle(String middle) {
		this.middle = middle;
	}

	void setParams(IRCParameters params) {
		this.params = params;
	}

	@Override
	public String toString() {
		String paramsString = "";
		if (middle != null) {
			paramsString = middle + params.toString();
		} else if (trailing != null) {
			paramsString = TRAILING_INDICATOR + trailing;
		}
		return EMPTY_SPACE + paramsString;
	}
}
