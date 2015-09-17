package command;

import parser.IRCMessage;
import event.IRCEventBroadcaster;

/**
 * This command should be used to set a password for the connection.
 * 
 * @param password
 * @throws IRCException
 * @throws {@link IllegalArgumentException} If password is null.
 */
public class PassCommand extends IRCCommand {

	private static final int PASSWORD_INDEX = 0;

	private String password;

	/**
	 * @param password
	 *            Password to be used for the connection.
	 * @throws InvalidCommandException
	 *             If password is null.
	 */
	public PassCommand(String password) throws InvalidCommandException {
		this(new IRCMessage(PASS_COMMAND, password), null);
	}

	public PassCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster)
			throws InvalidCommandException {
		super(ircMessage, broadcaster);
		password = ircMessage.getParameter(PASSWORD_INDEX);
		if (password == null) {
			throw new InvalidCommandException();
		}
	}

	@Override
	public void onExecute() {
		throw new UnsupportedOperationException();
	}
}
