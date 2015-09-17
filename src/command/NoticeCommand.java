package command;

import parser.IRCMessage;
import event.IRCEventBroadcaster;

/**
 * This command sends a notice, that works similar to a private message.
 * 
 * @author Tomas
 */
public class NoticeCommand extends IRCCommand {

	private static final int MSGTARGET_INDEX = 0;
	private static final int MESSAGE_INDEX = 1;

	private String msgtarget;
	private String message;

	/**
	 * @param msgtarget
	 *            Target of the notice.
	 * @param message
	 *            Message of the notice.
	 * @throws InvalidCommandException
	 *             If any of the arguments is null.
	 */
	public NoticeCommand(String msgtarget, String message)
			throws InvalidCommandException {
		this(new IRCMessage(NOTICE_COMMAND, msgtarget, message), null);
	}

	public NoticeCommand(IRCMessage ircMessage, IRCEventBroadcaster broadcaster)
			throws InvalidCommandException {
		super(ircMessage, broadcaster);
		msgtarget = ircMessage.getParameter(MSGTARGET_INDEX);
		message = ircMessage.getParameter(MESSAGE_INDEX);
		if (msgtarget == null || message == null) {
			throw new InvalidCommandException();
		}
	}

	@Override
	public void onExecute() {
		getBroadcaster().onNotice(msgtarget, message);
	}
}
