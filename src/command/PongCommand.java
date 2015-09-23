package command;

import parser.IRCMessage;
import parser.IRCMessageImpl;
import event.IRCRawEventListener;

/**
 * This command represents a PONG command (The response to a PING command).
 * 
 * @author Tomas
 */
public class PongCommand extends IRCCommandImpl {

	public static final String PONG_COMMAND = "PONG";

	/**
	 * @param server
	 *            Server to send the pong.
	 * @throws InvalidCommandException
	 *             If server is null.
	 */
	public PongCommand(String server) throws InvalidCommandException {
		super(new IRCMessageImpl(PONG_COMMAND, server));
		if (server == null) {
			throw new InvalidCommandException();
		}
	}

	public PongCommand(IRCMessage ircMessage) throws InvalidCommandException {
		super(ircMessage);
		throw new IllegalStateException();
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		throw new UnsupportedOperationException();
	}
}
