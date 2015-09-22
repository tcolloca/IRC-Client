package command;

import java.util.List;

import model.IRCDao;
import parser.IRCMessage;
import util.ConnectionReplyValues;
import event.IRCEventListener;

public class ConnectionReplyCommand extends IRCCommandImpl implements
		ConnectionReplyValues {

	private int replyNumber;
	private List<String> parameters;

	public ConnectionReplyCommand(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
		try {
			replyNumber = Integer.valueOf(ircMessage.getCommand());
		} catch (NumberFormatException e) {
			throw new InvalidCommandException();
		}
		parameters = ircMessage.getParametersAsList();
		if (parameters == null) {
			throw new IllegalArgumentException();
		}
		parameters = parameters.subList(1, parameters.size());
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		listener.onConnectionReply(replyNumber, parameters);
		if (replyNumber == RPL_WELCOME) {
			listener.onLogin();
		}
	}
}
