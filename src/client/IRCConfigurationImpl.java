package client;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import event.IRCEventListener;

class IRCConfigurationImpl implements IRCConfiguration {

	private static final boolean DEFAULT_ISMULTITHREAD = false;
	private static final String DEFAULT_NICKNAME = "IrcClient";
	private static final String DEFAULT_USERNAME = "IrcClient";
	private static final String DEFAULT_REALNAME = "IrcClient 1.0 developed by Tomas Colloca :)";
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
	private static final IRCConnectionHandler DEFAULT_CONNECTION_HANDLER = new IRCConnectionHandlerWithNonBlockingNio();
	private static final IRCReader DEFAULT_READER = new IRCReaderImpl();
	private static final IRCWriter DEFAULT_WRITER = new IRCWriterImpl();

	private String server;
	private boolean isMultiThread;
	private String nickname;
	private String password;
	private String username;
	private String realname;
	private List<String> channels;
	private List<String> passwords;
	private Charset charset;
	private IRCConnectionHandler handler;
	private IRCReader reader;
	private IRCWriter writer;
	private List<IRCEventListener> listeners;

	IRCConfigurationImpl(String server) {
		if (server == null) {
			throw new IllegalArgumentException();
		}
		this.server = server;
		this.isMultiThread = DEFAULT_ISMULTITHREAD;
		this.nickname = DEFAULT_NICKNAME;
		this.password = null;
		this.username = DEFAULT_USERNAME;
		this.realname = DEFAULT_REALNAME;
		this.channels = new ArrayList<String>();
		this.passwords = new ArrayList<String>();
		this.charset = DEFAULT_CHARSET;
		this.handler = DEFAULT_CONNECTION_HANDLER;
		this.reader = DEFAULT_READER;
		this.writer = DEFAULT_WRITER;
		this.listeners = new ArrayList<IRCEventListener>();
	}

	@Override
	public IRCConfiguration setServer(String server) {
		this.server = server;
		return this;
	}

	@Override
	public IRCConfiguration setMultiThread(boolean isMultiThread) {
		this.isMultiThread = isMultiThread;
		return this;
	}

	@Override
	public IRCConfiguration setNickname(String nickname) {
		if (nickname == null || nickname.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.nickname = nickname;
		return this;
	}

	@Override
	public IRCConfiguration setPassword(String password) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.password = password;
		return this;
	}

	@Override
	public IRCConfiguration setUsername(String username) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.username = username;
		return this;
	}

	@Override
	public IRCConfiguration setRealname(String realname) {
		if (realname == null || realname.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.realname = realname;
		return this;
	}

	@Override
	public IRCConfiguration setInitialChannels(List<String> channels) {
		return setInitialChannels(channels, null);
	}

	@Override
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

	@Override
	public IRCConfiguration setCharset(Charset charset) {
		if (charset == null) {
			throw new IllegalArgumentException();
		}
		this.charset = charset;
		return this;
	}

	@Override
	public IRCConfiguration setConnectionHandler(IRCConnectionHandler handler) {
		if (handler == null) {
			throw new IllegalArgumentException();
		}
		this.handler = handler;
		return this;
	}

	@Override
	public IRCConfiguration setReader(IRCReader reader) {
		if (reader == null) {
			throw new IllegalArgumentException();
		}
		this.reader = reader;
		return this;
	}

	@Override
	public IRCConfiguration setWriter(IRCWriter writer) {
		if (writer == null) {
			throw new IllegalArgumentException();
		}
		this.writer = writer;
		return this;
	}

	@Override
	public IRCConfiguration addListener(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.add(listener);
		return this;
	}

	@Override
	public IRCConfiguration removeListener(IRCEventListener listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServer() {
		return server;
	}

	@Override
	public boolean isMultiThread() {
		return isMultiThread;
	}

	@Override
	public String getNickname() {
		return nickname;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getRealname() {
		return realname;
	}

	@Override
	public List<String> getChannelNames() {
		return channels;
	}

	@Override
	public List<String> getChannelPasswords() {
		return passwords;
	}

	@Override
	public Charset getCharset() {
		return charset;
	}

	@Override
	public IRCConnectionHandler getConnectionHandler() {
		return handler;
	}

	@Override
	public IRCReader getReader() {
		return reader;
	}

	@Override
	public IRCWriter getWriter() {
		return writer;
	}

	@Override
	public List<IRCEventListener> getListeners() {
		return listeners;
	}
}
