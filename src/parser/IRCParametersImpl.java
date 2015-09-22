package parser;

import java.util.ArrayList;
import java.util.List;

import util.IRCValues;

public class IRCParametersImpl implements IRCParameters, IRCValues {

	public String trailing;
	public String middle;
	public IRCParametersImpl parameters;

	IRCParametersImpl() {
	}

	IRCParametersImpl(List<String> parametersList) {
		if (parametersList.isEmpty()) {
			return;
		}
		if (parametersList.size() == 1) {
			this.trailing = parametersList.get(0);
		} else {
			this.middle = parametersList.get(0);
		}
		this.parameters = new IRCParametersImpl(parametersList.subList(1,
				parametersList.size()));
	}

	@Override
	public String getTrailing() {
		return trailing;
	}

	@Override
	public String getMiddle() {
		return middle;
	}

	@Override
	public IRCParameters getParameters() {
		return parameters;
	}

	@Override
	public String getParameter(int index) {
		if (index == 0) {
			return middle != null ? middle : trailing;
		}
		return parameters != null ? parameters.getParameter(index - 1) : null;
	}

	@Override
	public List<String> getParametersAsList() {
		List<String> list = new ArrayList<String>();
		if (middle != null) {
			list.add(middle);
		} else if (trailing != null) {
			list.add(trailing);
		}
		if (parameters != null) {
			list.addAll(parameters.getParametersAsList());
		}
		return list;
	}

	@Override
	public String getString() {
		String paramsString = "";
		if (middle != null) {
			paramsString = middle + parameters.getString();
		} else if (trailing != null) {
			paramsString = TRAILING_INDICATOR + trailing;
		}
		return EMPTY_SPACE + paramsString;
	}

	int amountOfParameters() {
		int aux = 0;
		if (middle != null || trailing != null) {
			aux++;
		}
		return parameters != null ? parameters.amountOfParameters() + aux : aux;
	}

	void setTrailing(String trailing) {
		this.trailing = trailing;
	}

	void setMiddle(String middle) {
		this.middle = middle;
	}

	void setParameters(IRCParametersImpl parameters) {
		this.parameters = parameters;
	}
}
