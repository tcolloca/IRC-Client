package command;

import model.IRCDao;
import parser.IRCMessage;
import parser.IRCMessageImpl;
import event.IRCEventListener;

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

	public PongCommand(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
		throw new IllegalStateException();
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		throw new UnsupportedOperationException();
	}
}
