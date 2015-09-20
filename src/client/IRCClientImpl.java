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
import parser.IRCMessage;
import parser.IRCParser;
import parser.IRCParserImpl;
import reader.IRCReader;
import reader.IRCReaderImpl;
import util.IRCException;
import util.IRCFrameworkErrorException;
import writer.IRCWriter;
import writer.IRCWriterImpl;

import command.IRCCommand;
import command.IRCCommandFactory;
import command.IRCCommandFactoryImpl;
import command.InvalidCommandException;
import command.JoinCommand;
import command.NickCommand;
import command.PassCommand;
import command.PongCommand;
import command.UserCommand;

import event.IRCEventAdapter;
import event.IRCEventDispatcher;
import event.IRCEventDispatcherMultiThread;
import event.IRCEventDispatcherSingleThread;
import event.IRCEventListener;

/**
 * The main class of the IRC Connection framework.
 * 
 * @author Tomas
 */
public class IRCClientImpl extends IRCEventAdapter implements IRCClient {

	private static final int PORT = 6667;

	private IRCConfiguration config;
	private IRCReader reader;
	private IRCWriter writer;
	private IRCParser parser;
	private IRCCommandFactory commandFactory;
	private IRCEventDispatcher eventDispatcher;
	private IRCDao dao;
	private SocketChannel channel;

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
		reader = new IRCReaderImpl(this);
		writer = new IRCWriterImpl();
		parser = new IRCParserImpl();
		initializeEventDispatcher();
		dao = new IRCDaoImpl(parser);
		commandFactory = new IRCCommandFactoryImpl();
		try {
			channel = SocketChannel.open();
			channel.configureBlocking(false);
		} catch (IOException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	private void initializeEventDispatcher() {
		if (config.isMultiThread()) {
			this.eventDispatcher = new IRCEventDispatcherMultiThread();
		} else {
			this.eventDispatcher = new IRCEventDispatcherSingleThread();
		}
		eventDispatcher.addListener(this);
		for (IRCEventListener listener : config.getListeners()) {
			eventDispatcher.addListener(listener);
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
			IRCCommand command = commandFactory.build(dao, ircMessage);
			if (command != null) {
				eventDispatcher.onExecute(command);
			}
		} catch (InvalidCommandException e) {
			// TODO
			// throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void onPing(String server) {
		try {
			sendCommand(new PongCommand(server));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
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
				if (i % 1000 == 0) {
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

	private void sendCommand(IRCCommand command) {
		try {
			writer.write(channel, command.getMessage());
		} catch (IRCException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	public static void main(String[] args) throws IOException, IRCException,
			InterruptedException {
		IRCClient client = new IRCClientImpl((new IRCConfiguration(
				"irc.mibbit.net")).setInitialChannels(Arrays.asList("#guiamt")));
		System.out.println("run");
		client.run();
	}
}
