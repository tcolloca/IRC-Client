package parser;

import java.util.ArrayList;
import java.util.List;

import model.IRCChannelMode;
import model.IRCModeAction;
import model.IRCDao;
import model.IRCMode;
import model.IRCUserMode;
import util.IRCValues;

import command.InvalidCommandException;

public class IRCModesParser implements IRCValues {

	public static List<IRCModeAction> parseUserModeActions(IRCDao dao,
			IRCParameters parameters) throws InvalidCommandException {
		return parseModeActions(dao, parameters, true);
	}

	public static List<IRCModeAction> parseChannelModeActions(
			IRCDao dao, IRCParameters parameters)
			throws InvalidCommandException {
		return parseModeActions(dao, parameters, false);
	}

	private static List<IRCModeAction> parseModeActions(IRCDao dao,
			IRCParameters parameters, boolean isUserMode) throws InvalidCommandException {
		int k = 0;
		List<IRCModeAction> actions = new ArrayList<IRCModeAction>();
		char[] charArr = parameters.getParameter(k++).toCharArray();
		int i = 0;
		if (i >= charArr.length || !isValidAction(charArr[i])) {
			throw new InvalidCommandException();
		}
		char currAction = charArr[i++];
		while (i < charArr.length) {
			if (isValidAction(charArr[i])) {
				currAction = charArr[i];
			} else if (isValidMode(charArr[i], isUserMode)) {
				IRCMode mode;
				if (isUserMode) {
					mode = IRCUserMode.getMode(charArr[i]);
				} else {
					mode = IRCChannelMode.getMode(charArr[i]);
				}
				String[] params = parseParams(parameters, dao, mode, k);
				k += params.length;
				actions.add(new IRCModeAction(mode, currAction, params));
			}
			i++;
		}
		return actions;
	}

	private static String[] parseParams(IRCParameters parameters, IRCDao dao,
			IRCMode mode, int k) throws InvalidCommandException {
		String[] params = new String[mode.getParamsAmount()];
		for (int j = 0; j < mode.getParamsAmount(); j++) {
			String param = parameters.getParameter(k + j);
			if (param == null) {
				throw new InvalidCommandException();
			}
			params[j] = param;
		}
		return params;
	}

	private static boolean isValidMode(char c, boolean isUserMode) {
		if (isUserMode) {
			return IRCUserMode.getMode(c) != null;
		} else {
			return IRCChannelMode.getMode(c) != null;
		}

	}

	private static boolean isValidAction(char c) {
		return c == DEL || c == ADD;
	}
}
