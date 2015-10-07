package model;

import java.util.HashMap;
import java.util.Map;

import parser.IRCParser;
import client.IRCClient;

public class IRCDaoImpl implements IRCDao {

	private Map<String, IRCUser> users;
	private Map<String, IRCChannel> channels;
	private IRCClient client;
	private IRCParser parser;

	/**
	 * @param client
	 *            Client that holds the Dao.
	 * @param parser
	 * @throws IllegalArgumentException
	 *             If client or parser are null.
	 */
	public IRCDaoImpl(IRCClient client, IRCParser parser) {
		if (client == null || parser == null) {
			throw new IllegalArgumentException();
		}
		this.client = client;
		this.parser = parser;
		users = new HashMap<String, IRCUser>();
		channels = new HashMap<String, IRCChannel>();
	}

	@Override
	public IRCUser getUser(String userName) {
		if (userName == null) {
			throw new IllegalArgumentException();
		}
		String nickname = parser.parseNickname(userName);
		return users.get(nickname);
	}

	@Override
	public IRCUser getOrAddUser(String userName) {
		IRCUser user = getUser(userName);
		if (user == null) {
			user = addUser(userName);
		}
		return user;
	}

	@Override
	public boolean hasUser(String userName) {
		return getUser(userName) != null;
	}

	@Override
	public IRCUser addUser(String userName) {
		if (userName == null || hasUser(userName)) {
			throw new IllegalArgumentException();
		}
		String nickname = parser.parseNickname(userName);
		String username = parser.parseUsername(userName);
		String hostname = parser.parseHostname(userName);
		IRCUser user = new IRCUserImpl(client, nickname, username, hostname);
		users.put(nickname, user);
		return user;
	}

	@Override
	public IRCUser addUser(IRCUser user, String nickname) {
		if (user == null || users.containsValue(user) || nickname == null) {
			throw new IllegalArgumentException();
		}
		users.put(nickname, user);
		return user;
	}

	@Override
	public IRCUser removeUser(String userName) {
		return users.remove(userName);
	}

	@Override
	public IRCChannel getChannel(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		return channels.get(name);
	}

	@Override
	public boolean hasChannel(String name) {
		return getChannel(name) != null;
	}

	@Override
	public IRCChannel addChannel(String name) {
		return addChannel(name, null);
	}

	@Override
	public IRCChannel addChannel(String name, String password) {
		if (name == null || hasChannel(name)) {
			throw new IllegalArgumentException();
		}
		IRCChannel channel = new IRCChannelImpl(client, name, password);
		channels.put(name, channel);
		return channel;
	}

	@Override
	public IRCChannel addChannel(IRCChannel channel) {
		if (channel == null || channels.containsValue(channel)) {
			throw new IllegalArgumentException();
		}
		channels.put(channel.getName(), channel);
		return channel;
	}

	@Override
	public IRCChannel removeChannel(String name) {
		return channels.remove(name);
	}
}
