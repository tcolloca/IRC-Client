package command;

import parser.IRCMessage;
import parser.IRCMessageImpl;
import event.IRCRawEventListener;

/**
 * A normal user uses the OPER command to obtain operator privileges.
 * 
 * @author Tomas
 */
public class OperCommand extends IRCCommandImpl {

	public static final String OPER_COMMAND = "OPER";

	/**
	 * @param username
	 *            Username of the operator.
	 * @param password
	 *            Password of the operator.
	 * @throws InvalidCommandException
	 *             If username or password are null.
	 */
	public OperCommand(String username, String password)
			throws InvalidCommandException {
		super(new IRCMessageImpl(OPER_COMMAND, username, password));
		if (username == null || password == null) {
			throw new InvalidCommandException();
		}
	}

	public OperCommand(IRCMessage ircMessage) throws InvalidCommandException {
		super(ircMessage);
		throw new IllegalStateException();
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		throw new UnsupportedOperationException();
	}
}
