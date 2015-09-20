package command;

import model.IRCDao;
import parser.IRCMessage;
import parser.IRCMessageImpl;
import event.IRCEventListener;

/**
 * This command must be used to register with the given username and realname.
 * 
 * @author Tomas
 */
public class UserCommand extends IRCCommandImpl {

	public static final String USER_COMMAND = "USER";
	
	/**
	 * Creates an UserCommand with mode 0.
	 * 
	 * @param username
	 * @param realname
	 * @throws InvalidCommandException
	 *             If username or realname are null.
	 */
	public UserCommand(String username, String realname)
			throws InvalidCommandException {
		this(username, 0, realname);
	}

	/**
	 * @param username
	 * @param mode
	 * @param realname
	 * @throws InvalidCommandException
	 *             If username or realname are null.
	 */
	public UserCommand(String username, int mode, String realname)
			throws InvalidCommandException {
		super(new IRCMessageImpl(USER_COMMAND, username, String.valueOf(mode),
				UNUSED, realname));
		if (username == null || realname == null) {
			throw new InvalidCommandException();
		}
	}

	public UserCommand(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
		throw new IllegalStateException();
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		throw new UnsupportedOperationException();
	}
}
