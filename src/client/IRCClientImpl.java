package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import model.IRCChannel;
import model.IRCDao;
import model.IRCDaoImpl;
import model.IRCUser;
import parser.IRCMessage;
import parser.IRCParser;
import parser.IRCParserImpl;
import util.IRCConnectionException;
import util.IRCConnectionReplyValues;
import util.IRCFrameworkErrorException;
import util.IRCValues;

import command.IRCCommand;
import command.InvalidCommandException;
import command.JoinCommand;
import command.NickCommand;
import command.PartCommand;
import command.PassCommand;
import command.PongCommand;
import command.PrivmsgCommand;
import command.QuitCommand;
import command.UserCommand;
import command.reply.IRCCommandFactoryImpl;
import command.reply.IRCReplyFactory;

import event.IRCEventDispatcher;
import event.IRCEventDispatcherMultiThread;
import event.IRCEventDispatcherSingleThread;
import event.IRCEventListener;
import event.IRCRawEventAdapter;
import event.IRCRawEventDispatcher;
import event.IRCRawEventListener;

// TODO : Remove public modifier from class and constructor
public class IRCClientImpl extends IRCRawEventAdapter implements IRCClient,
		IRCValues, IRCConnectionReplyValues {

	private static final int PORT = 6667;

	private IRCConfiguration config;
	private IRCConnectionHandler connectionHandler;
	private IRCReader reader;
	private IRCWriter writer;
	private IRCParser parser;
	private IRCReplyFactory commandFactory;
	private IRCRawEventDispatcher rawEventDispatcher;
	private IRCEventDispatcher eventDispatcher;
	private IRCDao dao;
	private IRCConnectionChannel channel;
	private IRCClientUser clientUser;

	/**
	 * @param config
	 *            IRCConfiguration to be used.
	 * @throws IllegalArgumentException
	 *             If config is null.
	 */
	public IRCClientImpl(IRCConfiguration config) {
		if (config == null) {
			throw new IllegalArgumentException();
		}
		this.config = config;
		this.connectionHandler = config.getConnectionHandler();
		initializeReaderAndWriter();
		parser = new IRCParserImpl();
		dao = new IRCDaoImpl(this, parser);
		initializeDispatchers();
		commandFactory = new IRCCommandFactoryImpl();
	}

	@Override
	public void run() throws IRCConnectionException {
		connect(config.getServer(), PORT);
		reader.read(channel);
	}

	@Override
	public void stop() {
		sendCommand(new QuitCommand());
	}

	@Override
	public void stop(String message) {
		if (message == null) {
			throw new IllegalArgumentException();
		}
		sendCommand(new QuitCommand(message));
	}

	@Override
	public void join(String channelName) {
		join(channelName, null);
	}

	@Override
	public void join(String channelName, String password) {
		join(Arrays.asList(channelName), Arrays.asList(password));
	}

	@Override
	public void join(List<String> channelNames) {
		join(channelNames, null);
	}

	@Override
	public void join(List<String> channelNames, List<String> passwords) {
		if (channelNames == null) {
			throw new IllegalArgumentException();
		}
		try {
			sendCommand(new JoinCommand(getIRCChannels(channelNames, passwords)));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void leave(IRCChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		try {
			sendCommand(new PartCommand(channel));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void setNickname(String newNick) {
		if (newNick == null) {
			throw new IllegalArgumentException();
		}
		try {
			sendCommand(new NickCommand(newNick));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void sendChannelMessage(IRCChannel channel, String message) {
		if (channel == null || message == null) {
			throw new IllegalArgumentException();
		}
		try {
			sendCommand(new PrivmsgCommand(channel.getName(), message));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void sendPrivateMessage(IRCUser user, String message) {
		if (user == null || message == null) {
			throw new IllegalArgumentException();
		}
		try {
			sendCommand(new PrivmsgCommand(user.getNickname(), message));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void sendCommand(IRCCommand command) {
		try {
			writer.write(channel, command.getMessage());
		} catch (IRCConnectionException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void sendRawLine(String message) {
		try {
			writer.write(channel, message);
		} catch (IRCConnectionException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public IRCUser getClientUser() {
		return clientUser;
	}

	@Override
	public IRCUser getUser(String userName) {
		if (userName == null) {
			throw new IllegalArgumentException();
		}
		return dao.getUser(userName);
	}

	@Override
	public IRCChannel getChannel(String channelName) {
		if (channelName == null) {
			throw new IllegalArgumentException();
		}
		return dao.getChannel(channelName);
	}

	@Override
	public Set<IRCUser> getAllUsers() {
		return dao.getAllUsers();
	}

	@Override
	public Set<IRCChannel> getAllChannels() {
		return dao.getAllChannels();
	}

	@Override
	public String getNick() {
		return clientUser.getNickname();
	}

	@Override
	public IRCConfiguration getConfiguration() {
		return config;
	}

	@Override
	public void addListener(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		eventDispatcher.addListener(listener);
	}

	@Override
	public void onPing(String server) {
		if (server == null) {
			throw new IllegalArgumentException();
		}
		try {
			sendCommand(new PongCommand(server));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void onJoin(String nickname, String channelName) {
		if (nickname == null || channelName == null) {
			throw new IllegalArgumentException();
		}
		getOrAddUser(nickname);
		IRCChannel channel = dao.getChannel(channelName);
		if (channel == null) {
			channel = dao.addChannel(channelName);
		}
	}

	@Override
	public void onNick(String prevFullUsername, String newNickname) {
		IRCUser user = dao.removeUser(prevFullUsername);
		dao.addUser(user, newNickname);
	}

	@Override
	public void onConnectionReply(int replyNumber, List<String> parameters) {
		switch (replyNumber) {
		case RPL_WELCOME:
			String[] splitWelcomeMsg = parameters.get(0).split(" ");
			dao.addUser(splitWelcomeMsg[splitWelcomeMsg.length - 1]);
			onLogin();
			break;
		}
	}

	// TODO : Maybe do sth.
	public IRCUser getOrAddUser(String userName) {
		return dao.getOrAddUser(userName);
	}

	// TODO : Maybe do sth.
	public void addRawListener(IRCRawEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		rawEventDispatcher.addListener(listener);
	}

	private void onLogin() {
		try {
			List<IRCChannel> channels = getIRCChannels(
					config.getChannelNames(), config.getChannelPasswords());
			if (!channels.isEmpty()) {
				sendCommand(new JoinCommand(channels));
			}
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	private List<IRCChannel> getIRCChannels(List<String> channelNames,
			List<String> passwords) {
		List<IRCChannel> channels = new ArrayList<IRCChannel>();
		for (int i = 0; i < channelNames.size(); i++) {
			String name = channelNames.get(i);
			if (name == null) {
				throw new IllegalArgumentException();
			}
			String password = null;
			if (passwords != null) {
				password = passwords.get(i);
			}
			channels.add(dao.addChannel(name, password));
		}
		return channels;
	}

	private void connect(String server, int port) throws IRCConnectionException {
		channel = connectionHandler.openConnection(server, port);
		onConnect();
	}

	private void onConnect() {
		try {
			if (config.getPassword() != null) {
				sendCommand(new PassCommand(config.getPassword()));
			}
			sendCommand(new NickCommand(config.getNickname()));
			sendCommand(new UserCommand(config.getUsername(),
					config.getRealname()));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	private void initializeReaderAndWriter() {
		reader = config.getReader();
		reader.setCharset(config.getCharset());
		// TODO : Add proper listener.
		reader.addListener(null);
		writer = config.getWriter();
		writer.setCharset(config.getCharset());
	}

	private void initializeDispatchers() {
		if (config.isMultiThread()) {
			this.eventDispatcher = new IRCEventDispatcherMultiThread();
		} else {
			this.eventDispatcher = new IRCEventDispatcherSingleThread();
		}
		for (IRCEventListener listener : config.getListeners()) {
			eventDispatcher.addListener(listener);
		}

		this.rawEventDispatcher = new IRCRawEventDispatcher(eventDispatcher,
				dao);
		rawEventDispatcher.addListener(this);
	}

	// TODO : Change of place?
	public synchronized void feed(String message) {
		IRCMessage ircMessage = parser.parse(message);
		try {
			IRCCommand command = commandFactory.build(ircMessage);
			if (command != null) {
				command.onExecute(rawEventDispatcher);
			}
			// TODO : Change exception to catch to InvalidCommandException
		} catch (Exception e) {
			// TODO : throw new IRCFrameworkErrorException();
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException,
			IRCConnectionException, InterruptedException {
		IRCClient client = new IRCClientImpl((new IRCConfigurationImpl(
				"irc.mibbit.net")).setInitialChannels(Arrays.asList("#guiamt"))
				.setNickname("tomBot"));
		client.run();
	}
}
