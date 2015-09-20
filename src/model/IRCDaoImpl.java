package model;

import java.util.HashMap;
import java.util.Map;

import parser.IRCParser;

public class IRCDaoImpl implements IRCDao {

	private Map<String, IRCUser> users;
	private Map<String, IRCChannel> channels;
	private IRCParser parser;

	public IRCDaoImpl(IRCParser parser) {
		if (parser == null) {
			throw new IllegalArgumentException();
		}
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
		String host = parser.parseHost(username);
		IRCUser user = new IRCUserImpl(nickname, username, host);
		users.put(nickname, user);
		return user;
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
		IRCChannel channel = new IRCChannelImpl(name, password);
		channels.put(name, channel);
		return channel;
	}
}
