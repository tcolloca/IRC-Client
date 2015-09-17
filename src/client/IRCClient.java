package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.nio.channels.UnsupportedAddressTypeException;

import command.IRCCommand;
import command.IRCCommandFactory;
import command.InvalidCommandException;
import command.JoinCommand;
import command.NamesCommand;
import command.NickCommand;
import command.PassCommand;
import command.PongCommand;
import command.UserCommand;
import event.IRCEventAdapter;
import event.IRCEventBroadcaster;
import parser.IRCMessage;
import parser.IRCParser;
import util.IRCFrameworkErrorException;

/**
 * The main class of the IRC Connection framework.
 * 
 * @author Tomas
 */
public class IRCClient extends IRCEventAdapter {

	private static final boolean SEND_PASS = false;
	private static final String PASSWORD = "tomas123";
	private static final String NICKNAME = "niiiiickname";
	private static final String USERNAME = "Tommmm";
	private static final String REALNAME = "Tommmm";
	private static final String CHANNEL = "#guiamt";

	private IRCReader reader;
	private IRCSender sender;
	private IRCParser parser;
	private IRCCommandFactory commandFactory;
	private IRCEventBroadcaster broadcaster;
	private SocketChannel channel;

	public IRCClient() {
		reader = new IRCReader(this);
		sender = new IRCSender();
		parser = new IRCParser();
		broadcaster = new IRCEventBroadcaster();
		broadcaster.addListener(sender);
		broadcaster.addListener(this);
		commandFactory = new IRCCommandFactory();
		try {
			channel = SocketChannel.open();
			channel.configureBlocking(false);
		} catch (IOException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	/**
	 * Connects to the given server in the given port, and then it occupies of
	 * the connection, reading and sending messages.
	 * 
	 * @param server
	 *            Server to connect to.
	 * @param port
	 *            Port to connect to.
	 * @throws IOException
	 * 
	 * @throws IllegalArgumentException
	 *             If server is null.
	 * 
	 * @throws IllegalStateException
	 *             If the client is already connected to a server.
	 * 
	 * @throws AsynchronousCloseException
	 *             If another thread closes the channel while the connect
	 *             operation is in progress
	 *
	 * @throws ClosedByInterruptException
	 *             If another thread interrupts the current thread while the
	 *             connect operation is in progress, thereby closing the channel
	 *             and setting the current thread's interrupt status
	 *
	 * @throws UnresolvedAddressException
	 *             If the given remote address is not fully resolved
	 *
	 * @throws UnsupportedAddressTypeException
	 *             If the type of the given remote address is not supported
	 *
	 * @throws SecurityException
	 *             If a security manager has been installed and it does not
	 *             permit access to the given remote endpoint
	 *
	 * @throws IOException
	 *             If some other I/O error occurs
	 */
	public void run(String server, int port) throws IOException {
		if (server == null) {
			throw new IllegalArgumentException();
		}
		connect(server, port);
		reader.read(channel);
	}

	void feed(String message) {
		IRCMessage ircMessage = parser.parse(message);
		System.out.println("READ: " + ircMessage);
		try {
			IRCCommand command = commandFactory.build(ircMessage, broadcaster);
			command.onExecute();
		} catch (InvalidCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void connect(String server, int port) throws IOException {
		if (channel.isConnected()) {
			throw new IllegalStateException();
		}
		System.out.println("Connecting to server and port...");
		channel.connect(new InetSocketAddress(server, port));
		System.out.println("Connected to server and port.");
		while (!channel.finishConnect()) {
			System.out.println("Trying to connect...");
		}
		broadcaster.onConnect();
	}

	private void sendCommand(IRCCommand command) {
		try {
			sender.send(channel, command.getMessage());
		} catch (IOException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void onConnect() {
		try {
			if (SEND_PASS) {
				sendCommand(new PassCommand(PASSWORD));
			}
			sendCommand(new NickCommand(NICKNAME));
			sendCommand(new UserCommand(USERNAME, REALNAME));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
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
			sendCommand(new JoinCommand(CHANNEL, PASSWORD));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}
	
	@Override
	public void onNotice(String msgtarget, String message) {
		try {
			System.out.println("Names sending");
			sendCommand(new NamesCommand());
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	public static void main(String[] args) throws IOException, IRCException, InterruptedException {
		IRCClient client = new IRCClient();
		client.run("irc.mibbit.net", 6667);
	}
}
