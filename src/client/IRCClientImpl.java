package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.IRCChannel;
import model.IRCDao;
import model.IRCDaoImpl;
import model.IRCUser;
import parser.IRCMessage;
import parser.IRCParser;
import parser.IRCParserImpl;
import reader.IRCReader;
import reader.IRCReaderImpl;
import util.IRCConnectionReplyValues;
import util.IRCException;
import util.IRCFrameworkErrorException;
import util.IRCValues;
import writer.IRCWriter;
import writer.IRCWriterImpl;

import command.IRCCommand;
import command.InvalidCommandException;
import command.JoinCommand;
import command.NickCommand;
import command.PassCommand;
import command.PongCommand;
import command.PrivmsgCommand;
import command.UserCommand;
import command.reply.IRCCommandFactoryImpl;
import command.reply.IRCReplyFactory;

import event.IRCEventAdapter;
import event.IRCEventDispatcher;
import event.IRCEventDispatcherMultiThread;
import event.IRCEventDispatcherSingleThread;
import event.IRCEventListener;
import event.IRCRawEventAdapter;
import event.IRCRawEventDispatcher;
import event.IRCRawEventListener;

/**
 * The main class of the IRC Connection framework.
 * 
 * @author Tomas
 */
public class IRCClientImpl extends IRCRawEventAdapter implements IRCClient,
		IRCValues, IRCConnectionReplyValues {

	private static final int PORT = 6667;

	private IRCConfiguration config;
	private IRCReader reader;
	private IRCWriter writer;
	private IRCParser parser;
	private IRCReplyFactory commandFactory;
	private IRCRawEventDispatcher rawEventDispatcher;
	private IRCEventDispatcher eventDispatcher;
	private IRCDao dao;
	private SocketChannel channel;
	@SuppressWarnings("unused")
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
		reader = new IRCReaderImpl(this, config.getCharset());
		writer = new IRCWriterImpl(config.getCharset());
		parser = new IRCParserImpl();
		dao = new IRCDaoImpl(this, parser);
		initializeDispatchers();
		commandFactory = new IRCCommandFactoryImpl();
		try {
			channel = SocketChannel.open();
			channel.configureBlocking(false);
		} catch (IOException e) {
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
	public void run() throws IRCException {
		connect(config.getServer(), PORT);
		reader.read(channel);
	}

	@Override
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

	@Override
	public void sendCommand(IRCCommand command) {
		try {
			writer.write(channel, command.getMessage());
		} catch (IRCException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void addRawListener(IRCRawEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		rawEventDispatcher.addListener(listener);
	}

	@Override
	public void addListener(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		eventDispatcher.addListener(listener);
	}

	@Override
	public IRCUser getUser(String userName) {
		if (userName == null) {
			throw new IllegalArgumentException();
		}
		return dao.getUser(userName);
	}

	@Override
	public IRCUser getOrAddUser(String userName) {
		return dao.getOrAddUser(userName);
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
	public void onNick(String prevNickname, String newNickname) {
		IRCUser user = dao.removeUser(prevNickname);
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

	private void onLogin() {
		try {
			List<IRCChannel> channels = getInitialChannels();
			if (!channels.isEmpty()) {
				sendCommand(new JoinCommand(channels));
			}
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	private List<IRCChannel> getInitialChannels() {
		List<IRCChannel> channels = new ArrayList<IRCChannel>();
		for (int i = 0; i < config.getChannels().size(); i++) {
			String name = config.getChannels().get(i);
			String password = null;
			if (config.getPasswords() != null) {
				password = config.getPasswords().get(i);
			}
			channels.add(dao.addChannel(name, password));
		}
		return channels;
	}

	private void connect(String server, int port) throws IRCException {
		if (channel.isConnected()) {
			throw new IllegalStateException();
		}
		System.out.println("Connecting to server and port...");
		try {
			channel.connect(new InetSocketAddress(server, port));
		} catch (Exception e) {
			throw new IRCException();
		}
		System.out.println("Connected to server and port.");
		int i = 0;
		try {
			while (!channel.finishConnect()) {
				if (i++ % 10000 == 0) {
					System.out.println("Trying to connect...");
				}
			}
		} catch (Exception e) {
			throw new IRCException();
		}
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

	public static void main(String[] args) throws IOException, IRCException,
			InterruptedException {
		IRCClient client = new IRCClientImpl((new IRCConfiguration(
				"irc.mibbit.net")).setInitialChannels(Arrays.asList("#guiamt"),
				Arrays.asList("lalala")).setNickname("TomBot"));
		client.addListener(new IRCEventAdapter() {
			@Override
			public void onMessage(IRCUser sender, String message) {
				try {
					client.sendCommand(new PrivmsgCommand("#guiamt", sender
							+ " dijo: " + message));
				} catch (InvalidCommandException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		client.run();
	}
}
