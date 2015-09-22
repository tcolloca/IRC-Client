package command;

import event.IRCEventListener;
import model.IRCDao;
import parser.IRCMessage;
import util.IRCConnectionReplyValues;

public class ConnectionReplyCommand extends ReplyCommand implements IRCConnectionReplyValues {

	public ConnectionReplyCommand(IRCDao dao, IRCMessage ircMessage) throws InvalidCommandException {
		super(dao, ircMessage);
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		listener.onConnectionReply(getReplyNumber(), getParameters());
		if (getReplyNumber() == RPL_WELCOME) {
			listener.onLogin();
		}
	}
}
