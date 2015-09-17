package parser;

import util.IRCValues;

/**
 * Represents a message's prefix of IRC according to the BNF representation specified in the RFC 1459.
 * 
 * @author Tomas
 */
public class IRCPrefix implements IRCValues {

	private String name;
	private String user;
	private String host;

	IRCPrefix() {
	}

	public String getName() {
		return name;
	}

	public String getUser() {
		return user;
	}

	public String getHost() {
		return host;
	}

	void setName(String name) {
		this.name = name;
	}

	void setUser(String user) {
		this.user = user;
	}

	void setHost(String host) {
		this.host = host;
	}

	@Override 
	public String toString() {
		String userString = "";
		if (user != null) {
			userString += USER_INDICATOR + user;
		}
		String hostString = "";
		if (host != null) {
			hostString += HOST_INDICATOR + host;
		}
		return name + userString + hostString;
	}
}
