package command.event;

import java.util.List;

import util.IRCConnectionReplyValues;
import event.IRCEventListener;

public class ConnectionReplyEvent extends ReplyEvent implements
		IRCConnectionReplyValues {

	public ConnectionReplyEvent(int replyNumber, List<String> parameters) {
		super(replyNumber, parameters);
	}

	@Override
	void onReplyExecute(IRCEventListener listener, int replyNumber,
			List<String> parameters) {
		if (replyNumber == RPL_WELCOME) {
			listener.onLogin();
		}
		listener.onConnectionReply(replyNumber, parameters);
	}
}
