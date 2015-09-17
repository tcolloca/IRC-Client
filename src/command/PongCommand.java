package command;

import parser.IRCMessage;
import event.IRCEventBroadcaster;

/**
* This command represents a PONG command (The response to a PING command).
* 
* @author Tomas
*/
public class PongCommand extends IRCCommand {

	private static final int SERVER_INDEX = 0;

	private String server;

	/**
	 * @param server
	 *            Server to send the pong.
	 * @throws InvalidCommandException
	 *             If server is null.
	 */
	public PongCommand(String server) throws InvalidCommandException {
		this(new IRCMessage(PONG_COMMAND, server), null);
	}

	public PongCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster)
			throws InvalidCommandException {
		super(ircMessage, broadcaster);
		server = ircMessage.getParameter(SERVER_INDEX);
		if (server == null) {
			throw new InvalidCommandException();
		}
	}

	@Override
	public void onExecute() {
		throw new UnsupportedOperationException();
	}
}

