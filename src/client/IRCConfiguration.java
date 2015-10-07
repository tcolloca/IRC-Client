package client;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import event.IRCEventListener;

public class IRCConfiguration {

	private static final String DEFAULT_NICKNAME = "IrcClient";
	private static final String DEFAULT_USERNAME = "IrcClient";
	private static final String DEFAULT_REALNAME = "IrcClient 1.0 developed by Tomas Colloca :)";
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	private String server;
	private boolean isMultiThread;
	private String nickname;
	private String password;
	private String username;
	private String realname;
	private List<String> channels;
	private List<String> passwords;
	private Charset charset;
	private List<IRCEventListener> listeners;

	/**
	 * @param server
	 *            Server to connect to.
	 * @throws IllegalArgumentException
	 *             If server is null.
	 */
	public IRCConfiguration(String server) {
		if (server == null) {
			throw new IllegalArgumentException();
		}
		this.server = server;
		this.nickname = DEFAULT_NICKNAME;
		this.password = null;
		this.username = DEFAULT_USERNAME;
		this.realname = DEFAULT_REALNAME;
		this.channels = new ArrayList<String>();
		this.passwords = new ArrayList<String>();
		this.charset = DEFAULT_CHARSET;
		this.listeners = new ArrayList<IRCEventListener>();
	}

	/**
	 * Sets the IRCConfiguration for a MultiThread IRCClient.
	 * 
	 * @param isMultiThread
	 * @return this
	 */
	public IRCConfiguration setMultiThread(boolean isMultiThread) {
		this.isMultiThread = isMultiThread;
		return this;
	}

	/**
	 * Sets the nickname of the IRCClient.
	 * 
	 * @param nickname
	 *            Nickname for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If nickname is null or empty.
	 */
	public IRCConfiguration setNickname(String nickname) {
		if (nickname == null || nickname.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.nickname = nickname;
		return this;
	}

	/**
	 * Sets the password for the nickname of the IRCClient.
	 * 
	 * @param password
	 *            password for the nickname for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If password is null or empty.
	 */
	public IRCConfiguration setPassword(String password) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.password = password;
		return this;
	}

	/**
	 * Sets the username of the IRCClient.
	 * 
	 * @param username
	 *            Username for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If username is null or empty.
	 */
	public IRCConfiguration setUsername(String username) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.username = username;
		return this;
	}

	/**
	 * Sets the realname of the IRCClient.
	 * 
	 * @param realname
	 *            Realname for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If realname is null or empty.
	 */
	public IRCConfiguration setRealname(String realname) {
		if (realname == null || realname.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.realname = realname;
		return this;
	}

	/**
	 * Sets the initial channels of the IRCClient.
	 * 
	 * @param channels
	 *            List with the name of the channels to join at start.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If channels or any channel is null.
	 */
	public IRCConfiguration setInitialChannels(List<String> channels) {
		return setInitialChannels(channels, null);
	}

	/**
	 * Sets the initial channels of the IRCClient.
	 * 
	 * @param channels
	 *            List with the name of the channels to join at start.
	 * @param passwords
	 *            List with the passwords of the channels to join at start.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If channels or any channels is null, or if channels size is
	 *             different to passwords size.
	 */
	public IRCConfiguration setInitialChannels(List<String> channels,
			List<String> passwords) {
		if (channels == null) {
			throw new IllegalArgumentException();
		}
		for (String channelName : channels) {
			if (channelName == null) {
				throw new IllegalArgumentException();
			}
		}
		if (passwords != null && channels.size() != passwords.size()) {
			throw new IllegalArgumentException();
		}
		this.channels = channels;
		this.passwords = passwords;
		return this;
	}

	/**
	 * Sets the charset being used by the IRCClient. The default is UTF-8.
	 * 
	 * @param charset
	 *            Charset for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If charset is null.
	 */
	public IRCConfiguration setCharset(Charset charset) {
		if (charset == null) {
			throw new IllegalArgumentException();
		}
		this.charset = charset;
		return this;
	}

	/**
	 * Adds a listener for the IRCEvents dispatched by the IRCClient.
	 * 
	 * @param listener
	 *            Listener to be added.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public IRCConfiguration addListener(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.add(listener);
		return this;
	}

	String getServer() {
		return server;
	}

	boolean isMultiThread() {
		return isMultiThread;
	}

	String getNickname() {
		return nickname;
	}

	String getPassword() {
		return password;
	}

	String getUsername() {
		return username;
	}

	String getRealname() {
		return realname;
	}

	List<String> getChannels() {
		return channels;
	}

	List<String> getPasswords() {
		return passwords;
	}

	Charset getCharset() {
		return charset;
	}

	List<IRCEventListener> getListeners() {
		return listeners;
	}
}
