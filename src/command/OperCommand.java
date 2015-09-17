package command;

import event.IRCEventBroadcaster;
import parser.IRCMessage;

/**
 * A normal user uses the OPER command to obtain operator privileges.
 * 
 * @author Tomas
 */
public class OperCommand extends IRCCommand {

	private static final int USERNAME_INDEX = 0;
	private static final int PASSWORD_INDEX = 1;

	private String username;
	private String password;

	/**
	 * @param username
	 *            Username of the user calling the command.
	 * @param password
	 *            Password of the username.
	 * @throws InvalidCommandException
	 *             If username or password are null.
	 */
	public OperCommand(String username, String password) throws InvalidCommandException {
		this(new IRCMessage(USER_COMMAND, username, password), null);
	}

	public OperCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster) throws InvalidCommandException {
		super(ircMessage, broadcaster);
		username = ircMessage.getParameter(USERNAME_INDEX);
		password = ircMessage.getParameter(PASSWORD_INDEX);
		if (username == null || password == null) {
			throw new InvalidCommandException();
		}
	}

	@Override
	public void onExecute() {
		throw new UnsupportedOperationException();
	}
}
