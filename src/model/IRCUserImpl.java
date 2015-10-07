package model;

import util.IRCValues;
import client.IRCClient;
import event.IRCRawEventAdapter;

public class IRCUserImpl extends IRCRawEventAdapter implements IRCUser,
		IRCValues {

	private String nickname;
	private String username;
	private String hostname;
	private String realname;

	private int hops;
	private IRCServer server;
	private int idleTime;
	private String awayMessage;

	private IRCUserChannels channels;
	private IRCUserFlags flags;

	private IRCClient client;

	/**
	 * @param client
	 *            IRCClient that has a reference to this channel.
	 * @param nickname
	 * @param username
	 * @param hostname
	 * @throws IllegalArgumentException
	 *             If client or nickname are null.
	 */
	public IRCUserImpl(IRCClient client, String nickname, String username,
			String hostname) {
		if (client == null || nickname == null) {
			throw new IllegalArgumentException();
		}
		this.nickname = nickname;
		this.username = username;
		this.hostname = hostname;
		hops = -1;
		idleTime = -1;
		channels = new IRCUserChannelsImpl();
		flags = new IRCUserFlagsImpl();
		client.addRawListener(this);
		this.client = client;
	}

	@Override
	public void send(String message) {
		if (message == null) {
			throw new IllegalArgumentException();
		}
		client.sendPrivateMessage(this, message);
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
	public String getHostname() {
		return hostname;
	}

	public String toString() {
		return "[nickname: " + nickname + ", " + "username: " + username + ", "
				+ "hostname: " + hostname + ", " + "realname: " + realname
				+ "]";
	}

	@Override
	public String getFullname() {
		if (username == null || hostname == null) {
			return null;
		}
		return nickname + USERNAME_INDICATOR + username + HOST_INDICATOR
				+ hostname;
	}

	@Override
	public String getRealname() {
		return realname;
	}

	@Override
	public int getHops() {
		return hops;
	}

	@Override
	public IRCServer getServer() {
		return server;
	}

	@Override
	public int getIdleTime() {
		return idleTime;
	}

	@Override
	public String getAwayMessage() {
		if (!flags.isAway()) {
			throw new IllegalStateException();
		}
		return awayMessage;
	}

	@Override
	public IRCUserChannels getUserChannels() {
		return channels;
	}

	@Override
	public IRCUserFlags getUserFlags() {
		return flags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IRCUserImpl other = (IRCUserImpl) obj;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		return true;
	}

	@Override
	public void onNick(String prevNickname, String newNickname) {
		if (prevNickname == null || newNickname == null) {
			throw new IllegalArgumentException();
		}
		if (prevNickname.equals(getNickname())) {
			this.nickname = newNickname;
		}
	}
}
