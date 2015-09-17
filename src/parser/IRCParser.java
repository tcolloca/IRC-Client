package parser;

import util.IRCValues;

public class IRCParser implements IRCValues {

	/**
	 * Parses the given message into the correct command of IRC that was
	 * received. The message should not contain any IRC message divider (\r\n).
	 * 
	 * @param message
	 * @return The {@link IRCMessage} associated with the message received.
	 * @throws IllegalArgumentException
	 *             If message is null.
	 */
	public IRCMessage parse(String message) {
		if (message == null) {
			throw new IllegalArgumentException();
		}
		char[] charArr = message.toCharArray();
		IRCMessage ircMessage = new IRCMessage();
		int start = 0;
		start = parsePrefix(ircMessage, charArr, start);
		start = parseCommand(ircMessage, charArr, start);
		if (charArr.length > start && charArr[start] == EMPTY_SPACE) {
			ircMessage.setParams(new IRCParameters());
			parseParams(ircMessage.getParams(), charArr, start);
		}
		return ircMessage;
	}

	private int parsePrefix(IRCMessage ircMessage, char[] charArr, int start) {
		start = trim(charArr, start);
		if (start >= charArr.length) {
			return charArr.length;
		}
		if (charArr.length > start && charArr[start] != PREFIX_INDICATOR) {
			return start;
		}
		start++;
		IRCPrefix ircPrefix = new IRCPrefix();
		start = parsePrefixName(ircPrefix, charArr, start);
		if (charArr.length > start && charArr[start] == USER_INDICATOR) {
			start++;
			start = parsePrefixUser(ircPrefix, charArr, start);
		}
		if (charArr.length > start && charArr[start] == HOST_INDICATOR) {
			start++;
			start = parsePrefixHost(ircPrefix, charArr, start);
		}
		ircMessage.setPrefix(ircPrefix);
		return start;
	}

	private int parsePrefixName(IRCPrefix ircPrefix, char[] charArr, int start) {
		int i;
		StringBuilder nameBuilder = new StringBuilder();
		for (i = start; i < charArr.length && charArr[i] != EMPTY_SPACE
				&& charArr[i] != USER_INDICATOR; i++) {
			nameBuilder.append(charArr[i]);
		}
		ircPrefix.setName(nameBuilder.toString());
		return i;
	}

	private int parsePrefixUser(IRCPrefix ircPrefix, char[] charArr, int start) {
		int i;
		StringBuilder userBuilder = new StringBuilder();
		for (i = start; i < charArr.length && charArr[i] != EMPTY_SPACE
				&& charArr[i] != HOST_INDICATOR; i++) {
			userBuilder.append(charArr[i]);
		}
		ircPrefix.setUser(userBuilder.toString());
		return i;
	}

	private int parsePrefixHost(IRCPrefix ircPrefix, char[] charArr, int start) {
		int i;
		StringBuilder hostBuilder = new StringBuilder();
		for (i = start; i < charArr.length && charArr[i] != EMPTY_SPACE; i++) {
			hostBuilder.append(charArr[i]);
		}
		ircPrefix.setHost(hostBuilder.toString());
		return i;
	}

	private int parseCommand(IRCMessage ircMessage, char[] charArr, int start) {
		start = trim(charArr, start);
		if (start >= charArr.length) {
			return charArr.length;
		}
		int i;
		StringBuilder commandBuilder = new StringBuilder();
		for (i = start; i < charArr.length && charArr[i] != EMPTY_SPACE; i++) {
			commandBuilder.append(charArr[i]);
		}
		ircMessage.setCommand(commandBuilder.toString());
		return i;
	}

	private int parseParams(IRCParameters params, char[] charArr, int start) {
		if (charArr.length > start && charArr[start] != EMPTY_SPACE) {
			return start;
		}
		start = trim(charArr, start);
		if (charArr.length <= start) {
			return charArr.length;
		}
		if (charArr.length > start && charArr[start] == TRAILING_INDICATOR) {
			start++;
			start = parseTrailing(params, charArr, start);
			return start;
		}
		start = parseMiddle(params, charArr, start);
		params.setParams(new IRCParameters());
		return parseParams(params.getParams(), charArr, start);
	}

	private int parseMiddle(IRCParameters params, char[] charArr, int start) {
		int i;
		StringBuilder middleBuilder = new StringBuilder();
		for (i = start; i < charArr.length && charArr[i] != EMPTY_SPACE; i++) {
			middleBuilder.append(charArr[i]);
		}
		params.setMiddle(middleBuilder.toString());
		return i;
	}

	private int parseTrailing(IRCParameters params, char[] charArr, int start) {
		int i;
		StringBuilder trailingBuilder = new StringBuilder();
		for (i = start; i < charArr.length; i++) {
			trailingBuilder.append(charArr[i]);
		}
		params.setTrailing(trailingBuilder.toString());
		return i;
	}

	private int trim(char[] charArr, int start) {
		while (start < charArr.length && charArr[start] == EMPTY_SPACE) {
			start++;
		}
		return start;
	}

	// // System.out.println("----" + message);
	// if (!message.isEmpty()) {
	// // System.out.println("RECEIVING " + message + "+++");
	//
	// if (message.contains(NOTICE_AUTH_COMMAND)) {
	// broadcaster.onConnect();
	// } else if (message.startsWith(PING_COMMAND)) {
	// System.out.println("ping");
	// parsePing(message);
	// } else if (message.contains(MODE_COMMAND)) {
	// broadcaster.onLogin();
	// } else if (message.contains(OPER_COMMAND)) {
	//
	// }
	// }

	// // TODO : Remove
	// private void parsePing(String message) throws IRCException {
	// String[] servers = message.split(" ");
	// if (servers.length == 2) {
	// broadcaster.onPing(servers[1]);
	// } else if (servers.length == 3) {
	// broadcaster.onPing(servers[2]);
	// } else {
	// // TODO
	// throw new IRCException();
	// }
	// }
}
