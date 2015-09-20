package parser;

import util.IRCValues;

public class IRCPrefixImpl implements IRCPrefix, IRCValues {

	private String nickname;
	private String user;
	private String host;

	IRCPrefixImpl() {
	}

	@Override
	public String getNickame() {
		return nickname;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getHost() {
		return host;
	}
	
	@Override
	public String getString() {
		String userString = "";
		if (user != null) {
			userString += USER_INDICATOR + user;
		}
		String hostString = "";
		if (host != null) {
			hostString += HOST_INDICATOR + host;
		}
		return nickname + userString + hostString;
	}

	void setNickname(String nickname) {
		this.nickname = nickname;
	}

	void setUser(String user) {
		this.user = user;
	}

	void setHost(String host) {
		this.host = host;
	}
}
