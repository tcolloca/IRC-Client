package command;

import model.IRCDao;
import parser.IRCMessage;
import parser.IRCMessageImpl;
import event.IRCRawEventListener;

/**
 * Is used to give user a nickname or change the existing one. When this command
 * is read, then it means that the user changed its nickname to the one
 * received.
 * 
 * @author Tomas
 */
public class NickCommand extends IRCCommandImpl {

	public static final String NICK_COMMAND = "NICK";
	private static final int NICK_INDEX = 0;

	private String prevNickname;
	private String newNickname;

	/**
	 * @param nickname
	 *            Nickname to be used from now onwards.
	 * @throws InvalidCommandException
	 *             If nickname are null.
	 */
	public NickCommand(String nickname) throws InvalidCommandException {
		super(new IRCMessageImpl(NICK_COMMAND, nickname));
		if (nickname == null) {
			throw new IllegalArgumentException();
		}
	}

	public NickCommand(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
		prevNickname = ircMessage.getPrefix().getString();
		newNickname = ircMessage.getParameter(NICK_INDEX);
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onNick(prevNickname, newNickname);
	}
}
