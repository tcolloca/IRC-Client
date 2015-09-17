package command;

import parser.IRCMessage;
import event.IRCEventBroadcaster;

/**
 * This command represents a PING command sent by the server.
 * 
 * @author Tomas
 */
public class PingCommand extends IRCCommand {

	private static final int SERVER_INDEX = 0;

	private String server;

	/**
	 * @param server
	 *            Server that sent the ping.
	 * @throws InvalidCommandException
	 *             If server is null.
	 */
	public PingCommand(String server) throws InvalidCommandException {
		this(new IRCMessage(PING_COMMAND, server), null);
	}

	public PingCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster)
			throws InvalidCommandException {
		super(ircMessage, broadcaster);
		server = ircMessage.getParameter(SERVER_INDEX);
		if (server == null) {
			throw new InvalidCommandException();
		}
	}

	@Override
	public void onExecute() {
		getBroadcaster().onPing(server);
	}
}
