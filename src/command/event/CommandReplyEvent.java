package command.event;

import java.util.List;

import event.IRCEventListener;

public class CommandReplyEvent extends ReplyEvent {

	public CommandReplyEvent(int replyNumber, List<String> parameters) {
		super(replyNumber, parameters);
	}

	@Override
	void onReplyExecute(IRCEventListener listener, int replyNumber,
			List<String> parameters) {
		listener.onCommandReply(replyNumber, parameters);
	}
}
