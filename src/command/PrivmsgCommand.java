package command;

import parser.IRCMessage;
import parser.IRCMessageImpl;
import event.IRCRawEventListener;

/**
 * Is used to send a message to a msgtarget. When this command is read, then a
 * message was either sent to the client or to any channel where the client is
 * present.
 * 
 * @author Tomas
 */
public class PrivmsgCommand extends IRCCommandImpl {

	public static final String PRIVMSG_COMMAND = "PRIVMSG";
	private static final int MSGTARGET_INDEX = 0;
	private static final int MESSAGE_INDEX = 1;

	private String sender;
	private String msgtarget;
	private String message;

	/**
	 * @param msgtarget
	 *            Target of the message.
	 * @param message
	 *            Message to be sent.
	 * @throws InvalidCommandException
	 *             If msgtarget or message are null.
	 */
	public PrivmsgCommand(String msgtarget, String message)
			throws InvalidCommandException {
		super(new IRCMessageImpl(PRIVMSG_COMMAND, msgtarget, message));
		if (msgtarget == null || message == null) {
			throw new IllegalArgumentException();
		}
	}

	public PrivmsgCommand(IRCMessage ircMessage) throws InvalidCommandException {
		super(ircMessage);
		sender = ircMessage.getPrefix().getString();
		msgtarget = ircMessage.getParameter(MSGTARGET_INDEX);
		message = ircMessage.getParameter(MESSAGE_INDEX);
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onMessage(sender, msgtarget, message);
	}
}
