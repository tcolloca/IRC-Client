package parser;

import util.IRCValues;

public class IRCParserImpl implements IRCParser, IRCValues {

	@Override
	public IRCMessage parse(String message) {
		if (message == null || !message.endsWith(MSG_END_SEQ)) {
			throw new IllegalArgumentException();
		}
		message = message.substring(0, message.length() - MSG_END_SEQ.length());
		char[] charArr = message.toCharArray();
		IRCMessageImpl ircMessage = new IRCMessageImpl();
		int start = 0;
		start = parsePrefix(ircMessage, charArr, start);
		start = parseCommand(ircMessage, charArr, start);
		if (charArr.length > start && charArr[start] == EMPTY_SPACE) {
			ircMessage.setParams(new IRCParametersImpl());
			parseParams((IRCParametersImpl) ircMessage.getParameters(),
					charArr, start);
		}
		return ircMessage;
	}

	@Override
	public String parseNickname(String userName) {
		if (userName == null) {
			throw new IllegalArgumentException();
		}
		char[] charArr = userName.toCharArray();
		StringBuilder nicknameBuilder = new StringBuilder();
		for (int i = 0; i < charArr.length && charArr[i] != EMPTY_SPACE
				&& charArr[i] != USERNAME_INDICATOR; i++) {
			nicknameBuilder.append(charArr[i]);
		}
		return nicknameBuilder.toString();
	}

	@Override
	public String parseUsername(String userName) {
		if (userName == null) {
			throw new IllegalArgumentException();
		}
		char[] charArr = userName.toCharArray();
		StringBuilder usernameBuilder = new StringBuilder();
		boolean flag = false;
		for (int i = 0; i < charArr.length && charArr[i] != EMPTY_SPACE
				&& charArr[i] != HOST_INDICATOR; i++) {
			if (flag) {
				usernameBuilder.append(charArr[i]);
			}
			if (charArr[i] == USERNAME_INDICATOR) {
				flag = true;
			}
		}
		return usernameBuilder.toString();
	}

	@Override
	public String parseHostname(String userName) {
		if (userName == null) {
			throw new IllegalArgumentException();
		}
		char[] charArr = userName.toCharArray();
		StringBuilder hostBuilder = new StringBuilder();
		boolean flag = false;
		for (int i = 0; i < charArr.length && charArr[i] != EMPTY_SPACE; i++) {
			if (flag) {
				hostBuilder.append(charArr[i]);
			}
			if (charArr[i] == HOST_INDICATOR) {
				flag = true;
			}
		}
		return hostBuilder.toString();
	}

	private int parsePrefix(IRCMessageImpl ircMessage, char[] charArr, int start) {
		start = trim(charArr, start);
		if (start >= charArr.length) {
			return charArr.length;
		}
		if (charArr.length > start && charArr[start] != PREFIX_INDICATOR) {
			return start;
		}
		start++;
		IRCPrefixImpl ircPrefix = new IRCPrefixImpl();
		start = parsePrefixName(ircPrefix, charArr, start);
		if (charArr.length > start && charArr[start] == USERNAME_INDICATOR) {
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

	private int parsePrefixName(IRCPrefixImpl ircPrefix, char[] charArr,
			int start) {
		int i;
		StringBuilder nameBuilder = new StringBuilder();
		for (i = start; i < charArr.length && charArr[i] != EMPTY_SPACE
				&& charArr[i] != USERNAME_INDICATOR; i++) {
			nameBuilder.append(charArr[i]);
		}
		ircPrefix.setNickname(nameBuilder.toString());
		return i;
	}

	private int parsePrefixUser(IRCPrefixImpl ircPrefix, char[] charArr,
			int start) {
		int i;
		StringBuilder userBuilder = new StringBuilder();
		for (i = start; i < charArr.length && charArr[i] != EMPTY_SPACE
				&& charArr[i] != HOST_INDICATOR; i++) {
			userBuilder.append(charArr[i]);
		}
		ircPrefix.setUser(userBuilder.toString());
		return i;
	}

	private int parsePrefixHost(IRCPrefixImpl ircPrefix, char[] charArr,
			int start) {
		int i;
		StringBuilder hostBuilder = new StringBuilder();
		for (i = start; i < charArr.length && charArr[i] != EMPTY_SPACE; i++) {
			hostBuilder.append(charArr[i]);
		}
		ircPrefix.setHost(hostBuilder.toString());
		return i;
	}

	private int parseCommand(IRCMessageImpl ircMessage, char[] charArr,
			int start) {
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

	private int parseParams(IRCParametersImpl params, char[] charArr, int start) {
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
		params.setParameters(new IRCParametersImpl());
		return parseParams((IRCParametersImpl) params.getParameters(), charArr,
				start);
	}

	private int parseMiddle(IRCParametersImpl params, char[] charArr, int start) {
		int i;
		StringBuilder middleBuilder = new StringBuilder();
		for (i = start; i < charArr.length && charArr[i] != EMPTY_SPACE; i++) {
			middleBuilder.append(charArr[i]);
		}
		params.setMiddle(middleBuilder.toString());
		return i;
	}

	private int parseTrailing(IRCParametersImpl params, char[] charArr,
			int start) {
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
}
