package command;

import parser.IRCMessage;
import parser.IRCMessageImpl;
import event.IRCRawEventListener;

/**
 * This command sends a notice, that works similar to a private message. When
 * read, then a notice has been sent to a channel where the client is, or
 * directly to the client.
 * 
 * @author Tomas
 */
public class NoticeCommand extends IRCCommandImpl {

	public static final String NOTICE_COMMAND = "NOTICE";
	private static final int MSGTARGET_INDEX = 0;
	private static final int MESSAGE_INDEX = 1;

	private String target;
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
		super(new IRCMessageImpl(NOTICE_COMMAND, msgtarget, message));
		if (msgtarget == null || message == null) {
			throw new IllegalArgumentException();
		}
	}

	public NoticeCommand(IRCMessage ircMessage) throws InvalidCommandException {
		super(ircMessage);
		this.target = ircMessage.getParameter(MSGTARGET_INDEX);
		this.message = ircMessage.getParameter(MESSAGE_INDEX);
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onNotice(target, message);
	}
}
