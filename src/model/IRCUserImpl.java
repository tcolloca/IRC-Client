package model;

public class IRCUserImpl implements IRCUser {

	private String nickname;
	private String username;
	private String host;

	/**
	 * @param nickname
	 * @param username
	 * @param host
	 * @throws IllegalArgumentException
	 *             If nickname is null.
	 */
	public IRCUserImpl(String nickname, String username, String host) {
		if (nickname == null) {
			throw new IllegalArgumentException();
		}
		this.nickname = nickname;
		this.username = username;
		this.host = host;
	}

	@Override
	public String getNickname() {
		return nickname;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getHost() {
		return host;
	}
}
