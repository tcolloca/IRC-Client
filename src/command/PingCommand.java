package command;

import parser.IRCMessage;
import event.IRCRawEventListener;

/**
 * This command represents a PING command sent by the server.
 * 
 * @author Tomas
 */
public class PingCommand extends IRCCommandImpl {

	public static final String PING_COMMAND = "PING";
	private static final int SERVER_INDEX = 0;

	private String server;

	public PingCommand(IRCMessage ircMessage) throws InvalidCommandException {
		super(ircMessage);
		server = ircMessage.getParameter(SERVER_INDEX);
		if (server == null) {
			throw new InvalidCommandException();
		}
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onPing(server);
	}
}
