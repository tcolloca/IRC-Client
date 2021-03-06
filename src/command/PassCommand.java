package command;

import parser.IRCMessage;
import parser.IRCMessageImpl;
import event.IRCRawEventListener;

/**
 * Sets the password for the nickname when connecting.
 * 
 * @author Tomas
 */
public class PassCommand extends IRCCommandImpl {

	public static final String PASS_COMMAND = "PASS";

	/**
	 * @param password
	 *            Password of the nickname.
	 * @throws InvalidCommandException
	 *             If password is null.
	 */
	public PassCommand(String password) throws InvalidCommandException {
		super(new IRCMessageImpl(PASS_COMMAND, password));
		if (password == null) {
			throw new InvalidCommandException();
		}
	}

	public PassCommand(IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
		throw new IllegalStateException();
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		throw new UnsupportedOperationException();
	}
}
