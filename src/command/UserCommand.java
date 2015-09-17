package command;

import parser.IRCMessage;
import event.IRCEventBroadcaster;

/**
 * This command must be used to register with the given username and realname.
 * 
 * @author Tomas
 */
public class UserCommand extends IRCCommand {

	private static final int MODE_DEFAULT = 0;
	private static final int USERNAME_INDEX = 0;
	private static final int MODE_INDEX = 1;
	private static final int REALNAME_INDEX = 3;

	private String username;
	private int mode;
	private String unused;
	private String realname;

	/**
	 * @param username
	 * @param realname
	 * @throws InvalidCommandException
	 *             If username or realname are null.
	 */
	public UserCommand(String username, String realname)
			throws InvalidCommandException {
		this(new IRCMessage(USER_COMMAND, username, realname), null);
	}
	
	/**
	 * @param username
	 * @param realname
	 * @throws InvalidCommandException
	 *             If username or realname are null.
	 */
	public UserCommand(String username, int mode, String realname)
			throws InvalidCommandException {
		this(new IRCMessage(USER_COMMAND, username, String.valueOf(mode), realname), null);
		if (username == null || realname == null) {
			throw new InvalidCommandException();
		}
	}

	public UserCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster)
			throws InvalidCommandException {
		super(ircMessage, broadcaster);
		username = ircMessage.getParameter(USERNAME_INDEX);
		if (ircMessage.amountOfParameters() == 2) {			
			mode = MODE_DEFAULT;
			realname = ircMessage.getParameter(REALNAME_INDEX - 2);
		} else if (ircMessage.amountOfParameters() == 3) {
			mode = Integer.valueOf(ircMessage.getParameter(MODE_INDEX));
			realname = ircMessage.getParameter(REALNAME_INDEX - 1);
		} else if (ircMessage.amountOfParameters() == 4) {
			mode = Integer.valueOf(ircMessage.getParameter(MODE_INDEX));
			realname = ircMessage.getParameter(REALNAME_INDEX);
		} else {
			throw new InvalidCommandException();
		}
		unused = UNUSED;
		if (username == null || realname == null) {
			throw new InvalidCommandException();
		}
		ircMessage.setParameters(username, String.valueOf(mode), unused, realname);
	}

	@Override
	public void onExecute() {
		throw new UnsupportedOperationException();
	}
}
